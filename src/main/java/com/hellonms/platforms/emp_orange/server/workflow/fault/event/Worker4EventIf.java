/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.server.workflow.fault.event;

import java.util.Date;

import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.server.workflow.WorkerIf;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_onion.share.model.STATISTICS_TYPE;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_EVENT;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO;
import com.hellonms.platforms.emp_orange.share.model.fault.event.GEN_TYPE;
import com.hellonms.platforms.emp_orange.share.model.fault.event.Model4Alarm;
import com.hellonms.platforms.emp_orange.share.model.fault.event.Model4AlarmStatistics;
import com.hellonms.platforms.emp_orange.share.model.fault.event.Model4AlarmStatistics.ITEM;
import com.hellonms.platforms.emp_orange.share.model.fault.event.Model4AlarmSummary;
import com.hellonms.platforms.emp_orange.share.model.fault.event.Model4Event;
import com.hellonms.platforms.emp_orange.share.model.fault.event.SEVERITY;

/**
 * <p>
 * Event Worker
 * </p>
 *
 * @since 1.6
 * @create 2015. 4. 21.
 * @modified 2015. 4. 21.
 * @author cchyun
 *
 */
public interface Worker4EventIf extends WorkerIf {

	/**
	 * <p>
	 * 이벤트 발생
	 * </p>
	 *
	 * @param context
	 * @param event
	 * @throws EmpException
	 */
	public void notifyEvent(EmpContext context, Model4Event event) throws EmpException;

	/**
	 * <p>
	 * 이벤트 발생
	 * </p>
	 *
	 * @param context
	 * @param events
	 * @throws EmpException
	 */
	public void notifyEvents(EmpContext context, Model4Event[] events) throws EmpException;

	/**
	 * <p>
	 * 이벤트 큐 크기
	 * </p>
	 * 
	 * @param context
	 * @return
	 * @throws EmpException
	 */
	public int eventQueueSize(EmpContext context) throws EmpException;

	/**
	 * <p>
	 * 특정 이벤트를 조회한다.
	 * </p>
	 *
	 * @param context
	 * @param event_id
	 * @return
	 * @throws EmpException
	 */
	public Model4Event queryEvent(EmpContext context, long event_id) throws EmpException;

	/**
	 * <p>
	 * NE그룹의 이벤트이력을 조회한다.
	 * </p>
	 *
	 * @param context
	 * @param ne_group_id
	 * @param event_def
	 * @param severity
	 * @param fromTime
	 * @param toTime
	 * @param startNo
	 * @param count
	 * @return
	 * @throws EmpException
	 */
	public Model4Event[] queryListEventByNeGroup(EmpContext context, int ne_group_id, EMP_MODEL_EVENT event_def, SEVERITY severity, Date fromTime, Date toTime, int startNo, int count) throws EmpException;

	/**
	 * <p>
	 * NE의 이벤트이력을 조회한다.
	 * </p>
	 *
	 * @param context
	 * @param ne_id
	 * @param event_def
	 * @param severity
	 * @param fromTime
	 * @param toTime
	 * @param startNo
	 * @param count
	 * @return
	 * @throws EmpException
	 */
	public Model4Event[] queryListEventByNe(EmpContext context, int ne_id, EMP_MODEL_EVENT event_def, SEVERITY severity, Date fromTime, Date toTime, int startNo, int count) throws EmpException;

	/**
	 * <p>
	 * 이벤트이력을 조회한다.
	 * </p>
	 *
	 * @param context
	 * @param startNo
	 * @param count
	 * @return
	 * @throws EmpException
	 */
	public Model4Event[] queryListEventByConsole(EmpContext context, int startNo, int count) throws EmpException;

	/**
	 * <p>
	 * NE그룹의 이벤트이력을 조회한다.
	 * </p>
	 *
	 * @param context
	 * @param ne_group_id
	 * @param event_def
	 * @param severity
	 * @param fromTime
	 * @param toTime
	 * @return
	 * @throws EmpException
	 */
	public int queryCountEventByNeGroup(EmpContext context, int ne_group_id, EMP_MODEL_EVENT event_def, SEVERITY severity, Date fromTime, Date toTime) throws EmpException;

	/**
	 * <p>
	 * NE의 이벤트이력을 조회한다.
	 * </p>
	 *
	 * @param context
	 * @param ne_id
	 * @param event_def
	 * @param severity
	 * @param fromTime
	 * @param toTime
	 * @return
	 * @throws EmpException
	 */
	public int queryCountEventByNe(EmpContext context, int ne_id, EMP_MODEL_EVENT event_def, SEVERITY severity, Date fromTime, Date toTime) throws EmpException;

	/**
	 * <p>
	 * 알람을 조회한다.
	 * </p>
	 * 
	 * @param context
	 * @param gen_first_event_id
	 * @return
	 * @throws EmpException
	 */
	public Model4Alarm queryAlarm(EmpContext context, long gen_first_event_id) throws EmpException;

	/**
	 * <p>
	 * NE그룹의 알람을 조회한다.
	 * </p>
	 *
	 * @param context
	 * @param ne_group_id
	 * @param event_def
	 * @param severity
	 * @param fromTime
	 * @param toTime
	 * @param startNo
	 * @param count
	 * @return
	 * @throws EmpException
	 */
	public Model4Alarm[] queryListAlarmByNeGroup(EmpContext context, int ne_group_id, EMP_MODEL_EVENT event_def, SEVERITY severity, Date fromTime, Date toTime, int startNo, int count) throws EmpException;

	/**
	 * <p>
	 * NE의 알람을 조회한다.
	 * </p>
	 *
	 * @param context
	 * @param ne_id
	 * @param event_def
	 * @param severity
	 * @param fromTime
	 * @param toTime
	 * @param startNo
	 * @param count
	 * @return
	 * @throws EmpException
	 */
	public Model4Alarm[] queryListAlarmByNe(EmpContext context, int ne_id, EMP_MODEL_EVENT event_def, SEVERITY severity, Date fromTime, Date toTime, int startNo, int count) throws EmpException;

	/**
	 * <p>
	 * 알람이력운 조회한다.
	 * </p>
	 *
	 * @param context
	 * @param startNo
	 * @param count
	 * @return
	 * @throws EmpException
	 */
	public Model4Alarm[] queryListAlarmByConsole(EmpContext context, int startNo, int count) throws EmpException;

	/**
	 * <p>
	 * NE그룹의 알람을 조회한다.
	 * </p>
	 *
	 * @param context
	 * @param ne_group_id
	 * @param event_def
	 * @param severity
	 * @param fromTime
	 * @param toTime
	 * @return
	 * @throws EmpException
	 */
	public int queryCountAlarmByNeGroup(EmpContext context, int ne_group_id, EMP_MODEL_EVENT event_def, SEVERITY severity, Date fromTime, Date toTime) throws EmpException;

	/**
	 * <p>
	 * NE의 알람을 조회한다.
	 * </p>
	 *
	 * @param context
	 * @param ne_id
	 * @param event_def
	 * @param severity
	 * @param fromTime
	 * @param toTime
	 * @return
	 * @throws EmpException
	 */
	public int queryCountAlarmByNe(EmpContext context, int ne_id, EMP_MODEL_EVENT event_def, SEVERITY severity, Date fromTime, Date toTime) throws EmpException;

	/**
	 * <p>
	 * 활성화된 알람을 조회한다.
	 * </p>
	 *
	 * @param context
	 * @param gen_first_event_id
	 * @return
	 * @throws EmpException
	 */
	public Model4Alarm queryAlarmActive(EmpContext context, long gen_first_event_id) throws EmpException;

	/**
	 * <p>
	 * NE그룹의 활성화된 알람을 조회한다.
	 * </p>
	 *
	 * @param context
	 * @param ne_group_id
	 * @param event_def
	 * @param severity
	 * @param startNo
	 * @param count
	 * @return
	 * @throws EmpException
	 */
	public Model4Alarm[] queryListAlarmActiveByNeGroup(EmpContext context, int ne_group_id, EMP_MODEL_EVENT event_def, SEVERITY severity, int startNo, int count) throws EmpException;

	/**
	 * <p>
	 * NE의 활성화된 알람을 조회한다.
	 * </p>
	 * 
	 * @param context
	 * @param ne_id
	 * @param ne_info_def
	 * @param event_def
	 * @param severity
	 * @param startNo
	 * @param count
	 * @return
	 * @throws EmpException
	 */
	public Model4Alarm[] queryListAlarmActiveByNe(EmpContext context, int ne_id, EMP_MODEL_NE_INFO ne_info_def, EMP_MODEL_EVENT event_def, SEVERITY severity, int startNo, int count) throws EmpException;

	/**
	 * <p>
	 * NE그룹의 활성화된 알람을 조회한다.
	 * </p>
	 *
	 * @param context
	 * @param ne_group_id
	 * @param event_def
	 * @param severity
	 * @return
	 * @throws EmpException
	 */
	public int queryCountAlarmActiveByNeGroup(EmpContext context, int ne_group_id, EMP_MODEL_EVENT event_def, SEVERITY severity) throws EmpException;

	/**
	 * <p>
	 * NE의활성화된 알람을 조회한다.
	 * </p>
	 *
	 * @param context
	 * @param ne_id
	 * @param event_def
	 * @param severity
	 * @return
	 * @throws EmpException
	 */
	public int queryCountAlarmActiveByNe(EmpContext context, int ne_id, EMP_MODEL_EVENT event_def, SEVERITY severity) throws EmpException;

	/**
	 * <p>
	 * 알람을 인지한다.
	 * </p>
	 * 
	 * @param context
	 * @param gen_first_event_id
	 * @return
	 * @throws EmpException
	 */
	public Model4Alarm ackAlarm(EmpContext context, long gen_first_event_id) throws EmpException;

	/**
	 * <p>
	 * NE INFO에 특정 정보에 속한 모든 Alarm을 해제한다.
	 * </p>
	 *
	 * @param context
	 * @param ne_id
	 * @param ne_info_code
	 * @param ne_info_index
	 * @param clear_type
	 * @param clear_description
	 * @return
	 * @throws EmpException
	 */
	public Model4Alarm[] clearListAlarmByNe_info_index(EmpContext context, int ne_id, int ne_info_code, int ne_info_index, GEN_TYPE clear_type, String clear_description) throws EmpException;

	/**
	 * <p>
	 * 현재 Alarm을 해제한다.
	 * </p>
	 *
	 * @param context
	 * @param gen_first_event_id
	 * @param clear_type
	 * @param clear_description
	 * @param isCreateEvent
	 * @return
	 * @throws EmpException
	 */
	public Model4Alarm clearAlarmByGen_first_event_id(EmpContext context, long gen_first_event_id, GEN_TYPE clear_type, String clear_description, boolean isCreateEvent) throws EmpException;

	/**
	 * <p>
	 * 대표알람 조회
	 * </p>
	 *
	 * @param context
	 * @return
	 * @throws EmpException
	 */
	public Model4AlarmSummary[] queryListAlarmSummary(EmpContext context) throws EmpException;

	/**
	 * <p>
	 * 알람 통계를 생성 한다.
	 * </p>
	 *
	 * @param context
	 * @param from_type
	 * @param to_type
	 * @param collect_time
	 * @return
	 * @throws EmpException
	 */
	public int syncListAlarmStatistics(EmpContext context, STATISTICS_TYPE from_type, STATISTICS_TYPE to_type, Date collect_time) throws EmpException;

	/**
	 * <p>
	 * 통계 조회
	 * </p>
	 *
	 * @param context
	 * @param fromTime
	 * @param toTime
	 * @param ne_group_id
	 * @param item
	 * @param statistics_type
	 * @return
	 * @throws EmpException
	 */
	public Model4AlarmStatistics[] queryListAlarmStatisticsByNeGroup(EmpContext context, int ne_group_id, ITEM item, STATISTICS_TYPE statistics_type, Date fromTime, Date toTime) throws EmpException;

	/**
	 * <p>
	 * 통계 조회
	 * </p>
	 *
	 * @param context
	 * @param fromTime
	 * @param toTime
	 * @param ne_id
	 * @param item
	 * @param statistics_type
	 * @return
	 * @throws EmpException
	 */
	public Model4AlarmStatistics[] queryListAlarmStatisticsByNe(EmpContext context, int ne_id, ITEM item, STATISTICS_TYPE statistics_type, Date fromTime, Date toTime) throws EmpException;

	/**
	 * <p>
	 * Fault 수정 seq 조회
	 * </p>
	 *
	 * @param context
	 * @return
	 * @throws EmpException
	 */
	public long queryCurrUpdate_seq_fault(EmpContext context) throws EmpException;

	/**
	 * <p>
	 * 이벤트(알람) 파티션 준비
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
