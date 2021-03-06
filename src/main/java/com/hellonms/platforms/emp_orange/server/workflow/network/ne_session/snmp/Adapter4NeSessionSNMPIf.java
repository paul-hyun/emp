/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.server.workflow.network.ne_session.snmp;

import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.server.workflow.AdapterIf;
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
 * SNMP 통신채널 Adapter
 * </p>
 *
 * @since 1.6
 * @create 2015. 4. 3.
 * @modified 2015. 4. 3.
 * @author cchyun
 *
 */
public interface Adapter4NeSessionSNMPIf extends AdapterIf {

	public interface Adapter4NeSessionSNMPListenerIf {

		public void handleTrap(EmpContext context, String host, int port, SNMP_VERSION version, String community, Model4NeSessionNotificationSNMP notification);

	}

	/**
	 * <p>
	 * 통신채널 상태 점검
	 * </p>
	 *
	 * @param context
	 * @param ne_session
	 * @return
	 * @throws EmpException
	 */
	public Model4NeSessionSNMP testNeSession(EmpContext context, Model4NeSessionSNMP ne_session) throws EmpException;

	/**
	 * <p>
	 * NE 통신채널을 검색한다.
	 * </p>
	 *
	 * @param context
	 * @param ne_session_discovery_filter
	 * @return
	 * @throws EmpException
	 */
	public Model4NeSessionDiscoveryResultSNMP[] discoveryListNeSession(EmpContext context, Model4NeSessionDiscoveryFilterSNMP ne_session_discovery_filter) throws EmpException;

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
	public Model4NeSessionResponseSNMPIf[] executeNeSession(EmpContext context, Model4NeSessionSNMP ne_session, Model4NeSessionRequestSNMPAt[] requests) throws EmpException;

	public void addListener(Adapter4NeSessionSNMPListenerIf listener);

	public void removeListener(Adapter4NeSessionSNMPListenerIf listener);

}
