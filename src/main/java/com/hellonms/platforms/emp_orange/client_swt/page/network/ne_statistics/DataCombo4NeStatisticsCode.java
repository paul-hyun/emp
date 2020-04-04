package com.hellonms.platforms.emp_orange.client_swt.page.network.ne_statistics;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.hellonms.platforms.emp_onion.client_swt.data.combo.DataComboAt;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO;
import com.hellonms.platforms.emp_util.string.UtilString;

/**
 * <p>
 * DataCombo4NeStatisticsCode
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class DataCombo4NeStatisticsCode extends DataComboAt {

	/**
	 * 성능항목 배열
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

	protected void setData(EMP_MODEL_NE_INFO[] ne_info_defs) {
		List<EMP_MODEL_NE_INFO> ne_info_list = new ArrayList<EMP_MODEL_NE_INFO>();
		for (EMP_MODEL_NE_INFO ne_info : ne_info_defs) {
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
		} else {
			return super.isNeedUpdate(datas);
		}
	}

	private boolean isNeedUpdate(EMP_MODEL_NE_INFO[] ne_info_defs) {
		if (ITEMS.length != ne_info_defs.length) {
			return true;
		}

		for (int i = 0; i < ITEMS.length; i++) {
			if (!ITEMS[i].equals(ne_info_defs[i])) {
				return true;
			}
		}

		return false;
	}

	@Override
	public Object findItem(Object element) {
		if (element instanceof EMP_MODEL_NE_INFO) {
			for (EMP_MODEL_NE_INFO ne_info_def : ITEMS) {
				if (ne_info_def.equals(element)) {
					return ne_info_def;
				}
			}
		}
		return super.findItem(element);
	}

}
