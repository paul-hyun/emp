/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.server.workflow.security.operation_log;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.hellonms.platforms.emp_core.server.log.BlackBox;
import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.server.workflow.DaoIf;
import com.hellonms.platforms.emp_core.server.workflow.WorkflowMap;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_core.share.operation.OPERATION_CODE;
import com.hellonms.platforms.emp_onion.server.driver.mybatis.Driver4MybatisIf;
import com.hellonms.platforms.emp_onion.server.util.UtilPartition;
import com.hellonms.platforms.emp_onion.share.model.STATISTICS_TYPE;
import com.hellonms.platforms.emp_orange.share.model.security.operation_log.Model4OperationLog;
import com.hellonms.platforms.emp_util.string.UtilString;

/**
 * <p>
 * 운용이력 Dao
 * </p>
 * 
 * @since 1.6
 * @create 2015. 3. 12.
 * @modified 2015. 3. 12.
 * @author cchyun
 */
public class Dao4OperationLog implements Dao4OperationLogIf {

	private OPERATION_CODE[] operation_codes = {};

	private Set<OPERATION_CODE> operation_code_map = new HashSet<OPERATION_CODE>();

	private Map<String, OPERATION_CODE[]> operation_filter_map = new HashMap<String, OPERATION_CODE[]>();

	protected Driver4MybatisIf driver4Mybatis;

	@SuppressWarnings("unused")
	private static final BlackBox blackBox = new BlackBox(Dao4OperationLog.class);

	public void setOperation_codes(OPERATION_CODE[] operation_codes) {
		Set<OPERATION_CODE> operation_code_map = new HashSet<OPERATION_CODE>();
		for (OPERATION_CODE operation_code : operation_codes) {
			operation_code_map.add(operation_code);
		}
		this.operation_codes = operation_codes;
		this.operation_code_map = operation_code_map;
	}

	@Override
	public Class<? extends DaoIf> getDefine_class() {
		return Dao4OperationLogIf.class;
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
	public OPERATION_CODE[] getListOperation_code(EmpContext context) throws EmpException {
		return operation_codes;
	}

	@Override
	public Model4OperationLog createOperationLog(EmpContext context, Model4OperationLog operation_log) throws EmpException {
		if (!operation_code_map.contains(operation_log.getOperation_code())) {
			return operation_log; // do nothing
		}

		if (operation_log.getNe_group_id() == 0 && operation_log.getNe_id() != 0) {
			operation_log.setNe_group_id(queryNe_group_id(context, operation_log.getNe_id()));
		}

		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("model", operation_log);
		parameter.put("partition_index", UtilPartition.getPartition_index(operation_log.getStart_time()));

		driver4Mybatis.insert(context, getDefine_class(), "createOperationLog", parameter);

		return operation_log;
	}

	private int queryNe_group_id(EmpContext context, int ne_id) throws EmpException {
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("ne_id", ne_id);

		Integer ne_group_id = (Integer) driver4Mybatis.selectOne(context, getDefine_class(), "queryNe_group_id", parameter);
		return ne_group_id == null ? 0 : ne_group_id;
	}

	@Override
	public Model4OperationLog[] queryListOperationLogByNeGroup(EmpContext context, int ne_group_id, String function_group, String function, String operation, Boolean result, Integer user_session_id, String user_account, Date fromTime, Date toTime, int startNo, int count) throws EmpException {
		Map<String, Object> parameter = new HashMap<String, Object>();
		if (context.getUser_id() != 0) {
			parameter.put("user_id", context.getUser_id());
		}
		parameter.put("ne_group_id", ne_group_id);
		parameter.put("operation_codes", getOperation_codes(function_group, function, operation));
		parameter.put("result", result);
		parameter.put("user_session_id", user_session_id);
		parameter.put("user_account", user_account);
		parameter.put("fromTime", fromTime);
		parameter.put("toTime", toTime);
		parameter.put("startNo", startNo);
		parameter.put("count", count);

		List<?> list = driver4Mybatis.selectList(context, getDefine_class(), "queryListOperationLogByNeGroup", parameter);

		return list.toArray(new Model4OperationLog[0]);
	}

	@Override
	public Model4OperationLog[] queryListOperationLogByNe(EmpContext context, int ne_id, String function_group, String function, String operation, Boolean result, Integer user_session_id, String user_account, Date fromTime, Date toTime, int startNo, int count) throws EmpException {
		Map<String, Object> parameter = new HashMap<String, Object>();
		if (context.getUser_id() != 0) {
			parameter.put("user_id", context.getUser_id());
		}
		parameter.put("ne_id", ne_id);
		parameter.put("operation_codes", getOperation_codes(function_group, function, operation));
		parameter.put("result", result);
		parameter.put("user_session_id", user_session_id);
		parameter.put("user_account", user_account);
		parameter.put("fromTime", fromTime);
		parameter.put("toTime", toTime);
		parameter.put("startNo", startNo);
		parameter.put("count", count);

		List<?> list = driver4Mybatis.selectList(context, getDefine_class(), "queryListOperationLogByNe", parameter);

		return list.toArray(new Model4OperationLog[0]);
	}

	@Override
	public int queryCountOperationLogByNeGroup(EmpContext context, int ne_group_id, String function_group, String function, String operation, Boolean result, Integer user_session_id, String user_account, Date fromTime, Date toTime) throws EmpException {
		Map<String, Object> parameter = new HashMap<String, Object>();
		if (context.getUser_id() != 0) {
			parameter.put("user_id", context.getUser_id());
		}
		parameter.put("ne_group_id", ne_group_id);
		parameter.put("operation_codes", getOperation_codes(function_group, function, operation));
		parameter.put("result", result);
		parameter.put("user_session_id", user_session_id);
		parameter.put("user_account", user_account);
		parameter.put("fromTime", fromTime);
		parameter.put("toTime", toTime);

		int count = (Integer) driver4Mybatis.selectOne(context, getDefine_class(), "queryCountOperationLogByNeGroup", parameter);

		return count;
	}

	@Override
	public int queryCountOperationLogByNe(EmpContext context, int ne_id, String function_group, String function, String operation, Boolean result, Integer user_session_id, String user_account, Date fromTime, Date toTime) throws EmpException {
		Map<String, Object> parameter = new HashMap<String, Object>();
		if (context.getUser_id() != 0) {
			parameter.put("user_id", context.getUser_id());
		}
		parameter.put("ne_id", ne_id);
		parameter.put("operation_codes", getOperation_codes(function_group, function, operation));
		parameter.put("result", result);
		parameter.put("user_session_id", user_session_id);
		parameter.put("user_account", user_account);
		parameter.put("fromTime", fromTime);
		parameter.put("toTime", toTime);

		int count = (Integer) driver4Mybatis.selectOne(context, getDefine_class(), "queryCountOperationLogByNe", parameter);

		return count;
	}

	protected OPERATION_CODE[] getOperation_codes(String function_group, String function, String operation) {
		if (function_group == null && function == null && operation == null) {
			return null;
		} else if (function_group != null && function == null && operation == null) {
			String key = new StringBuilder().append(function_group).toString();
			OPERATION_CODE[] operation_filters = operation_filter_map.get(key);
			if (operation_filters == null) {
				List<OPERATION_CODE> operation_list = new ArrayList<OPERATION_CODE>();
				for (OPERATION_CODE operation_code : operation_codes) {
					if (operation_code.getFunction_group().equals(function_group)) {
						operation_list.add(operation_code);
					}
				}
				operation_filters = operation_list.toArray(new OPERATION_CODE[0]);
				operation_filter_map.put(key, operation_filters);
			}
			return operation_filters;
		} else if (function_group != null && function != null && operation == null) {
			String key = new StringBuilder().append(function_group).append(".").append(function).toString();
			OPERATION_CODE[] operation_filters = operation_filter_map.get(key);
			if (operation_filters == null) {
				List<OPERATION_CODE> operation_list = new ArrayList<OPERATION_CODE>();
				for (OPERATION_CODE operation_code : operation_codes) {
					if (operation_code.getFunction_group().equals(function_group) && operation_code.getFunction().equals(function)) {
						operation_list.add(operation_code);
					}
				}
				operation_filters = operation_list.toArray(new OPERATION_CODE[0]);
				operation_filter_map.put(key, operation_filters);
			}
			return operation_filters;
		} else if (function_group != null && function != null && operation != null) {
			String key = new StringBuilder().append(function_group).append(".").append(function).append(".").append(operation).toString();
			OPERATION_CODE[] operation_filters = operation_filter_map.get(key);
			if (operation_filters == null) {
				List<OPERATION_CODE> operation_list = new ArrayList<OPERATION_CODE>();
				for (OPERATION_CODE operation_code : operation_codes) {
					if (operation_code.getFunction_group().equals(function_group) && operation_code.getFunction().equals(function) && operation_code.getOperation().equals(operation)) {
						operation_list.add(operation_code);
					}
				}
				operation_filters = operation_list.toArray(new OPERATION_CODE[0]);
				operation_filter_map.put(key, operation_filters);
			}
			return operation_filters;
		}
		return null;
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
					partition_create_list.add(UtilString.format("OPERATION_LOG.{}.{}", statistics_type, partition_index));
				}
			}

			for (String partition_index : partition_as_is) {
				if (!partition_to_be.contains(partition_index)) {
					parameter.clear();
					parameter.put("partition_type", UtilPartition.getPartition_type(statistics_type));
					parameter.put("partition_index", partition_index);
					driver4Mybatis.insert(context, getDefine_class(), "dropPartition", parameter);
					partition_drop_list.add(UtilString.format("OPERATION_LOG.{}.{}", statistics_type, partition_index));
				}
			}
		}

		return new String[][] { partition_create_list.toArray(new String[0]), partition_drop_list.toArray(new String[0]) };
	}

	@Override
	public void truncate(EmpContext context) throws EmpException {
		Map<String, Object> parameter = new HashMap<String, Object>();

		@SuppressWarnings("unused")
		int count = (Integer) driver4Mybatis.delete(context, getDefine_class(), "truncate", parameter);

		prepareListPartition(context, new Date());
	}

}
