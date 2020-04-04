/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_plug.snmp;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;
import java.util.Vector;

import org.snmp4j.CommandResponder;
import org.snmp4j.CommandResponderEvent;
import org.snmp4j.MessageDispatcher;
import org.snmp4j.MessageDispatcherImpl;
import org.snmp4j.MessageException;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.mp.MPv1;
import org.snmp4j.mp.MPv2c;
import org.snmp4j.mp.StatusInformation;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

import com.hellonms.platforms.emp_core.server.log.BlackBox;
import com.hellonms.platforms.emp_core.server.log.LEVEL;
import com.hellonms.platforms.emp_core.share.error.ERROR_CODE_CORE;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_util.string.UtilString;

/**
 * <p>
 * SNMP Server
 * </p>
 *
 * @since 1.6
 * @create 2015. 4. 3.
 * @modified 2015. 4. 3.
 * @author cchyun
 *
 */
public class Plug4SNMPServer {

	public interface Plug4SNMPServerRequestHandlerIf {

		public void handleRequest(InetAddress address, int port, PlugRequestSNMPIf request, Plug4SNMPServerResponseHandlerIf responseHandler);

	}

	public interface Plug4SNMPServerResponseHandlerIf {

		public void handleResponse(PlugResponseSNMPIf response);

		public void handleResponseException(int error_index, int error_status);

	}

	public class Plug4SnmpServerResponseHandler implements Plug4SNMPServerResponseHandlerIf {

		private CommandResponderEvent event;

		public Plug4SnmpServerResponseHandler(CommandResponderEvent event) {
			this.event = event;
		}

		@Override
		public void handleResponse(PlugResponseSNMPIf response) {
			try {
				PDU recvPDU = event.getPDU();
				PDU sendPdu = new PDU();
				sendPdu.setRequestID(recvPDU.getRequestID());
				sendPdu.setType(PDU.RESPONSE);
				sendPdu.setErrorIndex(PDU.noError);
				sendPdu.setErrorStatus(PDU.noError);
				sendPdu.addAll(response.getVbs().toArray(new VariableBinding[0]));
				event.getMessageDispatcher().returnResponsePdu(event.getMessageProcessingModel(), event.getSecurityModel(), event.getSecurityName(), event.getSecurityLevel(), sendPdu, event.getMaxSizeResponsePDU(), event.getStateReference(), new StatusInformation());
			} catch (Exception e) {
				if (blackBox.isEnabledFor(LEVEL.Fatal)) {
					blackBox.log(LEVEL.Fatal, null, e);
				}
			}
		}

		@Override
		public void handleResponseException(int error_index, int error_status) {
			try {
				PDU recvPDU = event.getPDU();
				PDU sendPdu = new PDU();
				sendPdu.setRequestID(recvPDU.getRequestID());
				sendPdu.setType(PDU.RESPONSE);
				sendPdu.setErrorIndex(error_index);
				sendPdu.setErrorStatus(error_status);
				event.getMessageDispatcher().returnResponsePdu(event.getMessageProcessingModel(), event.getSecurityModel(), event.getSecurityName(), event.getSecurityLevel(), sendPdu, event.getMaxSizeResponsePDU(), event.getStateReference(), new StatusInformation());
			} catch (Exception e) {
				if (blackBox.isEnabledFor(LEVEL.Fatal)) {
					blackBox.log(LEVEL.Fatal, null, e);
				}
			}
		}

	}

	private class Plug4SnmpServerCommandResponder implements CommandResponder {

		@Override
		public void processPdu(CommandResponderEvent event) {
			try {
				if (blackBox.isEnabledFor(LEVEL.UseCase)) {
					blackBox.log(LEVEL.UseCase, null, UtilString.format("recv snmp reqeust {}", event));
				}

				InetAddress address = ((UdpAddress) event.getPeerAddress()).getInetAddress();
				int port = ((UdpAddress) event.getPeerAddress()).getPort();

				PDU recvPDU = event.getPDU();
				String community = new String(event.getSecurityName());

				if ((recvPDU.getType() == PDU.SET && !community.equals(writeCommunity)) && !community.equals(readCommunity)) {
					return;
				}
				List<? extends VariableBinding> vbs = recvPDU.getVariableBindings();

				PlugRequestSNMPIf request = null;
				switch (recvPDU.getType()) {
				case PDU.GET: {
					OID[] oids = new OID[vbs.size()];
					for (int i = 0; i < oids.length; i++) {
						oids[i] = vbs.get(i).getOid();
					}
					request = new PlugRequestSNMPGet(oids);
					break;
				}
				case PDU.GETNEXT: {
					OID[] oids = new OID[vbs.size()];
					for (int i = 0; i < oids.length; i++) {
						oids[i] = vbs.get(i).getOid();
					}
					request = new PlugRequestSNMPGetNext(oids);
					break;
				}
				case PDU.SET: {
					OID[] oids = new OID[vbs.size()];
					for (int i = 0; i < oids.length; i++) {
						oids[i] = vbs.get(i).getOid();
					}
					request = new PlugRequestSNMPSet(vbs.toArray(new VariableBinding[0]));
					break;
				}
				default: {
					if (blackBox.isEnabledFor(LEVEL.Fatal)) {
						blackBox.log(LEVEL.Fatal, null, UtilString.format("unsupported request type = {}", recvPDU.getType()));
					}
					sendError(event, PDU.resourceUnavailable, PDU.resourceUnavailable);
					return;
				}
				}
				requestHandler.handleRequest(address, port, request, new Plug4SnmpServerResponseHandler(event));
			} catch (Exception e) {
				if (blackBox.isEnabledFor(LEVEL.Fatal)) {
					blackBox.log(LEVEL.Fatal, null, e);
				}

				try {
					sendError(event, PDU.resourceUnavailable, PDU.resourceUnavailable);
				} catch (Exception ex) {
					if (blackBox.isEnabledFor(LEVEL.Fatal)) {
						blackBox.log(LEVEL.Fatal, null, ex);
					}
				}
				return;
			}
		}

		private void sendError(CommandResponderEvent event, int error_index, int error_status) throws MessageException {
			PDU recvPDU = event.getPDU();
			PDU sendPdu = new PDU();
			sendPdu.setRequestID(recvPDU.getRequestID());
			sendPdu.setType(PDU.RESPONSE);
			sendPdu.setErrorIndex(error_index);
			sendPdu.setErrorStatus(error_status);
			event.getMessageDispatcher().returnResponsePdu(event.getMessageProcessingModel(), event.getSecurityModel(), event.getSecurityName(), event.getSecurityLevel(), sendPdu, event.getMaxSizeResponsePDU(), event.getStateReference(), new StatusInformation());
		}

	}

	private Plug4SNMPServerRequestHandlerIf requestHandler;

	private String readCommunity;

	private String writeCommunity;

	private Snmp session;

	private static final BlackBox blackBox = new BlackBox(Plug4SNMPServer.class);

	public Plug4SNMPServer(int port, Plug4SNMPServerRequestHandlerIf requestHandler) throws EmpException {
		this(port, "public", "private", requestHandler);
	}

	public Plug4SNMPServer(int port, String readCommunity, String writeCommunity, Plug4SNMPServerRequestHandlerIf requestHandler) throws EmpException {
		this.requestHandler = requestHandler;
		this.readCommunity = readCommunity;
		this.writeCommunity = writeCommunity;

		try {
			TransportMapping<UdpAddress> transportMapping = new DefaultUdpTransportMapping(new UdpAddress(port));

			MessageDispatcher messageDispatcher = new MessageDispatcherImpl();
			messageDispatcher.addCommandResponder(new Plug4SnmpServerCommandResponder());

			messageDispatcher.addMessageProcessingModel(new MPv1());
			messageDispatcher.addMessageProcessingModel(new MPv2c());

			session = new Snmp(messageDispatcher);
			session.addTransportMapping(transportMapping);

			session.listen();
		} catch (IOException e) {
			throw new EmpException(e, ERROR_CODE_CORE.NETOWRK_IO);
		}
	}

}
