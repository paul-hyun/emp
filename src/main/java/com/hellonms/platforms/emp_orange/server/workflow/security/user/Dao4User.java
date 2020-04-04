/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.server.workflow.security.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hellonms.platforms.emp_core.server.log.BlackBox;
import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.server.workflow.DaoIf;
import com.hellonms.platforms.emp_core.server.workflow.WorkflowMap;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_onion.server.driver.mybatis.Driver4MybatisIf;
import com.hellonms.platforms.emp_orange.share.error.ERROR_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.security.user.Model4User;
import com.hellonms.platforms.emp_util.cache.UtilCache;
import com.hellonms.platforms.emp_util.string.UtilString;

/**
 * <p>
 * 사용자 Dao
 * </p>
 * 
 * @since 1.6
 * @create 2015. 3. 13.
 * @modified 2015. 3. 13.
 * @author cchyun
 * 
 */
public class Dao4User implements Dao4UserIf {

	protected Driver4MybatisIf driver4Mybatis;

	private static String CACHE_ID_KEY = UtilString.format("{}.{}", Dao4User.class.getName(), "user_id");
	static {
		UtilCache.buildCache(CACHE_ID_KEY, 256, 300);
	}

	@SuppressWarnings("unused")
	private static final BlackBox blackBox = new BlackBox(Dao4User.class);

	@Override
	public Class<? extends DaoIf> getDefine_class() {
		return Dao4UserIf.class;
	}

	@Override
	public void initialize(EmpContext context) throws EmpException {
		driver4Mybatis = (Driver4MybatisIf) WorkflowMap.getDriver(Driver4MybatisIf.class);
	}

	@Override
	public void dispose(EmpContext context) throws EmpException {
	}

	@Override
	public Model4User createUser(EmpContext context, Model4User user) throws EmpException {
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("user_account", user.getUser_account());

		Model4User exists = (Model4User) driver4Mybatis.selectOne(context, getDefine_class(), "queryUserByAccount", parameter);
		if (exists != null) {
			throw new EmpException(ERROR_CODE_ORANGE.MODEL_ALREADYEXISTS, MESSAGE_CODE_ORANGE.USER, user.getUser_account());
		}

		user.setCreator(context.getUser_account());
		driver4Mybatis.insert(context, getDefine_class(), "createUser", user);

		int user_id = (Integer) driver4Mybatis.selectOne(context, getDefine_class(), "queryCurrval_user_id", user);
		user.setUser_id(user_id);
		saveUserManageNeGroups(context, user);

		clearCache();
		return queryUser(context, user_id);
	}

	@Override
	public Model4User queryUser(EmpContext context, int user_id) throws EmpException {
		Model4User user = (Model4User) UtilCache.get(CACHE_ID_KEY, user_id);

		if (user == null) {
			Map<String, Object> parameter = new HashMap<String, Object>();
			parameter.put("user_id", user_id);

			user = (Model4User) driver4Mybatis.selectOne(context, getDefine_class(), "queryUser", parameter);
			if (user == null) {
				throw new EmpException(ERROR_CODE_ORANGE.MODEL_NOTEXISTS, MESSAGE_CODE_ORANGE.USER, user_id);
			}
			user.setPassword(null);
			loadUserManageNeGroups(context, user);
			UtilCache.put(CACHE_ID_KEY, user_id, user);
		}
		return user.copy();
	}

	@Override
	public Model4User queryUserByAccount(EmpContext context, String user_account) throws EmpException {
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("user_account", user_account);

		Model4User user = (Model4User) driver4Mybatis.selectOne(context, getDefine_class(), "queryUserByAccount", parameter);
		if (user == null) {
			throw new EmpException(ERROR_CODE_ORANGE.MODEL_NOTEXISTS, MESSAGE_CODE_ORANGE.USER, user_account);
		}
		loadUserManageNeGroups(context, user);

		return user;
	}

	@Override
	public Model4User[] queryListUser(EmpContext context, int startNo, int count) throws EmpException {
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("startNo", startNo);
		parameter.put("count", count);

		List<?> list = driver4Mybatis.selectList(context, getDefine_class(), "queryListUser", parameter);
		for (Object aaa : list) {
			Model4User user = (Model4User) aaa;
			user.setPassword(null);
			loadUserManageNeGroups(context, user);
		}

		return list.toArray(new Model4User[0]);
	}

	@Override
	public boolean equalPassword(EmpContext context, int user_id, String password) throws EmpException {
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("user_id", user_id);
		parameter.put("password", password);

		Boolean result = (Boolean) driver4Mybatis.selectOne(context, getDefine_class(), "equalPassword", parameter);

		return result == null ? false : result;
	}

	@Override
	public int queryCountUser(EmpContext context) throws EmpException {
		Map<String, Object> parameter = new HashMap<String, Object>();

		int count = (Integer) driver4Mybatis.selectOne(context, getDefine_class(), "queryCountUser", parameter);

		return count;
	}

	@Override
	public Model4User updateUser(EmpContext context, Model4User user) throws EmpException {
		Model4User exists = queryUser(context, user.getUser_id());
		if (!exists.getAccess().isUpdate()) {
			throw new EmpException(ERROR_CODE_ORANGE.MODEL_DENYUPDATE, MESSAGE_CODE_ORANGE.USER, user.getUser_id());
		}

		user.setUpdater(context.getUser_account());

		@SuppressWarnings("unused")
		int count = driver4Mybatis.update(context, getDefine_class(), "updateUser", user);
		saveUserManageNeGroups(context, user);

		clearCache();
		return queryUser(context, user.getUser_id());
	}

	@Override
	public Model4User deleteUser(EmpContext context, int user_id) throws EmpException {
		Model4User user = queryUser(context, user_id);
		if (!user.getAccess().isDelete()) {
			throw new EmpException(ERROR_CODE_ORANGE.MODEL_DENYDELETE, MESSAGE_CODE_ORANGE.USER, user.getUser_account());
		}

		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("user_id", user_id);
		parameter.put("updater", context.getUser_account());

		@SuppressWarnings("unused")
		int count = driver4Mybatis.delete(context, getDefine_class(), "deleteUser", parameter);
		clearUserManageNeGroups(context, user);

		clearCache();
		return user;
	}

	/**
	 * <p>
	 * 사용자가 관리하는 NE Group 목록을 저장한다.
	 * </p>
	 * 
	 * @param context
	 * @param user
	 * @throws EmpException
	 */
	protected Model4User saveUserManageNeGroups(EmpContext context, Model4User user) throws EmpException {
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("user_id", user.getUser_id());
		parameter.put("manage_ne_groups", user.getManage_ne_groups());

		@SuppressWarnings("unused")
		int count_delete = driver4Mybatis.delete(context, getDefine_class(), "deleteListManageNeGroup", parameter);
		if (0 < user.getManage_ne_groups().length) {
			@SuppressWarnings("unused")
			int count_create = driver4Mybatis.insert(context, getDefine_class(), "createListManageNeGroup", parameter);
		}

		return user;
	}

	/**
	 * <p>
	 * 사용자가 관리하는 NE Group 목록을 조회한다.
	 * </p>
	 * 
	 * @param context
	 * @param user
	 * @throws EmpException
	 */
	protected Model4User loadUserManageNeGroups(EmpContext context, Model4User user) throws EmpException {
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("user_id", user.getUser_id());

		List<?> list = driver4Mybatis.selectList(context, getDefine_class(), "queryListManageNeGroup", parameter);
		int[] manage_ne_groups = new int[list.size()];
		for (int i = 0; i < manage_ne_groups.length; i++) {
			manage_ne_groups[i] = (Integer) list.get(i);
		}
		user.setManage_ne_groups(manage_ne_groups);

		return user;
	}

	/**
	 * <p>
	 * 사용자가 관리하는 NE Group 목록을 조회한다.
	 * </p>
	 * 
	 * @param context
	 * @param user
	 * @throws EmpException
	 */
	protected Model4User clearUserManageNeGroups(EmpContext context, Model4User user) throws EmpException {
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("user_id", user.getUser_id());

		@SuppressWarnings("unused")
		int count_delete = driver4Mybatis.delete(context, getDefine_class(), "deleteListManageNeGroup", parameter);

		return user;
	}

	protected void clearCache() {
		UtilCache.removeAll(CACHE_ID_KEY);
	}

}
