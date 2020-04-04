/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_onion.server.schedule_job.environment.database;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import com.hellonms.platforms.emp_core.server.log.BlackBox;
import com.hellonms.platforms.emp_core.server.log.LEVEL;
import com.hellonms.platforms.emp_core.server.schedule_job.ScheduleJobIf;
import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.server.transaction.UtilTransaction;
import com.hellonms.platforms.emp_core.server.workflow.WorkflowMap;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_onion.server.workflow.environment.database.Worker4DatabaseIf;
import com.hellonms.platforms.emp_onion.server.workflow.environment.preference.Worker4PreferenceIf;
import com.hellonms.platforms.emp_onion.share.model.environment.preference.PREFERENCE_CODE_ONION;
import com.hellonms.platforms.emp_orange.share.model.security.operation_log.OPERATION_CODE_ORANGE;
import com.hellonms.platforms.emp_util.date.UtilDate;

/**
 * <p>
 * 주기적으로 DB를 백업하고 정리한다.
 * </p>
 * 
 * @since 1.6
 * @create 2015. 3. 12.
 * @modified 2015. 3. 12.
 * @author cchyun
 * 
 */
public class ScheduleJob4Database implements ScheduleJobIf {

	private Worker4PreferenceIf worker4Preference;

	private Worker4DatabaseIf worker4Database;

	private static final BlackBox blackBox = new BlackBox(ScheduleJob4Database.class);

	@Override
	public Class<? extends ScheduleJobIf> getDefine_class() {
		return ScheduleJob4Database.class;
	}

	@Override
	public void initialize(EmpContext context) throws EmpException {
		worker4Preference = (Worker4PreferenceIf) WorkflowMap.getWorker(Worker4PreferenceIf.class);
		worker4Database = (Worker4DatabaseIf) WorkflowMap.getWorker(Worker4DatabaseIf.class);
	}

	@Override
	public void dispose(EmpContext context) throws EmpException {
	}

	@Override
	public void execute(EmpContext context, Date schedule_time) throws EmpException {
		{
			Date start_time = new Date();
			Map<String, Object> request = new LinkedHashMap<String, Object>();
			request.put("operation", "backupDatabase");
			request.put("schedule_time", UtilDate.format(schedule_time));
			Map<String, Object> response = new LinkedHashMap<String, Object>();
			String backup_file = null;
			Exception exception = null;
			try {
				backup_file = backupDatabase(context, schedule_time);
				response.put("backup_file", backup_file);
			} catch (Exception e) {
				exception = e;
				context.rollback();
				if (blackBox.isEnabledFor(LEVEL.Fatal)) {
					blackBox.log(LEVEL.Site, context, e);
				}
			} finally {
				if (backup_file != null || exception != null) {
					Date end_time = new Date();
					UtilTransaction.transaction_log(context.getTransaction_id_string(), context.getUser_id(), context.getUser_session_id(), context.getUser_account(), context.getUser_ip(), "SCHEDULE", OPERATION_CODE_ORANGE.HELP_DATABASE_BACKUP, start_time, end_time, request, exception == null, response, exception == null ? null : exception.getMessage());
				}
			}
		}

		{
			Date start_time = new Date();
			Map<String, Object> request = new LinkedHashMap<String, Object>();
			request.put("operation", "prepareListPartition");
			request.put("schedule_time", UtilDate.format(schedule_time));
			Map<String, Object> response = new LinkedHashMap<String, Object>();
			String[][] partitions = {};
			Exception exception = null;
			try {
				partitions = prepareListPartition(context, schedule_time);
				response.put("partition_create", partitions[0]);
				response.put("partition_drop", partitions[1]);
			} catch (Exception e) {
				exception = e;
				context.rollback();
				if (blackBox.isEnabledFor(LEVEL.Fatal)) {
					blackBox.log(LEVEL.Site, context, e);
				}
			} finally {
				if ((partitions != null && 0 < partitions.length) || exception != null) {
					Date end_time = new Date();
					UtilTransaction.transaction_log(context.getTransaction_id_string(), context.getUser_id(), context.getUser_session_id(), context.getUser_account(), context.getUser_ip(), "SCHEDULE", OPERATION_CODE_ORANGE.HELP_DATABASE_BACKUP, start_time, end_time, request, exception == null, response, exception == null ? null : exception.getMessage());
				}
			}
		}

		{
			Date start_time = new Date();
			Map<String, Object> request = new LinkedHashMap<String, Object>();
			request.put("operation", "deleteDatabaseGarbage");
			request.put("schedule_time", UtilDate.format(schedule_time));
			Map<String, Object> response = new LinkedHashMap<String, Object>();
			int garbage_count = 0;
			Exception exception = null;
			try {
				garbage_count = deleteDatabaseGarbage(context, schedule_time);
				response.put("garbage_count", garbage_count);
			} catch (Exception e) {
				exception = e;
				context.rollback();
				if (blackBox.isEnabledFor(LEVEL.Fatal)) {
					blackBox.log(LEVEL.Site, context, e);
				}
			} finally {
				if (0 < garbage_count || exception != null) {
					Date end_time = new Date();
					UtilTransaction.transaction_log(context.getTransaction_id_string(), context.getUser_id(), context.getUser_session_id(), context.getUser_account(), context.getUser_ip(), "SCHEDULE", OPERATION_CODE_ORANGE.HELP_DATABASE_BACKUP, start_time, end_time, request, exception == null, response, exception == null ? null : exception.getMessage());
				}
			}
		}
	}

	protected String backupDatabase(EmpContext context, Date schedule_time) throws EmpException {
		boolean admin_state = worker4Preference.queryPreference(context, PREFERENCE_CODE_ONION.PREFERENCE_ONION_ENVIRONMENT_DATABASE_BACKUP_ADMIN_STATE).getPreferenceBoolean();
		if (!admin_state) {
			return null;
		}

		int period = (int) worker4Preference.queryPreference(context, PREFERENCE_CODE_ONION.PREFERENCE_ONION_ENVIRONMENT_DATABASE_BACKUP_PERIOD_HOUR).getPreferenceLong();
		if (period == 0) {
			return null;
		}

		int offset = (int) worker4Preference.queryPreference(context, PREFERENCE_CODE_ONION.PREFERENCE_ONION_ENVIRONMENT_DATABASE_BACKUP_OFFSET_HOUR).getPreferenceLong();

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(schedule_time);
		int hour_of_day = calendar.get(Calendar.HOUR_OF_DAY);

		if (hour_of_day % period != offset) {
			return null;
		}

		return worker4Database.backupDatabaseBySchedule(context).getAbsolutePath();
	}

	protected String[][] prepareListPartition(EmpContext context, Date schedule_time) throws EmpException {
		return new String[][] {};
	}

	protected int deleteDatabaseGarbage(EmpContext context, Date schedule_time) throws EmpException {
		worker4Database.deleteDatabaseGarbage(context);
		return 0;
	}

}
