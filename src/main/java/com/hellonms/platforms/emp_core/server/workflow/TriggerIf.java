/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_core.server.workflow;

import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.share.error.EmpException;

/**
 * <p>
 * Worker의 세부 수행명령 담당
 * </p>
 * 
 * @since 1.6
 * @create 2015. 3. 10.
 * @modified 2015. 3. 10.
 * @author cchyun
 * 
 */
public interface TriggerIf {

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

}
