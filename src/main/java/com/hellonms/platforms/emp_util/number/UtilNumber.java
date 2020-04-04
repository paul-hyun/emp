package com.hellonms.platforms.emp_util.number;

import java.text.NumberFormat;

public class UtilNumber {

	public static String format(int value) {
		return NumberFormat.getInstance().format(value);
	}

	public static String format(int value, int length) {
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < length; i++) {
			stringBuilder.append("0");
		}
		stringBuilder.append(format(value));
		return stringBuilder.substring(stringBuilder.length() - length, stringBuilder.length());
	}

	public static String format(long value) {
		return NumberFormat.getInstance().format(value);
	}

	public static String format(float value) {
		return format(value, 1);
	}

	public static String format(float value, int fraction) {
		NumberFormat numberFormat = NumberFormat.getInstance();
		numberFormat.setMinimumFractionDigits(fraction);
		numberFormat.setMaximumFractionDigits(fraction);
		return numberFormat.format(value);
	}

	public static String format(double value) {
		return format(value, 1);
	}

	public static String format(double value, int fraction) {
		NumberFormat numberFormat = NumberFormat.getInstance();
		numberFormat.setMinimumFractionDigits(fraction);
		numberFormat.setMaximumFractionDigits(fraction);
		return numberFormat.format(value);
	}

	public static String format(Double value, int fraction) {
		return value == null ? "" : format(value.doubleValue(), fraction);
	}

	public static String formatNoComma(long value) {
		return formatNoComma(value, 1);
	}

	public static String formatNoComma(float value) {
		return formatNoComma(value, 1);
	}

	public static String formatNoComma(double value) {
		return formatNoComma(value, 1);
	}

	public static String formatNoComma(float value, int fraction) {
		return formatNoComma((double) value, fraction);
	}

	public static String formatNoComma(double value, int fraction) {
		return String.format("%." + fraction + "f", value);
	}

	private static final long KILO_1024 = 1024L;
	private static final long MEGA_1024 = KILO_1024 * 1024L;
	private static final long GIGA_1024 = MEGA_1024 * 1024L;
	private static final long TERA_1024 = GIGA_1024 * 1024L;

	public static String to1024String(double value) {
		if (TERA_1024 <= value) {
			return UtilNumber.format(value / TERA_1024) + " T ";
		} else if (GIGA_1024 <= value) {
			return UtilNumber.format(value / GIGA_1024) + " G ";
		} else if (MEGA_1024 <= value) {
			return UtilNumber.format(value / MEGA_1024) + " M ";
		} else if (KILO_1024 <= value) {
			return UtilNumber.format(value / KILO_1024) + " K ";
		} else {
			return UtilNumber.format(value) + " ";
		}
	}

	private static final long KILO_1000 = 1000L;
	private static final long MEGA_1000 = KILO_1000 * 1000L;
	private static final long GIGA_1000 = MEGA_1000 * 1000L;
	private static final long TERA_1000 = GIGA_1000 * 1000L;

	public static String to1000String(double value) {
		if (TERA_1000 <= value) {
			return UtilNumber.format(value / TERA_1000) + " T ";
		} else if (GIGA_1000 <= value) {
			return UtilNumber.format(value / GIGA_1000) + " G ";
		} else if (MEGA_1000 <= value) {
			return UtilNumber.format(value / MEGA_1000) + " M ";
		} else if (KILO_1000 <= value) {
			return UtilNumber.format(value / KILO_1000) + " K ";
		} else {
			return UtilNumber.format(value) + " ";
		}
	}

}
