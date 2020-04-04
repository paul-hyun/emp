package com.hellonms.platforms.emp_onion.client_swt.util.chart;

import java.util.Date;

import org.eclipse.nebula.visualization.xygraph.dataprovider.CircularBufferDataProvider;
import org.eclipse.nebula.visualization.xygraph.dataprovider.Sample;
import org.eclipse.nebula.visualization.xygraph.figures.Trace;
import org.eclipse.nebula.visualization.xygraph.figures.Trace.BaseLine;
import org.eclipse.nebula.visualization.xygraph.figures.Trace.ErrorBarType;
import org.eclipse.nebula.visualization.xygraph.figures.Trace.PointStyle;
import org.eclipse.nebula.visualization.xygraph.figures.Trace.TraceType;
import org.eclipse.nebula.visualization.xygraph.figures.XYGraph;
import org.eclipse.nebula.visualization.xygraph.linearscale.Range;
import org.eclipse.nebula.visualization.xygraph.util.XYGraphMediaFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;

import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_onion.client_swt.data.chart.DataChartIf;
import com.hellonms.platforms.emp_onion.client_swt.util.resource.UtilResource;
import com.hellonms.platforms.emp_util.string.UtilString;

public class UtilChart4Nebula {

	private static class EmpRange extends Range {

		public EmpRange(double start, double end) {
			super(start, end);
		}

		@Override
		public boolean inRange(final double value) {
			return false;
		}

	}

	protected static final RGB[] ITEM_COLORS = { //
	new RGB(201, 152, 47), //
			new RGB(33, 142, 226), //
			new RGB(16, 230, 5), //
			new RGB(218, 91, 250),//
			new RGB(255, 32, 32),//
			new RGB(223, 97, 8), //
	};

	protected static final RGB DEFAULT_ITEM_COLOR = new RGB(0, 0, 0);

	/**
	 * 라인(선) 차트를 생성합니다.
	 * 
	 * @param model
	 *            차트 모델
	 * @param title
	 *            차트 제목
	 * @param axisDateFormat
	 *            축의 날짜 형식
	 * @param tooltipDateFormat
	 *            툴팁의 날짜 형식
	 * @return 라인 차트
	 * @throws EmpException
	 */
	public static XYGraph createLineChart(DataChartIf dataChart, String title, String axisDateFormat, String tooltipDateFormat) throws EmpException {
		return createLineChart(dataChart, title, axisDateFormat, tooltipDateFormat, false);
	}

	/**
	 * 라인(선) 차트를 생성합니다.
	 * 
	 * @param dataChart
	 *            차트 모델
	 * @param title
	 *            차트 제목
	 * @param axisDateFormat
	 *            축의 날짜 형식
	 * @param tooltipDateFormat
	 *            툴팁의 날자 형식
	 * @param showLegend
	 *            아래 내용 표시 여부
	 * @return 라인 차트
	 * @throws EmpException
	 */
	public static XYGraph createLineChart(DataChartIf dataChart, String title, String axisDateFormat, String tooltipDateFormat, boolean showLegend) throws EmpException {
		XYGraph xyGraph = new XYGraph();
		xyGraph.setTitle(title);
		xyGraph.setFont(XYGraphMediaFactory.getInstance().getFont(XYGraphMediaFactory.FONT_TAHOMA));
		xyGraph.setBackgroundColor(UtilResource.getColor(255, 255, 255));

		if (UtilString.isEmpty(dataChart.getLabelX())) {
			xyGraph.getPrimaryXAxis().setTitle("");
			xyGraph.getPrimaryXAxis().setTitleFont(UtilResource.getFont("", 1, SWT.NONE, false, false));
		} else {
			xyGraph.getPrimaryXAxis().setTitle(dataChart.getLabelX());
			xyGraph.getPrimaryXAxis().setTitleFont(UtilResource.getFont("", 12, SWT.BOLD, false, false));
		}
		xyGraph.getPrimaryXAxis().setDateEnabled(true);
		xyGraph.getPrimaryXAxis().setFormatPattern(axisDateFormat);
		xyGraph.getPrimaryXAxis().setAutoScale(false);
		Date fromTime = dataChart.getRowCount() == 0 ? new Date() : dataChart.getSeriesX(0);
		Date toTime = dataChart.getRowCount() == 0 ? new Date() : dataChart.getSeriesX(dataChart.getRowCount() - 1);
		xyGraph.getPrimaryXAxis().setAutoScale(false);
		xyGraph.getPrimaryXAxis().setRange(new EmpRange(fromTime.getTime(), toTime.getTime()));
		xyGraph.getPrimaryXAxis().setShowMajorGrid(true);
		xyGraph.getPrimaryXAxis().setAutoScaleThreshold(0);
		xyGraph.setShowLegend(showLegend);

		if (UtilString.isEmpty(dataChart.getLabelY())) {
			xyGraph.getPrimaryYAxis().setTitle("");
			xyGraph.getPrimaryYAxis().setTitleFont(UtilResource.getFont("", 1, SWT.NONE, false, false));
		} else {
			xyGraph.getPrimaryYAxis().setTitle(dataChart.getLabelY());
			xyGraph.getPrimaryYAxis().setTitleFont(UtilResource.getFont("", 12, SWT.BOLD, false, false));
		}
		xyGraph.getPrimaryYAxis().setFormatPattern("###,###.#");
		xyGraph.getPrimaryYAxis().setAutoScale(false);
		xyGraph.getPrimaryYAxis().setRange(new EmpRange(dataChart.getMinValue(), dataChart.getMaxValue()));
		xyGraph.getPrimaryYAxis().setShowMajorGrid(true);

		for (int column = 0; column < dataChart.getColumnCount(); column++) {
			if (dataChart.isColumn_view(column)) {
				CircularBufferDataProvider dataProvider = new CircularBufferDataProvider(false);
				dataProvider.setBufferSize(Math.max(1, dataChart.getRowCount()));
				for (int row = 0; row < dataChart.getRowCount(); row++) {
					Date collect_time = dataChart.getSeriesX(row);
					Double value = dataChart.getValueAt(row, column);
					if (value != null) {
						dataProvider.addSample(new Sample(collect_time.getTime(), value == null ? Double.NaN : value));
					}
				}

				Trace trace = new Trace(dataChart.getColumnTitle(column), xyGraph.getPrimaryXAxis(), xyGraph.getPrimaryYAxis(), dataProvider);
				trace.setTraceColor(getItemColor(column, null));
				trace.setTraceType(TraceType.DOT_LINE);
				trace.setLineWidth(1);
				trace.setPointStyle(PointStyle.POINT);
				trace.setPointSize(4);
				trace.setBaseLine(BaseLine.ZERO);
				trace.setAreaAlpha(100);
				trace.setAntiAliasing(true);
				trace.setErrorBarEnabled(false);
				trace.setYErrorBarType(ErrorBarType.NONE);
				trace.setXErrorBarType(ErrorBarType.NONE);
				trace.setErrorBarCapWidth(0);
				xyGraph.addTrace(trace);
			}
		}

		return xyGraph;
	}

	/**
	 * 차트를 새로고침 합니다.
	 *
	 * @param chart
	 *            차트
	 * @param dataChart
	 *            차트 모델
	 * @param axisDateFormat
	 *            축의 날짜 형식
	 * @param tooltipDateFormat
	 *            툴팁의 날짜 형식
	 */
	public static void refreshChart(XYGraph xyGraph, DataChartIf dataChart, String axisDateFormat, String tooltipDateFormat) {
		Date fromTime = dataChart.getRowCount() == 0 ? new Date() : dataChart.getSeriesX(0);
		Date toTime = dataChart.getRowCount() == 0 ? new Date() : dataChart.getSeriesX(dataChart.getRowCount() - 1);
		xyGraph.getPrimaryXAxis().setRange(new EmpRange(fromTime.getTime(), toTime.getTime()));
		xyGraph.getPrimaryYAxis().setRange(new EmpRange(dataChart.getMinValue(), dataChart.getMaxValue()));

		for (Object trace : xyGraph.getPlotArea().getTraceList().toArray()) {
			xyGraph.removeTrace((Trace) trace);
		}
		for (int column = 0; column < dataChart.getColumnCount(); column++) {
			if (dataChart.isColumn_view(column)) {
				CircularBufferDataProvider dataProvider = new CircularBufferDataProvider(false);
				dataProvider.setBufferSize(Math.max(1, dataChart.getRowCount()));
				for (int row = 0; row < dataChart.getRowCount(); row++) {
					Date collect_time = dataChart.getSeriesX(row);
					Double value = dataChart.getValueAt(row, column);
					if (value != null) {
						dataProvider.addSample(new Sample(collect_time.getTime(), value == null ? Double.NaN : value));
					}
				}

				Trace trace = new Trace(dataChart.getColumnTitle(column), xyGraph.getPrimaryXAxis(), xyGraph.getPrimaryYAxis(), dataProvider);
				trace.setTraceColor(getItemColor(column, null));
				trace.setTraceType(TraceType.DOT_LINE);
				trace.setLineWidth(1);
				trace.setPointStyle(PointStyle.POINT);
				trace.setPointSize(4);
				trace.setBaseLine(BaseLine.ZERO);
				trace.setAreaAlpha(100);
				trace.setAntiAliasing(true);
				trace.setErrorBarEnabled(false);
				trace.setYErrorBarType(ErrorBarType.NONE);
				trace.setXErrorBarType(ErrorBarType.NONE);
				trace.setErrorBarCapWidth(0);
				xyGraph.addTrace(trace);
			}
		}
	}

	public static Color getItemColor(int column, Color color) {
		if (color == null) {
			return column < ITEM_COLORS.length ? UtilResource.getColor(ITEM_COLORS[column]) : UtilResource.getColor(DEFAULT_ITEM_COLOR);
		} else {
			return color;
		}
	}

}
