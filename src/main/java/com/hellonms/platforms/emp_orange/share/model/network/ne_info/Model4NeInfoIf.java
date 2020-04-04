/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.share.model.network.ne_info;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import com.hellonms.platforms.emp_core.share.model.ModelIf;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_ENUM_FIELD;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO_FIELD;

/**
 * <p>
 * NE 정보
 * </p>
 *
 * @since 1.6
 * @create 2015. 3. 26.
 * @modified 2015. 3. 26.
 * @author cchyun
 *
 */
public interface Model4NeInfoIf extends ModelIf {

	public int getNe_id();

	public void setNe_id(int ne_id);

	public EMP_MODEL_NE_INFO getNe_info_def();

	public int getNe_info_code();

	public int getNe_info_index();

	public void setNe_info_index(int info_index);

	public String[] getNe_info_index_values();

	public String[] getNe_statistics_index_values();

	public String getNe_statistics_location_display();

	public EMP_MODEL_NE_INFO_FIELD[] getField_defs();

	public EMP_MODEL_NE_INFO_FIELD getField_def(String ne_info_field_name);

	public boolean hasField_code(EMP_MODEL_NE_INFO_FIELD ne_info_field_def);

	public boolean hasField_code(String ne_info_field_name);

	public Serializable getField_value(EMP_MODEL_NE_INFO_FIELD ne_info_field_def);

	public Serializable getField_value(String ne_info_field_name);

	public EMP_MODEL_ENUM_FIELD getField_value_enum(EMP_MODEL_NE_INFO_FIELD ne_info_field_def);

	public EMP_MODEL_ENUM_FIELD getField_value_enum(String ne_info_field_name);

	public String getField_value_display(EMP_MODEL_NE_INFO_FIELD ne_info_field_def);

	public String getField_value_display(String ne_info_field_name);

	public boolean equalField_value(String ne_info_field_name, Serializable value);

	public void setField_value(EMP_MODEL_NE_INFO_FIELD ne_info_field_def, Serializable value);

	public void setField_value(String ne_info_field_name, Serializable value);

	public Date getCollect_time();

	public void setCollect_time(Date collect_time);

	public void replaceNull(Model4NeInfoIf ne_info);

	public Map<String, Object> getMap();

	public void setMap(Map<String, Object> map);

	public Map<String, Object> toJson();

}
