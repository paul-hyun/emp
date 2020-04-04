package com.hellonms.platforms.emp_orange.client_swt.page.network.ne_info;

import org.eclipse.swt.graphics.Image;

import com.hellonms.platforms.emp_onion.client_swt.data.combo.DataComboAt;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_ENUM_FIELD;

/**
 * <p>
 * DataCombo4NeFieldEnum
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class DataCombo4NeFieldEnum extends DataComboAt {

	/**
	 * 사용자 그룹 모델 배열
	 */
	protected EMP_MODEL_ENUM_FIELD[] ITEMS = {};

	@Override
	public Object[] getElements(Object inputElement) {
		return ITEMS;
	}

	@Override
	public Object getInput() {
		return this;
	}

	@Override
	public Image getImage(Object element) {
		return null;
	}

	@Override
	public String getText(Object element) {
		if (element instanceof EMP_MODEL_ENUM_FIELD) {
			return ((EMP_MODEL_ENUM_FIELD) element).getName();
		} else {
			return String.valueOf(element);
		}
	}

	@Override
	public void setDatas(Object... datas) {
		if (datas.length == 1 && datas[0] instanceof EMP_MODEL_ENUM_FIELD[]) {
			setData((EMP_MODEL_ENUM_FIELD[]) datas[0]);
			refresh();
		}
	}

	/**
	 * 데이터를 설정합니다.
	 * 
	 * @param neInfoCodes
	 *            사용자 그룹 모델 배열
	 */
	protected void setData(EMP_MODEL_ENUM_FIELD[] neInfoCodes) {
		this.ITEMS = neInfoCodes;
	}

	@Override
	public Object getDefaultItem() {
		return 0 < ITEMS.length ? ITEMS[0] : null;
	}

	@Override
	public Object getItem(int index) {
		if (ITEMS != null && -1 < index && index < ITEMS.length) {
			return ITEMS[index];
		}
		return super.getItem(index);
	}

	@Override
	public boolean isNeedUpdate(Object... datas) {
		if (datas.length == 1 && datas[0] instanceof EMP_MODEL_ENUM_FIELD[]) {
			return isNeedUpdate((EMP_MODEL_ENUM_FIELD[]) datas[0]);
		}
		return super.isNeedUpdate(datas);
	}

	/**
	 * 업데이트가 필요한지 확인합니다.
	 * 
	 * @param neInfoCodes
	 *            사용자 그룹 모델 배열
	 * @return 확인 결과
	 */
	public boolean isNeedUpdate(EMP_MODEL_ENUM_FIELD[] neInfoCodes) {
		if (this.ITEMS.length == neInfoCodes.length) {
			for (int i = 0; i < neInfoCodes.length; i++) {
				EMP_MODEL_ENUM_FIELD neInfoCode = (EMP_MODEL_ENUM_FIELD) this.ITEMS[i];
				if (neInfoCode.equals(neInfoCodes[i])) {
					return true;
				}
			}
			return false;
		} else {
			return true;
		}
	}

	@Override
	public Object findItem(Object element) {
		if (element instanceof EMP_MODEL_ENUM_FIELD) {
			EMP_MODEL_ENUM_FIELD neInfoCode = (EMP_MODEL_ENUM_FIELD) element;
			for (int i = 0; i < ITEMS.length; i++) {
				if (neInfoCode.equals(((EMP_MODEL_ENUM_FIELD) ITEMS[i]))) {
					return ITEMS[i];
				}
			}
		}
		return super.findItem(element);
	}

}
