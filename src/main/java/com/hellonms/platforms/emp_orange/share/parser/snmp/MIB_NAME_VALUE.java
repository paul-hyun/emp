package com.hellonms.platforms.emp_orange.share.parser.snmp;

import java.math.BigInteger;

public class MIB_NAME_VALUE {

	private String name;

	private BigInteger value;

	MIB_NAME_VALUE() {
	}

	MIB_NAME_VALUE(String name, BigInteger value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	void setName(String name) {
		this.name = name;
	}

	public BigInteger getValue() {
		return value;
	}

	void setValue(BigInteger value) {
		this.value = value;
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		if (name != null && value != null) {
			stringBuilder.append(name).append("(").append(value.longValue()).append(")");
		} else if (name != null) {
			stringBuilder.append(name);
		} else if (value != null) {
			stringBuilder.append(value.longValue());
		}
		return stringBuilder.toString();
	}

}
