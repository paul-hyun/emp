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
import com.hellonms.platforms.emp_util.date.UtilDate;
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
public class Model4Alarm implements ModelIf {

	/**
	 * 최초 알람이 발생한 event_id
	 */
	protected long gen_first_event_id;

	/**
	 * 마지막 알람이 발생한 event_id (중복발생일 경우를 대비)
	 */
	protected long gen_last_event_id;

	/**
	 * 알람이 해제된 event_id
	 */
	protected long clear_event_id;

	protected int ne_id;

	protected int ne_info_code;

	protected int info_index;

	protected String location_display;

	protected int event_code;

	protected SEVERITY severity;

	/**
	 * 발생회수
	 */
	protected int gen_count;

	/**
	 * Event 발생 시간
	 */
	protected Date gen_first_time;

	/**
	 * Event 발생 시간
	 */
	protected Date gen_last_time;

	/**
	 * Alarm 발생 타입
	 */
	protected GEN_TYPE gen_type;

	/**
	 * Event 설명
	 */
	protected String gen_description;

	/**
	 * 알람 해제 여부
	 */
	protected boolean clear_state;

	/**
	 * 알람 해제 시간
	 */
	protected Date clear_time;

	/**
	 * Alarm 복구 타입
	 */
	protected GEN_TYPE clear_type;

	/**
	 * 알람 해제 설명
	 */
	protected String clear_description;

	/**
	 * 알람 인지 여부
	 */
	protected boolean ack_state;

	/**
	 * 알람 마지막 인지 시간
	 */
	protected Date ack_time;

	/**
	 * 알람 마지막 인지 사용자
	 */
	protected String ack_user;

	public long getGen_first_event_id() {
		return gen_first_event_id;
	}

	public void setGen_first_event_id(long gen_first_event_id) {
		this.gen_first_event_id = gen_first_event_id;
	}

	public long getGen_last_event_id() {
		return gen_last_event_id;
	}

	public void setGen_last_event_id(long gen_last_event_id) {
		this.gen_last_event_id = gen_last_event_id;
	}

	public long getClear_event_id() {
		return clear_event_id;
	}

	public void setClear_event_id(long clear_event_id) {
		this.clear_event_id = clear_event_id;
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
		return info_index;
	}

	public void setNe_info_index(int info_index) {
		this.info_index = info_index;
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

	public int getGen_count() {
		return gen_count;
	}

	public void setGen_count(int gen_count) {
		this.gen_count = gen_count;
	}

	public Date getGen_first_time() {
		return gen_first_time;
	}

	public void setGen_first_time(Date gen_first_time) {
		this.gen_first_time = gen_first_time;
	}

	public Date getGen_last_time() {
		return gen_last_time;
	}

	public void setGen_last_time(Date gen_last_time) {
		this.gen_last_time = gen_last_time;
	}

	public GEN_TYPE getGen_type() {
		return gen_type;
	}

	public void setGen_type(GEN_TYPE gen_type) {
		this.gen_type = gen_type;
	}

	public String getGen_description() {
		return gen_description;
	}

	public void setGen_description(String gen_description) {
		this.gen_description = gen_description;
	}

	public boolean isClear_state() {
		return clear_state;
	}

	public void setClear_state(boolean clear_state) {
		this.clear_state = clear_state;
	}

	public Date getClear_time() {
		return clear_time;
	}

	public void setClear_time(Date clear_time) {
		this.clear_time = clear_time;
	}

	public GEN_TYPE getClear_type() {
		return clear_type;
	}

	public void setClear_type(GEN_TYPE clear_type) {
		this.clear_type = clear_type;
	}

	public String getClear_description() {
		return clear_description;
	}

	public void setClear_description(String clear_description) {
		this.clear_description = clear_description;
	}

	public boolean isAck_state() {
		return ack_state;
	}

	public void setAck_state(boolean ack_state) {
		this.ack_state = ack_state;
	}

	public Date getAck_time() {
		return ack_time;
	}

	public void setAck_time(Date ack_time) {
		this.ack_time = ack_time;
	}

	public String getAck_user() {
		return ack_user;
	}

	public void setAck_user(String ack_user) {
		this.ack_user = ack_user;
	}

	@Override
	public Model4Alarm copy() {
		Model4Alarm model = new Model4Alarm();
		model.gen_first_event_id = this.gen_first_event_id;
		model.gen_last_event_id = this.gen_last_event_id;
		model.clear_event_id = this.clear_event_id;
		model.ne_id = this.ne_id;
		model.ne_info_code = this.ne_info_code;
		model.info_index = this.info_index;
		model.location_display = this.location_display;
		model.event_code = this.event_code;
		model.severity = this.severity;
		model.gen_count = this.gen_count;
		model.gen_first_time = this.gen_first_time;
		model.gen_last_time = this.gen_last_time;
		model.gen_type = this.gen_type;
		model.gen_description = this.gen_description;
		model.clear_state = this.clear_state;
		model.clear_time = this.clear_time;
		model.clear_type = this.clear_type;
		model.clear_description = this.clear_description;
		model.ack_state = this.ack_state;
		model.ack_time = this.ack_time;
		model.ack_user = this.ack_user;
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
		stringBuilder.append(indent).append(S_TB).append("gen_first_event_id").append(S_DL).append(this.gen_first_event_id).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("gen_last_event_id").append(S_DL).append(this.gen_last_event_id).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("clear_event_id").append(S_DL).append(this.clear_event_id).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("ne_id").append(S_DL).append(this.ne_id).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("ne_info_code").append(S_DL).append(this.ne_info_code).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("info_index").append(S_DL).append(this.info_index).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("location_display").append(S_DL).append(this.location_display).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("event_code").append(S_DL).append(this.event_code).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("severity").append(S_DL).append(this.severity).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("gen_count").append(S_DL).append(this.gen_count).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("gen_first_time").append(S_DL).append(this.gen_first_time).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("gen_last_time").append(S_DL).append(this.gen_last_time).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("gen_type").append(S_DL).append(this.gen_type).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("gen_description").append(S_DL).append(this.gen_description).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("clear_state").append(S_DL).append(this.clear_state).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("clear_time").append(S_DL).append(this.clear_time).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("clear_type").append(S_DL).append(this.clear_type).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("clear_description").append(S_DL).append(this.clear_description).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("ack_state").append(S_DL).append(this.ack_state).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("ack_time").append(S_DL).append(this.ack_time).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("ack_user").append(S_DL).append(this.ack_user).append(S_NL);
		stringBuilder.append(indent).append(S_RB);
		return stringBuilder.toString();
	}

	@Override
	public String toDisplayString(String indent) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(indent).append(getClass().getName()).append(S_LB).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("gen_first_event_id").append(S_DL).append(this.gen_first_event_id).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("clear_event_id").append(S_DL).append(this.clear_event_id).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("ne_id").append(S_DL).append(this.ne_id).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("ne_info_code").append(S_DL).append(this.ne_info_code).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("location_display").append(S_DL).append(this.location_display).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("event_code").append(S_DL).append(this.event_code).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("severity").append(S_DL).append(this.severity).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("gen_first_time").append(S_DL).append(UtilDate.format(gen_first_time)).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("gen_type").append(S_DL).append(this.gen_type).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("gen_description").append(S_DL).append(this.gen_description).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("clear_state").append(S_DL).append(this.clear_state).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("clear_time").append(S_DL).append(clear_state ? UtilDate.format(clear_time) : "").append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("clear_type").append(S_DL).append(clear_state ? clear_type : "").append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("clear_description").append(S_DL).append(clear_state ? clear_description : "").append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("ack_state").append(S_DL).append(this.ack_state).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("ack_time").append(S_DL).append(ack_state ? UtilDate.format(ack_time) : "").append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("ack_user").append(S_DL).append(ack_state ? ack_user : "").append(S_NL);
		stringBuilder.append(indent).append(S_RB);
		return stringBuilder.toString();
	}

	public Model4Alarm setEventByClear(Model4Event event) {
		this.clear_event_id = event.getEvent_id();
		this.clear_state = true;
		this.clear_time = event.getGen_time();
		this.clear_type = event.getGen_type();
		this.clear_description = event.getDescription();
		return this;
	}

	public Model4Alarm setEventByRepetition(Model4Event event) {
		this.gen_last_event_id = event.getEvent_id();
		this.gen_count++;
		this.gen_last_time = event.getGen_time();
		return this;
	}

}
