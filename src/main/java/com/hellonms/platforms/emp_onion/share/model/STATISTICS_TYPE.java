/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_onion.share.model;

/**
 * <p>
 * 통계 타입
 * </p>
 *
 * @since 1.6
 * @create 2015. 4. 8.
 * @modified 2015. 4. 8.
 * @author cchyun
 *
 */
public enum STATISTICS_TYPE {

	MINUTE_5(5 * 60), //
	MINUTE_15(15 * 60), //
	MINUTE_30(30 * 60), //
	HOUR_1(1 * 60 * 60), //
	DAY_1(1 * 24 * 60 * 60), //
	MONTH_1(30 * 24 * 60 * 60), //
	HOURLY(1 * 60 * 60), //
	WEEKLY(1 * 24 * 60 * 60), //
	//
	;

	public final int peroid_seconds;

	private STATISTICS_TYPE(int peroid_seconds) {
		this.peroid_seconds = peroid_seconds;
	}

	public boolean isStatistics(int second_of_day) {
		return second_of_day % peroid_seconds == 0;
	}

}
