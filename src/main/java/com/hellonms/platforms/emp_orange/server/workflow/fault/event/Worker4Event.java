/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.server.workflow.fault.event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import com.hellonms.platforms.emp_core.server.log.BlackBox;
import com.hellonms.platforms.emp_core.server.log.LEVEL;
import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.server.workflow.WorkerIf;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_onion.share.model.STATISTICS_TYPE;
import com.hellonms.platforms.emp_orange.share.error.ERROR_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_EVENT;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO;
import com.hellonms.platforms.emp_orange.share.model.fault.event.GEN_TYPE;
import com.hellonms.platforms.emp_orange.share.model.fault.event.Model4Alarm;
import com.hellonms.platforms.emp_orange.share.model.fault.event.Model4AlarmStatistics;
import com.hellonms.platforms.emp_orange.share.model.fault.event.Model4AlarmStatistics.ITEM;
import com.hellonms.platforms.emp_orange.share.model.fault.event.Model4AlarmSummary;
import com.hellonms.platforms.emp_orange.share.model.fault.event.Model4Event;
import com.hellonms.platforms.emp_orange.share.model.fault.event.SEVERITY;
import com.hellonms.platforms.emp_util.cache.UtilCache;
import com.hellonms.platforms.emp_util.string.UtilString;

/**
 * <p>
 * Worker for Event
 * </p>
 *
 * @since 1.6
 * @create 2015. 4. 21.
 * @modified 2015. 4. 21.
 * @author cchyun
 *
 */
public class Worker4Event implements Worker4EventIf {

	private class EventLogQueue {

		private Map<Integer, List<Model4Event>> queue = Collections.synchronizedMap(new LinkedHashMap<Integer, List<Model4Event>>());

		private final ReentrantLock lock = new ReentrantLock();

		private final Condition condition = lock.newCondition();

		private Set<Integer> task = new HashSet<Integer>();

		public void push(Model4Event event) {
			lock.lock();
			try {
				List<Model4Event> event_list = queue.get(event.getNe_id());
				if (event_list == null) {
					event_list = new ArrayList<Model4Event>();
					queue.put(event.getNe_id(), event_list);
				}
				event_list.add(event);
				condition.signal();
			} finally {
				lock.unlock();
			}
		}

		public void push(Model4Event[] events) {
			lock.lock();
			try {
				for (Model4Event event : events) {
					List<Model4Event> event_list = queue.get(event.getNe_id());
					if (event_list == null) {
						event_list = new ArrayList<Model4Event>();
						queue.put(event.getNe_id(), event_list);
					}
					event_list.add(event);
				}
				condition.signal();
			} finally {
				lock.unlock();
			}
		}

		public List<Model4Event> pop_and_lock() {
			lock.lock();
			try {
				while (queue.isEmpty()) {
					try {
						condition.await();
					} catch (InterruptedException e) {
					}
				}
				Object[] keys = queue.keySet().toArray();
				for (Object key : keys) {
					int ne_id = (Integer) key;
					if (!task.contains(ne_id)) {
						task.add(ne_id);
						List<Model4Event> event_list = queue.remove(ne_id);
						if (0 < event_list.size()) {
							return event_list;
						}
					}
				}
				return null;
			} finally {
				lock.unlock();
			}
		}

		public void unlock_ne(int ne_id) {
			lock.lock();
			try {
				task.remove(ne_id);
			} finally {
				lock.unlock();
			}
		}

		public int size() {
			return queue.size();
		}
	}

	private class NotifyEventTask implements Runnable {

		@Override
		public void run() {
			while (true) {
				List<Model4Event> events = eventLogQueue.pop_and_lock();
				if (events != null && 0 < events.size()) {
					int ne_id = events.get(0).getNe_id();
					boolean queryNextUpdate_seq_fault = false;

					EmpContext context = new EmpContext(this);
					context.setAuto_commit(false);
					try {
						int no_commit_count = 0;
						for (Model4Event event : events) {
							try {
								hendleEvent(context, event);
								queryNextUpdate_seq_fault = true;
							} catch (EmpException e) {
								if (blackBox.isEnabledFor(LEVEL.Fatal)) {
									blackBox.log(LEVEL.Site, context, e);
								}
							} catch (Exception e) {
								if (blackBox.isEnabledFor(LEVEL.Fatal)) {
									blackBox.log(LEVEL.Site, context, e);
								}
							}
							no_commit_count++;
							if (15 < no_commit_count) {
								context.commit();
								no_commit_count = 0;
							}
						}
						if (0 < no_commit_count) {
							context.commit();
							no_commit_count = 0;
						}
					} finally {
						if (queryNextUpdate_seq_fault) {
							try {
								queryNextUpdate_seq_fault(context);
								context.commit();
							} catch (Throwable e) {
								if (blackBox.isEnabledFor(LEVEL.Fatal)) {
									blackBox.log(LEVEL.Site, context, e);
								}
							}
						}
						context.close();
						eventLogQueue.unlock_ne(ne_id);
					}
				}
			}
		}

	}

	protected Dao4EventIf dao4Event;

	protected Dao4AlarmIf dao4Alarm;

	protected Dao4AlarmStatisticsIf dao4AlarmStatistics;

	protected EventLogQueue eventLogQueue;

	protected static final BlackBox blackBox = new BlackBox(Worker4Event.class);

	public void setDao4Event(Dao4EventIf dao4Event) {
		this.dao4Event = dao4Event;
	}

	public void setDao4Alarm(Dao4AlarmIf dao4Alarm) {
		this.dao4Alarm = dao4Alarm;
	}

	public void setDao4AlarmStatistics(Dao4AlarmStatisticsIf dao4AlarmStatistics) {
		this.dao4AlarmStatistics = dao4AlarmStatistics;
	}

	@Override
	public Class<? extends WorkerIf> getDefine_class() {
		return Worker4EventIf.class;
	}

	@Override
	public void initialize(EmpContext context) throws EmpException {
		if (dao4Event == null) {
			throw new EmpException(ERROR_CODE_ORANGE.WORKFLOW_NOTSETTED, Dao4EventIf.class, getClass());
		}
		if (dao4Alarm == null) {
			throw new EmpException(ERROR_CODE_ORANGE.WORKFLOW_NOTSETTED, Dao4AlarmIf.class, getClass());
		}
		if (dao4AlarmStatistics == null) {
			throw new EmpException(ERROR_CODE_ORANGE.WORKFLOW_NOTSETTED, Dao4AlarmStatisticsIf.class, getClass());
		}
		dao4Event.initialize(context);
		dao4Alarm.initialize(context);
		dao4AlarmStatistics.initialize(context);

		eventLogQueue = new EventLogQueue();

		for (int i = 0; i < 8; i++) {
			Thread thread = new Thread(new NotifyEventTask(), UtilString.format("Worker4Event::Handler.{}", i));
			thread.setDaemon(true);
			thread.start();
		}
	}

	@Override
	public void dispose(EmpContext context) throws EmpException {
	}

	@Override
	public void notifyEvent(EmpContext context, Model4Event event) throws EmpException {
		eventLogQueue.push(event);
	}

	@Override
	public void notifyEvents(EmpContext context, Model4Event[] events) throws EmpException {
		eventLogQueue.push(events);
	}

	@Override
	public int eventQueueSize(EmpContext context) throws EmpException {
		return eventLogQueue.size();
	}

	protected Model4Event hendleEvent(EmpContext context, Model4Event event) throws EmpException {
		if (event.isAlarm()) {
			Model4Alarm alarm_active = null;
			try {
				alarm_active = dao4Alarm.queryAlarmActive(context, event.getNe_id(), event.getNe_info_code(), event.getNe_info_index(), event.getEvent_code());
			} catch (EmpException e) {
			}

			if (event.getGen_type() != GEN_TYPE.AUTO) { // 알람 필터링
				if (event.getSeverity() == SEVERITY.CLEAR && alarm_active == null) { // 3.1. 현재알람이 존재하지 않는 경우 중복해지 방지
					return null;
				} else if (alarm_active != null && event.getSeverity() == alarm_active.getSeverity()) { // 3.2. 현재알람이 이미 존재하는 경우 중복발생 방지
					return null;
				}
			}

			if (event.getSeverity() == SEVERITY.CLEAR) {
				if (alarm_active == null) {
					event = createEvent(context, event);
				} else {
					event = updateAlarmByClear(context, event, alarm_active);
				}
			} else {
				if (alarm_active == null) {
					createAlarm(context, event);
				} else {
					if (event.getSeverity() == alarm_active.getSeverity()) {
						updateAlarmByRepetition(context, event, alarm_active);
					} else {
						updateAlarmBySeverity(context, event, alarm_active);
					}
				}
			}
		} else {
			event = createEvent(context, event);
		}
		return event;
	}

	protected Model4Event createEvent(EmpContext context, Model4Event event) throws EmpException {
		event = dao4Event.createEvent(context, event);
		return event;
	}

	protected Model4Event createAlarm(EmpContext context, Model4Event event) throws EmpException {
		event = dao4Event.createEvent(context, event);

		Model4Alarm alarm_active = event.toAlarm();
		dao4Alarm.createAlarm(context, alarm_active);

		return event;
	}

	protected Model4Event updateAlarmByClear(EmpContext context, Model4Event event, Model4Alarm alarm_active) throws EmpException {
		event = dao4Event.createEvent(context, event);

		alarm_active = alarm_active.setEventByClear(event);
		alarm_active = dao4Alarm.updateAlarmByClear(context, alarm_active);

		return event;
	}

	protected Model4Event updateAlarmByRepetition(EmpContext context, Model4Event event, Model4Alarm alarm_active) throws EmpException {
		event = dao4Event.createEvent(context, event);

		// alarm_active = alarm_active.setEventByRepetition(event);
		// dao4Alarm.updateAlarmByRepetition(context, alarm_active);

		return event;
	}

	protected Model4Event updateAlarmBySeverity(EmpContext context, Model4Event event, Model4Alarm alarm_clear) throws EmpException {
		event = dao4Event.createEvent(context, event);

		// 알람 등급 변경
		alarm_clear = alarm_clear.setEventByClear(event);
		Model4Alarm alarm_active = event.toAlarm();
		alarm_active = dao4Alarm.updateAlarmBySeverity(context, alarm_clear, alarm_active);

		return event;
	}

	@Override
	public Model4Event queryEvent(EmpContext context, long event_id) throws EmpException {
		Model4Event event = dao4Event.queryEvent(context, event_id);
		return event;
	}

	@Override
	public Model4Event[] queryListEventByNeGroup(EmpContext context, int ne_group_id, EMP_MODEL_EVENT event_def, SEVERITY severity, Date fromTime, Date toTime, int startNo, int count) throws EmpException {
		Model4Event[] events = dao4Event.queryListEventByNeGroup(context, ne_group_id, event_def, severity, fromTime, toTime, startNo, count);
		return events;
	}

	@Override
	public Model4Event[] queryListEventByNe(EmpContext context, int ne_id, EMP_MODEL_EVENT event_def, SEVERITY severity, Date fromTime, Date toTime, int startNo, int count) throws EmpException {
		Model4Event[] events = dao4Event.queryListEventByNe(context, ne_id, event_def, severity, fromTime, toTime, startNo, count);
		return events;
	}

	@Override
	public Model4Event[] queryListEventByConsole(EmpContext context, int startNo, int count) throws EmpException {
		Model4Event[] events = dao4Event.queryListEventByConsole(context, startNo, count);
		return events;
	}

	@Override
	public int queryCountEventByNeGroup(EmpContext context, int ne_group_id, EMP_MODEL_EVENT event_def, SEVERITY severity, Date fromTime, Date toTime) throws EmpException {
		return dao4Event.queryCountEventByNeGroup(context, ne_group_id, event_def, severity, fromTime, toTime);
	}

	@Override
	public int queryCountEventByNe(EmpContext context, int ne_id, EMP_MODEL_EVENT event_def, SEVERITY severity, Date fromTime, Date toTime) throws EmpException {
		return dao4Event.queryCountEventByNe(context, ne_id, event_def, severity, fromTime, toTime);
	}

	@Override
	public Model4Alarm queryAlarm(EmpContext context, long gen_first_event_id) throws EmpException {
		Model4Alarm alarm = dao4Alarm.queryAlarm(context, gen_first_event_id);
		return alarm;
	}

	@Override
	public Model4Alarm[] queryListAlarmByNeGroup(EmpContext context, int ne_group_id, EMP_MODEL_EVENT event_def, SEVERITY severity, Date fromTime, Date toTime, int startNo, int count) throws EmpException {
		Model4Alarm[] alarms = dao4Alarm.queryListAlarmByNeGroup(context, ne_group_id, event_def == null ? 0 : event_def.getCode(), severity, fromTime, toTime, startNo, count);
		return alarms;
	}

	@Override
	public Model4Alarm[] queryListAlarmByNe(EmpContext context, int ne_id, EMP_MODEL_EVENT event_def, SEVERITY severity, Date fromTime, Date toTime, int startNo, int count) throws EmpException {
		Model4Alarm[] alarms = dao4Alarm.queryListAlarmByNe(context, ne_id, event_def == null ? 0 : event_def.getCode(), severity, fromTime, toTime, startNo, count);
		return alarms;
	}

	@Override
	public Model4Alarm[] queryListAlarmByConsole(EmpContext context, int startNo, int count) throws EmpException {
		Model4Alarm[] alarms = dao4Alarm.queryListAlarmByConsole(context, startNo, count);
		return alarms;
	}

	@Override
	public int queryCountAlarmByNeGroup(EmpContext context, int ne_group_id, EMP_MODEL_EVENT event_def, SEVERITY severity, Date fromTime, Date toTime) throws EmpException {
		return dao4Alarm.queryCountAlarmByNeGroup(context, ne_group_id, event_def == null ? 0 : event_def.getCode(), severity, fromTime, toTime);
	}

	@Override
	public int queryCountAlarmByNe(EmpContext context, int ne_id, EMP_MODEL_EVENT event_def, SEVERITY severity, Date fromTime, Date toTime) throws EmpException {
		return dao4Alarm.queryCountAlarmByNe(context, ne_id, event_def == null ? 0 : event_def.getCode(), severity, fromTime, toTime);
	}

	@Override
	public Model4Alarm queryAlarmActive(EmpContext context, long gen_first_event_id) throws EmpException {
		Model4Alarm alarm = dao4Alarm.queryAlarmActive(context, gen_first_event_id);
		return alarm;
	}

	@Override
	public Model4Alarm[] queryListAlarmActiveByNeGroup(EmpContext context, int ne_group_id, EMP_MODEL_EVENT event_def, SEVERITY severity, int startNo, int count) throws EmpException {
		Model4Alarm[] alarms = dao4Alarm.queryListAlarmActiveByNeGroup(context, ne_group_id, event_def == null ? 0 : event_def.getCode(), severity, startNo, count);
		return alarms;
	}

	@Override
	public Model4Alarm[] queryListAlarmActiveByNe(EmpContext context, int ne_id, EMP_MODEL_NE_INFO ne_info_def, EMP_MODEL_EVENT event_def, SEVERITY severity, int startNo, int count) throws EmpException {
		Model4Alarm[] alarms = dao4Alarm.queryListAlarmActiveByNe(context, ne_id, ne_info_def == null ? 0 : ne_info_def.getCode(), event_def == null ? 0 : event_def.getCode(), severity, startNo, count);
		return alarms;
	}

	@Override
	public int queryCountAlarmActiveByNeGroup(EmpContext context, int ne_group_id, EMP_MODEL_EVENT event_def, SEVERITY severity) throws EmpException {
		return dao4Alarm.queryCountAlarmActiveByNeGroup(context, ne_group_id, event_def == null ? 0 : event_def.getCode(), severity);
	}

	@Override
	public int queryCountAlarmActiveByNe(EmpContext context, int ne_id, EMP_MODEL_EVENT event_def, SEVERITY severity) throws EmpException {
		return dao4Alarm.queryCountAlarmActiveByNe(context, ne_id, event_def == null ? 0 : event_def.getCode(), severity);
	}

	@Override
	public Model4Alarm ackAlarm(EmpContext context, long gen_first_event_id) throws EmpException {
		Model4Alarm alarm_active = dao4Alarm.updateAlarmByAck(context, gen_first_event_id);

		queryNextUpdate_seq_fault(context);
		return alarm_active;
	}

	@Override
	public Model4Alarm[] clearListAlarmByNe_info_index(EmpContext context, int ne_id, int ne_info_code, int ne_info_index, GEN_TYPE clear_type, String clear_description) throws EmpException {
		Model4Alarm[] alarm_clears = dao4Alarm.updateAlarmByClearNe_info_index(context, ne_id, ne_info_code, ne_info_index, clear_type, clear_description);

		queryNextUpdate_seq_fault(context);
		return alarm_clears;
	}

	@Override
	public Model4Alarm clearAlarmByGen_first_event_id(EmpContext context, long gen_first_event_id, GEN_TYPE clear_type, String clear_description, boolean isCreateEvent) throws EmpException {
		long clear_event_id = 0L;

		Model4Alarm alarm_clear = dao4Alarm.queryAlarmActive(context, gen_first_event_id);
		alarm_clear.setClear_state(true);
		alarm_clear.setClear_time(new Date());
		alarm_clear.setClear_type(clear_type);

		if (isCreateEvent) {
			Model4Event event = new Model4Event();
			event.setNe_id(alarm_clear.getNe_id());
			event.setNe_info_code(alarm_clear.getNe_info_code());
			event.setNe_info_index(alarm_clear.getNe_info_index());
			event.setLocation_display(alarm_clear.getLocation_display());
			event.setEvent_code(alarm_clear.getEvent_code());
			event.setSeverity(SEVERITY.CLEAR);
			event.setGen_time(alarm_clear.getClear_time());
			event.setGen_type(alarm_clear.getClear_type());
			event.setDescription(clear_description);

			dao4Event.createEvent(context, event);
			clear_event_id = event.getEvent_id();
		}

		alarm_clear.setClear_event_id(clear_event_id);
		alarm_clear.setClear_description(clear_description);
		alarm_clear = dao4Alarm.updateAlarmByClear(context, alarm_clear);

		queryNextUpdate_seq_fault(context);
		return alarm_clear;
	}

	@Override
	public Model4AlarmSummary[] queryListAlarmSummary(EmpContext context) throws EmpException {
		return dao4Alarm.queryListAlarmSummary(context);
	}

	@Override
	public int syncListAlarmStatistics(EmpContext context, STATISTICS_TYPE from_type, STATISTICS_TYPE to_type, Date collect_time) throws EmpException {
		return dao4AlarmStatistics.syncListAlarmStatistics(context, from_type, to_type, collect_time);
	}

	@Override
	public Model4AlarmStatistics[] queryListAlarmStatisticsByNeGroup(EmpContext context, int ne_group_id, ITEM item, STATISTICS_TYPE statistics_type, Date fromTime, Date toTime) throws EmpException {
		return dao4AlarmStatistics.queryListAlarmStatisticsByNeGroup(context, ne_group_id, item, statistics_type, fromTime, toTime);
	}

	@Override
	public Model4AlarmStatistics[] queryListAlarmStatisticsByNe(EmpContext context, int ne_id, ITEM item, STATISTICS_TYPE statistics_type, Date fromTime, Date toTime) throws EmpException {
		return dao4AlarmStatistics.queryListAlarmStatisticsByNe(context, ne_id, item, statistics_type, fromTime, toTime);
	}

	@Override
	public long queryCurrUpdate_seq_fault(EmpContext context) throws EmpException {
		return dao4Event.queryCurrUpdate_seq_fault(context);
	}

	protected long queryNextUpdate_seq_fault(EmpContext context) throws EmpException {
		return dao4Event.queryNextUpdate_seq_fault(context);
	}

	@Override
	public String[][] prepareListPartition(EmpContext context, Date collect_time) throws EmpException {
		List<String> partition_create_list = new ArrayList<String>();
		List<String> partition_drop_list = new ArrayList<String>();

		String[][] partition_indexs = dao4Event.prepareListPartition(context, collect_time);
		for (String partition_index : partition_indexs[0]) {
			partition_create_list.add(partition_index);
		}
		for (String partition_index : partition_indexs[1]) {
			partition_drop_list.add(partition_index);
		}

		partition_indexs = dao4Alarm.prepareListPartition(context, collect_time);
		for (String partition_index : partition_indexs[0]) {
			partition_create_list.add(partition_index);
		}
		for (String partition_index : partition_indexs[1]) {
			partition_drop_list.add(partition_index);
		}

		partition_indexs = dao4AlarmStatistics.prepareListPartition(context, collect_time);
		for (String partition_index : partition_indexs[0]) {
			partition_create_list.add(partition_index);
		}
		for (String partition_index : partition_indexs[1]) {
			partition_drop_list.add(partition_index);
		}

		return new String[][] { partition_create_list.toArray(new String[0]), partition_drop_list.toArray(new String[0]) };
	}

	@Override
	public void truncate(EmpContext context) throws EmpException {
		dao4AlarmStatistics.truncate(context);
		dao4Alarm.truncate(context);
		dao4Event.truncate(context);
		UtilCache.removeAll();
		dao4Event.queryNextUpdate_seq_fault(context);
	}

}
