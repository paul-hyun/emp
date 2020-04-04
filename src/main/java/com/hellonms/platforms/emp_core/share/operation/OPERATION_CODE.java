/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_core.share.operation;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>
 * 운용이력 코드 정의
 * </p>
 * 
 * @since 1.6
 * @create 2015. 3. 12.
 * @modified 2015. 3. 12.
 * @author cchyun
 * 
 */
@SuppressWarnings("serial")
public class OPERATION_CODE implements Serializable {

	private static final Map<Integer, OPERATION_CODE> operation_code_map = new LinkedHashMap<Integer, OPERATION_CODE>();

	private static OPERATION_CODE[] operation_codes;

	public static final OPERATION_CODE OPERATION_UNKNOWN = new OPERATION_CODE(0xFFFFFFFF, "Unknown", "Unknown", "Unknown", false, "Unknown user operation !!");

	public static OPERATION_CODE add(int operation_code, String function_group, String function, String operation, boolean operation_for_ne, String description) {
		OPERATION_CODE value = operation_code_map.get(operation_code);
		if (value != null) {
			throw new RuntimeException("operation_code=" + operation_code + ", function_group=" + function_group + ", function=" + function + ", operation=" + operation + " duplicate !!");
		}
		value = new OPERATION_CODE(operation_code, function_group, function, operation, operation_for_ne, description);
		operation_code_map.put(operation_code, value);
		return value;
	}

	public static OPERATION_CODE[] values() {
		if (operation_codes == null) {
			operation_codes = operation_code_map.values().toArray(new OPERATION_CODE[0]);
		}
		return operation_codes;
	}

	public static OPERATION_CODE valueOf(int operation_code) {
		OPERATION_CODE value = operation_code_map.get(operation_code);
		return value == null ? OPERATION_CODE.OPERATION_UNKNOWN : value;
	}

	private static String getOperation_name(String function_group, String function, String operation) {
		return new StringBuilder().append(function_group).append(".").append(function).append(".").append(operation).toString();
	}

	/**
	 * 코드 이름
	 */
	private int operation_code;

	/**
	 * 기능 그룹
	 */
	private String function_group;

	/**
	 * 기능
	 */
	private String function;

	/**
	 * OPERATION
	 */
	private String operation;

	/**
	 * NE와의 관계
	 */
	private boolean operation_for_ne;

	/**
	 * 운용이력 설명
	 */
	private String description;

	public OPERATION_CODE() {
	}

	/**
	 * <p>
	 * 생성자
	 * </p>
	 * 
	 * @param operation_code
	 * @param function_group
	 * @param function
	 * @param operation
	 * @param operation_for_ne
	 * @param description
	 */
	private OPERATION_CODE(int operation_code, String function_group, String function, String operation, boolean operation_for_ne, String description) {
		this.operation_code = operation_code;
		this.function_group = function_group;
		this.function = function;
		this.operation = operation;
		this.operation_for_ne = operation_for_ne;
		this.description = description;
	}

	public int getOperation_code() {
		return operation_code;
	}

	public String getFunction_group() {
		return function_group;
	}

	public String getFunction() {
		return function;
	}

	public String getOperation() {
		return operation;
	}

	public boolean isOperation_for_ne() {
		return operation_for_ne;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return getOperation_name(function_group, function, operation);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof OPERATION_CODE) {
			return (operation_code == ((OPERATION_CODE) obj).operation_code);
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return operation_code;
	}

}
