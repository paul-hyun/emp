/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.share.model.fault.event;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hellonms.platforms.emp_core.share.model.ModelIf;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_EVENT;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO;
import com.hellonms.platforms.emp_util.string.UtilString;

/**
 * <p>
 * Event
 * </p>
 *
 * @since 1.6
 * @create 2015. 4. 21.
 * @modified 2015. 4. 21.
 * @author cchyun
 *
 */
@SuppressWarnings("serial")
public class Model4Event implements ModelIf {

	/**
	 * Event 구분자
	 */
	private long event_id;

	private int ne_id;

	private int ne_info_code;

	private int ne_info_index;

	private String location_display;

	private int event_code;

	private SEVERITY severity;

	/**
	 * Event 발생 시간
	 */
	private Date gen_time;

	/**
	 * Event 발생 타입
	 */
	private GEN_TYPE gen_type;

	/**
	 * Event 설명
	 */
	private String description;

	public long getEvent_id() {
		return event_id;
	}

	public void setEvent_id(long event_id) {
		this.event_id = event_id;
	}

	public int getNe_id() {
		return ne_id;
	}

	public void setNe_id(int ne_id) {
		this.ne_id = ne_id;
	}

	@JsonIgnore
	public EMP_MODEL_NE_INFO getNe_info_def() {
		EMP_MODEL_NE_INFO ne_info_def = EMP_MODEL.current().getNe_info(ne_info_code);
		if (ne_info_def == null) {
			throw new RuntimeException(UtilString.format("unknown info: '{}'", ne_info_code));
		}
		return ne_info_def;
	}

	public String getNe_info_name() {
		EMP_MODEL_NE_INFO ne_info_def = EMP_MODEL.current().getNe_info(ne_info_code);
		if (ne_info_def == null) {
			return "";
		}
		return ne_info_def.getName();
	}

	public int getNe_info_code() {
		return ne_info_code;
	}

	public void setNe_info_code(int ne_info_code) {
		this.ne_info_code = ne_info_code;
	}

	public int getNe_info_index() {
		return ne_info_index;
	}

	public void setNe_info_index(int ne_info_index) {
		this.ne_info_index = ne_info_index;
	}

	public String getLocation_display() {
		return location_display;
	}

	public void setLocation_display(String location_display) {
		this.location_display = location_display;
	}

	@JsonIgnore
	public EMP_MODEL_EVENT getEvent_def() {
		EMP_MODEL_EVENT event_def = EMP_MODEL.current().getEvent(event_code);
		if (event_def == null) {
			throw new RuntimeException(UtilString.format("unknown event: '{}'", event_code));
		}
		return event_def;
	}

	public String getEvent_name() {
		EMP_MODEL_EVENT event_def = EMP_MODEL.current().getEvent(event_code);
		if (event_def == null) {
			return "";
		}
		return event_def.getSpecific_problem();
	}

	public int getEvent_code() {
		return event_code;
	}

	public void setEvent_code(int event_code) {
		this.event_code = event_code;
	}

	public SEVERITY getSeverity() {
		return severity;
	}

	public void setSeverity(SEVERITY severity) {
		this.severity = severity;
	}

	public Date getGen_time() {
		return gen_time;
	}

	public void setGen_time(Date gen_time) {
		this.gen_time = gen_time;
	}

	public GEN_TYPE getGen_type() {
		return gen_type;
	}

	public void setGen_type(GEN_TYPE gen_type) {
		this.gen_type = gen_type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isAlarm() {
		switch (severity) {
		case COMMUNICATION_FAIL:
		case CRITICAL:
		case MAJOR:
		case MINOR:
		case CLEAR:
			return true;
		case INFO:
		default:
			return false;
		}
	}

	@Override
	public Model4Event copy() {
		Model4Event model = new Model4Event();
		model.event_id = this.event_id;
		model.ne_id = this.ne_id;
		model.ne_info_code = this.ne_info_code;
		model.ne_info_index = this.ne_info_index;
		model.location_display = this.location_display;
		model.event_code = this.event_code;
		model.severity = this.severity;
		model.gen_time = this.gen_time;
		model.gen_type = this.gen_type;
		model.description = this.description;
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
		stringBuilder.append(indent).append(S_TB).append("event_id").append(S_DL).append(event_id).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("ne_id").append(S_DL).append(ne_id).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("ne_info_code").append(S_DL).append(ne_info_code).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("ne_info_index").append(S_DL).append(ne_info_index).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("location_display").append(S_DL).append(location_display).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("event_code").append(S_DL).append(event_code).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("severity").append(S_DL).append(severity).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("gen_time").append(S_DL).append(gen_time).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("gen_type").append(S_DL).append(gen_type).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("description").append(S_DL).append(description).append(S_NL);
		stringBuilder.append(indent).append(S_RB);
		return stringBuilder.toString();
	}

	@Override
	public String toDisplayString(String indent) {
		return toString(indent);
	}

	public Model4Alarm toAlarm() {
		Model4Alarm model = new Model4Alarm();
		model.setGen_first_event_id(this.event_id);
		model.setGen_last_event_id(this.event_id);
		model.setClear_event_id(0);
		model.setNe_id(this.ne_id);
		model.setNe_info_code(this.ne_info_code);
		model.setNe_info_index(this.ne_info_index);
		model.setLocation_display(location_display);
		model.setEvent_code(this.event_code);
		model.setSeverity(this.severity);
		model.setGen_count(1);
		model.setGen_first_time(this.gen_time);
		model.setGen_last_time(this.gen_time);
		model.setGen_type(this.gen_type);
		model.setGen_description(this.description);
		model.setClear_state(false);
		model.setClear_time(new Date(0L));
		model.setClear_type(this.gen_type);
		model.setClear_description("");
		model.setAck_state(false);
		model.setAck_time(new Date(0L));
		model.setAck_user("");
		return model;
	}

}
