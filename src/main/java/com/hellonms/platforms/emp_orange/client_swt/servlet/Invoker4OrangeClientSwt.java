/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.client_swt.servlet;

import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.hellonms.platforms.emp_core.server.log.BlackBox;
import com.hellonms.platforms.emp_core.server.log.LEVEL;
import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.server.transaction.UtilTransaction;
import com.hellonms.platforms.emp_core.server.workflow.WorkflowMap;
import com.hellonms.platforms.emp_core.share.error.ERROR_CODE;
import com.hellonms.platforms.emp_core.share.error.ERROR_CODE_CORE;
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
import com.hellonms.platforms.emp_orange.server.invoker.Invoker4OrangeIf;
import com.hellonms.platforms.emp_orange.server.util.UtilOperationLog;
import com.hellonms.platforms.emp_orange.share.error.ERROR_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_EVENT;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO;
import com.hellonms.platforms.emp_orange.share.model.environment.preference.PREFERENCE_CODE_ORANGE;
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
import com.hellonms.platforms.emp_orange.share.model.security.operation_log.OPERATION_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.security.user.Model4User;
import com.hellonms.platforms.emp_orange.share.model.security.user_group.Model4UserGroup;
import com.hellonms.platforms.emp_orange.share.model.security.user_session.Model4UserSession;
import com.hellonms.platforms.emp_util.cache.UtilCache;
import com.hellonms.platforms.emp_util.date.UtilDate;
import com.hellonms.platforms.emp_util.string.UtilString;

/**
 * <p>
 * Invoker4OrangeClientSwtIf
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 20.
 * @modified 2015. 5. 20.
 * @author cchyun
 *
 */
public class Invoker4OrangeClientSwt implements Invoker4OrangeClientSwtIf {

	protected interface ClientSwtTaskIf {

		public Object run(Invoker4OrangeIf invoker, EmpContext context, String user_session_key, Map<String, Object> parm_req, Map<String, Object> parm_res) throws EmpException;

		public void transaction_log(EmpContext context, Map<String, Object> parm_req, Map<String, Object> parm_res, Exception exception);

	}

	private static final String TRANSACTION = "SWT";

	private static BlackBox blackBox = new BlackBox(Invoker4OrangeClientSwt.class);

	protected Object execute(Invoker4OrangeClientSwtReqeust request, ClientSwtTaskIf task) throws EmpException {
		LinkedHashMap<String, Object> parm_req = new LinkedHashMap<String, Object>();
		LinkedHashMap<String, Object> parm_res = new LinkedHashMap<String, Object>();
		Object result = null;
		Exception exception = null;
		EmpContext context = new EmpContext(null);
		try {
			Invoker4OrangeIf invoker = (Invoker4OrangeIf) WorkflowMap.getInvoker(Invoker4OrangeIf.class);
			if (!invoker.isReady(context)) {
				throw new EmpException(ERROR_CODE_CORE.SERVER_NOTREADY);
			}
			String user_session_key = loadUser_session_key(request);
			result = task.run(invoker, context, user_session_key, parm_req, parm_res);
			context.commit();
			return result;
		} catch (EmpException e) {
			if (blackBox.isEnabledFor(LEVEL.Fatal)) {
				blackBox.log(LEVEL.Fatal, context, e);
			}
			context.rollback();

			exception = e;
			throw e;
		} catch (Exception e) {
			if (blackBox.isEnabledFor(LEVEL.Fatal)) {
				blackBox.log(LEVEL.Fatal, context, e);
			}
			context.rollback();

			exception = e;
			throw new EmpException(e, ERROR_CODE.ERROR_UNKNOWN);
		} finally {
			try {
				context.close();
			} finally {
				task.transaction_log(context, parm_req, parm_res, exception);
			}
		}
	}

	public void saveUser_session_key(Invoker4OrangeClientSwtReqeust request, String user_session_key) {
		request.setProperty(Invoker4OrangeClientSwtReqeust.USER_SESSION_KEY, user_session_key);
	}

	private String loadUser_session_key(Invoker4OrangeClientSwtReqeust request) {
		return request.getProperty(Invoker4OrangeClientSwtReqeust.USER_SESSION_KEY);
	}

	public void clearUser_session_key(Invoker4OrangeClientSwtReqeust request) {
		request.setProperty(Invoker4OrangeClientSwtReqeust.USER_SESSION_KEY, null);
	}

	private void createOperationLog(EmpContext context, Model4OperationLog model4OperationLog) {
		try {
			Invoker4OrangeIf invoker = (Invoker4OrangeIf) WorkflowMap.getInvoker(Invoker4OrangeIf.class);
			invoker.createOperationLog(context, model4OperationLog);
		} catch (EmpException e) {
			if (blackBox.isEnabledFor(LEVEL.Fatal)) {
				blackBox.log(LEVEL.Fatal, context, e);
			}
		} catch (Exception e) {
			if (blackBox.isEnabledFor(LEVEL.Fatal)) {
				blackBox.log(LEVEL.Fatal, context, e);
			}
		}
	}

	@Override
	public long testSession(Invoker4OrangeClientSwtReqeust request) throws EmpException {
		return (long) execute(request, new ClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeIf invoker, EmpContext context, String user_session_key, Map<String, Object> parm_req, Map<String, Object> parm_res) throws EmpException {
				invoker.queryUserSession(context, user_session_key);
				return System.currentTimeMillis();
			}

			@Override
			public void transaction_log(EmpContext context, Map<String, Object> parm_req, Map<String, Object> parm_res, Exception exception) {
				// UtilTransaction.transaction_log(context, TRANSACTION, OPERATION_CODE_ORANGE.SECURITY_USERSESSION_QUERY, parm_req, exception == null, parm_res, exception);
			}
		});
	}

	@Override
	public Model4About getAbout(final Invoker4OrangeClientSwtReqeust request) throws EmpException {
		return (Model4About) execute(request, new ClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeIf invoker, EmpContext context, String user_session_key, Map<String, Object> parm_req, Map<String, Object> parm_res) throws EmpException {
				return invoker.getAbout(context);
			}

			@Override
			public void transaction_log(EmpContext context, Map<String, Object> parm_req, Map<String, Object> parm_res, Exception exception) {
				UtilTransaction.transaction_log(context, TRANSACTION, OPERATION_CODE_ORANGE.HELP_ABOUT_QUERY, parm_req, exception == null, parm_res, exception);
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String> queryLanguage(final Invoker4OrangeClientSwtReqeust request, final LANGUAGE language) throws EmpException {
		return (Map<String, String>) execute(request, new ClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeIf invoker, EmpContext context, String user_session_key, Map<String, Object> parm_req, Map<String, Object> parm_res) throws EmpException {
				return invoker.queryLanguage(context, language);
			}

			@Override
			public void transaction_log(EmpContext context, Map<String, Object> parm_req, Map<String, Object> parm_res, Exception exception) {
			}
		});
	}

	@Override
	public void createImage(Invoker4OrangeClientSwtReqeust request, String path, String filename, byte[] filedata) throws EmpException {
		execute(request, new ClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeIf invoker, EmpContext context, String user_session_key, Map<String, Object> parm_req, Map<String, Object> parm_res) throws EmpException {
				invoker.queryUserSession(context, user_session_key);
				invoker.createImage(context, path, filename, filedata);
				return null;
			}

			@Override
			public void transaction_log(EmpContext context, Map<String, Object> parm_req, Map<String, Object> parm_res, Exception exception) {
			}
		});
	}

	@Override
	public byte[] queryImage(final Invoker4OrangeClientSwtReqeust request, final String path, final int width, final int height, final SEVERITY severity) throws EmpException {
		return (byte[]) execute(request, new ClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeIf invoker, EmpContext context, String user_session_key, Map<String, Object> parm_req, Map<String, Object> parm_res) throws EmpException {
				invoker.queryUserSession(context, user_session_key);
				return invoker.queryImage(context, path, width, height, severity);
			}

			@Override
			public void transaction_log(EmpContext context, Map<String, Object> parm_req, Map<String, Object> parm_res, Exception exception) {
			}
		});
	}

	@Override
	public byte[] queryImage(final Invoker4OrangeClientSwtReqeust request, final String path) throws EmpException {
		return (byte[]) execute(request, new ClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeIf invoker, EmpContext context, String user_session_key, Map<String, Object> parm_req, Map<String, Object> parm_res) throws EmpException {
				invoker.queryUserSession(context, user_session_key);
				return invoker.queryImage(context, path);
			}

			@Override
			public void transaction_log(EmpContext context, Map<String, Object> parm_req, Map<String, Object> parm_res, Exception exception) {
			}
		});
	}

	@Override
	public String[] queryListImagePath(final Invoker4OrangeClientSwtReqeust request, final String path, final String[] extensions) throws EmpException {
		return (String[]) execute(request, new ClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeIf invoker, EmpContext context, String user_session_key, Map<String, Object> parm_req, Map<String, Object> parm_res) throws EmpException {
				invoker.queryUserSession(context, user_session_key);
				return invoker.queryListImagePath(context, path, extensions);
			}

			@Override
			public void transaction_log(EmpContext context, Map<String, Object> parm_req, Map<String, Object> parm_res, Exception exception) {
			}
		});
	}

	@Override
	public byte[] querySound(final Invoker4OrangeClientSwtReqeust request, final String path) throws EmpException {
		return (byte[]) execute(request, new ClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeIf invoker, EmpContext context, String user_session_key, Map<String, Object> parm_req, Map<String, Object> parm_res) throws EmpException {
				return invoker.querySound(context, path);
			}

			@Override
			public void transaction_log(EmpContext context, Map<String, Object> parm_req, Map<String, Object> parm_res, Exception exception) {
			}
		});
	}

	@Override
	public byte[] queryEmp_model(Invoker4OrangeClientSwtReqeust request) throws EmpException {
		return (byte[]) execute(request, new ClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeIf invoker, EmpContext context, String user_session_key, Map<String, Object> parm_req, Map<String, Object> parm_res) throws EmpException {
				return invoker.queryEmp_model(context);
			}

			@Override
			public void transaction_log(EmpContext context, Map<String, Object> parm_req, Map<String, Object> parm_res, Exception exception) {
			}
		});
	}

	@Override
	public byte[] updateEmp_model(Invoker4OrangeClientSwtReqeust request, final byte[] emp_model_data) throws EmpException {
		return (byte[]) execute(request, new ClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeIf invoker, EmpContext context, String user_session_key, Map<String, Object> parm_req, Map<String, Object> parm_res) throws EmpException {
				byte[] model = invoker.updateEmp_model(context, emp_model_data);
				Model4Preference preference = invoker.queryPreference(context, PREFERENCE_CODE_ORANGE.PREFERENCE_ORANGE_NETWORK_NEDETAIL_DISPLAYORDER);
				if (preference != null) {
					preference.setPreference("");
					invoker.updatePreference(context, preference);
				}
				UtilCache.removeAll(NETWORK_NEDETAIL_DISPLAYORDER);
				return model;
			}

			@Override
			public void transaction_log(EmpContext context, Map<String, Object> parm_req, Map<String, Object> parm_res, Exception exception) {
			}
		});
	}

	@Override
	public void truncate(Invoker4OrangeClientSwtReqeust request, final boolean ne, final boolean ne_info, final boolean fault, final boolean operation_log) throws EmpException {
		execute(request, new ClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeIf invoker, EmpContext context, String user_session_key, Map<String, Object> parm_req, Map<String, Object> parm_res) throws EmpException {
				invoker.truncate(context, ne, ne_info, fault, operation_log);
				return null;
			}

			@Override
			public void transaction_log(EmpContext context, Map<String, Object> parm_req, Map<String, Object> parm_res, Exception exception) {
			}
		});
	}

	@Override
	public Model4Preference queryPreference(Invoker4OrangeClientSwtReqeust request, final PREFERENCE_CODE preference_code) throws EmpException {
		return (Model4Preference) execute(request, new ClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeIf invoker, EmpContext context, String user_session_key, Map<String, Object> parm_req, Map<String, Object> parm_res) throws EmpException {
				parm_req.put("preference_code", preference_code);
				invoker.queryUserSession(context, user_session_key);

				return invoker.queryPreference(context, preference_code);
			}

			@Override
			public void transaction_log(EmpContext context, Map<String, Object> parm_req, Map<String, Object> parm_res, Exception exception) {
				UtilTransaction.transaction_log(context, TRANSACTION, OPERATION_CODE_ORANGE.HELP_PREFERENCE_QUERY, parm_req, exception == null, parm_res, exception);
			}
		});
	}

	@Override
	public Model4Preference[] queryListPreference(final Invoker4OrangeClientSwtReqeust request, final String function_group, final String function, final String preference) throws EmpException {
		return (Model4Preference[]) execute(request, new ClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeIf invoker, EmpContext context, String user_session_key, Map<String, Object> parm_req, Map<String, Object> parm_res) throws EmpException {
				parm_req.put("function_group", function_group);
				parm_req.put("function", function);
				parm_req.put("preference", preference);
				invoker.queryUserSession(context, user_session_key);

				return invoker.queryListPreference(context, function_group, function, preference);
			}

			@Override
			public void transaction_log(EmpContext context, Map<String, Object> parm_req, Map<String, Object> parm_res, Exception exception) {
				UtilTransaction.transaction_log(context, TRANSACTION, OPERATION_CODE_ORANGE.HELP_PREFERENCE_QUERY, parm_req, exception == null, parm_res, exception);
			}
		});
	}

	@Override
	public Model4Preference[] updateListPreference(final Invoker4OrangeClientSwtReqeust request, final Model4Preference[] preferences) throws EmpException {
		return (Model4Preference[]) execute(request, new ClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeIf invoker, EmpContext context, String user_session_key, Map<String, Object> parm_req, Map<String, Object> parm_res) throws EmpException {
				List<String> update_values = new ArrayList<String>();
				for (Model4Preference preference : preferences) {
					update_values.add(UtilString.format("[preference_code={}, preference={}]", preference.getPreference_code(), preference.getPreference()));
				}
				parm_req.put("preference_list", update_values);
				invoker.queryUserSession(context, user_session_key);

				return invoker.updateListPreference(context, preferences);
			}

			@Override
			public void transaction_log(EmpContext context, Map<String, Object> parm_req, Map<String, Object> parm_res, Exception exception) {
				UtilTransaction.transaction_log(context, TRANSACTION, OPERATION_CODE_ORANGE.HELP_PREFERENCE_UPDATE, parm_req, exception == null, parm_res, exception);
			}
		});
	}

	public File backupDatabaseByUser(final Invoker4OrangeClientSwtReqeust request) throws EmpException {
		return (File) execute(request, new ClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeIf invoker, EmpContext context, String user_session_key, Map<String, Object> parm_req, Map<String, Object> parm_res) throws EmpException {
				invoker.queryUserSession(context, user_session_key);
				return invoker.backupDatabaseByUser(context);
			}

			@Override
			public void transaction_log(EmpContext context, Map<String, Object> parm_req, Map<String, Object> parm_res, Exception exception) {
				UtilTransaction.transaction_log(context, TRANSACTION, OPERATION_CODE_ORANGE.HELP_DATABASE_BACKUP, parm_req, exception == null, parm_res, exception);
				createOperationLog(context, UtilOperationLog.newInstance(context, OPERATION_CODE_ORANGE.HELP_DATABASE_BACKUP, exception, UtilTransaction.toString(parm_req, parm_res)));
			}
		});
	}

	@Override
	public Model4UserSession queryUserSession(final Invoker4OrangeClientSwtReqeust request) throws EmpException {
		Model4UserSession model4UserSession = (Model4UserSession) execute(request, new ClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeIf invoker, EmpContext context, String user_session_key, Map<String, Object> parm_req, Map<String, Object> parm_res) throws EmpException {
				return user_session_key == null ? null : invoker.queryUserSession(context, user_session_key);
			}

			@Override
			public void transaction_log(EmpContext context, Map<String, Object> parm_req, Map<String, Object> parm_res, Exception exception) {
				// UtilTransaction.transaction_log(context, TRANSACTION, OPERATION_CODE_ORANGE.SECURITY_USERSESSION_QUERY, parm_req, exception == null, parm_res, exception);
			}
		});
		return model4UserSession;
	}

	@Override
	public String login(final Invoker4OrangeClientSwtReqeust request, final String user_account, final String password) throws EmpException {
		String user_session_key = (String) execute(request, new ClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeIf invoker, EmpContext context, String user_session_key, Map<String, Object> parm_req, Map<String, Object> parm_res) throws EmpException {
				parm_req.put("user_account", user_account);

				HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
				String userIp = req.getRemoteAddr();
				user_session_key = invoker.login(context, user_account, password, userIp);
				return user_session_key;
			}

			@Override
			public void transaction_log(EmpContext context, Map<String, Object> parm_req, Map<String, Object> parm_res, Exception exception) {
				UtilTransaction.transaction_log(context, TRANSACTION, OPERATION_CODE_ORANGE.SECURITY_USERSESSION_LOGIN, parm_req, exception == null, parm_res, exception);
				createOperationLog(context, UtilOperationLog.newInstance(context, OPERATION_CODE_ORANGE.SECURITY_USERSESSION_LOGIN, exception, UtilTransaction.toString(parm_req, parm_res)));
			}
		});
		return user_session_key;
	}

	@Override
	public void logout(final Invoker4OrangeClientSwtReqeust request) throws EmpException {
		execute(request, new ClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeIf invoker, EmpContext context, String user_session_key, Map<String, Object> parm_req, Map<String, Object> parm_res) throws EmpException {
				invoker.queryUserSession(context, user_session_key);
				parm_req.put("user_account", context.getUser_account());

				invoker.logout(context, user_session_key);
				return null;
			}

			@Override
			public void transaction_log(EmpContext context, Map<String, Object> parm_req, Map<String, Object> parm_res, Exception exception) {
				UtilTransaction.transaction_log(context, TRANSACTION, OPERATION_CODE_ORANGE.SECURITY_USERSESSION_LOGOUT, parm_req, exception == null, parm_res, exception);
				createOperationLog(context, UtilOperationLog.newInstance(context, OPERATION_CODE_ORANGE.SECURITY_USERSESSION_LOGOUT, exception, UtilTransaction.toString(parm_req, parm_res)));
			}
		});
	}

	@Override
	public OPERATION_CODE[] getListOperationCode(final Invoker4OrangeClientSwtReqeust request) throws EmpException {
		return (OPERATION_CODE[]) execute(request, new ClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeIf invoker, EmpContext context, String user_session_key, Map<String, Object> parm_req, Map<String, Object> parm_res) throws EmpException {
				invoker.queryUserSession(context, user_session_key);
				return invoker.getListOperation_code(context);
			}

			@Override
			public void transaction_log(EmpContext context, Map<String, Object> parm_req, Map<String, Object> parm_res, Exception exception) {
			}
		});
	}

	@Override
	public ModelDisplay4Network queryNetwork(final Invoker4OrangeClientSwtReqeust request, final long update_seq_network, final long update_seq_fault) throws EmpException {
		return (ModelDisplay4Network) execute(request, new ClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeIf invoker, EmpContext context, String user_session_key, Map<String, Object> parm_req, Map<String, Object> parm_res) throws EmpException {
				long curr_update_seq_network = invoker.queryCurrUpdate_seq_network(context);
				long curr_update_seq_fault = invoker.queryCurrUpdate_seq_fault(context);

				if (curr_update_seq_network != update_seq_network || curr_update_seq_fault != update_seq_fault) {
					invoker.queryUserSession(context, user_session_key);

					ModelDisplay4Network modelDisplay4Network = new ModelDisplay4Network();
					modelDisplay4Network.setSequenceNetwork(curr_update_seq_network);
					modelDisplay4Network.setSequenceFault(curr_update_seq_fault);

					TreeNode4NeGroup treeNode4NeGroup = invoker.queryTreeNeGroup(context);
					modelDisplay4Network.setTreeNode4NeGroup(treeNode4NeGroup);

					Model4Ne[] model4Nes = invoker.queryListNe(context, 0, 999999);
					modelDisplay4Network.setModel4Nes(model4Nes);

					Model4NetworkLink[] model4NetworkLinks = invoker.queryListNetworkLink(context, 0, 999999);
					modelDisplay4Network.setModel4NetworkLinks(model4NetworkLinks);

					Model4AlarmSummary[] model4AlarmSummaries = invoker.queryListAlarmSummary(context);
					modelDisplay4Network.setModel4AlarmSummaries(model4AlarmSummaries);

					Model4Event[] model4Events = invoker.queryListEventByConsole(context, 0, ModelDisplay4Network.MAX_CONSOLE_COUNT);
					modelDisplay4Network.setModel4Events(model4Events);

					Model4Alarm[] model4AlarmActives = invoker.queryListAlarmActiveByNeGroup(context, Model4NeGroup.ROOT_NE_GROUP_ID, null, null, 0, ModelDisplay4Network.MAX_CONSOLE_COUNT);
					modelDisplay4Network.setModel4AlarmActives(model4AlarmActives);

					Model4Alarm[] model4AlarmHistories = invoker.queryListAlarmByConsole(context, 0, ModelDisplay4Network.MAX_CONSOLE_COUNT);
					modelDisplay4Network.setModel4AlarmHistories(model4AlarmHistories);

					return modelDisplay4Network;
				} else {
					parm_req.put("doLog", false);
					return null;
				}
			}

			@Override
			public void transaction_log(EmpContext context, Map<String, Object> parm_req, Map<String, Object> parm_res, Exception exception) {
				if (!parm_req.containsKey("doLog")) {
					UtilTransaction.transaction_log(context, TRANSACTION, OPERATION_CODE_ORANGE.NETWORK_TOPOLOGY_QUERY, parm_req, exception == null, parm_res, exception);
				}
			}
		});
	}

	@Override
	public Model4NeGroup createNeGroup(final Invoker4OrangeClientSwtReqeust request, final Model4NeGroup ne_group) throws EmpException {
		return (Model4NeGroup) execute(request, new ClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeIf invoker, EmpContext context, String user_session_key, Map<String, Object> parm_req, Map<String, Object> parm_res) throws EmpException {
				parm_req.put("parent_ne_group_id", ne_group.getParent_ne_group_id());
				parm_req.put("ne_group_name", ne_group.getNe_group_name());
				invoker.queryUserSession(context, user_session_key);

				Model4NeGroup ne_group_created = invoker.createNeGroup(context, ne_group);
				parm_res.put("ne_group_id", ne_group_created.getNe_group_id());
				return ne_group_created;
			}

			@Override
			public void transaction_log(EmpContext context, Map<String, Object> parm_req, Map<String, Object> parm_res, Exception exception) {
				UtilTransaction.transaction_log(context, TRANSACTION, OPERATION_CODE_ORANGE.NETWORK_NEGROUP_CREATE, parm_req, exception == null, parm_res, exception);
				createOperationLog(context, UtilOperationLog.newInstance(context, OPERATION_CODE_ORANGE.NETWORK_NEGROUP_CREATE, exception, UtilTransaction.toString(parm_req, parm_res)));
			}
		});
	}

	@Override
	public Model4NeGroup[] queryListNeGroup(final Invoker4OrangeClientSwtReqeust request) throws EmpException {
		return (Model4NeGroup[]) execute(request, new ClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeIf invoker, EmpContext context, String user_session_key, Map<String, Object> parm_req, Map<String, Object> parm_res) throws EmpException {
				invoker.queryUserSession(context, user_session_key);

				Model4NeGroup[] ne_group_list = invoker.queryListNeGroup(context, 0, 999999);
				parm_res.put("count", ne_group_list.length);
				return ne_group_list;
			}

			@Override
			public void transaction_log(EmpContext context, Map<String, Object> parm_req, Map<String, Object> parm_res, Exception exception) {
				UtilTransaction.transaction_log(context, TRANSACTION, OPERATION_CODE_ORANGE.NETWORK_NEGROUP_QUERY, parm_req, exception == null, parm_res, exception);
			}
		});
	}

	@Override
	public Model4NeGroup[] queryListNeGroup(final Invoker4OrangeClientSwtReqeust request, final int ne_group_id) throws EmpException {
		return (Model4NeGroup[]) execute(request, new ClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeIf invoker, EmpContext context, String user_session_key, Map<String, Object> parm_req, Map<String, Object> parm_res) throws EmpException {
				parm_req.put("ne_group_id", ne_group_id);
				invoker.queryUserSession(context, user_session_key);

				Model4NeGroup[] ne_group_list = invoker.queryListNeGroup(context, ne_group_id, 0, 999999);
				parm_res.put("count", ne_group_list.length);
				return ne_group_list;
			}

			@Override
			public void transaction_log(EmpContext context, Map<String, Object> parm_req, Map<String, Object> parm_res, Exception exception) {
				UtilTransaction.transaction_log(context, TRANSACTION, OPERATION_CODE_ORANGE.NETWORK_NEGROUP_QUERY, parm_req, exception == null, parm_res, exception);
			}
		});
	}

	@Override
	public TreeNode4NeGroup queryTreeNeGroup(final Invoker4OrangeClientSwtReqeust request) throws EmpException {
		return (TreeNode4NeGroup) execute(request, new ClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeIf invoker, EmpContext context, String user_session_key, Map<String, Object> parm_req, Map<String, Object> parm_res) throws EmpException {
				parm_req.put("mode", "tree");
				invoker.queryUserSession(context, user_session_key);
				return invoker.queryTreeNeGroup(context);
			}

			@Override
			public void transaction_log(EmpContext context, Map<String, Object> parm_req, Map<String, Object> parm_res, Exception exception) {
				UtilTransaction.transaction_log(context, TRANSACTION, OPERATION_CODE_ORANGE.NETWORK_NEGROUP_QUERY, parm_req, exception == null, parm_res, exception);
			}
		});
	}

	@Override
	public Model4NeGroup updateNeGroup(final Invoker4OrangeClientSwtReqeust request, final Model4NeGroup ne_group) throws EmpException {
		return (Model4NeGroup) execute(request, new ClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeIf invoker, EmpContext context, String user_session_key, Map<String, Object> parm_req, Map<String, Object> parm_res) throws EmpException {
				parm_req.put("parent_ne_group_id", ne_group.getParent_ne_group_id());
				parm_req.put("ne_group_id", ne_group.getNe_group_id());
				parm_req.put("ne_group_name", ne_group.getNe_group_name());
				invoker.queryUserSession(context, user_session_key);

				Model4NeGroup ne_group_updated = invoker.updateNeGroup(context, ne_group);
				parm_res.put("parent_ne_group_id", ne_group_updated.getParent_ne_group_id());
				parm_res.put("ne_group_name", ne_group_updated.getNe_group_name());
				return ne_group_updated;
			}

			@Override
			public void transaction_log(EmpContext context, Map<String, Object> parm_req, Map<String, Object> parm_res, Exception exception) {
				UtilTransaction.transaction_log(context, TRANSACTION, OPERATION_CODE_ORANGE.NETWORK_NEGROUP_UPDATE, parm_req, exception == null, parm_res, exception);
				createOperationLog(context, UtilOperationLog.newInstance(context, OPERATION_CODE_ORANGE.NETWORK_NEGROUP_UPDATE, exception, UtilTransaction.toString(parm_req, parm_res)));
			}
		});
	}

	@Override
	public Model4NeGroup deleteNeGroup(final Invoker4OrangeClientSwtReqeust request, final int ne_group_id) throws EmpException {
		return (Model4NeGroup) execute(request, new ClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeIf invoker, EmpContext context, String user_session_key, Map<String, Object> parm_req, Map<String, Object> parm_res) throws EmpException {
				parm_req.put("ne_group_id", ne_group_id);
				invoker.queryUserSession(context, user_session_key);

				Model4NeGroup ne_group_deleted = invoker.deleteNeGroup(context, ne_group_id);
				parm_res.put("parent_ne_group_id", ne_group_deleted.getParent_ne_group_id());
				parm_res.put("ne_group_name", ne_group_deleted.getNe_group_name());
				return ne_group_deleted;
			}

			@Override
			public void transaction_log(EmpContext context, Map<String, Object> parm_req, Map<String, Object> parm_res, Exception exception) {
				UtilTransaction.transaction_log(context, TRANSACTION, OPERATION_CODE_ORANGE.NETWORK_NEGROUP_DELETE, parm_req, exception == null, parm_res, exception);
				createOperationLog(context, UtilOperationLog.newInstance(context, OPERATION_CODE_ORANGE.NETWORK_NEGROUP_DELETE, exception, UtilTransaction.toString(parm_req, parm_res)));
			}
		});
	}

	@Override
	public Model4NeSessionDiscoveryFilterIf[] newInstanceListDiscoveryFilter(Invoker4OrangeClientSwtReqeust request) throws EmpException {
		return (Model4NeSessionDiscoveryFilterIf[]) execute(request, new ClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeIf invoker, EmpContext context, String user_session_key, Map<String, Object> parm_req, Map<String, Object> parm_res) throws EmpException {
				invoker.queryUserSession(context, user_session_key);
				return invoker.newInstanceListDiscoveryFilter(context);
			}

			@Override
			public void transaction_log(EmpContext context, Map<String, Object> parm_req, Map<String, Object> parm_res, Exception exception) {
			}
		});
	}

	@Override
	public Model4Ne[] discoveryListNe(Invoker4OrangeClientSwtReqeust request, final Model4NeSessionDiscoveryFilterIf[] ne_session_discovery_filters) throws EmpException {
		return (Model4Ne[]) execute(request, new ClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeIf invoker, EmpContext context, String user_session_key, Map<String, Object> parm_req, Map<String, Object> parm_res) throws EmpException {
				invoker.queryUserSession(context, user_session_key);
				return invoker.discoveryListNe(context, ne_session_discovery_filters);
			}

			@Override
			public void transaction_log(EmpContext context, Map<String, Object> parm_req, Map<String, Object> parm_res, Exception exception) {
				UtilTransaction.transaction_log(context, TRANSACTION, OPERATION_CODE_ORANGE.NETWORK_NE_DISCOVERY, parm_req, exception == null, parm_res, exception);
			}
		});
	}

	@Override
	public Model4Ne[] createListNe(final Invoker4OrangeClientSwtReqeust request, final Model4Ne[] ne_list) throws EmpException {
		final AtomicInteger index = new AtomicInteger(0);
		return (Model4Ne[]) execute(request, new ClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeIf invoker, EmpContext context, String user_session_key, Map<String, Object> parm_req, Map<String, Object> parm_res) throws EmpException {
				List<String> create_values = new ArrayList<String>();
				for (Model4Ne ne : ne_list) {
					create_values.add(UtilString.format("[ne_group_id={}, ne_name={}, ne_def={}]", ne.getNe_group_id(), ne.getNe_name(), ne.getNe_code()));
				}
				parm_req.put("ne_list", create_values);
				invoker.queryUserSession(context, user_session_key);

				Model4Ne[] ne_created_list = new Model4Ne[ne_list.length];
				create_values = new ArrayList<String>();
				for (index.set(0); index.get() < ne_list.length; index.addAndGet(1)) {
					Model4Ne ne = ne_list[index.get()];
					for (int i = 0; true; i++) {
						String ne_name = i == 0 ? ne.getNe_name() : UtilString.format("{}-{}", ne.getNe_name(), i);
						try {
							invoker.queryNe(context, ne.getNe_group_id(), ne_name);
						} catch (EmpException e) {
							if (ERROR_CODE_ORANGE.MODEL_NOTEXISTS.equals(e.getError_code())) {
								ne.setNe_name(ne_name);
								break;
							}
						}
					}
					ne_created_list[index.get()] = invoker.createNe(context, ne);
					create_values.add(UtilString.format("[ne_id={}]", ne_created_list[index.get()].getNe_id()));
				}
				parm_res.put("ne_list", create_values);
				return ne_created_list;
			}

			@Override
			public void transaction_log(EmpContext context, Map<String, Object> parm_req, Map<String, Object> parm_res, Exception exception) {
				UtilTransaction.transaction_log(context, TRANSACTION, OPERATION_CODE_ORANGE.NETWORK_NE_CREATE, parm_req, exception == null, parm_res, exception);
				createOperationLog(context, UtilOperationLog.newInstance(context, OPERATION_CODE_ORANGE.NETWORK_NE_CREATE, exception, UtilTransaction.toString(parm_req, parm_res)));
			}
		});
	}

	@Override
	public Model4Ne newInstanceNe(final Invoker4OrangeClientSwtReqeust request) throws EmpException {
		return (Model4Ne) execute(request, new ClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeIf invoker, EmpContext context, String user_session_key, Map<String, Object> parm_req, Map<String, Object> parm_res) throws EmpException {
				invoker.queryUserSession(context, user_session_key);
				return invoker.newInstanceNe(context);
			}

			@Override
			public void transaction_log(EmpContext context, Map<String, Object> parm_req, Map<String, Object> parm_res, Exception exception) {
			}
		});
	}

	@Override
	public Model4Ne createNe(final Invoker4OrangeClientSwtReqeust request, final Model4Ne ne) throws EmpException {
		return (Model4Ne) execute(request, new ClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeIf invoker, EmpContext context, String user_session_key, Map<String, Object> parm_req, Map<String, Object> parm_res) throws EmpException {
				parm_req.put("ne_group_id", ne.getNe_group_id());
				parm_req.put("ne_name", ne.getNe_name());
				parm_req.put("ne_def", ne.getNe_code());
				invoker.queryUserSession(context, user_session_key);

				Model4Ne ne_created = invoker.createNe(context, ne);
				parm_res.put("ne_id", ne_created.getNe_id());
				return ne_created;
			}

			@Override
			public void transaction_log(EmpContext context, Map<String, Object> parm_req, Map<String, Object> parm_res, Exception exception) {
				UtilTransaction.transaction_log(context, TRANSACTION, OPERATION_CODE_ORANGE.NETWORK_NE_CREATE, parm_req, exception == null, parm_res, exception);
				createOperationLog(context, UtilOperationLog.newInstance(context, OPERATION_CODE_ORANGE.NETWORK_NE_CREATE, exception, UtilTransaction.toString(parm_req, parm_res)));
			}
		});
	}

	@Override
	public Model4Ne queryNe(final Invoker4OrangeClientSwtReqeust request, final int ne_id) throws EmpException {
		return (Model4Ne) execute(request, new ClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeIf invoker, EmpContext context, String user_session_key, Map<String, Object> parm_req, Map<String, Object> parm_res) throws EmpException {
				parm_req.put("ne_id", ne_id);
				invoker.queryUserSession(context, user_session_key);

				Model4Ne ne = invoker.queryNe(context, ne_id);
				parm_res.put("ne_name", ne.getNe_name());
				return ne;
			}

			@Override
			public void transaction_log(EmpContext context, Map<String, Object> parm_req, Map<String, Object> parm_res, Exception exception) {
				UtilTransaction.transaction_log(context, TRANSACTION, OPERATION_CODE_ORANGE.NETWORK_NE_QUERY, parm_req, exception == null, parm_res, exception);
			}
		});
	}

	@Override
	public Model4Ne[] queryListNe(final Invoker4OrangeClientSwtReqeust request) throws EmpException {
		return (Model4Ne[]) execute(request, new ClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeIf invoker, EmpContext context, String user_session_key, Map<String, Object> parm_req, Map<String, Object> parm_res) throws EmpException {
				invoker.queryUserSession(context, user_session_key);

				Model4Ne[] ne_list = invoker.queryListNe(context, 0, 999999);
				parm_res.put("count", ne_list.length);
				return ne_list;
			}

			@Override
			public void transaction_log(EmpContext context, Map<String, Object> parm_req, Map<String, Object> parm_res, Exception exception) {
				UtilTransaction.transaction_log(context, TRANSACTION, OPERATION_CODE_ORANGE.NETWORK_NE_QUERY, parm_req, exception == null, parm_res, exception);
			}
		});
	}

	@Override
	public Model4Ne[] queryListNe(final Invoker4OrangeClientSwtReqeust request, final int ne_group_id) throws EmpException {
		return (Model4Ne[]) execute(request, new ClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeIf invoker, EmpContext context, String user_session_key, Map<String, Object> parm_req, Map<String, Object> parm_res) throws EmpException {
				parm_req.put("ne_group_id", ne_group_id);
				invoker.queryUserSession(context, user_session_key);

				Model4Ne[] ne_list = invoker.queryListNe(context, ne_group_id, 0, 999999);
				parm_res.put("count", ne_list.length);
				return ne_list;
			}

			@Override
			public void transaction_log(EmpContext context, Map<String, Object> parm_req, Map<String, Object> parm_res, Exception exception) {
				UtilTransaction.transaction_log(context, TRANSACTION, OPERATION_CODE_ORANGE.NETWORK_NE_QUERY, parm_req, exception == null, parm_res, exception);
			}
		});
	}

	@Override
	public Model4Ne updateNe(final Invoker4OrangeClientSwtReqeust request, final Model4Ne ne) throws EmpException {
		return (Model4Ne) execute(request, new ClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeIf invoker, EmpContext context, String user_session_key, Map<String, Object> parm_req, Map<String, Object> parm_res) throws EmpException {
				parm_req.put("ne_group_id", ne.getNe_group_id());
				parm_req.put("ne_id", ne.getNe_id());
				parm_req.put("ne_name", ne.getNe_name());
				parm_req.put("ne_def", ne.getNe_code());
				invoker.queryUserSession(context, user_session_key);

				Model4Ne ne_updated = invoker.updateNe(context, ne);
				parm_res.put("ne_group_id", ne_updated.getNe_group_id());
				parm_res.put("ne_name", ne_updated.getNe_name());
				parm_res.put("ne_def", ne_updated.getNe_code());
				return ne_updated;
			}

			@Override
			public void transaction_log(EmpContext context, Map<String, Object> parm_req, Map<String, Object> parm_res, Exception exception) {
				UtilTransaction.transaction_log(context, TRANSACTION, OPERATION_CODE_ORANGE.NETWORK_NE_UPDATE, parm_req, exception == null, parm_res, exception);
				createOperationLog(context, UtilOperationLog.newInstance(context, OPERATION_CODE_ORANGE.NETWORK_NE_UPDATE, exception, UtilTransaction.toString(parm_req, parm_res)));
			}
		});
	}

	@Override
	public Model4Ne deleteNe(final Invoker4OrangeClientSwtReqeust request, final int ne_id) throws EmpException {
		return (Model4Ne) execute(request, new ClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeIf invoker, EmpContext context, String user_session_key, Map<String, Object> parm_req, Map<String, Object> parm_res) throws EmpException {
				parm_req.put("ne_id", ne_id);
				invoker.queryUserSession(context, user_session_key);

				Model4Ne ne_deleted = invoker.deleteNe(context, ne_id);
				parm_res.put("ne_group_id", ne_deleted.getNe_group_id());
				parm_res.put("ne_name", ne_deleted.getNe_name());
				parm_res.put("ne_def", ne_deleted.getNe_code());
				return ne_deleted;
			}

			@Override
			public void transaction_log(EmpContext context, Map<String, Object> parm_req, Map<String, Object> parm_res, Exception exception) {
				UtilTransaction.transaction_log(context, TRANSACTION, OPERATION_CODE_ORANGE.NETWORK_NE_DELETE, parm_req, exception == null, parm_res, exception);
				createOperationLog(context, UtilOperationLog.newInstance(context, OPERATION_CODE_ORANGE.NETWORK_NE_DELETE, exception, UtilTransaction.toString(parm_req, parm_res)));
			}
		});
	}

	@Override
	public ModelIf[] updateMapLocation(Invoker4OrangeClientSwtReqeust request, ModelIf[] models) throws EmpException {
		return (ModelIf[]) execute(request, new ClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeIf invoker, EmpContext context, String user_session_key, Map<String, Object> parm_req, Map<String, Object> parm_res) throws EmpException {
				List<String> update_values = new ArrayList<String>();
				for (int i = 0; i < models.length; i++) {
					if (models[i] instanceof Model4NeGroup) {
						Model4NeGroup ne_group = (Model4NeGroup) models[i];
						update_values.add(UtilString.format("[ne_group_id={}, x={}, y={}]", ne_group.getNe_group_id(), ne_group.getNe_group_map_location_x(), ne_group.getNe_group_map_location_y()));
					} else if (models[i] instanceof Model4Ne) {
						Model4Ne ne = (Model4Ne) models[i];
						update_values.add(UtilString.format("[ne_id={}, x={}, y={}]", ne.getNe_id(), ne.getNe_map_location_x(), ne.getNe_map_location_y()));
					}
				}
				parm_req.put("location_list", update_values);
				invoker.queryUserSession(context, user_session_key);

				ModelIf[] response = new ModelIf[models.length];
				for (int i = 0; i < models.length; i++) {
					if (models[i] instanceof Model4NeGroup) {
						Model4NeGroup ne_group = (Model4NeGroup) models[i];
						response[i] = invoker.updateNeGroupMapLocation(context, ne_group);
					} else if (models[i] instanceof Model4Ne) {
						Model4Ne ne = (Model4Ne) models[i];
						response[i] = invoker.updateNeMapLocation(context, ne);
					}
				}

				return response;
			}

			@Override
			public void transaction_log(EmpContext context, Map<String, Object> parm_req, Map<String, Object> parm_res, Exception exception) {
				UtilTransaction.transaction_log(context, TRANSACTION, OPERATION_CODE_ORANGE.NETWORK_TOPOLOGY_UPDATE, parm_req, exception == null, parm_res, exception);
			}
		});
	}

	@Override
	public Model4NetworkLink createNetworkLink(Invoker4OrangeClientSwtReqeust request, final Model4NetworkLink network_link) throws EmpException {
		return (Model4NetworkLink) execute(request, new ClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeIf invoker, EmpContext context, String user_session_key, Map<String, Object> parm_req, Map<String, Object> parm_res) throws EmpException {
				if (network_link.getNe_group_id_from() != 0) {
					parm_req.put("ne_group_id_from", network_link.getNe_group_id_from());
				}
				if (network_link.getNe_group_id_to() != 0) {
					parm_req.put("ne_group_id_to", network_link.getNe_group_id_to());
				}
				if (network_link.getNe_id_from() != 0) {
					parm_req.put("ne_id_from", network_link.getNe_id_from());
				}
				if (network_link.getNe_id_to() != 0) {
					parm_req.put("ne_id_to", network_link.getNe_id_to());
				}
				invoker.queryUserSession(context, user_session_key);

				Model4NetworkLink network_link_created = invoker.createNetworkLink(context, network_link);
				parm_res.put("network_link_id", network_link_created.getNetwork_link_id());
				return network_link_created;
			}

			@Override
			public void transaction_log(EmpContext context, Map<String, Object> parm_req, Map<String, Object> parm_res, Exception exception) {
				UtilTransaction.transaction_log(context, TRANSACTION, OPERATION_CODE_ORANGE.NETWORK_LINK_CREATE, parm_req, exception == null, parm_res, exception);
				createOperationLog(context, UtilOperationLog.newInstance(context, OPERATION_CODE_ORANGE.NETWORK_LINK_CREATE, exception, UtilTransaction.toString(parm_req, parm_res)));
			}
		});
	}

	@Override
	public Model4NetworkLink deleteNetworkLink(Invoker4OrangeClientSwtReqeust request, int network_link_id) throws EmpException {
		return (Model4NetworkLink) execute(request, new ClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeIf invoker, EmpContext context, String user_session_key, Map<String, Object> parm_req, Map<String, Object> parm_res) throws EmpException {
				parm_req.put("network_link_id", network_link_id);
				invoker.queryUserSession(context, user_session_key);

				return invoker.deleteNetworkLink(context, network_link_id);
			}

			@Override
			public void transaction_log(EmpContext context, Map<String, Object> parm_req, Map<String, Object> parm_res, Exception exception) {
				UtilTransaction.transaction_log(context, TRANSACTION, OPERATION_CODE_ORANGE.NETWORK_LINK_DELETE, parm_req, exception == null, parm_res, exception);
				createOperationLog(context, UtilOperationLog.newInstance(context, OPERATION_CODE_ORANGE.NETWORK_LINK_DELETE, exception, UtilTransaction.toString(parm_req, parm_res)));
			}
		});
	}

	@Override
	public ModelDisplay4Ne queryNetworkViewNe(Invoker4OrangeClientSwtReqeust request, final int ne_id, final Map<Integer, Integer> ne_statistics_index_map, final boolean auto_refresh) throws EmpException {
		return (ModelDisplay4Ne) execute(request, new ClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeIf invoker, EmpContext context, String user_session_key, Map<String, Object> parm_req, Map<String, Object> parm_res) throws EmpException {
				parm_req.put("ne_id", ne_id);
				invoker.queryUserSession(context, user_session_key);

				ModelDisplay4Ne model = new ModelDisplay4Ne();

				Model4Ne ne = invoker.queryNe(context, ne_id);
				model.setNe(ne);

				EMP_MODEL_NE ne_def = EMP_MODEL.current().getNe(ne.getNe_code());
				if (ne_def == null) {
					return model;
				}

				Date fromTime = new Date();
				Date toTime = fromTime;

				String[] order = queryOrderNetworkViewNe(invoker, context, ne_def);
				for (String code : order) {
					if (ModelDisplay4Ne.isNeInfo(code)) {
						EMP_MODEL_NE_INFO ne_info_def = EMP_MODEL.current().getNe_info(ModelDisplay4Ne.toNeInfo(code));

						ModelDisplay4NeInfo display = queryListNeInfo(invoker, context, ne_id, ne_info_def, true);
						display.setModel4Ne(ne);
						model.add(display);
					} else if (ModelDisplay4Ne.isNe_statistics(code)) {
						EMP_MODEL_NE_INFO ne_info_def = EMP_MODEL.current().getNe_info(ModelDisplay4Ne.toNe_statistics(code));

						ModelDisplay4NeStatistics modelDisplay4NeStatistics = new ModelDisplay4NeStatistics();
						modelDisplay4NeStatistics.setModel4Ne(ne);
						modelDisplay4NeStatistics.setNe_info_code(ne_info_def.getCode());
						modelDisplay4NeStatistics.setStatisticsType(ne_info_def.getStat_type());
						switch (ne_info_def.getStat_type()) {
						case MINUTE_5:
							fromTime = UtilDate.add(toTime, Calendar.HOUR, -1);
							break;
						case MINUTE_15:
							fromTime = UtilDate.add(toTime, Calendar.HOUR, -3);
							break;
						case MINUTE_30:
							fromTime = UtilDate.add(toTime, Calendar.HOUR, -6);
							break;
						default:
						}

						NE_INFO_INDEX[] ne_info_indexs = invoker.queryListNeStatisticsIndex(context, ne_id, ne_info_def, ne_info_def.getStat_type(), fromTime, toTime);
						Integer ne_info_index_key = ne_statistics_index_map.get(ne_info_def.getCode());
						NE_INFO_INDEX ne_info_index = 0 < ne_info_indexs.length ? ne_info_indexs[0] : null;
						if (ne_info_index_key != null) {
							for (NE_INFO_INDEX aaa : ne_info_indexs) {
								if (aaa.getNe_info_index() == ne_info_index_key) {
									ne_info_index = aaa;
									break;
								}
							}
						}

						modelDisplay4NeStatistics.setNe_info_indexs(ne_info_indexs);
						modelDisplay4NeStatistics.setNe_info_index(ne_info_index);
						modelDisplay4NeStatistics.setModel4NeStatisticses(ne_info_index == null ? new Model4NeStatisticsIf[0] : invoker.queryListNeStatistics(context, ne_id, ne_info_def, ne_info_index.getNe_info_index(), ne_info_def.getStat_type(), fromTime, toTime));
						model.add(modelDisplay4NeStatistics);
					}
				}

				return model;
			}

			@Override
			public void transaction_log(EmpContext context, Map<String, Object> parm_req, Map<String, Object> parm_res, Exception exception) {
				if (!auto_refresh) {
					UtilTransaction.transaction_log(context, TRANSACTION, OPERATION_CODE_ORANGE.NETWORK_NE_QUERY, parm_req, exception == null, parm_res, exception);
				}
			}
		});
	}

	@Override
	public String[] queryOrderNetworkViewNe(Invoker4OrangeClientSwtReqeust request, final int ne_code) throws EmpException {
		return (String[]) execute(request, new ClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeIf invoker, EmpContext context, String user_session_key, Map<String, Object> parm_req, Map<String, Object> parm_res) throws EmpException {
				EMP_MODEL_NE ne_def = EMP_MODEL.current().getNe(ne_code);
				return queryOrderNetworkViewNe(invoker, context, ne_def);
			}

			@Override
			public void transaction_log(EmpContext context, Map<String, Object> parm_req, Map<String, Object> parm_res, Exception exception) {
			}
		});
	}

	protected Model4Preference preference;
	protected Properties properties;
	protected final String NETWORK_NEDETAIL_DISPLAYORDER = "NETWORK_NEDETAIL_DISPLAYORDER";
	{
		UtilCache.buildCache(NETWORK_NEDETAIL_DISPLAYORDER, 256, 300);
	}

	protected String[] queryOrderNetworkViewNe(Invoker4OrangeIf invoker, EmpContext context, EMP_MODEL_NE ne_def) throws EmpException {
		String[] orders = (String[]) UtilCache.get(NETWORK_NEDETAIL_DISPLAYORDER, ne_def.getCode());

		if (orders == null) {
			orders = new String[0];
			String orer_string = null;
			Model4Preference preference = invoker.queryPreference(context, PREFERENCE_CODE_ORANGE.PREFERENCE_ORANGE_NETWORK_NEDETAIL_DISPLAYORDER);
			if (preference != null && (this.preference == null || !preference.getUpdate_time().equals(this.preference.getUpdate_time()))) {
				try {
					Properties properties = new Properties();
					properties.load(new StringReader(preference.getPreference()));
					this.properties = properties;
				} catch (Exception e) {
					throw new EmpException(ERROR_CODE_ORANGE.FILE_IO, e);
				}
			}
			if (this.properties != null) {
				orer_string = this.properties.getProperty(String.valueOf(ne_def.getCode()));
			}

			Set<String> order_set = new LinkedHashSet<String>();

			if (orer_string != null) {
				for (String order : orer_string.split(",")) {
					if (!UtilString.isEmpty(order.trim())) {
						order_set.add(order.trim());
					}
				}
			}

			if (orer_string == null) {
				order_set.add(NETWORK_NEDETAIL_DISPLAYORDER);
			}
			orders = order_set.toArray(new String[0]);
			UtilCache.put(NETWORK_NEDETAIL_DISPLAYORDER, ne_def.getCode(), orders);
		}
		if (orders.length == 1 && NETWORK_NEDETAIL_DISPLAYORDER.equals(orders[0])) {
			Set<String> order_set = new LinkedHashSet<String>();
			for (EMP_MODEL_NE_INFO ne_info_def : EMP_MODEL.current().getNe_infos_by_ne(ne_def.getCode())) {
				if (ne_info_def.isMonitoring()) {
					order_set.add(ModelDisplay4Ne.toNeInfo(ne_info_def.getCode()));
				}
			}
			for (EMP_MODEL_NE_INFO ne_info_def : EMP_MODEL.current().getNe_infos_by_ne(ne_def.getCode())) {
				if (ne_info_def.isStat_enable()) {
					order_set.add(ModelDisplay4Ne.toNe_statistics(ne_info_def.getCode()));
				}
			}
			orders = order_set.toArray(new String[0]);
		}

		return orders;
	}

	@Override
	public void updateOrderNetworkViewNe(Invoker4OrangeClientSwtReqeust request, int ne_code, String[] order) throws EmpException {
		try {
			execute(request, new ClientSwtTaskIf() {
				@Override
				public Object run(Invoker4OrangeIf invoker, EmpContext context, String user_session_key, Map<String, Object> parm_req, Map<String, Object> parm_res) throws EmpException {
					EMP_MODEL_NE ne_def = EMP_MODEL.current().getNe(ne_code);
					Model4Preference preference = invoker.queryPreference(context, PREFERENCE_CODE_ORANGE.PREFERENCE_ORANGE_NETWORK_NEDETAIL_DISPLAYORDER);
					if (preference != null) {
						Properties properties = new Properties();
						try {
							properties.load(new StringReader(preference.getPreference()));
							StringBuilder order_string = new StringBuilder();
							for (int i = 0; i < order.length; i++) {
								order_string.append(i == 0 ? "" : ",").append(order[i]);
							}
							properties.put(String.valueOf(ne_def.getCode()), order_string.toString());
							StringWriter writer = new StringWriter();
							properties.store(writer, null);
							preference.setPreference(writer.toString());
							invoker.updatePreference(context, preference);
						} catch (Exception e) {
							throw new EmpException(ERROR_CODE_ORANGE.FILE_IO, e);
						}
					}
					return null;
				}

				@Override
				public void transaction_log(EmpContext context, Map<String, Object> parm_req, Map<String, Object> parm_res, Exception exception) {
					UtilTransaction.transaction_log(context, TRANSACTION, OPERATION_CODE_ORANGE.NETWORK_NE_UPDATE, parm_req, exception == null, parm_res, exception);
				}
			});
		} finally {
			UtilCache.removeAll(NETWORK_NEDETAIL_DISPLAYORDER);
		}
	}

	@Override
	public Model4ResourceNMS[] queryListResourceNMS(Invoker4OrangeClientSwtReqeust request, final boolean auto_refresh) throws EmpException {
		return (Model4ResourceNMS[]) execute(request, new ClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeIf invoker, EmpContext context, String user_session_key, Map<String, Object> parm_req, Map<String, Object> parm_res) throws EmpException {
				parm_req.put("ne_type", "NMS");
				invoker.queryUserSession(context, user_session_key);

				return invoker.queryListResourceNMS(context);
			}

			@Override
			public void transaction_log(EmpContext context, Map<String, Object> parm_req, Map<String, Object> parm_res, Exception exception) {
				if (!auto_refresh) {
					UtilTransaction.transaction_log(context, TRANSACTION, OPERATION_CODE_ORANGE.NETWORK_NE_QUERY, parm_req, exception == null, parm_res, exception);
				}
			}
		});
	}

	@Override
	public ModelDisplay4NeInfo queryListNeInfo(final Invoker4OrangeClientSwtReqeust request, final int ne_id, final int ne_info_code, final boolean isQuery) throws EmpException {
		return (ModelDisplay4NeInfo) execute(request, new ClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeIf invoker, EmpContext context, String user_session_key, Map<String, Object> parm_req, Map<String, Object> parm_res) throws EmpException {
				EMP_MODEL_NE_INFO ne_info_def = EMP_MODEL.current().getNe_info(ne_info_code);
				parm_req.put("ne_id", ne_id);
				parm_req.put("ne_info_def", ne_info_def);
				invoker.queryUserSession(context, user_session_key);

				ModelDisplay4NeInfo modelDisplay4NeInfo = queryListNeInfo(invoker, context, ne_id, ne_info_def, isQuery);
				parm_res.put("count", modelDisplay4NeInfo.getModel4NeInfos().length);
				return modelDisplay4NeInfo;
			}

			@Override
			public void transaction_log(EmpContext context, Map<String, Object> parm_req, Map<String, Object> parm_res, Exception exception) {
				UtilTransaction.transaction_log(context, TRANSACTION, OPERATION_CODE_ORANGE.NETWORK_NE_INFO_QUERY, parm_req, exception == null, parm_res, exception);
			}
		});
	}

	@Override
	public ModelDisplay4NeInfo updateNeInfo(final Invoker4OrangeClientSwtReqeust request, final Model4NeInfoIf model4NeInfo) throws EmpException {
		return (ModelDisplay4NeInfo) execute(request, new ClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeIf invoker, EmpContext context, String user_session_key, Map<String, Object> parm_req, Map<String, Object> parm_res) throws EmpException {
				int ne_id = model4NeInfo.getNe_id();
				EMP_MODEL_NE_INFO ne_info_def = model4NeInfo.getNe_info_def();
				parm_req.put("ne_id", ne_id);
				parm_req.put("ne_info_def", ne_info_def);
				invoker.queryUserSession(context, user_session_key);

				invoker.updateNeInfo(context, model4NeInfo);

				ModelDisplay4NeInfo modelDisplay4NeInfo = queryListNeInfo(invoker, context, ne_id, ne_info_def, false);
				return modelDisplay4NeInfo;
			}

			@Override
			public void transaction_log(EmpContext context, Map<String, Object> parm_req, Map<String, Object> parm_res, Exception exception) {
				UtilTransaction.transaction_log(context, TRANSACTION, OPERATION_CODE_ORANGE.NETWORK_NE_INFO_UPDATE, parm_req, exception == null, parm_res, exception);
				createOperationLog(context, UtilOperationLog.newInstance(context, OPERATION_CODE_ORANGE.NETWORK_NE_INFO_UPDATE, exception, UtilTransaction.toString(parm_req, parm_res)));
			}
		});
	}

	/**
	 * @param invoker
	 * @param context
	 * @param ne_id
	 * @param ne_info_def
	 * @param startNo
	 * @param count
	 * @return
	 * @throws EmpException
	 */
	protected ModelDisplay4NeInfo queryListNeInfo(Invoker4OrangeIf invoker, EmpContext context, int ne_id, EMP_MODEL_NE_INFO ne_info_def, boolean isQuery) throws EmpException {
		ModelDisplay4NeInfo modelDisplay4NeInfo = new ModelDisplay4NeInfo();

		Model4Ne ne = invoker.queryNe(context, ne_id);
		modelDisplay4NeInfo.setModel4Ne(ne);

		modelDisplay4NeInfo.setNe_info_code(ne_info_def.getCode());

		Model4NeInfoIf[] infos = {};
		int totalCount = 0;
		if (isQuery && ne_info_def.isMonitoring()) {
			infos = invoker.queryListNeInfo(context, ne_id, ne_info_def);
		} else if (!isQuery) {
			infos = invoker.readListNeInfo(context, ne_id, ne_info_def);
		}
		totalCount = infos.length;
		TablePageConfig<Model4NeInfoIf> tablePageConfigNeInfo = new TablePageConfig<Model4NeInfoIf>(0, totalCount, infos, totalCount);
		modelDisplay4NeInfo.setTablePageConfigNeInfo(tablePageConfigNeInfo);

		return modelDisplay4NeInfo;
	}

	@Override
	public ModelDisplay4NeStatistics queryListNeStatistics(final Invoker4OrangeClientSwtReqeust request, final int ne_id, final int ne_info_code, final NE_INFO_INDEX old_index, final STATISTICS_TYPE statisticsType, final Date fromDate, final Date toDate) throws EmpException {
		return (ModelDisplay4NeStatistics) execute(request, new ClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeIf invoker, EmpContext context, String user_session_key, Map<String, Object> parm_req, Map<String, Object> parm_res) throws EmpException {
				EMP_MODEL_NE_INFO ne_info_def = EMP_MODEL.current().getNe_info(ne_info_code);
				parm_req.put("ne_id", ne_id);
				parm_req.put("ne_info_def", ne_info_def);
				invoker.queryUserSession(context, user_session_key);

				NE_INFO_INDEX[] ne_info_indexs = invoker.queryListNeStatisticsIndex(context, ne_id, ne_info_def, statisticsType, fromDate, toDate);
				NE_INFO_INDEX new_index = null;
				Model4NeStatisticsIf[] model4NeStatisticses = {};
				if (ne_info_indexs != null && ne_info_indexs.length > 0) {
					new_index = ne_info_indexs[0];
					if (old_index != null) {
						for (NE_INFO_INDEX ne_info_index : ne_info_indexs) {
							if (ne_info_index.getNe_info_index() == old_index.getNe_info_index()) {
								new_index = ne_info_index;
								break;
							}
						}
					}
					model4NeStatisticses = invoker.queryListNeStatistics(context, ne_id, ne_info_def, new_index.getNe_info_index(), statisticsType, fromDate, toDate);
				}
				ModelDisplay4NeStatistics modelDisplay4NeStatistics = new ModelDisplay4NeStatistics();
				Model4Ne ne = invoker.queryNe(context, ne_id);
				modelDisplay4NeStatistics.setModel4Ne(ne);
				modelDisplay4NeStatistics.setNe_info_code(ne_info_def.getCode());
				modelDisplay4NeStatistics.setStatisticsType(statisticsType);
				modelDisplay4NeStatistics.setNe_info_indexs(ne_info_indexs);
				modelDisplay4NeStatistics.setNe_info_index(new_index);
				modelDisplay4NeStatistics.setModel4NeStatisticses(model4NeStatisticses);
				parm_res.put("count", model4NeStatisticses.length);
				return modelDisplay4NeStatistics;
			}

			@Override
			public void transaction_log(EmpContext context, Map<String, Object> parm_req, Map<String, Object> parm_res, Exception exception) {
				UtilTransaction.transaction_log(context, TRANSACTION, OPERATION_CODE_ORANGE.NETWORK_NE_STATISTICS_QUERY, parm_req, exception == null, parm_res, exception);
			}
		});
	}

	@Override
	public Model4NeThresholdIf queryNeThreshold(Invoker4OrangeClientSwtReqeust request, final int ne_id, final int ne_info_code) throws EmpException {
		return (Model4NeThresholdIf) execute(request, new ClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeIf invoker, EmpContext context, String user_session_key, Map<String, Object> parm_req, Map<String, Object> parm_res) throws EmpException {
				EMP_MODEL_NE_INFO ne_info_def = EMP_MODEL.current().getNe_info(ne_info_code);
				parm_req.put("ne_id", ne_id);
				parm_req.put("ne_info_def", ne_info_def);
				invoker.queryUserSession(context, user_session_key);

				Model4NeThresholdIf ne_threshold = invoker.queryNeThreshold(context, ne_id, ne_info_def);
				return ne_threshold;
			}

			@Override
			public void transaction_log(EmpContext context, Map<String, Object> parm_req, Map<String, Object> parm_res, Exception exception) {
				UtilTransaction.transaction_log(context, TRANSACTION, OPERATION_CODE_ORANGE.NETWORK_NE_THRESHOLD_QUERY, parm_req, exception == null, parm_res, exception);
			}
		});
	}

	@Override
	public Model4NeThresholdIf updateNeThreshold(Invoker4OrangeClientSwtReqeust request, final Model4NeThresholdIf ne_threshold, final String ne_info_field_name) throws EmpException {
		return (Model4NeThresholdIf) execute(request, new ClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeIf invoker, EmpContext context, String user_session_key, Map<String, Object> parm_req, Map<String, Object> parm_res) throws EmpException {
				// parm_req.put("ne_id", ne_threshold.getNe_id());
				// parm_req.put("ne_info_def", ne_threshold.getNe_info_code());
				// parm_req.put("ne_info_field_def", ne_info_field_def);
				invoker.queryUserSession(context, user_session_key);

				Model4NeThresholdIf ne_threshold_updated = invoker.updateNeThreshold(context, ne_threshold, ne_threshold.getNe_info_def().getNe_info_field(ne_info_field_name));
				return ne_threshold_updated;
			}

			@Override
			public void transaction_log(EmpContext context, Map<String, Object> parm_req, Map<String, Object> parm_res, Exception exception) {
				UtilTransaction.transaction_log(context, TRANSACTION, OPERATION_CODE_ORANGE.NETWORK_NE_THRESHOLD_UPDATE, parm_req, exception == null, parm_res, exception);
				createOperationLog(context, UtilOperationLog.newInstance(context, OPERATION_CODE_ORANGE.NETWORK_NE_THRESHOLD_UPDATE, exception, UtilTransaction.toString(parm_req, parm_res)));
			}
		});
	}

	@Override
	public Model4NeThresholdIf[] copyListNeThreshold(Invoker4OrangeClientSwtReqeust request, final int ne_id, final int ne_info_code, final int[] ne_id_targets) throws EmpException {
		return (Model4NeThresholdIf[]) execute(request, new ClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeIf invoker, EmpContext context, String user_session_key, Map<String, Object> parm_req, Map<String, Object> parm_res) throws EmpException {
				EMP_MODEL_NE_INFO ne_info_def = EMP_MODEL.current().getNe_info(ne_info_code);
				parm_req.put("ne_id", ne_id);
				parm_req.put("ne_info_def", ne_info_def);
				List<Integer> update_list = new ArrayList<Integer>();
				for (int ne_id_target : ne_id_targets) {
					update_list.add(ne_id_target);
				}
				parm_req.put("ne_id_targets", update_list);
				invoker.queryUserSession(context, user_session_key);

				return invoker.copyListNeThreshold(context, ne_id, ne_info_def, ne_id_targets);
			}

			@Override
			public void transaction_log(EmpContext context, Map<String, Object> parm_req, Map<String, Object> parm_res, Exception exception) {
				UtilTransaction.transaction_log(context, TRANSACTION, OPERATION_CODE_ORANGE.NETWORK_NE_THRESHOLD_UPDATE, parm_req, exception == null, parm_res, exception);
				createOperationLog(context, UtilOperationLog.newInstance(context, OPERATION_CODE_ORANGE.NETWORK_NE_THRESHOLD_UPDATE, exception, UtilTransaction.toString(parm_req, parm_res)));
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public TablePageConfig<Model4Alarm> queryListAlarmActiveByNeGroup(final Invoker4OrangeClientSwtReqeust request, final int ne_group_id, final SEVERITY severity, final int startNo, final int count) throws EmpException {
		return (TablePageConfig<Model4Alarm>) execute(request, new ClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeIf invoker, EmpContext context, String user_session_key, Map<String, Object> parm_req, Map<String, Object> parm_res) throws EmpException {
				parm_req.put("type", "active");
				parm_req.put("ne_group_id", ne_group_id);
				parm_req.put("severity", severity);
				parm_req.put("startNo", startNo);
				parm_req.put("count", count);
				invoker.queryUserSession(context, user_session_key);

				Model4Alarm[] model4Alarms = invoker.queryListAlarmActiveByNeGroup(context, ne_group_id, null, severity, startNo, count);
				int total_count = invoker.queryCountAlarmActiveByNeGroup(context, ne_group_id, null, severity);
				parm_res.put("count", model4Alarms.length);
				parm_res.put("total_count", total_count);
				return new TablePageConfig<Model4Alarm>(startNo, count, model4Alarms, total_count);
			}

			@Override
			public void transaction_log(EmpContext context, Map<String, Object> parm_req, Map<String, Object> parm_res, Exception exception) {
				UtilTransaction.transaction_log(context, TRANSACTION, OPERATION_CODE_ORANGE.FAULT_ALARM_QUERY, parm_req, exception == null, parm_res, exception);
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public TablePageConfig<Model4Alarm> queryListAlarmActiveByNe(final Invoker4OrangeClientSwtReqeust request, final int ne_id, final SEVERITY severity, final int startNo, final int count) throws EmpException {
		return (TablePageConfig<Model4Alarm>) execute(request, new ClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeIf invoker, EmpContext context, String user_session_key, Map<String, Object> parm_req, Map<String, Object> parm_res) throws EmpException {
				parm_req.put("type", "active");
				parm_req.put("ne_id", ne_id);
				parm_req.put("severity", severity);
				parm_req.put("startNo", startNo);
				parm_req.put("count", count);
				invoker.queryUserSession(context, user_session_key);

				Model4Alarm[] model4Alarms = invoker.queryListAlarmActiveByNe(context, ne_id, null, null, severity, startNo, count);
				int total_count = invoker.queryCountAlarmActiveByNe(context, ne_id, null, severity);
				parm_res.put("count", model4Alarms.length);
				parm_res.put("total_count", total_count);
				return new TablePageConfig<Model4Alarm>(startNo, count, model4Alarms, total_count);
			}

			@Override
			public void transaction_log(EmpContext context, Map<String, Object> parm_req, Map<String, Object> parm_res, Exception exception) {
				UtilTransaction.transaction_log(context, TRANSACTION, OPERATION_CODE_ORANGE.FAULT_ALARM_QUERY, parm_req, exception == null, parm_res, exception);
			}
		});
	}

	@Override
	public Model4Alarm ackAlarm(final Invoker4OrangeClientSwtReqeust request, final long gen_first_event_id) throws EmpException {
		return (Model4Alarm) execute(request, new ClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeIf invoker, EmpContext context, String user_session_key, Map<String, Object> parm_req, Map<String, Object> parm_res) throws EmpException {
				parm_req.put("gen_first_event_id", "gen_first_event_id");
				invoker.queryUserSession(context, user_session_key);

				Model4Alarm alarm_acked = invoker.ackAlarm(context, gen_first_event_id);
				parm_res.put("ne_id", alarm_acked.getNe_id());
				parm_res.put("ne_info_def", alarm_acked.getNe_info_code() == 0 ? null : alarm_acked.getNe_info_def());
				parm_res.put("ne_info_index", alarm_acked.getNe_info_index());
				parm_res.put("location_display", alarm_acked.getLocation_display());
				parm_res.put("event_def", alarm_acked.getEvent_code());
				parm_res.put("severity", alarm_acked.getSeverity());
				return alarm_acked;
			}

			@Override
			public void transaction_log(EmpContext context, Map<String, Object> parm_req, Map<String, Object> parm_res, Exception exception) {
				UtilTransaction.transaction_log(context, TRANSACTION, OPERATION_CODE_ORANGE.FAULT_ALARM_ACK, parm_req, exception == null, parm_res, exception);
				createOperationLog(context, UtilOperationLog.newInstance(context, OPERATION_CODE_ORANGE.FAULT_ALARM_ACK, exception, UtilTransaction.toString(parm_req, parm_res)));
			}
		});
	}

	@Override
	public Model4Alarm clearAlarmByGen_first_event_id(final Invoker4OrangeClientSwtReqeust request, final long gen_first_event_id) throws EmpException {
		return (Model4Alarm) execute(request, new ClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeIf invoker, EmpContext context, String user_session_key, Map<String, Object> parm_req, Map<String, Object> parm_res) throws EmpException {
				parm_req.put("gen_first_event_id", "gen_first_event_id");
				invoker.queryUserSession(context, user_session_key);

				Model4Alarm alarm_cleared = invoker.clearAlarmByGen_first_event_id(context, gen_first_event_id);
				parm_res.put("ne_id", alarm_cleared.getNe_id());
				parm_res.put("ne_info_def", alarm_cleared.getNe_info_code());
				parm_res.put("ne_info_index", alarm_cleared.getNe_info_index());
				parm_res.put("location_display", alarm_cleared.getLocation_display());
				parm_res.put("event_def", alarm_cleared.getEvent_code());
				parm_res.put("severity", alarm_cleared.getSeverity());
				return alarm_cleared;
			}

			@Override
			public void transaction_log(EmpContext context, Map<String, Object> parm_req, Map<String, Object> parm_res, Exception exception) {
				UtilTransaction.transaction_log(context, TRANSACTION, OPERATION_CODE_ORANGE.FAULT_ALARM_CLEAR, parm_req, exception == null, parm_res, exception);
				createOperationLog(context, UtilOperationLog.newInstance(context, OPERATION_CODE_ORANGE.FAULT_ALARM_CLEAR, exception, UtilTransaction.toString(parm_req, parm_res)));
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public TablePageConfig<Model4Alarm> queryListAlarmHistoryByNeGroup(final Invoker4OrangeClientSwtReqeust request, final int ne_group_id, final int event_code, final SEVERITY severity, final Date fromTime, final Date toTime, final int startNo, final int count) throws EmpException {
		return (TablePageConfig<Model4Alarm>) execute(request, new ClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeIf invoker, EmpContext context, String user_session_key, Map<String, Object> parm_req, Map<String, Object> parm_res) throws EmpException {
				EMP_MODEL_EVENT event_def = EMP_MODEL.current().getEvent(event_code);
				parm_req.put("type", "history");
				parm_req.put("ne_group_id", ne_group_id);
				parm_req.put("severity", severity);
				parm_req.put("startNo", startNo);
				parm_req.put("count", count);
				invoker.queryUserSession(context, user_session_key);

				Model4Alarm[] model4Alarms = invoker.queryListAlarmByNeGroup(context, ne_group_id, event_def, severity, fromTime, toTime, startNo, count);
				int total_count = invoker.queryCountAlarmByNeGroup(context, ne_group_id, event_def, severity, fromTime, toTime);
				parm_res.put("count", model4Alarms.length);
				parm_res.put("total_count", total_count);
				return new TablePageConfig<Model4Alarm>(startNo, count, model4Alarms, total_count);
			}

			@Override
			public void transaction_log(EmpContext context, Map<String, Object> parm_req, Map<String, Object> parm_res, Exception exception) {
				UtilTransaction.transaction_log(context, TRANSACTION, OPERATION_CODE_ORANGE.FAULT_ALARM_QUERY, parm_req, exception == null, parm_res, exception);
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public TablePageConfig<Model4Alarm> queryListAlarmHistoryByNe(final Invoker4OrangeClientSwtReqeust request, final int ne_id, final int event_code, final SEVERITY severity, final Date fromTime, final Date toTime, final int startNo, final int count) throws EmpException {
		return (TablePageConfig<Model4Alarm>) execute(request, new ClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeIf invoker, EmpContext context, String user_session_key, Map<String, Object> parm_req, Map<String, Object> parm_res) throws EmpException {
				EMP_MODEL_EVENT event_def = EMP_MODEL.current().getEvent(event_code);
				parm_req.put("type", "history");
				parm_req.put("ne_id", ne_id);
				parm_req.put("severity", severity);
				parm_req.put("startNo", startNo);
				parm_req.put("count", count);
				invoker.queryUserSession(context, user_session_key);

				Model4Alarm[] model4Alarms = invoker.queryListAlarmByNe(context, ne_id, event_def, severity, fromTime, toTime, startNo, count);
				int total_count = invoker.queryCountAlarmByNe(context, ne_id, event_def, severity, fromTime, toTime);
				parm_res.put("count", model4Alarms.length);
				parm_res.put("total_count", total_count);
				return new TablePageConfig<Model4Alarm>(startNo, count, model4Alarms, total_count);
			}

			@Override
			public void transaction_log(EmpContext context, Map<String, Object> parm_req, Map<String, Object> parm_res, Exception exception) {
				UtilTransaction.transaction_log(context, TRANSACTION, OPERATION_CODE_ORANGE.FAULT_ALARM_QUERY, parm_req, exception == null, parm_res, exception);
			}
		});
	}

	@Override
	public Model4AlarmStatistics[] queryListAlarmStatisticsByNeGroup(final Invoker4OrangeClientSwtReqeust request, final Date fromTime, final Date toTime, final int ne_group_id, final ITEM item, final STATISTICS_TYPE statistics_type) throws EmpException {
		return (Model4AlarmStatistics[]) execute(request, new ClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeIf invoker, EmpContext context, String user_session_key, Map<String, Object> parm_req, Map<String, Object> parm_res) throws EmpException {
				parm_req.put("ne_group_id", ne_group_id);
				parm_req.put("item", item);
				parm_req.put("statistics_type", statistics_type);
				parm_req.put("fromTime", fromTime);
				parm_req.put("toTime", toTime);
				invoker.queryUserSession(context, user_session_key);

				Model4AlarmStatistics[] model4AlarmStatisticses = invoker.queryListAlarmStatisticsByNeGroup(context, ne_group_id, item, statistics_type, fromTime, toTime);
				parm_res.put("count", model4AlarmStatisticses.length);
				return model4AlarmStatisticses == null ? new Model4AlarmStatistics[0] : model4AlarmStatisticses;
			}

			@Override
			public void transaction_log(EmpContext context, Map<String, Object> parm_req, Map<String, Object> parm_res, Exception exception) {
				UtilTransaction.transaction_log(context, TRANSACTION, OPERATION_CODE_ORANGE.FAULT_ALARM_STATISTICS_QUERY, parm_req, exception == null, parm_res, exception);
			}
		});
	}

	@Override
	public Model4AlarmStatistics[] queryListAlarmStatisticsByNe(final Invoker4OrangeClientSwtReqeust request, final Date fromTime, final Date toTime, final int ne_id, final ITEM item, final STATISTICS_TYPE statistics_type) throws EmpException {
		return (Model4AlarmStatistics[]) execute(request, new ClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeIf invoker, EmpContext context, String user_session_key, Map<String, Object> parm_req, Map<String, Object> parm_res) throws EmpException {
				parm_req.put("ne_id", ne_id);
				parm_req.put("item", item);
				parm_req.put("statistics_type", statistics_type);
				parm_req.put("fromTime", fromTime);
				parm_req.put("toTime", toTime);
				invoker.queryUserSession(context, user_session_key);

				Model4AlarmStatistics[] model4AlarmStatisticses = invoker.queryListAlarmStatisticsByNe(context, ne_id, item, statistics_type, fromTime, toTime);
				parm_res.put("count", model4AlarmStatisticses.length);
				return model4AlarmStatisticses == null ? new Model4AlarmStatistics[0] : model4AlarmStatisticses;
			}

			@Override
			public void transaction_log(EmpContext context, Map<String, Object> parm_req, Map<String, Object> parm_res, Exception exception) {
				UtilTransaction.transaction_log(context, TRANSACTION, OPERATION_CODE_ORANGE.FAULT_ALARM_STATISTICS_QUERY, parm_req, exception == null, parm_res, exception);
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public TablePageConfig<Model4Event> queryListEventByNeGroup(final Invoker4OrangeClientSwtReqeust request, final int ne_group_id, final int event_code, final SEVERITY severity, final Date fromTime, final Date toTime, final int startNo, final int count) throws EmpException {
		return (TablePageConfig<Model4Event>) execute(request, new ClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeIf invoker, EmpContext context, String user_session_key, Map<String, Object> parm_req, Map<String, Object> parm_res) throws EmpException {
				EMP_MODEL_EVENT event_def = EMP_MODEL.current().getEvent(event_code);
				parm_req.put("ne_group_id", ne_group_id);
				parm_req.put("event_def", event_def);
				parm_req.put("severity", severity);
				parm_req.put("fromTime", fromTime);
				parm_req.put("toTime", toTime);
				parm_req.put("startNo", startNo);
				parm_req.put("count", count);
				invoker.queryUserSession(context, user_session_key);

				Model4Event[] model4Events = invoker.queryListEventByNeGroup(context, ne_group_id, event_def, severity, fromTime, toTime, startNo, count);
				int totalCount = invoker.queryCountEventByNeGroup(context, ne_group_id, event_def, severity, fromTime, toTime);
				parm_res.put("count", model4Events.length);
				parm_res.put("total_count", totalCount);
				return new TablePageConfig<Model4Event>(startNo, count, model4Events, totalCount);
			}

			@Override
			public void transaction_log(EmpContext context, Map<String, Object> parm_req, Map<String, Object> parm_res, Exception exception) {
				UtilTransaction.transaction_log(context, TRANSACTION, OPERATION_CODE_ORANGE.FAULT_EVENT_QUERY, parm_req, exception == null, parm_res, exception);
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public TablePageConfig<Model4Event> queryListEventByNe(final Invoker4OrangeClientSwtReqeust request, final int ne_id, final int event_code, final SEVERITY severity, final Date fromTime, final Date toTime, final int startNo, final int count) throws EmpException {
		return (TablePageConfig<Model4Event>) execute(request, new ClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeIf invoker, EmpContext context, String user_session_key, Map<String, Object> parm_req, Map<String, Object> parm_res) throws EmpException {
				EMP_MODEL_EVENT event_def = EMP_MODEL.current().getEvent(event_code);
				parm_req.put("ne_id", ne_id);
				parm_req.put("event_def", event_def);
				parm_req.put("severity", severity);
				parm_req.put("fromTime", fromTime);
				parm_req.put("toTime", toTime);
				parm_req.put("startNo", startNo);
				parm_req.put("count", count);
				invoker.queryUserSession(context, user_session_key);

				Model4Event[] model4Events = invoker.queryListEventByNe(context, ne_id, event_def, severity, fromTime, toTime, startNo, count);
				int totalCount = invoker.queryCountEventByNe(context, ne_id, event_def, severity, fromTime, toTime);
				parm_res.put("count", model4Events.length);
				parm_res.put("total_count", totalCount);
				return new TablePageConfig<Model4Event>(startNo, count, model4Events, totalCount);
			}

			@Override
			public void transaction_log(EmpContext context, Map<String, Object> parm_req, Map<String, Object> parm_res, Exception exception) {
				UtilTransaction.transaction_log(context, TRANSACTION, OPERATION_CODE_ORANGE.FAULT_EVENT_QUERY, parm_req, exception == null, parm_res, exception);
			}
		});
	}

	@Override
	public ModelDisplay4User createUser(final Invoker4OrangeClientSwtReqeust request, final Model4User user, final int startNo, final int count) throws EmpException {
		return (ModelDisplay4User) execute(request, new ClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeIf invoker, EmpContext context, String user_session_key, Map<String, Object> parm_req, Map<String, Object> parm_res) throws EmpException {
				parm_req.put("user_account", user.getUser_account());
				parm_req.put("user_name", user.getUser_name());
				parm_req.put("user_group_id", user.getUser_group_id());
				invoker.queryUserSession(context, user_session_key);

				Model4User user_created = invoker.createUser(context, user);
				parm_res.put("user_id", user_created.getUser_id());
				return queryListUser(invoker, context, startNo, count);
			}

			@Override
			public void transaction_log(EmpContext context, Map<String, Object> parm_req, Map<String, Object> parm_res, Exception exception) {
				UtilTransaction.transaction_log(context, TRANSACTION, OPERATION_CODE_ORANGE.SECURITY_USER_CREATE, parm_req, exception == null, parm_res, exception);
				createOperationLog(context, UtilOperationLog.newInstance(context, OPERATION_CODE_ORANGE.SECURITY_USER_CREATE, exception, UtilTransaction.toString(parm_req, parm_res)));
			}
		});
	}

	@Override
	public ModelDisplay4User queryListUser(final Invoker4OrangeClientSwtReqeust request, final int startNo, final int count) throws EmpException {
		return (ModelDisplay4User) execute(request, new ClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeIf invoker, EmpContext context, String user_session_key, Map<String, Object> parm_req, Map<String, Object> parm_res) throws EmpException {
				parm_req.put("startNo", startNo);
				parm_req.put("count", count);
				invoker.queryUserSession(context, user_session_key);

				ModelDisplay4User user_list = queryListUser(invoker, context, startNo, count);
				parm_res.put("count", user_list.getModel4Users().length);
				return user_list;
			}

			@Override
			public void transaction_log(EmpContext context, Map<String, Object> parm_req, Map<String, Object> parm_res, Exception exception) {
				UtilTransaction.transaction_log(context, TRANSACTION, OPERATION_CODE_ORANGE.SECURITY_USER_QUERY, parm_req, exception == null, parm_res, exception);
			}
		});
	}

	@Override
	public ModelDisplay4User updateUser(final Invoker4OrangeClientSwtReqeust request, final Model4User user, final int startNo, final int count) throws EmpException {
		return (ModelDisplay4User) execute(request, new ClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeIf invoker, EmpContext context, String user_session_key, Map<String, Object> parm_req, Map<String, Object> parm_res) throws EmpException {
				parm_req.put("user_id", user.getUser_id());
				parm_req.put("user_account", user.getUser_account());
				parm_req.put("user_name", user.getUser_name());
				parm_req.put("user_group_id", user.getUser_group_id());
				invoker.queryUserSession(context, user_session_key);
				invoker.updateUser(context, user);

				return queryListUser(invoker, context, startNo, count);
			}

			@Override
			public void transaction_log(EmpContext context, Map<String, Object> parm_req, Map<String, Object> parm_res, Exception exception) {
				UtilTransaction.transaction_log(context, TRANSACTION, OPERATION_CODE_ORANGE.SECURITY_USER_UPDATE, parm_req, exception == null, parm_res, exception);
				createOperationLog(context, UtilOperationLog.newInstance(context, OPERATION_CODE_ORANGE.SECURITY_USER_UPDATE, exception, UtilTransaction.toString(parm_req, parm_res)));
			}
		});
	}

	@Override
	public ModelDisplay4User deleteUser(final Invoker4OrangeClientSwtReqeust request, final int user_id, final int startNo, final int count) throws EmpException {
		return (ModelDisplay4User) execute(request, new ClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeIf invoker, EmpContext context, String user_session_key, Map<String, Object> parm_req, Map<String, Object> parm_res) throws EmpException {
				parm_req.put("user_id", user_id);
				invoker.queryUserSession(context, user_session_key);

				Model4User user_deleted = invoker.deleteUser(context, user_id);
				parm_req.put("user_account", user_deleted.getUser_account());
				parm_req.put("user_name", user_deleted.getUser_name());
				parm_req.put("user_group_id", user_deleted.getUser_group_id());

				return queryListUser(invoker, context, startNo, count);
			}

			@Override
			public void transaction_log(EmpContext context, Map<String, Object> parm_req, Map<String, Object> parm_res, Exception exception) {
				UtilTransaction.transaction_log(context, TRANSACTION, OPERATION_CODE_ORANGE.SECURITY_USER_DELETE, parm_req, exception == null, parm_res, exception);
				createOperationLog(context, UtilOperationLog.newInstance(context, OPERATION_CODE_ORANGE.SECURITY_USER_DELETE, exception, UtilTransaction.toString(parm_req, parm_res)));
			}
		});
	}

	/**
	 * <p>
	 * Query list of user information.
	 * </p>
	 * 
	 * @param invoker
	 * @param context
	 * @param startNo
	 * @param count
	 * @return
	 * @throws EmpException
	 */
	protected ModelDisplay4User queryListUser(Invoker4OrangeIf invoker, EmpContext context, int startNo, int count) throws EmpException {
		int totalCount = invoker.queryCountUser(context);
		Model4User[] model4Users = invoker.queryListUser(context, startNo, count);
		Model4UserGroup[] model4UserGroups = invoker.queryListUserGroup(context, 0, 999999);
		Model4NeGroup[] model4NeGroups = invoker.queryAllNeGroup(context);
		return new ModelDisplay4User(startNo, count, model4Users, totalCount, model4UserGroups, model4NeGroups);
	}

	@SuppressWarnings("unchecked")
	@Override
	public TablePageConfig<Model4OperationLog> queryListOperationLogByNeGroup(final Invoker4OrangeClientSwtReqeust request, final int ne_group_id, final String service, final String function, final String operation, final Boolean result, final Integer user_session_id, final String user_account, final Date fromTime, final Date toTime, final int startNo, final int count) throws EmpException {
		return (TablePageConfig<Model4OperationLog>) execute(request, new ClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeIf invoker, EmpContext context, String user_session_key, Map<String, Object> parm_req, Map<String, Object> parm_res) throws EmpException {
				parm_req.put("ne_group_id", ne_group_id);
				parm_req.put("service", service);
				parm_req.put("function", function);
				parm_req.put("operation", operation);
				parm_req.put("result", result);
				parm_req.put("user_session_id", user_session_id);
				parm_req.put("user_account", user_account);
				parm_req.put("fromTime", fromTime);
				parm_req.put("toTime", toTime);
				parm_req.put("startNo", startNo);
				parm_req.put("count", count);
				invoker.queryUserSession(context, user_session_key);

				Model4OperationLog[] model4OperationLogs = invoker.queryListOperationLogByNeGroup(context, ne_group_id, service, function, operation, result, user_session_id, user_account, fromTime, toTime, startNo, count);
				int totalCount = invoker.queryCountOperationLogByNeGroup(context, ne_group_id, service, function, operation, result, user_session_id, user_account, fromTime, toTime);
				parm_res.put("count", model4OperationLogs.length);
				parm_res.put("total_count", totalCount);
				return new TablePageConfig<Model4OperationLog>(startNo, count, model4OperationLogs, totalCount);
			}

			@Override
			public void transaction_log(EmpContext context, Map<String, Object> parm_req, Map<String, Object> parm_res, Exception exception) {
				UtilTransaction.transaction_log(context, TRANSACTION, OPERATION_CODE_ORANGE.SECURITY_OPERATIONLOG_QUERY, parm_req, exception == null, parm_res, exception);
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public TablePageConfig<Model4OperationLog> queryListOperationLogByNe(final Invoker4OrangeClientSwtReqeust request, final int ne_id, final String service, final String function, final String operation, final Boolean result, final Integer user_session_id, final String user_account, final Date fromTime, final Date toTime, final int startNo, final int count) throws EmpException {
		return (TablePageConfig<Model4OperationLog>) execute(request, new ClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeIf invoker, EmpContext context, String user_session_key, Map<String, Object> parm_req, Map<String, Object> parm_res) throws EmpException {
				parm_req.put("ne_id", ne_id);
				parm_req.put("service", service);
				parm_req.put("function", function);
				parm_req.put("operation", operation);
				parm_req.put("result", result);
				parm_req.put("user_session_id", user_session_id);
				parm_req.put("user_account", user_account);
				parm_req.put("fromTime", fromTime);
				parm_req.put("toTime", toTime);
				parm_req.put("startNo", startNo);
				parm_req.put("count", count);
				invoker.queryUserSession(context, user_session_key);

				Model4OperationLog[] model4OperationLogs = invoker.queryListOperationLogByNe(context, ne_id, service, function, operation, result, user_session_id, user_account, fromTime, toTime, startNo, count);
				int totalCount = invoker.queryCountOperationLogByNe(context, ne_id, service, function, operation, result, user_session_id, user_account, fromTime, toTime);
				parm_res.put("count", model4OperationLogs.length);
				parm_res.put("total_count", totalCount);
				return new TablePageConfig<Model4OperationLog>(startNo, count, model4OperationLogs, totalCount);
			}

			@Override
			public void transaction_log(EmpContext context, Map<String, Object> parm_req, Map<String, Object> parm_res, Exception exception) {
				UtilTransaction.transaction_log(context, TRANSACTION, OPERATION_CODE_ORANGE.SECURITY_OPERATIONLOG_QUERY, parm_req, exception == null, parm_res, exception);
			}
		});
	}

}
