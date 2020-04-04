package com.hellonms.platforms.emp_orange.share.parser.snmp;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MIB_NODE implements MIB_MODULE_ITEM {

	public enum MIB_MACRO_TYPE {
		OBJECT_IDENTIFIER, //
		MODULE_IDENTITY, //
		MODULE_COMPLIANCE, //
		OBJECT_GROUP, //
		OBJECT_TYPE, //
		OBJECT_IDENTITY, //
		TRAP_TYPE, //
		NOTIFICATION_GROUP, //
		NOTIFICATION_TYPE, //
		AGENT_CAPABILITIES, //
	}

	static final MIB_NODE ROOT = new MIB_NODE();
	static {
		ROOT.setName("iso");
		ROOT.setMacroType(MIB_MACRO_TYPE.OBJECT_IDENTIFIER);
		ROOT.setOid(new MIB_NAME_VALUE[] { new MIB_NAME_VALUE(null, BigInteger.valueOf(1)) });
	}

	private boolean valid;

	private String name;

	private MIB_MACRO_TYPE macroType;

	private List<Object[]> content_list = new ArrayList<Object[]>();

	private Map<String, Object> content_map = new LinkedHashMap<String, Object>();

	private String[] contents;

	private MIB_NAME_VALUE[] oid;

	private MIB_NODE parent;

	MIB_NODE() {
	}

	public boolean isValid() {
		return valid;
	}

	void setValid(boolean valid) {
		this.valid = valid;
	}

	public String getName() {
		return name;
	}

	void setName(String name) {
		this.name = name;
	}

	public MIB_MACRO_TYPE getMacroType() {
		return macroType;
	}

	void setMacroType(MIB_MACRO_TYPE macroType) {
		this.macroType = macroType;
	}

	public boolean isScalar() {
		return getNODE_TYPE() == NODE_TYPE.SCALAR;
	}

	public boolean isTable() {
		return getNODE_TYPE() == NODE_TYPE.TABLE;
	}

	public boolean isRow() {
		return getNODE_TYPE() == NODE_TYPE.ROW;
	}

	public boolean isColumn() {
		return getNODE_TYPE() == NODE_TYPE.INDEX || getNODE_TYPE() == NODE_TYPE.COLUMN;
	}

	public boolean isIndex() {
		return getNODE_TYPE() == NODE_TYPE.INDEX;
	}

	public String[] getIndexs() {
		if (indexs == null) {
			String[] index = (String[]) content_map.get("INDEX");
			if (index != null) {
				List<String> index_list = new ArrayList<String>();
				for (String iii : index) {
					if (!iii.equals(",")) {
						index_list.add(iii);
					}
				}
				indexs = index_list.toArray(new String[0]);
			} else {
				indexs = new String[] {};
			}
		}
		return indexs;
	}

	private boolean isIndex(String name) {
		String[] indexs = getIndexs();
		for (String index : indexs) {
			if (index.equals(name)) {
				return true;
			}
		}
		return false;
	}

	void putContents(String key, Object value) {
		this.content_list.add(new Object[] { key, value });
		this.content_map.put(key, value);
	}

	public String[] getContents() {
		return contents;
	}

	void setContents(String[] contents) {
		this.contents = contents;
	}

	public MIB_SYNTAX getSyntax() {
		return (MIB_SYNTAX) content_map.get("SYNTAX");
	}

	public boolean isRead() {
		if (access[0] == null) {
			String string = (String) content_map.get("ACCESS");
			if (string == null) {
				string = (String) content_map.get("MAX-ACCESS");
			}
			if (string != null) {
				access[0] = string.contains("read");
			} else {
				access[0] = false;
			}
		}
		return access[0];
	}

	public boolean isWrite() {
		if (access[1] == null) {
			String string = (String) content_map.get("ACCESS");
			if (string == null) {
				string = (String) content_map.get("MAX-ACCESS");
			}
			if (string != null) {
				access[1] = string.contains("write");
			} else {
				access[1] = false;
			}
		}
		return access[1];
	}

	boolean isRooted() {
		if (rooted == null) {
			if (this == MIB_NODE.ROOT) {
				rooted = true;
			}
			if (rooted == null && (parent != null && parent.isRooted())) {
				rooted = true;
			}
			if (rooted == null) {
				rooted = false;
			}
		}
		return rooted;
	}

	public Long[] getOidArray() {
		if (oidArray == null) {
			List<Long> oidList = new ArrayList<Long>();
			if (parent != null) {
				for (Long iii : parent.getOidArray()) {
					oidList.add(iii);
				}
			}
			for (int i = 0; i < oid.length; i++) {
				if (oid[i].getValue() != null) {
					oidList.add(oid[i].getValue().longValue());
				}
			}
			oidArray = oidList.toArray(new Long[0]);
		}
		return oidArray;
	}

	public String getParent_name() {
		return oid[0].getName();
	}

	public Long getThis_no() {
		return oid[oid.length - 1].getValue().longValue();
	}

	public String getOidString() {
		if (oidString == null) {
			StringBuilder stringBuilder = new StringBuilder();
			Long[] oid = getOidArray();
			for (int i = 0; i < oid.length; i++) {
				stringBuilder.append(i == 0 ? "" : ".").append(oid[i]);
			}
			oidString = stringBuilder.toString();
		}
		return oidString;
	}

	void setOid(MIB_NAME_VALUE[] oid) {
		this.oid = oid;
	}

	public MIB_NODE getParent() {
		return parent;
	}

	void setParent(MIB_NODE parent) {
		this.parent = parent;
		clearCache();
	}

	// /////////////////////////////////////////////////
	private enum NODE_TYPE {
		NODE, SCALAR, TABLE, ROW, COLUMN, INDEX
	}

	private NODE_TYPE node_type = null;
	private String[] indexs = null;
	private Boolean[] access = { null, null };
	private Boolean rooted = null;
	private Long[] oidArray = null;
	private String oidString = null;

	private void clearCache() {
		node_type = null;
		indexs = null;
		access[0] = null;
		access[1] = null;
		rooted = null;
		oidArray = null;
		oidString = null;
	}

	private NODE_TYPE getNODE_TYPE() {
		if (node_type == null) {
			if (macroType == MIB_MACRO_TYPE.OBJECT_IDENTIFIER) {
				node_type = NODE_TYPE.NODE;
			} else if (macroType == MIB_MACRO_TYPE.OBJECT_TYPE) {
				if (parent != null && parent.getNODE_TYPE() == NODE_TYPE.NODE) {
					MIB_SYNTAX syntax = (MIB_SYNTAX) content_map.get("SYNTAX");
					if (syntax.getType().equals("SEQUENCE OF")) {
						node_type = NODE_TYPE.TABLE;
					} else {
						node_type = NODE_TYPE.SCALAR;
					}
				} else if (parent != null && parent.getNODE_TYPE() == NODE_TYPE.TABLE) {
					node_type = NODE_TYPE.ROW;
				} else if (parent != null && parent.getNODE_TYPE() == NODE_TYPE.ROW) {
					node_type = parent.isIndex(name) ? NODE_TYPE.INDEX : NODE_TYPE.COLUMN;
				}
			}
			if (node_type == null) {
				node_type = NODE_TYPE.NODE;
			}
		}
		return node_type;
	}

	// /////////////////////////////////////////////////

	@Override
	public String toString() {
		return toString("");
	}

	public String toString(String indent) {
		StringBuilder stringBuilder = new StringBuilder();
		if (macroType == MIB_MACRO_TYPE.OBJECT_IDENTIFIER) {
			stringBuilder.append(indent).append(name).append(" ").append(macroType).append(" ").append("::=").append(" ").append("{");
			for (MIB_NAME_VALUE nv : oid) {
				stringBuilder.append(" ").append(nv);
			}
			stringBuilder.append(" ").append("}");
		} else {
			stringBuilder.append(indent).append(name).append(" ").append(macroType).append("\r\n");
			for (Object[] content : content_list) {
				if (content[1] instanceof String) {
					stringBuilder.append(indent).append("\t").append(content[0]).append("\t").append(((String) content[1]).replaceAll("\n", "\r\n")).append("\r\n");
				} else if (content[1] instanceof String[]) {
					stringBuilder.append(indent).append("\t").append(content[0]).append("\t").append("{");
					for (String ccc : (String[]) content[1]) {
						stringBuilder.append(" ").append(ccc);
					}
					stringBuilder.append(" }").append("\r\n");
				} else {
					stringBuilder.append(indent).append("\t").append(content[0]).append("\t").append(content[1]).append("\r\n");
				}
			}
			stringBuilder.append(indent).append("::=").append(" ").append("{");
			for (MIB_NAME_VALUE nv : oid) {
				stringBuilder.append(" ").append(nv);
			}
			stringBuilder.append(" ").append("}");
		}
		return stringBuilder.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof MIB_NODE) {
			MIB_NODE node = (MIB_NODE) obj;
			if (!name.equals(node.name)) {
				return false;
			}
			if (!macroType.equals(node.macroType)) {
				return false;
			}
			if (!getOidString().equals(node.getOidString())) {
				return false;
			}
			return true;
		} else {
			return false;
		}
	}

}
