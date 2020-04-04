package com.hellonms.platforms.emp_orange.client_openapi.rest;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.server.transaction.UtilTransaction;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_onion.share.rest.UtilRest;
import com.hellonms.platforms.emp_orange.server.invoker.Invoker4OrangeIf;
import com.hellonms.platforms.emp_orange.server.util.UtilOperationLog;
import com.hellonms.platforms.emp_orange.share.model.security.operation_log.OPERATION_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.security.user_session.Model4UserSession;

@RestController
public class OpenApi4UserSessionController extends OpenApiControllerAt {

	@RequestMapping(path = "/openapi/security/user_session.do", method = RequestMethod.POST)
	public Map<String, Object> login(HttpServletRequest request) {
		return execute(request, new RestControllerTaskIf() {
			@Override
			public Map<String, Object> run(HttpServletRequest request, Invoker4OrangeIf invoker, EmpContext context, LinkedHashMap<String, Object> parm_req, LinkedHashMap<String, Object> parm_res) throws EmpException {
				Map<String, Object> request_map = toMap(request);
				String user_account = (String) request_map.get("user_account");
				String password = (String) request_map.get("password");

				parm_req.put("user_account", user_account);
				String user_session_key = invoker.login(context, user_account, password, request.getRemoteAddr());
				Model4UserSession user_session = invoker.queryUserSession(context, user_session_key);

				Map<String, Object> response = UtilRest.success_response();
				response.put("user_session_key", user_session_key);
				response.put("user_group_id", user_session == null ? 0 : user_session.getUser_group_id());
				return response;
			}

			@Override
			public void transaction_log(EmpContext context, Map<String, Object> parm_req, Map<String, Object> parm_res, Exception exception) {
				UtilTransaction.transaction_log(context, getTransaction(), OPERATION_CODE_ORANGE.SECURITY_USERSESSION_LOGIN, parm_req, exception == null, parm_res, exception);
				createOperationLog(context, UtilOperationLog.newInstance(context, OPERATION_CODE_ORANGE.SECURITY_USERSESSION_LOGIN, exception, UtilTransaction.toString(parm_req, parm_res)));
			}
		});
	}

	@RequestMapping(path = "/openapi/security/user_session.do", method = RequestMethod.GET)
	public Map<String, Object> query(HttpServletRequest request) {
		return execute(request, new RestControllerTaskIf() {
			@Override
			public Map<String, Object> run(HttpServletRequest request, Invoker4OrangeIf invoker, EmpContext context, LinkedHashMap<String, Object> parm_req, LinkedHashMap<String, Object> parm_res) throws EmpException {
				String user_session_key = request.getHeader("Authorization");
				Model4UserSession user_session = user_session_key == null ? null : invoker.queryUserSession(context, user_session_key);

				Map<String, Object> response = UtilRest.success_response();
				response.put("user_session", user_session);
				return response;
			}

			@Override
			public void transaction_log(EmpContext context, Map<String, Object> parm_req, Map<String, Object> parm_res, Exception exception) {
				UtilTransaction.transaction_log(context, getTransaction(), OPERATION_CODE_ORANGE.SECURITY_USERSESSION_QUERY, parm_req, exception == null, parm_res, exception);
				createOperationLog(context, UtilOperationLog.newInstance(context, OPERATION_CODE_ORANGE.SECURITY_USERSESSION_LOGOUT, exception, UtilTransaction.toString(parm_req, parm_res)));
			}
		});
	}

	@RequestMapping(path = "/openapi/security/user_session.do", method = RequestMethod.DELETE)
	public Map<String, Object> logout(HttpServletRequest request) {
		return execute(request, new RestControllerTaskIf() {
			@Override
			public Map<String, Object> run(HttpServletRequest request, Invoker4OrangeIf invoker, EmpContext context, LinkedHashMap<String, Object> parm_req, LinkedHashMap<String, Object> parm_res) throws EmpException {
				String user_session_key = request.getHeader("Authorization");
				parm_req.put("user_account", "");
				invoker.logout(context, user_session_key);

				Map<String, Object> response = UtilRest.success_response();
				return response;
			}

			@Override
			public void transaction_log(EmpContext context, Map<String, Object> parm_req, Map<String, Object> parm_res, Exception exception) {
				UtilTransaction.transaction_log(context, getTransaction(), OPERATION_CODE_ORANGE.SECURITY_USERSESSION_LOGOUT, parm_req, exception == null, parm_res, exception);
			}
		});
	}

}
