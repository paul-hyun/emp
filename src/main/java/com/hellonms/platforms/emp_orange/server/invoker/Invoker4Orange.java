/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.server.invoker;

import java.awt.Color;
import java.io.File;
import java.util.Date;

import com.hellonms.platforms.emp_core.EmpConfig4Server;
import com.hellonms.platforms.emp_core.server.invoker.InvokerIf;
import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.server.workflow.WorkflowMap;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_core.share.operation.OPERATION_CODE;
import com.hellonms.platforms.emp_onion.server.invoker.Invoker4Onion;
import com.hellonms.platforms.emp_onion.share.model.STATISTICS_TYPE;
import com.hellonms.platforms.emp_onion.share.model.environment.preference.Model4Preference;
import com.hellonms.platforms.emp_onion.share.model.help.about.Model4About;
import com.hellonms.platforms.emp_orange.server.workflow.fault.event.Worker4EventIf;
import com.hellonms.platforms.emp_orange.server.workflow.network.link.Worker4NetworkLinkIf;
import com.hellonms.platforms.emp_orange.server.workflow.network.ne.Worker4NMSIf;
import com.hellonms.platforms.emp_orange.server.workflow.network.ne.Worker4NeIf;
import com.hellonms.platforms.emp_orange.server.workflow.network.ne_group.Worker4NeGroupIf;
import com.hellonms.platforms.emp_orange.server.workflow.network.ne_info.Worker4NeInfoIf;
import com.hellonms.platforms.emp_orange.server.workflow.security.operation_log.Worker4OperationLogIf;
import com.hellonms.platforms.emp_orange.server.workflow.security.user.Worker4UserIf;
import com.hellonms.platforms.emp_orange.server.workflow.security.user_group.Worker4UserGroupIf;
import com.hellonms.platforms.emp_orange.server.workflow.security.user_session.Worker4UserSessionIf;
import com.hellonms.platforms.emp_orange.share.EMP_ORANGE_DEFINE;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_EVENT;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO_FIELD;
import com.hellonms.platforms.emp_orange.share.model.fault.event.GEN_TYPE;
import com.hellonms.platforms.emp_orange.share.model.fault.event.Model4Alarm;
import com.hellonms.platforms.emp_orange.share.model.fault.event.Model4AlarmStatistics;
import com.hellonms.platforms.emp_orange.share.model.fault.event.Model4AlarmStatistics.ITEM;
import com.hellonms.platforms.emp_orange.share.model.fault.event.Model4AlarmSummary;
import com.hellonms.platforms.emp_orange.share.model.fault.event.Model4Event;
import com.hellonms.platforms.emp_orange.share.model.fault.event.SEVERITY;
import com.hellonms.platforms.emp_orange.share.model.network.link.Model4NetworkLink;
import com.hellonms.platforms.emp_orange.share.model.network.ne.Model4Ne;
import com.hellonms.platforms.emp_orange.share.model.network.ne.Model4ResourceNMS;
import com.hellonms.platforms.emp_orange.share.model.network.ne_group.Model4NeGroup;
import com.hellonms.platforms.emp_orange.share.model.network.ne_group.TreeNode4NeGroup;
import com.hellonms.platforms.emp_orange.share.model.network.ne_info.Model4NeInfoIf;
import com.hellonms.platforms.emp_orange.share.model.network.ne_info.Model4NeStatisticsIf;
import com.hellonms.platforms.emp_orange.share.model.network.ne_info.Model4NeThresholdIf;
import com.hellonms.platforms.emp_orange.share.model.network.ne_info.NE_INFO_INDEX;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.Model4NeSessionDiscoveryFilterIf;
import com.hellonms.platforms.emp_orange.share.model.security.operation_log.Model4OperationLog;
import com.hellonms.platforms.emp_orange.share.model.security.user.Model4User;
import com.hellonms.platforms.emp_orange.share.model.security.user_group.Model4UserGroup;
import com.hellonms.platforms.emp_orange.share.model.security.user_session.Model4UserSession;
import com.hellonms.platforms.emp_util.cache.UtilCache;

/**
 * <p>
 * Orange Invoker 구현
 * </p>
 * 
 * @since 1.6
 * @create 2015. 3. 10.
 * @modified 2015. 3. 10.
 * @author cchyun
 * 
 */
public class Invoker4Orange extends Invoker4Onion implements Invoker4OrangeIf {

	@Override
	public Class<? extends InvokerIf> getDefine_class() {
		return Invoker4OrangeIf.class;
	}

	@Override
	public void initialize(EmpContext context) throws EmpException {
		super.initialize(context);
	}

	@Override
	public void dispose(EmpContext context) throws EmpException {
		super.dispose(context);
	}

	@Override
	protected Model4About newAbout(EmpContext context) {
		Model4About about = new Model4About();
		about.setManufacturer(EMP_ORANGE_DEFINE.MANUFACTURER);
		about.setOui(EMP_ORANGE_DEFINE.OUI);
		about.setProduct_class(EMP_ORANGE_DEFINE.PRODUCT_CLASS);
		about.setVersion(EMP_ORANGE_DEFINE.VERSION);
		about.setBuild_id(EMP_ORANGE_DEFINE.BUILD_ID);
		about.setProduct_name(EMP_ORANGE_DEFINE.PRODUCT_NAME);
		about.setAbout(EMP_ORANGE_DEFINE.ABOUT);
		about.setDevelop_mode(EmpConfig4Server.getBoolean("develop_mode"));
		return about;
	}

	@Override
	public byte[] queryEmp_model(EmpContext context) throws EmpException {
		assertAuthorized(context);

		return EMP_MODEL.current().getBytes();
	}

	@Override
	public byte[] updateEmp_model(EmpContext context, final byte[] emp_model_data) throws EmpException {
		assertAuthorized(context);

		EMP_MODEL.init_current(emp_model_data);
		byte[] buf = EMP_MODEL.current().getBytes();
		UtilCache.removeAll();
		return buf;
	}

	@Override
	public Model4Preference[] queryListPreference(EmpContext context, String function_group, String function, String preference) throws EmpException {
		assertAuthorized(context);

		return super.queryListPreference(context, function_group, function, preference);
	}

	@Override
	public Model4Preference[] updateListPreference(EmpContext context, Model4Preference[] preferences) throws EmpException {
		assertAuthorized(context);

		return super.updateListPreference(context, preferences);
	}

	@Override
	public File backupDatabaseByUser(EmpContext context) throws EmpException {
		assertAuthorized(context);

		return super.backupDatabaseByUser(context);
	}

	@Override
	public Model4UserSession queryUserSession(EmpContext context, String user_session_key) throws EmpException {
		Worker4UserSessionIf worker4UserSession = (Worker4UserSessionIf) WorkflowMap.getWorker(Worker4UserSessionIf.class);
		Model4UserSession user_session = worker4UserSession.queryUserSession(context, user_session_key);

		return user_session;
	}

	@Override
	public String login(EmpContext context, String user_account, String password, String user_ip) throws EmpException {
		Worker4UserSessionIf worker4UserSession = (Worker4UserSessionIf) WorkflowMap.getWorker(Worker4UserSessionIf.class);
		Model4UserSession user_session = worker4UserSession.login(context, user_account, password, user_ip);

		return user_session.getUser_session_key();
	}

	@Override
	public String logout(EmpContext context, String user_session_key) throws EmpException {
		Worker4UserSessionIf worker4UserSession = (Worker4UserSessionIf) WorkflowMap.getWorker(Worker4UserSessionIf.class);
		Model4UserSession user_session = worker4UserSession.logout(context, user_session_key);

		return user_session.getUser_session_key();
	}

	@Override
	public Model4NeGroup createNeGroup(EmpContext context, Model4NeGroup ne_group) throws EmpException {
		assertAuthorized(context);

		Worker4NeGroupIf worker4NeGroup = (Worker4NeGroupIf) WorkflowMap.getWorker(Worker4NeGroupIf.class);
		return worker4NeGroup.createNeGroup(context, ne_group);
	}

	@Override
	public Model4NeGroup queryNeGroup(EmpContext context, int ne_group_id) throws EmpException {
		assertAuthorized(context);

		Worker4NeGroupIf worker4NeGroup = (Worker4NeGroupIf) WorkflowMap.getWorker(Worker4NeGroupIf.class);
		return worker4NeGroup.queryNeGroup(context, ne_group_id);
	}

	@Override
	public Model4NeGroup[] queryListNeGroup(EmpContext context, int startNo, int count) throws EmpException {
		assertAuthorized(context);

		Worker4NeGroupIf worker4NeGroup = (Worker4NeGroupIf) WorkflowMap.getWorker(Worker4NeGroupIf.class);
		return worker4NeGroup.queryListNeGroup(context, startNo, count);
	}

	@Override
	public Model4NeGroup[] queryListNeGroup(EmpContext context, int ne_group_id, int startNo, int count) throws EmpException {
		assertAuthorized(context);

		Worker4NeGroupIf worker4NeGroup = (Worker4NeGroupIf) WorkflowMap.getWorker(Worker4NeGroupIf.class);
		return worker4NeGroup.queryListNeGroup(context, ne_group_id, startNo, count);
	}

	@Override
	public Model4NeGroup[] queryAllNeGroup(EmpContext context) throws EmpException {
		assertAuthorized(context);

		Worker4NeGroupIf worker4NeGroup = (Worker4NeGroupIf) WorkflowMap.getWorker(Worker4NeGroupIf.class);
		return worker4NeGroup.queryAllNeGroup(context);
	}

	@Override
	public TreeNode4NeGroup queryTreeNeGroup(EmpContext context) throws EmpException {
		assertAuthorized(context);

		Worker4NeGroupIf worker4NeGroup = (Worker4NeGroupIf) WorkflowMap.getWorker(Worker4NeGroupIf.class);
		return worker4NeGroup.queryTreeNeGroup(context);
	}

	@Override
	public int queryCountNeGroup(EmpContext context) throws EmpException {
		assertAuthorized(context);

		Worker4NeGroupIf worker4NeGroup = (Worker4NeGroupIf) WorkflowMap.getWorker(Worker4NeGroupIf.class);
		return worker4NeGroup.queryCountNeGroup(context);
	}

	@Override
	public int queryCountNeGroup(EmpContext context, int ne_group_id) throws EmpException {
		assertAuthorized(context);

		Worker4NeGroupIf worker4NeGroup = (Worker4NeGroupIf) WorkflowMap.getWorker(Worker4NeGroupIf.class);
		return worker4NeGroup.queryCountNeGroup(context, ne_group_id);
	}

	@Override
	public Model4NeGroup updateNeGroup(EmpContext context, Model4NeGroup ne_group) throws EmpException {
		assertAuthorized(context);

		Worker4NeGroupIf worker4NeGroup = (Worker4NeGroupIf) WorkflowMap.getWorker(Worker4NeGroupIf.class);
		return worker4NeGroup.updateNeGroup(context, ne_group);
	}

	@Override
	public Model4NeGroup updateNeGroupMapLocation(EmpContext context, Model4NeGroup ne_group) throws EmpException {
		assertAuthorized(context);

		Worker4NeGroupIf worker4NeGroup = (Worker4NeGroupIf) WorkflowMap.getWorker(Worker4NeGroupIf.class);
		return worker4NeGroup.updateNeGroupMapLocation(context, ne_group);
	}

	@Override
	public Model4NeGroup deleteNeGroup(EmpContext context, int ne_group_id) throws EmpException {
		assertAuthorized(context);

		Worker4NeGroupIf worker4NeGroup = (Worker4NeGroupIf) WorkflowMap.getWorker(Worker4NeGroupIf.class);
		return worker4NeGroup.deleteNeGroup(context, ne_group_id);
	}

	@Override
	public Model4Ne newInstanceNe(EmpContext context) throws EmpException {
		assertAuthorized(context);

		Worker4NeIf worker4Ne = (Worker4NeIf) WorkflowMap.getWorker(Worker4NeIf.class);
		return worker4Ne.newInstanceNe(context);
	}

	@Override
	public Model4Ne createNe(EmpContext context, Model4Ne ne) throws EmpException {
		assertAuthorized(context);

		Worker4NeIf worker4Ne = (Worker4NeIf) WorkflowMap.getWorker(Worker4NeIf.class);
		return worker4Ne.createNe(context, ne);
	}

	@Override
	public Model4Ne queryNe(EmpContext context, int ne_id) throws EmpException {
		assertAuthorized(context);

		Worker4NeIf worker4Ne = (Worker4NeIf) WorkflowMap.getWorker(Worker4NeIf.class);
		return worker4Ne.queryNe(context, ne_id);
	}

	@Override
	public Model4Ne queryNe(EmpContext context, int ne_group_id, String ne_name) throws EmpException {
		assertAuthorized(context);

		Worker4NeIf worker4Ne = (Worker4NeIf) WorkflowMap.getWorker(Worker4NeIf.class);
		return worker4Ne.queryNe(context, ne_group_id, ne_name);
	}

	@Override
	public Model4Ne[] queryListNe(EmpContext context, int startNo, int count) throws EmpException {
		assertAuthorized(context);

		Worker4NeIf worker4Ne = (Worker4NeIf) WorkflowMap.getWorker(Worker4NeIf.class);
		return worker4Ne.queryListNe(context, startNo, count);
	}

	@Override
	public Model4Ne[] queryListNe(EmpContext context, int ne_group_id, int startNo, int count) throws EmpException {
		assertAuthorized(context);

		Worker4NeIf worker4Ne = (Worker4NeIf) WorkflowMap.getWorker(Worker4NeIf.class);
		return worker4Ne.queryListNe(context, ne_group_id, startNo, count);
	}

	@Override
	public Model4NeSessionDiscoveryFilterIf[] newInstanceListDiscoveryFilter(EmpContext context) throws EmpException {
		assertAuthorized(context);

		Worker4NeIf worker4Ne = (Worker4NeIf) WorkflowMap.getWorker(Worker4NeIf.class);
		return worker4Ne.newInstanceListDiscoveryFilter(context);
	}

	@Override
	public Model4Ne[] discoveryListNe(EmpContext context, Model4NeSessionDiscoveryFilterIf[] ne_session_discovery_filters) throws EmpException {
		assertAuthorized(context);

		Worker4NeIf worker4Ne = (Worker4NeIf) WorkflowMap.getWorker(Worker4NeIf.class);
		return worker4Ne.discoveryListNe(context, ne_session_discovery_filters);
	}

	@Override
	public int queryCountNe(EmpContext context) throws EmpException {
		assertAuthorized(context);

		Worker4NeIf worker4Ne = (Worker4NeIf) WorkflowMap.getWorker(Worker4NeIf.class);
		return worker4Ne.queryCountNe(context);
	}

	@Override
	public int queryCountNe(EmpContext context, int ne_group_id) throws EmpException {
		assertAuthorized(context);

		Worker4NeIf worker4Ne = (Worker4NeIf) WorkflowMap.getWorker(Worker4NeIf.class);
		return worker4Ne.queryCountNe(context, ne_group_id);
	}

	@Override
	public Model4Ne updateNe(EmpContext context, Model4Ne ne) throws EmpException {
		assertAuthorized(context);

		Worker4NeIf worker4Ne = (Worker4NeIf) WorkflowMap.getWorker(Worker4NeIf.class);
		return worker4Ne.updateNe(context, ne);
	}

	@Override
	public Model4Ne updateNeMapLocation(EmpContext context, Model4Ne ne) throws EmpException {
		assertAuthorized(context);

		Worker4NeIf worker4Ne = (Worker4NeIf) WorkflowMap.getWorker(Worker4NeIf.class);
		return worker4Ne.updateNeMapLocation(context, ne);
	}

	@Override
	public Model4Ne deleteNe(EmpContext context, int ne_id) throws EmpException {
		assertAuthorized(context);

		Worker4NeIf worker4Ne = (Worker4NeIf) WorkflowMap.getWorker(Worker4NeIf.class);
		return worker4Ne.deleteNe(context, ne_id);
	}

	@Override
	public Model4NetworkLink createNetworkLink(EmpContext context, Model4NetworkLink network_link) throws EmpException {
		assertAuthorized(context);

		Worker4NetworkLinkIf worker4NetworkLink = (Worker4NetworkLinkIf) WorkflowMap.getWorker(Worker4NetworkLinkIf.class);
		return worker4NetworkLink.createNetworkLink(context, network_link);
	}

	@Override
	public Model4NetworkLink[] queryListNetworkLink(EmpContext context, int startNo, int count) throws EmpException {
		assertAuthorized(context);

		Worker4NetworkLinkIf worker4NetworkLink = (Worker4NetworkLinkIf) WorkflowMap.getWorker(Worker4NetworkLinkIf.class);
		return worker4NetworkLink.queryListNetworkLink(context, startNo, count);
	}

	@Override
	public Model4NetworkLink deleteNetworkLink(EmpContext context, int network_link_id) throws EmpException {
		assertAuthorized(context);

		Worker4NetworkLinkIf worker4NetworkLink = (Worker4NetworkLinkIf) WorkflowMap.getWorker(Worker4NetworkLinkIf.class);
		return worker4NetworkLink.deleteNetworkLink(context, network_link_id);
	}

	@Override
	public Model4ResourceNMS[] queryListResourceNMS(EmpContext context) throws EmpException {
		assertAuthorized(context);

		Worker4NMSIf worker4NMS = (Worker4NMSIf) WorkflowMap.getWorker(Worker4NMSIf.class);
		return worker4NMS.queryListResourceNMS(context);
	}

	@Override
	public Model4NeInfoIf[] queryAllNeInfo(EmpContext context, EMP_MODEL_NE_INFO ne_info_def) throws EmpException {
		assertAuthorized(context);

		Worker4NeInfoIf worker4NeInfo = (Worker4NeInfoIf) WorkflowMap.getWorker(Worker4NeInfoIf.class);
		return worker4NeInfo.queryAllNeInfo(context, ne_info_def);
	}

	@Override
	public Model4NeInfoIf[] queryListNeInfo(EmpContext context, int ne_id, EMP_MODEL_NE_INFO ne_info_def) throws EmpException {
		assertAuthorized(context);

		Worker4NeInfoIf worker4NeInfo = (Worker4NeInfoIf) WorkflowMap.getWorker(Worker4NeInfoIf.class);
		return worker4NeInfo.queryListNeInfo(context, ne_id, ne_info_def);
	}

	@Override
	public Model4NeInfoIf[] readListNeInfo(EmpContext context, int ne_id, EMP_MODEL_NE_INFO ne_info_def) throws EmpException {
		assertAuthorized(context);

		Worker4NeInfoIf worker4NeInfo = (Worker4NeInfoIf) WorkflowMap.getWorker(Worker4NeInfoIf.class);
		return worker4NeInfo.readListNeInfo(context, ne_id, ne_info_def);
	}

	@Override
	public Model4NeInfoIf updateNeInfo(EmpContext context, Model4NeInfoIf ne_info) throws EmpException {
		assertAuthorized(context);

		Worker4NeInfoIf worker4NeInfo = (Worker4NeInfoIf) WorkflowMap.getWorker(Worker4NeInfoIf.class);
		return worker4NeInfo.updateNeInfo(context, ne_info);
	}

	@Override
	public NE_INFO_INDEX[] queryListNeStatisticsIndex(EmpContext context, int ne_id, EMP_MODEL_NE_INFO ne_info_def, STATISTICS_TYPE statistics_type, Date fromTime, Date toTime) throws EmpException {
		assertAuthorized(context);

		Worker4NeInfoIf worker4NeInfo = (Worker4NeInfoIf) WorkflowMap.getWorker(Worker4NeInfoIf.class);
		return worker4NeInfo.queryListNeStatisticsIndex(context, ne_id, ne_info_def, statistics_type, fromTime, toTime);
	}

	@Override
	public Model4NeStatisticsIf[] queryListNeStatistics(EmpContext context, int ne_id, EMP_MODEL_NE_INFO ne_info_def, STATISTICS_TYPE statistics_type, Date fromTime, Date toTime) throws EmpException {
		assertAuthorized(context);

		Worker4NeInfoIf worker4NeInfo = (Worker4NeInfoIf) WorkflowMap.getWorker(Worker4NeInfoIf.class);
		return worker4NeInfo.queryListNeStatistics(context, ne_id, ne_info_def, statistics_type, fromTime, toTime);
	}

	@Override
	public Model4NeStatisticsIf[] queryListNeStatistics(EmpContext context, int ne_id, EMP_MODEL_NE_INFO ne_info_def, int ne_info_index, STATISTICS_TYPE statistics_type, Date fromTime, Date toTime) throws EmpException {
		assertAuthorized(context);

		Worker4NeInfoIf worker4NeInfo = (Worker4NeInfoIf) WorkflowMap.getWorker(Worker4NeInfoIf.class);
		return worker4NeInfo.queryListNeStatistics(context, ne_id, ne_info_def, ne_info_index, statistics_type, fromTime, toTime);
	}

	@Override
	public Model4NeThresholdIf queryNeThreshold(EmpContext context, int ne_id, EMP_MODEL_NE_INFO ne_info_def) throws EmpException {
		assertAuthorized(context);

		Worker4NeInfoIf worker4NeInfo = (Worker4NeInfoIf) WorkflowMap.getWorker(Worker4NeInfoIf.class);
		return worker4NeInfo.queryNeThreshold(context, ne_id, ne_info_def);
	}

	@Override
	public Model4NeThresholdIf updateNeThreshold(EmpContext context, Model4NeThresholdIf ne_threshold, EMP_MODEL_NE_INFO_FIELD ne_info_field_def) throws EmpException {
		assertAuthorized(context);

		Worker4NeInfoIf worker4NeInfo = (Worker4NeInfoIf) WorkflowMap.getWorker(Worker4NeInfoIf.class);
		return worker4NeInfo.updateNeThreshold(context, ne_threshold, ne_info_field_def);
	}

	@Override
	public Model4NeThresholdIf[] copyListNeThreshold(EmpContext context, int ne_id_source, EMP_MODEL_NE_INFO ne_info_def, int[] ne_id_targets) throws EmpException {
		assertAuthorized(context);

		Worker4NeInfoIf worker4NeInfo = (Worker4NeInfoIf) WorkflowMap.getWorker(Worker4NeInfoIf.class);
		return worker4NeInfo.copyListNeThreshold(context, ne_id_source, ne_info_def, ne_id_targets);
	}

	@Override
	public Model4Event queryEvent(EmpContext context, long event_id) throws EmpException {
		assertAuthorized(context);

		Worker4EventIf worker4Event = (Worker4EventIf) WorkflowMap.getWorker(Worker4EventIf.class);
		return worker4Event.queryEvent(context, event_id);
	}

	@Override
	public Model4Event[] queryListEventByNeGroup(EmpContext context, int ne_group_id, EMP_MODEL_EVENT event_def, SEVERITY severity, Date fromTime, Date toTime, int startNo, int count) throws EmpException {
		assertAuthorized(context);

		Worker4EventIf worker4Event = (Worker4EventIf) WorkflowMap.getWorker(Worker4EventIf.class);
		return worker4Event.queryListEventByNeGroup(context, ne_group_id, event_def, severity, fromTime, toTime, startNo, count);
	}

	@Override
	public Model4Event[] queryListEventByNe(EmpContext context, int ne_id, EMP_MODEL_EVENT event_def, SEVERITY severity, Date fromTime, Date toTime, int startNo, int count) throws EmpException {
		assertAuthorized(context);

		Worker4EventIf worker4Event = (Worker4EventIf) WorkflowMap.getWorker(Worker4EventIf.class);
		return worker4Event.queryListEventByNe(context, ne_id, event_def, severity, fromTime, toTime, startNo, count);
	}

	@Override
	public Model4Event[] queryListEventByConsole(EmpContext context, int startNo, int count) throws EmpException {
		assertAuthorized(context);

		Worker4EventIf worker4Event = (Worker4EventIf) WorkflowMap.getWorker(Worker4EventIf.class);
		return worker4Event.queryListEventByConsole(context, startNo, count);
	}

	@Override
	public int queryCountEventByNeGroup(EmpContext context, int ne_group_id, EMP_MODEL_EVENT event_def, SEVERITY severity, Date fromTime, Date toTime) throws EmpException {
		assertAuthorized(context);

		Worker4EventIf worker4Event = (Worker4EventIf) WorkflowMap.getWorker(Worker4EventIf.class);
		return worker4Event.queryCountEventByNeGroup(context, ne_group_id, event_def, severity, fromTime, toTime);
	}

	@Override
	public int queryCountEventByNe(EmpContext context, int ne_id, EMP_MODEL_EVENT event_def, SEVERITY severity, Date fromTime, Date toTime) throws EmpException {
		assertAuthorized(context);

		Worker4EventIf worker4Event = (Worker4EventIf) WorkflowMap.getWorker(Worker4EventIf.class);
		return worker4Event.queryCountEventByNe(context, ne_id, event_def, severity, fromTime, toTime);
	}

	@Override
	public Model4Alarm queryAlarm(EmpContext context, long gen_first_event_id) throws EmpException {
		assertAuthorized(context);

		Worker4EventIf worker4Event = (Worker4EventIf) WorkflowMap.getWorker(Worker4EventIf.class);
		return worker4Event.queryAlarm(context, gen_first_event_id);
	}

	@Override
	public Model4Alarm[] queryListAlarmByNeGroup(EmpContext context, int ne_group_id, EMP_MODEL_EVENT event_def, SEVERITY severity, Date fromTime, Date toTime, int startNo, int count) throws EmpException {
		assertAuthorized(context);

		Worker4EventIf worker4Event = (Worker4EventIf) WorkflowMap.getWorker(Worker4EventIf.class);
		return worker4Event.queryListAlarmByNeGroup(context, ne_group_id, event_def, severity, fromTime, toTime, startNo, count);
	}

	@Override
	public Model4Alarm[] queryListAlarmByConsole(EmpContext context, int startNo, int count) throws EmpException {
		assertAuthorized(context);

		Worker4EventIf worker4Event = (Worker4EventIf) WorkflowMap.getWorker(Worker4EventIf.class);
		return worker4Event.queryListAlarmByConsole(context, startNo, count);
	}

	@Override
	public Model4Alarm[] queryListAlarmByNe(EmpContext context, int ne_id, EMP_MODEL_EVENT event_def, SEVERITY severity, Date fromTime, Date toTime, int startNo, int count) throws EmpException {
		assertAuthorized(context);

		Worker4EventIf worker4Event = (Worker4EventIf) WorkflowMap.getWorker(Worker4EventIf.class);
		return worker4Event.queryListAlarmByNe(context, ne_id, event_def, severity, fromTime, toTime, startNo, count);
	}

	@Override
	public int queryCountAlarmByNeGroup(EmpContext context, int ne_group_id, EMP_MODEL_EVENT event_def, SEVERITY severity, Date fromTime, Date toTime) throws EmpException {
		assertAuthorized(context);

		Worker4EventIf worker4Event = (Worker4EventIf) WorkflowMap.getWorker(Worker4EventIf.class);
		return worker4Event.queryCountAlarmByNeGroup(context, ne_group_id, event_def, severity, fromTime, toTime);
	}

	@Override
	public int queryCountAlarmByNe(EmpContext context, int ne_id, EMP_MODEL_EVENT event_def, SEVERITY severity, Date fromTime, Date toTime) throws EmpException {
		assertAuthorized(context);

		Worker4EventIf worker4Event = (Worker4EventIf) WorkflowMap.getWorker(Worker4EventIf.class);
		return worker4Event.queryCountAlarmByNe(context, ne_id, event_def, severity, fromTime, toTime);
	}

	@Override
	public Model4Alarm queryAlarmActive(EmpContext context, long gen_first_event_id) throws EmpException {
		assertAuthorized(context);

		Worker4EventIf worker4Event = (Worker4EventIf) WorkflowMap.getWorker(Worker4EventIf.class);
		return worker4Event.queryAlarmActive(context, gen_first_event_id);
	}

	@Override
	public Model4Alarm[] queryListAlarmActiveByNeGroup(EmpContext context, int ne_group_id, EMP_MODEL_EVENT event_def, SEVERITY severity, int startNo, int count) throws EmpException {
		assertAuthorized(context);

		Worker4EventIf worker4Event = (Worker4EventIf) WorkflowMap.getWorker(Worker4EventIf.class);
		return worker4Event.queryListAlarmActiveByNeGroup(context, ne_group_id, event_def, severity, startNo, count);
	}

	@Override
	public Model4Alarm[] queryListAlarmActiveByNe(EmpContext context, int ne_id, EMP_MODEL_NE_INFO ne_info_def, EMP_MODEL_EVENT event_def, SEVERITY severity, int startNo, int count) throws EmpException {
		assertAuthorized(context);

		Worker4EventIf worker4Event = (Worker4EventIf) WorkflowMap.getWorker(Worker4EventIf.class);
		return worker4Event.queryListAlarmActiveByNe(context, ne_id, ne_info_def, event_def, severity, startNo, count);
	}

	@Override
	public int queryCountAlarmActiveByNeGroup(EmpContext context, int ne_group_id, EMP_MODEL_EVENT event_def, SEVERITY severity) throws EmpException {
		assertAuthorized(context);

		Worker4EventIf worker4Event = (Worker4EventIf) WorkflowMap.getWorker(Worker4EventIf.class);
		return worker4Event.queryCountAlarmActiveByNeGroup(context, ne_group_id, event_def, severity);
	}

	@Override
	public int queryCountAlarmActiveByNe(EmpContext context, int ne_id, EMP_MODEL_EVENT event_def, SEVERITY severity) throws EmpException {
		assertAuthorized(context);

		Worker4EventIf worker4Event = (Worker4EventIf) WorkflowMap.getWorker(Worker4EventIf.class);
		return worker4Event.queryCountAlarmActiveByNe(context, ne_id, event_def, severity);
	}

	@Override
	public Model4Alarm ackAlarm(EmpContext context, long gen_first_event_id) throws EmpException {
		assertAuthorized(context);

		Worker4EventIf worker4Event = (Worker4EventIf) WorkflowMap.getWorker(Worker4EventIf.class);
		return worker4Event.ackAlarm(context, gen_first_event_id);
	}

	@Override
	public Model4Alarm clearAlarmByGen_first_event_id(EmpContext context, long gen_first_event_id) throws EmpException {
		assertAuthorized(context);

		Worker4EventIf worker4Event = (Worker4EventIf) WorkflowMap.getWorker(Worker4EventIf.class);
		return worker4Event.clearAlarmByGen_first_event_id(context, gen_first_event_id, GEN_TYPE.MANUAL, "Alarm clear by " + context.getUser_account(), true);
	}

	@Override
	public Model4AlarmSummary[] queryListAlarmSummary(EmpContext context) throws EmpException {
		assertAuthorized(context);

		Worker4EventIf worker4Event = (Worker4EventIf) WorkflowMap.getWorker(Worker4EventIf.class);
		return worker4Event.queryListAlarmSummary(context);
	}

	@Override
	public Model4AlarmStatistics[] queryListAlarmStatisticsByNeGroup(EmpContext context, int ne_group_id, ITEM item, STATISTICS_TYPE statistics_type, Date fromTime, Date toTime) throws EmpException {
		assertAuthorized(context);

		Worker4EventIf worker4Event = (Worker4EventIf) WorkflowMap.getWorker(Worker4EventIf.class);
		return worker4Event.queryListAlarmStatisticsByNeGroup(context, ne_group_id, item, statistics_type, fromTime, toTime);
	}

	@Override
	public Model4AlarmStatistics[] queryListAlarmStatisticsByNe(EmpContext context, int ne_id, ITEM item, STATISTICS_TYPE statistics_type, Date fromTime, Date toTime) throws EmpException {
		assertAuthorized(context);

		Worker4EventIf worker4Event = (Worker4EventIf) WorkflowMap.getWorker(Worker4EventIf.class);
		return worker4Event.queryListAlarmStatisticsByNe(context, ne_id, item, statistics_type, fromTime, toTime);
	}

	@Override
	public Model4UserGroup queryUserGroup(EmpContext context, int user_group_id) throws EmpException {
		assertAuthorized(context);

		Worker4UserGroupIf worker4UserGroup = (Worker4UserGroupIf) WorkflowMap.getWorker(Worker4UserGroupIf.class);
		return worker4UserGroup.queryUserGroup(context, user_group_id);
	}

	@Override
	public Model4UserGroup[] queryListUserGroup(EmpContext context, int startNo, int count) throws EmpException {
		assertAuthorized(context);

		Worker4UserGroupIf worker4UserGroup = (Worker4UserGroupIf) WorkflowMap.getWorker(Worker4UserGroupIf.class);
		return worker4UserGroup.queryListUserGroup(context, startNo, count);
	}

	@Override
	public int queryCountUserGroup(EmpContext context) throws EmpException {
		assertAuthorized(context);

		Worker4UserGroupIf worker4UserGroup = (Worker4UserGroupIf) WorkflowMap.getWorker(Worker4UserGroupIf.class);
		return worker4UserGroup.queryCountUserGroup(context);
	}

	@Override
	public Model4User createUser(EmpContext context, Model4User user) throws EmpException {
		assertAuthorized(context);

		Worker4UserIf worker4User = (Worker4UserIf) WorkflowMap.getWorker(Worker4UserIf.class);
		return worker4User.createUser(context, user);
	}

	@Override
	public Model4User queryUser(EmpContext context, int user_id) throws EmpException {
		assertAuthorized(context);

		Worker4UserIf worker4User = (Worker4UserIf) WorkflowMap.getWorker(Worker4UserIf.class);
		return worker4User.queryUser(context, user_id);
	}

	@Override
	public Model4User[] queryListUser(EmpContext context, int startNo, int count) throws EmpException {
		assertAuthorized(context);

		Worker4UserIf worker4User = (Worker4UserIf) WorkflowMap.getWorker(Worker4UserIf.class);
		return worker4User.queryListUser(context, startNo, count);
	}

	@Override
	public int queryCountUser(EmpContext context) throws EmpException {
		assertAuthorized(context);

		Worker4UserIf worker4User = (Worker4UserIf) WorkflowMap.getWorker(Worker4UserIf.class);
		return worker4User.queryCountUser(context);
	}

	@Override
	public Model4User updateUser(EmpContext context, Model4User user) throws EmpException {
		assertAuthorized(context);

		Worker4UserIf worker4User = (Worker4UserIf) WorkflowMap.getWorker(Worker4UserIf.class);
		return worker4User.updateUser(context, user);
	}

	@Override
	public Model4User deleteUser(EmpContext context, int user_id) throws EmpException {
		assertAuthorized(context);

		Worker4UserIf worker4User = (Worker4UserIf) WorkflowMap.getWorker(Worker4UserIf.class);
		return worker4User.deleteUser(context, user_id);
	}

	@Override
	public OPERATION_CODE[] getListOperation_code(EmpContext context) throws EmpException {
		Worker4OperationLogIf worker4OperationLog = (Worker4OperationLogIf) WorkflowMap.getWorker(Worker4OperationLogIf.class);
		return worker4OperationLog.getListOperation_code(context);
	}

	@Override
	public Model4OperationLog createOperationLog(EmpContext context, Model4OperationLog operation_log) throws EmpException {
		Worker4OperationLogIf worker4OperationLog = (Worker4OperationLogIf) WorkflowMap.getWorker(Worker4OperationLogIf.class);
		return worker4OperationLog.createOperationLog(context, operation_log);
	}

	@Override
	public Model4OperationLog[] queryListOperationLogByNeGroup(EmpContext context, int ne_group_id, String function_group, String function, String operation, Boolean result, Integer user_session_id, String user_account, Date fromTime, Date toTime, int startNo, int count) throws EmpException {
		assertAuthorized(context);

		Worker4OperationLogIf worker4OperationLog = (Worker4OperationLogIf) WorkflowMap.getWorker(Worker4OperationLogIf.class);
		return worker4OperationLog.queryListOperationLogByNeGroup(context, ne_group_id, function_group, function, operation, result, user_session_id, user_account, fromTime, toTime, startNo, count);
	}

	@Override
	public Model4OperationLog[] queryListOperationLogByNe(EmpContext context, int ne_id, String function_group, String function, String operation, Boolean result, Integer user_session_id, String user_account, Date fromTime, Date toTime, int startNo, int count) throws EmpException {
		assertAuthorized(context);

		Worker4OperationLogIf worker4OperationLog = (Worker4OperationLogIf) WorkflowMap.getWorker(Worker4OperationLogIf.class);
		return worker4OperationLog.queryListOperationLogByNe(context, ne_id, function_group, function, operation, result, user_session_id, user_account, fromTime, toTime, startNo, count);
	}

	@Override
	public int queryCountOperationLogByNeGroup(EmpContext context, int ne_group_id, String function_group, String function, String operation, Boolean result, Integer user_session_id, String user_account, Date fromTime, Date toTime) throws EmpException {
		assertAuthorized(context);

		Worker4OperationLogIf worker4OperationLog = (Worker4OperationLogIf) WorkflowMap.getWorker(Worker4OperationLogIf.class);
		return worker4OperationLog.queryCountOperationLogByNeGroup(context, ne_group_id, function_group, function, operation, result, user_session_id, user_account, fromTime, toTime);
	}

	@Override
	public int queryCountOperationLogByNe(EmpContext context, int ne_id, String function_group, String function, String operation, Boolean result, Integer user_session_id, String user_account, Date fromTime, Date toTime) throws EmpException {
		assertAuthorized(context);

		Worker4OperationLogIf worker4OperationLog = (Worker4OperationLogIf) WorkflowMap.getWorker(Worker4OperationLogIf.class);
		return worker4OperationLog.queryCountOperationLogByNe(context, ne_id, function_group, function, operation, result, user_session_id, user_account, fromTime, toTime);
	}

	@Override
	public byte[] queryImage(EmpContext context, String path, int width, int height, SEVERITY severity) throws EmpException {
		switch (severity) {
		case COMMUNICATION_FAIL:
			return queryImage(context, path, width, height, new Color(128, 128, 128, 128));
		case CRITICAL:
			return queryImage(context, path, width, height, new Color(255, 0, 0, 96));
		case MAJOR:
			return queryImage(context, path, width, height, new Color(255, 128, 0, 96));
		case MINOR:
			return queryImage(context, path, width, height, new Color(217, 196, 49, 96));
		case CLEAR:
		default:
			return queryImage(context, path, width, height);
		}
	}

	@Override
	public long queryCurrUpdate_seq_network(EmpContext context) throws EmpException {
		Worker4NeIf worker4Ne = (Worker4NeIf) WorkflowMap.getWorker(Worker4NeIf.class);
		return worker4Ne.queryCurrUpdate_seq_network(context);
	}

	@Override
	public long queryCurrUpdate_seq_fault(EmpContext context) throws EmpException {
		Worker4EventIf worker4Event = (Worker4EventIf) WorkflowMap.getWorker(Worker4EventIf.class);
		return worker4Event.queryCurrUpdate_seq_fault(context);
	}

	protected void assertAuthorized(EmpContext context) throws EmpException {
		// if (context.getUser_session_id() == 0) {
		// throw new EmpException(ERROR_CODE_ORANGE.USER_NOTLOGIN);
		// }
	}

	@Override
	public void truncate(EmpContext context, boolean ne, boolean ne_info, boolean fault, boolean operation_log) throws EmpException {
		if (operation_log) {
			Worker4OperationLogIf worker4OperationLog = (Worker4OperationLogIf) WorkflowMap.getWorker(Worker4OperationLogIf.class);
			worker4OperationLog.truncate(context);
		}
		if (fault) {
			Worker4EventIf Worker4Event = (Worker4EventIf) WorkflowMap.getWorker(Worker4EventIf.class);
			Worker4Event.truncate(context);
		}
		if (ne_info) {
			Worker4NeInfoIf Worker4NeInfo = (Worker4NeInfoIf) WorkflowMap.getWorker(Worker4NeInfoIf.class);
			Worker4NeInfo.truncate(context);
		}
		if (ne) {
			Worker4NetworkLinkIf worker4NetworkLink = (Worker4NetworkLinkIf) WorkflowMap.getWorker(Worker4NetworkLinkIf.class);
			worker4NetworkLink.truncate(context);
			Worker4NeIf worker4Ne = (Worker4NeIf) WorkflowMap.getWorker(Worker4NeIf.class);
			worker4Ne.truncate(context);
		}
	}

}
