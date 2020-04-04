/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.server.workflow.network.ne;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.hellonms.platforms.emp_core.server.log.BlackBox;
import com.hellonms.platforms.emp_core.server.log.LEVEL;
import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.server.workflow.WorkerIf;
import com.hellonms.platforms.emp_core.server.workflow.WorkflowMap;
import com.hellonms.platforms.emp_core.share.error.ERROR_CODE;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_onion.share.model.ACCESS;
import com.hellonms.platforms.emp_orange.server.workflow.network.ne_group.Worker4NeGroupIf;
import com.hellonms.platforms.emp_orange.server.workflow.network.ne_info.Worker4NeInfoIf;
import com.hellonms.platforms.emp_orange.server.workflow.network.ne_session.Worker4NeSessionIf;
import com.hellonms.platforms.emp_orange.server.workflow.network.ne_session.Worker4NeSessionIf.Worker4NeSessionListenerIf;
import com.hellonms.platforms.emp_orange.share.error.ERROR_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE;
import com.hellonms.platforms.emp_orange.share.model.network.ne.Model4Ne;
import com.hellonms.platforms.emp_orange.share.model.network.ne_group.Model4NeGroup;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.*;
import com.hellonms.platforms.emp_util.cache.UtilCache;
import com.hellonms.platforms.emp_util.map.UtilMapOID;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * <p>
 * 사용자 Worker
 * </p>
 * 
 * @since 1.6
 * @create 2015. 3. 13.
 * @modified 2015. 3. 13.
 * @author cchyun
 */
public class Worker4Ne implements Worker4NeIf {

	private class AsyncTask implements Runnable {
		private final Model4Ne ne;

		public AsyncTask(Model4Ne ne) {
			this.ne = ne;
		}

		@Override
		public void run() {
			EmpContext context = new EmpContext(this);
			try {
				Date collect_time = new Date();
				for (Model4NeSessionIf ne_session : ne.getNeSessions()) {
					ne_session = getWorker4NeSession(ne_session.getProtocol()).testNeSession(context, ne.getNe_id(), collect_time);
					if (ne_session.isAdmin_state() && ne_session.isNe_session_state()) {
						worker4NeInfo.syncListNeInfo(context, ne.getNe_id(), ne_session.getProtocol());
					}
				}
			} catch (Exception e) {
				if (blackBox.isEnabledFor(LEVEL.Site)) {
					blackBox.log(LEVEL.Site, context, e);
				}
			} finally {
				context.close();
			}
		}
	}

	protected Dao4NeIf dao4Ne;

	protected Worker4NeGroupIf worker4NeGroup;

	protected UtilMapOID<Trigger4NeIf> trigger_map = new UtilMapOID<Trigger4NeIf>();

	protected Map<NE_SESSION_PROTOCOL, Worker4NeSessionIf> worker4NeSessionMap = new LinkedHashMap<NE_SESSION_PROTOCOL, Worker4NeSessionIf>();

	protected Worker4NeSessionIf[] worker4NeSessions;

	protected Worker4NeInfoIf worker4NeInfo;

	protected ThreadPoolExecutor threadpool_async;

	protected static final BlackBox blackBox = new BlackBox(Worker4Ne.class);

	public Worker4Ne() {
	}

	public void setDao4Ne(Dao4NeIf dao4Ne) {
		this.dao4Ne = dao4Ne;
	}

	public void addTrigger(Trigger4NeIf trigger) {
		trigger_map.put(trigger, trigger.getNe_def());
	}

	@Override
	public Class<? extends WorkerIf> getDefine_class() {
		return Worker4NeIf.class;
	}

	@Override
	public void initialize(EmpContext context) throws EmpException {
		if (dao4Ne == null) {
			throw new EmpException(ERROR_CODE_ORANGE.WORKFLOW_NOTSETTED, Dao4NeIf.class, getClass());
		}
		dao4Ne.initialize(context);

		worker4NeGroup = (Worker4NeGroupIf) WorkflowMap.getWorker(Worker4NeGroupIf.class);
		worker4NeInfo = (Worker4NeInfoIf) WorkflowMap.getWorker(Worker4NeInfoIf.class);
		threadpool_async = (ThreadPoolExecutor) Executors.newFixedThreadPool(6);
	}

	@Override
	public void dispose(EmpContext context) throws EmpException {
		dao4Ne.dispose(context);
	}

	public void addWorker4NeSession(Worker4NeSessionIf worker4NeSession) throws EmpException {
		if (worker4NeSessionMap.get(worker4NeSession.getProtocol()) != null) {
			throw new EmpException(ERROR_CODE_ORANGE.WORKFLOW_DUPLICATE, worker4NeSession.getProtocol().toString());
		}
		worker4NeSessions = null;
		worker4NeSessionMap.put(worker4NeSession.getProtocol(), worker4NeSession);
	}

	@Override
	public Worker4NeSessionIf[] getListWorker4NeSession() {
		Worker4NeSessionIf[] worker4NeSessions = this.worker4NeSessions;
		if (worker4NeSessions == null) {
			worker4NeSessions = worker4NeSessionMap.values().toArray(new Worker4NeSessionIf[0]);
			this.worker4NeSessions = worker4NeSessions;
		}
		return worker4NeSessions;
	}

	@Override
	public Worker4NeSessionIf getWorker4NeSession(NE_SESSION_PROTOCOL protocol) throws EmpException {
		Worker4NeSessionIf worker4NeSession = worker4NeSessionMap.get(protocol);
		if (worker4NeSession == null) {
			throw new EmpException(ERROR_CODE_ORANGE.WORKFLOW_NOTEXISTS, protocol.toString());
		}
		return worker4NeSession;
	}

	@Override
	public Model4Ne newInstanceNe(EmpContext context) throws EmpException {
		Model4Ne ne = Model4Ne.newInstance();
		for (Worker4NeSessionIf worker4NeSession : getListWorker4NeSession()) {
			ne.addNeSession(worker4NeSession.newInstanceNeSession(context));
		}
		return ne;
	}

	@Override
	public Model4Ne createNe(EmpContext context, Model4Ne ne) throws EmpException {
		@SuppressWarnings("unused")
		Model4NeGroup ne_group = worker4NeGroup.queryNeGroup(context, ne.getNe_group_id());

		Trigger4NeIf trigger = trigger_map.get(ne.getNe_code());
		if (trigger != null) {
			Model4NeSessionRequestIf[] requests = trigger.preCreateNe(context, ne);
			if (requests != null && 0 < requests.length) {
				Model4NeSessionResponseIf[] responses = executeNe(context, ne.getNe_id(), requests);
				ne = trigger.postCreateNe(context, ne, responses);
			}
		}

		boolean auto_commit = context.isAuto_commit();
		Model4Ne ne_created = ne;
		try {
			context.setAuto_commit(false);
			ne_created = dao4Ne.createNe(context, ne);
			for (Model4NeSessionIf ne_session : ne.getNeSessions()) {
				ne_session.setNe_id(ne_created.getNe_id());
				getWorker4NeSession(ne_session.getProtocol()).createNeSession(context, ne_session);
			}
			context.commit();
		} catch (EmpException e) {
			context.rollback();
			throw e;
		} catch (Exception e) {
			context.rollback();
			throw new EmpException(ERROR_CODE.ERROR_UNKNOWN, e);
		} finally {
			context.setAuto_commit(auto_commit);
		}
		queryNextUpdate_seq_network(context);

		ne_created = queryNe(context, ne_created.getNe_id());
		threadpool_async.execute(new AsyncTask(ne_created));

		return ne_created;
	}

	@Override
	public Model4Ne queryNe(EmpContext context, int ne_id) throws EmpException {
		return queryListNeSession(context, dao4Ne.queryNe(context, ne_id));
	}

	@Override
	public Model4Ne queryNe(EmpContext context, int ne_group_id, String ne_name) throws EmpException {
		return queryListNeSession(context, dao4Ne.queryNe(context, ne_group_id, ne_name));
	}

	@Override
	public Model4Ne[] queryListNe(EmpContext context, int startNo, int count) throws EmpException {
		Model4Ne[] nes = dao4Ne.queryListNe(context, startNo, count);
		for (int i = 0; i < nes.length; i++) {
			nes[i] = queryListNeSession(context, nes[i]);
		}
		return nes;
	}

	@Override
	public Model4Ne[] queryListNe(EmpContext context, int ne_group_id, int startNo, int count) throws EmpException {
		Model4Ne[] nes = dao4Ne.queryListNe(context, ne_group_id, startNo, count);
		for (int i = 0; i < nes.length; i++) {
			nes[i] = queryListNeSession(context, nes[i]);
		}
		return nes;
	}

	@Override
	public Model4Ne[] queryListNe(EmpContext context, EMP_MODEL_NE ne_def, int startNo, int count) throws EmpException {
		Model4Ne[] nes = dao4Ne.queryListNe(context, ne_def, startNo, count);
		for (int i = 0; i < nes.length; i++) {
			nes[i] = queryListNeSession(context, nes[i]);
		}
		return nes;
	}

	@Override
	public Model4Ne[] queryAllNe(EmpContext context) throws EmpException {
		Model4Ne[] nes = dao4Ne.queryAllNe(context);
		for (int i = 0; i < nes.length; i++) {
			nes[i] = queryListNeSession(context, nes[i]);
		}
		return nes;
	}

	@Override
	public Model4Ne[] queryAllNe(EmpContext context, int ne_group_id) throws EmpException {
		Model4Ne[] nes = dao4Ne.queryAllNe(context, ne_group_id);
		for (int i = 0; i < nes.length; i++) {
			nes[i] = queryListNeSession(context, nes[i]);
		}
		return nes;
	}

	protected Model4Ne queryListNeSession(EmpContext context, Model4Ne ne) {
		if (!ne.isNMS()) {
			ne.clearNeSession();
			for (Worker4NeSessionIf worker4NeSession : getListWorker4NeSession()) {
				try {
					ne.addNeSession(worker4NeSession.queryNeSession(context, ne.getNe_id()));
				} catch (EmpException e) {
					if (blackBox.isEnabledFor(LEVEL.Fatal)) {
						blackBox.log(LEVEL.Fatal, context, e);
					}
				}
			}
		}
		return ne;
	}

	@Override
	public Model4NeSessionDiscoveryFilterIf[] newInstanceListDiscoveryFilter(EmpContext context) throws EmpException {
		Worker4NeSessionIf[] worker4NeSessions = getListWorker4NeSession();
		Model4NeSessionDiscoveryFilterIf[] ne_session_discovery_filters = new Model4NeSessionDiscoveryFilterIf[worker4NeSessions.length];
		for (int i = 0; i < worker4NeSessions.length; i++) {
			ne_session_discovery_filters[i] = worker4NeSessions[i].newInstanceDiscoveryFilter(context);
		}
		return ne_session_discovery_filters;
	}

	@Override
	public Model4Ne[] discoveryListNe(EmpContext context, Model4NeSessionDiscoveryFilterIf[] ne_session_discovery_filters) throws EmpException {
		Multimap<String, Model4NeSessionDiscoveryResultIf> ne_session_discovery_result_map = ArrayListMultimap.create();
		for (Model4NeSessionDiscoveryFilterIf ne_session_discovery_filter : ne_session_discovery_filters) {
			Model4NeSessionDiscoveryResultIf[] ne_session_discovery_results = getWorker4NeSession(ne_session_discovery_filter.getProtocol()).discoveryListNeSession(context, ne_session_discovery_filter);

			for (Model4NeSessionDiscoveryResultIf ne_session_discovery_result : ne_session_discovery_results) {
				ne_session_discovery_result_map.put(ne_session_discovery_result.getAddress(), ne_session_discovery_result);
			}
		}

		List<Model4Ne> ne_list = new ArrayList<Model4Ne>();
		for (String inetAddress : ne_session_discovery_result_map.keySet()) {
			Collection<Model4NeSessionDiscoveryResultIf> ne_session_discovery_result_list = ne_session_discovery_result_map.get(inetAddress);
			Model4Ne[] nes = newDiscoveryNe(context, inetAddress, ne_session_discovery_result_list.toArray(new Model4NeSessionDiscoveryResultIf[0]));
			if (nes != null) {
				for (Model4Ne ne : nes) {
					ne_list.add(ne);
				}
			}
		}
		return ne_list.toArray(new Model4Ne[0]);
	}

	/**
	 * <p>
	 * 검색 결과를 NE로 변경한다.
	 * </p>
	 *
	 * @param context
	 * @param address
	 * @param ne_session_discovery_results
	 * @return
	 * @throws EmpException
	 */
	protected Model4Ne[] newDiscoveryNe(EmpContext context, String address, Model4NeSessionDiscoveryResultIf... ne_session_discovery_results) throws EmpException {
		// 성공여부 확인: 전체 실패는 처리하지 않음
		boolean success = false;
		for (Model4NeSessionDiscoveryResultIf ne_session_discovery_result : ne_session_discovery_results) {
			if (ne_session_discovery_result.isSuccess()) {
				success = true;
				break;
			}
		}
		if (!success) {
			return null;
		}

		// 존재여부 확인: 동일주소가 등록된 경우는 skip
		for (Worker4NeSessionIf worker4NeSession : getListWorker4NeSession()) {
			try {
				worker4NeSession.queryNeSessionByAddress(context, address);
				return null;
			} catch (EmpException e) {
			}
		}

		// NE 생성
		EMP_MODEL_NE ne_def = null;
		String ne_name = null;
		String description = null;
		for (Model4NeSessionDiscoveryResultIf ne_session_discovery_result : ne_session_discovery_results) {
			EMP_MODEL_NE new_ne_code = ne_session_discovery_result.getNe_oid() == null ? null : EMP_MODEL.current().getNe_by_oid(ne_session_discovery_result.getNe_oid());
			String aaa_ne_name = ne_session_discovery_result.getNe_name();
			String aaa_description = ne_session_discovery_result.getDescription();
			if (ne_def == null) {
				ne_def = new_ne_code;
			}
			if (ne_name == null && aaa_ne_name != null) {
				ne_name = aaa_ne_name;
			}
			if (description == null && aaa_description != null) {
				description = aaa_description;
			}
		}

		if (ne_name == null) {
			ne_name = address;
		}
		if (description == null) {
			description = ne_name;
		}

		Model4Ne model = newInstanceNe(context);
		model.setNe_code(ne_def == null ? 0 : ne_def.getCode());
		model.setNe_name(ne_name);
		model.setAccess(ACCESS.ACCESS_DELETE);
		model.setDescription(description);
		model.setNe_icon(ne_def == null ? EMP_MODEL_NE.NE_ICON : ne_def.getNe_icon());
		model.setAdmin_state(true);

		for (Model4NeSessionDiscoveryResultIf ne_session_discovery_result : ne_session_discovery_results) {
			model.addNeSession(ne_session_discovery_result.toNeSession());
		}

		return new Model4Ne[] { model };
	}

	@Override
	public int queryCountNe(EmpContext context) throws EmpException {
		return dao4Ne.queryCountNe(context);
	}

	@Override
	public int queryCountNe(EmpContext context, int ne_group_id) throws EmpException {
		return dao4Ne.queryCountNe(context, ne_group_id);
	}

	@Override
	public int queryCountNe(EmpContext context, EMP_MODEL_NE ne_def) throws EmpException {
		return dao4Ne.queryCountNe(context, ne_def);
	}

	@Override
	public Model4Ne updateNe(EmpContext context, Model4Ne ne) throws EmpException {
		@SuppressWarnings("unused")
		Model4NeGroup ne_group = worker4NeGroup.queryNeGroup(context, ne.getNe_group_id());

		Trigger4NeIf trigger = trigger_map.get(ne.getNe_code());
		if (trigger != null) {
			Model4NeSessionRequestIf[] requests = trigger.preUpdateNe(context, ne);
			if (requests != null && 0 < requests.length) {
				Model4NeSessionResponseIf[] responses = executeNe(context, ne.getNe_id(), requests);
				ne = trigger.postUpdateNe(context, ne, responses);
			}
		}

		boolean auto_commit = context.isAuto_commit();
		Model4Ne ne_updated = ne;
		try {
			context.setAuto_commit(false);
			ne_updated = dao4Ne.updateNe(context, ne);
			for (Model4NeSessionIf ne_session : ne.getNeSessions()) {
				getWorker4NeSession(ne_session.getProtocol()).updateNeSession(context, ne_session);
			}
			context.commit();
		} catch (EmpException e) {
			context.rollback();
			throw e;
		} catch (Exception e) {
			context.rollback();
			throw new EmpException(ERROR_CODE.ERROR_UNKNOWN, e);
		} finally {
			context.setAuto_commit(auto_commit);
		}
		queryNextUpdate_seq_network(context);

		ne_updated = queryNe(context, ne_updated.getNe_id());
		threadpool_async.execute(new AsyncTask(ne_updated));

		return ne_updated;
	}

	@Override
	public Model4Ne updateNeMapLocation(EmpContext context, Model4Ne ne) throws EmpException {
		dao4Ne.updateNeMapLocation(context, ne);
		queryNextUpdate_seq_network(context);

		Model4Ne ne_updated = queryNe(context, ne.getNe_id());
		return ne_updated;
	}

	@Override
	public Model4Ne deleteNe(EmpContext context, int ne_id) throws EmpException {
		Model4Ne exists = dao4Ne.queryNe(context, ne_id);
		exists = queryListNeSession(context, exists);

		Trigger4NeIf trigger = trigger_map.get(exists.getNe_code());
		if (trigger != null) {
			Model4NeSessionRequestIf[] requests = trigger.preDeleteNe(context, exists);
			if (requests != null && 0 < requests.length) {
				Model4NeSessionResponseIf[] responses = executeNe(context, exists.getNe_id(), requests);
				exists = trigger.postDeleteNe(context, exists, responses);
			}
		}

		@SuppressWarnings("unused")
		Model4Ne ne_deleted = dao4Ne.deleteNe(context, ne_id);
		for (Model4NeSessionIf ne_session : exists.getNeSessions()) {
			getWorker4NeSession(ne_session.getProtocol()).deleteNeSession(context, ne_id);
		}

		queryNextUpdate_seq_network(context);
		return exists;
	}

	@Override
	public Model4NeSessionIf[] queryListNeSessionBySchedule(EmpContext context, int second_of_day) throws EmpException {
		List<Model4NeSessionIf> ne_session_list = new ArrayList<Model4NeSessionIf>();

		for (Worker4NeSessionIf worker4NeSession : getListWorker4NeSession()) {
			Model4NeSessionIf[] ne_sessions = worker4NeSession.queryListNeSessionBySchedule(context, second_of_day);

			for (Model4NeSessionIf ne_session : ne_sessions) {
				ne_session_list.add(ne_session);
			}
		}

		return ne_session_list.toArray(new Model4NeSessionIf[0]);
	}

	@Override
	public Model4NeSessionIf testNeSession(EmpContext context, int ne_id, NE_SESSION_PROTOCOL protocol, Date collect_time) throws EmpException {
		Model4NeSessionIf ne_session = getWorker4NeSession(protocol).testNeSession(context, ne_id, collect_time);

		queryNextUpdate_seq_network(context);
		return ne_session;
	}

	@Override
	public Model4NeSessionResponseIf[] executeNe(EmpContext context, int ne_id, Model4NeSessionRequestIf[] requests) throws EmpException {
		Model4Ne ne_executed = dao4Ne.queryNe(context, ne_id);
		ne_executed = queryListNeSession(context, ne_executed);

		Multimap<NE_SESSION_PROTOCOL, Model4NeSessionRequestIf> request_map = ArrayListMultimap.create();
		for (Model4NeSessionRequestIf request : requests) {
			request_map.put(request.getProtocol(), request);
		}

		List<Model4NeSessionResponseIf> response_list = new ArrayList<Model4NeSessionResponseIf>();
		for (NE_SESSION_PROTOCOL protocol : request_map.keySet()) {
			Collection<Model4NeSessionRequestIf> request_list = request_map.get(protocol);
			Model4NeSessionResponseIf[] responses = getWorker4NeSession(protocol).executeNeSession(context, ne_id, request_list.toArray(new Model4NeSessionRequestIf[0]));
			for (Model4NeSessionResponseIf response : responses) {
				response_list.add(response);
			}
		}

		return response_list.toArray(new Model4NeSessionResponseIf[0]);
	}

	@Override
	public void addListener(Worker4NeSessionListenerIf listener) {
		for (Worker4NeSessionIf worker4NeSession : getListWorker4NeSession()) {
			worker4NeSession.addListener(listener);
		}
	}

	@Override
	public void removeListener(Worker4NeSessionListenerIf listener) {
		for (Worker4NeSessionIf worker4NeSession : getListWorker4NeSession()) {
			worker4NeSession.removeListener(listener);
		}
	}

	@Override
	public long queryCurrUpdate_seq_network(EmpContext context) throws EmpException {
		return dao4Ne.queryCurrUpdate_seq_network(context);
	}

	protected long queryNextUpdate_seq_network(EmpContext context) throws EmpException {
		return dao4Ne.queryNextUpdate_seq_network(context);
	}

	@Override
	public void truncate(EmpContext context) throws EmpException {
		for (Worker4NeSessionIf worker4NeSession : getListWorker4NeSession()) {
			worker4NeSession.truncate(context);
		}
		dao4Ne.truncate(context);
		UtilCache.removeAll();
		dao4Ne.queryNextUpdate_seq_network(context);
	}

}
