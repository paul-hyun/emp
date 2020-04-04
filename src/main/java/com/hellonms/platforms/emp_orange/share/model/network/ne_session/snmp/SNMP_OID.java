/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.share.model.network.ne_session.snmp;

import com.hellonms.platforms.emp_orange.share.model.network.ne_session.NE_SESSION_OID;

/**
 * <p>
 * SNMP OID 정의
 * </p>
 *
 * @since 1.6
 * @create 2015. 4. 7.
 * @modified 2015. 4. 7.
 * @author cchyun
 *
 */
@SuppressWarnings("serial")
public final class SNMP_OID implements NE_SESSION_OID, Comparable<SNMP_OID> {

	private int[] oid;

	private int index_length;

	public SNMP_OID() {
		this.oid = new int[0];
		this.index_length = 0;
	}

	public SNMP_OID(int[] oid) {
		this.oid = oid;
		this.index_length = 0;
	}

	public SNMP_OID(int[] oid, int[] indexs) {
		this.oid = new int[oid.length + indexs.length];
		System.arraycopy(oid, 0, this.oid, 0, oid.length);
		System.arraycopy(indexs, 0, this.oid, oid.length, indexs.length);
		this.index_length = 0;
	}
	

	public SNMP_OID(int[] oid, String indexs) {
		String[] array = indexs.split("\\.");
		int endIndex = array.length - 1;
		while (0 <= endIndex) {
			if (!array[endIndex].equals("*")) {
				break;
			}
			endIndex--;
		}
		int[] vvv = new int[endIndex + 1];
		for (int i = 0; i < vvv.length; i++) {
			vvv[i] = Integer.parseInt(array[i]);
		}
		
		this.oid = new int[oid.length + vvv.length];
		System.arraycopy(oid, 0, this.oid, 0, oid.length);
		System.arraycopy(vvv, 0, this.oid, oid.length, vvv.length);
		this.index_length = 0;
	}

	public SNMP_OID(String oid) {
		String[] array = oid.split("\\.");
		int endIndex = array.length - 1;
		while (0 <= endIndex) {
			if (!array[endIndex].equals("*")) {
				break;
			}
			endIndex--;
		}
		int[] vvv = new int[endIndex + 1];
		for (int i = 0; i < vvv.length; i++) {
			vvv[i] = Integer.parseInt(array[i]);
		}
		this.oid = vvv;
		this.index_length = array.length - endIndex - 1;
	}

	public int[] getOid() {
		return oid;
	}

	public int getOidLength() {
		return oid.length;
	}

	public int getOidValue(int index) {
		return oid[index];
	}

	public int getOidValueLast() {
		return oid[oid.length - 1];
	}

	@Override
	public int getIndex_length() {
		return index_length;
	}

	public SNMP_OID subOid(int beginIndex) {
		int[] oid = new int[Math.max(0, this.oid.length - beginIndex)];
		System.arraycopy(this.oid, beginIndex, oid, 0, oid.length);
		return new SNMP_OID(oid);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof SNMP_OID) {
			return compareTo((SNMP_OID) obj) == 0;
		} else {
			return false;
		}
	}

	@Override
	public int compareTo(SNMP_OID o) {
		int compare = oid.length - o.oid.length;
		if (compare == 0) {
			for (int i = 0; i < oid.length; i++) {
				compare = oid[i] - o.oid[i];
				if (compare != 0) {
					break;
				}
			}
		}
		return compare;
	}

	public boolean matchOf(SNMP_OID o) {
		int length = Math.min(o.oid.length, oid.length);
		for (int i = 0; i < length; i++) {
			if (o.oid[i] != oid[i]) {
				return false;
			}
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		for (int i : oid) {
			stringBuilder.append(i).append('.');
		}
		for (int i = 0; i < index_length; i++) {
			stringBuilder.append("*").append('.');
		}
		if (0 < stringBuilder.length()) {
			stringBuilder.setLength(stringBuilder.length() - 1);
		}
		return stringBuilder.toString();
	}

}
