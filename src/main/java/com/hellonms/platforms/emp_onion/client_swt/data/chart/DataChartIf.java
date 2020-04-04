package com.hellonms.platforms.emp_onion.client_swt.data.chart;

import java.util.Date;

import org.eclipse.swt.graphics.Color;

import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelChartIf;

/**
 * <p>
 * 차트의 데이터를 처리하기 위한 인터페이스
 * </p>
 * 
 * @since 1.6
 * @create 2015. 5. 21.
 * @modified 2015. 5. 21.
 * @author jungsun
 *
 */
public interface DataChartIf {

	/**
	 * @return 차트 가로축의 제목
	 */
	public String getLabelX();

	/**
	 * @return 차트 세로측의 제목
	 */
	public String getLabelY();

	/**
	 * @param row
	 * @return 차트 가로축의 값
	 */
	public Date getSeriesX(int row);

	/**
	 * @return column의 개수
	 */
	public int getColumnCount();

	/**
	 * @param column
	 * @return column의 이름
	 */
	public String getColumnTitle(int column);

	/**
	 * @param column
	 * @return column의 색
	 */
	public Color getColumnColor(int column);

	/**
	 * @return row의 개수
	 */
	public int getRowCount();

	/**
	 * @param row
	 * @param column
	 * @return 값
	 */
	public Double getValueAt(int row, int column);

	/**
	 * @param column
	 * @return column의 값 모음 (라인 차트에서 하나의 라인을 이루는 값)
	 */
	public Double[] getDataSerie(int column);

	/**
	 * @return 최소값
	 */
	public double getMinValue();

	/**
	 * @return 최대값
	 */
	public double getMaxValue();

	/**
	 * 차트 데이터가 변경될 경우 PanelChart를 refresh하기 위해 DataChart를 사용하는 PanelChart를 추가
	 * 
	 * @param panelChart
	 */
	public void addPanelChart(PanelChartIf panelChart);

	/**
	 * 더이상 DataChart를 사용하지 않는 PanelChart를 제거
	 * 
	 * @param panelChart
	 */
	public void removePanelChart(PanelChartIf panelChart);

	/**
	 * 차트 데이터가 변경될 경우 PanelChart를 refresh
	 */
	public void refresh();

	/**
	 * 차트 데이터 설정
	 * 
	 * @param datas
	 */
	public void setDatas(Object... datas);

	/**
	 * @param column
	 * @return column의 표시 상태
	 */
	public boolean isColumn_view(int column);

	/**
	 * 차트에 표시되지 않는 column 설정
	 * 
	 * @param column_hides
	 */
	public void setColumn_hides(int[] column_hides);

}
