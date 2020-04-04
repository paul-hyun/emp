/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.server.workflow.fault.event;

import java.util.Date;

import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.server.workflow.DaoIf;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_onion.share.model.STATISTICS_TYPE;
import com.hellonms.platforms.emp_orange.share.model.fault.event.Model4AlarmStatistics;
import com.hellonms.platforms.emp_orange.share.model.fault.event.Model4AlarmStatistics.ITEM;

/**
 * <p>
 * Event Dao
 * </p>
 *
 * @since 1.6
 * @create 2015. 4. 21.
 * @modified 2015. 4. 21.
 * @author cchyun
 *
 */
public interface Dao4AlarmStatisticsIf extends DaoIf {

	/**
	 * <p>
	 * 알람 통계를 생성 한다.
	 * </p>
	 *
	 * @param context
	 * @param from_type
	 * @param to_type
	 * @param collect_time
	 * @return
	 * @throws EmpException
	 */
	public int syncListAlarmStatistics(EmpContext context, STATISTICS_TYPE from_type, STATISTICS_TYPE to_type, Date collect_time) throws EmpException;

	/**
	 * <p>
	 * 통계 조회
	 * </p>
	 *
	 * @param context
	 * @param fromTime
	 * @param toTime
	 * @param ne_group_id
	 * @param item
	 * @param statistics_type
	 * @return
	 * @throws EmpException
	 */
	public Model4AlarmStatistics[] queryListAlarmStatisticsByNeGroup(EmpContext context, int ne_group_id, ITEM item, STATISTICS_TYPE statistics_type, Date fromTime, Date toTime) throws EmpException;

	/**
	 * <p>
	 * 통계 조회
	 * </p>
	 *
	 * @param context
	 * @param fromTime
	 * @param toTime
	 * @param ne_id
	 * @param item
	 * @param statistics_type
	 * @return
	 * @throws EmpException
	 */
	public Model4AlarmStatistics[] queryListAlarmStatisticsByNe(EmpContext context, int ne_id, ITEM item, STATISTICS_TYPE statistics_type, Date fromTime, Date toTime) throws EmpException;

	/**
	 * <p>
	 * 알람통계 파티션 준비
	 * </p>
	 *
	 * @param context
	 * @param collect_time
	 * @return
	 * @throws EmpException
	 */
	public String[][] prepareListPartition(EmpContext context, Date collect_time) throws EmpException;

	/**
	 * <p>
	 * 관련 테이블 초기화
	 * </p>
	 * 
	 * @param context
	 * @throws EmpException
	 */
	public void truncate(EmpContext context) throws EmpException;

}
