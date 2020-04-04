/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.server.workflow.network.ne_info;

import java.util.Date;

import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.server.workflow.WorkerIf;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_onion.share.model.STATISTICS_TYPE;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO_FIELD;
import com.hellonms.platforms.emp_orange.share.model.network.ne_info.Model4NeInfoIf;
import com.hellonms.platforms.emp_orange.share.model.network.ne_info.Model4NeStatisticsIf;
import com.hellonms.platforms.emp_orange.share.model.network.ne_info.Model4NeThresholdIf;
import com.hellonms.platforms.emp_orange.share.model.network.ne_info.NE_INFO_INDEX;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.NE_SESSION_PROTOCOL;

/**
 * <p>
 * NE 정보 Worker
 * </p>
 *
 * @since 1.6
 * @create 2015. 4. 6.
 * @modified 2015. 4. 6.
 * @author cchyun
 *
 */
public interface Worker4NeInfoIf extends WorkerIf {

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
	 * NE 정보 동기화
	 * </p>
	 * 
	 * @param context
	 * @param ne_id
	 * @param ne_info_code
	 * @return
	 * @throws EmpException
	 */
	public void syncListNeInfo(EmpContext context, int ne_id, NE_SESSION_PROTOCOL protocol) throws EmpException;

	/**
	 * <p>
	 * NE 정보 조회
	 * </p>
	 * 
	 * @param context
	 * @param ne_id
	 * @param ne_info_def
	 * @return
	 * @throws EmpException
	 */
	public Model4NeInfoIf[] readListNeInfo(EmpContext context, int ne_id, EMP_MODEL_NE_INFO ne_info_def) throws EmpException;

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
	 * NE 정보 수정
	 * </p>
	 * 
	 * @param context
	 * @param info
	 * @return
	 * @throws EmpException
	 */
	public Model4NeInfoIf updateNeInfo(EmpContext context, Model4NeInfoIf info) throws EmpException;

	/**
	 * <p>
	 * NE 정보 동기화
	 * </p>
	 *
	 * @param context
	 * @param ne_id
	 * @param ne_info_def
	 * @param infos
	 * @return
	 * @throws EmpException
	 */
	public Model4NeInfoIf[] syncListNeInfo(EmpContext context, int ne_id, EMP_MODEL_NE_INFO ne_info_def, Model4NeInfoIf[] infos) throws EmpException;

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
	 * NE 통계 정보 조회
	 * </p>
	 *
	 * @param context
	 * @param ne_id
	 * @param ne_info_def
	 * @param collect_time
	 * @return
	 * @throws EmpException
	 */
	public Model4NeStatisticsIf[] readListNeStatistics(EmpContext context, int ne_id, EMP_MODEL_NE_INFO ne_info_def, Date collect_time) throws EmpException;

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
	public NE_INFO_INDEX[] queryListNeStatisticsIndex(EmpContext context, int ne_id, EMP_MODEL_NE_INFO ne_info_def, STATISTICS_TYPE statistics_type, Date fromTime, Date toTime) throws EmpException;

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
	 * @param info_index
	 * @param statistics_type
	 * @param fromTime
	 * @param toTime
	 * @return
	 * @throws EmpException
	 */
	public Model4NeStatisticsIf[] queryListNeStatistics(EmpContext context, int ne_id, EMP_MODEL_NE_INFO ne_info_def, int info_index, STATISTICS_TYPE statistics_type, Date fromTime, Date toTime) throws EmpException;

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
