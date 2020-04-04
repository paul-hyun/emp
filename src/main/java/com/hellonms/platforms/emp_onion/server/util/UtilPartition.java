/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_onion.server.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_onion.share.model.STATISTICS_TYPE;
import com.hellonms.platforms.emp_util.date.UtilDate;
import com.hellonms.platforms.emp_util.string.UtilString;

/**
 * <p>
 * 파티션 기능 관리 Util
 * </p>
 *
 * @since 1.6
 * @create 2015. 4. 29.
 * @modified 2015. 4. 29.
 * @author cchyun
 *
 */
public class UtilPartition {

	private static final int PARTITION_MINUTE_HOUR = 4;

	private static final int PARTITION_MINUTE_DAY = 7;

	private static final int PARTITION_HOUR_DAY = 31;

	private static final int PARTITION_DAY_MONTH = 12;

	private static final int PARTITION_MONTH_YEAR = 10;

	public static String getPartition_type(STATISTICS_TYPE statistics_type) {
		switch (statistics_type) {
		case MINUTE_5:
		case MINUTE_15:
		case MINUTE_30:
			return "MINUTE";
		case HOUR_1:
		case HOURLY:
			return "HOUR";
		case DAY_1:
		case WEEKLY:
			return "DAY";
		case MONTH_1:
			return "MONTH";
		default:
			throw new RuntimeException(UtilString.format("Unsupported partition type={}", statistics_type));
		}
	}

	public static String getPartition_index(Date date) {
		return getPartition_index(STATISTICS_TYPE.HOUR_1, date);
	}

	public static String getPartition_index(STATISTICS_TYPE statistics_type, Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		switch (statistics_type) {
		case MINUTE_5:
		case MINUTE_15:
		case MINUTE_30:
			return getPartitionIndex4Minute(calendar);
		case HOUR_1:
			return getPartitionIndex4Hour(calendar);
		case DAY_1:
			return getPartitionIndex4Day(calendar);
		case MONTH_1:
			return getPartitionIndex4Month(calendar);
		default:
			throw new RuntimeException(UtilString.format("Unsupported partition type={}", statistics_type));
		}
	}

	private static String getPartitionIndex4Minute(Calendar calendar) {
		Calendar local = Calendar.getInstance();
		local.setTime(calendar.getTime());
		local.add(Calendar.HOUR_OF_DAY, -(calendar.get(Calendar.HOUR_OF_DAY) % PARTITION_MINUTE_HOUR));
		return UtilString.format("{}", UtilDate.format("yyyyMMddHH", local.getTime()));
	}

	private static String getPartitionIndex4Hour(Calendar calendar) {
		return UtilString.format("{}{}", UtilDate.format("yyyyMMdd", calendar.getTime()));
	}

	private static String getPartitionIndex4Day(Calendar calendar) {
		return UtilString.format("{}{}", UtilDate.format("yyyyMM", calendar.getTime()));
	}

	private static String getPartitionIndex4Month(Calendar calendar) {
		return UtilString.format("{}{}", UtilDate.format("yyyy", calendar.getTime()));
	}

	public static String[] getPartition_indexs(Date collect_time, STATISTICS_TYPE statistics_type) {
		List<String> partition_list = new ArrayList<String>();

		switch (statistics_type) {
		case MINUTE_5:
		case MINUTE_15:
		case MINUTE_30: {
			Date formTime = UtilDate.add(collect_time, Calendar.DAY_OF_YEAR, -PARTITION_MINUTE_DAY);
			Date toTime = UtilDate.add(collect_time, Calendar.DAY_OF_YEAR, 1);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(formTime);
			while (calendar.getTimeInMillis() <= toTime.getTime()) {
				partition_list.add(getPartitionIndex4Minute(calendar));
				calendar.add(Calendar.HOUR_OF_DAY, PARTITION_MINUTE_HOUR);
			}
			break;
		}
		case HOUR_1: {
			Date formTime = UtilDate.add(collect_time, Calendar.DAY_OF_YEAR, -PARTITION_HOUR_DAY);
			Date toTime = UtilDate.add(collect_time, Calendar.DAY_OF_YEAR, 1);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(formTime);
			while (calendar.getTimeInMillis() <= toTime.getTime()) {
				partition_list.add(getPartitionIndex4Hour(calendar));
				calendar.add(Calendar.DAY_OF_YEAR, 1);
			}
			break;
		}
		case DAY_1: {
			Date formTime = UtilDate.add(collect_time, Calendar.MONTH, -PARTITION_DAY_MONTH);
			Date toTime = UtilDate.add(collect_time, Calendar.MONTH, 1);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(formTime);
			while (calendar.getTimeInMillis() <= toTime.getTime()) {
				partition_list.add(getPartitionIndex4Day(calendar));
				calendar.add(Calendar.MONTH, 1);
			}
			break;
		}
		case MONTH_1: {
			Date formTime = UtilDate.add(collect_time, Calendar.YEAR, -PARTITION_MONTH_YEAR);
			Date toTime = UtilDate.add(collect_time, Calendar.YEAR, 1);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(formTime);
			while (calendar.getTimeInMillis() <= toTime.getTime()) {
				partition_list.add(getPartitionIndex4Month(calendar));
				calendar.add(Calendar.YEAR, 1);
			}
			break;
		}
		default:
			return new String[0];
		}

		return partition_list.toArray(new String[0]);
	}

	public static String getPartition_from_time(String partition_index, STATISTICS_TYPE statistics_type) throws EmpException {
		switch (statistics_type) {
		case MINUTE_5:
		case MINUTE_15:
		case MINUTE_30: {
			Date collect_time = UtilDate.parse("yyyyMMddHH", partition_index);
			return UtilDate.format(collect_time);
		}
		case HOUR_1: {
			Date collect_time = UtilDate.parse("yyyyMMdd", partition_index);
			return UtilDate.format(collect_time);
		}
		case DAY_1: {
			Date collect_time = UtilDate.parse("yyyyMM", partition_index);
			return UtilDate.format(collect_time);
		}
		case MONTH_1: {
			Date collect_time = UtilDate.parse("yyyy", partition_index);
			return UtilDate.format(collect_time);
		}
		default:
			return "";
		}
	}

	public static String getPartition_to_time(String partition_index, STATISTICS_TYPE statistics_type) throws EmpException {
		switch (statistics_type) {
		case MINUTE_5:
		case MINUTE_15:
		case MINUTE_30: {
			Date collect_time = UtilDate.parse("yyyyMMddHH", partition_index);
			return UtilDate.format(UtilDate.add(collect_time, Calendar.HOUR_OF_DAY, PARTITION_MINUTE_HOUR));
		}
		case HOUR_1: {
			Date collect_time = UtilDate.parse("yyyyMMdd", partition_index);
			return UtilDate.format(UtilDate.add(collect_time, Calendar.DAY_OF_YEAR, 1));
		}
		case DAY_1: {
			Date collect_time = UtilDate.parse("yyyyMM", partition_index);
			return UtilDate.format(UtilDate.add(collect_time, Calendar.MONTH, 1));
		}
		case MONTH_1: {
			Date collect_time = UtilDate.parse("yyyy", partition_index);
			return UtilDate.format(UtilDate.add(collect_time, Calendar.YEAR, 1));
		}
		default:
			return "";
		}
	}

}
