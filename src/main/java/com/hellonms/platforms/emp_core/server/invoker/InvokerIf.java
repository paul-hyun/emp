/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_core.server.invoker;

import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.share.error.EmpException;

/**
 * <p>
 * 상위(GUI, NBI) 연동 담당
 * </p>
 * 
 * @since 1.6
 * @create 2015. 3. 10.
 * @modified 2015. 3. 10.
 * @author cchyun
 * 
 */
public interface InvokerIf {

	/**
	 * <p>
	 * 정의 구분자 조회
	 * </p>
	 * 
	 * @return
	 */
	public Class<? extends InvokerIf> getDefine_class();

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
	 * 서비스 가능 여부
	 * </p>
	 * 
	 * @param context
	 * @throws EmpException
	 */
	public boolean isReady(EmpContext context) throws EmpException;

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
