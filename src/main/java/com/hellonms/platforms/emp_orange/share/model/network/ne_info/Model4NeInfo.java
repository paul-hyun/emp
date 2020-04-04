/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.share.model.network.ne_info;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_ENUM_FIELD;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO_FIELD;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_TYPE;
import com.hellonms.platforms.emp_util.network.UtilInetAddress;
import com.hellonms.platforms.emp_util.string.UtilString;

/**
 * <p>
 * NE 정보
 * </p>
 *
 * @since 1.6
 * @create 2015. 4. 8.
 * @modified 2015. 4. 8.
 * @author cchyun
 *
 */
@SuppressWarnings("serial")
public class Model4NeInfo implements Model4NeInfoIf {

	private int ne_id;

	private int ne_info_code;

	private int info_index;

	private Date collect_time;

	private Map<String, Serializable> ne_info_value_map = new LinkedHashMap<String, Serializable>();

	public Model4NeInfo() {
	}

	public Model4NeInfo(Model4NeInfo info) {
		this.ne_id = info.ne_id;
		this.ne_info_code = info.ne_info_code;
		this.info_index = info.info_index;
		this.collect_time = info.collect_time;
		this.ne_info_value_map.putAll(info.ne_info_value_map);
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
		return info_index;
	}

	@Override
	public void setNe_info_index(int info_index) {
		this.info_index = info_index;
	}

	@Override
	public String[] getNe_info_index_values() {
		List<String> index_values = new ArrayList<String>();
		int length = 0;
		for (EMP_MODEL_NE_INFO_FIELD ne_info_field_def : getNe_info_def().getNe_info_indexs()) {
			length = Math.max(length, ne_info_field_def.getNe_session_oid().getIndex_length());
			Serializable value = getField_value(ne_info_field_def);
			index_values.add(value == null ? "" : String.valueOf(value));
		}
		return index_values.toArray(new String[0]);
	}

	@Override
	public String[] getNe_statistics_index_values() {
		List<String> index_values = new ArrayList<String>();
		for (EMP_MODEL_NE_INFO_FIELD ne_info_field_def : getNe_info_def().getNe_info_fields()) {
			if (ne_info_field_def.isIndex() || ne_info_field_def.isStat_label()) {
				Serializable value = getField_value(ne_info_field_def);
				index_values.add(value == null ? "" : String.valueOf(value));
			}
		}
		return index_values.toArray(new String[0]);
	}

	@Override
	public String getNe_statistics_location_display() {
		StringBuilder location_display = new StringBuilder();
		for (EMP_MODEL_NE_INFO_FIELD ne_info_field_def : getNe_info_def().getNe_info_fields()) {
			if (ne_info_field_def.isStat_label()) {
				Serializable value = getField_value(ne_info_field_def);
				if (value != null) {
					location_display.append(location_display.length() == 0 ? "" : ".").append(String.valueOf(value));
				}
			}
		}
		return location_display.toString();
	}

	@Override
	public EMP_MODEL_NE_INFO_FIELD[] getField_defs() {
		return getNe_info_def().getNe_info_fields();
	}

	@Override
	public EMP_MODEL_NE_INFO_FIELD getField_def(String ne_info_field_name) {
		EMP_MODEL_NE_INFO_FIELD ne_info_field_def = getNe_info_def().getNe_info_field(ne_info_field_name);
		if (ne_info_field_def == null) {
			throw new RuntimeException(UtilString.format("unknown info_field: '{}'", ne_info_field_name));
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
	public Serializable getField_value(EMP_MODEL_NE_INFO_FIELD ne_info_field_def) {
		return ne_info_value_map.get(ne_info_field_def.getName());
	}

	@Override
	public Serializable getField_value(String ne_info_field_name) {
		return getField_value(getField_def(ne_info_field_name));
	}

	@Override
	public EMP_MODEL_ENUM_FIELD getField_value_enum(EMP_MODEL_NE_INFO_FIELD ne_info_field_def) {
		if (ne_info_field_def.getEnum_code() != 0) {
			Serializable field_value = ne_info_value_map.get(ne_info_field_def.getName());
			return EMP_MODEL.current().getEnum_field(ne_info_field_def.getEnum_code(), String.valueOf(field_value));
		} else {
			throw new RuntimeException(UtilString.format("not enum: '{}'", ne_info_field_def.getName()));
		}
	}

	@Override
	public EMP_MODEL_ENUM_FIELD getField_value_enum(String ne_info_field_name) {
		return getField_value_enum(getField_def(ne_info_field_name));
	}

	@Override
	public String getField_value_display(EMP_MODEL_NE_INFO_FIELD ne_info_field_def) {
		Serializable field_value = getField_value(ne_info_field_def);
		return ne_info_field_def.getType_local().toDisplay(field_value);
	}

	@Override
	public String getField_value_display(String ne_info_field_name) {
		return getField_value_display(getField_def(ne_info_field_name));
	}

	@Override
	public boolean equalField_value(String ne_info_field_name, Serializable value) {
		Serializable field_value = ne_info_value_map.get(ne_info_field_name);
		return (field_value != null && field_value.equals(value));
	}

	@Override
	public void setField_value(EMP_MODEL_NE_INFO_FIELD ne_info_field_def, Serializable value) {
		if (value == null || ne_info_field_def.getType_local().premitive.isInstance(value)) {
			ne_info_value_map.put(ne_info_field_def.getName(), value);
		} else {
			throw new RuntimeException(UtilString.format("{} type {} != {}", ne_info_field_def.getName(), ne_info_field_def.getType_local(), value.getClass()));
		}
	}

	@Override
	public void setField_value(String ne_info_field_name, Serializable value) {
		setField_value(getField_def(ne_info_field_name), value);
	}

	@Override
	public Date getCollect_time() {
		return collect_time;
	}

	@Override
	public void setCollect_time(Date collect_time) {
		this.collect_time = collect_time;
	}

	@Override
	public void replaceNull(Model4NeInfoIf ne_info) {
		for (Map.Entry<String, Serializable> entry : ne_info_value_map.entrySet()) {
			Serializable value = entry.getValue();
			if (value == null) {
				entry.setValue(ne_info.getField_value(entry.getKey()));
			}
		}
	}

	@Override
	public Model4NeInfo copy() {
		Model4NeInfo model = new Model4NeInfo();
		model.ne_id = this.ne_id;
		model.ne_info_code = this.ne_info_code;
		model.info_index = this.info_index;
		model.collect_time = this.collect_time;
		model.ne_info_value_map.putAll(this.ne_info_value_map);
		return model;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Model4NeInfo) {
			Model4NeInfo model = (Model4NeInfo) obj;
			if (ne_id != model.ne_id) {
				return false;
			}
			if (ne_info_code != model.ne_info_code) {
				return false;
			}
			if (ne_info_value_map.size() != model.ne_info_value_map.size()) {
				return false;
			}
			for (String ne_field_name : ne_info_value_map.keySet()) {
				Serializable value1 = ne_info_value_map.get(ne_field_name);
				Serializable value2 = model.ne_info_value_map.get(ne_field_name);
				if (value1 == null || value2 == null || !value1.equals(value2)) {
					return false;
				}
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Map<String, Object> getMap() {
		LinkedHashMap<String, Object> model = new LinkedHashMap<String, Object>();
		for (EMP_MODEL_NE_INFO_FIELD aaa : getNe_info_def().getNe_info_fields()) {
			if (aaa.isRead()) {
				if (aaa.getType_local() == EMP_MODEL_TYPE.ARRAY_BYTE) {
					byte[] value1 = (byte[]) getField_value(aaa);
					List<Integer> value2 = null;
					if (value1 != null) {
						value2 = new ArrayList<Integer>();
						for (byte b : value1) {
							value2.add(0x000000FF & b);
						}
					}
					model.put(aaa.getName(), value2);
				} else {
					model.put(aaa.getName(), getField_value(aaa));
				}
			}
		}
		return model;
	}

	@Override
	public void setMap(Map<String, Object> map) {
		for (Entry<String, Object> entry : map.entrySet()) {
			EMP_MODEL_NE_INFO_FIELD ne_info_field_def = getNe_info_def().getNe_info_field(entry.getKey());
			if (ne_info_field_def == null || !ne_info_field_def.isRead()) {
				continue;
			}

			Serializable value = (Serializable) entry.getValue();
			if (value == null || ne_info_field_def.getType_local().premitive.isInstance(value)) {
				ne_info_value_map.put(ne_info_field_def.getName(), value);
			} else if (value instanceof String && UtilString.isInt((String) value) && ne_info_field_def.getType_local() == EMP_MODEL_TYPE.INT_32) {
				ne_info_value_map.put(ne_info_field_def.getName(), new Integer((String) value));
			} else if (value instanceof Integer && ne_info_field_def.getType_local() == EMP_MODEL_TYPE.INT_64) {
				ne_info_value_map.put(ne_info_field_def.getName(), new Long((Integer) value));
			} else if (value instanceof Long && ne_info_field_def.getType_local() == EMP_MODEL_TYPE.DATE) {
				ne_info_value_map.put(ne_info_field_def.getName(), new Date((Long) value));
			} else if (value instanceof List<?> && ne_info_field_def.getType_local() == EMP_MODEL_TYPE.ARRAY_BYTE) {
				@SuppressWarnings("unchecked")
				List<Integer> value1 = (List<Integer>) value;
				byte[] value2 = new byte[value1.size()];
				for (int i = 0; i < value2.length; i++) {
					value2[i] = value1.get(i).byteValue();
				}
				ne_info_value_map.put(ne_info_field_def.getName(), value2);
			} else if (value instanceof String && ne_info_field_def.getType_local() == EMP_MODEL_TYPE.IP_V4) {
				try {
					ne_info_value_map.put(ne_info_field_def.getName(), UtilInetAddress.getInetAddresses((String) value));
				} catch (EmpException e) {
					throw new RuntimeException(UtilString.format("{} -> {} : {}", value, ne_info_field_def.getType_local(), e.getCause()));
				}
			} else {
				throw new RuntimeException(UtilString.format("{} type {} != {}", ne_info_field_def.getName(), ne_info_field_def.getType_local(), value.getClass()));
			}
		}
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
		stringBuilder.append(indent).append(S_TB).append("info_index").append(S_DL).append(info_index).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("collect_time").append(S_DL).append(collect_time).append(S_NL);
		for (String ne_info_field_name : ne_info_value_map.keySet()) {
			stringBuilder.append(indent).append(S_TB).append(ne_info_field_name).append(S_DL).append(ne_info_value_map.get(ne_info_field_name)).append(S_NL);
		}
		stringBuilder.append(indent).append(S_RB);
		return stringBuilder.toString();
	}

	@Override
	public String toDisplayString(String indent) {
		return toString(indent);
	}

	@Override
	public Map<String, Object> toJson() {
		Map<String, Object> json = new LinkedHashMap<String, Object>();

		json.put("ne_id", ne_id);
		json.put("ne_info_code", ne_info_code);
		json.put("ne_info_name", getNe_info_def().getName());
		json.put("info_index", info_index);
		json.put("collect_time", collect_time);
		json.putAll(getMap());

		return json;
	}

}
