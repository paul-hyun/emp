/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.server.workflow.security.user;

import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.server.workflow.DaoIf;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_orange.share.model.security.user.Model4User;

/**
 * <p>
 * 사용자 Dao
 * </p>
 * 
 * @since 1.6
 * @create 2015. 3. 12.
 * @modified 2015. 3. 12.
 * @author cchyun
 * 
 */
public interface Dao4UserIf extends DaoIf {

	/**
	 * <p>
	 * 사용자 생성
	 * </p>
	 * 
	 * @param context
	 * @return
	 */
	public Model4User createUser(EmpContext context, Model4User user) throws EmpException;

	/**
	 * <p>
	 * 사용자 조회
	 * </p>
	 * 
	 * @param context
	 * @param user_id
	 * @return
	 * @throws EmpException
	 */
	public Model4User queryUser(EmpContext context, int user_id) throws EmpException;

	/**
	 * <p>
	 * 사용자 조회
	 * </p>
	 * 
	 * @param context
	 * @param user_account
	 * @return
	 * @throws EmpException
	 */
	public Model4User queryUserByAccount(EmpContext context, String user_account) throws EmpException;

	/**
	 * <p>
	 * 사용자 목록조회
	 * </p>
	 * 
	 * @param context
	 * @param startNo
	 * @param count
	 * @return
	 * @throws EmpException
	 */
	public Model4User[] queryListUser(EmpContext context, int startNo, int count) throws EmpException;

	/**
	 * <p>
	 * 암호비교
	 * </p>
	 * 
	 * @param context
	 * @param user_id
	 * @param password
	 * @return
	 * @throws EmpException 
	 */
	public boolean equalPassword(EmpContext context, int user_id, String password) throws EmpException;

	/**
	 * <p>
	 * 사용자 개수조회
	 * </p>
	 * 
	 * @param context
	 * @return
	 * @throws EmpException
	 */
	public int queryCountUser(EmpContext context) throws EmpException;

	/**
	 * <p>
	 * 사용자 수정
	 * </p>
	 * 
	 * @param context
	 * @param user
	 * @return
	 * @throws EmpException
	 */
	public Model4User updateUser(EmpContext context, Model4User user) throws EmpException;

	/**
	 * <p>
	 * 사용자 삭제
	 * </p>
	 * 
	 * @param context
	 * @param user_id
	 * @return
	 * @throws EmpException
	 */
	public Model4User deleteUser(EmpContext context, int user_id) throws EmpException;

}
