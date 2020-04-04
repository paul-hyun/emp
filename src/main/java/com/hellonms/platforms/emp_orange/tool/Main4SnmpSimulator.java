package com.hellonms.platforms.emp_orange.tool;

import java.io.File;
import java.io.FileOutputStream;
import java.net.InetAddress;
import java.util.Properties;

import org.snmp4j.smi.OID;
import org.snmp4j.smi.VariableBinding;

import com.hellonms.platforms.emp_orange.server.invoker.snmp.Invoker4OrangeSNMPSimulator;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.snmp.Model4NeSessionSNMP.SNMP_VERSION;
import com.hellonms.platforms.emp_plug.snmp.Plug4SNMPClient;
import com.hellonms.platforms.emp_plug.snmp.PlugRequestSNMPWalk;
import com.hellonms.platforms.emp_plug.snmp.PlugResponseSNMPIf;
import com.hellonms.platforms.emp_plug.snmp.UtilSNMP;
import com.hellonms.platforms.emp_util.string.UtilString;

public class Main4SnmpSimulator {

	public static void main(String[] args) throws Exception {
		if (args.length == 2 && args[0].equals("run")) {
			run(args[1]);
		} else if (args.length == 5 && args[0].equals("load")) {
			load(args[1], args[2], args[3], args[4]);

		} else {
			printUsage();
			return;
		}
	}

	private static void run(String file) throws Exception {
		File f = new File(file);
		if (!f.isFile()) {
			System.out.println(UtilString.format("file : '{}' invalid", file));
			return;
		}

		Invoker4OrangeSNMPSimulator simulator = new Invoker4OrangeSNMPSimulator();
		simulator.setProperties(file);
		simulator.initialize(null);

		System.out.println(UtilString.format("{} successed {}", "run", file));

		while (true) {
			Thread.sleep(1000L);
		}
	}

	private static void load(String address, String port, String community, String file) throws Exception {
		try {
			InetAddress.getByName(address);
		} catch (Exception e) {
			System.out.println(UtilString.format("address : '{}' invalid", address));
			return;
		}
		try {
			Integer.parseInt(port);
		} catch (Exception e) {
			System.out.println(UtilString.format("port : '{}' invalid", port));
			return;
		}
		FileOutputStream out;
		try {
			out = new FileOutputStream(file);
		} catch (Exception e) {
			System.out.println(UtilString.format("file : '{}' invalid", port));
			return;
		}
		Plug4SNMPClient client = new Plug4SNMPClient();
		PlugRequestSNMPWalk request = new PlugRequestSNMPWalk(new OID[] { new OID("1.3.6.1") });
		PlugResponseSNMPIf response = client.snmpWalk(null, InetAddress.getByName(address), Integer.parseInt(port), SNMP_VERSION.V2c, community, 5000, 1, request);
		Properties properties = UtilSNMP.toProperties(response.getVbs().toArray(new VariableBinding[0]));
		properties.store(out, null);
		out.close();

		System.out.println(UtilString.format("{} successed {} : {}", "load", file, response.getVbs().size()));
	}

	private static void printUsage() {
		System.out.println(UtilString.format("Usage: "));
		System.out.println(UtilString.format("{} {} {}", Main4SnmpSimulator.class, "run", "file"));
		System.out.println(UtilString.format("{} {} {} {} {} {}", Main4SnmpSimulator.class, "load", "address", "port", "community", "file"));
	}

}
