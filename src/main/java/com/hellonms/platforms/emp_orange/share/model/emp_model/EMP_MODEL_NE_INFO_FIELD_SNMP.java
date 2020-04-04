package com.hellonms.platforms.emp_orange.share.model.emp_model;

import java.util.ArrayList;
import java.util.List;

import com.hellonms.platforms.emp_orange.share.model.network.ne_session.NE_SESSION_OID;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.snmp.SNMP_OID;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.snmp.SNMP_TYPE;
import com.hellonms.platforms.emp_util.string.UtilString;

public class EMP_MODEL_NE_INFO_FIELD_SNMP {

	public static String[] getType_reals() {
		List<String> type_reals = new ArrayList<String>();
		for (SNMP_TYPE type_real : SNMP_TYPE.values()) {
			type_reals.add(type_real.name());
		}
		return type_reals.toArray(new String[0]);
	}

	public static int getIndex_length(String oid) {
		return new SNMP_OID(oid).getIndex_length();
	}

	public static boolean isTable(String oid) {
		return oid.contains("*");
	}

	public static NE_SESSION_OID getNe_session_oid(String oid) {
		return new SNMP_OID(oid);
	}

	static String getError(String oid, String type_remote, EMP_MODEL_TYPE type_local, int enum_code, boolean index, int index_count) {
		SNMP_TYPE snmp_type = null;
		try {
			snmp_type = SNMP_TYPE.valueOf(type_remote);
		} catch (Exception e) {
			return UtilString.format("잘못된 값 입니다. '{}'", "Type_remote");
		}
		if (index) {
			if (!SNMP_TYPE.isIndexable(snmp_type)) {
				return UtilString.format("잘못된 값 입니다. '{}': {}", "index type", type_remote);
			}
		} else {
			if (!snmp_type.isAllowed(type_local)) {
				return UtilString.format("잘못된 값 입니다. '{}', {} <-> {}", "Type", type_local, snmp_type);
			}
		}
		String[] tokens = oid.split("\\.");
		int index_level = 0;
		for (String token : tokens) {
			if (token.equals("*")) {
				index_level++;
			} else {
				if (0 < index_level) {
					return UtilString.format("잘못된 값 입니다. '{}'", "Oid");
				}
				try {
					Integer.parseInt(token);
				} catch (Exception e) {
					return UtilString.format("잘못된 값 입니다. '{}'", "Oid");
				}
			}
		}
		if (index_level < index_count) {
			return UtilString.format("잘못된 값 입니다. '{}' {}", "Oid", "index");
		}
		return null;
	}

	static String getErrorUpdate(String oid, String type_remote, EMP_MODEL_TYPE type_local, int enum_code, boolean index) {
		SNMP_TYPE snmp_type = null;
		try {
			snmp_type = SNMP_TYPE.valueOf(type_remote);
		} catch (Exception e) {
			return UtilString.format("잘못된 값 입니다. '{}'", "Type_remote");
		}
		if (index) {
			if (!SNMP_TYPE.isIndexable(snmp_type)) {
				return UtilString.format("잘못된 값 입니다. '{}': {}", "index type", type_remote);
			}
		} else {
			if (!snmp_type.isAllowed(type_local)) {
				return UtilString.format("잘못된 값 입니다. '{}', {} <-> {}", "Type", type_local, snmp_type);
			}
		}
		String[] tokens = oid.split("\\.");
		int index_level = 0;
		for (String token : tokens) {
			if (token.equals("*")) {
				index_level++;
			} else {
				if (0 < index_level) {
					return UtilString.format("잘못된 값 입니다. '{}'", "Oid");
				}
				try {
					Integer.parseInt(token);
				} catch (Exception e) {
					return UtilString.format("잘못된 값 입니다. '{}'", "Oid");
				}
			}
		}
		return null;
	}

}
