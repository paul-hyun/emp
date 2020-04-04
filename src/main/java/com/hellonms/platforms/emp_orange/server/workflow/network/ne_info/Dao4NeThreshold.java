/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.server.workflow.network.ne_info;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hellonms.platforms.emp_core.server.log.BlackBox;
import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.server.workflow.DaoIf;
import com.hellonms.platforms.emp_core.server.workflow.WorkflowMap;
import com.hellonms.platforms.emp_core.share.error.ERROR_CODE;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_onion.server.driver.mybatis.Driver4MybatisIf;
import com.hellonms.platforms.emp_orange.server.util.UtilInternalError;
import com.hellonms.platforms.emp_orange.server.workflow.network.ne.Dao4Ne;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO_FIELD;
import com.hellonms.platforms.emp_orange.share.model.network.ne_info.Model4NeThreshold;
import com.hellonms.platforms.emp_orange.share.model.network.ne_info.Model4NeThresholdIf;
import com.hellonms.platforms.emp_util.cache.UtilCache;
import com.hellonms.platforms.emp_util.http.UtilJson;
import com.hellonms.platforms.emp_util.string.UtilString;

/**
 * <p>
 * NE 임계치
 * </p>
 *
 * @since 1.6
 * @create 2015. 4. 10.
 * @modified 2015. 4. 10.
 * @author cchyun
 *
 */
public class Dao4NeThreshold implements Dao4NeThresholdIf {

	public static class NeThresholdValue4iBATIS {

		public int ne_id;

		public int ne_info_code;

		public String ne_info_value;

		public String creator;

		public Date create_time;

		public String updater;

		public Date update_time;

		public NeThresholdValue4iBATIS() {
		}

	}

	protected Driver4MybatisIf driver4Mybatis;

	private static String CACHE_FIELD_KEY = UtilString.format("{}.{}", Dao4NeThreshold.class.getName(), "ne_field");
	private static String CACHE_VALUE_KEY = UtilString.format("{}.{}", Dao4NeThreshold.class.getName(), "ne_value");
	static {
		UtilCache.buildCache(CACHE_FIELD_KEY, 20480, 3600);
		UtilCache.buildCache(CACHE_VALUE_KEY, 20480, 3600);
	}

	@SuppressWarnings("unused")
	private static final BlackBox blackBox = new BlackBox(Dao4Ne.class);

	@Override
	public Class<? extends DaoIf> getDefine_class() {
		return Dao4NeThresholdIf.class;
	}

	@Override
	public void initialize(EmpContext context) throws EmpException {
		driver4Mybatis = (Driver4MybatisIf) WorkflowMap.getDriver(Driver4MybatisIf.class);
	}

	@Override
	public void dispose(EmpContext context) throws EmpException {
	}

	@Override
	public Model4NeThresholdIf queryNeThreshold(EmpContext context, int ne_id, EMP_MODEL_NE_INFO ne_info_def) throws EmpException {
		String key = new StringBuilder().append(ne_id).append(".").append(ne_info_def.getCode()).toString();
		Model4NeThresholdIf ne_threshold = (Model4NeThresholdIf) UtilCache.get(CACHE_VALUE_KEY, key);

		if (ne_threshold == null) {
			Map<String, Object> parameter = new HashMap<String, Object>();
			parameter.put("ne_id", ne_id);
			parameter.put("ne_info_code", ne_info_def.getCode());

			List<?> db_list = driver4Mybatis.selectList(context, getDefine_class(), "queryNeThreshold", parameter);
			if (db_list.isEmpty()) {
				ne_threshold = EMP_MODEL.current().newInstanceThreshold(ne_info_def.getCode());
				if (ne_threshold != null) {
					ne_threshold.setNe_id(ne_id);
				}
			} else {
				ne_threshold = toModel(context, ne_info_def, db_list);
			}
			UtilCache.put(CACHE_VALUE_KEY, key, ne_threshold);
		}

		return (Model4NeThresholdIf) (ne_threshold == null ? null : ne_threshold.copy());
	}

	@Override
	public Model4NeThresholdIf updateNeThreshold(EmpContext context, Model4NeThresholdIf ne_threshold, EMP_MODEL_NE_INFO_FIELD ne_info_field_def) throws EmpException {
		Model4NeThresholdIf ne_threshold_db = queryNeThreshold(context, ne_threshold.getNe_id(), ne_threshold.getNe_info_def());
		ne_threshold_db.setThreshold(ne_info_field_def, ne_threshold);

		Map<String, Object> parameter = new HashMap<String, Object>();
		List<NeThresholdValue4iBATIS> row_list = new ArrayList<NeThresholdValue4iBATIS>();
		row_list.add(toDatabase(context, ne_threshold_db));
		parameter.put("row_list", row_list);
		driver4Mybatis.insert(context, getDefine_class(), "createNeThreshold", parameter);

		clearCache();
		return queryNeThreshold(context, ne_threshold.getNe_id(), ne_threshold.getNe_info_def());
	}

	@Override
	public Model4NeThresholdIf[] copyListNeThreshold(EmpContext context, int ne_id_source, EMP_MODEL_NE_INFO ne_info_def, int[] ne_id_targets) throws EmpException {
		Model4NeThresholdIf ne_threshold_surce = queryNeThreshold(context, ne_id_source, ne_info_def);

		boolean auto_commit = context.isAuto_commit();
		try {
			context.setAuto_commit(false);
			for (int ne_id_target : ne_id_targets) {
				ne_threshold_surce.setNe_id(ne_id_target);

				Map<String, Object> parameter = new HashMap<String, Object>();
				List<NeThresholdValue4iBATIS> row_list = new ArrayList<NeThresholdValue4iBATIS>();
				row_list.add(toDatabase(context, ne_threshold_surce));
				parameter.put("row_list", row_list);
				driver4Mybatis.insert(context, getDefine_class(), "createNeThreshold", parameter);
			}
			context.commit();
		} catch (EmpException e) {
			context.rollback();
			throw e;
		} catch (Exception e) {
			context.rollback();
			throw new EmpException(ERROR_CODE.ERROR_UNKNOWN, e);
		} finally {
			context.setAuto_commit(auto_commit);
		}

		clearCache();
		List<Model4NeThresholdIf> list = new ArrayList<Model4NeThresholdIf>();
		for (int ne_id_target : ne_id_targets) {
			list.add(queryNeThreshold(context, ne_id_target, ne_info_def));
		}
		return list.toArray(new Model4NeThresholdIf[0]);
	}

	protected NeThresholdValue4iBATIS toDatabase(EmpContext context, Model4NeThresholdIf ne_threshold) throws EmpException {
		NeThresholdValue4iBATIS value = new NeThresholdValue4iBATIS();
		value.ne_id = ne_threshold.getNe_id();
		value.ne_info_code = ne_threshold.getNe_info_code();
		value.ne_info_value = UtilJson.toString(ne_threshold.getMap());
		value.creator = context.getUser_account();
		value.create_time = new Date();
		value.updater = context.getUser_account();
		value.update_time = value.create_time;
		return value;
	}

	protected Model4NeThresholdIf toModel(EmpContext context, EMP_MODEL_NE_INFO ne_info_def, List<?> db_list) {
		Model4NeThreshold model = EMP_MODEL.current().newInstanceThreshold(ne_info_def.getCode());
		for (Object db : db_list) {
			NeThresholdValue4iBATIS vdb_value = (NeThresholdValue4iBATIS) db;
			try {
				Map<String, Object> mmm = UtilJson.toMap(vdb_value.ne_info_value);

				model.setNe_id(vdb_value.ne_id);
				model.setMap(mmm);
				model.setCreator(vdb_value.creator);
				model.setCreate_time(vdb_value.create_time);
				model.setUpdater(vdb_value.updater);
				model.setUpdate_time(vdb_value.update_time);
			} catch (EmpException e) {
				UtilInternalError.notifyInternalError(context, vdb_value.ne_id, "NE_THRESHOLD_VALUE", UtilString.format("ne_info_value fail {}", e.getCause()));
			} catch (Exception e) {
				UtilInternalError.notifyInternalError(context, vdb_value.ne_id, "NE_THRESHOLD_VALUE", UtilString.format("ne_info_value fail {}", e));
			}
		}
		return model;
	}

	protected void clearCache() {
		UtilCache.removeAll(CACHE_FIELD_KEY);
		UtilCache.removeAll(CACHE_VALUE_KEY);
	}

	@Override
	public void truncate(EmpContext context) throws EmpException {
		Map<String, Object> parameter = new HashMap<String, Object>();

		@SuppressWarnings("unused")
		int count = (Integer) driver4Mybatis.delete(context, getDefine_class(), "truncate", parameter);
	}

}
