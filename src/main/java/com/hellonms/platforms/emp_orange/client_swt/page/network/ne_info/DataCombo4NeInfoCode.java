package com.hellonms.platforms.emp_orange.client_swt.page.network.ne_info;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.hellonms.platforms.emp_onion.client_swt.data.combo.DataComboAt;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO;
import com.hellonms.platforms.emp_util.string.UtilString;

/**
 * <p>
 * DataCombo4NeInfoCode
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class DataCombo4NeInfoCode extends DataComboAt {

	/**
	 * 사용자 그룹 모델 배열
	 */
	protected EMP_MODEL_NE_INFO[] ITEMS = {};

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
		if (element instanceof EMP_MODEL_NE_INFO) {
			EMP_MODEL_NE_INFO ne_info = (EMP_MODEL_NE_INFO) element;
			if (UtilString.isEmpty(ne_info.getDisplay_name())) {
				return ne_info.getName();
			}
			return ne_info.getDisplay_name();
		} else {
			return String.valueOf(element);
		}
	}

	@Override
	public void setDatas(Object... datas) {
		if (datas.length == 1 && datas[0] instanceof EMP_MODEL_NE_INFO[]) {
			setData((EMP_MODEL_NE_INFO[]) datas[0]);
			refresh();
		}
	}

	/**
	 * 데이터를 설정합니다.
	 * 
	 * @param neInfoCodes
	 *            사용자 그룹 모델 배열
	 */
	protected void setData(EMP_MODEL_NE_INFO[] neInfoCodes) {
		List<EMP_MODEL_NE_INFO> ne_info_list = new ArrayList<EMP_MODEL_NE_INFO>();
		for (EMP_MODEL_NE_INFO ne_info : neInfoCodes) {
			if (ne_info.isDisplay_enable()) {
				ne_info_list.add(ne_info);
			}
		}
		this.ITEMS = ne_info_list.toArray(new EMP_MODEL_NE_INFO[0]);
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
		if (datas.length == 1 && datas[0] instanceof EMP_MODEL_NE_INFO[]) {
			return isNeedUpdate((EMP_MODEL_NE_INFO[]) datas[0]);
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
	protected boolean isNeedUpdate(EMP_MODEL_NE_INFO[] neInfoCodes) {
		if (this.ITEMS.length == neInfoCodes.length) {
			for (int i = 0; i < neInfoCodes.length; i++) {
				EMP_MODEL_NE_INFO neInfoCode = (EMP_MODEL_NE_INFO) this.ITEMS[i];
				if (!neInfoCode.equals(neInfoCodes[i])) {
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
		if (element instanceof EMP_MODEL_NE_INFO) {
			EMP_MODEL_NE_INFO neInfoCode = (EMP_MODEL_NE_INFO) element;
			for (int i = 0; i < ITEMS.length; i++) {
				if (neInfoCode.equals(((EMP_MODEL_NE_INFO) ITEMS[i]))) {
					return ITEMS[i];
				}
			}
		}
		return super.findItem(element);
	}

}
