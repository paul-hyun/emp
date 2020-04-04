package com.hellonms.platforms.emp_orange.client_swt.page.network.network_view.ne;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import com.hellonms.platforms.emp_onion.share.model.STATISTICS_TYPE;
import com.hellonms.platforms.emp_orange.client_swt.page.network.ne_statistics.DataChart4NeStatistics;
import com.hellonms.platforms.emp_orange.client_swt.page.network.ne_statistics.ModelClient4NeStatisticsValue;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO;
import com.hellonms.platforms.emp_orange.share.model.network.ne_info.Model4NeStatisticsIf;
import com.hellonms.platforms.emp_util.date.UtilDate;
import com.hellonms.platforms.emp_util.number.UtilNumber;
import com.hellonms.platforms.emp_util.string.UtilString;

/**
 * <p>
 * DataChart4NmsCpu
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class DataChart4NetworkViewNe extends DataChart4NeStatistics {

	public DataChart4NetworkViewNe(EMP_MODEL_NE_INFO ne_info_def) {
		super(ne_info_def);
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
	public String getColumnTitle(int column) {
		Double value = getRowCount() == 0 ? null : getValueAt(getRowCount() - 1, column);
		if (value != null) {
			String title = UtilString.isEmpty(column_names[column].getDisplay_name()) ? column_names[column].getName() : column_names[column].getDisplay_name();
			if (UtilString.isEmpty(column_names[column].getUnit())) {
				return UtilString.format("{} [{}]", title, UtilNumber.format(value));
			} else {
				return UtilString.format("{} [{} {}]", title, UtilNumber.format(value), column_names[column].getUnit());
			}
		} else {
			return super.getColumnTitle(column);
		}
	}

	@Override
	protected void setData(STATISTICS_TYPE statisticsType, Model4NeStatisticsIf[] model4NeStatisticses) {
		this.statisticsType = statisticsType;

		Map<Date, ModelClient4NeStatisticsValue> neStatisticsValueMap = new TreeMap<Date, ModelClient4NeStatisticsValue>();
		if (model4NeStatisticses != null && 0 < model4NeStatisticses.length) {
			Date fromTime = UtilDate.startMinute(model4NeStatisticses[0].getCollect_time());
			Date toTime = UtilDate.startMinute(model4NeStatisticses[model4NeStatisticses.length - 1].getCollect_time());

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
