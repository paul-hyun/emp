package com.hellonms.platforms.emp_orange.client_swt.page.network.ne_statistics;

import org.eclipse.swt.graphics.Image;

import com.hellonms.platforms.emp_onion.client_swt.data.combo.DataComboAt;
import com.hellonms.platforms.emp_onion.share.model.STATISTICS_TYPE;

/**
 * <p>
 * DataCombo4NeStatisticsType
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class DataCombo4NeStatisticsType extends DataComboAt {

	/**
	 * 성능항목 배열
	 */
	protected STATISTICS_TYPE[] ITEMS = new STATISTICS_TYPE[] { STATISTICS_TYPE.MINUTE_5, STATISTICS_TYPE.HOUR_1, STATISTICS_TYPE.DAY_1, STATISTICS_TYPE.MONTH_1 };

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
		return ((STATISTICS_TYPE) element).name();
	}

	@Override
	public void setDatas(Object... datas) {
		if (datas.length == 1 && datas[0] instanceof STATISTICS_TYPE[]) {
			setData((STATISTICS_TYPE[]) datas[0]);
			refresh();
		}
	}

	protected void setData(STATISTICS_TYPE[] categorys) {
		this.ITEMS = categorys;
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
		if (datas.length == 1 && datas[0] instanceof STATISTICS_TYPE[]) {
			return isNeedUpdate((STATISTICS_TYPE[]) datas[0]);
		} else {
			return super.isNeedUpdate(datas);
		}
	}

	private boolean isNeedUpdate(STATISTICS_TYPE[] categorys) {
		if (ITEMS.length != categorys.length) {
			return true;
		}

		for (int i = 0; i < ITEMS.length; i++) {
			if (!ITEMS[i].equals(categorys[i])) {
				return true;
			}
		}

		return false;
	}

	@Override
	public Object findItem(Object element) {
		if (element instanceof STATISTICS_TYPE) {
			for (STATISTICS_TYPE category : ITEMS) {
				if (category.equals(element)) {
					return category;
				}
			}
		}
		return super.findItem(element);
	}

}
