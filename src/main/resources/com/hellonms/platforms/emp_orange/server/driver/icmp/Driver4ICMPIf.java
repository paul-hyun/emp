/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.server.driver.icmp;

import com.hellonms.platforms.emp_core.server.driver.DriverIf;
import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.icmp.Model4NeSessionDiscoveryFilterICMP;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.icmp.Model4NeSessionDiscoveryResultICMP;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.icmp.Model4NeSessionICMP;

/**
 * <p>
 * ICMP 통신 Driver
 * </p>
 *
 * @since 1.6
 * @create 2015. 3. 30.
 * @modified 2015. 3. 30.
 * @author cchyun
 *
 */
public interface Driver4ICMPIf extends DriverIf {

	public Model4NeSessionICMP testNeSession(EmpContext context, Model4NeSessionICMP ne_session) throws EmpException;

	public Model4NeSessionDiscoveryResultICMP[] discoveryListNeSession(EmpContext context, Model4NeSessionDiscoveryFilterICMP ne_session_discovery_filter) throws EmpException;

}
