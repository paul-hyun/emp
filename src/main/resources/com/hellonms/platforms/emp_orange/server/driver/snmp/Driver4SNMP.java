/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.server.driver.snmp;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.snmp4j.smi.Counter32;
import org.snmp4j.smi.Counter64;
import org.snmp4j.smi.Gauge32;
import org.snmp4j.smi.Integer32;
import org.snmp4j.smi.IpAddress;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.TimeTicks;
import org.snmp4j.smi.VariableBinding;

import com.hellonms.platforms.emp_core.server.driver.DriverIf;
import com.hellonms.platforms.emp_core.server.log.BlackBox;
import com.hellonms.platforms.emp_core.server.log.LEVEL;
import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_orange.share.error.ERROR_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.snmp.Model4NeSessionDiscoveryFilterSNMP;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.snmp.Model4NeSessionDiscoveryResultSNMP;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.snmp.Model4NeSessionNotificationSNMP;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.snmp.Model4NeSessionRequestSNMPAt;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.snmp.Model4NeSessionRequestSNMPGet;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.snmp.Model4NeSessionRequestSNMPSet;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.snmp.Model4NeSessionRequestSNMPWalk;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.snmp.Model4NeSessionResponseSNMP;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.snmp.Model4NeSessionResponseSNMPIf;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.snmp.Model4NeSessionSNMP;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.snmp.Model4NeSessionSNMP.SNMP_VERSION;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.snmp.SNMP_OID;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.snmp.SNMP_TYPE;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.snmp.SNMP_VALUE;
import com.hellonms.platforms.emp_plug.snmp.Plug4SNMPClient;
import com.hellonms.platforms.emp_plug.snmp.Plug4SNMPTrapServer;
import com.hellonms.platforms.emp_plug.snmp.Plug4SNMPTrapServer.Plug4SNMPTrapServerHandlerIf;
import com.hellonms.platforms.emp_plug.snmp.PlugNotifySNMPTrap;
import com.hellonms.platforms.emp_plug.snmp.PlugRequestSNMPGet;
import com.hellonms.platforms.emp_plug.snmp.PlugRequestSNMPIf;
import com.hellonms.platforms.emp_plug.snmp.PlugRequestSNMPSet;
import com.hellonms.platforms.emp_plug.snmp.PlugRequestSNMPWalk;
import com.hellonms.platforms.emp_plug.snmp.PlugResponseSNMP;
import com.hellonms.platforms.emp_plug.snmp.PlugResponseSNMPIf;
import com.hellonms.platforms.emp_util.network.UtilInetAddress;
import com.hellonms.platforms.emp_util.pool.UtilPool;
import com.hellonms.platforms.emp_util.pool.UtilPool.UtilPoolFactoryIf;
import com.hellonms.platforms.emp_util.string.UtilString;

/**
 * <p>
 * SNMP 통신 Driver
 * </p>
 *
 * @since 1.6
 * @create 2015. 3. 30.
 * @modified 2015. 3. 30.
 * @author cchyun
 *
 */
public class Driver4SNMP implements Driver4SNMPIf {

	private class Plug4SNMPTrapServerHandler implements Plug4SNMPTrapServerHandlerIf {

		@Override
		public void handleTrap(InetAddress address, int port, SNMP_VERSION version, String community, PlugNotifySNMPTrap trap) {
			address = getTrap_addresses(address, community);
			community = getTrap_community(address, community);
			Driver4SNMP.this.handleTrap(address, port, version, community, trap);
		}

	}

	private int[] trap_ports = {};

	private Driver4SNMPListenerIf[] listeners = {};

	private Plug4SNMPTrapServerHandler trapHandler = new Plug4SNMPTrapServerHandler();

	private UtilPool<Plug4SNMPClient> pool;

	private static final BlackBox blackBox = new BlackBox(Driver4SNMP.class);

	@Override
	public Class<? extends DriverIf> getDefine_class() {
		return Driver4SNMPIf.class;
	}

	public void setTrap_ports(int[] trap_ports) {
		this.trap_ports = trap_ports;
	}

	@Override
	public void initialize(EmpContext context) throws EmpException {
		pool = new UtilPool<Plug4SNMPClient>(32, new UtilPoolFactoryIf<Plug4SNMPClient>() {
			@Override
			public Plug4SNMPClient createObject() throws EmpException {
				return Driver4SNMP.this.createSNMPClient();
			}
		});

		for (int trap_port : trap_ports) {
			new Plug4SNMPTrapServer(trap_port, trapHandler);
		}
	}

	@Override
	public void dispose(EmpContext context) throws EmpException {
	}

	@Override
	public Model4NeSessionSNMP testNeSession(EmpContext context, Model4NeSessionSNMP ne_session) throws EmpException {
		Plug4SNMPClient snmpClient = borrowSNMPClient();
		try {
			InetAddress address = getInetAddresses(ne_session.getAddress(), ne_session.getRead_community(), ne_session.getWrite_community());
			String read_community = getRead_community(ne_session.getAddress(), ne_session.getRead_community(), ne_session.getWrite_community());
			int response_time = snmpClient.testSession(context, address, ne_session.getPort(), ne_session.getVersion(), read_community, ne_session.getTimeout(), ne_session.getRetry());

			ne_session = ne_session.copy();
			ne_session.setResponse_time(response_time);
			ne_session.setNe_session_state(0 <= response_time);
			ne_session.setNe_session_state_time(new Date());

			return ne_session;
		} finally {
			returnSNMPClient(snmpClient);
		}
	}

	@Override
	public Model4NeSessionResponseSNMPIf[] execute(EmpContext context, Model4NeSessionSNMP ne_session, Model4NeSessionRequestSNMPAt[] requests) throws EmpException {
		Plug4SNMPClient snmpClient = borrowSNMPClient();
		try {
			InetAddress address = getInetAddresses(ne_session.getAddress(), ne_session.getRead_community(), ne_session.getWrite_community());
			String read_community = getRead_community(ne_session.getAddress(), ne_session.getRead_community(), ne_session.getWrite_community());
			String write_community = getWrite_community(ne_session.getAddress(), ne_session.getRead_community(), ne_session.getWrite_community());
			return toNeSessionResponse(context, snmpClient.snmpRequst(context, address, ne_session.getPort(), ne_session.getVersion(), read_community, write_community, ne_session.getTimeout(), ne_session.getRetry(), toPlugRequest(context, requests)));
		} finally {
			returnSNMPClient(snmpClient);
		}
	}

	protected InetAddress getInetAddresses(String address, String read_community, String write_community) throws EmpException {
		return UtilInetAddress.getInetAddresses(address);
	}

	protected String getRead_community(String address, String read_community, String write_community) throws EmpException {
		return read_community;
	}

	protected String getWrite_community(String address, String read_community, String write_community) throws EmpException {
		return write_community;
	}

	protected InetAddress getTrap_addresses(InetAddress address, String community) {
		return address;
	}

	protected String getTrap_community(InetAddress address, String community) {
		return community;
	}

	@Override
	public Model4NeSessionDiscoveryResultSNMP[] discoveryListNeSession(EmpContext context, Model4NeSessionDiscoveryFilterSNMP ne_session_discovery_filter) throws EmpException {
		int count = 32;
		Plug4SNMPClient[] snmpClients = new Plug4SNMPClient[4];
		try {
			for (int i = 0; i < snmpClients.length; i++) {
				snmpClients[i] = borrowSNMPClient();
			}
			int snmp_index = 0;

			InetAddress[] addresses = UtilInetAddress.getInetAddresses(InetAddress.getByName(ne_session_discovery_filter.getHost()), ne_session_discovery_filter.getCount());
			Map<String, Integer> response_map = new LinkedHashMap<String, Integer>();
			for (int i = 0; i < addresses.length; i += count) {
				InetAddress[] addresses_temp = new InetAddress[Math.min(count, addresses.length - i)];
				System.arraycopy(addresses, i, addresses_temp, 0, addresses_temp.length);
				int[] response_times = snmpClients[snmp_index++ % snmpClients.length].testListSession(context, addresses_temp, ne_session_discovery_filter.getPort(), ne_session_discovery_filter.getVersion(), ne_session_discovery_filter.getRead_community(), ne_session_discovery_filter.getTimeout(), ne_session_discovery_filter.getRetry());
				for (int j = 0; j < addresses_temp.length; j++) {
					if (0 <= response_times[j]) {
						response_map.put(addresses_temp[j].getHostAddress(), response_times[j]);
					}
				}
			}

			Model4NeSessionDiscoveryResultSNMP[] ne_session_discovery_results = new Model4NeSessionDiscoveryResultSNMP[addresses.length];
			for (int i = 0; i < addresses.length; i++) {
				Integer response_time = response_map.get(addresses[i].getHostAddress());
				ne_session_discovery_results[i] = ne_session_discovery_filter.toNeSessionDiscoveryResultSNMP(addresses[i].getHostAddress(), response_time == null ? -1 : response_time);
				if (ne_session_discovery_results[i].isSuccess() && ne_session_discovery_filter.getRequests() != null && 0 < ne_session_discovery_filter.getRequests().length) {
					PlugResponseSNMPIf[] snmp_responses = snmpClients[snmp_index++ % snmpClients.length].snmpRequst(context, UtilInetAddress.getInetAddresses(ne_session_discovery_results[i].getAddress()), ne_session_discovery_results[i].getPort(), ne_session_discovery_results[i].getVersion(), ne_session_discovery_results[i].getRead_community(), ne_session_discovery_results[i].getRead_community(), ne_session_discovery_results[i].getTimeout(), ne_session_discovery_results[i].getRetry(), toPlugRequest(context, ne_session_discovery_filter.getRequests()));
					ne_session_discovery_results[i].setResponses(toNeSessionResponse(context, snmp_responses));
				}
			}
			return ne_session_discovery_results;
		} catch (IOException e) {
			throw new EmpException(ERROR_CODE_ORANGE.NETOWRK_IO, e);
		} finally {
			for (int i = 0; i < snmpClients.length; i++) {
				if (snmpClients[i] != null) {
					returnSNMPClient(snmpClients[i]);
				}
			}
		}
	}

	/**
	 * <p>
	 * 명령을 SNMP plug 형태로 변경
	 * </p>
	 *
	 * @param context
	 * @param requests
	 * @return
	 * @throws EmpException
	 */
	protected PlugRequestSNMPIf[] toPlugRequest(EmpContext context, Model4NeSessionRequestSNMPAt[] requests) throws EmpException {
		PlugRequestSNMPIf[] plug_requests = new PlugRequestSNMPIf[requests.length];
		for (int i = 0; i < requests.length; i++) {
			if (requests[i] instanceof Model4NeSessionRequestSNMPGet) {
				List<OID> oid_list = new ArrayList<OID>();
				for (SNMP_OID oid : ((Model4NeSessionRequestSNMPGet) requests[i]).getOIDs()) {
					oid_list.add(new OID(oid.getOid()));
				}
				plug_requests[i] = new PlugRequestSNMPGet(oid_list.toArray(new OID[0]));
			} else if (requests[i] instanceof Model4NeSessionRequestSNMPWalk) {
				List<OID> oid_list = new ArrayList<OID>();
				for (SNMP_OID oid : ((Model4NeSessionRequestSNMPWalk) requests[i]).getOIDs()) {
					oid_list.add(new OID(oid.getOid()));
				}
				plug_requests[i] = new PlugRequestSNMPWalk(oid_list.toArray(new OID[0]));
			} else if (requests[i] instanceof Model4NeSessionRequestSNMPSet) {
				List<VariableBinding> vb_list = new ArrayList<VariableBinding>();
				for (SNMP_VALUE value : ((Model4NeSessionRequestSNMPSet) requests[i]).getVALUEs()) {
					switch (value.getType()) {
					case INTEGER_32:
						vb_list.add(new VariableBinding(new OID(value.getOid().getOid()), new Integer32((Integer) value.getValue())));
						break;
					case OCTET_STRING:
						vb_list.add(new VariableBinding(new OID(value.getOid().getOid()), new OctetString((byte[]) value.getValue())));
						break;
					// case NULL(5):
					case OBJECT_IDENTIFIER:
						vb_list.add(new VariableBinding(new OID(value.getOid().getOid()), new OID((String) value.getValue())));
						break;
					case IPADDRESS:
						vb_list.add(new VariableBinding(new OID(value.getOid().getOid()), new IpAddress((InetAddress) value.getValue())));
						break;
					case COUNTER_32:
						vb_list.add(new VariableBinding(new OID(value.getOid().getOid()), new Counter32((Long) value.getValue())));
						break;
					case GAUGE_32:
						vb_list.add(new VariableBinding(new OID(value.getOid().getOid()), new Gauge32((Long) value.getValue())));
						break;
					case TIME_TICKS:
						TimeTicks timeTicks = new TimeTicks();
						timeTicks.fromMilliseconds((Long) value.getValue());
						vb_list.add(new VariableBinding(new OID(value.getOid().getOid()), timeTicks));
						break;
					// case OPAQUE(68):
					case COUNTER_64:
						vb_list.add(new VariableBinding(new OID(value.getOid().getOid()), new Counter64((Long) value.getValue())));
						break;
					default:
						if (blackBox.isEnabledFor(LEVEL.Fatal)) {
							blackBox.log(LEVEL.Fatal, context, UtilString.format("Unknown SNMP type : {}", value.getType()));
						}
					}
				}
				plug_requests[i] = new PlugRequestSNMPSet(vb_list.toArray(new VariableBinding[0]));
			} else {
				if (blackBox.isEnabledFor(LEVEL.Fatal)) {
					blackBox.log(LEVEL.Fatal, context, UtilString.format("Unknown SNMP request : {}", requests[i].getClass()));
				}
			}
		}
		return plug_requests;
	}

	/**
	 * <p>
	 * 응답을 NE Session 형태로 변경
	 * </p>
	 *
	 * @param context
	 * @param snmp_responses
	 * @return
	 * @throws EmpException
	 */
	protected Model4NeSessionResponseSNMPIf[] toNeSessionResponse(EmpContext context, PlugResponseSNMPIf[] snmp_responses) throws EmpException {
		Model4NeSessionResponseSNMP[] responses = new Model4NeSessionResponseSNMP[snmp_responses.length];
		for (int i = 0; i < snmp_responses.length; i++) {
			responses[i] = new Model4NeSessionResponseSNMP();
			Model4NeSessionResponseSNMP response = responses[i];
			for (VariableBinding vb : ((PlugResponseSNMP) snmp_responses[i]).getVbs()) {
				SNMP_TYPE snmp_type = SNMP_TYPE.toEnum(vb.getSyntax());
				Serializable value = toNeFieldValue(context, vb);
				if (value != null) {
					response.addValue(vb.getOid().getValue(), snmp_type, value);
				}
			}
		}
		return responses;
	}

	protected Serializable toNeFieldValue(EmpContext context, VariableBinding vb) throws EmpException {
		SNMP_TYPE snmp_type = SNMP_TYPE.toEnum(vb.getSyntax());
		switch (snmp_type) {
		case INTEGER_32:
			return ((Integer32) vb.getVariable()).getValue();
		case OCTET_STRING:
			return ((OctetString) vb.getVariable()).getValue();
			// case NULL(5):
		case OBJECT_IDENTIFIER:
			return ((OID) vb.getVariable()).toDottedString();
		case IPADDRESS:
			return ((IpAddress) vb.getVariable()).getInetAddress();
		case COUNTER_32:
			return ((Counter32) vb.getVariable()).getValue();
		case GAUGE_32:
			return ((Gauge32) vb.getVariable()).getValue();
		case TIME_TICKS:
			return ((TimeTicks) vb.getVariable()).toMilliseconds();
			// case OPAQUE(68):
		case COUNTER_64:
			return ((Counter64) vb.getVariable()).getValue();
		default:
			if (blackBox.isEnabledFor(LEVEL.Fatal)) {
				blackBox.log(LEVEL.Fatal, context, UtilString.format("Unknown SNMP type : {}", snmp_type));
			}
			return null;
		}
	}

	protected Plug4SNMPClient createSNMPClient() throws EmpException {
		return new Plug4SNMPClient();
	}

	protected Plug4SNMPClient borrowSNMPClient() throws EmpException {
		return pool.borrowObject();
	}

	protected void returnSNMPClient(Plug4SNMPClient snmpClient) {
		pool.returnObject(snmpClient);
	}

	@Override
	public void addListener(Driver4SNMPListenerIf listener) {
		List<Driver4SNMPListenerIf> listenerList = new ArrayList<Driver4SNMPListenerIf>();
		for (Driver4SNMPListenerIf aaa : listeners) {
			listenerList.add(aaa);
		}
		listenerList.add(listener);

		listeners = listenerList.toArray(new Driver4SNMPListenerIf[0]);
	}

	@Override
	public void removeListener(Driver4SNMPListenerIf listener) {
		List<Driver4SNMPListenerIf> listenerList = new ArrayList<Driver4SNMPListenerIf>();
		for (Driver4SNMPListenerIf aaa : listeners) {
			if (listener != aaa) {
				listenerList.add(aaa);
			}
		}

		listeners = listenerList.toArray(new Driver4SNMPListenerIf[0]);
	}

	protected void handleTrap(InetAddress address, int port, SNMP_VERSION version, String community, PlugNotifySNMPTrap trap) {
		EmpContext context = new EmpContext(Driver4SNMP.this);
		try {
			Model4NeSessionNotificationSNMP notification = new Model4NeSessionNotificationSNMP();
			notification.setSysUpTime(trap.getSysUpTime());
			if (version == SNMP_VERSION.V1) {
				int[] trap_oid = trap.getTrapOid().getValue();
				int[] oid = new int[trap_oid.length + 2];
				System.arraycopy(trap_oid, 0, oid, 0, trap_oid.length);
				oid[trap_oid.length] = trap.getGenericTrap();
				oid[trap_oid.length + 1] = trap.getSpecificTrap();
				notification.setNotification_oid(new SNMP_OID(oid));
			} else {
				notification.setNotification_oid(new SNMP_OID(trap.getTrapOid().getValue()));
			}
			notification.setGenericTrap(trap.getGenericTrap());
			notification.setSpecificTrap(trap.getSpecificTrap());
			for (VariableBinding vb : trap.getVbs()) {
				SNMP_TYPE snmp_type = SNMP_TYPE.toEnum(vb.getSyntax());
				Serializable value = toNeFieldValue(context, vb);
				if (value != null) {
					notification.addValue(vb.getOid().getValue(), snmp_type, value);
				}
			}

			for (Driver4SNMPListenerIf listener : listeners) {
				try {
					listener.handleTrap(context, address.getHostAddress(), port, version, community, notification);
				} catch (Exception e) {
					if (blackBox.isEnabledFor(LEVEL.Fatal)) {
						blackBox.log(LEVEL.Fatal, context, e, UtilString.format("trap={}", trap));
					}
				}
			}
		} catch (EmpException e) {
			if (blackBox.isEnabledFor(LEVEL.Fatal)) {
				blackBox.log(LEVEL.Fatal, context, e);
			}
		} catch (Exception e) {
			if (blackBox.isEnabledFor(LEVEL.Fatal)) {
				blackBox.log(LEVEL.Fatal, context, e);
			}
		} finally {
			context.close();
		}
	}

}
