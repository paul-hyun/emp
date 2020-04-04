/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_core.share.language;

/**
 * <p>
 * Insert description of LANGUAGE.java
 * </p>
 * 
 * @since 1.6
 * @create 2015. 3. 17.
 * @modified 2015. 3. 17.
 * @author jungsun
 * 
 */
public enum LANGUAGE {

	ENGLISH("English", "en"), KOREAN("한국어", "ko");

	private String displayName;

	private String locale;

	private LANGUAGE(String displayName, String locale) {
		this.displayName = displayName;
		this.locale = locale;
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getLocale() {
		return locale;
	}
	
	public static LANGUAGE toEnum(String name) {
		for (LANGUAGE language : LANGUAGE.values()) {
			if (language.name().equals(name) || language.displayName.equals(name)) {
				return language;
			}
		}
		return LANGUAGE.ENGLISH;
	}

}
