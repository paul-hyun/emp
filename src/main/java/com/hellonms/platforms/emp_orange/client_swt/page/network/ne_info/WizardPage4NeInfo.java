package com.hellonms.platforms.emp_orange.client_swt.page.network.ne_info;

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
import com.hellonms.platforms.emp_orange.client_swt.page.PanelBuilder4Orange.INPUT_ORANGE;
import com.hellonms.platforms.emp_orange.client_swt.page.PanelFactory;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.network.ne_info.Model4NeInfoIf;

/**
 * <p>
 * WizardPage4NeInfo
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class WizardPage4NeInfo extends WizardPage {

	/**
	 * 입력판넬의 리스너 인터페이스를 구현한 리스너 클래스 입니다.
	 * 
	 * @since Version 1.4.0
	 * @author Hello NMS
	 */
	public class ChildListener implements //
			PanelInputListenerIf {

		@Override
		public void completeChanged() {
			WizardPage4NeInfo.this.checkComplete();
		}

	}

	/**
	 * 사용자 입력판넬
	 */
	protected PanelInput4NeInfo panelInput4NeInfo;

	protected Model4NeInfoIf model4NeInfo;

	protected PANEL_INPUT_TYPE panelInputType;

	/**
	 * 생성자 입니다.
	 * 
	 * @param model4UserGroups
	 *            사용자그룹 모델 배열
	 */
	public WizardPage4NeInfo(PANEL_INPUT_TYPE panelInputType, Model4NeInfoIf model4NeInfo) {
		super(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.PROPERTY_TITLE, model4NeInfo.getNe_info_def().getName()));
		setTitle(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.PROPERTY_TITLE, model4NeInfo.getNe_info_def().getName()));
		setDescription(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.INSERT_PROPERTY, model4NeInfo.getNe_info_def().getName()));

		this.panelInputType = panelInputType;
		this.model4NeInfo = model4NeInfo;
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

		panelInput4NeInfo = (PanelInput4NeInfo) PanelFactory.createPanelInput(INPUT_ORANGE.NE_INFO, container, SWT.NONE, panelInputType, new ChildListener(), model4NeInfo.getNe_info_def(), new Object[0]);
		panelInput4NeInfo.setModel(model4NeInfo);
		panelInput4NeInfo.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		checkComplete();
	}

	/**
	 * 완료상태를 확인합니다.
	 */
	protected void checkComplete() {
		if (panelInput4NeInfo != null && !panelInput4NeInfo.isComplete()) {
			setErrorMessage(panelInput4NeInfo.getErrorMessage());
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
	public Model4NeInfoIf getModel() {
		return panelInput4NeInfo.getModel();
	}

}
