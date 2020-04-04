/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_core.server.driver;

import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.share.error.EmpException;

/**
 * <p>
 * 하위 연동 담당
 * </p>
 * 
 * @since 1.6
 * @create 2015. 3. 10.
 * @modified 2015. 3. 10.
 * @author cchyun
 * 
 */
public interface DriverIf {

	/**
	 * <p>
	 * 정의 구분자 조회
	 * </p>
	 * 
	 * @return
	 */
	public Class<? extends DriverIf> getDefine_class();

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
