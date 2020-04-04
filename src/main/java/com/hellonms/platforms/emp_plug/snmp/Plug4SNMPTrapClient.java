/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_plug.snmp;

import java.io.IOException;
import java.net.InetAddress;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.PDUv1;
import org.snmp4j.Snmp;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.TimeTicks;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

import com.hellonms.platforms.emp_core.share.error.ERROR_CODE_CORE;
import com.hellonms.platforms.emp_core.share.error.EmpException;

/**
 * <p>
 * SNMP Trap Client
 * </p>
 *
 * @since 1.6
 * @create 2015. 4. 3.
 * @modified 2015. 4. 3.
 * @author cchyun
 *
 */
public class Plug4SNMPTrapClient {

	private final Snmp session;

	public Plug4SNMPTrapClient() throws EmpException {
		try {
			this.session = new Snmp(new DefaultUdpTransportMapping());
			this.session.listen();
		} catch (IOException e) {
			throw new EmpException(e, ERROR_CODE_CORE.NETOWRK_IO);
		}
	}

	public void snmpTrapV1(InetAddress host, int port, String community, OID trapOid, long sysUpTime, int genericTrap, int specificTrap, VariableBinding[] vbs) throws EmpException {
		try {
			long sysUpTimeMn = (System.currentTimeMillis() - sysUpTime) / 10;

			CommunityTarget communityTarget = new CommunityTarget();
			communityTarget.setAddress(new UdpAddress(host, port));
			communityTarget.setCommunity(new OctetString(community));
			communityTarget.setVersion(SnmpConstants.version1);

			PDUv1 pdu = new PDUv1();
			pdu.setType(PDU.V1TRAP);
			pdu.setTimestamp(sysUpTimeMn);
			pdu.setEnterprise(trapOid);
			pdu.setGenericTrap(genericTrap);
			pdu.setSpecificTrap(specificTrap);
			pdu.addAll(vbs);

			session.send(pdu, communityTarget);
		} catch (IOException e) {
			throw new EmpException(e, ERROR_CODE_CORE.NETOWRK_IO);
		}
	}

	public void snmpTrapV2(InetAddress host, int port, String community, OID trapOid, long sysUpTime, VariableBinding[] vbs) throws EmpException {
		try {
			long sysUpTimeMn = (System.currentTimeMillis() - sysUpTime) / 10;

			CommunityTarget communityTarget = new CommunityTarget();
			communityTarget.setAddress(new UdpAddress(host, port));
			communityTarget.setCommunity(new OctetString(community));
			communityTarget.setVersion(SnmpConstants.version2c);

			PDU pdu = new PDU();
			pdu.setType(PDU.TRAP);
			pdu.add(new VariableBinding(SnmpConstants.snmpTrapOID, trapOid)); // trapOid
			pdu.add(new VariableBinding(SnmpConstants.sysUpTime, new TimeTicks(sysUpTimeMn))); // sysUpTime
			pdu.addAll(vbs);

			session.notify(pdu, communityTarget);
		} catch (IOException e) {
			throw new EmpException(e, ERROR_CODE_CORE.NETOWRK_IO);
		}
	}

}
