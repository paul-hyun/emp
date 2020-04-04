package com.hellonms.platforms.emp_orange.share.model.emp_model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.script.Invocable;
import javax.script.ScriptEngine;

import org.dom4j.Element;

import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_orange.share.error.ERROR_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.fault.event.SEVERITY;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.NE_SESSION_OID;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.NE_SESSION_PROTOCOL;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.NE_SESSION_PROTOCOL_ORANGE;
import com.hellonms.platforms.emp_util.dynamic_code.UtilJavaScript;
import com.hellonms.platforms.emp_util.string.UtilString;

public final class EMP_MODEL_NE_INFO_FIELD {

	public enum STATISTICS_SAVE {
		RAW, //
		COUNTER, //
	}

	public enum STATISTICS_AGGREGATION {
		SUM, //
		AVG, //
		MIN, //
		MAX, //
	}

	public enum THRESHOLD_TYPE {
		GT, //
		LT, //
		BT, //
		OT, //
	}

	// ////////////////////////////////////////////////////////////////////////////////////////

	private final EMP_MODEL_NE_INFO emp_model_info;
	private boolean editable;

	private String name;
	private String display_name;
	private boolean display_enable;
	private String unit;
	private boolean virtual_enable;
	private String oid;
	private NE_SESSION_OID ne_session_oid;
	private String type_remote;
	private EMP_MODEL_TYPE type_local;
	private String field_script;
	private ScriptEngine field_script_engine;
	private int enum_code;
	private boolean index;
	private boolean read;
	private boolean update;

	private boolean stat_label;
	private boolean stat_enable;
	private boolean chart_default;
	private STATISTICS_SAVE stat_save;
	private STATISTICS_AGGREGATION stat_aggr;

	private boolean thr_enable;
	private int thr_event_code;
	private THRESHOLD_TYPE thr_type;
	private Long thr_critical_min;
	private Long thr_critical_max;
	private Long thr_major_min;
	private Long thr_major_max;
	private Long thr_minor_min;
	private Long thr_minor_max;

	EMP_MODEL_NE_INFO_FIELD(EMP_MODEL_NE_INFO emp_model_info, Element element, Map<Integer, EMP_MODEL_ENUM> enum_map, boolean editable) {
		this.emp_model_info = emp_model_info;
		this.editable = editable;
		this.name = element.attributeValue("name", "");
		this.display_name = element.attributeValue("display_name", "");
		this.display_enable = Boolean.parseBoolean(element.attributeValue("display_enable", "false"));
		this.unit = element.attributeValue("unit", "");
		this.virtual_enable = Boolean.parseBoolean(element.attributeValue("virtual_enable", "false"));
		this.oid = element.attributeValue("oid", "");
		this.type_remote = element.attributeValue("type_remote", "");
		this.type_local = EMP_MODEL_TYPE.valueOf(element.attributeValue("type_local", ""));
		this.field_script = element.elementText("field_script");
		if (UtilString.isEmpty(this.field_script)) {
			this.field_script = "";
		}
		String enum_code = element.attributeValue("enum_code", "0").trim();
		this.enum_code = enum_code.length() == 0 ? 0 : Integer.parseInt(enum_code);
		this.index = Boolean.parseBoolean(element.attributeValue("index", "false"));
		this.read = Boolean.parseBoolean(element.attributeValue("read", "false"));
		this.update = Boolean.parseBoolean(element.attributeValue("update", "false"));

		this.stat_label = Boolean.parseBoolean(element.attributeValue("stat_label", "false"));
		this.stat_enable = Boolean.parseBoolean(element.attributeValue("stat_enable", "false"));
		this.chart_default = Boolean.parseBoolean(element.attributeValue("chart_default", "false"));
		String stat_save = element.attributeValue("stat_save", "").trim();
		this.stat_save = stat_save.length() == 0 ? null : STATISTICS_SAVE.valueOf(stat_save);
		String stat_aggr = element.attributeValue("stat_aggr", "").trim();
		this.stat_aggr = stat_aggr.length() == 0 ? null : STATISTICS_AGGREGATION.valueOf(stat_aggr);

		this.thr_enable = Boolean.parseBoolean(element.attributeValue("thr_enable", "false"));
		this.thr_event_code = Integer.parseInt(element.attributeValue("thr_event_code", "0"));
		String thr_type = element.attributeValue("thr_type", "").trim();
		this.thr_type = thr_type.length() == 0 ? null : THRESHOLD_TYPE.valueOf(thr_type);
		String thr_critical_min = element.attributeValue("thr_critical_min", "").trim();
		this.thr_critical_min = thr_critical_min.length() == 0 ? null : Long.parseLong(thr_critical_min);
		String thr_critical_max = element.attributeValue("thr_critical_max", "").trim();
		this.thr_critical_max = thr_critical_max.length() == 0 ? null : Long.parseLong(thr_critical_max);
		String thr_major_min = element.attributeValue("thr_major_min", "").trim();
		this.thr_major_min = thr_major_min.length() == 0 ? null : Long.parseLong(thr_major_min);
		String thr_major_max = element.attributeValue("thr_major_max", "").trim();
		this.thr_major_max = thr_major_max.length() == 0 ? null : Long.parseLong(thr_major_max);
		String thr_minor_min = element.attributeValue("thr_minor_min", "").trim();
		this.thr_minor_min = thr_minor_min.length() == 0 ? null : Long.parseLong(thr_minor_min);
		String thr_minor_max = element.attributeValue("thr_minor_max", "").trim();
		this.thr_minor_max = thr_minor_max.length() == 0 ? null : Long.parseLong(thr_minor_max);
	}

	EMP_MODEL_NE_INFO_FIELD(EMP_MODEL_NE_INFO emp_model_info, boolean editable) {
		this(emp_model_info, "--", "", true, "", "", "", null, 0, false, true, false, editable);
	}

	EMP_MODEL_NE_INFO_FIELD(EMP_MODEL_NE_INFO emp_model_info, String name, String display_name, boolean display_enable, String unit, String type_remote, String oid, EMP_MODEL_TYPE type_local, int enum_code, boolean index, boolean read, boolean update, boolean editable) {
		this.emp_model_info = emp_model_info;
		this.editable = editable;
		this.name = name;
		this.display_name = display_name;
		this.display_enable = display_enable;
		this.unit = unit;
		this.type_remote = type_remote;
		this.oid = oid;
		this.type_local = type_local;
		this.field_script = "";
		this.enum_code = enum_code;
		this.index = index;
		this.read = read;
		this.update = update;
	}

	public void moveUp() {
		if (editable) {
			emp_model_info.moveUpNe_info_field(this);
		} else {
			throw EMP_MODEL.editError();
		}
	}

	public void moveDown() {
		if (editable) {
			emp_model_info.moveDownNe_info_field(this);
		} else {
			throw EMP_MODEL.editError();
		}
	}

	public void dispose() {
		if (editable) {
			emp_model_info.removeNe_info_field(this);
		} else {
			throw EMP_MODEL.editError();
		}
	}

	public EMP_MODEL_NE_INFO getNe_info() {
		return emp_model_info;
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

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		if (editable) {
			this.unit = unit;
		} else {
			throw EMP_MODEL.editError();
		}
	}

	public boolean isVirtual_enable() {
		return virtual_enable;
	}

	public void setVirtual_enable(boolean virtual_enable) {
		if (editable) {
			this.virtual_enable = virtual_enable;
		} else {
			throw EMP_MODEL.editError();
		}
	}

	public NE_SESSION_OID getNe_session_oid() {
		if (virtual_enable) {
			return null;
		} else if (editable) {
			return EMP_MODEL_NE_INFO_FIELD.getNe_session_oid(emp_model_info.getProtocol(), oid);
		} else {
			if (ne_session_oid == null) {
				ne_session_oid = EMP_MODEL_NE_INFO_FIELD.getNe_session_oid(emp_model_info.getProtocol(), oid);
			}
			return ne_session_oid;
		}
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		if (editable) {
			this.oid = oid;
		} else {
			throw EMP_MODEL.editError();
		}
	}

	public String getType_remote() {
		return type_remote;
	}

	public void setType_remote(String type_remote) {
		if (editable) {
			this.type_remote = type_remote;
		} else {
			throw EMP_MODEL.editError();
		}
	}

	public EMP_MODEL_TYPE getType_local() {
		return type_local;
	}

	public void setType_local(EMP_MODEL_TYPE type_local) {
		if (editable) {
			this.type_local = type_local;
		} else {
			throw EMP_MODEL.editError();
		}
	}

	public String getField_script() {
		return field_script;
	}

	public void setField_script(String field_script) {
		if (editable) {
			this.field_script = field_script;
		} else {
			throw EMP_MODEL.editError();
		}
	}

	public Serializable evalField_script(Map<String, Object> model) throws EmpException {
		try {
			if (virtual_enable && !UtilString.isEmpty(field_script)) {
				emp_model_info.script_lock.lock();
				try {
					if (field_script_engine == null) {
						StringBuilder script = new StringBuilder();
						script.append("var ne_field_def = new Object(); ne_field_def.field_script = function(model) {\n").append(field_script).append("\n}");
						field_script_engine = UtilJavaScript.evalScript(script.toString());
					}
					Invocable inv = (Invocable) field_script_engine;
					Object ne_field_def = field_script_engine.get("ne_field_def");
					inv.invokeMethod(ne_field_def, "field_script", model);
					Object vvv = model.get(this.name);
					return this.type_local.fromScript(vvv);
				} finally {
					emp_model_info.script_lock.unlock();
				}
			}
			return null;
		} catch (Exception e) {
			throw new EmpException(e, ERROR_CODE_ORANGE.FILE_IO);
		}
	}

	public int getEnum_code() {
		return enum_code;
	}

	public void setEnum_code(int enum_code) {
		if (editable) {
			this.enum_code = enum_code;
		} else {
			throw EMP_MODEL.editError();
		}
	}

	public boolean isIndex() {
		return index;
	}

	public void setIndex(boolean index) {
		if (editable) {
			this.index = index;
		} else {
			throw EMP_MODEL.editError();
		}
	}

	public int getIndex_length() {
		return virtual_enable ? 0 : EMP_MODEL_NE_INFO_FIELD.getIndex_length(emp_model_info.getProtocol(), oid);
	}

	public boolean isTable() {
		return virtual_enable ? false : EMP_MODEL_NE_INFO_FIELD.isTable(emp_model_info.getProtocol(), oid);
	}

	public boolean isRead() {
		return read;
	}

	public void setRead(boolean read) {
		if (editable) {
			this.read = read;
		} else {
			throw EMP_MODEL.editError();
		}
	}

	public boolean isUpdate() {
		return update;
	}

	public void setUpdate(boolean update) {
		if (editable) {
			this.update = update;
		} else {
			throw EMP_MODEL.editError();
		}
	}

	public boolean isStat_label() {
		return stat_label;
	}

	public void setStat_label(boolean stat_label) {
		if (editable) {
			this.stat_label = stat_label;
		} else {
			throw EMP_MODEL.editError();
		}
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

	public boolean isChart_default() {
		return chart_default;
	}

	public void setChart_default(boolean chart_default) {
		this.chart_default = chart_default;
	}

	public STATISTICS_SAVE getStat_save() {
		return stat_save;
	}

	public void setStat_save(STATISTICS_SAVE stat_save) {
		if (editable) {
			this.stat_save = stat_save;
		} else {
			throw EMP_MODEL.editError();
		}
	}

	public STATISTICS_AGGREGATION getStat_aggr() {
		return stat_aggr;
	}

	public void setStat_aggr(STATISTICS_AGGREGATION stat_aggr) {
		if (editable) {
			this.stat_aggr = stat_aggr;
		} else {
			throw EMP_MODEL.editError();
		}
	}

	public boolean isThr_enable() {
		return thr_enable;
	}

	public void setThr_enable(boolean thr_enable) {
		if (editable) {
			this.thr_enable = thr_enable;
		} else {
			throw EMP_MODEL.editError();
		}
	}

	public int getThr_event_code() {
		return thr_event_code;
	}

	public void setThr_event_code(int thr_event_code) {
		if (editable) {
			this.thr_event_code = thr_event_code;
		} else {
			throw EMP_MODEL.editError();
		}
	}

	public THRESHOLD_TYPE getThr_type() {
		return thr_type;
	}

	public void setThr_type(THRESHOLD_TYPE thr_type) {
		if (editable) {
			this.thr_type = thr_type;
		} else {
			throw EMP_MODEL.editError();
		}
	}

	public boolean isThr_critical_enable() {
		return thr_critical_min != null || thr_critical_max != null;
	}

	public Long getThr_critical_min() {
		return thr_critical_min;
	}

	public void setThr_critical_min(Long thr_critical_min) {
		if (editable) {
			this.thr_critical_min = thr_critical_min;
		} else {
			throw EMP_MODEL.editError();
		}
	}

	public Long getThr_critical_max() {
		return thr_critical_max;
	}

	public void setThr_critical_max(Long thr_critical_max) {
		if (editable) {
			this.thr_critical_max = thr_critical_max;
		} else {
			throw EMP_MODEL.editError();
		}
	}

	public boolean isThr_major_enable() {
		return thr_major_min != null || thr_major_max != null;
	}

	public Long getThr_major_min() {
		return thr_major_min;
	}

	public void setThr_major_min(Long thr_major_min) {
		if (editable) {
			this.thr_major_min = thr_major_min;
		} else {
			throw EMP_MODEL.editError();
		}
	}

	public Long getThr_major_max() {
		return thr_major_max;
	}

	public void setThr_major_max(Long thr_major_max) {
		if (editable) {
			this.thr_major_max = thr_major_max;
		} else {
			throw EMP_MODEL.editError();
		}
	}

	public boolean isThr_minor_enable() {
		return thr_minor_min != null || thr_minor_max != null;
	}

	public Long getThr_minor_min() {
		return thr_minor_min;
	}

	public void setThr_minor_min(Long thr_minor_min) {
		if (editable) {
			this.thr_minor_min = thr_minor_min;
		} else {
			throw EMP_MODEL.editError();
		}
	}

	public Long getThr_minor_max() {
		return thr_minor_max;
	}

	public void setThr_minor_max(Long thr_minor_max) {
		if (editable) {
			this.thr_minor_max = thr_minor_max;
		} else {
			throw EMP_MODEL.editError();
		}
	}

	public SEVERITY getSeverity() {
		if (!UtilString.isName(name)) {
			return SEVERITY.CRITICAL;
		}
		if (!isInfo_field_unique(emp_model_info.getNe_info_fields(), this)) {
			return SEVERITY.CRITICAL;
		}
		if (!virtual_enable && UtilString.isEmpty(oid)) {
			return SEVERITY.CRITICAL;
		}
		if (!virtual_enable && UtilString.isEmpty(type_remote)) {
			return SEVERITY.CRITICAL;
		}
		if (type_local == null) {
			return SEVERITY.CRITICAL;
		}
		if (!virtual_enable) {
			String error = EMP_MODEL_NE_INFO_FIELD.getError(emp_model_info.getProtocol(), oid, type_remote, type_local, enum_code, index, emp_model_info.getNe_info_indexs().length);
			if (error != null) {
				return SEVERITY.CRITICAL;
			}
		}
		if (stat_enable) {
			if (stat_save == null) {
				return SEVERITY.CRITICAL;
			}
			if (stat_aggr == null) {
				return SEVERITY.CRITICAL;
			}
		}
		if (thr_enable) {
			if (thr_event_code == 0) {
				return SEVERITY.CRITICAL;
			}
			if (thr_type == null) {
				return SEVERITY.CRITICAL;
			}
		}
		return SEVERITY.CLEAR;
	}

	public String getError() {
		if (!UtilString.isName(name)) {
			return UtilString.format("잘못된 값 입니다: '{}'.", "Name");
		}
		if (!isInfo_field_unique(emp_model_info.getNe_info_fields(), this)) {
			return UtilString.format("중복되는 값이 있습니다.");
		}
		if (!virtual_enable && UtilString.isEmpty(oid)) {
			return UtilString.format("잘못된 값 입니다: '{}'.", "Oid");
		}
		if (!virtual_enable && UtilString.isEmpty(type_remote)) {
			return UtilString.format("잘못된 값 입니다: '{}'.", "Type_remote");
		}
		if (type_local == null) {
			return UtilString.format("잘못된 값 입니다: '{}'.", "Type_local");
		}
		if (!virtual_enable) {
			String error = EMP_MODEL_NE_INFO_FIELD.getError(emp_model_info.getProtocol(), oid, type_remote, type_local, enum_code, index, emp_model_info.getNe_info_indexs().length);
			if (error != null) {
				return error;
			}
		}
		if (stat_enable) {
			if (stat_save == null) {
				return UtilString.format("잘못된 값 입니다: '{}'.", "Stat_save");
			}
			if (stat_aggr == null) {
				return UtilString.format("잘못된 값 입니다: '{}'.", "Stat_aggr");
			}
		}
		if (thr_enable) {
			if (thr_event_code == 0) {
				return UtilString.format("잘못된 값 입니다: '{}'.", "Thr_event_code");
			}
			if (thr_type == null) {
				return UtilString.format("잘못된 값 입니다: '{}'.", "Thr_type");
			}
		}
		return "정상 입니다.";
	}

	public String getErrorUpdate(String name, boolean virtual_enable, String oid, String type_remote, EMP_MODEL_TYPE type_local, String field_script, int enum_code, boolean index, boolean read, boolean update) {
		if (!UtilString.isName(name)) {
			return UtilString.format("'{}'을 입력하세요.", "Name");
		}
		if (!virtual_enable && UtilString.isEmpty(oid)) {
			return UtilString.format("'{}'을 입력하세요.", "Oid");
		}
		if (!virtual_enable && UtilString.isEmpty(type_remote)) {
			return UtilString.format("'{}'을 입력하세요.", "Type_remote");
		}
		if (type_local == null) {
			return UtilString.format("잘못된 값 입니다: '{}'.", "Type_local");
		}
		if (!virtual_enable) {
			String error = EMP_MODEL_NE_INFO_FIELD.getErrorUpdate(emp_model_info.getProtocol(), oid, type_remote, type_local, enum_code, index);
			if (error != null) {
				return error;
			}
		}
		return null;
	}

	public String getErrorUpdate(boolean virtual_enable, boolean stat_enable, boolean stat_label, STATISTICS_SAVE stat_save, STATISTICS_AGGREGATION stat_aggr) {
		if (!virtual_enable && stat_enable && stat_save == null) {
			return UtilString.format("'{}'을 입력하세요.", "Stat_save");
		}
		if (!virtual_enable && stat_enable && stat_aggr == null) {
			return UtilString.format("'{}'을 입력하세요.", "Stat_aggr");
		}
		return null;
	}

	public String getErrorUpdate(boolean virtual_enable, boolean stat_enable, boolean thr_enable, int thr_event_code, THRESHOLD_TYPE thr_type, Long thr_critical_min, Long thr_critical_max, Long thr_major_min, Long thr_major_max, Long thr_minor_min, Long thr_minor_max) {
		if (!virtual_enable && stat_enable && thr_enable && thr_event_code == 0) {
			return UtilString.format("'{}'을 입력하세요.", "Thr_event_code");
		}
		if (!virtual_enable && stat_enable && thr_enable && thr_type == null) {
			return UtilString.format("'{}'을 입력하세요.", "Thr_type");
		}
		return null;
	}

	public String getErrorDelete() {
		return null;
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public String toString() {
		return name;
	}

	public Element toElement(Element ne_info_def) {
		Element info_field = ne_info_def.addElement(EMP_MODEL.NE_INFO_FIELD);
		if (!UtilString.isEmpty(name)) {
			info_field.addAttribute("name", String.valueOf(name));
		}
		if (!UtilString.isEmpty(display_name)) {
			info_field.addAttribute("display_name", String.valueOf(display_name));
		}
		if (display_enable != false) {
			info_field.addAttribute("display_enable", String.valueOf(display_enable));
		}
		if (!UtilString.isEmpty(unit)) {
			info_field.addAttribute("unit", String.valueOf(unit));
		}
		if (virtual_enable != false) {
			info_field.addAttribute("virtual_enable", String.valueOf(virtual_enable));
		}
		if (!UtilString.isEmpty(oid)) {
			info_field.addAttribute("oid", String.valueOf(oid));
		}
		if (!UtilString.isEmpty(type_remote)) {
			info_field.addAttribute("type_remote", String.valueOf(type_remote));
		}
		if (type_local != null) {
			info_field.addAttribute("type_local", String.valueOf(type_local));
		}
		if (!UtilString.isEmpty(field_script)) {
			info_field.addElement("field_script").addCDATA(field_script);
		}
		if (enum_code != 0) {
			info_field.addAttribute("enum_code", String.valueOf(enum_code));
		}
		if (index != false) {
			info_field.addAttribute("index", String.valueOf(index));
		}
		if (read != false) {
			info_field.addAttribute("read", String.valueOf(read));
		}
		if (update != false) {
			info_field.addAttribute("update", String.valueOf(update));
		}

		if (stat_label != false) {
			info_field.addAttribute("stat_label", String.valueOf(stat_label));
		}
		if (stat_enable != false) {
			info_field.addAttribute("stat_enable", String.valueOf(stat_enable));
		}
		if (chart_default != false) {
			info_field.addAttribute("chart_default", String.valueOf(chart_default));
		}
		if (stat_save != null) {
			info_field.addAttribute("stat_save", String.valueOf(stat_save));
		}
		if (stat_aggr != null) {
			info_field.addAttribute("stat_aggr", String.valueOf(stat_aggr));
		}

		if (thr_enable != false) {
			info_field.addAttribute("thr_enable", String.valueOf(thr_enable));
		}
		if (thr_event_code != 0) {
			info_field.addAttribute("thr_event_code", String.valueOf(thr_event_code));
		}
		if (thr_type != null) {
			info_field.addAttribute("thr_type", String.valueOf(thr_type));
		}
		if (thr_critical_min != null) {
			info_field.addAttribute("thr_critical_min", String.valueOf(thr_critical_min));
		}
		if (thr_critical_max != null) {
			info_field.addAttribute("thr_critical_max", String.valueOf(thr_critical_max));
		}
		if (thr_major_min != null) {
			info_field.addAttribute("thr_major_min", String.valueOf(thr_major_min));
		}
		if (thr_major_max != null) {
			info_field.addAttribute("thr_major_max", String.valueOf(thr_major_max));
		}
		if (thr_minor_min != null) {
			info_field.addAttribute("thr_minor_min", String.valueOf(thr_minor_min));
		}
		if (thr_minor_max != null) {
			info_field.addAttribute("thr_minor_max", String.valueOf(thr_minor_max));
		}

		return info_field;
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof EMP_MODEL_NE_INFO_FIELD) {
			EMP_MODEL_NE_INFO_FIELD ne_info_field_def = (EMP_MODEL_NE_INFO_FIELD) obj;

			boolean equals = name == null ? ne_info_field_def.name == null : name.equals(ne_info_field_def.name);
			if (equals) {
				equals = display_name == null ? ne_info_field_def.display_name == null : display_name.equals(ne_info_field_def.display_name);
			}
			if (equals) {
				equals = ne_info_field_def.display_enable == display_enable;
			}
			if (equals) {
				equals = unit == null ? ne_info_field_def.unit == null : unit.equals(ne_info_field_def.unit);
			}
			if (equals) {
				equals = ne_info_field_def.virtual_enable == virtual_enable;
			}
			if (equals) {
				equals = oid == null ? ne_info_field_def.oid == null : oid.equals(ne_info_field_def.oid);
			}
			if (equals) {
				equals = type_remote == null ? ne_info_field_def.type_remote == null : type_remote.equals(ne_info_field_def.type_remote);
			}
			if (equals) {
				equals = (type_local == ne_info_field_def.type_local);
			}
			if (equals) {
				equals = (enum_code == ne_info_field_def.enum_code);
			}
			if (equals) {
				equals = (index == ne_info_field_def.index);
			}
			if (equals) {
				equals = (read == ne_info_field_def.read);
			}
			if (equals) {
				equals = (update == ne_info_field_def.update);
			}
			return equals;
		} else {
			return false;
		}
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////

	private static boolean isInfo_field_unique(EMP_MODEL_NE_INFO_FIELD[] info_fields, EMP_MODEL_NE_INFO_FIELD info_field) {
		for (EMP_MODEL_NE_INFO_FIELD aaa : info_fields) {
			if (info_field != aaa) {
				if (info_field.name.equals(aaa.name)) {
					return false;
				}
			}
		}
		return true;
	}

	static boolean isInfo_field_unique(EMP_MODEL_NE_INFO_FIELD[] info_fields) {
		Set<String> name_set = new HashSet<String>();
		for (EMP_MODEL_NE_INFO_FIELD aaa : info_fields) {
			if (name_set.contains(aaa.name)) {
				return false;
			}
			name_set.add(aaa.name);
		}
		return true;
	}

	static boolean isInfo_field_critical(EMP_MODEL_NE_INFO_FIELD[] info_fields) {
		for (EMP_MODEL_NE_INFO_FIELD aaa : info_fields) {
			if (aaa.getSeverity().equals(SEVERITY.CRITICAL)) {
				return true;
			}
		}
		return false;
	}

	static boolean isInfo_field_index_critical(EMP_MODEL_NE_INFO_FIELD[] info_fields) {
		int index_length = info_fields.length == 0 ? 0 : info_fields[0].getIndex_length();
		for (EMP_MODEL_NE_INFO_FIELD aaa : info_fields) {
			if (!aaa.isVirtual_enable() && index_length != aaa.getIndex_length()) {
				return true;
			}
		}
		return false;
	}

	public static String[] getType_reals(NE_SESSION_PROTOCOL protocol) {
		if (protocol.getProtocl_code() == NE_SESSION_PROTOCOL_ORANGE.SNMP.getProtocl_code()) {
			return EMP_MODEL_NE_INFO_FIELD_SNMP.getType_reals();
		} else {
			return new String[0];
		}
	}

	public static int getIndex_length(NE_SESSION_PROTOCOL protocol, String oid) {
		if (protocol.getProtocl_code() == NE_SESSION_PROTOCOL_ORANGE.SNMP.getProtocl_code()) {
			return EMP_MODEL_NE_INFO_FIELD_SNMP.getIndex_length(oid);
		} else {
			return 0;
		}
	}

	public static boolean isTable(NE_SESSION_PROTOCOL protocol, String oid) {
		if (protocol.getProtocl_code() == NE_SESSION_PROTOCOL_ORANGE.SNMP.getProtocl_code()) {
			return EMP_MODEL_NE_INFO_FIELD_SNMP.isTable(oid);
		} else {
			return false;
		}
	}

	public static NE_SESSION_OID getNe_session_oid(NE_SESSION_PROTOCOL protocol, String oid) {
		if (protocol.getProtocl_code() == NE_SESSION_PROTOCOL_ORANGE.SNMP.getProtocl_code()) {
			return EMP_MODEL_NE_INFO_FIELD_SNMP.getNe_session_oid(oid);
		} else {
			return null;
		}
	}

	private static String getError(NE_SESSION_PROTOCOL protocol, String oid, String type_remote, EMP_MODEL_TYPE type_local, int enum_code, boolean index, int index_count) {
		if (protocol.getProtocl_code() == NE_SESSION_PROTOCOL_ORANGE.SNMP.getProtocl_code()) {
			return EMP_MODEL_NE_INFO_FIELD_SNMP.getError(oid, type_remote, type_local, enum_code, index, index_count);
		} else {
			return null;
		}
	}

	private static String getErrorUpdate(NE_SESSION_PROTOCOL protocol, String oid, String type_remote, EMP_MODEL_TYPE type_local, int enum_code, boolean index) {
		if (protocol.getProtocl_code() == NE_SESSION_PROTOCOL_ORANGE.SNMP.getProtocl_code()) {
			return EMP_MODEL_NE_INFO_FIELD_SNMP.getErrorUpdate(oid, type_remote, type_local, enum_code, index);
		} else {
			return null;
		}
	}

}
