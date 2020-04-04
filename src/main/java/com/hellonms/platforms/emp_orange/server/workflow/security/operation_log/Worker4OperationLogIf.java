/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.server.workflow.security.operation_log;

import java.util.Date;

import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.server.workflow.WorkerIf;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_core.share.operation.OPERATION_CODE;
import com.hellonms.platforms.emp_orange.share.model.security.operation_log.Model4OperationLog;

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
public interface Worker4OperationLogIf extends WorkerIf {

	/**
	 * <p>
	 * 운용이력 코드를 조회한다.
	 * </p>
	 * 
	 * @param context
	 * @return
	 * @throws EmpException
	 */
	public OPERATION_CODE[] getListOperation_code(EmpContext context) throws EmpException;

	/**
	 * <p>
	 * 운용이력을 생성한다.
	 * </p>
	 * 
	 * @param context
	 * @param operation_log
	 * @return
	 * @throws EmpException
	 */
	public Model4OperationLog createOperationLog(EmpContext context, Model4OperationLog operation_log) throws EmpException;

	/**
	 * <p>
	 * 운용이력을 조회한다.
	 * </p>
	 * 
	 * @param context
	 * @param ne_group_id
	 * @param function_group
	 * @param function
	 * @param operation
	 * @param result
	 * @param user_session_id
	 * @param user_account
	 * @param fromTime
	 * @param toTime
	 * @param startNo
	 * @param count
	 * @return
	 * @throws EmpException
	 */
	public Model4OperationLog[] queryListOperationLogByNeGroup(EmpContext context, int ne_group_id, String function_group, String function, String operation, Boolean result, Integer user_session_id, String user_account, Date fromTime, Date toTime, int startNo, int count) throws EmpException;

	/**
	 * <p>
	 * 운용이력을 조회한다.
	 * </p>
	 * 
	 * @param context
	 * @param ne_id
	 * @param function_group
	 * @param function
	 * @param operation
	 * @param result
	 * @param user_session_id
	 * @param user_account
	 * @param fromTime
	 * @param toTime
	 * @param startNo
	 * @param count
	 * @return
	 * @throws EmpException
	 */
	public Model4OperationLog[] queryListOperationLogByNe(EmpContext context, int ne_id, String function_group, String function, String operation, Boolean result, Integer user_session_id, String user_account, Date fromTime, Date toTime, int startNo, int count) throws EmpException;

	/**
	 * <p>
	 * 운용이력을 갯수을 조회한다.
	 * </p>
	 * 
	 * @param context
	 * @param ne_group_id
	 * @param function_group
	 * @param function
	 * @param operation
	 * @param result
	 * @param user_session_id
	 * @param user_account
	 * @param fromTime
	 * @param toTime
	 * @return
	 * @throws EmpException
	 */
	public int queryCountOperationLogByNeGroup(EmpContext context, int ne_group_id, String function_group, String function, String operation, Boolean result, Integer user_session_id, String user_account, Date fromTime, Date toTime) throws EmpException;

	/**
	 * <p>
	 * 운용이력을 갯수을 조회한다.
	 * </p>
	 * 
	 * @param context
	 * @param ne_id
	 * @param function_group
	 * @param function
	 * @param operation
	 * @param result
	 * @param user_session_id
	 * @param user_account
	 * @param fromTime
	 * @param toTime
	 * @return
	 * @throws EmpException
	 */
	public int queryCountOperationLogByNe(EmpContext context, int ne_id, String function_group, String function, String operation, Boolean result, Integer user_session_id, String user_account, Date fromTime, Date toTime) throws EmpException;


	/**
	 * <p>
	 * 운용이력 파티션 준비
	 * </p>
	 *
	 * @param context
	 * @param collect_time
	 * @return
	 * @throws EmpException
	 */
	public String[][] prepareListPartition(EmpContext context, Date collect_time) throws EmpException;

	/**
	 * <p>
	 * 관련 테이블 초기화
	 * </p>
	 * 
	 * @param context
	 * @throws EmpException
	 */
	public void truncate(EmpContext context) throws EmpException;

}
