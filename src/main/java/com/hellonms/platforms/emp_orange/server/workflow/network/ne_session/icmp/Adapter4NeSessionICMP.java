/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.server.workflow.network.ne_session.icmp;

import com.hellonms.platforms.emp_core.server.log.BlackBox;
import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.server.workflow.AdapterIf;
import com.hellonms.platforms.emp_core.server.workflow.WorkflowMap;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_orange.server.driver.icmp.Driver4ICMPIf;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.icmp.Model4NeSessionDiscoveryFilterICMP;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.icmp.Model4NeSessionDiscoveryResultICMP;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.icmp.Model4NeSessionICMP;

/**
 * <p>
 * ICMP 통신채널 Adapter
 * </p>
 *
 * @since 1.6
 * @create 2015. 3. 30.
 * @modified 2015. 3. 30.
 * @author cchyun
 *
 */
public class Adapter4NeSessionICMP implements Adapter4NeSessionICMPIf {

	private Driver4ICMPIf driver4ICMP;

	@SuppressWarnings("unused")
	private static final BlackBox blackBox = new BlackBox(Adapter4NeSessionICMP.class);

	@Override
	public Class<? extends AdapterIf> getDefine_class() {
		return Adapter4NeSessionICMPIf.class;
	}

	@Override
	public void initialize(EmpContext context) throws EmpException {
		driver4ICMP = (Driver4ICMPIf) WorkflowMap.getDriver(Driver4ICMPIf.class);
	}

	@Override
	public void dispose(EmpContext context) throws EmpException {
	}

	@Override
	public Model4NeSessionICMP testNeSession(EmpContext context, Model4NeSessionICMP ne_session) throws EmpException {
		ne_session = driver4ICMP.testNeSession(context, ne_session);
		return ne_session;
	}

	@Override
	public Model4NeSessionDiscoveryResultICMP[] discoveryListNeSession(EmpContext context, Model4NeSessionDiscoveryFilterICMP ne_session_discovery_filter) throws EmpException {
		return driver4ICMP.discoveryListNeSession(context, ne_session_discovery_filter);
	}

}
