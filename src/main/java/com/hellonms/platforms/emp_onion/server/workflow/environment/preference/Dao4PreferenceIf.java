/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_onion.server.workflow.environment.preference;

import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.server.workflow.DaoIf;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_onion.share.model.environment.preference.Model4Preference;
import com.hellonms.platforms.emp_onion.share.model.environment.preference.PREFERENCE_CODE;

/**
 * <p>
 * Environment Dao
 * </p>
 *
 * @since 1.6
 * @create 2015. 6. 10.
 * @modified 2015. 6. 10.
 * @author cchyun
 *
 */
public interface Dao4PreferenceIf extends DaoIf {

	public Model4Preference queryPreference(EmpContext context, PREFERENCE_CODE preference_code) throws EmpException;

	public Model4Preference[] queryListPreference(EmpContext context, String function_group, String function, String preference) throws EmpException;

	public Model4Preference updatePreference(EmpContext context, Model4Preference preference) throws EmpException;

	public Model4Preference[] updateListPreference(EmpContext context, Model4Preference[] preferences) throws EmpException;

}
