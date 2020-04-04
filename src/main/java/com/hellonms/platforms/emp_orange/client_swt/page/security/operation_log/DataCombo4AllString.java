package com.hellonms.platforms.emp_orange.client_swt.page.security.operation_log;

import org.eclipse.swt.graphics.Image;

import com.hellonms.platforms.emp_core.share.language.UtilLanguage;
import com.hellonms.platforms.emp_onion.client_swt.data.combo.DataComboAt;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;

/**
 * <p>
 * DataCombo4AllString
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class DataCombo4AllString extends DataComboAt {

	protected String[] ITEMS = new String[] { UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.ALL) };

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
		return String.valueOf(element);
	}

	@Override
	public void setDatas(Object... datas) {
		if (datas.length == 1 && datas[0] instanceof String[]) {
			setData((String[]) datas[0]);
			refresh();
		}
	}

	protected void setData(String[] services) {
		int length = services == null ? 0 : services.length;
		ITEMS = new String[length + 1];
		ITEMS[0] = UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.ALL);
		for (int i = 0; i < length; i++) {
			ITEMS[i + 1] = services[i];
		}
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
		if (datas.length == 1 && datas[0] instanceof String[]) {
			return isNeedUpdate((String[]) datas[0]);
		} else {
			return super.isNeedUpdate(datas);
		}
	}

	protected boolean isNeedUpdate(String[] elements) {
		if (ITEMS.length != elements.length + 1) {
			return true;
		}

		for (int i = 0; i < elements.length; i++) {
			if (!ITEMS[i + 1].equals(elements[i])) {
				return true;
			}
		}

		return false;
	}

	@Override
	public Object findItem(Object element) {
		if (element instanceof String) {
			for (String aaa : ITEMS) {
				if (aaa.equals(element)) {
					return aaa;
				}
			}
		}
		return super.findItem(element);
	}

}
