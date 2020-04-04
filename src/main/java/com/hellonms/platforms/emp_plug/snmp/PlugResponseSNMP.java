/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_plug.snmp;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import org.snmp4j.smi.OID;
import org.snmp4j.smi.VariableBinding;

/**
 * <p>
 * SNMP 응답
 * </p>
 *
 * @since 1.6
 * @create 2015. 4. 3.
 * @modified 2015. 4. 3.
 * @author cchyun
 *
 */
public class PlugResponseSNMP implements PlugResponseSNMPIf {

	private Map<OID, VariableBinding> vb_map = new TreeMap<OID, VariableBinding>();

	public void add(VariableBinding vb) {
		vb_map.put(vb.getOid(), vb);
	}

	@Override
	public Collection<VariableBinding> getVbs() {
		return vb_map.values();
	}

	@Override
	public String toString() {
		return toString("");
	}

	public String toString(String indent) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(indent).append(getClass().getName()).append(" {").append('\n');
		for (VariableBinding vb : vb_map.values()) {
			stringBuilder.append(indent).append('\t').append(vb).append('\n');
		}
		stringBuilder.append(indent).append('}');
		return stringBuilder.toString();
	}

}
