/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.server.driver.syslog;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import com.hellonms.platforms.emp_core.server.driver.DriverIf;
import com.hellonms.platforms.emp_core.server.log.BlackBox;
import com.hellonms.platforms.emp_core.server.log.LEVEL;
import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_plug.syslog.Plug4SyslogServer;
import com.hellonms.platforms.emp_plug.syslog.Plug4SyslogServer.Plug4SyslogServerEventHandlerIf;
import com.hellonms.platforms.emp_util.string.UtilString;

/**
 * <p>
 * Syslog
 * </p>
 *
 * @since 1.6
 * @create 2015. 7. 10.
 * @modified 2015. 7. 10.
 * @author cchyun
 *
 */
public class Driver4Syslog implements Driver4SyslogIf {

	private class Plug4SyslogServerEventHandler implements Plug4SyslogServerEventHandlerIf {

		@Override
		public void handleSyslog(InetAddress host, int port, String syslog) {
			EmpContext context = new EmpContext(Driver4Syslog.this);
			try {
				for (Driver4SyslogListenerIf listener : listeners) {
					try {
						listener.handleSyslog(context, host.getHostAddress(), port, syslog);
					} catch (Exception e) {
						if (blackBox.isEnabledFor(LEVEL.Fatal)) {
							blackBox.log(LEVEL.Fatal, context, e, UtilString.format("Syslog handleSyslog fail {} from {}:{}", syslog, host, port));
						}
					}
				}
			} catch (Exception e) {
				if (blackBox.isEnabledFor(LEVEL.Fatal)) {
					blackBox.log(LEVEL.Fatal, null, e);
				}
			} finally {
				context.close();
			}
		}

	}

	private int[] syslog_ports = {};

	private Plug4SyslogServerEventHandler handler = new Plug4SyslogServerEventHandler();

	private List<Driver4SyslogListenerIf> listeners = new ArrayList<Driver4SyslogListenerIf>();

	private static BlackBox blackBox = new BlackBox(Driver4Syslog.class);

	@Override
	public Class<? extends DriverIf> getDefine_class() {
		return Driver4SyslogIf.class;
	}

	public void setSyslog_ports(int[] syslog_ports) {
		this.syslog_ports = syslog_ports;
	}

	@Override
	public void initialize(EmpContext context) throws EmpException {
		for (int syslog_port : syslog_ports) {
			new Plug4SyslogServer(syslog_port, handler);
		}
	}

	@Override
	public void dispose(EmpContext context) throws EmpException {
	}

	@Override
	public void addListener(Driver4SyslogListenerIf listener) {
		listeners.add(listener);
	}

	@Override
	public void removeListener(Driver4SyslogListenerIf listener) {
		listeners.remove(listener);
	}

}
