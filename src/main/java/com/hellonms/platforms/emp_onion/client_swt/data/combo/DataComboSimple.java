package com.hellonms.platforms.emp_onion.client_swt.data.combo;

import org.eclipse.swt.graphics.Image;

/**
 * <p>
 * 고정 값 combo 처리
 * </p>
 * 
 * @since 1.6
 * @create 2015. 5. 21.
 * @modified 2015. 5. 21.
 * @author jungsun
 *
 */
public class DataComboSimple extends DataComboAt {

	private Object[] datas = {};

	public DataComboSimple(Object... datas) {
		this.datas = datas;
	}

	@Override
	public Object getInput() {
		return this;
	}

	@Override
	public void setDatas(Object... datas) {
		this.datas = datas;
	}

	@Override
	public Object getDefaultItem() {
		return datas.length == 0 ? null : datas[0];
	}

	@Override
	public Object[] getElements(Object element) {
		return datas;
	}

	@Override
	public Image getImage(Object element) {
		return null;
	}

	@Override
	public String getText(Object element) {
		return String.valueOf(element);
	}

}
