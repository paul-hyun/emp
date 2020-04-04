/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.server.workflow.security.user_session;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hellonms.platforms.emp_core.server.log.BlackBox;
import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.server.workflow.DaoIf;
import com.hellonms.platforms.emp_core.server.workflow.WorkflowMap;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_onion.server.driver.mybatis.Driver4MybatisIf;
import com.hellonms.platforms.emp_orange.server.workflow.security.user_group.Dao4UserGroup.OperationAuthority;
import com.hellonms.platforms.emp_orange.share.error.ERROR_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.security.user_session.Model4UserSession;
import com.hellonms.platforms.emp_util.cache.UtilCache;
import com.hellonms.platforms.emp_util.string.UtilString;

/**
 * <p>
 * Insert description of Dao4UserSession.java
 * </p>
 * 
 * @since 1.6
 * @create 2015. 3. 10.
 * @modified 2015. 3. 10.
 * @author cchyun
 * 
 */
public class Dao4UserSession implements Dao4UserSessionIf {

	protected Driver4MybatisIf driver4Mybatis;

	private static String CACHE_ID_KEY = UtilString.format("{}.{}", Dao4UserSession.class.getName(), "user_session_id");
	private static String CACHE_AUTH_KEY = UtilString.format("{}.{}", Dao4UserSession.class.getName(), "user_authorities");
	static {
		UtilCache.buildCache(CACHE_ID_KEY, 128, 60);
		UtilCache.buildCache(CACHE_AUTH_KEY, 64, 300);
	}

	@SuppressWarnings("unused")
	private static final BlackBox blackBox = new BlackBox(Dao4UserSession.class);

	@Override
	public Class<? extends DaoIf> getDefine_class() {
		return Dao4UserSessionIf.class;
	}

	@Override
	public void initialize(EmpContext context) throws EmpException {
		driver4Mybatis = (Driver4MybatisIf) WorkflowMap.getDriver(Driver4MybatisIf.class);
	}

	@Override
	public void dispose(EmpContext context) throws EmpException {
	}

	@Override
	public Model4UserSession createUserSession(EmpContext context, Model4UserSession user_session) throws EmpException {
		user_session.setCreator(context.getUser_account());
		driver4Mybatis.insert(context, getDefine_class(), "createUserSession", user_session);

		clearCache();
		return queryUserSession(context, user_session.getUser_session_key());
	}

	@Override
	public Model4UserSession queryUserSession(EmpContext context, String user_session_key) throws EmpException {
		if (user_session_key == null || user_session_key.length() == 0) {
			throw new EmpException(ERROR_CODE_ORANGE.MODEL_NOTEXISTS, MESSAGE_CODE_ORANGE.USER_SESSION, user_session_key);
		}
		Model4UserSession user_session = (Model4UserSession) UtilCache.get(CACHE_ID_KEY, user_session_key);

		if (user_session == null) {
			Map<String, Object> parameter = new HashMap<String, Object>();
			parameter.put("user_session_key", user_session_key);

			driver4Mybatis.update(context, getDefine_class(), "updateUserSession_last_access_time", parameter);
			user_session = (Model4UserSession) driver4Mybatis.selectOne(context, getDefine_class(), "queryUserSession", parameter);
			if (user_session == null) {
				throw new EmpException(ERROR_CODE_ORANGE.MODEL_NOTEXISTS, MESSAGE_CODE_ORANGE.USER_SESSION, user_session_key);
			}
			user_session = loadOperationAuthorities(context, user_session);
			UtilCache.put(CACHE_ID_KEY, user_session_key, user_session);
		}
		return user_session.copy();
	}

	@Override
	public Model4UserSession[] queryListUserSessionByAccount(EmpContext context, String user_account) throws EmpException {
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("user_account", user_account);

		List<?> list = driver4Mybatis.selectList(context, getDefine_class(), "queryListUserSessionByAccount", parameter);

		for (Object aaa : list) {
			Model4UserSession user_session = (Model4UserSession) aaa;
			user_session = loadOperationAuthorities(context, user_session);
		}
		return list.toArray(new Model4UserSession[0]);
	}

	@Override
	public Model4UserSession[] queryListUserSessionByInterval(EmpContext context, int interval_seconds) throws EmpException {
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("interval_seconds", interval_seconds);

		List<?> list = driver4Mybatis.selectList(context, getDefine_class(), "queryListUserSessionByInterval", parameter);

		for (Object aaa : list) {
			Model4UserSession user_session = (Model4UserSession) aaa;
			user_session = loadOperationAuthorities(context, user_session);
		}
		return list.toArray(new Model4UserSession[0]);
	}

	@Override
	public Model4UserSession deleteUserSession(EmpContext context, String user_session_key) throws EmpException {
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("user_session_key", user_session_key);

		Model4UserSession user_session = (Model4UserSession) driver4Mybatis.selectOne(context, getDefine_class(), "queryUserSession", parameter);
		if (user_session == null) {
			throw new EmpException(ERROR_CODE_ORANGE.MODEL_NOTEXISTS, MESSAGE_CODE_ORANGE.USER_SESSION, user_session_key);
		}

		driver4Mybatis.delete(context, getDefine_class(), "deleteUserSession", parameter);

		clearCache();
		return user_session;
	}

	/**
	 * <p>
	 * 사사용자 권한그룹 조회
	 * </p>
	 * 
	 * @param context
	 * @param user
	 * @throws EmpException
	 */
	private Model4UserSession loadOperationAuthorities(EmpContext context, Model4UserSession user_session) throws EmpException {
		OperationAuthority[] operation_authorities = (OperationAuthority[]) UtilCache.get(CACHE_AUTH_KEY, user_session.getUser_group_id());
		if (operation_authorities == null) {
			Map<String, Object> parameter = new HashMap<String, Object>();
			parameter.put("user_group_id", user_session.getUser_group_id());

			List<?> list = driver4Mybatis.selectList(context, getDefine_class(), "queryListOperationAuthorities", parameter);

			operation_authorities = list.toArray(new OperationAuthority[0]);
			UtilCache.put(CACHE_AUTH_KEY, user_session.getUser_group_id(), operation_authorities);
		}

		for (OperationAuthority operation_authority : operation_authorities) {
			user_session.setOperation_authority(operation_authority.operation_code, operation_authority.authority);
		}
		return user_session;
	}

	protected void clearCache() {
		UtilCache.removeAll(CACHE_ID_KEY);
		UtilCache.removeAll(CACHE_AUTH_KEY);
	}

}
