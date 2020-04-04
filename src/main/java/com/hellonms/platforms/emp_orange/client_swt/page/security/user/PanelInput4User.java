package com.hellonms.platforms.emp_orange.client_swt.page.security.user;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.hellonms.platforms.emp_core.share.language.UtilLanguage;
import com.hellonms.platforms.emp_onion.client_swt.data.DataFactory;
import com.hellonms.platforms.emp_onion.client_swt.widget.button.ButtonClick;
import com.hellonms.platforms.emp_onion.client_swt.widget.label.LabelText;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelTree4Check;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelTreeIf;
import com.hellonms.platforms.emp_onion.client_swt.widget.selector.SelectorCombo;
import com.hellonms.platforms.emp_onion.client_swt.widget.text.TextInput4String;
import com.hellonms.platforms.emp_orange.client_swt.ApplicationProperty;
import com.hellonms.platforms.emp_orange.client_swt.page.DataBuilder4Orange.DATA_COMBO_ORANGE;
import com.hellonms.platforms.emp_orange.client_swt.page.DataBuilder4Orange.DATA_TREE_ORANGE;
import com.hellonms.platforms.emp_orange.client_swt.page.PanelBuilder4Orange.TREE_ORANGE;
import com.hellonms.platforms.emp_orange.client_swt.page.PanelFactory;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.fault.event.SEVERITY;
import com.hellonms.platforms.emp_orange.share.model.network.ne_group.Model4NeGroup;
import com.hellonms.platforms.emp_orange.share.model.security.user.Model4User;
import com.hellonms.platforms.emp_orange.share.model.security.user_group.Model4UserGroup;

/**
 * <p>
 * PanelInput4User
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class PanelInput4User extends PanelInput4UserAt {

	/**
	 * 사용자 계정명 입력 필드
	 */
	protected TextInput4String textInputUserAccount;

	/**
	 * 비밀번호1 입력 필드
	 */
	protected TextInput4String textInputPassword1;

	/**
	 * 비밀번호2 입력 필드
	 */
	protected TextInput4String textInputPassword2;

	/**
	 * 사용자 이름 입력 필드
	 */
	protected TextInput4String textInputUserName;

	/**
	 * 사용자 이메일 입력 필드
	 */
	protected TextInput4String textInputUserEmail;

	/**
	 * 전화번호 입력 필드
	 */
	protected TextInput4String textInputPhone;

	/**
	 * 휴대폰 번호 입력 필드
	 */
	protected TextInput4String textInputMobile;

	/**
	 * 사용자 그룹 콤보 뷰어
	 */
	protected SelectorCombo selectorComboUserGroup;

	/**
	 * 통신오류 알람 발생 시 이메일 발송 체크 버튼
	 */
	protected ButtonClick buttonCheckEmailCf;

	/**
	 * 심각 알람 발생 시 이메일 발송 체크 버튼
	 */
	protected ButtonClick buttonCheckEmailCritical;

	/**
	 * 주의 알람 발생 시 이메일 발송 체크 버트
	 */
	protected ButtonClick buttonCheckEmailMajor;

	/**
	 * 경계 알람 발생 시 이메일 발송 체크 버튼
	 */
	protected ButtonClick buttonCheckEmailMinor;

	/**
	 * 통신오류 알람 발생 시 SMS 발송 체크 버튼
	 */
	protected ButtonClick buttonCheckSMSCf;

	/**
	 * 심각 알람 발생 시 SMS 발송 체크 버튼
	 */
	protected ButtonClick buttonCheckSMSCritical;

	/**
	 * 주의 알람 발생 시 SMS 발송 체크 버튼
	 */
	protected ButtonClick buttonCheckSMSMajor;

	/**
	 * 경계 알람 발생 시 SMS 발송 체크 버튼
	 */
	protected ButtonClick buttonCheckSMSMinor;

	/**
	 * NE 그룹 관리 트리 뷰어
	 */
	protected PanelTreeIf panelTreeManageNeGroup;

	/**
	 * 생성자 입니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 * @param style
	 *            스타일
	 * @param listener
	 *            입력 판넬 리스너
	 */
	public PanelInput4User(Composite parent, int style, PANEL_INPUT_TYPE panelInputType, PanelInputListenerIf listener) {
		super(parent, style, panelInputType, listener);

		createGUI();
	}

	/**
	 * GUI를 생성합니다.
	 */
	protected void createGUI() {
		GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.marginHeight = 0;
		gridLayout.marginWidth = 0;
		getContentComposite().setLayout(gridLayout);

		LabelText labelUserAccount = new LabelText(getContentComposite(), SWT.NONE, true);
		labelUserAccount.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		labelUserAccount.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.USER_ID));

		textInputUserAccount = new TextInput4String(getContentComposite(), SWT.BORDER);
		textInputUserAccount.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				checkComplete();
			}
		});
		textInputUserAccount.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		LabelText labelPassword1 = new LabelText(getContentComposite(), SWT.NONE, panelInputType.equals(PANEL_INPUT_TYPE.CREATE));
		labelPassword1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		labelPassword1.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.PASSWORD));

		textInputPassword1 = new TextInput4String(getContentComposite(), SWT.BORDER | SWT.PASSWORD);
		textInputPassword1.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				checkComplete();
			}
		});
		textInputPassword1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		LabelText labelPassword2 = new LabelText(getContentComposite(), SWT.NONE, panelInputType.equals(PANEL_INPUT_TYPE.CREATE));
		labelPassword2.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		labelPassword2.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.CONFIRM_PASSWORD));

		textInputPassword2 = new TextInput4String(getContentComposite(), SWT.BORDER | SWT.PASSWORD);
		textInputPassword2.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				checkComplete();
			}
		});
		textInputPassword2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		LabelText labelUserName = new LabelText(getContentComposite(), SWT.NONE, true);
		labelUserName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		labelUserName.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.USER_NAME));

		textInputUserName = new TextInput4String(getContentComposite(), SWT.BORDER);
		textInputUserName.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				checkComplete();
			}
		});
		textInputUserName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		LabelText labelUserEmail = new LabelText(getContentComposite(), SWT.NONE);
		labelUserEmail.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		labelUserEmail.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.EMAIL));

		textInputUserEmail = new TextInput4String(getContentComposite(), SWT.BORDER);
		textInputUserEmail.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				checkComplete();
			}
		});
		textInputUserEmail.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		LabelText labelPhone = new LabelText(getContentComposite(), SWT.NONE);
		labelPhone.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		labelPhone.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.PHONE));

		textInputPhone = new TextInput4String(getContentComposite(), SWT.BORDER);
		textInputPhone.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				checkComplete();
			}
		});
		textInputPhone.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		LabelText labelMobile = new LabelText(getContentComposite(), SWT.NONE);
		labelMobile.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		labelMobile.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.MOBILE));

		textInputMobile = new TextInput4String(getContentComposite(), SWT.BORDER);
		textInputMobile.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				checkComplete();
			}
		});
		textInputMobile.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		LabelText labelUserGroup = new LabelText(getContentComposite(), SWT.NONE);
		labelUserGroup.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		labelUserGroup.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.USER_GROUP));

		selectorComboUserGroup = new SelectorCombo(getContentComposite(), SWT.READ_ONLY);
		selectorComboUserGroup.setDataCombo(DataFactory.createDataCombo(DATA_COMBO_ORANGE.USER_GROUP));
		selectorComboUserGroup.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				checkComplete();
			}
		});
		selectorComboUserGroup.getControl().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		if (ApplicationProperty.isEmail_status()) {
			LabelText labelEmail = new LabelText(getContentComposite(), SWT.NONE);
			labelEmail.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, false, false, 1, 1));
			labelEmail.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.SEND_EMAIL));

			Composite panlEmail = new Composite(getContentComposite(), SWT.BORDER);
			GridLayout gl_panlEmail = new GridLayout(1, false);
			panlEmail.setLayout(gl_panlEmail);
			panlEmail.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

			buttonCheckEmailCf = new ButtonClick(panlEmail, SWT.CHECK);
			buttonCheckEmailCf.setText(SEVERITY.COMMUNICATION_FAIL.name());

			buttonCheckEmailCritical = new ButtonClick(panlEmail, SWT.CHECK);
			buttonCheckEmailCritical.setText(SEVERITY.CRITICAL.name());

			buttonCheckEmailMajor = new ButtonClick(panlEmail, SWT.CHECK);
			buttonCheckEmailMajor.setText(SEVERITY.MAJOR.name());

			buttonCheckEmailMinor = new ButtonClick(panlEmail, SWT.CHECK);
			buttonCheckEmailMinor.setText(SEVERITY.MINOR.name());
		}

		if (ApplicationProperty.isNe_group_management()) {
			LabelText labelNeGroupManagement = new LabelText(getContentComposite(), SWT.NONE);
			labelNeGroupManagement.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, false, false, 1, 1));
			labelNeGroupManagement.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.MANAGE_NE_GROUP));

			panelTreeManageNeGroup = (PanelTree4Check) PanelFactory.createPanelTree(TREE_ORANGE.MANAGE_NE_GROUP, getContentComposite(), SWT.NONE);
			panelTreeManageNeGroup.setDataTree(DataFactory.createDataTree(DATA_TREE_ORANGE.MANAGE_NE_GROUP));
			panelTreeManageNeGroup.getTree().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		}
	}

	@Override
	protected void checkComplete() {
		if (textInputUserAccount.getText().length() == 0) {
			setErrorMessage(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.INSERT_VALUE, MESSAGE_CODE_ORANGE.USER_ID));
			setComplete(false);
		} else if (panelInputType.equals(PANEL_INPUT_TYPE.CREATE) && textInputPassword1.getText().length() == 0) {
			setErrorMessage(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.INSERT_VALUE, MESSAGE_CODE_ORANGE.NEW_PASSWORD));
			setComplete(false);
		} else if (panelInputType.equals(PANEL_INPUT_TYPE.CREATE) && textInputPassword2.getText().length() == 0) {
			setErrorMessage(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.INSERT_VALUE, MESSAGE_CODE_ORANGE.CONFIRM_PASSWORD));
			setComplete(false);
		} else if (!textInputPassword1.getText().equals(textInputPassword2.getText())) {
			setErrorMessage(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.MISMATCH_PASSWORD));
			setComplete(false);
		} else if (textInputUserName.getText().length() == 0) {
			setErrorMessage(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.INSERT_VALUE, MESSAGE_CODE_ORANGE.USER_NAME));
			setComplete(false);
		} else if (selectorComboUserGroup.getSelection().isEmpty()) {
			setErrorMessage(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.INSERT_VALUE, MESSAGE_CODE_ORANGE.USER_GROUP_NAME));
			setComplete(false);
		} else {
			setErrorMessage(null);
			setComplete(true);
		}

		completeChanged();
	}

	@Override
	public Model4User getModel() {
		applyModel();
		return model;
	}

	@Override
	public void setModel(Model4User model) {
		this.model = model.copy();
		displayModel();
	}

	/**
	 * 사용자 그룹 콤보 모델의 항목을 설정한다.
	 * 
	 * @param userGroups
	 *            사용자 그룹 배열
	 */
	@Override
	public void setModel4UserGroups(Model4UserGroup[] userGroups) {
		boolean needUpdate = selectorComboUserGroup.isNeedUpdate((Object) userGroups);
		if (needUpdate) {
			selectorComboUserGroup.setDatas((Object) userGroups);
			selectorComboUserGroup.refresh();
			selectorComboUserGroup.setSelectedItem(selectorComboUserGroup.findElement(selectorComboUserGroup.getSelectedItem()));
		}
	}

	@Override
	public void setModel4NeGroups(Model4NeGroup[] model4NeGroups) {
		if (ApplicationProperty.isNe_group_management()) {
			panelTreeManageNeGroup.setDatas((Object) model4NeGroups);
		}
	}

	protected void displayModel() {
		if (model != null) {
			textInputUserAccount.setText(model.getUser_account());
			textInputUserAccount.setEnabled(panelInputType.equals(PANEL_INPUT_TYPE.CREATE));
			textInputPassword1.setText("");
			textInputPassword2.setText("");
			textInputUserName.setText(model.getUser_name());
			textInputUserEmail.setText(model.getUser_email());
			textInputPhone.setText(model.getTelephone());
			textInputMobile.setText(model.getMobilephone());
			selectorComboUserGroup.getControl().setEnabled(model.getAccess().isUpdate() && model.getUser_id() != 1);
			selectorComboUserGroup.setSelectedItem(selectorComboUserGroup.findElement(model.getUser_group_id()));
			if (ApplicationProperty.isEmail_status()) {
				buttonCheckEmailCf.setSelection(model.getAlarm_email_state(SEVERITY.COMMUNICATION_FAIL));
				buttonCheckEmailCritical.setSelection(model.getAlarm_email_state(SEVERITY.CRITICAL));
				buttonCheckEmailMajor.setSelection(model.getAlarm_email_state(SEVERITY.MAJOR));
				buttonCheckEmailMinor.setSelection(model.getAlarm_email_state(SEVERITY.MINOR));
			}
			if (ApplicationProperty.isNe_group_management()) {
				List<Model4NeGroup> neGroupList = new ArrayList<Model4NeGroup>();
				for (int ne_group_id : model.getManage_ne_groups()) {
					Model4NeGroup model4NeGroup = ((DataTree4ManageNeGroup) panelTreeManageNeGroup.getDataTree()).getNeGroup(ne_group_id);
					if (model4NeGroup != null) {
						neGroupList.add(model4NeGroup);
					}
				}

				if (panelTreeManageNeGroup instanceof PanelTree4Check) {
					((PanelTree4Check) panelTreeManageNeGroup).setCheckedElements(neGroupList.toArray());
					panelTreeManageNeGroup.getDataTree().refresh();
					((PanelTree4Check) panelTreeManageNeGroup).expandToLevel(2);
				}

			}
		}
		checkComplete();
	}

	protected void applyModel() {
		if (model != null) {
			model.setUser_group_id(((Model4UserGroup) ((IStructuredSelection) selectorComboUserGroup.getSelection()).getFirstElement()).getUser_group_id());
			model.setUser_account(textInputUserAccount.getText());
			model.setPassword(textInputPassword1.getText());
			model.setUser_name(textInputUserName.getText());
			model.setUser_email(textInputUserEmail.getText());
			model.setTelephone(textInputPhone.getText());
			model.setMobilephone(textInputMobile.getText());
			if (ApplicationProperty.isEmail_status()) {
				model.setAlarm_email_state(SEVERITY.COMMUNICATION_FAIL, buttonCheckEmailCf.getSelection());
				model.setAlarm_email_state(SEVERITY.CRITICAL, buttonCheckEmailCritical.getSelection());
				model.setAlarm_email_state(SEVERITY.MAJOR, buttonCheckEmailMajor.getSelection());
				model.setAlarm_email_state(SEVERITY.MINOR, buttonCheckEmailMinor.getSelection());
			}
			if (ApplicationProperty.isNe_group_management()) {
				Object[] checkedTopElements = {};
				if (panelTreeManageNeGroup instanceof PanelTree4Check) {
					checkedTopElements = ((PanelTree4Check) panelTreeManageNeGroup).getCheckedTopElements();
				}

				int[] ne_group_managements = new int[checkedTopElements.length];
				int index = 0;
				for (Object checkedElement : checkedTopElements) {
					ne_group_managements[index++] = ((Model4NeGroup) checkedElement).getNe_group_id();
				}

				model.setManage_ne_groups(ne_group_managements);
			} else {
				model.setManage_ne_groups(new int[] { Model4NeGroup.ROOT_NE_GROUP_ID });
			}
			model.setDescription("");
			model.setAdmin_state(true);
		}
	}

}
