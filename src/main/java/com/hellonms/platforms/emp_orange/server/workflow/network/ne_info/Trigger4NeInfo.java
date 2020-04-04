/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.server.workflow.network.ne_info;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hellonms.platforms.emp_core.server.log.BlackBox;
import com.hellonms.platforms.emp_core.server.log.LEVEL;
import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.server.workflow.WorkflowMap;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_orange.server.util.UtilInternalError;
import com.hellonms.platforms.emp_orange.server.util.UtilNeSessionSNMP;
import com.hellonms.platforms.emp_orange.server.workflow.fault.event.Worker4EventIf;
import com.hellonms.platforms.emp_orange.server.workflow.network.ne.Worker4NeIf;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_EVENT;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO_FIELD;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO_FIELD.STATISTICS_SAVE;
import com.hellonms.platforms.emp_orange.share.model.fault.event.GEN_TYPE;
import com.hellonms.platforms.emp_orange.share.model.fault.event.Model4Alarm;
import com.hellonms.platforms.emp_orange.share.model.fault.event.Model4Event;
import com.hellonms.platforms.emp_orange.share.model.fault.event.SEVERITY;
import com.hellonms.platforms.emp_orange.share.model.network.ne_info.Model4NeInfo;
import com.hellonms.platforms.emp_orange.share.model.network.ne_info.Model4NeInfoIf;
import com.hellonms.platforms.emp_orange.share.model.network.ne_info.Model4NeStatistics;
import com.hellonms.platforms.emp_orange.share.model.network.ne_info.Model4NeStatisticsIf;
import com.hellonms.platforms.emp_orange.share.model.network.ne_info.Model4NeThresholdIf;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.Model4NeSessionNotificationIf;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.Model4NeSessionRequestIf;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.Model4NeSessionResponseIf;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.NE_SESSION_PROTOCOL_ORANGE;
import com.hellonms.platforms.emp_util.string.UtilString;

/**
 * <p>
 * NE 정보 Trigger
 * </p>
 *
 * @since 1.6
 * @create 2015. 4. 15.
 * @modified 2015. 4. 15.
 * @author cchyun
 *
 */
public class Trigger4NeInfo implements Trigger4NeInfoIf {

	protected Worker4NeIf worker4Ne;

	protected Worker4NeInfoIf worker4NeInfo;

	protected Worker4EventIf worker4Event;

	private static final BlackBox blackBox = new BlackBox(Trigger4NeInfo.class);

	@Override
	public void initialize(EmpContext context) throws EmpException {
		worker4Ne = (Worker4NeIf) WorkflowMap.getWorker(Worker4NeIf.class);
		worker4NeInfo = (Worker4NeInfoIf) WorkflowMap.getWorker(Worker4NeInfoIf.class);
		worker4Event = (Worker4EventIf) WorkflowMap.getWorker(Worker4EventIf.class);
	}

	@Override
	public void dispose(EmpContext context) throws EmpException {
	}

	@Override
	public EMP_MODEL_NE_INFO getNe_info_code() {
		return null;
	}

	@Override
	public Model4NeSessionRequestIf[] preReadListNeInfo(EmpContext context, int ne_id, EMP_MODEL_NE_INFO ne_info_def) throws EmpException {
		if (ne_info_def.getProtocol().equals(NE_SESSION_PROTOCOL_ORANGE.SNMP)) {
			return UtilNeSessionSNMP.toNeSessionRequests4ReadList(ne_info_def);
		} else {
			return null;
		}
	}

	@Override
	public Model4NeInfoIf[] postReadListNeInfo(EmpContext context, int ne_id, EMP_MODEL_NE_INFO ne_info_def, Model4NeSessionResponseIf[] responses) throws EmpException {
		if (ne_info_def.getProtocol().equals(NE_SESSION_PROTOCOL_ORANGE.SNMP)) {
			Date collect_time = new Date();
			List<Model4NeInfoIf> ne_info_list = new ArrayList<Model4NeInfoIf>();
			Model4NeInfo[] ne_infos = UtilNeSessionSNMP.toNeInfos(ne_info_def, responses);
			for (Model4NeInfo ne_info : ne_infos) {
				ne_info.setNe_id(ne_id);
				ne_info.setCollect_time(collect_time);

				for (EMP_MODEL_NE_INFO_FIELD ne_info_field_def : ne_info_def.getNe_info_virtual_field()) {
					try {
						Serializable value = ne_info_field_def.evalField_script(ne_info.getMap());
						ne_info.setField_value(ne_info_field_def, value);
					} catch (Exception e) {
						if (blackBox.isEnabledFor(LEVEL.Fatal)) {
							blackBox.log(LEVEL.Fatal, context, e);
						}
						UtilInternalError.notifyInternalError(context, ne_id, "virtual_field_script", UtilString.format("{} virtual_field_script fail {}.{}", ne_info_def.getName(), ne_info_field_def.getName(), e.getMessage()));
					}
				}

				try {
					boolean filter = ne_info_def.isFilter_enable() ? ne_info_def.evalFilter_script(ne_info.getMap()) : true;
					if (filter) {
						ne_info_list.add(ne_info);
					}
				} catch (Exception e) {
					if (blackBox.isEnabledFor(LEVEL.Fatal)) {
						blackBox.log(LEVEL.Fatal, context, e);
					}
					UtilInternalError.notifyInternalError(context, ne_id, "filter_script", UtilString.format("{} filter_script fail {}", ne_info_def.getName(), e.getMessage()));
				}
			}
			return ne_info_list.toArray(new Model4NeInfoIf[0]);
		} else {
			return null;
		}
	}

	@Override
	public Model4Event[] eventListNeInfo(EmpContext context, int ne_id, EMP_MODEL_NE_INFO ne_info_def, Model4NeInfoIf[] ne_infos) throws EmpException {
		List<Model4Event> event_list = new ArrayList<Model4Event>();

		for (Model4NeInfoIf ne_info : ne_infos) {
			try {
				if (ne_info_def.isFault_enable()) {
					Model4Event[] events = ne_info_def.evalEvent_script(ne_info.getMap());
					if (events != null) {
						for (Model4Event event : events) {
							event.setNe_id(ne_info.getNe_id());
							event.setNe_info_index(worker4NeInfo.queryNeInfoIndex(context, event.getLocation_display()).getNe_info_index());
							event.setGen_type(GEN_TYPE.SERVICE);
							event_list.add(event);
						}
					}
				}
			} catch (Exception e) {
				if (blackBox.isEnabledFor(LEVEL.Fatal)) {
					blackBox.log(LEVEL.Fatal, context, e);
				}
				UtilInternalError.notifyInternalError(context, ne_id, "event_script", UtilString.format("{} event_script fail {}", ne_info_def.getName(), e.getMessage()));
			}
		}
		if (ne_info_def.isAudit_alarm()) {
			syncListAlarmActive(context, ne_id, ne_info_def, ne_infos, event_list);
		}
		return event_list.toArray(new Model4Event[0]);
	}

	protected void syncListAlarmActive(EmpContext context, int ne_id, EMP_MODEL_NE_INFO ne_info_def, Model4NeInfoIf[] ne_infos, List<Model4Event> event_list) throws EmpException {
		Map<String, Model4Alarm> ne_alarm_map = new HashMap<String, Model4Alarm>();
		Model4Alarm[] alarms = worker4Event.queryListAlarmActiveByNe(context, ne_id, ne_info_def, null, null, 0, 999999);
		for (Model4Alarm alarm : alarms) {
			EMP_MODEL_EVENT event_def = EMP_MODEL.current().getEvent(alarm.getEvent_code());
			if (event_def != null && event_def.isAudit_alarm() && alarm.getNe_info_code() == ne_info_def.getCode()) {
				String key = new StringBuilder().append(alarm.getNe_id()).append(".").append(alarm.getNe_info_code()).append(".").append(alarm.getNe_info_index()).append(".").append(alarm.getEvent_code()).toString();
				ne_alarm_map.put(key, alarm);
			} else if (event_def == null) {
				Model4Event event = new Model4Event();
				event.setNe_id(alarm.getNe_id());
				event.setNe_info_code(alarm.getNe_info_code());
				event.setNe_info_index(alarm.getNe_info_index());
				event.setLocation_display(alarm.getLocation_display());
				event.setEvent_code(alarm.getEvent_code());
				event.setSeverity(SEVERITY.CLEAR);
				event.setGen_time(new Date());
				event.setGen_type(GEN_TYPE.SERVICE);
				event.setDescription(UtilString.format("Unknwon event_code={}", alarm.getEvent_code()));
				event_list.add(event);
			}
		}
		for (Model4Event event : event_list) {
			if (event.isAlarm()) {
				String key = new StringBuilder().append(event.getNe_id()).append(".").append(event.getNe_info_code()).append(".").append(event.getNe_info_index()).append(".").append(event.getEvent_code()).toString();
				ne_alarm_map.remove(key);
			}
		}
		for (Model4Alarm alarm : ne_alarm_map.values()) {
			Model4Event event = new Model4Event();
			event.setNe_id(alarm.getNe_id());
			event.setNe_info_code(alarm.getNe_info_code());
			event.setNe_info_index(alarm.getNe_info_index());
			event.setLocation_display(alarm.getLocation_display());
			event.setEvent_code(alarm.getEvent_code());
			event.setSeverity(SEVERITY.CLEAR);
			event.setGen_time(new Date());
			event.setGen_type(GEN_TYPE.SERVICE);
			event.setDescription("Audit Clear!!");
			event_list.add(event);
		}
	}

	@Override
	public Model4NeStatisticsIf[] postReadListNeStatistics(EmpContext context, int ne_id, EMP_MODEL_NE_INFO ne_info_def, Model4NeStatisticsIf[] last_ne_statisticss, Date collect_time, Model4NeInfoIf[] ne_infos) throws EmpException {
		if (ne_info_def.getProtocol().equals(NE_SESSION_PROTOCOL_ORANGE.SNMP)) {
			Map<Integer, Model4NeStatisticsIf> last_ne_statistics_map = new HashMap<Integer, Model4NeStatisticsIf>();
			int peroid_timeout = ne_info_def.getStat_type().peroid_seconds / 4;
			for (Model4NeStatisticsIf last_ne_statistics : last_ne_statisticss) {
				Date last_collect_time = last_ne_statistics.getCollect_time();
				int peroid_diff = (int) (collect_time.getTime() - last_collect_time.getTime()) / 1000;
				if (Math.abs(ne_info_def.getStat_type().peroid_seconds - peroid_diff) < peroid_timeout) {
					last_ne_statistics_map.put(last_ne_statistics.getNe_info_index(), last_ne_statistics);
				}
			}

			List<Model4NeStatistics> ne_statistics_list = new ArrayList<Model4NeStatistics>();
			for (Model4NeInfoIf ne_info : ne_infos) {
				Model4NeStatistics ne_statistics = EMP_MODEL.current().newInstanceNe_statistics(ne_info_def.getCode());
				ne_statistics.setNe_id(ne_id);
				ne_statistics.setNe_info_index(ne_info.getNe_info_index());
				ne_statistics.setCollect_time(collect_time);
				ne_statistics.setLocation_display(ne_info.getNe_statistics_location_display());

				for (EMP_MODEL_NE_INFO_FIELD ne_info_field_def : ne_info_def.getNe_statistics()) {
					try {
						Serializable ne_info_field_value = ne_info.getField_value(ne_info_field_def);
						if (ne_info_field_value != null && ne_info_field_value instanceof Number) {
							if (ne_info_field_def.getStat_save() == STATISTICS_SAVE.RAW) {
								ne_statistics.setField_value(ne_info_field_def, ((Number) ne_info_field_value).longValue());
								ne_statistics.setField_counter(ne_info_field_def, ((Number) ne_info_field_value).longValue());
							} else if (ne_info_field_def.getStat_save() == STATISTICS_SAVE.COUNTER) {
								Model4NeStatisticsIf last_ne_statistics = last_ne_statistics_map.get(ne_statistics.getNe_info_index());
								ne_statistics.setField_counter(ne_info_field_def, ((Number) ne_info_field_value).longValue());
								if (last_ne_statistics != null) {
									Number field_counter = ne_statistics.getField_counter(ne_info_field_def);
									Number last_field_counter = last_ne_statistics.getField_counter(ne_info_field_def);
									if (last_field_counter != null && field_counter != null && last_field_counter.longValue() <= field_counter.longValue()) {
										ne_statistics.setField_value(ne_info_field_def, field_counter.longValue() - last_field_counter.longValue());
									} else {
										ne_statistics.setField_value(ne_info_field_def, null);
									}
								} else {
									ne_statistics.setField_value(ne_info_field_def, null);
								}
							}
						}
					} catch (Exception e) {
						if (blackBox.isEnabledFor(LEVEL.Fatal)) {
							blackBox.log(LEVEL.Fatal, context, e);
						}
						UtilInternalError.notifyInternalError(context, ne_id, "NE_STATISTICS_FIELD", UtilString.format("NE_STATISTICS_FIELD fail {}[{}] error {}", ne_info_def.getName(), ne_info_field_def.getName(), e.getCause() == null ? e.getMessage() : e.getCause().getMessage()));
					}
				}
				ne_statistics_list.add(ne_statistics);
			}

			return ne_statistics_list.toArray(new Model4NeStatisticsIf[0]);
		} else {
			return new Model4NeStatisticsIf[0];
		}
	}

	@Override
	public Model4Event[] eventListNeStatistics(EmpContext context, int ne_id, EMP_MODEL_NE_INFO ne_info_def, Model4NeStatisticsIf[] ne_statisticss, Model4NeThresholdIf ne_threshold) throws EmpException {
		List<Model4Event> event_list = new ArrayList<Model4Event>();
		for (Model4NeStatisticsIf ne_statistics : ne_statisticss) {
			for (EMP_MODEL_NE_INFO_FIELD ne_info_field_def : ne_threshold.getField_defs()) {
				Long ne_info_field_value = ne_statistics.getField_value(ne_info_field_def);

				int event_code = ne_info_field_def.getThr_event_code();
				SEVERITY severity = ne_threshold.getSeverity(ne_info_field_def, ne_info_field_value);
				String description = ne_threshold.getDescription(ne_info_field_def, ne_info_field_value);

				Model4Event event = new Model4Event();
				event.setNe_id(ne_id);
				event.setNe_info_code(ne_statistics.getNe_info_code());
				event.setNe_info_index(ne_statistics.getNe_info_index());
				event.setLocation_display(ne_statistics.getLocation_display());
				event.setEvent_code(event_code);
				event.setSeverity(severity);
				event.setGen_time(new Date());
				event.setGen_type(GEN_TYPE.SERVICE);
				event.setDescription(description);
				event_list.add(event);
			}
		}

		return event_list.toArray(new Model4Event[0]);
	}

	@Override
	public Model4NeSessionRequestIf[] preUpdateNeInfo(EmpContext context, Model4NeInfoIf ne_info) throws EmpException {
		if (ne_info.getNe_info_def().getProtocol().equals(NE_SESSION_PROTOCOL_ORANGE.SNMP)) {
			return UtilNeSessionSNMP.toNeSessionRequests4Update(ne_info);
		} else {
			return null;
		}
	}

	@Override
	public Model4NeInfoIf postUpdateNeInfo(EmpContext context, Model4NeInfoIf ne_info, Model4NeSessionResponseIf[] responses) throws EmpException {
		EMP_MODEL_NE_INFO ne_info_def = ne_info.getNe_info_def();
		if (ne_info_def.getProtocol().equals(NE_SESSION_PROTOCOL_ORANGE.SNMP)) {
			Date collect_time = new Date();

			Model4NeInfo[] new_ne_infos = UtilNeSessionSNMP.toNeInfos(ne_info_def, responses);
			for (Model4NeInfo new_ne_info : new_ne_infos) {
				new_ne_info.setNe_id(ne_info.getNe_id());
				new_ne_info.setNe_info_index(ne_info.getNe_info_index());
				new_ne_info.setCollect_time(collect_time);
				new_ne_info.replaceNull(ne_info);
			}
			return new_ne_infos.length == 1 ? new_ne_infos[0] : null;
		} else {
			return null;
		}
	}

	@Override
	public Model4NeInfoIf postNeNotification(EmpContext context, int ne_id, EMP_MODEL_NE_INFO ne_info_def, Model4NeSessionNotificationIf notification) {
		if (ne_info_def.getProtocol().equals(NE_SESSION_PROTOCOL_ORANGE.SNMP)) {
			Date collect_time = new Date();
			Model4NeInfo ne_info = UtilNeSessionSNMP.toNeInfo(ne_info_def, notification);
			ne_info.setNe_id(ne_id);
			ne_info.setCollect_time(collect_time);
			return ne_info;
		} else {
			return null;
		}
	}

	@Override
	public Model4Event[] eventListNeNotification(EmpContext context, int ne_id, EMP_MODEL_NE_INFO ne_info_def, Model4NeInfoIf ne_info, Model4NeSessionNotificationIf notification) throws EmpException {
		List<Model4Event> event_list = new ArrayList<Model4Event>();
		try {
			if (ne_info_def.isFault_enable()) {
				Model4Event[] events = ne_info_def.evalScript_notification(notification.getNotification_oid().toString(), ne_info.getMap());
				if (events != null) {
					if (events != null) {
						for (Model4Event event : events) {
							event.setNe_id(ne_info.getNe_id());
							event.setNe_info_index(worker4NeInfo.queryNeInfoIndex(context, event.getLocation_display()).getNe_info_index());
							event.setGen_type(GEN_TYPE.AUTO);
							event_list.add(event);
						}
					}
				}
			}
		} catch (Exception e) {
			if (blackBox.isEnabledFor(LEVEL.Fatal)) {
				blackBox.log(LEVEL.Fatal, context, e);
			}
			UtilInternalError.notifyInternalError(context, ne_id, "notification_script", UtilString.format("{} notification_script fail {}", ne_info_def.getName(), e.getCause() == null ? e.getMessage() : e.getCause().getMessage()));
		}
		return event_list.toArray(new Model4Event[0]);
	}

}
