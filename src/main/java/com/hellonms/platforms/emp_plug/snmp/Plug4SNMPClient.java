/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_plug.snmp;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.event.ResponseListener;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Null;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

import com.hellonms.platforms.emp_core.server.log.BlackBox;
import com.hellonms.platforms.emp_core.server.log.LEVEL;
import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.share.error.ERROR_CODE;
import com.hellonms.platforms.emp_core.share.error.ERROR_CODE_CORE;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_util.string.UtilString;

/**
 * <p>
 * SNMP Client
 * </p>
 *
 * @since 1.6
 * @create 2015. 4. 3.
 * @modified 2015. 4. 3.
 * @author cchyun
 *
 */
public class Plug4SNMPClient {

	private class Model4Snmp {

		@SuppressWarnings("unused")
		private InetAddress host;

		@SuppressWarnings("unused")
		private int port;

		@SuppressWarnings("unused")
		private String community;

		private long send_time;

		private long recv_time;

		public Model4Snmp(InetAddress host, int port, String community) {
			this.host = host;
			this.port = port;
			this.community = community;
			this.send_time = System.currentTimeMillis();
		}

	}

	private static final OID testSessionOid = new OID("1.3.6.1.2.1.1");

	protected final Snmp session;

	protected static final BlackBox blackBox = new BlackBox(Plug4SNMPClient.class);

	public Plug4SNMPClient() throws EmpException {
		try {
			session = new Snmp(new DefaultUdpTransportMapping());
			session.listen();
		} catch (IOException e) {
			throw new EmpException(e, ERROR_CODE_CORE.NETOWRK_IO);
		}
	}

	/**
	 * <p>
	 * 통신상태 점검
	 * </p>
	 *
	 * @param context
	 * @param address
	 * @param port
	 * @param version
	 * @param read_community
	 * @param timeout
	 * @param retry
	 * @return
	 * @throws EmpException
	 */
	public int testSession(EmpContext context, InetAddress address, int port, SNMP_VERSION version, String read_community, int timeout, int retry) throws EmpException {
		try {
			VariableBinding vb = new VariableBinding(testSessionOid);

			PDU sendPdu = new PDU();
			sendPdu.setType(PDU.GETNEXT);
			sendPdu.addOID(vb);

			UdpAddress udpAddress = new UdpAddress(address, port);

			CommunityTarget target = new CommunityTarget();
			target.setCommunity(new OctetString(read_community));
			target.setAddress(udpAddress);
			target.setTimeout(timeout);
			target.setRetries(retry);
			target.setVersion(version == SNMP_VERSION.V1 ? SnmpConstants.version1 : SnmpConstants.version2c);

			long send_time = System.currentTimeMillis();
			long recv_time = 0;

			ResponseEvent responseEvent = session.send(sendPdu, target);
			PDU recvPDU = responseEvent.getResponse();
			try {
				if (recvPDU != null && recvPDU.getErrorIndex() == 0) {
					if (send_time < recv_time) {
						send_time = recv_time;
					}
					recv_time = System.currentTimeMillis();
				}
			} finally {
				if (recvPDU != null) {
					recvPDU.clear();
				}
			}

			int response_time = (int) Math.max(recv_time - send_time, -1);
			boolean result = 0 <= response_time;
			if (blackBox.isEnabledFor(LEVEL.UseCase)) {
				if (result) {
					blackBox.log(LEVEL.UseCase, context, UtilString.format("SNMP {} ok response_time {} ms", address.getHostAddress(), response_time));
				} else {
					blackBox.log(LEVEL.UseCase, context, UtilString.format("SNMP {} fail time_out {} ms", address.getHostAddress(), timeout));
				}
			}

			return response_time;
		} catch (IOException e) {
			throw new EmpException(e, ERROR_CODE_CORE.NETOWRK_IO);
		} catch (Exception e) {
			throw new EmpException(e, ERROR_CODE.ERROR_UNKNOWN);
		} finally {
		}
	}

	/**
	 * <p>
	 * 통신상태 점검
	 * </p>
	 *
	 * @param context
	 * @param addresses
	 * @param port
	 * @param version
	 * @param read_community
	 * @param timeout
	 * @param retry
	 * @return
	 * @throws EmpException
	 */
	public int[] testListSession(EmpContext context, InetAddress[] addresses, int port, SNMP_VERSION version, String read_community, int timeout, int retry) throws EmpException {
		final Map<InetAddress, Model4Snmp> testSessionMap = new HashMap<InetAddress, Model4Snmp>();
		PDU sendPdu = new PDU();
		ResponseListener responseListener = new ResponseListener() {
			@Override
			public void onResponse(ResponseEvent responseEvent) {
				PDU recvPDU = responseEvent.getResponse();

				if (recvPDU != null && recvPDU.getErrorIndex() == 0) {
					Model4Snmp testSession = testSessionMap.get(((UdpAddress) responseEvent.getPeerAddress()).getInetAddress());
					if (testSession != null && testSession.recv_time == 0) {
						testSession.recv_time = System.currentTimeMillis();
					}
				}
			}
		};

		try {
			VariableBinding vb = new VariableBinding(testSessionOid);

			for (int i = 0; i < addresses.length; i++) {
				sendPdu.setType(PDU.GETNEXT);
				sendPdu.addOID(vb);

				UdpAddress udpAddress = new UdpAddress(addresses[i], port);
				CommunityTarget target = new CommunityTarget();
				target.setAddress(udpAddress);
				target.setCommunity(new OctetString(read_community));
				target.setTimeout(timeout);
				target.setRetries(retry);
				target.setVersion(version == SNMP_VERSION.V1 ? SnmpConstants.version1 : SnmpConstants.version2c);

				testSessionMap.put(addresses[i], new Model4Snmp(addresses[i], port, read_community));

				session.send(sendPdu, target, null, responseListener);
			}

			// wait for finish
			long timestamp = System.currentTimeMillis() + timeout;
			while (System.currentTimeMillis() < timestamp) {
				try {
					Thread.sleep(100L);
				} catch (InterruptedException e) {
					if (blackBox.isEnabledFor(LEVEL.Fatal)) {
						blackBox.log(LEVEL.Fatal, null, e);
					}
				}
				boolean do_wait = false;
				for (InetAddress host : addresses) {
					Model4Snmp testSession = testSessionMap.get(host);
					if (testSession != null && testSession.recv_time == 0) {
						do_wait = true;
						break;
					}
				}
				if (!do_wait) {
					break;
				}
			}

			int[] response_times = new int[addresses.length];

			for (int i = 0; i < addresses.length; i++) {
				Model4Snmp testSession = testSessionMap.get(addresses[i]);
				response_times[i] = (int) Math.max(testSession.recv_time - testSession.send_time, -1);
				boolean result = 0 <= response_times[i];
				if (blackBox.isEnabledFor(LEVEL.UseCase)) {
					if (result) {
						blackBox.log(LEVEL.UseCase, context, UtilString.format("SNMP {} ok response_time {} ms", addresses[i].getHostAddress(), response_times[i]));
					} else {
						blackBox.log(LEVEL.UseCase, context, UtilString.format("SNMP {} fail time_out {} ms", addresses[i].getHostAddress(), timeout));
					}
				}
			}

			return response_times;
		} catch (IOException e) {
			throw new EmpException(e, ERROR_CODE_CORE.NETOWRK_IO);
		} catch (Exception e) {
			throw new EmpException(e, ERROR_CODE.ERROR_UNKNOWN);
		} finally {
			session.cancel(sendPdu, responseListener);
		}
	}

	/**
	 * <p>
	 * Insert Description of snmpRequst
	 * </p>
	 *
	 * @param context
	 * @param address
	 * @param port
	 * @param version
	 * @param read_community
	 * @param write_community
	 * @param timeout
	 * @param retry
	 * @param requests
	 * @return
	 * @throws EmpException
	 */
	public PlugResponseSNMPIf[] snmpRequst(EmpContext context, InetAddress address, int port, SNMP_VERSION version, String read_community, String write_community, int timeout, int retry, PlugRequestSNMPIf[] requests) throws EmpException {
		PlugResponseSNMPIf[] responses = new PlugResponseSNMPIf[requests.length];
		for (int i = 0; i < requests.length; i++) {
			PlugRequestSNMPIf request = requests[i];
			if (request instanceof PlugRequestSNMPGet) {
				responses[i] = snmpGet(context, address, port, version, read_community, timeout, retry, (PlugRequestSNMPGet) request);
			} else if (request instanceof PlugRequestSNMPWalk) {
				responses[i] = snmpWalk(context, address, port, version, read_community, timeout, retry, (PlugRequestSNMPWalk) request);
			} else if (request instanceof PlugRequestSNMPSet) {
				responses[i] = snmpSet(context, address, port, version, write_community, timeout, retry, (PlugRequestSNMPSet) request);
			}
		}
		return responses;
	}

	/**
	 * <p>
	 * SNMP 명령 수행
	 * </p>
	 *
	 * @param context
	 * @param address
	 * @param port
	 * @param version
	 * @param read_community
	 * @param timeout
	 * @param retry
	 * @param request
	 * @return
	 * @throws EmpException
	 */
	public PlugResponseSNMPIf snmpGet(EmpContext context, InetAddress address, int port, SNMP_VERSION version, String read_community, int timeout, int retry, PlugRequestSNMPGet request) throws EmpException {
		try {
			if (blackBox.isEnabledFor(LEVEL.UseCase)) {
				blackBox.log(LEVEL.UseCase, context, UtilString.format("snmpGet request: {}:{} {} {}", address, port, version, request));
			}

			PlugResponseSNMP response = new PlugResponseSNMP();

			OID[] oids = request.getOids();
			VariableBinding[] vbs = new VariableBinding[oids.length];
			for (int i = 0; i < vbs.length; i++) {
				vbs[i] = new VariableBinding(new OID(oids[i].getValue()));
			}

			PDU sendPdu = new PDU();
			sendPdu.setType(PDU.GET);
			sendPdu.addAllOIDs(vbs);

			UdpAddress udpAddress = new UdpAddress(address, port);

			CommunityTarget target = new CommunityTarget();
			target.setCommunity(new OctetString(read_community));
			target.setAddress(udpAddress);
			target.setTimeout(timeout);
			target.setRetries(retry);
			target.setVersion(version == SNMP_VERSION.V1 ? SnmpConstants.version1 : SnmpConstants.version2c);

			ResponseEvent responseEvent = session.send(sendPdu, target);
			PDU recvPDU = responseEvent.getResponse();
			try {
				if (recvPDU == null) {
					throw new EmpException(ERROR_CODE_SNMP.ERROR_SNMP_RESPONSE_TIMEOUT, address, port, read_community, timeout, retry);
				} else if (recvPDU.getErrorIndex() != 0) {
					throw new EmpException(ERROR_CODE_SNMP.ERROR_SNMP_RESPONSE_ERROR, recvPDU.getErrorIndex(), recvPDU.getErrorStatusText());
				} else {
					for (Object object : recvPDU.getVariableBindings()) {
						VariableBinding vb = (VariableBinding) object;
						if (vb.getVariable() instanceof Null) {
							// throw new EmpException(ERROR_CODE_SNMP.ERROR_SNMP_RESPONSE_NULL, address, port, vb.getOid().toString());
						} else {
							response.add(vb);
						}
					}
				}
			} finally {
				if (recvPDU != null) {
					recvPDU.clear();
				}
			}

			if (blackBox.isEnabledFor(LEVEL.UseCase)) {
				blackBox.log(LEVEL.UseCase, context, UtilString.format("snmpGet response: {}:{} {} {}", address, port, version, response));
			}
			return response;
		} catch (EmpException e) {
			throw e;
		} catch (IOException e) {
			throw new EmpException(e, ERROR_CODE_CORE.NETOWRK_IO);
		} catch (Exception e) {
			throw new EmpException(e, ERROR_CODE.ERROR_UNKNOWN);
		} finally {
		}
	}

	/**
	 * <p>
	 * Insert Description of snmpWalk
	 * </p>
	 *
	 * @param context
	 * @param address
	 * @param port
	 * @param version
	 * @param read_community
	 * @param timeout
	 * @param retry
	 * @param request
	 * @return
	 * @throws EmpException
	 */
	public PlugResponseSNMPIf snmpWalk(EmpContext context, InetAddress address, int port, SNMP_VERSION version, String read_community, int timeout, int retry, PlugRequestSNMPWalk request) throws EmpException {
		try {
			if (blackBox.isEnabledFor(LEVEL.UseCase)) {
				blackBox.log(LEVEL.UseCase, context, UtilString.format("snmpWalk request: {}:{} {} {}", address, port, version, request));
			}

			PlugResponseSNMP response = new PlugResponseSNMP();

			OID[] oids = request.getOids();
			List<VariableBinding> beginWalkList = new ArrayList<VariableBinding>();
			List<VariableBinding> currentWalkList = new ArrayList<VariableBinding>();
			for (int i = 0; i < oids.length; i++) {
				beginWalkList.add(new VariableBinding(new OID(oids[i])));
				currentWalkList.add(new VariableBinding(new OID(oids[i])));
			}

			UdpAddress udpAddress = new UdpAddress(address, port);

			CommunityTarget target = new CommunityTarget();
			target.setCommunity(new OctetString(read_community));
			target.setAddress(udpAddress);
			target.setTimeout(timeout);
			target.setRetries(retry);
			target.setVersion(version == SNMP_VERSION.V1 ? SnmpConstants.version1 : SnmpConstants.version2c);

			while (currentWalkList.size() > 0) {
				PDU sendPdu = new PDU();
				sendPdu.setType(PDU.GETNEXT);
				sendPdu.addAllOIDs(currentWalkList.toArray(new VariableBinding[0]));

				ResponseEvent responseEvent = session.send(sendPdu, target);
				PDU recvPDU = responseEvent.getResponse();
				try {
					if (recvPDU == null) {
						throw new EmpException(ERROR_CODE_SNMP.ERROR_SNMP_RESPONSE_TIMEOUT, address, port, read_community, timeout, retry);
					} else if (recvPDU.getErrorIndex() != 0) {
						throw new EmpException(ERROR_CODE_SNMP.ERROR_SNMP_RESPONSE_ERROR, recvPDU.getErrorIndex(), recvPDU.getErrorStatusText());
					} else {
						VariableBinding[] beginArray = beginWalkList.toArray(new VariableBinding[0]);
						beginWalkList.clear();
						currentWalkList.clear();
						int index = 0;
						for (Object object : recvPDU.getVariableBindings()) {
							VariableBinding vb = (VariableBinding) object;
							if (beginArray.length > index && vb.getOid().startsWith(beginArray[index].getOid()) && !(vb.getVariable() instanceof Null)) {
								response.add(vb);
								beginWalkList.add(beginArray[index]);
								currentWalkList.add(vb);
							}
							index++;
						}
					}
				} finally {
					if (recvPDU != null) {
						recvPDU.clear();
					}
				}
			}

			if (blackBox.isEnabledFor(LEVEL.UseCase)) {
				blackBox.log(LEVEL.UseCase, context, UtilString.format("snmpWalk response: {}:{} {} {}", address, port, version, response));
			}
			return response;
		} catch (EmpException e) {
			throw e;
		} catch (IOException e) {
			throw new EmpException(e, ERROR_CODE_CORE.NETOWRK_IO);
		} catch (Exception e) {
			throw new EmpException(e, ERROR_CODE.ERROR_UNKNOWN);
		} finally {
		}
	}

	/**
	 * <p>
	 * Insert Description of snmpSet
	 * </p>
	 *
	 * @param context
	 * @param address
	 * @param port
	 * @param version
	 * @param write_community
	 * @param timeout
	 * @param retry
	 * @param request
	 * @return
	 * @throws EmpException
	 */
	public PlugResponseSNMPIf snmpSet(EmpContext context, InetAddress address, int port, SNMP_VERSION version, String write_community, int timeout, int retry, PlugRequestSNMPSet request) throws EmpException {
		try {
			if (blackBox.isEnabledFor(LEVEL.UseCase)) {
				blackBox.log(LEVEL.UseCase, context, UtilString.format("snmpSet request: {}:{} {} {}", address, port, version, request));
			}
			PlugResponseSNMP response = new PlugResponseSNMP();

			VariableBinding[] vbs = request.getVbs();

			PDU sendPdu = new PDU();
			sendPdu.setType(PDU.SET);
			sendPdu.addAll(vbs);

			UdpAddress udpAddress = new UdpAddress(address, port);

			CommunityTarget target = new CommunityTarget();
			target.setCommunity(new OctetString(write_community));
			target.setAddress(udpAddress);
			target.setTimeout(timeout);
			target.setRetries(retry);
			target.setVersion(version == SNMP_VERSION.V1 ? SnmpConstants.version1 : SnmpConstants.version2c);

			ResponseEvent responseEvent = session.send(sendPdu, target);
			PDU recvPDU = responseEvent.getResponse();
			try {
				if (recvPDU == null) {
					throw new EmpException(ERROR_CODE_SNMP.ERROR_SNMP_RESPONSE_TIMEOUT, address, port, write_community, timeout, retry);
				} else if (recvPDU.getErrorIndex() != 0) {
					throw new EmpException(ERROR_CODE_SNMP.ERROR_SNMP_RESPONSE_ERROR, recvPDU.getErrorIndex(), recvPDU.getErrorStatusText());
				} else {
					for (Object object : recvPDU.getVariableBindings()) {
						VariableBinding vb = (VariableBinding) object;
						if (vb.getVariable() instanceof Null) {
							// throw new EmpException(ERROR_CODE_SNMP.ERROR_SNMP_RESPONSE_NULL, address, port, vb.getOid().toString());
						} else {
							response.add(vb);
						}
					}
				}
			} finally {
				if (recvPDU != null) {
					recvPDU.clear();
				}
			}

			if (blackBox.isEnabledFor(LEVEL.UseCase)) {
				blackBox.log(LEVEL.UseCase, context, UtilString.format("snmpSet response: {}:{} {} {}", address, port, version, response));
			}
			return response;
		} catch (EmpException e) {
			throw e;
		} catch (IOException e) {
			throw new EmpException(e, ERROR_CODE_CORE.NETOWRK_IO);
		} catch (Exception e) {
			throw new EmpException(e, ERROR_CODE.ERROR_UNKNOWN);
		} finally {
		}
	}

	public void close() throws EmpException {
		try {
			session.close();
		} catch (IOException e) {
			throw new EmpException(e, ERROR_CODE_CORE.NETOWRK_IO);
		}
	}

}
