/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.server.workflow.network.ne_session.snmp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hellonms.platforms.emp_core.server.log.BlackBox;
import com.hellonms.platforms.emp_core.server.log.LEVEL;
import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.server.workflow.WorkerIf;
import com.hellonms.platforms.emp_core.server.workflow.WorkflowMap;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_orange.server.workflow.fault.event.Worker4EventIf;
import com.hellonms.platforms.emp_orange.server.workflow.network.ne_session.snmp.Adapter4NeSessionSNMPIf.Adapter4NeSessionSNMPListenerIf;
import com.hellonms.platforms.emp_orange.share.error.ERROR_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_EVENT;
import com.hellonms.platforms.emp_orange.share.model.fault.event.GEN_TYPE;
import com.hellonms.platforms.emp_orange.share.model.fault.event.Model4Event;
import com.hellonms.platforms.emp_orange.share.model.fault.event.SEVERITY;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.Model4NeSessionDiscoveryFilterIf;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.Model4NeSessionDiscoveryResultIf;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.Model4NeSessionIf;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.Model4NeSessionRequestIf;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.Model4NeSessionResponseIf;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.NE_SESSION_PROTOCOL;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.snmp.Model4NeSessionDiscoveryFilterSNMP;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.snmp.Model4NeSessionNotificationSNMP;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.snmp.Model4NeSessionRequestSNMPAt;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.snmp.Model4NeSessionResponseSNMP;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.snmp.Model4NeSessionResponseSNMPIf;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.snmp.Model4NeSessionSNMP;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.snmp.Model4NeSessionSNMP.SNMP_VERSION;
import com.hellonms.platforms.emp_util.cache.UtilCache;
import com.hellonms.platforms.emp_util.string.UtilString;

/**
 * <p>
 * SNMP 통신채널 Worker
 * </p>
 *
 * @since 1.6
 * @create 2015. 4. 3.
 * @modified 2015. 4. 3.
 * @author cchyun
 *
 */
public class Worker4NeSessionSNMP implements Worker4NeSessionSNMPIf {

	private class Adapter4NeSessionSNMPListener implements Adapter4NeSessionSNMPListenerIf {

		@Override
		public void handleTrap(EmpContext context, String address, int port, SNMP_VERSION version, String community, Model4NeSessionNotificationSNMP notification) {
			try {
				Model4NeSessionSNMP ne_session = dao4NeSession.queryNeSessionByAddress(context, address);
				if (ne_session != null) {
					notification.setCharset(ne_session.getCharset());
					for (Worker4NeSessionListenerIf listener : listeners) {
						try {
							listener.handleNotification(context, ne_session, notification);
						} catch (Exception e) {
							if (blackBox.isEnabledFor(LEVEL.Site)) {
								blackBox.log(LEVEL.Site, context, e);
							}
						}
					}
					if (ne_session.isAdmin_state() && !ne_session.isNe_session_state()) {
						testNeSession(context, ne_session.getNe_id(), new Date());
					}
				}
			} catch (Exception e) {
				if (blackBox.isEnabledFor(LEVEL.Site)) {
					blackBox.log(LEVEL.Site, context, e);
				}
			}
		}

	}

	private Dao4NeSessionSNMPIf dao4NeSession;

	private Adapter4NeSessionSNMPIf adapter4NeSession;

	private Worker4NeSessionListenerIf[] listeners = {};

	private Worker4EventIf worker4Event;

	private static final BlackBox blackBox = new BlackBox(Worker4NeSessionSNMP.class);

	public void setDao4NeSession(Dao4NeSessionSNMPIf dao4NeSessionSNMP) {
		this.dao4NeSession = dao4NeSessionSNMP;
	}

	public void setAdapter4NeSession(Adapter4NeSessionSNMPIf adapter4NeSessionSNMP) {
		this.adapter4NeSession = adapter4NeSessionSNMP;
	}

	@Override
	public Class<? extends WorkerIf> getDefine_class() {
		return Worker4NeSessionSNMPIf.class;
	}

	@Override
	public NE_SESSION_PROTOCOL getProtocol() {
		return Model4NeSessionSNMP.PROTOCOL;
	}

	@Override
	public void initialize(EmpContext context) throws EmpException {
		if (dao4NeSession == null) {
			throw new EmpException(ERROR_CODE_ORANGE.WORKFLOW_NOTSETTED, Dao4NeSessionSNMPIf.class, getClass());
		}
		if (adapter4NeSession == null) {
			throw new EmpException(ERROR_CODE_ORANGE.WORKFLOW_NOTSETTED, Adapter4NeSessionSNMPIf.class, getClass());
		}
		dao4NeSession.initialize(context);
		adapter4NeSession.addListener(new Adapter4NeSessionSNMPListener());
		adapter4NeSession.initialize(context);

		worker4Event = (Worker4EventIf) WorkflowMap.getWorker(Worker4EventIf.class);
	}

	@Override
	public void dispose(EmpContext context) throws EmpException {
	}

	@Override
	public Model4NeSessionSNMP newInstanceNeSession(EmpContext context) throws EmpException {
		return Model4NeSessionSNMP.newInstance();
	}

	@Override
	public Model4NeSessionSNMP createNeSession(EmpContext context, Model4NeSessionIf ne_session) throws EmpException {
		ne_session = dao4NeSession.createNeSession(context, (Model4NeSessionSNMP) ne_session);
		return (Model4NeSessionSNMP) ne_session;
	}

	@Override
	public Model4NeSessionSNMP queryNeSession(EmpContext context, int ne_id) throws EmpException {
		return dao4NeSession.queryNeSession(context, ne_id);
	}

	@Override
	public Model4NeSessionSNMP queryNeSessionByAddress(EmpContext context, String address) throws EmpException {
		return dao4NeSession.queryNeSessionByAddress(context, address);
	}

	@Override
	public Model4NeSessionIf[] queryListNeSessionBySchedule(EmpContext context, int second_of_day) throws EmpException {
		return dao4NeSession.queryListNeSessionBySchedule(context, second_of_day);
	}

	@Override
	public Model4NeSessionSNMP updateNeSession(EmpContext context, Model4NeSessionIf ne_session) throws EmpException {
		ne_session = dao4NeSession.updateNeSession(context, (Model4NeSessionSNMP) ne_session);
		return (Model4NeSessionSNMP) ne_session;
	}

	@Override
	public Model4NeSessionSNMP deleteNeSession(EmpContext context, int ne_id) throws EmpException {
		return dao4NeSession.deleteNeSession(context, ne_id);
	}

	@Override
	public Model4NeSessionSNMP testNeSession(EmpContext context, int ne_id, Date collect_time) throws EmpException {
		Model4NeSessionSNMP ne_session = dao4NeSession.queryNeSession(context, ne_id);
		// boolean ne_session_state_prev = ne_session.isNe_session_state();

		if (ne_session.isAdmin_state()) {
			ne_session = adapter4NeSession.testNeSession(context, ne_session);
			ne_session = dao4NeSession.updateNeSessionState(context, ne_session);
		} else {
			ne_session.setNe_session_state(true);
			ne_session = dao4NeSession.updateNeSessionState(context, ne_session);

		}

		// if (ne_session.isNe_session_state() != ne_session_state_prev) {
		Model4Event event = toEvent(ne_session);
		if (event != null) {
			worker4Event.notifyEvent(context, event);
		}
		// }
		return ne_session;
	}

	protected Model4Event toEvent(Model4NeSessionSNMP ne_session) {
		Model4Event event = new Model4Event();
		event.setNe_id(ne_session.getNe_id());
		event.setNe_info_code(0);
		event.setNe_info_index(0);
		event.setLocation_display("SNMP");
		event.setEvent_code(EMP_MODEL_EVENT.SNMP_FAIL);
		event.setSeverity(ne_session.isAdmin_state() ? (ne_session.isNe_session_state() ? SEVERITY.CLEAR : SEVERITY.COMMUNICATION_FAIL) : SEVERITY.CLEAR);
		event.setGen_time(new Date());
		event.setGen_type(GEN_TYPE.SERVICE);
		event.setDescription(UtilString.format("SNMP {} {}", ne_session.getAddress(), ne_session.isAdmin_state() ? (ne_session.isNe_session_state() ? "Success" : "Fail") : "Disbled"));
		return event;
	}

	@Override
	public Model4NeSessionResponseIf[] executeNeSession(EmpContext context, int ne_id, Model4NeSessionRequestIf[] requests) throws EmpException {
		Model4NeSessionSNMP ne_session = dao4NeSession.queryNeSession(context, ne_id);
		if (ne_session != null && ne_session.isAdmin_state()) {
			Model4NeSessionRequestSNMPAt[] snmp_requests = new Model4NeSessionRequestSNMPAt[requests.length];
			System.arraycopy(requests, 0, snmp_requests, 0, requests.length);
			Model4NeSessionResponseSNMPIf[] responses = adapter4NeSession.executeNeSession(context, ne_session, snmp_requests);
			for (Model4NeSessionResponseSNMPIf response : responses) {
				((Model4NeSessionResponseSNMP) response).setCharset(ne_session.getCharset());
			}

			if (!ne_session.isNe_session_state()) {
				testNeSession(context, ne_session.getNe_id(), new Date());
			}

			return responses;
		} else {
			throw new EmpException(ERROR_CODE_ORANGE.MODEL_STATE, MESSAGE_CODE_ORANGE.SNMP, "admin", "false");
		}
	}

	@Override
	public Model4NeSessionDiscoveryFilterSNMP newInstanceDiscoveryFilter(EmpContext context) throws EmpException {
		return Model4NeSessionDiscoveryFilterSNMP.newInstance();
	}

	@Override
	public Model4NeSessionDiscoveryResultIf[] discoveryListNeSession(EmpContext context, Model4NeSessionDiscoveryFilterIf ne_session_discovery_filter) throws EmpException {
		return adapter4NeSession.discoveryListNeSession(context, (Model4NeSessionDiscoveryFilterSNMP) ne_session_discovery_filter);
	}

	protected long queryNextUpdate_seq_network(EmpContext context) throws EmpException {
		return dao4NeSession.queryNextUpdate_seq_network(context);
	}

	@Override
	public void addListener(Worker4NeSessionListenerIf listener) {
		List<Worker4NeSessionListenerIf> listenerList = new ArrayList<Worker4NeSessionListenerIf>();
		for (Worker4NeSessionListenerIf aaa : listeners) {
			listenerList.add(aaa);
		}
		listenerList.add(listener);

		listeners = listenerList.toArray(new Worker4NeSessionListenerIf[0]);
	}

	@Override
	public void removeListener(Worker4NeSessionListenerIf listener) {
		List<Worker4NeSessionListenerIf> listenerList = new ArrayList<Worker4NeSessionListenerIf>();
		for (Worker4NeSessionListenerIf aaa : listeners) {
			if (listener != aaa) {
				listenerList.add(aaa);
			}
		}

		listeners = listenerList.toArray(new Worker4NeSessionListenerIf[0]);
	}

	@Override
	public void truncate(EmpContext context) throws EmpException {
		dao4NeSession.truncate(context);
		UtilCache.removeAll();
	}

}
