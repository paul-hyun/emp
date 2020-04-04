/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.server.workflow.security.user_session;

import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.server.workflow.DaoIf;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_orange.share.model.security.user_session.Model4UserSession;

/**
 * <p>
 * 사용자 로그인 세션 Dao
 * </p>
 * 
 * @since 1.6
 * @create 2015. 3. 10.
 * @modified 2015. 3. 10.
 * @author cchyun
 * 
 */
public interface Dao4UserSessionIf extends DaoIf {

	/**
	 * <p>
	 * 로그인 사용자 정보 추가 (로그인)
	 * </p>
	 * 
	 * @param context
	 * @return
	 */
	public Model4UserSession createUserSession(EmpContext context, Model4UserSession user_session) throws EmpException;

	/**
	 * <p>
	 * 로그인된 사용자 정보를 조회한다.
	 * </p>
	 * 
	 * @param context
	 * @param user_session_key
	 * @return
	 * @throws EmpException
	 */
	public Model4UserSession queryUserSession(EmpContext context, String user_session_key) throws EmpException;

	/**
	 * <p>
	 * 사용자 계정으로 로그인 정보 조회
	 * </p>
	 * 
	 * @param context
	 * @param user_account
	 * @return
	 * @throws EmpException
	 */
	public Model4UserSession[] queryListUserSessionByAccount(EmpContext context, String user_account) throws EmpException;

	/**
	 * <p>
	 * 일정시간동안 사용되지 않는 세션 목록을 조회한다.
	 * </p>
	 *
	 * @param context
	 * @param interval_seconds
	 * @return
	 * @throws EmpException
	 */
	public Model4UserSession[] queryListUserSessionByInterval(EmpContext context, int interval_seconds) throws EmpException;

	/**
	 * <p>
	 * 로그인된 사용자 정보를 삭제 (로그아웃)
	 * </p>
	 * 
	 * @param context
	 * @param user_session_key
	 * @return
	 */
	public Model4UserSession deleteUserSession(EmpContext context, String user_session_key) throws EmpException;

}
