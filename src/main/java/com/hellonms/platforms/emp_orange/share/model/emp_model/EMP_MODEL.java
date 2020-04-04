package com.hellonms.platforms.emp_orange.share.model.emp_model;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.tree.DefaultDocument;

import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_orange.share.error.ERROR_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.network.ne_info.Model4NeInfo;
import com.hellonms.platforms.emp_orange.share.model.network.ne_info.Model4NeStatistics;
import com.hellonms.platforms.emp_orange.share.model.network.ne_info.Model4NeThreshold;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.NE_SESSION_PROTOCOL;
import com.hellonms.platforms.emp_security.opened.AESCrypto;
import com.hellonms.platforms.emp_security.opened.KeyMap4Public;
import com.hellonms.platforms.emp_util.file.UtilFile;
import com.hellonms.platforms.emp_util.string.UtilString;

public final class EMP_MODEL {

	public static final String ROOT = "EMP_MODEL";

	public static final String ENUM_LIST = "ENUM_LIST";
	public static final String ENUM = "ENUM";
	public static final String ENUM_FIELD = "ENUM_FIELD";

	public static final String EVENT_LIST = "EVENT_LIST";
	public static final String EVENT = "EVENT";

	public static final String NE_INFO_LIST = "NE_INFO_LIST";
	public static final String NE_INFO = "NE_INFO";
	public static final String NE_INFO_FIELD = "NE_INFO_FIELD";

	public static final String NE_LIST = "NE_LIST";
	public static final String NE = "NE";
	public static final String NE_INFO_CODE = "NE_INFO_CODE";

	// /////////////////////////////////////////////////////////////////////////////

	private static String KEY = KeyMap4Public.get("eOrange.emp_model");
	private static File current_file;
	private static EMP_MODEL current;

	public static EMP_MODEL init_current(File file) throws EmpException {
		current = new EMP_MODEL(AESCrypto.decrypt(KEY, UtilFile.readFile(file)), false);
		EMP_MODEL.current_file = file;
		return current;
	}

	public static EMP_MODEL init_current(byte[] bytes) throws EmpException {
		current = new EMP_MODEL(bytes, false);
		if (EMP_MODEL.current_file != null) {
			UtilFile.saveFile(AESCrypto.encrypt(KEY, bytes), EMP_MODEL.current_file);
		}
		return current;
	}

	public static EMP_MODEL current() {
		return current;
	}

	static RuntimeException editError() {
		return new RuntimeException("emp_model edit not allowed");
	}

	// /////////////////////////////////////////////////////////////////////////////
	private final boolean editable;

	private final AtomicInteger code_sequence = new AtomicInteger(0x00001000);

	private final Map<Integer, EMP_MODEL_ENUM> enum_map = new LinkedHashMap<Integer, EMP_MODEL_ENUM>();

	private EMP_MODEL_ENUM[] enums = null;

	private final Map<Integer, EMP_MODEL_EVENT> event_map = new LinkedHashMap<Integer, EMP_MODEL_EVENT>();
	private final Map<String, EMP_MODEL_EVENT> specific_problem_map = new LinkedHashMap<String, EMP_MODEL_EVENT>();
	private EMP_MODEL_EVENT[] events = null;

	private final Map<Integer, EMP_MODEL_NE_INFO> ne_info_map = new LinkedHashMap<Integer, EMP_MODEL_NE_INFO>();
	private final Map<String, EMP_MODEL_NE_INFO> ne_info_name_map = new LinkedHashMap<String, EMP_MODEL_NE_INFO>();
	private EMP_MODEL_NE_INFO[] ne_infos = null;

	private final Map<Integer, EMP_MODEL_NE_INFO[]> ne_info_ne_map = new LinkedHashMap<Integer, EMP_MODEL_NE_INFO[]>();
	private final Map<String, EMP_MODEL_NE_INFO[]> ne_info_ne_protocol_map = new LinkedHashMap<String, EMP_MODEL_NE_INFO[]>();
	private final Map<String, EMP_MODEL_NE_INFO[]> ne_info_ne_protocol_monitor_map = new LinkedHashMap<String, EMP_MODEL_NE_INFO[]>();
	private final Map<Integer, EMP_MODEL_NE_INFO[]> ne_statistics_ne_map = new LinkedHashMap<Integer, EMP_MODEL_NE_INFO[]>();
	private final Map<Integer, EMP_MODEL_NE_INFO[]> ne_threshold_ne_map = new LinkedHashMap<Integer, EMP_MODEL_NE_INFO[]>();
	private final Map<String, EMP_MODEL_NE_INFO> ne_info_notification_map = new LinkedHashMap<String, EMP_MODEL_NE_INFO>();

	private final Map<Integer, EMP_MODEL_NE> ne_map = new LinkedHashMap<Integer, EMP_MODEL_NE>();

	private EMP_MODEL_NE[] nes = null;

	// /////////////////////////////////////////////////////////////////////////////
	// /////////////////////////////////////////////////////////////////////////////

	public EMP_MODEL(byte[] bytes) throws EmpException {
		this(bytes, true);
	}

	private EMP_MODEL(byte[] bytes, boolean editable) throws EmpException {
		this.editable = editable;
		try {
			ByteArrayInputStream input_stream = new ByteArrayInputStream(bytes);
			SAXReader reader = new SAXReader();
			Document document = reader.read(input_stream);

			Object[] enum_objects = document.getRootElement().element(ENUM_LIST).elements(ENUM).toArray();
			for (int i = 0; i < enum_objects.length; i++) {
				EMP_MODEL_ENUM element = new EMP_MODEL_ENUM(this, (Element) enum_objects[i], editable);
				if (enum_map.containsKey(element.getCode())) {
					throw new RuntimeException("emp_model enum duplicate");
				}
				if (code_sequence.get() < element.getCode()) {
					code_sequence.set(element.getCode());
				}
				enum_map.put(element.getCode(), element);
			}
			this.enums = enum_map.values().toArray(new EMP_MODEL_ENUM[0]);

			// //////////////////////////////////////////////////////////////////////////////////////////
			{
				EMP_MODEL_EVENT[] elements = EMP_MODEL_EVENT.createHiddens(this);
				for (EMP_MODEL_EVENT element : elements) {
					event_map.put(element.getCode(), element);
					specific_problem_map.put(element.getSpecific_problem(), element);
				}
			}

			Object[] event_objects = document.getRootElement().element(EVENT_LIST).elements(EVENT).toArray();
			for (int i = 0; i < event_objects.length; i++) {
				EMP_MODEL_EVENT element = new EMP_MODEL_EVENT(this, (Element) event_objects[i], editable);
				if (!EMP_MODEL_EVENT.isHidden(element.getCode())) {
					if (event_map.containsKey(element.getCode())) {
						throw new RuntimeException("emp_model event duplicate");
					}
					if (code_sequence.get() < element.getCode()) {
						code_sequence.set(element.getCode());
					}
					event_map.put(element.getCode(), element);
					specific_problem_map.put(element.getSpecific_problem(), element);
				}
			}
			this.events = event_map.values().toArray(new EMP_MODEL_EVENT[0]);

			Object[] ne_info_objects = document.getRootElement().element(NE_INFO_LIST).elements(NE_INFO).toArray();
			for (int i = 0; i < ne_info_objects.length; i++) {
				EMP_MODEL_NE_INFO element = new EMP_MODEL_NE_INFO(this, (Element) ne_info_objects[i], enum_map, editable);
				if (ne_info_map.containsKey(element.getCode())) {
					throw new RuntimeException("emp_model info duplicate");
				}
				if (code_sequence.get() < element.getCode()) {
					code_sequence.set(element.getCode());
				}
				ne_info_map.put(element.getCode(), element);
				ne_info_name_map.put(element.getName(), element);
			}
			this.ne_infos = ne_info_map.values().toArray(new EMP_MODEL_NE_INFO[0]);

			// //////////////////////////////////////////////////////////////////////////////////////////
			{
				EMP_MODEL_NE[] elements = EMP_MODEL_NE.createHiddens(this);
				for (EMP_MODEL_NE element : elements) {
					ne_map.put(element.getCode(), element);
				}
			}

			Object[] ne_objects = document.getRootElement().element(NE_LIST).elements(NE).toArray();
			for (int i = 0; i < ne_objects.length; i++) {
				EMP_MODEL_NE element = new EMP_MODEL_NE(this, (Element) ne_objects[i], ne_info_map, editable);
				if (!EMP_MODEL_NE.isHidden(element.getCode())) {
					if (ne_map.containsKey(element.getCode())) {
						throw new RuntimeException("emp_model ne duplicate");
					}
					if (code_sequence.get() < element.getCode()) {
						code_sequence.set(element.getCode());
					}
					ne_map.put(element.getCode(), element);
				}
			}
			this.nes = ne_map.values().toArray(new EMP_MODEL_NE[0]);
		} catch (Exception e) {
			throw new EmpException(e, ERROR_CODE_ORANGE.FILE_IO);
		}
	}

	// ///////////////////////////////////////////////////////
	public EMP_MODEL_ENUM addEnum() {
		if (editable) {
			EMP_MODEL_ENUM enum_def = new EMP_MODEL_ENUM(this, code_sequence.incrementAndGet(), editable);
			enum_map.put(enum_def.getCode(), enum_def);
			return enum_def;
		} else {
			throw EMP_MODEL.editError();
		}
	}

	public EMP_MODEL_ENUM copyEnum(EMP_MODEL_ENUM enum_def) {
		if (editable) {
			EMP_MODEL_ENUM new_enum = new EMP_MODEL_ENUM(this, code_sequence.incrementAndGet(), editable);
			new_enum.setName(UtilString.format("{}_copy", enum_def.getName()));
			for (EMP_MODEL_ENUM_FIELD enum_field : enum_def.getEnum_fields()) {
				new_enum.copyEnum_field(enum_field);
			}
			enum_map.put(new_enum.getCode(), new_enum);
			return new_enum;
		} else {
			throw EMP_MODEL.editError();
		}
	}

	public EMP_MODEL_ENUM[] getEnums() {
		if (editable) {
			return enum_map.values().toArray(new EMP_MODEL_ENUM[0]);
		} else {
			return enums;
		}
	}

	public EMP_MODEL_ENUM getEnum(int enum_code) {
		return enum_map.get(enum_code);
	}

	EMP_MODEL_ENUM moveUpEnum(EMP_MODEL_ENUM enum_def) {
		if (editable) {
			List<EMP_MODEL_ENUM> enum_list = new ArrayList<EMP_MODEL_ENUM>();
			enum_list.addAll(enum_map.values());
			int index = enum_list.indexOf(enum_def);
			if (0 < index) {
				enum_list.set(index, enum_list.get(index - 1));
				enum_list.set(index - 1, enum_def);
				enum_map.clear();
				for (EMP_MODEL_ENUM aaa : enum_list) {
					enum_map.put(aaa.getCode(), aaa);
				}
				return enum_def;
			} else {
				return null;
			}
		} else {
			throw EMP_MODEL.editError();
		}
	}

	EMP_MODEL_ENUM moveDownEnum(EMP_MODEL_ENUM enum_def) {
		if (editable) {
			List<EMP_MODEL_ENUM> enum_list = new ArrayList<EMP_MODEL_ENUM>();
			enum_list.addAll(enum_map.values());
			int index = enum_list.indexOf(enum_def);
			if (index + 1 < enum_list.size()) {
				enum_list.set(index, enum_list.get(index + 1));
				enum_list.set(index + 1, enum_def);
				enum_map.clear();
				for (EMP_MODEL_ENUM aaa : enum_list) {
					enum_map.put(aaa.getCode(), aaa);
				}
				return enum_def;
			} else {
				return null;
			}
		} else {
			throw EMP_MODEL.editError();
		}
	}

	public EMP_MODEL_ENUM removeEnum(EMP_MODEL_ENUM enum_def) {
		if (editable) {
			return enum_map.remove(enum_def.getCode());
		} else {
			throw EMP_MODEL.editError();
		}
	}

	public EMP_MODEL_ENUM_FIELD getEnum_field(int enum_code, String value) {
		EMP_MODEL_ENUM enum_def = enum_map.get(enum_code);
		if (enum_def == null) {
			throw new RuntimeException(UtilString.format("unknown enum {}", enum_code));
		}
		return enum_def.getEnum_field(value);
	}

	// ///////////////////////////////////////////////////////
	public EMP_MODEL_EVENT addEvent() {
		if (editable) {
			EMP_MODEL_EVENT event = new EMP_MODEL_EVENT(this, code_sequence.incrementAndGet(), editable);
			event_map.put(event.getCode(), event);
			return event;
		} else {
			throw EMP_MODEL.editError();
		}
	}

	public EMP_MODEL_EVENT copyEvent(EMP_MODEL_EVENT event) {
		if (editable) {
			EMP_MODEL_EVENT new_event = new EMP_MODEL_EVENT(this, code_sequence.incrementAndGet(), editable);
			new_event.setAlarm(event.isAlarm());
			new_event.setAudit_alarm(event.isAudit_alarm());
			new_event.setProbable_cause(event.getProbable_cause());
			new_event.setSpecific_problem(UtilString.format("{}_copy", event.getSpecific_problem()));
			event_map.put(new_event.getCode(), new_event);
			return new_event;
		} else {
			throw EMP_MODEL.editError();
		}
	}

	public EMP_MODEL_EVENT[] getEvents() {
		if (editable) {
			return event_map.values().toArray(new EMP_MODEL_EVENT[0]);
		} else {
			return events;
		}
	}

	public EMP_MODEL_EVENT getEvent(int event_code) {
		return event_map.get(event_code);
	}

	public EMP_MODEL_EVENT getEvent(String specific_problem) {
		if (editable) {
			for (EMP_MODEL_EVENT event : event_map.values()) {
				if (event.getSpecific_problem().equals(specific_problem)) {
					return event;
				}
			}
			return null;
		} else {
			return specific_problem_map.get(specific_problem);
		}
	}

	public EMP_MODEL_EVENT moveUpEvent(EMP_MODEL_EVENT event) {
		if (editable) {
			List<EMP_MODEL_EVENT> event_list = new ArrayList<EMP_MODEL_EVENT>();
			event_list.addAll(event_map.values());
			int index = event_list.indexOf(event);
			if (0 < index) {
				event_list.set(index, event_list.get(index - 1));
				event_list.set(index - 1, event);
				event_map.clear();
				for (EMP_MODEL_EVENT aaa : event_list) {
					event_map.put(aaa.getCode(), aaa);
				}
				return event;
			} else {
				return null;
			}
		} else {
			throw EMP_MODEL.editError();
		}
	}

	public EMP_MODEL_EVENT moveDownEvent(EMP_MODEL_EVENT event) {
		if (editable) {
			List<EMP_MODEL_EVENT> event_list = new ArrayList<EMP_MODEL_EVENT>();
			event_list.addAll(event_map.values());
			int index = event_list.indexOf(event);
			if (index + 1 < event_list.size()) {
				event_list.set(index, event_list.get(index + 1));
				event_list.set(index + 1, event);
				event_map.clear();
				for (EMP_MODEL_EVENT aaa : event_list) {
					event_map.put(aaa.getCode(), aaa);
				}
				return event;
			} else {
				return null;
			}
		} else {
			throw EMP_MODEL.editError();
		}
	}

	public EMP_MODEL_EVENT removeEvent(EMP_MODEL_EVENT emp_model_event) {
		if (editable) {
			return event_map.remove(emp_model_event.getCode());
		} else {
			throw EMP_MODEL.editError();
		}
	}

	// ///////////////////////////////////////////////////////
	public EMP_MODEL_NE_INFO addNe_info() {
		if (editable) {
			EMP_MODEL_NE_INFO ne_info_def = new EMP_MODEL_NE_INFO(this, code_sequence.incrementAndGet(), editable);
			ne_info_map.put(ne_info_def.getCode(), ne_info_def);
			return ne_info_def;
		} else {
			throw EMP_MODEL.editError();
		}
	}

	public EMP_MODEL_NE_INFO copyNe_info(EMP_MODEL_NE_INFO ne_info) {
		if (editable) {
			EMP_MODEL_NE_INFO new_ne_info = new EMP_MODEL_NE_INFO(this, code_sequence.incrementAndGet(), editable);
			new_ne_info.setAudit_alarm(ne_info.isAudit_alarm());
			new_ne_info.setEvent_script(ne_info.getEvent_script());
			new_ne_info.setFault_enable(ne_info.isFault_enable());
			new_ne_info.setMonitoring(ne_info.isMonitoring());
			new_ne_info.setName(UtilString.format("{}_copy", ne_info.getName()));
			new_ne_info.setNotification_map(ne_info.getNotification_map());
			new_ne_info.setProtocol(ne_info.getProtocol());
			new_ne_info.setStat_enable(ne_info.isStat_enable());
			new_ne_info.setStat_type(ne_info.getStat_type());
			for (EMP_MODEL_NE_INFO_FIELD ne_info_field : ne_info.getNe_info_fields()) {
				new_ne_info.copyNe_info_field(ne_info_field);
			}
			ne_info_map.put(new_ne_info.getCode(), new_ne_info);
			return new_ne_info;
		} else {
			throw EMP_MODEL.editError();
		}
	}

	public EMP_MODEL_NE_INFO[] getNe_infos() {
		if (editable) {
			return ne_info_map.values().toArray(new EMP_MODEL_NE_INFO[0]);
		} else {
			return ne_infos;
		}
	}

	public EMP_MODEL_NE_INFO[] getNe_infos_by_ne(int ne_code) {
		if (editable) {
			List<EMP_MODEL_NE_INFO> ne_info_defs = new ArrayList<EMP_MODEL_NE_INFO>();
			EMP_MODEL_NE ne_def = ne_map.get(ne_code);
			if (ne_def != null) {
				for (int ne_info_code : ne_def.getNe_info_codes()) {
					EMP_MODEL_NE_INFO ne_info_def = ne_info_map.get(ne_info_code);
					if (ne_info_def != null) {
						ne_info_defs.add(ne_info_def);
					}
				}
			}
			return ne_info_defs.toArray(new EMP_MODEL_NE_INFO[0]);
		} else {
			EMP_MODEL_NE_INFO[] ne_infos = ne_info_ne_map.get(ne_code);
			if (ne_infos == null) {
				List<EMP_MODEL_NE_INFO> ne_info_defs = new ArrayList<EMP_MODEL_NE_INFO>();
				EMP_MODEL_NE ne_def = ne_map.get(ne_code);
				if (ne_def != null) {
					for (int ne_info_code : ne_def.getNe_info_codes()) {
						EMP_MODEL_NE_INFO ne_info_def = ne_info_map.get(ne_info_code);
						if (ne_info_def != null) {
							ne_info_defs.add(ne_info_def);
						}
					}
				}
				ne_infos = ne_info_defs.toArray(new EMP_MODEL_NE_INFO[0]);
				ne_info_ne_map.put(ne_code, ne_infos);
			}
			return ne_infos;
		}
	}

	public EMP_MODEL_NE_INFO[] getNe_infos_by_ne(int ne_code, NE_SESSION_PROTOCOL protocol) {
		if (editable) {
			List<EMP_MODEL_NE_INFO> ne_info_defs = new ArrayList<EMP_MODEL_NE_INFO>();
			EMP_MODEL_NE ne_def = ne_map.get(ne_code);
			if (ne_def != null) {
				for (int ne_info_code : ne_def.getNe_info_codes()) {
					EMP_MODEL_NE_INFO ne_info_def = ne_info_map.get(ne_info_code);
					if (ne_info_def != null && ne_info_def.getProtocol() == protocol) {
						ne_info_defs.add(ne_info_def);
					}
				}
			}
			return ne_info_defs.toArray(new EMP_MODEL_NE_INFO[0]);
		} else {
			String key = UtilString.format("{}.{}", ne_code, protocol);
			EMP_MODEL_NE_INFO[] ne_infos = ne_info_ne_protocol_map.get(key);
			if (ne_infos == null) {
				List<EMP_MODEL_NE_INFO> ne_info_defs = new ArrayList<EMP_MODEL_NE_INFO>();
				EMP_MODEL_NE ne_def = ne_map.get(ne_code);
				if (ne_def != null) {
					for (int ne_info_code : ne_def.getNe_info_codes()) {
						EMP_MODEL_NE_INFO ne_info_def = ne_info_map.get(ne_info_code);
						if (ne_info_def != null && ne_info_def.getProtocol() == protocol) {
							ne_info_defs.add(ne_info_def);
						}
					}
				}
				ne_infos = ne_info_defs.toArray(new EMP_MODEL_NE_INFO[0]);
				ne_info_ne_protocol_map.put(key, ne_infos);
			}
			return ne_infos;
		}
	}

	public EMP_MODEL_NE_INFO[] getNe_infos_by_ne_monitoring(int ne_code, NE_SESSION_PROTOCOL protocol) {
		if (editable) {
			List<EMP_MODEL_NE_INFO> ne_info_defs = new ArrayList<EMP_MODEL_NE_INFO>();
			EMP_MODEL_NE ne_def = ne_map.get(ne_code);
			if (ne_def != null) {
				for (int ne_info_code : ne_def.getNe_info_codes()) {
					EMP_MODEL_NE_INFO ne_info_def = ne_info_map.get(ne_info_code);
					if (ne_info_def != null && ne_info_def.getProtocol() == protocol && ne_info_def.isMonitoring()) {
						ne_info_defs.add(ne_info_def);
					}
				}
			}
			return ne_info_defs.toArray(new EMP_MODEL_NE_INFO[0]);
		} else {
			String key = UtilString.format("{}.{}", ne_code, protocol);
			EMP_MODEL_NE_INFO[] ne_infos = ne_info_ne_protocol_monitor_map.get(key);
			if (ne_infos == null) {
				List<EMP_MODEL_NE_INFO> ne_info_defs = new ArrayList<EMP_MODEL_NE_INFO>();
				EMP_MODEL_NE ne_def = ne_map.get(ne_code);
				if (ne_def != null) {
					for (int ne_info_code : ne_def.getNe_info_codes()) {
						EMP_MODEL_NE_INFO ne_info_def = ne_info_map.get(ne_info_code);
						if (ne_info_def != null && ne_info_def.getProtocol() == protocol && ne_info_def.isMonitoring()) {
							ne_info_defs.add(ne_info_def);
						}
					}
				}
				ne_infos = ne_info_defs.toArray(new EMP_MODEL_NE_INFO[0]);
				ne_info_ne_protocol_monitor_map.put(key, ne_infos);
			}
			return ne_infos;
		}
	}

	public EMP_MODEL_NE_INFO[] getNe_statisticss_by_ne(int ne_code) {
		if (editable) {
			List<EMP_MODEL_NE_INFO> ne_info_defs = new ArrayList<EMP_MODEL_NE_INFO>();
			EMP_MODEL_NE ne_def = ne_map.get(ne_code);
			if (ne_def != null) {
				for (int ne_info_code : ne_def.getNe_info_codes()) {
					EMP_MODEL_NE_INFO ne_info_def = ne_info_map.get(ne_info_code);
					if (ne_info_def != null && ne_info_def.isStat_enable()) {
						ne_info_defs.add(ne_info_def);
					}
				}
			}
			return ne_info_defs.toArray(new EMP_MODEL_NE_INFO[0]);
		} else {
			EMP_MODEL_NE_INFO[] ne_infos = ne_statistics_ne_map.get(ne_code);
			if (ne_infos == null) {
				List<EMP_MODEL_NE_INFO> ne_info_defs = new ArrayList<EMP_MODEL_NE_INFO>();
				EMP_MODEL_NE ne_def = ne_map.get(ne_code);
				if (ne_def != null) {
					for (int ne_info_code : ne_def.getNe_info_codes()) {
						EMP_MODEL_NE_INFO ne_info_def = ne_info_map.get(ne_info_code);
						if (ne_info_def != null && ne_info_def.isStat_enable()) {
							ne_info_defs.add(ne_info_def);
						}
					}
				}
				ne_infos = ne_info_defs.toArray(new EMP_MODEL_NE_INFO[0]);
				ne_statistics_ne_map.put(ne_code, ne_infos);
			}
			return ne_infos;
		}
	}

	public EMP_MODEL_NE_INFO[] getNe_thresholds_by_ne(int ne_code) {
		if (editable) {
			List<EMP_MODEL_NE_INFO> ne_info_defs = new ArrayList<EMP_MODEL_NE_INFO>();
			EMP_MODEL_NE ne_def = ne_map.get(ne_code);
			if (ne_def != null) {
				for (int ne_info_code : ne_def.getNe_info_codes()) {
					EMP_MODEL_NE_INFO ne_info_def = ne_info_map.get(ne_info_code);
					if (ne_info_def != null && ne_info_def.isThr_enable()) {
						ne_info_defs.add(ne_info_def);
					}
				}
			}
			return ne_info_defs.toArray(new EMP_MODEL_NE_INFO[0]);
		} else {
			EMP_MODEL_NE_INFO[] ne_infos = ne_threshold_ne_map.get(ne_code);
			if (ne_infos == null) {
				List<EMP_MODEL_NE_INFO> ne_info_defs = new ArrayList<EMP_MODEL_NE_INFO>();
				EMP_MODEL_NE ne_def = ne_map.get(ne_code);
				if (ne_def != null) {
					for (int ne_info_code : ne_def.getNe_info_codes()) {
						EMP_MODEL_NE_INFO ne_info_def = ne_info_map.get(ne_info_code);
						if (ne_info_def != null && ne_info_def.isThr_enable()) {
							ne_info_defs.add(ne_info_def);
						}
					}
				}
				ne_infos = ne_info_defs.toArray(new EMP_MODEL_NE_INFO[0]);
				ne_threshold_ne_map.put(ne_code, ne_infos);
			}
			return ne_infos;
		}
	}

	public EMP_MODEL_NE_INFO getNe_info(int ne_info_code) {
		return ne_info_map.get(ne_info_code);
	}

	public EMP_MODEL_NE_INFO getNe_info(String ne_info_name) {
		return ne_info_name_map.get(ne_info_name);
	}

	public EMP_MODEL_NE_INFO getNe_info_by_notifiction(String notification_oid) {
		if (editable) {
			EMP_MODEL_NE_INFO ne_info = null;
			for (EMP_MODEL_NE_INFO aaa : ne_info_map.values()) {
				if (aaa.hasNotification(notification_oid)) {
					ne_info = aaa;
					break;
				}
			}
			return ne_info;
		} else {
			EMP_MODEL_NE_INFO ne_info = ne_info_notification_map.get(notification_oid);
			if (ne_info == null) {
				for (EMP_MODEL_NE_INFO aaa : ne_info_map.values()) {
					if (aaa.hasNotification(notification_oid)) {
						ne_info = aaa;
						break;
					}
				}
				ne_info_notification_map.put(notification_oid, ne_info);
			}
			return ne_info;
		}
	}

	public EMP_MODEL_NE_INFO moveUpNe_info(EMP_MODEL_NE_INFO ne_info_def) {
		if (editable) {
			List<EMP_MODEL_NE_INFO> ne_info_list = new ArrayList<EMP_MODEL_NE_INFO>();
			ne_info_list.addAll(ne_info_map.values());
			int index = ne_info_list.indexOf(ne_info_def);
			if (0 < index) {
				ne_info_list.set(index, ne_info_list.get(index - 1));
				ne_info_list.set(index - 1, ne_info_def);
				ne_info_map.clear();
				for (EMP_MODEL_NE_INFO aaa : ne_info_list) {
					ne_info_map.put(aaa.getCode(), aaa);
				}
				return ne_info_def;
			} else {
				return null;
			}
		} else {
			throw EMP_MODEL.editError();
		}
	}

	public EMP_MODEL_NE_INFO moveDownNe_info(EMP_MODEL_NE_INFO ne_info_def) {
		if (editable) {
			List<EMP_MODEL_NE_INFO> ne_info_list = new ArrayList<EMP_MODEL_NE_INFO>();
			ne_info_list.addAll(ne_info_map.values());
			int index = ne_info_list.indexOf(ne_info_def);
			if (index + 1 < ne_info_list.size()) {
				ne_info_list.set(index, ne_info_list.get(index + 1));
				ne_info_list.set(index + 1, ne_info_def);
				ne_info_map.clear();
				for (EMP_MODEL_NE_INFO aaa : ne_info_list) {
					ne_info_map.put(aaa.getCode(), aaa);
				}
				return ne_info_def;
			} else {
				return null;
			}
		} else {
			throw EMP_MODEL.editError();
		}
	}

	public EMP_MODEL_NE_INFO removeNe_info(EMP_MODEL_NE_INFO info) {
		if (editable) {
			return ne_info_map.remove(info.getCode());
		} else {
			throw EMP_MODEL.editError();
		}
	}

	public EMP_MODEL_NE_INFO_FIELD[] getNe_info_fields_by_enum(EMP_MODEL_ENUM enum_def) {
		List<EMP_MODEL_NE_INFO_FIELD> ne_info_fields = new ArrayList<EMP_MODEL_NE_INFO_FIELD>();
		for (EMP_MODEL_NE_INFO info : ne_info_map.values()) {
			for (EMP_MODEL_NE_INFO_FIELD ne_info_field : info.getNe_info_fields()) {
				int enum_code = ne_info_field.getEnum_code();
				if (enum_code == enum_def.getCode()) {
					ne_info_fields.add(ne_info_field);
				}
			}
		}
		return ne_info_fields.toArray(new EMP_MODEL_NE_INFO_FIELD[0]);
	}

	private Map<Integer, Model4NeInfo> ne_info_instance_map = new HashMap<Integer, Model4NeInfo>();

	public Model4NeInfo newInstanceNe_info(int ne_info_code) {
		Model4NeInfo model = ne_info_instance_map.get(ne_info_code);
		if (model == null) {
			EMP_MODEL_NE_INFO ne_info_def = ne_info_map.get(ne_info_code);
			if (ne_info_def != null) {
				model = new Model4NeInfo();
				model.setNe_info_code(ne_info_code);
				model.setNe_info_index(0);
				model.setCollect_time(new Date());
				for (EMP_MODEL_NE_INFO_FIELD ne_info_field_def : ne_info_def.getNe_info_fields()) {
					model.setField_value(ne_info_field_def, null);
				}
				ne_info_instance_map.put(ne_info_code, model);
			}
		}
		return model == null ? null : model.copy();
	}

	private Map<Integer, Model4NeStatistics> ne_statistics_instance_map = new HashMap<Integer, Model4NeStatistics>();

	public Model4NeStatistics newInstanceNe_statistics(int ne_info_code) {
		Model4NeStatistics model = ne_statistics_instance_map.get(ne_info_code);
		if (model == null) {
			EMP_MODEL_NE_INFO ne_info_def = ne_info_map.get(ne_info_code);
			if (ne_info_def != null && ne_info_def.isStat_enable()) {
				model = new Model4NeStatistics();
				model.setNe_info_code(ne_info_code);
				model.setNe_info_index(0);
				model.setCollect_time(new Date());
				for (EMP_MODEL_NE_INFO_FIELD ne_info_field_def : ne_info_def.getNe_statistics()) {
					model.setField_value(ne_info_field_def, null);
					model.setField_counter(ne_info_field_def, null);
				}
				ne_statistics_instance_map.put(ne_info_code, model);
			}
		}
		return model == null ? null : model.copy();
	}

	private Map<Integer, Model4NeThreshold> ne_threshold_instance_map = new HashMap<Integer, Model4NeThreshold>();

	public Model4NeThreshold newInstanceThreshold(int ne_info_code) {
		Model4NeThreshold model = ne_threshold_instance_map.get(ne_info_code);
		if (model == null) {
			EMP_MODEL_NE_INFO ne_info_def = ne_info_map.get(ne_info_code);
			if (ne_info_def != null && ne_info_def.isThr_enable()) {
				model = new Model4NeThreshold();
				model.setNe_info_code(ne_info_code);
				for (EMP_MODEL_NE_INFO_FIELD ne_info_field_def : ne_info_def.getNe_thresholds()) {
					model.initThreshold_values(ne_info_field_def);
					model.setThreshold_critical_min(ne_info_field_def, ne_info_field_def.getThr_critical_min());
					model.setThreshold_critical_max(ne_info_field_def, ne_info_field_def.getThr_critical_max());
					model.setThreshold_major_min(ne_info_field_def, ne_info_field_def.getThr_major_min());
					model.setThreshold_major_max(ne_info_field_def, ne_info_field_def.getThr_major_max());
					model.setThreshold_minor_min(ne_info_field_def, ne_info_field_def.getThr_minor_min());
					model.setThreshold_minor_max(ne_info_field_def, ne_info_field_def.getThr_minor_max());
				}
				ne_threshold_instance_map.put(ne_info_code, model);
			}
		}
		return model == null ? null : model.copy();
	}

	// ///////////////////////////////////////////////////////
	public EMP_MODEL_NE addNe() {
		if (editable) {
			EMP_MODEL_NE ne = new EMP_MODEL_NE(this, code_sequence.incrementAndGet(), editable);
			ne_map.put(ne.getCode(), ne);
			return ne;
		} else {
			throw EMP_MODEL.editError();
		}
	}

	public EMP_MODEL_NE copyNe(EMP_MODEL_NE ne) {
		if (editable) {
			EMP_MODEL_NE new_ne = new EMP_MODEL_NE(this, code_sequence.incrementAndGet(), editable);
			new_ne.setManufacturer(ne.getManufacturer());
			new_ne.setNe_icon(ne.getNe_icon());
			new_ne.setNe_info_codes(ne.getNe_info_codes());
			new_ne.setNe_oid(ne.getNe_oid());
			new_ne.setOui(ne.getOui());
			new_ne.setProduct_class(UtilString.format("{}_copy", ne.getProduct_class()));
			ne_map.put(new_ne.getCode(), new_ne);
			return new_ne;
		} else {
			throw EMP_MODEL.editError();
		}
	}

	public EMP_MODEL_NE[] getNes() {
		if (editable) {
			return ne_map.values().toArray(new EMP_MODEL_NE[0]);
		} else {
			return nes;
		}
	}

	public EMP_MODEL_NE[] getNes_by_info(int code) {
		List<EMP_MODEL_NE> ne_list = new ArrayList<EMP_MODEL_NE>();
		for (EMP_MODEL_NE ne : ne_map.values()) {
			for (int ne_info_code : ne.getNe_info_codes()) {
				if (code == ne_info_code) {
					ne_list.add(ne);
					break;
				}
			}
		}
		return ne_list.toArray(new EMP_MODEL_NE[0]);
	}

	public EMP_MODEL_NE getNe(int ne_code) {
		return ne_map.get(ne_code);
	}

	public EMP_MODEL_NE getNe_by_oid(String ne_oid) {
		for (EMP_MODEL_NE ne : ne_map.values()) {
			if (ne.getNe_oid().equals(ne_oid)) {
				return ne;
			}
		}
		for (EMP_MODEL_NE ne : ne_map.values()) {
			if (ne.getNe_oid().equals("*")) {
				return ne;
			}
		}
		return null;
	}

	public EMP_MODEL_NE moveUpNe(EMP_MODEL_NE ne) {
		if (editable) {
			List<EMP_MODEL_NE> ne_list = new ArrayList<EMP_MODEL_NE>();
			ne_list.addAll(ne_map.values());
			int index = ne_list.indexOf(ne);
			if (0 < index) {
				ne_list.set(index, ne_list.get(index - 1));
				ne_list.set(index - 1, ne);
				ne_map.clear();
				for (EMP_MODEL_NE aaa : ne_list) {
					ne_map.put(aaa.getCode(), aaa);
				}
				return ne;
			} else {
				return null;
			}
		} else {
			throw EMP_MODEL.editError();
		}
	}

	public EMP_MODEL_NE moveDownNe(EMP_MODEL_NE ne) {
		if (editable) {
			List<EMP_MODEL_NE> ne_list = new ArrayList<EMP_MODEL_NE>();
			ne_list.addAll(ne_map.values());
			int index = ne_list.indexOf(ne);
			if (index + 1 < ne_list.size()) {
				ne_list.set(index, ne_list.get(index + 1));
				ne_list.set(index + 1, ne);
				ne_map.clear();
				for (EMP_MODEL_NE aaa : ne_list) {
					ne_map.put(aaa.getCode(), aaa);
				}
				return ne;
			} else {
				return null;
			}
		} else {
			throw EMP_MODEL.editError();
		}
	}

	public EMP_MODEL_NE removeNe(EMP_MODEL_NE ne) {
		if (editable) {
			return ne_map.remove(ne.getCode());
		} else {
			throw EMP_MODEL.editError();
		}
	}

	// /////////////////////////////////////////////////////////////////////////////
	public long getCapacity(int ne_code) {
		EMP_MODEL_NE ne = getNe(ne_code);
		if (ne == null) {
			return 5000;
		}
		if (editable) {
			return ne.getCapacity();
		} else {
			return ne.getCapacity();
		}
	}

	// /////////////////////////////////////////////////////////////////////////////

	public byte[] getBytes() {
		DefaultDocument document = new DefaultDocument();
		document.setXMLEncoding("UTF-8");
		Element root = document.addElement(ROOT);
		Element enum_list = root.addElement(ENUM_LIST);
		for (Entry<Integer, EMP_MODEL_ENUM> entry : enum_map.entrySet()) {
			entry.getValue().toElement(enum_list);
		}
		Element event_list = root.addElement(EVENT_LIST);
		for (Entry<Integer, EMP_MODEL_EVENT> entry : event_map.entrySet()) {
			if (!EMP_MODEL_EVENT.isHidden(entry.getKey())) {
				entry.getValue().toElement(event_list);
			}
		}
		Element ne_info_list = root.addElement(NE_INFO_LIST);
		for (Entry<Integer, EMP_MODEL_NE_INFO> entry : ne_info_map.entrySet()) {
			entry.getValue().toElement(ne_info_list);
		}
		Element ne_list = root.addElement(NE_LIST);
		for (Entry<Integer, EMP_MODEL_NE> entry : ne_map.entrySet()) {
			if (!EMP_MODEL_NE.isHidden(entry.getKey())) {
				entry.getValue().toElement(ne_list);
			}
		}
		return document.asXML().getBytes();
	}

}
