/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.server.workflow.network.ne;

import java.util.Date;
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
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE;
import com.hellonms.platforms.emp_orange.share.model.network.ne.Model4Ne;
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
public class Dao4Ne implements Dao4NeIf {

	protected Driver4MybatisIf driver4Mybatis;

	private static String CACHE_ID_KEY = UtilString.format("{}.{}", Dao4Ne.class.getName(), "ne_id");
	static {
		UtilCache.buildCache(CACHE_ID_KEY, 256, 300);
	}

	protected static final BlackBox blackBox = new BlackBox(Dao4Ne.class);

	@Override
	public Class<? extends DaoIf> getDefine_class() {
		return Dao4NeIf.class;
	}

	@Override
	public void initialize(EmpContext context) throws EmpException {
		driver4Mybatis = (Driver4MybatisIf) WorkflowMap.getDriver(Driver4MybatisIf.class);
	}

	@Override
	public void dispose(EmpContext context) throws EmpException {
	}

	@Override
	public Model4Ne createNe(EmpContext context, Model4Ne ne) throws EmpException {
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("ne_group_id", ne.getNe_group_id());
		parameter.put("ne_name", ne.getNe_name());

		Model4Ne exists = (Model4Ne) driver4Mybatis.selectOne(context, getDefine_class(), "queryNeByName", parameter);
		if (exists != null) {
			throw new EmpException(ERROR_CODE_ORANGE.MODEL_ALREADYEXISTS, MESSAGE_CODE_ORANGE.NE, ne.getNe_name());
		}

		ne.setMonitoring_timestamp(new Date());
		ne.setCreator(context.getUser_account());
		driver4Mybatis.insert(context, getDefine_class(), "createNe", ne);

		int ne_id = (Integer) driver4Mybatis.selectOne(context, getDefine_class(), "queryCurrval_ne_id", ne);
		ne.setNe_id(ne_id);

		clearCache();
		return queryNe(context, ne_id);
	}

	@Override
	public Model4Ne queryNe(EmpContext context, int ne_id) throws EmpException {
		Model4Ne ne = (Model4Ne) UtilCache.get(CACHE_ID_KEY, ne_id);

		if (ne == null) {
			Map<String, Object> parameter = new HashMap<String, Object>();
			parameter.put("ne_id", ne_id);

			ne = (Model4Ne) driver4Mybatis.selectOne(context, getDefine_class(), "queryNe", parameter);
			if (ne == null) {
				throw new EmpException(ERROR_CODE_ORANGE.MODEL_NOTEXISTS, MESSAGE_CODE_ORANGE.NE, ne_id);
			}
			UtilCache.put(CACHE_ID_KEY, ne_id, ne);
		}
		return ne.copy();
	}

	@Override
	public Model4Ne queryNe(EmpContext context, int ne_group_id, String ne_name) throws EmpException {
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("ne_group_id", ne_group_id);
		parameter.put("ne_name", ne_name);

		Model4Ne ne = (Model4Ne) driver4Mybatis.selectOne(context, getDefine_class(), "queryNeByName", parameter);
		if (ne == null) {
			throw new EmpException(ERROR_CODE_ORANGE.MODEL_NOTEXISTS, MESSAGE_CODE_ORANGE.NE, ne_group_id, ne_name);
		}
		return ne;
	}

	@Override
	public Model4Ne[] queryListNe(EmpContext context, int startNo, int count) throws EmpException {
		Map<String, Object> parameter = new HashMap<String, Object>();
		if (context.getUser_id() != 0) {
			parameter.put("user_id", context.getUser_id());
		}
		parameter.put("startNo", startNo);
		parameter.put("count", count);

		List<?> list = driver4Mybatis.selectList(context, getDefine_class(), "queryListNe", parameter);

		return list.toArray(new Model4Ne[0]);
	}

	@Override
	public Model4Ne[] queryListNe(EmpContext context, int ne_group_id, int startNo, int count) throws EmpException {
		Map<String, Object> parameter = new HashMap<String, Object>();
		if (context.getUser_id() != 0) {
			parameter.put("user_id", context.getUser_id());
		}
		parameter.put("ne_group_id", ne_group_id);
		parameter.put("startNo", startNo);
		parameter.put("count", count);

		List<?> list = driver4Mybatis.selectList(context, getDefine_class(), "queryListNe", parameter);

		return list.toArray(new Model4Ne[0]);
	}

	@Override
	public Model4Ne[] queryListNe(EmpContext context, EMP_MODEL_NE ne_def, int startNo, int count) throws EmpException {
		Map<String, Object> parameter = new HashMap<String, Object>();
		if (context.getUser_id() != 0) {
			parameter.put("user_id", context.getUser_id());
		}
		parameter.put("ne_code", ne_def.getCode());
		parameter.put("startNo", startNo);
		parameter.put("count", count);

		List<?> list = driver4Mybatis.selectList(context, getDefine_class(), "queryListNe", parameter);

		return list.toArray(new Model4Ne[0]);
	}

	@Override
	public Model4Ne[] queryAllNe(EmpContext context) throws EmpException {
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("startNo", 0);
		parameter.put("count", 999999);

		List<?> list = driver4Mybatis.selectList(context, getDefine_class(), "queryListNe", parameter);

		return list.toArray(new Model4Ne[0]);
	}

	@Override
	public Model4Ne[] queryAllNe(EmpContext context, int ne_group_id) throws EmpException {
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("ne_group_id", ne_group_id);
		parameter.put("startNo", 0);
		parameter.put("count", 999999);

		List<?> list = driver4Mybatis.selectList(context, getDefine_class(), "queryListNe", parameter);

		return list.toArray(new Model4Ne[0]);
	}

	@Override
	public int queryCountNe(EmpContext context) throws EmpException {
		Map<String, Object> parameter = new HashMap<String, Object>();
		if (context.getUser_id() != 0) {
			parameter.put("user_id", context.getUser_id());
		}

		int count = (Integer) driver4Mybatis.selectOne(context, getDefine_class(), "queryCountNe", parameter);

		return count;
	}

	@Override
	public int queryCountNe(EmpContext context, int ne_group_id) throws EmpException {
		Map<String, Object> parameter = new HashMap<String, Object>();
		if (context.getUser_id() != 0) {
			parameter.put("user_id", context.getUser_id());
		}
		parameter.put("ne_group_id", ne_group_id);

		int count = (Integer) driver4Mybatis.selectOne(context, getDefine_class(), "queryCountNe", parameter);

		return count;
	}

	@Override
	public int queryCountNe(EmpContext context, EMP_MODEL_NE ne_def) throws EmpException {
		Map<String, Object> parameter = new HashMap<String, Object>();
		if (context.getUser_id() != 0) {
			parameter.put("user_id", context.getUser_id());
		}
		parameter.put("ne_code", ne_def.getCode());

		int count = (Integer) driver4Mybatis.selectOne(context, getDefine_class(), "queryCountNe", parameter);

		return count;
	}

	@Override
	public Model4Ne updateNe(EmpContext context, Model4Ne ne) throws EmpException {
		Model4Ne exists = queryNe(context, ne.getNe_id());
		if (exists == null) {
			throw new EmpException(ERROR_CODE_ORANGE.MODEL_NOTEXISTS, MESSAGE_CODE_ORANGE.NE, ne.getNe_id());
		}
		if (!exists.getAccess().isUpdate()) {
			throw new EmpException(ERROR_CODE_ORANGE.MODEL_DENYUPDATE, MESSAGE_CODE_ORANGE.NE, ne.getNe_name());
		}

		if (ne.getNe_group_id() != exists.getNe_group_id()) { // 이동인 경우
			// 2. 동일한 이름 존재여부 확인
			Map<String, Object> parameter = new HashMap<String, Object>();
			parameter.clear();
			parameter.put("ne_group_id", ne.getNe_group_id());
			parameter.put("ne_name", ne.getNe_name());

			Model4Ne duplicate = (Model4Ne) driver4Mybatis.selectOne(context, getDefine_class(), "queryNeByName", parameter);
			if (duplicate != null) {
				throw new EmpException(ERROR_CODE_ORANGE.MODEL_ALREADYEXISTS, MESSAGE_CODE_ORANGE.NE, ne.getNe_name());
			}

			parameter.put("ne_id", ne.getNe_id());
			parameter.put("ne_group_id", ne.getNe_group_id());
			parameter.put("updater", context.getUser_account());
			driver4Mybatis.update(context, getDefine_class(), "moveNe", parameter);
		}

		ne.setUpdater(context.getUser_account());

		@SuppressWarnings("unused")
		int count = driver4Mybatis.update(context, getDefine_class(), "updateNe", ne);

		clearCache();
		return queryNe(context, ne.getNe_id());
	}

	@Override
	public Model4Ne updateNeMapLocation(EmpContext context, Model4Ne ne) throws EmpException {
		Model4Ne exists = queryNe(context, ne.getNe_id());
		if (exists == null) {
			throw new EmpException(ERROR_CODE_ORANGE.MODEL_NOTEXISTS, MESSAGE_CODE_ORANGE.NE, ne.getNe_id());
		}
		// read only라도 좌표는 수정할 수 있음.
		// if (!exists.getAccess().isUpdate()) {
		// throw new EmpException(ERROR_CODE_ORANGE.MODEL_DENYUPDATE, MESSAGE_CODE_ORANGE.NE, ne.getNe_name());
		// }

		@SuppressWarnings("unused")
		int count = driver4Mybatis.update(context, getDefine_class(), "updateNeMapLocation", ne);

		clearCache();
		return queryNe(context, ne.getNe_id());
	}

	@Override
	public Model4Ne deleteNe(EmpContext context, int ne_id) throws EmpException {
		Model4Ne ne = queryNe(context, ne_id);
		if (ne == null) {
			throw new EmpException(ERROR_CODE_ORANGE.MODEL_NOTEXISTS, MESSAGE_CODE_ORANGE.NE, ne_id);
		}
		if (!ne.getAccess().isDelete()) {
			throw new EmpException(ERROR_CODE_ORANGE.MODEL_DENYDELETE, MESSAGE_CODE_ORANGE.NE, ne.getNe_name());
		}

		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("ne_id", ne_id);
		parameter.put("updater", context.getUser_account());

		@SuppressWarnings("unused")
		int count = driver4Mybatis.delete(context, getDefine_class(), "deleteNe", parameter);

		clearCache();
		return ne;
	}

	@Override
	public long queryCurrUpdate_seq_network(EmpContext context) throws EmpException {
		long update_seq_network = (Long) driver4Mybatis.selectOne(context, getDefine_class(), "queryCurrUpdate_seq_network", null);
		return update_seq_network;
	}

	@Override
	public long queryNextUpdate_seq_network(EmpContext context) throws EmpException {
		long update_seq_network = (Long) driver4Mybatis.selectOne(context, getDefine_class(), "queryNextUpdate_seq_network", null);
		return update_seq_network;
	}

	protected void clearCache() {
		UtilCache.removeAll(CACHE_ID_KEY);
	}

	@Override
	public void truncate(EmpContext context) throws EmpException {
		Map<String, Object> parameter = new HashMap<String, Object>();

		@SuppressWarnings("unused")
		int count = (Integer) driver4Mybatis.delete(context, getDefine_class(), "truncate", parameter);
		count = driver4Mybatis.insert(context, getDefine_class(), "init_nms", parameter);
	}

}
