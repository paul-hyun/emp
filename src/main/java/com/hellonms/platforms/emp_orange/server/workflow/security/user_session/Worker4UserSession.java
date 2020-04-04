/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.server.workflow.security.user_session;

import com.hellonms.platforms.emp_core.server.log.BlackBox;
import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.server.workflow.WorkerIf;
import com.hellonms.platforms.emp_core.server.workflow.WorkflowMap;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_orange.server.workflow.security.user.Worker4UserIf;
import com.hellonms.platforms.emp_orange.share.error.ERROR_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.security.user.Model4User;
import com.hellonms.platforms.emp_orange.share.model.security.user_session.Model4UserSession;
import com.hellonms.platforms.emp_util.key.UtilKey;

/**
 * <p>
 * 사용자 로그인 세션 Worker
 * </p>
 * 
 * @since 1.6
 * @create 2015. 3. 10.
 * @modified 2015. 3. 10.
 * @author cchyun
 * 
 */
public class Worker4UserSession implements Worker4UserSessionIf {

	/**
	 * 중복로그인 허용
	 */
	private boolean allow_duplicate_login = true;

	private Dao4UserSessionIf dao4UserSession;

	private Worker4UserIf worker4User;

	@SuppressWarnings("unused")
	private static final BlackBox blackBox = new BlackBox(Worker4UserSession.class);

	public void setDao4UserSession(Dao4UserSessionIf dao4UserSession) {
		this.dao4UserSession = dao4UserSession;
	}

	@Override
	public Class<? extends WorkerIf> getDefine_class() {
		return Worker4UserSessionIf.class;
	}

	@Override
	public void initialize(EmpContext context) throws EmpException {
		if (dao4UserSession == null) {
			throw new EmpException(ERROR_CODE_ORANGE.WORKFLOW_NOTSETTED, Dao4UserSessionIf.class, getClass());
		}
		dao4UserSession.initialize(context);

		worker4User = (Worker4UserIf) WorkflowMap.getWorker(Worker4UserIf.class);
	}

	@Override
	public void dispose(EmpContext context) throws EmpException {
		dao4UserSession.dispose(context);
	}

	@Override
	public Model4UserSession login(EmpContext context, String user_account, String password, String user_ip) throws EmpException {
		if (!allow_duplicate_login) {
			Model4UserSession[] user_sessions = dao4UserSession.queryListUserSessionByAccount(context, user_account);
			if (0 < user_sessions.length) {
				throw new EmpException(ERROR_CODE_ORANGE.USER_ALREADYLOGIN, user_account, user_sessions[0].getUser_ip());
			}
		}

		// user 조회
		Model4User user = worker4User.queryUserByAccount(context, user_account);

		// 암호 비교
		if (user == null || !worker4User.equalPassword(context, user.getUser_id(), password)) {
			throw new EmpException(ERROR_CODE_ORANGE.USER_LOGINFAIL, user_account);
		}

		Model4UserSession user_session = new Model4UserSession();
		user_session.setUser_session_key(UtilKey.randomUUID().toString());
		user_session.setUser_id(user.getUser_id());
		user_session.setUser_ip(user_ip);
		user_session.setMeta_data("");

		context.setUser_account(user_account);
		user_session = dao4UserSession.createUserSession(context, user_session);

		user_session.setUser_account(user_account);
		initContext(context, user_session);
		return user_session;
	}

	@Override
	public Model4UserSession queryUserSession(EmpContext context, String user_session_key) throws EmpException {
		Model4UserSession user_session = dao4UserSession.queryUserSession(context, user_session_key);

		initContext(context, user_session);
		return user_session;
	}

	@Override
	public Model4UserSession[] queryListUserSessionByInterval(EmpContext context, int interval_seconds) throws EmpException {
		return dao4UserSession.queryListUserSessionByInterval(context, interval_seconds);
	}

	@Override
	public Model4UserSession logout(EmpContext context, String user_session_key) throws EmpException {
		return dao4UserSession.deleteUserSession(context, user_session_key);
	}

	protected EmpContext initContext(EmpContext context, Model4UserSession user_session) {
		context.setUser_id(user_session.getUser_id());
		context.setUser_session_id(user_session.getUser_session_id());
		context.setUser_account(user_session.getUser_account());
		context.setUser_ip(user_session.getUser_ip());

		return context;
	}

}
