/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.share.model.network.ne_session.snmp;

import java.util.Date;

import com.hellonms.platforms.emp_core.share.model.ModelIf;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.Model4NeSessionDiscoveryFilterIf;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.NE_SESSION_PROTOCOL;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.snmp.Model4NeSessionSNMP.SNMP_VERSION;

/**
 * <p>
 * SNMP 검색 조건
 * </p>
 *
 * @since 1.6
 * @create 2015. 4. 3.
 * @modified 2015. 4. 3.
 * @author cchyun
 *
 */
@SuppressWarnings("serial")
public class Model4NeSessionDiscoveryFilterSNMP implements Model4NeSessionDiscoveryFilterIf {

	public static Model4NeSessionDiscoveryFilterSNMP newInstance() {
		Model4NeSessionDiscoveryFilterSNMP model = new Model4NeSessionDiscoveryFilterSNMP();
		model.setHost("127.0.0.1");
		model.setPort(161);
		model.setVersion(SNMP_VERSION.V2c);
		model.setRead_community("public");
		model.setCount(1);
		model.setRetry(2);
		model.setTimeout(5000);
		model.setSession_check_period(300);
		Model4NeSessionRequestSNMPWalk walk = new Model4NeSessionRequestSNMPWalk();
		walk.addOID(new SNMP_OID("1.3.6.1.2.1.1"));
		model.setRequests(new Model4NeSessionRequestSNMPAt[] { walk });
		return model;
	}

	/**
	 * 
	 */
	private String host;

	/**
	 * 
	 */
	private int port = 161;

	/**
	 * 
	 */
	private SNMP_VERSION version;

	/**
	 * 
	 */
	private String read_community;

	/**
	 * 검색 개수
	 */
	private int count;

	/**
	 * 타임아웃
	 */
	private int timeout;

	/**
	 * 재시도 회수
	 */
	private int retry;

	/**
	 * 통신상태확인 주기
	 */
	private int session_check_period;

	private Model4NeSessionRequestSNMPAt[] requests;

	@Override
	public NE_SESSION_PROTOCOL getProtocol() {
		return Model4NeSessionSNMP.PROTOCOL;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public SNMP_VERSION getVersion() {
		return version;
	}

	public void setVersion(SNMP_VERSION version) {
		this.version = version;
	}

	public String getRead_community() {
		return read_community;
	}

	public void setRead_community(String read_community) {
		this.read_community = read_community;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public int getRetry() {
		return retry;
	}

	public void setRetry(int retry) {
		this.retry = retry;
	}

	public int getSession_check_period() {
		return session_check_period;
	}

	public void setSession_check_period(int session_check_period) {
		this.session_check_period = session_check_period;
	}

	public Model4NeSessionRequestSNMPAt[] getRequests() {
		return requests;
	}

	public void setRequests(Model4NeSessionRequestSNMPAt[] requests) {
		this.requests = requests;
	}

	public Model4NeSessionDiscoveryResultSNMP toNeSessionDiscoveryResultSNMP(String address, int response_time) {
		Model4NeSessionDiscoveryResultSNMP model = new Model4NeSessionDiscoveryResultSNMP();
		model.setAddress(address);
		model.setPort(port);
		model.setVersion(version);
		model.setRead_community(read_community);
		model.setTimeout(timeout);
		model.setRetry(retry);
		model.setSession_check_period(session_check_period);
		model.setResponse_time(response_time);
		return model;
	}

	@Override
	public ModelIf copy() {
		Model4NeSessionDiscoveryFilterSNMP model = new Model4NeSessionDiscoveryFilterSNMP();
		model.count = count;
		model.host = host;
		model.port = port;
		model.read_community = read_community;
		model.retry = retry;
		model.session_check_period = session_check_period;
		model.timeout = timeout;
		model.version = version;
		model.requests = requests;
		return model;
	}

	public Model4NeSessionSNMP toNeSession() {
		Model4NeSessionSNMP model = Model4NeSessionSNMP.newInstance();
		model.setHost(host);
		model.setAddress(host);
		model.setPort(port);
		model.setVersion(version);
		model.setRead_community(read_community);
		model.setWrite_community("private");
		model.setTimeout(timeout);
		model.setRetry(retry);
		model.setSession_check_period(session_check_period);
		model.setDescription(host);
		model.setNe_session_state(false);
		model.setNe_session_state_time(new Date());
		model.setResponse_time(0);
		model.setAdmin_state(true);
		return model;
	}

	@Override
	public String toString() {
		return toString("");
	}

	@Override
	public String toString(String indent) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(indent).append(getClass().getName()).append(S_LB).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("host").append(S_DL).append(host).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("count").append(S_DL).append(count).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("timeout").append(S_DL).append(timeout).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("retry").append(S_DL).append(retry).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("session_check_period").append(S_DL).append(session_check_period).append(S_NL);
		stringBuilder.append(indent).append(S_RB);
		return stringBuilder.toString();
	}

	@Override
	public String toDisplayString(String indent) {
		return toString(indent);
	}

}
