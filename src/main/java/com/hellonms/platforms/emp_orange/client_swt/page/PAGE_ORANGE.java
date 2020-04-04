package com.hellonms.platforms.emp_orange.client_swt.page;

import com.hellonms.platforms.emp_core.share.language.UtilLanguage;
import com.hellonms.platforms.emp_onion.client_swt.widget.viewpart.PAGE;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;

/**
 * <p>
 * PAGE
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class PAGE_ORANGE {

	public static final PAGE TOOLBAR = new PAGE("", "toolbar", false);
	public static final PAGE NETWORK_TREE = new PAGE(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NETWORK_TREE), "network.tree", false);
	public static final PAGE NETWORK_VIEW = new PAGE(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NETWORK_VIEW), "network.view", false);
	public static final PAGE NE_INFO = new PAGE(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NE_INFO), "network.ne_info", true);
	public static final PAGE NE_STATISTICS = new PAGE(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NE_STATISTICS), "network.ne_statistics", true);
	public static final PAGE NE_THRESHOLD = new PAGE(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NE_THRESHOLD), "network.ne_threshold", true);
	public static final PAGE ALARM_ACTIVE = new PAGE(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.ALARM_ACTIVE), "fault.alarm.active", true);
	public static final PAGE ALARM_HISTORY = new PAGE(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.ALARM_HISTORY), "fault.alarm.history", true);
	public static final PAGE EVENT = new PAGE(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.EVENT), "fault.event", true);
	public static final PAGE ALARM_STATISTICS = new PAGE(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.ALARM_STATISTICS), "fault.alarm.statistics", true);
	public static final PAGE USER = new PAGE(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.USER_MANAGEMENT), "security.user", true);
	public static final PAGE OPERATION_LOG = new PAGE(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.OPERATION_LOG), "security.operation.log", true);
	public static final PAGE EVENT_CONSOLE = new PAGE(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.CONSOLE_TITLE, MESSAGE_CODE_ORANGE.EVENT), "fault.event.console", false);
	public static final PAGE ALARM_ACTIVE_CONSOLE = new PAGE(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.CONSOLE_TITLE, MESSAGE_CODE_ORANGE.ALARM_ACTIVE), "fault.alarm.active.console", false);
	public static final PAGE ALARM_HISTORY_CONSOLE = new PAGE(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.CONSOLE_TITLE, MESSAGE_CODE_ORANGE.ALARM_HISTORY), "fault.alarm.history.console", false);;

}
