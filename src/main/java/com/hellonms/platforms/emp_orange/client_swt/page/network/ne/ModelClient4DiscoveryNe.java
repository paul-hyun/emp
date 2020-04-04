package com.hellonms.platforms.emp_orange.client_swt.page.network.ne;

public class ModelClient4DiscoveryNe {

	protected String host;

	protected int counter;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	@Override
	public String toString() {
		return "host: " + host + ", counter: " + counter;
	}

}
