/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_onion.server.workflow.environment.database;

import java.io.File;

import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.server.workflow.WorkerIf;
import com.hellonms.platforms.emp_core.server.workflow.WorkflowMap;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_onion.server.workflow.environment.preference.Worker4PreferenceIf;
import com.hellonms.platforms.emp_onion.share.model.environment.preference.Model4Preference;
import com.hellonms.platforms.emp_onion.share.model.environment.preference.PREFERENCE_CODE_ONION;
import com.hellonms.platforms.emp_orange.share.error.ERROR_CODE_ORANGE;
import com.hellonms.platforms.emp_util.date.UtilDate;
import com.hellonms.platforms.emp_util.string.UtilString;

/**
 * <p>
 * DB Worker
 * </p>
 *
 * @since 1.6
 * @create 2015. 6. 17.
 * @modified 2015. 6. 17.
 * @author cchyun
 *
 */
public class Worker4Database implements Worker4DatabaseIf {

	private Dao4DatabaseIf dao4Database;

	private Worker4PreferenceIf worker4Preference;

	@Override
	public Class<? extends WorkerIf> getDefine_class() {
		return Worker4DatabaseIf.class;
	}

	public void setDao4Database(Dao4DatabaseIf dao4Database) {
		this.dao4Database = dao4Database;
	}

	@Override
	public void initialize(EmpContext context) throws EmpException {
		if (dao4Database == null) {
			throw new EmpException(ERROR_CODE_ORANGE.WORKFLOW_NOTSETTED, Dao4DatabaseIf.class, getClass());
		}
		dao4Database.initialize(context);

		worker4Preference = (Worker4PreferenceIf) WorkflowMap.getWorker(Worker4PreferenceIf.class);
	}

	@Override
	public void dispose(EmpContext context) throws EmpException {
	}

	@Override
	public File backupDatabaseByUser(EmpContext context) throws EmpException {
		return dao4Database.backupDatabase(context, getOutput(context, "dbbackup_{}_user_{}.sql", UtilDate.format(UtilDate.MILLI_FORMAT_TRIM), context.getUser_account().replaceAll("[^a-zA-Z]", "")));
	}

	@Override
	public File backupDatabaseBySchedule(EmpContext context) throws EmpException {
		return dao4Database.backupDatabase(context, getOutput(context, "dbbackup_{}_schedule.sql", UtilDate.format(UtilDate.MILLI_FORMAT_TRIM)));
	}

	@Override
	public int deleteDatabaseGarbage(EmpContext context) throws EmpException {
		return dao4Database.deleteDatabaseGarbage(context);
	}

	private File getOutput(EmpContext context, String text, Object... values) throws EmpException {
		Model4Preference preference = worker4Preference.queryPreference(context, PREFERENCE_CODE_ONION.PREFERENCE_ONION_ENVIRONMENT_DATABASE_BACKUP_DIRECTORY);
		File backup_directory = new File(preference.getPreferenceString());
		File backup_month = new File(backup_directory, UtilDate.format(UtilDate.MONTH_FORMAT));
		backup_month.mkdirs();
		File output = new File(backup_month, UtilString.format(text, values));
		return output;
	}

}
