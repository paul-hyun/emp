/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.share.model.network.ne_info;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO_FIELD;
import com.hellonms.platforms.emp_util.string.UtilString;

/**
 * <p>
 * NE 통계
 * </p>
 *
 * @since 1.6
 * @create 2015. 4. 8.
 * @modified 2015. 4. 8.
 * @author cchyun
 *
 */
@SuppressWarnings("serial")
public class Model4NeStatistics implements Model4NeStatisticsIf {

	private int ne_id;

	private int ne_info_code;

	private int ne_info_index;

	private String location_display;

	private Date collect_time;

	private int rop_num = 1;

	private Map<String, Long[]> ne_info_value_map = new LinkedHashMap<String, Long[]>();

	public Model4NeStatistics() {
	}

	public Model4NeStatistics(Model4NeStatistics ne_statistics) {
		this.ne_id = ne_statistics.ne_id;
		this.ne_info_code = ne_statistics.ne_info_code;
		this.ne_info_index = ne_statistics.ne_info_index;
		this.collect_time = ne_statistics.collect_time;
		for (Map.Entry<String, Long[]> entry : this.ne_info_value_map.entrySet()) {
			Long[] field_value = new Long[2];
			System.arraycopy(entry.getValue(), 0, field_value, 0, 2);
			this.ne_info_value_map.put(entry.getKey(), field_value);
		}
	}

	@Override
	public int getNe_id() {
		return ne_id;
	}

	public void setNe_id(int ne_id) {
		this.ne_id = ne_id;
	}

	@Override
	public EMP_MODEL_NE_INFO getNe_info_def() {
		EMP_MODEL_NE_INFO ne_info_def = EMP_MODEL.current().getNe_info(ne_info_code);
		if (ne_info_def == null) {
			throw new RuntimeException(UtilString.format("unknown info: '{}'", ne_info_code));
		}
		return ne_info_def;
	}

	@Override
	public int getNe_info_code() {
		return ne_info_code;
	}

	public void setNe_info_code(int ne_info_code) {
		this.ne_info_code = ne_info_code;
	}

	@Override
	public int getNe_info_index() {
		return ne_info_index;
	}

	public void setNe_info_index(int ne_info_index) {
		this.ne_info_index = ne_info_index;
	}

	@Override
	public String getLocation_display() {
		return location_display;
	}

	public void setLocation_display(String location_display) {
		this.location_display = location_display;
	}

	public Date getCollect_time() {
		return collect_time;
	}

	public void setCollect_time(Date collect_time) {
		this.collect_time = collect_time;
	}

	public int getRop_num() {
		return rop_num;
	}

	public void setRop_num(int rop_num) {
		this.rop_num = rop_num;
	}

	@Override
	public EMP_MODEL_NE_INFO_FIELD[] getField_defs() {
		return getNe_info_def().getNe_statistics();
	}

	@Override
	public EMP_MODEL_NE_INFO_FIELD getField_def(String ne_info_field_name) {
		EMP_MODEL_NE_INFO_FIELD ne_info_field_def = getNe_info_def().getNe_info_field(ne_info_field_name);
		if (ne_info_field_def == null) {
			throw new RuntimeException(UtilString.format("unknown info_field: '{}'", ne_info_field_name));
		}
		if (!ne_info_field_def.isStat_enable()) {
			throw new RuntimeException(UtilString.format("no statatistics info_field: '{}'", ne_info_field_name));
		}
		return ne_info_field_def;
	}

	@Override
	public boolean hasField_code(EMP_MODEL_NE_INFO_FIELD ne_info_field_def) {
		try {
			return getField_def(ne_info_field_def.getName()) != null;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean hasField_code(String ne_info_field_name) {
		try {
			return getField_def(ne_info_field_name) != null;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Long getField_value(EMP_MODEL_NE_INFO_FIELD ne_info_field_def) {
		Long[] field_value = ne_info_value_map.get(ne_info_field_def.getName());
		return field_value == null ? null : field_value[0];
	}

	@Override
	public Long getField_value(String ne_info_field_name) {
		return getField_value(getField_def(ne_info_field_name));
	}

	public void setField_value(EMP_MODEL_NE_INFO_FIELD ne_info_field_def, Long value) {
		Long[] field_value = ne_info_value_map.get(ne_info_field_def.getName());
		if (field_value == null) {
			field_value = new Long[] { null, null };
		}
		field_value[0] = value;
		ne_info_value_map.put(ne_info_field_def.getName(), field_value);
	}

	public void setField_value(String ne_info_field_name, Long value) {
		setField_value(getField_def(ne_info_field_name), value);
	}

	@Override
	public Long getField_counter(EMP_MODEL_NE_INFO_FIELD ne_info_field_def) {
		Long[] field_value = ne_info_value_map.get(ne_info_field_def.getName());
		return field_value == null ? null : field_value[1];
	}

	@Override
	public Long getField_counter(String ne_info_field_name) {
		return getField_counter(getField_def(ne_info_field_name));
	}

	public void setField_counter(EMP_MODEL_NE_INFO_FIELD ne_info_field_def, Long counter) {
		Long[] field_value = ne_info_value_map.get(ne_info_field_def.getName());
		if (field_value == null) {
			field_value = new Long[] { null, null };
		}
		field_value[1] = counter;
		ne_info_value_map.put(ne_info_field_def.getName(), field_value);
	}

	public void setField_counter(String ne_info_field_name, Long counter) {
		setField_counter(getField_def(ne_info_field_name), counter);
	}

	@Override
	public Map<String, Object> getMap() {
		LinkedHashMap<String, Object> model = new LinkedHashMap<String, Object>();
		for (EMP_MODEL_NE_INFO_FIELD aaa : getNe_info_def().getNe_statistics()) {
			Long[] field_value = ne_info_value_map.get(aaa.getName());
			model.put(aaa.getName(), field_value);
		}
		return model;
	}

	@Override
	public void setMap(Map<String, Object> map) {
		for (Entry<String, Object> entry : map.entrySet()) {
			EMP_MODEL_NE_INFO_FIELD ne_info_field_def = getNe_info_def().getNe_info_field(entry.getKey());
			if (ne_info_field_def == null || !ne_info_field_def.isStat_enable()) {
				continue;
			}

			Serializable value = (Serializable) entry.getValue();
			Long[] field_value = { null, null };
			if (value instanceof List<?> && ((List<?>) value).size() == 2) {
				Object[] vvv = ((List<?>) value).toArray();
				for (int i = 0; i < vvv.length; i++) {
					if (vvv[i] instanceof Number) {
						field_value[i] = ((Number) vvv[i]).longValue();
					}
				}
				ne_info_value_map.put(ne_info_field_def.getName(), field_value);
			}
		}
	}

	@Override
	public Model4NeStatistics copy() {
		Model4NeStatistics model = new Model4NeStatistics();
		model.ne_id = this.ne_id;
		model.ne_info_code = this.ne_info_code;
		model.ne_info_index = this.ne_info_index;
		model.collect_time = this.collect_time;
		for (Map.Entry<String, Long[]> entry : this.ne_info_value_map.entrySet()) {
			Long[] field_value = new Long[2];
			System.arraycopy(entry.getValue(), 0, field_value, 0, 2);
			model.ne_info_value_map.put(entry.getKey(), field_value);
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
		stringBuilder.append(indent).append(S_TB).append("ne_info_code").append(S_DL).append(ne_info_code).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("ne_info_index").append(S_DL).append(ne_info_index).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("collect_time").append(S_DL).append(collect_time).append(S_NL);
		for (String ne_info_field_name : ne_info_value_map.keySet()) {
			Number[] values = ne_info_value_map.get(ne_info_field_name);
			stringBuilder.append(indent).append(S_TB).append(ne_info_field_name).append(S_DL).append(values[0]).append(", ").append(values[1]).append(S_NL);
		}
		stringBuilder.append(indent).append(S_RB);
		return stringBuilder.toString();
	}

	@Override
	public String toDisplayString(String indent) {
		return toString(indent);
	}

}
