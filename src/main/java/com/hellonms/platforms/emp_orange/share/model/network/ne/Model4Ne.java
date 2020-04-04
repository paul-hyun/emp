/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.share.model.network.ne;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hellonms.platforms.emp_core.share.model.ModelIf;
import com.hellonms.platforms.emp_onion.share.model.ACCESS;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE;
import com.hellonms.platforms.emp_orange.share.model.network.ne_group.Model4NeGroup;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.Model4NeSessionIf;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.NE_SESSION_PROTOCOL;
import com.hellonms.platforms.emp_util.date.UtilDate;
import com.hellonms.platforms.emp_util.string.UtilString;

/**
 * <p>
 * NE 모델
 * </p>
 * 
 * @since 1.6
 * @create 2015. 3. 10.
 * @modified 2015. 3. 10.
 * @author cchyun
 * 
 */
@SuppressWarnings("serial")
public class Model4Ne implements ModelIf {

	public static final int NMS_NE_ID = 1;

	public static Model4Ne newInstance() {
		Model4Ne model = new Model4Ne();
		model.ne_id = 0;
		model.ne_group_id = Model4NeGroup.ROOT_NE_GROUP_ID;
		model.ne_code = 0;
		model.ne_name = "";
		model.access = ACCESS.ACCESS_DELETE;
		model.description = "";
		model.ne_icon = EMP_MODEL_NE.NE_ICON;
		model.ne_map_location_x = 0;
		model.ne_map_location_y = 0;
		model.monitoring_timestamp = new Date();
		model.admin_state = true;
		model.meta_data = null;
		model.creator = "";
		model.create_time = new Date();
		model.updater = "";
		model.update_time = new Date();
		return model;
	}

	/**
	 * 구분자 (DB에서 자동 생성되는 값)
	 */
	protected int ne_id;

	/**
	 * NE가 속한 NE Group
	 */
	protected int ne_group_id;

	/**
	 * NE 타입 구분자
	 */
	protected int ne_code;

	/**
	 * Ne Name
	 */
	protected String ne_name;

	/**
	 * NE 대한 접근 권한
	 */
	protected ACCESS access;

	/**
	 * 설명
	 */
	protected String description;

	/**
	 * NE를 표현할 아이콘
	 */
	protected String ne_icon;

	/**
	 * 좌표 X
	 */
	protected int ne_map_location_x;

	/**
	 * 좌표 Y
	 */
	protected int ne_map_location_y;

	/**
	 * 마지막으로 NE 정보를 조회한 시간
	 */
	protected Date monitoring_timestamp;

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

	/**
	 * 통신채널 목록
	 */
	protected Map<NE_SESSION_PROTOCOL, Model4NeSessionIf> ne_session_map = new LinkedHashMap<NE_SESSION_PROTOCOL, Model4NeSessionIf>();

	// ////////////////////////////////////////////////////////
	// DB에 저장되지 않는 값
	// ////////////////////////////////////////////////////////
	/**
	 * NE그룹 이름
	 */
	protected String ne_group_name;

	public int getNe_id() {
		return ne_id;
	}

	public void setNe_id(int ne_id) {
		this.ne_id = ne_id;
	}

	public int getNe_group_id() {
		return ne_group_id;
	}

	public void setNe_group_id(int ne_group_id) {
		this.ne_group_id = ne_group_id;
	}

	@JsonIgnore
	public EMP_MODEL_NE getNe_def() {
		if (ne_code == 0) {
			return null;
		}
		EMP_MODEL_NE ne_def = EMP_MODEL.current().getNe(ne_code);
		if (ne_def == null) {
			throw new RuntimeException(UtilString.format("unknown ne: '{}'", ne_code));
		}
		return ne_def;
	}

	public int getNe_code() {
		return ne_code;
	}

	public void setNe_code(int ne_code) {
		this.ne_code = ne_code;
	}

	public boolean isNMS() {
		return ne_code == EMP_MODEL_NE.CODE_NMS;
	}

	public String getNe_name() {
		return ne_name;
	}

	public void setNe_name(String ne_name) {
		this.ne_name = ne_name;
	}

	public ACCESS getAccess() {
		return access;
	}

	public void setAccess(ACCESS access) {
		this.access = access;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getNe_icon() {
		return ne_icon;
	}

	public void setNe_icon(String ne_icon) {
		this.ne_icon = ne_icon;
	}

	public int getNe_map_location_x() {
		return ne_map_location_x;
	}

	public void setNe_map_location_x(int ne_map_location_x) {
		this.ne_map_location_x = ne_map_location_x;
	}

	public int getNe_map_location_y() {
		return ne_map_location_y;
	}

	public void setNe_map_location_y(int ne_map_location_y) {
		this.ne_map_location_y = ne_map_location_y;
	}

	public Date getMonitoring_timestamp() {
		return monitoring_timestamp;
	}

	public void setMonitoring_timestamp(Date monitoring_timestamp) {
		this.monitoring_timestamp = monitoring_timestamp;
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

	public String getNe_group_name() {
		return ne_group_name;
	}

	public void setNe_group_name(String ne_group_name) {
		this.ne_group_name = ne_group_name;
	}

	public void addNeSession(Model4NeSessionIf ne_session) {
		this.ne_session_map.put(ne_session.getProtocol(), ne_session);
	}

	public Model4NeSessionIf getNeSession(NE_SESSION_PROTOCOL protocol) {
		return this.ne_session_map.get(protocol);
	}

	public Model4NeSessionIf[] getNeSessions() {
		return this.ne_session_map.values().toArray(new Model4NeSessionIf[0]);
	}

	public void clearNeSession() {
		this.ne_session_map.clear();
	}

	@Override
	public Model4Ne copy() {
		Model4Ne model = new Model4Ne();
		model.ne_id = this.ne_id;
		model.ne_group_id = this.ne_group_id;
		model.ne_code = this.ne_code;
		model.ne_name = this.ne_name;
		model.access = this.access;
		model.description = this.description;
		model.ne_icon = this.ne_icon;
		model.ne_map_location_x = this.ne_map_location_x;
		model.ne_map_location_y = this.ne_map_location_y;
		model.monitoring_timestamp = this.monitoring_timestamp;
		model.admin_state = this.admin_state;
		model.meta_data = this.meta_data;
		model.creator = this.creator;
		model.create_time = this.create_time;
		model.updater = this.updater;
		model.update_time = this.update_time;
		model.ne_group_name = this.ne_group_name;
		for (Model4NeSessionIf neSession : this.ne_session_map.values()) {
			model.ne_session_map.put(neSession.getProtocol(), (Model4NeSessionIf) neSession.copy());
		}
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
		stringBuilder.append(indent).append(S_TB).append("ne_group_id").append(S_DL).append(ne_group_id).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("ne_code").append(S_DL).append(ne_code).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("ne_name").append(S_DL).append(ne_name).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("access").append(S_DL).append(access).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("description").append(S_DL).append(description).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("ne_icon").append(S_DL).append(ne_icon).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("ne_map_location_x").append(S_DL).append(ne_map_location_x).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("ne_map_location_y").append(S_DL).append(ne_map_location_y).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("monitoring_timestamp").append(S_DL).append(monitoring_timestamp).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("admin_state").append(S_DL).append(admin_state).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("meta_data").append(S_DL).append(meta_data).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("creator").append(S_DL).append(creator).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("create_time").append(S_DL).append(create_time).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("updater").append(S_DL).append(updater).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("update_time").append(S_DL).append(update_time).append(S_NL);
		for (Model4NeSessionIf ne_session : ne_session_map.values()) {
			stringBuilder.append(ne_session.toString(indent + S_TB)).append(S_NL);
		}
		stringBuilder.append(indent).append(S_RB);
		return stringBuilder.toString();
	}

	@Override
	public String toDisplayString(String indent) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(indent).append(getClass().getName()).append(S_LB).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("ne_id").append(S_DL).append(ne_id).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("ne_group_id").append(S_DL).append(ne_group_id).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("ne_code").append(S_DL).append(ne_code).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("ne_name").append(S_DL).append(ne_name).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("access").append(S_DL).append(access).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("description").append(S_DL).append(description).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("ne_icon").append(S_DL).append(ne_icon).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("admin_state").append(S_DL).append(admin_state).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("creator").append(S_DL).append(creator).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("create_time").append(S_DL).append(UtilDate.format(create_time)).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("updater").append(S_DL).append(updater).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("update_time").append(S_DL).append(UtilDate.format(update_time)).append(S_NL);
		for (Model4NeSessionIf ne_session : ne_session_map.values()) {
			stringBuilder.append(ne_session.toDisplayString(indent + S_TB)).append(S_NL);
		}
		stringBuilder.append(indent).append(S_RB);
		return stringBuilder.toString();
	}

}
