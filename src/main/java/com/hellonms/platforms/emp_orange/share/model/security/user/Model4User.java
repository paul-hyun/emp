/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.share.model.security.user;

import java.util.Date;

import com.hellonms.platforms.emp_core.share.model.ModelIf;
import com.hellonms.platforms.emp_onion.share.model.ACCESS;
import com.hellonms.platforms.emp_orange.share.model.fault.event.SEVERITY;

/**
 * <p>
 * 사용자
 * </p>
 * 
 * @since 1.6
 * @create 2015. 3. 10.
 * @modified 2015. 3. 10.
 * @author cchyun
 */
@SuppressWarnings("serial")
public final class Model4User implements ModelIf {

	/**
	 * USER ID
	 */
	private int user_id;

	/**
	 * USER GROUP ID
	 */
	private int user_group_id;

	/**
	 * 사용자 로그인 계정
	 */
	private String user_account;

	/**
	 * 사용자 암호
	 */
	private String password;

	/**
	 * 암호 변경시간
	 */
	private Date password_time;

	/**
	 * 계정에 대한 접근 권한
	 */
	private ACCESS access;

	/**
	 * 사용자 이름
	 */
	private String user_name;

	/**
	 * 사용자 이메일
	 */
	private String user_email;

	/**
	 * 사용자 전화번호
	 */
	private String telephone;

	/**
	 * 사용자 이동전화 번호
	 */
	private String mobilephone;

	/**
	 * 
	 * 알람 Email 발송여부
	 */
	private String alarm_email_state;

	/**
	 * 알람 SMS 발송여부
	 */
	private String alarm_sms_state;

	/**
	 * 관리할 NE 그룹 목록
	 */
	private int[] manage_ne_groups;

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

	// ////////////////////////////////////////////////////////
	// DB에 저장되지 않는 값
	// ////////////////////////////////////////////////////////
	/**
	 * 권한그룹 계정
	 */
	private String user_group_account;

	public Model4User() {
		this.user_id = 0;
		this.user_group_id = 2;
		this.user_account = "";
		this.password = "";
		this.access = ACCESS.ACCESS_DELETE;
		this.user_name = "";
		this.user_email = "";
		this.telephone = "";
		this.mobilephone = "";
		this.alarm_email_state = "";
		this.alarm_sms_state = "";
		this.manage_ne_groups = new int[] {};
		this.description = "";
		this.admin_state = true;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getUser_group_id() {
		return user_group_id;
	}

	public void setUser_group_id(int user_group_id) {
		this.user_group_id = user_group_id;
	}

	public String getUser_account() {
		return user_account;
	}

	public void setUser_account(String user_account) {
		this.user_account = user_account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getPassword_time() {
		return password_time;
	}

	public void setPassword_time(Date password_time) {
		this.password_time = password_time;
	}

	public ACCESS getAccess() {
		return access;
	}

	public void setAccess(ACCESS access) {
		this.access = access;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getMobilephone() {
		return mobilephone;
	}

	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}

	public String getAlarm_email_state() {
		return alarm_email_state;
	}

	public void setAlarm_email_state(String alarm_email_state) {
		this.alarm_email_state = alarm_email_state;
	}

	public boolean getAlarm_email_state(SEVERITY severity) {
		int index = severity.ordinal();
		if (alarm_email_state == null || alarm_email_state.length() <= index) {
			return false;
		} else {
			char c = alarm_email_state.charAt(index);
			return (c == 'y' || c == 'Y');
		}
	}

	public void setAlarm_email_state(SEVERITY severity, boolean state) {
		int index = severity.ordinal();
		StringBuilder stringBuilder = new StringBuilder();
		if (alarm_email_state == null) {
			for (int i = 0; i < index; i++) {
				stringBuilder.append('N');
			}
			stringBuilder.append(state ? 'Y' : 'N');
		} else if (alarm_email_state.length() <= index) {
			stringBuilder.append(alarm_email_state);
			for (int i = alarm_email_state.length(); i < index; i++) {
				stringBuilder.append('N');
			}
			stringBuilder.append(state ? 'Y' : 'N');
		} else {
			stringBuilder.append(alarm_email_state.substring(0, index));
			stringBuilder.append(state ? 'Y' : 'N');
			stringBuilder.append(alarm_email_state.substring(index + 1));
		}
		this.alarm_email_state = stringBuilder.toString();
	}

	public String getAlarm_sms_state() {
		return alarm_sms_state;
	}

	public void setAlarm_sms_state(String alarm_sms_state) {
		this.alarm_sms_state = alarm_sms_state;
	}

	public boolean getAlarm_sms_state(SEVERITY severity) {
		int index = severity.ordinal();
		if (alarm_sms_state == null || alarm_sms_state.length() <= index) {
			return false;
		} else {
			char c = alarm_sms_state.charAt(index);
			return (c == 'y' || c == 'Y');
		}
	}

	public void setAlarm_sms_state(SEVERITY severity, boolean state) {
		int index = severity.ordinal();
		StringBuilder stringBuilder = new StringBuilder();
		if (alarm_sms_state == null) {
			for (int i = 0; i < index; i++) {
				stringBuilder.append('N');
			}
			stringBuilder.append(state ? 'Y' : 'N');
		} else if (alarm_sms_state.length() <= index) {
			stringBuilder.append(alarm_sms_state);
			for (int i = alarm_sms_state.length(); i < index; i++) {
				stringBuilder.append('N');
			}
			stringBuilder.append(state ? 'Y' : 'N');
		} else {
			stringBuilder.append(alarm_sms_state.substring(0, index));
			stringBuilder.append(state ? 'Y' : 'N');
			stringBuilder.append(alarm_sms_state.substring(index + 1));
		}
		this.alarm_sms_state = stringBuilder.toString();
	}

	public int[] getManage_ne_groups() {
		return manage_ne_groups;
	}

	public void setManage_ne_groups(int[] manage_ne_groups) {
		this.manage_ne_groups = manage_ne_groups;
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

	public String getUser_group_account() {
		return user_group_account;
	}

	public void setUser_group_account(String user_group_account) {
		this.user_group_account = user_group_account;
	}

	@Override
	public Model4User copy() {
		Model4User model = new Model4User();
		model.user_id = this.user_id;
		model.user_group_id = this.user_group_id;
		model.user_account = this.user_account;
		model.password = this.password;
		model.password_time = this.password_time;
		model.access = this.access;
		model.user_name = this.user_name;
		model.user_email = this.user_email;
		model.telephone = this.telephone;
		model.mobilephone = this.mobilephone;
		model.alarm_email_state = this.alarm_email_state;
		model.alarm_sms_state = this.alarm_sms_state;
		model.manage_ne_groups = this.manage_ne_groups;
		model.description = this.description;
		model.admin_state = this.admin_state;
		model.meta_data = this.meta_data;
		model.creator = this.creator;
		model.create_time = this.create_time;
		model.updater = this.updater;
		model.update_time = this.update_time;

		model.user_group_account = this.user_group_account;
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
		stringBuilder.append(indent).append(S_TB).append("user_id").append(S_DL).append(user_id).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("user_group_id").append(S_DL).append(user_group_id).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("user_account").append(S_DL).append(user_account).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("password").append(S_DL).append("********").append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("password_time").append(S_DL).append(password_time).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("access").append(S_DL).append(access).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("user_name").append(S_DL).append(user_name).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("user_email").append(S_DL).append(user_email).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("telephone").append(S_DL).append(telephone).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("mobilephone").append(S_DL).append(mobilephone).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("alarm_email_state").append(S_DL).append(alarm_email_state).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("alarm_sms_state").append(S_DL).append(alarm_sms_state).append(S_NL);
		if (manage_ne_groups != null) {
			stringBuilder.append(indent).append(S_TB).append("manage_ne_groups").append(S_DL).append(S_LA);
			for (int manage_ne_group : manage_ne_groups) {
				stringBuilder.append(manage_ne_group).append(", ");
			}
		} else {
			stringBuilder.append(indent).append(S_TB).append("manage_ne_groups").append(S_DL).append(manage_ne_groups).append(S_NL);
		}
		stringBuilder.append(S_RA).append(S_NL);
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
		return toString("");
	}

}
