/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.server.workflow.security.user_group;

import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.server.workflow.WorkerIf;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_orange.share.model.security.user_group.Model4UserGroup;

/**
 * <p>
 * 사용자 그룹 Worker
 * </p>
 * 
 * @since 1.6
 * @create 2015. 3. 12.
 * @modified 2015. 3. 12.
 * @author cchyun
 * 
 */
public interface Worker4UserGroupIf extends WorkerIf {

	/**
	 * <p>
	 * 사용자그룹 생성
	 * </p>
	 * 
	 * @param context
	 * @param user_group
	 * @return
	 * @throws EmpException
	 */
	public Model4UserGroup createUserGroup(EmpContext context, Model4UserGroup user_group) throws EmpException;

	/**
	 * <p>
	 * 사용자그룹 조회
	 * </p>
	 * 
	 * @param context
	 * @param user_group_id
	 * @return
	 * @throws EmpException
	 */
	public Model4UserGroup queryUserGroup(EmpContext context, int user_group_id) throws EmpException;

	/**
	 * <p>
	 * 사용자그룹 목록조회
	 * </p>
	 * 
	 * @param context
	 * @param startNo
	 * @param count
	 * @return
	 * @throws EmpException
	 */
	public Model4UserGroup[] queryListUserGroup(EmpContext context, int startNo, int count) throws EmpException;

	/**
	 * <p>
	 * 사용자그룹 개수조회
	 * </p>
	 * 
	 * @param context
	 * @return
	 * @throws EmpException
	 */
	public int queryCountUserGroup(EmpContext context) throws EmpException;

	/**
	 * <p>
	 * 사용자그룹 수정
	 * </p>
	 * 
	 * @param context
	 * @param user_group
	 * @return
	 * @throws EmpException
	 */
	public Model4UserGroup updateUserGroup(EmpContext context, Model4UserGroup user_group) throws EmpException;

	/**
	 * <p>
	 * 사용자그룹 삭제
	 * </p>
	 * 
	 * @param context
	 * @param user_group_id
	 * @return
	 * @throws EmpException
	 */
	public Model4UserGroup deleteUserGroup(EmpContext context, int user_group_id) throws EmpException;

}
