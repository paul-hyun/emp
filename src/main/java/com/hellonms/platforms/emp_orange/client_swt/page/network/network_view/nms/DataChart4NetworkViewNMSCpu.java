package com.hellonms.platforms.emp_orange.client_swt.page.network.network_view.nms;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import org.eclipse.swt.graphics.Color;

import com.hellonms.platforms.emp_core.share.language.UtilLanguage;
import com.hellonms.platforms.emp_onion.client_swt.data.chart.DataChartAt;
import com.hellonms.platforms.emp_onion.share.model.STATISTICS_TYPE;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.network.ne.Model4ResourceNMS;
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
public class DataChart4NetworkViewNMSCpu extends DataChartAt {

	protected final String[] COLUMN_NAMES = { UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.CPU) };

	/**
	 * 기간단위
	 */
	protected STATISTICS_TYPE statisticsType = STATISTICS_TYPE.MINUTE_5;

	protected ModelClient4ResourceNMSValue[] resource_nmss = {};

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
		return resource_nmss[row].getCollectTime();
	}

	@Override
	public int getColumnCount() {
		return COLUMN_NAMES.length;
	}

	@Override
	public String getColumnTitle(int column) {
		Double value = getRowCount() == 0 ? null : getValueAt(getRowCount() - 1, column);
		if (value != null) {
			return UtilString.format("{} [{} %]", COLUMN_NAMES[column], UtilNumber.format(value));
		} else {
			return UtilString.format("{}", COLUMN_NAMES[column]);
		}
	}

	@Override
	public Color getColumnColor(int column) {
		return null;
	}

	@Override
	public int getRowCount() {
		return resource_nmss.length;
	}

	@Override
	public double getMinValue() {
		return 0;
	}

	@Override
	public double getMaxValue() {
		return 100;
	}

	@Override
	public Double getValueAt(int row, int column) {
		Model4ResourceNMS resource_nms = resource_nmss[row].getResource_nms();
		if (resource_nms == null) {
			return null;
		}
		return resource_nms.getCpu_usage();
	}

	@Override
	public void setDatas(Object... datas) {
		if (datas.length == 1 && datas[0] instanceof Model4ResourceNMS[]) {
			setData((Model4ResourceNMS[]) datas[0]);
		}
	}

	protected void setData(Model4ResourceNMS[] resource_nmss) {
		Map<Date, ModelClient4ResourceNMSValue> statisticsValueMap = new TreeMap<Date, ModelClient4ResourceNMSValue>();
		if (resource_nmss != null && 0 < resource_nmss.length) {
			Date fromTime = UtilDate.startMinute(resource_nmss[0].getCollect_time());
			Date toTime = UtilDate.startMinute(resource_nmss[resource_nmss.length - 1].getCollect_time());

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(fromTime);
			while (calendar.getTimeInMillis() <= toTime.getTime()) {
				statisticsValueMap.put(calendar.getTime(), new ModelClient4ResourceNMSValue(calendar.getTime()));
				calendar.add(Calendar.MINUTE, 1);
			}
			for (Model4ResourceNMS resource_nms : resource_nmss) {
				ModelClient4ResourceNMSValue model = statisticsValueMap.get(UtilDate.startMinute(resource_nms.getCollect_time()));
				if (model == null) {
					System.err.println("Unknown performance: " + UtilDate.format(resource_nms.getCollect_time()));
				} else {
					model.setResource_nms(resource_nms);
				}
			}
		}
		this.resource_nmss = statisticsValueMap.values().toArray(new ModelClient4ResourceNMSValue[0]);

		makeDataSerie();
	}

}
