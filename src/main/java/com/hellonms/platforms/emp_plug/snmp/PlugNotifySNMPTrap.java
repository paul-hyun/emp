/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_plug.snmp;

import org.snmp4j.smi.OID;
import org.snmp4j.smi.VariableBinding;

import com.hellonms.platforms.emp_plug.PlugNotifyIf;

/**
 * <p>
 * SNMP Trap
 * </p>
 *
 * @since 1.6
 * @create 2015. 4. 3.
 * @modified 2015. 4. 3.
 * @author cchyun
 *
 */
public class PlugNotifySNMPTrap implements PlugNotifyIf {

	private long sysUpTime;

	private OID trapOid;

	private int genericTrap;

	private int specificTrap;

	private VariableBinding[] vbs = {};

	public long getSysUpTime() {
		return sysUpTime;
	}

	public void setSysUpTime(long sysUpTime) {
		this.sysUpTime = sysUpTime;
	}

	public OID getTrapOid() {
		return trapOid;
	}

	public void setTrapOid(OID trapOid) {
		this.trapOid = trapOid;
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

	public VariableBinding[] getVbs() {
		return vbs;
	}

	public void setVbs(VariableBinding[] vbs) {
		this.vbs = vbs;
	}

	@Override
	public String toString() {
		return toString("");
	}

	public String toString(String indent) {
		StringBuilder stirngBuilder = new StringBuilder();
		stirngBuilder.append(indent).append(getClass().getName()).append(" {").append("\n");
		stirngBuilder.append(indent).append("\t").append("sysUpTime").append(": ").append(sysUpTime).append("\n");
		stirngBuilder.append(indent).append("\t").append("trapOid").append(": ").append(trapOid).append("\n");
		stirngBuilder.append(indent).append("\t").append("genericTrap").append(": ").append(genericTrap).append("\n");
		stirngBuilder.append(indent).append("\t").append("specificTrap").append(": ").append(specificTrap).append("\n");
		for (VariableBinding vb : vbs) {
			stirngBuilder.append(indent).append("\t\t").append(vb).append("\n");
		}
		stirngBuilder.append(indent).append("}");
		return stirngBuilder.toString();
	}

}
