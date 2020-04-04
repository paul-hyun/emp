package com.hellonms.platforms.emp_orange.client_swt.page.emp_model;

import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_orange.client_swt.driver.Driver4OrangeClientSwt;
import com.hellonms.platforms.emp_orange.client_swt.driver.Driver4OrangeClientSwt.Driver4OrangeClientSwtTaskIf;
import com.hellonms.platforms.emp_orange.client_swt.servlet.Invoker4OrangeClientSwtIf;
import com.hellonms.platforms.emp_orange.client_swt.servlet.Invoker4OrangeClientSwtReqeust;

public class Shell4ModelAdvisor {

	public byte[] queryEmp_model() throws EmpException {
		return (byte[]) Driver4OrangeClientSwt.getInstance().execute(new Driver4OrangeClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeClientSwtIf invoker, Invoker4OrangeClientSwtReqeust request) throws EmpException {
				return invoker.queryEmp_model(request);
			}
		});
	}

	public byte[] updateEmp_model(final byte[] emp_model_data) throws EmpException {
		return (byte[]) Driver4OrangeClientSwt.getInstance().execute(new Driver4OrangeClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeClientSwtIf invoker, Invoker4OrangeClientSwtReqeust request) throws EmpException {
				return invoker.updateEmp_model(request, emp_model_data);
			}
		});
	}

	public void createImage(String path, String filename, byte[] filedata) throws EmpException {
		Driver4OrangeClientSwt.getInstance().execute(new Driver4OrangeClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeClientSwtIf invoker, Invoker4OrangeClientSwtReqeust request) throws EmpException {
				invoker.createImage(request, path, filename, filedata);
				return null;
			}
		});
	}

	public String[] queryListImagePath(final String path, final String[] extensions) throws EmpException {
		return (String[]) Driver4OrangeClientSwt.getInstance().execute(new Driver4OrangeClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeClientSwtIf invoker, Invoker4OrangeClientSwtReqeust request) throws EmpException {
				return invoker.queryListImagePath(request, path, extensions);
			}
		});
	}

	public void truncate(final boolean ne, final boolean ne_info, final boolean fault, final boolean operation_log) throws EmpException {
		Driver4OrangeClientSwt.getInstance().execute(new Driver4OrangeClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeClientSwtIf invoker, Invoker4OrangeClientSwtReqeust request) throws EmpException {
				invoker.truncate(request, ne, ne_info, fault, operation_log);
				return null;
			}
		});
	}

}
