package com.hellonms.platforms.emp_orange.client_swt.page.help.environment;

import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_onion.share.model.environment.preference.Model4Preference;
import com.hellonms.platforms.emp_onion.share.model.environment.preference.PREFERENCE_CODE;
import com.hellonms.platforms.emp_orange.client_swt.driver.Driver4OrangeClientSwt;
import com.hellonms.platforms.emp_orange.client_swt.driver.Driver4OrangeClientSwt.Driver4OrangeClientSwtTaskIf;
import com.hellonms.platforms.emp_orange.client_swt.servlet.Invoker4OrangeClientSwtIf;
import com.hellonms.platforms.emp_orange.client_swt.servlet.Invoker4OrangeClientSwtReqeust;

public class PagePreferenceAdvisor {

	/**
	 * <p>
	 * </p>
	 *
	 * @param preference_code
	 * @return
	 * @throws EmpException
	 */
	public Model4Preference queryPreference(final PREFERENCE_CODE preference_code) throws EmpException {
		return (Model4Preference) Driver4OrangeClientSwt.getInstance().execute(new Driver4OrangeClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeClientSwtIf invoker, Invoker4OrangeClientSwtReqeust request) throws EmpException {
				return invoker.queryPreference(request, preference_code);
			}
		});
	}

	/**
	 * @param function_group
	 * @param function
	 * @param preference
	 * @return
	 * @throws EmpException
	 */
	public Model4Preference[] queryListPreference(final String function_group, final String function, final String preference) throws EmpException {
		return (Model4Preference[]) Driver4OrangeClientSwt.getInstance().execute(new Driver4OrangeClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeClientSwtIf invoker, Invoker4OrangeClientSwtReqeust request) throws EmpException {
				return invoker.queryListPreference(request, function_group, function, preference);
			}
		});
	}

	/**
	 * @param function_group
	 * @param function
	 * @param preference
	 * @return
	 * @throws EmpException
	 */
	public Model4Preference[] updateListPreference(final Model4Preference[] preferences) throws EmpException {
		return (Model4Preference[]) Driver4OrangeClientSwt.getInstance().execute(new Driver4OrangeClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeClientSwtIf invoker, Invoker4OrangeClientSwtReqeust request) throws EmpException {
				return invoker.updateListPreference(request, preferences);
			}
		});
	}

}
