/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_plug.snmp;

import java.util.Collection;

import org.snmp4j.smi.VariableBinding;

import com.hellonms.platforms.emp_plug.PlugResponseIf;

/**
 * <p>
 * SNMP 응답
 * </p>
 *
 * @since 1.6
 * @create 2015. 4. 6.
 * @modified 2015. 4. 6.
 * @author cchyun
 *
 */
public interface PlugResponseSNMPIf extends PlugResponseIf {

	public Collection<VariableBinding> getVbs();

}
