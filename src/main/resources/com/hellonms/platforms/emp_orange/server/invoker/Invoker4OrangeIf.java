/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.server.invoker;

import java.util.Date;

import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_core.share.operation.OPERATION_CODE;
import com.hellonms.platforms.emp_onion.server.invoker.Invoker4OnionIf;
import com.hellonms.platforms.emp_onion.share.model.STATISTICS_TYPE;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_EVENT;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO_FIELD;
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

/**
 * <p>
 * Orange Invoker 정의
 * </p>
 * 
 * @since 1.6
 * @create 2015. 3. 10.
 * @modified 2015. 3. 10.
 * @author cchyun
 * 
 */
public interface Invoker4OrangeIf extends Invoker4OnionIf {

	/**
	 * <p>
	 * emp_model 파일 조회
	 * </p>
	 *
	 * @param context
	 * @return
	 * @throws EmpException
	 */
	public byte[] queryEmp_model(EmpContext context) throws EmpException;

	/**
	 * <p>
	 * emp_model 파일 수정
	 * </p>
	 *
	 * @param context
	 * @param emp_model_data
	 * @return
	 * @throws EmpException
	 */
	public byte[] updateEmp_model(EmpContext context, byte[] emp_model_data) throws EmpException;

	/**
	 * <p>
	 * 로그인 세션을 조회한다. (로그인 되지 않은 경우는 null)
	 * </p>
	 * 
	 * @param context
	 * @return
	 * @throws EmpException
	 */
	public Model4UserSession queryUserSession(EmpContext context, String user_session_key) throws EmpException;

	/**
	 * <p>
	 * 로그인을 수행한다.
	 * </p>
	 * 
	 * @param context
	 * @param user_account
	 * @param password
	 * @return 세션 key
	 * @throws EmpException
	 */
	public String login(EmpContext context, String user_account, String password, String user_ip) throws EmpException;

	/**
	 * <p>
	 * 로그아웃을 수행한다.
	 * </p>
	 * 
	 * @param context
	 * @param user_session_key
	 * @return
	 * @throws EmpException
	 */
	public String logout(EmpContext context, String user_session_key) throws EmpException;

	/**
	 * <p>
	 * NE그룹 생성
	 * </p>
	 * 
	 * @param context
	 * @param ne_group
	 * @return
	 * @throws EmpException
	 */
	public Model4NeGroup createNeGroup(EmpContext context, Model4NeGroup ne_group) throws EmpException;

	/**
	 * <p>
	 * NE그룹 조회
	 * </p>
	 * 
	 * @param context
	 * @param ne_group_id
	 * @return
	 * @throws EmpException
	 */
	public Model4NeGroup queryNeGroup(EmpContext context, int ne_group_id) throws EmpException;

	/**
	 * <p>
	 * NE그룹 목록조회
	 * </p>
	 * 
	 * @param context
	 * @param startNo
	 * @param count
	 * @return
	 * @throws EmpException
	 */
	public Model4NeGroup[] queryListNeGroup(EmpContext context, int startNo, int count) throws EmpException;

	/**
	 * <p>
	 * NE그룹 목록조회
	 * </p>
	 * 
	 * @param context
	 * @param ne_group_id
	 * @param startNo
	 * @param count
	 * @return
	 * @throws EmpException
	 */
	public Model4NeGroup[] queryListNeGroup(EmpContext context, int ne_group_id, int startNo, int count) throws EmpException;

	/**
	 * <p>
	 * 권한에 관계 없이 모든 NE그룹 조회
	 * </p>
	 *
	 * @param context
	 * @return
	 * @throws EmpException
	 */
	public Model4NeGroup[] queryAllNeGroup(EmpContext context) throws EmpException;

	/**
	 * <p>
	 * NE그룹 Tree 형태로 조회
	 * </p>
	 * 
	 * @param context
	 * @return
	 * @throws EmpException
	 */
	public TreeNode4NeGroup queryTreeNeGroup(EmpContext context) throws EmpException;

	/**
	 * <p>
	 * NE그룹 개수 조회
	 * </p>
	 * 
	 * @param context
	 * @return
	 * @throws EmpException
	 */
	public int queryCountNeGroup(EmpContext context) throws EmpException;

	/**
	 * <p>
	 * NE그룹 개수 조회
	 * </p>
	 * 
	 * @param context
	 * @param ne_group_id
	 * @return
	 * @throws EmpException
	 */
	public int queryCountNeGroup(EmpContext context, int ne_group_id) throws EmpException;

	/**
	 * <p>
	 * NE그룹 수정
	 * </p>
	 * 
	 * @param context
	 * @param ne_group
	 * @return
	 * @throws EmpException
	 */
	public Model4NeGroup updateNeGroup(EmpContext context, Model4NeGroup ne_group) throws EmpException;

	/**
	 * <p>
	 * NE그룹 위치 저장
	 * </p>
	 *
	 * @param context
	 * @param ne_group
	 * @return
	 * @throws EmpException
	 */
	public Model4NeGroup updateNeGroupMapLocation(EmpContext context, Model4NeGroup ne_group) throws EmpException;

	/**
	 * <p>
	 * NE그룹 삭제
	 * </p>
	 * 
	 * @param context
	 * @param ne_group_id
	 * @return
	 * @throws EmpException
	 */
	public Model4NeGroup deleteNeGroup(EmpContext context, int ne_group_id) throws EmpException;

	/**
	 * <p>
	 * NE 기본값을 조회한다.
	 * </p>
	 *
	 * @param context
	 * @return
	 * @throws EmpException
	 */
	public Model4Ne newInstanceNe(EmpContext context) throws EmpException;

	/**
	 * <p>
	 * NE 생성
	 * </p>
	 * 
	 * @param context
	 * @param ne
	 * @return
	 * @throws EmpException
	 */
	public Model4Ne createNe(EmpContext context, Model4Ne ne) throws EmpException;

	/**
	 * <p>
	 * NE 조회
	 * </p>
	 * 
	 * @param context
	 * @param ne_id
	 * @return
	 * @throws EmpException
	 */
	public Model4Ne queryNe(EmpContext context, int ne_id) throws EmpException;

	/**
	 * <p>
	 * NE 조회
	 * </p>
	 * 
	 * @param context
	 * @param ne_group_id
	 * @param ne_name
	 * @throws EmpException
	 */
	public Model4Ne queryNe(EmpContext context, int ne_group_id, String ne_name) throws EmpException;

	/**
	 * <p>
	 * NE 목록조회
	 * </p>
	 * 
	 * @param context
	 * @param startNo
	 * @param count
	 * @return
	 * @throws EmpException
	 */
	public Model4Ne[] queryListNe(EmpContext context, int startNo, int count) throws EmpException;

	/**
	 * <p>
	 * NE 목록조회
	 * </p>
	 * 
	 * @param context
	 * @param ne_group_id
	 * @param startNo
	 * @param count
	 * @return
	 * @throws EmpException
	 */
	public Model4Ne[] queryListNe(EmpContext context, int ne_group_id, int startNo, int count) throws EmpException;

	/**
	 * <p>
	 * NE를 검색할 검색 정보의 기본 값을 조회한다.
	 * </p>
	 *
	 * @param context
	 * @return
	 * @throws EmpException
	 */
	public Model4NeSessionDiscoveryFilterIf[] newInstanceListDiscoveryFilter(EmpContext context) throws EmpException;

	/**
	 * <p>
	 * NE 검색
	 * </p>
	 *
	 * @param context
	 * @param ne_session_discovery_requests
	 * @return
	 * @throws EmpException
	 */
	public Model4Ne[] discoveryListNe(EmpContext context, Model4NeSessionDiscoveryFilterIf[] ne_session_discovery_filters) throws EmpException;

	/**
	 * <p>
	 * NE 개수 조회
	 * </p>
	 * 
	 * @param context
	 * @return
	 * @throws EmpException
	 */
	public int queryCountNe(EmpContext context) throws EmpException;

	/**
	 * <p>
	 * NE 개수 조회
	 * </p>
	 * 
	 * @param context
	 * @param ne_group_id
	 * @return
	 * @throws EmpException
	 */
	public int queryCountNe(EmpContext context, int ne_group_id) throws EmpException;

	/**
	 * <p>
	 * NE 수정
	 * </p>
	 * 
	 * @param context
	 * @param ne
	 * @return
	 * @throws EmpException
	 */
	public Model4Ne updateNe(EmpContext context, Model4Ne ne) throws EmpException;

	/**
	 * <p>
	 * NE 위치 저장
	 * </p>
	 *
	 * @param context
	 * @param ne
	 * @return
	 * @throws EmpException
	 */
	public Model4Ne updateNeMapLocation(EmpContext context, Model4Ne ne) throws EmpException;

	/**
	 * <p>
	 * NE 삭제
	 * </p>
	 * 
	 * @param context
	 * @param ne_id
	 * @return
	 * @throws EmpException
	 */
	public Model4Ne deleteNe(EmpContext context, int ne_id) throws EmpException;

	/**
	 * <p>
	 * Network Link 생성
	 * </p>
	 *
	 * @param context
	 * @param network_link
	 * @return
	 * @throws EmpException
	 */
	public Model4NetworkLink createNetworkLink(EmpContext context, Model4NetworkLink network_link) throws EmpException;

	/**
	 * <p>
	 * Network Link 조회
	 * </p>
	 *
	 * @param context
	 * @param startNo
	 * @param count
	 * @return
	 * @throws EmpException
	 */
	public Model4NetworkLink[] queryListNetworkLink(EmpContext context, int startNo, int count) throws EmpException;

	/**
	 * <p>
	 * Network Link 삭제
	 * </p>
	 *
	 * @param context
	 * @param network_link_id
	 * @return
	 * @throws EmpException
	 */
	public Model4NetworkLink deleteNetworkLink(EmpContext context, int network_link_id) throws EmpException;

	/**
	 * <p>
	 * NMS Resource 조회
	 * </p>
	 *
	 * @param context
	 * @return
	 */
	public Model4ResourceNMS[] queryListResourceNMS(EmpContext context) throws EmpException;

	/**
	 * @param context
	 * @param ne_info_def
	 * @return
	 * @throws EmpException
	 */
	public Model4NeInfoIf[] queryAllNeInfo(EmpContext context, EMP_MODEL_NE_INFO ne_info_def) throws EmpException;

	/**
	 * <p>
	 * NE 정보 목록조회
	 * </p>
	 *
	 * @param context
	 * @param ne_id
	 * @param ne_info_def
	 * @param startNo
	 * @param count
	 * @return
	 * @throws EmpException
	 */
	public Model4NeInfoIf[] queryListNeInfo(EmpContext context, int ne_id, EMP_MODEL_NE_INFO ne_info_def) throws EmpException;

	/**
	 * <p>
	 * NE 정보 조회
	 * </p>
	 * 
	 * @param context
	 * @param ne_id
	 * @param ne_info_def
	 * @return
	 * @throws EmpException
	 */
	public Model4NeInfoIf[] readListNeInfo(EmpContext context, int ne_id, EMP_MODEL_NE_INFO ne_info_def) throws EmpException;

	/**
	 * <p>
	 * NE 정보 수정
	 * </p>
	 * 
	 * @param context
	 * @param ne_info
	 * @return
	 * @throws EmpException
	 */
	public Model4NeInfoIf updateNeInfo(EmpContext context, Model4NeInfoIf ne_info) throws EmpException;

	/**
	 * <p>
	 * 통계 키 조회
	 * </p>
	 *
	 * @param context
	 * @param ne_id
	 * @param ne_info_def
	 * @param statistics_type
	 * @param fromTime
	 * @param toTime
	 * @return
	 * @throws EmpException
	 */
	public NE_INFO_INDEX[] queryListNeStatisticsIndex(EmpContext context, int ne_id, EMP_MODEL_NE_INFO ne_info_def, STATISTICS_TYPE statistics_type, Date fromTime, Date toTime) throws EmpException;

	/**
	 * <p>
	 * 통계 조회
	 * </p>
	 *
	 * @param context
	 * @param ne_id
	 * @param ne_info_def
	 * @param statistics_type
	 * @param fromTime
	 * @param toTime
	 * @return
	 * @throws EmpException
	 */
	public Model4NeStatisticsIf[] queryListNeStatistics(EmpContext context, int ne_id, EMP_MODEL_NE_INFO ne_info_def, STATISTICS_TYPE statistics_type, Date fromTime, Date toTime) throws EmpException;

	/**
	 * <p>
	 * 임계치 조회
	 * </p>
	 *
	 * @param context
	 * @param neId
	 * @param ne_info_def
	 * @return
	 * @throws EmpException
	 */
	public Model4NeThresholdIf queryNeThreshold(EmpContext context, int ne_id, EMP_MODEL_NE_INFO ne_info_def) throws EmpException;

	/**
	 * <p>
	 * 임계치 수정
	 * </p>
	 *
	 * @param context
	 * @param ne_threshold
	 * @param ne_info_field_def
	 * @return
	 * @throws EmpException
	 */
	public Model4NeThresholdIf updateNeThreshold(EmpContext context, Model4NeThresholdIf ne_threshold, EMP_MODEL_NE_INFO_FIELD ne_info_field_def) throws EmpException;

	/**
	 * <p>
	 * 임계치 복사
	 * </p>
	 *
	 * @param context
	 * @param ne_id_source
	 * @param ne_info_def
	 * @param ne_id_targets
	 * @return
	 * @throws EmpException
	 */
	public Model4NeThresholdIf[] copyListNeThreshold(EmpContext context, int ne_id_source, EMP_MODEL_NE_INFO ne_info_def, int[] ne_id_targets) throws EmpException;

	/**
	 * <p>
	 * 통계 조회
	 * </p>
	 *
	 * @param context
	 * @param ne_id
	 * @param ne_info_def
	 * @param ne_info_index
	 * @param statistics_type
	 * @param fromTime
	 * @param toTime
	 * @return
	 * @throws EmpException
	 */
	public Model4NeStatisticsIf[] queryListNeStatistics(EmpContext context, int ne_id, EMP_MODEL_NE_INFO ne_info_def, int ne_info_index, STATISTICS_TYPE statistics_type, Date fromTime, Date toTime) throws EmpException;

	/**
	 * <p>
	 * 특정 이벤트를 조회한다.
	 * </p>
	 *
	 * @param context
	 * @param event_id
	 * @return
	 * @throws EmpException
	 */
	public Model4Event queryEvent(EmpContext context, long event_id) throws EmpException;

	/**
	 * <p>
	 * NE그룹의 이벤트이력을 조회한다.
	 * </p>
	 *
	 * @param context
	 * @param ne_group_id
	 * @param event_def
	 * @param severity
	 * @param fromTime
	 * @param toTime
	 * @param startNo
	 * @param count
	 * @return
	 * @throws EmpException
	 */
	public Model4Event[] queryListEventByNeGroup(EmpContext context, int ne_group_id, EMP_MODEL_EVENT event_def, SEVERITY severity, Date fromTime, Date toTime, int startNo, int count) throws EmpException;

	/**
	 * <p>
	 * NE의 이벤트이력을 조회한다.
	 * </p>
	 *
	 * @param context
	 * @param ne_id
	 * @param event_def
	 * @param severity
	 * @param fromTime
	 * @param toTime
	 * @param startNo
	 * @param count
	 * @return
	 * @throws EmpException
	 */
	public Model4Event[] queryListEventByNe(EmpContext context, int ne_id, EMP_MODEL_EVENT event_def, SEVERITY severity, Date fromTime, Date toTime, int startNo, int count) throws EmpException;

	/**
	 * <p>
	 * 이벤트이력을 조회한다.
	 * </p>
	 *
	 * @param context
	 * @param startNo
	 * @param count
	 * @return
	 * @throws EmpException
	 */
	public Model4Event[] queryListEventByConsole(EmpContext context, int startNo, int count) throws EmpException;

	/**
	 * <p>
	 * NE그룹의 이벤트이력을 조회한다.
	 * </p>
	 *
	 * @param context
	 * @param ne_group_id
	 * @param event_def
	 * @param severity
	 * @param fromTime
	 * @param toTime
	 * @return
	 * @throws EmpException
	 */
	public int queryCountEventByNeGroup(EmpContext context, int ne_group_id, EMP_MODEL_EVENT event_def, SEVERITY severity, Date fromTime, Date toTime) throws EmpException;

	/**
	 * <p>
	 * NE의 이벤트이력을 조회한다.
	 * </p>
	 *
	 * @param context
	 * @param ne_id
	 * @param event_def
	 * @param severity
	 * @param fromTime
	 * @param toTime
	 * @return
	 * @throws EmpException
	 */
	public int queryCountEventByNe(EmpContext context, int ne_id, EMP_MODEL_EVENT event_def, SEVERITY severity, Date fromTime, Date toTime) throws EmpException;

	/**
	 * <p>
	 * 알람을 조회한다.
	 * </p>
	 * 
	 * @param context
	 * @param gen_first_event_id
	 * @return
	 * @throws EmpException
	 */
	public Model4Alarm queryAlarm(EmpContext context, long gen_first_event_id) throws EmpException;

	/**
	 * <p>
	 * NE그룹의 알람을 조회한다.
	 * </p>
	 *
	 * @param context
	 * @param ne_group_id
	 * @param event_def
	 * @param severity
	 * @param fromTime
	 * @param toTime
	 * @param startNo
	 * @param count
	 * @return
	 * @throws EmpException
	 */
	public Model4Alarm[] queryListAlarmByNeGroup(EmpContext context, int ne_group_id, EMP_MODEL_EVENT event_def, SEVERITY severity, Date fromTime, Date toTime, int startNo, int count) throws EmpException;

	/**
	 * <p>
	 * NE의 알람을 조회한다.
	 * </p>
	 *
	 * @param context
	 * @param ne_id
	 * @param event_def
	 * @param severity
	 * @param fromTime
	 * @param toTime
	 * @param startNo
	 * @param count
	 * @return
	 * @throws EmpException
	 */
	public Model4Alarm[] queryListAlarmByNe(EmpContext context, int ne_id, EMP_MODEL_EVENT event_def, SEVERITY severity, Date fromTime, Date toTime, int startNo, int count) throws EmpException;

	/**
	 * <p>
	 * 알람이력운 조회한다.
	 * </p>
	 *
	 * @param context
	 * @param startNo
	 * @param count
	 * @return
	 * @throws EmpException
	 */
	public Model4Alarm[] queryListAlarmByConsole(EmpContext context, int startNo, int count) throws EmpException;

	/**
	 * <p>
	 * NE그룹의 알람을 조회한다.
	 * </p>
	 *
	 * @param context
	 * @param ne_group_id
	 * @param event_def
	 * @param severity
	 * @param fromTime
	 * @param toTime
	 * @return
	 * @throws EmpException
	 */
	public int queryCountAlarmByNeGroup(EmpContext context, int ne_group_id, EMP_MODEL_EVENT event_def, SEVERITY severity, Date fromTime, Date toTime) throws EmpException;

	/**
	 * <p>
	 * NE의 알람을 조회한다.
	 * </p>
	 *
	 * @param context
	 * @param ne_id
	 * @param event_def
	 * @param severity
	 * @param fromTime
	 * @param toTime
	 * @return
	 * @throws EmpException
	 */
	public int queryCountAlarmByNe(EmpContext context, int ne_id, EMP_MODEL_EVENT event_def, SEVERITY severity, Date fromTime, Date toTime) throws EmpException;

	/**
	 * <p>
	 * 활성화된 알람을 조회한다.
	 * </p>
	 *
	 * @param context
	 * @param gen_first_event_id
	 * @return
	 * @throws EmpException
	 */
	public Model4Alarm queryAlarmActive(EmpContext context, long gen_first_event_id) throws EmpException;

	/**
	 * <p>
	 * NE그룹의 활성화된 알람을 조회한다.
	 * </p>
	 *
	 * @param context
	 * @param ne_group_id
	 * @param event_def
	 * @param severity
	 * @param startNo
	 * @param count
	 * @return
	 * @throws EmpException
	 */
	public Model4Alarm[] queryListAlarmActiveByNeGroup(EmpContext context, int ne_group_id, EMP_MODEL_EVENT event_def, SEVERITY severity, int startNo, int count) throws EmpException;

	/**
	 * <p>
	 * NE의 활성화된 알람을 조회한다.
	 * </p>
	 *
	 * @param context
	 * @param ne_id
	 * @param ne_info_def
	 * @param event_def
	 * @param severity
	 * @param startNo
	 * @param count
	 * @return
	 * @throws EmpException
	 */
	public Model4Alarm[] queryListAlarmActiveByNe(EmpContext context, int ne_id, EMP_MODEL_NE_INFO ne_info_def, EMP_MODEL_EVENT event_def, SEVERITY severity, int startNo, int count) throws EmpException;

	/**
	 * <p>
	 * NE그룹의 활성화된 알람을 조회한다.
	 * </p>
	 *
	 * @param context
	 * @param ne_group_id
	 * @param event_def
	 * @param severity
	 * @return
	 * @throws EmpException
	 */
	public int queryCountAlarmActiveByNeGroup(EmpContext context, int ne_group_id, EMP_MODEL_EVENT event_def, SEVERITY severity) throws EmpException;

	/**
	 * <p>
	 * NE의활성화된 알람을 조회한다.
	 * </p>
	 *
	 * @param context
	 * @param ne_id
	 * @param event_def
	 * @param severity
	 * @return
	 * @throws EmpException
	 */
	public int queryCountAlarmActiveByNe(EmpContext context, int ne_id, EMP_MODEL_EVENT event_def, SEVERITY severity) throws EmpException;

	/**
	 * <p>
	 * 알람을 인지한다.
	 * </p>
	 *
	 * @param context
	 * @param gen_first_event_id
	 * @return
	 * @throws EmpException
	 */
	public Model4Alarm ackAlarm(EmpContext context, long gen_first_event_id) throws EmpException;

	/**
	 * <p>
	 * 현재 Alarm을 해제한다.
	 * </p>
	 *
	 * @param context
	 * @param gen_first_event_id
	 * @return
	 * @throws EmpException
	 */
	public Model4Alarm clearAlarmByGen_first_event_id(EmpContext context, long gen_first_event_id) throws EmpException;

	/**
	 * <p>
	 * 대표알람 조회
	 * </p>
	 *
	 * @param context
	 * @return
	 * @throws EmpException
	 */
	public Model4AlarmSummary[] queryListAlarmSummary(EmpContext context) throws EmpException;

	/**
	 * <p>
	 * 통계 조회
	 * </p>
	 *
	 * @param context
	 * @param fromTime
	 * @param toTime
	 * @param ne_group_id
	 * @param item
	 * @param statistics_type
	 * @return
	 * @throws EmpException
	 */
	public Model4AlarmStatistics[] queryListAlarmStatisticsByNeGroup(EmpContext context, int ne_group_id, ITEM item, STATISTICS_TYPE statistics_type, Date fromTime, Date toTime) throws EmpException;

	/**
	 * <p>
	 * 통계 조회
	 * </p>
	 *
	 * @param context
	 * @param fromTime
	 * @param toTime
	 * @param ne_id
	 * @param item
	 * @param statistics_type
	 * @return
	 * @throws EmpException
	 */
	public Model4AlarmStatistics[] queryListAlarmStatisticsByNe(EmpContext context, int ne_id, ITEM item, STATISTICS_TYPE statistics_type, Date fromTime, Date toTime) throws EmpException;

	/**
	 * <p>
	 * 사용자그룹 목록조회
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
	 * 사용자그룹 조회
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
	 * 사용자그룹 개수 조회
	 * </p>
	 * 
	 * @param context
	 * @return
	 * @throws EmpException
	 */
	public int queryCountUserGroup(EmpContext context) throws EmpException;

	/**
	 * <p>
	 * 사용자 생성
	 * </p>
	 * 
	 * @param context
	 * @param user
	 * @return
	 * @throws EmpException
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
	 * @param user_id
	 * @return
	 * @throws EmpException
	 */
	public Model4User[] queryListUser(EmpContext context, int startNo, int count) throws EmpException;

	/**
	 * <p>
	 * 사용자 개수 조회
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

	/**
	 * <p>
	 * 운용이력 코드를 조회한다.
	 * </p>
	 * 
	 * @param context
	 * @return
	 * @throws EmpException
	 */
	public OPERATION_CODE[] getListOperation_code(EmpContext context) throws EmpException;

	/**
	 * <p>
	 * 운용이력을 저장한다.
	 * </p>
	 * 
	 * @param context
	 * @param operationLog
	 * @return
	 * @throws EmpException
	 */
	public Model4OperationLog createOperationLog(EmpContext context, Model4OperationLog operation_log) throws EmpException;

	/**
	 * <p>
	 * 운용이력을 조회한다.
	 * </p>
	 * 
	 * @param context
	 * @param ne_group_id
	 * @param function_group
	 * @param function
	 * @param operation
	 * @param result
	 * @param user_session_id
	 * @param user_account
	 * @param fromTime
	 * @param toTime
	 * @param startNo
	 * @param count
	 * @return
	 * @throws EmpException
	 */
	public Model4OperationLog[] queryListOperationLogByNeGroup(EmpContext context, int ne_group_id, String function_group, String function, String operation, Boolean result, Integer user_session_id, String user_account, Date fromTime, Date toTime, int startNo, int count) throws EmpException;

	/**
	 * <p>
	 * 운용이력을 조회한다.
	 * </p>
	 * 
	 * @param context
	 * @param ne_id
	 * @param function_group
	 * @param function
	 * @param operation
	 * @param result
	 * @param user_session_id
	 * @param user_account
	 * @param fromTime
	 * @param toTime
	 * @param startNo
	 * @param count
	 * @return
	 * @throws EmpException
	 */
	public Model4OperationLog[] queryListOperationLogByNe(EmpContext context, int ne_id, String function_group, String function, String operation, Boolean result, Integer user_session_id, String user_account, Date fromTime, Date toTime, int startNo, int count) throws EmpException;

	/**
	 * <p>
	 * 운용이력을 갯수을 조회한다.
	 * </p>
	 * 
	 * @param context
	 * @param ne_group_id
	 * @param function_group
	 * @param function
	 * @param operation
	 * @param result
	 * @param user_session_id
	 * @param user_account
	 * @param fromTime
	 * @param toTime
	 * @return
	 * @throws EmpException
	 */
	public int queryCountOperationLogByNeGroup(EmpContext context, int ne_group_id, String function_group, String function, String operation, Boolean result, Integer user_session_id, String user_account, Date fromTime, Date toTime) throws EmpException;

	/**
	 * <p>
	 * 운용이력을 갯수을 조회한다.
	 * </p>
	 * 
	 * @param context
	 * @param ne_id
	 * @param function_group
	 * @param function
	 * @param operation
	 * @param result
	 * @param user_session_id
	 * @param user_account
	 * @param fromTime
	 * @param toTime
	 * @return
	 * @throws EmpException
	 */
	public int queryCountOperationLogByNe(EmpContext context, int ne_id, String function_group, String function, String operation, Boolean result, Integer user_session_id, String user_account, Date fromTime, Date toTime) throws EmpException;

	/**
	 * <p>
	 * 이미지 조회
	 * </p>
	 *
	 * @param context
	 * @param path
	 * @param width
	 * @param height
	 * @param color
	 * @return
	 * @throws EmpException
	 */
	public byte[] queryImage(EmpContext context, String path, int width, int height, SEVERITY severity) throws EmpException;

	/**
	 * <p>
	 * Network 정보 수정 플래그 조회
	 * </p>
	 *
	 * @param context
	 * @return
	 * @throws EmpException
	 */
	public long queryCurrUpdate_seq_network(EmpContext context) throws EmpException;

	/**
	 * <p>
	 * Fault 정보 수정 플래그 조회
	 * </p>
	 *
	 * @param context
	 * @return
	 * @throws EmpException
	 */
	public long queryCurrUpdate_seq_fault(EmpContext context) throws EmpException;

	/**
	 * <p>
	 * DB 초기화
	 * </p>
	 * 
	 * @param context
	 * @param ne
	 * @param ne_info
	 * @param fault
	 * @param operation_log
	 * @throws EmpException
	 */
	public void truncate(EmpContext context, boolean ne, boolean ne_info, boolean fault, boolean operation_log) throws EmpException;

}
