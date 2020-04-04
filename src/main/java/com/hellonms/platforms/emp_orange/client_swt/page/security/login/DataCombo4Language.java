package com.hellonms.platforms.emp_orange.client_swt.page.security.login;

import org.eclipse.swt.graphics.Image;

import com.hellonms.platforms.emp_core.share.language.LANGUAGE;
import com.hellonms.platforms.emp_onion.client_swt.data.combo.DataComboAt;

/**
 * <p>
 * DataCombo4Language
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class DataCombo4Language extends DataComboAt {

	/**
	 * 언어 배열
	 */
	protected LANGUAGE[] ITEMS = {};

	/**
	 * 선택된 언어
	 */
	protected LANGUAGE selectedLanguage = LANGUAGE.ENGLISH;

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
		if (datas.length == 1 && datas[0] instanceof LANGUAGE[]) {
			setData((LANGUAGE[]) datas[0]);
			refresh();
		}
	}

	/**
	 * @param languages
	 */
	protected void setData(LANGUAGE[] languages) {
		this.ITEMS = languages;
	}

	@Override
	public Object getDefaultItem() {
		return 0 < ITEMS.length ? ITEMS[0] : selectedLanguage;
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
		if (element instanceof LANGUAGE) {
			for (LANGUAGE aaa : ITEMS) {
				if (aaa.equals(element)) {
					return aaa;
				}
			}
		}
		return super.findItem(element);
	}

	/**
	 * 선택된 언어를 반환합니다.
	 * 
	 * @return 선택된 언어
	 */
	public LANGUAGE getSelectedLanguage() {
		return selectedLanguage;
	}

	/**
	 * 선택된 언어를 설정합니다.
	 * 
	 * @param selectedLanguage
	 *            선택된 언어
	 */
	public void setSelectedLanaguage(LANGUAGE selectedLanguage) {
		this.selectedLanguage = selectedLanguage;
	}
}
