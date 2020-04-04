/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.share.model.fault.event;

/**
 * <p>
 * Event 등급
 * </p>
 * 
 * @since 1.6
 * @create 2015. 3. 14.
 * @modified 2015. 3. 14.
 * @author cchyun
 * 
 */
public enum SEVERITY {

	/**
	 * 통신오류
	 */
	COMMUNICATION_FAIL,

	/**
	 * 심각
	 */
	CRITICAL,

	/**
	 * 주의
	 */
	MAJOR,

	/**
	 * 경계
	 */
	MINOR,

	/**
	 * 정상
	 */
	CLEAR,

	/**
	 * 경고
	 */
	WARNING,

	/**
	 * 알림
	 */
	INFO;

	public static SEVERITY toEnum(String name) {
		if (name == null) {
			return null;
		} else if (name.equals(COMMUNICATION_FAIL.name())) {
			return COMMUNICATION_FAIL;
		} else if (name.equals(CRITICAL.name())) {
			return CRITICAL;
		} else if (name.equals(MAJOR.name())) {
			return MAJOR;
		} else if (name.equals(MINOR.name())) {
			return MINOR;
		} else if (name.equals(CLEAR.name())) {
			return CLEAR;
		} else if (name.equals(WARNING.name())) {
			return WARNING;
		} else {
			return INFO;
		}
	}

}
