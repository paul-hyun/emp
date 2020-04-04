package com.hellonms.platforms.emp_core.server.transaction;

import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hellonms.platforms.emp_core.share.operation.OPERATION_CODE;
import com.hellonms.platforms.emp_util.date.UtilDate;

public class UtilTransaction {

	private static final Logger blackBox = LoggerFactory.getLogger(UtilTransaction.class);;

	private static final String DELIMINATOR = "|";

	private static final Set<String> encripts = new HashSet<String>();
	static {
		encripts.add("password");
	}

	public static void addEncript(String key) {
		encripts.add(key);
	}

	public static void transaction_log(EmpContext context, String tranaction, OPERATION_CODE operation, Map<String, Object> request, boolean result, Map<String, Object> response, Exception exception) {
		UtilTransaction.transaction_log(context.getTransaction_id_string(), context.getUser_id(), context.getUser_session_id(), context.getUser_account(), context.getUser_ip(), tranaction, operation, new Date(context.getOpen_timestamp()), context.getClose_timestamp() == 0 ? new Date() : new Date(context.getClose_timestamp()), request, result, response, exception == null ? null : exception.getMessage());
	}

	public static void transaction_log(String transaction_id, int user_id, int user_session_id, String user_account, String user_ip, String tranaction, OPERATION_CODE operation, Date start_time, Date end_time, Map<String, Object> request, boolean result, Map<String, Object> response, String exception) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(transaction_id).append(DELIMINATOR);
		stringBuilder.append(user_id).append(DELIMINATOR);
		stringBuilder.append(user_session_id).append(DELIMINATOR);
		stringBuilder.append(user_account).append(DELIMINATOR);
		stringBuilder.append(user_ip).append(DELIMINATOR);
		stringBuilder.append(tranaction).append(DELIMINATOR);
		stringBuilder.append(operation).append(DELIMINATOR);
		stringBuilder.append(UtilDate.format(UtilDate.MILLI_FORMAT, start_time)).append(DELIMINATOR);
		stringBuilder.append(end_time.getTime() - start_time.getTime()).append(DELIMINATOR);
		if (request == null) {
			stringBuilder.append("").append(DELIMINATOR);
		} else {
			int count = 0;
			for (Entry<String, Object> entry : request.entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();
				if (value != null) {
					if (0 < count++) {
						stringBuilder.append(",");
					}
					stringBuilder.append(key).append("=").append(toString(key, value));
				}
			}
			stringBuilder.append(DELIMINATOR);
		}
		stringBuilder.append(result).append(DELIMINATOR);
		if (response == null) {
			stringBuilder.append("").append(DELIMINATOR);
		} else {
			int count = 0;
			for (Entry<String, Object> entry : response.entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();
				if (value != null) {
					if (0 < count++) {
						stringBuilder.append(",");
					}
					stringBuilder.append(key).append("=").append(toString(key, value));
				}
			}
			stringBuilder.append(DELIMINATOR);
		}
		if (exception == null) {
			stringBuilder.append("");
		} else {
			stringBuilder.append(exception);
		}
		blackBox.error(stringBuilder.toString());
	}

	private static String toString(String key, Object value) {
		if (value instanceof Date) {
			return UtilDate.format((Date) value);
		} else if (encripts.contains(key)) {
			return "****";
		} else if (value instanceof Object[]) {
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("[");
			int index = 0;
			for (Object item : (Object[]) value) {
				stringBuilder.append(index == 0 ? "" : ",").append(item);
			}
			stringBuilder.append("]");
			return stringBuilder.toString();
		} else {
			return value.toString();
		}
	}

	public static String toString(Map<String, Object> request, Map<String, Object> response) {
		StringBuilder stringBuilder = new StringBuilder();
		if (request == null) {
			stringBuilder.append("").append(DELIMINATOR);
		} else {
			int count = 0;
			for (Entry<String, Object> entry : request.entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();
				if (value != null) {
					if (0 < count++) {
						stringBuilder.append(",");
					}
					stringBuilder.append(key).append("=").append(toString(key, value));
				}
			}
			stringBuilder.append(DELIMINATOR);
		}
		if (response == null) {
			stringBuilder.append("");
		} else {
			int count = 0;
			for (Entry<String, Object> entry : response.entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();
				if (value != null) {
					if (0 < count++) {
						stringBuilder.append(",");
					}
					stringBuilder.append(key).append("=").append(toString(key, value));
				}
			}
		}
		return stringBuilder.toString();
	}

}
