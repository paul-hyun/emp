package com.hellonms.platforms.emp_orange.client_swt.page.network.ne_statistics;

import org.eclipse.swt.widgets.Composite;

import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelChart4Nebula;
import com.hellonms.platforms.emp_orange.client_swt.model_display.ModelDisplay4NeStatistics;
import com.hellonms.platforms.emp_util.date.UtilDate;

/**
 * <p>
 * PanelInput4NeStatistics
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class PanelChart4NeStatistics extends PanelChart4Nebula implements PanelInput4NeStatisticsIf {

	public PanelChart4NeStatistics(Composite parent, int style) {
		super(parent, style);
	}

	@Override
	public void display(ModelDisplay4NeStatistics modelDisplay4NeStatistics) {
		getDataChart().setDatas(modelDisplay4NeStatistics);

		String axisDateFormat;
		String tooltipDateFormat;
		switch (modelDisplay4NeStatistics.getStatisticsType()) {
		case MINUTE_5:
			axisDateFormat = UtilDate.MINUTELY_FORMAT;
			tooltipDateFormat = UtilDate.MINUTELY_FORMAT;
			break;
		case MINUTE_15:
			axisDateFormat = UtilDate.MINUTELY_FORMAT;
			tooltipDateFormat = UtilDate.MINUTELY_FORMAT;
			break;
		case MINUTE_30:
			axisDateFormat = UtilDate.MINUTELY_FORMAT;
			tooltipDateFormat = UtilDate.MINUTELY_FORMAT;
			break;
		case HOUR_1:
			axisDateFormat = UtilDate.HOURLY_FORMAT;
			tooltipDateFormat = UtilDate.HOURLY_FORMAT;
			break;
		case DAY_1:
			axisDateFormat = UtilDate.DAILY_FORMAT;
			tooltipDateFormat = UtilDate.DAILY_FORMAT;
			break;
		case MONTH_1:
			axisDateFormat = UtilDate.MONTHLY_FORMAT;
			tooltipDateFormat = UtilDate.MONTHLY_FORMAT;
			break;
		default:
			axisDateFormat = UtilDate.TIME_FORMAT;
			tooltipDateFormat = UtilDate.TIME_FORMAT;
			break;
		}

		displayLineChart("", axisDateFormat, tooltipDateFormat, true);
	}

}
