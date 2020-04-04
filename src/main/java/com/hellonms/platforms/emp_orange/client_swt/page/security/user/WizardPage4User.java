package com.hellonms.platforms.emp_orange.client_swt.page.security.user;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.hellonms.platforms.emp_core.share.language.UtilLanguage;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelInputAt.PANEL_INPUT_TYPE;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelInputAt.PanelInputListenerIf;
import com.hellonms.platforms.emp_onion.theme.ThemeBuilder4Onion.COLOR_ONION;
import com.hellonms.platforms.emp_onion.theme.ThemeFactory;
import com.hellonms.platforms.emp_orange.client_swt.model_display.ModelDisplay4User;
import com.hellonms.platforms.emp_orange.client_swt.page.PanelBuilder4Orange.INPUT_ORANGE;
import com.hellonms.platforms.emp_orange.client_swt.page.PanelFactory;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.security.user.Model4User;

/**
 * <p>
 * WizardPage4User
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class WizardPage4User extends WizardPage {

	/**
	 * 입력판넬의 리스너 인터페이스를 구현한 리스너 클래스 입니다.
	 * 
	 * @since Version 1.4.0
	 * @author Hello NMS
	 */
	public class WizardPage4UserChildListener implements //
			PanelInputListenerIf {

		@Override
		public void completeChanged() {
			WizardPage4User.this.checkComplete();
		}

	}

	protected PANEL_INPUT_TYPE panelInputType;

	/**
	 * 사용자 입력판넬
	 */
	protected PanelInput4UserAt panelInput4User;

	/**
	 * 사용자그룹 모델 배열
	 */
	protected ModelDisplay4User displayModel4User;

	/**
	 * 생성자 입니다.
	 * 
	 * @param model4UserGroups
	 *            사용자그룹 모델 배열
	 */
	public WizardPage4User(PANEL_INPUT_TYPE panelInputType, ModelDisplay4User displayModel4User) {
		super(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.PROPERTY_TITLE, MESSAGE_CODE_ORANGE.USER));
		setTitle(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.PROPERTY_TITLE, MESSAGE_CODE_ORANGE.USER));
		setDescription(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.INSERT_PROPERTY, MESSAGE_CODE_ORANGE.USER));

		this.panelInputType = panelInputType;
		this.displayModel4User = displayModel4User;
	}

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		container.setBackground(ThemeFactory.getColor(COLOR_ONION.PANEL_CONTENTS_BG));
		container.setBackgroundMode(SWT.INHERIT_DEFAULT);
		setControl(container);
		GridLayout gl_container = new GridLayout(1, false);
		gl_container.marginWidth = 0;
		gl_container.marginHeight = 0;
		container.setLayout(gl_container);

		panelInput4User = (PanelInput4UserAt) PanelFactory.createPanelInput(INPUT_ORANGE.USER, container, SWT.NONE, panelInputType, new WizardPage4UserChildListener());
		panelInput4User.setModel4UserGroups(displayModel4User.getModel4UserGroups());
		panelInput4User.setModel4NeGroups(displayModel4User.getModel4NeGroups());
		panelInput4User.setModel(new Model4User());
		panelInput4User.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		checkComplete();
	}

	/**
	 * 완료상태를 확인합니다.
	 */
	protected void checkComplete() {
		if (panelInput4User != null && !panelInput4User.isComplete()) {
			setErrorMessage(panelInput4User.getErrorMessage());
			setPageComplete(false);
		} else {
			setErrorMessage(null);
			setPageComplete(true);
		}
	}

	/**
	 * 사용자를 반환합니다.
	 * 
	 * @return 사용자 모델
	 */
	public Model4User getUser() {
		Model4User user = panelInput4User.getModel();
		return user;
	}

}
