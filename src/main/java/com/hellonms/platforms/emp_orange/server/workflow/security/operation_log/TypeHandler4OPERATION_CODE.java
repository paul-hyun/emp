/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.server.workflow.security.operation_log;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import com.hellonms.platforms.emp_core.share.operation.OPERATION_CODE;

/**
 * <p>
 * OPERATION_CODE Type Handler
 * </p>
 *
 * @since 1.6
 * @create 2015. 4. 8.
 * @modified 2015. 4. 8.
 * @author cchyun
 *
 */
public class TypeHandler4OPERATION_CODE implements TypeHandler<OPERATION_CODE> {

	@Override
	public void setParameter(PreparedStatement ps, int i, OPERATION_CODE parameter, JdbcType jdbcType) throws SQLException {
		ps.setInt(i, parameter.getOperation_code());
	}

	@Override
	public OPERATION_CODE getResult(ResultSet rs, String columnName) throws SQLException {
		return OPERATION_CODE.valueOf(rs.getInt(columnName));
	}

	@Override
	public OPERATION_CODE getResult(ResultSet rs, int columnIndex) throws SQLException {
		return OPERATION_CODE.valueOf(rs.getInt(columnIndex));
	}

	@Override
	public OPERATION_CODE getResult(CallableStatement cs, int columnIndex) throws SQLException {
		return OPERATION_CODE.valueOf(cs.getInt(columnIndex));
	}

}
