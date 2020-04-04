/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.share.model.network.ne_session.snmp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.hellonms.platforms.emp_orange.share.model.network.ne_session.Model4NeSessionNotificationIf;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.NE_SESSION_PROTOCOL;

/**
 * <p>
 * SNMP 명령 응답
 * </p>
 *
 * @since 1.6
 * @create 2015. 4. 7.
 * @modified 2015. 4. 7.
 * @author cchyun
 *
 */
@SuppressWarnings("serial")
public class Model4NeSessionNotificationSNMP implements Model4NeSessionNotificationIf {

	private String charset;

	private long sysUpTime;

	private SNMP_OID notification_oid;

	private int genericTrap;

	private int specificTrap;

	private Map<SNMP_OID, SNMP_VALUE> value_map = new TreeMap<SNMP_OID, SNMP_VALUE>();

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public long getSysUpTime() {
		return sysUpTime;
	}

	public void setSysUpTime(long sysUpTime) {
		this.sysUpTime = sysUpTime;
	}

	public SNMP_OID getNotification_oid() {
		return notification_oid;
	}

	public void setNotification_oid(SNMP_OID notification_oid) {
		this.notification_oid = notification_oid;
	}

	public int getGenericTrap() {
		return genericTrap;
	}

	public void setGenericTrap(int genericTrap) {
		this.genericTrap = genericTrap;
	}

	public int getSpecificTrap() {
		return specificTrap;
	}

	public void setSpecificTrap(int specificTrap) {
		this.specificTrap = specificTrap;
	}

	public void addValue(int[] oid, SNMP_TYPE type, Serializable value) {
		addValue(new SNMP_OID(oid), type, value);
	}

	public void addValue(SNMP_OID oid, SNMP_TYPE type, Serializable value) {
		value_map.put(oid, new SNMP_VALUE(oid, type, value));
	}

	public SNMP_VALUE[] getValues() {
		return value_map.values().toArray(new SNMP_VALUE[0]);
	}

	public SNMP_VALUE[] getValuesMatch(SNMP_OID oid) {
		return getValuesMatch(oid, oid.getIndex_length());
	}

	public SNMP_VALUE[] getValuesMatch(SNMP_OID oid, int index_count) {
		List<SNMP_VALUE> value_list = new ArrayList<SNMP_VALUE>();
		for (SNMP_VALUE value : getValues()) {
			if (value.getOid().matchOf(oid)) {
				value_list.add(value);
			}
		}
		return value_list.toArray(new SNMP_VALUE[0]);
	}

	@Override
	public NE_SESSION_PROTOCOL getProtocol() {
		return Model4NeSessionSNMP.PROTOCOL;
	}

	@Override
	public Model4NeSessionNotificationSNMP copy() {
		Model4NeSessionNotificationSNMP model = new Model4NeSessionNotificationSNMP();
		return model;
	}

	@Override
	public String toString() {
		return toString("");
	}

	@Override
	public String toString(String indent) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(indent).append(getClass().getName()).append(S_LB).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("sysUpTime").append(S_DL).append(sysUpTime).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("notification_oid").append(S_DL).append(notification_oid).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("genericTrap").append(S_DL).append(genericTrap).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("specificTrap").append(S_DL).append(specificTrap).append(S_NL);
		for (SNMP_VALUE value : value_map.values()) {
			stringBuilder.append(indent).append(S_TB).append(value).append('\n');
		}
		stringBuilder.append(indent).append(S_RB);
		return stringBuilder.toString();
	}

	@Override
	public String toDisplayString(String indent) {
		return toString(indent);
	}

}
