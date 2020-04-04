package com.hellonms.platforms.emp_orange.client_swt.page.security.user;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;

import com.hellonms.platforms.emp_core.share.language.UtilLanguage;
import com.hellonms.platforms.emp_onion.client_swt.data.table.DataTableAt;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.security.user.Model4User;

/**
 * <p>
 * DataTable4User
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class DataTable4User extends DataTableAt {

	/**
	 * 컬럼명 배열
	 */
	protected String[] COLUMN_NAMES = { UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.USER_ID), //
			UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.USER_NAME), //
			UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.USER_GROUP_NAME), //
	};

	/**
	 * 컬럼 너비 배열
	 */
	protected int[] COLUMN_WIDTHS = { 90, 100, 100, };

	/**
	 * 사용자 모델 배열
	 */
	protected Model4User[] users = {};

	@Override
	public int getColumnCount() {
		return COLUMN_NAMES.length;
	}

	@Override
	public String getColumnTitle(int column) {
		return COLUMN_NAMES[column];
	}

	@Override
	public int getColumnStyle(int column) {
		return SWT.NONE;
	}

	@Override
	public int getColumnWidth(int column) {
		return COLUMN_WIDTHS[column];
	}

	@Override
	public Object getInput() {
		return this;
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return users;
	}

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		Model4User user = (Model4User) element;

		switch (columnIndex) {
		case 0:
			return user.getUser_account();
		case 1:
			return user.getUser_name();
		case 2:
			return user.getUser_group_account();
		default:
			return "";
		}
	}

	@Override
	public Color getForeground(Object element) {
		return null;
	}

	@Override
	public Color getBackground(Object element) {
		return null;
	}

	@Override
	public void clear() {
		this.users = new Model4User[] {};
	}

	@Override
	public void setDatas(Object... datas) {
		if (datas.length == 1 && datas[0] instanceof Model4User[]) {
			setData((Model4User[]) datas[0]);
			refresh();
		}
	}

	/**
	 * 데이터를 설정합니다.
	 * 
	 * @param users
	 *            사용자 모델 배열
	 */
	protected void setData(Model4User[] users) {
		this.users = users;
	}

	@Override
	public Model4User[] getData() {
		return users;
	}

}
