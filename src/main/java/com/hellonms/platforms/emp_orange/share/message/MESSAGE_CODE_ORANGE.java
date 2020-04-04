/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.share.message;

import com.hellonms.platforms.emp_core.share.message.MESSAGE_CODE;
import com.hellonms.platforms.emp_onion.share.message.MESSAGE_CODE_ONION;

/**
 * <p>
 * Insert description of MESSAGE_CODE_ORANGE.java
 * </p>
 * 
 * @since 1.6
 * @create 2015. 3. 17.
 * @modified 2015. 3. 17.
 * @author jungsun
 * 
 */
public class MESSAGE_CODE_ORANGE extends MESSAGE_CODE_ONION {

	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// #LICENSE
	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Refresh
	 */
	public static final MESSAGE_CODE LICENSE = new MESSAGE_CODE("MESSAGE_ORANGE_LICENSE", "License");
	/**
	 * Refresh
	 */
	public static final MESSAGE_CODE LICENSE_OVER_USE = new MESSAGE_CODE("MESSAGE_ORANGE_LICENSE_OVER_USE", "License over use");

	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// #Common
	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Refresh
	 */
	public static final MESSAGE_CODE REFRESH = new MESSAGE_CODE("MESSAGE_ORANGE_REFRESH", "Refresh");

	/**
	 * Create
	 */
	public static final MESSAGE_CODE CREATE = new MESSAGE_CODE("MESSAGE_ORANGE_CREATE", "Create");

	/**
	 * Property
	 */
	public static final MESSAGE_CODE PROPERTY = new MESSAGE_CODE("MESSAGE_ORANGE_PROPERTY", "Property");

	/**
	 * Update
	 */
	public static final MESSAGE_CODE UPDATE = new MESSAGE_CODE("MESSAGE_ORANGE_UPDATE", "Update");

	/**
	 * Update
	 */
	public static final MESSAGE_CODE COPY = new MESSAGE_CODE("MESSAGE_ORANGE_COPY", "Copy");

	/**
	 * Delete
	 */
	public static final MESSAGE_CODE DELETE = new MESSAGE_CODE("MESSAGE_ORANGE_DELETE", "Delete");

	/**
	 * Save Excel
	 */
	public static final MESSAGE_CODE EXCEL = new MESSAGE_CODE("MESSAGE_ORANGE_EXCEL", "Excel");

	/**
	 * Success
	 */
	public static final MESSAGE_CODE SUCCESS = new MESSAGE_CODE("MESSAGE_ORANGE_SUCCESS", "Success");

	/**
	 * Fail
	 */
	public static final MESSAGE_CODE FAIL = new MESSAGE_CODE("MESSAGE_ORANGE_FAIL", "Fail {}");

	/**
	 * All
	 */
	public static final MESSAGE_CODE ALL = new MESSAGE_CODE("MESSAGE_ORANGE_ALL", "All");

	/**
	 * No.
	 */
	public static final MESSAGE_CODE SEQUENCE = new MESSAGE_CODE("MESSAGE_ORANGE_SEQUENCE", "No.");

	/**
	 * Insert Value
	 */
	public static final MESSAGE_CODE INSERT_VALUE = new MESSAGE_CODE("MESSAGE_ORANGE_INSERT_VALUE", "Insert value '{}'.");

	/**
	 * Create Title
	 */
	public static final MESSAGE_CODE CREATE_TITLE = new MESSAGE_CODE("MESSAGE_ORANGE_CREATE_TITLE", "Create {}");

	/**
	 * Create Message
	 */
	public static final MESSAGE_CODE CREATE_CONFIRM = new MESSAGE_CODE("MESSAGE_ORANGE_CREATE_CONFIRM", "Do you want create {} '{}'?");

	/**
	 * Update Title
	 */
	public static final MESSAGE_CODE UPDATE_TITLE = new MESSAGE_CODE("MESSAGE_ORANGE_UPDATE_TITLE", "Update {}");

	/**
	 * Update Message
	 */
	public static final MESSAGE_CODE UPDATE_CONFIRM = new MESSAGE_CODE("MESSAGE_ORANGE_UPDATE_CONFIRM", "Do you want to update {} '{}'?");

	/**
	 * Copy Title
	 */
	public static final MESSAGE_CODE COPY_TITLE = new MESSAGE_CODE("MESSAGE_ORANGE_COPY_TITLE", "Copy {}");

	/**
	 * COPY Message
	 */
	public static final MESSAGE_CODE COPY_CONFIRM = new MESSAGE_CODE("MESSAGE_ORANGE_COPY_CONFIRM", "Do you want to copy {} '{}'?");

	/**
	 * Delete Title
	 */
	public static final MESSAGE_CODE DELETE_TITLE = new MESSAGE_CODE("MESSAGE_ORANGE_DELETE_TITLE", "Delete {}");

	/**
	 * Delete Message
	 */
	public static final MESSAGE_CODE DELETE_CONFIRM = new MESSAGE_CODE("MESSAGE_ORANGE_DELETE_CONFIRM", "Do you want to delete {} '{}'?");

	/**
	 * Property Title
	 */
	public static final MESSAGE_CODE PROPERTY_TITLE = new MESSAGE_CODE("MESSAGE_ORANGE_PROPERTY_TITLE", "Property {}");

	/**
	 * Insert Property.
	 */
	public static final MESSAGE_CODE INSERT_PROPERTY = new MESSAGE_CODE("MESSAGE_ORANGE_INSERT_PROPERTY", "Insert property {}.");

	/**
	 * Filter Title
	 */
	public static final MESSAGE_CODE FILTER_TITLE = new MESSAGE_CODE("MESSAGE_ORANGE_FILTER_TITLE", "{} Filter");

	/**
	 * Data Title
	 */
	public static final MESSAGE_CODE DATA_TITLE = new MESSAGE_CODE("MESSAGE_ORANGE_DATA_TITLE", "{} Data");

	/**
	 * Console Title
	 */
	public static final MESSAGE_CODE CONSOLE_TITLE = new MESSAGE_CODE("MESSAGE_ORANGE_CONSOLE_TITLE", "{} Console");

	/**
	 * Connection closed
	 */
	public static final MESSAGE_CODE CONNECTION_CLOSED = new MESSAGE_CODE("MESSAGE_ORANGE_CONNECTION_CLOSED", "Connection closed");

	/**
	 * Connection closed with server.
	 */
	public static final MESSAGE_CODE CONNECTION_CLOSED_ERROR = new MESSAGE_CODE("MESSAGE_ORANGE_CONNECTION_CLOSED_ERROR", "Connection closed with server.");

	/**
	 * Execute client error
	 */
	public static final MESSAGE_CODE EXEC_CLIENT_ERROR = new MESSAGE_CODE("MESSAGE_ORANGE_EXEC_CLIENT_ERROR", "Execute client error");

	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// #Launcher(Login)
	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * SERVER HOST
	 */
	public static final MESSAGE_CODE SERVER_ADDRESS = new MESSAGE_CODE("MESSAGE_ORANGE_SERVER_ADDRESS", "Server Address");

	/**
	 * User ID
	 */
	public static final MESSAGE_CODE USER_ID = new MESSAGE_CODE("MESSAGE_ORANGE_USER_ID", "User ID");

	/**
	 * Password
	 */
	public static final MESSAGE_CODE PASSWORD = new MESSAGE_CODE("MESSAGE_ORANGE_PASSWORD", "Password");

	/**
	 * Language
	 */
	public static final MESSAGE_CODE LANGUAGE = new MESSAGE_CODE("MESSAGE_ORANGE_LANGUAGE", "Language");

	/**
	 * Login
	 */
	public static final MESSAGE_CODE LOGIN = new MESSAGE_CODE("MESSAGE_ORANGE_LOGIN", "Login");

	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// #Menu(Network)
	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Network
	 */
	public static final MESSAGE_CODE NETWORK = new MESSAGE_CODE("MESSAGE_ORANGE_NETWORK", "Network");

	/**
	 * Network Tree
	 */
	public static final MESSAGE_CODE NETWORK_TREE = new MESSAGE_CODE("MESSAGE_ORANGE_NETWORK_TREE", "Network Tree");

	/**
	 * Network View
	 */
	public static final MESSAGE_CODE NETWORK_VIEW = new MESSAGE_CODE("MESSAGE_ORANGE_NETWORK_VIEW", "Network View");

	/**
	 * NE Info
	 */
	public static final MESSAGE_CODE NE_INFO = new MESSAGE_CODE("MESSAGE_ORANGE_NE_INFO", "NE Info");

	/**
	 * NE Statistics
	 */
	public static final MESSAGE_CODE NE_STATISTICS = new MESSAGE_CODE("MESSAGE_ORANGE_NE_STATISTICS", "NE Statistics");

	/**
	 * NE Threshold
	 */
	public static final MESSAGE_CODE NE_THRESHOLD = new MESSAGE_CODE("MESSAGE_ORANGE_NE_THRESHOLD", "NE Threshold");

	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// #Menu(Fault)
	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Fault
	 */
	public static final MESSAGE_CODE FAULT = new MESSAGE_CODE("MESSAGE_ORANGE_FAULT", "Fault");

	/**
	 * Alarm Active
	 */
	public static final MESSAGE_CODE ALARM_ACTIVE = new MESSAGE_CODE("MESSAGE_ORANGE_ALARM_ACTIVE", "Alarm Active");

	/**
	 * Alarm History
	 */
	public static final MESSAGE_CODE ALARM_HISTORY = new MESSAGE_CODE("MESSAGE_ORANGE_ALARM_HISTORY", "Alarm History");

	/**
	 * Event
	 */
	public static final MESSAGE_CODE EVENT = new MESSAGE_CODE("MESSAGE_ORANGE_EVENT", "Event");

	/**
	 * Alarm Statistics
	 */
	public static final MESSAGE_CODE ALARM_STATISTICS = new MESSAGE_CODE("MESSAGE_ORANGE_ALARM_STATISTICS", "Alarm Statistics");

	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// #Menu(Security)
	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Security
	 */
	public static final MESSAGE_CODE SECURITY = new MESSAGE_CODE("MESSAGE_ORANGE_SECURITY", "Security");

	/**
	 * User Group
	 */
	public static final MESSAGE_CODE USER_GROUP = new MESSAGE_CODE("MESSAGE_ORANGE_USER_GROUP", "User Group");

	/**
	 * User Management
	 */
	public static final MESSAGE_CODE USER_MANAGEMENT = new MESSAGE_CODE("MESSAGE_ORANGE_USER_MANAGEMENT", "User Management");

	/**
	 * User Session
	 */
	public static final MESSAGE_CODE USER_SESSION = new MESSAGE_CODE("MESSAGE_ORANGE_USER_SESSION", "User Session");

	/**
	 * Operation Log
	 */
	public static final MESSAGE_CODE OPERATION_LOG = new MESSAGE_CODE("MESSAGE_ORANGE_OPERATION_LOG", "Operation Log");

	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// #Menu(Help)
	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Help
	 */
	public static final MESSAGE_CODE HELP = new MESSAGE_CODE("MESSAGE_ORANGE_HELP", "Help");

	/**
	 * Environment
	 */
	public static final MESSAGE_CODE ENVIRONMENT = new MESSAGE_CODE("MESSAGE_ORANGE_ENVIRONMENT", "Environment");

	/**
	 * Help
	 */
	public static final MESSAGE_CODE HELP_CONTENTS = new MESSAGE_CODE("MESSAGE_ORANGE_HELP_CONTENTS", "Help Contents");

	/**
	 * About
	 */
	public static final MESSAGE_CODE ABOUT = new MESSAGE_CODE("MESSAGE_ORANGE_ABOUT", "About");

	/**
	 * Modeling
	 */
	public static final MESSAGE_CODE MODELING = new MESSAGE_CODE("MESSAGE_ORANGE_MODELING", "Modeling");

	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// #Menu(Logout)
	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Logout
	 */
	public static final MESSAGE_CODE LOGOUT = new MESSAGE_CODE("MESSAGE_ORANGE_LOGOUT", "Logout");

	/**
	 * Do you want logout?
	 */
	public static final MESSAGE_CODE LOGOUT_CONFIRM = new MESSAGE_CODE("MESSAGE_ORANGE_LOGOUT_CONFIRM", "Do you want to logout?");

	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// #NE Group
	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * NE Group
	 */
	public static final MESSAGE_CODE NE_GROUP = new MESSAGE_CODE("MESSAGE_ORANGE_NE_GROUP", "NE Group");

	/**
	 * NE Group ID
	 */
	public static final MESSAGE_CODE NE_GROUP_ID = new MESSAGE_CODE("MESSAGE_ORANGE_NE_GROUP_ID", "NE Group ID");

	/**
	 * Group
	 */
	public static final MESSAGE_CODE GROUP = new MESSAGE_CODE("MESSAGE_ORANGE_GROUP", "Group");

	/**
	 * Parent NE Group
	 */
	public static final MESSAGE_CODE PARENT_NE_GROUP = new MESSAGE_CODE("MESSAGE_ORANGE_PARENT_NE_GROUP", "Parent NE Group");

	/**
	 * NE Group Name
	 */
	public static final MESSAGE_CODE NE_GROUP_NAME = new MESSAGE_CODE("MESSAGE_ORANGE_NE_GROUP_NAME", "NE Group Name");

	/**
	 * Icon
	 */
	public static final MESSAGE_CODE ICON = new MESSAGE_CODE("MESSAGE_ORANGE_ICON", "Icon");

	/**
	 * Description
	 */
	public static final MESSAGE_CODE DESCRIPTION = new MESSAGE_CODE("MESSAGE_ORANGE_DESCRIPTION", "Description");

	/**
	 * Child NE Group Count
	 */
	public static final MESSAGE_CODE CHILD_NE_GROUP_COUNT = new MESSAGE_CODE("MESSAGE_ORANGE_CHILD_NE_GROUP_COUNT", "Child NE Group Count");

	/**
	 * Child NE Count
	 */
	public static final MESSAGE_CODE CHILD_NE_COUNT = new MESSAGE_CODE("MESSAGE_ORANGE_CHILD_NE_COUNT", "Child NE Count");

	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// #NE
	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * NE ID
	 */
	public static final MESSAGE_CODE NE_ID = new MESSAGE_CODE("MESSAGE_ORANGE_NE_ID", "NE ID");

	/**
	 * NE Name
	 */
	public static final MESSAGE_CODE NE_NAME = new MESSAGE_CODE("MESSAGE_ORANGE_NE_NAME", "NE Name");

	/**
	 * NE Type
	 */
	public static final MESSAGE_CODE NE_TYPE = new MESSAGE_CODE("MESSAGE_ORANGE_NE_TYPE", "NE Type");

	/**
	 * NE IP
	 */
	public static final MESSAGE_CODE NE_IP = new MESSAGE_CODE("MESSAGE_ORANGE_NE_IP", "NE IP");

	/**
	 * Alarm
	 */
	public static final MESSAGE_CODE ALARM = new MESSAGE_CODE("MESSAGE_ORANGE_ALARM", "Alarm");

	/**
	 * Not Manage
	 */
	public static final MESSAGE_CODE NOT_MANAGE = new MESSAGE_CODE("MESSAGE_ORANGE_NOT_MANAGE", "Not Manage");

	/**
	 * Normal
	 */
	public static final MESSAGE_CODE NORMAL = new MESSAGE_CODE("MESSAGE_ORANGE_NORMAL", "Normal");

	/**
	 * Abnormal
	 */
	public static final MESSAGE_CODE ABNORMAL = new MESSAGE_CODE("MESSAGE_ORANGE_ABNORMAL", "Abnormal");

	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// #NE Session(ICMP)
	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * ICMP
	 */
	public static final MESSAGE_CODE ICMP = new MESSAGE_CODE("MESSAGE_ORANGE_ICMP", "Ping");

	/**
	 * Timeout
	 */
	public static final MESSAGE_CODE TIMEOUT = new MESSAGE_CODE("MESSAGE_ORANGE_TIMEOUT", "Timeout");

	/**
	 * Secs
	 */
	public static final MESSAGE_CODE SECS = new MESSAGE_CODE("MESSAGE_ORANGE_SECS", "Secs");

	/**
	 * Period
	 */
	public static final MESSAGE_CODE PERIOD = new MESSAGE_CODE("MESSAGE_ORANGE_PERIOD", "Period");

	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// #NE Session(SNMP)
	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * SNMP
	 */
	public static final MESSAGE_CODE SNMP = new MESSAGE_CODE("MESSAGE_ORANGE_SNMP", "SNMP");

	/**
	 * Port
	 */
	public static final MESSAGE_CODE PORT = new MESSAGE_CODE("MESSAGE_ORANGE_PORT", "Port");

	/**
	 * SNMP version
	 */
	public static final MESSAGE_CODE SNMP_VERSION = new MESSAGE_CODE("MESSAGE_ORANGE_SNMP_VERSION", "SNMP version");

	/**
	 * Read community
	 */
	public static final MESSAGE_CODE READ_COMMUNITY = new MESSAGE_CODE("MESSAGE_ORANGE_READ_COMMUNITY", "Read community");

	/**
	 * Write community
	 */
	public static final MESSAGE_CODE WRITE_COMMUNITY = new MESSAGE_CODE("MESSAGE_ORANGE_WRITE_COMMUNITY", "Write community");

	/**
	 * CharSet
	 */
	public static final MESSAGE_CODE CHARACTER_SET = new MESSAGE_CODE("MESSAGE_ORANGE_CHARACTER_SET", "Character Set");

	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// #Network View
	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Link
	 */
	public static final MESSAGE_CODE LINK = new MESSAGE_CODE("MESSAGE_ORANGE_LINK", "Link");

	/**
	 * Edit
	 */
	public static final MESSAGE_CODE EDIT = new MESSAGE_CODE("MESSAGE_ORANGE_EDIT", "Edit");

	/**
	 * Save
	 */
	public static final MESSAGE_CODE SAVE = new MESSAGE_CODE("MESSAGE_ORANGE_SAVE", "Save");

	/**
	 * Cpu
	 */
	public static final MESSAGE_CODE CPU = new MESSAGE_CODE("MESSAGE_ORANGE_CPU", "CPU");

	/**
	 * Mem
	 */
	public static final MESSAGE_CODE MEM = new MESSAGE_CODE("MESSAGE_ORANGE_MEM", "MEM");

	/**
	 * Rx
	 */
	public static final MESSAGE_CODE RX = new MESSAGE_CODE("MESSAGE_ORANGE_RX", "Rx");

	/**
	 * Tx
	 */
	public static final MESSAGE_CODE TX = new MESSAGE_CODE("MESSAGE_ORANGE_TX", "Tx");

	/**
	 * Tx
	 */
	public static final MESSAGE_CODE PARTITION = new MESSAGE_CODE("MESSAGE_ORANGE_PARTITION", "Partition");

	/**
	 * Update Location
	 */
	public static final MESSAGE_CODE UPDATE_MAP_LOCATION = new MESSAGE_CODE("MESSAGE_ORANGE_UPDATE_MAP_LOCATION", "Update Location");

	/**
	 * Do you want to update Location?
	 */
	public static final MESSAGE_CODE UPDATE_CONFIRM_MAP_LOCATION = new MESSAGE_CODE("MESSAGE_ORANGE_UPDATE_CONFIRM_MAP_LOCATION", "Do you want to update Location?");

	/**
	 * Update Display NE INFO
	 */
	public static final MESSAGE_CODE UPDATE_NE_INFO_DISPLAY = new MESSAGE_CODE("MESSAGE_ORANGE_UPDATE_NE_INFO_DISPLAY", "Update NE Display");

	/**
	 * Do you want to update display NE INFO
	 */
	public static final MESSAGE_CODE UPDATE_CONFIRM_NE_INFO_DISPLAY = new MESSAGE_CODE("MESSAGE_ORANGE_UPDATE_CONFIRM_NE_INFO_DISPLAY", "Do you want to update NE Display?");

	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// #Discovery NE
	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Discovery NE
	 */
	public static final MESSAGE_CODE DISCOVERY_NE = new MESSAGE_CODE("MESSAGE_ORANGE_DISCOVERY_NE", "Discovery NE");

	/**
	 * Search IP
	 */
	public static final MESSAGE_CODE SEARCH_IP = new MESSAGE_CODE("MESSAGE_ORANGE_SEARCH_IP", "Search IP");

	/**
	 * IP Counter
	 */
	public static final MESSAGE_CODE IP_COUNTER = new MESSAGE_CODE("MESSAGE_ORANGE_IP_COUNTER", "IP Counter");

	/**
	 * Select
	 */
	public static final MESSAGE_CODE SELECT = new MESSAGE_CODE("MESSAGE_ORANGE_SELECT", "Select");

	/**
	 * Session Response Time
	 */
	public static final MESSAGE_CODE SESSION_RESPONSE_TIME = new MESSAGE_CODE("MESSAGE_ORANGE_SESSION_RESPONSE_TIME", "{} Response Time");

	/**
	 * Millisecond
	 */
	public static final MESSAGE_CODE MILLISECOND = new MESSAGE_CODE("MESSAGE_ORANGE_MILLISECOND", "{} ms");

	/**
	 * No Response
	 */
	public static final MESSAGE_CODE NO_RESPONSE = new MESSAGE_CODE("MESSAGE_ORANGE_NO_RESPONSE", "No Response");

	/**
	 * NE Discovery Result
	 */
	public static final MESSAGE_CODE NE_DISCOVERY_RESULT = new MESSAGE_CODE("MESSAGE_ORANGE_NE_DISCOVERY_RESULT", "NE Discovery Result");

	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// #Network Popup Menu
	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Child NE/NE Group
	 */
	public static final MESSAGE_CODE STOP_BLINK = new MESSAGE_CODE("MESSAGE_ORANGE_STOP_BLINK", "Stop Blink");

	/**
	 * NE Group exists child
	 */
	public static final MESSAGE_CODE CHILD_NE_EXISTS = new MESSAGE_CODE("MESSAGE_ORANGE_CHILD_NE_EXISTS", "Child NE exists.");

	/**
	 * NE Detail View
	 */
	public static final MESSAGE_CODE NE_DETAIL_VIEW = new MESSAGE_CODE("MESSAGE_ORANGE_NE_DETAIL_VIEW", "NE Detail View");

	/**
	 * Telnet
	 */
	public static final MESSAGE_CODE TELNET = new MESSAGE_CODE("MESSAGE_ORANGE_TELNET", "Telnet");

	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// #NE Info
	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Item
	 */
	public static final MESSAGE_CODE ITEM = new MESSAGE_CODE("MESSAGE_ORANGE_ITEM", "Item");

	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// #NE Statistics
	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Date
	 */
	public static final MESSAGE_CODE DATE = new MESSAGE_CODE("MESSAGE_ORANGE_DATE", "Date");

	/**
	 * Item
	 */
	public static final MESSAGE_CODE PERFORMANCE_ITEM = new MESSAGE_CODE("MESSAGE_ORANGE_PERFORMANCE_ITEM", "Item");

	/**
	 * Location
	 */
	public static final MESSAGE_CODE PERFORMANCE_LOCATION = new MESSAGE_CODE("MESSAGE_ORANGE_PERFORMANCE_LOCATION", "Location");

	/**
	 * Type
	 */
	public static final MESSAGE_CODE STATISTICS_TYPE = new MESSAGE_CODE("MESSAGE_ORANGE_STATISTICS_TYPE", "Type");

	/**
	 * Collect Time
	 */
	public static final MESSAGE_CODE COLLECT_TIME = new MESSAGE_CODE("MESSAGE_ORANGE_COLLECT_TIME", "Collect Time");

	/**
	 * Value
	 */
	public static final MESSAGE_CODE VALUE = new MESSAGE_CODE("MESSAGE_ORANGE_VALUE", "Value");

	/**
	 * Chart Item
	 */
	public static final MESSAGE_CODE CHART_ITEM = new MESSAGE_CODE("MESSAGE_ORANGE_CHART_ITEM", "Chart Item");

	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// #NE Threshold
	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static final MESSAGE_CODE FIELD = new MESSAGE_CODE("MESSAGE_ORANGE_FIELD", "Field");

	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// #Alarm Active
	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Alarm Severity
	 */
	public static final MESSAGE_CODE ALARM_SEVERITY = new MESSAGE_CODE("MESSAGE_ORANGE_ALARM_SEVERITY", "Alarm Severity");

	/**
	 * Severity
	 */
	public static final MESSAGE_CODE SEVERITY = new MESSAGE_CODE("MESSAGE_ORANGE_SEVERITY", "Severity");

	/**
	 * Gen Time
	 */
	public static final MESSAGE_CODE GEN_TIME = new MESSAGE_CODE("MESSAGE_ORANGE_GEN_TIME", "Gen Time");

	/**
	 * NE
	 */
	public static final MESSAGE_CODE NE = new MESSAGE_CODE("MESSAGE_ORANGE_NE", "NE");

	/**
	 * Location
	 */
	public static final MESSAGE_CODE LOCATION = new MESSAGE_CODE("MESSAGE_ORANGE_LOCATION", "Location");

	/**
	 * Cause
	 */
	public static final MESSAGE_CODE CAUSE = new MESSAGE_CODE("MESSAGE_ORANGE_CAUSE", "Cause");

	/**
	 * Repetition
	 */
	public static final MESSAGE_CODE REPETITION = new MESSAGE_CODE("MESSAGE_ORANGE_REPETITION", "Repetition");

	/**
	 * Ack User
	 */
	public static final MESSAGE_CODE ACK_USER = new MESSAGE_CODE("MESSAGE_ORANGE_ACK_USER", "Ack User");

	/**
	 * Gen Description
	 */
	public static final MESSAGE_CODE GEN_DESCRIPTION = new MESSAGE_CODE("MESSAGE_ORANGE_GEN_DESCRIPTION", "Gen Description");

	/**
	 * Ack Time
	 */
	public static final MESSAGE_CODE ACK_TIME = new MESSAGE_CODE("MESSAGE_ORANGE_ACK_TIME", "Ack Time");

	/**
	 * Ack
	 */
	public static final MESSAGE_CODE ACK = new MESSAGE_CODE("MESSAGE_ORANGE_ACK", "Ack");

	/**
	 * Clear
	 */
	public static final MESSAGE_CODE CLEAR = new MESSAGE_CODE("MESSAGE_ORANGE_CLEAR", "Clear");

	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// #Alarm History
	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * From Date
	 */
	public static final MESSAGE_CODE FROM_DATE = new MESSAGE_CODE("MESSAGE_ORANGE_FROM_DATE", "From Date");

	/**
	 * To Date
	 */
	public static final MESSAGE_CODE TO_DATE = new MESSAGE_CODE("MESSAGE_ORANGE_TO_DATE", "To Date");

	/**
	 * Alarm Code
	 */
	public static final MESSAGE_CODE ALARM_CODE = new MESSAGE_CODE("MESSAGE_ORANGE_ALARM_CODE", "Alarm Code");

	/**
	 * Clear Time
	 */
	public static final MESSAGE_CODE CLEAR_TIME = new MESSAGE_CODE("MESSAGE_ORANGE_CLEAR_TIME", "Clear Time");

	/**
	 * Clear Description
	 */
	public static final MESSAGE_CODE CLEAR_DESCRIPTION = new MESSAGE_CODE("MESSAGE_ORANGE_CLEAR_DESCRIPTION", "Clear Description");

	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// #Alarm Statistics
	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Item
	 */
	public static final MESSAGE_CODE ALARM_STATISTICS_ITEM = new MESSAGE_CODE("MESSAGE_ORANGE_ALARM_STATISTICS_ITEM", "Item");

	/**
	 * Type
	 */
	public static final MESSAGE_CODE ALARM_STATISTICS_TYPE = new MESSAGE_CODE("MESSAGE_ORANGE_ALARM_STATISTICS_TYPE", "Type");

	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// #Event
	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Event Severity
	 */
	public static final MESSAGE_CODE EVENT_SEVERITY = new MESSAGE_CODE("MESSAGE_ORANGE_EVENT_SEVERITY", "Event Severity");

	/**
	 * Event Code
	 */
	public static final MESSAGE_CODE EVENT_CODE = new MESSAGE_CODE("MESSAGE_ORANGE_EVENT_CODE", "Event Code");

	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// #User
	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * User
	 */
	public static final MESSAGE_CODE USER = new MESSAGE_CODE("MESSAGE_ORANGE_USER", "User");

	/**
	 * User List
	 */
	public static final MESSAGE_CODE USER_LIST = new MESSAGE_CODE("MESSAGE_ORANGE_USER_LIST", "User List");

	/**
	 * User Name
	 */
	public static final MESSAGE_CODE USER_NAME = new MESSAGE_CODE("MESSAGE_ORANGE_USER_NAME", "User Name");

	/**
	 * User Group Name
	 */
	public static final MESSAGE_CODE USER_GROUP_NAME = new MESSAGE_CODE("MESSAGE_ORANGE_USER_GROUP_NAME", "User Group Name");

	/**
	 * User Detail
	 */
	public static final MESSAGE_CODE USER_DETAIL = new MESSAGE_CODE("MESSAGE_ORANGE_USER_DETAIL", "User Detail");

	/**
	 * New Password
	 */
	public static final MESSAGE_CODE NEW_PASSWORD = new MESSAGE_CODE("MESSAGE_ORANGE_NEW_PASSWORD", "New Password");

	/**
	 * Confirm Password
	 */
	public static final MESSAGE_CODE CONFIRM_PASSWORD = new MESSAGE_CODE("MESSAGE_ORANGE_CONFIRM_PASSWORD", "Confirm Password");

	/**
	 * Email
	 */
	public static final MESSAGE_CODE EMAIL = new MESSAGE_CODE("MESSAGE_ORANGE_EMAIL", "Email");

	/**
	 * Phone
	 */
	public static final MESSAGE_CODE PHONE = new MESSAGE_CODE("MESSAGE_ORANGE_PHONE", "Phone");

	/**
	 * Mobile
	 */
	public static final MESSAGE_CODE MOBILE = new MESSAGE_CODE("MESSAGE_ORANGE_MOBILE", "Mobile");

	/**
	 * Sand Email
	 */
	public static final MESSAGE_CODE SEND_EMAIL = new MESSAGE_CODE("MESSAGE_ORANGE_SEND_EMAIL", "Sand Email");

	/**
	 * Manage NE Group
	 */
	public static final MESSAGE_CODE MANAGE_NE_GROUP = new MESSAGE_CODE("MESSAGE_ORANGE_MANAGE_NE_GROUP", "Manage NE Group");

	/**
	 * Mismatch Passwords.
	 */
	public static final MESSAGE_CODE MISMATCH_PASSWORD = new MESSAGE_CODE("MESSAGE_ORANGE_MISMATCH_PASSWORD", "Mismatch Passwords.");

	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// #Operation Log
	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Service
	 */
	public static final MESSAGE_CODE SERVICE = new MESSAGE_CODE("MESSAGE_ORANGE_SERVICE", "Service");

	/**
	 * Function
	 */
	public static final MESSAGE_CODE FUNCTION = new MESSAGE_CODE("MESSAGE_ORANGE_FUNCTION", "Function");

	/**
	 * Operation
	 */
	public static final MESSAGE_CODE OPERATION = new MESSAGE_CODE("MESSAGE_ORANGE_OPERATION", "Operation");

	/**
	 * Result
	 */
	public static final MESSAGE_CODE RESULT = new MESSAGE_CODE("MESSAGE_ORANGE_RESULT", "Result");

	/**
	 * Session ID
	 */
	public static final MESSAGE_CODE SESSION_ID = new MESSAGE_CODE("MESSAGE_ORANGE_SESSION_ID", "Session ID");

	/**
	 * Operation Time
	 */
	public static final MESSAGE_CODE OPERATION_TIME = new MESSAGE_CODE("MESSAGE_ORANGE_OPERATION_TIME", "Operation Time");

	/**
	 * Transaction ID
	 */
	public static final MESSAGE_CODE TRANSACTION_ID = new MESSAGE_CODE("MESSAGE_ORANGE_TRANSACTION_ID", "Transaction ID");

	/**
	 * User IP
	 */
	public static final MESSAGE_CODE USER_IP = new MESSAGE_CODE("MESSAGE_ORANGE_USER_IP", "User IP");

	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// #Environment(Email)
	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Environment Description
	 */
	public static final MESSAGE_CODE ENVIRONMENT_DESCRIPTION = new MESSAGE_CODE("MESSAGE_ORANGE_ALARM_ENVIRONMENT", "{} Environment");

	/**
	 * Email Send
	 */
	public static final MESSAGE_CODE ALARM_EMAIL_SEND = new MESSAGE_CODE("MESSAGE_ORANGE_EMAIL_SEND", "Alarm Email Send");

	/**
	 * From Email
	 */
	public static final MESSAGE_CODE FROM_EMAIL = new MESSAGE_CODE("MESSAGE_ORANGE_FROM_EMAIL", "From Email");

	/**
	 * From Name
	 */
	public static final MESSAGE_CODE FROM_NAME = new MESSAGE_CODE("MESSAGE_ORANGE_FROM_NAME", "From Name");

	/**
	 * SMTP IP
	 */
	public static final MESSAGE_CODE SMTP_IP = new MESSAGE_CODE("MESSAGE_ORANGE_SMTP_IP", "SMTP IP");

	/**
	 * SMTP Port
	 */
	public static final MESSAGE_CODE SMTP_PORT = new MESSAGE_CODE("MESSAGE_ORANGE_SMTP_PORT", "SMTP Port");

	/**
	 * SMTP Account
	 */
	public static final MESSAGE_CODE SMTP_ACCOUNT = new MESSAGE_CODE("MESSAGE_ORANGE_SMTP_ACCOUNT", "SMTP Account");

	/**
	 * SMTP Password
	 */
	public static final MESSAGE_CODE SMTP_PASSWORD = new MESSAGE_CODE("MESSAGE_ORANGE_SMTP_PASSWORD", "SMTP Password");

	/**
	 * Test
	 */
	public static final MESSAGE_CODE TEST = new MESSAGE_CODE("MESSAGE_ORANGE_TEST", "Test");

	/**
	 * To
	 */
	public static final MESSAGE_CODE TO = new MESSAGE_CODE("MESSAGE_ORANGE_TO", "To");

	/**
	 * Subject
	 */
	public static final MESSAGE_CODE SUBJECT = new MESSAGE_CODE("MESSAGE_ORANGE_SUBJECT", "Subject");

	/**
	 * Content
	 */
	public static final MESSAGE_CODE CONTENT = new MESSAGE_CODE("MESSAGE_ORANGE_CONTENT", "Content");

	/**
	 * Invalid Value
	 */
	public static final MESSAGE_CODE INVALID_VALUE = new MESSAGE_CODE("MESSAGE_ORANGE_INVALID_VALUE", "Invalid {}.");

	/**
	 * Default Confirm
	 */
	public static final MESSAGE_CODE DEFAULT_CONFIRM = new MESSAGE_CODE("MESSAGE_ORANGE_DEFAULT_CONFIRM", "Do you want to update default value?");

	/**
	 * Update Environment Confirm
	 */
	public static final MESSAGE_CODE UPDATE_ENVIRONMENT_CONFIRM = new MESSAGE_CODE("MESSAGE_ORANGE_UPDATE_ENVIRONMENT_CONFIRM", "Do you want to update {} Environment ?");

	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// #Environment(DB Backup)
	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * DB Backup
	 */
	public static final MESSAGE_CODE DB_BACKUP = new MESSAGE_CODE("MESSAGE_ORANGE_DB_BACKUP", "DB Backup");

	/**
	 * Backup Period
	 */
	public static final MESSAGE_CODE BACKUP_PERIOD = new MESSAGE_CODE("MESSAGE_ORANGE_BACKUP_PERIOD", "Backup Period");

	/**
	 * Backup Offset
	 */
	public static final MESSAGE_CODE BACKUP_OFFSET = new MESSAGE_CODE("MESSAGE_ORANGE_BACKUP_OFFSET", "Backup Offset");

	/**
	 * Backup Directory
	 */
	public static final MESSAGE_CODE BACKUP_DIRECTORY = new MESSAGE_CODE("MESSAGE_ORANGE_BACKUP_DIRECTORY", "Backup Directory");

	/**
	 * Instant Backup
	 */
	public static final MESSAGE_CODE INSTANT_BACKUP = new MESSAGE_CODE("MESSAGE_ORANGE_INSTANT_BACKUP", "Instant Backup");

	/**
	 * DB Backup Completed
	 */
	public static final MESSAGE_CODE DB_BACKUP_COMPLETED = new MESSAGE_CODE("MESSAGE_ORANGE_DB_BACKUP_COMPLETED", "DB Backup Completed. Backup Absolute Path: {}");

	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// #Modeling
	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Init DB
	 */
	public static final MESSAGE_CODE INIT_DB = new MESSAGE_CODE("MESSAGE_ORANGE_INIT_DB", "Init DB");

	/**
	 * Do you want initialize DB?
	 */
	public static final MESSAGE_CODE INIT_DB_CONFIRM = new MESSAGE_CODE("MESSAGE_ORANGE_INIT_DB_CONFIRM", "Do you want initialize DB?");
	
	/**
	 * Editor
	 */
	public static final MESSAGE_CODE EDITOR = new MESSAGE_CODE("MESSAGE_ORANGE_EDITOR", "Editor");
	
	/**
	 * Add
	 */
	public static final MESSAGE_CODE ADD = new MESSAGE_CODE("MESSAGE_ORANGE_ADD", "Add");
	
	/**
	 * Remove
	 */
	public static final MESSAGE_CODE REMOVE = new MESSAGE_CODE("MESSAGE_ORANGE_REMOVE", "Remove");
	
	/**
	 * Sample
	 */
	public static final MESSAGE_CODE SAMPLE = new MESSAGE_CODE("MESSAGE_ORANGE_SAMPLE", "Sample");

	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// #Modeling - ToolTip
	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * ToolTip. NE_INFO - Name
	 */
	public static final MESSAGE_CODE TOOLTIP_NE_INFO_NAME = new MESSAGE_CODE("MESSAGE_ORANGE_TOOLTIP_NE_INFO_NAME", "NE_INFO - Name");
	
	/**
	 * ToolTip. NE_INFO - Display Name
	 */
	public static final MESSAGE_CODE TOOLTIP_NE_INFO_DISPLAYT_NAME = new MESSAGE_CODE("MESSAGE_ORANGE_TOOLTIP_NE_INFO_DISPLAYT_NAME", "NE_INFO - Display Name");
	
	/**
	 * ToolTip. NE_INFO - Display Enable
	 */
	public static final MESSAGE_CODE TOOLTIP_NE_INFO_DISPLAY_ENABLE = new MESSAGE_CODE("MESSAGE_ORANGE_TOOLTIP_NE_INFO_DISPLAY_ENABLE", "NE_INFO - Display Enable");
	
	/**
	 * ToolTip. NE_INFO - Protocol
	 */
	public static final MESSAGE_CODE TOOLTIP_NE_INFO_PROTOCOL = new MESSAGE_CODE("MESSAGE_ORANGE_TOOLTIP_NE_INFO_PROTOCOL", "NE_INFO - Protocol");
	
	/**
	 * ToolTip. NE_INFO - Monitoring
	 */
	public static final MESSAGE_CODE TOOLTIP_NE_INFO_MONITORING = new MESSAGE_CODE("MESSAGE_ORANGE_TOOLTIP_NE_INFO_MONITORING", "NE_INFO - Monitoring");
	
	/**
	 * ToolTip. NE_INFO - Filter enable
	 */
	public static final MESSAGE_CODE TOOLTIP_NE_INFO_FILTER_ENABLE = new MESSAGE_CODE("MESSAGE_ORANGE_TOOLTIP_NE_INFO_FILTER_ENABLE", "NE_INFO - Filter enable");
	
	/**
	 * ToolTip. NE_INFO - Filter script
	 */
	public static final MESSAGE_CODE TOOLTIP_NE_INFO_FILTER_SCRIPT = new MESSAGE_CODE("MESSAGE_ORANGE_TOOLTIP_NE_INFO_FILTER_SCRIPT", "NE_INFO - Filter script");
	
	/**
	 * ToolTip. NE_INFO - Filter script editor
	 */
	public static final MESSAGE_CODE TOOLTIP_NE_INFO_FILTER_SCRIPT_EDITOR = new MESSAGE_CODE("MESSAGE_ORANGE_TOOLTIP_NE_INFO_FILTER_SCRIPT_EDITOR", "NE_INFO - Filter script editor");
	
	/**
	 * ToolTip. NE_INFO - Fault enable
	 */
	public static final MESSAGE_CODE TOOLTIP_NE_INFO_FAULT_ENABLE = new MESSAGE_CODE("MESSAGE_ORANGE_TOOLTIP_NE_INFO_FAULT_ENABLE", "NE_INFO - Fault enable");
	
	/**
	 * ToolTip. NE_INFO - Audit alarm
	 */
	public static final MESSAGE_CODE TOOLTIP_NE_INFO_AUDIT_ALARM = new MESSAGE_CODE("MESSAGE_ORANGE_TOOLTIP_NE_INFO_AUDIT_ALARM", "NE_INFO - Audit alarm");
	
	/**
	 * ToolTip. NE_INFO - Event script
	 */
	public static final MESSAGE_CODE TOOLTIP_NE_INFO_EVENT_SCRIPT = new MESSAGE_CODE("MESSAGE_ORANGE_TOOLTIP_NE_INFO_EVENT_SCRIPT", "NE_INFO - Event script");
	
	/**
	 * ToolTip. NE_INFO - Event script editor
	 */
	public static final MESSAGE_CODE TOOLTIP_NE_INFO_EVENT_SCRIPT_EDITOR = new MESSAGE_CODE("MESSAGE_ORANGE_TOOLTIP_NE_INFO_EVENT_SCRIPT_EDITOR", "NE_INFO - Event script editor");
	
	/**
	 * ToolTip. NE_INFO - Notification
	 */
	public static final MESSAGE_CODE TOOLTIP_NE_INFO_NOTIFICATION = new MESSAGE_CODE("MESSAGE_ORANGE_TOOLTIP_NE_INFO_NOTIFICATION", "NE_INFO - Notification");
	
	/**
	 * ToolTip. NE_INFO - Notification add
	 */
	public static final MESSAGE_CODE TOOLTIP_NE_INFO_NOTIFICATION_ADD = new MESSAGE_CODE("MESSAGE_ORANGE_TOOLTIP_NE_INFO_NOTIFICATION_ADD", "NE_INFO - Notification add");
	
	/**
	 * ToolTip. NE_INFO - Notification edit
	 */
	public static final MESSAGE_CODE TOOLTIP_NE_INFO_NOTIFICATION_EDIT = new MESSAGE_CODE("MESSAGE_ORANGE_TOOLTIP_NE_INFO_NOTIFICATION_EDIT", "NE_INFO - Notification edit");
	
	/**
	 * ToolTip. NE_INFO - Notification remove
	 */
	public static final MESSAGE_CODE TOOLTIP_NE_INFO_NOTIFICATION_REMOVE = new MESSAGE_CODE("MESSAGE_ORANGE_TOOLTIP_NE_INFO_NOTIFICATION_REMOVE", "NE_INFO - Notification remove");
	
	/**
	 * ToolTip. NE_INFO - Stat enable
	 */
	public static final MESSAGE_CODE TOOLTIP_NE_INFO_STAT_ENABLE = new MESSAGE_CODE("MESSAGE_ORANGE_TOOLTIP_NE_INFO_STAT_ENABLE", "NE_INFO - Stat enable");
	
	/**
	 * ToolTip. NE_INFO - Stat type
	 */
	public static final MESSAGE_CODE TOOLTIP_NE_INFO_STAT_TYPE = new MESSAGE_CODE("MESSAGE_ORANGE_TOOLTIP_NE_INFO_STAT_TYPE", "NE_INFO - Stat type");
	
	/**
	 * ToolTip. NE_INFO_FIELD - Name
	 */
	public static final MESSAGE_CODE TOOLTIP_NE_INFO_FIELD_NAME = new MESSAGE_CODE("MESSAGE_ORANGE_TOOLTIP_NE_INFO_FIELD_NAME", "NE_INFO_FIELD - Name");
	
	/**
	 * ToolTip. NE_INFO_FIELD - Display Name
	 */
	public static final MESSAGE_CODE TOOLTIP_NE_INFO_FIELD_DISPLAY_NAME = new MESSAGE_CODE("MESSAGE_ORANGE_TOOLTIP_NE_INFO_FIELD_DISPLAY_NAME", "NE_INFO_FIELD - Display Name");
	
	/**
	 * ToolTip. NE_INFO_FIELD - Display Enable
	 */
	public static final MESSAGE_CODE TOOLTIP_NE_INFO_FIELD_DISPLAY_ENABLE = new MESSAGE_CODE("MESSAGE_ORANGE_TOOLTIP_NE_INFO_FIELD_DISPLAY_ENABLE", "NE_INFO_FIELD - Display Enable");
	
	/**
	 * ToolTip. NE_INFO_FIELD - Unit
	 */
	public static final MESSAGE_CODE TOOLTIP_NE_INFO_FIELD_UNIT = new MESSAGE_CODE("MESSAGE_ORANGE_TOOLTIP_NE_INFO_FIELD_UNIT", "NE_INFO_FIELD - Unit");
	
	/**
	 * ToolTip. NE_INFO_FIELD - Virtual
	 */
	public static final MESSAGE_CODE TOOLTIP_NE_INFO_FIELD_VIRTUAL = new MESSAGE_CODE("MESSAGE_ORANGE_TOOLTIP_NE_INFO_FIELD_VIRTUAL", "NE_INFO_FIELD - Virtual");
	
	/**
	 * ToolTip. NE_INFO_FIELD - Oid
	 */
	public static final MESSAGE_CODE TOOLTIP_NE_INFO_FIELD_OID = new MESSAGE_CODE("MESSAGE_ORANGE_TOOLTIP_NE_INFO_FIELD_OID", "NE_INFO_FIELD - Oid");
	
	/**
	 * ToolTip. NE_INFO_FIELD - Type remote
	 */
	public static final MESSAGE_CODE TOOLTIP_NE_INFO_FIELD_TYPE_REMOTE = new MESSAGE_CODE("MESSAGE_ORANGE_TOOLTIP_NE_INFO_FIELD_TYPE_REMOTE", "NE_INFO_FIELD - Type remote");
	
	/**
	 * ToolTip. NE_INFO_FIELD - Type local
	 */
	public static final MESSAGE_CODE TOOLTIP_NE_INFO_FIELD_TYPE_LOCAL = new MESSAGE_CODE("MESSAGE_ORANGE_TOOLTIP_NE_INFO_FIELD_TYPE_LOCAL", "NE_INFO_FIELD - Type local");
	
	/**
	 * ToolTip. NE_INFO_FIELD - Field script
	 */
	public static final MESSAGE_CODE TOOLTIP_NE_INFO_FIELD_FIELD_SCRIPT = new MESSAGE_CODE("MESSAGE_ORANGE_TOOLTIP_NE_INFO_FIELD_FIELD_SCRIPT", "NE_INFO_FIELD - Field script");
	
	/**
	 * ToolTip. NE_INFO_FIELD - Field script editor
	 */
	public static final MESSAGE_CODE TOOLTIP_NE_INFO_FIELD_FIELD_SCRIPT_EDITOR = new MESSAGE_CODE("MESSAGE_ORANGE_TOOLTIP_NE_INFO_FIELD_FIELD_SCRIPT_EDITOR", "NE_INFO_FIELD - Field script editor");
	
	/**
	 * ToolTip. NE_INFO_FIELD - Enum
	 */
	public static final MESSAGE_CODE TOOLTIP_NE_INFO_FIELD_ENUM = new MESSAGE_CODE("MESSAGE_ORANGE_TOOLTIP_NE_INFO_FIELD_ENUM", "NE_INFO_FIELD - Enum");
	
	/**
	 * ToolTip. NE_INFO_FIELD - Index
	 */
	public static final MESSAGE_CODE TOOLTIP_NE_INFO_FIELD_INDEX = new MESSAGE_CODE("MESSAGE_ORANGE_TOOLTIP_NE_INFO_FIELD_INDEX", "NE_INFO_FIELD - Index");
	
	/**
	 * ToolTip. NE_INFO_FIELD - Read
	 */
	public static final MESSAGE_CODE TOOLTIP_NE_INFO_FIELD_READ = new MESSAGE_CODE("MESSAGE_ORANGE_TOOLTIP_NE_INFO_FIELD_READ", "NE_INFO_FIELD - Read");
	
	/**
	 * ToolTip. NE_INFO_FIELD - Update
	 */
	public static final MESSAGE_CODE TOOLTIP_NE_INFO_FIELD_UPDATE = new MESSAGE_CODE("MESSAGE_ORANGE_TOOLTIP_NE_INFO_FIELD_UPDATE", "NE_INFO_FIELD - Update");
	
	/**
	 * ToolTip. NE_INFO_FIELD - Stat label
	 */
	public static final MESSAGE_CODE TOOLTIP_NE_INFO_FIELD_STAT_LABEL = new MESSAGE_CODE("MESSAGE_ORANGE_TOOLTIP_NE_INFO_FIELD_STAT_LABEL", "NE_INFO_FIELD - Stat label");
	
	/**
	 * ToolTip. NE_INFO_FIELD - Stat enable
	 */
	public static final MESSAGE_CODE TOOLTIP_NE_INFO_FIELD_STAT_ENABLE = new MESSAGE_CODE("MESSAGE_ORANGE_TOOLTIP_NE_INFO_FIELD_STAT_ENABLE", "NE_INFO_FIELD - Stat enable");
	
	/**
	 * ToolTip. NE_INFO_FIELD - Chart Default
	 */
	public static final MESSAGE_CODE TOOLTIP_NE_INFO_FIELD_CHART_DEFAULT = new MESSAGE_CODE("MESSAGE_ORANGE_TOOLTIP_NE_INFO_FIELD_CHART_DEFAULT", "NE_INFO_FIELD - Chart Default");
	
	/**
	 * ToolTip. NE_INFO_FIELD - Stat save
	 */
	public static final MESSAGE_CODE TOOLTIP_NE_INFO_FIELD_STAT_SAVE = new MESSAGE_CODE("MESSAGE_ORANGE_TOOLTIP_NE_INFO_FIELD_STAT_SAVE", "NE_INFO_FIELD - Stat save");
	
	/**
	 * ToolTip. NE_INFO_FIELD - Stat aggr
	 */
	public static final MESSAGE_CODE TOOLTIP_NE_INFO_FIELD_STAT_AGGR = new MESSAGE_CODE("MESSAGE_ORANGE_TOOLTIP_NE_INFO_FIELD_STAT_AGGR", "NE_INFO_FIELD - Stat aggr");
	
	/**
	 * ToolTip. NE_INFO_FIELD - Thr enable
	 */
	public static final MESSAGE_CODE TOOLTIP_NE_INFO_FIELD_THR_ENABLE = new MESSAGE_CODE("MESSAGE_ORANGE_TOOLTIP_NE_INFO_FIELD_THR_ENABLE", "NE_INFO_FIELD - Thr enable");
	
	/**
	 * ToolTip. NE_INFO_FIELD - Thr event
	 */
	public static final MESSAGE_CODE TOOLTIP_NE_INFO_FIELD_THR_EVENT = new MESSAGE_CODE("MESSAGE_ORANGE_TOOLTIP_NE_INFO_FIELD_THR_EVENT", "NE_INFO_FIELD - Thr event");
	
	/**
	 * ToolTip. NE_INFO_FIELD - Thr type
	 */
	public static final MESSAGE_CODE TOOLTIP_NE_INFO_FIELD_THR_TYPE = new MESSAGE_CODE("MESSAGE_ORANGE_TOOLTIP_NE_INFO_FIELD_THR_TYPE", "NE_INFO_FIELD - Thr type");
	
	/**
	 * ToolTip. NE_INFO_FIELD - Thr critical enable
	 */
	public static final MESSAGE_CODE TOOLTIP_NE_INFO_FIELD_THR_CRITICAL_ENABLE = new MESSAGE_CODE("MESSAGE_ORANGE_TOOLTIP_NE_INFO_FIELD_THR_CRITICAL_ENABLE", "NE_INFO_FIELD - Thr critical enable");
	
	/**
	 * ToolTip. NE_INFO_FIELD - Thr critical min
	 */
	public static final MESSAGE_CODE TOOLTIP_NE_INFO_FIELD_THR_CRITICAL_MIN = new MESSAGE_CODE("MESSAGE_ORANGE_TOOLTIP_NE_INFO_FIELD_THR_CRITICAL_MIN", "NE_INFO_FIELD - Thr critical min");
	
	/**
	 * ToolTip. NE_INFO_FIELD - Thr critical max
	 */
	public static final MESSAGE_CODE TOOLTIP_NE_INFO_FIELD_THR_CRITICAL_MAX = new MESSAGE_CODE("MESSAGE_ORANGE_TOOLTIP_NE_INFO_FIELD_THR_CRITICAL_MAX", "NE_INFO_FIELD - Thr critical max");
	
	/**
	 * ToolTip. NE_INFO_FIELD - Thr major enable
	 */
	public static final MESSAGE_CODE TOOLTIP_NE_INFO_FIELD_THR_MAJOR_ENABLE = new MESSAGE_CODE("MESSAGE_ORANGE_TOOLTIP_NE_INFO_FIELD_THR_MAJOR_ENABLE", "NE_INFO_FIELD - Thr major enable");
	
	/**
	 * ToolTip. NE_INFO_FIELD - Thr major min
	 */
	public static final MESSAGE_CODE TOOLTIP_NE_INFO_FIELD_THR_MAJOR_MIN = new MESSAGE_CODE("MESSAGE_ORANGE_TOOLTIP_NE_INFO_FIELD_THR_MAJOR_MIN", "NE_INFO_FIELD - Thr major min");
	
	/**
	 * ToolTip. NE_INFO_FIELD - Thr major max
	 */
	public static final MESSAGE_CODE TOOLTIP_NE_INFO_FIELD_THR_MAJOR_MAX = new MESSAGE_CODE("MESSAGE_ORANGE_TOOLTIP_NE_INFO_FIELD_THR_MAJOR_MAX", "NE_INFO_FIELD - Thr major max");
	
	/**
	 * ToolTip. NE_INFO_FIELD - Thr minor enable
	 */
	public static final MESSAGE_CODE TOOLTIP_NE_INFO_FIELD_THR_MINOR_ENABLE = new MESSAGE_CODE("MESSAGE_ORANGE_TOOLTIP_NE_INFO_FIELD_THR_MINOR_ENABLE", "NE_INFO_FIELD - Thr minor enable");
	
	/**
	 * ToolTip. NE_INFO_FIELD - Thr minor min
	 */
	public static final MESSAGE_CODE TOOLTIP_NE_INFO_FIELD_THR_MINOR_MIN = new MESSAGE_CODE("MESSAGE_ORANGE_TOOLTIP_NE_INFO_FIELD_THR_MINOR_MIN", "NE_INFO_FIELD - Thr minor min");
	
	/**
	 * ToolTip. NE_INFO_FIELD - Thr minor max
	 */
	public static final MESSAGE_CODE TOOLTIP_NE_INFO_FIELD_THR_MINOR_MAX = new MESSAGE_CODE("MESSAGE_ORANGE_TOOLTIP_NE_INFO_FIELD_THR_MINOR_MAX", "NE_INFO_FIELD - Thr minor max");
	
	/**
	 * ToolTip. NE - Manufacturer
	 */
	public static final MESSAGE_CODE TOOLTIP_NE_MANUFACTURER = new MESSAGE_CODE("MESSAGE_ORANGE_TOOLTIP_NE_MANUFACTURER", "NE - Manufacturer");
	
	/**
	 * ToolTip. NE - Oui
	 */
	public static final MESSAGE_CODE TOOLTIP_NE_OUI = new MESSAGE_CODE("MESSAGE_ORANGE_TOOLTIP_NE_OUI", "NE - Oui");
	
	/**
	 * ToolTip. NE - Product class
	 */
	public static final MESSAGE_CODE TOOLTIP_NE_PRODUCT_CLASS = new MESSAGE_CODE("MESSAGE_ORANGE_TOOLTIP_NE_PRODUCT_CLASS", "NE - Product class");
	
	/**
	 * ToolTip. NE - Ne oid
	 */
	public static final MESSAGE_CODE TOOLTIP_NE_NE_OID = new MESSAGE_CODE("MESSAGE_ORANGE_TOOLTIP_NE_NE_OID", "NE - Ne oid");
	
	/**
	 * ToolTip. NE - Ne icon
	 */
	public static final MESSAGE_CODE TOOLTIP_NE_NE_ICON = new MESSAGE_CODE("MESSAGE_ORANGE_TOOLTIP_NE_NE_ICON", "NE - Ne icon");
	
	/**
	 * ToolTip. NE - Ne icon add
	 */
	public static final MESSAGE_CODE TOOLTIP_NE_NE_ICON_ADD = new MESSAGE_CODE("MESSAGE_ORANGE_TOOLTIP_NE_NE_ICON_ADD", "NE - Ne icon add");
	
	/**
	 * ToolTip. NE - Ne infos
	 */
	public static final MESSAGE_CODE TOOLTIP_NE_NE_INFOS = new MESSAGE_CODE("MESSAGE_ORANGE_TOOLTIP_NE_NE_INFOS", "NE - Ne infos");
	
	/**
	 * ToolTip. ENUM - Name
	 */
	public static final MESSAGE_CODE TOOLTIP_ENUM_NAME = new MESSAGE_CODE("MESSAGE_ORANGE_TOOLTIP_ENUM_NAME", "ENUM - Name");
	
	/**
	 * ToolTip. ENUM_FIELD - Name
	 */
	public static final MESSAGE_CODE TOOLTIP_ENUM_FIELD_NAME = new MESSAGE_CODE("MESSAGE_ORANGE_TOOLTIP_ENUM_FIELD_NAME", "ENUM_FIELD - Name");
	
	/**
	 * ToolTip. ENUM_FIELD - Value
	 */
	public static final MESSAGE_CODE TOOLTIP_ENUM_FIELD_VALUE = new MESSAGE_CODE("MESSAGE_ORANGE_TOOLTIP_ENUM_FIELD_VALUE", "ENUM_FIELD - Value");
	
	/**
	 * ToolTip. EVENT - Specific problem
	 */
	public static final MESSAGE_CODE TOOLTIP_EVENT_SPECIFIC_PROBLEM = new MESSAGE_CODE("MESSAGE_ORANGE_TOOLTIP_EVENT_SPECIFIC_PROBLEM", "EVENT - Specific problem");
	
	/**
	 * ToolTip. EVENT - Alarm
	 */
	public static final MESSAGE_CODE TOOLTIP_EVENT_ALARM = new MESSAGE_CODE("MESSAGE_ORANGE_TOOLTIP_EVENT_ALARM", "EVENT - Alarm");
	
	/**
	 * ToolTip. EVENT - Audit alarm
	 */
	public static final MESSAGE_CODE TOOLTIP_EVENT_AUDIT_ALARM = new MESSAGE_CODE("MESSAGE_ORANGE_TOOLTIP_EVENT_AUDIT_ALARM", "EVENT - Audit alarm");

}
