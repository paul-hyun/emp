package com.hellonms.platforms.emp_onion.share.rest;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.hellonms.platforms.emp_core.share.error.ERROR_CODE;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_util.http.UtilJson;

public class UtilRest {

	private static final String RESULT = "result";

	private static final String ERR_CODE = "error_code";

	private static final String ERR_ARGS = "error_args";

	private static final String ERR_MSG = "error_msg";

	public static Map<String, Object> success_response() {
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		response.put(RESULT, true);
		return response;
	}

	public static Map<String, Object> fail_response(Exception e) {
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		response.put(RESULT, false);
		if (e instanceof EmpException) {
			EmpException exception = (EmpException) e;
			response.put(ERR_CODE, exception.getError_code().getError_code());
			response.put(ERR_ARGS, exception.getArgs());
			response.put(ERR_MSG, exception.getMessage());
		} else {
			response.put(ERR_CODE, ERROR_CODE.ERROR_UNKNOWN.getError_code());
			response.put(ERR_ARGS, new String[] {});
			response.put(ERR_MSG, e.getMessage());
		}
		return response;
	}

	public static Map<String, Object> parse_response(String json) throws EmpException {
		Map<String, Object> response = UtilJson.toMap(json);
		Boolean result = (Boolean) response.get(RESULT);
		if (result != null && result) {
			return response;
		} else {
			Integer error_code = (Integer) response.get(ERR_CODE);
			List<?> error_args = (List<?>) response.get(ERR_ARGS);
			throw new EmpException(ERROR_CODE.valueOf(error_code == null ? 0 : error_code), error_args.toArray());
		}
	}

}
