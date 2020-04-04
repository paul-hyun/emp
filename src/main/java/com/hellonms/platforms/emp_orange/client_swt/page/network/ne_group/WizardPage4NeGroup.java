package com.hellonms.platforms.emp_orange.client_swt.page.network.ne_group;

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
import com.hellonms.platforms.emp_orange.share.model.network.ne_group.Model4NeGroup;

/**
 * <p>
 * WizardPage4NeGroup
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class WizardPage4NeGroup extends WizardPage {

	/**
	 * 입력판넬의 리스너 인터페이스를 구현한 리스너 클래스 입니다.
	 * 
	 * @since Version 1.4.0
	 * @author Hello NMS
	 */
	public class WizardPage4NeGroupChildListener implements //
			PanelInputListenerIf {

		@Override
		public void completeChanged() {
			WizardPage4NeGroup.this.checkComplete();
		}

	}

	protected PANEL_INPUT_TYPE panelInputType;

	/**
	 * NE그룹 모델
	 */
	protected Model4NeGroup model4NeGroup;

	/**
	 * NE그룹 입력판넬
	 */
	protected PanelInput4NeGroupAt inputPanel4NeGroup;

	/**
	 * 완료상태
	 */
	protected boolean checkComplete;

	private String[] image_paths;

	/**
	 * 생성자 입니다.
	 * 
	 * @param neGroup
	 *            NE그룹 모델
	 */
	public WizardPage4NeGroup(PANEL_INPUT_TYPE panelInputType, Model4NeGroup model4NeGroup, String[] image_paths, boolean checkComplete) {
		super(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.PROPERTY_TITLE, MESSAGE_CODE_ORANGE.NE_GROUP));
		setTitle(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.PROPERTY_TITLE, MESSAGE_CODE_ORANGE.NE_GROUP));
		setDescription(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.INSERT_PROPERTY, MESSAGE_CODE_ORANGE.NE_GROUP));

		this.panelInputType = panelInputType;
		this.model4NeGroup = model4NeGroup;
		this.image_paths = image_paths;
		this.checkComplete = checkComplete;
	}

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		container.setBackground(ThemeFactory.getColor(COLOR_ONION.PANEL_CONTENTS_BG));
		container.setBackgroundMode(SWT.INHERIT_DEFAULT);
		setControl(container);
		GridLayout gl_container = new GridLayout(1, false);
		gl_container.marginHeight = 0;
		gl_container.marginWidth = 0;
		container.setLayout(gl_container);

		inputPanel4NeGroup = (PanelInput4NeGroupAt) PanelFactory.createPanelInput(INPUT_ORANGE.NE_GROUP, container, SWT.NONE, panelInputType, new WizardPage4NeGroupChildListener(), (Object) image_paths);
		inputPanel4NeGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		inputPanel4NeGroup.setModel(model4NeGroup);

		checkComplete();
	}

	/**
	 * 완료상태를 확인합니다.
	 */
	protected void checkComplete() {
		if (checkComplete) {
			if (inputPanel4NeGroup != null && !inputPanel4NeGroup.isComplete()) {
				setErrorMessage(inputPanel4NeGroup.getErrorMessage());
				setPageComplete(false);
				return;
			}

			setErrorMessage(null);
			setPageComplete(true);
		} else {
			setErrorMessage(null);
			setPageComplete(false);
		}
	}

	/**
	 * NE그룹을 반환합니다.
	 * 
	 * @return NE그룹 모델
	 */
	public Model4NeGroup getNeGroup() {
		return inputPanel4NeGroup.getModel();
	}

}
