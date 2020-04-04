package com.hellonms.platforms.emp_orange.client_swt.page.fault.event;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.hellonms.platforms.emp_core.share.language.UtilLanguage;
import com.hellonms.platforms.emp_onion.client_swt.data.combo.DataComboAt;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_EVENT;

/**
 * <p>
 * DataCombo4EventCode
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class DataCombo4EventCode extends DataComboAt {

	/**
	 * 알람등급 배열
	 */
	protected Object[] ITEMS = {};

	public DataCombo4EventCode() {
		List<Object> itemList = new ArrayList<Object>();
		itemList.add(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.ALL));
		for (EMP_MODEL_EVENT event_def : EMP_MODEL.current().getEvents()) {
			itemList.add(event_def);
		}
		ITEMS = itemList.toArray(new Object[0]);
	}

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
		if (element instanceof EMP_MODEL_EVENT) {
			return ((EMP_MODEL_EVENT) element).getSpecific_problem();
		} else {
			return String.valueOf(element);
		}
	}

	@Override
	public void setDatas(Object... datas) {
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
	public Object findItem(Object element) {
		if (element instanceof Object) {
			for (Object aaa : ITEMS) {
				if (aaa.equals(element)) {
					return aaa;
				}
			}
		} else if (element instanceof String && ITEMS[0].equals(element)) {
			return ITEMS[0];
		}
		return super.findItem(element);
	}

}
