/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.share.model.network.ne_info;

import java.io.Serializable;

import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO_FIELD;

/**
 * <p>
 * NE_INFO INDEX
 * </p>
 *
 * @since 1.6
 * @create 2015. 4. 24.
 * @modified 2015. 4. 24.
 * @author cchyun
 *
 */
@SuppressWarnings("serial")
public class NE_INFO_INDEX implements Serializable {

	public static String toDatabase(String... values) {
		StringBuilder stringBuilder = new StringBuilder();
		for (String ne_field_index_value : values) {
			stringBuilder.append(ne_field_index_value.replaceAll("\r", "").replaceAll("\n", "")).append("\n");
		}
		if (0 < stringBuilder.length()) {
			stringBuilder.setLength(stringBuilder.length() - 1);
		}
		return stringBuilder.toString();
	}

	public static String[] toModel(String value) {
		return value.split("\n");
	}

	private int ne_info_index;

	private String[] ne_field_index_values;

	public NE_INFO_INDEX(int ne_info_index, String[] ne_field_index_values) {
		this.ne_info_index = ne_info_index;
		this.ne_field_index_values = ne_field_index_values;
	}

	public int getNe_info_index() {
		return ne_info_index;
	}

	public String[] getNe_field_index_values() {
		return ne_field_index_values;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof NE_INFO_INDEX) {
			return (ne_info_index == ((NE_INFO_INDEX) obj).ne_info_index);
		} else {
			return false;
		}
	}

	public String toString(EMP_MODEL_NE_INFO ne_info_def) {
		StringBuilder index_string = new StringBuilder();
		StringBuilder stat_string = new StringBuilder();
		int index = 0;
		for (EMP_MODEL_NE_INFO_FIELD ne_info_field_def : ne_info_def.getNe_info_fields()) {
			if (ne_field_index_values.length <= index) {
				break;
			}
			if (ne_info_field_def.isStat_label()) {
				stat_string.append(stat_string.length() == 0 ? "" : ".").append(ne_field_index_values[index++]);
			} else if (ne_info_field_def.isIndex()) {
				index_string.append(index_string.length() == 0 ? "" : ".").append(ne_field_index_values[index++]);
			}
		}
		return stat_string.length() == 0 ? index_string.toString() : stat_string.toString();
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		for (String ne_field_index_value : ne_field_index_values) {
			stringBuilder.append(stringBuilder.length() == 0 ? "" : ".").append(ne_field_index_value);
		}
		return stringBuilder.toString();
	}

	@Override
	public int hashCode() {
		return ne_info_index;
	}

}
