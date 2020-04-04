/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.server.workflow.fault.event;

import java.util.Date;

import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.server.workflow.DaoIf;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_orange.share.model.fault.event.GEN_TYPE;
import com.hellonms.platforms.emp_orange.share.model.fault.event.Model4Alarm;
import com.hellonms.platforms.emp_orange.share.model.fault.event.Model4AlarmSummary;
import com.hellonms.platforms.emp_orange.share.model.fault.event.SEVERITY;

/**
 * <p>
 * Event Dao
 * </p>
 *
 * @since 1.6
 * @create 2015. 4. 21.
 * @modified 2015. 4. 21.
 * @author cchyun
 *
 */
public interface Dao4AlarmIf extends DaoIf {

	/**
	 * <p>
	 * 알람 생성
	 * </p>
	 *
	 * @param context
	 * @param alarm_active
	 * @return
	 * @throws EmpException
	 */
	public Model4Alarm createAlarm(EmpContext context, Model4Alarm alarm_active) throws EmpException;

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
	 * @param event_code
	 * @param severity
	 * @param fromTime
	 * @param toTime
	 * @param startNo
	 * @param count
	 * @return
	 * @throws EmpException
	 */
	public Model4Alarm[] queryListAlarmByNeGroup(EmpContext context, int ne_group_id, int event_code, SEVERITY severity, Date fromTime, Date toTime, int startNo, int count) throws EmpException;

	/**
	 * <p>
	 * NE의 알람을 조회한다.
	 * </p>
	 *
	 * @param context
	 * @param ne_id
	 * @param event_code
	 * @param severity
	 * @param fromTime
	 * @param toTime
	 * @param startNo
	 * @param count
	 * @return
	 * @throws EmpException
	 */
	public Model4Alarm[] queryListAlarmByNe(EmpContext context, int ne_id, int event_code, SEVERITY severity, Date fromTime, Date toTime, int startNo, int count) throws EmpException;

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
	 * @param event_code
	 * @param severity
	 * @param fromTime
	 * @param toTime
	 * @return
	 * @throws EmpException
	 */
	public int queryCountAlarmByNeGroup(EmpContext context, int ne_group_id, int event_code, SEVERITY severity, Date fromTime, Date toTime) throws EmpException;

	/**
	 * <p>
	 * NE의 알람을 조회한다.
	 * </p>
	 *
	 * @param context
	 * @param ne_id
	 * @param event_code
	 * @param severity
	 * @param fromTime
	 * @param toTime
	 * @return
	 * @throws EmpException
	 */
	public int queryCountAlarmByNe(EmpContext context, int ne_id, int event_code, SEVERITY severity, Date fromTime, Date toTime) throws EmpException;

	/**
	 * <p>
	 * 활성화 된 알람 조회
	 * </p>
	 *
	 * @param context
	 * @param gen_first_event_id
	 * @return
	 */
	public Model4Alarm queryAlarmActive(EmpContext context, long gen_first_event_id) throws EmpException;

	/**
	 * <p>
	 * 활성화 된 알람 조회
	 * </p>
	 *
	 * @param context
	 * @param ne_id
	 * @param ne_info_code
	 * @param ne_info_index
	 * @param event_code
	 * @return
	 * @throws EmpException
	 */
	public Model4Alarm queryAlarmActive(EmpContext context, int ne_id, int ne_info_code, int ne_info_index, int event_code) throws EmpException;

	/**
	 * <p>
	 * NE그룹의 활성화된 알람을 조회한다.
	 * </p>
	 *
	 * @param context
	 * @param ne_group_id
	 * @param event_code
	 * @param severity
	 * @param startNo
	 * @param count
	 * @return
	 * @throws EmpException
	 */
	public Model4Alarm[] queryListAlarmActiveByNeGroup(EmpContext context, int ne_group_id, int event_code, SEVERITY severity, int startNo, int count) throws EmpException;

	/**
	 * <p>
	 * NE의 활성화된 알람을 조회한다.
	 * </p>
	 *
	 * @param context
	 * @param ne_id
	 * @param ne_info_code
	 * @param event_code
	 * @param severity
	 * @param startNo
	 * @param count
	 * @return
	 * @throws EmpException
	 */
	public Model4Alarm[] queryListAlarmActiveByNe(EmpContext context, int ne_id, int ne_info_code, int event_code, SEVERITY severity, int startNo, int count) throws EmpException;

	/**
	 * <p>
	 * NE그룹의 활성화된 알람을 조회한다.
	 * </p>
	 *
	 * @param context
	 * @param ne_group_id
	 * @param event_code
	 * @param severity
	 * @return
	 * @throws EmpException
	 */
	public int queryCountAlarmActiveByNeGroup(EmpContext context, int ne_group_id, int event_code, SEVERITY severity) throws EmpException;

	/**
	 * <p>
	 * NE의활성화된 알람을 조회한다.
	 * </p>
	 *
	 * @param context
	 * @param ne_id
	 * @param event_code
	 * @param severity
	 * @return
	 * @throws EmpException
	 */
	public int queryCountAlarmActiveByNe(EmpContext context, int ne_id, int event_code, SEVERITY severity) throws EmpException;

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
	 * Ack
	 * </p>
	 *
	 * @param context
	 * @param gen_first_event_id
	 * @return
	 */
	public Model4Alarm updateAlarmByAck(EmpContext context, long gen_first_event_id) throws EmpException;

	/**
	 * <p>
	 * Clear ne_info
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
	public Model4Alarm[] updateAlarmByClearNe_info_index(EmpContext context, int ne_id, int ne_info_code, int ne_info_index, GEN_TYPE clear_type, String clear_description) throws EmpException;

	/**
	 * <p>
	 * 알람 복구
	 * </p>
	 *
	 * @param context
	 * @param alarm_active
	 * @param event
	 * @return
	 * @throws EmpException
	 */
	public Model4Alarm updateAlarmByClear(EmpContext context, Model4Alarm alarm_active) throws EmpException;

	/**
	 * <p>
	 * 알람 중복 발생
	 * </p>
	 *
	 * @param context
	 * @param alarm_active
	 * @param event
	 * @return
	 * @throws EmpException
	 */
	public Model4Alarm updateAlarmByRepetition(EmpContext context, Model4Alarm alarm_active) throws EmpException;

	/**
	 * <p>
	 * 알람 등급 변경
	 * </p>
	 *
	 * @param context
	 * @param alarm_clear
	 * @param alarm_active
	 * @return
	 * @throws EmpException
	 */
	public Model4Alarm updateAlarmBySeverity(EmpContext context, Model4Alarm alarm_clear, Model4Alarm alarm_active) throws EmpException;

	/**
	 * <p>
	 * 알람 파티션 준비
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
