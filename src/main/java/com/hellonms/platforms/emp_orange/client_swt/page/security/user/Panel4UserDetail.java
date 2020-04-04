package com.hellonms.platforms.emp_orange.client_swt.page.security.user;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;

import com.hellonms.platforms.emp_core.share.language.UtilLanguage;
import com.hellonms.platforms.emp_onion.client_swt.widget.button.ButtonClick;
import com.hellonms.platforms.emp_onion.client_swt.widget.dialog.DialogMessage;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.Panel;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelInputAt.PANEL_INPUT_TYPE;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelInputAt.PanelInputListenerIf;
import com.hellonms.platforms.emp_orange.client_swt.ApplicationProperty;
import com.hellonms.platforms.emp_orange.client_swt.model_display.ModelDisplay4User;
import com.hellonms.platforms.emp_orange.client_swt.page.PanelFactory;
import com.hellonms.platforms.emp_orange.client_swt.page.PanelBuilder4Orange.INPUT_ORANGE;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.security.operation_log.OPERATION_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.security.user.Model4User;

/**
 * <p>
 * Panel4UserDetail
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class Panel4UserDetail extends Panel {

	/**
	 * 사용자 상세정보 판넬의 리스너 인터페이스 입니다.
	 * 
	 * @since Version 1.4.0
	 * @author Hello NMS
	 */
	public interface Panel4UserDetailListenerIf {

		/**
		 * 사용자 등록정보를 수정합니다.
		 * 
		 * @param user
		 *            사용자 모델
		 */
		public void updateUser(Model4User model4User);

		/**
		 * 사용자를 삭제합니다.
		 * 
		 * @param user_id
		 *            사용자 아이디
		 */
		public void deleteUser(Model4User model4User);

	}

	/**
	 * 입력판넬의 리스너 인터페이스를 구현한 리스너 클래스 입니다.
	 * 
	 * @since Version 1.4.0
	 * @author Hello NMS
	 */
	public class Panel4UserDetailChildListener implements //
			PanelInputListenerIf {

		@Override
		public void completeChanged() {
		}

	}

	/**
	 * 리스너
	 */
	protected Panel4UserDetailListenerIf listener;

	/**
	 * 사용자 입력판넬
	 */
	protected PanelInput4UserAt panelInput4User;

	/**
	 * 수정 버튼
	 */
	protected ButtonClick buttonUpdate;

	/**
	 * 삭제 버튼
	 */
	protected ButtonClick buttonDelete;

	/**
	 * 생성자 입니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 * @param style
	 *            스타일
	 * @param listener
	 *            리스너
	 */
	public Panel4UserDetail(Composite parent, int style, Panel4UserDetailListenerIf listener) {
		super(parent, style, UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.USER_DETAIL));
		this.listener = listener;

		createGUI();
	}

	/**
	 * GUI를 생성합니다.
	 */
	protected void createGUI() {
		getContentComposite().setLayout(new FormLayout());

		panelInput4User = (PanelInput4UserAt) PanelFactory.createPanelInput(INPUT_ORANGE.USER, getContentComposite(), SWT.NONE, PANEL_INPUT_TYPE.UPDATE, new Panel4UserDetailChildListener(), PANEL_INPUT_TYPE.UPDATE);
		FormData fd_panelInput4User = new FormData();
		fd_panelInput4User.bottom = new FormAttachment(100, -5);
		fd_panelInput4User.right = new FormAttachment(100, -80);
		fd_panelInput4User.top = new FormAttachment(0, 5);
		fd_panelInput4User.left = new FormAttachment(0, 5);
		panelInput4User.setLayoutData(fd_panelInput4User);

		ButtonClick[] detailButtons = createDetailButton(getContentComposite());
		for (int i = 0; i < detailButtons.length; i++) {
			FormData fd_button = new FormData();
			fd_button.right = (i == 0) ? new FormAttachment(100, -5) : new FormAttachment(detailButtons[i - 1], 0, SWT.RIGHT);
			fd_button.left = (i == 0) ? new FormAttachment(panelInput4User, 5) : new FormAttachment(detailButtons[i - 1], 0, SWT.LEFT);
			fd_button.top = (i == 0) ? new FormAttachment(panelInput4User, 0, SWT.TOP) : new FormAttachment(detailButtons[i - 1], 5, SWT.BOTTOM);
			detailButtons[i].setLayoutData(fd_button);
		}
	}

	/**
	 * 상세뷰어의 버튼을 생성합니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 * @return 버튼 배열
	 */
	protected ButtonClick[] createDetailButton(Composite parent) {
		List<ButtonClick> buttonList = new ArrayList<ButtonClick>();

		if (ApplicationProperty.getOperation_authority(OPERATION_CODE_ORANGE.SECURITY_USER_UPDATE)) {
			buttonUpdate = new ButtonClick(parent, SWT.NONE);
			buttonUpdate.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					Panel4UserDetail.this.updateUser();
				}
			});
			buttonUpdate.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.UPDATE));
			buttonUpdate.setEnabled(false);
			buttonList.add(buttonUpdate);
		}

		if (ApplicationProperty.getOperation_authority(OPERATION_CODE_ORANGE.SECURITY_USER_DELETE)) {
			buttonDelete = new ButtonClick(parent, SWT.NONE);
			buttonDelete.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					Panel4UserDetail.this.deleteUser();
				}
			});
			buttonDelete.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.DELETE));
			buttonDelete.setEnabled(false);
			buttonList.add(buttonDelete);
		}

		return buttonList.toArray(new ButtonClick[0]);
	}

	/**
	 * 화면에 출력합니다.
	 * 
	 * @param modelDisplay4User
	 *            사용자 표시모델
	 */
	public void display(ModelDisplay4User modelDisplay4User) {
		panelInput4User.setModel4UserGroups(modelDisplay4User.getModel4UserGroups());
		panelInput4User.setModel4NeGroups(modelDisplay4User.getModel4NeGroups());
		panelInput4User.setModel(new Model4User());

		setEnabled(buttonUpdate, false);
		setEnabled(buttonDelete, false);
	}

	/**
	 * 화면에 출력합니다.
	 * 
	 * @param user
	 *            사용자 모델
	 */
	public void display(Model4User model4User) {
		if (model4User == null) {
			panelInput4User.setModel(new Model4User());

			setEnabled(buttonUpdate, false);
			setEnabled(buttonDelete, false);
		} else {
			panelInput4User.setModel(model4User);

			setEnabled(buttonUpdate, model4User.getAccess().isUpdate());
			setEnabled(buttonDelete, model4User.getAccess().isDelete());
		}
	}

	private void setEnabled(ButtonClick button, boolean enabled) {
		if (button != null) {
			button.setEnabled(enabled);
		}
	}

	/**
	 * 사용자 등록정보를 수정합니다.
	 */
	protected void updateUser() {
		if (!panelInput4User.isComplete()) {
			DialogMessage.openInfo(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.UPDATE_TITLE, MESSAGE_CODE_ORANGE.USER_MANAGEMENT), panelInput4User.getErrorMessage());
			return;
		}

		Model4User model4User = panelInput4User.getModel();
		if (DialogMessage.openConfirm(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.UPDATE_TITLE, MESSAGE_CODE_ORANGE.USER_MANAGEMENT), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.UPDATE_CONFIRM, MESSAGE_CODE_ORANGE.USER, model4User.getUser_account()))) {
			listener.updateUser(model4User);
		}
	}

	/**
	 * 사용자를 삭제합니다.
	 */
	protected void deleteUser() {
		Model4User model4User = panelInput4User.getModel();
		if (DialogMessage.openConfirm(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.DELETE_TITLE, MESSAGE_CODE_ORANGE.USER_MANAGEMENT), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.DELETE_CONFIRM, MESSAGE_CODE_ORANGE.USER, model4User.getUser_account()))) {
			listener.deleteUser(model4User);
		}
	}

}
