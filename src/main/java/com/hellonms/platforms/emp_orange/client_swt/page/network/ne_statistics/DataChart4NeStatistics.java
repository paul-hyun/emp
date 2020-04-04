package com.hellonms.platforms.emp_orange.client_swt.page.network.ne_statistics;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.eclipse.swt.graphics.Color;

import com.hellonms.platforms.emp_onion.client_swt.data.chart.DataChartAt;
import com.hellonms.platforms.emp_onion.share.model.STATISTICS_TYPE;
import com.hellonms.platforms.emp_orange.client_swt.model_display.ModelDisplay4NeStatistics;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO_FIELD;
import com.hellonms.platforms.emp_orange.share.model.network.ne_info.Model4NeStatisticsIf;
import com.hellonms.platforms.emp_util.date.UtilDate;
import com.hellonms.platforms.emp_util.string.UtilString;

/**
 * <p>
 * DataChart4NeStatistics
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class DataChart4NeStatistics extends DataChartAt {

	protected EMP_MODEL_NE_INFO ne_info_def;

	protected EMP_MODEL_NE_INFO_FIELD[] column_names;

	/**
	 * 기간단위
	 */
	protected STATISTICS_TYPE statisticsType = STATISTICS_TYPE.MINUTE_5;

	/**
	 * 성능통계 표시모델
	 */
	protected ModelClient4NeStatisticsValue[] modelClient4NeStatisticsValues = {};

	public DataChart4NeStatistics(EMP_MODEL_NE_INFO ne_info_def) {
		this.ne_info_def = ne_info_def;

		List<EMP_MODEL_NE_INFO_FIELD> neFieldCodeList = new ArrayList<EMP_MODEL_NE_INFO_FIELD>();
		for (EMP_MODEL_NE_INFO_FIELD ne_info_field_def : ne_info_def.getNe_statistics()) {
			if (ne_info_field_def.isRead() && ne_info_field_def.isDisplay_enable()) {
				neFieldCodeList.add(ne_info_field_def);
			}
		}
		List<Integer> column_hide_list = new ArrayList<Integer>();
		for (int i = 0; i < neFieldCodeList.size(); i++) {
			if (!neFieldCodeList.get(i).isChart_default()) {
				column_hide_list.add(i);
			}
		}
		column_hides.addAll(column_hide_list);
		column_names = neFieldCodeList.toArray(new EMP_MODEL_NE_INFO_FIELD[0]);
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
		return modelClient4NeStatisticsValues[row].getCollectTime();
	}

	@Override
	public int getColumnCount() {
		return column_names.length;
	}

	@Override
	public String getColumnTitle(int column) {
		String title = UtilString.isEmpty(column_names[column].getDisplay_name()) ? column_names[column].getName() : column_names[column].getDisplay_name();
		if (UtilString.isEmpty(column_names[column].getUnit())) {
			return UtilString.format("{}", title);
		} else {
			return UtilString.format("{} ({})", title, column_names[column].getUnit());
		}
	}

	@Override
	public Color getColumnColor(int column) {
		return null;
	}

	@Override
	public int getRowCount() {
		return modelClient4NeStatisticsValues.length;
	}

	@Override
	public Double getValueAt(int row, int column) {
		Model4NeStatisticsIf model4NeStatistics = modelClient4NeStatisticsValues[row].getModel4NeStatistics();
		if (model4NeStatistics == null) {
			return null;
		}
		Number value = model4NeStatistics.getField_value(column_names[column]);
		if (value == null) {
			return null;
		}
		return Double.valueOf(value.doubleValue());
	}

	@Override
	public void setDatas(Object... datas) {
		if (datas.length == 1 && datas[0] instanceof ModelDisplay4NeStatistics) {
			setData((ModelDisplay4NeStatistics) datas[0]);
		}
	}

	protected void setData(ModelDisplay4NeStatistics modelDisplay4NeStatistics) {
		setData(modelDisplay4NeStatistics.getStatisticsType(), modelDisplay4NeStatistics.getModel4NeStatisticses());
	}

	/**
	 * 데이터를 설정합니다.
	 * 
	 * @param duration
	 *            기간단위
	 * @param performances
	 *            성능모델 배열
	 */
	protected void setData(STATISTICS_TYPE statisticsType, Model4NeStatisticsIf[] model4NeStatisticses) {
		this.statisticsType = statisticsType;

		Map<Date, ModelClient4NeStatisticsValue> neStatisticsValueMap = new TreeMap<Date, ModelClient4NeStatisticsValue>();
		if (model4NeStatisticses != null && 0 < model4NeStatisticses.length) {
			Date fromTime;
			Date toTime;
			switch (statisticsType) {
			case MINUTE_5:
			case MINUTE_15:
			case MINUTE_30:
				fromTime = UtilDate.startDay(model4NeStatisticses[0].getCollect_time());
				toTime = UtilDate.endDay(fromTime);
				break;
			case HOUR_1:
				fromTime = UtilDate.startDay(model4NeStatisticses[0].getCollect_time());
				toTime = UtilDate.endDay(fromTime);
				break;
			case DAY_1:
				fromTime = UtilDate.startMonth(model4NeStatisticses[0].getCollect_time());
				toTime = UtilDate.endMonth(fromTime);
				break;
			case MONTH_1:
				fromTime = UtilDate.startYear(model4NeStatisticses[0].getCollect_time());
				toTime = UtilDate.endYear(fromTime);
				break;
			default:
				fromTime = model4NeStatisticses[0].getCollect_time();
				toTime = fromTime;
				break;
			}

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(fromTime);
			while (calendar.getTimeInMillis() <= toTime.getTime()) {
				neStatisticsValueMap.put(calendar.getTime(), new ModelClient4NeStatisticsValue(calendar.getTime()));

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
			for (Model4NeStatisticsIf neStatistics : model4NeStatisticses) {
				ModelClient4NeStatisticsValue model = neStatisticsValueMap.get(UtilDate.startMinute(neStatistics.getCollect_time()));
				if (model == null) {
					System.err.println("Unknown performance: " + UtilDate.format(neStatistics.getCollect_time()));
				} else {
					model.setModel4NeStatistics((Model4NeStatisticsIf) neStatistics);
				}
			}
		}
		modelClient4NeStatisticsValues = neStatisticsValueMap.values().toArray(new ModelClient4NeStatisticsValue[0]);

		makeDataSerie();
	}

}
