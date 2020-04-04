/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.share.model.security.user_session;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import com.hellonms.platforms.emp_core.share.model.ModelIf;
import com.hellonms.platforms.emp_core.share.operation.OPERATION_CODE;

/**
 * <p>
 * 사용자 로그인 세션
 * </p>
 * 
 * @since 1.6
 * @create 2015. 3. 10.
 * @modified 2015. 3. 10.
 * @author cchyun
 * 
 */
@SuppressWarnings("serial")
public final class Model4UserSession implements ModelIf {

	/**
	 * Session ID
	 */
	private int user_session_id;

	/**
	 * Session Key
	 */
	private String user_session_key;

	/**
	 * User ID
	 */
	private int user_id;

	/**
	 * User IP
	 */
	private String user_ip;

	/**
	 * Login Time
	 */
	private Date login_time;

	/**
	 * Last Access Time
	 */
	private Date last_access_time;

	/**
	 * 메타데이터 (JSON 형태로 저장)
	 */
	private String meta_data;

	/**
	 * 생성한 사람
	 */
	private String creator;

	/**
	 * 생성 시간
	 */
	private Date create_time;

	/**
	 * 마지막 수정한 사람
	 */
	private String updater;

	/**
	 * 마직막 수정 시간
	 */
	private Date update_time;

	// ////////////////////////////////////////////////////////
	// DB에 저장되지 않는 값
	// ////////////////////////////////////////////////////////
	/**
	 * 사용자 로그인 계정
	 */
	private String user_account;

	/**
	 * 사용자 이름
	 */
	private String user_name;

	/**
	 * 권한그룹 계정
	 */
	private int user_group_id;

	/**
	 * 권한그룹 계정
	 */
	private String user_group_account;

	/**
	 * 권한그룹의 메뉴 접근 권한
	 */
	private Map<OPERATION_CODE, Boolean> operation_authorities = new LinkedHashMap<OPERATION_CODE, Boolean>();

	public int getUser_session_id() {
		return user_session_id;
	}

	public void setUser_session_id(int user_session_id) {
		this.user_session_id = user_session_id;
	}

	public String getUser_session_key() {
		return user_session_key;
	}

	public void setUser_session_key(String user_session_key) {
		this.user_session_key = user_session_key;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getUser_ip() {
		return user_ip;
	}

	public void setUser_ip(String user_ip) {
		this.user_ip = user_ip;
	}

	public Date getLogin_time() {
		return login_time;
	}

	public void setLogin_time(Date login_time) {
		this.login_time = login_time;
	}

	public Date getLast_access_time() {
		return last_access_time;
	}

	public void setLast_access_time(Date last_access_time) {
		this.last_access_time = last_access_time;
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

	public String getUser_account() {
		return user_account;
	}

	public void setUser_account(String user_account) {
		this.user_account = user_account;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public int getUser_group_id() {
		return user_group_id;
	}

	public void setUser_group_id(int user_group_id) {
		this.user_group_id = user_group_id;
	}

	public String getUser_group_account() {
		return user_group_account;
	}

	public void setUser_group_account(String user_group_account) {
		this.user_group_account = user_group_account;
	}

	public boolean getOperation_authority(OPERATION_CODE operation_code) {
		Boolean authority = operation_authorities.get(operation_code);
		return authority == null ? false : authority;
	}

	public void setOperation_authority(OPERATION_CODE operation_code, boolean authority) {
		this.operation_authorities.put(operation_code, authority);
	}

	@Override
	public Model4UserSession copy() {
		Model4UserSession model = new Model4UserSession();
		model.user_session_id = this.user_session_id;
		model.user_session_key = this.user_session_key;
		model.user_id = this.user_id;
		model.user_ip = this.user_ip;
		model.login_time = this.login_time;
		model.last_access_time = this.last_access_time;
		model.meta_data = this.meta_data;
		model.creator = this.creator;
		model.create_time = this.create_time;
		model.updater = this.updater;
		model.update_time = this.update_time;

		model.user_account = this.user_account;
		model.user_name = this.user_name;
		model.user_group_id = this.user_group_id;
		model.user_group_account = this.user_group_account;
		model.operation_authorities = new LinkedHashMap<OPERATION_CODE, Boolean>(this.operation_authorities);
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
		stringBuilder.append(indent).append(S_TB).append("user_session_id").append(S_DL).append(user_session_id).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("user_session_key").append(S_DL).append(user_session_key).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("user_id").append(S_DL).append(user_id).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("user_ip").append(S_DL).append(user_ip).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("login_time").append(S_DL).append(login_time).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("last_access_time").append(S_DL).append(last_access_time).append(S_NL);
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
		return toString(indent);
	}

}
