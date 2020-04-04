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
import com.hellonms.platforms.emp_orange.share.model.network.ne.Model4Ne;
import com.hellonms.platforms.emp_orange.share.model.security.operation_log.OPERATION_CODE_ORANGE;

@RestController
public class OpenApi4NeController extends OpenApiControllerAt {

	@RequestMapping(path = "/openapi/network/ne.do", method = RequestMethod.GET)
	public Map<String, Object> queryList(HttpServletRequest request) {
		return execute(request, new RestControllerTaskIf() {
			@Override
			public Map<String, Object> run(HttpServletRequest request, Invoker4OrangeIf invoker, EmpContext context, LinkedHashMap<String, Object> parm_req, LinkedHashMap<String, Object> parm_res) throws EmpException {
				int startNo = getInt(request, "startNo", 0);
				int count = getInt(request, "count", 30);

				int total = invoker.queryCountUser(context);
				Model4Ne[] nes = invoker.queryListNe(context, startNo, count);

				Map<String, Object> response = UtilRest.success_response();
				response.put("total", total);
				response.put("startNo", startNo);
				response.put("count", nes.length);
				response.put("nes", nes);
				return response;
			}

			@Override
			public void transaction_log(EmpContext context, Map<String, Object> parm_req, Map<String, Object> parm_res, Exception exception) {
				UtilTransaction.transaction_log(context, getTransaction(), OPERATION_CODE_ORANGE.NETWORK_NE_QUERY, parm_req, exception == null, parm_res, exception);
			}
		});
	}

	@RequestMapping(path = "/openapi/network/ne.do/{ne_id}", method = RequestMethod.GET)
	public Map<String, Object> query(HttpServletRequest request, @PathVariable(value = "ne_id") int ne_id) {
		return execute(request, new RestControllerTaskIf() {
			@Override
			public Map<String, Object> run(HttpServletRequest request, Invoker4OrangeIf invoker, EmpContext context, LinkedHashMap<String, Object> parm_req, LinkedHashMap<String, Object> parm_res) throws EmpException {
				Model4Ne ne = invoker.queryNe(context, ne_id);

				Map<String, Object> response = UtilRest.success_response();
				response.put("ne", ne);
				return response;
			}

			@Override
			public void transaction_log(EmpContext context, Map<String, Object> parm_req, Map<String, Object> parm_res, Exception exception) {
				UtilTransaction.transaction_log(context, getTransaction(), OPERATION_CODE_ORANGE.NETWORK_NE_QUERY, parm_req, exception == null, parm_res, exception);
			}
		});
	}

	@RequestMapping(path = "/openapi/network/ne.do/{ne_id}", method = RequestMethod.DELETE)
	public Map<String, Object> delete(HttpServletRequest request, @PathVariable(value = "ne_id") int ne_id) {
		return execute(request, new RestControllerTaskIf() {
			@Override
			public Map<String, Object> run(HttpServletRequest request, Invoker4OrangeIf invoker, EmpContext context, LinkedHashMap<String, Object> parm_req, LinkedHashMap<String, Object> parm_res) throws EmpException {
				Model4Ne ne = invoker.deleteNe(context, ne_id);

				Map<String, Object> response = UtilRest.success_response();
				response.put("ne", ne);
				return response;
			}

			@Override
			public void transaction_log(EmpContext context, Map<String, Object> parm_req, Map<String, Object> parm_res, Exception exception) {
				UtilTransaction.transaction_log(context, getTransaction(), OPERATION_CODE_ORANGE.NETWORK_NE_DELETE, parm_req, exception == null, parm_res, exception);
			}
		});
	}

}
