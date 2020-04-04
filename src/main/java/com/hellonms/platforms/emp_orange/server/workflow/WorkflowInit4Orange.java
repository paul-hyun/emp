/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.server.workflow;

import java.io.File;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.server.workflow.WorkflowInitIf;
import com.hellonms.platforms.emp_core.server.workflow.WorkflowMap;
import com.hellonms.platforms.emp_core.share.error.ERROR_CODE_CORE;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_core.share.operation.OPERATION_CODE;
import com.hellonms.platforms.emp_onion.server.driver.mybatis.Driver4Mybatis;
import com.hellonms.platforms.emp_onion.server.schedule_job.environment.database.ScheduleJob4Database;
import com.hellonms.platforms.emp_onion.server.schedule_job.environment.file.ScheduleJob4File;
import com.hellonms.platforms.emp_onion.server.workflow.client.image.Worker4Image;
import com.hellonms.platforms.emp_onion.server.workflow.client.language.Worker4Language;
import com.hellonms.platforms.emp_onion.server.workflow.client.sound.Worker4Sound;
import com.hellonms.platforms.emp_onion.server.workflow.environment.database.Dao4Database;
import com.hellonms.platforms.emp_onion.server.workflow.environment.database.Worker4Database;
import com.hellonms.platforms.emp_onion.server.workflow.environment.preference.Dao4Preference;
import com.hellonms.platforms.emp_onion.server.workflow.environment.preference.Worker4Preference;
import com.hellonms.platforms.emp_orange.server.driver.icmp.Driver4ICMP;
import com.hellonms.platforms.emp_orange.server.driver.icmp.Driver4ICMPSimultor;
import com.hellonms.platforms.emp_orange.server.driver.snmp.Driver4SNMP;
import com.hellonms.platforms.emp_orange.server.driver.snmp.Driver4SNMPSimultor;
import com.hellonms.platforms.emp_orange.server.invoker.Invoker4Orange;
import com.hellonms.platforms.emp_orange.server.schedule_job.environment.database.ScheduleJob4DatabaseOrange;
import com.hellonms.platforms.emp_orange.server.schedule_job.fault.event.ScheduleJob4AlarmStatistics;
import com.hellonms.platforms.emp_orange.server.schedule_job.network.ne.ScheduleJob4NMS;
import com.hellonms.platforms.emp_orange.server.schedule_job.network.ne_info.ScheduleJob4NeStatistics;
import com.hellonms.platforms.emp_orange.server.schedule_job.network.ne_session.ScheduleJob4NeSession;
import com.hellonms.platforms.emp_orange.server.schedule_job.security.user_session.ScheduleJob4UserSession;
import com.hellonms.platforms.emp_orange.server.workflow.fault.event.Dao4Alarm;
import com.hellonms.platforms.emp_orange.server.workflow.fault.event.Dao4AlarmStatistics;
import com.hellonms.platforms.emp_orange.server.workflow.fault.event.Dao4Event;
import com.hellonms.platforms.emp_orange.server.workflow.fault.event.Worker4Event;
import com.hellonms.platforms.emp_orange.server.workflow.network.link.Dao4NetworkLink;
import com.hellonms.platforms.emp_orange.server.workflow.network.link.Worker4NetworkLink;
import com.hellonms.platforms.emp_orange.server.workflow.network.ne.Dao4Ne;
import com.hellonms.platforms.emp_orange.server.workflow.network.ne.Worker4NMS;
import com.hellonms.platforms.emp_orange.server.workflow.network.ne.Worker4Ne;
import com.hellonms.platforms.emp_orange.server.workflow.network.ne_group.Dao4NeGroup;
import com.hellonms.platforms.emp_orange.server.workflow.network.ne_group.Worker4NeGroup;
import com.hellonms.platforms.emp_orange.server.workflow.network.ne_info.Dao4NeInfo;
import com.hellonms.platforms.emp_orange.server.workflow.network.ne_info.Dao4NeStatistics;
import com.hellonms.platforms.emp_orange.server.workflow.network.ne_info.Dao4NeThreshold;
import com.hellonms.platforms.emp_orange.server.workflow.network.ne_info.Trigger4NeInfo;
import com.hellonms.platforms.emp_orange.server.workflow.network.ne_info.Worker4NeInfo;
import com.hellonms.platforms.emp_orange.server.workflow.network.ne_session.icmp.Adapter4NeSessionICMP;
import com.hellonms.platforms.emp_orange.server.workflow.network.ne_session.icmp.Dao4NeSessionICMP;
import com.hellonms.platforms.emp_orange.server.workflow.network.ne_session.icmp.Worker4NeSessionICMP;
import com.hellonms.platforms.emp_orange.server.workflow.network.ne_session.snmp.Adapter4NeSessionSNMP;
import com.hellonms.platforms.emp_orange.server.workflow.network.ne_session.snmp.Dao4NeSessionSNMP;
import com.hellonms.platforms.emp_orange.server.workflow.network.ne_session.snmp.Worker4NeSessionSNMP;
import com.hellonms.platforms.emp_orange.server.workflow.security.operation_log.Dao4OperationLog;
import com.hellonms.platforms.emp_orange.server.workflow.security.operation_log.Worker4OperationLog;
import com.hellonms.platforms.emp_orange.server.workflow.security.user.Dao4User;
import com.hellonms.platforms.emp_orange.server.workflow.security.user.Worker4User;
import com.hellonms.platforms.emp_orange.server.workflow.security.user_group.Dao4UserGroup;
import com.hellonms.platforms.emp_orange.server.workflow.security.user_group.Worker4UserGroup;
import com.hellonms.platforms.emp_orange.server.workflow.security.user_session.Dao4UserSession;
import com.hellonms.platforms.emp_orange.server.workflow.security.user_session.Worker4UserSession;
import com.hellonms.platforms.emp_orange.share.error.ERROR_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.environment.preference.PREFERENCE_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.security.operation_log.OPERATION_CODE_ORANGE;

/**
 * <p>
 * Orange가 동작하기 위한 Workflow를 초기화 함
 * </p>
 * 
 * @since 1.6
 * @create 2015. 3. 10.
 * @modified 2015. 3. 10.
 * @author cchyun
 */
public class WorkflowInit4Orange implements WorkflowInitIf {

	private boolean run_simulaotr = false;
	private String simulator = "127.0.0.1";

	@Override
	public void initialize(EmpContext context) throws EmpException {
		initializeCode(context);
		if (run_simulaotr) {
			initializeDriverSimulator(context);
		} else {
			initializeDriver(context);
		}
		initializeWorkflow(context);
		initializeScheduleJob(context);
		initializeInvoker(context);
	}

	/**
	 * <p>
	 * 코드 초기화
	 * </p>
	 * 
	 * @param context
	 */
	protected void initializeCode(EmpContext context) {
		new ERROR_CODE_ORANGE();
		new PREFERENCE_CODE_ORANGE();
	}

	/**
	 * <p>
	 * Driver 초기화 함
	 * </p>
	 * 
	 * @param context
	 * @throws EmpException
	 */
	protected void initializeDriver(EmpContext context) throws EmpException {
		{
			Driver4Mybatis driver = new Driver4Mybatis();
			WorkflowMap.setDriver(driver);
		}
		{
			Driver4ICMP driver = new Driver4ICMP();
			WorkflowMap.setDriver(driver);
		}
		{
			Driver4SNMP driver = new Driver4SNMP();
			driver.setTrap_ports(new int[] { 162 });
			WorkflowMap.setDriver(driver);
		}
	}

	/**
	 * <p>
	 * Driver 초기화 함
	 * </p>
	 * 
	 * @param context
	 * @throws EmpException
	 */
	protected void initializeDriverSimulator(EmpContext context) throws EmpException {
		try {
			InetAddress simulator = InetAddress.getByName(this.simulator);
			{
				Driver4Mybatis driver = new Driver4Mybatis();
				WorkflowMap.setDriver(driver);
			}
			{
				Driver4ICMPSimultor driver = new Driver4ICMPSimultor();
				driver.setSimulator(simulator);
				WorkflowMap.setDriver(driver);
			}
			{
				Driver4SNMPSimultor driver = new Driver4SNMPSimultor();
				driver.setTrap_ports(new int[] { 162 });
				driver.setSimulator(simulator);
				WorkflowMap.setDriver(driver);
			}
		} catch (Exception e) {
			throw new EmpException(e, ERROR_CODE_CORE.NETOWRK_IO);
		}
	}

	/**
	 * <p>
	 * Workflow 초기화 함
	 * </p>
	 * 
	 * @param context
	 */
	protected void initializeWorkflow(EmpContext context) throws EmpException {
		initializeWorkflowClient(context);
		initializeWorkflowEnvironment(context);
		initializeWorkflowNetwork(context);
		initializeWorkflowFault(context);
		initializeWorkflowSecurity(context);
	}

	/**
	 * <p>
	 * Client Worflow 초기화
	 * </p>
	 *
	 * @param context
	 * @throws EmpException
	 */
	protected void initializeWorkflowClient(EmpContext context) throws EmpException {
		{
			Worker4Language worker = new Worker4Language();
			worker.setLanguage_folders("/com/hellonms/platforms/emp_core/share/language", "/com/hellonms/platforms/emp_onion/share/language", "/com/hellonms/platforms/emp_orange/share/language");
			WorkflowMap.setWorker(worker);
		}
		{
			Worker4Image worker = new Worker4Image();
			WorkflowMap.setWorker(worker);
		}
		{
			Worker4Sound worker = new Worker4Sound();
			WorkflowMap.setWorker(worker);
		}
	}

	/**
	 * <p>
	 * Environment Worflow 초기화
	 * </p>
	 *
	 * @param context
	 * @throws EmpException
	 */
	protected void initializeWorkflowEnvironment(EmpContext context) throws EmpException {
		{
			Worker4Preference worker = new Worker4Preference();
			WorkflowMap.setWorker(worker);

			Dao4Preference dao = new Dao4Preference();
			worker.setDao4Preference(dao);
		}
		{
			Worker4Database worker = new Worker4Database();
			WorkflowMap.setWorker(worker);

			Dao4Database dao = new Dao4Database();
			dao.addDatabaseGarbage("EMP_USER", "user_id", "delete_state", true, 31);
			dao.addDatabaseGarbage("EMP_USER_GROUP", "user_group_id", "delete_state", true, 31);
			dao.addDatabaseGarbage("EMP_NE_SESSION_SNMP", "ne_session_id", "delete_state", true, 31);
			dao.addDatabaseGarbage("EMP_NE_SESSION_ICMP", "ne_session_id", "delete_state", true, 31);
			List<String> foregin_list = new ArrayList<String>();
			foregin_list.add("EMP_NE_SESSION_ICMP");
			foregin_list.add("EMP_NE_SESSION_SNMP");
			foregin_list.add("EMP_NE_INFO_VALUE");

			dao.addDatabaseGarbage("EMP_NE", "ne_id", "delete_state", true, 31, foregin_list.toArray(new String[0]));
			dao.addDatabaseGarbage("EMP_NE_GROUP", "ne_group_id", "delete_state", true, 31);
			worker.setDao4Database(dao);
		}
	}

	/**
	 * <p>
	 * Workflow 초기화 함 (네트워크관련 기능)
	 * </p>
	 * 
	 * @param context
	 */
	protected void initializeWorkflowNetwork(EmpContext context) throws EmpException {
		{
			Worker4NeGroup worker = new Worker4NeGroup();
			WorkflowMap.setWorker(worker);

			Dao4NeGroup dao = new Dao4NeGroup();
			worker.setDao4NeGroup(dao);
		}

		Worker4Ne worker4Ne = new Worker4Ne();
		WorkflowMap.setWorker(worker4Ne);
		{

			Dao4Ne dao = new Dao4Ne();
			worker4Ne.setDao4Ne(dao);
		}
		{
			Worker4NeSessionICMP worker = new Worker4NeSessionICMP();
			WorkflowMap.setWorker(worker);

			Dao4NeSessionICMP dao = new Dao4NeSessionICMP();
			worker.setDao4NeSession(dao);

			Adapter4NeSessionICMP adapter = new Adapter4NeSessionICMP();
			worker.setAdapter4NeSession(adapter);

			worker4Ne.addWorker4NeSession(worker);
		}
		{
			Worker4NeSessionSNMP worker = new Worker4NeSessionSNMP();
			WorkflowMap.setWorker(worker);

			Dao4NeSessionSNMP dao = new Dao4NeSessionSNMP();
			worker.setDao4NeSession(dao);

			Adapter4NeSessionSNMP adapter = new Adapter4NeSessionSNMP();
			worker.setAdapter4NeSession(adapter);

			worker4Ne.addWorker4NeSession(worker);
		}
		{
			Worker4NetworkLink worker = new Worker4NetworkLink();
			WorkflowMap.setWorker(worker);

			Dao4NetworkLink dao = new Dao4NetworkLink();
			worker.setDao4NetworkLink(dao);
		}
		{
			Worker4NMS worker = new Worker4NMS();
			WorkflowMap.setWorker(worker);
		}
		{
			Worker4NeInfo worker = new Worker4NeInfo();
			WorkflowMap.setWorker(worker);

			Dao4NeInfo dao4NeInfo = new Dao4NeInfo();
			worker.setDao4NeInfo(dao4NeInfo);

			Dao4NeStatistics dao4NeStatistics = new Dao4NeStatistics();
			worker.setDao4NeStatistics(dao4NeStatistics);

			Dao4NeThreshold dao4NeThreshold = new Dao4NeThreshold();
			worker.setDao4NeThreshold(dao4NeThreshold);

			worker.addTrigger(new Trigger4NeInfo());
		}
	}

	/**
	 * <p>
	 * Workflow 초기화 함 (장애 기능)
	 * </p>
	 * 
	 * @param context
	 */
	protected void initializeWorkflowFault(EmpContext context) throws EmpException {
		{
			Worker4Event worker = new Worker4Event();
			WorkflowMap.setWorker(worker);

			Dao4Event dao4Event = new Dao4Event();
			worker.setDao4Event(dao4Event);

			Dao4Alarm dao4Alarm = new Dao4Alarm();
			worker.setDao4Alarm(dao4Alarm);

			Dao4AlarmStatistics dao4AlarmStatistics = new Dao4AlarmStatistics();
			worker.setDao4AlarmStatistics(dao4AlarmStatistics);
		}
	}

	/**
	 * <p>
	 * Workflow 초기화 함 (보안관련 기능)
	 * </p>
	 * 
	 * @param context
	 */
	protected void initializeWorkflowSecurity(EmpContext context) throws EmpException {
		{
			Worker4UserSession worker = new Worker4UserSession();
			WorkflowMap.setWorker(worker);

			Dao4UserSession dao = new Dao4UserSession();
			worker.setDao4UserSession(dao);
		}
		{
			Worker4UserGroup worker = new Worker4UserGroup();
			WorkflowMap.setWorker(worker);

			Dao4UserGroup dao4UserGroup = new Dao4UserGroup();
			worker.setDao4UserGroup(dao4UserGroup);
		}
		{
			Worker4User worker = new Worker4User();
			WorkflowMap.setWorker(worker);

			Dao4User dao4User = new Dao4User();
			worker.setDao4User(dao4User);
		}
		{
			Worker4OperationLog worker = new Worker4OperationLog();
			WorkflowMap.setWorker(worker);

			Dao4OperationLog dao = new Dao4OperationLog();
			dao.setOperation_codes(new OPERATION_CODE[] { //
			OPERATION_CODE_ORANGE.SECURITY_USERSESSION_LOGIN, //
					OPERATION_CODE_ORANGE.SECURITY_USERSESSION_LOGOUT, //
					OPERATION_CODE_ORANGE.SECURITY_USERSESSION_TERMINATE, //
					OPERATION_CODE_ORANGE.NETWORK_NEGROUP_CREATE, //
					OPERATION_CODE_ORANGE.NETWORK_NEGROUP_UPDATE, //
					OPERATION_CODE_ORANGE.NETWORK_NEGROUP_DELETE, //
					OPERATION_CODE_ORANGE.NETWORK_NE_CREATE, //
					OPERATION_CODE_ORANGE.NETWORK_NE_UPDATE, //
					OPERATION_CODE_ORANGE.NETWORK_NE_DELETE, //
					OPERATION_CODE_ORANGE.NETWORK_LINK_CREATE, //
					OPERATION_CODE_ORANGE.NETWORK_LINK_DELETE, //
					OPERATION_CODE_ORANGE.NETWORK_NE_INFO_CREATE, //
					OPERATION_CODE_ORANGE.NETWORK_NE_INFO_UPDATE, //
					OPERATION_CODE_ORANGE.NETWORK_NE_INFO_DELETE, //
					OPERATION_CODE_ORANGE.NETWORK_NE_THRESHOLD_UPDATE, //
					OPERATION_CODE_ORANGE.FAULT_ALARM_ACK, //
					OPERATION_CODE_ORANGE.FAULT_ALARM_CLEAR, //
					OPERATION_CODE_ORANGE.SECURITY_USER_CREATE, //
					OPERATION_CODE_ORANGE.SECURITY_USER_UPDATE, //
					OPERATION_CODE_ORANGE.SECURITY_USER_DELETE, //
			});
			worker.setDao4OperationLog(dao);
		}
	}

	/**
	 * <p>
	 * ScheduleJob 초기화 함
	 * </p>
	 * 
	 * @param context
	 */
	protected void initializeScheduleJob(EmpContext context) throws EmpException {
		{
			ScheduleJob4Database schedule_job = new ScheduleJob4DatabaseOrange();
			WorkflowMap.setScheduleJob(schedule_job);
		}
		{
			ScheduleJob4File schedule_job = new ScheduleJob4File();
			File emp_home = new File(EmpContext.getEMP_HOME());
			schedule_job.addFileGarbage(new File(emp_home, "../log"), 33);
			schedule_job.addFileGarbage(new File(emp_home, "../transaction"), 33);
			schedule_job.addPreferenceGarbage(PREFERENCE_CODE_ORANGE.PREFERENCE_ONION_ENVIRONMENT_DATABASE_BACKUP_DIRECTORY, 33);
			WorkflowMap.setScheduleJob(schedule_job);
		}
		{
			ScheduleJob4UserSession schedule_job = new ScheduleJob4UserSession();
			WorkflowMap.setScheduleJob(schedule_job);
		}
		{
			ScheduleJob4NeSession schedule_job = new ScheduleJob4NeSession();
			WorkflowMap.setScheduleJob(schedule_job);
		}
		{
			ScheduleJob4NMS schedule_job = new ScheduleJob4NMS();
			WorkflowMap.setScheduleJob(schedule_job);
		}
		{
			ScheduleJob4NeStatistics schedule_job = new ScheduleJob4NeStatistics();
			WorkflowMap.setScheduleJob(schedule_job);
		}
		{
			ScheduleJob4AlarmStatistics schedule_job = new ScheduleJob4AlarmStatistics();
			WorkflowMap.setScheduleJob(schedule_job);
		}
	}

	/**
	 * <p>
	 * Invoker 초기화 함
	 * </p>
	 * 
	 * @param context
	 */
	protected void initializeInvoker(EmpContext context) throws EmpException {
		{
			Invoker4Orange invoker = new Invoker4Orange();
			WorkflowMap.setInvoker(invoker);
		}
	}

}
