package com.hellonms.platforms.emp_orange.client_swt.page.security.user;

import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_orange.client_swt.driver.Driver4OrangeClientSwt;
import com.hellonms.platforms.emp_orange.client_swt.driver.Driver4OrangeClientSwt.Driver4OrangeClientSwtTaskIf;
import com.hellonms.platforms.emp_orange.client_swt.model_display.ModelDisplay4User;
import com.hellonms.platforms.emp_orange.client_swt.servlet.Invoker4OrangeClientSwtIf;
import com.hellonms.platforms.emp_orange.client_swt.servlet.Invoker4OrangeClientSwtReqeust;
import com.hellonms.platforms.emp_orange.share.model.security.user.Model4User;

/**
 * <p>
 * Page4UserAdvisor
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class Page4UserAdvisor {

	public ModelDisplay4User createUser(final Model4User model4User, final int startNo, final int count) throws EmpException {
		return (ModelDisplay4User) Driver4OrangeClientSwt.getInstance().execute(new Driver4OrangeClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeClientSwtIf invoker, Invoker4OrangeClientSwtReqeust request) throws EmpException {
				return invoker.createUser(request, model4User, startNo, count);
			}
		});
	}

	public ModelDisplay4User queryListUser(final int startNo, final int count) throws EmpException {
		return (ModelDisplay4User) Driver4OrangeClientSwt.getInstance().execute(new Driver4OrangeClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeClientSwtIf invoker, Invoker4OrangeClientSwtReqeust request) throws EmpException {
				return invoker.queryListUser(request, startNo, count);
			}
		});
	}

	public ModelDisplay4User updateUser(final Model4User model4User, final int startNo, final int count) throws EmpException {
		return (ModelDisplay4User) Driver4OrangeClientSwt.getInstance().execute(new Driver4OrangeClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeClientSwtIf invoker, Invoker4OrangeClientSwtReqeust request) throws EmpException {
				return invoker.updateUser(request, model4User, startNo, count);
			}
		});
	}

	public ModelDisplay4User deleteUser(final Model4User model4User, final int startNo, final int count) throws EmpException {
		return (ModelDisplay4User) Driver4OrangeClientSwt.getInstance().execute(new Driver4OrangeClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeClientSwtIf invoker, Invoker4OrangeClientSwtReqeust request) throws EmpException {
				return invoker.deleteUser(request, model4User.getUser_id(), startNo, count);
			}
		});
	}

}
