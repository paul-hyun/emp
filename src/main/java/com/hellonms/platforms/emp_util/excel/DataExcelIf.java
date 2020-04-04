package com.hellonms.platforms.emp_util.excel;

public interface DataExcelIf {

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
	 * @param 전체
	 *            항목조회
	 * @return
	 */
	public Object[] getElements(Object inputElement);

	/**
	 * @param column
	 * @return column의 너비
	 */
	public int getColumnWidth(int column);

	/**
	 * @param element
	 * @param columnIndex
	 * @return
	 */
	public String getColumnText(Object element, int columnIndex);

}
