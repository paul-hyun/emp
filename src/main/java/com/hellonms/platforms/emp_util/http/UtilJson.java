/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_util.http;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hellonms.platforms.emp_core.share.error.ERROR_CODE;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_util.string.UtilString;

/**
 * <p>
 * Json Utility
 * </p>
 *
 * @since 1.6
 * @create 2015. 10. 29.
 * @modified 2015. 10. 29.
 * @author cchyun
 */
public class UtilJson {

	private static final ThreadLocal<ObjectMapper> mapperLocal = new ThreadLocal<ObjectMapper>() {
		@Override
		protected ObjectMapper initialValue() {
			return new ObjectMapper();
		}
	};

	public static Map<String, Object> toMap(String value) throws EmpException {
		ObjectMapper mapper = mapperLocal.get();
		try {
			Map<String, Object> result = mapper.readValue(value, new TypeReference<Map<String, Object>>() {
			});
			return result;
		} catch (Exception e) {
			throw new EmpException(e, ERROR_CODE.ERROR_UNKNOWN);
		}
	}

	public static <T> T toObject(String value, Class<T> clazz) throws EmpException {
		ObjectMapper mapper = mapperLocal.get();
		try {
			T result = mapper.readValue(value, clazz);
			return result;
		} catch (Exception e) {
			throw new EmpException(e, ERROR_CODE.ERROR_UNKNOWN);
		}
	}

	public static <T> T toObject(Object value, Class<T> clazz) throws EmpException {
		ObjectMapper mapper = mapperLocal.get();
		try {
			T result = mapper.convertValue(value, clazz);
			return result;
		} catch (Exception e) {
			throw new EmpException(e, ERROR_CODE.ERROR_UNKNOWN);
		}
	}

	public static String toString(Object value) throws EmpException {
		ObjectMapper mapper = mapperLocal.get();
		try {
			return mapper.writeValueAsString(value);
		} catch (Exception e) {
			throw new EmpException(e, ERROR_CODE.ERROR_UNKNOWN);
		}
	}

	public static String toString(EmpException exception) {
		ObjectMapper mapper = mapperLocal.get();
		try {
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("error_code", exception.getError_code().getError_code());
			result.put("args", exception.getArgs());
			return mapper.writeValueAsString(result);
		} catch (Exception e) {
			return "";
		}
	}

	// //////////////////////////////////////////////////////////////////
	public static List<?> getList(Map<?, ?> map, Object key) {
		Object value = map.get(key);
		if (value == null) {
			return null;
		} else if (value instanceof List<?>) {
			return (List<?>) value;
		} else {
			throw new RuntimeException(UtilString.format("{} is not {} : {}", List.class, value.getClass()));
		}
	}

	public static Map<?, ?> getMap(Map<?, ?> map, Object key) {
		Object value = map.get(key);
		if (value == null) {
			return null;
		} else if (value instanceof Map<?, ?>) {
			return (Map<?, ?>) value;
		} else {
			throw new RuntimeException(UtilString.format("{} is not {} : {}", Map.class, value.getClass()));
		}
	}

	public static String getString(Map<?, ?> map, Object key) {
		Object value = map.get(key);
		if (value == null) {
			return null;
		} else if (value instanceof String) {
			return (String) value;
		} else {
			return String.valueOf(value);
		}
	}

	public static Integer getInt(Map<?, ?> map, Object key) {
		Object value = map.get(key);
		if (value == null) {
			return null;
		} else if (value instanceof Integer) {
			return (Integer) value;
		} else if (value instanceof String) {
			return Integer.parseInt((String) value);
		} else {
			throw new RuntimeException(UtilString.format("{} is not {} : {}", Integer.class, value.getClass()));
		}
	}

	public static Long getLong(Map<?, ?> map, Object key) {
		Object value = map.get(key);
		if (value == null) {
			return null;
		} else if (value instanceof Long) {
			return (Long) value;
		} else if (value instanceof String) {
			return Long.parseLong((String) value);
		} else {
			throw new RuntimeException(UtilString.format("{} is not {} : {}", Long.class, value.getClass()));
		}
	}

	public static Double getDouble(Map<?, ?> map, Object key) {
		Object value = map.get(key);
		if (value == null) {
			return null;
		} else if (value instanceof Double) {
			return (Double) value;
		} else if (value instanceof String) {
			return Double.parseDouble((String) value);
		} else {
			throw new RuntimeException(UtilString.format("{} is not {} : {}", Double.class, value.getClass()));
		}
	}

}
