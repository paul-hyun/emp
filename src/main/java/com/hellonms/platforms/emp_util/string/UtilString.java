/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_util.string;

import java.util.StringTokenizer;

/**
 * <p>
 * String Util
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 21.
 * @modified 2015. 5. 21.
 * @author cchyun
 *
 */
public class UtilString {

	public static boolean isEmpty(String text) {
		if (text == null || text.trim().length() < 1) {
			return true;
		}
		return false;
	}

	public static boolean isName(String text) {
		if (text != null && 0 < text.trim().length()) {
			return text.trim().matches("[a-zA-Z][a-zA-Z0-9_]*");
		}
		return false;
	}

	public static boolean isInt(String text) {
		if (text != null && 0 < text.trim().length()) {
			try {
				Integer.parseInt(text.trim());
				return true;
			} catch (Exception e) {
				return false;
			}
		}
		return false;
	}

	public static String format(String text, Object... values) {
		char[] cs = text.toCharArray();

		int value_index = 0;
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < cs.length; i++) {
			if (cs[i] == '{' && i < cs.length - 1 && cs[i + 1] == '}') {
				i++;
				if (value_index < values.length) {
					stringBuilder.append(values[value_index++]);
				}
			} else {
				stringBuilder.append(cs[i]);
			}
		}
		return stringBuilder.toString();
	}

	public static boolean isPrintable(byte[] value) {
		for (byte aValue : value) {
			char c = (char) aValue;
			if ((Character.isISOControl(c) || ((c & 0xFF) >= 0x80)) && ((!Character.isWhitespace(c)) || (((c & 0xFF) >= 0x1C)) && ((c & 0xFF) <= 0x1F))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * <p>
	 * null을 zero length 문자열로 변환 한다.
	 * </p>
	 * 
	 * @param string
	 * @return
	 */
	public static String toEmptyIfNull(String string) {
		return string == null ? "" : string;
	}

	public static String formatZero(int value, int length) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(value);

		while (stringBuilder.length() < length) {
			stringBuilder.insert(0, '0');
		}

		return stringBuilder.toString();
	}

	public static String lpad(String value, int size) {
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < size; i++) {
			stringBuilder.append(' ');
		}
		stringBuilder.append(value);
		return stringBuilder.substring(stringBuilder.length() - size);
	}

	public static String rpad(String value, int size) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(value);
		for (int i = 0; i < size; i++) {
			stringBuilder.append(' ');
		}
		return stringBuilder.substring(stringBuilder.length() - size);
	}

	public static String toHexString(byte[] buf) {
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < buf.length; i++) {
			stringBuilder.append(i == 0 ? "" : ":");
			int value = buf[i] & 0x000000FF;
			if (value < 0x10) {
				stringBuilder.append('0');
			}
			stringBuilder.append(Integer.toHexString(value));
		}
		return stringBuilder.toString();
	}

	public static byte[] fromHexString(String value) {
		StringTokenizer token = new StringTokenizer(value, ":");
		byte[] buf = new byte[token.countTokens()];
		for (int i = 0; i < buf.length; i++) {
			buf[i] = (byte) Integer.parseInt(token.nextToken(), 16);
		}
		return buf;
	}

	public static int compare(String string1, String string2) {
		int diff = string1.length() - string2.length();

		if (diff == 0) {
			return string1.compareTo(string2);
		} else if (diff > 0) {
			return 1;
		} else {
			return -1;
		}
	}

	public static String toHTML(String string) {
		StringBuilder stringBuilder = new StringBuilder();
		for (char c : string.toCharArray()) {
			if (c == '\n') {
				stringBuilder.append("<br />").append('\n');
			} else if (c == ' ') {
				stringBuilder.append("&nbsp;");
			} else if (c == '<') {
				stringBuilder.append("&lt;");
			} else if (c == '>') {
				stringBuilder.append("&gt;");
			} else {
				stringBuilder.append(c);
			}
		}
		return stringBuilder.toString();
	}

}
