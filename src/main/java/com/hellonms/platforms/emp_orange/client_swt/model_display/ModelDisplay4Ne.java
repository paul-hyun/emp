package com.hellonms.platforms.emp_orange.client_swt.model_display;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import com.hellonms.platforms.emp_orange.share.model.network.ne.Model4Ne;
import com.hellonms.platforms.emp_util.string.UtilString;

@SuppressWarnings("serial")
public class ModelDisplay4Ne implements Serializable {

	public static String toNeInfo(int ne_info_code) {
		return UtilString.format("ne_info.{}", ne_info_code);
	}

	public static int toNeInfo(String code) {
		return Integer.parseInt(code.substring("ne_info.".length()));
	}

	public static boolean isNeInfo(String code) {
		return code.startsWith("ne_info.");
	}

	public static String toNe_statistics(int ne_info_code) {
		return UtilString.format("ne_statistics.{}", ne_info_code);
	}

	public static int toNe_statistics(String code) {
		return Integer.parseInt(code.substring("ne_statistics.".length()));
	}

	public static boolean isNe_statistics(String code) {
		return code.startsWith("ne_statistics.");
	}

	private Model4Ne ne;

	private Map<String, Serializable> model_map = new LinkedHashMap<String, Serializable>();

	public Model4Ne getNe() {
		return ne;
	}

	public void setNe(Model4Ne model4Ne) {
		this.ne = model4Ne;
	}

	public void add(ModelDisplay4NeInfo ne_info) {
		model_map.put(UtilString.format("ne_info.{}", ne_info.getNe_info_def()), ne_info);
	}

	public void add(ModelDisplay4NeStatistics ne_statistics) {
		model_map.put(UtilString.format("ne_statistics.{}", ne_statistics.getNe_info_code()), ne_statistics);
	}

	public Object get(String key) {
		return model_map.get(key);
	}

	public Object[] keys() {
		return model_map.keySet().toArray();
	}

	public Object[] values() {
		return model_map.values().toArray();
	}

}
