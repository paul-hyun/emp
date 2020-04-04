/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_plug.snmp;

import org.snmp4j.smi.VariableBinding;

/**
 * <p>
 * SNMP Set
 * </p>
 *
 * @since 1.6
 * @create 2015. 4. 3.
 * @modified 2015. 4. 3.
 * @author cchyun
 *
 */
public class PlugRequestSNMPSet implements PlugRequestSNMPIf {

	private final VariableBinding[] vbs;

	public PlugRequestSNMPSet(VariableBinding[] vbs) {
		this.vbs = vbs;
	}

	public VariableBinding[] getVbs() {
		return vbs;
	}

	@Override
	public String toString() {
		return toString("");
	}

	public String toString(String indent) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(indent).append(getClass().getName()).append(" {").append('\n');
		for (VariableBinding vb : vbs) {
			stringBuilder.append(indent).append('\t').append(vb).append('\n');
		}
		stringBuilder.append(indent).append('}');
		return stringBuilder.toString();
	}

}
