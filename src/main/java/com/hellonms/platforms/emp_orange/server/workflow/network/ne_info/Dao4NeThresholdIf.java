/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.server.workflow.network.ne_info;

import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.server.workflow.DaoIf;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO_FIELD;
import com.hellonms.platforms.emp_orange.share.model.network.ne_info.Model4NeThresholdIf;

/**
 * <p>
 * NE 임계치 Dao
 * </p>
 *
 * @since 1.6
 * @create 2016. 1. 20.
 * @modified 2016. 1. 20.
 * @author cchyun
 *
 */
public interface Dao4NeThresholdIf extends DaoIf {

	/**
	 * <p>
	 * 임계치 조회
	 * </p>
	 *
	 * @param context
	 * @param ne_id
	 * @param ne_info_def
	 * @return
	 * @throws EmpException
	 */
	public Model4NeThresholdIf queryNeThreshold(EmpContext context, int ne_id, EMP_MODEL_NE_INFO ne_info_def) throws EmpException;

	/**
	 * <p>
	 * 임계치 생성
	 * </p>
	 *
	 * @param context
	 * @param ne_threshold
	 * @param ne_info_field_def
	 * @return
	 * @throws EmpException
	 */
	public Model4NeThresholdIf updateNeThreshold(EmpContext context, Model4NeThresholdIf ne_threshold, EMP_MODEL_NE_INFO_FIELD ne_info_field_def) throws EmpException;

	/**
	 * <p>
	 * 임계치 복사
	 * </p>
	 *
	 * @param context
	 * @param ne_id_source
	 * @param ne_info_def
	 * @param ne_id_targets
	 * @return
	 * @throws EmpException
	 */
	public Model4NeThresholdIf[] copyListNeThreshold(EmpContext context, int ne_id_source, EMP_MODEL_NE_INFO ne_info_def, int[] ne_id_targets) throws EmpException;

	/**
	 * <p>
	 * 관련 테이블 초기화
	 * </p>
	 * 
	 * @param context
	 * @throws EmpException
	 */
	public void truncate(EmpContext context) throws EmpException;

}
