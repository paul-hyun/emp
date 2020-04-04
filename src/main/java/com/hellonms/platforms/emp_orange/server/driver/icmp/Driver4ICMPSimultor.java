package com.hellonms.platforms.emp_orange.server.driver.icmp;

import java.io.IOException;
import java.net.InetAddress;

import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_orange.share.error.ERROR_CODE_ORANGE;

public class Driver4ICMPSimultor extends Driver4ICMP {

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
	protected InetAddress getInetAddresses(String address) throws EmpException {
		return simulator;
	}

}
