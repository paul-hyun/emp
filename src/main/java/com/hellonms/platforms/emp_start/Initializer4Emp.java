/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_start;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import java.util.prefs.Preferences;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

import com.hellonms.platforms.emp_core.EmpConfig4Server;
import com.hellonms.platforms.emp_core.server.log.BlackBox;
import com.hellonms.platforms.emp_core.server.log.LEVEL;
import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.server.workflow.WorkflowInitIf;
import com.hellonms.platforms.emp_core.server.workflow.WorkflowMap;
import com.hellonms.platforms.emp_core.share.error.ERROR_CODE;
import com.hellonms.platforms.emp_core.share.error.ERROR_CODE_CORE;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL;
import com.hellonms.platforms.emp_security.opened.AESCrypto;
import com.hellonms.platforms.emp_security.opened.KeyMap4Public;
import com.hellonms.platforms.emp_util.date.UtilDate;
import com.hellonms.platforms.emp_util.file.UtilFile;
import com.hellonms.platforms.emp_util.string.UtilString;
import com.hellonms.platforms.emp_util.system.UtilSystem;

/**
 * <p>
 * EMP 구동
 * </p>
 *
 * @since 1.6
 * @create 2015. 4. 29.
 * @modified 2015. 4. 29.
 * @author cchyun
 *
 */
public final class Initializer4Emp {

	private static final BlackBox blackBox = new BlackBox(Initializer4Emp.class);

	private static final long CAPACITY_FREE = 1000000;

	private static long capacity = CAPACITY_FREE;

	/**
	 * <p>
	 * 실제 시작
	 * </p>
	 * 
	 * @param emp_home_string
	 * @param product_name
	 * @param scheduleJob
	 * @throws EmpException
	 */
	public static void initialize(String emp_home_string, String product_name, boolean scheduleJob) throws EmpException {
		EmpContext context = new EmpContext(emp_home_string);

		try {
			File emp_home = Initializer4Emp.initializeEmpHome(context, emp_home_string);
			{
				File emp_home_lib = new File(emp_home, "WEB-INF/lib");
				if (emp_home_lib.isDirectory()) {
					System.setProperty("java.library.path", System.getProperty("java.library.path") + File.pathSeparator + emp_home_lib.getAbsolutePath());
					final Field sysPathsField = ClassLoader.class.getDeclaredField("sys_paths");
					sysPathsField.setAccessible(true);
					sysPathsField.set(null, null);
				}
			}
			Initializer4Emp.initializeConfig(context, emp_home);
			Initializer4Emp.initializeModel(context, emp_home);
			Initializer4Emp.initializeDatabase(context, emp_home);
			Initializer4Emp.initializeWFlow(context, emp_home);
			if (scheduleJob) {
				Initializer4Emp.initializeScheduleJob(context, emp_home);
			}

			context.commit();
		} catch (EmpException e) {
			throw e;
		} catch (Exception e) {
			context.rollback();
			throw new EmpException(e, ERROR_CODE.ERROR_UNKNOWN);
		} finally {
			context.close();
		}
	}

	/**
	 * <p>
	 * EMP HOME 초기화
	 * </p>
	 *
	 * @param context
	 * @param emp_home_string
	 * @return
	 * @throws EmpException
	 */
	private static File initializeEmpHome(EmpContext context, String emp_home_string) throws EmpException {
		File emp_home = new File(emp_home_string);
		if (!emp_home.isDirectory()) {
			throw new EmpException(ERROR_CODE_CORE.NOT_FOLDER, emp_home.getAbsolutePath());
		}
		EmpContext.setEMP_HOME(emp_home.getAbsolutePath());
		System.setProperty("emp.home", EmpContext.getEMP_HOME());

		return emp_home;
	}

	/**
	 * <p>
	 * 구성 초기화
	 * </p>
	 * 
	 * @param context
	 * @param emp_home
	 * @throws EmpException
	 */
	private static void initializeConfig(EmpContext context, File emp_home) throws EmpException {
		File emp_config = new File(emp_home, UtilString.format("config/{}", "emp_config.xml"));
		if (!emp_config.isFile()) {
			throw new EmpException(ERROR_CODE_CORE.NOT_FILE, emp_config.getAbsolutePath());
		}
		EmpConfig4Server.initialize(context, emp_config);
	}

	/**
	 * <p>
	 * 모델 초기화
	 * </p>
	 * 
	 * @param context
	 * @param emp_home
	 * @throws EmpException
	 */
	private static void initializeModel(EmpContext context, File emp_home) throws EmpException {
		File emp_model = new File(emp_home, UtilString.format("config/{}", EmpConfig4Server.getString("emp_model")));
		if (!emp_model.isFile()) {
			throw new EmpException(ERROR_CODE_CORE.FILE_IO, emp_model.getAbsolutePath());
		}

		EMP_MODEL.init_current(emp_model);
	}

	/**
	 * <p>
	 * Database 초기화
	 * </p>
	 * 
	 * @param context
	 * @throws EmpException
	 */
	private static void initializeDatabase(EmpContext context, File emp_home) throws EmpException {
		File mybatis_config = new File(emp_home, UtilString.format("config/{}", EmpConfig4Server.getString("mybatis_config")));
		if (!mybatis_config.isFile()) {
			throw new EmpException(ERROR_CODE_CORE.NOT_FILE, mybatis_config.getAbsolutePath());
		}

		try {
			FileInputStream input_stream = new FileInputStream(mybatis_config);
			EmpContext.initialize(input_stream);
			input_stream.close();
		} catch (IOException e) {
			throw new EmpException(ERROR_CODE_CORE.FILE_IO, mybatis_config.getAbsolutePath());
		}
	}

	/**
	 * <p>
	 * WFlow 초기화
	 * </p>
	 * 
	 * @param context
	 * @throws EmpException
	 */
	private static void initializeWFlow(EmpContext context, File emp_home) throws EmpException {
		String wflow_initialize = EmpConfig4Server.getString("wflow_initialize");
		try {
			@SuppressWarnings("unchecked")
			Class<? extends WorkflowInitIf> wflow_class = (Class<? extends WorkflowInitIf>) Class.forName(wflow_initialize);
			WorkflowInitIf wflow_object = wflow_class.newInstance();
			wflow_object.initialize(context);
		} catch (Exception e) {
			throw new EmpException(e, ERROR_CODE_CORE.INVALID_CONFIG, wflow_initialize);
		}

		WorkflowMap.initialize(context);
	}

	/**
	 * <p>
	 * WFlow 초기화
	 * </p>
	 * 
	 * @param context
	 * @throws EmpException
	 */
	private static void initializeScheduleJob(EmpContext context, File emp_home) throws EmpException {
		File quartz_properties = new File(emp_home, UtilString.format("config/{}", EmpConfig4Server.getString("quartz_properties")));
		if (!quartz_properties.isFile()) {
			throw new EmpException(ERROR_CODE_CORE.NOT_FILE, quartz_properties.getAbsolutePath());
		}
		File quartz_config = new File(emp_home, UtilString.format("config/{}", EmpConfig4Server.getString("quartz_config")));
		if (!quartz_config.isFile()) {
			throw new EmpException(ERROR_CODE_CORE.NOT_FILE, quartz_config.getAbsolutePath());
		}

		Properties property = new Properties();
		try {
			FileInputStream input_stream = new FileInputStream(quartz_properties);
			property.load(input_stream);
			input_stream.close();
		} catch (IOException e) {
			throw new EmpException(ERROR_CODE_CORE.FILE_IO, quartz_properties.getAbsolutePath());
		}

		try {
			property.setProperty("org.quartz.plugin.jobInitializer.fileNames", quartz_config.getAbsolutePath());
			StdSchedulerFactory schedulerFactory = new StdSchedulerFactory(property);
			Scheduler scheduler = schedulerFactory.getScheduler();
			WorkflowMap.setScheduler(scheduler);
			scheduler.start();
		} catch (SchedulerException e) {
			throw new EmpException(ERROR_CODE_CORE.FILE_IO, e, quartz_config.getAbsolutePath());
		}
	}

	/**
	 * <p>
	 * 종료
	 * </p>
	 *
	 * @param context
	 * @throws EmpException
	 */
	public static void dispose(EmpContext context) throws EmpException {
		WorkflowMap.dispose(context);
	}

}
