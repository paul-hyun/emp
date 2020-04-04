package com.hellonms.platforms.emp_onion.server.workflow.email;

import java.io.File;
import java.util.Map;

import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.server.workflow.AdapterIf;
import com.hellonms.platforms.emp_core.share.error.EmpException;

public interface Adapter4EmailIf extends AdapterIf {

	/**
	 * <p>
	 * 이메일을 보낸다.
	 * </p>
	 * 
	 * @param context
	 *            EMP context
	 * @param host
	 *            SMTP 서버 주소
	 * @param port
	 *            SMTP 서버 포트
	 * @param account
	 *            SMTP 서버 계정
	 * @param password
	 *            SMTP 서버 암호
	 * @param fromMail
	 *            보내는 사람 Email
	 * @param fromName
	 *            보내는 사람 이름
	 * @param tos
	 *            수신 목록
	 * @param ccs
	 *            참조 목록
	 * @param bccs
	 *            숨은참조 목록
	 * @param subject
	 *            제목
	 * @param content
	 *            내용
	 * @param files
	 *            첨부파일
	 * @throws EmpException
	 */
	public void sendEmail(EmpContext context, String host, int port, String account, String password, String fromMail, String fromName, String[] tos, String[] ccs, String[] bccs, String subject, String content, Map<String, File> files) throws EmpException;

}
