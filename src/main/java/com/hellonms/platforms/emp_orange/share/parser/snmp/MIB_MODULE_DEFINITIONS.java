package com.hellonms.platforms.emp_orange.share.parser.snmp;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.hellonms.platforms.emp_util.string.UtilString;

public class MIB_MODULE_DEFINITIONS {

	private String name;

	private List<MIB_MODULE_ITEM> item_list = new ArrayList<MIB_MODULE_ITEM>();

	private List<MIB_IMPORTS> import_list = new ArrayList<MIB_IMPORTS>();

	private Map<String, MIB_SYNTAX> syntax_map = new LinkedHashMap<String, MIB_SYNTAX>();

	private Map<String, MIB_NODE> node_map = new LinkedHashMap<String, MIB_NODE>();

	MIB_MODULE_DEFINITIONS() {
	}

	public String getName() {
		return name;
	}

	void setName(String name) {
		this.name = name;
	}

	void addItem(MIB_MODULE_ITEM item) {
		item_list.add(item);
	}

	MIB_SYNTAX[] getSyntaxs() {
		return syntax_map.values().toArray(new MIB_SYNTAX[0]);
	}

	MIB_NODE getNode(String name) {
		return node_map.get(name);
	}

	MIB_NODE[] getNodes() {
		return node_map.values().toArray(new MIB_NODE[0]);
	}

	void analysisBasic(List<String> error) {
		for (MIB_MODULE_ITEM item : item_list) {
			if (item instanceof MIB_IMPORTS) {
				MIB_IMPORTS iii = (MIB_IMPORTS) item;
				import_list.add(iii);
			} else if (item instanceof MIB_SYNTAX) {
				MIB_SYNTAX syntax = (MIB_SYNTAX) item;
				if (!UtilString.isEmpty(syntax.getNick_name())) {
					syntax_map.put(syntax.getNick_name(), syntax);
				} else {
					error.add(UtilString.format("Unknown Syntax : {}", syntax));
				}
			} else if (item instanceof MIB_NODE) {
				MIB_NODE node = (MIB_NODE) item;
				if (node.isValid()) {
					if (!UtilString.isEmpty(node.getName())) {
						node_map.put(node.getName(), node);
					} else {
						error.add(UtilString.format("Unknown Node : {}", node));
					}
				}
			} else {
				error.add(UtilString.format("Unknown Item class : {} --> {}", name, item.getClass()));
			}
		}
	}

	void analysisTree(MIB_REPOSITORY repository, List<String> error) {
		for (MIB_MODULE_ITEM item : item_list) {
			if (item instanceof MIB_NODE) {
				MIB_NODE node = (MIB_NODE) item;
				if (node.isValid()) {
					if (node.getParent() == null && !UtilString.isEmpty(node.getParent_name())) {
						MIB_NODE parent = node_map.get(node.getParent_name());
						if (parent != null) {
							node.setParent(parent);
						} else {
							for (MIB_IMPORTS iii : import_list) {
								for (String from : iii.getFroms()) {
									MIB_MODULE_DEFINITIONS ddd = repository.getMODULE_DEFINITIONS(from);
									if (ddd != null) {
										parent = ddd.getNode(node.getParent_name());
										if (parent != null) {
											node.setParent(parent);
											break;
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}

	@Override
	public String toString() {
		return toString("");
	}

	public String toString(String indent) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(indent).append(name).append(" ").append("DEFINITIONS").append(" ").append("::=").append(" ").append("BEGIN").append("\n");
		for (MIB_MODULE_ITEM item : item_list) {
			stringBuilder.append(item.toString(indent + "\t")).append("\n").append("\n");
		}
		stringBuilder.append(indent).append("END");
		return stringBuilder.toString();
	}

}
