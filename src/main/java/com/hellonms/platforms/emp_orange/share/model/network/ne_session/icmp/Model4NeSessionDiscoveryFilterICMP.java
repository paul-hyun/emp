/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.share.model.network.ne_session.icmp;

import com.hellonms.platforms.emp_core.share.model.ModelIf;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.Model4NeSessionDiscoveryFilterIf;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.NE_SESSION_PROTOCOL;

/**
 * <p>
 * ICMP 검색 조건
 * </p>
 *
 * @since 1.6
 * @create 2015. 3. 30.
 * @modified 2015. 3. 30.
 * @author cchyun
 *
 */
@SuppressWarnings("serial")
public class Model4NeSessionDiscoveryFilterICMP implements Model4NeSessionDiscoveryFilterIf {

	public static Model4NeSessionDiscoveryFilterICMP newInstance() {
		Model4NeSessionDiscoveryFilterICMP model = new Model4NeSessionDiscoveryFilterICMP();
		model.setHost("127.0.0.1");
		model.setCount(1);
		model.setRetry(2);
		model.setTimeout(1000);
		model.setSession_check_period(60);
		return model;
	}

	/**
	 * 
	 */
	private String host;

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

	@Override
	public NE_SESSION_PROTOCOL getProtocol() {
		return Model4NeSessionICMP.PROTOCOL;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
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

	public Model4NeSessionDiscoveryResultICMP toNeSessionDiscoveryResultICMP(String address, int response_time) {
		Model4NeSessionDiscoveryResultICMP model = new Model4NeSessionDiscoveryResultICMP();
		model.setAddress(address);
		model.setTimeout(timeout);
		model.setRetry(retry);
		model.setSession_check_period(session_check_period);
		model.setResponse_time(response_time);
		return model;
	}

	@Override
	public ModelIf copy() {
		Model4NeSessionDiscoveryFilterICMP model = new Model4NeSessionDiscoveryFilterICMP();
		model.count = count;
		model.host = host;
		model.retry = retry;
		model.session_check_period = session_check_period;
		model.timeout = timeout;
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
