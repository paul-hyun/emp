/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.server.workflow.network.ne;

import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.server.workflow.DaoIf;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE;
import com.hellonms.platforms.emp_orange.share.model.network.ne.Model4Ne;

/**
 * <p>
 * NE Dao
 * </p>
 * 
 * @since 1.6
 * @create 2015. 3. 12.
 * @modified 2015. 3. 12.
 * @author cchyun
 * 
 */
public interface Dao4NeIf extends DaoIf {

	/**
	 * <p>
	 * NE 생성
	 * </p>
	 * 
	 * @param context
	 * @return
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
