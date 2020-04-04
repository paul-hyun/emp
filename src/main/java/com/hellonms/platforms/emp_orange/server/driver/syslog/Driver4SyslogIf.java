/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.server.driver.syslog;

import com.hellonms.platforms.emp_core.server.driver.DriverIf;
import com.hellonms.platforms.emp_core.server.transaction.EmpContext;

/**
 * <p>
 * Driver4Syslog
 * </p>
 *
 * @since 1.6
 * @create 2015. 7. 10.
 * @modified 2015. 7. 10.
 * @author cchyun
 *
 */
public interface Driver4SyslogIf extends DriverIf {

	public interface Driver4SyslogListenerIf {

		public void handleSyslog(EmpContext context, String address, int port, String syslog);

	}

	/**
	 * @param listener
	 */
	public void addListener(Driver4SyslogListenerIf listener);

	/**
	 * @param listener
	 */
	public void removeListener(Driver4SyslogListenerIf listener);

}
