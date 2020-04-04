/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.share.model.network.ne_session.snmp;

import java.util.Set;
import java.util.TreeSet;

import com.hellonms.platforms.emp_orange.share.model.network.ne_session.NE_SESSION_PROTOCOL;

/**
 * <p>
 * SNMP Walk 명령
 * </p>
 *
 * @since 1.6
 * @create 2015. 4. 7.
 * @modified 2015. 4. 7.
 * @author cchyun
 *
 */
@SuppressWarnings("serial")
public class Model4NeSessionRequestSNMPWalk extends Model4NeSessionRequestSNMPAt {

	private Set<SNMP_OID> oid_set = new TreeSet<SNMP_OID>();

	public void addOID(int[] oid) {
		addOID(new SNMP_OID(oid));
	}

	public void addOID(SNMP_OID oid) {
		oid_set.add(oid);
	}

	public SNMP_OID[] getOIDs() {
		return oid_set.toArray(new SNMP_OID[0]);
	}

	@Override
	public NE_SESSION_PROTOCOL getProtocol() {
		return Model4NeSessionSNMP.PROTOCOL;
	}

	@Override
	public Model4NeSessionRequestSNMPWalk copy() {
		Model4NeSessionRequestSNMPWalk model = new Model4NeSessionRequestSNMPWalk();
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
