/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_onion.server.driver.mybatis;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.hellonms.platforms.emp_core.server.driver.DriverIf;
import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.server.workflow.DaoIf;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_onion.share.error.ERROR_CODE_ONION;

/**
 * <p>
 * DB를 mybatis를 통해서 접근하는 Driver 구현
 * </p>
 * 
 * @since 1.6
 * @create 2015. 3. 10.
 * @modified 2015. 3. 10.
 * @author cchyun
 * 
 */
public class Driver4Mybatis implements Driver4MybatisIf {

	@Override
	public Class<? extends DriverIf> getDefine_class() {
		return Driver4MybatisIf.class;
	}

	@Override
	public void initialize(EmpContext context) throws EmpException {
	}

	@Override
	public void dispose(EmpContext context) throws EmpException {
	}

	@Override
	public int insert(EmpContext context, Class<? extends DaoIf> clazz, String statement, Object parameter) throws EmpException {
		try {
			SqlSession sqlSession = context.getSqlSession();
			return sqlSession.insert(new StringBuilder().append(clazz.getName()).append('.').append(statement).toString(), parameter);
		} catch (Exception e) {
			throw new EmpException(e, ERROR_CODE_ONION.SQL_EXCEPTION, "insert");
		}
	}

	@Override
	public Object selectOne(EmpContext context, Class<? extends DaoIf> clazz, String statement, Object parameter) throws EmpException {
		try {
			SqlSession sqlSession = context.getSqlSession();
			return sqlSession.selectOne(new StringBuilder().append(clazz.getName()).append('.').append(statement).toString(), parameter);
		} catch (Exception e) {
			throw new EmpException(e, ERROR_CODE_ONION.SQL_EXCEPTION, "selectOne");
		}
	}

	@Override
	public List<?> selectList(EmpContext context, Class<? extends DaoIf> clazz, String statement, Object parameter) throws EmpException {
		try {
			SqlSession sqlSession = context.getSqlSession();
			return sqlSession.selectList(new StringBuilder().append(clazz.getName()).append('.').append(statement).toString(), parameter);
		} catch (Exception e) {
			throw new EmpException(e, ERROR_CODE_ONION.SQL_EXCEPTION, "selectList");
		}
	}

	@Override
	public int update(EmpContext context, Class<? extends DaoIf> clazz, String statement, Object parameter) throws EmpException {
		try {
			SqlSession sqlSession = context.getSqlSession();
			return sqlSession.update(new StringBuilder().append(clazz.getName()).append('.').append(statement).toString(), parameter);
		} catch (Exception e) {
			throw new EmpException(e, ERROR_CODE_ONION.SQL_EXCEPTION, "update");
		}
	}

	@Override
	public int delete(EmpContext context, Class<? extends DaoIf> clazz, String statement, Object parameter) throws EmpException {
		try {
			SqlSession sqlSession = context.getSqlSession();
			return sqlSession.delete(new StringBuilder().append(clazz.getName()).append('.').append(statement).toString(), parameter);
		} catch (Exception e) {
			throw new EmpException(e, ERROR_CODE_ONION.SQL_EXCEPTION, "delete");
		}
	}

}
