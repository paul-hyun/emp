/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.server.workflow.fault.event;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.hellonms.platforms.emp_core.server.log.BlackBox;
import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.server.workflow.DaoIf;
import com.hellonms.platforms.emp_core.server.workflow.WorkflowMap;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_onion.server.driver.mybatis.Driver4MybatisIf;
import com.hellonms.platforms.emp_onion.server.util.UtilPartition;
import com.hellonms.platforms.emp_onion.share.model.STATISTICS_TYPE;
import com.hellonms.platforms.emp_orange.share.error.ERROR_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.fault.event.GEN_TYPE;
import com.hellonms.platforms.emp_orange.share.model.fault.event.Model4Alarm;
import com.hellonms.platforms.emp_orange.share.model.fault.event.Model4AlarmSummary;
import com.hellonms.platforms.emp_orange.share.model.fault.event.SEVERITY;
import com.hellonms.platforms.emp_util.cache.UtilCache;
import com.hellonms.platforms.emp_util.string.UtilString;

/**
 * <p>
 * 알람 DAO
 * </p>
 *
 * @since 1.6
 * @create 2015. 4. 22.
 * @modified 2015. 4. 22.
 * @author cchyun
 *
 */
public class Dao4Alarm implements Dao4AlarmIf {

	protected Driver4MybatisIf driver4Mybatis;

	private static String CACHE_ACTIVE_KEY = UtilString.format("{}.{}", Dao4Alarm.class.getName(), "Active");
	static {
		UtilCache.buildCache(CACHE_ACTIVE_KEY, 256, 300);
	}

	@SuppressWarnings("unused")
	private static final BlackBox blackBox = new BlackBox(Dao4Alarm.class);

	@Override
	public Class<? extends DaoIf> getDefine_class() {
		return Dao4AlarmIf.class;
	}

	@Override
	public void initialize(EmpContext context) throws EmpException {
		driver4Mybatis = (Driver4MybatisIf) WorkflowMap.getDriver(Driver4MybatisIf.class);
		prepareListPartition(context, new Date());
	}

	@Override
	public void dispose(EmpContext context) throws EmpException {
	}

	@Override
	public Model4Alarm createAlarm(EmpContext context, Model4Alarm alarm_active) throws EmpException {
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("model", alarm_active);
		parameter.put("partition_index", UtilPartition.getPartition_index(alarm_active.getGen_first_time()));

		driver4Mybatis.insert(context, getDefine_class(), "createAlarm", parameter);
		driver4Mybatis.insert(context, getDefine_class(), "createAlarmActive", parameter);

		clearCache();
		return queryAlarmActive(context, alarm_active.getNe_id(), alarm_active.getNe_info_code(), alarm_active.getNe_info_index(), alarm_active.getEvent_code());
	}

	@Override
	public Model4Alarm queryAlarm(EmpContext context, long gen_first_event_id) throws EmpException {
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("gen_first_event_id", gen_first_event_id);

		Model4Alarm alarm = (Model4Alarm) driver4Mybatis.selectOne(context, getDefine_class(), "queryAlarm", parameter);
		if (alarm == null) {
			throw new EmpException(ERROR_CODE_ORANGE.MODEL_NOTEXISTS, MESSAGE_CODE_ORANGE.ALARM, gen_first_event_id);
		}

		return alarm;
	}

	@Override
	public Model4Alarm[] queryListAlarmByNeGroup(EmpContext context, int ne_group_id, int event_code, SEVERITY severity, Date fromTime, Date toTime, int startNo, int count) throws EmpException {
		Map<String, Object> parameter = new HashMap<String, Object>();
		if (context.getUser_id() != 0) {
			parameter.put("user_id", context.getUser_id());
		}
		parameter.put("ne_group_id", ne_group_id);
		if (event_code != 0) {
			parameter.put("event_code", event_code);
		}
		parameter.put("severity", severity);
		parameter.put("fromTime", fromTime);
		parameter.put("toTime", toTime);
		parameter.put("startNo", startNo);
		parameter.put("count", count);

		List<?> list = driver4Mybatis.selectList(context, getDefine_class(), "queryListAlarmByNeGroup", parameter);

		return list.toArray(new Model4Alarm[0]);
	}

	@Override
	public Model4Alarm[] queryListAlarmByConsole(EmpContext context, int startNo, int count) throws EmpException {
		Map<String, Object> parameter = new HashMap<String, Object>();
		if (context.getUser_id() != 0) {
			parameter.put("user_id", context.getUser_id());
		}
		parameter.put("startNo", startNo);
		parameter.put("count", count);

		List<?> list = driver4Mybatis.selectList(context, getDefine_class(), "queryListAlarmByConsole", parameter);

		return list.toArray(new Model4Alarm[0]);
	}

	@Override
	public Model4Alarm[] queryListAlarmByNe(EmpContext context, int ne_id, int event_code, SEVERITY severity, Date fromTime, Date toTime, int startNo, int count) throws EmpException {
		Map<String, Object> parameter = new HashMap<String, Object>();
		if (context.getUser_id() != 0) {
			parameter.put("user_id", context.getUser_id());
		}
		parameter.put("ne_id", ne_id);
		if (event_code != 0) {
			parameter.put("event_code", event_code);
		}
		parameter.put("severity", severity);
		parameter.put("fromTime", fromTime);
		parameter.put("toTime", toTime);
		parameter.put("startNo", startNo);
		parameter.put("count", count);

		List<?> list = driver4Mybatis.selectList(context, getDefine_class(), "queryListAlarmByNe", parameter);

		return list.toArray(new Model4Alarm[0]);
	}

	@Override
	public int queryCountAlarmByNeGroup(EmpContext context, int ne_group_id, int event_code, SEVERITY severity, Date fromTime, Date toTime) throws EmpException {
		Map<String, Object> parameter = new HashMap<String, Object>();
		if (context.getUser_id() != 0) {
			parameter.put("user_id", context.getUser_id());
		}
		parameter.put("ne_group_id", ne_group_id);
		if (event_code != 0) {
			parameter.put("event_code", event_code);
		}
		parameter.put("severity", severity);
		parameter.put("fromTime", fromTime);
		parameter.put("toTime", toTime);

		int count = (Integer) driver4Mybatis.selectOne(context, getDefine_class(), "queryCountAlarmByNeGroup", parameter);

		return count;
	}

	@Override
	public int queryCountAlarmByNe(EmpContext context, int ne_id, int event_code, SEVERITY severity, Date fromTime, Date toTime) throws EmpException {
		Map<String, Object> parameter = new HashMap<String, Object>();
		if (context.getUser_id() != 0) {
			parameter.put("user_id", context.getUser_id());
		}
		parameter.put("ne_id", ne_id);
		if (event_code != 0) {
			parameter.put("event_code", event_code);
		}
		parameter.put("severity", severity);
		parameter.put("fromTime", fromTime);
		parameter.put("toTime", toTime);

		int count = (Integer) driver4Mybatis.selectOne(context, getDefine_class(), "queryCountAlarmByNe", parameter);

		return count;
	}

	@Override
	public Model4Alarm queryAlarmActive(EmpContext context, long gen_first_event_id) throws EmpException {
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("gen_first_event_id", gen_first_event_id);

		Model4Alarm alarm_active = (Model4Alarm) driver4Mybatis.selectOne(context, getDefine_class(), "queryAlarmActive", parameter);
		if (alarm_active == null) {
			throw new EmpException(ERROR_CODE_ORANGE.MODEL_NOTEXISTS, MESSAGE_CODE_ORANGE.ALARM_ACTIVE, gen_first_event_id);
		}
		UtilCache.put(CACHE_ACTIVE_KEY, getCache_key(alarm_active.getNe_id(), alarm_active.getNe_info_code(), alarm_active.getNe_info_index(), alarm_active.getEvent_code()), alarm_active);

		return alarm_active;
	}

	@Override
	public Model4Alarm queryAlarmActive(EmpContext context, int ne_id, int ne_info_code, int ne_info_index, int event_code) throws EmpException {
		String cache_key = getCache_key(ne_id, ne_info_code, ne_info_index, event_code);
		Model4Alarm alarm_active = (Model4Alarm) UtilCache.get(CACHE_ACTIVE_KEY, cache_key);

		if (alarm_active == null) {
			Map<String, Object> parameter = new HashMap<String, Object>();
			parameter.put("ne_id", ne_id);
			parameter.put("ne_info_code", ne_info_code);
			parameter.put("ne_info_index", ne_info_index);
			parameter.put("event_code", event_code);

			alarm_active = (Model4Alarm) driver4Mybatis.selectOne(context, getDefine_class(), "queryAlarmActive", parameter);
			if (alarm_active == null) {
				throw new EmpException(ERROR_CODE_ORANGE.MODEL_NOTEXISTS, MESSAGE_CODE_ORANGE.ALARM_ACTIVE, ne_id, ne_info_code, ne_info_index, event_code);
			}
			UtilCache.put(CACHE_ACTIVE_KEY, cache_key, alarm_active);
		}
		return alarm_active.copy();
	}

	@Override
	public Model4Alarm[] queryListAlarmActiveByNeGroup(EmpContext context, int ne_group_id, int event_code, SEVERITY severity, int startNo, int count) throws EmpException {
		Map<String, Object> parameter = new HashMap<String, Object>();
		if (context.getUser_id() != 0) {
			parameter.put("user_id", context.getUser_id());
		}
		parameter.put("ne_group_id", ne_group_id);
		if (event_code != 0) {
			parameter.put("event_code", event_code);
		}
		parameter.put("severity", severity);
		parameter.put("startNo", startNo);
		parameter.put("count", count);

		List<?> list = driver4Mybatis.selectList(context, getDefine_class(), "queryListAlarmActiveByNeGroup", parameter);

		return list.toArray(new Model4Alarm[0]);
	}

	@Override
	public Model4Alarm[] queryListAlarmActiveByNe(EmpContext context, int ne_id, int ne_info_code, int event_code, SEVERITY severity, int startNo, int count) throws EmpException {
		Map<String, Object> parameter = new HashMap<String, Object>();
		if (context.getUser_id() != 0) {
			parameter.put("user_id", context.getUser_id());
		}
		parameter.put("ne_id", ne_id);
		if (ne_info_code != 0) {
			parameter.put("ne_info_code", ne_info_code);
		}
		if (event_code != 0) {
			parameter.put("event_code", event_code);
		}
		parameter.put("severity", severity);
		parameter.put("startNo", startNo);
		parameter.put("count", count);

		List<?> list = driver4Mybatis.selectList(context, getDefine_class(), "queryListAlarmActiveByNe", parameter);

		return list.toArray(new Model4Alarm[0]);
	}

	@Override
	public int queryCountAlarmActiveByNeGroup(EmpContext context, int ne_group_id, int event_code, SEVERITY severity) throws EmpException {
		Map<String, Object> parameter = new HashMap<String, Object>();
		if (context.getUser_id() != 0) {
			parameter.put("user_id", context.getUser_id());
		}
		parameter.put("ne_group_id", ne_group_id);
		if (event_code != 0) {
			parameter.put("event_code", event_code);
		}
		parameter.put("severity", severity);

		int count = (Integer) driver4Mybatis.selectOne(context, getDefine_class(), "queryCountAlarmActiveByNeGroup", parameter);

		return count;
	}

	@Override
	public int queryCountAlarmActiveByNe(EmpContext context, int ne_id, int event_code, SEVERITY severity) throws EmpException {
		Map<String, Object> parameter = new HashMap<String, Object>();
		if (context.getUser_id() != 0) {
			parameter.put("user_id", context.getUser_id());
		}
		parameter.put("ne_id", ne_id);
		if (event_code != 0) {
			parameter.put("event_code", event_code);
		}
		parameter.put("severity", severity);

		int count = (Integer) driver4Mybatis.selectOne(context, getDefine_class(), "queryCountAlarmActiveByNe", parameter);

		return count;
	}

	@Override
	public Model4AlarmSummary[] queryListAlarmSummary(EmpContext context) throws EmpException {
		Map<String, Object> parameter = new HashMap<String, Object>();

		List<?> list = driver4Mybatis.selectList(context, getDefine_class(), "queryListAlarmActiveBySummary", parameter);
		@SuppressWarnings("unchecked")
		Model4AlarmSummary[] alarmSymmarys = toAlarmSummarys(context, (List<Model4Alarm>) list);
		return alarmSymmarys;
	}

	protected Model4AlarmSummary[] toAlarmSummarys(EmpContext context, List<Model4Alarm> alarms) {
		Map<Integer, Model4AlarmSummary> alarm_summary_map = new TreeMap<Integer, Model4AlarmSummary>();
		for (Model4Alarm alarm : alarms) {
			Model4AlarmSummary alarmSymmary = alarm_summary_map.get(alarm.getNe_id());
			if (alarmSymmary == null) {
				alarmSymmary = new Model4AlarmSummary();
				alarmSymmary.setNe_id(alarm.getNe_id());
				alarm_summary_map.put(alarm.getNe_id(), alarmSymmary);
			}
			switch (alarm.getSeverity()) {
			case COMMUNICATION_FAIL:
				alarmSymmary.setCommunication_fail_count(alarmSymmary.getCommunication_fail_count() + 1);
				if (!alarm.isAck_state()) {
					alarmSymmary.setCommunication_fail_unack_count(alarmSymmary.getCommunication_fail_unack_count() + 1);
				}
				break;
			case CRITICAL:
				alarmSymmary.setCritical_count(alarmSymmary.getCritical_count() + 1);
				if (!alarm.isAck_state()) {
					alarmSymmary.setCritical_unack_count(alarmSymmary.getCritical_unack_count() + 1);
				}
				break;
			case MAJOR:
				alarmSymmary.setMajor_count(alarmSymmary.getMajor_count() + 1);
				if (!alarm.isAck_state()) {
					alarmSymmary.setMajor_unack_count(alarmSymmary.getMajor_unack_count() + 1);
				}
				break;
			case MINOR:
				alarmSymmary.setMinor_count(alarmSymmary.getMinor_count() + 1);
				if (!alarm.isAck_state()) {
					alarmSymmary.setMinor_unack_count(alarmSymmary.getMinor_unack_count() + 1);
				}
				break;
			default:
				break;
			}
		}
		return alarm_summary_map.values().toArray(new Model4AlarmSummary[0]);
	}

	@Override
	public Model4Alarm updateAlarmByAck(EmpContext context, long gen_first_event_id) throws EmpException {
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("gen_first_event_id", gen_first_event_id);
		Model4Alarm alarm_active = (Model4Alarm) driver4Mybatis.selectOne(context, getDefine_class(), "queryAlarmActive", parameter);
		if (alarm_active == null) {
			throw new EmpException(ERROR_CODE_ORANGE.MODEL_NOTEXISTS, MESSAGE_CODE_ORANGE.ALARM_ACTIVE, gen_first_event_id);
		}

		alarm_active.setAck_user(context.getUser_account());
		alarm_active.setAck_time(new Date());
		alarm_active.setAck_state(true);

		driver4Mybatis.update(context, getDefine_class(), "updateAlarmByAck", alarm_active);
		driver4Mybatis.update(context, getDefine_class(), "updateAlarmActiveByAck", alarm_active);

		clearCache();
		return queryAlarmActive(context, alarm_active.getNe_id(), alarm_active.getNe_info_code(), alarm_active.getNe_info_index(), alarm_active.getEvent_code());
	}

	@Override
	public Model4Alarm[] updateAlarmByClearNe_info_index(EmpContext context, int ne_id, int ne_info_code, int ne_info_index, GEN_TYPE clear_type, String clear_description) throws EmpException {
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("ne_id", ne_id);
		parameter.put("ne_info_code", ne_info_code);
		parameter.put("ne_info_index", ne_info_index);
		parameter.put("startNo", 0);
		parameter.put("count", 999999);

		List<?> list = driver4Mybatis.selectList(context, getDefine_class(), "queryListAlarmActiveByNe", parameter);
		Model4Alarm[] alarm_clears = list.toArray(new Model4Alarm[0]);

		for (Model4Alarm alarm_clear : alarm_clears) {
			alarm_clear.setClear_type(clear_type);
			alarm_clear.setClear_description(clear_description);

			driver4Mybatis.update(context, getDefine_class(), "updateAlarmByClear", alarm_clear);
			driver4Mybatis.delete(context, getDefine_class(), "deleteAlarmActive", alarm_clear);
		}

		clearCache();
		return alarm_clears;
	}

	@Override
	public Model4Alarm updateAlarmByClear(EmpContext context, Model4Alarm alarm_active) throws EmpException {
		driver4Mybatis.update(context, getDefine_class(), "updateAlarmByClear", alarm_active);
		driver4Mybatis.delete(context, getDefine_class(), "deleteAlarmActive", alarm_active);

		clearCache();

		return alarm_active;
	}

	@Override
	public Model4Alarm updateAlarmByRepetition(EmpContext context, Model4Alarm alarm_active) throws EmpException {
		driver4Mybatis.update(context, getDefine_class(), "updateAlarmByRepetition", alarm_active);
		driver4Mybatis.delete(context, getDefine_class(), "updateAlarmActiveByRepetition", alarm_active);

		clearCache();
		return queryAlarmActive(context, alarm_active.getNe_id(), alarm_active.getNe_info_code(), alarm_active.getNe_info_index(), alarm_active.getEvent_code());
	}

	@Override
	public Model4Alarm updateAlarmBySeverity(EmpContext context, Model4Alarm alarm_clear, Model4Alarm alarm_active) throws EmpException {
		// 기존알람 복구
		alarm_clear.setClear_description("severity change");

		driver4Mybatis.update(context, getDefine_class(), "updateAlarmByClear", alarm_clear);
		driver4Mybatis.delete(context, getDefine_class(), "deleteAlarmActive", alarm_clear);

		// 새로운 알람 생성
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("model", alarm_active);
		parameter.put("partition_index", UtilPartition.getPartition_index(alarm_active.getGen_first_time()));

		driver4Mybatis.insert(context, getDefine_class(), "createAlarm", parameter);
		driver4Mybatis.insert(context, getDefine_class(), "createAlarmActive", parameter);

		clearCache();
		return queryAlarmActive(context, alarm_active.getNe_id(), alarm_active.getNe_info_code(), alarm_active.getNe_info_index(), alarm_active.getEvent_code());
	}

	protected String getCache_key(int ne_id, int ne_info_code, int ne_info_index, int event_code) {
		return new StringBuilder().append(ne_id).append('.').append(ne_info_code).append('.').append(ne_info_index).append('.').append(event_code).toString();
	}

	protected void clearCache() {
		UtilCache.removeAll(CACHE_ACTIVE_KEY);
	}

	@Override
	public String[][] prepareListPartition(EmpContext context, Date collect_time) throws EmpException {
		List<String> partition_create_list = new ArrayList<String>();
		List<String> partition_drop_list = new ArrayList<String>();

		STATISTICS_TYPE[] statistics_types = { STATISTICS_TYPE.HOUR_1 };
		for (STATISTICS_TYPE statistics_type : statistics_types) {
			Set<String> partition_as_is = new LinkedHashSet<String>();
			Set<String> partition_to_be = new LinkedHashSet<String>();

			Map<String, Object> parameter = new HashMap<String, Object>();
			parameter.put("partition_type", UtilPartition.getPartition_type(statistics_type));
			List<?> list = driver4Mybatis.selectList(context, getDefine_class(), "queryListPartition", parameter);
			for (Object object : list) {
				partition_as_is.add((String) object);
			}

			String[] partition_indexs = UtilPartition.getPartition_indexs(collect_time, statistics_type);
			for (String partition_index : partition_indexs) {
				partition_to_be.add(partition_index);
			}

			for (String partition_index : partition_to_be) {
				if (!partition_as_is.contains(partition_index)) {
					parameter.clear();
					parameter.put("partition_type", UtilPartition.getPartition_type(statistics_type));
					parameter.put("partition_index", partition_index);
					parameter.put("fromTime", UtilPartition.getPartition_from_time(partition_index, statistics_type));
					parameter.put("toTime", UtilPartition.getPartition_to_time(partition_index, statistics_type));
					driver4Mybatis.insert(context, getDefine_class(), "createPartition", parameter);
					partition_create_list.add(UtilString.format("ALARM.{}.{}", statistics_type, partition_index));
				}
			}

			for (String partition_index : partition_as_is) {
				if (!partition_to_be.contains(partition_index)) {
					parameter.clear();
					parameter.put("partition_type", UtilPartition.getPartition_type(statistics_type));
					parameter.put("partition_index", partition_index);
					driver4Mybatis.insert(context, getDefine_class(), "dropPartition", parameter);
					partition_drop_list.add(UtilString.format("ALARM.{}.{}", statistics_type, partition_index));
				}
			}
		}

		return new String[][] { partition_create_list.toArray(new String[0]), partition_drop_list.toArray(new String[0]) };
	}

	@Override
	public void truncate(EmpContext context) throws EmpException {
		Map<String, Object> parameter = new HashMap<String, Object>();

		@SuppressWarnings("unused")
		int count = (Integer) driver4Mybatis.delete(context, getDefine_class(), "truncateAlarmActive", parameter);
		count = (Integer) driver4Mybatis.delete(context, getDefine_class(), "truncateAlarm", parameter);

		prepareListPartition(context, new Date());
	}

}
