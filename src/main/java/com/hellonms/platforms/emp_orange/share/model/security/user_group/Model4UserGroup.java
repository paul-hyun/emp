/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.share.model.security.user_group;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import com.hellonms.platforms.emp_core.share.model.ModelIf;
import com.hellonms.platforms.emp_core.share.operation.OPERATION_CODE;
import com.hellonms.platforms.emp_onion.share.model.ACCESS;

/**
 * <p>
 * 사용자 권한 그룹
 * </p>
 * 
 * @since 1.6
 * @create 2015. 3. 10.
 * @modified 2015. 3. 10.
 * @author cchyun
 */
@SuppressWarnings("serial")
public final class Model4UserGroup implements ModelIf, Cloneable {

	/**
	 * 권한그룹 ID
	 */
	private int user_group_id;

	/**
	 * 권한그룹 계정
	 */
	private String user_group_account;

	/**
	 * 계정에 대한 접근 권한
	 */
	private ACCESS access;

	/**
	 * 권한그룹의 메뉴 접근 권한
	 */
	private Map<OPERATION_CODE, Boolean> operation_authorities = new LinkedHashMap<OPERATION_CODE, Boolean>();

	/**
	 * 설명
	 */
	private String description;

	/**
	 * 관리여부 (true: 관리, false: 비관리)
	 */
	private boolean admin_state;

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

	public ACCESS getAccess() {
		return access;
	}

	public void setAccess(ACCESS access) {
		this.access = access;
	}

	public boolean getOperation_authority(OPERATION_CODE operation_code) {
		Boolean authority = operation_authorities.get(operation_code);
		return authority == null ? false : authority;
	}

	public void setOperation_authority(OPERATION_CODE operation_code, boolean authority) {
		this.operation_authorities.put(operation_code, authority);
	}

	public OPERATION_CODE[] getOperation_codes() {
		return operation_authorities.keySet().toArray(new OPERATION_CODE[0]);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
	public Model4UserGroup copy() {
		Model4UserGroup model = new Model4UserGroup();
		model.user_group_id = this.user_group_id;
		model.user_group_account = this.user_group_account;
		model.access = this.access;
		model.operation_authorities = new LinkedHashMap<OPERATION_CODE, Boolean>(this.operation_authorities);
		model.description = this.description;
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
		stringBuilder.append(indent).append(S_TB).append("user_group_id").append(S_DL).append(user_group_id).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("user_group_account").append(S_DL).append(user_group_account).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("access").append(S_DL).append(access).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("authorities").append(S_DL).append(S_NL);
		for (OPERATION_CODE operation_code : operation_authorities.keySet()) {
			stringBuilder.append(indent).append(S_TB).append(S_TB).append(operation_code).append(S_DL).append(operation_authorities.get(operation_code)).append(S_NL);
		}
		stringBuilder.append(indent).append(S_TB).append("description").append(S_DL).append(description).append(S_NL);
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
		return toString(indent);
	}

}
