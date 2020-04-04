package com.hellonms.platforms.emp_orange.share.model.emp_model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;

import com.hellonms.platforms.emp_orange.share.model.fault.event.SEVERITY;
import com.hellonms.platforms.emp_util.string.UtilString;

public final class EMP_MODEL_ENUM {

	private final EMP_MODEL emp_model;
	private final boolean editable;

	private int code;
	private String name;

	private final Map<String, EMP_MODEL_ENUM_FIELD> enum_field_map = new LinkedHashMap<String, EMP_MODEL_ENUM_FIELD>();

	private EMP_MODEL_ENUM_FIELD[] enum_fields = null;

	EMP_MODEL_ENUM(EMP_MODEL emp_model, Element element, boolean editable) {
		this.emp_model = emp_model;
		this.editable = editable;
		this.code = Integer.parseInt(element.attributeValue("code", ""));
		this.name = element.attributeValue("name", "");

		Object[] enum_field_objects = element.elements(EMP_MODEL.ENUM_FIELD).toArray();
		this.enum_fields = new EMP_MODEL_ENUM_FIELD[enum_field_objects.length];
		for (int i = 0; i < enum_field_objects.length; i++) {
			EMP_MODEL_ENUM_FIELD enum_field = new EMP_MODEL_ENUM_FIELD(this, (Element) enum_field_objects[i], editable);
			this.enum_fields[i] = enum_field;
			this.enum_field_map.put(enum_field.getValue(), enum_field);
		}
	}

	EMP_MODEL_ENUM(EMP_MODEL emp_model, int code, boolean editable) {
		this(emp_model, code, "--", editable);
	}

	EMP_MODEL_ENUM(EMP_MODEL emp_model, int code, String name, boolean editable) {
		this.emp_model = emp_model;
		this.editable = editable;
		this.code = code;
		this.name = name;
		this.enum_fields = new EMP_MODEL_ENUM_FIELD[0];
	}

	public void moveUp() {
		if (editable) {
			emp_model.moveUpEnum(this);
		} else {
			throw EMP_MODEL.editError();
		}
	}

	public void moveDown() {
		if (editable) {
			emp_model.moveDownEnum(this);
		} else {
			throw EMP_MODEL.editError();
		}
	}

	public void dispose() {
		if (editable) {
			emp_model.removeEnum(this);
		} else {
			throw EMP_MODEL.editError();
		}
	}

	public int getCode() {
		return code;
	}

	void setCode(int code) {
		this.code = code;
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

	public EMP_MODEL_ENUM_FIELD addEnum_field() {
		if (editable) {
			List<EMP_MODEL_ENUM_FIELD> enum_field_list = new ArrayList<EMP_MODEL_ENUM_FIELD>();
			for (EMP_MODEL_ENUM_FIELD aaa : enum_fields) {
				enum_field_list.add(aaa);
			}
			EMP_MODEL_ENUM_FIELD enum_field = new EMP_MODEL_ENUM_FIELD(this, editable);
			enum_field_list.add(enum_field);

			this.enum_fields = enum_field_list.toArray(new EMP_MODEL_ENUM_FIELD[0]);

			return enum_field;
		} else {
			throw EMP_MODEL.editError();
		}
	}

	public EMP_MODEL_ENUM_FIELD copyEnum_field(EMP_MODEL_ENUM_FIELD enum_field) {
		if (editable) {
			List<EMP_MODEL_ENUM_FIELD> enum_field_list = new ArrayList<EMP_MODEL_ENUM_FIELD>();
			for (EMP_MODEL_ENUM_FIELD aaa : enum_fields) {
				enum_field_list.add(aaa);
			}
			EMP_MODEL_ENUM_FIELD new_enum_field = new EMP_MODEL_ENUM_FIELD(this, editable);
			new_enum_field.setName(UtilString.format("{}_copy", enum_field.getName()));
			new_enum_field.setValue(enum_field.getValue());
			enum_field_list.add(new_enum_field);

			this.enum_fields = enum_field_list.toArray(new EMP_MODEL_ENUM_FIELD[0]);

			return new_enum_field;
		} else {
			throw EMP_MODEL.editError();
		}
	}

	public EMP_MODEL_ENUM_FIELD[] getEnum_fields() {
		return enum_fields;
	}

	public EMP_MODEL_ENUM_FIELD getEnum_field(String value) {
		if (editable) {
			for (EMP_MODEL_ENUM_FIELD enum_field : enum_fields) {
				if (enum_field.getValue().equals(value)) {
					return enum_field;
				}
			}
			return null;
		} else {
			return enum_field_map.get(value);
		}
	}

	EMP_MODEL_ENUM_FIELD moveUpEnum_field(EMP_MODEL_ENUM_FIELD enum_field) {
		if (editable) {
			int index = -1;
			for (int i = 0; i < enum_fields.length; i++) {
				if (enum_field == enum_fields[i]) {
					index = i;
					break;
				}
			}

			if (0 < index) {
				enum_fields[index] = enum_fields[index - 1];
				enum_fields[index - 1] = enum_field;
				return enum_field;
			} else {
				return null;
			}
		} else {
			throw EMP_MODEL.editError();
		}
	}

	EMP_MODEL_ENUM_FIELD moveDownEnum_field(EMP_MODEL_ENUM_FIELD enum_field) {
		if (editable) {
			int index = -1;
			for (int i = 0; i < enum_fields.length; i++) {
				if (enum_field == enum_fields[i]) {
					index = i;
					break;
				}
			}

			if (index + 1 < enum_fields.length) {
				enum_fields[index] = enum_fields[index + 1];
				enum_fields[index + 1] = enum_field;
				return enum_field;
			} else {
				return null;
			}
		} else {
			throw EMP_MODEL.editError();
		}
	}

	EMP_MODEL_ENUM_FIELD removeEnum_field(EMP_MODEL_ENUM_FIELD enum_field) {
		if (editable) {
			EMP_MODEL_ENUM_FIELD enum_field_removed = null;

			List<EMP_MODEL_ENUM_FIELD> enum_field_list = new ArrayList<EMP_MODEL_ENUM_FIELD>();
			for (EMP_MODEL_ENUM_FIELD aaa : enum_fields) {
				if (enum_field != aaa) {
					enum_field_list.add(aaa);
					enum_field_removed = aaa;
				}
			}

			this.enum_fields = enum_field_list.toArray(new EMP_MODEL_ENUM_FIELD[0]);

			return enum_field_removed;
		} else {
			throw EMP_MODEL.editError();
		}
	}

	public SEVERITY getSeverity() {
		if (!UtilString.isName(name)) {
			return SEVERITY.CRITICAL;
		}
		if (!EMP_MODEL_ENUM_FIELD.isEnum_field_unique(enum_fields)) {
			return SEVERITY.CRITICAL;
		}
		if (!isEnum_unique(emp_model.getEnums(), this)) {
			return SEVERITY.CRITICAL;
		}
		if (EMP_MODEL_ENUM_FIELD.isEnum_field_critical(enum_fields)) {
			return SEVERITY.CRITICAL;
		}
		if (emp_model.getNe_info_fields_by_enum(this).length == 0) {
			return SEVERITY.MINOR;
		}
		return SEVERITY.CLEAR;
	}

	public String getError() {
		if (!UtilString.isName(name)) {
			return UtilString.format("잘못된 값 입니다: '{}'.", "Name");
		}
		if (!EMP_MODEL_ENUM_FIELD.isEnum_field_unique(enum_fields)) {
			return UtilString.format("중복되는 필드가 있습니다.");
		}
		if (!isEnum_unique(emp_model.getEnums(), this)) {
			return UtilString.format("중복되는 값이 있습니다.");
		}
		if (EMP_MODEL_ENUM_FIELD.isEnum_field_critical(enum_fields)) {
			return UtilString.format("ENUM_FIELD에 잘못된 값이 있습니다.");
		}
		if (emp_model.getNe_info_fields_by_enum(this).length == 0) {
			return UtilString.format("사용되지 않는 값 입니다.");
		}
		return "정상 입니다.";
	}

	public String getErrorUpdate(String name) {
		if (!UtilString.isName(name)) {
			return UtilString.format("'{}'을 입력하세요.", "Name");
		}
		return null;
	}

	public String getErrorDelete() {
		if (0 < emp_model.getNe_info_fields_by_enum(this).length) {
			return UtilString.format("사용되고 있는 값 입니다.");
		}
		return null;
	}

	private static boolean isEnum_unique(EMP_MODEL_ENUM[] enum_defs, EMP_MODEL_ENUM enum_def) {
		for (EMP_MODEL_ENUM aaa : enum_defs) {
			if (enum_def != aaa) {
				if (enum_def.name.equals(aaa.name)) {
					return false;
				}
			}
		}
		return true;
	}

	// /////////////////////////////////////////////////////////////////

	public Element toElement(Element enum_list) {
		Element enum_def = enum_list.addElement(EMP_MODEL.ENUM);
		enum_def.addAttribute("code", String.valueOf(code));
		enum_def.addAttribute("name", name);
		for (EMP_MODEL_ENUM_FIELD enum_field : enum_fields) {
			enum_field.toElement(enum_def);
		}
		return enum_def;
	}

}
