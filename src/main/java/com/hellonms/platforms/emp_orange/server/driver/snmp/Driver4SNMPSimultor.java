package com.hellonms.platforms.emp_orange.server.driver.snmp;

import java.io.IOException;
import java.net.InetAddress;

import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_orange.share.error.ERROR_CODE_ORANGE;
import com.hellonms.platforms.emp_util.network.UtilInetAddress;

public class Driver4SNMPSimultor extends Driver4SNMP {

	private InetAddress simulator = null;

	@Override
	public void initialize(EmpContext context) throws EmpException {
		super.initialize(context);
		if (simulator == null) {
			try {
				simulator = InetAddress.getByName("127.0.0.1");
			} catch (IOException e) {
				throw new EmpException(e, ERROR_CODE_ORANGE.NETOWRK_IO);
			}
		}
	}

	public void setSimulator(InetAddress simulator) {
		this.simulator = simulator;
	}

	@Override
	protected InetAddress getInetAddresses(String address, String read_community, String write_community) throws EmpException {
		return simulator;
	}

	@Override
	protected String getRead_community(String address, String read_community, String write_community) throws EmpException {
		return address;
	}

	@Override
	protected String getWrite_community(String address, String read_community, String write_community) throws EmpException {
		return address;
	}

	@Override
	protected InetAddress getTrap_addresses(InetAddress address, String community) {
		try {
			return UtilInetAddress.getInetAddresses(community);
		} catch (EmpException e) {
			return address;
		}
	}

	@Override
	protected String getTrap_community(InetAddress address, String community) {
		return "public";
	}

}
