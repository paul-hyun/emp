/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.server.workflow.network.ne_session.icmp;

import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.server.workflow.DaoIf;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.icmp.Model4NeSessionICMP;

/**
 * <p>
 * ICMP 통신채널 Dao
 * </p>
 * 
 * @since 1.6
 * @create 2015. 3. 24.
 * @modified 2015. 3. 24.
 * @author cchyun
 * 
 */
public interface Dao4NeSessionICMPIf extends DaoIf {

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
	public Model4NeSessionICMP createNeSession(EmpContext context, Model4NeSessionICMP ne_session) throws EmpException;

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
	public Model4NeSessionICMP queryNeSession(EmpContext context, int ne_id) throws EmpException;

	/**
	 * <p>
	 * 주소로 NE 통신채널 조회
	 * </p>
	 *
	 * @param context
	 * @param address
	 * @return
	 * @throws EmpException
	 */
	public Model4NeSessionICMP queryNeSessionByAddress(EmpContext context, String address) throws EmpException;

	/**
	 * <p>
	 * NE 통신채널 조회
	 * </p>
	 *
	 * @param context
	 * @param startNo
	 * @param count
	 * @return
	 * @throws EmpException
	 */
	public Model4NeSessionICMP[] queryListNeSession(EmpContext context, int startNo, int count) throws EmpException;

	/**
	 * <p>
	 * NE 통신채널 조회
	 * </p>
	 *
	 * @param context
	 * @param ne_group_id
	 * @param startNo
	 * @param count
	 * @return
	 * @throws EmpException
	 */
	public Model4NeSessionICMP[] queryListNeSession(EmpContext context, int ne_group_id, int startNo, int count) throws EmpException;

	/**
	 * <p>
	 * NE 통신채널 조회 (주기적 작업을 위한)
	 * </p>
	 *
	 * @param context
	 * @param second_of_day
	 * @return
	 * @throws EmpException
	 */
	public Model4NeSessionICMP[] queryListNeSessionBySchedule(EmpContext context, int second_of_day) throws EmpException;

	/**
	 * <p>
	 * NE 통신채널 개수 조회
	 * </p>
	 *
	 * @param context
	 * @return
	 * @throws EmpException
	 */
	public int queryCountNeSession(EmpContext context) throws EmpException;

	/**
	 * <p>
	 * NE 통신채널 개수 조회
	 * </p>
	 *
	 * @param context
	 * @param ne_group_id
	 * @return
	 * @throws EmpException
	 */
	public int queryCountNeSession(EmpContext context, int ne_group_id) throws EmpException;

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
	public Model4NeSessionICMP updateNeSession(EmpContext context, Model4NeSessionICMP ne_session) throws EmpException;

	/**
	 * <p>
	 * NE 통신채널 수정
	 * </p>
	 *
	 * @param context
	 * @param ne_session
	 * @throws EmpException
	 */
	public Model4NeSessionICMP updateNeSessionState(EmpContext context, Model4NeSessionICMP ne_session) throws EmpException;

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
	public Model4NeSessionICMP deleteNeSession(EmpContext context, int ne_id) throws EmpException;

	/**
	 * <p>
	 * Network 정보 수정 플래그 증가
	 * </p>
	 *
	 * @param context
	 * @return
	 * @throws EmpException
	 */
	public long queryNextUpdate_seq_network(EmpContext context) throws EmpException;

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
