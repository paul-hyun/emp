package com.hellonms.platforms.emp_onion.server.workflow.email;

import java.io.File;
import java.util.Map;

import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.server.workflow.AdapterIf;
import com.hellonms.platforms.emp_core.server.workflow.WorkflowMap;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_onion.server.driver.email.Driver4EmailIf;

public class Adapter4Email implements Adapter4EmailIf {

	private Driver4EmailIf driver4Email;

	@Override
	public Class<? extends AdapterIf> getDefine_class() {
		return Adapter4EmailIf.class;
	}

	@Override
	public void initialize(EmpContext context) throws EmpException {
		driver4Email = (Driver4EmailIf) WorkflowMap.getDriver(Driver4EmailIf.class);
	}

	@Override
	public void dispose(EmpContext context) throws EmpException {
	}

	@Override
	public void sendEmail(EmpContext context, String host, int port, String account, String password, String fromMail, String fromName, String[] tos, String[] ccs, String[] bccs, String subject, String content, Map<String, File> files) throws EmpException {
		driver4Email.sendEmail(context, host, port, account, password, fromMail, fromName, tos, ccs, bccs, subject, content, files);
	}

}
