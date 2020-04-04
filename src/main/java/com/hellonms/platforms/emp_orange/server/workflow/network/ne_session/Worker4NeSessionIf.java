/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.server.workflow.network.ne_session;

import java.util.Date;

import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.server.workflow.WorkerIf;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.Model4NeSessionDiscoveryFilterIf;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.Model4NeSessionDiscoveryResultIf;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.Model4NeSessionIf;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.Model4NeSessionNotificationIf;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.Model4NeSessionRequestIf;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.Model4NeSessionResponseIf;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.NE_SESSION_PROTOCOL;

/**
 * <p>
 * NE 통신 채널 Worker
 * </p>
 * 
 * @since 1.6
 * @create 2015. 3. 24.
 * @modified 2015. 3. 24.
 * @author cchyun
 * 
 */
public interface Worker4NeSessionIf extends WorkerIf {

	public interface Worker4NeSessionListenerIf {

		public void handleNotification(EmpContext context, Model4NeSessionIf ne_session, Model4NeSessionNotificationIf notification);

	}

	/**
	 * <p>
	 * 통신 프로토콜 조회
	 * </p>
	 *
	 * @return
	 */
	public NE_SESSION_PROTOCOL getProtocol();

	/**
	 * <p>
	 * NE 통신채널 기본값 생성
	 * </p>
	 *
	 * @param context
	 * @return
	 */
	public Model4NeSessionIf newInstanceNeSession(EmpContext context) throws EmpException;

	/**
	 * <p>
	 * NE 통신채널 생성
	 * </p>
	 *
	 * @param context
	 * @param ne_session
	 * @return
	 * @throws EmpException
	 */
	public Model4NeSessionIf createNeSession(EmpContext context, Model4NeSessionIf ne_session) throws EmpException;

	/**
	 * <p>
	 * NE 통신채널 조회
	 * </p>
	 *
	 * @param context
	 * @param ne_id
	 * @return
	 * @throws EmpException
	 */
	public Model4NeSessionIf queryNeSession(EmpContext context, int ne_id) throws EmpException;

	/**
	 * <p>
	 * NE 통신채널 조회
	 * </p>
	 *
	 * @param context
	 * @param addr
	 * @return
	 * @throws EmpException
	 */
	public Model4NeSessionIf queryNeSessionByAddress(EmpContext context, String address) throws EmpException;

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
	 * NE 통신채널 수정
	 * </p>
	 *
	 * @param context
	 * @param ne_session
	 * @return
	 * @throws EmpException
	 */
	public Model4NeSessionIf updateNeSession(EmpContext context, Model4NeSessionIf ne_session) throws EmpException;

	/**
	 * <p>
	 * NE 통신채널 삭제
	 * </p>
	 *
	 * @param context
	 * @param ne_id
	 * @return
	 * @throws EmpException
	 */
	public Model4NeSessionIf deleteNeSession(EmpContext context, int ne_id) throws EmpException;

	/**
	 * <p>
	 * NE 통신채널 상태 점검
	 * </p>
	 *
	 * @param context
	 * @param ne_id
	 * @param collect_time
	 * @return
	 * @throws EmpException
	 */
	public Model4NeSessionIf testNeSession(EmpContext context, int ne_id, Date collect_time) throws EmpException;

	/**
	 * <p>
	 * NE에 통신채널을 통한 명령 수행
	 * </p>
	 *
	 * @param context
	 * @param ne_id
	 * @param requests
	 * @return
	 * @throws EmpException
	 */
	public Model4NeSessionResponseIf[] executeNeSession(EmpContext context, int ne_id, Model4NeSessionRequestIf[] requests) throws EmpException;

	/**
	 * <p>
	 * NE를 검색할 검색 정보의 기본 값을 조회한다.
	 * </p>
	 *
	 * @param context
	 * @return
	 * @throws EmpException
	 */
	public Model4NeSessionDiscoveryFilterIf newInstanceDiscoveryFilter(EmpContext context) throws EmpException;

	/**
	 * <p>
	 * NE 통신채널을 검색한다.
	 * </p>
	 *
	 * @param context
	 * @param ne_session_discovery_filter
	 * @return
	 * @throws EmpException
	 */
	public Model4NeSessionDiscoveryResultIf[] discoveryListNeSession(EmpContext context, Model4NeSessionDiscoveryFilterIf ne_session_discovery_filter) throws EmpException;

	public void addListener(Worker4NeSessionListenerIf listener);

	public void removeListener(Worker4NeSessionListenerIf listener);

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
