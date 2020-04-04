/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.server.workflow.network.ne_session.icmp;

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
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.icmp.Model4NeSessionICMP;
import com.hellonms.platforms.emp_util.cache.UtilCache;
import com.hellonms.platforms.emp_util.network.UtilInetAddress;
import com.hellonms.platforms.emp_util.string.UtilString;

/**
 * <p>
 * NE 통신채널 Dao
 * </p>
 *
 * @since 1.6
 * @create 2015. 4. 1.
 * @modified 2015. 4. 1.
 * @author cchyun
 *
 */
public class Dao4NeSessionICMP implements Dao4NeSessionICMPIf {

	protected Driver4MybatisIf driver4Mybatis;

	private static String CACHE_ID_KEY = UtilString.format("{}.{}", Dao4NeSessionICMP.class.getName(), "ne_session_id");
	private static String CACHE_ADDRESS_KEY = UtilString.format("{}.{}", Dao4NeSessionICMP.class.getName(), "ne_address");
	static {
		UtilCache.buildCache(CACHE_ID_KEY, 256, 300);
		UtilCache.buildCache(CACHE_ADDRESS_KEY, 256, 300);
	}

	@SuppressWarnings("unused")
	private static final BlackBox blackBox = new BlackBox(Dao4NeSessionICMP.class);

	@Override
	public Class<? extends DaoIf> getDefine_class() {
		return Dao4NeSessionICMPIf.class;
	}

	@Override
	public void initialize(EmpContext context) throws EmpException {
		driver4Mybatis = (Driver4MybatisIf) WorkflowMap.getDriver(Driver4MybatisIf.class);
	}

	@Override
	public void dispose(EmpContext context) throws EmpException {
	}

	@Override
	public Model4NeSessionICMP createNeSession(EmpContext context, Model4NeSessionICMP ne_session) throws EmpException {
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("ne_id", ne_session.getNe_id());
		Model4NeSessionICMP exists = (Model4NeSessionICMP) driver4Mybatis.selectOne(context, getDefine_class(), "queryNeSession", parameter);
		if (exists != null) {
			throw new EmpException(ERROR_CODE_ORANGE.MODEL_ALREADYEXISTS, MESSAGE_CODE_ORANGE.ICMP, ne_session.getNe_id());
		}

		if (!ne_session.getHost().equals(ne_session.getAddress())) {
			ne_session.setAddress(UtilInetAddress.getInetAddresses(ne_session.getHost()).getHostAddress());
		}
		parameter.clear();
		parameter.put("address", ne_session.getAddress());
		exists = (Model4NeSessionICMP) driver4Mybatis.selectOne(context, getDefine_class(), "queryNeSessionByAddress", parameter);
		if (exists != null) {
			throw new EmpException(ERROR_CODE_ORANGE.MODEL_ALREADYEXISTS, MESSAGE_CODE_ORANGE.ICMP, ne_session.getAddress());
		}

		ne_session.setCreator(context.getUser_account());
		driver4Mybatis.insert(context, getDefine_class(), "createNeSession", ne_session);

		clearCache();
		return queryNeSession(context, ne_session.getNe_id());
	}

	@Override
	public Model4NeSessionICMP queryNeSession(EmpContext context, int ne_id) throws EmpException {
		Model4NeSessionICMP ne_session = (Model4NeSessionICMP) UtilCache.get(CACHE_ID_KEY, ne_id);

		if (ne_session == null) {
			Map<String, Object> parameter = new HashMap<String, Object>();
			parameter.put("ne_id", ne_id);

			ne_session = (Model4NeSessionICMP) driver4Mybatis.selectOne(context, getDefine_class(), "queryNeSession", parameter);
			if (ne_session == null) {
				throw new EmpException(ERROR_CODE_ORANGE.MODEL_NOTEXISTS, MESSAGE_CODE_ORANGE.ICMP, ne_id);
			}
			UtilCache.put(CACHE_ID_KEY, ne_id, ne_session);
		}
		return ne_session.copy();
	}

	@Override
	public Model4NeSessionICMP queryNeSessionByAddress(EmpContext context, String address) throws EmpException {
		Model4NeSessionICMP ne_session = (Model4NeSessionICMP) UtilCache.get(CACHE_ADDRESS_KEY, address);

		if (ne_session == null) {
			Map<String, Object> parameter = new HashMap<String, Object>();
			parameter.put("address", address);

			ne_session = (Model4NeSessionICMP) driver4Mybatis.selectOne(context, getDefine_class(), "queryNeSessionByAddress", parameter);
			if (ne_session == null) {
				throw new EmpException(ERROR_CODE_ORANGE.MODEL_NOTEXISTS, MESSAGE_CODE_ORANGE.ICMP, address);
			}
			UtilCache.put(CACHE_ADDRESS_KEY, address, ne_session);
		}
		return ne_session.copy();
	}

	@Override
	public Model4NeSessionICMP[] queryListNeSession(EmpContext context, int startNo, int count) throws EmpException {
		Map<String, Object> parameter = new HashMap<String, Object>();
		if (context.getUser_id() != 0) {
			parameter.put("user_id", context.getUser_id());
		}
		parameter.put("startNo", startNo);
		parameter.put("count", count);

		List<?> list = driver4Mybatis.selectList(context, getDefine_class(), "queryListNeSession", parameter);

		return list.toArray(new Model4NeSessionICMP[0]);
	}

	@Override
	public Model4NeSessionICMP[] queryListNeSession(EmpContext context, int ne_group_id, int startNo, int count) throws EmpException {
		Map<String, Object> parameter = new HashMap<String, Object>();
		if (context.getUser_id() != 0) {
			parameter.put("user_id", context.getUser_id());
		}
		parameter.put("ne_group_id", ne_group_id);
		parameter.put("startNo", startNo);
		parameter.put("count", count);

		List<?> list = driver4Mybatis.selectList(context, getDefine_class(), "queryListNeSession", parameter);

		return list.toArray(new Model4NeSessionICMP[0]);
	}

	@Override
	public Model4NeSessionICMP[] queryListNeSessionBySchedule(EmpContext context, int second_of_day) throws EmpException {
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("second_of_day", second_of_day);

		List<?> list = driver4Mybatis.selectList(context, getDefine_class(), "queryListNeSessionBySchedule", parameter);

		return list.toArray(new Model4NeSessionICMP[0]);
	}

	@Override
	public int queryCountNeSession(EmpContext context) throws EmpException {
		Map<String, Object> parameter = new HashMap<String, Object>();
		if (context.getUser_id() != 0) {
			parameter.put("user_id", context.getUser_id());
		}

		int count = (Integer) driver4Mybatis.selectOne(context, getDefine_class(), "queryCountNeSession", parameter);

		return count;
	}

	@Override
	public int queryCountNeSession(EmpContext context, int ne_group_id) throws EmpException {
		Map<String, Object> parameter = new HashMap<String, Object>();
		if (context.getUser_id() != 0) {
			parameter.put("user_id", context.getUser_id());
		}
		parameter.put("ne_group_id", ne_group_id);

		int count = (Integer) driver4Mybatis.selectOne(context, getDefine_class(), "queryCountNeSession", parameter);

		return count;
	}

	@Override
	public Model4NeSessionICMP updateNeSession(EmpContext context, Model4NeSessionICMP ne_session) throws EmpException {
		Model4NeSessionICMP exists = queryNeSession(context, ne_session.getNe_id());
		if (exists == null) {
			throw new EmpException(ERROR_CODE_ORANGE.MODEL_NOTEXISTS, MESSAGE_CODE_ORANGE.ICMP, ne_session.getNe_id());
		}

		if (!ne_session.getHost().equals(ne_session.getAddress())) {
			ne_session.setAddress(UtilInetAddress.getInetAddresses(ne_session.getHost()).getHostAddress());
		}
		ne_session.setUpdater(context.getUser_account());
		@SuppressWarnings("unused")
		int count = driver4Mybatis.update(context, getDefine_class(), "updateNeSession", ne_session);

		clearCache();
		return queryNeSession(context, ne_session.getNe_id());
	}

	@Override
	public Model4NeSessionICMP updateNeSessionState(EmpContext context, Model4NeSessionICMP ne_session) throws EmpException {
		Model4NeSessionICMP exists = queryNeSession(context, ne_session.getNe_id());
		if (exists == null) {
			throw new EmpException(ERROR_CODE_ORANGE.MODEL_NOTEXISTS, MESSAGE_CODE_ORANGE.ICMP, ne_session.getNe_id());
		}

		if (!ne_session.getHost().equals(ne_session.getAddress())) {
			ne_session.setAddress(UtilInetAddress.getInetAddresses(ne_session.getHost()).getHostAddress());
		}
		ne_session.setUpdater(context.getUser_account());
		@SuppressWarnings("unused")
		int count = driver4Mybatis.update(context, getDefine_class(), "updateNeSessionState", ne_session);

		clearCache();
		return queryNeSession(context, ne_session.getNe_id());
	}

	@Override
	public Model4NeSessionICMP deleteNeSession(EmpContext context, int ne_id) throws EmpException {
		Model4NeSessionICMP ne_session = queryNeSession(context, ne_id);
		if (ne_session == null) {
			throw new EmpException(ERROR_CODE_ORANGE.MODEL_NOTEXISTS, MESSAGE_CODE_ORANGE.ICMP, ne_id);
		}

		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("ne_id", ne_id);
		parameter.put("updater", context.getUser_account());

		@SuppressWarnings("unused")
		int count = driver4Mybatis.delete(context, getDefine_class(), "deleteNeSession", parameter);

		clearCache();
		return ne_session;
	}

	@Override
	public long queryNextUpdate_seq_network(EmpContext context) throws EmpException {
		long update_seq_network = (Long) driver4Mybatis.selectOne(context, getDefine_class(), "queryNextUpdate_seq_network", null);
		return update_seq_network;
	}

	protected void clearCache() {
		UtilCache.removeAll(CACHE_ID_KEY);
		UtilCache.removeAll(CACHE_ADDRESS_KEY);
	}

	@Override
	public void truncate(EmpContext context) throws EmpException {
		Map<String, Object> parameter = new HashMap<String, Object>();

		@SuppressWarnings("unused")
		int count = (Integer) driver4Mybatis.delete(context, getDefine_class(), "truncate", parameter);
	}

}
