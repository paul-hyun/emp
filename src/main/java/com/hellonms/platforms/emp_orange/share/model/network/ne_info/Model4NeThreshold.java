/**
 * Copyright 2016 Hello NMS. All rights reserved.
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
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO_FIELD.THRESHOLD_TYPE;
import com.hellonms.platforms.emp_orange.share.model.fault.event.SEVERITY;
import com.hellonms.platforms.emp_util.string.UtilString;

/**
 * <p>
 * NE Threshold 정의
 * </p>
 *
 * @since 1.6
 * @create 2016. 1. 20.
 * @modified 2016. 1. 20.
 * @author cchyun
 *
 */
@SuppressWarnings("serial")
public class Model4NeThreshold implements Model4NeThresholdIf {

	private int ne_id;

	private int ne_info_code;

	private Map<String, Long[]> ne_info_value_map = new LinkedHashMap<String, Long[]>();

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

	@Override
	public int getNe_id() {
		return ne_id;
	}

	@Override
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

	@Override
	public void setNe_info_code(int ne_info_code) {
		this.ne_info_code = ne_info_code;
	}

	@Override
	public EMP_MODEL_NE_INFO_FIELD[] getField_defs() {
		return getNe_info_def().getNe_thresholds();
	}

	@Override
	public EMP_MODEL_NE_INFO_FIELD getField_def(String ne_info_field_name) {
		EMP_MODEL_NE_INFO_FIELD ne_info_field_def = getNe_info_def().getNe_info_field(ne_info_field_name);
		if (ne_info_field_def == null) {
			throw new RuntimeException(UtilString.format("unknown info_field: '{}'", ne_info_field_name));
		}
		if (!ne_info_field_def.isThr_enable()) {
			throw new RuntimeException(UtilString.format("no threshold info_field: '{}'", ne_info_field_name));
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

	public Long[] initThreshold_values(EMP_MODEL_NE_INFO_FIELD ne_info_field_def) {
		Long[] field_values = ne_info_value_map.get(ne_info_field_def.getName());
		if (field_values == null) {
			field_values = new Long[6];
			ne_info_value_map.put(ne_info_field_def.getName(), field_values);
		}
		return field_values;
	}

	@Override
	public Long getThreshold_critical_min(EMP_MODEL_NE_INFO_FIELD ne_info_field_def) {
		Long[] field_values = ne_info_value_map.get(ne_info_field_def.getName());
		return field_values[0];
	}

	@Override
	public Long getThreshold_critical_min(String ne_info_field_name) {
		return getThreshold_critical_min(getField_def(ne_info_field_name));
	}

	@Override
	public void setThreshold_critical_min(EMP_MODEL_NE_INFO_FIELD ne_info_field_def, Long threshold_critical_min) {
		Long[] field_values = ne_info_value_map.get(ne_info_field_def.getName());
		field_values[0] = threshold_critical_min;
	}

	@Override
	public void setThreshold_critical_min(String ne_info_field_name, Long threshold_critical_min) {
		setThreshold_critical_min(getField_def(ne_info_field_name), threshold_critical_min);
	}

	@Override
	public Long getThreshold_critical_max(EMP_MODEL_NE_INFO_FIELD ne_info_field_def) {
		Long[] field_values = ne_info_value_map.get(ne_info_field_def.getName());
		return field_values[1];
	}

	@Override
	public Long getThreshold_critical_max(String ne_info_field_name) {
		return getThreshold_critical_max(getField_def(ne_info_field_name));
	}

	@Override
	public void setThreshold_critical_max(EMP_MODEL_NE_INFO_FIELD ne_info_field_def, Long threshold_critical_max) {
		Long[] field_values = ne_info_value_map.get(ne_info_field_def.getName());
		field_values[1] = threshold_critical_max;
	}

	@Override
	public void setThreshold_critical_max(String ne_info_field_name, Long threshold_critical_max) {
		setThreshold_critical_max(getField_def(ne_info_field_name), threshold_critical_max);
	}

	@Override
	public Long getThreshold_major_min(EMP_MODEL_NE_INFO_FIELD ne_info_field_def) {
		Long[] field_values = ne_info_value_map.get(ne_info_field_def.getName());
		return field_values[2];
	}

	@Override
	public Long getThreshold_major_min(String ne_info_field_name) {
		return getThreshold_major_min(getField_def(ne_info_field_name));
	}

	@Override
	public void setThreshold_major_min(EMP_MODEL_NE_INFO_FIELD ne_info_field_def, Long threshold_major_min) {
		Long[] field_values = ne_info_value_map.get(ne_info_field_def.getName());
		field_values[2] = threshold_major_min;
	}

	@Override
	public void setThreshold_major_min(String ne_info_field_name, Long threshold_major_min) {
		setThreshold_major_min(getField_def(ne_info_field_name), threshold_major_min);
	}

	@Override
	public Long getThreshold_major_max(EMP_MODEL_NE_INFO_FIELD ne_info_field_def) {
		Long[] field_values = ne_info_value_map.get(ne_info_field_def.getName());
		return field_values[3];
	}

	@Override
	public Long getThreshold_major_max(String ne_info_field_name) {
		return getThreshold_major_max(getField_def(ne_info_field_name));
	}

	@Override
	public void setThreshold_major_max(EMP_MODEL_NE_INFO_FIELD ne_info_field_def, Long threshold_major_min) {
		Long[] field_values = ne_info_value_map.get(ne_info_field_def.getName());
		field_values[3] = threshold_major_min;
	}

	@Override
	public void setThreshold_major_max(String ne_info_field_name, Long threshold_major_max) {
		setThreshold_major_max(getField_def(ne_info_field_name), threshold_major_max);
	}

	@Override
	public Long getThreshold_minor_min(EMP_MODEL_NE_INFO_FIELD ne_info_field_def) {
		Long[] field_values = ne_info_value_map.get(ne_info_field_def.getName());
		return field_values[4];
	}

	@Override
	public Long getThreshold_minor_min(String ne_info_field_name) {
		return getThreshold_minor_min(getField_def(ne_info_field_name));
	}

	@Override
	public void setThreshold_minor_min(EMP_MODEL_NE_INFO_FIELD ne_info_field_def, Long threshold_minor_min) {
		Long[] field_values = ne_info_value_map.get(ne_info_field_def.getName());
		field_values[4] = threshold_minor_min;
	}

	@Override
	public void setThreshold_minor_min(String ne_info_field_name, Long threshold_minor_min) {
		setThreshold_minor_min(getField_def(ne_info_field_name), threshold_minor_min);
	}

	@Override
	public Long getThreshold_minor_max(EMP_MODEL_NE_INFO_FIELD ne_info_field_def) {
		Long[] field_values = ne_info_value_map.get(ne_info_field_def.getName());
		return field_values[5];
	}

	@Override
	public Long getThreshold_minor_max(String ne_info_field_name) {
		return getThreshold_minor_max(getField_def(ne_info_field_name));
	}

	@Override
	public void setThreshold_minor_max(EMP_MODEL_NE_INFO_FIELD ne_info_field_def, Long threshold_minor_min) {
		Long[] field_values = ne_info_value_map.get(ne_info_field_def.getName());
		field_values[5] = threshold_minor_min;
	}

	@Override
	public void setThreshold_minor_max(String ne_info_field_name, Long threshold_minor_max) {
		setThreshold_minor_max(getField_def(ne_info_field_name), threshold_minor_max);
	}

	@Override
	public void setThreshold(EMP_MODEL_NE_INFO_FIELD ne_info_field_def, Model4NeThresholdIf ne_threshold) {
		this.setThreshold_critical_min(ne_info_field_def, ne_threshold.getThreshold_critical_min(ne_info_field_def));
		this.setThreshold_critical_max(ne_info_field_def, ne_threshold.getThreshold_critical_max(ne_info_field_def));
		this.setThreshold_major_min(ne_info_field_def, ne_threshold.getThreshold_major_min(ne_info_field_def));
		this.setThreshold_major_max(ne_info_field_def, ne_threshold.getThreshold_major_max(ne_info_field_def));
		this.setThreshold_minor_min(ne_info_field_def, ne_threshold.getThreshold_minor_min(ne_info_field_def));
		this.setThreshold_minor_max(ne_info_field_def, ne_threshold.getThreshold_minor_max(ne_info_field_def));
	}

	@Override
	public void setThreshold(String ne_info_field_name, Model4NeThresholdIf ne_threshold) {
		setThreshold(getField_def(ne_info_field_name), ne_threshold);
	}

	@Override
	public String getCreator() {
		return creator;
	}

	@Override
	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Override
	public Date getCreate_time() {
		return create_time;
	}

	@Override
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	@Override
	public String getUpdater() {
		return updater;
	}

	@Override
	public void setUpdater(String updater) {
		this.updater = updater;
	}

	@Override
	public Date getUpdate_time() {
		return update_time;
	}

	@Override
	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}

	@Override
	public SEVERITY getSeverity(EMP_MODEL_NE_INFO_FIELD ne_info_field_def, Number ne_info_field_values) {
		THRESHOLD_TYPE thr_type = ne_info_field_def.getThr_type();
		Long[] field_values = ne_info_value_map.get(ne_info_field_def.getName());
		if (thr_type != null && ne_info_field_values != null && field_values != null) {
			switch (thr_type) {
			case GT:
				if (field_values[1] != null && field_values[1].longValue() < ne_info_field_values.longValue()) {
					return SEVERITY.CRITICAL;
				} else if (field_values[3] != null && field_values[3].longValue() < ne_info_field_values.longValue()) {
					return SEVERITY.MAJOR;
				} else if (field_values[5] != null && field_values[5].longValue() < ne_info_field_values.longValue()) {
					return SEVERITY.MINOR;
				} else {
					return SEVERITY.CLEAR;
				}
			case LT:
				if (field_values[0] != null && field_values[0].longValue() > ne_info_field_values.longValue()) {
					return SEVERITY.CRITICAL;
				} else if (field_values[2] != null && field_values[2].longValue() > ne_info_field_values.longValue()) {
					return SEVERITY.MAJOR;
				} else if (field_values[4] != null && field_values[4].longValue() > ne_info_field_values.longValue()) {
					return SEVERITY.MINOR;
				} else {
					return SEVERITY.CLEAR;
				}
			case BT:
				if (field_values[0] != null && field_values[1] != null && (field_values[0].longValue() < ne_info_field_values.longValue() && field_values[1].longValue() > ne_info_field_values.longValue())) {
					return SEVERITY.CRITICAL;
				} else if (field_values[2] != null && field_values[3] != null && (field_values[2].longValue() < ne_info_field_values.longValue() && field_values[3].longValue() > ne_info_field_values.longValue())) {
					return SEVERITY.MAJOR;
				} else if (field_values[4] != null && field_values[5] != null && (field_values[4].longValue() < ne_info_field_values.longValue() && field_values[5].longValue() > ne_info_field_values.longValue())) {
					return SEVERITY.MINOR;
				} else {
					return SEVERITY.CLEAR;
				}
			case OT:
				if (field_values[0] != null && field_values[1] != null && (field_values[0].longValue() > ne_info_field_values.longValue() || field_values[1].longValue() < ne_info_field_values.longValue())) {
					return SEVERITY.CRITICAL;
				} else if (field_values[2] != null && field_values[3] != null && (field_values[2].longValue() > ne_info_field_values.longValue() || field_values[3].longValue() < ne_info_field_values.longValue())) {
					return SEVERITY.MAJOR;
				} else if (field_values[4] != null && field_values[5] != null && (field_values[4].longValue() > ne_info_field_values.longValue() || field_values[5].longValue() < ne_info_field_values.longValue())) {
					return SEVERITY.MINOR;
				} else {
					return SEVERITY.CLEAR;
				}
			}
		}
		return SEVERITY.CLEAR;
	}

	@Override
	public SEVERITY getSeverity(String ne_info_field_name, Number ne_info_field_values) {
		return getSeverity(getField_def(ne_info_field_name), ne_info_field_values);
	}

	@Override
	public String getDescription(EMP_MODEL_NE_INFO_FIELD ne_info_field_def, Number ne_info_field_values) {
		THRESHOLD_TYPE thr_type = ne_info_field_def.getThr_type();
		Long[] field_values = ne_info_value_map.get(ne_info_field_def.getName());
		if (thr_type != null && ne_info_field_values != null && field_values != null) {
			switch (thr_type) {
			case GT:
				if (field_values[1] != null && field_values[1].longValue() < ne_info_field_values.longValue()) {
					return UtilString.format("{}={} > {}", ne_info_field_def.getName(), ne_info_field_values, field_values[1].longValue());
				} else if (field_values[3] != null && field_values[3].longValue() < ne_info_field_values.longValue()) {
					return UtilString.format("{}={} > {}", ne_info_field_def.getName(), ne_info_field_values, field_values[3].longValue());
				} else if (field_values[5] != null && field_values[5].longValue() < ne_info_field_values.longValue()) {
					return UtilString.format("{}={} > {}", ne_info_field_def.getName(), ne_info_field_values, field_values[5].longValue());
				} else {
					if (field_values[5] != null) {
						return UtilString.format("{}={} < {}", ne_info_field_def.getName(), ne_info_field_values, field_values[5]);
					} else if (field_values[3] != null) {
						return UtilString.format("{}={} < {}", ne_info_field_def.getName(), ne_info_field_values, field_values[3]);
					} else if (field_values[1] != null) {
						return UtilString.format("{}={} < {}", ne_info_field_def.getName(), ne_info_field_values, field_values[1]);
					} else {
						return UtilString.format("{}={} Threshold Disabled", ne_info_field_def.getName(), ne_info_field_values);
					}
				}
			case LT:
				if (field_values[0] != null && field_values[0].longValue() > ne_info_field_values.longValue()) {
					return UtilString.format("{}={} < {}", ne_info_field_def.getName(), ne_info_field_values, field_values[0].longValue());
				} else if (field_values[2] != null && field_values[2].longValue() > ne_info_field_values.longValue()) {
					return UtilString.format("{}={} < {}", ne_info_field_def.getName(), ne_info_field_values, field_values[2].longValue());
				} else if (field_values[4] != null && field_values[4].longValue() > ne_info_field_values.longValue()) {
					return UtilString.format("{}={} < {}", ne_info_field_def.getName(), ne_info_field_values, field_values[4].longValue());
				} else {
					if (field_values[4] != null) {
						return UtilString.format("{}={} > {}", ne_info_field_def.getName(), ne_info_field_values, field_values[4]);
					} else if (field_values[3] != null) {
						return UtilString.format("{}={} > {}", ne_info_field_def.getName(), ne_info_field_values, field_values[3]);
					} else if (field_values[0] != null) {
						return UtilString.format("{}={} > {}", ne_info_field_def.getName(), ne_info_field_values, field_values[0]);
					} else {
						return UtilString.format("{}={} Threshold Disabled", ne_info_field_def.getName(), ne_info_field_values);
					}
				}
			case BT:
				if (field_values[0] != null && field_values[1] != null && (field_values[0].longValue() < ne_info_field_values.longValue() && field_values[1].longValue() > ne_info_field_values.longValue())) {
					return UtilString.format("{} < {}={} < {}", field_values[0].longValue(), ne_info_field_def.getName(), ne_info_field_values, field_values[1].longValue());
				} else if (field_values[2] != null && field_values[3] != null && (field_values[2].longValue() < ne_info_field_values.longValue() && field_values[3].longValue() > ne_info_field_values.longValue())) {
					return UtilString.format("{} < {}={} < {}", field_values[2].longValue(), ne_info_field_def.getName(), ne_info_field_values, field_values[3].longValue());
				} else if (field_values[4] != null && field_values[5] != null && (field_values[4].longValue() < ne_info_field_values.longValue() && field_values[5].longValue() > ne_info_field_values.longValue())) {
					return UtilString.format("{} < {}={} < {}", field_values[4].longValue(), ne_info_field_def.getName(), ne_info_field_values, field_values[5].longValue());
				} else {
					if (field_values[0] != null && field_values[1] != null && (field_values[0].longValue() > ne_info_field_values.longValue())) {
						return UtilString.format("{}={} < {}", ne_info_field_def.getName(), ne_info_field_values, field_values[0].longValue());
					} else if (field_values[0] != null && field_values[1] != null && (field_values[1].longValue() < ne_info_field_values.longValue())) {
						return UtilString.format("{}={} > {}", ne_info_field_def.getName(), ne_info_field_values, field_values[1].longValue());
					} else if (field_values[2] != null && field_values[3] != null && (field_values[2].longValue() > ne_info_field_values.longValue())) {
						return UtilString.format("{}={} < {}", ne_info_field_def.getName(), ne_info_field_values, field_values[2].longValue());
					} else if (field_values[2] != null && field_values[3] != null && (field_values[3].longValue() < ne_info_field_values.longValue())) {
						return UtilString.format("{}={} > {}", ne_info_field_def.getName(), ne_info_field_values, field_values[3].longValue());
					} else if (field_values[4] != null && field_values[5] != null && (field_values[4].longValue() > ne_info_field_values.longValue())) {
						return UtilString.format("{}={} < {}", ne_info_field_def.getName(), ne_info_field_values, field_values[4].longValue());
					} else if (field_values[4] != null && field_values[5] != null && (field_values[5].longValue() < ne_info_field_values.longValue())) {
						return UtilString.format("{}={} > {}", ne_info_field_def.getName(), ne_info_field_values, field_values[5].longValue());
					} else {
						return UtilString.format("{}={} Threshold Disabled", ne_info_field_def.getName(), ne_info_field_values);
					}
				}
			case OT:
				if (field_values[0] != null && field_values[1] != null && (field_values[0].longValue() > ne_info_field_values.longValue())) {
					return UtilString.format("{}={} < {}", ne_info_field_def.getName(), ne_info_field_values, field_values[0].longValue());
				} else if (field_values[0] != null && field_values[1] != null && (field_values[1].longValue() < ne_info_field_values.longValue())) {
					return UtilString.format("{}={} > {}", ne_info_field_def.getName(), ne_info_field_values, field_values[1].longValue());
				} else if (field_values[2] != null && field_values[3] != null && (field_values[2].longValue() > ne_info_field_values.longValue())) {
					return UtilString.format("{}={} < {}", ne_info_field_def.getName(), ne_info_field_values, field_values[2].longValue());
				} else if (field_values[2] != null && field_values[3] != null && (field_values[3].longValue() < ne_info_field_values.longValue())) {
					return UtilString.format("{}={} > {}", ne_info_field_def.getName(), ne_info_field_values, field_values[3].longValue());
				} else if (field_values[4] != null && field_values[5] != null && (field_values[4].longValue() > ne_info_field_values.longValue())) {
					return UtilString.format("{}={} < {}", ne_info_field_def.getName(), ne_info_field_values, field_values[4].longValue());
				} else if (field_values[4] != null && field_values[5] != null && (field_values[5].longValue() < ne_info_field_values.longValue())) {
					return UtilString.format("{}={} > {}", ne_info_field_def.getName(), ne_info_field_values, field_values[5].longValue());
				} else {
					if (field_values[4] != null && field_values[5] != null) {
						return UtilString.format("{} < {}={} < {}", field_values[4].longValue(), ne_info_field_def.getName(), ne_info_field_values, field_values[5].longValue());
					} else if (field_values[2] != null && field_values[3] != null) {
						return UtilString.format("{} < {}={} < {}", field_values[2].longValue(), ne_info_field_def.getName(), ne_info_field_values, field_values[3].longValue());
					} else if (field_values[0] != null && field_values[1] != null) {
						return UtilString.format("{} < {}={} < {}", field_values[0].longValue(), ne_info_field_def.getName(), ne_info_field_values, field_values[1].longValue());
					} else {
						return UtilString.format("{}={} Threshold Disabled", ne_info_field_def.getName(), ne_info_field_values);
					}
				}
			default:
				return UtilString.format("threshold_type={}, {}={}", thr_type, ne_info_field_def.getName(), ne_info_field_values);
			}
		}
		return UtilString.format("threshold_type={}, {}={}", thr_type, ne_info_field_def.getName(), ne_info_field_values);
	}

	@Override
	public String getDescription(String ne_info_field_name, Number ne_info_field_values) {
		return getDescription(getField_def(ne_info_field_name), ne_info_field_values);
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
			Long[] field_value = { null, null, null, null, null, null };
			if (value instanceof List<?> && ((List<?>) value).size() == 6) {
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
	public Model4NeThreshold copy() {
		Model4NeThreshold model = new Model4NeThreshold();
		model.ne_id = this.ne_id;
		model.ne_info_code = this.ne_info_code;
		for (Entry<String, Long[]> entry : ne_info_value_map.entrySet()) {
			Long[] values = entry.getValue();
			model.ne_info_value_map.put(entry.getKey(), new Long[] { values[0], values[1], values[2], values[3], values[4], values[5] });
		}
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
		stringBuilder.append(indent).append(S_TB).append("ne_id").append(S_DL).append(ne_id).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("ne_info_code").append(S_DL).append(ne_info_code).append(S_NL);
		for (Entry<String, Long[]> entry : ne_info_value_map.entrySet()) {
			Long[] values = entry.getValue();
			stringBuilder.append(indent).append(S_TB).append(entry.getKey()).append(S_DL).append(values[0]).append(", ").append(values[1]).append(", ").append(values[2]).append(", ").append(values[3]).append(", ").append(values[4]).append(", ").append(values[5]).append(S_NL);
		}
		stringBuilder.append(indent).append(S_RB);
		return stringBuilder.toString();
	}

	@Override
	public String toDisplayString(String indent) {
		return toString(indent);
	}

}
