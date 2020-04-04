/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_onion.server.workflow.environment.preference;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import com.hellonms.platforms.emp_onion.share.model.environment.preference.PREFERENCE_CODE;

/**
 * <p>
 * PREFERENCE_CODE Type Handler
 * </p>
 *
 * @since 1.6
 * @create 2015. 4. 8.
 * @modified 2015. 4. 8.
 * @author cchyun
 *
 */
public class TypeHandler4PREFERENCE_CODE implements TypeHandler<PREFERENCE_CODE> {

	@Override
	public void setParameter(PreparedStatement ps, int i, PREFERENCE_CODE parameter, JdbcType jdbcType) throws SQLException {
		ps.setInt(i, parameter.getPreference_code());
	}

	@Override
	public PREFERENCE_CODE getResult(ResultSet rs, String columnName) throws SQLException {
		return PREFERENCE_CODE.valueOf(rs.getInt(columnName));
	}

	@Override
	public PREFERENCE_CODE getResult(ResultSet rs, int columnIndex) throws SQLException {
		return PREFERENCE_CODE.valueOf(rs.getInt(columnIndex));
	}

	@Override
	public PREFERENCE_CODE getResult(CallableStatement cs, int columnIndex) throws SQLException {
		return PREFERENCE_CODE.valueOf(cs.getInt(columnIndex));
	}

}
