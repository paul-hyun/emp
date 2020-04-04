/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.share.model.security.operation_log;

import com.hellonms.platforms.emp_core.share.operation.OPERATION_CODE;

/**
 * <p>
 * Orange에서 수행가능한 Operation 목록
 * </p>
 * 
 * @since 1.6
 * @create 2015. 3. 12.
 * @modified 2015. 3. 12.
 * @author cchyun
 * 
 */
public class OPERATION_CODE_ORANGE {

	public static final OPERATION_CODE NETWORK_TOPOLOGY_QUERY = OPERATION_CODE.add(0x00002103, "Network", "Topology", "Query", true, "Query topology");
	public static final OPERATION_CODE NETWORK_TOPOLOGY_UPDATE = OPERATION_CODE.add(0x00002104, "Network", "Topology", "Update", true, "Update topology");

	public static final OPERATION_CODE NETWORK_NEGROUP_CREATE = OPERATION_CODE.add(0x00002111, "Network", "NeGroup", "Create", true, "Create ne group");
	public static final OPERATION_CODE NETWORK_NEGROUP_QUERY = OPERATION_CODE.add(0x00002113, "Network", "NeGroup", "Query", true, "Query ne group");
	public static final OPERATION_CODE NETWORK_NEGROUP_UPDATE = OPERATION_CODE.add(0x00002114, "Network", "NeGroup", "Update", true, "Update ne group");
	public static final OPERATION_CODE NETWORK_NEGROUP_DELETE = OPERATION_CODE.add(0x00002115, "Network", "NeGroup", "Delete", true, "Delete ne group");

	public static final OPERATION_CODE NETWORK_NE_CREATE = OPERATION_CODE.add(0x00002121, "Network", "Ne", "Create", true, "Create ne");
	public static final OPERATION_CODE NETWORK_NE_READ = OPERATION_CODE.add(0x00002122, "Network", "Ne", "Read", true, "Read ne");
	public static final OPERATION_CODE NETWORK_NE_QUERY = OPERATION_CODE.add(0x00002123, "Network", "Ne", "Query", true, "Query ne");
	public static final OPERATION_CODE NETWORK_NE_UPDATE = OPERATION_CODE.add(0x00002124, "Network", "Ne", "Update", true, "Update ne");
	public static final OPERATION_CODE NETWORK_NE_DELETE = OPERATION_CODE.add(0x00002125, "Network", "Ne", "Delete", true, "Delete ne");
	public static final OPERATION_CODE NETWORK_NE_DISCOVERY = OPERATION_CODE.add(0x00002126, "Network", "Ne", "Discovery", true, "Discovery ne");

	public static final OPERATION_CODE NETWORK_LINK_CREATE = OPERATION_CODE.add(0x00002131, "Network", "NetworkLink", "Create", true, "Create network link");
	public static final OPERATION_CODE NETWORK_LINK_DELETE = OPERATION_CODE.add(0x00002135, "Network", "NetworkLink", "Delete", true, "Delete network link");

	public static final OPERATION_CODE NETWORK_NE_INFO_CREATE = OPERATION_CODE.add(0x00002211, "Network", "NeInfo", "Create", true, "Create ne info");
	public static final OPERATION_CODE NETWORK_NE_INFO_READ = OPERATION_CODE.add(0x00002212, "Network", "NeInfo", "Read", true, "Read ne info");
	public static final OPERATION_CODE NETWORK_NE_INFO_QUERY = OPERATION_CODE.add(0x00002213, "Network", "NeInfo", "Query", true, "Query ne info");
	public static final OPERATION_CODE NETWORK_NE_INFO_UPDATE = OPERATION_CODE.add(0x00002214, "Network", "NeInfo", "Update", true, "Update ne info");
	public static final OPERATION_CODE NETWORK_NE_INFO_DELETE = OPERATION_CODE.add(0x00002215, "Network", "NeInfo", "Delete", true, "Delete ne info");

	public static final OPERATION_CODE NETWORK_NE_STATISTICS_CREATE = OPERATION_CODE.add(0x00002221, "Network", "NeStatistics", "Create", true, "Create ne statistics");
	public static final OPERATION_CODE NETWORK_NE_STATISTICS_READ = OPERATION_CODE.add(0x00002222, "Network", "NeStatistics", "Read", true, "Read ne statistics");
	public static final OPERATION_CODE NETWORK_NE_STATISTICS_QUERY = OPERATION_CODE.add(0x00002223, "Network", "NeStatistics", "Query", true, "Query ne statistics");
	public static final OPERATION_CODE NETWORK_NE_STATISTICS_UPDATE = OPERATION_CODE.add(0x00002224, "Network", "NeStatistics", "Update", true, "Update ne statistics");
	public static final OPERATION_CODE NETWORK_NE_STATISTICS_DELETE = OPERATION_CODE.add(0x00002225, "Network", "NeStatistics", "Delete", true, "Delete ne statistics");

	public static final OPERATION_CODE NETWORK_NE_THRESHOLD_QUERY = OPERATION_CODE.add(0x00002231, "Network", "NeThreshold", "Query", true, "Query ne threshold");
	public static final OPERATION_CODE NETWORK_NE_THRESHOLD_UPDATE = OPERATION_CODE.add(0x00002234, "Network", "NeThreshold", "Update", true, "Update ne threshold");

	public static final OPERATION_CODE FAULT_EVENT_CREATE = OPERATION_CODE.add(0x00002311, "Fault", "Event", "Create", true, "Create event");
	public static final OPERATION_CODE FAULT_EVENT_QUERY = OPERATION_CODE.add(0x00002313, "Fault", "Event", "Query", true, "Query event");
	public static final OPERATION_CODE FAULT_EVENT_UPDATE = OPERATION_CODE.add(0x00002314, "Fault", "Event", "Update", true, "Update event");
	public static final OPERATION_CODE FAULT_EVENT_DELETE = OPERATION_CODE.add(0x00002315, "Fault", "Event", "Delete", true, "Delete event");

	public static final OPERATION_CODE FAULT_ALARM_CREATE = OPERATION_CODE.add(0x00002321, "Fault", "Alarm", "Create", true, "Create alarm");
	public static final OPERATION_CODE FAULT_ALARM_QUERY = OPERATION_CODE.add(0x00002323, "Fault", "Alarm", "Query", true, "Query alarm");
	public static final OPERATION_CODE FAULT_ALARM_UPDATE = OPERATION_CODE.add(0x00002324, "Fault", "Alarm", "Update", true, "Update alarm");
	public static final OPERATION_CODE FAULT_ALARM_DELETE = OPERATION_CODE.add(0x00002325, "Fault", "Alarm", "Delete", true, "Delete alarm");
	public static final OPERATION_CODE FAULT_ALARM_ACK = OPERATION_CODE.add(0x00002326, "Fault", "Alarm", "Ack", true, "Ack alarm");
	public static final OPERATION_CODE FAULT_ALARM_UNACK = OPERATION_CODE.add(0x00002327, "Fault", "Alarm", "UnAck", true, "UnAck alarm");
	public static final OPERATION_CODE FAULT_ALARM_CLEAR = OPERATION_CODE.add(0x00002328, "Fault", "Alarm", "Clear", true, "Clear alarm");
	public static final OPERATION_CODE FAULT_ALARM_COMMENT = OPERATION_CODE.add(0x00002329, "Fault", "Alarm", "Comment", true, "Comment alarm");

	public static final OPERATION_CODE FAULT_ALARM_STATISTICS_CREATE = OPERATION_CODE.add(0x00002331, "Fault", "AlarmStatistics", "Create", true, "Create alarm statistics");
	public static final OPERATION_CODE FAULT_ALARM_STATISTICS_QUERY = OPERATION_CODE.add(0x00002333, "Fault", "AlarmStatistics", "Query", true, "Query alarm statistics");
	public static final OPERATION_CODE FAULT_ALARM_STATISTICS_UPDATE = OPERATION_CODE.add(0x00002334, "Fault", "AlarmStatistics", "Update", true, "Update alarm statistics");
	public static final OPERATION_CODE FAULT_ALARM_STATISTICS_DELETE = OPERATION_CODE.add(0x00002335, "Fault", "AlarmStatistics", "Delete", true, "Delete alarm statistics");

	public static final OPERATION_CODE SECURITY_USERSESSION_LOGIN = OPERATION_CODE.add(0x00002911, "Security", "UserSession", "Login", false, "Login");
	public static final OPERATION_CODE SECURITY_USERSESSION_QUERY = OPERATION_CODE.add(0x00002913, "Security", "UserSession", "Query", false, "Query logind user session");
	public static final OPERATION_CODE SECURITY_USERSESSION_LOGOUT = OPERATION_CODE.add(0x00002914, "Security", "UserSession", "Logout", false, "Logout");
	public static final OPERATION_CODE SECURITY_USERSESSION_TERMINATE = OPERATION_CODE.add(0x00002915, "Security", "UserSession", "Terminate", false, "Terminate idle user session");

	public static final OPERATION_CODE SECURITY_USERGROUP_CREATE = OPERATION_CODE.add(0x00002921, "Security", "UserGroup", "Create", false, "Create user group");
	public static final OPERATION_CODE SECURITY_USERGROUP_QUERY = OPERATION_CODE.add(0x00002923, "Security", "UserGroup", "Query", false, "Query user group");
	public static final OPERATION_CODE SECURITY_USERGROUP_UPDATE = OPERATION_CODE.add(0x00002924, "Security", "UserGroup", "Update", false, "Update user group");
	public static final OPERATION_CODE SECURITY_USERGROUP_DELETE = OPERATION_CODE.add(0x00002925, "Security", "UserGroup", "Delete", false, "Delete user group");

	public static final OPERATION_CODE SECURITY_USER_CREATE = OPERATION_CODE.add(0x00002931, "Security", "User", "Create", false, "Create user");
	public static final OPERATION_CODE SECURITY_USER_QUERY = OPERATION_CODE.add(0x00002933, "Security", "User", "Query", false, "Query user");
	public static final OPERATION_CODE SECURITY_USER_UPDATE = OPERATION_CODE.add(0x00002934, "Security", "User", "Update", false, "Update user");
	public static final OPERATION_CODE SECURITY_USER_DELETE = OPERATION_CODE.add(0x00002935, "Security", "User", "Delete", false, "Delete user");

	public static final OPERATION_CODE SECURITY_OPERATIONLOG_QUERY = OPERATION_CODE.add(0x00002943, "Security", "OperationLog", "Query", false, "Query user");

	public static final OPERATION_CODE HELP_ABOUT_QUERY = OPERATION_CODE.add(0x00002A13, "Help", "About", "Query", false, "Query about");

	public static final OPERATION_CODE HELP_PREFERENCE_QUERY = OPERATION_CODE.add(0x00002A23, "Help", "Preference", "Query", false, "Query preference");
	public static final OPERATION_CODE HELP_PREFERENCE_UPDATE = OPERATION_CODE.add(0x00002A24, "Help", "Preference", "Update", false, "Update preference");
	public static final OPERATION_CODE HELP_DATABASE_BACKUP = OPERATION_CODE.add(0x00002A31, "Help", "Database", "Backup", false, "Backup database");

}
