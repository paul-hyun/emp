/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.share.model.network.ne_session.snmp;

import java.io.Serializable;

import com.hellonms.platforms.emp_util.string.UtilString;

/**
 * <p>
 * SNMP 값
 * </p>
 *
 * @since 1.6
 * @create 2015. 4. 7.
 * @modified 2015. 4. 7.
 * @author cchyun
 *
 */
@SuppressWarnings("serial")
public class SNMP_VALUE implements Serializable {

	private SNMP_OID oid;

	private SNMP_TYPE type;

	private Serializable value;

	public SNMP_VALUE() {
	}

	public SNMP_VALUE(SNMP_OID oid, SNMP_TYPE type, Serializable value) {
		this.oid = oid;
		this.type = type;
		this.value = value;
	}

	public SNMP_OID getOid() {
		return oid;
	}

	public SNMP_TYPE getType() {
		return type;
	}

	public Serializable getValue() {
		return value;
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		if (type == SNMP_TYPE.OCTET_STRING && UtilString.isPrintable((byte[]) value)) {
		}
		stringBuilder.append(oid).append('=').append(type).append(':');
		if (value instanceof byte[]) {
			if (UtilString.isPrintable((byte[]) value)) {
				stringBuilder.append(new String((byte[]) value));
			} else {
				stringBuilder.append(UtilString.toHexString((byte[]) value));
			}
		} else {
			stringBuilder.append(value);
		}
		return stringBuilder.toString();
	}

}
