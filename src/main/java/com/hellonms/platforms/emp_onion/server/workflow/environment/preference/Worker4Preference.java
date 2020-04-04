/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_onion.server.workflow.environment.preference;

import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.server.workflow.WorkerIf;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_onion.share.model.environment.preference.Model4Preference;
import com.hellonms.platforms.emp_onion.share.model.environment.preference.PREFERENCE_CODE;
import com.hellonms.platforms.emp_orange.share.error.ERROR_CODE_ORANGE;

/**
 * <p>
 * Environment worker
 * </p>
 *
 * @since 1.6
 * @create 2015. 6. 17.
 * @modified 2015. 6. 17.
 * @author cchyun
 *
 */
public class Worker4Preference implements Worker4PreferenceIf {

	private Dao4PreferenceIf dao4Preference;

	@Override
	public Class<? extends WorkerIf> getDefine_class() {
		return Worker4PreferenceIf.class;
	}

	public void setDao4Preference(Dao4PreferenceIf dao4Preference) {
		this.dao4Preference = dao4Preference;
	}

	@Override
	public void initialize(EmpContext context) throws EmpException {
		if (dao4Preference == null) {
			throw new EmpException(ERROR_CODE_ORANGE.WORKFLOW_NOTSETTED, Dao4PreferenceIf.class, getClass());
		}
		dao4Preference.initialize(context);
	}

	@Override
	public void dispose(EmpContext context) throws EmpException {
	}

	@Override
	public Model4Preference queryPreference(EmpContext context, PREFERENCE_CODE preference_code) throws EmpException {
		return dao4Preference.queryPreference(context, preference_code);
	}

	@Override
	public Model4Preference[] queryListPreference(EmpContext context, String function_group, String function, String preference) throws EmpException {
		return dao4Preference.queryListPreference(context, function_group, function, preference);
	}

	@Override
	public Model4Preference updatePreference(EmpContext context, Model4Preference preference) throws EmpException {
		return dao4Preference.updatePreference(context, preference);
	}

	@Override
	public Model4Preference[] updateListPreference(EmpContext context, Model4Preference[] preferences) throws EmpException {
		return dao4Preference.updateListPreference(context, preferences);
	}

}
