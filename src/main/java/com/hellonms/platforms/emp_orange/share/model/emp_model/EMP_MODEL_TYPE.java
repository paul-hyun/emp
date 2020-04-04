package com.hellonms.platforms.emp_orange.share.model.emp_model;

import java.io.Serializable;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.util.Date;

import com.hellonms.platforms.emp_util.date.UtilDate;
import com.hellonms.platforms.emp_util.string.UtilString;

public enum EMP_MODEL_TYPE {
	INT_32(true, Integer.class), //
	INT_64(true, Long.class), //
	STRING(false, String.class), //
	TEXT(false, String.class), //
	ARRAY_BYTE(false, byte[].class), //
	DATE(false, Date.class), //
	IP_V4(false, Inet4Address.class), //
	IP_V6(false, Inet6Address.class), //
	;

	public final boolean save_statistics;

	public final Class<?> premitive;

	private EMP_MODEL_TYPE(boolean save_statistics, Class<?> premitive) {
		this.save_statistics = save_statistics;
		this.premitive = premitive;
	}

	public String toDisplay(Serializable value) {
		if (value == null) {
			return "";
		} else {
			switch (this) {
			case INT_32:
				return String.valueOf(value);
			case INT_64:
				return String.valueOf(value);
			case STRING:
			case TEXT:
				return (String) value;
			case ARRAY_BYTE:
				return UtilString.toHexString((byte[]) value);
			case DATE:
				return UtilDate.format(UtilDate.MILLI_FORMAT, (Date) value);
			case IP_V4:
				if (value instanceof Inet4Address) {
					return ((Inet4Address) value).getHostAddress();
				}
				return String.valueOf(value);
			case IP_V6:
				return String.valueOf(value);
			default:
				return null;
			}
		}
	}

	public Serializable fromDisplay(String value) {
		if (value == "") {
			return null;
		}
		try {
			switch (this) {
			case INT_32:
				return Integer.parseInt(value);
			case INT_64:
				return Long.parseLong(value);
			case STRING:
			case TEXT:
				return (String) value;
			case ARRAY_BYTE:
				return UtilString.fromHexString(value);
			case DATE:
				return UtilDate.parse(UtilDate.MILLI_FORMAT_TRIM, value);
			case IP_V4:
				return Inet4Address.getByName(value);
			case IP_V6:
				return Inet4Address.getByName(value);
			default:
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

	public String toDatabase(Serializable value) {
		switch (this) {
		case INT_32:
			return value == null ? "" : String.valueOf(value);
		case INT_64:
			return value == null ? "" : String.valueOf(value);
		case STRING:
		case TEXT:
			return value == null ? "" : (String) value;
		case ARRAY_BYTE:
			return value == null ? "" : UtilString.toHexString((byte[]) value);
		case DATE:
			return value == null ? "" : UtilDate.format(UtilDate.MILLI_FORMAT_TRIM, (Date) value);
		case IP_V4:
			return value == null ? "" : ((Inet4Address) value).getHostAddress();
		case IP_V6:
			return value == null ? "" : ((Inet6Address) value).getHostAddress();
		default:
			return "";
		}
	}

	public Serializable fromDatabase(String value) {
		if (value == "") {
			return null;
		}
		try {
			switch (this) {
			case INT_32:
				return Integer.parseInt(value);
			case INT_64:
				return Long.parseLong(value);
			case STRING:
			case TEXT:
				return (String) value;
			case ARRAY_BYTE:
				return UtilString.fromHexString(value);
			case DATE:
				return UtilDate.parse(UtilDate.MILLI_FORMAT_TRIM, value);
			case IP_V4:
				return Inet4Address.getByName(value);
			case IP_V6:
				return Inet4Address.getByName(value);
			default:
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

	public Serializable fromScript(Object value) {
		if (value == null) {
			return null;
		}
		try {
			switch (this) {
			case INT_32:
				if (value instanceof Number) {
					return ((Number) value).intValue();
				} else if (value instanceof String) {
					return Integer.parseInt((String) value);
				} else {
					return null;
				}
			case INT_64:
				if (value instanceof Number) {
					return ((Number) value).longValue();
				} else if (value instanceof String) {
					return Long.parseLong((String) value);
				} else {
					return null;
				}
			case STRING:
			case TEXT:
				return String.valueOf(value);
			case ARRAY_BYTE:
				if (value instanceof byte[]) {
					return (byte[]) value;
				} else if (value instanceof String) {
					return UtilString.fromHexString((String) value);
				} else {
					return null;
				}
			case DATE:
				if (value instanceof String) {
					return UtilDate.parse(UtilDate.MILLI_FORMAT_TRIM, (String) value);
				} else {
					return null;
				}
			case IP_V4:
				if (value instanceof String) {
					return Inet4Address.getByName((String) value);
				} else {
					return null;
				}
			case IP_V6:
				if (value instanceof String) {
					return Inet4Address.getByName((String) value);
				} else {
					return null;
				}
			default:
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

}
