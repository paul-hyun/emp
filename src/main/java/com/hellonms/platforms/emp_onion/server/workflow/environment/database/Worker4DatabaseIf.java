/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_onion.server.workflow.environment.database;

import java.io.File;

import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.server.workflow.WorkerIf;
import com.hellonms.platforms.emp_core.share.error.EmpException;

/**
 * <p>
 * Database Worker
 * </p>
 *
 * @since 1.6
 * @create 2015. 6. 10.
 * @modified 2015. 6. 10.
 * @author cchyun
 *
 */
public interface Worker4DatabaseIf extends WorkerIf {

	/**
	 * <p>
	 * 사용자 DB 백업
	 * </p>
	 *
	 * @param context
	 * @return
	 * @throws EmpException
	 */
	public File backupDatabaseByUser(EmpContext context) throws EmpException;

	/**
	 * <p>
	 * 주기적 DB 백업
	 * </p>
	 *
	 * @param context
	 * @return
	 * @throws EmpException
	 */
	public File backupDatabaseBySchedule(EmpContext context) throws EmpException;

	/**
	 * <p>
	 * DB garbage 값 삭제
	 * </p>
	 *
	 * @param context
	 * @return
	 * @throws EmpException 
	 */
	public int deleteDatabaseGarbage(EmpContext context) throws EmpException;

}
