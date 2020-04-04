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
import com.hellonms.platforms.emp_orange.share.model.security.operation_log.OPERATION_CODE_ORANGE;

@RestController
public class OpenApi4ServerController extends OpenApiControllerAt {

	@RequestMapping(path = "/openapi/environment/terminate", method = RequestMethod.GET)
	public Map<String, Object> login(HttpServletRequest request) {
		return execute(request, new RestControllerTaskIf() {
			@Override
			public Map<String, Object> run(HttpServletRequest request, Invoker4OrangeIf invoker, EmpContext context, LinkedHashMap<String, Object> parm_req, LinkedHashMap<String, Object> parm_res) throws EmpException {
				if (request.getRemoteAddr().equals("127.0.0.1")) {
					new Thread() {
						@Override
						public void run() {
							try {
								Thread.sleep(5000L);
							} catch (Exception e) {
								e.printStackTrace();
							}
							System.exit(0);
						}
					}.start();
				}

				Map<String, Object> response = UtilRest.success_response();
				return response;
			}

			@Override
			public void transaction_log(EmpContext context, Map<String, Object> parm_req, Map<String, Object> parm_res, Exception exception) {
				UtilTransaction.transaction_log(context, getTransaction(), OPERATION_CODE_ORANGE.SECURITY_USERSESSION_TERMINATE, parm_req, exception == null, parm_res, exception);
			}
		});
	}

}
