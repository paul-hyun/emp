package com.hellonms.platforms.emp_onion.client_common.rest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.hellonms.platforms.emp_core.server.log.BlackBox;
import com.hellonms.platforms.emp_core.server.log.LEVEL;
import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.server.workflow.WorkflowMap;
import com.hellonms.platforms.emp_core.share.error.ERROR_CODE_CORE;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_onion.share.rest.UtilRest;
import com.hellonms.platforms.emp_orange.server.invoker.Invoker4OrangeIf;
import com.hellonms.platforms.emp_util.http.UtilJson;

public abstract class RestControllerAt {

	protected interface RestControllerTaskIf {

		public Map<String, Object> run(HttpServletRequest request, Invoker4OrangeIf invoker, EmpContext context, LinkedHashMap<String, Object> parm_req, LinkedHashMap<String, Object> parm_res) throws EmpException;

		public void transaction_log(EmpContext context, Map<String, Object> parm_req, Map<String, Object> parm_res, Exception exception);

	}

	private static BlackBox blackBox = new BlackBox(RestControllerAt.class);

	@ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
	@ExceptionHandler({ EmpException.class })
	public String empError(HttpServletRequest req, EmpException ex) {
		return ex.getMessage();
	}

	@ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
	@ExceptionHandler({ Exception.class })
	public String unknownError(HttpServletRequest req, Exception ex) {
		return ex.getMessage();
	}

	protected Map<String, Object> execute(HttpServletRequest request, RestControllerTaskIf task) {
		EmpContext context = new EmpContext(this);
		context.setUser_ip(request.getRemoteAddr());
		LinkedHashMap<String, Object> parm_req = new LinkedHashMap<String, Object>();
		LinkedHashMap<String, Object> parm_res = new LinkedHashMap<String, Object>();
		Exception exception = null;

		try {
			Invoker4OrangeIf invoker = (Invoker4OrangeIf) WorkflowMap.getInvoker(Invoker4OrangeIf.class);
			if (!invoker.isReady(context)) {
				throw new EmpException(ERROR_CODE_CORE.SERVER_NOTREADY);
			}
			Map<String, Object> response = task.run(request, invoker, context, parm_req, parm_res);
			return response;
		} catch (EmpException e) {
			if (blackBox.isEnabledFor(LEVEL.Fatal)) {
				blackBox.log(LEVEL.Fatal, context, e);
			}
			context.rollback();

			exception = e;
			return UtilRest.fail_response(e);
		} catch (Exception e) {
			if (blackBox.isEnabledFor(LEVEL.Fatal)) {
				blackBox.log(LEVEL.Fatal, context, e);
			}
			context.rollback();

			exception = e;
			return UtilRest.fail_response(e);
		} finally {
			try {
				context.close();
			} finally {
				task.transaction_log(context, parm_req, parm_res, exception);
			}
		}
	}

	protected Map<String, Object> toMap(HttpServletRequest request) {
		try {
			StringBuilder contents = new StringBuilder();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream()));
			for (String line = null; (line = bufferedReader.readLine()) != null;) {
				contents.append(line);
			}
			return UtilJson.toMap(contents.toString());
		} catch (Exception e) {
			return new LinkedHashMap<String, Object>();
		}
	}

	protected abstract String getTransaction();

	protected String getString(HttpServletRequest request, String name) {
		String value = request.getParameter(name);
		if (value == null) {
			return "";
		} else {
			return value;
		}
	}

	protected int getInt(HttpServletRequest request, String name) {
		return getInt(request, name, 0);
	}

	protected int getInt(HttpServletRequest request, String name, int defultValue) {
		String value = request.getParameter(name);
		if (value == null) {
			return defultValue;
		} else {
			try {
				return Integer.parseInt(value);
			} catch (Exception e) {
				return defultValue;
			}
		}
	}

	protected Integer getInteger(HttpServletRequest request, String name) {
		String value = request.getParameter(name);
		if (value == null) {
			return null;
		} else {
			try {
				return Integer.parseInt(value);
			} catch (Exception e) {
				return null;
			}
		}
	}

	protected long getLong(HttpServletRequest request, String name) {
		return getLong(request, name, 0L);
	}

	protected long getLong(HttpServletRequest request, String name, long defultValue) {
		String value = request.getParameter(name);
		if (value == null) {
			return defultValue;
		} else {
			try {
				return Long.parseLong(value);
			} catch (Exception e) {
				return defultValue;
			}
		}
	}

	protected String getString(Map<String, Object> request, String name) {
		Object value = request.get(name);
		if (value == null) {
			return "";
		} else if (value instanceof String) {
			return (String) value;
		} else {
			return String.valueOf(value);
		}
	}

}
