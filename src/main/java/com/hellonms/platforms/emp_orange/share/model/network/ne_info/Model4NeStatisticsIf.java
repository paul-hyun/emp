/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.share.model.network.ne_info;

import java.util.Date;
import java.util.Map;

import com.hellonms.platforms.emp_core.share.model.ModelIf;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO_FIELD;

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
public interface Model4NeStatisticsIf extends ModelIf {

	public int getNe_id();

	public EMP_MODEL_NE_INFO getNe_info_def();

	public int getNe_info_code();

	public int getNe_info_index();

	public String getLocation_display();

	public Date getCollect_time();

	public int getRop_num();

	public EMP_MODEL_NE_INFO_FIELD[] getField_defs();

	public EMP_MODEL_NE_INFO_FIELD getField_def(String ne_info_field_name);

	public boolean hasField_code(EMP_MODEL_NE_INFO_FIELD ne_info_field_def);

	public boolean hasField_code(String ne_info_field_name);

	public Long getField_value(EMP_MODEL_NE_INFO_FIELD ne_info_field_def);

	public Long getField_value(String ne_info_field_name);

	public Long getField_counter(EMP_MODEL_NE_INFO_FIELD ne_info_field_def);

	public Long getField_counter(String ne_info_field_name);

	public Map<String, Object> getMap();

	public void setMap(Map<String, Object> map);

}
