/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_onion.share.model.environment.preference;

import java.util.Date;

import com.hellonms.platforms.emp_core.share.model.ModelIf;

/**
 * <p>
 * Preference Model
 * </p>
 *
 * @since 1.6
 * @create 2015. 6. 17.
 * @modified 2015. 6. 17.
 * @author cchyun
 *
 */
@SuppressWarnings("serial")
public class Model4Preference implements ModelIf {

	private PREFERENCE_CODE preference_code;

	private String preference;

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

	public PREFERENCE_CODE getPreference_code() {
		return preference_code;
	}

	public void setPreference_code(PREFERENCE_CODE preference_code) {
		this.preference_code = preference_code;
	}

	public String getPreference() {
		return preference;
	}

	public void setPreference(String preference) {
		this.preference = preference;
	}

	public boolean getPreferenceBoolean() {
		return "true".equalsIgnoreCase(preference);
	}

	public long getPreferenceLong() {
		return Long.parseLong(preference);
	}

	public String getPreferenceString() {
		return preference;
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
	public Model4Preference copy() {
		Model4Preference model = new Model4Preference();
		model.preference_code = this.preference_code;
		model.preference = this.preference;
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
		stringBuilder.append(indent).append(S_TB).append("preference_code").append(S_DL).append(preference_code).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("preference").append(S_DL).append(preference).append(S_NL);
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
