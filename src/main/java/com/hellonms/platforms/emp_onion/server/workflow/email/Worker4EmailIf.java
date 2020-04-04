package com.hellonms.platforms.emp_onion.server.workflow.email;

import java.io.File;
import java.util.Map;

import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.server.workflow.WorkerIf;
import com.hellonms.platforms.emp_core.share.error.EmpException;

public interface Worker4EmailIf extends WorkerIf {

	/**
	 * <p>
	 * 이메일을 보낸다.
	 * </p>
	 * 
	 * @param context
	 *            EMP context
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
	public void sendEmail(EmpContext context, String fromMail, String fromName, String[] tos, String[] ccs, String[] bccs, String subject, String content, Map<String, File> files) throws EmpException;

}
