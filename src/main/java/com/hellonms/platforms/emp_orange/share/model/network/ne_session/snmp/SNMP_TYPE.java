/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.share.model.network.ne_session.snmp;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_orange.share.error.ERROR_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_TYPE;
import com.hellonms.platforms.emp_util.string.UtilString;

/**
 * <p>
 * 데이터 타입
 * </p>
 *
 * @since 1.6
 * @create 2015. 4. 7.
 * @modified 2015. 4. 7.
 * @author cchyun
 *
 */
public enum SNMP_TYPE {

	INTEGER_32(2, EMP_MODEL_TYPE.INT_32), //
	OCTET_STRING(4, EMP_MODEL_TYPE.STRING, EMP_MODEL_TYPE.TEXT, EMP_MODEL_TYPE.ARRAY_BYTE, EMP_MODEL_TYPE.DATE), //
	NULL(5), //
	OBJECT_IDENTIFIER(6, EMP_MODEL_TYPE.STRING), //
	IPADDRESS(64, EMP_MODEL_TYPE.IP_V4), //
	COUNTER_32(65, EMP_MODEL_TYPE.INT_64), //
	GAUGE_32(66, EMP_MODEL_TYPE.INT_64), //
	TIME_TICKS(67, EMP_MODEL_TYPE.DATE), //
	OPAQUE(68), //
	COUNTER_64(70, EMP_MODEL_TYPE.INT_64), //
	;

	public final int id;

	private final EMP_MODEL_TYPE[] types;

	private SNMP_TYPE(int id, EMP_MODEL_TYPE... types) {
		this.id = id;
		this.types = types;
	}

	private static Map<Integer, SNMP_TYPE> snmp_type_map = null;

	public static SNMP_TYPE toEnum(int id) throws EmpException {
		if (snmp_type_map == null) {
			Map<Integer, SNMP_TYPE> snmp_type_map = new HashMap<Integer, SNMP_TYPE>();
			for (SNMP_TYPE snmp_type : SNMP_TYPE.values()) {
				snmp_type_map.put(snmp_type.id, snmp_type);
			}
			SNMP_TYPE.snmp_type_map = snmp_type_map;
		}
		SNMP_TYPE snmp_type = snmp_type_map.get(id);
		if (snmp_type != null) {
			return snmp_type;
		}
		throw new EmpException(ERROR_CODE_ORANGE.NESESSION_NOTYPE, "SNMP", id);
	}

	public String toDatabase(Serializable value) {
		switch (this) {
		case INTEGER_32:
			return String.valueOf(value);
		case OCTET_STRING:
			return UtilString.toHexString((byte[]) value);
		case NULL:
			return null;
		case OBJECT_IDENTIFIER:
			return String.valueOf(value);
		case IPADDRESS:
			return UtilString.toHexString(((InetAddress) value).getAddress());
		case COUNTER_32:
			return String.valueOf(value);
		case GAUGE_32:
			return String.valueOf(value);
		case TIME_TICKS:
			return String.valueOf(value);
		case OPAQUE:
			return null;
		case COUNTER_64:
			return String.valueOf(value);
		}
		return null;
	}

	public Serializable fromDatabase(String value) {
		switch (this) {
		case INTEGER_32:
			return Integer.parseInt(value);
		case OCTET_STRING:
			return UtilString.fromHexString(value);
		case NULL:
			return null;
		case OBJECT_IDENTIFIER:
			return value;
		case IPADDRESS:
			try {
				return InetAddress.getByAddress(UtilString.fromHexString(value));
			} catch (Exception e) {
				return null;
			}
		case COUNTER_32:
			return Long.parseLong(value);
		case GAUGE_32:
			return Long.parseLong(value);
		case TIME_TICKS:
			return String.valueOf(value);
		case OPAQUE:
			return null;
		case COUNTER_64:
			return Long.parseLong(value);
		}
		return null;
	}

	public EMP_MODEL_TYPE[] getModel_types() {
		return types;
	}

	public boolean isAllowed(EMP_MODEL_TYPE type) {
		for (EMP_MODEL_TYPE ttt : types) {
			if (ttt == type) {
				return true;
			}
		}
		return false;
	}

	public static boolean isIndexable(SNMP_TYPE snmp_type) {
		switch (snmp_type) {
		case INTEGER_32:
		case OCTET_STRING:
		case IPADDRESS:
			return true;
		default:
			return false;
		}
	}

}
