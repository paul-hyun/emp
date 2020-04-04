/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_plug.snmp;

import java.io.IOException;
import java.net.InetAddress;

import org.snmp4j.CommandResponder;
import org.snmp4j.CommandResponderEvent;
import org.snmp4j.PDU;
import org.snmp4j.PDUv1;
import org.snmp4j.Snmp;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.TimeTicks;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

import com.hellonms.platforms.emp_core.server.log.BlackBox;
import com.hellonms.platforms.emp_core.server.log.LEVEL;
import com.hellonms.platforms.emp_core.share.error.ERROR_CODE_CORE;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_util.queue.UtilQueue;
import com.hellonms.platforms.emp_util.string.UtilString;

/**
 * <p>
 * SNMP Server
 * </p>
 *
 * @since 1.6
 * @create 2015. 4. 3.
 * @modified 2015. 4. 3.
 * @author cchyun
 *
 */
public class Plug4SNMPTrapServer {

	public interface Plug4SNMPTrapServerHandlerIf {

		public void handleTrap(InetAddress address, int port, SNMP_VERSION version, String community, PlugNotifySNMPTrap trap);

	}

	private class TrapValue {

		private final InetAddress host;

		private final int port;

		private SNMP_VERSION version;

		private String community;

		private PlugNotifySNMPTrap trap;

		public TrapValue(InetAddress host, int port, SNMP_VERSION version, String community, long sysUpTime, OID trapOid, int genericTrap, int specificTrap, VariableBinding[] vbs) {
			this.host = host;
			this.port = port;
			this.version = version;
			this.community = community;

			this.trap = new PlugNotifySNMPTrap();
			this.trap.setSysUpTime(sysUpTime);
			this.trap.setTrapOid(trapOid);
			this.trap.setGenericTrap(genericTrap);
			this.trap.setSpecificTrap(specificTrap);
			this.trap.setVbs(vbs);
		}

	}

	private class TrapCommandResponder implements CommandResponder {

		@Override
		public void processPdu(CommandResponderEvent event) {
			try {
				if (blackBox.isEnabledFor(LEVEL.UseCase)) {
					blackBox.log(LEVEL.UseCase, null, UtilString.format("recv trap {}", event));
				}

				PDU pdu = event.getPDU();
				UdpAddress udpAddress = (UdpAddress) event.getPeerAddress();
				String community = new String(event.getSecurityName());

				if (pdu != null && pdu.getType() == PDU.TRAP) {
					OID trapOid = null;
					Long sysUpTime = null;
					VariableBinding[] vbs = pdu.getVariableBindings().toArray(new VariableBinding[0]);

					for (VariableBinding vb : vbs) {
						if (vb.getOid().equals(SnmpConstants.snmpTrapOID)) {
							trapOid = (OID) vb.getVariable();
						} else if (vb.getOid().equals(SnmpConstants.sysUpTime)) {
							sysUpTime = ((TimeTicks) vb.getVariable()).getValue();
						}
					}

					if (trapOid != null && sysUpTime != null) {
						trapQueue.push(new TrapValue(udpAddress.getInetAddress(), udpAddress.getPort(), SNMP_VERSION.V2c, community, sysUpTime, trapOid, 0, 0, vbs));
					} else {
						if (blackBox.isEnabledFor(LEVEL.Fatal)) {
							blackBox.log(LEVEL.Fatal, null, UtilString.format("discard trap from={} community={}, snmpTrapOID or sysUpTime null", udpAddress, community));
						}
					}
				} else if (pdu != null && pdu.getType() == PDU.V1TRAP) {
					PDUv1 v1Pdu = (PDUv1) pdu;

					long sysUpTime = v1Pdu.getTimestamp();
					OID trapOid = new OID(v1Pdu.getEnterprise().getValue());
					int genericTrap = v1Pdu.getGenericTrap();
					int specificTrap = v1Pdu.getSpecificTrap();
					VariableBinding[] vbs = pdu.getVariableBindings().toArray(new VariableBinding[0]);

					trapQueue.push(new TrapValue(udpAddress.getInetAddress(), udpAddress.getPort(), SNMP_VERSION.V1, community, sysUpTime, trapOid, genericTrap, specificTrap, vbs));
				}

				if (pdu != null) {
					pdu.clear();
				}
			} catch (Exception ex) {
				if (blackBox.isEnabledFor(LEVEL.Fatal)) {
					blackBox.log(LEVEL.Fatal, null, ex, UtilString.format("trap processing error {}", event));
				}
			}
		}

	}

	private class TrapServerHandler implements Runnable {

		@Override
		public void run() {
			while (true) {
				try {
					TrapValue trapValue = trapQueue.pop();
					trapHandler.handleTrap(trapValue.host, trapValue.port, trapValue.version, trapValue.community, trapValue.trap);
				} catch (Throwable ex) {
					if (blackBox.isEnabledFor(LEVEL.Fatal)) {
						blackBox.log(LEVEL.Fatal, null, ex, UtilString.format("trap processing error"));
					}
				}
			}
		}

	}

	@SuppressWarnings("unused")
	private int port;

	private Snmp session;

	private Plug4SNMPTrapServerHandlerIf trapHandler;

	private UtilQueue<TrapValue> trapQueue = new UtilQueue<TrapValue>();

	private static final BlackBox blackBox = new BlackBox(Plug4SNMPTrapServer.class);

	public Plug4SNMPTrapServer(int port, Plug4SNMPTrapServerHandlerIf trapHandler) throws EmpException {
		try {
			this.port = port;
			this.trapHandler = trapHandler;

			session = new Snmp(new DefaultUdpTransportMapping(new UdpAddress(port)));
			session.addCommandResponder(new TrapCommandResponder());
			session.listen();

			Thread thread = new Thread(new TrapServerHandler(), "Plug4SnmpTrapServer::Handler");
			thread.setDaemon(true);
			thread.start();
		} catch (IOException e) {
			throw new EmpException(e, ERROR_CODE_CORE.NETOWRK_IO);
		}
	}

}
