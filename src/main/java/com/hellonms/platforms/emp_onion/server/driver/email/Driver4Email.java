package com.hellonms.platforms.emp_onion.server.driver.email;

import java.io.File;
import java.util.Map;

import com.hellonms.platforms.emp_core.server.driver.DriverIf;
import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_plug.email.Plug4EmailClient;

public class Driver4Email implements Driver4EmailIf {

	private final Plug4EmailClient emailClient = new Plug4EmailClient();

	@Override
	public Class<? extends DriverIf> getDefine_class() {
		return Driver4EmailIf.class;
	}

	@Override
	public void initialize(EmpContext context) throws EmpException {
	}

	@Override
	public void dispose(EmpContext context) throws EmpException {
	}

	@Override
	public void sendEmail(EmpContext context, String host, int port, String account, String password, String fromMail, String fromName, String[] tos, String[] ccs, String[] bccs, String subject, String content, Map<String, File> files) throws EmpException {
		emailClient.sendEmail(context, host, port, account, password, fromMail, fromName, tos, ccs, bccs, subject, content, files);
	}

}
