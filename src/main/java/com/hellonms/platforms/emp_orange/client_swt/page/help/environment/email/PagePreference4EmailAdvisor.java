package com.hellonms.platforms.emp_orange.client_swt.page.help.environment.email;

import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_orange.client_swt.driver.Driver4OrangeClientSwt;
import com.hellonms.platforms.emp_orange.client_swt.driver.Driver4OrangeClientSwt.Driver4OrangeClientSwtTaskIf;
import com.hellonms.platforms.emp_orange.client_swt.servlet.Invoker4OrangeClientSwtIf;
import com.hellonms.platforms.emp_orange.client_swt.servlet.Invoker4OrangeClientSwtReqeust;

public class PagePreference4EmailAdvisor {

	/**
	 * 이메일을 보냅니다.
	 * 
	 * @param host
	 *            SMTP서버 주소
	 * @param port
	 *            SMTP서버 포트
	 * @param account
	 *            SMTP 계정
	 * @param password
	 *            SMTP 암호
	 * @param from
	 *            발신 Email
	 * @param fromName
	 *            발신 이름
	 * @param tos
	 *            수신 Email
	 * @param ccs
	 *            참조
	 * @param bccs
	 *            숨은참조
	 * @param subject
	 *            제목
	 * @param content
	 *            내용
	 * @throws EmpException
	 */
	public void sendEmail(final String host, final int port, final String account, final String password, final String from, final String fromName, final String[] tos, final String[] ccs, final String[] bccs, final String subject, final String content) throws EmpException {
		Driver4OrangeClientSwt.getInstance().execute(new Driver4OrangeClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeClientSwtIf invoker, Invoker4OrangeClientSwtReqeust request) throws EmpException {
				// invoker.sendEmail(request, host, port, account, password, from, fromName, tos, ccs, bccs, subject, content);
				return null;
			}
		});
	}

}
