/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.server.workflow.network.ne_info;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.locks.ReentrantLock;

import com.hellonms.platforms.emp_core.server.log.BlackBox;
import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.server.workflow.DaoIf;
import com.hellonms.platforms.emp_core.server.workflow.WorkflowMap;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_onion.server.driver.mybatis.Driver4MybatisIf;
import com.hellonms.platforms.emp_orange.server.util.UtilInternalError;
import com.hellonms.platforms.emp_orange.share.error.ERROR_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO;
import com.hellonms.platforms.emp_orange.share.model.network.ne_info.Model4NeInfo;
import com.hellonms.platforms.emp_orange.share.model.network.ne_info.Model4NeInfoIf;
import com.hellonms.platforms.emp_orange.share.model.network.ne_info.NE_INFO_INDEX;
import com.hellonms.platforms.emp_util.cache.UtilCache;
import com.hellonms.platforms.emp_util.http.UtilJson;
import com.hellonms.platforms.emp_util.lock.UtilKeyLock;
import com.hellonms.platforms.emp_util.string.UtilString;

/**
 * <p>
 * NE 정보
 * </p>
 *
 * @since 1.6
 * @create 2015. 4. 10.
 * @modified 2015. 4. 10.
 * @author cchyun
 *
 */
public class Dao4NeInfo implements Dao4NeInfoIf {

	public static class NeInfoValue4iBATIS {

		public int ne_id;

		public int ne_info_code;

		public int ne_info_index;

		public Date collect_time;

		public String ne_info_value;

		public boolean equals(NeInfoValue4iBATIS value) {
			return ne_info_value.equals(value.ne_info_value);
		}

	}

	public static final ReentrantLock ne_info_field_lock = new ReentrantLock();

	protected Driver4MybatisIf driver4Mybatis;

	private static String CACHE_INDEX_VALUE_KEY = UtilString.format("{}.{}", Dao4NeInfo.class.getName(), "ne_info_index.value");
	private static String CACHE_INDEX_CODE_KEY = UtilString.format("{}.{}", Dao4NeInfo.class.getName(), "ne_info_index.key");
	static {
		UtilCache.buildCache(CACHE_INDEX_VALUE_KEY, 20480, 3600);
		UtilCache.buildCache(CACHE_INDEX_CODE_KEY, 20480, 3600);
	}
	private ReentrantLock ne_info_index_lock = new ReentrantLock();

	private UtilKeyLock<Integer> ne_id_lock = new UtilKeyLock<Integer>();

	@SuppressWarnings("unused")
	private static final BlackBox blackBox = new BlackBox(Dao4NeInfo.class);

	@Override
	public Class<? extends DaoIf> getDefine_class() {
		return Dao4NeInfoIf.class;
	}

	@Override
	public void initialize(EmpContext context) throws EmpException {
		driver4Mybatis = (Driver4MybatisIf) WorkflowMap.getDriver(Driver4MybatisIf.class);
	}

	@Override
	public void dispose(EmpContext context) throws EmpException {
	}

	@Override
	public Model4NeInfoIf queryNeInfo(EmpContext context, int ne_id, EMP_MODEL_NE_INFO ne_info_def, int ne_info_index) throws EmpException {
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("ne_id", ne_id);
		parameter.put("ne_info_code", ne_info_def.getCode());
		parameter.put("ne_info_index", ne_info_index);

		List<?> db_list = driver4Mybatis.selectList(context, getDefine_class(), "queryListNeInfo", parameter);
		Map<String, Model4NeInfo> db_map = toModel(context, ne_info_def, db_list);

		if (db_map.size() != 1) {
			throw new EmpException(ERROR_CODE_ORANGE.MODEL_NOTEXISTS, ne_info_def, ne_id, ne_info_def, ne_info_index);
		} else {
			return (Model4NeInfoIf) db_map.values().toArray()[0];
		}
	}

	@Override
	public Model4NeInfoIf[] queryAllNeInfo(EmpContext context, EMP_MODEL_NE_INFO ne_info_def) throws EmpException {
		if (ne_info_def == null || !ne_info_def.isMonitoring()) {
			return new Model4NeInfoIf[] {};
		}

		Map<String, Object> parameter = new HashMap<String, Object>();
		if (context.getUser_id() != 0) {
			parameter.put("user_id", context.getUser_id());
		}
		parameter.put("ne_info_code", ne_info_def.getCode());

		List<?> db_list = driver4Mybatis.selectList(context, getDefine_class(), "queryAllNeInfo", parameter);
		Map<String, Model4NeInfo> db_map = toModel(context, ne_info_def, db_list);

		Model4NeInfoIf[] ne_infos = db_map.values().toArray(new Model4NeInfoIf[0]);
		return ne_infos;
	}

	@Override
	public Model4NeInfoIf[] queryListNeInfo(EmpContext context, int ne_id, EMP_MODEL_NE_INFO ne_info_def) throws EmpException {
		if (!ne_info_def.isMonitoring()) {
			return new Model4NeInfoIf[] {};
		}

		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("ne_id", ne_id);
		parameter.put("ne_info_code", ne_info_def.getCode());

		List<?> db_list = driver4Mybatis.selectList(context, getDefine_class(), "queryListNeInfo", parameter);
		Map<String, Model4NeInfo> db_map = toModel(context, ne_info_def, db_list);

		return db_map.values().toArray(new Model4NeInfoIf[0]);
	}

	@Override
	public Model4NeInfoIf syncNeInfo(EmpContext context, int ne_id, EMP_MODEL_NE_INFO ne_info_def, Model4NeInfoIf ne_info) throws EmpException {
		ne_id_lock.lock(ne_id);
		try {
			List<NeInfoValue4iBATIS> ne_list = new ArrayList<NeInfoValue4iBATIS>();
			ne_list.add(toDatabase(context, ne_info));

			Map<String, Object> parameter = new HashMap<String, Object>();
			parameter.put("ne_id", ne_id);
			parameter.put("ne_info_code", ne_info_def.getCode());
			parameter.put("ne_info_index", ne_info.getNe_info_index());
			parameter.put("startNo", 0);
			parameter.put("count", 999999);
			List<?> db_list = driver4Mybatis.selectList(context, getDefine_class(), "queryListNeInfo", parameter);

			Map<String, NeInfoValue4iBATIS> db_map = new LinkedHashMap<String, NeInfoValue4iBATIS>();
			for (Object object : db_list) {
				NeInfoValue4iBATIS value = (NeInfoValue4iBATIS) object;
				db_map.put(UtilString.format("{}.{}.{}", value.ne_id, value.ne_info_code, value.ne_info_index), value);
			}

			LinkedList<NeInfoValue4iBATIS> value_create = new LinkedList<NeInfoValue4iBATIS>();
			LinkedList<NeInfoValue4iBATIS> value_update = new LinkedList<NeInfoValue4iBATIS>();
			LinkedList<NeInfoValue4iBATIS> value_delete = new LinkedList<NeInfoValue4iBATIS>();
			for (NeInfoValue4iBATIS ne_value : ne_list) {
				NeInfoValue4iBATIS db_value = db_map.remove(UtilString.format("{}.{}.{}", ne_value.ne_id, ne_value.ne_info_code, ne_value.ne_info_index));
				if (db_value == null) {
					value_create.addLast(ne_value);
				} else if (!ne_value.equals(db_value)) {
					value_update.addLast(ne_value);
				}
			}
			value_delete.addAll(db_map.values());

			while (!value_delete.isEmpty()) {
				List<NeInfoValue4iBATIS> row_list = new ArrayList<NeInfoValue4iBATIS>();
				for (int i = 0; !value_delete.isEmpty() && i < 1000; i++) {
					row_list.add(value_delete.removeFirst());
				}

				parameter.clear();
				parameter.put("row_list", row_list);

				@SuppressWarnings("unused")
				int count = driver4Mybatis.delete(context, getDefine_class(), "deleteListNeInfo", parameter);
			}
			while (!value_update.isEmpty()) {
				List<NeInfoValue4iBATIS> row_list = new ArrayList<NeInfoValue4iBATIS>();
				for (int i = 0; !value_update.isEmpty() && i < 1000; i++) {
					row_list.add(value_update.removeFirst());
				}

				parameter.clear();
				parameter.put("row_list", row_list);

				@SuppressWarnings("unused")
				int count = driver4Mybatis.update(context, getDefine_class(), "updateListNeInfo", parameter);
			}
			while (!value_create.isEmpty()) {
				List<NeInfoValue4iBATIS> row_list = new ArrayList<NeInfoValue4iBATIS>();
				for (int i = 0; !value_create.isEmpty() && i < 1000; i++) {
					row_list.add(value_create.removeFirst());
				}

				parameter.clear();
				parameter.put("row_list", row_list);

				@SuppressWarnings("unused")
				int count = driver4Mybatis.insert(context, getDefine_class(), "createListNeInfo", parameter);
			}

			return ne_info;
		} finally {
			ne_id_lock.unlock(ne_id);
			clearCache();
		}
	}

	@Override
	public Model4NeInfoIf[] syncListNeInfo(EmpContext context, int ne_id, EMP_MODEL_NE_INFO ne_info_def, Model4NeInfoIf[] ne_infos) throws EmpException {
		ne_id_lock.lock(ne_id);
		try {
			List<NeInfoValue4iBATIS> ne_list = new ArrayList<NeInfoValue4iBATIS>();
			for (Model4NeInfoIf ne_info : ne_infos) {
				ne_list.add(toDatabase(context, ne_info));
			}

			Map<String, Object> parameter = new HashMap<String, Object>();
			parameter.put("ne_id", ne_id);
			parameter.put("ne_info_code", ne_info_def.getCode());
			parameter.put("startNo", 0);
			parameter.put("count", 999999);
			List<?> db_list = driver4Mybatis.selectList(context, getDefine_class(), "queryListNeInfo", parameter);

			Map<String, NeInfoValue4iBATIS> db_map = new LinkedHashMap<String, NeInfoValue4iBATIS>();
			for (Object object : db_list) {
				NeInfoValue4iBATIS value = (NeInfoValue4iBATIS) object;
				db_map.put(UtilString.format("{}.{}.{}", value.ne_id, value.ne_info_code, value.ne_info_index), value);
			}

			LinkedList<NeInfoValue4iBATIS> value_create = new LinkedList<NeInfoValue4iBATIS>();
			LinkedList<NeInfoValue4iBATIS> value_update = new LinkedList<NeInfoValue4iBATIS>();
			LinkedList<NeInfoValue4iBATIS> value_delete = new LinkedList<NeInfoValue4iBATIS>();
			for (NeInfoValue4iBATIS ne_value : ne_list) {
				NeInfoValue4iBATIS db_value = db_map.remove(UtilString.format("{}.{}.{}", ne_value.ne_id, ne_value.ne_info_code, ne_value.ne_info_index));
				if (db_value == null) {
					value_create.addLast(ne_value);
				} else if (!ne_value.equals(db_value)) {
					value_update.addLast(ne_value);
				}
			}
			value_delete.addAll(db_map.values());

			while (!value_delete.isEmpty()) {
				List<NeInfoValue4iBATIS> row_list = new ArrayList<NeInfoValue4iBATIS>();
				for (int i = 0; !value_delete.isEmpty() && i < 1000; i++) {
					row_list.add(value_delete.removeFirst());
				}

				parameter.clear();
				parameter.put("row_list", row_list);

				@SuppressWarnings("unused")
				int count = driver4Mybatis.delete(context, getDefine_class(), "deleteListNeInfo", parameter);
			}
			while (!value_update.isEmpty()) {
				List<NeInfoValue4iBATIS> row_list = new ArrayList<NeInfoValue4iBATIS>();
				for (int i = 0; !value_update.isEmpty() && i < 1000; i++) {
					row_list.add(value_update.removeFirst());
				}

				parameter.clear();
				parameter.put("row_list", row_list);

				@SuppressWarnings("unused")
				int count = driver4Mybatis.update(context, getDefine_class(), "updateListNeInfo", parameter);
			}
			while (!value_create.isEmpty()) {
				List<NeInfoValue4iBATIS> row_list = new ArrayList<NeInfoValue4iBATIS>();
				for (int i = 0; !value_create.isEmpty() && i < 1000; i++) {
					row_list.add(value_create.removeFirst());
				}

				parameter.clear();
				parameter.put("row_list", row_list);

				@SuppressWarnings("unused")
				int count = driver4Mybatis.insert(context, getDefine_class(), "createListNeInfo", parameter);
			}

			return ne_infos;
		} finally {
			ne_id_lock.unlock(ne_id);
			clearCache();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public NE_INFO_INDEX queryNeInfoIndex(EmpContext context, String... ne_info_index_values) throws EmpException {
		String ne_info_field_values = NE_INFO_INDEX.toDatabase(ne_info_index_values);

		NE_INFO_INDEX ne_info_index_code = (NE_INFO_INDEX) UtilCache.get(CACHE_INDEX_VALUE_KEY, ne_info_field_values);
		if (ne_info_index_code == null) {
			ne_info_index_lock.lock();
			try {
				try {
					Map<String, Object> parameter = new HashMap<String, Object>();
					parameter.put("ne_info_field_values", ne_info_field_values);

					Map<String, Object> index_value = (Map<String, Object>) driver4Mybatis.selectOne(context, getDefine_class(), "queryNeInfoIndex", parameter);
					if (index_value == null) {
						driver4Mybatis.insert(context, getDefine_class(), "createNeInfoIndex", parameter);
						index_value = (Map<String, Object>) driver4Mybatis.selectOne(context, getDefine_class(), "queryNeInfoIndex", parameter);
					}

					if (index_value == null) {
						throw new RuntimeException(UtilString.format("Can't set ne_info_index to database !!"));
					}
					ne_info_field_values = (String) index_value.get("ne_info_field_values");
					ne_info_index_code = new NE_INFO_INDEX((Integer) index_value.get("ne_info_index"), NE_INFO_INDEX.toModel(ne_info_field_values));

					UtilCache.put(CACHE_INDEX_VALUE_KEY, ne_info_field_values, ne_info_index_code);
					UtilCache.put(CACHE_INDEX_CODE_KEY, ne_info_index_code.getNe_info_index(), ne_info_index_code);
				} finally {
				}
			} finally {
				ne_info_index_lock.unlock();
			}
		}
		return ne_info_index_code;
	}

	@Override
	public NE_INFO_INDEX queryNeInfoIndex(EmpContext context, int ne_info_index) throws EmpException {
		NE_INFO_INDEX ne_info_index_code = (NE_INFO_INDEX) UtilCache.get(CACHE_INDEX_CODE_KEY, ne_info_index);
		if (ne_info_index_code == null) {
			ne_info_index_lock.lock();
			try {
				try {
					Map<String, Object> parameter = new HashMap<String, Object>();
					parameter.put("ne_info_index", ne_info_index);

					@SuppressWarnings("unchecked")
					Map<String, Object> index_value = (Map<String, Object>) driver4Mybatis.selectOne(context, getDefine_class(), "queryNeInfoIndexByCode", parameter);
					if (index_value == null) {
						throw new EmpException(ERROR_CODE_ORANGE.MODEL_NOTEXISTS, ne_info_index);
					}

					String ne_info_field_values = (String) index_value.get("ne_info_field_values");
					ne_info_index_code = new NE_INFO_INDEX((Integer) index_value.get("ne_info_index"), NE_INFO_INDEX.toModel(ne_info_field_values));

					UtilCache.put(CACHE_INDEX_VALUE_KEY, ne_info_field_values, ne_info_index_code);
					UtilCache.put(CACHE_INDEX_CODE_KEY, ne_info_index_code.getNe_info_index(), ne_info_index_code);
				} finally {
				}
			} finally {
				ne_info_index_lock.unlock();
			}
		}
		return ne_info_index_code;
	}

	protected NeInfoValue4iBATIS toDatabase(EmpContext context, Model4NeInfoIf ne_info) throws EmpException {
		NeInfoValue4iBATIS value = new NeInfoValue4iBATIS();
		value.ne_id = ne_info.getNe_id();
		value.ne_info_code = ne_info.getNe_info_code();
		value.ne_info_index = ne_info.getNe_info_index();
		value.collect_time = ne_info.getCollect_time();
		value.ne_info_value = UtilJson.toString(ne_info.getMap());
		return value;
	}

	protected Map<String, Model4NeInfo> toModel(EmpContext context, EMP_MODEL_NE_INFO ne_info_def, List<?> db_list) {
		TreeMap<String, Model4NeInfo> map = new TreeMap<String, Model4NeInfo>();
		for (Object db : db_list) {
			NeInfoValue4iBATIS vdb_value = (NeInfoValue4iBATIS) db;
			try {
				Map<String, Object> mmm = UtilJson.toMap(vdb_value.ne_info_value);
				String key = UtilString.format("{}.{}.{}", vdb_value.ne_id, vdb_value.ne_info_code, vdb_value.ne_info_index);
				Model4NeInfo ne_info = map.get(key);
				if (ne_info == null) {
					ne_info = EMP_MODEL.current().newInstanceNe_info(ne_info_def.getCode());
					ne_info.setNe_id(vdb_value.ne_id);
					ne_info.setNe_info_index(vdb_value.ne_info_index);
					ne_info.setCollect_time(vdb_value.collect_time);
					map.put(key, ne_info);
				}
				ne_info.setMap(mmm);
			} catch (EmpException e) {
				UtilInternalError.notifyInternalError(context, vdb_value.ne_id, "NE_INFO_VALUE", UtilString.format("ne_info_value fail {}", e.getCause()));
			} catch (Exception e) {
				UtilInternalError.notifyInternalError(context, vdb_value.ne_id, "NE_INFO_VALUE", UtilString.format("ne_info_value fail {}", e));
			}
		}
		return map;
	}

	protected void clearCache() {
	}

	@Override
	public void truncate(EmpContext context) throws EmpException {
		Map<String, Object> parameter = new HashMap<String, Object>();

		@SuppressWarnings("unused")
		int count = (Integer) driver4Mybatis.delete(context, getDefine_class(), "truncateNeInfoValue", parameter);
		count = (Integer) driver4Mybatis.delete(context, getDefine_class(), "truncateNeInfoIndex", parameter);
	}

}
