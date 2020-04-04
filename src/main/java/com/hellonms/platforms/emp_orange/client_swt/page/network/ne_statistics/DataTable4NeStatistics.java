package com.hellonms.platforms.emp_orange.client_swt.page.network.ne_statistics;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;

import com.hellonms.platforms.emp_core.share.language.UtilLanguage;
import com.hellonms.platforms.emp_onion.client_swt.data.table.DataTableAt;
import com.hellonms.platforms.emp_onion.share.model.STATISTICS_TYPE;
import com.hellonms.platforms.emp_orange.client_swt.model_display.ModelDisplay4NeStatistics;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO_FIELD;
import com.hellonms.platforms.emp_orange.share.model.network.ne_info.Model4NeStatisticsIf;
import com.hellonms.platforms.emp_util.date.UtilDate;
import com.hellonms.platforms.emp_util.number.UtilNumber;
import com.hellonms.platforms.emp_util.string.UtilString;

/**
 * <p>
 * DataTable4NeStatistics
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class DataTable4NeStatistics extends DataTableAt {

	protected EMP_MODEL_NE_INFO ne_info_def;

	protected EMP_MODEL_NE_INFO_FIELD[] column_names;

	/**
	 * 기간단위
	 */
	protected STATISTICS_TYPE statisticsType = STATISTICS_TYPE.MINUTE_5;

	protected ModelClient4NeStatisticsValue[] modelClient4NeStatisticsValues = {};

	public DataTable4NeStatistics(EMP_MODEL_NE_INFO ne_info_def) {
		this.ne_info_def = ne_info_def;

		List<EMP_MODEL_NE_INFO_FIELD> neFieldCodeList = new ArrayList<EMP_MODEL_NE_INFO_FIELD>();
		for (EMP_MODEL_NE_INFO_FIELD ne_info_field_def : ne_info_def.getNe_statistics()) {
			if (ne_info_field_def.isRead() && ne_info_field_def.isDisplay_enable()) {
				neFieldCodeList.add(ne_info_field_def);
			}
		}
		column_names = neFieldCodeList.toArray(new EMP_MODEL_NE_INFO_FIELD[0]);
	}

	@Override
	public int getColumnCount() {
		return column_names.length + 1;
	}

	@Override
	public String getColumnTitle(int column) {
		if (column == 0) {
			return UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.COLLECT_TIME);
		} else if (column <= column_names.length) {
			String title = UtilString.isEmpty(column_names[column -1].getDisplay_name()) ? column_names[column - 1].getName() : column_names[column - 1].getDisplay_name();
			if (UtilString.isEmpty(column_names[column - 1].getUnit())) {
				return UtilString.format("{}", title);
			} else {
				return UtilString.format("{} ({})", title, column_names[column - 1].getUnit());
			}
		}
		return "";
	}

	@Override
	public int getColumnStyle(int column) {
		return SWT.NONE;
	}

	@Override
	public int getColumnWidth(int column) {
		return column == 0 ? 120 : 100;
	}

	@Override
	public Object getInput() {
		return this;
	}

	@Override
	public void clear() {
		modelClient4NeStatisticsValues = new ModelClient4NeStatisticsValue[0];
	}

	@Override
	public void setDatas(Object... datas) {
		if (datas.length == 1 && datas[0] instanceof ModelDisplay4NeStatistics) {
			setData((ModelDisplay4NeStatistics) datas[0]);
			refresh();
		}
	}

	protected void setData(ModelDisplay4NeStatistics modelDisplay4NeStatistics) {
		setData(modelDisplay4NeStatistics.getStatisticsType(), modelDisplay4NeStatistics.getModel4NeStatisticses());
	}

	protected void setData(STATISTICS_TYPE statisticsType, Model4NeStatisticsIf[] model4NeStatisticses) {
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
				ModelClient4NeStatisticsValue wrapper = neStatisticsValueMap.get(UtilDate.startMinute(neStatistics.getCollect_time()));
				if (wrapper == null) {
					System.err.println("Unknown performance: " + UtilDate.format(neStatistics.getCollect_time()));
				} else {
					wrapper.setModel4NeStatistics(neStatistics);
				}
			}
		}
		this.statisticsType = statisticsType;
		this.modelClient4NeStatisticsValues = neStatisticsValueMap.values().toArray(new ModelClient4NeStatisticsValue[0]);
	}

	@Override
	public Object getData() {
		return modelClient4NeStatisticsValues;
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return modelClient4NeStatisticsValues;
	}

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		ModelClient4NeStatisticsValue modelClient4NeStatisticsValue = (ModelClient4NeStatisticsValue) element;
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
			Model4NeStatisticsIf model4NeStatistics = modelClient4NeStatisticsValue.getModel4NeStatistics();
			if (model4NeStatistics != null) {
				Number neFieldValue = model4NeStatistics.getField_value(column_names[columnIndex - 1]);
				return neFieldValue == null ? "N/A" : UtilNumber.format(neFieldValue.longValue());
			}
		}
		return "";
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
