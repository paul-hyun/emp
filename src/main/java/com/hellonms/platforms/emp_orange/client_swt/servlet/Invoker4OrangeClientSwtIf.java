/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.client_swt.servlet;

import java.io.File;
import java.util.Date;
import java.util.Map;

import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_core.share.language.LANGUAGE;
import com.hellonms.platforms.emp_core.share.model.ModelIf;
import com.hellonms.platforms.emp_core.share.operation.OPERATION_CODE;
import com.hellonms.platforms.emp_onion.share.model.STATISTICS_TYPE;
import com.hellonms.platforms.emp_onion.share.model.TablePageConfig;
import com.hellonms.platforms.emp_onion.share.model.environment.preference.Model4Preference;
import com.hellonms.platforms.emp_onion.share.model.environment.preference.PREFERENCE_CODE;
import com.hellonms.platforms.emp_onion.share.model.help.about.Model4About;
import com.hellonms.platforms.emp_orange.client_swt.model_display.ModelDisplay4Ne;
import com.hellonms.platforms.emp_orange.client_swt.model_display.ModelDisplay4NeInfo;
import com.hellonms.platforms.emp_orange.client_swt.model_display.ModelDisplay4NeStatistics;
import com.hellonms.platforms.emp_orange.client_swt.model_display.ModelDisplay4Network;
import com.hellonms.platforms.emp_orange.client_swt.model_display.ModelDisplay4User;
import com.hellonms.platforms.emp_orange.share.model.fault.event.Model4Alarm;
import com.hellonms.platforms.emp_orange.share.model.fault.event.Model4AlarmStatistics;
import com.hellonms.platforms.emp_orange.share.model.fault.event.Model4AlarmStatistics.ITEM;
import com.hellonms.platforms.emp_orange.share.model.fault.event.Model4Event;
import com.hellonms.platforms.emp_orange.share.model.fault.event.SEVERITY;
import com.hellonms.platforms.emp_orange.share.model.network.link.Model4NetworkLink;
import com.hellonms.platforms.emp_orange.share.model.network.ne.Model4Ne;
import com.hellonms.platforms.emp_orange.share.model.network.ne.Model4ResourceNMS;
import com.hellonms.platforms.emp_orange.share.model.network.ne_group.Model4NeGroup;
import com.hellonms.platforms.emp_orange.share.model.network.ne_group.TreeNode4NeGroup;
import com.hellonms.platforms.emp_orange.share.model.network.ne_info.Model4NeInfoIf;
import com.hellonms.platforms.emp_orange.share.model.network.ne_info.Model4NeThresholdIf;
import com.hellonms.platforms.emp_orange.share.model.network.ne_info.NE_INFO_INDEX;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.Model4NeSessionDiscoveryFilterIf;
import com.hellonms.platforms.emp_orange.share.model.security.operation_log.Model4OperationLog;
import com.hellonms.platforms.emp_orange.share.model.security.user.Model4User;
import com.hellonms.platforms.emp_orange.share.model.security.user_session.Model4UserSession;

/**
 * <p>
 * Invoker for client swt
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 20.
 * @modified 2015. 5. 20.
 * @author cchyun
 *
 */
public interface Invoker4OrangeClientSwtIf {

	/**
	 * @param request
	 * @return
	 */
	public long testSession(Invoker4OrangeClientSwtReqeust request) throws EmpException;

	/**
	 * @param context
	 * @return
	 * @throws EmpException
	 */
	public Model4About getAbout(Invoker4OrangeClientSwtReqeust request) throws EmpException;

	/**
	 * @param request
	 * @param language
	 * @return
	 * @throws EmpException
	 */
	public Map<String, String> queryLanguage(Invoker4OrangeClientSwtReqeust request, LANGUAGE language) throws EmpException;

	/**
	 * @param request
	 * @param path
	 * @param filename
	 * @param filedata
	 * @throws EmpException
	 */
	public void createImage(Invoker4OrangeClientSwtReqeust request, String path, String filename, byte[] filedata) throws EmpException;

	/**
	 * <p>
	 * Query image in server.
	 * </p>
	 * 
	 * @param request
	 * @param path
	 * @param width
	 * @param height
	 * @param severity
	 * @return
	 * @throws EmpException
	 */
	public byte[] queryImage(Invoker4OrangeClientSwtReqeust request, String path, int width, int height, SEVERITY severity) throws EmpException;

	/**
	 * <p>
	 * Query image in server.
	 * </p>
	 * 
	 * @param request
	 * @param path
	 * @return
	 * @throws EmpException
	 */
	public byte[] queryImage(Invoker4OrangeClientSwtReqeust request, String path) throws EmpException;

	/**
	 * <p>
	 * Query list image_path in server.
	 * </p>
	 * 
	 * @param request
	 * @param path
	 * @param extensions
	 * @return
	 * @throws EmpException
	 */
	public String[] queryListImagePath(Invoker4OrangeClientSwtReqeust request, String path, String[] extensions) throws EmpException;

	/**
	 * @param context
	 * @param path
	 * @return
	 * @throws EmpException
	 */
	public byte[] querySound(Invoker4OrangeClientSwtReqeust request, String path) throws EmpException;

	/**
	 * @param request
	 * @return
	 * @throws EmpException
	 */
	public byte[] queryEmp_model(Invoker4OrangeClientSwtReqeust request) throws EmpException;

	/**
	 * @param request
	 * @param emp_model_data
	 * @return
	 * @throws EmpException
	 */
	public byte[] updateEmp_model(Invoker4OrangeClientSwtReqeust request, byte[] emp_model_data) throws EmpException;

	/**
	 * @param request
	 * @param ne
	 * @param ne_info
	 * @param fault
	 * @param operation_log
	 * @throws EmpException
	 */
	public void truncate(Invoker4OrangeClientSwtReqeust request, boolean ne, boolean ne_info, boolean fault, boolean operation_log) throws EmpException;

	/**
	 * <p>
	 * </p>
	 *
	 * @param request
	 * @param preference_code
	 * @return
	 * @throws EmpException
	 */
	public Model4Preference queryPreference(Invoker4OrangeClientSwtReqeust request, PREFERENCE_CODE preference_code) throws EmpException;

	/**
	 * @param context
	 * @param function_group
	 * @param function
	 * @param preference
	 * @return
	 * @throws EmpException
	 */
	public Model4Preference[] queryListPreference(Invoker4OrangeClientSwtReqeust request, String function_group, String function, String preference) throws EmpException;

	/**
	 * @param request
	 * @param preferences
	 * @return
	 * @throws EmpException
	 */
	public Model4Preference[] updateListPreference(Invoker4OrangeClientSwtReqeust request, Model4Preference[] preferences) throws EmpException;

	/**
	 * @param request
	 * @return
	 * @throws EmpException
	 */
	public File backupDatabaseByUser(Invoker4OrangeClientSwtReqeust request) throws EmpException;

	/**
	 * <p>
	 * 사용자 Session 조회
	 * </p>
	 *
	 * @param request
	 * @return
	 * @throws EmpException
	 */
	public Model4UserSession queryUserSession(Invoker4OrangeClientSwtReqeust request) throws EmpException;

	/**
	 * <p>
	 * 로그인
	 * </p>
	 *
	 * @param request
	 * @param user_account
	 * @param password
	 * @return
	 * @throws EmpException
	 */
	public String login(Invoker4OrangeClientSwtReqeust request, String user_account, String password) throws EmpException;

	/**
	 * <p>
	 * 로그아웃
	 * </p>
	 *
	 * @param request
	 * @throws EmpException
	 */
	public void logout(Invoker4OrangeClientSwtReqeust request) throws EmpException;

	/**
	 * @param request
	 * @return
	 * @throws EmpException
	 */
	public OPERATION_CODE[] getListOperationCode(Invoker4OrangeClientSwtReqeust request) throws EmpException;

	/**
	 * <p>
	 * 네트워크 정보
	 * </p>
	 *
	 * @param request
	 * @param update_seq_network
	 * @param update_seq_fault
	 * @return
	 * @throws EmpException
	 */
	public ModelDisplay4Network queryNetwork(Invoker4OrangeClientSwtReqeust request, long update_seq_network, long update_seq_fault) throws EmpException;

	/**
	 * @param request
	 * @param model4NeGroup
	 * @return
	 * @throws EmpException
	 */
	public Model4NeGroup createNeGroup(Invoker4OrangeClientSwtReqeust request, Model4NeGroup model4NeGroup) throws EmpException;

	/**
	 * @param request
	 * @return
	 * @throws EmpException
	 */
	public Model4NeGroup[] queryListNeGroup(Invoker4OrangeClientSwtReqeust request) throws EmpException;

	/**
	 * @param request
	 * @param ne_group_id
	 * @return
	 * @throws EmpException
	 */
	public Model4NeGroup[] queryListNeGroup(Invoker4OrangeClientSwtReqeust request, int ne_group_id) throws EmpException;

	/**
	 * @param request
	 * @return
	 * @throws EmpException
	 */
	public TreeNode4NeGroup queryTreeNeGroup(Invoker4OrangeClientSwtReqeust request) throws EmpException;

	/**
	 * @param request
	 * @param ne_group
	 * @return
	 * @throws EmpException
	 */
	public Model4NeGroup updateNeGroup(Invoker4OrangeClientSwtReqeust request, Model4NeGroup ne_group) throws EmpException;

	/**
	 * @param request
	 * @param model4NeGroup
	 * @return
	 * @throws EmpException
	 */
	public Model4NeGroup deleteNeGroup(Invoker4OrangeClientSwtReqeust request, int ne_group_id) throws EmpException;

	/**
	 * @param request
	 * @return
	 * @throws EmpException
	 */
	public Model4NeSessionDiscoveryFilterIf[] newInstanceListDiscoveryFilter(Invoker4OrangeClientSwtReqeust request) throws EmpException;

	/**
	 * @param request
	 * @param ne_session_discovery_filters
	 * @return
	 * @throws EmpException
	 */
	public Model4Ne[] discoveryListNe(Invoker4OrangeClientSwtReqeust request, Model4NeSessionDiscoveryFilterIf[] ne_session_discovery_filters) throws EmpException;

	/**
	 * @param request
	 * @param model4Nes
	 * @throws EmpException
	 */
	public Model4Ne[] createListNe(Invoker4OrangeClientSwtReqeust request, Model4Ne[] model4Nes) throws EmpException;

	/**
	 * @param request
	 * @return
	 * @throws EmpException
	 */
	public Model4Ne newInstanceNe(Invoker4OrangeClientSwtReqeust request) throws EmpException;

	/**
	 * @param request
	 * @param model4Ne
	 * @return
	 * @throws EmpException
	 */
	public Model4Ne createNe(Invoker4OrangeClientSwtReqeust request, Model4Ne model4Ne) throws EmpException;

	/**
	 * @param request
	 * @param ne_id
	 * @return
	 * @throws EmpException
	 */
	public Model4Ne queryNe(Invoker4OrangeClientSwtReqeust request, int ne_id) throws EmpException;

	/**
	 * @param request
	 * @return
	 * @throws EmpException
	 */
	public Model4Ne[] queryListNe(Invoker4OrangeClientSwtReqeust request) throws EmpException;

	/**
	 * @param request
	 * @param ne_group_id
	 * @return
	 * @throws EmpException
	 */
	public Model4Ne[] queryListNe(Invoker4OrangeClientSwtReqeust request, int ne_group_id) throws EmpException;

	/**
	 * @param request
	 * @param ne
	 * @return
	 * @throws EmpException
	 */
	public Model4Ne updateNe(Invoker4OrangeClientSwtReqeust request, Model4Ne ne) throws EmpException;

	/**
	 * @param request
	 * @param ne_id
	 * @return
	 * @throws EmpException
	 */
	public Model4Ne deleteNe(Invoker4OrangeClientSwtReqeust request, int ne_id) throws EmpException;

	/**
	 * @param request
	 * @param models
	 * @return
	 * @throws EmpException
	 */
	public ModelIf[] updateMapLocation(Invoker4OrangeClientSwtReqeust request, ModelIf[] models) throws EmpException;

	/**
	 * @param request
	 * @param network_link
	 * @return
	 * @throws EmpException
	 */
	public Model4NetworkLink createNetworkLink(Invoker4OrangeClientSwtReqeust request, Model4NetworkLink network_link) throws EmpException;

	/**
	 * @param request
	 * @param network_link_id
	 * @return
	 * @throws EmpException
	 */
	public Model4NetworkLink deleteNetworkLink(Invoker4OrangeClientSwtReqeust request, int network_link_id) throws EmpException;

	/**
	 * @param request
	 * @param ne_id
	 * @param ne_statistics_index_map
	 * @param auto_refresh
	 * @return
	 * @throws EmpException
	 */
	public ModelDisplay4Ne queryNetworkViewNe(Invoker4OrangeClientSwtReqeust request, int ne_id, Map<Integer, Integer> ne_statistics_index_map, boolean auto_refresh) throws EmpException;

	/**
	 * @param invoker
	 * @param context
	 * @param ne_code
	 * @return
	 * @throws EmpException
	 */
	public String[] queryOrderNetworkViewNe(Invoker4OrangeClientSwtReqeust request, int ne_code) throws EmpException;

	/**
	 * @param request
	 * @param ne_code
	 * @param order
	 * @throws EmpException
	 */
	public void updateOrderNetworkViewNe(Invoker4OrangeClientSwtReqeust request, int ne_code, String[] order) throws EmpException;

	/**
	 * @param request
	 * @param auto_refresh
	 * @return
	 * @throws EmpException
	 */
	public Model4ResourceNMS[] queryListResourceNMS(Invoker4OrangeClientSwtReqeust request, boolean auto_refresh) throws EmpException;

	/**
	 * @param request
	 * @param ne_id
	 * @param ne_info_code
	 * @param isQuery
	 * @return
	 * @throws EmpException
	 */
	public ModelDisplay4NeInfo queryListNeInfo(Invoker4OrangeClientSwtReqeust request, int ne_id, int ne_info_code, boolean isQuery) throws EmpException;

	/**
	 * @param request
	 * @param model4NeInfo
	 * @param count
	 * @return
	 * @throws EmpException
	 */
	public ModelDisplay4NeInfo updateNeInfo(Invoker4OrangeClientSwtReqeust request, Model4NeInfoIf model4NeInfo) throws EmpException;

	/**
	 * @param request
	 * @param ne_id
	 * @param ne_info_code
	 * @param ne_info_index
	 * @param statistics_type
	 * @param fromDate
	 * @param toDate
	 * @return
	 * @throws EmpException
	 */
	public ModelDisplay4NeStatistics queryListNeStatistics(Invoker4OrangeClientSwtReqeust request, int ne_id, int ne_info_code, NE_INFO_INDEX ne_info_index, STATISTICS_TYPE statistics_type, Date fromDate, Date toDate) throws EmpException;

	/**
	 * @param request
	 * @param model4Ne
	 * @param ne_info_code
	 * @return
	 * @throws EmpException
	 */
	public Model4NeThresholdIf queryNeThreshold(Invoker4OrangeClientSwtReqeust request, int ne_id, int ne_info_code) throws EmpException;

	/**
	 * @param request
	 * @param ne_threshold
	 * @param ne_info_field_name
	 * @return
	 * @throws EmpException
	 */
	public Model4NeThresholdIf updateNeThreshold(Invoker4OrangeClientSwtReqeust request, Model4NeThresholdIf ne_threshold, String ne_info_field_name) throws EmpException;

	/**
	 * <p>
	 * 임계치 복사
	 * </p>
	 *
	 * @param request
	 * @param ne_id
	 * @param ne_info_code
	 * @param ne_id_targets
	 * @return
	 * @throws EmpException
	 */
	public Model4NeThresholdIf[] copyListNeThreshold(Invoker4OrangeClientSwtReqeust request, int ne_id, int ne_info_code, int[] ne_id_targets) throws EmpException;

	/**
	 * @param request
	 * @param ne_group_id
	 * @param event_code
	 * @param severity
	 * @param startNo
	 * @param count
	 * @return
	 * @throws EmpException
	 */
	public TablePageConfig<Model4Alarm> queryListAlarmActiveByNeGroup(Invoker4OrangeClientSwtReqeust request, int ne_group_id, SEVERITY severity, int startNo, int count) throws EmpException;

	/**
	 * @param request
	 * @param ne_id
	 * @param event_code
	 * @param severity
	 * @param startNo
	 * @param count
	 * @return
	 * @throws EmpException
	 */
	public TablePageConfig<Model4Alarm> queryListAlarmActiveByNe(Invoker4OrangeClientSwtReqeust request, int ne_id, SEVERITY severity, int startNo, int count) throws EmpException;

	/**
	 * @param request
	 * @param gen_first_event_id
	 * @return
	 * @throws EmpException
	 */
	public Model4Alarm ackAlarm(Invoker4OrangeClientSwtReqeust request, long gen_first_event_id) throws EmpException;

	/**
	 * @param request
	 * @param gen_first_event_id
	 * @return
	 * @throws EmpException
	 */
	public Model4Alarm clearAlarmByGen_first_event_id(Invoker4OrangeClientSwtReqeust request, long gen_first_event_id) throws EmpException;

	/**
	 * @param request
	 * @param ne_group_id
	 * @param event_code
	 * @param severity
	 * @param fromTime
	 * @param toTime
	 * @param startNo
	 * @param count
	 * @return
	 * @throws EmpException
	 */
	public TablePageConfig<Model4Alarm> queryListAlarmHistoryByNeGroup(Invoker4OrangeClientSwtReqeust request, int ne_group_id, int event_code, SEVERITY severity, Date fromTime, Date toTime, int startNo, int count) throws EmpException;

	/**
	 * @param request
	 * @param ne_id
	 * @param event_code
	 * @param severity
	 * @param fromTime
	 * @param toTime
	 * @param startNo
	 * @param count
	 * @return
	 * @throws EmpException
	 */
	public TablePageConfig<Model4Alarm> queryListAlarmHistoryByNe(Invoker4OrangeClientSwtReqeust request, int ne_id, int event_code, SEVERITY severity, Date fromTime, Date toTime, int startNo, int count) throws EmpException;

	/**
	 * @param request
	 * @param fromTime
	 * @param toTime
	 * @param ne_group_id
	 * @param item
	 * @param statistics_type
	 * @return
	 * @throws EmpException
	 */
	public Model4AlarmStatistics[] queryListAlarmStatisticsByNeGroup(Invoker4OrangeClientSwtReqeust request, Date fromTime, Date toTime, int ne_group_id, ITEM item, STATISTICS_TYPE statistics_type) throws EmpException;

	/**
	 * @param request
	 * @param fromTime
	 * @param toTime
	 * @param ne_id
	 * @param item
	 * @param statistics_type
	 * @return
	 * @throws EmpException
	 */
	public Model4AlarmStatistics[] queryListAlarmStatisticsByNe(Invoker4OrangeClientSwtReqeust request, Date fromTime, Date toTime, int ne_id, ITEM item, STATISTICS_TYPE statistics_type) throws EmpException;

	/**
	 * @param request
	 * @param ne_group_id
	 * @param event_code
	 * @param severity
	 * @param fromTime
	 * @param toTime
	 * @param startNo
	 * @param count
	 * @return
	 * @throws EmpException
	 */
	public TablePageConfig<Model4Event> queryListEventByNeGroup(Invoker4OrangeClientSwtReqeust request, int ne_group_id, int event_code, SEVERITY severity, Date fromTime, Date toTime, int startNo, int count) throws EmpException;

	/**
	 * @param request
	 * @param ne_id
	 * @param event_code
	 * @param severity
	 * @param fromTime
	 * @param toTime
	 * @param startNo
	 * @param count
	 * @return
	 * @throws EmpException
	 */
	public TablePageConfig<Model4Event> queryListEventByNe(Invoker4OrangeClientSwtReqeust request, int ne_id, int event_code, SEVERITY severity, Date fromTime, Date toTime, int startNo, int count) throws EmpException;

	/**
	 * <p>
	 * Create user information.
	 * </p>
	 * 
	 * @param request
	 * @param model4User
	 * @param startNo
	 * @param count
	 * @return
	 * @throws EmpException
	 */
	public ModelDisplay4User createUser(Invoker4OrangeClientSwtReqeust request, Model4User model4User, int startNo, int count) throws EmpException;

	/**
	 * <p>
	 * Query list of user information.
	 * </p>
	 * 
	 * @param request
	 * @param startNo
	 * @param count
	 * @return
	 * @throws EmpException
	 */
	public ModelDisplay4User queryListUser(Invoker4OrangeClientSwtReqeust request, int startNo, int count) throws EmpException;

	/**
	 * <p>
	 * Update user information.
	 * </p>
	 * 
	 * @param request
	 * @param model4User
	 * @param startNo
	 * @param count
	 * @return
	 * @throws EmpException
	 */
	public ModelDisplay4User updateUser(Invoker4OrangeClientSwtReqeust request, Model4User model4User, int startNo, int count) throws EmpException;

	/**
	 * <p>
	 * Delete user information.
	 * </p>
	 * 
	 * @param request
	 * @param user_id
	 * @param startNo
	 * @param count
	 * @return
	 * @throws EmpException
	 */
	public ModelDisplay4User deleteUser(Invoker4OrangeClientSwtReqeust request, int user_id, int startNo, int count) throws EmpException;

	/**
	 * @param request
	 * @param ne_group_id
	 * @param service
	 * @param function
	 * @param operation
	 * @param result
	 * @param sessionId
	 * @param userId
	 * @param fromTime
	 * @param toTime
	 * @param startNo
	 * @param count
	 * @return
	 * @throws EmpException
	 */
	public TablePageConfig<Model4OperationLog> queryListOperationLogByNeGroup(Invoker4OrangeClientSwtReqeust request, int ne_group_id, String service, String function, String operation, Boolean result, Integer user_session_id, String userId, Date fromTime, Date toTime, int startNo, int count) throws EmpException;

	/**
	 * @param request
	 * @param ne_id
	 * @param service
	 * @param function
	 * @param operation
	 * @param result
	 * @param sessionId
	 * @param userId
	 * @param fromTime
	 * @param toTime
	 * @param startNo
	 * @param count
	 * @return
	 * @throws EmpException
	 */
	public TablePageConfig<Model4OperationLog> queryListOperationLogByNe(Invoker4OrangeClientSwtReqeust request, int ne_id, String service, String function, String operation, Boolean result, Integer user_session_id, String userId, Date fromTime, Date toTime, int startNo, int count) throws EmpException;

}
