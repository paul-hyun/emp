/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.share.model.network.ne_session.snmp;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

import com.hellonms.platforms.emp_orange.share.model.network.ne_session.NE_SESSION_PROTOCOL;

/**
 * <p>
 * SNMP Get 명령
 * </p>
 *
 * @since 1.6
 * @create 2015. 4. 7.
 * @modified 2015. 4. 7.
 * @author cchyun
 *
 */
@SuppressWarnings("serial")
public class Model4NeSessionRequestSNMPSet extends Model4NeSessionRequestSNMPAt {

	private Map<SNMP_OID, SNMP_VALUE> value_map = new TreeMap<SNMP_OID, SNMP_VALUE>();

	public void addValue(SNMP_OID oid, SNMP_TYPE type, Serializable value) {
		value_map.put(oid, new SNMP_VALUE(oid, type, value));
	}

	public void addValue(SNMP_VALUE snmp_value) {
		value_map.put(snmp_value.getOid(), snmp_value);
	}

	public SNMP_VALUE[] getVALUEs() {
		return value_map.values().toArray(new SNMP_VALUE[0]);
	}

	@Override
	public NE_SESSION_PROTOCOL getProtocol() {
		return Model4NeSessionSNMP.PROTOCOL;
	}

	@Override
	public Model4NeSessionRequestSNMPSet copy() {
		Model4NeSessionRequestSNMPSet model = new Model4NeSessionRequestSNMPSet();
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
		stringBuilder.append(indent).append(S_RB);
		return stringBuilder.toString();
	}

	@Override
	public String toDisplayString(String indent) {
		return toString(indent);
	}

}
