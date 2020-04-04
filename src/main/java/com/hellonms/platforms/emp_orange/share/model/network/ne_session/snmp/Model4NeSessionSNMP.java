/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.share.model.network.ne_session.snmp;

import java.util.Date;

import com.hellonms.platforms.emp_orange.share.model.network.ne_session.Model4NeSessionIf;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.NE_SESSION_PROTOCOL;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.NE_SESSION_PROTOCOL_ORANGE;
import com.hellonms.platforms.emp_util.date.UtilDate;

/**
 * <p>
 * SNMP 통신채널 모델
 * </p>
 * 
 * @since 1.6
 * @create 2015. 3. 10.
 * @modified 2015. 3. 10.
 * @author cchyun
 * 
 */
@SuppressWarnings("serial")
public final class Model4NeSessionSNMP implements Model4NeSessionIf {

	public static Model4NeSessionSNMP newInstance() {
		Model4NeSessionSNMP model = new Model4NeSessionSNMP();
		model.ne_id = 0;
		model.host = "127.0.0.1";
		model.address = "127.0.0.1";
		model.port = 161;
		model.version = SNMP_VERSION.V2c;
		model.read_community = "public";
		model.write_community = "private";
		model.charset = "";
		model.timeout = 5000;
		model.retry = 2;
		model.session_check_period = 300;
		model.description = "";
		model.ne_session_state = true;
		model.ne_session_state_time = new Date();
		model.response_time = 0;
		model.admin_state = true;
		model.meta_data = null;
		model.creator = "";
		model.create_time = new Date();
		model.updater = "";
		model.update_time = new Date();
		return model;
	}

	public enum SNMP_VERSION {
		V2c, V1,
	}

	public static final NE_SESSION_PROTOCOL PROTOCOL = NE_SESSION_PROTOCOL_ORANGE.SNMP;

	/**
	 * 장치가 속한 NE의 Ne ID
	 */
	protected int ne_id;

	/**
	 * 
	 */
	protected String host;

	/**
	 * 
	 */
	protected String address;

	/**
	 * 
	 */
	protected int port;

	/**
	 * 
	 */
	protected SNMP_VERSION version;

	/**
	 * 
	 */
	protected String read_community;

	/**
	 * 
	 */
	protected String write_community;

	/**
	 * 
	 */
	protected String charset;

	/**
	 * 
	 */
	protected int timeout;

	/**
	 * 
	 */
	protected int retry;

	/**
	 * SNMP 세션 상태 확인 주기
	 */
	protected int session_check_period;

	/**
	 * 설명
	 */
	protected String description;

	/**
	 * SNMP 상태
	 */
	protected boolean ne_session_state;

	/**
	 * SNMP 상태 변경 시간
	 */
	protected Date ne_session_state_time;

	/**
	 * SNMP 응답시간
	 */
	protected int response_time;

	/**
	 * 관리여부 (true: 관리, false: 비관리)
	 */
	protected boolean admin_state;

	/**
	 * 메타데이터 (JSON 형태로 저장)
	 */
	protected String meta_data;

	/**
	 * 생성한 사람
	 */
	protected String creator;

	/**
	 * 생성 시간
	 */
	protected Date create_time;

	/**
	 * 마지막 수정한 사람
	 */
	protected String updater;

	/**
	 * 마직막 수정 시간
	 */
	protected Date update_time;

	@Override
	public NE_SESSION_PROTOCOL getProtocol() {
		return PROTOCOL;
	}

	public int getNe_id() {
		return ne_id;
	}

	public void setNe_id(int ne_id) {
		this.ne_id = ne_id;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
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

	public String getWrite_community() {
		return write_community;
	}

	public void setWrite_community(String write_community) {
		this.write_community = write_community;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isNe_session_state() {
		return ne_session_state;
	}

	public void setNe_session_state(boolean ne_session_state) {
		this.ne_session_state = ne_session_state;
	}

	public Date getNe_session_state_time() {
		return ne_session_state_time;
	}

	public void setNe_session_state_time(Date ne_session_state_time) {
		this.ne_session_state_time = ne_session_state_time;
	}

	public int getResponse_time() {
		return response_time;
	}

	public void setResponse_time(int response_time) {
		this.response_time = response_time;
	}

	public boolean isAdmin_state() {
		return admin_state;
	}

	public void setAdmin_state(boolean admin_state) {
		this.admin_state = admin_state;
	}

	public String getMeta_data() {
		return meta_data;
	}

	public void setMeta_data(String meta_data) {
		this.meta_data = meta_data;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public String getUpdater() {
		return updater;
	}

	public void setUpdater(String updater) {
		this.updater = updater;
	}

	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}

	@Override
	public Model4NeSessionSNMP copy() {
		Model4NeSessionSNMP model = new Model4NeSessionSNMP();
		model.ne_id = this.ne_id;
		model.host = this.host;
		model.address = this.address;
		model.port = this.port;
		model.version = this.version;
		model.read_community = this.read_community;
		model.write_community = this.write_community;
		model.charset = this.charset;
		model.timeout = this.timeout;
		model.retry = this.retry;
		model.session_check_period = this.session_check_period;
		model.description = this.description;
		model.ne_session_state = this.ne_session_state;
		model.ne_session_state_time = this.ne_session_state_time;
		model.response_time = this.response_time;
		model.admin_state = this.admin_state;
		model.meta_data = this.meta_data;
		model.creator = this.creator;
		model.create_time = this.create_time;
		model.updater = this.updater;
		model.update_time = this.update_time;
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
		stringBuilder.append(indent).append(S_TB).append("ne_id").append(S_DL).append(ne_id).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("host").append(S_DL).append(host).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("address").append(S_DL).append(address).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("port").append(S_DL).append(port).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("version").append(S_DL).append(version).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("read_community").append(S_DL).append(read_community).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("write_community").append(S_DL).append(write_community).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("timeout").append(S_DL).append(timeout).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("retry").append(S_DL).append(retry).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("session_check_period").append(S_DL).append(session_check_period).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("description").append(S_DL).append(description).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("ne_session_state").append(S_DL).append(ne_session_state).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("ne_session_state_time").append(S_DL).append(ne_session_state_time).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("response_time").append(S_DL).append(response_time).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("admin_state").append(S_DL).append(admin_state).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("meta_data").append(S_DL).append(meta_data).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("creator").append(S_DL).append(creator).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("create_time").append(S_DL).append(create_time).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("updater").append(S_DL).append(updater).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("update_time").append(S_DL).append(update_time).append(S_NL);
		stringBuilder.append(indent).append(S_RB);
		return stringBuilder.toString();
	}

	@Override
	public String toDisplayString(String indent) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(indent).append(getClass().getName()).append(S_LB).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("ne_id").append(S_DL).append(ne_id).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("host").append(S_DL).append(host).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("address").append(S_DL).append(address).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("port").append(S_DL).append(port).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("version").append(S_DL).append(version).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("read_community").append(S_DL).append(read_community).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("write_community").append(S_DL).append(write_community).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("timeout").append(S_DL).append(timeout).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("retry").append(S_DL).append(retry).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("session_check_period").append(S_DL).append(session_check_period).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("description").append(S_DL).append(description).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("ne_session_state").append(S_DL).append(ne_session_state).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("ne_session_state_time").append(S_DL).append(UtilDate.format(ne_session_state_time)).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("response_time").append(S_DL).append(response_time).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("admin_state").append(S_DL).append(admin_state).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("creator").append(S_DL).append(creator).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("create_time").append(S_DL).append(UtilDate.format(create_time)).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("updater").append(S_DL).append(updater).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("update_time").append(S_DL).append(UtilDate.format(update_time)).append(S_NL);
		stringBuilder.append(indent).append(S_RB);
		return stringBuilder.toString();
	}

}
