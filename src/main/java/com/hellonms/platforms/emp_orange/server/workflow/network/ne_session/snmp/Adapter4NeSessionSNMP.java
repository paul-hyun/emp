/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.server.workflow.network.ne_session.snmp;

import java.util.ArrayList;
import java.util.List;

import com.hellonms.platforms.emp_core.server.log.BlackBox;
import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.server.workflow.AdapterIf;
import com.hellonms.platforms.emp_core.server.workflow.WorkflowMap;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_orange.server.driver.snmp.Driver4SNMPIf;
import com.hellonms.platforms.emp_orange.server.driver.snmp.Driver4SNMPIf.Driver4SNMPListenerIf;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.snmp.Model4NeSessionDiscoveryFilterSNMP;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.snmp.Model4NeSessionDiscoveryResultSNMP;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.snmp.Model4NeSessionNotificationSNMP;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.snmp.Model4NeSessionRequestSNMPAt;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.snmp.Model4NeSessionResponseSNMPIf;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.snmp.Model4NeSessionSNMP;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.snmp.Model4NeSessionSNMP.SNMP_VERSION;

/**
 * <p>
 * SNMP 통신채널 Adapter
 * </p>
 *
 * @since 1.6
 * @create 2015. 4. 3.
 * @modified 2015. 4. 3.
 * @author cchyun
 *
 */
public class Adapter4NeSessionSNMP implements Adapter4NeSessionSNMPIf {

	private class Driver4SNMPListener implements Driver4SNMPListenerIf {

		@Override
		public void handleTrap(EmpContext context, String address, int port, SNMP_VERSION version, String community, Model4NeSessionNotificationSNMP notification) {
			for (Adapter4NeSessionSNMPListenerIf listener : listeners) {
				listener.handleTrap(context, address, port, version, community, notification);
			}
		}

	}

	private Driver4SNMPIf driver4SNMP;

	private Adapter4NeSessionSNMPListenerIf[] listeners = {};

	@SuppressWarnings("unused")
	private static final BlackBox blackBox = new BlackBox(Adapter4NeSessionSNMP.class);

	@Override
	public Class<? extends AdapterIf> getDefine_class() {
		return Adapter4NeSessionSNMPIf.class;
	}

	@Override
	public void initialize(EmpContext context) throws EmpException {
		driver4SNMP = (Driver4SNMPIf) WorkflowMap.getDriver(Driver4SNMPIf.class);
		driver4SNMP.addListener(new Driver4SNMPListener());
	}

	@Override
	public void dispose(EmpContext context) throws EmpException {
	}

	@Override
	public Model4NeSessionSNMP testNeSession(EmpContext context, Model4NeSessionSNMP ne_session) throws EmpException {
		ne_session = driver4SNMP.testNeSession(context, ne_session);
		return ne_session;
	}

	@Override
	public Model4NeSessionDiscoveryResultSNMP[] discoveryListNeSession(EmpContext context, Model4NeSessionDiscoveryFilterSNMP ne_session_discovery_filter) throws EmpException {
		return driver4SNMP.discoveryListNeSession(context, ne_session_discovery_filter);
	}

	@Override
	public Model4NeSessionResponseSNMPIf[] executeNeSession(EmpContext context, Model4NeSessionSNMP ne_session, Model4NeSessionRequestSNMPAt[] requests) throws EmpException {
		return driver4SNMP.execute(context, ne_session, requests);
	}

	@Override
	public void addListener(Adapter4NeSessionSNMPListenerIf listener) {
		List<Adapter4NeSessionSNMPListenerIf> listenerList = new ArrayList<Adapter4NeSessionSNMPListenerIf>();
		for (Adapter4NeSessionSNMPListenerIf aaa : listeners) {
			listenerList.add(aaa);
		}
		listenerList.add(listener);

		listeners = listenerList.toArray(new Adapter4NeSessionSNMPListenerIf[0]);
	}

	@Override
	public void removeListener(Adapter4NeSessionSNMPListenerIf listener) {
		List<Adapter4NeSessionSNMPListenerIf> listenerList = new ArrayList<Adapter4NeSessionSNMPListenerIf>();
		for (Adapter4NeSessionSNMPListenerIf aaa : listeners) {
			if (listener != aaa) {
				listenerList.add(aaa);
			}
		}

		listeners = listenerList.toArray(new Adapter4NeSessionSNMPListenerIf[0]);
	}

}
