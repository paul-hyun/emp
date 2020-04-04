/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.server.workflow.fault.event;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_EVENT;
import com.hellonms.platforms.emp_orange.share.model.fault.event.Model4Alarm;
import com.hellonms.platforms.emp_orange.share.model.fault.event.Model4AlarmStatistics;
import com.hellonms.platforms.emp_orange.share.model.fault.event.Model4AlarmStatistics.ITEM;
import com.hellonms.platforms.emp_orange.share.model.fault.event.SEVERITY;
import com.hellonms.platforms.emp_util.date.UtilDate;
import com.hellonms.platforms.emp_util.string.UtilString;

/**
 * <p>
 * Insert description of Dao4AlarmStatistics.java
 * </p>
 *
 * @since 1.6
 * @create 2015. 4. 22.
 * @modified 2015. 4. 22.
 * @author cchyun
 *
 */
public class Dao4AlarmStatistics implements Dao4AlarmStatisticsIf {

	public static class AlarmStatisticsValue {

		public int ne_id;

		public int event_code;

		public SEVERITY severity;

		public int alarm_count;

		public Date collect_time;

		public int rop_num;

	}

	protected Driver4MybatisIf driver4Mybatis;

	@SuppressWarnings("unused")
	private static final BlackBox blackBox = new BlackBox(Dao4AlarmStatistics.class);

	@Override
	public Class<? extends DaoIf> getDefine_class() {
		return Dao4AlarmStatisticsIf.class;
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
	public int syncListAlarmStatistics(EmpContext context, STATISTICS_TYPE from_type, STATISTICS_TYPE to_type, Date collect_time) throws EmpException {
		Date fromTime = collect_time;
		Date toTime = collect_time;
		switch (to_type) {
		case MINUTE_5:
		case MINUTE_15:
		case MINUTE_30:
			throw new EmpException(ERROR_CODE_ORANGE.MODEL_NOTEXISTS, MESSAGE_CODE_ORANGE.ALARM_STATISTICS_TYPE, from_type, to_type);
		case HOUR_1:
			fromTime = UtilDate.startHour(collect_time);
			toTime = UtilDate.endHour(collect_time);
			break;
		case DAY_1:
			fromTime = UtilDate.startDay(collect_time);
			toTime = UtilDate.endDay(collect_time);
			break;
		case MONTH_1:
			fromTime = UtilDate.startMonth(collect_time);
			toTime = UtilDate.endMonth(collect_time);
			break;
		default:
			throw new EmpException(ERROR_CODE_ORANGE.MODEL_NOTEXISTS, MESSAGE_CODE_ORANGE.ALARM_STATISTICS_TYPE, from_type, to_type);
		}

		Map<String, AlarmStatisticsValue> to_statistics_map = new LinkedHashMap<String, AlarmStatisticsValue>();
		if (to_type == STATISTICS_TYPE.HOUR_1) {
			Map<String, Object> parameter = new HashMap<String, Object>();
			parameter.put("statistics_type", from_type);
			parameter.put("partition_type", UtilPartition.getPartition_type(STATISTICS_TYPE.HOUR_1));
			parameter.put("partition_index", UtilPartition.getPartition_index(STATISTICS_TYPE.HOUR_1, collect_time));
			parameter.put("fromTime", fromTime);
			parameter.put("toTime", toTime);

			List<?> db_list = driver4Mybatis.selectList(context, getDefine_class(), "queryListAlarm", parameter);
			for (Object object : db_list) {
				Model4Alarm src = (Model4Alarm) object;
				String key = UtilString.format("{}.{}.{}", src.getNe_id(), src.getEvent_code(), src.getSeverity().ordinal());
				AlarmStatisticsValue statValue = to_statistics_map.get(key);
				if (statValue == null) {
					statValue = new AlarmStatisticsValue();
					statValue.ne_id = src.getNe_id();
					statValue.event_code = src.getEvent_code();
					statValue.severity = src.getSeverity();
					statValue.collect_time = collect_time;
					statValue.rop_num = 1;
					to_statistics_map.put(key, statValue);
				}
				statValue.alarm_count += 1;
			}
		} else {
			Map<String, Object> parameter = new HashMap<String, Object>();
			parameter.put("statistics_type", from_type);
			parameter.put("partition_type", UtilPartition.getPartition_type(from_type));
			parameter.put("partition_index", UtilPartition.getPartition_index(from_type, collect_time));
			parameter.put("fromTime", fromTime);
			parameter.put("toTime", toTime);

			List<?> db_list = driver4Mybatis.selectList(context, getDefine_class(), "queryListAlarmStatistics", parameter);
			for (Object object : db_list) {
				AlarmStatisticsValue src = (AlarmStatisticsValue) object;
				String key = UtilString.format("{}.{}.{}.{}", src.ne_id, src.event_code, src.severity.ordinal());
				AlarmStatisticsValue statValue = to_statistics_map.get(key);
				if (statValue == null) {
					statValue = new AlarmStatisticsValue();
					statValue.ne_id = src.ne_id;
					statValue.event_code = src.event_code;
					statValue.severity = src.severity;
					statValue.collect_time = collect_time;
					statValue.rop_num = 0;
					to_statistics_map.put(key, statValue);
				}
				statValue.alarm_count += src.alarm_count;
				statValue.rop_num += src.rop_num;
			}
		}
		if (0 < to_statistics_map.size()) {
			Map<String, Object> parameter = new HashMap<String, Object>();
			parameter.put("partition_type", UtilPartition.getPartition_type(to_type));
			parameter.put("partition_index", UtilPartition.getPartition_index(to_type, collect_time));
			parameter.put("row_list", to_statistics_map.values());
			driver4Mybatis.insert(context, getDefine_class(), "createListAlarmStatistics", parameter);
		}
		return to_statistics_map.size();
	}

	@Override
	public Model4AlarmStatistics[] queryListAlarmStatisticsByNeGroup(EmpContext context, int ne_group_id, ITEM item, STATISTICS_TYPE statistics_type, Date fromTime, Date toTime) throws EmpException {
		Map<String, Object> parameter = new HashMap<String, Object>();
		if (context.getUser_id() != 0) {
			parameter.put("user_id", context.getUser_id());
		}
		parameter.put("ne_group_id", ne_group_id);
		parameter.put("item", item);
		parameter.put("partition_type", UtilPartition.getPartition_type(statistics_type));
		parameter.put("statistics_type", statistics_type);
		parameter.put("fromTime", fromTime);
		parameter.put("toTime", toTime);

		List<?> list = driver4Mybatis.selectList(context, getDefine_class(), "queryListAlarmStatisticsByNeGroup", parameter);

		if (item == ITEM.CAUSE) {
			return toAlarmStatisticsCAUSE(context, list.toArray(new AlarmStatisticsValue[0]), getStatistics_item_cause());
		} else if (item == ITEM.SEVERITY) {
			return toAlarmStatisticsSEVERITY(context, list.toArray(new AlarmStatisticsValue[0]), getStatistics_item_severity());
		} else {
			throw new RuntimeException(UtilString.format("Unknown alarm statistics item {}", item));
		}
	}

	@Override
	public Model4AlarmStatistics[] queryListAlarmStatisticsByNe(EmpContext context, int ne_id, ITEM item, STATISTICS_TYPE statistics_type, Date fromTime, Date toTime) throws EmpException {
		Map<String, Object> parameter = new HashMap<String, Object>();
		if (context.getUser_id() != 0) {
			parameter.put("user_id", context.getUser_id());
		}
		parameter.put("ne_id", ne_id);
		parameter.put("item", item);
		parameter.put("statistics_type", statistics_type);
		parameter.put("partition_type", UtilPartition.getPartition_type(statistics_type));
		parameter.put("fromTime", fromTime);
		parameter.put("toTime", toTime);

		List<?> list = driver4Mybatis.selectList(context, getDefine_class(), "queryListAlarmStatisticsByNe", parameter);

		if (item == ITEM.CAUSE) {
			return toAlarmStatisticsCAUSE(context, list.toArray(new AlarmStatisticsValue[0]), getStatistics_item_cause());
		} else if (item == ITEM.SEVERITY) {
			return toAlarmStatisticsSEVERITY(context, list.toArray(new AlarmStatisticsValue[0]), getStatistics_item_severity());
		} else {
			throw new RuntimeException(UtilString.format("Unknown alarm statistics item {}", item));
		}
	}

	protected SEVERITY[] getStatistics_item_severity() {
		List<SEVERITY> itemList = new ArrayList<SEVERITY>();
		itemList.add(SEVERITY.COMMUNICATION_FAIL);
		itemList.add(SEVERITY.CRITICAL);
		itemList.add(SEVERITY.MAJOR);
		itemList.add(SEVERITY.MINOR);
		return itemList.toArray(new SEVERITY[0]);
	}

	protected EMP_MODEL_EVENT[] getStatistics_item_cause() {
		List<EMP_MODEL_EVENT> itemList = new ArrayList<EMP_MODEL_EVENT>();
		for (EMP_MODEL_EVENT event_def : EMP_MODEL.current().getEvents()) {
			if (event_def.isAlarm()) {
				itemList.add(event_def);
			}
		}
		return itemList.toArray(new EMP_MODEL_EVENT[0]);
	}

	protected Model4AlarmStatistics[] toAlarmStatisticsCAUSE(EmpContext context, AlarmStatisticsValue[] list, EMP_MODEL_EVENT[] items) {
		Map<Date, Model4AlarmStatistics> statisticsMap = new TreeMap<Date, Model4AlarmStatistics>();

		for (AlarmStatisticsValue row : list) {
			Model4AlarmStatistics alarmStatistics = statisticsMap.get(row.collect_time);
			if (alarmStatistics == null) {
				alarmStatistics = new Model4AlarmStatistics();
				alarmStatistics.setCollect_time(row.collect_time);
				alarmStatistics.initializeItems(items);
				statisticsMap.put(row.collect_time, alarmStatistics);
			}
			EMP_MODEL_EVENT event_def = EMP_MODEL.current().getEvent(row.event_code);
			if (event_def != null) {
				alarmStatistics.setItemValue(event_def.getSpecific_problem(), row.alarm_count);
			}
		}

		return statisticsMap.values().toArray(new Model4AlarmStatistics[0]);
	}

	protected Model4AlarmStatistics[] toAlarmStatisticsSEVERITY(EmpContext context, AlarmStatisticsValue[] list, SEVERITY[] items) {
		Map<Date, Model4AlarmStatistics> statisticsMap = new TreeMap<Date, Model4AlarmStatistics>();

		for (AlarmStatisticsValue row : list) {
			Model4AlarmStatistics alarmStatistics = statisticsMap.get(row.collect_time);
			if (alarmStatistics == null) {
				alarmStatistics = new Model4AlarmStatistics();
				alarmStatistics.setCollect_time(row.collect_time);
				alarmStatistics.initializeItems(items);
				statisticsMap.put(row.collect_time, alarmStatistics);
			}
			alarmStatistics.setItemValue(row.severity.toString(), row.alarm_count);
		}

		return statisticsMap.values().toArray(new Model4AlarmStatistics[0]);
	}

	@Override
	public String[][] prepareListPartition(EmpContext context, Date collect_time) throws EmpException {
		List<String> partition_create_list = new ArrayList<String>();
		List<String> partition_drop_list = new ArrayList<String>();

		STATISTICS_TYPE[] statistics_types = { STATISTICS_TYPE.HOUR_1, STATISTICS_TYPE.DAY_1, STATISTICS_TYPE.MONTH_1 };
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
					partition_create_list.add(UtilString.format("ALARM_STATISTICS.{}.{}", statistics_type, partition_index));
				}
			}

			for (String partition_index : partition_as_is) {
				if (!partition_to_be.contains(partition_index)) {
					parameter.clear();
					parameter.put("partition_type", UtilPartition.getPartition_type(statistics_type));
					parameter.put("partition_index", partition_index);
					driver4Mybatis.insert(context, getDefine_class(), "dropPartition", parameter);
					partition_drop_list.add(UtilString.format("ALARM_STATISTICS.{}.{}", statistics_type, partition_index));
				}
			}
		}

		return new String[][] { partition_create_list.toArray(new String[0]), partition_drop_list.toArray(new String[0]) };
	}

	@Override
	public void truncate(EmpContext context) throws EmpException {
		STATISTICS_TYPE[] statistics_types = { STATISTICS_TYPE.MONTH_1, STATISTICS_TYPE.DAY_1, STATISTICS_TYPE.HOUR_1 };
		for (STATISTICS_TYPE statistics_type : statistics_types) {
			if (!"MINUTE".equals(UtilPartition.getPartition_type(statistics_type))) {
				Map<String, Object> parameter = new HashMap<String, Object>();
				parameter.put("statistics_type", statistics_type);
				parameter.put("partition_type", UtilPartition.getPartition_type(statistics_type));
				@SuppressWarnings("unused")
				int count = (Integer) driver4Mybatis.delete(context, getDefine_class(), "truncate", parameter);
			}
		}

		prepareListPartition(context, new Date());
	}
}
