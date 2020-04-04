/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_onion.server.workflow.environment.database;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hellonms.platforms.emp_core.server.log.BlackBox;
import com.hellonms.platforms.emp_core.server.log.LEVEL;
import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.server.workflow.DaoIf;
import com.hellonms.platforms.emp_core.server.workflow.WorkflowMap;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_onion.server.driver.mybatis.Driver4MybatisIf;
import com.hellonms.platforms.emp_onion.share.error.ERROR_CODE_ONION;
import com.hellonms.platforms.emp_util.string.UtilString;
import com.hellonms.platforms.emp_util.system.UtilSystem;

/**
 * <p>
 * Insert description of Dao4Database.java
 * </p>
 *
 * @since 1.6
 * @create 2015. 6. 11.
 * @modified 2015. 6. 11.
 * @author cchyun
 *
 */
public class Dao4Database implements Dao4DatabaseIf {

	private class DatabaseGarbage {
		private final String table_primary_name;
		private final String table_primary_key;
		private final String field_name;
		private final Object field_value;
		private final int garbage_offset_sec;
		private final String[] table_foregin_names;

		public DatabaseGarbage(String table_primary_name, String table_primary_key, String field_name, Object field_value, int garbage_offset_day, String... table_foregin_names) {
			this.table_primary_name = table_primary_name;
			this.table_primary_key = table_primary_key;
			this.field_name = field_name;
			this.field_value = field_value;
			this.garbage_offset_sec = garbage_offset_day * 24 * 60 * 60;
			this.table_foregin_names = table_foregin_names;
		}
	}

	protected Driver4MybatisIf driver4Mybatis;

	private List<DatabaseGarbage> database_garbage_list = new ArrayList<DatabaseGarbage>();

	private static final BlackBox blackBox = new BlackBox(Dao4Database.class);

	@Override
	public Class<? extends DaoIf> getDefine_class() {
		return Dao4DatabaseIf.class;
	}

	public void addDatabaseGarbage(String table_primary_name, String table_primary_key, String field_name, Object field_value, int garbage_offset_day, String... table_foregin_names) {
		database_garbage_list.add(new DatabaseGarbage(table_primary_name, table_primary_key, field_name, field_value, garbage_offset_day, table_foregin_names));
	}

	@Override
	public void initialize(EmpContext context) throws EmpException {
		driver4Mybatis = (Driver4MybatisIf) WorkflowMap.getDriver(Driver4MybatisIf.class);
	}

	@Override
	public void dispose(EmpContext context) throws EmpException {
	}

	@Override
	public File backupDatabase(EmpContext context, File output) throws EmpException {
		try {
			File cmd = new File(UtilString.format("{}/bin/db_backup_{}_{}.bat", EmpContext.getEMP_HOME(), UtilSystem.getOS(), EmpContext.getDatabase()));
			if (!cmd.isFile()) {
				throw new EmpException(ERROR_CODE_ONION.NOT_FILE, cmd.getAbsolutePath());
			}
			Process process = Runtime.getRuntime().exec(new String[] { cmd.getAbsolutePath(), output.getAbsolutePath() });
			process.waitFor();

			InputStream out = process.getInputStream();
			StringBuilder string_out = new StringBuilder();

			InputStream err = process.getErrorStream();
			StringBuilder string_err = new StringBuilder();

			try {
				byte[] buf = new byte[1024 * 8];

				for (int length = 0; 0 < (length = out.read(buf));) {
					String string = new String(buf, 0, length, System.getProperty("sun.jnu.encoding"));
					string_out.append(string);
					if (blackBox.isEnabledFor(LEVEL.Fatal)) {
						blackBox.log(LEVEL.Fatal, context, string);
					}
				}

				for (int length = 0; 0 < (length = err.read(buf));) {
					String string = new String(buf, 0, length, System.getProperty("sun.jnu.encoding"));
					string_err.append(string);
					if (blackBox.isEnabledFor(LEVEL.Fatal)) {
						blackBox.log(LEVEL.Fatal, context, string);
					}
				}
			} catch (Exception e) {
				if (blackBox.isEnabledFor(LEVEL.Fatal)) {
					blackBox.log(LEVEL.Fatal, context, e);
				}
			}

			if (0 < string_err.length()) {
				throw new EmpException(ERROR_CODE_ONION.FILE_IO, string_err.toString());
			}
			return output;
		} catch (EmpException e) {
			throw e;
		} catch (Exception e) {
			throw new EmpException(e, ERROR_CODE_ONION.FILE_IO, output.getAbsolutePath());
		}
	}

	@Override
	public int deleteDatabaseGarbage(EmpContext context) throws EmpException {
		int delete_count = 0;
		Map<String, Object> parameter = new HashMap<String, Object>();
		for (DatabaseGarbage database_garbage : database_garbage_list) {
			parameter.clear();
			parameter.put("table_primary_name", database_garbage.table_primary_name);
			parameter.put("table_primary_key", database_garbage.table_primary_key);
			parameter.put("field_name", database_garbage.field_name);
			parameter.put("field_value", database_garbage.field_value);
			parameter.put("garbage_offset_sec", database_garbage.garbage_offset_sec);

			{
				int count = driver4Mybatis.delete(context, getDefine_class(), "deleteListDatabaseGarbageByPrimary_key", parameter);
				delete_count += count;
				if (0 < count && blackBox.isEnabledFor(LEVEL.Fatal)) {
					blackBox.log(LEVEL.Fatal, context, UtilString.format("delete primary garbage {} item from {} of {}", count, database_garbage.table_primary_key, database_garbage.table_primary_name));
				}
			}

			for (String table_foregin_name : database_garbage.table_foregin_names) {
				parameter.put("table_foregin_name", table_foregin_name);
				int count = driver4Mybatis.delete(context, getDefine_class(), "deleteListDatabaseGarbageByForegin_key", parameter);
				delete_count += count;
				if (0 < count && blackBox.isEnabledFor(LEVEL.Fatal)) {
					blackBox.log(LEVEL.Fatal, context, UtilString.format("delete foregin garbage {} item from {} by foregin key {} of {}", count, table_foregin_name, database_garbage.table_primary_key, database_garbage.table_primary_name));
				}
			}
		}
		return delete_count;
	}

}
