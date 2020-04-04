/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.server.workflow.fault.event;

import java.util.Date;

import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.server.workflow.DaoIf;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_EVENT;
import com.hellonms.platforms.emp_orange.share.model.fault.event.Model4Event;
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
public interface Dao4EventIf extends DaoIf {

	/**
	 * <p>
	 * 이벤트 생성
	 * </p>
	 *
	 * @param context
	 * @param event
	 * @return
	 * @throws EmpException
	 */
	public Model4Event createEvent(EmpContext context, Model4Event event) throws EmpException;

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
	 * Fault 정보 수정 플래그 조회
	 * </p>
	 *
	 * @param context
	 * @return
	 * @throws EmpException
	 */
	public long queryCurrUpdate_seq_fault(EmpContext context) throws EmpException;

	/**
	 * <p>
	 * Fault 정보 수정됨을 지정
	 * </p>
	 *
	 * @param context
	 * @return
	 * @throws EmpException
	 */
	public long queryNextUpdate_seq_fault(EmpContext context) throws EmpException;

	/**
	 * <p>
	 * 이벤트 파티션 준비
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
