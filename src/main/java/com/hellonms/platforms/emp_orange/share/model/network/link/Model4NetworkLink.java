/**
 * Copyright 2016 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.share.model.network.link;

import java.util.Date;

import com.hellonms.platforms.emp_core.share.model.ModelIf;
import com.hellonms.platforms.emp_util.date.UtilDate;

/**
 * <p>
 * Network Link
 * </p>
 *
 * @since 1.6
 * @create 2016. 1. 25.
 * @modified 2016. 1. 25.
 * @author cchyun
 */
@SuppressWarnings("serial")
public class Model4NetworkLink implements ModelIf {

	private int network_link_id;

	private int ne_group_id_from;

	private int ne_id_from;

	private int ne_group_id_to;

	private int ne_id_to;

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

	public int getNetwork_link_id() {
		return network_link_id;
	}

	public void setNetwork_link_id(int network_link_id) {
		this.network_link_id = network_link_id;
	}

	public int getNe_group_id_from() {
		return ne_group_id_from;
	}

	public void setNe_group_id_from(int ne_group_id_from) {
		this.ne_group_id_from = ne_group_id_from;
	}

	public int getNe_id_from() {
		return ne_id_from;
	}

	public void setNe_id_from(int ne_id_from) {
		this.ne_id_from = ne_id_from;
	}

	public int getNe_group_id_to() {
		return ne_group_id_to;
	}

	public void setNe_group_id_to(int ne_group_id_to) {
		this.ne_group_id_to = ne_group_id_to;
	}

	public int getNe_id_to() {
		return ne_id_to;
	}

	public void setNe_id_to(int ne_id_to) {
		this.ne_id_to = ne_id_to;
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
	public Model4NetworkLink copy() {
		Model4NetworkLink model = new Model4NetworkLink();
		model.network_link_id = this.network_link_id;
		model.ne_group_id_from = this.ne_group_id_from;
		model.ne_id_from = this.ne_id_from;
		model.ne_group_id_to = this.ne_group_id_to;
		model.ne_id_to = this.ne_id_to;
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
		stringBuilder.append(indent).append(S_TB).append("network_link_id").append(S_DL).append(network_link_id).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("ne_group_id_from").append(S_DL).append(ne_group_id_from).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("ne_id_from").append(S_DL).append(ne_id_from).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("ne_group_id_to").append(S_DL).append(ne_group_id_to).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("ne_id_to").append(S_DL).append(ne_id_to).append(S_NL);
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
		stringBuilder.append(indent).append(S_TB).append("network_link_id").append(S_DL).append(network_link_id).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("ne_group_id_from").append(S_DL).append(ne_group_id_from).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("ne_id_from").append(S_DL).append(ne_id_from).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("ne_group_id_to").append(S_DL).append(ne_group_id_to).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("ne_id_to").append(S_DL).append(ne_id_to).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("creator").append(S_DL).append(creator).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("create_time").append(S_DL).append(UtilDate.format(create_time)).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("updater").append(S_DL).append(updater).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("update_time").append(S_DL).append(UtilDate.format(update_time)).append(S_NL);
		stringBuilder.append(indent).append(S_RB);
		return stringBuilder.toString();
	}

}
