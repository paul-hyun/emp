/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_plug.snmp;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.snmp4j.smi.AbstractVariable;
import org.snmp4j.smi.AssignableFromString;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.Variable;
import org.snmp4j.smi.VariableBinding;

import com.hellonms.platforms.emp_util.string.UtilString;

/**
 * <p>
 * SNMP 처리 유틸
 * </p>
 *
 * @since 1.6
 * @create 2015. 4. 3.
 * @modified 2015. 4. 3.
 * @author cchyun
 *
 */
public class UtilSNMP {

	public static Properties toProperties(VariableBinding[] vbs) {
		Properties properties = new Properties();

		for (VariableBinding vb : vbs) {
			String oid = vb.getOid().toDottedString();
			int syntax = vb.getSyntax();
			String value = vb.getVariable().toString();

			properties.setProperty(oid, new StringBuilder().append(syntax).append(':').append(value).toString());
		}

		return properties;
	}

	public static VariableBinding[] toVariableBinding(Properties properties) {
		List<VariableBinding> vb_list = new ArrayList<VariableBinding>();

		for (Object key : properties.keySet()) {
			OID oid = new OID((String) key);
			String line = properties.getProperty((String) key);
			int index = line.indexOf(':');
			if (0 < index) {
				int syntax = Integer.parseInt(line.substring(0, index));
				Variable variable = AbstractVariable.createFromSyntax(syntax);
				String value = line.substring(index + 1);
				if (variable instanceof AssignableFromString) {
					((AssignableFromString) variable).setValue(value);
				} else {
					throw new RuntimeException(UtilString.format("{} = {}", key, line));
				}
				vb_list.add(new VariableBinding(oid, variable));
			} else {
				throw new RuntimeException(UtilString.format("{} = {}", key, line));
			}

		}
		return vb_list.toArray(new VariableBinding[0]);
	}

}
