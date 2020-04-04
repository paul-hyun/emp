/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.server.workflow.network.ne_group;

import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.server.workflow.WorkerIf;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_orange.share.model.network.ne_group.Model4NeGroup;
import com.hellonms.platforms.emp_orange.share.model.network.ne_group.TreeNode4NeGroup;

/**
 * <p>
 * NeGroup Worker
 * </p>
 * 
 * @since 1.6
 * @create 2015. 3. 19.
 * @modified 2015. 3. 19.
 * @author cchyun
 * 
 */
public interface Worker4NeGroupIf extends WorkerIf {

	/**
	 * <p>
	 * NE그룹 생성
	 * </p>
	 * 
	 * @param context
	 * @param ne_group
	 * @return
	 * @throws EmpException
	 */
	public Model4NeGroup createNeGroup(EmpContext context, Model4NeGroup ne_group) throws EmpException;

	/**
	 * <p>
	 * NE그룹 조회
	 * </p>
	 * 
	 * @param context
	 * @param ne_group_id
	 * @return
	 * @throws EmpException
	 */
	public Model4NeGroup queryNeGroup(EmpContext context, int ne_group_id) throws EmpException;

	/**
	 * <p>
	 * NE그룹 목록조회
	 * </p>
	 * 
	 * @param context
	 * @param startNo
	 * @param count
	 * @return
	 * @throws EmpException
	 */
	public Model4NeGroup[] queryListNeGroup(EmpContext context, int startNo, int count) throws EmpException;

	/**
	 * <p>
	 * NE그룹 목록조회
	 * </p>
	 * 
	 * @param context
	 * @param ne_group_id
	 * @param startNo
	 * @param count
	 * @return
	 * @throws EmpException
	 */
	public Model4NeGroup[] queryListNeGroup(EmpContext context, int ne_group_id, int startNo, int count) throws EmpException;

	/**
	 * <p>
	 * 권한에 관계 없이 모든 NE그룹 조회
	 * </p>
	 *
	 * @param context
	 * @return
	 * @throws EmpException
	 */
	public Model4NeGroup[] queryAllNeGroup(EmpContext context) throws EmpException;

	/**
	 * <p>
	 * NE그룹 Tree 형태로 조회
	 * </p>
	 * 
	 * @param context
	 * @return
	 * @throws EmpException
	 */
	public TreeNode4NeGroup queryTreeNeGroup(EmpContext context) throws EmpException;

	/**
	 * <p>
	 * NE그룹 개수조회
	 * </p>
	 * 
	 * @param context
	 * @return
	 * @throws EmpException
	 */
	public int queryCountNeGroup(EmpContext context) throws EmpException;

	/**
	 * <p>
	 * NE그룹 개수조회
	 * </p>
	 * 
	 * @param context
	 * @param ne_group_id
	 * @return
	 * @throws EmpException
	 */
	public int queryCountNeGroup(EmpContext context, int ne_group_id) throws EmpException;

	/**
	 * <p>
	 * NE그룹 수정
	 * </p>
	 * 
	 * @param context
	 * @param ne_group
	 * @return
	 * @throws EmpException
	 */
	public Model4NeGroup updateNeGroup(EmpContext context, Model4NeGroup ne_group) throws EmpException;

	/**
	 * <p>
	 * NE그룹 좌표 저장
	 * </p>
	 *
	 * @param context
	 * @param ne_group
	 * @return
	 * @throws EmpException
	 */
	public Model4NeGroup updateNeGroupMapLocation(EmpContext context, Model4NeGroup ne_group) throws EmpException;

	/**
	 * <p>
	 * NE그룹 삭제
	 * </p>
	 * 
	 * @param context
	 * @param ne_group_id
	 * @return
	 * @throws EmpException
	 */
	public Model4NeGroup deleteNeGroup(EmpContext context, int ne_group_id) throws EmpException;

	/**
	 * <p>
	 * Cache 삭제
	 * </p>
	 *
	 * @param context
	 * @throws EmpException 
	 */
	public void clearCache(EmpContext context) throws EmpException;

}
