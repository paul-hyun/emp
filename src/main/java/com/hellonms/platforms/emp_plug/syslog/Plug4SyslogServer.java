/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_plug.syslog;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import com.hellonms.platforms.emp_core.server.log.BlackBox;
import com.hellonms.platforms.emp_core.server.log.LEVEL;
import com.hellonms.platforms.emp_core.share.error.ERROR_CODE_CORE;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_util.queue.UtilQueue;
import com.hellonms.platforms.emp_util.string.UtilString;

/**
 * <p>
 * Syslog Server
 * </p>
 *
 * @since 1.6
 * @create 2015. 7. 10.
 * @modified 2015. 7. 10.
 * @author cchyun
 *
 */
public class Plug4SyslogServer {

	public interface Plug4SyslogServerEventHandlerIf {

		public void handleSyslog(InetAddress address, int port, String syslog);

	}

	private class SyslogValue {

		private final InetAddress address;

		private final int port;

		private final String syslog;

		public SyslogValue(InetAddress address, int port, String syslog) {
			this.address = address;
			this.port = port;
			this.syslog = syslog;
		}

	}

	private class Plug4SyslogServerReceiver implements Runnable {

		public void run() {
			byte[] buf = new byte[1024 * 8];
			DatagramPacket packet = new DatagramPacket(buf, buf.length);
			while (!socket.isClosed()) {
				try {
					socket.receive(packet);
					InetAddress host = packet.getAddress();
					int port = packet.getPort();
					String syslog = new String(packet.getData(), 0, packet.getLength());

					if (blackBox.isEnabledFor(LEVEL.UseCase)) {
						blackBox.log(LEVEL.UseCase, null, UtilString.format("recv syslog from={}:{} syslog={}", host, port, syslog));
					}

					syslogQueue.push(new SyslogValue(host, port, syslog));
				} catch (Exception e) {
					if (blackBox.isEnabledFor(LEVEL.Fatal)) {
						blackBox.log(LEVEL.Fatal, null, e, "syslog processing error");
					}
				}
			}
		}

	}

	private class Plug4SyslogServerHandler implements Runnable {

		public void run() {
			while (true) {
				try {
					SyslogValue syslogValue = syslogQueue.pop();
					syslogHandler.handleSyslog(syslogValue.address, syslogValue.port, syslogValue.syslog);
				} catch (Exception ex) {
					if (blackBox.isEnabledFor(LEVEL.Fatal)) {
						blackBox.log(LEVEL.Fatal, null, ex, "syslog processing error");
					}
				}
			}
		}

	}

	@SuppressWarnings("unused")
	private int port = 514;

	private DatagramSocket socket;

	private Plug4SyslogServerEventHandlerIf syslogHandler;

	private UtilQueue<SyslogValue> syslogQueue = new UtilQueue<SyslogValue>();

	private static BlackBox blackBox = new BlackBox(Plug4SyslogServer.class);

	public Plug4SyslogServer(int port, Plug4SyslogServerEventHandlerIf syslogHandler) throws EmpException {
		try {
			this.port = port;
			this.socket = new DatagramSocket(port);
			this.syslogHandler = syslogHandler;

			new Thread(new Plug4SyslogServerReceiver(), "Plug4SyslogServer::Receiver").start();
			new Thread(new Plug4SyslogServerHandler(), "Plug4SyslogServer::Handler").start();
		} catch (IOException e) {
			throw new EmpException(e, ERROR_CODE_CORE.NETOWRK_IO);
		}
	}

}
