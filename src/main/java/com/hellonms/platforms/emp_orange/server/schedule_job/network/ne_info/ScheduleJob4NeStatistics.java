/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.server.schedule_job.network.ne_info;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.hellonms.platforms.emp_core.server.log.BlackBox;
import com.hellonms.platforms.emp_core.server.log.LEVEL;
import com.hellonms.platforms.emp_core.server.schedule_job.ScheduleJobIf;
import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.server.transaction.UtilTransaction;
import com.hellonms.platforms.emp_core.server.workflow.WorkflowMap;
import com.hellonms.platforms.emp_core.share.error.ERROR_CODE;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_onion.share.model.STATISTICS_TYPE;
import com.hellonms.platforms.emp_orange.server.workflow.network.ne.Worker4NeIf;
import com.hellonms.platforms.emp_orange.server.workflow.network.ne_info.Worker4NeInfoIf;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO;
import com.hellonms.platforms.emp_orange.share.model.network.ne.Model4Ne;
import com.hellonms.platforms.emp_orange.share.model.network.ne_info.Model4NeStatisticsIf;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.Model4NeSessionIf;
import com.hellonms.platforms.emp_orange.share.model.security.operation_log.OPERATION_CODE_ORANGE;
import com.hellonms.platforms.emp_util.date.UtilDate;
import com.hellonms.platforms.emp_util.queue.UtilQueue;
import com.hellonms.platforms.emp_util.string.UtilString;

/**
 * <p>
 * NE 성능정보를 조회한다.
 * </p>
 * 
 * @since 1.6
 * @create 2015. 3. 12.
 * @modified 2015. 3. 12.
 * @author cchyun
 * 
 */
public class ScheduleJob4NeStatistics implements ScheduleJobIf {

	private class NeStatisticsValue {

		private final Date schedule_time;

		private final Model4Ne ne;

		private final EMP_MODEL_NE_INFO[] ne_info_defs;

		public NeStatisticsValue(Date schedule_time, Model4Ne ne, EMP_MODEL_NE_INFO[] ne_info_defs) {
			this.schedule_time = schedule_time;
			this.ne = ne;
			this.ne_info_defs = ne_info_defs;
		}

	}

	private class NeStatisticsQueue implements Runnable {

		private Map<Integer, NeStatisticsValue> queue = new LinkedHashMap<Integer, NeStatisticsValue>();

		private ReentrantLock lock_queue = new ReentrantLock();

		private final Condition condition_queue = lock_queue.newCondition();

		private Set<Integer> task = new HashSet<Integer>();

		private ReentrantLock lock_task = new ReentrantLock();

		public void push(Date schedule_time, Model4Ne ne, EMP_MODEL_NE_INFO[] ne_info_defs) {
			lock_queue.lock();
			try {
				NeStatisticsValue value = queue.get(ne.getNe_id());
				if (value == null) {
					queue.put(ne.getNe_id(), new NeStatisticsValue(schedule_time, ne, ne_info_defs));
					condition_queue.signal();
				} else {
					if (blackBox.isEnabledFor(LEVEL.Site)) {
						blackBox.log(LEVEL.Site, null, UtilString.format("Not finshed previous statistics ne. duplicate : {} : {}", ne, ne_info_defs));
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
								NeStatisticsValue value = queue.remove(ne_id);
								if (value != null) {
									lock_ne(ne_id);
									threadpool_success.execute(new NeStatisticsTask(ne_id, value));
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

	private class NeStatisticsTask implements Runnable {

		private int ne_id;

		private NeStatisticsValue value;

		public NeStatisticsTask(int ne_id, NeStatisticsValue value) {
			this.ne_id = ne_id;
			this.value = value;
		}

		@Override
		public void run() {
			Map<String, Object> request = new LinkedHashMap<String, Object>();
			request.put("operation", "readListNeStatistics");
			request.put("ne_id", value.ne.getNe_id());
			request.put("ne_info_defs", value.ne_info_defs);
			request.put("schedule_time", UtilDate.format(value.schedule_time));
			Map<String, Object> response = new LinkedHashMap<String, Object>();
			Exception exception = null;
			EmpContext context = new EmpContext(this);
			try {
				try {
					saveNeStatistics(context, value.schedule_time, value.ne, value.ne_info_defs);
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
			} finally {
				context.close();
				neStatisticsQueue.unlock_ne(ne_id);
				UtilTransaction.transaction_log(context, "SCHEDULE", OPERATION_CODE_ORANGE.NETWORK_NE_STATISTICS_CREATE, request, exception == null, response, exception);
			}
		}

	}

	private class NeStatisticsSyncValue {

		private final EMP_MODEL_NE_INFO ne_info_def;

		private final STATISTICS_TYPE from_type;

		private final STATISTICS_TYPE to_type;

		private final Date collect_time;

		public NeStatisticsSyncValue(EMP_MODEL_NE_INFO ne_info_def, STATISTICS_TYPE from_type, STATISTICS_TYPE to_type, Date collect_time) {
			this.ne_info_def = ne_info_def;
			this.from_type = from_type;
			this.to_type = to_type;
			this.collect_time = collect_time;
		}
	}

	private class NeStatisticsSyncTask implements Runnable {

		@Override
		public void run() {
			while (true) {
				NeStatisticsSyncValue value = neStatisticsSyncQueue.pop();

				Map<String, Object> request = new LinkedHashMap<String, Object>();
				request.put("operation", "syncListNeStatistics");
				request.put("ne_info_def", value.ne_info_def.getName());
				request.put("from_type", value.from_type);
				request.put("to_type", value.to_type);
				request.put("collect_time", UtilDate.format(value.collect_time));
				Map<String, Object> response = new LinkedHashMap<String, Object>();
				Exception exception = null;

				EmpContext context = new EmpContext(this);
				try {
					try {
						syncNeStatistics(context, value.ne_info_def, value.from_type, value.to_type, value.collect_time);
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
				} finally {
					context.close();
					UtilTransaction.transaction_log(context, "SCHEDULE", OPERATION_CODE_ORANGE.NETWORK_NE_STATISTICS_CREATE, request, exception == null, response, exception);
				}
			}
		}
	}

	private Worker4NeIf worker4Ne;

	private Worker4NeInfoIf worker4NeInfo;

	private ThreadPoolExecutor threadpool_success;

	private NeStatisticsQueue neStatisticsQueue;

	private UtilQueue<NeStatisticsSyncValue> neStatisticsSyncQueue = new UtilQueue<NeStatisticsSyncValue>(256);

	private Calendar calendar_sync = Calendar.getInstance();

	private static final BlackBox blackBox = new BlackBox(ScheduleJob4NeStatistics.class);

	@Override
	public Class<? extends ScheduleJobIf> getDefine_class() {
		return ScheduleJob4NeStatistics.class;
	}

	@Override
	public void initialize(EmpContext context) throws EmpException {
		worker4Ne = (Worker4NeIf) WorkflowMap.getWorker(Worker4NeIf.class);
		worker4NeInfo = (Worker4NeInfoIf) WorkflowMap.getWorker(Worker4NeInfoIf.class);

		threadpool_success = (ThreadPoolExecutor) Executors.newFixedThreadPool(16);

		neStatisticsQueue = new NeStatisticsQueue();
		Thread thread_queue = new Thread(neStatisticsQueue, "ScheduleJob4NeStatistics::NeStatisticsQueue");
		thread_queue.setDaemon(true);
		thread_queue.start();

		Thread thread_sync = new Thread(new NeStatisticsSyncTask(), "ScheduleJob4NeStatistics::NeStatisticsSyncTask");
		thread_sync.setDaemon(true);
		thread_sync.start();

		calendar_sync.setTimeInMillis(0L);
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

			Multimap<EMP_MODEL_NE, EMP_MODEL_NE_INFO> statistics_map = ArrayListMultimap.create();
			for (EMP_MODEL_NE ne_def : EMP_MODEL.current().getNes()) {
				for (EMP_MODEL_NE_INFO ne_info_def : EMP_MODEL.current().getNe_statisticss_by_ne(ne_def.getCode())) {
					if (ne_info_def.getStat_type().isStatistics(second_of_day)) {
						statistics_map.put(ne_def, ne_info_def);
					}
				}
			}

			for (EMP_MODEL_NE ne_code : statistics_map.keySet()) {
				Model4Ne[] nes = worker4Ne.queryListNe(context, ne_code, 0, 999999);
				Collection<EMP_MODEL_NE_INFO> statistics_list = statistics_map.get(ne_code);
				for (Model4Ne ne : nes) {
					List<EMP_MODEL_NE_INFO> ne_info_list = new ArrayList<EMP_MODEL_NE_INFO>();
					for (EMP_MODEL_NE_INFO ne_info_def : statistics_list) {
						Model4NeSessionIf ne_session = ne.getNeSession(ne_info_def.getProtocol());
						if (ne_session != null && ne_session.isAdmin_state()) {
							ne_info_list.add(ne_info_def);
						}
					}
					if (0 < ne_info_list.size()) {
						neStatisticsQueue.push(calendar.getTime(), ne, ne_info_list.toArray(new EMP_MODEL_NE_INFO[0]));
					}
				}
			}

			Date collect_time_hour = null;
			Date collect_time_day = null;
			Date collect_time_month = null;
			if (calendar_sync.get(Calendar.YEAR) != calendar.get(Calendar.YEAR) || calendar_sync.get(Calendar.MONTH) != calendar.get(Calendar.MONTH)) {
				calendar_sync = calendar;

				Calendar calendar_hour = Calendar.getInstance();
				calendar_hour.setTime(calendar.getTime());
				calendar_hour.add(Calendar.HOUR_OF_DAY, -1);
				collect_time_hour = UtilDate.startHour(calendar_hour.getTime());

				Calendar calendar_day = Calendar.getInstance();
				calendar_day.setTime(calendar.getTime());
				calendar_day.add(Calendar.DAY_OF_MONTH, -1);
				collect_time_day = UtilDate.startDay(calendar_day.getTime());

				Calendar calendar_month = Calendar.getInstance();
				calendar_month.setTime(calendar.getTime());
				calendar_month.add(Calendar.MONTH, -1);
				collect_time_month = UtilDate.startMonth(calendar_month.getTime());
			} else if (calendar_sync.get(Calendar.DAY_OF_MONTH) != calendar.get(Calendar.DAY_OF_MONTH)) {
				calendar_sync = calendar;

				Calendar calendar_hour = Calendar.getInstance();
				calendar_hour.setTime(calendar.getTime());
				calendar_hour.add(Calendar.HOUR_OF_DAY, -1);
				collect_time_hour = UtilDate.startHour(calendar_hour.getTime());

				Calendar calendar_day = Calendar.getInstance();
				calendar_day.setTime(calendar.getTime());
				calendar_day.add(Calendar.DAY_OF_MONTH, -1);
				collect_time_day = UtilDate.startDay(calendar_day.getTime());
			} else if (calendar_sync.get(Calendar.HOUR_OF_DAY) != calendar.get(Calendar.HOUR_OF_DAY)) {
				calendar_sync = calendar;

				Calendar calendar_hour = Calendar.getInstance();
				calendar_hour.setTime(calendar.getTime());
				calendar_hour.add(Calendar.HOUR_OF_DAY, -1);
				collect_time_hour = UtilDate.startHour(calendar_hour.getTime());
			}

			if (collect_time_hour != null || collect_time_day != null || collect_time_month != null) {
				for (EMP_MODEL_NE ne_def : EMP_MODEL.current().getNes()) {
					for (EMP_MODEL_NE_INFO ne_info_def : EMP_MODEL.current().getNe_statisticss_by_ne(ne_def.getCode())) {
						STATISTICS_TYPE statistics_type = ne_info_def.getStat_type();
						if (collect_time_hour != null) {
							STATISTICS_TYPE from_type = statistics_type;
							STATISTICS_TYPE to_type = STATISTICS_TYPE.HOUR_1;
							if (blackBox.isEnabledFor(LEVEL.UseCase)) {
								blackBox.log(LEVEL.UseCase, context, UtilString.format("queue sync ne statistics {}, {}, {}, {}", ne_info_def, from_type, to_type, collect_time_hour));
							}
							neStatisticsSyncQueue.push(new NeStatisticsSyncValue(ne_info_def, from_type, to_type, collect_time_hour));
						}
						if (collect_time_day != null) {
							STATISTICS_TYPE from_type = STATISTICS_TYPE.HOUR_1;
							STATISTICS_TYPE to_type = STATISTICS_TYPE.DAY_1;
							if (blackBox.isEnabledFor(LEVEL.UseCase)) {
								blackBox.log(LEVEL.UseCase, context, UtilString.format("queue sync ne statistics {}, {}, {}, {}", ne_info_def, from_type, to_type, collect_time_day));
							}
							neStatisticsSyncQueue.push(new NeStatisticsSyncValue(ne_info_def, from_type, to_type, collect_time_day));
						}
						if (collect_time_month != null) {
							STATISTICS_TYPE from_type = STATISTICS_TYPE.DAY_1;
							STATISTICS_TYPE to_type = STATISTICS_TYPE.MONTH_1;
							if (blackBox.isEnabledFor(LEVEL.UseCase)) {
								blackBox.log(LEVEL.UseCase, context, UtilString.format("queue sync ne statistics {}, {}, {}, {}", ne_info_def, from_type, to_type, collect_time_month));
							}
							neStatisticsSyncQueue.push(new NeStatisticsSyncValue(ne_info_def, from_type, to_type, collect_time_month));
						}
					}
				}
			}
		} catch (EmpException e) {
			throw e;
		} catch (Exception e) {
			throw new EmpException(e, ERROR_CODE.ERROR_UNKNOWN);
		}
	}

	protected int saveNeStatistics(EmpContext context, Date schedule_time, Model4Ne ne, EMP_MODEL_NE_INFO[] ne_info_defs) throws EmpException {
		int count = 0;
		for (EMP_MODEL_NE_INFO ne_info_def : ne_info_defs) {
			if (blackBox.isEnabledFor(LEVEL.Site)) {
				blackBox.log(LEVEL.Site, context, UtilString.format("start readListNeStatistics {}, {}, {}, {}", ne.getNe_id(), ne_info_def, schedule_time));
			}
			Model4NeStatisticsIf[] ne_statistics = worker4NeInfo.readListNeStatistics(context, ne.getNe_id(), ne_info_def, schedule_time);
			count += ne_statistics.length;
			if (blackBox.isEnabledFor(LEVEL.Site)) {
				blackBox.log(LEVEL.Site, context, UtilString.format("end readListNeStatistics {}, {}, {}, {}", ne.getNe_id(), ne_info_def, schedule_time));
			}
		}
		return count;
	}

	protected int syncNeStatistics(EmpContext context, EMP_MODEL_NE_INFO ne_info_def, STATISTICS_TYPE from_type, STATISTICS_TYPE to_type, Date collect_time) throws EmpException {
		if (blackBox.isEnabledFor(LEVEL.Site)) {
			blackBox.log(LEVEL.Site, context, UtilString.format("start syncListNeStatistics ne_info_def={}, from_type={}, to_type={}, collect_time={}", ne_info_def, from_type, to_type, collect_time));
		}
		int count = worker4NeInfo.syncListNeStatistics(context, ne_info_def, from_type, to_type, collect_time);
		if (blackBox.isEnabledFor(LEVEL.Site)) {
			blackBox.log(LEVEL.Site, context, UtilString.format("end syncListNeStatistics ne_info_def={}, from_type={}, to_type={}, collect_time={} : count={}", ne_info_def, from_type, to_type, collect_time, count));
		}
		return count;
	}

}
