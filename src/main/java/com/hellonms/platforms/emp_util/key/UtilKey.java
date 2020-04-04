/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_util.key;

import java.security.MessageDigest;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

/**
 * <p>
 * Key 관련 유틸
 * </p>
 * 
 * @since 1.6
 * @create 2015. 3. 11.
 * @modified 2015. 3. 11.
 * @author cchyun
 * 
 */
public class UtilKey {

	private static final String[] HEXSTR = { //
			"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "0a", "0b", "0c", "0d", "0e", "0f", //
			"10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "1a", "1b", "1c", "1d", "1e", "1f", //
			"20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "2a", "2b", "2c", "2d", "2e", "2f", //
			"30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "3a", "3b", "3c", "3d", "3e", "3f", //
			"40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "4a", "4b", "4c", "4d", "4e", "4f", //
			"50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "5a", "5b", "5c", "5d", "5e", "5f", //
			"60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "6a", "6b", "6c", "6d", "6e", "6f", //
			"70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "7a", "7b", "7c", "7d", "7e", "7f", //
			"80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "8a", "8b", "8c", "8d", "8e", "8f", //
			"90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "9a", "9b", "9c", "9d", "9e", "9f", //
			"a0", "a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "aa", "ab", "ac", "ad", "ae", "af", //
			"b0", "b1", "b2", "b3", "b4", "b5", "b6", "b7", "b8", "b9", "ba", "bb", "bc", "bd", "be", "bf", //
			"c0", "c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8", "c9", "ca", "cb", "cc", "cd", "ce", "cf", //
			"d0", "d1", "d2", "d3", "d4", "d5", "d6", "d7", "d8", "d9", "da", "db", "dc", "dd", "de", "df", //
			"e0", "e1", "e2", "e3", "e4", "e5", "e6", "e7", "e8", "e9", "ea", "eb", "ec", "ed", "ee", "ef", //
			"f0", "f1", "f2", "f3", "f4", "f5", "f6", "f7", "f8", "f9", "fa", "fb", "fc", "fd", "fe", "ff" //
	};

	private static AtomicLong long_id_count = new AtomicLong(0L);

	public static long nextLong_id() {
		return (System.currentTimeMillis() & 0xFFFFFFFFFFFFF000L) + (long_id_count.incrementAndGet() & 0x0000000000000FFFL);
	}

	public static UUID randomUUID() {
		return UUID.randomUUID();
	}

	public static String stringMD5(String md5) {
		try {
			java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
			byte[] array = md.digest(md5.getBytes());
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < array.length; ++i) {
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
			}
			return sb.toString();
		} catch (Exception e) {
		}
		return null;
	}

	public static String hashToHex(String str, String charset) {
		try {
			return toHexString(encrypt(str.getBytes(charset)));
		} catch (Exception e) {
			return null;
		}
	}

	private static String toHexString(byte[] data) {
		return toHexString(data, 0, data.length);
	}

	private static String toHexString(byte[] data, int start, int length) {
		StringBuffer sb = new StringBuffer(length * 2);
		for (int i = start, iend = start + length; i < iend; i++)
			sb.append(HEXSTR[(data[i] + 256) % 256]);
		return sb.toString();
	}

	private static byte[] encrypt(byte[] data) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			md.update(data);
			return md.digest();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
