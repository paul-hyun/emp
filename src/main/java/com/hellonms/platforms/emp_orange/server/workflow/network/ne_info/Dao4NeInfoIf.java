/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.server.workflow.network.ne_info;

import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.server.workflow.DaoIf;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO;
import com.hellonms.platforms.emp_orange.share.model.network.ne_info.Model4NeInfoIf;
import com.hellonms.platforms.emp_orange.share.model.network.ne_info.NE_INFO_INDEX;

/**
 * <p>
 * NE 정보 Dao
 * </p>
 *
 * @since 1.6
 * @create 2015. 4. 6.
 * @modified 2015. 4. 6.
 * @author cchyun
 *
 */
public interface Dao4NeInfoIf extends DaoIf {

	/**
	 * <p>
	 * NE 정보 조회
	 * </p>
	 * 
	 * @param context
	 * @param ne_id
	 * @param ne_info_def
	 * @param ne_info_index
	 * @return
	 * @throws EmpException
	 */
	public Model4NeInfoIf queryNeInfo(EmpContext context, int ne_id, EMP_MODEL_NE_INFO ne_info_def, int ne_info_index) throws EmpException;

	/**
	 * @param context
	 * @param ne_info_def
	 * @return
	 * @throws EmpException
	 */
	public Model4NeInfoIf[] queryAllNeInfo(EmpContext context, EMP_MODEL_NE_INFO ne_info_def) throws EmpException;

	/**
	 * <p>
	 * NE 정보 목록조회
	 * </p>
	 *
	 * @param context
	 * @param ne_id
	 * @param ne_info_def
	 * @param startNo
	 * @param count
	 * @return
	 * @throws EmpException
	 */
	public Model4NeInfoIf[] queryListNeInfo(EmpContext context, int ne_id, EMP_MODEL_NE_INFO ne_info_def) throws EmpException;

	/**
	 * <p>
	 * NE 정보를 아래 값으로 대체한다
	 * </p>
	 *
	 * @param context
	 * @param ne_id
	 * @param ne_info_def
	 * @param ne_info
	 * @return
	 * @throws EmpException
	 */
	public Model4NeInfoIf syncNeInfo(EmpContext context, int ne_id, EMP_MODEL_NE_INFO ne_info_def, Model4NeInfoIf ne_info) throws EmpException;

	/**
	 * <p>
	 * NE 정보를 아래 값으로 대체한다
	 * </p>
	 *
	 * @param context
	 * @param ne_id
	 * @param ne_info_def
	 * @param ne_infos
	 * @return
	 * @throws EmpException
	 */
	public Model4NeInfoIf[] syncListNeInfo(EmpContext context, int ne_id, EMP_MODEL_NE_INFO ne_info_def, Model4NeInfoIf[] ne_infos) throws EmpException;

	/**
	 * <p>
	 * 인텍스 정보 조회
	 * </p>
	 *
	 * @param context
	 * @param ne_field_index_values
	 * @return
	 * @throws EmpException
	 */
	public NE_INFO_INDEX queryNeInfoIndex(EmpContext context, String... ne_info_index_values) throws EmpException;

	/**
	 * <p>
	 * 인텍스 정보 조회
	 * </p>
	 *
	 * @param context
	 * @param ne_info_index
	 * @return
	 * @throws EmpException
	 */
	public NE_INFO_INDEX queryNeInfoIndex(EmpContext context, int ne_info_index) throws EmpException;

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
