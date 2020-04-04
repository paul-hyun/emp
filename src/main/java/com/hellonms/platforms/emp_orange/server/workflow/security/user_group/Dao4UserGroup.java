/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.server.workflow.security.user_group;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hellonms.platforms.emp_core.server.log.BlackBox;
import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.server.workflow.DaoIf;
import com.hellonms.platforms.emp_core.server.workflow.WorkflowMap;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_core.share.operation.OPERATION_CODE;
import com.hellonms.platforms.emp_onion.server.driver.mybatis.Driver4MybatisIf;
import com.hellonms.platforms.emp_orange.share.error.ERROR_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.security.user_group.Model4UserGroup;
import com.hellonms.platforms.emp_util.cache.UtilCache;
import com.hellonms.platforms.emp_util.string.UtilString;

/**
 * <p>
 * 사용자 그룹 Dao
 * </p>
 * 
 * @since 1.6
 * @create 2015. 3. 13.
 * @modified 2015. 3. 13.
 * @author cchyun
 * 
 */
public class Dao4UserGroup implements Dao4UserGroupIf {

	public static class OperationAuthority {

		public OPERATION_CODE operation_code;

		public boolean authority;

		public OperationAuthority() {
		}

		public OperationAuthority(OPERATION_CODE operation_code, boolean authority) {
			this.operation_code = operation_code;
			this.authority = authority;
		}

	}

	protected Driver4MybatisIf driver4Mybatis;

	private static String CACHE_ID_KEY = UtilString.format("{}.{}", Dao4UserGroup.class.getName(), "user_group_id");
	static {
		UtilCache.buildCache(CACHE_ID_KEY, 256, 300);
	}

	@SuppressWarnings("unused")
	private static final BlackBox blackBox = new BlackBox(Dao4UserGroup.class);

	@Override
	public Class<? extends DaoIf> getDefine_class() {
		return Dao4UserGroupIf.class;
	}

	@Override
	public void initialize(EmpContext context) throws EmpException {
		driver4Mybatis = (Driver4MybatisIf) WorkflowMap.getDriver(Driver4MybatisIf.class);
	}

	@Override
	public void dispose(EmpContext context) throws EmpException {
	}

	@Override
	public Model4UserGroup createUserGroup(EmpContext context, Model4UserGroup user_group) throws EmpException {
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("user_group_account", user_group.getUser_group_account());

		Model4UserGroup exists_group = (Model4UserGroup) driver4Mybatis.selectOne(context, getDefine_class(), "queryUserGroupByAccount", parameter);
		if (exists_group != null) {
			throw new EmpException(ERROR_CODE_ORANGE.MODEL_ALREADYEXISTS, MESSAGE_CODE_ORANGE.USER_GROUP, user_group.getUser_group_account());
		}

		user_group.setCreator(context.getUser_account());
		driver4Mybatis.insert(context, getDefine_class(), "createUserGroup", user_group);

		int user_group_id = (Integer) driver4Mybatis.selectOne(context, getDefine_class(), "queryCurrval_user_group_id", user_group);
		user_group.setUser_group_id(user_group_id);
		saveOperationAuthorities(context, user_group);

		clearCache();
		return queryUserGroup(context, user_group_id);
	}

	@Override
	public Model4UserGroup queryUserGroup(EmpContext context, int user_group_id) throws EmpException {
		Model4UserGroup user_group = (Model4UserGroup) UtilCache.get(CACHE_ID_KEY, user_group_id);

		if (user_group == null) {
			Map<String, Object> parameter = new HashMap<String, Object>();
			parameter.put("user_group_id", user_group_id);

			user_group = (Model4UserGroup) driver4Mybatis.selectOne(context, getDefine_class(), "queryUserGroup", parameter);
			if (user_group == null) {
				throw new EmpException(ERROR_CODE_ORANGE.MODEL_NOTEXISTS, MESSAGE_CODE_ORANGE.USER_GROUP, user_group_id);
			}
			loadOperationAuthorities(context, user_group);
			UtilCache.put(CACHE_ID_KEY, user_group_id, user_group);
		}
		return user_group.copy();
	}

	@Override
	public Model4UserGroup[] queryListUserGroup(EmpContext context, int startNo, int count) throws EmpException {
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("startNo", startNo);
		parameter.put("count", count);

		List<?> list = driver4Mybatis.selectList(context, getDefine_class(), "queryListUserGroup", parameter);

		for (Object aaa : list) {
			Model4UserGroup user_group = (Model4UserGroup) aaa;
			user_group = loadOperationAuthorities(context, user_group);
		}
		return list.toArray(new Model4UserGroup[0]);
	}

	@Override
	public int queryCountUserGroup(EmpContext context) throws EmpException {
		Map<String, Object> parameter = new HashMap<String, Object>();

		int count = (Integer) driver4Mybatis.selectOne(context, getDefine_class(), "queryCountUserGroup", parameter);

		return count;
	}

	@Override
	public Model4UserGroup updateUserGroup(EmpContext context, Model4UserGroup user_group) throws EmpException {
		Model4UserGroup exists = queryUserGroup(context, user_group.getUser_group_id());
		if (exists == null) {
			throw new EmpException(ERROR_CODE_ORANGE.MODEL_NOTEXISTS, MESSAGE_CODE_ORANGE.USER_GROUP, user_group.getUser_group_id());
		}
		if (!exists.getAccess().isUpdate()) {
			throw new EmpException(ERROR_CODE_ORANGE.MODEL_DENYUPDATE, MESSAGE_CODE_ORANGE.USER_GROUP, user_group.getUser_group_account());
		}

		user_group.setUpdater(context.getUser_account());

		@SuppressWarnings("unused")
		int count = driver4Mybatis.update(context, getDefine_class(), "updateUserGroup", user_group);
		saveOperationAuthorities(context, user_group);

		clearCache();
		return queryUserGroup(context, user_group.getUser_group_id());
	}

	@Override
	public Model4UserGroup deleteUserGroup(EmpContext context, int user_group_id) throws EmpException {
		Model4UserGroup user_group = queryUserGroup(context, user_group_id);
		if (user_group == null) {
			throw new EmpException(ERROR_CODE_ORANGE.MODEL_NOTEXISTS, MESSAGE_CODE_ORANGE.USER_GROUP, user_group_id);
		}
		if (!user_group.getAccess().isDelete()) {
			throw new EmpException(ERROR_CODE_ORANGE.MODEL_DENYDELETE, MESSAGE_CODE_ORANGE.USER_GROUP, user_group.getUser_group_account());
		}

		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("user_group_id", user_group_id);
		parameter.put("updater", context.getUser_account());

		@SuppressWarnings("unused")
		int count = driver4Mybatis.delete(context, getDefine_class(), "deleteUserGroup", parameter);
		clearOperationAuthorities(context, user_group);

		clearCache();
		return user_group;
	}

	/**
	 * <p>
	 * 사용자 권한그룹 저장
	 * </p>
	 *
	 * @param context
	 * @param user_group
	 * @return
	 * @throws EmpException
	 */
	private Model4UserGroup saveOperationAuthorities(EmpContext context, Model4UserGroup user_group) throws EmpException {
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("user_group_id", user_group.getUser_group_id());
		OPERATION_CODE[] operation_codes = user_group.getOperation_codes();
		OperationAuthority[] operation_authorities = new OperationAuthority[operation_codes.length];
		for (int i = 0; i < operation_codes.length; i++) {
			operation_authorities[i] = new OperationAuthority(operation_codes[i], user_group.getOperation_authority(operation_codes[i]));
		}
		parameter.put("operation_authorities", operation_authorities);

		@SuppressWarnings("unused")
		int count_delete = driver4Mybatis.delete(context, getDefine_class(), "deleteListOperationAuthorities", parameter);
		@SuppressWarnings("unused")
		int count_create = driver4Mybatis.insert(context, getDefine_class(), "createListOperationAuthorities", parameter);

		return user_group;
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
	private Model4UserGroup loadOperationAuthorities(EmpContext context, Model4UserGroup user_group) throws EmpException {
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("user_group_id", user_group.getUser_group_id());

		List<?> list = driver4Mybatis.selectList(context, getDefine_class(), "queryListOperationAuthorities", parameter);
		for (Object object : list) {
			OperationAuthority operation_authority = (OperationAuthority) object;
			user_group.setOperation_authority(operation_authority.operation_code, operation_authority.authority);
		}

		return user_group;
	}

	/**
	 * <p>
	 * 사용자 권한그룹 삭제
	 * </p>
	 * 
	 * @param context
	 * @param user
	 * @throws EmpException
	 */
	private Model4UserGroup clearOperationAuthorities(EmpContext context, Model4UserGroup user_group) throws EmpException {
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("user_group_id", user_group.getUser_group_id());

		@SuppressWarnings("unused")
		int count_delete = driver4Mybatis.delete(context, getDefine_class(), "deleteListOperationAuthorities", parameter);

		return user_group;
	}

	protected void clearCache() {
		UtilCache.removeAll(CACHE_ID_KEY);
	}

}
