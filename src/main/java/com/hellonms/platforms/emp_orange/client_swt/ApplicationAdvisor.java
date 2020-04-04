package com.hellonms.platforms.emp_orange.client_swt;

import java.util.Map;

import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_core.share.language.LANGUAGE;
import com.hellonms.platforms.emp_core.share.operation.OPERATION_CODE;
import com.hellonms.platforms.emp_onion.share.model.help.about.Model4About;
import com.hellonms.platforms.emp_orange.client_swt.driver.Driver4OrangeClientSwt;
import com.hellonms.platforms.emp_orange.client_swt.driver.Driver4OrangeClientSwt.Driver4OrangeClientSwtTaskIf;
import com.hellonms.platforms.emp_orange.client_swt.servlet.Invoker4OrangeClientSwtIf;
import com.hellonms.platforms.emp_orange.client_swt.servlet.Invoker4OrangeClientSwtReqeust;
import com.hellonms.platforms.emp_orange.share.model.security.user_session.Model4UserSession;

/**
 * <p>
 * ApplicationAdvisor
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class ApplicationAdvisor {

	/**
	 * 사용자 세션을 조회합니다.
	 * 
	 * @return 사용자 세션 모델
	 * @throws EmpException
	 */
	public Model4UserSession queryUserSession() throws EmpException {
		return (Model4UserSession) Driver4OrangeClientSwt.getInstance().execute(new Driver4OrangeClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeClientSwtIf invoker, Invoker4OrangeClientSwtReqeust request) throws EmpException {
				return invoker.queryUserSession(request);
			}
		});
	}

	@SuppressWarnings("unchecked")
	public Map<String, String> queryLanguage(final LANGUAGE language) throws EmpException {
		return (Map<String, String>) Driver4OrangeClientSwt.getInstance().execute(new Driver4OrangeClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeClientSwtIf invoker, Invoker4OrangeClientSwtReqeust request) throws EmpException {
				return invoker.queryLanguage(request, language);
			}
		});
	}

	public byte[] queryEmp_model() throws EmpException {
		return (byte[]) Driver4OrangeClientSwt.getInstance().execute(new Driver4OrangeClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeClientSwtIf invoker, Invoker4OrangeClientSwtReqeust request) throws EmpException {
				return invoker.queryEmp_model(request);
			}
		});
	}

	public Model4About getAbout() throws EmpException {
		return (Model4About) Driver4OrangeClientSwt.getInstance().execute(new Driver4OrangeClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeClientSwtIf invoker, Invoker4OrangeClientSwtReqeust request) throws EmpException {
				return invoker.getAbout(request);
			}
		});
	}

	public OPERATION_CODE[] getListOperationCode() throws EmpException {
		return (OPERATION_CODE[]) Driver4OrangeClientSwt.getInstance().execute(new Driver4OrangeClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeClientSwtIf invoker, Invoker4OrangeClientSwtReqeust request) throws EmpException {
				return invoker.getListOperationCode(request);
			}
		});
	}

	/**
	 * 로그아웃을 합니다.
	 * 
	 * @throws EmpException
	 */
	public void logout() throws EmpException {
		Driver4OrangeClientSwt.getInstance().execute(new Driver4OrangeClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeClientSwtIf invoker, Invoker4OrangeClientSwtReqeust request) throws EmpException {
				invoker.logout(request);
				request.setProperty(Invoker4OrangeClientSwtReqeust.USER_SESSION_KEY, "");
				return null;
			}
		});
	}

}
