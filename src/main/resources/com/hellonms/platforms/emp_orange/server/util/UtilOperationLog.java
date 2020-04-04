/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.server.util;

import java.util.Date;

import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.share.operation.OPERATION_CODE;
import com.hellonms.platforms.emp_orange.share.model.security.operation_log.Model4OperationLog;

/**
 * <p>
 * Util for Operation Log
 * </p>
 *
 * @since 1.6
 * @create 2015. 6. 8.
 * @modified 2015. 6. 8.
 * @author cchyun
 *
 */
public class UtilOperationLog {

	/**
	 * <p>
	 * 운용이력 생성
	 * </p>
	 * 
	 * @param context
	 * @param user_ip
	 * @param operation_code
	 * @param exception
	 * @return
	 */
	public static Model4OperationLog newInstance(EmpContext context, OPERATION_CODE operation_code) {
		return newInstance(context, 0, 0, context.getUser_ip(), operation_code, true, "", "");
	}

	/**
	 * @param context
	 * @param operation_code
	 * @param description
	 * @return
	 */
	public static Model4OperationLog newInstance(EmpContext context, OPERATION_CODE operation_code, String description) {
		return newInstance(context, 0, 0, context.getUser_ip(), operation_code, true, "", description);
	}

	/**
	 * @param context
	 * @param ne_group_id
	 * @param ne_id
	 * @param operation_code
	 * @param description
	 * @return
	 */
	public static Model4OperationLog newInstance(EmpContext context, int ne_group_id, int ne_id, OPERATION_CODE operation_code, String description) {
		return newInstance(context, ne_group_id, ne_id, context.getUser_ip(), operation_code, true, "", description);
	}

	/**
	 * <p>
	 * 운용이력 생성
	 * </p>
	 * 
	 * @param context
	 * @param operation_code
	 * @param exception
	 * @param description
	 * @return
	 */
	public static Model4OperationLog newInstance(EmpContext context, OPERATION_CODE operation_code, Throwable exception, String description) {
		boolean result = (exception == null);
		return newInstance(context, 0, 0, context.getUser_ip(), operation_code, result, result ? "" : exception.getMessage(), description);
	}

	/**
	 * <p>
	 * 운용이력 생성
	 * </p>
	 * 
	 * @param context
	 * @param ne_group_id
	 * @param operation_code
	 * @param exception
	 * @param description
	 * @return
	 */
	public static Model4OperationLog newInstance(EmpContext context, int ne_group_id, OPERATION_CODE operation_code, Throwable exception, String description) {
		boolean result = (exception == null);
		return newInstance(context, ne_group_id, 0, context.getUser_ip(), operation_code, result, result ? "" : exception.getMessage(), description);
	}

	/**
	 * <p>
	 * 운용이력 생성
	 * </p>
	 * 
	 * @param context
	 * @param ne_group_id
	 * @param ne_id
	 * @param operation_code
	 * @param exception
	 * @param description
	 * @return
	 */
	public static Model4OperationLog newInstance(EmpContext context, int ne_group_id, int ne_id, OPERATION_CODE operation_code, Throwable exception, String description) {
		boolean result = (exception == null);
		return newInstance(context, ne_group_id, ne_id, context.getUser_ip(), operation_code, result, result ? "" : exception.getMessage(), description);
	}

	/**
	 * <p>
	 * 운용이력 생성
	 * </p>
	 * 
	 * @param context
	 * @param operation_code
	 * @param result
	 * @param fail_cause
	 * @param description
	 * @return
	 */
	public static Model4OperationLog newInstance(EmpContext context, OPERATION_CODE operation_code, boolean result, String fail_cause, String description) {
		return newInstance(context, 0, 0, context.getUser_ip(), operation_code, result, fail_cause, description);
	}

	/**
	 * <p>
	 * 운용이력 생성
	 * </p>
	 * 
	 * @param context
	 * @param ne_group_id
	 * @param operation_code
	 * @param result
	 * @param fail_cause
	 * @param description
	 * @return
	 */
	public static Model4OperationLog newInstance(EmpContext context, int ne_group_id, OPERATION_CODE operation_code, boolean result, String fail_cause, String description) {
		return newInstance(context, ne_group_id, 0, context.getUser_ip(), operation_code, result, fail_cause, description);
	}

	/**
	 * <p>
	 * 운용이력 생성
	 * </p>
	 * 
	 * @param context
	 * @param ne_group_id
	 * @param ne_id
	 * @param user_ip
	 * @param operation_code
	 * @param result
	 * @param fail_cause
	 * @param description
	 * @return
	 */
	public static Model4OperationLog newInstance(EmpContext context, int ne_group_id, int ne_id, String user_ip, OPERATION_CODE operation_code, boolean result, String fail_cause, String description) {
		Model4OperationLog model = new Model4OperationLog();
		model.setTransaction_id(context.getTransaction_id());
		model.setNe_group_id(ne_group_id);
		model.setNe_id(ne_id);
		model.setUser_id(context.getUser_id());
		model.setUser_account(context.getUser_account());
		model.setUser_session_id(context.getUser_session_id());
		model.setUser_account(context.getUser_account());
		model.setUser_ip(user_ip);
		model.setOperation_code(operation_code);
		model.setResult(result);
		model.setFail_cause(fail_cause);
		model.setStart_time(new Date(context.getOpen_timestamp()));
		model.setEnd_time(new Date(context.getClose_timestamp() == 0 ? System.currentTimeMillis() : context.getClose_timestamp()));
		model.setDescription(description);
		return model;
	}

}
