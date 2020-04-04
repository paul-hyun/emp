package com.hellonms.platforms.emp_orange.client_openapi.rest;

import javax.servlet.http.HttpServletRequest;

import com.hellonms.platforms.emp_core.server.log.BlackBox;
import com.hellonms.platforms.emp_core.server.log.LEVEL;
import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.server.workflow.WorkflowMap;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_onion.client_common.rest.RestControllerAt;
import com.hellonms.platforms.emp_orange.server.invoker.Invoker4OrangeIf;
import com.hellonms.platforms.emp_orange.share.error.ERROR_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.security.operation_log.Model4OperationLog;
import com.hellonms.platforms.emp_orange.share.model.security.user_session.Model4UserSession;

public abstract class OpenApiControllerAt extends RestControllerAt {

	private static BlackBox blackBox = new BlackBox(OpenApiControllerAt.class);

	@Override
	protected String getTransaction() {
		return "OPEN_API";
	}

	protected Model4UserSession queryUserSession(HttpServletRequest request, EmpContext context, Invoker4OrangeIf invoker) throws EmpException {
		String user_session_key = request.getHeader("Authorization");

		Model4UserSession userSession = null;
		if (user_session_key != null) {
			userSession = invoker.queryUserSession(context, user_session_key);
		}

		if (userSession == null) {
			throw new EmpException(ERROR_CODE_ORANGE.USER_NOTLOGIN);
		}
		return userSession;
	}

	protected void createOperationLog(EmpContext context, Model4OperationLog model4OperationLog) {
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

}
