/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_core;

import java.io.File;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.share.error.ERROR_CODE_CORE;
import com.hellonms.platforms.emp_core.share.error.EmpException;

/**
 * <p>
 * EMP Configuration으로 <emp_home>/config/emp_config.xml 파일을 참조한다.
 * </p>
 * 
 * @since 1.6
 * @create 2015. 3. 10.
 * @modified 2015. 3. 10.
 * @author cchyun
 * 
 */
public class EmpConfig4Server {

	private static XMLConfiguration configuration;

	/**
	 * <p>
	 * confguration 초기화
	 * </p>
	 * 
	 * @param context
	 * @param emp_config
	 * @throws EmpException
	 */
	public static void initialize(EmpContext context, File emp_config) throws EmpException {
		try {
			configuration = new XMLConfiguration(emp_config);
		} catch (ConfigurationException e) {
			throw new EmpException(e, ERROR_CODE_CORE.INVALID_CONFIG, emp_config.getAbsolutePath());
		}
	}

	/**
	 * <p>
	 * Configuration 조회
	 * </p>
	 * 
	 * @param key
	 * @return
	 * @throws EmpException
	 */
	public static String getString(String key) throws EmpException {
		String value = configuration.getString(key, null);
		if (value == null) {
			throw new EmpException(ERROR_CODE_CORE.INVALID_CONFIG, key);
		}
		return value;
	}

	/**
	 * <p>
	 * Configuration 조회 (없을 경우 기본 값)
	 * </p>
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static String getString(String key, String defaultValue) {
		return configuration.getString(key, defaultValue);
	}

	/**
	 * <p>
	 * Configuration 조회
	 * </p>
	 * 
	 * @param key
	 * @return
	 * @throws EmpException
	 */
	public static int getInt(String key) throws EmpException {
		Integer value = configuration.getInteger(key, null);
		if (value == null) {
			throw new EmpException(ERROR_CODE_CORE.INVALID_CONFIG, key);
		}
		return value;
	}

	/**
	 * <p>
	 * Configuration 조회 (없을 경우 기본 값)
	 * </p>
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static int getInt(String key, int defaultValue) {
		return configuration.getInteger(key, defaultValue);
	}

	/**
	 * <p>
	 * Configuration 조회 (없을 경우 기본 값)
	 * </p>
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static boolean getBoolean(String key) {
		return configuration.getBoolean(key, false);
	}

}
