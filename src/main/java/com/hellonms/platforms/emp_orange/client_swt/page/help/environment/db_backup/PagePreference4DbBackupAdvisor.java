package com.hellonms.platforms.emp_orange.client_swt.page.help.environment.db_backup;

import java.io.File;

import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_orange.client_swt.driver.Driver4OrangeClientSwt;
import com.hellonms.platforms.emp_orange.client_swt.driver.Driver4OrangeClientSwt.Driver4OrangeClientSwtTaskIf;
import com.hellonms.platforms.emp_orange.client_swt.page.help.environment.PagePreferenceAdvisor;
import com.hellonms.platforms.emp_orange.client_swt.servlet.Invoker4OrangeClientSwtIf;
import com.hellonms.platforms.emp_orange.client_swt.servlet.Invoker4OrangeClientSwtReqeust;

public class PagePreference4DbBackupAdvisor extends PagePreferenceAdvisor {

	/**
	 * @return
	 * @throws EmpException
	 */
	public File backupDatabaseByUser() throws EmpException {
		return (File) Driver4OrangeClientSwt.getInstance().execute(new Driver4OrangeClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeClientSwtIf invoker, Invoker4OrangeClientSwtReqeust request) throws EmpException {
				return invoker.backupDatabaseByUser(request);
			}
		});
	}

}
