/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.share.model.fault.event;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hellonms.platforms.emp_core.share.model.ModelIf;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_EVENT;

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
public class Model4AlarmStatistics implements ModelIf {

	public enum ITEM {
		/**
		 * 등급별
		 */
		SEVERITY,

		/**
		 * 원인별
		 */
		CAUSE;
	}

	public static class Model4AlarmStatisticsItem implements Serializable {

		private String name;

		private int value;

		public Model4AlarmStatisticsItem() {
		}

		public Model4AlarmStatisticsItem(String name, int value) {
			this.name = name;
			this.value = value;
		}

		public Model4AlarmStatisticsItem copy() {
			Model4AlarmStatisticsItem model = new Model4AlarmStatisticsItem();
			model.name = name;
			model.value = value;
			return model;
		}

	}

	/**
	 * 통계시간
	 */
	private Date collect_time;

	/**
	 * 통계항목
	 */
	private List<Model4AlarmStatisticsItem> item_list = new ArrayList<Model4AlarmStatisticsItem>();

	public Date getCollect_time() {
		return collect_time;
	}

	public void setCollect_time(Date collect_time) {
		this.collect_time = collect_time;
	}

	public int getItemCount() {
		return item_list.size();
	}

	public void initializeItems(EMP_MODEL_EVENT[] items) {
		item_list.clear();
		for (EMP_MODEL_EVENT event_def : items) {
			item_list.add(new Model4AlarmStatisticsItem(event_def.getSpecific_problem(), 0));
		}
	}

	public void initializeItems(SEVERITY[] items) {
		item_list.clear();
		for (SEVERITY item : items) {
			item_list.add(new Model4AlarmStatisticsItem(item.toString(), 0));
		}
	}

	public String getItemName(int index) {
		return item_list.get(index).name;
	}

	public int getItemValue(int index) {
		return item_list.get(index).value;
	}

	public void setItemValue(int index, int value) {
		item_list.get(index).value = value;
	}

	public void setItemValue(String name, int value) {
		for (Model4AlarmStatisticsItem item : item_list) {
			if (item.name.equals(name)) {
				item.value = value;
				break;
			}
		}
	}

	@Override
	public Model4AlarmStatistics copy() {
		Model4AlarmStatistics model = new Model4AlarmStatistics();
		model.collect_time = collect_time;
		for (Model4AlarmStatisticsItem item : this.item_list) {
			model.item_list.add(item.copy());
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
		stringBuilder.append(indent).append(S_TB).append("collect_time").append(S_DL).append(collect_time).append(S_NL);
		for (Model4AlarmStatisticsItem item : item_list) {
			stringBuilder.append(indent).append(S_TB).append(S_TB).append(item.name).append(S_DL).append(item.value).append(S_NL);
		}
		stringBuilder.append(indent).append(S_RB);
		return stringBuilder.toString();
	}

	@Override
	public String toDisplayString(String indent) {
		return toString(indent);
	}

}
