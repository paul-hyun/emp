package com.hellonms.platforms.emp_onion.client_swt.data.chart;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelChartIf;

/**
 * <p>
 * 차트의 데이터를 처리하기 위한 공통적인 기능을 미리 구현한 추상 클래스
 * </p>
 * 
 * @since 1.6
 * @create 2015. 5. 21.
 * @modified 2015. 5. 21.
 * @author jungsun
 *
 */
public abstract class DataChartAt implements DataChartIf {

	protected List<Double[]> dataSerieList = new ArrayList<Double[]>();

	protected double minValue = 0;

	protected double maxValue = 0;

	protected Set<Integer> column_hides = new TreeSet<Integer>();

	protected List<PanelChartIf> panelChartList = new ArrayList<PanelChartIf>();

	@Override
	public Double[] getDataSerie(int column) {
		if (column < dataSerieList.size()) {
			return dataSerieList.get(column);
		} else {
			return new Double[] {};
		}
	}

	@Override
	public double getMinValue() {
		return minValue;
	}

	@Override
	public double getMaxValue() {
		return maxValue;
	}

	/**
	 * column의 값 모음 생성
	 */
	protected void makeDataSerie() {
		minValue = 0;
		maxValue = 0;
		dataSerieList.clear();

		int columnCount = getColumnCount();
		int rowCount = getRowCount();
		for (int column = 0; column < columnCount; column++) {
			Double[] dataSerie;
			if (isColumn_view(column)) {
				dataSerie = new Double[rowCount];
				for (int row = 0; row < rowCount; row++) {
					dataSerie[row] = getValueAt(row, column);
					if (dataSerie[row] != null) {
						minValue = Math.min(minValue, dataSerie[row]);
						maxValue = Math.max(maxValue, dataSerie[row]);
					}
				}
			} else {
				dataSerie = new Double[0];
			}
			dataSerieList.add(dataSerie);
		}
	}

	@Override
	public void addPanelChart(PanelChartIf panelChart) {
		panelChartList.add(panelChart);
	}

	@Override
	public void removePanelChart(PanelChartIf panelChart) {
		panelChartList.remove(panelChart);
	}

	@Override
	public void refresh() {
		if (panelChartList.size() > 0) {
			panelChartList.get(0).getDisplay().syncExec(new Runnable() {
				@Override
				public void run() {
					for (PanelChartIf panelChart : panelChartList) {
						panelChart.refresh();
					}
				}
			});
		}
	}

	@Override
	public boolean isColumn_view(int column) {
		return !column_hides.contains(column);
	}

	@Override
	public void setColumn_hides(int[] column_hides) {
		this.column_hides.clear();
		for (int column_hide : column_hides) {
			this.column_hides.add(column_hide);
		}
		makeDataSerie();
		refresh();
	}

}
