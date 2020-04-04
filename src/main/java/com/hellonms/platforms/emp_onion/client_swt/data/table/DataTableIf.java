package com.hellonms.platforms.emp_onion.client_swt.data.table;

import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;

import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelTableIf;
import com.hellonms.platforms.emp_util.excel.DataExcelIf;

/**
 * <p>
 * 테이블의 데이터를 처리하기 위한 인터페이스
 * </p>
 * 
 * @since 1.6
 * @create 2015. 5. 21.
 * @modified 2015. 5. 21.
 * @author jungsun
 *
 */
public interface DataTableIf extends DataExcelIf, IStructuredContentProvider, ITableLabelProvider, IColorProvider {

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
	 * @return column의 스타일
	 */
	public int getColumnStyle(int column);

	/**
	 * @param column
	 * @return column의 너비
	 */
	public int getColumnWidth(int column);

	/**
	 * @return this
	 */
	public Object getInput();

	/**
	 * 테이블 데이터가 변경될 경우 PanelTable을 refresh하기 위해 DataTable을 사용하는 PanelTable을 추가
	 * 
	 * @param panelTable
	 */
	public void addPanelTable(PanelTableIf panelTable);

	/**
	 * 더이상 DataTable을 사용하지 않는 PanelTable을 제거
	 * 
	 * @param panelTable
	 */
	public void removePanelTable(PanelTableIf panelTable);

	/**
	 * 테이블 데이터가 변경될 경우 PanelTable을 refresh
	 */
	public void refresh();

	/**
	 * 테이블 데이터를 클리어
	 */
	public void clear();

	/**
	 * 테이블 데이터 설정
	 * 
	 * @param datas
	 */
	public void setDatas(Object... datas);

	/**
	 * @return 데이터
	 */
	public Object getData();

	@Override
	default void dispose() {
	}

}
