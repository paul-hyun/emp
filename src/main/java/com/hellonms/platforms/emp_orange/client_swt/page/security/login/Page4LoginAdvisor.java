package com.hellonms.platforms.emp_orange.client_swt.page.security.login;

import java.util.Map;

import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_core.share.language.LANGUAGE;
import com.hellonms.platforms.emp_orange.client_swt.driver.Driver4OrangeClientSwt;
import com.hellonms.platforms.emp_orange.client_swt.driver.Driver4OrangeClientSwt.Driver4OrangeClientSwtTaskIf;
import com.hellonms.platforms.emp_orange.client_swt.servlet.Invoker4OrangeClientSwtIf;
import com.hellonms.platforms.emp_orange.client_swt.servlet.Invoker4OrangeClientSwtReqeust;

/**
 * <p>
 * Page4LoginAdvisor
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class Page4LoginAdvisor {

	/**
	 * 로그인을 합니다.
	 * 
	 * @param user_account
	 *            사용자 계정
	 * @param password
	 *            비밀번호
	 * @return 로그인 상태
	 * @throws EmpException
	 */
	public boolean login(final String user_account, final String password) throws EmpException {
		Driver4OrangeClientSwt.getInstance().execute(new Driver4OrangeClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeClientSwtIf invoker, Invoker4OrangeClientSwtReqeust request) throws EmpException {
				String user_session_key = invoker.login(request, user_account, password);
				request.setProperty(Invoker4OrangeClientSwtReqeust.USER_SESSION_KEY, user_session_key);
				return user_session_key;
			}
		});
		return true;
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

}
