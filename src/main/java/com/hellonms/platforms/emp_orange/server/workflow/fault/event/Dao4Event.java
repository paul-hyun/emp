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
import java.util.concurrent.locks.ReentrantLock;

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
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_EVENT;
import com.hellonms.platforms.emp_orange.share.model.fault.event.Model4Event;
import com.hellonms.platforms.emp_orange.share.model.fault.event.SEVERITY;
import com.hellonms.platforms.emp_util.string.UtilString;

/**
 * <p>
 * Insert description of Dao4EventLog.java
 * </p>
 *
 * @since 1.6
 * @create 2015. 4. 22.
 * @modified 2015. 4. 22.
 * @author cchyun
 *
 */
public class Dao4Event implements Dao4EventIf {

	protected Driver4MybatisIf driver4Mybatis;

	@SuppressWarnings("unused")
	private static final BlackBox blackBox = new BlackBox(Dao4Event.class);

	@Override
	public Class<? extends DaoIf> getDefine_class() {
		return Dao4EventIf.class;
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
	public Model4Event createEvent(EmpContext context, Model4Event event) throws EmpException {
		event.setEvent_id(queryNextEvent_id(context));

		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("model", event);
		parameter.put("partition_index", UtilPartition.getPartition_index(event.getGen_time()));

		driver4Mybatis.insert(context, getDefine_class(), "createEvent", parameter);

		return event;
	}

	@Override
	public Model4Event queryEvent(EmpContext context, long event_id) throws EmpException {
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("event_id", event_id);

		Model4Event event = (Model4Event) driver4Mybatis.selectOne(context, getDefine_class(), "queryEvent", parameter);
		if (event == null) {
			throw new EmpException(ERROR_CODE_ORANGE.MODEL_NOTEXISTS, MESSAGE_CODE_ORANGE.EVENT, event_id);
		}

		return event;
	}

	@Override
	public Model4Event[] queryListEventByNeGroup(EmpContext context, int ne_group_id, EMP_MODEL_EVENT event_def, SEVERITY severity, Date fromTime, Date toTime, int startNo, int count) throws EmpException {
		Map<String, Object> parameter = new HashMap<String, Object>();
		if (context.getUser_id() != 0) {
			parameter.put("user_id", context.getUser_id());
		}
		parameter.put("ne_group_id", ne_group_id);
		if (event_def != null) {
			parameter.put("event_code", event_def.getCode());
		}
		parameter.put("severity", severity);
		parameter.put("fromTime", fromTime);
		parameter.put("toTime", toTime);
		parameter.put("startNo", startNo);
		parameter.put("count", count);

		List<?> list = driver4Mybatis.selectList(context, getDefine_class(), "queryListEventByNeGroup", parameter);

		return list.toArray(new Model4Event[0]);
	}

	@Override
	public Model4Event[] queryListEventByNe(EmpContext context, int ne_id, EMP_MODEL_EVENT event_def, SEVERITY severity, Date fromTime, Date toTime, int startNo, int count) throws EmpException {
		Map<String, Object> parameter = new HashMap<String, Object>();
		if (context.getUser_id() != 0) {
			parameter.put("user_id", context.getUser_id());
		}
		parameter.put("ne_id", ne_id);
		if (event_def != null) {
			parameter.put("event_code", event_def.getCode());
		}
		parameter.put("severity", severity);
		parameter.put("fromTime", fromTime);
		parameter.put("toTime", toTime);
		parameter.put("startNo", startNo);
		parameter.put("count", count);

		List<?> list = driver4Mybatis.selectList(context, getDefine_class(), "queryListEventByNe", parameter);

		return list.toArray(new Model4Event[0]);
	}

	@Override
	public Model4Event[] queryListEventByConsole(EmpContext context, int startNo, int count) throws EmpException {
		Map<String, Object> parameter = new HashMap<String, Object>();
		if (context.getUser_id() != 0) {
			parameter.put("user_id", context.getUser_id());
		}
		parameter.put("startNo", startNo);
		parameter.put("count", count);

		List<?> list = driver4Mybatis.selectList(context, getDefine_class(), "queryListEventByConsole", parameter);

		return list.toArray(new Model4Event[0]);
	}

	@Override
	public int queryCountEventByNeGroup(EmpContext context, int ne_group_id, EMP_MODEL_EVENT event_def, SEVERITY severity, Date fromTime, Date toTime) throws EmpException {
		Map<String, Object> parameter = new HashMap<String, Object>();
		if (context.getUser_id() != 0) {
			parameter.put("user_id", context.getUser_id());
		}
		parameter.put("ne_group_id", ne_group_id);
		if (event_def != null) {
			parameter.put("event_code", event_def.getCode());
		}
		parameter.put("severity", severity);
		parameter.put("fromTime", fromTime);
		parameter.put("toTime", toTime);

		int count = (Integer) driver4Mybatis.selectOne(context, getDefine_class(), "queryCountEventByNeGroup", parameter);

		return count;
	}

	@Override
	public int queryCountEventByNe(EmpContext context, int ne_id, EMP_MODEL_EVENT event_def, SEVERITY severity, Date fromTime, Date toTime) throws EmpException {
		Map<String, Object> parameter = new HashMap<String, Object>();
		if (context.getUser_id() != 0) {
			parameter.put("user_id", context.getUser_id());
		}
		parameter.put("ne_id", ne_id);
		if (event_def != null) {
			parameter.put("event_code", event_def.getCode());
		}
		parameter.put("severity", severity);
		parameter.put("fromTime", fromTime);
		parameter.put("toTime", toTime);

		int count = (Integer) driver4Mybatis.selectOne(context, getDefine_class(), "queryCountEventByNe", parameter);

		return count;
	}

	@Override
	public long queryCurrUpdate_seq_fault(EmpContext context) throws EmpException {
		long update_seq_fault = (Long) driver4Mybatis.selectOne(context, getDefine_class(), "queryCurrUpdate_seq_fault", null);
		return update_seq_fault;
	}

	@Override
	public long queryNextUpdate_seq_fault(EmpContext context) throws EmpException {
		long update_seq_fault = (Long) driver4Mybatis.selectOne(context, getDefine_class(), "queryNextUpdate_seq_fault", null);
		return update_seq_fault;
	}

	private ReentrantLock lock = new ReentrantLock();

	protected long queryNextEvent_id(EmpContext context) throws EmpException {
		lock.lock();
		try {
			long event_id = (Long) driver4Mybatis.selectOne(context, getDefine_class(), "queryNextEvent_id", null);
			return event_id;
		} finally {
			lock.unlock();
		}
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
					partition_create_list.add(UtilString.format("EVENT.{}.{}", statistics_type, partition_index));
				}
			}

			for (String partition_index : partition_as_is) {
				if (!partition_to_be.contains(partition_index)) {
					parameter.clear();
					parameter.put("partition_type", UtilPartition.getPartition_type(statistics_type));
					parameter.put("partition_index", partition_index);
					driver4Mybatis.insert(context, getDefine_class(), "dropPartition", parameter);
					partition_drop_list.add(UtilString.format("EVENT.{}.{}", statistics_type, partition_index));
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
