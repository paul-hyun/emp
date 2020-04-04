/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.server.workflow.network.ne_session.icmp;

import java.util.Date;

import com.hellonms.platforms.emp_core.server.log.BlackBox;
import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.server.workflow.WorkerIf;
import com.hellonms.platforms.emp_core.server.workflow.WorkflowMap;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_orange.server.workflow.fault.event.Worker4EventIf;
import com.hellonms.platforms.emp_orange.share.error.ERROR_CODE_ORANGE;
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
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.icmp.Model4NeSessionDiscoveryFilterICMP;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.icmp.Model4NeSessionICMP;
import com.hellonms.platforms.emp_util.cache.UtilCache;
import com.hellonms.platforms.emp_util.string.UtilString;

/**
 * <p>
 * ICMP 통신채널 Worker
 * </p>
 *
 * @since 1.6
 * @create 2015. 3. 30.
 * @modified 2015. 3. 30.
 * @author cchyun
 *
 */
public class Worker4NeSessionICMP implements Worker4NeSessionICMPIf {

	private Dao4NeSessionICMPIf dao4NeSession;

	private Adapter4NeSessionICMPIf adapter4NeSession;

	private Worker4EventIf worker4Event;

	@SuppressWarnings("unused")
	private static final BlackBox blackBox = new BlackBox(Worker4NeSessionICMP.class);

	public void setDao4NeSession(Dao4NeSessionICMPIf dao4NeSessionICMP) {
		this.dao4NeSession = dao4NeSessionICMP;
	}

	public void setAdapter4NeSession(Adapter4NeSessionICMPIf adapter4NeSessionICMP) {
		this.adapter4NeSession = adapter4NeSessionICMP;
	}

	@Override
	public Class<? extends WorkerIf> getDefine_class() {
		return Worker4NeSessionICMPIf.class;
	}

	@Override
	public NE_SESSION_PROTOCOL getProtocol() {
		return Model4NeSessionICMP.PROTOCOL;
	}

	@Override
	public void initialize(EmpContext context) throws EmpException {
		if (dao4NeSession == null) {
			throw new EmpException(ERROR_CODE_ORANGE.WORKFLOW_NOTSETTED, Dao4NeSessionICMPIf.class, getClass());
		}
		if (adapter4NeSession == null) {
			throw new EmpException(ERROR_CODE_ORANGE.WORKFLOW_NOTSETTED, Adapter4NeSessionICMPIf.class, getClass());
		}
		dao4NeSession.initialize(context);
		adapter4NeSession.initialize(context);

		worker4Event = (Worker4EventIf) WorkflowMap.getWorker(Worker4EventIf.class);
	}

	@Override
	public void dispose(EmpContext context) throws EmpException {
	}

	@Override
	public Model4NeSessionICMP newInstanceNeSession(EmpContext context) throws EmpException {
		return Model4NeSessionICMP.newInstance();
	}

	@Override
	public Model4NeSessionICMP createNeSession(EmpContext context, Model4NeSessionIf ne_session) throws EmpException {
		ne_session = dao4NeSession.createNeSession(context, (Model4NeSessionICMP) ne_session);
		return (Model4NeSessionICMP) ne_session;
	}

	@Override
	public Model4NeSessionICMP queryNeSession(EmpContext context, int ne_id) throws EmpException {
		return dao4NeSession.queryNeSession(context, ne_id);
	}

	@Override
	public Model4NeSessionICMP queryNeSessionByAddress(EmpContext context, String address) throws EmpException {
		return dao4NeSession.queryNeSessionByAddress(context, address);
	}

	@Override
	public Model4NeSessionIf[] queryListNeSessionBySchedule(EmpContext context, int second_of_day) throws EmpException {
		return dao4NeSession.queryListNeSessionBySchedule(context, second_of_day);
	}

	@Override
	public Model4NeSessionICMP updateNeSession(EmpContext context, Model4NeSessionIf ne_session) throws EmpException {
		ne_session = dao4NeSession.updateNeSession(context, (Model4NeSessionICMP) ne_session);
		return (Model4NeSessionICMP) ne_session;
	}

	@Override
	public Model4NeSessionICMP deleteNeSession(EmpContext context, int ne_id) throws EmpException {
		return dao4NeSession.deleteNeSession(context, ne_id);
	}

	@Override
	public Model4NeSessionICMP testNeSession(EmpContext context, int ne_id, Date collect_time) throws EmpException {
		Model4NeSessionICMP ne_session = dao4NeSession.queryNeSession(context, ne_id);
		boolean ne_session_state_prev = ne_session.isNe_session_state();

		if (ne_session.isAdmin_state()) {
			ne_session = adapter4NeSession.testNeSession(context, ne_session);
			ne_session = dao4NeSession.updateNeSessionState(context, ne_session);
		} else {
			ne_session.setNe_session_state(true);
			ne_session = dao4NeSession.updateNeSessionState(context, ne_session);

		}

		if (ne_session.isNe_session_state() != ne_session_state_prev) {
			Model4Event event = toEvent(ne_session);
			if (event != null) {
				worker4Event.notifyEvent(context, event);
			}
		}
		return ne_session;
	}

	protected Model4Event toEvent(Model4NeSessionICMP ne_session) {
		Model4Event event = new Model4Event();
		event.setNe_id(ne_session.getNe_id());
		event.setNe_info_code(0);
		event.setNe_info_index(0);
		event.setLocation_display("ICMP");
		event.setEvent_code(EMP_MODEL_EVENT.ICMP_FAIL);
		event.setSeverity(ne_session.isAdmin_state() ? (ne_session.isNe_session_state() ? SEVERITY.CLEAR : SEVERITY.COMMUNICATION_FAIL) : SEVERITY.CLEAR);
		event.setGen_time(new Date());
		event.setGen_type(GEN_TYPE.SERVICE);
		event.setDescription(UtilString.format("ICMP {} {}", ne_session.getAddress(), ne_session.isAdmin_state() ? (ne_session.isNe_session_state() ? "Success" : "Fail") : "Disbled"));
		return event;
	}

	@Override
	public Model4NeSessionResponseIf[] executeNeSession(EmpContext context, int ne_id, Model4NeSessionRequestIf[] requests) throws EmpException {
		throw new EmpException(ERROR_CODE_ORANGE.NESESSION_EXECUTEFAIL, Model4NeSessionICMP.PROTOCOL.toString());
	}

	@Override
	public Model4NeSessionDiscoveryFilterICMP newInstanceDiscoveryFilter(EmpContext context) throws EmpException {
		return Model4NeSessionDiscoveryFilterICMP.newInstance();
	}

	@Override
	public Model4NeSessionDiscoveryResultIf[] discoveryListNeSession(EmpContext context, Model4NeSessionDiscoveryFilterIf ne_session_discovery_filter) throws EmpException {
		return adapter4NeSession.discoveryListNeSession(context, (Model4NeSessionDiscoveryFilterICMP) ne_session_discovery_filter);
	}

	protected long queryNextUpdate_seq_network(EmpContext context) throws EmpException {
		return dao4NeSession.queryNextUpdate_seq_network(context);
	}

	@Override
	public void addListener(Worker4NeSessionListenerIf listener) {
	}

	@Override
	public void removeListener(Worker4NeSessionListenerIf listener) {
	}

	@Override
	public void truncate(EmpContext context) throws EmpException {
		dao4NeSession.truncate(context);
		UtilCache.removeAll();
	}

}
