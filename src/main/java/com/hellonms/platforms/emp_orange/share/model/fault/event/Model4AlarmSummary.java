/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.share.model.fault.event;

import com.hellonms.platforms.emp_core.share.model.ModelIf;

/**
 * <p>
 * 알람 개수
 * </p>
 *
 * @since 1.6
 * @create 2015. 4. 21.
 * @modified 2015. 4. 21.
 * @author cchyun
 *
 */
@SuppressWarnings("serial")
public class Model4AlarmSummary implements ModelIf {

	private int ne_id;

	/**
	 * 통신 불능
	 */
	private int communication_fail_count;

	/**
	 * 통신 불능
	 */
	private int communication_fail_unack_count;

	/**
	 * 긴급.중요 알람
	 */
	private int critical_count;

	/**
	 * 긴급.중요 알람
	 */
	private int critical_unack_count;

	/**
	 * 중요알람
	 */
	private int major_count;

	/**
	 * 중요알람
	 */
	private int major_unack_count;

	/**
	 * 알람
	 */
	private int minor_count;

	/**
	 * 알람
	 */
	private int minor_unack_count;

	public int getNe_id() {
		return ne_id;
	}

	public void setNe_id(int ne_id) {
		this.ne_id = ne_id;
	}

	public int getCommunication_fail_count() {
		return communication_fail_count;
	}

	public void setCommunication_fail_count(int communication_fail_count) {
		this.communication_fail_count = communication_fail_count;
	}

	public int getCommunication_fail_unack_count() {
		return communication_fail_unack_count;
	}

	public void setCommunication_fail_unack_count(int communication_fail_unack_count) {
		this.communication_fail_unack_count = communication_fail_unack_count;
	}

	public int getCritical_count() {
		return critical_count;
	}

	public void setCritical_count(int critical_count) {
		this.critical_count = critical_count;
	}

	public int getCritical_unack_count() {
		return critical_unack_count;
	}

	public void setCritical_unack_count(int critical_unack_count) {
		this.critical_unack_count = critical_unack_count;
	}

	public int getMajor_count() {
		return major_count;
	}

	public void setMajor_count(int major_count) {
		this.major_count = major_count;
	}

	public int getMajor_unack_count() {
		return major_unack_count;
	}

	public void setMajor_unack_count(int major_unack_count) {
		this.major_unack_count = major_unack_count;
	}

	public int getMinor_count() {
		return minor_count;
	}

	public void setMinor_count(int minor_count) {
		this.minor_count = minor_count;
	}

	public int getMinor_unack_count() {
		return minor_unack_count;
	}

	public void setMinor_unack_count(int minor_unack_count) {
		this.minor_unack_count = minor_unack_count;
	}

	@Override
	public Model4AlarmSummary copy() {
		Model4AlarmSummary model = new Model4AlarmSummary();
		model.ne_id = this.ne_id;
		model.communication_fail_count = this.communication_fail_count;
		model.communication_fail_unack_count = this.communication_fail_unack_count;
		model.critical_count = this.critical_count;
		model.critical_unack_count = this.critical_unack_count;
		model.major_count = this.major_count;
		model.major_unack_count = this.major_unack_count;
		model.minor_count = this.minor_count;
		model.minor_unack_count = this.minor_unack_count;
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
		stringBuilder.append(indent).append(S_TB).append("ne_id").append(S_DL).append(this.ne_id).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("communication_fail_count").append(S_DL).append(this.communication_fail_count).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("communication_fail_unack_count").append(S_DL).append(this.communication_fail_unack_count).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("critical_count").append(S_DL).append(this.critical_count).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("critical_unack_count").append(S_DL).append(this.critical_unack_count).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("major_count").append(S_DL).append(this.major_count).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("major_unack_count").append(S_DL).append(this.major_unack_count).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("minor_count").append(S_DL).append(this.minor_count).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("minor_unack_count").append(S_DL).append(this.minor_unack_count).append(S_NL);
		stringBuilder.append(indent).append(S_RB);
		return stringBuilder.toString();
	}

	@Override
	public String toDisplayString(String indent) {
		return toString(indent);
	}

}
