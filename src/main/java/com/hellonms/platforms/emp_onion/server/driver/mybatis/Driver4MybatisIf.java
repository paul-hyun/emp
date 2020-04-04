/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_onion.server.driver.mybatis;

import java.util.List;

import com.hellonms.platforms.emp_core.server.driver.DriverIf;
import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.server.workflow.DaoIf;
import com.hellonms.platforms.emp_core.share.error.EmpException;

/**
 * <p>
 * DB를 mybatis를 통해서 접근하는 Driver 정의
 * </p>
 * 
 * @since 1.6
 * @create 2015. 3. 10.
 * @modified 2015. 3. 10.
 * @author cchyun
 * 
 */
public interface Driver4MybatisIf extends DriverIf {

	public int insert(EmpContext context, Class<? extends DaoIf> clazz, String statement, Object parameter) throws EmpException;

	public Object selectOne(EmpContext context, Class<? extends DaoIf> clazz, String statement, Object parameter) throws EmpException;
	
	public List<?> selectList(EmpContext context, Class<? extends DaoIf> clazz, String statement, Object parameter) throws EmpException;

	public int update(EmpContext context, Class<? extends DaoIf> clazz, String statement, Object parameter) throws EmpException;

	public int delete(EmpContext context, Class<? extends DaoIf> clazz, String statement, Object parameter) throws EmpException;

}
