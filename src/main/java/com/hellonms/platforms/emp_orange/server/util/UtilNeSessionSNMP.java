/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.server.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TimeZone;
import java.util.TreeMap;

import com.hellonms.platforms.emp_core.server.log.BlackBox;
import com.hellonms.platforms.emp_core.server.log.LEVEL;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_orange.share.error.ERROR_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO_FIELD;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_TYPE;
import com.hellonms.platforms.emp_orange.share.model.network.ne.Model4Ne;
import com.hellonms.platforms.emp_orange.share.model.network.ne_info.Model4NeInfo;
import com.hellonms.platforms.emp_orange.share.model.network.ne_info.Model4NeInfoIf;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.Model4NeSessionNotificationIf;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.Model4NeSessionResponseIf;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.snmp.Model4NeSessionNotificationSNMP;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.snmp.Model4NeSessionRequestSNMPAt;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.snmp.Model4NeSessionRequestSNMPGet;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.snmp.Model4NeSessionRequestSNMPSet;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.snmp.Model4NeSessionRequestSNMPWalk;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.snmp.Model4NeSessionResponseSNMP;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.snmp.SNMP_OID;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.snmp.SNMP_TYPE;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.snmp.SNMP_VALUE;
import com.hellonms.platforms.emp_util.string.UtilString;

/**
 * <p>
 * SNMP 통신채널 관련 Util
 * </p>
 *
 * @since 1.6
 * @create 2015. 4. 23.
 * @modified 2015. 4. 23.
 * @author cchyun
 *
 */
public class UtilNeSessionSNMP {

	private static final BlackBox blackBox = new BlackBox(UtilNeSessionSNMP.class);

	public static Model4NeSessionRequestSNMPAt[] toNeSessionRequests4ReadList(EMP_MODEL_NE_INFO ne_info_def) {
		Model4NeSessionRequestSNMPGet snmp_get = new Model4NeSessionRequestSNMPGet();
		Model4NeSessionRequestSNMPWalk snmp_walk = new Model4NeSessionRequestSNMPWalk();

		for (EMP_MODEL_NE_INFO_FIELD ne_info_field_def : ne_info_def.getNe_info_fields()) {
			if (ne_info_field_def.isRead() && !ne_info_field_def.isVirtual_enable()) {
				SNMP_OID oid = (SNMP_OID) ne_info_field_def.getNe_session_oid();
				if (oid != null) {
					if (oid.getIndex_length() == 0) {
						snmp_get.addOID(oid);
					} else if (0 < oid.getIndex_length()) {
						snmp_walk.addOID(oid);
					}
				}
			}
		}

		if (0 < snmp_get.getOIDs().length && 0 < snmp_walk.getOIDs().length) {
			return new Model4NeSessionRequestSNMPAt[] { snmp_get, snmp_walk };
		} else if (0 < snmp_get.getOIDs().length) {
			return new Model4NeSessionRequestSNMPAt[] { snmp_get };
		} else if (0 < snmp_walk.getOIDs().length) {
			return new Model4NeSessionRequestSNMPAt[] { snmp_walk };
		} else {
			return new Model4NeSessionRequestSNMPAt[] {};
		}
	}

	public static Model4NeSessionRequestSNMPAt[] toNeSessionRequests4Update(Model4NeInfoIf ne_info) throws EmpException {
		Model4NeSessionRequestSNMPSet snmp_set = new Model4NeSessionRequestSNMPSet();

		EMP_MODEL_NE_INFO_FIELD[] index_fields = ne_info.getNe_info_def().getNe_info_indexs();
		StringBuilder indexs = new StringBuilder();
		for (int i = 0; i < index_fields.length; i++) {
			indexs.append(i == 0 ? "" : ".").append(toSnmp_index(index_fields[i], ne_info.getField_value(index_fields[i])));
		}

		for (EMP_MODEL_NE_INFO_FIELD ne_info_field_def : ne_info.getNe_info_def().getNe_info_fields()) {
			if (ne_info_field_def.isUpdate() && ne_info.getField_value(ne_info_field_def) != null) {
				snmp_set.addValue(toSnmp_value(ne_info.getField_value(ne_info_field_def), ne_info_field_def, indexs.toString()));
			}
		}
		if (0 < snmp_set.getVALUEs().length) {
			return new Model4NeSessionRequestSNMPAt[] { snmp_set };
		} else {
			return new Model4NeSessionRequestSNMPAt[] {};
		}
	}

	public static Model4NeSessionRequestSNMPAt[] toNeSessionRequests4Update(Model4NeInfoIf ne_info, List<EMP_MODEL_NE_INFO_FIELD> update_fields) throws EmpException {
		Model4NeSessionRequestSNMPSet snmp_set = new Model4NeSessionRequestSNMPSet();

		EMP_MODEL_NE_INFO_FIELD[] index_fields = ne_info.getNe_info_def().getNe_info_indexs();
		StringBuilder indexs = new StringBuilder();
		for (int i = 0; i < index_fields.length; i++) {
			indexs.append(i == 0 ? "" : ".").append(toSnmp_index(index_fields[i], ne_info.getField_value(index_fields[i])));
		}

		for (EMP_MODEL_NE_INFO_FIELD update_field : update_fields) {
			if (update_field.isUpdate()) {
				snmp_set.addValue(toSnmp_value(ne_info.getField_value(update_field), update_field, indexs.toString()));
			}
		}
		if (0 < snmp_set.getVALUEs().length) {
			return new Model4NeSessionRequestSNMPAt[] { snmp_set };
		} else {
			return new Model4NeSessionRequestSNMPAt[] {};
		}
	}

	public static Model4NeInfo[] toNeInfos(EMP_MODEL_NE_INFO ne_info_def, Model4NeSessionResponseIf[] responses) {
		EMP_MODEL_NE_INFO_FIELD[] index_defs = ne_info_def.getNe_info_indexs();
		boolean auto_index = true;
		for (EMP_MODEL_NE_INFO_FIELD ne_info_index_def : index_defs) {
			if (ne_info_index_def.getType_local() != EMP_MODEL_TYPE.INT_32) {
				auto_index = false;
				break;
			}
		}

		Map<EMP_MODEL_NE_INFO_FIELD, List<SNMP_VALUE>> field_value_map = new HashMap<EMP_MODEL_NE_INFO_FIELD, List<SNMP_VALUE>>();
		String charset = null;
		for (Model4NeSessionResponseIf aaa : responses) {
			if (aaa instanceof Model4NeSessionResponseSNMP) {
				Model4NeSessionResponseSNMP response = (Model4NeSessionResponseSNMP) aaa;
				charset = response.getCharset();

				for (EMP_MODEL_NE_INFO_FIELD ne_info_field_def : ne_info_def.getNe_info_fields()) {
					SNMP_OID oid = (SNMP_OID) ne_info_field_def.getNe_session_oid();
					if (oid != null) {
						List<SNMP_VALUE> value_list = field_value_map.get(ne_info_field_def);
						if (value_list == null) {
							value_list = new ArrayList<SNMP_VALUE>();
							field_value_map.put(ne_info_field_def, value_list);
						}
						SNMP_VALUE[] values = response.getValuesMatch(oid);
						for (SNMP_VALUE value : values) {
							value_list.add(value);
						}
					}
				}
			}
		}

		Map<SNMP_OID, Model4NeInfo> ne_info_map = new TreeMap<SNMP_OID, Model4NeInfo>();
		Map<String, Map<SNMP_OID, Serializable>> idx_val_map = new TreeMap<String, Map<SNMP_OID, Serializable>>();
		for (EMP_MODEL_NE_INFO_FIELD ne_info_field_def : field_value_map.keySet()) {
			List<SNMP_VALUE> value_list = field_value_map.get(ne_info_field_def);
			SNMP_OID oid = (SNMP_OID) ne_info_field_def.getNe_session_oid();
			for (SNMP_VALUE value : value_list) {
				SNMP_OID index = value.getOid().subOid(oid.getOidLength());
				Serializable vvv = toNeInfo_value(value, ne_info_field_def, charset);
				if (ne_info_field_def.isIndex()) {
					Map<SNMP_OID, Serializable> mmm = idx_val_map.get(ne_info_field_def.getName());
					if (mmm == null) {
						mmm = new TreeMap<SNMP_OID, Serializable>();
						idx_val_map.put(ne_info_field_def.getName(), mmm);
					}
					mmm.put(index, vvv);
				}
				Model4NeInfo ne_info = ne_info_map.get(index);
				if (ne_info == null) {
					ne_info = EMP_MODEL.current().newInstanceNe_info(ne_info_def.getCode());
					if (auto_index && index.getOidLength() == index_defs.length) {
						int iii = 0;
						for (EMP_MODEL_NE_INFO_FIELD ne_info_index_def : index_defs) {
							ne_info.setField_value(ne_info_index_def, index.getOidValue(iii++));
						}
					}
					ne_info_map.put(index, ne_info);
				}
				ne_info.setField_value(ne_info_field_def, vvv);
			}
		}

		for (Entry<SNMP_OID, Model4NeInfo> eee : ne_info_map.entrySet()) {
			SNMP_OID index = eee.getKey();
			Model4NeInfo ne_info = eee.getValue();
			for (EMP_MODEL_NE_INFO_FIELD index_def : index_defs) {
				if (ne_info.getField_value(index_def) == null) {
					Map<SNMP_OID, Serializable> mmm = idx_val_map.get(index_def.getName());
					if (mmm != null) {
						Entry<SNMP_OID, Serializable> nnn = null;
						for (Entry<SNMP_OID, Serializable> entry : mmm.entrySet()) {
							if (entry.getKey().getOidLength() < index.getOidLength() && index.matchOf(entry.getKey())) {
								if (nnn == null || nnn.getKey().getOidLength() < entry.getKey().getOidLength()) {
									nnn = entry;
								}
							}
						}
						if (nnn != null) {
							ne_info.setField_value(index_def, nnn.getValue());
						}
					}
				}
			}
		}
		return ne_info_map.values().toArray(new Model4NeInfo[0]);
	}

	public static Model4NeInfo toNeInfo(EMP_MODEL_NE_INFO ne_info_def, Model4NeSessionNotificationIf notification) {
		Map<EMP_MODEL_NE_INFO_FIELD, SNMP_VALUE[]> field_value_map = new HashMap<EMP_MODEL_NE_INFO_FIELD, SNMP_VALUE[]>();
		String charset = null;
		int index_count = 0;
		if (notification instanceof Model4NeSessionNotificationSNMP) {
			Model4NeSessionNotificationSNMP response = (Model4NeSessionNotificationSNMP) notification;
			charset = response.getCharset();
			for (EMP_MODEL_NE_INFO_FIELD ne_info_field_def : ne_info_def.getNe_info_fields()) {
				if (ne_info_field_def.isIndex()) {
					index_count += (ne_info_field_def.getType_remote().equals(SNMP_TYPE.IPADDRESS.name()) ? 4 : 1);
				}
			}

			for (EMP_MODEL_NE_INFO_FIELD ne_info_field_def : ne_info_def.getNe_info_fields()) {
				SNMP_OID oid = (SNMP_OID) ne_info_field_def.getNe_session_oid();
				SNMP_VALUE[] values = response.getValuesMatch(oid, index_count);
				if (values != null && 0 < values.length) {
					field_value_map.put(ne_info_field_def, values);
				}
			}
		}

		Model4NeInfo ne_info = EMP_MODEL.current().newInstanceNe_info(ne_info_def.getCode());
		for (EMP_MODEL_NE_INFO_FIELD ne_info_field_def : field_value_map.keySet()) {
			SNMP_VALUE[] values = field_value_map.get(ne_info_field_def);
			ne_info.setField_value(ne_info_field_def, toNeInfo_value(values[0], ne_info_field_def, charset));
		}

		return ne_info;
	}

	protected static Serializable toNeInfo_value(SNMP_VALUE value, EMP_MODEL_NE_INFO_FIELD ne_info_field_def, String charset) {
		if (value.getType().name().equals(ne_info_field_def.getType_remote())) {
			if (ne_info_field_def.getType_local().premitive == value.getValue().getClass()) {
				return value.getValue();
			} else if (value.getType() == SNMP_TYPE.OCTET_STRING && ne_info_field_def.getType_local().premitive == String.class) { // octet string to display string
				byte[] buf = (byte[]) value.getValue();
				if (!UtilString.isEmpty(charset)) {
					try {
						return new String(buf, charset).trim();
					} catch (Exception e) {
						if (blackBox.isEnabledFor(LEVEL.Fatal)) {
							blackBox.log(LEVEL.Fatal, null, e);
						}
					}
				}
				return new String(buf).trim();
			} else if (value.getType() == SNMP_TYPE.OCTET_STRING && ne_info_field_def.getType_local() == EMP_MODEL_TYPE.DATE) { // DateAndTime to Date
				byte[] buf = (byte[]) value.getValue();
				return fromDateAndTime(buf);
			} else if (value.getType() == SNMP_TYPE.TIME_TICKS && ne_info_field_def.getType_local() == EMP_MODEL_TYPE.DATE) { // timetick to date
				return new Date(System.currentTimeMillis() - (Long) value.getValue());
			} else {
				UtilInternalError.notifyInternalError(null, Model4Ne.NMS_NE_ID, "localType", UtilString.format("localType fail {}[{}] is not {} ({})", ne_info_field_def.getNe_info().getName(), ne_info_field_def.getName(), ne_info_field_def.getType_local(), value.getType()));
				return null;
			}
		} else {
			UtilInternalError.notifyInternalError(null, Model4Ne.NMS_NE_ID, "remoteType", UtilString.format("remoteType fail {}[{}] is not {} ({})", ne_info_field_def.getNe_info().getName(), ne_info_field_def.getName(), ne_info_field_def.getType_remote(), value.getType()));
			return null;
		}
	}

	protected static SNMP_VALUE toSnmp_value(Serializable value, EMP_MODEL_NE_INFO_FIELD ne_info_field_def, String indexs) {
		SNMP_OID oid = (SNMP_OID) ne_info_field_def.getNe_session_oid();
		if (0 < oid.getIndex_length()) {
			oid = new SNMP_OID(oid.getOid(), indexs);
		}
		SNMP_TYPE type = SNMP_TYPE.valueOf(ne_info_field_def.getType_remote());
		if (type == SNMP_TYPE.OCTET_STRING && ne_info_field_def.getType_local().premitive == String.class) { // string to octet string
			return new SNMP_VALUE(oid, type, ((String) value).getBytes());
		} else {
			return new SNMP_VALUE(oid, type, value);
		}
	}

	protected static Date fromDateAndTime(byte[] buf) {
		if (buf.length == 8) {
			int year = (buf[0] & 0xFF) * 256 + (buf[1] & 0xFF);
			int month = (buf[2] & 0xFF);
			int date = (buf[3] & 0xFF);
			int hour = (buf[4] & 0xFF);
			int minute = (buf[5] & 0xFF);
			int second = (buf[6] & 0xFF);
			int deci = (buf[7] & 0xFF);
			GregorianCalendar gc = new GregorianCalendar(year, month - 1, date, hour, minute, second);
			gc.set(Calendar.MILLISECOND, deci * 100);
			if (buf.length == 11) {
				String timezone = "GMT" + buf[8] + buf[9] + ":" + buf[10];
				GregorianCalendar tgc = new GregorianCalendar(TimeZone.getTimeZone(timezone));
				tgc.setTimeInMillis(gc.getTimeInMillis());
				return tgc.getTime();
			}
			return gc.getTime();
		} else {
			return null;
		}
	}

	private static String toSnmp_index(EMP_MODEL_NE_INFO_FIELD field, Serializable value) throws EmpException {
		if (field.getType_remote().equals("INTEGER_32")) {
			return String.valueOf(value);
		} else if (field.getType_remote().equals("OCTET_STRING")) {
			byte[] buf;
			switch (field.getType_local()) {
			case ARRAY_BYTE:
				buf = (byte[]) value;
				break;
			default:
				buf = String.valueOf(value).getBytes();
				break;
			}
			StringBuilder oid = new StringBuilder();
			for (int i = 0; i < buf.length; i++) {
				oid.append(i == 0 ? "" : ".").append(0x000000FF & buf[i]);
			}
			return oid.toString();
		} else {
			throw new EmpException(ERROR_CODE_ORANGE.MODEL_NOTEXISTS, UtilString.format("Invalid Index {}", field.getType_remote()));
		}
	}

}
