package com.hellonms.platforms.emp_onion.server.workflow.email;

import java.io.File;
import java.util.Map;

import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.server.workflow.WorkerIf;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_orange.share.error.ERROR_CODE_ORANGE;

public class Worker4Email implements Worker4EmailIf {

	private Adapter4EmailIf adapter4Email;

	private String host = "127.0.0.1";

	private int port = 25;

	private String account = "hellonms";

	private String password = "hellonms";

	@Override
	public Class<? extends WorkerIf> getDefine_class() {
		return Worker4EmailIf.class;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setAdapter4Email(Adapter4EmailIf adapter4Email) {
		this.adapter4Email = adapter4Email;
	}

	@Override
	public void initialize(EmpContext context) throws EmpException {
		if (adapter4Email == null) {
			throw new EmpException(ERROR_CODE_ORANGE.WORKFLOW_NOTSETTED, Adapter4EmailIf.class, getClass());
		}
		adapter4Email.initialize(context);
	}

	@Override
	public void dispose(EmpContext context) throws EmpException {
	}

	@Override
	public void sendEmail(EmpContext context, String fromMail, String fromName, String[] tos, String[] ccs, String[] bccs, String subject, String content, Map<String, File> files) throws EmpException {
		adapter4Email.sendEmail(context, host, port, account, password, fromMail, fromName, tos, ccs, bccs, subject, content, files);
	}

}
