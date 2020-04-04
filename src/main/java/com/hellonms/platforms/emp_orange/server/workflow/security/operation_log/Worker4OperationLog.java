/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.server.workflow.security.operation_log;

import java.util.Date;

import com.hellonms.platforms.emp_core.server.log.BlackBox;
import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.server.workflow.WorkerIf;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_core.share.operation.OPERATION_CODE;
import com.hellonms.platforms.emp_orange.share.error.ERROR_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.security.operation_log.Model4OperationLog;
import com.hellonms.platforms.emp_util.cache.UtilCache;

/**
 * <p>
 * 운영이력 Worker
 * </p>
 * 
 * @since 1.6
 * @create 2015. 3. 12.
 * @modified 2015. 3. 12.
 * @author cchyun
 * 
 */
public class Worker4OperationLog implements Worker4OperationLogIf {

	private Dao4OperationLogIf dao4OperationLog;

	@SuppressWarnings("unused")
	private static final BlackBox blackBox = new BlackBox(Worker4OperationLog.class);

	public void setDao4OperationLog(Dao4OperationLogIf dao4OperationLog) {
		this.dao4OperationLog = dao4OperationLog;
	}

	@Override
	public Class<? extends WorkerIf> getDefine_class() {
		return Worker4OperationLogIf.class;
	}

	@Override
	public void initialize(EmpContext context) throws EmpException {
		if (dao4OperationLog == null) {
			throw new EmpException(ERROR_CODE_ORANGE.WORKFLOW_NOTSETTED, Dao4OperationLogIf.class, getClass());
		}
		dao4OperationLog.initialize(context);
	}

	@Override
	public void dispose(EmpContext context) throws EmpException {
		dao4OperationLog.dispose(context);
	}

	@Override
	public OPERATION_CODE[] getListOperation_code(EmpContext context) throws EmpException {
		return dao4OperationLog.getListOperation_code(context);
	}

	@Override
	public Model4OperationLog createOperationLog(EmpContext context, Model4OperationLog operation_log) throws EmpException {
		EmpContext context_local = new EmpContext(this);
		try {
			operation_log.setCreator(context.getUser_account());
			return dao4OperationLog.createOperationLog(context_local, operation_log);
		} finally {
			context_local.close();
		}
	}

	@Override
	public Model4OperationLog[] queryListOperationLogByNeGroup(EmpContext context, int ne_group_id, String function_group, String function, String operation, Boolean result, Integer user_session_id, String user_account, Date fromTime, Date toTime, int startNo, int count) throws EmpException {
		return dao4OperationLog.queryListOperationLogByNeGroup(context, ne_group_id, function_group, function, operation, result, user_session_id, user_account, fromTime, toTime, startNo, count);
	}

	@Override
	public int queryCountOperationLogByNeGroup(EmpContext context, int ne_group_id, String function_group, String function, String operation, Boolean result, Integer user_session_id, String user_account, Date fromTime, Date toTime) throws EmpException {
		return dao4OperationLog.queryCountOperationLogByNeGroup(context, ne_group_id, function_group, function, operation, result, user_session_id, user_account, fromTime, toTime);
	}

	@Override
	public Model4OperationLog[] queryListOperationLogByNe(EmpContext context, int ne_id, String function_group, String function, String operation, Boolean result, Integer user_session_id, String user_account, Date fromTime, Date toTime, int startNo, int count) throws EmpException {
		return dao4OperationLog.queryListOperationLogByNe(context, ne_id, function_group, function, operation, result, user_session_id, user_account, fromTime, toTime, startNo, count);
	}

	@Override
	public int queryCountOperationLogByNe(EmpContext context, int ne_id, String function_group, String function, String operation, Boolean result, Integer user_session_id, String user_account, Date fromTime, Date toTime) throws EmpException {
		return dao4OperationLog.queryCountOperationLogByNe(context, ne_id, function_group, function, operation, result, user_session_id, user_account, fromTime, toTime);
	}

	@Override
	public String[][] prepareListPartition(EmpContext context, Date collect_time) throws EmpException {
		return dao4OperationLog.prepareListPartition(context, collect_time);
	}

	@Override
	public void truncate(EmpContext context) throws EmpException {
		dao4OperationLog.truncate(context);
		UtilCache.removeAll();
	}

}
