package com.hellonms.platforms.emp_orange.client_swt.page.emp_model;

import org.eclipse.swt.graphics.Image;

import com.hellonms.platforms.emp_onion.client_swt.data.combo.DataComboAt;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_TYPE;

public class DataCombo4DataTypeLocal extends DataComboAt {

	private EMP_MODEL_TYPE[] datas = {};

	@Override
	public Object getInput() {
		return this;
	}

	@Override
	public void setDatas(Object... datas) {
		if (datas.length == 1 && datas[0] instanceof EMP_MODEL_TYPE[]) {
			setData((EMP_MODEL_TYPE[]) datas[0]);
			refresh();
		}
	}

	private void setData(EMP_MODEL_TYPE[] emp_model_types) {
		this.datas = emp_model_types;
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

	@Override
	public boolean isNeedUpdate(Object... datas) {
		if (datas.length == 1 && datas[0] instanceof EMP_MODEL_TYPE[]) {
			return isNeedUpdate((EMP_MODEL_TYPE[]) datas[0]);
		} else {
			return super.isNeedUpdate(datas);
		}
	}

	private boolean isNeedUpdate(EMP_MODEL_TYPE[] emp_model_types) {
		if (datas.length != emp_model_types.length) {
			return true;
		}

		for (int i = 0; i < datas.length; i++) {
			if (!datas[i].equals(emp_model_types[i])) {
				return true;
			}
		}

		return false;
	}

	@Override
	public Object findItem(Object element) {
		if (element instanceof EMP_MODEL_TYPE) {
			for (EMP_MODEL_TYPE ne_info_def : datas) {
				if (ne_info_def.equals(element)) {
					return ne_info_def;
				}
			}
		}
		return super.findItem(element);
	}

}
