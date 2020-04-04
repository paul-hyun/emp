/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_core.share.language;

import java.util.HashMap;
import java.util.Map;

import com.hellonms.platforms.emp_core.share.error.ERROR_CODE;
import com.hellonms.platforms.emp_core.share.message.MESSAGE_CODE;
import com.hellonms.platforms.emp_util.string.UtilString;

/**
 * <p>
 * 다국어지원을 위한 유틸
 * </p>
 * 
 * @since 1.6
 * @create 2015. 3. 10.
 * @modified 2015. 3. 10.
 * @author cchyun
 * 
 */
public class UtilLanguage {

	private static LANGUAGE language = LANGUAGE.ENGLISH;

	private static Map<String, String> messageMap = new HashMap<String, String>();

	/**
	 * <p>
	 * 현재 설정되어 있는 언어를 반환한다.
	 * </p>
	 * 
	 * @return
	 */
	public static LANGUAGE getLanguage() {
		return language;
	}

	/**
	 * <p>
	 * 현재 언어를 설정한다.
	 * </p>
	 * 
	 * @param language
	 */
	public static void setLanguage(LANGUAGE language) {
		UtilLanguage.language = language;
	}

	/**
	 * <p>
	 * 메시지를 반환한다.
	 * </p>
	 * 
	 * @param message_code
	 * @param values
	 *            메시지에 추가할 내용들
	 * @return
	 */
	public static String getMessage(MESSAGE_CODE message_code, Object... values) {
		for (int i = 0; i < values.length; i++) {
			if (values[i] instanceof MESSAGE_CODE) {
				String value = messageMap.get(((MESSAGE_CODE) values[i]).getMessageCode());
				values[i] = UtilString.isEmpty(value) ? ((MESSAGE_CODE) values[i]).getDefaultValue() : value;
			}
		}

		String message = messageMap.get(message_code.getMessageCode());
		if (UtilString.isEmpty(message)) {
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(message_code.getDefaultValue());
			for (int i = 0; i < values.length; i++) {
				stringBuilder.append(" ").append(values[i]);
			}
			return stringBuilder.toString();
		} else {
			return UtilString.format(message, values).trim();
		}
	}

	/**
	 * <p>
	 * 메시지를 반환한다.
	 * </p>
	 * 
	 * @param error_code
	 * @param values
	 *            메시지에 추가할 내용들
	 * @return
	 */
	public static String getMessage(ERROR_CODE error_code, Object... values) {
		for (int i = 0; i < values.length; i++) {
			if (values[i] instanceof MESSAGE_CODE) {
				String value = messageMap.get(((MESSAGE_CODE) values[i]).getMessageCode());
				values[i] = UtilString.isEmpty(value) ? ((MESSAGE_CODE) values[i]).getDefaultValue() : value;
			}
		}

		String message = messageMap.get(error_code.toString());
		if (UtilString.isEmpty(message)) {
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(error_code.toString());
			for (int i = 0; i < values.length; i++) {
				stringBuilder.append(" ").append(values[i]);
			}
			return stringBuilder.toString();
		} else {
			return UtilString.format(message, values).trim();
		}
	}

	/**
	 * <p>
	 * 메시지를 반환한다.
	 * </p>
	 *
	 * @param key
	 * @param values
	 * @return
	 */
	public static String getMessage(String key, Object... values) {
		for (int i = 0; i < values.length; i++) {
			if (values[i] instanceof MESSAGE_CODE) {
				String value = messageMap.get(((MESSAGE_CODE) values[i]).getMessageCode());
				values[i] = UtilString.isEmpty(value) ? ((MESSAGE_CODE) values[i]).getDefaultValue() : value;
			}
		}

		String message = messageMap.get(key);
		if (UtilString.isEmpty(message)) {
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(key);
			for (int i = 0; i < values.length; i++) {
				stringBuilder.append(" ").append(values[i]);
			}
			return stringBuilder.toString();
		} else {
			return UtilString.format(message, values).trim();
		}
	}

	/**
	 * <p>
	 * 메시지들을 SessionStorage에 설정(저장)한다.
	 * </p>
	 * 
	 * @param messages
	 */
	public static void setMessages(Map<String, String> messages) {
		if (messages != null) {
			for (String key : messages.keySet()) {
				messageMap.put(key, messages.get(key));
			}
		}
	}

}
