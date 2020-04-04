package com.hellonms.platforms.emp_orange.client_swt.page.help.environment.db_backup;

import org.eclipse.swt.graphics.Image;

import com.hellonms.platforms.emp_onion.client_swt.data.combo.DataComboAt;

public class DataCombo4Counter extends DataComboAt {

	/**
	 * 일정 간격으로 숫자가 증가되는 항목
	 */
	protected Integer[] ITEMS = {};

	/**
	 * 카운트 항목의 개수를 입력 받는 생성자 입니다.
	 * <p>
	 * 1부터 카운트의 개수만큼 1씩 증가합니다.
	 * </p>
	 * 
	 * @param count
	 *            항목의 개수
	 */
	public DataCombo4Counter(int count) {
		this(count, 1, 1);
	}

	/**
	 * 카운트 항목의 개수와 간격을 입력 받는 생성자 입니다.
	 * <p>
	 * 시작값도 간격의 값으로 설정됩니다.
	 * </p>
	 * 
	 * @param count
	 *            항목의 개수
	 * @param interval
	 *            간격과 시작값
	 */
	public DataCombo4Counter(int count, int interval) {
		this(count, interval, interval);
	}

	/**
	 * 카운트 항목의 개수, 간격, 시작값을 입력 받는 생성자 입니다.
	 * 
	 * @param count
	 *            항목의 개수
	 * @param interval
	 *            간격
	 * @param start
	 *            시작값
	 */
	public DataCombo4Counter(int count, int interval, int start) {
		this.ITEMS = new Integer[count];
		for (int i = 0; i < ITEMS.length; i++) {
			ITEMS[i] = start + interval * i;
		}
	}

	public DataCombo4Counter(int[] counts) {
		if (counts != null) {
			this.ITEMS = new Integer[counts.length];
			for (int i = 0; i < ITEMS.length; i++) {
				ITEMS[i] = counts[i];
			}
		}

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
		return String.valueOf(element);
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
		return null;
	}

}
