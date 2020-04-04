/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_core.server.workflow;

import java.util.HashMap;
import java.util.Map;

import org.quartz.Scheduler;

import com.hellonms.platforms.emp_core.server.driver.DriverIf;
import com.hellonms.platforms.emp_core.server.invoker.InvokerIf;
import com.hellonms.platforms.emp_core.server.log.BlackBox;
import com.hellonms.platforms.emp_core.server.log.LEVEL;
import com.hellonms.platforms.emp_core.server.schedule_job.ScheduleJobIf;
import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.share.error.ERROR_CODE_CORE;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_util.string.UtilString;

/**
 * <p>
 * Workflow 저장소<br />
 * Workflow 및 관련 컴포넌트를 저장한다.
 * </p>
 * 
 * @since 1.6
 * @create 2015. 3. 10.
 * @modified 2015. 3. 10.
 * @author cchyun
 * 
 */
public class WorkflowMap {

	/**
	 * Quartz Scheduler (주기적 작업 수행)
	 */
	private static Scheduler scheduler;

	/**
	 * Driver 저장
	 */
	private static Map<Class<? extends DriverIf>, DriverIf> driver_map = new HashMap<Class<? extends DriverIf>, DriverIf>();

	/**
	 * Worker 저장
	 */
	private static Map<Class<? extends WorkerIf>, WorkerIf> worker_map = new HashMap<Class<? extends WorkerIf>, WorkerIf>();

	/**
	 * 주기적 작업 저장
	 */
	private static Map<Class<? extends ScheduleJobIf>, ScheduleJobIf> schedule_job_map = new HashMap<Class<? extends ScheduleJobIf>, ScheduleJobIf>();

	/**
	 * Worker 저장
	 */
	private static Map<Class<? extends InvokerIf>, InvokerIf> invoker_map = new HashMap<Class<? extends InvokerIf>, InvokerIf>();

	/**
	 * 로거
	 */
	private static final BlackBox blackBox = new BlackBox(WorkflowMap.class);

	/**
	 * <p>
	 * Quartz Scheduler 조회
	 * </p>
	 * 
	 * @return
	 */
	public static Scheduler getScheduler() {
		return WorkflowMap.scheduler;
	}

	/**
	 * <p>
	 * Quartz Scheduler 저장
	 * </p>
	 * 
	 * @param scheduler
	 */
	public static void setScheduler(Scheduler scheduler) {
		WorkflowMap.scheduler = scheduler;
	}

	/**
	 * <p>
	 * Driver 조회
	 * </p>
	 * 
	 * @param clazz
	 * @return
	 * @throws EmpException
	 */
	public static DriverIf getDriver(Class<? extends DriverIf> clazz) throws EmpException {
		DriverIf driver = driver_map.get(clazz);
		if (driver == null) {
			throw new EmpException(ERROR_CODE_CORE.WORKFLOW_NOTEXISTS, clazz);
		}
		return driver;
	}

	/**
	 * <p>
	 * Driver 저장
	 * </p>
	 * 
	 * @param clazz
	 * @param driver
	 * @throws EmpException
	 */
	public static void setDriver(DriverIf driver) throws EmpException {
		if (driver_map.get(driver.getDefine_class()) != null) {
			throw new EmpException(ERROR_CODE_CORE.WORKFLOW_DUPLICATE, driver.getDefine_class());
		}
		driver_map.put(driver.getDefine_class(), driver);

		if (blackBox.isEnabledFor(LEVEL.Component)) {
			blackBox.log(LEVEL.Component, null, UtilString.format("Load Driver id={} bean={}", driver.getDefine_class().getName(), driver.getClass().getName()));
		}
	}

	/**
	 * <p>
	 * Worker 조회
	 * </p>
	 * 
	 * @param clazz
	 * @return
	 * @throws EmpException
	 */
	public static WorkerIf getWorker(Class<? extends WorkerIf> clazz) throws EmpException {
		WorkerIf worker = worker_map.get(clazz);
		if (worker == null) {
			throw new EmpException(ERROR_CODE_CORE.WORKFLOW_NOTEXISTS, clazz);
		}
		return worker;
	}

	/**
	 * <p>
	 * Worker 저장
	 * </p>
	 * 
	 * @param clazz
	 * @param worker
	 * @throws EmpException
	 */
	public static void setWorker(WorkerIf worker) throws EmpException {
		if (worker_map.get(worker.getDefine_class()) != null) {
			throw new EmpException(ERROR_CODE_CORE.WORKFLOW_DUPLICATE, worker.getDefine_class());
		}
		worker_map.put(worker.getDefine_class(), worker);

		if (blackBox.isEnabledFor(LEVEL.Component)) {
			blackBox.log(LEVEL.Component, null, UtilString.format("Load Worker id={} bean={}", worker.getDefine_class().getName(), worker.getClass().getName()));
		}
	}

	/**
	 * <p>
	 * 주기적 작업 조회
	 * </p>
	 * 
	 * @param clazz
	 * @return
	 * @throws EmpException
	 */
	public static ScheduleJobIf getScheduleJob(Class<? extends ScheduleJobIf> clazz) throws EmpException {
		ScheduleJobIf schedule_job = schedule_job_map.get(clazz);
		if (schedule_job == null) {
			throw new EmpException(ERROR_CODE_CORE.WORKFLOW_NOTEXISTS, clazz);
		}
		return schedule_job;
	}

	/**
	 * <p>
	 * 주기적 작업 저장
	 * </p>
	 * 
	 * @param clazz
	 * @param schedule_job
	 * @throws EmpException
	 */
	public static void setScheduleJob(ScheduleJobIf schedule_job) throws EmpException {
		if (schedule_job_map.get(schedule_job.getDefine_class()) != null) {
			throw new EmpException(ERROR_CODE_CORE.WORKFLOW_DUPLICATE, schedule_job.getDefine_class());
		}
		schedule_job_map.put(schedule_job.getDefine_class(), schedule_job);

		if (blackBox.isEnabledFor(LEVEL.Component)) {
			blackBox.log(LEVEL.Component, null, UtilString.format("Load ScheduleJob id={} bean={}", schedule_job.getDefine_class().getName(), schedule_job.getClass().getName()));
		}
	}

	/**
	 * <p>
	 * Invoker를 조회한다.
	 * </p>
	 * 
	 * @param clazz
	 * @return
	 * @throws EmpException
	 */
	public static InvokerIf getInvoker(Class<? extends InvokerIf> clazz) throws EmpException {
		InvokerIf invoker = invoker_map.get(clazz);
		if (invoker == null) {
			throw new EmpException(ERROR_CODE_CORE.WORKFLOW_NOTEXISTS, clazz);
		}
		return invoker;
	}

	/**
	 * <p>
	 * Invoker를 저장한다.
	 * </p>
	 * 
	 * @param clazz
	 * @param invoker
	 * @throws EmpException
	 */
	public static void setInvoker(InvokerIf invoker) throws EmpException {
		if (invoker_map.get(invoker.getDefine_class()) != null) {
			throw new EmpException(ERROR_CODE_CORE.WORKFLOW_DUPLICATE, invoker.getDefine_class());
		}
		invoker_map.put(invoker.getDefine_class(), invoker);

		if (blackBox.isEnabledFor(LEVEL.Component)) {
			blackBox.log(LEVEL.Component, null, UtilString.format("Load Invoker id={} bean={}", invoker.getDefine_class().getName(), invoker.getClass().getName()));
		}
	}

	/**
	 * <p>
	 * 초기화 수행
	 * </p>
	 * 
	 * @param context
	 * @throws EmpException
	 */
	public static void initialize(EmpContext context) throws EmpException {
		for (DriverIf driver : driver_map.values()) {
			if (blackBox.isEnabledFor(LEVEL.Component)) {
				blackBox.log(LEVEL.Component, null, UtilString.format("Initialize Driver id={} bean={}", driver.getDefine_class().getName(), driver.getClass().getName()));
			}
			driver.initialize(context);
		}
		for (WorkerIf worker : worker_map.values()) {
			if (blackBox.isEnabledFor(LEVEL.Component)) {
				blackBox.log(LEVEL.Component, null, UtilString.format("Initialize Worker id={} bean={}", worker.getDefine_class().getName(), worker.getClass().getName()));
			}
			worker.initialize(context);
		}
		for (ScheduleJobIf scheduleJob : schedule_job_map.values()) {
			if (blackBox.isEnabledFor(LEVEL.Component)) {
				blackBox.log(LEVEL.Component, null, UtilString.format("Initialize ScheduleJob id={} bean={}", scheduleJob.getDefine_class().getName(), scheduleJob.getClass().getName()));
			}
			scheduleJob.initialize(context);
		}
		for (InvokerIf invoker : invoker_map.values()) {
			if (blackBox.isEnabledFor(LEVEL.Component)) {
				blackBox.log(LEVEL.Component, null, UtilString.format("Initialize Invoker id={} bean={}", invoker.getDefine_class().getName(), invoker.getClass().getName()));
			}
			invoker.initialize(context);
		}
	}

	/**
	 * <p>
	 * 종료 수행
	 * </p>
	 * 
	 * @param context
	 * @throws EmpException
	 */
	public static void dispose(EmpContext context) throws EmpException {
		for (InvokerIf invoker : invoker_map.values()) {
			invoker.dispose(context);
		}
		for (ScheduleJobIf driver : schedule_job_map.values()) {
			driver.dispose(context);
		}
		for (WorkerIf worker : worker_map.values()) {
			worker.dispose(context);
		}
		for (DriverIf driver : driver_map.values()) {
			driver.dispose(context);
		}
	}
}
