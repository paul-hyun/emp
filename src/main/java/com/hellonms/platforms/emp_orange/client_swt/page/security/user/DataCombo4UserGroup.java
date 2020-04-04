package com.hellonms.platforms.emp_orange.client_swt.page.security.user;

import org.eclipse.swt.graphics.Image;

import com.hellonms.platforms.emp_onion.client_swt.data.combo.DataComboAt;
import com.hellonms.platforms.emp_orange.share.model.security.user_group.Model4UserGroup;

/**
 * <p>
 * DataCombo4UserGroup
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class DataCombo4UserGroup extends DataComboAt {

	/**
	 * 사용자 그룹 모델 배열
	 */
	protected Model4UserGroup[] ITEMS = {};

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
		if (element instanceof Model4UserGroup) {
			return ((Model4UserGroup) element).getUser_group_account();
		} else {
			return String.valueOf(element);
		}
	}

	@Override
	public void setDatas(Object... datas) {
		if (datas.length == 1 && datas[0] instanceof Model4UserGroup[]) {
			setData((Model4UserGroup[]) datas[0]);
		}
	}

	/**
	 * 데이터를 설정합니다.
	 * 
	 * @param userGroups
	 *            사용자 그룹 모델 배열
	 */
	protected void setData(Model4UserGroup[] userGroups) {
		this.ITEMS = userGroups;
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

	@Override
	public boolean isNeedUpdate(Object... datas) {
		if (datas.length == 1 && datas[0] instanceof Model4UserGroup[]) {
			return isNeedUpdate((Model4UserGroup[]) datas[0]);
		}
		return false;
	}

	/**
	 * 업데이트가 필요한지 확인합니다.
	 * 
	 * @param userGroups
	 *            사용자 그룹 모델 배열
	 * @return 확인 결과
	 */
	public boolean isNeedUpdate(Model4UserGroup[] userGroups) {
		if (this.ITEMS.length == userGroups.length) {
			for (int i = 0; i < userGroups.length; i++) {
				Model4UserGroup userGroup = (Model4UserGroup) this.ITEMS[i];
				if (userGroup.getUser_group_id() != userGroups[i].getUser_group_id() || !userGroup.getUser_group_account().equals(userGroups[i].getUser_group_account())) {
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
		if (element instanceof Model4UserGroup) {
			Model4UserGroup userGroup = (Model4UserGroup) element;
			for (int i = 0; i < ITEMS.length; i++) {
				if (userGroup.getUser_group_id() == ((Model4UserGroup) ITEMS[i]).getUser_group_id()) {
					return ITEMS[i];
				}
			}
			return null;
		} else if (element instanceof Integer) {
			int user_group_id = (Integer) element;
			for (int i = 0; i < ITEMS.length; i++) {
				if (user_group_id == ((Model4UserGroup) ITEMS[i]).getUser_group_id()) {
					return ITEMS[i];
				}
			}
			return getDefaultItem();
		} else {
			return getDefaultItem();
		}
	}

}