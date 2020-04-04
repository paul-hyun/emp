/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.server.workflow.network.ne_info;

import java.util.Calendar;
import java.util.Date;

import com.hellonms.platforms.emp_core.server.log.BlackBox;
import com.hellonms.platforms.emp_core.server.log.LEVEL;
import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.server.workflow.WorkerIf;
import com.hellonms.platforms.emp_core.server.workflow.WorkflowMap;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_onion.share.model.STATISTICS_TYPE;
import com.hellonms.platforms.emp_orange.server.workflow.fault.event.Worker4EventIf;
import com.hellonms.platforms.emp_orange.server.workflow.network.ne.Worker4NeIf;
import com.hellonms.platforms.emp_orange.server.workflow.network.ne_session.Worker4NeSessionIf.Worker4NeSessionListenerIf;
import com.hellonms.platforms.emp_orange.share.error.ERROR_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO_FIELD;
import com.hellonms.platforms.emp_orange.share.model.fault.event.Model4Event;
import com.hellonms.platforms.emp_orange.share.model.network.ne.Model4Ne;
import com.hellonms.platforms.emp_orange.share.model.network.ne_info.Model4NeInfoIf;
import com.hellonms.platforms.emp_orange.share.model.network.ne_info.Model4NeStatistics;
import com.hellonms.platforms.emp_orange.share.model.network.ne_info.Model4NeStatisticsIf;
import com.hellonms.platforms.emp_orange.share.model.network.ne_info.Model4NeThresholdIf;
import com.hellonms.platforms.emp_orange.share.model.network.ne_info.NE_INFO_INDEX;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.Model4NeSessionIf;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.Model4NeSessionNotificationIf;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.Model4NeSessionRequestIf;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.Model4NeSessionResponseIf;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.NE_SESSION_PROTOCOL;
import com.hellonms.platforms.emp_util.cache.UtilCache;
import com.hellonms.platforms.emp_util.date.UtilDate;
import com.hellonms.platforms.emp_util.map.UtilMapOID;
import com.hellonms.platforms.emp_util.string.UtilString;

/**
 * <p>
 * NE 정보 Worker
 * </p>
 *
 * @since 1.6
 * @create 2015. 4. 7.
 * @modified 2015. 4. 7.
 * @author cchyun
 *
 */
public class Worker4NeInfo implements Worker4NeInfoIf {

	private class Worker4NeSessionListener implements Worker4NeSessionListenerIf {

		@Override
		public void handleNotification(EmpContext context, Model4NeSessionIf ne_session, Model4NeSessionNotificationIf notification) {
			try {
				Worker4NeInfo.this.handleNotification(context, ne_session, notification);
			} catch (Exception e) {
				if (blackBox.isEnabledFor(LEVEL.Site)) {
					blackBox.log(LEVEL.Site, context, e);
				}
			}
		}

	}

	protected Dao4NeInfoIf dao4NeInfo;

	protected Dao4NeStatisticsIf dao4NeStatistics;

	protected Dao4NeThresholdIf dao4NeThreshold;

	protected UtilMapOID<Trigger4NeInfoIf> trigger_map = new UtilMapOID<Trigger4NeInfoIf>();

	protected Worker4NeIf worker4Ne;

	protected Worker4EventIf worker4Event;

	private static final BlackBox blackBox = new BlackBox(Worker4NeInfo.class);

	public void setDao4NeInfo(Dao4NeInfoIf dao4NeInfo) {
		this.dao4NeInfo = dao4NeInfo;
	}

	public void setDao4NeStatistics(Dao4NeStatisticsIf dao4NeStatistics) {
		this.dao4NeStatistics = dao4NeStatistics;
	}

	public void setDao4NeThreshold(Dao4NeThresholdIf dao4NeThreshold) {
		this.dao4NeThreshold = dao4NeThreshold;
	}

	public void addTrigger(Trigger4NeInfoIf trigger) {
		trigger_map.put(trigger, trigger.getNe_info_code());
	}

	@Override
	public Class<? extends WorkerIf> getDefine_class() {
		return Worker4NeInfoIf.class;
	}

	@Override
	public void initialize(EmpContext context) throws EmpException {
		if (dao4NeInfo == null) {
			throw new EmpException(ERROR_CODE_ORANGE.WORKFLOW_NOTSETTED, Dao4NeInfoIf.class, getClass());
		}
		if (dao4NeStatistics == null) {
			throw new EmpException(ERROR_CODE_ORANGE.WORKFLOW_NOTSETTED, Dao4NeStatisticsIf.class, getClass());
		}
		if (dao4NeThreshold == null) {
			throw new EmpException(ERROR_CODE_ORANGE.WORKFLOW_NOTSETTED, Dao4NeThresholdIf.class, getClass());
		}
		for (Trigger4NeInfoIf trigger : trigger_map.values(new Trigger4NeInfoIf[0])) {
			trigger.initialize(context);
		}
		dao4NeInfo.initialize(context);
		dao4NeStatistics.initialize(context);
		dao4NeThreshold.initialize(context);

		worker4Ne = (Worker4NeIf) WorkflowMap.getWorker(Worker4NeIf.class);
		worker4Event = (Worker4EventIf) WorkflowMap.getWorker(Worker4EventIf.class);

		worker4Ne.addListener(new Worker4NeSessionListener());
	}

	@Override
	public void dispose(EmpContext context) throws EmpException {
	}

	protected Trigger4NeInfoIf getTrigger(Object... keys) {
		return trigger_map.get(keys);
	}

	@Override
	public NE_INFO_INDEX queryNeInfoIndex(EmpContext context, String... ne_info_index_values) throws EmpException {
		return dao4NeInfo.queryNeInfoIndex(context, ne_info_index_values);
	}

	@Override
	public void syncListNeInfo(EmpContext context, int ne_id, NE_SESSION_PROTOCOL protocol) throws EmpException {
		Model4Ne ne = worker4Ne.queryNe(context, ne_id);

		EMP_MODEL_NE_INFO[] ne_info_defs = EMP_MODEL.current().getNe_infos_by_ne_monitoring(ne.getNe_code(), protocol);
		for (EMP_MODEL_NE_INFO ne_info_def : ne_info_defs) {
			try {
				if (blackBox.isEnabledFor(LEVEL.Site)) {
					blackBox.log(LEVEL.Site, context, UtilString.format("start readListNeInfo {}, {}", ne_id, ne_info_def));
				}
				Model4NeInfoIf[] ne_infos = readListNeInfo(context, ne_id, ne_info_def);
				if (blackBox.isEnabledFor(LEVEL.Site)) {
					blackBox.log(LEVEL.Site, context, UtilString.format("end readListNeInfo {}, {}, {}", ne_id, ne_info_def, ne_infos.length));
				}
			} catch (Exception e) {
				if (blackBox.isEnabledFor(LEVEL.Fatal)) {
					blackBox.log(LEVEL.Fatal, context, e, UtilString.format("fail readListNeInfo {}, {}", ne_id, ne_info_def));
				}
			}
		}
	}

	@Override
	public Model4NeInfoIf[] readListNeInfo(EmpContext context, int ne_id, EMP_MODEL_NE_INFO ne_info_def) throws EmpException {
		@SuppressWarnings("unused")
		Model4Ne ne = worker4Ne.queryNe(context, ne_id);
		Model4NeInfoIf[] ne_infos = {};

		Trigger4NeInfoIf trigger = getTrigger(ne_info_def);
		if (trigger != null) {
			Model4NeSessionRequestIf[] requests = trigger.preReadListNeInfo(context, ne_id, ne_info_def);
			if (requests != null && 0 < requests.length) {
				Model4NeSessionResponseIf[] responses = worker4Ne.executeNe(context, ne_id, requests);
				ne_infos = trigger.postReadListNeInfo(context, ne_id, ne_info_def, responses);

				if (ne_infos != null) {
					syncListNeInfo(context, ne_id, ne_info_def, ne_infos);

					if (ne_info_def.isFault_enable()) {
						Model4Event[] event_logs = trigger.eventListNeInfo(context, ne_id, ne_info_def, ne_infos);
						if (event_logs != null && 0 < event_logs.length) {
							this.notifyEvents(context, event_logs);
						}
					}
				}
			}
		}

		return ne_infos;
	}

	@Override
	public Model4NeInfoIf[] queryAllNeInfo(EmpContext context, EMP_MODEL_NE_INFO ne_info_def) throws EmpException {
		return dao4NeInfo.queryAllNeInfo(context, ne_info_def);
	}

	@Override
	public Model4NeInfoIf[] queryListNeInfo(EmpContext context, int ne_id, EMP_MODEL_NE_INFO ne_info_def) throws EmpException {
		@SuppressWarnings("unused")
		Model4Ne ne = worker4Ne.queryNe(context, ne_id);

		return dao4NeInfo.queryListNeInfo(context, ne_id, ne_info_def);
	}

	@Override
	public Model4NeInfoIf updateNeInfo(EmpContext context, Model4NeInfoIf ne_info) throws EmpException {
		@SuppressWarnings("unused")
		Model4Ne ne = worker4Ne.queryNe(context, ne_info.getNe_id());

		Trigger4NeInfoIf trigger = getTrigger(ne_info.getNe_info_def());
		if (trigger != null) {
			Model4NeSessionRequestIf[] requests = trigger.preUpdateNeInfo(context, ne_info);
			if (requests != null && 0 < requests.length) {
				Model4NeSessionResponseIf[] responses = worker4Ne.executeNe(context, ne_info.getNe_id(), requests);
				ne_info = trigger.postUpdateNeInfo(context, ne_info, responses);
			}
		}

		return ne_info;
	}

	@Override
	public Model4NeInfoIf[] syncListNeInfo(EmpContext context, int ne_id, EMP_MODEL_NE_INFO ne_info_def, Model4NeInfoIf[] ne_infos) throws EmpException {
		for (Model4NeInfoIf ne_info : ne_infos) {
			if (ne_info.getNe_info_index() == 0) {
				ne_info.setNe_info_index(dao4NeInfo.queryNeInfoIndex(context, ne_info.getNe_info_index_values()).getNe_info_index());
			}
		}
		if (ne_info_def.isMonitoring()) {
			ne_infos = dao4NeInfo.syncListNeInfo(context, ne_id, ne_info_def, ne_infos);
		}
		return ne_infos;
	}

	protected void handleNotification(EmpContext context, Model4NeSessionIf ne_session, Model4NeSessionNotificationIf notification) throws EmpException {
		EMP_MODEL_NE_INFO ne_info_def = EMP_MODEL.current().getNe_info_by_notifiction(notification.getNotification_oid().toString());
		if (ne_info_def != null && ne_info_def.isFault_enable()) {
			Model4Ne ne = worker4Ne.queryNe(context, ne_session.getNe_id());

			Trigger4NeInfoIf trigger = getTrigger(ne_info_def);
			if (trigger != null) {
				Model4NeInfoIf ne_info = trigger.postNeNotification(context, ne.getNe_id(), ne_info_def, notification);
				if (ne_info != null) {
					if (ne_info.getNe_info_index() == 0) {
						ne_info.setNe_info_index(dao4NeInfo.queryNeInfoIndex(context, ne_info.getNe_info_index_values()).getNe_info_index());
					}

					if (ne_info_def.isMonitoring()) {
						try {
							Model4NeInfoIf db_ne_info = dao4NeInfo.queryNeInfo(context, ne_info.getNe_id(), ne_info_def, ne_info.getNe_info_index());
							if (db_ne_info != null) {
								ne_info.replaceNull(db_ne_info);
								dao4NeInfo.syncNeInfo(context, ne_session.getNe_id(), ne_info_def, ne_info);
							}
						} catch (EmpException e) {
						}
					}

					Model4Event[] event_logs = trigger.eventListNeNotification(context, ne.getNe_id(), ne_info_def, ne_info, notification);
					if (event_logs != null && 0 < event_logs.length) {
						this.notifyEvents(context, event_logs);

						if (blackBox.isEnabledFor(LEVEL.Site)) {
							StringBuilder event_string = new StringBuilder();
							for (Model4Event event_log : event_logs) {
								event_string.append(event_log).append("\n");
							}
							blackBox.log(LEVEL.Site, context, UtilString.format("Ok Event notification {} -> {} -> {}", event_logs.length, notification, ne_info, event_string));
						}
					} else {
						if (blackBox.isEnabledFor(LEVEL.Site)) {
							blackBox.log(LEVEL.Site, context, UtilString.format("No Event notification {} -> {}", notification, ne_info));
						}
					}
				} else {
					if (blackBox.isEnabledFor(LEVEL.Site)) {
						blackBox.log(LEVEL.Site, context, UtilString.format("Null NeInfo notification {}", notification));
					}
				}
			} else {
				if (blackBox.isEnabledFor(LEVEL.Site)) {
					blackBox.log(LEVEL.Site, context, UtilString.format("No Trigger notification {}", notification));
				}
			}
		} else {
			if (blackBox.isEnabledFor(LEVEL.Site)) {
				blackBox.log(LEVEL.Site, context, UtilString.format("Discard notification {}", notification));
			}
		}
	}

	@Override
	public Model4NeStatisticsIf[] createListNeStatistics(EmpContext context, EMP_MODEL_NE_INFO ne_info_def, STATISTICS_TYPE statistics_type, Model4NeStatisticsIf[] ne_statisticss) throws EmpException {
		return dao4NeStatistics.createListNeStatistics(context, ne_info_def, statistics_type, ne_statisticss);
	}

	@Override
	public int syncListNeStatistics(EmpContext context, EMP_MODEL_NE_INFO ne_info_def, STATISTICS_TYPE from_type, STATISTICS_TYPE to_type, Date collect_time) throws EmpException {
		return dao4NeStatistics.syncListNeStatistics(context, ne_info_def, from_type, to_type, collect_time);
	}

	@Override
	public Model4NeStatisticsIf[] readListNeStatistics(EmpContext context, int ne_id, EMP_MODEL_NE_INFO ne_info_def, Date collect_time) throws EmpException {
		if (ne_info_def.getNe_statistics().length == 0) {
			return new Model4NeStatisticsIf[0];
		}

		@SuppressWarnings("unused")
		Model4Ne ne = worker4Ne.queryNe(context, ne_id);
		Model4NeStatisticsIf[] ne_statisticss = {};

		Trigger4NeInfoIf trigger = getTrigger(ne_info_def);
		if (trigger != null) {
			Model4NeSessionRequestIf[] requests = trigger.preReadListNeInfo(context, ne_id, ne_info_def);
			if (requests != null && 0 < requests.length) {
				Model4NeSessionResponseIf[] responses = worker4Ne.executeNe(context, ne_id, requests);
				Model4NeInfoIf[] ne_infos = trigger.postReadListNeInfo(context, ne_id, ne_info_def, responses);

				if (ne_infos != null && 0 < ne_infos.length) {
					for (Model4NeInfoIf ne_info : ne_infos) {
						if (ne_info.getNe_info_index() == 0) {
							ne_info.setNe_info_index(dao4NeInfo.queryNeInfoIndex(context, ne_info.getNe_statistics_index_values()).getNe_info_index());
						}
					}

					Date prev_time = null;
					switch (ne_info_def.getStat_type()) {
					case MINUTE_5:
						prev_time = UtilDate.add(collect_time, Calendar.MINUTE, -5);
						break;
					case MINUTE_15:
						prev_time = UtilDate.add(collect_time, Calendar.MINUTE, -15);
						break;
					case MINUTE_30:
						prev_time = UtilDate.add(collect_time, Calendar.MINUTE, -30);
						break;
					default:
						break;
					}
					Model4NeStatisticsIf[] last_ne_statisticss = prev_time == null ? new Model4NeStatisticsIf[0] : dao4NeStatistics.queryListNeStatistics(context, ne_id, ne_info_def, ne_info_def.getStat_type(), prev_time);
					ne_statisticss = trigger.postReadListNeStatistics(context, ne_id, ne_info_def, last_ne_statisticss, collect_time, ne_infos);
					if (ne_statisticss != null && 0 < ne_statisticss.length) {
						dao4NeStatistics.createListNeStatistics(context, ne_info_def, ne_info_def.getStat_type(), ne_statisticss);

						if (ne_info_def.isThr_enable()) {
							Model4NeThresholdIf ne_threshold = dao4NeThreshold.queryNeThreshold(context, ne_id, ne_info_def);
							if (ne_threshold != null && 0 < ne_info_def.getNe_thresholds().length) {
								Model4Event[] ne_statistics_events = trigger.eventListNeStatistics(context, ne_id, ne_info_def, ne_statisticss, ne_threshold);
								if (ne_statistics_events != null && 0 < ne_statistics_events.length) {
									this.notifyEvents(context, ne_statistics_events);
								}
							}
						}
					}
				}
			}
		}

		return ne_statisticss;
	}

	@Override
	public NE_INFO_INDEX[] queryListNeStatisticsIndex(EmpContext context, int ne_id, EMP_MODEL_NE_INFO ne_info_def, STATISTICS_TYPE statistics_type, Date fromTime, Date toTime) throws EmpException {
		int[] indexs = dao4NeStatistics.queryListNeStatisticsIndex(context, ne_id, ne_info_def, statistics_type, fromTime, toTime);
		NE_INFO_INDEX[] ne_info_indexs = new NE_INFO_INDEX[indexs.length];
		for (int i = 0; i < ne_info_indexs.length; i++) {
			ne_info_indexs[i] = dao4NeInfo.queryNeInfoIndex(context, indexs[i]);
		}
		return ne_info_indexs;
	}

	@Override
	public Model4NeStatisticsIf[] queryListNeStatistics(EmpContext context, int ne_id, EMP_MODEL_NE_INFO ne_info_def, STATISTICS_TYPE statistics_type, Date fromTime, Date toTime) throws EmpException {
		return setLocation_display(context, ne_info_def, dao4NeStatistics.queryListNeStatistics(context, ne_id, ne_info_def, statistics_type, fromTime, toTime));
	}

	@Override
	public Model4NeStatisticsIf[] queryListNeStatistics(EmpContext context, int ne_id, EMP_MODEL_NE_INFO ne_info_def, int ne_info_index, STATISTICS_TYPE statistics_type, Date fromTime, Date toTime) throws EmpException {
		return setLocation_display(context, ne_info_def, dao4NeStatistics.queryListNeStatistics(context, ne_id, ne_info_def, ne_info_index, statistics_type, fromTime, toTime));
	}

	protected Model4NeStatisticsIf[] setLocation_display(EmpContext context, EMP_MODEL_NE_INFO ne_info_def, Model4NeStatisticsIf[] ne_statisticss) throws EmpException {
		for (Model4NeStatisticsIf aaa : ne_statisticss) {
			NE_INFO_INDEX ne_info_index = dao4NeInfo.queryNeInfoIndex(context, aaa.getNe_info_index());
			((Model4NeStatistics) aaa).setLocation_display(ne_info_index.toString(ne_info_def));
		}
		return ne_statisticss;
	}

	@Override
	public Model4NeThresholdIf queryNeThreshold(EmpContext context, int ne_id, EMP_MODEL_NE_INFO ne_info_def) throws EmpException {
		return dao4NeThreshold.queryNeThreshold(context, ne_id, ne_info_def);
	}

	@Override
	public Model4NeThresholdIf updateNeThreshold(EmpContext context, Model4NeThresholdIf ne_threshold, EMP_MODEL_NE_INFO_FIELD ne_info_field_def) throws EmpException {
		return dao4NeThreshold.updateNeThreshold(context, ne_threshold, ne_info_field_def);
	}

	@Override
	public Model4NeThresholdIf[] copyListNeThreshold(EmpContext context, int ne_id_source, EMP_MODEL_NE_INFO ne_info_def, int[] ne_id_targets) throws EmpException {
		return dao4NeThreshold.copyListNeThreshold(context, ne_id_source, ne_info_def, ne_id_targets);
	}

	@Override
	public String[][] prepareListPartition(EmpContext context, Date collect_time) throws EmpException {
		return dao4NeStatistics.prepareListPartition(context, collect_time);
	}

	@Override
	public void truncate(EmpContext context) throws EmpException {
		dao4NeThreshold.truncate(context);
		dao4NeStatistics.truncate(context);
		dao4NeInfo.truncate(context);
		UtilCache.removeAll();
	}

	protected void notifyEvents(EmpContext context, Model4Event[] events) throws EmpException {
		worker4Event.notifyEvents(context, events);
	}

}
