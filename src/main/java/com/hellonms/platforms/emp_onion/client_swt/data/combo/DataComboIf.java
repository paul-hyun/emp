package com.hellonms.platforms.emp_onion.client_swt.data.combo;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;

import com.hellonms.platforms.emp_onion.client_swt.widget.selector.SelectorCombo;

/**
 * <p>
 * 콤보의 데이터를 처리하기 위한 인터페이스
 * </p>
 * 
 * @since 1.6
 * @create 2015. 5. 21.
 * @modified 2015. 5. 21.
 * @author jungsun
 *
 */
public interface DataComboIf extends IStructuredContentProvider, ILabelProvider {

	/**
	 * @return this
	 */
	public Object getInput();

	/**
	 * 콤보 데이터가 변경될 경우 SelectorCombo를 refresh하기 위해 DataCombo를 사용하는 SelectorCombo를 추가
	 * 
	 * @param selectorCombo
	 */
	public void addSelector(SelectorCombo selectorCombo);

	/**
	 * 더이상 DataCombo를 사용하지 않는 SelectorCombo를 제거
	 * 
	 * @param selectorCombo
	 */
	public void removeSelector(SelectorCombo selectorCombo);

	/**
	 * 콤보 데이터가 변경될 경우 SelectorCombo를 refresh
	 */
	public void refresh();

	/**
	 * 콤보 데이터 설정
	 * 
	 * @param datas
	 */
	public void setDatas(Object... datas);

	/**
	 * @return 기본 항목
	 */
	public Object getDefaultItem();

	/**
	 * @param index
	 * @return 항목
	 */
	public Object getItem(int index);

	/**
	 * @param datas
	 * @return 업데이터 필요여부
	 */
	public boolean isNeedUpdate(Object... datas);

	/**
	 * @param element
	 * @return 찾은 항목
	 */
	public Object findItem(Object element);

	@Override
	default void dispose() {
	}

}
