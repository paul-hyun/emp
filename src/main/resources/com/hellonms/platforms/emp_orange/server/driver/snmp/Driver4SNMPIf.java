/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.server.driver.snmp;

import com.hellonms.platforms.emp_core.server.driver.DriverIf;
import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.snmp.Model4NeSessionDiscoveryFilterSNMP;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.snmp.Model4NeSessionDiscoveryResultSNMP;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.snmp.Model4NeSessionNotificationSNMP;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.snmp.Model4NeSessionRequestSNMPAt;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.snmp.Model4NeSessionResponseSNMPIf;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.snmp.Model4NeSessionSNMP;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.snmp.Model4NeSessionSNMP.SNMP_VERSION;

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
public interface Driver4SNMPIf extends DriverIf {

	public interface Driver4SNMPListenerIf {

		public void handleTrap(EmpContext context, String address, int port, SNMP_VERSION version, String community, Model4NeSessionNotificationSNMP notification);

	}

	public Model4NeSessionSNMP testNeSession(EmpContext context, Model4NeSessionSNMP ne_session) throws EmpException;

	/**
	 * <p>
	 * NE 통신채널 명령 수행
	 * </p>
	 *
	 * @param context
	 * @param ne_session
	 * @param requests
	 * @return
	 * @throws EmpException
	 */
	public Model4NeSessionResponseSNMPIf[] execute(EmpContext context, Model4NeSessionSNMP ne_session, Model4NeSessionRequestSNMPAt[] requests) throws EmpException;

	/**
	 * <p>
	 * NE 통신채널 검색
	 * </p>
	 *
	 * @param context
	 * @param ne_session_discovery_filter
	 * @return
	 * @throws EmpException
	 */
	public Model4NeSessionDiscoveryResultSNMP[] discoveryListNeSession(EmpContext context, Model4NeSessionDiscoveryFilterSNMP ne_session_discovery_filter) throws EmpException;

	public void addListener(Driver4SNMPListenerIf listener);

	public void removeListener(Driver4SNMPListenerIf listener);

}
