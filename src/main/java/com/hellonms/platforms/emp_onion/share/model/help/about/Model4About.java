/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_onion.share.model.help.about;

import com.hellonms.platforms.emp_core.share.model.ModelIf;

/**
 * <p>
 * EMP 정보 모델
 * </p>
 * 
 * @since 1.6
 * @create 2015. 3. 10.
 * @modified 2015. 3. 10.
 * @author cchyun
 * 
 */
@SuppressWarnings("serial")
public final class Model4About implements ModelIf {

	private String manufacturer;

	private String oui;

	private String product_class;

	private String version;

	private String build_id;

	private String product_name;

	private String about;

	private boolean develop_mode;

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getOui() {
		return oui;
	}

	public void setOui(String oui) {
		this.oui = oui;
	}

	public String getProduct_class() {
		return product_class;
	}

	public void setProduct_class(String product_class) {
		this.product_class = product_class;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getBuild_id() {
		return build_id;
	}

	public void setBuild_id(String build_id) {
		this.build_id = build_id;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public boolean isDevelop_mode() {
		return develop_mode;
	}

	public void setDevelop_mode(boolean develop_mode) {
		this.develop_mode = develop_mode;
	}

	@Override
	public Model4About copy() {
		Model4About model = new Model4About();
		model.manufacturer = manufacturer;
		model.oui = oui;
		model.product_class = product_class;
		model.version = version;
		model.build_id = build_id;
		model.product_name = product_name;
		model.about = about;
		model.develop_mode = develop_mode;
		return model;
	}

	@Override
	public String toString() {
		return toString("");
	}

	@Override
	public String toString(String indent) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(indent).append(getClass().getName()).append(S_LB).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("manufacturer").append(S_DL).append(manufacturer).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("oui").append(S_DL).append(oui).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("product_class").append(S_DL).append(product_class).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("version").append(S_DL).append(version).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("build_id").append(S_DL).append(build_id).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("product_name").append(S_DL).append(product_name).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("about").append(S_DL).append(about).append(S_NL);
		stringBuilder.append(indent).append(S_RB);
		return stringBuilder.toString();
	}

	@Override
	public String toDisplayString(String indent) {
		return toString(indent);
	}

}
