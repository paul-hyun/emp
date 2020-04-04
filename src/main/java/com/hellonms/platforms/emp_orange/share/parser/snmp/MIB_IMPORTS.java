package com.hellonms.platforms.emp_orange.share.parser.snmp;

import java.util.ArrayList;
import java.util.List;

public class MIB_IMPORTS implements MIB_MODULE_ITEM {

	private List<String> from_list = new ArrayList<String>();

	// private String[] contents;

	MIB_IMPORTS() {
	}

	// public String[] getContents() {
	// return contents;
	// }
	//
	// void setContents(String[] contents) {
	// this.contents = contents;
	// }

	@Override
	public String toString() {
		return toString("");
	}

	public String toString(String indent) {
		StringBuilder stringBuilder = new StringBuilder();
		return stringBuilder.toString();
	}

	@Override
	public boolean equals(Object obj) {
		return false;
	}

	public String[] getFroms() {
		return from_list.toArray(new String[0]);
	}

	void addFrom(String from) {
		from_list.add(from);
	}

}
