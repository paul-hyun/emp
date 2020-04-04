package com.hellonms.platforms.emp_orange.share.model.emp_model;

import java.util.Map;

import org.dom4j.Element;

import com.hellonms.platforms.emp_orange.share.model.fault.event.SEVERITY;
import com.hellonms.platforms.emp_util.string.UtilString;

public final class EMP_MODEL_NE {

	public static final String NE_ICON = "/data/image/node_icon/UNKNOWN.png";
	public static final int CODE_NMS = 0x0900;

	public static boolean isHidden(int code) {
		return CODE_NMS == code;
	}

	public static EMP_MODEL_NE[] createHiddens(EMP_MODEL emp_model) {
		return new EMP_MODEL_NE[] { //
		new EMP_MODEL_NE(emp_model, EMP_MODEL_NE.CODE_NMS, "Hello NMS", "Enterprise Management Platform", "NMS", "--", "/data/image/node_icon/NMS.png", false), //
		};
	}

	// /////////////////////////////////////////////////////////////////////////////////////////////////////
	private final EMP_MODEL emp_model;
	private boolean editable;

	private int code;

	private String manufacturer;
	private String oui;
	private String product_class;
	private String ne_oid;
	private String ne_icon;

	private int[] ne_info_codes;

	EMP_MODEL_NE(EMP_MODEL emp_model, Element element, Map<Integer, EMP_MODEL_NE_INFO> emp_model_map, boolean editable) {
		this.emp_model = emp_model;
		this.editable = editable;
		this.code = Integer.parseInt(element.attributeValue("code", ""));
		this.manufacturer = element.attributeValue("manufacturer", "");
		this.oui = element.attributeValue("oui", "");
		this.product_class = element.attributeValue("product_class", "");
		this.ne_oid = element.attributeValue("ne_oid", "");
		this.ne_icon = element.attributeValue("ne_icon", "");

		Object[] info_objects = element.elements(EMP_MODEL.NE_INFO_CODE).toArray();
		this.ne_info_codes = new int[info_objects.length];
		for (int i = 0; i < info_objects.length; i++) {
			this.ne_info_codes[i] = Integer.parseInt((((Element) info_objects[i]).attributeValue("ne_info_code", "")));
		}
	}

	EMP_MODEL_NE(EMP_MODEL emp_model, int code, boolean editable) {
		this.emp_model = emp_model;
		this.editable = editable;
		this.code = code;
		this.manufacturer = "--";
		this.oui = "--";
		this.product_class = "--";
		this.ne_oid = "";
		this.ne_icon = NE_ICON;
		this.ne_info_codes = new int[0];
	}

	EMP_MODEL_NE(EMP_MODEL emp_model, int code, String manufacturer, String oui, String product_class, String ne_oid, String ne_icon, boolean editable) {
		this.emp_model = emp_model;
		this.editable = editable;
		this.code = code;
		this.manufacturer = manufacturer;
		this.oui = oui;
		this.product_class = product_class;
		this.ne_oid = ne_oid;
		this.ne_icon = ne_icon;
		this.ne_info_codes = new int[0];
	}

	public void moveUp() {
		if (editable) {
			emp_model.moveUpNe(this);
		} else {
			throw EMP_MODEL.editError();
		}
	}

	public void moveDown() {
		if (editable) {
			emp_model.moveDownNe(this);
		} else {
			throw EMP_MODEL.editError();
		}
	}

	public void dispose() {
		if (editable) {
			emp_model.removeNe(this);
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

	public boolean isNMS() {
		return code == EMP_MODEL_NE.CODE_NMS;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		if (editable) {
			this.manufacturer = manufacturer;
		} else {
			throw EMP_MODEL.editError();
		}
	}

	public String getOui() {
		return oui;
	}

	public void setOui(String oui) {
		if (editable) {
			this.oui = oui;
		} else {
			throw EMP_MODEL.editError();
		}
	}

	public String getProduct_class() {
		return product_class;
	}

	public void setProduct_class(String product_class) {
		if (editable) {
			this.product_class = product_class;
		} else {
			throw EMP_MODEL.editError();
		}
	}

	public String getNe_oid() {
		return ne_oid;
	}

	public void setNe_oid(String ne_oid) {
		if (editable) {
			this.ne_oid = ne_oid;
		} else {
			throw EMP_MODEL.editError();
		}
	}

	public String getNe_icon() {
		return ne_icon;
	}

	public void setNe_icon(String ne_icon) {
		if (editable) {
			this.ne_icon = ne_icon;
		} else {
			throw EMP_MODEL.editError();
		}
	}

	public int[] getNe_info_codes() {
		return ne_info_codes;
	}

	public void setNe_info_codes(int[] ne_info_codes) {
		if (editable) {
			if (!isNMS()) {
				this.ne_info_codes = ne_info_codes;
			}
		} else {
			throw EMP_MODEL.editError();
		}
	}

	// /////////////////////////////////////////////////////////////////////////////
	private Long capacity;

	long getCapacity() {
		if (EMP_MODEL_NE.isHidden(code)) {
			return 0;
		}

		if (editable) {
			long capacity = 0L;

			EMP_MODEL_NE_INFO[] ne_infos = emp_model.getNe_infos_by_ne(code);
			for (EMP_MODEL_NE_INFO ne_info : ne_infos) {
				capacity += ne_info.getCapacity();
			}

			EMP_MODEL_EVENT[] events = emp_model.getEvents();
			for (EMP_MODEL_EVENT event : events) {
				capacity += event.getCapacity();
			}
			return capacity;
		} else {
			if (capacity == null) {
				capacity = 0L;

				EMP_MODEL_NE_INFO[] ne_infos = emp_model.getNe_infos_by_ne(code);
				for (EMP_MODEL_NE_INFO ne_info : ne_infos) {
					capacity += ne_info.getCapacity();
				}

				EMP_MODEL_EVENT[] events = emp_model.getEvents();
				for (EMP_MODEL_EVENT event : events) {
					capacity += event.getCapacity();
				}
			}
			return capacity;
		}
	}

	// /////////////////////////////////////////////////////////////////////////////

	public SEVERITY getSeverity() {
		if (UtilString.isEmpty(manufacturer)) {
			return SEVERITY.CRITICAL;
		}
		if (UtilString.isEmpty(oui)) {
			return SEVERITY.CRITICAL;
		}
		if (!UtilString.isName(product_class)) {
			return SEVERITY.CRITICAL;
		}
		if (UtilString.isEmpty(ne_oid)) {
			return SEVERITY.CRITICAL;
		}
		if (!isNMS()) {
			String[] tokens = ne_oid.split("\\.");
			int index_level = 0;
			for (String token : tokens) {
				if (token.equals("*")) {
					index_level++;
				} else {
					if (0 < index_level) {
						return SEVERITY.CRITICAL;
					}
					try {
						Integer.parseInt(token);
					} catch (Exception e) {
						return SEVERITY.CRITICAL;
					}
				}
			}
		}
		return SEVERITY.CLEAR;
	}

	public String getError() {
		if (UtilString.isEmpty(manufacturer)) {
			return UtilString.format("잘못된 값 입니다: '{}'.", "Manufacturer");
		}
		if (UtilString.isEmpty(oui)) {
			return UtilString.format("잘못된 값 입니다: '{}'.", "Oui");
		}
		if (!UtilString.isName(product_class)) {
			return UtilString.format("잘못된 값 입니다: '{}'.", "Product_class");
		}
		if (UtilString.isEmpty(ne_oid)) {
			return UtilString.format("잘못된 값 입니다: '{}'.", "Ne_oid");
		}
		if (!isNMS()) {
			String[] tokens = ne_oid.split("\\.");
			int index_level = 0;
			for (String token : tokens) {
				if (token.equals("*")) {
					index_level++;
				} else {
					if (0 < index_level) {
						return UtilString.format("잘못된 값 입니다: '{}'.", "Ne_oid");
					}
					try {
						Integer.parseInt(token);
					} catch (Exception e) {
						return UtilString.format("잘못된 값 입니다: '{}'.", "Ne_oid");
					}
				}
			}
		}
		return "정상 입니다.";
	}

	public String getErrorUpdate(String manufacturer, String oui, String product_class, String ne_oid, String ne_icon, int[] ne_info_codes) {
		if (UtilString.isEmpty(manufacturer)) {
			return UtilString.format("'{}'을 입력하세요.", "Manufacturer");
		}
		if (UtilString.isEmpty(oui)) {
			return UtilString.format("'{}'을 입력하세요.", "Oui");
		}
		if (!UtilString.isName(product_class)) {
			return UtilString.format("'{}'을 입력하세요.", "Product_class");
		}
		if (UtilString.isEmpty(ne_oid)) {
			return UtilString.format("'{}'을 입력하세요.", "Ne_oid");
		}
		if (isNMS()) {
			return UtilString.format("기본 값 입니다: '{}'.", "NMS");
		}
		return null;
	}

	public String getErrorDelete() {
		if (isNMS()) {
			return UtilString.format("기본 값 입니다: '{}'.", "NMS");
		}
		return null;
	}

	// /////////////////////////////////////////////////////////////////

	public Element toElement(Element ne_list) {
		Element ne_def = ne_list.addElement(EMP_MODEL.NE);
		if (code != 0) {
			ne_def.addAttribute("code", String.valueOf(code));
		}
		if (!UtilString.isEmpty(manufacturer)) {
			ne_def.addAttribute("manufacturer", manufacturer);
		}
		if (!UtilString.isEmpty(oui)) {
			ne_def.addAttribute("oui", oui);
		}
		if (!UtilString.isEmpty(product_class)) {
			ne_def.addAttribute("product_class", product_class);
		}
		if (!UtilString.isEmpty(ne_oid)) {
			ne_def.addAttribute("ne_oid", ne_oid);
		}
		if (!UtilString.isEmpty(ne_icon)) {
			ne_def.addAttribute("ne_icon", ne_icon);
		}
		for (int ne_info_code : ne_info_codes) {
			if (ne_info_code != 0) {
				ne_def.addElement(EMP_MODEL.NE_INFO_CODE).addAttribute("ne_info_code", String.valueOf(ne_info_code));
			}
		}
		return ne_def;
	}

	@Override
	public int hashCode() {
		return code;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof EMP_MODEL_NE) {
			EMP_MODEL_NE ne_def = (EMP_MODEL_NE) obj;

			boolean equals = (code == ne_def.code);
			if (equals) {
				equals = manufacturer.equals(ne_def.manufacturer);
			}
			if (equals) {
				equals = oui.equals(ne_def.oui);
			}
			if (equals) {
				equals = product_class.equals(ne_def.product_class);
			}
			if (equals) {
				equals = ne_icon.equals(ne_def.ne_icon);
			}
			if (equals) {
				equals = (ne_info_codes.length == ne_def.ne_info_codes.length);
				for (int i = 0; i < ne_info_codes.length && equals; i++) {
					equals = (ne_info_codes[i] == ne_def.ne_info_codes[i]);
				}
			}
			return equals;
		} else {
			return false;
		}
	}

}
