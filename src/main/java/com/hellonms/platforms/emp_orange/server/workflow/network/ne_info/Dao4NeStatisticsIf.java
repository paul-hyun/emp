/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.server.workflow.network.ne_info;

import java.util.Date;

import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.server.workflow.DaoIf;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_onion.share.model.STATISTICS_TYPE;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO;
import com.hellonms.platforms.emp_orange.share.model.network.ne_info.Model4NeStatisticsIf;

/**
 * <p>
 * NE 통계 Dao
 * </p>
 *
 * @since 1.6
 * @create 2015. 4. 6.
 * @modified 2015. 4. 6.
 * @author cchyun
 *
 */
public interface Dao4NeStatisticsIf extends DaoIf {

	/**
	 * <p>
	 * 통계 데이터 생성
	 * </p>
	 *
	 * @param context
	 * @param ne_info_def
	 * @param statistics_type
	 * @param ne_statisticss
	 * @return
	 * @throws EmpException
	 */
	public Model4NeStatisticsIf[] createListNeStatistics(EmpContext context, EMP_MODEL_NE_INFO ne_info_def, STATISTICS_TYPE statistics_type, Model4NeStatisticsIf[] ne_statisticss) throws EmpException;

	/**
	 * <p>
	 * 통계 데이터 생성
	 * </p>
	 *
	 * @param context
	 * @param ne_info_def
	 * @param from_type
	 * @param to_type
	 * @param collect_time
	 * @return
	 * @throws EmpException
	 */
	public int syncListNeStatistics(EmpContext context, EMP_MODEL_NE_INFO ne_info_def, STATISTICS_TYPE from_type, STATISTICS_TYPE to_type, Date collect_time) throws EmpException;

	/**
	 * <p>
	 * 통계 키 조회
	 * </p>
	 *
	 * @param context
	 * @param ne_id
	 * @param ne_info_def
	 * @param statistics_type
	 * @param fromTime
	 * @param toTime
	 * @return
	 * @throws EmpException
	 */
	public int[] queryListNeStatisticsIndex(EmpContext context, int ne_id, EMP_MODEL_NE_INFO ne_info_def, STATISTICS_TYPE statistics_type, Date fromTime, Date toTime) throws EmpException;

	/**
	 * <p>
	 * 통계 조회
	 * </p>
	 *
	 * @param context
	 * @param ne_id
	 * @param ne_info_def
	 * @param statistics_type
	 * @param fromTime
	 * @param toTime
	 * @return
	 * @throws EmpException
	 */
	public Model4NeStatisticsIf[] queryListNeStatistics(EmpContext context, int ne_id, EMP_MODEL_NE_INFO ne_info_def, STATISTICS_TYPE statistics_type, Date fromTime, Date toTime) throws EmpException;

	/**
	 * <p>
	 * 통계 조회
	 * </p>
	 *
	 * @param context
	 * @param ne_id
	 * @param ne_info_def
	 * @param ne_info_index
	 * @param statistics_type
	 * @param fromTime
	 * @param toTime
	 * @return
	 * @throws EmpException
	 */
	public Model4NeStatisticsIf[] queryListNeStatistics(EmpContext context, int ne_id, EMP_MODEL_NE_INFO ne_info_def, int ne_info_index, STATISTICS_TYPE statistics_type, Date fromTime, Date toTime) throws EmpException;

	/**
	 * <p>
	 * 특정시간대에 저장된 통계 조회
	 * </p>
	 *
	 * @param context
	 * @param ne_id
	 * @param ne_info_def
	 * @param statistics_type
	 * @param collect_time
	 * @return
	 * @throws EmpException
	 */
	public Model4NeStatisticsIf[] queryListNeStatistics(EmpContext context, int ne_id, EMP_MODEL_NE_INFO ne_info_def, STATISTICS_TYPE statistics_type, Date collect_time) throws EmpException;

	/**
	 * <p>
	 * 통계 파티션 준비
	 * </p>
	 *
	 * @param context
	 * @param collect_time
	 * @return
	 * @throws EmpException
	 */
	public String[][] prepareListPartition(EmpContext context, Date collect_time) throws EmpException;

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
