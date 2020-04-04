package com.hellonms.platforms.emp_orange.share.model.emp_model;

import java.util.HashSet;
import java.util.Set;

import org.dom4j.Element;

import com.hellonms.platforms.emp_orange.share.model.fault.event.SEVERITY;
import com.hellonms.platforms.emp_util.string.UtilString;

public final class EMP_MODEL_ENUM_FIELD {

	transient private final EMP_MODEL_ENUM emp_model_enum;

	// /////////////////////////////////////////////////////////////////////////
	private boolean editable;

	private String name;
	private String value;

	EMP_MODEL_ENUM_FIELD(EMP_MODEL_ENUM emp_model_enum, Element element, boolean editable) {
		this.emp_model_enum = emp_model_enum;
		this.editable = editable;
		this.name = element.attributeValue("name", "--");
		this.value = element.attributeValue("value", "");
	}

	EMP_MODEL_ENUM_FIELD(EMP_MODEL_ENUM emp_model_enum, boolean editable) {
		this(emp_model_enum, "--", "", editable);
	}

	EMP_MODEL_ENUM_FIELD(EMP_MODEL_ENUM emp_model_enum, String name, String value, boolean editable) {
		this.emp_model_enum = emp_model_enum;
		this.editable = editable;
		this.name = name;
		this.value = value;
	}

	public void moveUp() {
		if (editable) {
			emp_model_enum.moveUpEnum_field(this);
		} else {
			throw EMP_MODEL.editError();
		}
	}

	public void moveDown() {
		if (editable) {
			emp_model_enum.moveDownEnum_field(this);
		} else {
			throw EMP_MODEL.editError();
		}
	}

	public void dispose() {
		if (editable) {
			emp_model_enum.removeEnum_field(this);
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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		if (editable) {
			this.value = value;
		} else {
			throw EMP_MODEL.editError();
		}
	}

	public EMP_MODEL_ENUM getEnum() {
		return emp_model_enum;
	}

	public SEVERITY getSeverity() {
		if (UtilString.isEmpty(name)) {
			return SEVERITY.CRITICAL;
		}
		if (UtilString.isEmpty(value)) {
			return SEVERITY.CRITICAL;
		}
		if (!isEnum_field_unique(emp_model_enum.getEnum_fields(), this)) {
			return SEVERITY.CRITICAL;
		}
		return SEVERITY.CLEAR;
	}

	public String getError() {
		if (UtilString.isEmpty(name)) {
			return UtilString.format("잘못된 값 입니다: '{}'.", "이름");
		}
		if (UtilString.isEmpty(value)) {
			return UtilString.format("잘못된 값 입니다: '{}'.", "값");
		}
		if (!isEnum_field_unique(emp_model_enum.getEnum_fields(), this)) {
			return UtilString.format("중복되는 값이 있습니다.");
		}
		return "정상 입니다.";
	}

	public String getErrorUpdate(String name, String value) {
		if (UtilString.isEmpty(name)) {
			return UtilString.format("'{}'을 입력하세요.", "이름");
		}
		if (UtilString.isEmpty(value)) {
			return UtilString.format("'{}'을 입력하세요.", "값");
		}
		return null;
	}

	public String getErrorDelete() {
		return null;
	}

	private static boolean isEnum_field_unique(EMP_MODEL_ENUM_FIELD[] enum_fields, EMP_MODEL_ENUM_FIELD enum_field) {
		for (EMP_MODEL_ENUM_FIELD aaa : enum_fields) {
			if (enum_field != aaa) {
				if (enum_field.name.equals(aaa.name)) {
					return false;
				}
				if (enum_field.value.equals(aaa.value)) {
					return false;
				}
			}
		}
		return true;
	}

	static boolean isEnum_field_unique(EMP_MODEL_ENUM_FIELD[] enum_fields) {
		Set<String> name_set = new HashSet<String>();
		Set<String> value_set = new HashSet<String>();
		for (EMP_MODEL_ENUM_FIELD aaa : enum_fields) {
			if (name_set.contains(aaa.name)) {
				return false;
			}
			name_set.add(aaa.name);
			if (value_set.contains(aaa.value)) {
				return false;
			}
			value_set.add(aaa.value);
		}
		return true;
	}

	static boolean isEnum_field_critical(EMP_MODEL_ENUM_FIELD[] enum_fields) {
		for (EMP_MODEL_ENUM_FIELD aaa : enum_fields) {
			if (aaa.getSeverity().equals(SEVERITY.CRITICAL)) {
				return true;
			}
		}
		return false;
	}

	public Element toElement(Element enum_def) {
		Element enum_field = enum_def.addElement(EMP_MODEL.ENUM_FIELD);
		enum_field.addAttribute("name", name);
		enum_field.addAttribute("value", value);
		return enum_field;
	}

}
