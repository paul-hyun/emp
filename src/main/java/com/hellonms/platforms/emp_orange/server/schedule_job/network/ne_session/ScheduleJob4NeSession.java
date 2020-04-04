/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.server.schedule_job.network.ne_session;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import com.hellonms.platforms.emp_core.server.log.BlackBox;
import com.hellonms.platforms.emp_core.server.log.LEVEL;
import com.hellonms.platforms.emp_core.server.schedule_job.ScheduleJobIf;
import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.server.transaction.UtilTransaction;
import com.hellonms.platforms.emp_core.server.workflow.WorkflowMap;
import com.hellonms.platforms.emp_core.share.error.ERROR_CODE;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_orange.server.workflow.network.ne.Worker4NeIf;
import com.hellonms.platforms.emp_orange.server.workflow.network.ne_info.Worker4NeInfoIf;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.Model4NeSessionIf;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.NE_SESSION_PROTOCOL;
import com.hellonms.platforms.emp_orange.share.model.security.operation_log.OPERATION_CODE_ORANGE;
import com.hellonms.platforms.emp_util.date.UtilDate;
import com.hellonms.platforms.emp_util.string.UtilString;

/**
 * <p>
 * NE 통신채널 상태를 확인한다.
 * </p>
 * 
 * @since 1.6
 * @create 2015. 3. 12.
 * @modified 2015. 3. 12.
 * @author cchyun
 * 
 */
public class ScheduleJob4NeSession implements ScheduleJobIf {

	private class TestNeSessionValue {

		private final Date schedule_time;

		private final Model4NeSessionIf ne_session;

		public TestNeSessionValue(Date schedule_time, Model4NeSessionIf ne_session) {
			this.schedule_time = schedule_time;
			this.ne_session = ne_session;
		}

	}

	private class TestNeSessionQueue implements Runnable {

		private Map<Integer, Map<NE_SESSION_PROTOCOL, TestNeSessionValue>> queue = new LinkedHashMap<Integer, Map<NE_SESSION_PROTOCOL, TestNeSessionValue>>();

		private ReentrantLock lock_queue = new ReentrantLock();

		private final Condition condition_queue = lock_queue.newCondition();

		private Set<Integer> task = new HashSet<Integer>();

		private ReentrantLock lock_task = new ReentrantLock();

		public void push(Date schedule_time, Model4NeSessionIf ne_session) {
			lock_queue.lock();
			try {
				Map<NE_SESSION_PROTOCOL, TestNeSessionValue> value_map = queue.get(ne_session.getNe_id());
				if (value_map == null) {
					value_map = new LinkedHashMap<NE_SESSION_PROTOCOL, TestNeSessionValue>();
					queue.put(ne_session.getNe_id(), value_map);
				}
				if (!value_map.containsKey(ne_session.getProtocol())) {
					value_map.put(ne_session.getProtocol(), new TestNeSessionValue(schedule_time, ne_session));
					condition_queue.signal();
				} else {
					if (blackBox.isEnabledFor(LEVEL.Site)) {
						blackBox.log(LEVEL.Site, null, UtilString.format("Not finshed previous test ne session. duplicate : {}", ne_session));
					}
				}
			} finally {
				lock_queue.unlock();
			}
		}

		@Override
		public void run() {
			while (true) {
				lock_queue.lock();
				try {
					if (queue.size() == 0) {
						condition_queue.await();
					} else {
						Object[] keys = queue.keySet().toArray();
						for (Object key : keys) {
							int ne_id = (Integer) key;
							if (!is_locked_ne(ne_id)) {
								Map<NE_SESSION_PROTOCOL, TestNeSessionValue> value_map = queue.remove(ne_id);
								TestNeSessionValue[] values = value_map.values().toArray(new TestNeSessionValue[0]);
								if (values != null && 0 < values.length) {
									boolean ne_session_state = true;
									for (TestNeSessionValue value : values) {
										if (!value.ne_session.isNe_session_state()) {
											ne_session_state = false;
											break;
										}
									}

									lock_ne(ne_id);
									if (ne_session_state) {
										threadpool_success.execute(new TestNeSessionTask(ne_id, values));
									} else {
										threadpool_fail.execute(new TestNeSessionTask(ne_id, values));
									}
								}
							}
						}
					}
				} catch (Exception e) {
					if (blackBox.isEnabledFor(LEVEL.Fatal)) {
						blackBox.log(LEVEL.Fatal, null, e);
					}
				} finally {
					lock_queue.unlock();
				}

				if (0 < queue.size()) {
					try {
						Thread.sleep(100);
					} catch (Exception e) {
						if (blackBox.isEnabledFor(LEVEL.Fatal)) {
							blackBox.log(LEVEL.Fatal, null, e);
						}
					}
				}
			}
		}

		public boolean is_locked_ne(int ne_id) {
			lock_task.lock();
			try {
				return task.contains(ne_id);
			} finally {
				lock_task.unlock();
			}
		}

		public void lock_ne(int ne_id) {
			lock_task.lock();
			try {
				task.add(ne_id);
			} finally {
				lock_task.unlock();
			}
		}

		public void unlock_ne(int ne_id) {
			lock_task.lock();
			try {
				task.remove(ne_id);
			} finally {
				lock_task.unlock();
			}
		}
	}

	private class TestNeSessionTask implements Runnable {

		private int ne_id;

		private TestNeSessionValue[] values;

		public TestNeSessionTask(int ne_id, TestNeSessionValue[] values) {
			this.ne_id = ne_id;
			this.values = values;
		}

		@Override
		public void run() {
			Map<String, Object> request = new LinkedHashMap<String, Object>();
			request.put("operation", "testNeSession");
			request.put("ne_id", ne_id);
			Map<String, Object> response = new LinkedHashMap<String, Object>();
			Exception exception = null;

			EmpContext context = new EmpContext(this);
			try {
				for (TestNeSessionValue value : values) {
					request.put(UtilString.format("schedule_time.{}", value.ne_session.getProtocol()), UtilDate.format(value.schedule_time));
					try {
						Model4NeSessionIf ne_session = testNeSession(context, value.schedule_time, value.ne_session);
						response.put(UtilString.format("ne_session_state.{}", ne_session.getProtocol()), ne_session.isNe_session_state());
						context.commit();
					} catch (EmpException e) {
						exception = e;
						context.rollback();
						if (blackBox.isEnabledFor(LEVEL.Fatal)) {
							blackBox.log(LEVEL.Site, context, e);
						}
					} catch (Exception e) {
						exception = e;
						context.rollback();
						if (blackBox.isEnabledFor(LEVEL.Fatal)) {
							blackBox.log(LEVEL.Site, context, e);
						}
					}
				}
			} finally {
				context.close();
				testNeSessionQueue.unlock_ne(ne_id);
				UtilTransaction.transaction_log(context, "SCHEDULE", OPERATION_CODE_ORANGE.NETWORK_NE_READ, request, exception == null, response, exception);
			}
		}

	}

	private Worker4NeIf worker4Ne;

	private Worker4NeInfoIf worker4NeInfo;

	private ThreadPoolExecutor threadpool_success;

	private ThreadPoolExecutor threadpool_fail;

	private TestNeSessionQueue testNeSessionQueue;

	private static final BlackBox blackBox = new BlackBox(ScheduleJob4NeSession.class);

	@Override
	public Class<? extends ScheduleJobIf> getDefine_class() {
		return ScheduleJob4NeSession.class;
	}

	@Override
	public void initialize(EmpContext context) throws EmpException {
		worker4Ne = (Worker4NeIf) WorkflowMap.getWorker(Worker4NeIf.class);
		worker4NeInfo = (Worker4NeInfoIf) WorkflowMap.getWorker(Worker4NeInfoIf.class);

		threadpool_success = (ThreadPoolExecutor) Executors.newFixedThreadPool(16);
		threadpool_fail = (ThreadPoolExecutor) Executors.newFixedThreadPool(8);

		testNeSessionQueue = new TestNeSessionQueue();
		Thread thread = new Thread(testNeSessionQueue, "ScheduleJob4NeSession::TestNeSessionQueue");
		thread.setDaemon(true);
		thread.start();
	}

	@Override
	public void dispose(EmpContext context) throws EmpException {
	}

	@Override
	public void execute(EmpContext context, Date schedule_time) throws EmpException {
		try {
			int second_of_day = 0;

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(schedule_time);
			calendar.set(Calendar.MILLISECOND, 0);

			second_of_day += calendar.get(Calendar.HOUR_OF_DAY) * 60 * 60;
			second_of_day += calendar.get(Calendar.MINUTE) * 60;
			second_of_day += calendar.get(Calendar.SECOND);
			second_of_day -= (second_of_day % 5);

			Model4NeSessionIf[] ne_sessions = worker4Ne.queryListNeSessionBySchedule(context, second_of_day);
			for (Model4NeSessionIf ne_session : ne_sessions) {
				testNeSessionQueue.push(calendar.getTime(), ne_session);
			}
		} catch (EmpException e) {
			throw e;
		} catch (Exception e) {
			throw new EmpException(e, ERROR_CODE.ERROR_UNKNOWN);
		}
	}

	protected Model4NeSessionIf preTestNeSession(EmpContext context, Date schedule_time, Model4NeSessionIf ne_session) throws EmpException {
		return ne_session;
	}

	protected Model4NeSessionIf testNeSession(EmpContext context, Date schedule_time, Model4NeSessionIf ne_session) throws EmpException {
		ne_session = preTestNeSession(context, schedule_time, ne_session);

		if (blackBox.isEnabledFor(LEVEL.Site)) {
			blackBox.log(LEVEL.Site, context, UtilString.format("start testNeSession {}, {}, {}", ne_session.getNe_id(), ne_session.getProtocol(), schedule_time));
		}
		ne_session = worker4Ne.testNeSession(context, ne_session.getNe_id(), ne_session.getProtocol(), schedule_time);
		if (blackBox.isEnabledFor(LEVEL.Site)) {
			blackBox.log(LEVEL.Site, context, UtilString.format("end testNeSession {}, {}, {}", ne_session.getNe_id(), ne_session.getProtocol(), schedule_time));
		}

		ne_session = postTestNeSession(context, schedule_time, ne_session);

		return ne_session;
	}

	protected Model4NeSessionIf postTestNeSession(EmpContext context, Date schedule_time, Model4NeSessionIf ne_session) throws EmpException {
		if (ne_session.isNe_session_state()) {
			worker4NeInfo.syncListNeInfo(context, ne_session.getNe_id(), ne_session.getProtocol());
		}
		return ne_session;
	}

}
