/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_plug.snmp;

import org.snmp4j.smi.OID;

/**
 * <p>
 * SNMP GetNext
 * </p>
 *
 * @since 1.6
 * @create 2015. 4. 3.
 * @modified 2015. 4. 3.
 * @author cchyun
 *
 */
public class PlugRequestSNMPGetNext implements PlugRequestSNMPIf {

	private final OID[] oids;

	public PlugRequestSNMPGetNext(OID[] oids) {
		this.oids = oids;
	}

	public OID[] getOids() {
		return oids;
	}

	@Override
	public String toString() {
		return toString("");
	}

	public String toString(String indent) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(indent).append(getClass().getName()).append(" {").append('\n');
		for (OID oid : oids) {
			stringBuilder.append(indent).append('\t').append(oid).append('\n');
		}
		stringBuilder.append(indent).append('}');
		return stringBuilder.toString();
	}

}
