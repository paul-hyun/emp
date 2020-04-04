/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.server.driver.icmp;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

import com.hellonms.platforms.emp_core.server.driver.DriverIf;
import com.hellonms.platforms.emp_core.server.log.BlackBox;
import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_orange.share.error.ERROR_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.icmp.Model4NeSessionDiscoveryFilterICMP;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.icmp.Model4NeSessionDiscoveryResultICMP;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.icmp.Model4NeSessionICMP;
import com.hellonms.platforms.emp_plug.icmp.Plug4ICMPClient;
import com.hellonms.platforms.emp_util.network.UtilInetAddress;

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
public class Driver4ICMP implements Driver4ICMPIf {

	@SuppressWarnings("unused")
	private static final BlackBox blackBox = new BlackBox(Driver4ICMP.class);

	@Override
	public Class<? extends DriverIf> getDefine_class() {
		return Driver4ICMPIf.class;
	}

	@Override
	public void initialize(EmpContext context) throws EmpException {
		try {
			Plug4ICMPClient.getInstance().testNeSession(context, InetAddress.getLocalHost(), 1000, 0);
		} catch (UnknownHostException e) {
			throw new EmpException(e, ERROR_CODE_ORANGE.NETOWRK_IO);
		}
	}

	@Override
	public void dispose(EmpContext context) throws EmpException {
	}

	@Override
	public Model4NeSessionICMP testNeSession(EmpContext context, Model4NeSessionICMP ne_session) throws EmpException {
		InetAddress address = getInetAddresses(ne_session.getAddress());
		int response_time = Plug4ICMPClient.getInstance().testNeSession(context, address, ne_session.getTimeout(), ne_session.getRetry());

		ne_session = ne_session.copy();
		ne_session.setResponse_time(response_time);
		ne_session.setNe_session_state(0 <= response_time);
		ne_session.setNe_session_state_time(new Date());

		return ne_session;
	}

	protected InetAddress getInetAddresses(String address) throws EmpException {
		return UtilInetAddress.getInetAddresses(address);
	}

	@Override
	public Model4NeSessionDiscoveryResultICMP[] discoveryListNeSession(EmpContext context, Model4NeSessionDiscoveryFilterICMP ne_session_discovery_filter) throws EmpException {
		try {
			InetAddress[] addresses = UtilInetAddress.getInetAddresses(InetAddress.getByName(ne_session_discovery_filter.getHost()), ne_session_discovery_filter.getCount());

			int[] response_times = Plug4ICMPClient.getInstance().testListNeSession(context, addresses, ne_session_discovery_filter.getTimeout(), ne_session_discovery_filter.getRetry());

			Model4NeSessionDiscoveryResultICMP[] ne_session_discovery_results = new Model4NeSessionDiscoveryResultICMP[addresses.length];
			for (int i = 0; i < addresses.length; i++) {
				ne_session_discovery_results[i] = ne_session_discovery_filter.toNeSessionDiscoveryResultICMP(addresses[i].getHostAddress(), response_times[i]);
			}
			return ne_session_discovery_results;
		} catch (IOException e) {
			throw new EmpException(e, ERROR_CODE_ORANGE.NETOWRK_IO, ne_session_discovery_filter.getHost());
		}
	}

}
