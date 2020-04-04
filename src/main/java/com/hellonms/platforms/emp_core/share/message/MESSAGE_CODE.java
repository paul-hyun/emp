/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_core.share.message;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * Insert description of MESSAGE_CODE.java
 * </p>
 * 
 * @since 1.6
 * @create 2015. 3. 17.
 * @modified 2015. 3. 17.
 * @author jungsun
 * 
 */
@SuppressWarnings("serial")
public class MESSAGE_CODE implements Serializable {

	private static final Map<String, MESSAGE_CODE> message_code_map = new HashMap<String, MESSAGE_CODE>();

	private static MESSAGE_CODE[] message_codes;

	public static MESSAGE_CODE[] values() {
		if (message_codes == null) {
			message_codes = message_code_map.values().toArray(new MESSAGE_CODE[0]);
		}
		return message_codes;
	}

	public static MESSAGE_CODE valueOf(String message_code) {
		MESSAGE_CODE value = message_code_map.get(message_code);
		return value == null ? MESSAGE_CODE_CORE.ZERO_STRING : value;
	}

	private String message_code;

	private String default_value;

	public MESSAGE_CODE() {
		message_code = "";
		default_value = "";
	}

	/**
	 * <p>
	 * 생성자
	 * </p>
	 * 
	 * @param message_code
	 *            언어코드
	 */
	public MESSAGE_CODE(String message_code, String default_value) {
		this.message_code = message_code;
		this.default_value = default_value;

		if (message_code_map.get(message_code) == null) {
			message_code_map.put(message_code, this);
			message_codes = null;
		}
	}

	public String getMessageCode() {
		return message_code;
	}

	public String getDefaultValue() {
		return default_value;
	}

	@Override
	public String toString() {
		return message_code;
	}

	@Override
	public int hashCode() {
		return message_code.hashCode();
	}

}
