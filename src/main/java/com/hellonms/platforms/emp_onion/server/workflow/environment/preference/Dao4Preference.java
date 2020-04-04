/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_onion.server.workflow.environment.preference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hellonms.platforms.emp_core.server.log.BlackBox;
import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.server.workflow.DaoIf;
import com.hellonms.platforms.emp_core.server.workflow.WorkflowMap;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_onion.server.driver.mybatis.Driver4MybatisIf;
import com.hellonms.platforms.emp_onion.share.model.environment.preference.Model4Preference;
import com.hellonms.platforms.emp_onion.share.model.environment.preference.PREFERENCE_CODE;
import com.hellonms.platforms.emp_orange.share.error.ERROR_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;
import com.hellonms.platforms.emp_util.cache.UtilCache;

/**
 * <p>
 * Environment dao
 * </p>
 *
 * @since 1.6
 * @create 2015. 6. 11.
 * @modified 2015. 6. 11.
 * @author cchyun
 *
 */
public class Dao4Preference implements Dao4PreferenceIf {

	protected Driver4MybatisIf driver4Mybatis;

	private static String CACHE_KEY = Dao4Preference.class.getName();
	static {
		UtilCache.buildCache(CACHE_KEY, 256, 300);
	}

	private Map<String, PREFERENCE_CODE[]> preference_filter_map = new HashMap<String, PREFERENCE_CODE[]>();

	@SuppressWarnings("unused")
	private static final BlackBox blackBox = new BlackBox(Dao4Preference.class);

	@Override
	public Class<? extends DaoIf> getDefine_class() {
		return Dao4PreferenceIf.class;
	}

	@Override
	public void initialize(EmpContext context) throws EmpException {
		driver4Mybatis = (Driver4MybatisIf) WorkflowMap.getDriver(Driver4MybatisIf.class);
	}

	@Override
	public void dispose(EmpContext context) throws EmpException {
	}

	@Override
	public Model4Preference queryPreference(EmpContext context, PREFERENCE_CODE preference_code) throws EmpException {
		Model4Preference preference = (Model4Preference) UtilCache.get(CACHE_KEY, preference_code.getPreference_code());

		if (preference == null) {
			Map<String, Object> parameter = new HashMap<String, Object>();
			parameter.put("preference_code", preference_code);

			preference = (Model4Preference) driver4Mybatis.selectOne(context, getDefine_class(), "queryPreference", parameter);
			if (preference == null) {
				throw new EmpException(ERROR_CODE_ORANGE.MODEL_NOTEXISTS, MESSAGE_CODE_ORANGE.PROPERTY, preference_code);
			}
			UtilCache.put(CACHE_KEY, preference_code.getPreference_code(), preference);
		}

		return preference.copy();
	}

	@Override
	public Model4Preference[] queryListPreference(EmpContext context, String function_group, String function, String preference) throws EmpException {
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("preference_codes", getPreference_codes(function_group, function, preference));

		List<?> list = driver4Mybatis.selectList(context, getDefine_class(), "queryListPreference", parameter);

		return list.toArray(new Model4Preference[0]);
	}

	@Override
	public Model4Preference updatePreference(EmpContext context, Model4Preference preference) throws EmpException {
		PREFERENCE_CODE preference_code = preference.getPreference_code();

		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("preference_code", preference_code);
		Model4Preference preference_exists = (Model4Preference) driver4Mybatis.selectOne(context, getDefine_class(), "queryPreference", parameter);
		if (preference_exists == null) {
			throw new EmpException(ERROR_CODE_ORANGE.MODEL_NOTEXISTS, MESSAGE_CODE_ORANGE.PROPERTY, preference_code);
		}
		driver4Mybatis.update(context, getDefine_class(), "updatePreference", preference);
		UtilCache.remove(CACHE_KEY, preference_code.getPreference_code());

		return queryPreference(context, preference_code);
	}

	@Override
	public Model4Preference[] updateListPreference(EmpContext context, Model4Preference[] preferences) throws EmpException {
		for (int i = 0; i < preferences.length; i++) {
			Map<String, Object> parameter = new HashMap<String, Object>();
			PREFERENCE_CODE preference_code = preferences[i].getPreference_code();
			parameter.put("preference_code", preference_code);

			Model4Preference preference = (Model4Preference) driver4Mybatis.selectOne(context, getDefine_class(), "queryPreference", parameter);
			if (preference == null) {
				throw new EmpException(ERROR_CODE_ORANGE.MODEL_NOTEXISTS, MESSAGE_CODE_ORANGE.PROPERTY, preference_code);
			}

			driver4Mybatis.update(context, getDefine_class(), "updatePreference", preferences[i]);
			UtilCache.remove(CACHE_KEY, preference_code.getPreference_code());
			preferences[i] = queryPreference(context, preference_code);
		}
		return preferences;
	}

	protected PREFERENCE_CODE[] getPreference_codes(String function_group, String function, String preference) {
		if (function_group == null && function == null && preference == null) {
			return null;
		} else if (function_group != null && function == null && preference == null) {
			String key = new StringBuilder().append(function_group).toString();
			PREFERENCE_CODE[] preference_filters = preference_filter_map.get(key);
			if (preference_filters == null) {
				List<PREFERENCE_CODE> preference_list = new ArrayList<PREFERENCE_CODE>();
				for (PREFERENCE_CODE preference_code : PREFERENCE_CODE.values()) {
					if (preference_code.getFunction_group().equals(function_group)) {
						preference_list.add(preference_code);
					}
				}
				preference_filters = preference_list.toArray(new PREFERENCE_CODE[0]);
				preference_filter_map.put(key, preference_filters);
			}
			return preference_filters;
		} else if (function_group != null && function != null && preference == null) {
			String key = new StringBuilder().append(function_group).append(".").append(function).toString();
			PREFERENCE_CODE[] preference_filters = preference_filter_map.get(key);
			if (preference_filters == null) {
				List<PREFERENCE_CODE> preference_list = new ArrayList<PREFERENCE_CODE>();
				for (PREFERENCE_CODE preference_code : PREFERENCE_CODE.values()) {
					if (preference_code.getFunction_group().equals(function_group) && preference_code.getFunction().equals(function)) {
						preference_list.add(preference_code);
					}
				}
				preference_filters = preference_list.toArray(new PREFERENCE_CODE[0]);
				preference_filter_map.put(key, preference_filters);
			}
			return preference_filters;
		} else if (function_group != null && function != null && preference != null) {
			String key = new StringBuilder().append(function_group).append(".").append(function).append(".").append(preference).toString();
			PREFERENCE_CODE[] preference_filters = preference_filter_map.get(key);
			if (preference_filters == null) {
				List<PREFERENCE_CODE> preference_list = new ArrayList<PREFERENCE_CODE>();
				for (PREFERENCE_CODE preference_code : PREFERENCE_CODE.values()) {
					if (preference_code.getFunction_group().equals(function_group) && preference_code.getFunction().equals(function) && preference_code.getPreference().equals(preference)) {
						preference_list.add(preference_code);
					}
				}
				preference_filters = preference_list.toArray(new PREFERENCE_CODE[0]);
				preference_filter_map.put(key, preference_filters);
			}
			return preference_filters;
		}
		return null;
	}

}
