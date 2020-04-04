/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.share.model.network.ne_group;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hellonms.platforms.emp_core.share.model.ModelIf;
import com.hellonms.platforms.emp_onion.share.model.ACCESS;
import com.hellonms.platforms.emp_util.date.UtilDate;

/**
 * <p>
 * NE그룹 모델
 * </p>
 * 
 * @since 1.6
 * @create 2015. 3. 10.
 * @modified 2015. 3. 10.
 * @author cchyun
 */
@SuppressWarnings("serial")
public class Model4NeGroup implements ModelIf {

	public static final int ROOT_NE_GROUP_ID = 1;

	/**
	 * 구분자 (DB에서 자동 생성되는 값)
	 */
	private int ne_group_id;

	/**
	 * 검색 속도를 높이기 위한 필드 (외쪽)
	 */
	@JsonIgnore
	private int left_bound;

	/**
	 * 검색 속도를 높이기 위한 필드 (오른 쪽)
	 */
	@JsonIgnore
	private int right_bound;

	/**
	 * 부모 ne_group_id
	 */
	private int parent_ne_group_id;

	/**
	 * NE그룹 Name
	 */
	private String ne_group_name;

	/**
	 * 접근 권한
	 */
	private ACCESS access;

	/**
	 * 설명
	 */
	private String description;

	/**
	 * NE Group을 표현할 이미지
	 */
	private String ne_group_icon;

	/**
	 * NE Group이 Map 배경화면
	 */
	private int ne_group_map_bg_color;

	/**
	 * NE Group이 Map 배경화면
	 */
	private String ne_group_map_bg_image;

	/**
	 * 좌표 X
	 */
	private int ne_group_map_location_x;

	/**
	 * 좌표 Y
	 */
	private int ne_group_map_location_y;

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

	public Model4NeGroup() {
		parent_ne_group_id = ROOT_NE_GROUP_ID;
		ne_group_name = "";
		access = ACCESS.ACCESS_DELETE;
		description = "";
		ne_group_icon = "/data/image/node_icon/NE_GROUP.png";
		ne_group_map_bg_color = 0x00FFFFFF;
		ne_group_map_bg_image = "";
		ne_group_map_location_x = 0;
		ne_group_map_location_y = 0;
		admin_state = true;
	}

	public int getNe_group_id() {
		return ne_group_id;
	}

	public void setNe_group_id(int ne_group_id) {
		this.ne_group_id = ne_group_id;
	}

	public int getLeft_bound() {
		return left_bound;
	}

	public void setLeft_bound(int left_bound) {
		this.left_bound = left_bound;
	}

	public int getRight_bound() {
		return right_bound;
	}

	public void setRight_bound(int right_bound) {
		this.right_bound = right_bound;
	}

	public int getParent_ne_group_id() {
		return parent_ne_group_id;
	}

	public void setParent_ne_group_id(int parent_ne_group_id) {
		this.parent_ne_group_id = parent_ne_group_id;
	}

	public String getNe_group_name() {
		return ne_group_name;
	}

	public void setNe_group_name(String ne_group_name) {
		this.ne_group_name = ne_group_name;
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

	public String getNe_group_icon() {
		return ne_group_icon;
	}

	public void setNe_group_icon(String ne_group_icon) {
		this.ne_group_icon = ne_group_icon;
	}

	public int getNe_group_map_bg_color() {
		return ne_group_map_bg_color;
	}

	public void setNe_group_map_bg_color(int ne_group_map_bg_color) {
		this.ne_group_map_bg_color = ne_group_map_bg_color;
	}

	public String getNe_group_map_bg_image() {
		return ne_group_map_bg_image;
	}

	public void setNe_group_map_bg_image(String ne_group_map_bg_image) {
		this.ne_group_map_bg_image = ne_group_map_bg_image;
	}

	public int getNe_group_map_location_x() {
		return ne_group_map_location_x;
	}

	public void setNe_group_map_location_x(int ne_group_map_location_x) {
		this.ne_group_map_location_x = ne_group_map_location_x;
	}

	public int getNe_group_map_location_y() {
		return ne_group_map_location_y;
	}

	public void setNe_group_map_location_y(int ne_group_map_location_y) {
		this.ne_group_map_location_y = ne_group_map_location_y;
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
	public Model4NeGroup copy() {
		Model4NeGroup model = new Model4NeGroup();
		model.ne_group_id = this.ne_group_id;
		model.left_bound = this.left_bound;
		model.right_bound = this.right_bound;
		model.parent_ne_group_id = this.parent_ne_group_id;
		model.ne_group_name = this.ne_group_name;
		model.access = this.access;
		model.description = this.description;
		model.ne_group_icon = this.ne_group_icon;
		model.ne_group_map_bg_color = this.ne_group_map_bg_color;
		model.ne_group_map_bg_image = this.ne_group_map_bg_image;
		model.ne_group_map_location_x = this.ne_group_map_location_x;
		model.ne_group_map_location_y = this.ne_group_map_location_y;
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
		stringBuilder.append(indent).append(S_TB).append("ne_group_id").append(S_DL).append(ne_group_id).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("left_bound").append(S_DL).append(left_bound).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("right_bound").append(S_DL).append(right_bound).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("parent_ne_group_id").append(S_DL).append(parent_ne_group_id).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("ne_group_name").append(S_DL).append(ne_group_name).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("access").append(S_DL).append(access).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("description").append(S_DL).append(description).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("ne_group_icon").append(S_DL).append(ne_group_icon).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("ne_group_map_bg_color").append(S_DL).append(ne_group_map_bg_color).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("ne_group_map_bg_image").append(S_DL).append(ne_group_map_bg_image).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("ne_group_map_location_x").append(S_DL).append(ne_group_map_location_x).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("ne_group_map_location_y").append(S_DL).append(ne_group_map_location_y).append(S_NL);
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
		stringBuilder.append(indent).append(S_TB).append("ne_group_id").append(S_DL).append(ne_group_id).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("parent_ne_group_id").append(S_DL).append(parent_ne_group_id).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("ne_group_name").append(S_DL).append(ne_group_name).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("access").append(S_DL).append(access).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("description").append(S_DL).append(description).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("ne_group_icon").append(S_DL).append(ne_group_icon).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("admin_state").append(S_DL).append(admin_state).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("creator").append(S_DL).append(creator).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("create_time").append(S_DL).append(UtilDate.format(create_time)).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("updater").append(S_DL).append(updater).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("update_time").append(S_DL).append(UtilDate.format(update_time)).append(S_NL);
		stringBuilder.append(indent).append(S_RB);
		return stringBuilder.toString();
	}

}
