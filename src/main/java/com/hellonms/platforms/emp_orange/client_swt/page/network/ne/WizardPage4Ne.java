package com.hellonms.platforms.emp_orange.client_swt.page.network.ne;

import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.hellonms.platforms.emp_core.share.language.UtilLanguage;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelInputAt.PANEL_INPUT_TYPE;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelInputAt.PanelInputListenerIf;
import com.hellonms.platforms.emp_onion.theme.ThemeBuilder4Onion.COLOR_ONION;
import com.hellonms.platforms.emp_onion.theme.ThemeFactory;
import com.hellonms.platforms.emp_orange.client_swt.ApplicationProperty;
import com.hellonms.platforms.emp_orange.client_swt.page.PanelBuilder4Orange.INPUT_ORANGE;
import com.hellonms.platforms.emp_orange.client_swt.page.PanelFactory;
import com.hellonms.platforms.emp_orange.client_swt.page.network.ne_session.PanelInput4NeSessionAt;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.network.ne.Model4Ne;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.Model4NeSessionIf;
import com.hellonms.platforms.emp_orange.share.model.security.operation_log.OPERATION_CODE_ORANGE;

/**
 * <p>
 * WizardPage4Ne
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class WizardPage4Ne extends WizardPage {

	/**
	 * 입력판넬의 리스너 인터페이스를 구현한 리스너 클래스 입니다.
	 * 
	 * @since Version 1.4.0
	 * @author Hello NMS
	 */
	public class WizardPage4NeChildListener implements PanelInputListenerIf {

		@Override
		public void completeChanged() {
			WizardPage4Ne.this.checkComplete();
		}

	}

	protected PANEL_INPUT_TYPE panelInputType;

	/**
	 * NE 모델
	 */
	protected Model4Ne model4Ne;

	/**
	 * 스크롤 여부
	 */
	protected static boolean scrolled = false;

	/**
	 * 스크롤 여부를 설정합니다.
	 * 
	 * @param scrolled
	 *            스크롤 여부
	 */
	public static void setScrolled(boolean scrolled) {
		WizardPage4Ne.scrolled = scrolled;
	}

	/**
	 * 스크롤 컴포지트
	 */
	protected ScrolledComposite scrolledComposite;

	/**
	 * NE 입력판넬
	 */
	protected PanelInput4NeAt panelInput4Ne;

	/**
	 * NE 세션 입력판넬
	 */
	protected Map<String, PanelInput4NeSessionAt> modelPanel4NeSessionMap = new LinkedHashMap<String, PanelInput4NeSessionAt>();

	/**
	 * 완료상태
	 */
	protected boolean checkComplete;

	protected String[] image_paths;

	/**
	 * 생성자 입니다.
	 * 
	 * @param ne
	 *            NE 모델
	 */
	public WizardPage4Ne(PANEL_INPUT_TYPE panelInputType, Model4Ne model4Ne, String[] image_paths) {
		super(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.PROPERTY_TITLE, MESSAGE_CODE_ORANGE.NE));
		setTitle(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.PROPERTY_TITLE, MESSAGE_CODE_ORANGE.NE));
		setDescription(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.INSERT_PROPERTY, MESSAGE_CODE_ORANGE.NE));

		this.panelInputType = panelInputType;
		this.model4Ne = model4Ne;
		this.image_paths = image_paths;
		if (PANEL_INPUT_TYPE.CREATE == panelInputType) {
			checkComplete = ApplicationProperty.getOperation_authority(OPERATION_CODE_ORANGE.NETWORK_NE_CREATE);
		} else if (PANEL_INPUT_TYPE.UPDATE == panelInputType) {
			checkComplete = model4Ne.getAccess().isUpdate() && ApplicationProperty.getOperation_authority(OPERATION_CODE_ORANGE.NETWORK_NE_UPDATE);
		}
	}

	@Override
	public void createControl(Composite parent) {
		Composite container = null;
		if (WizardPage4Ne.scrolled) {
			scrolledComposite = new ScrolledComposite(parent, SWT.H_SCROLL | SWT.V_SCROLL);
			scrolledComposite.getHorizontalBar().setIncrement(8);
			scrolledComposite.getVerticalBar().setIncrement(8);
			scrolledComposite.setExpandHorizontal(true);
			scrolledComposite.setExpandVertical(true);
			scrolledComposite.setBackground(ThemeFactory.getColor(COLOR_ONION.PANEL_CONTENTS_BG));
			scrolledComposite.setBackgroundMode(SWT.INHERIT_DEFAULT);

			container = new Composite(scrolledComposite, SWT.NULL);
			scrolledComposite.setContent(container);
		} else {
			container = new Composite(parent, SWT.NULL);
		}

		container.setBackground(ThemeFactory.getColor(COLOR_ONION.PANEL_CONTENTS_BG));
		container.setBackgroundMode(SWT.INHERIT_DEFAULT);
		setControl(container);
		GridLayout gl_container = new GridLayout(1, false);
		gl_container.marginHeight = 0;
		gl_container.marginWidth = 0;
		container.setLayout(gl_container);

		panelInput4Ne = (PanelInput4NeAt) PanelFactory.createPanelInput(INPUT_ORANGE.NE, container, SWT.NONE, panelInputType, new WizardPage4NeChildListener(), (Object) image_paths);
		panelInput4Ne.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		panelInput4Ne.setModel(model4Ne);

		for (Model4NeSessionIf model4NeSession : model4Ne.getNeSessions()) {
			PanelInput4NeSessionAt panelInput4NeSession = (PanelInput4NeSessionAt) PanelFactory.createPanelInput(INPUT_ORANGE.NE_SESSION, container, SWT.NONE, panelInputType, new WizardPage4NeChildListener(), model4NeSession.getProtocol());
			if (panelInput4NeSession != null) {
				panelInput4NeSession.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
				panelInput4NeSession.setModel(model4NeSession);
				modelPanel4NeSessionMap.put(model4NeSession.getProtocol().toString(), panelInput4NeSession);
			}
		}

		checkComplete();

		if (WizardPage4Ne.scrolled) {
			scrolledComposite.setMinSize(container.computeSize(0, SWT.DEFAULT));
		}
	}

	/**
	 * 완료상태를 확인합니다.
	 */
	protected void checkComplete() {
		if (checkComplete) {
			if (panelInput4Ne != null && !panelInput4Ne.isComplete()) {
				setErrorMessage(panelInput4Ne.getErrorMessage());
				setPageComplete(false);
				return;
			}

			for (PanelInput4NeSessionAt modelPanel4NeSession : modelPanel4NeSessionMap.values()) {
				if (modelPanel4NeSession != null && !modelPanel4NeSession.isComplete()) {
					setErrorMessage(modelPanel4NeSession.getErrorMessage());
					setPageComplete(false);
					return;
				}
			}

			setErrorMessage(null);
			setPageComplete(true);
		} else {
			setErrorMessage(null);
			setPageComplete(false);
		}
	}

	/**
	 * NE를 반환합니다.
	 * 
	 * @return NE 모델
	 */
	public Model4Ne getNe() {
		Model4Ne model4Ne = panelInput4Ne.getModel();
		String host = panelInput4Ne.getHost();
		for (PanelInput4NeSessionAt modelPanel4NeSession : modelPanel4NeSessionMap.values()) {
			Model4NeSessionIf model4NeSession = modelPanel4NeSession.getModel();
			model4NeSession.setHost(host);
			model4Ne.addNeSession(model4NeSession);
		}

		return model4Ne;
	}

}
