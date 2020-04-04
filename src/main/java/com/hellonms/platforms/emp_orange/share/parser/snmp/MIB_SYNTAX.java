package com.hellonms.platforms.emp_orange.share.parser.snmp;

import java.math.BigInteger;
import java.util.LinkedHashMap;
import java.util.Map;

public class MIB_SYNTAX implements MIB_MODULE_ITEM {

	private String type;

	private String arg;

	private String[] brace;

	private String[] paren;

	private String nick_name;

	private boolean implicit;

	// //////////////////////////////////////////////////
	private Map<BigInteger, String> enum_map;

	// //////////////////////////////////////////////////

	public String getType() {
		return type;
	}

	void setType(String type) {
		this.type = type;
	}

	public String getArg() {
		return arg;
	}

	void setArg(String arg) {
		this.arg = arg;
	}

	public String[] getBrace() {
		return brace;
	}

	void setBrace(String[] brace) {
		this.brace = brace;
	}

	public String[] getParen() {
		return paren;
	}

	void setParen(String[] paren) {
		this.paren = paren;
	}

	public String getNick_name() {
		return nick_name;
	}

	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}

	public boolean isImplicit() {
		return implicit;
	}

	void setImplicit(boolean implicit) {
		this.implicit = implicit;
	}

	public Map<BigInteger, String> getEnum_map() {
		if (enum_map == null) {
			enum_map = new LinkedHashMap<BigInteger, String>();
			if (brace != null) {
				for (int i = 0; i < brace.length - 3; i++) {
					if (brace[i + 1].equals("(") && brace[i + 3].equals(")")) {
						enum_map.put(new BigInteger(brace[i + 2]), brace[i]);
					}
				}
			}
		}
		return enum_map;
	}

	public boolean isEnum() {
		if (brace != null) {
			for (int i = 0; i < brace.length - 2; i++) {
				if (brace[i].equals("(") && brace[i + 2].equals(")")) {
					return true;
				}
			}
		}
		return 0 < getEnum_map().size();
	}

	@Override
	public String toString() {
		return toString("");
	}

	public String toString(String indent) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(indent);
		if (nick_name != null) {
			stringBuilder.append(nick_name).append(" ").append("::=").append(" ");
		}
		stringBuilder.append(type).append(" ");
		if (arg != null) {
			stringBuilder.append(arg).append(" ");
		}
		if (brace != null) {
			stringBuilder.append("{ ");
			for (String ccc : brace) {
				stringBuilder.append(ccc);
				if (ccc.equals(",")) {
					stringBuilder.append(" ");
				}
			}
			stringBuilder.append(" }");
		}
		if (paren != null) {
			stringBuilder.append("( ");
			for (String ccc : paren) {
				stringBuilder.append(ccc);
			}
			stringBuilder.append(" )");
		}
		return stringBuilder.toString();
	}

}
