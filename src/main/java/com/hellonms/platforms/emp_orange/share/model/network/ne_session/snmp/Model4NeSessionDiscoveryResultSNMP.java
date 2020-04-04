/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.share.model.network.ne_session.snmp;

import java.util.Date;

import com.hellonms.platforms.emp_orange.share.model.network.ne_session.Model4NeSessionDiscoveryResultIf;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.NE_SESSION_PROTOCOL;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.snmp.Model4NeSessionSNMP.SNMP_VERSION;

/**
 * <p>
 * SNMP 검색 결과
 * </p>
 *
 * @since 1.6
 * @create 2015. 4. 3.
 * @modified 2015. 4. 3.
 * @author cchyun
 *
 */
@SuppressWarnings("serial")
public class Model4NeSessionDiscoveryResultSNMP implements Model4NeSessionDiscoveryResultIf {

	/**
	 * IP
	 */
	private String address;

	/**
	 * 
	 */
	private int port;

	/**
	 * 
	 */
	private SNMP_VERSION version;

	/**
	 * 
	 */
	private String read_community;

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
	 * SNMP 응답시간
	 */
	private int response_time;

	private Model4NeSessionResponseSNMPIf[] responses;

	@Override
	public NE_SESSION_PROTOCOL getProtocol() {
		return Model4NeSessionSNMP.PROTOCOL;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public Model4NeSessionResponseSNMPIf[] getResponses() {
		return responses;
	}

	public void setResponses(Model4NeSessionResponseSNMPIf[] responses) {
		this.responses = responses;
	}

	@Override
	public String getNe_oid() {
		if (responses != null) {
			for (Model4NeSessionResponseSNMPIf aaa : responses) {
				Model4NeSessionResponseSNMP response = (Model4NeSessionResponseSNMP) aaa;
				SNMP_VALUE[] values = response.getValuesMatch(new SNMP_OID("1.3.6.1.2.1.1.2.0"));
				if (values != null) {
					for (SNMP_VALUE value : values) {
						return value.getValue().toString();
					}
				}
			}
		}
		return null;
	}

	@Override
	public String getNe_name() {
		if (responses != null) {
			for (Model4NeSessionResponseSNMPIf aaa : responses) {
				Model4NeSessionResponseSNMP response = (Model4NeSessionResponseSNMP) aaa;
				SNMP_VALUE[] values = response.getValuesMatch(new SNMP_OID("1.3.6.1.2.1.1.5.0"));
				if (values != null) {
					for (SNMP_VALUE value : values) {
						if (value.getType() == SNMP_TYPE.OCTET_STRING) {
							return new String((byte[]) value.getValue());
						} else {
							return value.getValue().toString();
						}
					}
				}
			}
		}
		return null;
	}

	@Override
	public String getDescription() {
		if (responses != null) {
			for (Model4NeSessionResponseSNMPIf aaa : responses) {
				Model4NeSessionResponseSNMP response = (Model4NeSessionResponseSNMP) aaa;
				SNMP_VALUE[] values = response.getValuesMatch(new SNMP_OID("1.3.6.1.2.1.1.1.0"));
				if (values != null) {
					for (SNMP_VALUE value : values) {
						if (value.getType() == SNMP_TYPE.OCTET_STRING) {
							return new String((byte[]) value.getValue());
						} else {
							return value.getValue().toString();
						}
					}
				}
			}
		}
		return null;
	}

	@Override
	public Model4NeSessionSNMP toNeSession() {
		Model4NeSessionSNMP model = Model4NeSessionSNMP.newInstance();
		model.setHost(address);
		model.setAddress(address);
		model.setPort(port);
		model.setVersion(version);
		model.setRead_community(read_community);
		model.setWrite_community("private");
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
	public Model4NeSessionDiscoveryResultSNMP copy() {
		Model4NeSessionDiscoveryResultSNMP model = new Model4NeSessionDiscoveryResultSNMP();
		model.address = this.address;
		model.port = this.port;
		model.version = this.version;
		model.read_community = this.read_community;
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
		stringBuilder.append(indent).append(S_TB).append("port").append(S_DL).append(port).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("version").append(S_DL).append(version).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("read_community").append(S_DL).append(read_community).append(S_NL);
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
