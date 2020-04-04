/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.share.model.network.ne_session.icmp;

import java.util.Date;

import com.hellonms.platforms.emp_orange.share.model.network.ne_session.Model4NeSessionDiscoveryResultIf;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.NE_SESSION_PROTOCOL;

/**
 * <p>
 * ICMP 검색 결과
 * </p>
 *
 * @since 1.6
 * @create 2015. 3. 30.
 * @modified 2015. 3. 30.
 * @author cchyun
 *
 */
@SuppressWarnings("serial")
public class Model4NeSessionDiscoveryResultICMP implements Model4NeSessionDiscoveryResultIf {

	/**
	 * IP
	 */
	private String address;

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

	/**
	 * ICMP 응답시간
	 */
	private int response_time;

	@Override
	public NE_SESSION_PROTOCOL getProtocol() {
		return Model4NeSessionICMP.PROTOCOL;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public boolean isSuccess() {
		return 0 <= response_time;
	}

	public int getResponse_time() {
		return response_time;
	}

	public void setResponse_time(int response_time) {
		this.response_time = response_time;
	}

	@Override
	public String getNe_oid() {
		return null;
	}

	@Override
	public String getNe_name() {
		return null;
	}

	@Override
	public String getDescription() {
		return null;
	}

	@Override
	public Model4NeSessionICMP toNeSession() {
		Model4NeSessionICMP model = Model4NeSessionICMP.newInstance();
		model.setHost(address);
		model.setAddress(address);
		model.setTimeout(timeout);
		model.setRetry(retry);
		model.setSession_check_period(session_check_period);
		model.setDescription(address);
		model.setNe_session_state(isSuccess());
		model.setNe_session_state_time(new Date());
		model.setResponse_time(response_time);
		model.setAdmin_state(model.isNe_session_state());
		return model;
	}

	@Override
	public Model4NeSessionDiscoveryResultICMP copy() {
		Model4NeSessionDiscoveryResultICMP model = new Model4NeSessionDiscoveryResultICMP();
		model.address = this.address;
		model.timeout = this.timeout;
		model.retry = this.retry;
		model.session_check_period = this.session_check_period;
		model.response_time = this.response_time;
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
		stringBuilder.append(indent).append(S_TB).append("address").append(S_DL).append(address).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("timeout").append(S_DL).append(timeout).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("retry").append(S_DL).append(retry).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("session_check_period").append(S_DL).append(session_check_period).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("response_time").append(S_DL).append(response_time).append(S_NL);
		stringBuilder.append(indent).append(S_RB);
		return stringBuilder.toString();
	}

	@Override
	public String toDisplayString(String indent) {
		return toString(indent);
	}

}
