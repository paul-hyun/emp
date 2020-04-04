package com.hellonms.platforms.emp_orange.share.model.emp_model;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.locks.ReentrantLock;

import javax.script.Invocable;
import javax.script.ScriptEngine;

import org.dom4j.Element;

import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_onion.share.model.STATISTICS_TYPE;
import com.hellonms.platforms.emp_orange.share.error.ERROR_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.fault.event.Model4Event;
import com.hellonms.platforms.emp_orange.share.model.fault.event.SEVERITY;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.NE_SESSION_PROTOCOL;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.NE_SESSION_PROTOCOL_ORANGE;
import com.hellonms.platforms.emp_util.date.UtilDate;
import com.hellonms.platforms.emp_util.dynamic_code.UtilJavaScript;
import com.hellonms.platforms.emp_util.string.UtilString;

public final class EMP_MODEL_NE_INFO {

	public static final String getScript_template(int code) {
		StringBuilder event_script = new StringBuilder();
		event_script.append("event['severity'] = 'critical';").append("\n");
		event_script.append("event['event_code'] = '';").append("\n");
		event_script.append("event['location'] = 'location';").append("\n");
		event_script.append("event['gen_time'] = new java.util.Date();").append("\n");
		event_script.append("event['description'] = 'description';").append("\n");
		return event_script.toString();
	}

	private final EMP_MODEL emp_model;
	private boolean editable;

	private int code;

	private String name;
	private String display_name;
	private boolean display_enable;
	private NE_SESSION_PROTOCOL protocol;
	private boolean monitoring;

	private boolean filter_enable;
	private String filter_script;
	private ScriptEngine filter_script_engine;

	private boolean fault_enable;
	private boolean audit_alarm;
	private String event_script;
	private ScriptEngine event_script_engine;
	private final LinkedHashMap<String, String> notification_map = new LinkedHashMap<String, String>();
	private final LinkedHashMap<String, ScriptEngine> notification_engine_map = new LinkedHashMap<String, ScriptEngine>();

	private boolean stat_enable;
	private STATISTICS_TYPE stat_type;
	private Boolean thr_enable;

	private final Map<String, EMP_MODEL_NE_INFO_FIELD> ne_info_field_map = new LinkedHashMap<String, EMP_MODEL_NE_INFO_FIELD>();

	private EMP_MODEL_NE_INFO_FIELD[] ne_info_fields;

	private EMP_MODEL_NE_INFO_FIELD[] ne_info_indexs;

	private Boolean table;

	private EMP_MODEL_NE_INFO_FIELD[] ne_info_virtual_fields;

	private EMP_MODEL_NE_INFO_FIELD[] ne_statistics;

	private EMP_MODEL_NE_INFO_FIELD[] ne_thresholds;

	ReentrantLock script_lock = new ReentrantLock();

	EMP_MODEL_NE_INFO(EMP_MODEL emp_model, Element element, Map<Integer, EMP_MODEL_ENUM> enum_map, boolean editable) {
		this.emp_model = emp_model;
		this.editable = editable;
		this.code = Integer.parseInt(element.attributeValue("code", ""));
		this.name = element.attributeValue("name", "");
		this.display_name = element.attributeValue("display_name", "");
		this.display_enable = Boolean.parseBoolean(element.attributeValue("display_enable", "false"));
		this.protocol = NE_SESSION_PROTOCOL_ORANGE.SNMP;
		this.monitoring = Boolean.parseBoolean(element.attributeValue("monitoring", "false"));

		this.filter_enable = Boolean.parseBoolean(element.attributeValue("filter_enable", "false"));
		this.filter_script = element.elementText("filter_script");
		if (UtilString.isEmpty(this.filter_script)) {
			this.filter_script = "";
		}

		this.fault_enable = Boolean.parseBoolean(element.attributeValue("fault_enable", "false"));
		this.audit_alarm = Boolean.parseBoolean(element.attributeValue("audit_alarm", "false"));
		this.event_script = element.elementText("event_script");
		if (UtilString.isEmpty(this.event_script)) {
			this.event_script = "";
		}
		Object[] notification_objs = element.elements("notification").toArray();
		for (int i = 0; i < notification_objs.length; i++) {
			Element notification_obj = (Element) notification_objs[i];
			notification_map.put(notification_obj.attributeValue("oid", ""), notification_obj.getText());
		}

		this.stat_enable = Boolean.parseBoolean(element.attributeValue("stat_enable", "false"));
		String stat_type = element.attributeValue("stat_type", "").trim();
		this.stat_type = stat_type.length() == 0 ? null : STATISTICS_TYPE.valueOf(stat_type);

		Object[] ne_info_field_objects = element.elements(EMP_MODEL.NE_INFO_FIELD).toArray();
		this.ne_info_fields = new EMP_MODEL_NE_INFO_FIELD[ne_info_field_objects.length];
		List<EMP_MODEL_NE_INFO_FIELD> ne_info_indexs = new ArrayList<EMP_MODEL_NE_INFO_FIELD>();
		List<EMP_MODEL_NE_INFO_FIELD> ne_info_virtual_fields = new ArrayList<EMP_MODEL_NE_INFO_FIELD>();
		List<EMP_MODEL_NE_INFO_FIELD> ne_statistics = new ArrayList<EMP_MODEL_NE_INFO_FIELD>();
		List<EMP_MODEL_NE_INFO_FIELD> ne_thresholds = new ArrayList<EMP_MODEL_NE_INFO_FIELD>();
		for (int i = 0; i < ne_info_field_objects.length; i++) {
			EMP_MODEL_NE_INFO_FIELD ne_info_field = new EMP_MODEL_NE_INFO_FIELD(this, (Element) ne_info_field_objects[i], enum_map, editable);
			this.ne_info_fields[i] = ne_info_field;
			this.ne_info_field_map.put(ne_info_field.getName(), ne_info_field);
			if (ne_info_field.isIndex()) {
				ne_info_indexs.add(ne_info_field);
			}
			if (ne_info_field.isVirtual_enable()) {
				ne_info_virtual_fields.add(ne_info_field);
			}
			if (ne_info_field.isStat_enable()) {
				ne_statistics.add(ne_info_field);
			}
			if (ne_info_field.isThr_enable()) {
				ne_thresholds.add(ne_info_field);
			}
		}
		this.ne_info_indexs = ne_info_indexs.toArray(new EMP_MODEL_NE_INFO_FIELD[0]);
		this.ne_info_virtual_fields = ne_info_virtual_fields.toArray(new EMP_MODEL_NE_INFO_FIELD[0]);
		this.ne_statistics = ne_statistics.toArray(new EMP_MODEL_NE_INFO_FIELD[0]);
		this.ne_thresholds = ne_thresholds.toArray(new EMP_MODEL_NE_INFO_FIELD[0]);
	}

	EMP_MODEL_NE_INFO(EMP_MODEL emp_model, int code, boolean editable) {
		this(emp_model, code, "--", "", true, NE_SESSION_PROTOCOL_ORANGE.SNMP, editable);
	}

	EMP_MODEL_NE_INFO(EMP_MODEL emp_model, int code, String name, String display_name, boolean display_enable, NE_SESSION_PROTOCOL protocol, boolean editable) {
		this.emp_model = emp_model;
		this.display_name = display_name;
		this.display_enable = display_enable;
		this.editable = editable;
		this.code = code;
		this.name = name;
		this.protocol = protocol;
		this.monitoring = false;
		this.event_script = "";
		this.stat_type = null;
		this.ne_info_fields = new EMP_MODEL_NE_INFO_FIELD[0];
	}

	public void moveUp() {
		if (editable) {
			emp_model.moveUpNe_info(this);
		} else {
			throw EMP_MODEL.editError();
		}
	}

	public void moveDown() {
		if (editable) {
			emp_model.moveDownNe_info(this);
		} else {
			throw EMP_MODEL.editError();
		}
	}

	public void dispose() {
		if (editable) {
			emp_model.removeNe_info(this);
		} else {
			throw EMP_MODEL.editError();
		}
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		if (editable) {
			this.code = code;
		} else {
			throw EMP_MODEL.editError();
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (editable) {
			this.name = name;
		} else {
			throw EMP_MODEL.editError();
		}
	}

	public String getDisplay_name() {
		return display_name;
	}

	public void setDisplay_name(String display_name) {
		this.display_name = display_name;
	}

	public String getDisplay_label() {
		return UtilString.isEmpty(display_name) ? name : display_name;
	}

	public boolean isDisplay_enable() {
		return display_enable;
	}

	public void setDisplay_enable(boolean display_enable) {
		this.display_enable = display_enable;
	}

	public NE_SESSION_PROTOCOL getProtocol() {
		return protocol;
	}

	public void setProtocol(NE_SESSION_PROTOCOL protocol) {
		if (editable) {
			this.protocol = protocol;
		} else {
			throw EMP_MODEL.editError();
		}
	}

	public boolean isMonitoring() {
		return monitoring;
	}

	public void setMonitoring(boolean monitoring) {
		if (editable) {
			this.monitoring = monitoring;
		} else {
			throw EMP_MODEL.editError();
		}
	}

	public boolean isFilter_enable() {
		return filter_enable;
	}

	public void setFilter_enable(boolean filter_enable) {
		if (editable) {
			this.filter_enable = filter_enable;
		} else {
			throw EMP_MODEL.editError();
		}
	}

	public String getFilter_script() {
		return filter_script;
	}

	public void setFilter_script(String filter_script) {
		if (editable) {
			this.filter_script = filter_script;
		} else {
			throw EMP_MODEL.editError();
		}
	}

	public boolean evalFilter_script(Map<String, Object> model) throws EmpException {
		try {
			if (!filter_enable) {
				return true;
			} else if (!UtilString.isEmpty(filter_script)) {
				script_lock.lock();
				try {
					if (filter_script_engine == null) {
						StringBuilder script = new StringBuilder();
						script.append("var ne_info_def = new Object(); ne_info_def.filter_script = function(model) {\n").append(filter_script).append("\n}");
						filter_script_engine = UtilJavaScript.evalScript(script.toString());
					}
					Invocable inv = (Invocable) filter_script_engine;
					Object ne_info_def = filter_script_engine.get("ne_info_def");
					Object filter = inv.invokeMethod(ne_info_def, "filter_script", model);
					if (filter instanceof Boolean) {
						return (Boolean) filter;
					} else {
						return false;
					}
				} finally {
					script_lock.unlock();
				}
			} else {
				return false;
			}
		} catch (Exception e) {
			throw new EmpException(e, ERROR_CODE_ORANGE.FILE_IO);
		}
	}

	public boolean isFault_enable() {
		return fault_enable;
	}

	public void setFault_enable(boolean fault_enable) {
		if (editable) {
			this.fault_enable = fault_enable;
		} else {
			throw EMP_MODEL.editError();
		}
	}

	public boolean isAudit_alarm() {
		return audit_alarm;
	}

	public void setAudit_alarm(boolean audit_alarm) {
		if (editable) {
			this.audit_alarm = audit_alarm;
		} else {
			throw EMP_MODEL.editError();
		}
	}

	public String getEvent_script() {
		return event_script;
	}

	public void setEvent_script(String event_script) {
		if (editable) {
			this.event_script = event_script;
		} else {
			throw EMP_MODEL.editError();
		}
	}

	public Model4Event[] evalEvent_script(Map<String, Object> model) throws EmpException {
		try {
			if (fault_enable && !UtilString.isEmpty(event_script)) {
				script_lock.lock();
				try {
					if (event_script_engine == null) {
						StringBuilder script = new StringBuilder();
						script.append("var ne_info_def = new Object(); ne_info_def.event_script = function(model, events) {\n").append(event_script).append("\n}");
						event_script_engine = UtilJavaScript.evalScript(script.toString());
					}
					Invocable inv = (Invocable) event_script_engine;
					Object ne_info_def = event_script_engine.get("ne_info_def");
					List<Object> events = new ArrayList<Object>();
					inv.invokeMethod(ne_info_def, "event_script", model, events);

					List<Model4Event> eventResults = new ArrayList<Model4Event>();
					for (Object object : events) {
						if (object instanceof Map<?, ?>) {
							@SuppressWarnings("unchecked")
							Model4Event event = toEvent((Map<String, Object>) object);
							if (event != null) {
								eventResults.add(event);
							}
						}
					}
					return eventResults.toArray(new Model4Event[0]);
				} finally {
					script_lock.unlock();
				}
			}
			return null;
		} catch (Exception e) {
			throw new EmpException(e, ERROR_CODE_ORANGE.FILE_IO);
		}
	}

	public Map<String, String> getNotification_map() {
		return new LinkedHashMap<String, String>(this.notification_map);
	}

	public void setNotification_map(Map<String, String> notification_map) {
		if (editable) {
			this.notification_map.clear();
			this.notification_map.putAll(notification_map);
		} else {
			throw EMP_MODEL.editError();
		}
	}

	public boolean hasNotification(String notification_oid) {
		return notification_map.containsKey(notification_oid);
	}

	public Model4Event[] evalScript_notification(String notification_oid, Map<String, Object> model) throws EmpException {
		try {
			String script_notification = notification_map.get(notification_oid);
			if (fault_enable && !UtilString.isEmpty(script_notification)) {
				script_lock.lock();
				try {
					ScriptEngine script_notification_engine = notification_engine_map.get(notification_oid);
					if (script_notification_engine == null) {
						StringBuilder script = new StringBuilder();
						script.append("var ne_info_def = new Object(); ne_info_def.script_notification = function(model, events) {\n").append(script_notification).append("\n}");
						script_notification_engine = UtilJavaScript.evalScript(script.toString());
						notification_engine_map.put(notification_oid, script_notification_engine);
					}
					Invocable inv = (Invocable) script_notification_engine;
					Object ne_info_def = script_notification_engine.get("ne_info_def");
					List<Object> events = new ArrayList<Object>();
					inv.invokeMethod(ne_info_def, "script_notification", model, events);

					List<Model4Event> eventResults = new ArrayList<Model4Event>();
					for (Object object : events) {
						if (object instanceof Map<?, ?>) {
							@SuppressWarnings("unchecked")
							Model4Event event = toEvent((Map<String, Object>) object);
							if (event != null) {
								eventResults.add(event);
							}
						}
					}
					return eventResults.toArray(new Model4Event[0]);
				} finally {
					script_lock.unlock();
				}
			}
			return null;
		} catch (Exception e) {
			throw new EmpException(e, ERROR_CODE_ORANGE.FILE_IO);
		}
	}

	private Model4Event toEvent(Map<String, Object> event) throws EmpException {
		SEVERITY severity = null;
		Object object_severity = event.get("severity");
		if (object_severity instanceof String) {
			if (SEVERITY.CRITICAL.name().equalsIgnoreCase((String) object_severity)) {
				severity = SEVERITY.CRITICAL;
			} else if (SEVERITY.MAJOR.name().equalsIgnoreCase((String) object_severity)) {
				severity = SEVERITY.MAJOR;
			} else if (SEVERITY.MINOR.name().equalsIgnoreCase((String) object_severity)) {
				severity = SEVERITY.MINOR;
			} else if (SEVERITY.CLEAR.name().equalsIgnoreCase((String) object_severity)) {
				severity = SEVERITY.CLEAR;
			} else if (SEVERITY.INFO.name().equalsIgnoreCase((String) object_severity)) {
				severity = SEVERITY.INFO;
			} else {
				throw new EmpException(ERROR_CODE_ORANGE.FILE_IO, UtilString.format("Unknown severity={}", object_severity));
			}
		}
		if (severity == null) {
			return null;
		}

		EMP_MODEL_EVENT event_def = null;
		Object object_event_code = event.get("event_code");
		if (severity != null && object_event_code instanceof String) {
			event_def = EMP_MODEL.current().getEvent((String) object_event_code);
			if (event_def == null) {
				throw new EmpException(ERROR_CODE_ORANGE.FILE_IO, UtilString.format("Unknown event_code={}", object_event_code));
			}
		}
		if (event_def == null) {
			return null;
		}

		EMP_MODEL_NE_INFO ne_info_def = this;
		Object ne_info_name = event.get("ne_info_name");
		if (event_def != null && ne_info_name instanceof String) {
			ne_info_def = EMP_MODEL.current().getNe_info((String) ne_info_name);
			if (ne_info_def == null) {
				throw new EmpException(ERROR_CODE_ORANGE.FILE_IO, UtilString.format("Unknown ne_info_def={}", ne_info_name));
			}
		}

		String location = null;
		Object object_location = event.get("location");
		if (event_def != null && object_location instanceof String) {
			location = (String) object_location;
			// if (location == null) {
			// throw new EmpException(ERROR_CODE_ORANGE.FILE_IO, UtilString.format("Unknown location={}", object_location));
			// }
		}
		if (location == null) {
			return null;
		}

		Date gen_time = null;
		Object object_gen_time = event.get("gen_time");
		if (object_gen_time instanceof Date) {
			gen_time = (Date) object_gen_time;
		} else if (object_gen_time instanceof Long) {
			gen_time = new Date((Long) object_gen_time);
		} else {
			StringBuilder datetime = new StringBuilder();
			for (char c : String.valueOf(object_gen_time).toCharArray()) {
				if (Character.isDigit(c)) {
					datetime.append(c);
				}
			}
			if (datetime.length() == 17) {
				try {
					gen_time = UtilDate.parse(UtilDate.MILLI_FORMAT_TRIM, datetime.toString());
				} catch (Exception e) {
					if (gen_time == null) {
						throw new EmpException(e, ERROR_CODE_ORANGE.FILE_IO, UtilString.format("Unknown gen_time={}", object_gen_time));
					}
				}
			}
		}
		if (gen_time == null) {
			return null;
		}

		String description = null;
		Object object_description = event.get("description");
		if (object_description instanceof String) {
			description = (String) object_description;
		}
		if (description == null) {
			return null;
		}

		Model4Event model = new Model4Event();
		model.setSeverity(severity);
		model.setEvent_code(event_def.getCode());
		model.setNe_info_code(ne_info_def.code);
		model.setLocation_display(location);
		model.setGen_time(gen_time);
		model.setDescription(description);
		return model;
	}

	public boolean isStat_enable() {
		return stat_enable;
	}

	public void setStat_enable(boolean stat_enable) {
		if (editable) {
			this.stat_enable = stat_enable;
		} else {
			throw EMP_MODEL.editError();
		}
	}

	public boolean isThr_enable() {
		if (editable) {
			if (isStat_enable()) {
				for (EMP_MODEL_NE_INFO_FIELD ne_info_field : ne_info_fields) {
					if (ne_info_field.isThr_enable()) {
						return true;
					}
				}
			}
			return false;
		} else {
			if (thr_enable == null) {
				thr_enable = false;
				if (isStat_enable()) {
					for (EMP_MODEL_NE_INFO_FIELD ne_info_field : ne_info_fields) {
						if (ne_info_field.isThr_enable()) {
							thr_enable = true;
							break;
						}
					}
				}
			}
			return thr_enable;
		}
	}

	public STATISTICS_TYPE getStat_type() {
		return stat_type;
	}

	public void setStat_type(STATISTICS_TYPE stat_type) {
		if (editable) {
			this.stat_type = stat_type;
		} else {
			throw EMP_MODEL.editError();
		}
	}

	public EMP_MODEL_NE_INFO_FIELD addNe_info_field() {
		if (editable) {
			List<EMP_MODEL_NE_INFO_FIELD> ne_info_field_list = new ArrayList<EMP_MODEL_NE_INFO_FIELD>();
			for (EMP_MODEL_NE_INFO_FIELD aaa : ne_info_fields) {
				ne_info_field_list.add(aaa);
			}
			EMP_MODEL_NE_INFO_FIELD ne_info_field = new EMP_MODEL_NE_INFO_FIELD(this, editable);
			ne_info_field_list.add(ne_info_field);

			this.ne_info_fields = ne_info_field_list.toArray(new EMP_MODEL_NE_INFO_FIELD[0]);

			return ne_info_field;
		} else {
			throw EMP_MODEL.editError();
		}
	}

	public EMP_MODEL_NE_INFO_FIELD copyNe_info_field(EMP_MODEL_NE_INFO_FIELD info_field) {
		if (editable) {
			List<EMP_MODEL_NE_INFO_FIELD> ne_info_field_list = new ArrayList<EMP_MODEL_NE_INFO_FIELD>();
			for (EMP_MODEL_NE_INFO_FIELD aaa : ne_info_fields) {
				ne_info_field_list.add(aaa);
			}
			EMP_MODEL_NE_INFO_FIELD new_info_field = new EMP_MODEL_NE_INFO_FIELD(this, editable);
			new_info_field.setEnum_code(info_field.getEnum_code());
			new_info_field.setField_script(info_field.getField_script());
			new_info_field.setIndex(info_field.isIndex());
			new_info_field.setName(UtilString.format("{}_copy", info_field.getName()));
			new_info_field.setOid(info_field.getOid());
			new_info_field.setRead(info_field.isRead());
			new_info_field.setStat_aggr(info_field.getStat_aggr());
			new_info_field.setStat_enable(info_field.isStat_enable());
			new_info_field.setStat_label(info_field.isStat_label());
			new_info_field.setStat_save(info_field.getStat_save());
			new_info_field.setThr_critical_max(info_field.getThr_critical_max());
			new_info_field.setThr_critical_min(info_field.getThr_critical_min());
			new_info_field.setThr_enable(info_field.isThr_enable());
			new_info_field.setThr_event_code(info_field.getThr_event_code());
			new_info_field.setThr_major_max(info_field.getThr_major_max());
			new_info_field.setThr_major_min(info_field.getThr_major_min());
			new_info_field.setThr_minor_max(info_field.getThr_minor_max());
			new_info_field.setThr_minor_min(info_field.getThr_minor_min());
			new_info_field.setThr_type(info_field.getThr_type());
			new_info_field.setType_local(info_field.getType_local());
			new_info_field.setType_remote(info_field.getType_remote());
			new_info_field.setUpdate(info_field.isUpdate());
			new_info_field.setVirtual_enable(info_field.isVirtual_enable());
			ne_info_field_list.add(new_info_field);

			this.ne_info_fields = ne_info_field_list.toArray(new EMP_MODEL_NE_INFO_FIELD[0]);

			return new_info_field;
		} else {
			throw EMP_MODEL.editError();
		}
	}

	public EMP_MODEL_NE_INFO_FIELD[] getNe_info_fields() {
		return ne_info_fields;
	}

	public EMP_MODEL_NE_INFO_FIELD getNe_info_field(String ne_info_field_name) {
		if (editable) {
			for (EMP_MODEL_NE_INFO_FIELD ne_info_field : ne_info_fields) {
				if (ne_info_field.getName().equals(ne_info_field_name)) {
					return ne_info_field;
				}
			}
			return null;
		} else {
			return ne_info_field_map.get(ne_info_field_name);
		}
	}

	public EMP_MODEL_NE_INFO_FIELD[] getNe_info_indexs() {
		if (editable) {
			List<EMP_MODEL_NE_INFO_FIELD> ne_info_indexs = new ArrayList<EMP_MODEL_NE_INFO_FIELD>();
			for (EMP_MODEL_NE_INFO_FIELD ne_info_field : ne_info_fields) {
				if (ne_info_field.isIndex()) {
					ne_info_indexs.add(ne_info_field);
				}
			}
			return ne_info_indexs.toArray(new EMP_MODEL_NE_INFO_FIELD[0]);
		} else {
			return ne_info_indexs;
		}
	}

	public boolean isTable() {
		if (editable) {
			for (EMP_MODEL_NE_INFO_FIELD ne_info_field : ne_info_fields) {
				if (ne_info_field.isTable()) {
					return true;
				}
			}
			return false;
		} else {
			if (table == null) {
				table = false;
				for (EMP_MODEL_NE_INFO_FIELD ne_info_field : ne_info_fields) {
					if (ne_info_field.isTable()) {
						table = true;
						break;
					}
				}
			}
			return table;
		}
	}

	public EMP_MODEL_NE_INFO_FIELD[] getNe_info_virtual_field() {
		if (editable) {
			List<EMP_MODEL_NE_INFO_FIELD> ne_info_virtual_fields = new ArrayList<EMP_MODEL_NE_INFO_FIELD>();
			for (EMP_MODEL_NE_INFO_FIELD ne_info_field : ne_info_fields) {
				if (ne_info_field.isVirtual_enable()) {
					ne_info_virtual_fields.add(ne_info_field);
				}
			}
			return ne_info_virtual_fields.toArray(new EMP_MODEL_NE_INFO_FIELD[0]);
		} else {
			return ne_info_virtual_fields;
		}
	}

	public EMP_MODEL_NE_INFO_FIELD[] getNe_statistics() {
		if (editable) {
			List<EMP_MODEL_NE_INFO_FIELD> ne_statistics = new ArrayList<EMP_MODEL_NE_INFO_FIELD>();
			for (EMP_MODEL_NE_INFO_FIELD ne_info_field : ne_info_fields) {
				if (ne_info_field.isStat_enable()) {
					ne_statistics.add(ne_info_field);
				}
			}
			return ne_statistics.toArray(new EMP_MODEL_NE_INFO_FIELD[0]);
		} else {
			return ne_statistics;
		}
	}

	public EMP_MODEL_NE_INFO_FIELD[] getNe_thresholds() {
		if (editable) {
			List<EMP_MODEL_NE_INFO_FIELD> ne_thresholds = new ArrayList<EMP_MODEL_NE_INFO_FIELD>();
			for (EMP_MODEL_NE_INFO_FIELD ne_info_field : ne_info_fields) {
				if (ne_info_field.isThr_enable()) {
					ne_thresholds.add(ne_info_field);
				}
			}
			return ne_thresholds.toArray(new EMP_MODEL_NE_INFO_FIELD[0]);
		} else {
			return ne_thresholds;
		}
	}

	EMP_MODEL_NE_INFO_FIELD moveUpNe_info_field(EMP_MODEL_NE_INFO_FIELD ne_info_field) {
		if (editable) {
			int index = -1;
			for (int i = 0; i < ne_info_fields.length; i++) {
				if (ne_info_field == ne_info_fields[i]) {
					index = i;
					break;
				}
			}

			if (0 < index) {
				ne_info_fields[index] = ne_info_fields[index - 1];
				ne_info_fields[index - 1] = ne_info_field;
				return ne_info_field;
			} else {
				return null;
			}
		} else {
			throw EMP_MODEL.editError();
		}
	}

	EMP_MODEL_NE_INFO_FIELD moveDownNe_info_field(EMP_MODEL_NE_INFO_FIELD ne_info_field) {
		if (editable) {
			int index = -1;
			for (int i = 0; i < ne_info_fields.length; i++) {
				if (ne_info_field == ne_info_fields[i]) {
					index = i;
					break;
				}
			}

			if (index + 1 < ne_info_fields.length) {
				ne_info_fields[index] = ne_info_fields[index + 1];
				ne_info_fields[index + 1] = ne_info_field;
				return ne_info_field;
			} else {
				return null;
			}
		} else {
			throw EMP_MODEL.editError();
		}
	}

	public EMP_MODEL_NE_INFO_FIELD removeNe_info_field(EMP_MODEL_NE_INFO_FIELD ne_info_field) {
		if (editable) {
			EMP_MODEL_NE_INFO_FIELD ne_info_field_removed = null;

			List<EMP_MODEL_NE_INFO_FIELD> ne_info_field_list = new ArrayList<EMP_MODEL_NE_INFO_FIELD>();
			for (EMP_MODEL_NE_INFO_FIELD aaa : ne_info_fields) {
				if (ne_info_field != aaa) {
					ne_info_field_list.add(aaa);
					ne_info_field_removed = aaa;
				}
			}

			this.ne_info_fields = ne_info_field_list.toArray(new EMP_MODEL_NE_INFO_FIELD[0]);

			return ne_info_field_removed;
		} else {
			throw EMP_MODEL.editError();
		}
	}

	// /////////////////////////////////////////////////////////////////

	private static final int CAPACITY_READ = 100;
	private static final int CAPACITY_UPDATE = 100;
	private static final int CAPACITY_INDEX = 2;
	private static final int CAPACITY_MONITOR = 2;

	long getCapacity() {
		long capacity_ne_info = 0;
		long index = 0;
		for (EMP_MODEL_NE_INFO_FIELD ne_info_field : getNe_info_fields()) {
			if (ne_info_field.isIndex()) {
				index++;
			} else if (ne_info_field.isRead()) {
				capacity_ne_info += CAPACITY_READ;
			}
			if (ne_info_field.isUpdate()) {
				capacity_ne_info += CAPACITY_UPDATE;
			}
		}
		if (0 < index) {
			capacity_ne_info *= (index * CAPACITY_INDEX);
		}
		if (isMonitoring()) {
			capacity_ne_info *= CAPACITY_MONITOR;
		}

		long capacity_ne_statistics = 0;
		if (isStat_enable()) {
			for (EMP_MODEL_NE_INFO_FIELD ne_info_field : getNe_info_fields()) {
				if (ne_info_field.isStat_enable()) {
					capacity_ne_statistics += 1000;
				}
			}
			if (0 < index) {
				capacity_ne_statistics *= (index * CAPACITY_INDEX);
			}
		}
		return Math.max(1000, capacity_ne_info + capacity_ne_statistics);
	}

	// /////////////////////////////////////////////////////////////////

	public SEVERITY getSeverity() {
		if (!UtilString.isName(name)) {
			return SEVERITY.CRITICAL;
		}
		if (protocol == null) {
			return SEVERITY.CRITICAL;
		}
		if (stat_enable && stat_type == null) {
			return SEVERITY.CRITICAL;
		}
		if (!EMP_MODEL_NE_INFO_FIELD.isInfo_field_unique(getNe_info_fields())) {
			return SEVERITY.CRITICAL;
		}
		if (!isNe_info_unique(emp_model.getNe_infos(), this)) {
			return SEVERITY.CRITICAL;
		}
		if (EMP_MODEL_NE_INFO_FIELD.isInfo_field_critical(getNe_info_fields())) {
			return SEVERITY.CRITICAL;
		}
		if (EMP_MODEL_NE_INFO_FIELD.isInfo_field_index_critical(getNe_info_fields())) {
			return SEVERITY.CRITICAL;
		}
		if (emp_model.getNes_by_info(code).length == 0) {
			return SEVERITY.MINOR;
		}
		return SEVERITY.CLEAR;
	}

	public String getError() {
		if (!UtilString.isName(name)) {
			return UtilString.format("잘못된 값 입니다: '{}'.", "Name");
		}
		if (protocol == null) {
			return UtilString.format("잘못된 값 입니다: '{}'.", "Protocol");
		}
		if (stat_enable && stat_type == null) {
			return UtilString.format("잘못된 값 입니다: '{}'.", "Protocol");
		}
		if (!EMP_MODEL_NE_INFO_FIELD.isInfo_field_unique(getNe_info_fields())) {
			return UtilString.format("중복되는 필드가 있습니다.");
		}
		if (!isNe_info_unique(emp_model.getNe_infos(), this)) {
			return UtilString.format("중복되는 값이 있습니다.");
		}
		if (EMP_MODEL_NE_INFO_FIELD.isInfo_field_critical(getNe_info_fields())) {
			return UtilString.format("NE_INFO_FIELD에 잘못된 값이 있습니다.");
		}
		if (EMP_MODEL_NE_INFO_FIELD.isInfo_field_index_critical(getNe_info_fields())) {
			return UtilString.format("NE_INFO_FIELD의 Index 길이가 일치하지 않습니다.");
		}
		if (emp_model.getNes_by_info(code).length == 0) {
			return UtilString.format("사용되지 않는 값 입니다.");
		}
		return "정상 입니다.";
	}

	public String getErrorUpdate(String name, NE_SESSION_PROTOCOL protocol, boolean monitoring) {
		if (!UtilString.isName(name)) {
			return UtilString.format("'{}'을 입력하세요.", "Name");
		}
		if (protocol == null) {
			return UtilString.format("'{}'을 입력하세요.", "Protocol");
		}
		return null;
	}

	public String getErrorUpdate(boolean fault_enable, boolean audit_alarm, String event_script, Map<String, String> notification_map) {
		return null;
	}

	public String getErrorUpdate(boolean stat_enable, STATISTICS_TYPE stat_type) {
		if (stat_enable && stat_type == null) {
			return UtilString.format("'{}'을 입력하세요.", "Stat_type");
		}
		return null;
	}

	public String getErrorDelete() {
		if (0 < emp_model.getNes_by_info(code).length) {
			return UtilString.format("사용되고 있는 값 입니다.");
		}
		return null;
	}

	private static boolean isNe_info_unique(EMP_MODEL_NE_INFO[] ne_infos, EMP_MODEL_NE_INFO ne_info) {
		for (EMP_MODEL_NE_INFO aaa : ne_infos) {
			if (ne_info != aaa) {
				if (ne_info.name.equals(aaa.name)) {
					return false;
				}
			}
		}
		return true;
	}

	// /////////////////////////////////////////////////////////////////

	@Override
	public String toString() {
		return name;
	}

	public Element toElement(Element ne_info_list) {
		Element ne_info_def = ne_info_list.addElement(EMP_MODEL.NE_INFO);
		if (code != 0) {
			ne_info_def.addAttribute("code", String.valueOf(code));
		}
		if (!UtilString.isEmpty(name)) {
			ne_info_def.addAttribute("name", String.valueOf(name));
		}
		if (!UtilString.isEmpty(display_name)) {
			ne_info_def.addAttribute("display_name", String.valueOf(display_name));
		}
		if (display_enable != false) {
			ne_info_def.addAttribute("display_enable", String.valueOf(display_enable));
		}
		if (protocol != null) {
			ne_info_def.addAttribute("protocol", String.valueOf(protocol));
		}
		if (monitoring != false) {
			ne_info_def.addAttribute("monitoring", String.valueOf(monitoring));
		}
		if (filter_enable != false) {
			ne_info_def.addAttribute("filter_enable", String.valueOf(filter_enable));
		}
		if (!UtilString.isEmpty(filter_script)) {
			ne_info_def.addElement("filter_script").addCDATA(filter_script);
		}
		if (fault_enable != false) {
			ne_info_def.addAttribute("fault_enable", String.valueOf(fault_enable));
		}
		if (audit_alarm != false) {
			ne_info_def.addAttribute("audit_alarm", String.valueOf(audit_alarm));
		}
		if (!UtilString.isEmpty(event_script)) {
			ne_info_def.addElement("event_script").addCDATA(event_script);
		}
		for (Entry<String, String> entry : notification_map.entrySet()) {
			ne_info_def.addElement("notification").addAttribute("oid", entry.getKey()).addCDATA(entry.getValue());
		}
		if (stat_enable != false) {
			ne_info_def.addAttribute("stat_enable", String.valueOf(stat_enable));
		}
		if (stat_type != null) {
			ne_info_def.addAttribute("stat_type", String.valueOf(stat_type));
		}
		for (EMP_MODEL_NE_INFO_FIELD ne_info_field : ne_info_fields) {
			ne_info_field.toElement(ne_info_def);
		}
		return ne_info_def;
	}

	@Override
	public int hashCode() {
		return code;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof EMP_MODEL_NE_INFO) {
			EMP_MODEL_NE_INFO ne_info_def = (EMP_MODEL_NE_INFO) obj;

			boolean equals = (code == ne_info_def.code);
			if (equals) {
				equals = name == null ? ne_info_def.name == null : name.equals(ne_info_def.name);
			}
			if (equals) {
				equals = display_name == null ? ne_info_def.display_name == null : display_name.equals(ne_info_def.display_name);
			}
			if (equals) {
				equals = ne_info_def.display_enable == display_enable;
			}
			if (equals) {
				equals = (protocol == ne_info_def.protocol);
			}
			if (equals) {
				equals = (monitoring == ne_info_def.monitoring);
			}
			if (equals) {
				equals = (stat_type == ne_info_def.stat_type);
			}
			if (equals) {
				equals = (notification_map.size() == ne_info_def.notification_map.size());
				if (equals) {
					for (Entry<String, String> entry : notification_map.entrySet()) {
						if (!entry.getValue().equals(ne_info_def.notification_map.get(entry.getKey()))) {
							equals = false;
							break;
						}
					}
				}
			}
			if (equals) {
				equals = (ne_info_fields.length == ne_info_def.ne_info_fields.length);
				if (equals) {
					for (int i = 0; i < ne_info_fields.length && equals; i++) {
						if (!ne_info_fields[i].equals(ne_info_def.ne_info_fields[i])) {
							equals = false;
							break;
						}
					}
				}
			}
			return equals;
		} else {
			return false;
		}
	}

}
