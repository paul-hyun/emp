/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_onion.share.model.environment.preference;

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
public class PREFERENCE_CODE implements Serializable {

	private static final Map<Integer, PREFERENCE_CODE> preference_code_map = new LinkedHashMap<Integer, PREFERENCE_CODE>();

	private static PREFERENCE_CODE[] preference_codes;

	public static final PREFERENCE_CODE PREFERENCE_UNKNOWN = new PREFERENCE_CODE(0xFFFFFFFF, "Unknown", "Unknown", "Unknown", null, 0, 0, "Unknown preference !!");

	public static PREFERENCE_CODE add(int preference_code, String function_group, String function, String preference, PREFERENCE_TYPE preference_type, int preference_min, int preference_max, String description) {
		PREFERENCE_CODE value = preference_code_map.get(preference_code);
		if (value != null) {
			throw new RuntimeException("preference_code=" + preference_code + ", function_group=" + function_group + ", function=" + function + ", preference=" + preference + " duplicate !!");
		}
		value = new PREFERENCE_CODE(preference_code, function_group, function, preference, preference_type, preference_min, preference_max, description);
		preference_code_map.put(preference_code, value);
		return value;
	}

	public static PREFERENCE_CODE[] values() {
		if (preference_codes == null) {
			preference_codes = preference_code_map.values().toArray(new PREFERENCE_CODE[0]);
		}
		return preference_codes;
	}

	public static PREFERENCE_CODE valueOf(int preference_code) {
		PREFERENCE_CODE value = preference_code_map.get(preference_code);
		return value == null ? PREFERENCE_CODE.PREFERENCE_UNKNOWN : value;
	}

	private static String getOperation_name(String function_group, String function, String preference) {
		return new StringBuilder().append(function_group).append(".").append(function).append(".").append(preference).toString();
	}

	/**
	 * 코드 이름
	 */
	private int preference_code;

	/**
	 * 기능 그룹
	 */
	private String function_group;

	/**
	 * 기능
	 */
	private String function;

	/**
	 * PREFERENCE
	 */
	private String preference;

	private PREFERENCE_TYPE preference_type;

	private int preference_min;

	private int preference_max;

	/**
	 * 운용이력 설명
	 */
	private String description;

	public PREFERENCE_CODE() {
	}

	/**
	 * <p>
	 * 생성자
	 * </p>
	 * 
	 * @param preference_code
	 * @param function_group
	 * @param function
	 * @param preference
	 * @param preference_type
	 * @param preference_min
	 * @param preference_max
	 * @param description
	 */
	private PREFERENCE_CODE(int preference_code, String function_group, String function, String preference, PREFERENCE_TYPE preference_type, int preference_min, int preference_max, String description) {
		this.preference_code = preference_code;
		this.function_group = function_group;
		this.function = function;
		this.preference = preference;
		this.preference_type = preference_type;
		this.preference_min = preference_min;
		this.preference_max = preference_max;
		this.description = description;
	}

	public int getPreference_code() {
		return preference_code;
	}

	public String getFunction_group() {
		return function_group;
	}

	public String getFunction() {
		return function;
	}

	public String getPreference() {
		return preference;
	}

	public PREFERENCE_TYPE getPreference_type() {
		return preference_type;
	}

	public int getPreference_min() {
		return preference_min;
	}

	public int getPreference_max() {
		return preference_max;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return getOperation_name(function_group, function, preference);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PREFERENCE_CODE) {
			return (preference_code == ((PREFERENCE_CODE) obj).preference_code);
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return preference_code;
	}

}
