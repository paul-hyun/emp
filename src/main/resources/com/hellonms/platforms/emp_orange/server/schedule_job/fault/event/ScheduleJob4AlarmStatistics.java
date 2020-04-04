/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.server.schedule_job.fault.event;

import java.util.Calendar;
import java.util.Date;

import com.hellonms.platforms.emp_core.server.log.BlackBox;
import com.hellonms.platforms.emp_core.server.log.LEVEL;
import com.hellonms.platforms.emp_core.server.schedule_job.ScheduleJobIf;
import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.server.workflow.WorkflowMap;
import com.hellonms.platforms.emp_core.share.error.ERROR_CODE;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_onion.share.model.STATISTICS_TYPE;
import com.hellonms.platforms.emp_orange.server.workflow.fault.event.Worker4EventIf;
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
public class ScheduleJob4AlarmStatistics implements ScheduleJobIf {

	private class AlarmStatisticsSyncValue {

		private final STATISTICS_TYPE from_type;

		private final STATISTICS_TYPE to_type;

		private final Date collect_time;

		public AlarmStatisticsSyncValue(STATISTICS_TYPE from_type, STATISTICS_TYPE to_type, Date collect_time) {
			this.from_type = from_type;
			this.to_type = to_type;
			this.collect_time = collect_time;
		}
	}

	private class AlarmStatisticsSyncTask implements Runnable {

		@Override
		public void run() {
			while (true) {
				AlarmStatisticsSyncValue value = alarmStatisticsSyncQueue.pop();
				EmpContext context = new EmpContext(this);
				try {
					try {
						syncAlarmStatistics(context, value.from_type, value.to_type, value.collect_time);
						context.commit();
					} catch (EmpException e) {
						context.rollback();
						if (blackBox.isEnabledFor(LEVEL.Fatal)) {
							blackBox.log(LEVEL.Site, context, e);
						}
					} catch (Exception e) {
						context.rollback();
						if (blackBox.isEnabledFor(LEVEL.Fatal)) {
							blackBox.log(LEVEL.Site, context, e);
						}
					}
				} finally {
					context.close();
				}
			}
		}
	}

	private Worker4EventIf worker4Event;

	private UtilQueue<AlarmStatisticsSyncValue> alarmStatisticsSyncQueue = new UtilQueue<AlarmStatisticsSyncValue>(256);

	private Calendar calendar_sync = Calendar.getInstance();

	private static final BlackBox blackBox = new BlackBox(ScheduleJob4AlarmStatistics.class);

	@Override
	public Class<? extends ScheduleJobIf> getDefine_class() {
		return ScheduleJob4AlarmStatistics.class;
	}

	@Override
	public void initialize(EmpContext context) throws EmpException {
		worker4Event = (Worker4EventIf) WorkflowMap.getWorker(Worker4EventIf.class);

		Thread thread = new Thread(new AlarmStatisticsSyncTask(), "ScheduleJob4AlarmStatistics::AlarmStatisticsSyncTask");
		thread.setDaemon(true);
		thread.start();

		calendar_sync.setTimeInMillis(0L);
	}

	@Override
	public void dispose(EmpContext context) throws EmpException {
	}

	@Override
	public void execute(EmpContext context, Date schedule_time) throws EmpException {
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(schedule_time);

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
				if (collect_time_hour != null) {
					STATISTICS_TYPE from_type = STATISTICS_TYPE.MINUTE_5;
					STATISTICS_TYPE to_type = STATISTICS_TYPE.HOUR_1;
					if (blackBox.isEnabledFor(LEVEL.UseCase)) {
						blackBox.log(LEVEL.UseCase, context, UtilString.format("queue sync alarm statistics {}, {}, {}", from_type, to_type, collect_time_hour));
					}
					alarmStatisticsSyncQueue.push(new AlarmStatisticsSyncValue(from_type, to_type, collect_time_hour));
				}
				if (collect_time_day != null) {
					STATISTICS_TYPE from_type = STATISTICS_TYPE.HOUR_1;
					STATISTICS_TYPE to_type = STATISTICS_TYPE.DAY_1;
					if (blackBox.isEnabledFor(LEVEL.UseCase)) {
						blackBox.log(LEVEL.UseCase, context, UtilString.format("queue sync alarm statistics {}, {}, {}", from_type, to_type, collect_time_day));
					}
					alarmStatisticsSyncQueue.push(new AlarmStatisticsSyncValue(from_type, to_type, collect_time_day));
				}
				if (collect_time_month != null) {
					STATISTICS_TYPE from_type = STATISTICS_TYPE.DAY_1;
					STATISTICS_TYPE to_type = STATISTICS_TYPE.MONTH_1;
					if (blackBox.isEnabledFor(LEVEL.UseCase)) {
						blackBox.log(LEVEL.UseCase, context, UtilString.format("queue sync alarm statistics {}, {}, {}", from_type, to_type, collect_time_month));
					}
					alarmStatisticsSyncQueue.push(new AlarmStatisticsSyncValue(from_type, to_type, collect_time_month));
				}
			}
		} catch (Exception e) {
			throw new EmpException(e, ERROR_CODE.ERROR_UNKNOWN);
		}
	}

	protected int syncAlarmStatistics(EmpContext context, STATISTICS_TYPE from_type, STATISTICS_TYPE to_type, Date collect_time) throws EmpException {
		if (blackBox.isEnabledFor(LEVEL.Site)) {
			blackBox.log(LEVEL.Site, context, UtilString.format("start syncListAlarmStatistics from_type={}, to_type={}, collect_time={}", from_type, to_type, collect_time));
		}
		int count = worker4Event.syncListAlarmStatistics(context, from_type, to_type, collect_time);
		if (blackBox.isEnabledFor(LEVEL.Site)) {
			blackBox.log(LEVEL.Site, context, UtilString.format("end syncListAlarmStatistics from_type={}, to_type={}, collect_time={} : count={}", from_type, to_type, collect_time, count));
		}
		return count;
	}

}
