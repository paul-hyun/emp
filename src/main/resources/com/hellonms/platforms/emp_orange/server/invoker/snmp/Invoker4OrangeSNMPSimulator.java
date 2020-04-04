/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.server.invoker.snmp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.snmp4j.PDU;
import org.snmp4j.smi.Null;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.Variable;
import org.snmp4j.smi.VariableBinding;

import com.hellonms.platforms.emp_core.server.invoker.InvokerIf;
import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.share.error.ERROR_CODE_CORE;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_plug.snmp.Plug4SNMPServer;
import com.hellonms.platforms.emp_plug.snmp.Plug4SNMPServer.Plug4SNMPServerRequestHandlerIf;
import com.hellonms.platforms.emp_plug.snmp.Plug4SNMPServer.Plug4SNMPServerResponseHandlerIf;
import com.hellonms.platforms.emp_plug.snmp.PlugRequestSNMPGet;
import com.hellonms.platforms.emp_plug.snmp.PlugRequestSNMPGetNext;
import com.hellonms.platforms.emp_plug.snmp.PlugRequestSNMPIf;
import com.hellonms.platforms.emp_plug.snmp.PlugRequestSNMPSet;
import com.hellonms.platforms.emp_plug.snmp.PlugResponseSNMP;
import com.hellonms.platforms.emp_plug.snmp.PlugResponseSNMPIf;
import com.hellonms.platforms.emp_plug.snmp.UtilSNMP;
import com.hellonms.platforms.emp_util.string.UtilString;

/**
 * <p>
 * Invoker SNMP Simulator
 * </p>
 *
 * @since 1.6
 * @create 2015. 7. 22.
 * @modified 2015. 7. 22.
 * @author cchyun
 *
 */
public class Invoker4OrangeSNMPSimulator implements Invoker4OrangeSNMPIf, Plug4SNMPServerRequestHandlerIf {

	private int port = 161;

	private String read_community = "public";

	private String write_community = "private";

	private String properties = null;

	private boolean ready = false;

	@SuppressWarnings("unused")
	private Plug4SNMPServer server;

	private TreeMap<OID, Variable> variable_map = new TreeMap<OID, Variable>(new Comparator<OID>() {
		@Override
		public int compare(OID o1, OID o2) {
			int[] value1 = o1.getValue();
			int[] value2 = o2.getValue();
			int length = Math.min(value1.length, value2.length);

			int compare = 0;

			for (int i = 0; i < length; i++) {
				compare = value1[i] - value2[i];
				if (compare != 0) {
					return compare;
				}
			}

			if (length < value1.length) {
				compare = 1;
			} else if (length < value2.length) {
				compare = -1;
			}

			return compare;
		}
	});

	private OID END_OF_MIB = new OID("1.3.6.1.4.2");

	private int thread_count = 4;

	private ThreadPoolExecutor threadPoolExecutor;

	@Override
	public Class<? extends InvokerIf> getDefine_class() {
		return Invoker4OrangeSNMPIf.class;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setRead_community(String read_community) {
		this.read_community = read_community;
	}

	public void setWrite_community(String write_community) {
		this.write_community = write_community;
	}

	public void setProperties(String properties) {
		this.properties = properties;
	}

	public void setThread_count(int thread_count) {
		this.thread_count = thread_count;
	}

	@Override
	public void initialize(EmpContext context) throws EmpException {
		if (this.properties == null) {
			throw new EmpException(ERROR_CODE_CORE.FILE_IO, "snmp mib properties file not setted !!!");
		}

		load();
		server = new Plug4SNMPServer(port, read_community, write_community, this);
		threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(thread_count);
		ready = true;
	}

	@Override
	public boolean isReady(EmpContext context) throws EmpException {
		return ready;
	}

	@Override
	public void dispose(EmpContext context) throws EmpException {
		ready = false;
	}

	@Override
	public void handleRequest(final InetAddress address, final int port, final PlugRequestSNMPIf request, final Plug4SNMPServerResponseHandlerIf responseHandler) {
		threadPoolExecutor.execute(new Runnable() {
			@Override
			public void run() {
				try {
					if (request instanceof PlugRequestSNMPGet) {
						responseHandler.handleResponse(snmp_get((PlugRequestSNMPGet) request));
					} else if (request instanceof PlugRequestSNMPGetNext) {
						responseHandler.handleResponse(snmp_get_next((PlugRequestSNMPGetNext) request));
					} else if (request instanceof PlugRequestSNMPSet) {
						responseHandler.handleResponse(snmp_set((PlugRequestSNMPSet) request));
					} else {
						responseHandler.handleResponseException(PDU.resourceUnavailable, PDU.resourceUnavailable);
					}
				} catch (EmpException e) {
					e.printStackTrace();
					responseHandler.handleResponseException(PDU.resourceUnavailable, PDU.resourceUnavailable);
				}
			}
		});
	}

	private void load() throws EmpException {
		try {
			Properties properties = new Properties();
			File file = new File(this.properties);
			FileInputStream in = new FileInputStream(file);
			properties.load(in);
			in.close();

			VariableBinding[] vbs = UtilSNMP.toVariableBinding(properties);
			for (VariableBinding vb : vbs) {
				variable_map.put(vb.getOid(), vb.getVariable());
			}
		} catch (Exception e) {
			throw new EmpException(e, ERROR_CODE_CORE.FILE_IO);
		}
	}

	private void store() throws EmpException {
		try {
			List<VariableBinding> vb_list = new ArrayList<VariableBinding>();
			for (Map.Entry<OID, Variable> entry : variable_map.entrySet()) {
				vb_list.add(new VariableBinding(entry.getKey(), entry.getValue()));
			}
			Properties properties = UtilSNMP.toProperties(vb_list.toArray(new VariableBinding[0]));
			File file = new File(this.properties);
			FileOutputStream out = new FileOutputStream(file);
			properties.store(out, null);
			out.close();
		} catch (Exception e) {
			throw new EmpException(e, ERROR_CODE_CORE.FILE_IO);
		}
	}

	private PlugResponseSNMPIf snmp_get(PlugRequestSNMPGet request) throws EmpException {
		PlugResponseSNMP response = new PlugResponseSNMP();
		for (OID oid : request.getOids()) {
			Variable variable = variable_map.get(oid);
			if (variable == null) {
				throw new EmpException(ERROR_CODE_CORE.FILE_IO, UtilString.format("no such snmp_oid {}", oid));
			}
			response.add(new VariableBinding(oid, variable));
		}
		return response;
	}

	private PlugResponseSNMPIf snmp_get_next(PlugRequestSNMPGetNext request) throws EmpException {
		PlugResponseSNMP response = new PlugResponseSNMP();
		for (OID oid : request.getOids()) {
			Map.Entry<OID, Variable> entry = variable_map.higherEntry(oid);
			if (entry == null) {
				response.add(new VariableBinding(END_OF_MIB, new Null()));
			} else {
				response.add(new VariableBinding(entry.getKey(), entry.getValue()));
			}
		}
		return response;
	}

	private PlugResponseSNMPIf snmp_set(PlugRequestSNMPSet request) throws EmpException {
		PlugResponseSNMP response = new PlugResponseSNMP();
		for (VariableBinding vb : request.getVbs()) {
			Variable variable = variable_map.get(vb.getOid());
			if (variable == null) {
				throw new EmpException(ERROR_CODE_CORE.FILE_IO, UtilString.format("no such snmp_oid {}", vb.getOid()));
			}
			variable_map.put(vb.getOid(), vb.getVariable());
			response.add(new VariableBinding(vb.getOid(), vb.getVariable()));
		}
		store();
		return response;
	}

}
