/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_core.share.error;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import com.hellonms.platforms.emp_util.string.UtilString;

/**
 * <p>
 * 에러코드 정의
 * </p>
 * 
 * @since 1.6
 * @create 2015. 3. 10.
 * @modified 2015. 3. 10.
 * @author cchyun
 * 
 */
@SuppressWarnings("serial")
public final class ERROR_CODE implements Serializable {

	private static final Map<Integer, ERROR_CODE> error_code_map = new LinkedHashMap<Integer, ERROR_CODE>();

	private static ERROR_CODE[] error_codes;

	/**
	 * 알수없는 오류
	 */
	public static final ERROR_CODE ERROR_UNKNOWN = new ERROR_CODE(0xFFFFFFFF, "ERROR_CORE_UNKNOWN");

	public static ERROR_CODE add(int error_code, String error_name) {
		ERROR_CODE value = error_code_map.get(error_code);
		if (value != null) {
			System.err.println(UtilString.format("error_code={}, error_name={} duplicate !!", error_code, error_name));
			System.exit(1);
		}
		value = new ERROR_CODE(error_code, error_name);
		error_code_map.put(error_code, value);
		return value;
	}

	public static ERROR_CODE[] values() {
		if (error_codes == null) {
			error_codes = error_code_map.values().toArray(new ERROR_CODE[0]);
		}
		return error_codes;
	}

	public static ERROR_CODE valueOf(int error_code) {
		ERROR_CODE value = error_code_map.get(error_code);
		return value == null ? ERROR_CODE.ERROR_UNKNOWN : value;
	}

	private int error_code;

	private String error_name;

	public ERROR_CODE() {
		error_code = 0;
		error_name = "";
	}

	private ERROR_CODE(int error_code, String error_name) {
		this.error_code = error_code;
		this.error_name = error_name;
	}

	public int getError_code() {
		return error_code;
	}

	@Override
	public String toString() {
		return error_name;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ERROR_CODE) {
			return (error_code == ((ERROR_CODE) obj).error_code);
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return error_code;
	}

}
