/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_onion.server.workflow.environment.database;

import java.io.File;

import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.server.workflow.DaoIf;
import com.hellonms.platforms.emp_core.share.error.EmpException;

/**
 * <p>
 * Database Dao
 * </p>
 *
 * @since 1.6
 * @create 2015. 6. 10.
 * @modified 2015. 6. 10.
 * @author cchyun
 *
 */
public interface Dao4DatabaseIf extends DaoIf {

	/**
	 * <p>
	 * DB 백업
	 * </p>
	 *
	 * @param context
	 * @param output
	 * @return
	 * @throws EmpException
	 */
	public File backupDatabase(EmpContext context, File output) throws EmpException;

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
