/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_util.date;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.hellonms.platforms.emp_core.share.error.ERROR_CODE;
import com.hellonms.platforms.emp_core.share.error.EmpException;

/**
 * <p>
 * Date Util
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 2.
 * @modified 2015. 5. 2.
 * @author cchyun
 *
 */
public class UtilDate {

	private static final ThreadLocal<Map<String, SimpleDateFormat>> dateFormatLocal = new ThreadLocal<Map<String, SimpleDateFormat>>() {
		@Override
		protected Map<String, SimpleDateFormat> initialValue() {
			return new HashMap<String, SimpleDateFormat>();
		}
	};

	private static final ThreadLocal<Calendar> calendarLocal = new ThreadLocal<Calendar>() {
		@Override
		protected Calendar initialValue() {
			return Calendar.getInstance();
		}
	};

	public static final long SECOND = 1000L;

	public static final long MINUTE = 60L * SECOND;

	public static final long HOUR = 60L * MINUTE;

	public static final long DAY = 24L * HOUR;

	public static final String MILLI_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

	public static final String MILLI_FORMAT_TRIM = "yyyyMMddHHmmssSSS";

	public static final String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public static final String MINUTE_FORMAT = "yyyy-MM-dd HH:mm";

	public static final String HOUR_FORMAT = "yyyy-MM-dd HH";

	public static final String DATE_FORMAT = "yyyy-MM-dd";

	public static final String MONTH_FORMAT = "yyyy-MM";

	public static final String YEAR_FORMAT = "yyyy";

	public static final String MINUTELY_FORMAT = "HH:mm";

	public static final String HOURLY_FORMAT = "HH";

	public static final String DAILY_FORMAT = "dd";

	public static final String MONTHLY_FORMAT = "MM";

	private static long timestampoffset = 0L;

	public static long getTimestampOffset() {
		return timestampoffset;
	}

	public static void setTimestampOffset(long timestampOffset) {
		UtilDate.timestampoffset = timestampOffset;
	}

	public static long currentTimeMillis() {
		return System.currentTimeMillis() + timestampoffset;
	}

	public static Date currentTime() {
		return new Date(currentTimeMillis());
	}

	public static Date startSecond(Date date) {
		Calendar calendar = calendarLocal.get();
		calendar.setTime(date);
		calendar.set(Calendar.MILLISECOND, 0);

		return calendar.getTime();
	}

	public static Date endSecond(Date date) {
		Calendar calendar = calendarLocal.get();
		calendar.setTime(date);
		calendar.set(Calendar.MILLISECOND, 999);

		return calendar.getTime();
	}

	public static Date startMinute(Date date) {
		Calendar calendar = calendarLocal.get();
		calendar.setTime(date);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.SECOND, 0);

		return calendar.getTime();
	}

	public static Date endMinute(Date date) {
		Calendar calendar = calendarLocal.get();
		calendar.setTime(date);
		calendar.set(Calendar.MILLISECOND, 999);
		calendar.set(Calendar.SECOND, 59);

		return calendar.getTime();
	}

	public static Date startHour(Date date) {
		Calendar calendar = calendarLocal.get();
		calendar.setTime(date);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MINUTE, 0);

		return calendar.getTime();
	}

	public static Date endHour(Date date) {
		Calendar calendar = calendarLocal.get();
		calendar.setTime(date);
		calendar.set(Calendar.MILLISECOND, 999);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MINUTE, 59);

		return calendar.getTime();
	}

	public static Date startDay(Date date) {
		Calendar calendar = calendarLocal.get();
		calendar.setTime(date);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.HOUR_OF_DAY, 0);

		return calendar.getTime();
	}

	public static Date endDay(Date date) {
		Calendar calendar = calendarLocal.get();
		calendar.setTime(date);
		calendar.set(Calendar.MILLISECOND, 999);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.HOUR_OF_DAY, 23);

		return calendar.getTime();
	}

	public static Date startWeek(Date date) {
		Calendar calendar = calendarLocal.get();
		calendar.setTime(date);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.DAY_OF_WEEK, 1);
		return calendar.getTime();
	}

	public static Date endWeek(Date date) {
		Calendar calendar = calendarLocal.get();
		calendar.setTime(date);
		calendar.set(Calendar.MILLISECOND, 999);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.DAY_OF_WEEK, 7);
		return calendar.getTime();
	}

	public static Date startWeekInMonth(Date date) {
		Calendar calendar = calendarLocal.get();
		calendar.setTime(date);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.DAY_OF_WEEK_IN_MONTH, 1);
		return calendar.getTime();
	}

	public static Date startMonth(Date date) {
		Calendar calendar = calendarLocal.get();
		calendar.setTime(date);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.DAY_OF_MONTH, 1);

		return calendar.getTime();
	}

	public static Date endMonth(Date date) {
		Calendar calendar = calendarLocal.get();
		calendar.setTime(date);
		calendar.set(Calendar.MILLISECOND, 999);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

		return calendar.getTime();
	}

	public static Date startYear(Date date) {
		Calendar calendar = calendarLocal.get();
		calendar.setTime(date);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.MONTH, 0);

		return calendar.getTime();
	}

	public static Date endYear(Date date) {
		Calendar calendar = calendarLocal.get();
		calendar.setTime(date);
		calendar.set(Calendar.MILLISECOND, 999);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.MONTH, 11);

		return calendar.getTime();
	}

	public static Date add(Date date, int field, int amount) {
		Calendar calendar = calendarLocal.get();
		calendar.setTime(date);
		calendar.add(field, amount);

		return calendar.getTime();
	}

	public static int get(Date date, int field, int amount) {
		Calendar calendar = calendarLocal.get();
		calendar.setTime(date);

		return calendar.get(field);
	}

	private static SimpleDateFormat getDateFormat(String format) {
		Map<String, SimpleDateFormat> formatMap = dateFormatLocal.get();
		SimpleDateFormat dateFormat = formatMap.get(format);
		if (dateFormat == null) {
			dateFormat = new SimpleDateFormat(format);
			formatMap.put(format, dateFormat);
		}
		return dateFormat;
	}

	/**
	 * @return
	 */
	public static String format() {
		return format(UtilDate.currentTime());
	}

	/**
	 * @param date
	 * @return
	 */
	public static String format(Date date) {
		return format(TIME_FORMAT, date);
	}

	/**
	 * @param format
	 * @return
	 */
	public static String format(String format) {
		return format(format, UtilDate.currentTime());
	}

	/**
	 * @param format
	 * @param date
	 * @return
	 */
	public static String format(String format, Date date) {
		try {
			return getDateFormat(format).format(date);
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * @param format
	 * @param date
	 * @param defaultValue
	 * @return
	 */
	public static String format(String format, Date date, String defaultValue) {
		try {
			return getDateFormat(format).format(date);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	/**
	 * @param date
	 * @return
	 * @throws EmpException
	 */
	public static Date parse(String date) throws EmpException {
		return parse(TIME_FORMAT, date);
	}

	/**
	 * @param date
	 * @param defaultValue
	 * @return
	 */
	public static Date parse(String date, Date defaultValue) {
		return parse(TIME_FORMAT, date, defaultValue);
	}

	/**
	 * @param format
	 * @param date
	 * @return
	 * @throws EmpException
	 */
	public static Date parse(String format, String date) throws EmpException {
		try {
			return getDateFormat(format).parse(date);
		} catch (Exception e) {
			throw new EmpException(e, ERROR_CODE.ERROR_UNKNOWN);
		}
	}

	/**
	 * @param format
	 * @param date
	 * @param defaultValue
	 * @return
	 */
	public static Date parse(String format, String date, Date defaultValue) {
		try {
			return getDateFormat(format).parse(date);
		} catch (Exception e) {
			return defaultValue;
		}
	}

}
