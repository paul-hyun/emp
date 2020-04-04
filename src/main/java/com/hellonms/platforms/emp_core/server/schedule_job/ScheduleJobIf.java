/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_core.server.schedule_job;

import java.util.Date;

import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.share.error.EmpException;

/**
 * <p>
 * 주기적 작업 정의
 * </p>
 * 
 * @since 1.6
 * @create 2015. 3. 10.
 * @modified 2015. 3. 10.
 * @author cchyun
 * 
 */
public interface ScheduleJobIf {

	/**
	 * <p>
	 * 정의 구분자 조회
	 * </p>
	 * 
	 * @return
	 */
	public Class<? extends ScheduleJobIf> getDefine_class();

	/**
	 * <p>
	 * 초기화
	 * </p>
	 * 
	 * @param context
	 * @throws EmpException
	 */
	public void initialize(EmpContext context) throws EmpException;

	/**
	 * <p>
	 * 종료
	 * </p>
	 * 
	 * @param context
	 * @throws EmpException
	 */
	public void dispose(EmpContext context) throws EmpException;

	/**
	 * <p>
	 * 주기적작업을 수행한다.
	 * </p>
	 * 
	 * @param context
	 * @param schedule_time
	 * @throws EmpException
	 */
	public void execute(EmpContext context, Date schedule_time) throws EmpException;

}
