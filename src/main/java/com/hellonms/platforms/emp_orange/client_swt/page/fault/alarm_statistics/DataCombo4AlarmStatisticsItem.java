package com.hellonms.platforms.emp_orange.client_swt.page.fault.alarm_statistics;

import org.eclipse.swt.graphics.Image;

import com.hellonms.platforms.emp_onion.client_swt.data.combo.DataComboAt;
import com.hellonms.platforms.emp_orange.share.model.fault.event.Model4AlarmStatistics.ITEM;

/**
 * <p>
 * DataCombo4AlarmStatisticsItem
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class DataCombo4AlarmStatisticsItem extends DataComboAt {

	/**
	 * 성능항목 배열
	 */
	protected ITEM[] ITEMS = ITEM.values();

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
		return ((ITEM) element).name();
	}

	@Override
	public void setDatas(Object... datas) {
		if (datas.length == 1 && datas[0] instanceof ITEM[]) {
			setData((ITEM[]) datas[0]);
			refresh();
		}
	}

	protected void setData(ITEM[] items) {
		this.ITEMS = items;
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
		if (datas.length == 1 && datas[0] instanceof ITEM[]) {
			return isNeedUpdate((ITEM[]) datas[0]);
		} else {
			return super.isNeedUpdate(datas);
		}
	}

	private boolean isNeedUpdate(ITEM[] items) {
		if (ITEMS.length != items.length) {
			return true;
		}

		for (int i = 0; i < ITEMS.length; i++) {
			if (!ITEMS[i].equals(items[i])) {
				return true;
			}
		}

		return false;
	}

	@Override
	public Object findItem(Object element) {
		if (element instanceof ITEM) {
			for (ITEM category : ITEMS) {
				if (category.equals(element)) {
					return category;
				}
			}
		}
		return super.findItem(element);
	}

}
