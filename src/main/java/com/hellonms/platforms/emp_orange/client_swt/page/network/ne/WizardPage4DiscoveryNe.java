package com.hellonms.platforms.emp_orange.client_swt.page.network.ne;

import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.hellonms.platforms.emp_core.share.language.UtilLanguage;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelInputAt.PanelInputListenerIf;
import com.hellonms.platforms.emp_onion.theme.ThemeBuilder4Onion.COLOR_ONION;
import com.hellonms.platforms.emp_onion.theme.ThemeFactory;
import com.hellonms.platforms.emp_orange.client_swt.page.PanelBuilder4Orange.INPUT_ORANGE;
import com.hellonms.platforms.emp_orange.client_swt.page.PanelFactory;
import com.hellonms.platforms.emp_orange.client_swt.page.network.ne_session.PanelInput4DiscoveryNeSessionAt;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.Model4NeSessionDiscoveryFilterIf;

public class WizardPage4DiscoveryNe extends WizardPage {

	/**
	 * 입력판넬의 리스너 인터페이스를 구현한 리스너 클래스 입니다.
	 * 
	 * @since Version 1.4.0
	 * @author Hello NMS
	 */
	public class WizardPage4DiscoveryNeChildListener implements //
			PanelInputListenerIf {

		@Override
		public void completeChanged() {
			WizardPage4DiscoveryNe.this.checkComplete();
		}

	}

	/**
	 * NE 검색 모델
	 */
	protected Model4NeSessionDiscoveryFilterIf[] model4NeSessionDiscoveryFilters;

	/**
	 * NE 검색 입력판넬
	 */
	protected PanelInput4DiscoveryNeAt panelInput4DiscoveryNe;

	protected Map<String, PanelInput4DiscoveryNeSessionAt> panelInput4DiscoveryNeSessionMap = new LinkedHashMap<String, PanelInput4DiscoveryNeSessionAt>();

	/**
	 * 생성자 입니다.
	 * 
	 * @param discoveryNe
	 *            NE 검색 모델
	 */
	public WizardPage4DiscoveryNe(Model4NeSessionDiscoveryFilterIf[] model4NeSessionDiscoveryFilters) {
		super(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.PROPERTY_TITLE, MESSAGE_CODE_ORANGE.NE));
		setTitle(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.PROPERTY_TITLE, MESSAGE_CODE_ORANGE.NE));
		setDescription(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.INSERT_PROPERTY, MESSAGE_CODE_ORANGE.NE));

		this.model4NeSessionDiscoveryFilters = model4NeSessionDiscoveryFilters;
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

		panelInput4DiscoveryNe = (PanelInput4DiscoveryNeAt) PanelFactory.createPanelInput(INPUT_ORANGE.DISCOVERY_NE, container, SWT.NONE, null, new WizardPage4DiscoveryNeChildListener());
		panelInput4DiscoveryNe.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		for (Model4NeSessionDiscoveryFilterIf model4DiscoveryNeSession : model4NeSessionDiscoveryFilters) {
			PanelInput4DiscoveryNeSessionAt panelInput4DiscoveryNeSession = (PanelInput4DiscoveryNeSessionAt) PanelFactory.createPanelInput(INPUT_ORANGE.DISCOVERY_NE_SESSION, container, SWT.NONE, null, new WizardPage4DiscoveryNeChildListener(), model4DiscoveryNeSession.getProtocol());
			if (panelInput4DiscoveryNeSession != null) {
				panelInput4DiscoveryNeSession.setModel(model4DiscoveryNeSession);
				panelInput4DiscoveryNeSession.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
				panelInput4DiscoveryNeSessionMap.put(model4DiscoveryNeSession.getProtocol().toString(), panelInput4DiscoveryNeSession);
			}
		}

		checkComplete();
	}

	/**
	 * 완료상태를 확인합니다.
	 */
	protected void checkComplete() {
		if (panelInput4DiscoveryNe != null && !panelInput4DiscoveryNe.isComplete()) {
			setErrorMessage(panelInput4DiscoveryNe.getErrorMessage());
			setPageComplete(false);
			return;
		}

		setErrorMessage(null);
		setPageComplete(true);
	}

	public Model4NeSessionDiscoveryFilterIf[] getModel() {
		Object model = panelInput4DiscoveryNe.getModel();
		if (model != null && model instanceof ModelClient4DiscoveryNe) {
			ModelClient4DiscoveryNe model4DiscoveryNe = (ModelClient4DiscoveryNe) model;
			Model4NeSessionDiscoveryFilterIf[] model4DiscoveryNeSessions = new Model4NeSessionDiscoveryFilterIf[panelInput4DiscoveryNeSessionMap.size()];
			int index = 0;
			for (PanelInput4DiscoveryNeSessionAt panelInput4DiscoveryNeSession : panelInput4DiscoveryNeSessionMap.values()) {
				Model4NeSessionDiscoveryFilterIf model4DiscoveryNeSession = panelInput4DiscoveryNeSession.getModel();
				model4DiscoveryNeSession.setHost(model4DiscoveryNe.getHost());
				model4DiscoveryNeSession.setCount(model4DiscoveryNe.getCounter());
				model4DiscoveryNeSessions[index++] = model4DiscoveryNeSession;
			}
			return model4DiscoveryNeSessions;
		}
		return new Model4NeSessionDiscoveryFilterIf[0];
	}

}
