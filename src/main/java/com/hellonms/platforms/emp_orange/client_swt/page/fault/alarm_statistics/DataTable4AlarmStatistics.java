package com.hellonms.platforms.emp_orange.client_swt.page.fault.alarm_statistics;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;

import com.hellonms.platforms.emp_core.share.language.UtilLanguage;
import com.hellonms.platforms.emp_onion.client_swt.data.table.DataTableAt;
import com.hellonms.platforms.emp_onion.share.model.STATISTICS_TYPE;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.fault.event.Model4AlarmStatistics;
import com.hellonms.platforms.emp_orange.share.model.fault.event.Model4AlarmStatistics.ITEM;
import com.hellonms.platforms.emp_util.date.UtilDate;
import com.hellonms.platforms.emp_util.number.UtilNumber;

/**
 * <p>
 * DataTable4AlarmStatistics
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class DataTable4AlarmStatistics extends DataTableAt {

	/**
	 * 컬럼명 배열
	 */
	protected String[] COLUMN_NAMES = { UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.COLLECT_TIME) };

	/**
	 * 컬럼 너비 배열
	 */
	protected int[] COLUMN_WIDTHS = { 130 };

	/**
	 * 통계 항목
	 */
	protected ITEM item = ITEM.SEVERITY;

	/**
	 * 기간단위
	 */
	protected STATISTICS_TYPE statisticsType = STATISTICS_TYPE.MINUTE_15;

	protected ModelClient4AlarmStatisticsValue[] modelClient4AlarmStatisticsValues = {};

	@Override
	public int getColumnCount() {
		int column_count = COLUMN_NAMES.length;
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
		if (column == 0) {
			return COLUMN_NAMES[column];
		} else {
			for (ModelClient4AlarmStatisticsValue alarmStatistics : modelClient4AlarmStatisticsValues) {
				if (alarmStatistics.getModel4AlarmStatistics() != null) {
					return alarmStatistics.getModel4AlarmStatistics().getItemName(column - 1);
				}
			}
			return "";
		}
	}

	@Override
	public int getColumnStyle(int column) {
		switch (column) {
		case 0:
			return SWT.NONE;
		default:
			return SWT.RIGHT;
		}
	}

	@Override
	public int getColumnWidth(int column) {
		if (column == 0) {
			return COLUMN_WIDTHS[column];
		} else {
			return 60;
		}
	}

	@Override
	public Object getInput() {
		return this;
	}

	@Override
	public void clear() {
		modelClient4AlarmStatisticsValues = new ModelClient4AlarmStatisticsValue[0];
	}

	@Override
	public void setDatas(Object... datas) {
		if (datas.length == 3 && datas[0] instanceof ITEM && datas[1] instanceof STATISTICS_TYPE && datas[2] instanceof Model4AlarmStatistics[]) {
			setData((ITEM) datas[0], (STATISTICS_TYPE) datas[1], (Model4AlarmStatistics[]) datas[2]);
		}
	}

	protected void setData(ITEM item, STATISTICS_TYPE statisticsType, Model4AlarmStatistics[] model4AlarmStatisticses) {
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
				ModelClient4AlarmStatisticsValue wrapper = neStatisticsValueMap.get(neStatistics.getCollect_time());
				if (wrapper == null) {
					System.err.println("Unknown performance: " + UtilDate.format(neStatistics.getCollect_time()));
				} else {
					wrapper.setModel4AlarmStatistics(neStatistics);
				}
			}
		}
		this.item = item;
		this.statisticsType = statisticsType;
		this.modelClient4AlarmStatisticsValues = neStatisticsValueMap.values().toArray(new ModelClient4AlarmStatisticsValue[0]);
	}

	@Override
	public Object getData() {
		return modelClient4AlarmStatisticsValues;
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return modelClient4AlarmStatisticsValues;
	}

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		if (element instanceof ModelClient4AlarmStatisticsValue) {
			ModelClient4AlarmStatisticsValue modelClient4NeStatisticsValue = (ModelClient4AlarmStatisticsValue) element;
			switch (columnIndex) {
			case 0:
				Date collectTime = modelClient4NeStatisticsValue.getCollectTime();
				switch (statisticsType) {
				case MINUTE_5:
				case MINUTE_15:
				case MINUTE_30:
					return UtilDate.format(UtilDate.MINUTE_FORMAT, collectTime);
				case HOUR_1:
					return UtilDate.format(UtilDate.HOUR_FORMAT, collectTime);
				case DAY_1:
					return UtilDate.format(UtilDate.DATE_FORMAT, collectTime);
				case MONTH_1:
					return UtilDate.format(UtilDate.MONTH_FORMAT, collectTime);
				default:
					return UtilDate.format(UtilDate.HOUR_FORMAT, collectTime);
				}
			default:
				Model4AlarmStatistics model4AlarmStatistics = modelClient4NeStatisticsValue.getModel4AlarmStatistics();
				return model4AlarmStatistics == null ? "" : UtilNumber.format(model4AlarmStatistics.getItemValue(columnIndex - 1));
			}
		} else {
			return "";
		}
	}

	@Override
	public Color getBackground(Object element) {
		return null;
	}

	@Override
	public Color getForeground(Object element) {
		return null;
	}

}
