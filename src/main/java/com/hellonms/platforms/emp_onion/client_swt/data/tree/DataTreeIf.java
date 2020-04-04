package com.hellonms.platforms.emp_onion.client_swt.data.tree;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;

import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelTreeIf;

/**
 * <p>
 * 트리의 데이터를 처리하기 위한 인터페이스
 * </p>
 * 
 * @since 1.6
 * @create 2015. 5. 21.
 * @modified 2015. 5. 21.
 * @author jungsun
 *
 */
public interface DataTreeIf extends ITreeContentProvider, ILabelProvider {

	/**
	 * @return this
	 */
	public Object getInput();

	/**
	 * 트리 데이터가 변경될 경우 PanelTree를 refresh하기 위해 DataTree를 사용하는 PanelTree를 추가
	 * 
	 * @param panelTable
	 */
	public void addPanelTree(PanelTreeIf panelTree);

	/**
	 * 더이상 DataTree를 사용하지 않는 PanelTree를 제거
	 * 
	 * @param panelTree
	 */
	public void removePanelTree(PanelTreeIf panelTree);

	/**
	 * 트리 데이터가 변경될 경우 PanelTree를 refresh
	 */
	public void refresh();

	/**
	 * 트리 데이터 설정
	 * 
	 * @param datas
	 */
	public void setDatas(Object... datas);

	@Override
	default void dispose() {
	}

}
