/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.client_swt.servlet;

import java.io.Serializable;
import java.util.Properties;

/**
 * <p>
 * Request Object
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 20.
 * @modified 2015. 5. 20.
 * @author cchyun
 */
@SuppressWarnings("serial")
public class Invoker4OrangeClientSwtReqeust implements Serializable {

	public static final String USER_SESSION_KEY = "user_session_key";

	private Properties properties = new Properties();

	public String getProperty(String key) {
		return properties.getProperty(key);
	}

	public void setProperty(String key, String value) {
		properties.setProperty(key, value);
	}

}
