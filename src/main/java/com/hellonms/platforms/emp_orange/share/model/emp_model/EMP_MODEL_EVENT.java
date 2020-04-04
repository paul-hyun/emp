package com.hellonms.platforms.emp_orange.share.model.emp_model;

import org.dom4j.Element;

import com.hellonms.platforms.emp_orange.share.model.fault.event.SEVERITY;
import com.hellonms.platforms.emp_util.string.UtilString;

public final class EMP_MODEL_EVENT {

	public static final int INTERNAL_ERROR = 0x0901;

	public static final int ICMP_FAIL = 0x0902;

	public static final int SNMP_FAIL = 0x0903;

	static {

	}

	public static boolean isHidden(int code) {
		switch (code) {
		case INTERNAL_ERROR:
		case ICMP_FAIL:
		case SNMP_FAIL:
			return true;
		default:
			return false;
		}
	}

	public static EMP_MODEL_EVENT[] createHiddens(EMP_MODEL emp_model) {
		return new EMP_MODEL_EVENT[] { //
		new EMP_MODEL_EVENT(emp_model, INTERNAL_ERROR, "ServerError", "InternalError", false, false, false), //
				new EMP_MODEL_EVENT(emp_model, ICMP_FAIL, "CommunicationError", "ICMP", true, false, false), //
				new EMP_MODEL_EVENT(emp_model, SNMP_FAIL, "CommunicationError", "SNMP", true, false, false), //
		};
	}

	private final EMP_MODEL emp_model;

	private final boolean editable;

	private int code;

	private String probable_cause;
	private String specific_problem;
	private boolean alarm;
	private boolean audit_alarm;

	EMP_MODEL_EVENT(EMP_MODEL emp_model, Element element, boolean editable) {
		this.emp_model = emp_model;
		this.editable = editable;
		this.code = Integer.parseInt(element.attributeValue("code", "0"));
		this.probable_cause = element.attributeValue("probable_cause", "");
		this.specific_problem = element.attributeValue("specific_problem", "");
		this.alarm = Boolean.parseBoolean(element.attributeValue("alarm", "false"));
		this.audit_alarm = Boolean.parseBoolean(element.attributeValue("audit_alarm", "false"));
	}

	EMP_MODEL_EVENT(EMP_MODEL emp_model, int code, boolean editable) {
		this(emp_model, code, "Equipment", "--", true, false, editable);
	}

	EMP_MODEL_EVENT(EMP_MODEL emp_model, int code, String probable_cause, String specific_problem, boolean alarm, boolean audit_alarm, boolean editable) {
		this.emp_model = emp_model;
		this.editable = editable;
		this.code = code;
		this.probable_cause = probable_cause;
		this.specific_problem = specific_problem;
		this.alarm = alarm;
		this.audit_alarm = audit_alarm;
	}

	public void moveUp() {
		if (editable) {
			emp_model.moveUpEvent(this);
		} else {
			throw EMP_MODEL.editError();
		}
	}

	public void moveDown() {
		if (editable) {
			emp_model.moveDownEvent(this);
		} else {
			throw EMP_MODEL.editError();
		}
	}

	public void dispose() {
		emp_model.removeEvent(this);
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

	public String getProbable_cause() {
		return probable_cause;
	}

	public void setProbable_cause(String probable_cause) {
		if (editable) {
			this.probable_cause = probable_cause;
		} else {
			throw EMP_MODEL.editError();
		}
	}

	public String getSpecific_problem() {
		return specific_problem;
	}

	public void setSpecific_problem(String specific_problem) {
		if (editable) {
			this.specific_problem = specific_problem;
		} else {
			throw EMP_MODEL.editError();
		}
	}

	public boolean isAlarm() {
		return alarm;
	}

	public void setAlarm(boolean alarm) {
		if (editable) {
			this.alarm = alarm;
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

	// /////////////////////////////////////////////////////////////////
	private Long capacity;

	long getCapacity() {
		if (EMP_MODEL_EVENT.isHidden(code)) {
			return 0;
		}

		if (editable) {
			return (long) (isAlarm() ? 1000 : 500);
		} else {
			if (capacity == null) {
				capacity = (long) (isAlarm() ? 1000 : 500);
			}
			return capacity;
		}
	}

	// /////////////////////////////////////////////////////////////////

	public SEVERITY getSeverity() {
		if (!UtilString.isName(probable_cause)) {
			return SEVERITY.CRITICAL;
		}
		if (!UtilString.isName(specific_problem)) {
			return SEVERITY.CRITICAL;
		}
		return SEVERITY.CLEAR;
	}

	public String getError() {
		if (!UtilString.isName(probable_cause)) {
			return UtilString.format("잘못된 값 입니다: '{}'.", "Probable_cause");
		}
		if (!UtilString.isName(specific_problem)) {
			return UtilString.format("잘못된 값 입니다: '{}'.", "Specific_problem");
		}
		return "정상 입니다.";
	}

	public String getErrorUpdate(String probable_cause, String specific_problem, boolean alarm, boolean audit_alarm) {
		if (!UtilString.isName(probable_cause)) {
			return UtilString.format("'{}'을 입력하세요.", "Probable_cause");
		}
		if (!UtilString.isName(specific_problem)) {
			return UtilString.format("'{}'을 입력하세요.", "Specific_problem");
		}
		return null;
	}

	public String getErrorDelete() {
		return null;
	}

	// /////////////////////////////////////////////////////////////////

	public Element toElement(Element event_list) {
		Element event_def = event_list.addElement(EMP_MODEL.EVENT);
		event_def.addAttribute("code", String.valueOf(code));
		event_def.addAttribute("probable_cause", probable_cause);
		event_def.addAttribute("specific_problem", specific_problem);
		event_def.addAttribute("alarm", String.valueOf(alarm));
		event_def.addAttribute("audit_alarm", String.valueOf(audit_alarm));
		return event_def;
	}

}
