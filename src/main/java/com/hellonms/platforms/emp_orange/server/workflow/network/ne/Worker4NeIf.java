/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.server.workflow.network.ne;

import java.util.Date;

import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.server.workflow.WorkerIf;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_orange.server.workflow.network.ne_session.Worker4NeSessionIf;
import com.hellonms.platforms.emp_orange.server.workflow.network.ne_session.Worker4NeSessionIf.Worker4NeSessionListenerIf;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE;
import com.hellonms.platforms.emp_orange.share.model.network.ne.Model4Ne;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.Model4NeSessionDiscoveryFilterIf;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.Model4NeSessionIf;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.Model4NeSessionRequestIf;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.Model4NeSessionResponseIf;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.NE_SESSION_PROTOCOL;

/**
 * <p>
 * NE Worker
 * </p>
 * 
 * @since 1.6
 * @create 2015. 3. 12.
 * @modified 2015. 3. 12.
 * @author cchyun
 * 
 */
public interface Worker4NeIf extends WorkerIf {

	/**
	 * <p>
	 * 사용가능한 모든 통신채널을 조회한다.
	 * </p>
	 * 
	 * @return
	 */
	public Worker4NeSessionIf[] getListWorker4NeSession();

	/**
	 * <p>
	 * 특정 프로토톨을 지원하는 통신채널을 조회한다.
	 * </p>
	 * 
	 * @param protocol
	 * @return
	 * @throws EmpException
	 */
	public Worker4NeSessionIf getWorker4NeSession(NE_SESSION_PROTOCOL protocol) throws EmpException;

	/**
	 * <p>
	 * NE 기본값을 조회한다.
	 * </p>
	 *
	 * @param context
	 * @return
	 * @throws EmpException
	 */
	public Model4Ne newInstanceNe(EmpContext context) throws EmpException;

	/**
	 * <p>
	 * NE 생성
	 * </p>
	 * 
	 * @param context
	 * @param ne
	 * @return
	 * @throws EmpException
	 */
	public Model4Ne createNe(EmpContext context, Model4Ne ne) throws EmpException;

	/**
	 * <p>
	 * NE 조회
	 * </p>
	 * 
	 * @param context
	 * @param ne_id
	 * @return
	 * @throws EmpException
	 */
	public Model4Ne queryNe(EmpContext context, int ne_id) throws EmpException;

	/**
	 * <p>
	 * NE 조회
	 * </p>
	 * 
	 * @param context
	 * @param ne_group_id
	 * @param ne_name
	 * @return
	 * @throws EmpException
	 */
	public Model4Ne queryNe(EmpContext context, int ne_group_id, String ne_name) throws EmpException;

	/**
	 * <p>
	 * NE 목록조회
	 * </p>
	 * 
	 * @param context
	 * @param startNo
	 * @param count
	 * @return
	 * @throws EmpException
	 */
	public Model4Ne[] queryListNe(EmpContext context, int startNo, int count) throws EmpException;

	/**
	 * <p>
	 * NE 목록조회
	 * </p>
	 * 
	 * @param context
	 * @param ne_group_id
	 * @param startNo
	 * @param count
	 * @return
	 * @throws EmpException
	 */
	public Model4Ne[] queryListNe(EmpContext context, int ne_group_id, int startNo, int count) throws EmpException;

	/**
	 * <p>
	 * NE 목록조회
	 * </p>
	 *
	 * @param context
	 * @param ne_def
	 * @param startNo
	 * @param count
	 * @return
	 */
	public Model4Ne[] queryListNe(EmpContext context, EMP_MODEL_NE ne_def, int startNo, int count) throws EmpException;

	/**
	 * <p>
	 * NE 목록조회
	 * </p>
	 *
	 * @param context
	 * @return
	 * @throws EmpException
	 */
	public Model4Ne[] queryAllNe(EmpContext context) throws EmpException;

	/**
	 * <p>
	 * NE 목록조회
	 * </p>
	 *
	 * @param context
	 * @param ne_group_id
	 * @return
	 * @throws EmpException
	 */
	public Model4Ne[] queryAllNe(EmpContext context, int ne_group_id) throws EmpException;

	/**
	 * <p>
	 * NE를 검색 개본값을 조회한다.
	 * </p>
	 *
	 * @param context
	 * @return
	 * @throws EmpException
	 */
	public Model4NeSessionDiscoveryFilterIf[] newInstanceListDiscoveryFilter(EmpContext context) throws EmpException;

	/**
	 * <p>
	 * NE 검색
	 * </p>
	 *
	 * @param context
	 * @param ne_session_discovery_requests
	 * @return
	 * @throws EmpException
	 */
	public Model4Ne[] discoveryListNe(EmpContext context, Model4NeSessionDiscoveryFilterIf[] ne_session_discovery_filters) throws EmpException;

	/**
	 * <p>
	 * NE 개수조회
	 * </p>
	 * 
	 * @param context
	 * @return
	 * @throws EmpException
	 */
	public int queryCountNe(EmpContext context) throws EmpException;

	/**
	 * <p>
	 * NE 개수조회
	 * </p>
	 * 
	 * @param context
	 * @param ne_group_id
	 * @return
	 * @throws EmpException
	 */
	public int queryCountNe(EmpContext context, int ne_group_id) throws EmpException;

	/**
	 * <p>
	 * NE 개수조회
	 * </p>
	 *
	 * @param context
	 * @param ne_def
	 * @return
	 * @throws EmpException
	 */
	public int queryCountNe(EmpContext context, EMP_MODEL_NE ne_def) throws EmpException;

	/**
	 * <p>
	 * NE 수정
	 * </p>
	 * 
	 * @param context
	 * @param ne
	 * @return
	 * @throws EmpException
	 */
	public Model4Ne updateNe(EmpContext context, Model4Ne ne) throws EmpException;

	/**
	 * <p>
	 * NE 좌표 저장
	 * </p>
	 *
	 * @param context
	 * @param ne
	 * @return
	 * @throws EmpException
	 */
	public Model4Ne updateNeMapLocation(EmpContext context, Model4Ne ne) throws EmpException;

	/**
	 * <p>
	 * NE 삭제
	 * </p>
	 * 
	 * @param context
	 * @param ne_id
	 * @return
	 * @throws EmpException
	 */
	public Model4Ne deleteNe(EmpContext context, int ne_id) throws EmpException;

	/**
	 * <p>
	 * NE 통신상패 확인을 해야할 목록 조회
	 * </p>
	 *
	 * @param context
	 * @param second_of_day
	 * @return
	 * @throws EmpException
	 */
	public Model4NeSessionIf[] queryListNeSessionBySchedule(EmpContext context, int second_of_day) throws EmpException;

	/**
	 * <p>
	 * NE 통신상태 점검
	 * </p>
	 *
	 * @param context
	 * @param ne_id
	 * @param protocol
	 * @param collect_time
	 * @return
	 * @throws EmpException
	 */
	public Model4NeSessionIf testNeSession(EmpContext context, int ne_id, NE_SESSION_PROTOCOL protocol, Date collect_time) throws EmpException;

	/**
	 * <p>
	 * NE에 명령어 수행
	 * </p>
	 *
	 * @param context
	 * @param ne_id
	 * @param requests
	 * @return
	 * @throws EmpException
	 */
	public Model4NeSessionResponseIf[] executeNe(EmpContext context, int ne_id, Model4NeSessionRequestIf[] requests) throws EmpException;

	public void addListener(Worker4NeSessionListenerIf listener);

	public void removeListener(Worker4NeSessionListenerIf listener);

	/**
	 * <p>
	 * Network 정보 수정 플래그 조회
	 * </p>
	 *
	 * @param context
	 * @return
	 * @throws EmpException
	 */
	public long queryCurrUpdate_seq_network(EmpContext context) throws EmpException;

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
