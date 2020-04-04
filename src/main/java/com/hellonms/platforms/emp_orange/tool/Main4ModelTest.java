package com.hellonms.platforms.emp_orange.tool;

import java.io.File;
import java.io.PrintStream;

import com.hellonms.platforms.emp_orange.server.driver.snmp.Driver4SNMP;
import com.hellonms.platforms.emp_orange.server.util.UtilNeSessionSNMP;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.snmp.Model4NeSessionRequestSNMPAt;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.snmp.Model4NeSessionResponseSNMP;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.snmp.Model4NeSessionResponseSNMPIf;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.snmp.Model4NeSessionSNMP;
import com.hellonms.platforms.emp_util.string.UtilString;

public class Main4ModelTest {

	public static void main(String[] args) throws Exception {
		if (args.length == 6 && args[0].equals("snmp_simple")) {
			testSnmpSimple(args[1], args[2], args[3], args[4], args[5]);
		} else {
			printUsage();
			return;
		}
	}

	private static void testSnmpSimple(String model, String address, String port, String community, String file) throws Exception {
		EMP_MODEL.init_current(new File(model));
		Driver4SNMP driver = new Driver4SNMP();
		driver.initialize(null);
		Model4NeSessionSNMP ne_session = new Model4NeSessionSNMP();
		ne_session.setAddress(address);
		ne_session.setPort(Integer.parseInt(port));
		ne_session.setRead_community(community);
		ne_session.setTimeout(5000);

		PrintStream out = new PrintStream(file);
		for (EMP_MODEL_NE_INFO ne_info_def : EMP_MODEL.current().getNe_infos()) {
			try {
				Model4NeSessionRequestSNMPAt[] requests = UtilNeSessionSNMP.toNeSessionRequests4ReadList(ne_info_def);
				Model4NeSessionResponseSNMPIf[] responses = driver.execute(null, ne_session, requests);
				int count = 0;
				for (Model4NeSessionResponseSNMPIf response : responses) {
					if (response instanceof Model4NeSessionResponseSNMP) {
						count += ((Model4NeSessionResponseSNMP) response).getValues().length;
					} else {
						System.exit(1);
					}
				}
				if (count == 0) {
					out.println(UtilString.format("{}, nodata,  {}", ne_info_def.getName(), count));
				} else {
					out.println(UtilString.format("{}, success,  {}", ne_info_def.getName(), count));
				}
			} catch (Exception e) {
				out.println(UtilString.format("{}, fail, {}", ne_info_def.getName(), e.getMessage()));
			}
		}
		out.close();
	}

	private static void printUsage() {
		System.out.println(UtilString.format("Usage: "));
		System.out.println(UtilString.format("{} {} {} {} {} {} {}", Main4ModelTest.class, "snmp_simple", "model", "address", "port", "community", "file"));
	}

}
