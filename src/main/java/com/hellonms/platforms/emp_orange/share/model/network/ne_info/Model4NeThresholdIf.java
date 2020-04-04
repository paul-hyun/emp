/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.share.model.network.ne_info;

import java.util.Date;
import java.util.Map;

import com.hellonms.platforms.emp_core.share.model.ModelIf;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO_FIELD;
import com.hellonms.platforms.emp_orange.share.model.fault.event.SEVERITY;

/**
 * <p>
 * NE 정보 중 통계로 저장되는 정보
 * </p>
 *
 * @since 1.6
 * @create 2015. 3. 26.
 * @modified 2015. 3. 26.
 * @author cchyun
 *
 */
public interface Model4NeThresholdIf extends ModelIf {

	public int getNe_id();

	public void setNe_id(int ne_id);

	public EMP_MODEL_NE_INFO getNe_info_def();

	public int getNe_info_code();

	public void setNe_info_code(int ne_info_code);

	public EMP_MODEL_NE_INFO_FIELD[] getField_defs();

	public EMP_MODEL_NE_INFO_FIELD getField_def(String ne_info_field_name);

	public boolean hasField_code(EMP_MODEL_NE_INFO_FIELD ne_info_field_def);

	public boolean hasField_code(String ne_info_field_name);

	public Long getThreshold_critical_min(EMP_MODEL_NE_INFO_FIELD ne_info_field_def);

	public Long getThreshold_critical_min(String ne_info_field_name);

	public void setThreshold_critical_min(EMP_MODEL_NE_INFO_FIELD ne_info_field_def, Long threshold_critical_min);

	public void setThreshold_critical_min(String ne_info_field_name, Long threshold_critical_min);

	public Long getThreshold_critical_max(EMP_MODEL_NE_INFO_FIELD ne_info_field_def);

	public Long getThreshold_critical_max(String ne_info_field_name);

	public void setThreshold_critical_max(EMP_MODEL_NE_INFO_FIELD ne_info_field_def, Long threshold_critical_max);

	public void setThreshold_critical_max(String ne_info_field_name, Long threshold_critical_max);

	public Long getThreshold_major_min(EMP_MODEL_NE_INFO_FIELD ne_info_field_def);

	public Long getThreshold_major_min(String ne_info_field_name);

	public void setThreshold_major_min(EMP_MODEL_NE_INFO_FIELD ne_info_field_def, Long threshold_major_min);

	public void setThreshold_major_min(String ne_info_field_name, Long threshold_major_min);

	public Long getThreshold_major_max(EMP_MODEL_NE_INFO_FIELD ne_info_field_def);

	public Long getThreshold_major_max(String ne_info_field_name);

	public void setThreshold_major_max(EMP_MODEL_NE_INFO_FIELD ne_info_field_def, Long threshold_major_max);

	public void setThreshold_major_max(String ne_info_field_name, Long threshold_major_max);

	public Long getThreshold_minor_min(EMP_MODEL_NE_INFO_FIELD ne_info_field_def);

	public Long getThreshold_minor_min(String ne_info_field_name);

	public void setThreshold_minor_min(EMP_MODEL_NE_INFO_FIELD ne_info_field_def, Long threshold_minor_min);

	public void setThreshold_minor_min(String ne_info_field_name, Long threshold_minor_min);

	public Long getThreshold_minor_max(EMP_MODEL_NE_INFO_FIELD ne_info_field_def);

	public Long getThreshold_minor_max(String ne_info_field_name);

	public void setThreshold_minor_max(EMP_MODEL_NE_INFO_FIELD ne_info_field_def, Long threshold_minor_max);

	public void setThreshold_minor_max(String ne_info_field_name, Long threshold_minor_max);

	public void setThreshold(EMP_MODEL_NE_INFO_FIELD ne_info_field_def, Model4NeThresholdIf ne_threshold);

	public void setThreshold(String ne_info_field_name, Model4NeThresholdIf ne_threshold);

	public String getCreator();

	public void setCreator(String creator);

	public Date getCreate_time();

	public void setCreate_time(Date create_time);

	public String getUpdater();

	public void setUpdater(String updater);

	public Date getUpdate_time();

	public void setUpdate_time(Date update_time);

	public SEVERITY getSeverity(EMP_MODEL_NE_INFO_FIELD ne_info_field_def, Number ne_info_field_value);

	public SEVERITY getSeverity(String ne_info_field_name, Number ne_info_field_value);

	public String getDescription(EMP_MODEL_NE_INFO_FIELD ne_info_field_def, Number ne_info_field_value);

	public String getDescription(String ne_info_field_name, Number ne_info_field_value);

	public Map<String, Object> getMap();

	public void setMap(Map<String, Object> map);

}
