package com.hellonms.platforms.emp_orange.client_openapi.rest;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.server.transaction.UtilTransaction;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_onion.share.rest.UtilRest;
import com.hellonms.platforms.emp_orange.server.invoker.Invoker4OrangeIf;
import com.hellonms.platforms.emp_orange.share.model.network.ne_group.Model4NeGroup;
import com.hellonms.platforms.emp_orange.share.model.security.operation_log.OPERATION_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.security.user.Model4User;
import com.hellonms.platforms.emp_orange.share.model.security.user_session.Model4UserSession;
import com.hellonms.platforms.emp_util.http.UtilJson;

@RestController
public class OpenApi4UserController extends OpenApiControllerAt {

	@RequestMapping(path = "/openapi/security/user.do", method = RequestMethod.POST, produces = { "application/json; charset=utf8", "text/plain" })
	public Map<String, Object> create(HttpServletRequest request) {
		return execute(request, new RestControllerTaskIf() {
			@Override
			public Map<String, Object> run(HttpServletRequest request, Invoker4OrangeIf invoker, EmpContext context, LinkedHashMap<String, Object> parm_req, LinkedHashMap<String, Object> parm_res) throws EmpException {
				@SuppressWarnings("unused")
				Model4UserSession userSession = queryUserSession(request, context, invoker);

				Map<String, Object> request_map = toMap(request);
				Model4User user = UtilJson.toObject(request_map.get("user"), Model4User.class);

				if (user != null) {
					user.setManage_ne_groups(new int[] { Model4NeGroup.ROOT_NE_GROUP_ID });
					invoker.createUser(context, user);
				}

				Map<String, Object> response = UtilRest.success_response();
				return response;
			}

			@Override
			public void transaction_log(EmpContext context, Map<String, Object> parm_req, Map<String, Object> parm_res, Exception exception) {
				UtilTransaction.transaction_log(context, getTransaction(), OPERATION_CODE_ORANGE.SECURITY_USER_CREATE, parm_req, exception == null, parm_res, exception);
			}
		});
	}

	@RequestMapping(path = "/openapi/security/user.do", method = RequestMethod.GET, produces = { "application/json; charset=utf8", "text/plain" })
	public Map<String, Object> queryList(HttpServletRequest request) {
		return execute(request, new RestControllerTaskIf() {
			@Override
			public Map<String, Object> run(HttpServletRequest request, Invoker4OrangeIf invoker, EmpContext context, LinkedHashMap<String, Object> parm_req, LinkedHashMap<String, Object> parm_res) throws EmpException {
				@SuppressWarnings("unused")
				Model4UserSession userSession = queryUserSession(request, context, invoker);

				int startNo = getInt(request, "startNo", 0);
				int count = getInt(request, "count", 30);

				int total = invoker.queryCountUser(context);
				Model4User[] users = invoker.queryListUser(context, startNo, count);

				Map<String, Object> response = UtilRest.success_response();
				response.put("total", total);
				response.put("startNo", startNo);
				response.put("count", users.length);
				response.put("users", users);
				return response;
			}

			@Override
			public void transaction_log(EmpContext context, Map<String, Object> parm_req, Map<String, Object> parm_res, Exception exception) {
				UtilTransaction.transaction_log(context, getTransaction(), OPERATION_CODE_ORANGE.SECURITY_USER_QUERY, parm_req, exception == null, parm_res, exception);
			}
		});
	}

	@RequestMapping(path = "/openapi/security/user.do/{user_id}", method = RequestMethod.GET, produces = { "application/json; charset=utf8", "text/plain" })
	public Map<String, Object> query(HttpServletRequest request, @PathVariable(value = "user_id") int user_id) {
		return execute(request, new RestControllerTaskIf() {
			@Override
			public Map<String, Object> run(HttpServletRequest request, Invoker4OrangeIf invoker, EmpContext context, LinkedHashMap<String, Object> parm_req, LinkedHashMap<String, Object> parm_res) throws EmpException {
				@SuppressWarnings("unused")
				Model4UserSession userSession = queryUserSession(request, context, invoker);

				Model4User user = invoker.queryUser(context, user_id);

				Map<String, Object> response = UtilRest.success_response();
				response.put("user", user);
				return response;
			}

			@Override
			public void transaction_log(EmpContext context, Map<String, Object> parm_req, Map<String, Object> parm_res, Exception exception) {
				UtilTransaction.transaction_log(context, getTransaction(), OPERATION_CODE_ORANGE.SECURITY_USER_QUERY, parm_req, exception == null, parm_res, exception);
			}
		});
	}

	@RequestMapping(path = "/openapi/security/user.do", method = RequestMethod.PUT, produces = { "application/json; charset=utf8", "text/plain" })
	public Map<String, Object> update(HttpServletRequest request) {
		return execute(request, new RestControllerTaskIf() {
			@Override
			public Map<String, Object> run(HttpServletRequest request, Invoker4OrangeIf invoker, EmpContext context, LinkedHashMap<String, Object> parm_req, LinkedHashMap<String, Object> parm_res) throws EmpException {
				@SuppressWarnings("unused")
				Model4UserSession userSession = queryUserSession(request, context, invoker);

				Map<String, Object> request_map = toMap(request);
				Model4User user = UtilJson.toObject(request_map.get("user"), Model4User.class);

				if (user != null) {
					user.setManage_ne_groups(new int[] { Model4NeGroup.ROOT_NE_GROUP_ID });
					invoker.updateUser(context, user);
				}

				Map<String, Object> response = UtilRest.success_response();
				return response;
			}

			@Override
			public void transaction_log(EmpContext context, Map<String, Object> parm_req, Map<String, Object> parm_res, Exception exception) {
				UtilTransaction.transaction_log(context, getTransaction(), OPERATION_CODE_ORANGE.SECURITY_USER_CREATE, parm_req, exception == null, parm_res, exception);
			}
		});
	}

	@RequestMapping(path = "/openapi/security/user.do/{user_id}", method = RequestMethod.DELETE, produces = { "application/json; charset=utf8", "text/plain" })
	public Map<String, Object> delete(HttpServletRequest request, @PathVariable(value = "user_id") int user_id) {
		return execute(request, new RestControllerTaskIf() {
			@Override
			public Map<String, Object> run(HttpServletRequest request, Invoker4OrangeIf invoker, EmpContext context, LinkedHashMap<String, Object> parm_req, LinkedHashMap<String, Object> parm_res) throws EmpException {
				// @SuppressWarnings("unused")
				// Model4UserSession userSession = queryUserSession(request, context, invoker);

				Model4User user = invoker.deleteUser(context, user_id);

				Map<String, Object> response = UtilRest.success_response();
				response.put("user", user);
				return response;
			}

			@Override
			public void transaction_log(EmpContext context, Map<String, Object> parm_req, Map<String, Object> parm_res, Exception exception) {
				UtilTransaction.transaction_log(context, getTransaction(), OPERATION_CODE_ORANGE.SECURITY_USER_DELETE, parm_req, exception == null, parm_res, exception);
			}
		});
	}

}
