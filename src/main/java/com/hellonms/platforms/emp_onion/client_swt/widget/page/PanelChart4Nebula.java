package com.hellonms.platforms.emp_onion.client_swt.widget.page;

import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.nebula.visualization.xygraph.figures.XYGraph;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_onion.client_swt.data.chart.DataChartIf;
import com.hellonms.platforms.emp_onion.client_swt.util.chart.UtilChart4Nebula;

/**
 * <p>
 * PanelChart4Emp
 * </p>
 *
 * @since 1.6
 * @create 2016. 1. 12.
 * @modified 2016. 1. 12.
 * @author cchyun
 *
 */
public class PanelChart4Nebula extends Canvas implements PanelChartIf {

	/**
	 * 장치의 렌더러
	 */
	protected LightweightSystem lightweightSystem;

	/**
	 * 차트 모델
	 */
	protected DataChartIf dataChart;

	/**
	 * 축의 날짜 형식
	 */
	protected String axisDateFormat;

	/**
	 * 툴팁의 날짜 형식
	 */
	protected String tooltipDateFormat;

	private XYGraph xyGraph;

	/**
	 * 생성자 입니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 * @param style
	 *            스타일
	 */
	public PanelChart4Nebula(Composite parent, int style) {
		super(parent, style);

		createGUI();
	}

	public PanelChart4Nebula(Composite parent, int style, DataChartIf dataChart) {
		this(parent, style);

		setDataChart(dataChart);
	}

	/**
	 * GUI를 생성합니다.
	 */
	protected void createGUI() {
		lightweightSystem = new LightweightSystem(this);
	}

	@Override
	protected void checkSubclass() {
	}

	@Override
	public void displayLineChart(String title, String axisDateFormat, String tooltipDateFormat, boolean showLegend) {
		try {
			this.axisDateFormat = axisDateFormat;
			this.tooltipDateFormat = tooltipDateFormat;

			XYGraph xyGraph = UtilChart4Nebula.createLineChart(dataChart, title, axisDateFormat, tooltipDateFormat, showLegend);
			this.xyGraph = xyGraph;
			this.lightweightSystem.setContents(xyGraph);
		} catch (EmpException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void displayResourceChart(String axisDateFormat, String tooltipDateFormat) {
	}

	@Override
	public void refresh() {
		UtilChart4Nebula.refreshChart(xyGraph, dataChart, axisDateFormat, tooltipDateFormat);
	}

	/**
	 * 차트 모델을 반환합니다.
	 * 
	 * @return 차트 모델
	 */
	public DataChartIf getDataChart() {
		return dataChart;
	}

	/**
	 * 차트 모델을 설정합니다.
	 * 
	 * @param dataChart
	 *            차트 모델
	 */
	public void setDataChart(DataChartIf dataChart) {
		if (this.dataChart != null) {
			this.dataChart.removePanelChart(this);

		}
		this.dataChart = dataChart;
		this.dataChart.addPanelChart(this);
	}

	public void setDatas(Object... datas) {
		dataChart.setDatas(datas);
	}

}
