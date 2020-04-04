/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.server.workflow.network.ne_session.icmp;

import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.server.workflow.AdapterIf;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.icmp.Model4NeSessionDiscoveryFilterICMP;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.icmp.Model4NeSessionDiscoveryResultICMP;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.icmp.Model4NeSessionICMP;

/**
 * <p>
 * ICMP 통신채널 Adapter
 * </p>
 * 
 * @since 1.6
 * @create 2015. 3. 24.
 * @modified 2015. 3. 24.
 * @author cchyun
 * 
 */
public interface Adapter4NeSessionICMPIf extends AdapterIf {

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
	public Model4NeSessionICMP testNeSession(EmpContext context, Model4NeSessionICMP ne_session) throws EmpException;

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
	public Model4NeSessionDiscoveryResultICMP[] discoveryListNeSession(EmpContext context, Model4NeSessionDiscoveryFilterICMP ne_session_discovery_filter) throws EmpException;

}
