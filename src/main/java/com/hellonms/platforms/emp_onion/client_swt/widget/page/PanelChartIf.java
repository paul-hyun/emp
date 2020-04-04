package com.hellonms.platforms.emp_onion.client_swt.widget.page;

import org.eclipse.swt.widgets.Display;

import com.hellonms.platforms.emp_onion.client_swt.data.chart.DataChartIf;

/**
 * <p>
 * PanelChartIf
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public interface PanelChartIf {

	/**
	 * Display를 반환합니다.
	 * 
	 * @return Display 객체
	 */
	public Display getDisplay();

	/**
	 * 화면에 라인 차트를 그립니다.
	 * 
	 * @param model
	 *            차트 모델
	 * @param title
	 *            차트 제목
	 * @param axisDateFormat
	 *            축의 날짜 형식
	 * @param tooltipDateFormat
	 *            툴팁의 날짜 형식
	 * @param showLegend
	 */
	public void displayLineChart(String title, String axisDateFormat, String tooltipDateFormat, boolean showLegend);

	/**
	 * 화면에 리소스 차트를 그립니다.
	 * 
	 * @param model
	 *            차트 모델
	 * @param axisDateFormat
	 *            축의 날짜 형식
	 * @param tooltipDateFormat
	 *            툴팁의 날짜 형식
	 */
	public void displayResourceChart(String axisDateFormat, String tooltipDateFormat);

	/**
	 * 차트를 새로고침 합니다.
	 */
	public void refresh();

	public DataChartIf getDataChart();

	public void setDataChart(DataChartIf dataChart);

	public void setDatas(Object... datas);

}
