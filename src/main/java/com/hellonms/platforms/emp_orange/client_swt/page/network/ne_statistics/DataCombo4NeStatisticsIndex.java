package com.hellonms.platforms.emp_orange.client_swt.page.network.ne_statistics;

import org.eclipse.swt.graphics.Image;

import com.hellonms.platforms.emp_onion.client_swt.data.combo.DataComboAt;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO;
import com.hellonms.platforms.emp_orange.share.model.network.ne_info.NE_INFO_INDEX;

/**
 * <p>
 * DataCombo4NeStatisticsIndex
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class DataCombo4NeStatisticsIndex extends DataComboAt {

	private EMP_MODEL_NE_INFO ne_info_def;

	/**
	 * 성능항목 배열
	 */
	protected NE_INFO_INDEX[] ITEMS = {};

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
		if (ne_info_def != null) {
			return ((NE_INFO_INDEX) element).toString(ne_info_def);
		}
		return ((NE_INFO_INDEX) element).toString();
	}

	@Override
	public void setDatas(Object... datas) {
		if (datas.length == 2 && datas[0] instanceof EMP_MODEL_NE_INFO && datas[1] instanceof NE_INFO_INDEX[]) {
			setData((EMP_MODEL_NE_INFO) datas[0], (NE_INFO_INDEX[]) datas[1]);
			refresh();
		}
	}

	protected void setData(EMP_MODEL_NE_INFO ne_info_def, NE_INFO_INDEX[] ne_info_indexs) {
		this.ne_info_def = ne_info_def;
		this.ITEMS = ne_info_indexs;
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
		if (datas.length == 2 && datas[0] instanceof EMP_MODEL_NE_INFO && datas[1] instanceof NE_INFO_INDEX[]) {
			return isNeedUpdate((EMP_MODEL_NE_INFO) datas[0], (NE_INFO_INDEX[]) datas[1]);
		} else {
			return super.isNeedUpdate(datas);
		}
	}

	private boolean isNeedUpdate(EMP_MODEL_NE_INFO ne_info_def, NE_INFO_INDEX[] ne_info_indexs) {
		if (this.ne_info_def != ne_info_def) {

		}
		if (ITEMS.length != ne_info_indexs.length) {
			return true;
		}

		for (int i = 0; i < ITEMS.length; i++) {
			if (!ITEMS[i].equals(ne_info_indexs[i])) {
				return true;
			}
		}

		return false;
	}

	@Override
	public Object findItem(Object element) {
		if (element instanceof NE_INFO_INDEX) {
			for (NE_INFO_INDEX category : ITEMS) {
				if (category.equals(element)) {
					return category;
				}
			}
		}
		return super.findItem(element);
	}

}
