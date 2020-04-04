/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.server.workflow.network.ne_info;

import java.util.Date;

import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.server.workflow.TriggerIf;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO;
import com.hellonms.platforms.emp_orange.share.model.fault.event.Model4Event;
import com.hellonms.platforms.emp_orange.share.model.network.ne_info.Model4NeInfoIf;
import com.hellonms.platforms.emp_orange.share.model.network.ne_info.Model4NeStatisticsIf;
import com.hellonms.platforms.emp_orange.share.model.network.ne_info.Model4NeThresholdIf;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.Model4NeSessionNotificationIf;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.Model4NeSessionRequestIf;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.Model4NeSessionResponseIf;

/**
 * <p>
 * NE정보 정보 Trigger
 * </p>
 *
 * @since 1.6
 * @create 2015. 4. 6.
 * @modified 2015. 4. 6.
 * @author cchyun
 *
 */
public interface Trigger4NeInfoIf extends TriggerIf {

	/**
	 * <p>
	 * Trigger가 지원하는 NE정보_INFO_CODE 조회
	 * </p>
	 *
	 * @return (null일 경우는 범용으로 사용 됨)
	 */
	public EMP_MODEL_NE_INFO getNe_info_code();

	/**
	 * <p>
	 * NE정보 조회 전에 수행할 명령어
	 * </p>
	 *
	 * @param context
	 * @param ne_id
	 * @param ne_info_def
	 * @param ne_infos
	 * @return
	 * @throws EmpException
	 */
	public Model4NeSessionRequestIf[] preReadListNeInfo(EmpContext context, int ne_id, EMP_MODEL_NE_INFO ne_info_def) throws EmpException;

	/**
	 * <p>
	 * 명령어 수행후 분석결과 처리
	 * </p>
	 *
	 * @param context
	 * @param ne_id
	 * @param ne_info_def
	 * @param ne_infos
	 * @param responses
	 * @return
	 * @throws EmpException
	 */
	public Model4NeInfoIf[] postReadListNeInfo(EmpContext context, int ne_id, EMP_MODEL_NE_INFO ne_info_def, Model4NeSessionResponseIf[] responses) throws EmpException;

	/**
	 * <p>
	 * NE 정보에서 이벤트 추출
	 * </p>
	 *
	 * @param context
	 * @param ne_id
	 * @param ne_info_def
	 * @param ne_infos
	 * @param responses
	 * @return
	 * @throws EmpException
	 */
	public Model4Event[] eventListNeInfo(EmpContext context, int ne_id, EMP_MODEL_NE_INFO ne_info_def, Model4NeInfoIf[] ne_infos) throws EmpException;

	/**
	 * <p>
	 * 명령어 수행후 분석결과 처리
	 * </p>
	 *
	 * @param context
	 * @param ne_id
	 * @param ne_info_def
	 * @param last_ne_statisticss
	 * @param collect_time
	 * @param responses
	 * @return
	 * @throws EmpException
	 */
	public Model4NeStatisticsIf[] postReadListNeStatistics(EmpContext context, int ne_id, EMP_MODEL_NE_INFO ne_info_def, Model4NeStatisticsIf[] last_ne_statisticss, Date collect_time, Model4NeInfoIf[] ne_infos) throws EmpException;

	/**
	 * <p>
	 * NE 통계 정보에서 이벤트 추출
	 * </p>
	 *
	 * @param context
	 * @param ne_id
	 * @param ne_info_def
	 * @param ne_statisticss
	 * @return
	 * @throws EmpException
	 */
	public Model4Event[] eventListNeStatistics(EmpContext context, int ne_id, EMP_MODEL_NE_INFO ne_info_def, Model4NeStatisticsIf[] ne_statisticss, Model4NeThresholdIf ne_threshold) throws EmpException;

	/**
	 * <p>
	 * NE정보 수정 전에 수행할 명령어
	 * </p>
	 *
	 * @param context
	 * @param ne_info
	 * @return
	 * @throws EmpException
	 */
	public Model4NeSessionRequestIf[] preUpdateNeInfo(EmpContext context, Model4NeInfoIf ne_info) throws EmpException;

	/**
	 * <p>
	 * 명령어 수행후 분석결과 처리
	 * </p>
	 *
	 * @param context
	 * @param ne_info
	 * @return
	 * @throws EmpException
	 */
	public Model4NeInfoIf postUpdateNeInfo(EmpContext context, Model4NeInfoIf ne_info, Model4NeSessionResponseIf[] responses) throws EmpException;

	/**
	 * <p>
	 * Notifcation을 NeInfo로 변환한다.
	 * </p>
	 *
	 * @param context
	 * @param ne_id
	 * @param ne_info_def
	 * @param notification
	 * @return
	 */
	public Model4NeInfoIf postNeNotification(EmpContext context, int ne_id, EMP_MODEL_NE_INFO ne_info_def, Model4NeSessionNotificationIf notification);

	/**
	 * <p>
	 * Notification을 처리 한다.
	 * </p>
	 *
	 * @param context
	 * @param ne_id
	 * @param ne_info_def
	 * @param notification
	 * @param notification
	 * @return
	 * @throws EmpException
	 */
	public Model4Event[] eventListNeNotification(EmpContext context, int ne_id, EMP_MODEL_NE_INFO ne_info_def, Model4NeInfoIf ne_info, Model4NeSessionNotificationIf notification) throws EmpException;

}
