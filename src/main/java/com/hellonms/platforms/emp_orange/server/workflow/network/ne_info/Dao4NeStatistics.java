/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.server.workflow.network.ne_info;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.locks.ReentrantLock;

import com.hellonms.platforms.emp_core.server.log.BlackBox;
import com.hellonms.platforms.emp_core.server.log.LEVEL;
import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.server.workflow.DaoIf;
import com.hellonms.platforms.emp_core.server.workflow.WorkflowMap;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_onion.server.driver.mybatis.Driver4MybatisIf;
import com.hellonms.platforms.emp_onion.server.util.UtilPartition;
import com.hellonms.platforms.emp_onion.share.model.STATISTICS_TYPE;
import com.hellonms.platforms.emp_orange.server.util.UtilInternalError;
import com.hellonms.platforms.emp_orange.server.workflow.network.ne.Dao4Ne;
import com.hellonms.platforms.emp_orange.share.error.ERROR_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO_FIELD;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO_FIELD.STATISTICS_AGGREGATION;
import com.hellonms.platforms.emp_orange.share.model.network.ne_info.Model4NeStatistics;
import com.hellonms.platforms.emp_orange.share.model.network.ne_info.Model4NeStatisticsIf;
import com.hellonms.platforms.emp_util.cache.UtilCache;
import com.hellonms.platforms.emp_util.date.UtilDate;
import com.hellonms.platforms.emp_util.http.UtilJson;
import com.hellonms.platforms.emp_util.string.UtilString;

/**
 * <p>
 * NE 통계
 * </p>
 *
 * @since 1.6
 * @create 2015. 4. 10.
 * @modified 2015. 4. 10.
 * @author cchyun
 *
 */
public class Dao4NeStatistics implements Dao4NeStatisticsIf {

	public static class NeStatisticsValue4iBATIS {

		public int ne_id;

		public int ne_info_code;

		public int ne_info_index;

		public Date collect_time;

		public int rop_num;

		public String ne_info_value;

	}

	protected Driver4MybatisIf driver4Mybatis;

	private static String CACHE_FIELD_KEY = UtilString.format("{}.{}", Dao4NeStatistics.class.getName(), "ne_field");
	static {
		UtilCache.buildCache(CACHE_FIELD_KEY, 20480, 3600);
	}

	private static final BlackBox blackBox = new BlackBox(Dao4Ne.class);

	@Override
	public Class<? extends DaoIf> getDefine_class() {
		return Dao4NeStatisticsIf.class;
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
	public Model4NeStatisticsIf[] createListNeStatistics(EmpContext context, EMP_MODEL_NE_INFO ne_info_def, STATISTICS_TYPE statistics_type, Model4NeStatisticsIf[] ne_statisticss) throws EmpException {
		String partition_type = UtilPartition.getPartition_type(statistics_type);

		Map<String, List<NeStatisticsValue4iBATIS>> ne_field_map = new TreeMap<String, List<NeStatisticsValue4iBATIS>>();
		for (Model4NeStatisticsIf ne_statistics : ne_statisticss) {
			String partition_index = UtilPartition.getPartition_index(statistics_type, ne_statistics.getCollect_time());
			List<NeStatisticsValue4iBATIS> row_list = ne_field_map.get(partition_index);
			if (row_list == null) {
				row_list = new ArrayList<NeStatisticsValue4iBATIS>();
				ne_field_map.put(partition_index, row_list);
			}
			row_list.add(toDatabase(context, ne_statistics));
		}

		for (String partition_index : ne_field_map.keySet()) {
			List<NeStatisticsValue4iBATIS> row_list = ne_field_map.get(partition_index);

			Map<String, Object> parameter = new HashMap<String, Object>();
			parameter.put("ne_info_code", ne_info_def.getCode());
			parameter.put("partition_type", partition_type);
			parameter.put("row_list", row_list);
			parameter.put("partition_index", partition_index);
			driver4Mybatis.insert(context, getDefine_class(), "createListNeStatistics", parameter);
		}

		createListNeStatisticsSync(context, ne_info_def, statistics_type, ne_statisticss);

		return ne_statisticss;
	}

	private final ReentrantLock lock = new ReentrantLock();

	private void createListNeStatisticsSync(EmpContext context, EMP_MODEL_NE_INFO ne_info_def, STATISTICS_TYPE from_type, Model4NeStatisticsIf[] ne_statisticss) throws EmpException {
		lock.lock();
		try {
			STATISTICS_TYPE to_type = STATISTICS_TYPE.HOUR_1;
			switch (from_type) {
			case MINUTE_5:
			case MINUTE_15:
			case MINUTE_30:
				to_type = STATISTICS_TYPE.HOUR_1;
				break;
			case HOUR_1:
				to_type = STATISTICS_TYPE.DAY_1;
				break;
			case DAY_1:
				to_type = STATISTICS_TYPE.MONTH_1;
				break;
			default:
				return;
			}

			Set<Date> collect_times = new TreeSet<Date>();
			for (Model4NeStatisticsIf ne_statistics : ne_statisticss) {
				switch (to_type) {
				case HOUR_1:
					collect_times.add(UtilDate.startHour(ne_statistics.getCollect_time()));
					break;
				case DAY_1:
					collect_times.add(UtilDate.startDay(ne_statistics.getCollect_time()));
					break;
				case MONTH_1:
					collect_times.add(UtilDate.startMonth(ne_statistics.getCollect_time()));
					break;
				default:
				}
			}

			if (0 < collect_times.size()) {
				Map<String, Object> parameter = new HashMap<String, Object>();
				parameter.put("statistics_type", to_type);
				parameter.put("ne_info_code", ne_info_def.getCode());
				parameter.put("collect_times", collect_times);
				driver4Mybatis.insert(context, getDefine_class(), "createListNeStatisticsSync", parameter);
			}
		} finally {
			lock.unlock();
		}
	}

	@Override
	public int syncListNeStatistics(EmpContext context, EMP_MODEL_NE_INFO ne_info_def, STATISTICS_TYPE from_type, STATISTICS_TYPE to_type, Date collect_time) throws EmpException {
		switch (from_type) {
		case MINUTE_5:
		case MINUTE_15:
		case MINUTE_30:
			if (to_type != STATISTICS_TYPE.HOUR_1) {
				throw new RuntimeException(UtilString.format("Invalid statistics type {} -> {}", from_type, to_type));
			}
			break;
		case HOUR_1:
			if (to_type != STATISTICS_TYPE.DAY_1) {
				throw new RuntimeException(UtilString.format("Invalid statistics type {} -> {}", from_type, to_type));
			}
			break;
		case DAY_1:
			if (to_type != STATISTICS_TYPE.MONTH_1) {
				throw new RuntimeException(UtilString.format("Invalid statistics type {} -> {}", from_type, to_type));
			}
			break;
		case MONTH_1:
		default:
			throw new RuntimeException(UtilString.format("Invalid statistics type {} -> {}", from_type, to_type));
		}

		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("statistics_type", to_type);
		parameter.put("ne_info_code", ne_info_def.getCode());
		parameter.put("collect_time", collect_time);

		List<?> list = driver4Mybatis.selectList(context, getDefine_class(), "queryListNeStatisticsSync", parameter);

		int count = 0;
		for (Object object : list) {
			Date time = (Date) object;
			count += syncListNeStatisticsEach(context, ne_info_def, from_type, to_type, time);
		}
		return count;
	}

	protected int syncListNeStatisticsEach(EmpContext context, EMP_MODEL_NE_INFO ne_info_def, STATISTICS_TYPE from_type, STATISTICS_TYPE to_type, Date collect_time) throws EmpException {
		Date fromTime = collect_time;
		Date toTime = collect_time;
		switch (to_type) {
		case MINUTE_5:
		case MINUTE_15:
		case MINUTE_30:
			throw new EmpException(ERROR_CODE_ORANGE.MODEL_NOTEXISTS, ne_info_def, from_type, to_type);
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
			throw new EmpException(ERROR_CODE_ORANGE.MODEL_NOTEXISTS, ne_info_def, from_type, to_type);
		}

		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("statistics_type", from_type);
		parameter.put("partition_type", UtilPartition.getPartition_type(from_type));
		parameter.put("ne_info_code", ne_info_def.getCode());
		parameter.put("fromTime", fromTime);
		parameter.put("toTime", toTime);
		parameter.put("partition_index", UtilPartition.getPartition_index(from_type, collect_time));

		List<?> db_list = driver4Mybatis.selectList(context, getDefine_class(), "queryListNeStatisticsByPartition", parameter);
		Map<String, Model4NeStatistics> db_map = toModel(context, ne_info_def, db_list);
		Map<String, Integer> avg_count = new LinkedHashMap<String, Integer>();
		Map<String, Model4NeStatistics> to_statistics_map = new LinkedHashMap<String, Model4NeStatistics>();

		for (Entry<String, Model4NeStatistics> entry : db_map.entrySet()) {
			Model4NeStatistics from_statistics = entry.getValue();
			String key_model = UtilString.format("{}.{}.{}", from_statistics.getNe_id(), ne_info_def.getCode(), from_statistics.getNe_info_index());
			Model4NeStatistics to_statistics = to_statistics_map.get(key_model);
			if (to_statistics == null) {
				to_statistics = EMP_MODEL.current().newInstanceNe_statistics(ne_info_def.getCode());
				to_statistics.setNe_id(from_statistics.getNe_id());
				to_statistics.setNe_info_code(ne_info_def.getCode());
				to_statistics.setNe_info_index(from_statistics.getNe_info_index());
				to_statistics.setCollect_time(collect_time);
				to_statistics_map.put(key_model, to_statistics);
			}
			to_statistics.setRop_num(to_statistics.getRop_num() + 1);

			for (EMP_MODEL_NE_INFO_FIELD ne_info_field_def : ne_info_def.getNe_statistics()) {
				try {
					Long value = from_statistics.getField_value(ne_info_field_def);
					if (value == null) {
						continue;
					}

					switch (ne_info_field_def.getStat_aggr()) {
					case AVG:
						Long value_avg = to_statistics.getField_value(ne_info_field_def);
						if (value_avg == null) {
							value_avg = 0L;
						}
						to_statistics.setField_value(ne_info_field_def, value + value_avg);

						String key_field = UtilString.format("{}.{}.{}.{}", to_statistics.getNe_id(), ne_info_def.getCode(), to_statistics.getNe_info_index(), ne_info_field_def.getName());
						Integer value_avg_count = avg_count.get(key_field);
						if (value_avg_count == null) {
							value_avg_count = 0;
						}
						avg_count.put(key_field, value_avg_count + 1);
						break;
					case SUM:
						Long value_sum = to_statistics.getField_value(ne_info_field_def);
						if (value_sum == null) {
							value_sum = 0L;
						}
						to_statistics.setField_value(ne_info_field_def, value + value_sum);
						break;
					case MIN:
						Long value_min = to_statistics.getField_value(ne_info_field_def);
						if (value_min == null) {
							value_min = value;
						}
						to_statistics.setField_value(ne_info_field_def, Math.min(value, value_min));
						break;
					case MAX:
						Long value_max = to_statistics.getField_value(ne_info_field_def);
						if (value_max == null) {
							value_max = value;
						}
						to_statistics.setField_value(ne_info_field_def, Math.max(value, value_max));
						break;
					}
				} catch (Exception e) {
					if (blackBox.isEnabledFor(LEVEL.Fatal)) {
						blackBox.log(LEVEL.Fatal, context, e);
					}
				}
			}
		}

		for (EMP_MODEL_NE_INFO_FIELD ne_info_field_def : ne_info_def.getNe_statistics()) {
			if (ne_info_field_def.getStat_aggr() == STATISTICS_AGGREGATION.AVG) {
				for (Entry<String, Model4NeStatistics> entry : to_statistics_map.entrySet()) {
					Model4NeStatistics to_statistics = entry.getValue();
					String key_field = UtilString.format("{}.{}.{}.{}", to_statistics.getNe_id(), ne_info_def.getCode(), to_statistics.getNe_info_index(), ne_info_field_def.getName());
					Integer value_avg_count = avg_count.get(key_field);
					Long value_avg = to_statistics.getField_value(ne_info_field_def);
					if (value_avg_count != null && 0 < value_avg_count && value_avg != null) {
						to_statistics.setField_value(ne_info_field_def, value_avg.longValue() / value_avg_count.longValue());
					}
				}
			}
		}

		createListNeStatistics(context, ne_info_def, to_type, to_statistics_map.values().toArray(new Model4NeStatistics[0]));

		parameter.clear();
		parameter.put("statistics_type", to_type);
		parameter.put("ne_info_code", ne_info_def.getCode());
		parameter.put("collect_time", collect_time);
		driver4Mybatis.delete(context, getDefine_class(), "deleteNeStatisticsSync", parameter);

		return to_statistics_map.size();
	}

	@Override
	public int[] queryListNeStatisticsIndex(EmpContext context, int ne_id, EMP_MODEL_NE_INFO ne_info_def, STATISTICS_TYPE statistics_type, Date fromTime, Date toTime) throws EmpException {
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("statistics_type", statistics_type);
		parameter.put("ne_id", ne_id);
		parameter.put("ne_info_code", ne_info_def.getCode());
		parameter.put("partition_type", UtilPartition.getPartition_type(statistics_type));
		parameter.put("fromTime", fromTime);
		parameter.put("toTime", toTime);

		List<?> list = driver4Mybatis.selectList(context, getDefine_class(), "queryListNeStatisticsIndex", parameter);

		int[] ne_info_indexs = new int[list.size()];
		for (int i = 0; i < ne_info_indexs.length; i++) {
			ne_info_indexs[i] = (Integer) list.get(i);
		}

		return ne_info_indexs;
	}

	@Override
	public Model4NeStatisticsIf[] queryListNeStatistics(EmpContext context, int ne_id, EMP_MODEL_NE_INFO ne_info_def, STATISTICS_TYPE statistics_type, Date fromTime, Date toTime) throws EmpException {
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("statistics_type", statistics_type);
		parameter.put("ne_id", ne_id);
		parameter.put("ne_info_code", ne_info_def.getCode());
		parameter.put("partition_type", UtilPartition.getPartition_type(statistics_type));
		parameter.put("fromTime", fromTime);
		parameter.put("toTime", toTime);

		List<?> db_list = driver4Mybatis.selectList(context, getDefine_class(), "queryListNeStatistics", parameter);
		Map<String, Model4NeStatistics> db_map = toModel(context, ne_info_def, db_list);

		return db_map.values().toArray(new Model4NeStatisticsIf[0]);
	}

	@Override
	public Model4NeStatisticsIf[] queryListNeStatistics(EmpContext context, int ne_id, EMP_MODEL_NE_INFO ne_info_def, int ne_info_index, STATISTICS_TYPE statistics_type, Date fromTime, Date toTime) throws EmpException {
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("statistics_type", statistics_type);
		parameter.put("ne_id", ne_id);
		parameter.put("ne_info_code", ne_info_def.getCode());
		parameter.put("ne_info_index", ne_info_index);
		parameter.put("partition_type", UtilPartition.getPartition_type(statistics_type));
		parameter.put("fromTime", fromTime);
		parameter.put("toTime", toTime);

		List<?> db_list = driver4Mybatis.selectList(context, getDefine_class(), "queryListNeStatistics", parameter);
		Map<String, Model4NeStatistics> db_map = toModel(context, ne_info_def, db_list);

		return db_map.values().toArray(new Model4NeStatisticsIf[0]);
	}

	@Override
	public Model4NeStatisticsIf[] queryListNeStatistics(EmpContext context, int ne_id, EMP_MODEL_NE_INFO ne_info_def, STATISTICS_TYPE statistics_type, Date collect_time) throws EmpException {
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("statistics_type", statistics_type);
		parameter.put("partition_index", UtilPartition.getPartition_index(statistics_type, collect_time));
		parameter.put("ne_id", ne_id);
		parameter.put("ne_info_code", ne_info_def.getCode());
		parameter.put("partition_type", UtilPartition.getPartition_type(statistics_type));
		parameter.put("collect_time", collect_time);

		List<?> db_list = driver4Mybatis.selectList(context, getDefine_class(), "queryListNeStatisticsByPartition", parameter);
		Map<String, Model4NeStatistics> db_map = toModel(context, ne_info_def, db_list);

		return db_map.values().toArray(new Model4NeStatisticsIf[0]);
	}

	protected NeStatisticsValue4iBATIS toDatabase(EmpContext context, Model4NeStatisticsIf ne_statistics) throws EmpException {
		NeStatisticsValue4iBATIS value = new NeStatisticsValue4iBATIS();
		value.ne_id = ne_statistics.getNe_id();
		value.ne_info_code = ne_statistics.getNe_info_code();
		value.ne_info_index = ne_statistics.getNe_info_index();
		value.collect_time = ne_statistics.getCollect_time();
		value.rop_num = 1;
		value.ne_info_value = UtilJson.toString(ne_statistics.getMap());
		return value;
	}

	protected Map<String, Model4NeStatistics> toModel(EmpContext context, EMP_MODEL_NE_INFO ne_info_def, List<?> db_list) {
		TreeMap<String, Model4NeStatistics> map = new TreeMap<String, Model4NeStatistics>();
		for (Object db : db_list) {
			NeStatisticsValue4iBATIS vdb_value = (NeStatisticsValue4iBATIS) db;
			try {
				Map<String, Object> mmm = UtilJson.toMap(vdb_value.ne_info_value);

				String key = UtilString.format("{}.{}.{}.{}", UtilDate.format(vdb_value.collect_time), vdb_value.ne_id, ne_info_def.getCode(), vdb_value.ne_info_index);
				Model4NeStatistics ne_statistics = map.get(key);
				if (ne_statistics == null) {
					ne_statistics = EMP_MODEL.current().newInstanceNe_statistics(ne_info_def.getCode());
					ne_statistics.setNe_id(vdb_value.ne_id);
					ne_statistics.setNe_info_index(vdb_value.ne_info_index);
					ne_statistics.setCollect_time(vdb_value.collect_time);
					map.put(key, ne_statistics);
				}
				ne_statistics.setMap(mmm);
			} catch (EmpException e) {
				UtilInternalError.notifyInternalError(context, vdb_value.ne_id, "NE_STATISTICS_VALUE", UtilString.format("ne_info_value fail {}", e.getCause()));
			} catch (Exception e) {
				UtilInternalError.notifyInternalError(context, vdb_value.ne_id, "NE_STATISTICS_VALUE", UtilString.format("ne_info_value fail {}", e));
			}
		}
		return map;
	}

	protected void clearCache() {
		UtilCache.removeAll(CACHE_FIELD_KEY);
	}

	@Override
	public String[][] prepareListPartition(EmpContext context, Date collect_time) throws EmpException {
		List<String> partition_create_list = new ArrayList<String>();
		List<String> partition_drop_list = new ArrayList<String>();

		STATISTICS_TYPE[] statistics_types = { STATISTICS_TYPE.MINUTE_5, STATISTICS_TYPE.HOUR_1, STATISTICS_TYPE.DAY_1, STATISTICS_TYPE.MONTH_1 };
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
					partition_create_list.add(UtilString.format("NE_STATISTICS.{}.{}", statistics_type, partition_index));
				}
			}

			for (String partition_index : partition_as_is) {
				if (!partition_to_be.contains(partition_index)) {
					parameter.clear();
					parameter.put("partition_type", UtilPartition.getPartition_type(statistics_type));
					parameter.put("partition_index", partition_index);
					driver4Mybatis.insert(context, getDefine_class(), "dropPartition", parameter);
					partition_drop_list.add(UtilString.format("NE_STATISTICS.{}.{}", statistics_type, partition_index));
				}
			}
		}

		return new String[][] { partition_create_list.toArray(new String[0]), partition_drop_list.toArray(new String[0]) };
	}

	@Override
	public void truncate(EmpContext context) throws EmpException {
		STATISTICS_TYPE[] statistics_types = { STATISTICS_TYPE.MONTH_1, STATISTICS_TYPE.DAY_1, STATISTICS_TYPE.HOUR_1, STATISTICS_TYPE.MINUTE_5 };
		for (STATISTICS_TYPE statistics_type : statistics_types) {
			Map<String, Object> parameter = new HashMap<String, Object>();
			parameter.put("statistics_type", statistics_type);
			parameter.put("partition_type", UtilPartition.getPartition_type(statistics_type));
			@SuppressWarnings("unused")
			int count = (Integer) driver4Mybatis.delete(context, getDefine_class(), "truncateSync", parameter);
			count += (Integer) driver4Mybatis.delete(context, getDefine_class(), "truncate", parameter);
		}
		prepareListPartition(context, new Date());
	}

}
