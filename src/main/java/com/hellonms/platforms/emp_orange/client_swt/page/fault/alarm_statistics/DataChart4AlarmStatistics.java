package com.hellonms.platforms.emp_orange.client_swt.page.fault.alarm_statistics;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import org.eclipse.swt.graphics.Color;

import com.hellonms.platforms.emp_onion.client_swt.data.chart.DataChartAt;
import com.hellonms.platforms.emp_onion.share.model.STATISTICS_TYPE;
import com.hellonms.platforms.emp_onion.theme.ThemeFactory;
import com.hellonms.platforms.emp_orange.share.model.fault.event.Model4AlarmStatistics;
import com.hellonms.platforms.emp_orange.share.model.fault.event.Model4AlarmStatistics.ITEM;
import com.hellonms.platforms.emp_orange.theme.ThemeBuilder4Orange.COLOR_ORANGE;
import com.hellonms.platforms.emp_util.date.UtilDate;

/**
 * <p>
 * DataChart4AlarmStatistics
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class DataChart4AlarmStatistics extends DataChartAt {

	/**
	 * 컬럼색 배열
	 */
	protected final Color[] COLUMN_COLORS;

	/**
	 * 통계항목
	 * <p>
	 * 기본값: 등급별(ITEM.SEVERITY)
	 * </p>
	 */
	protected ITEM item = ITEM.SEVERITY;

	/**
	 * 기간단위
	 */
	protected STATISTICS_TYPE statisticsType = STATISTICS_TYPE.MINUTE_5;

	/**
	 * 성능통계 표시모델
	 */
	protected ModelClient4AlarmStatisticsValue[] modelClient4AlarmStatisticsValues = {};

	public DataChart4AlarmStatistics() {
		COLUMN_COLORS = new Color[] { ThemeFactory.getColor(COLOR_ORANGE.FAULT_ALARM_CF), ThemeFactory.getColor(COLOR_ORANGE.FAULT_ALARM_CRITICAL), ThemeFactory.getColor(COLOR_ORANGE.FAULT_ALARM_MAJOR), ThemeFactory.getColor(COLOR_ORANGE.FAULT_ALARM_MINOR) };
	}

	@Override
	public String getLabelX() {
		return "";
	}

	@Override
	public String getLabelY() {
		return "";
	}

	@Override
	public Date getSeriesX(int row) {
		return modelClient4AlarmStatisticsValues[row].getCollectTime();
	}

	@Override
	public int getColumnCount() {
		int column_count = 0;
		for (ModelClient4AlarmStatisticsValue alarmStatistics : modelClient4AlarmStatisticsValues) {
			if (alarmStatistics.getModel4AlarmStatistics() != null) {
				column_count += alarmStatistics.getModel4AlarmStatistics().getItemCount();
				break;
			}
		}
		return column_count;
	}

	@Override
	public String getColumnTitle(int column) {
		for (ModelClient4AlarmStatisticsValue alarmStatistics : modelClient4AlarmStatisticsValues) {
			if (alarmStatistics.getModel4AlarmStatistics() != null) {
				return alarmStatistics.getModel4AlarmStatistics().getItemName(column);
			}
		}
		return "";
	}

	@Override
	public Color getColumnColor(int column) {
		return null;
	}

	@Override
	public int getRowCount() {
		return modelClient4AlarmStatisticsValues.length;
	}

	@Override
	public Double getValueAt(int row, int column) {
		return modelClient4AlarmStatisticsValues[row].getModel4AlarmStatistics() == null ? null : (double) modelClient4AlarmStatisticsValues[row].getModel4AlarmStatistics().getItemValue(column);
	}

	@Override
	public void setDatas(Object... datas) {
		if (datas.length == 3 && datas[0] instanceof ITEM && datas[1] instanceof STATISTICS_TYPE && datas[2] instanceof Model4AlarmStatistics[]) {
			setData((ITEM) datas[0], (STATISTICS_TYPE) datas[1], (Model4AlarmStatistics[]) datas[2]);
		}
	}

	protected void setData(ITEM item, STATISTICS_TYPE statisticsType, Model4AlarmStatistics[] model4AlarmStatisticses) {
		this.statisticsType = statisticsType;

		Map<Date, ModelClient4AlarmStatisticsValue> neStatisticsValueMap = new TreeMap<Date, ModelClient4AlarmStatisticsValue>();
		if (model4AlarmStatisticses != null && 0 < model4AlarmStatisticses.length) {
			Date fromTime;
			Date toTime;
			switch (statisticsType) {
			case MINUTE_5:
			case MINUTE_15:
			case MINUTE_30:
				fromTime = UtilDate.startDay(model4AlarmStatisticses[0].getCollect_time());
				toTime = UtilDate.endDay(fromTime);
				break;
			case HOUR_1:
				fromTime = UtilDate.startDay(model4AlarmStatisticses[0].getCollect_time());
				toTime = UtilDate.endDay(fromTime);
				break;
			case DAY_1:
				fromTime = UtilDate.startMonth(model4AlarmStatisticses[0].getCollect_time());
				toTime = UtilDate.endMonth(fromTime);
				break;
			case MONTH_1:
				fromTime = UtilDate.startYear(model4AlarmStatisticses[0].getCollect_time());
				toTime = UtilDate.endYear(fromTime);
				break;
			default:
				fromTime = model4AlarmStatisticses[0].getCollect_time();
				toTime = fromTime;
				break;
			}

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(fromTime);
			while (calendar.getTimeInMillis() <= toTime.getTime()) {
				neStatisticsValueMap.put(calendar.getTime(), new ModelClient4AlarmStatisticsValue(calendar.getTime()));

				switch (statisticsType) {
				case MINUTE_5:
					calendar.add(Calendar.MINUTE, 5);
					break;
				case MINUTE_15:
					calendar.add(Calendar.MINUTE, 15);
					break;
				case MINUTE_30:
					calendar.add(Calendar.MINUTE, 30);
					break;
				case HOUR_1:
					calendar.add(Calendar.HOUR_OF_DAY, 1);
					break;
				case DAY_1:
					calendar.add(Calendar.DAY_OF_YEAR, 1);
					break;
				case MONTH_1:
					calendar.add(Calendar.MONTH, 1);
					break;
				default:
					calendar.add(Calendar.YEAR, 9999);
					break;
				}
			}
			for (Model4AlarmStatistics neStatistics : model4AlarmStatisticses) {
				ModelClient4AlarmStatisticsValue model = neStatisticsValueMap.get(neStatistics.getCollect_time());
				if (model == null) {
					System.err.println("Unknown performance: " + UtilDate.format(neStatistics.getCollect_time()));
				} else {
					model.setModel4AlarmStatistics((Model4AlarmStatistics) neStatistics);
				}
			}
		}

		modelClient4AlarmStatisticsValues = neStatisticsValueMap.values().toArray(new ModelClient4AlarmStatisticsValue[0]);

		makeDataSerie();
	}

}
