package com.hellonms.platforms.emp_orange.client_swt.page.network.ne_threshold;

import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Shell;

import com.hellonms.platforms.emp_core.share.language.UtilLanguage;
import com.hellonms.platforms.emp_onion.client_swt.widget.dialog.DialogMessage;
import com.hellonms.platforms.emp_onion.client_swt.widget.dialog.DialogWizard;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelInputAt.PANEL_INPUT_TYPE;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO_FIELD;
import com.hellonms.platforms.emp_orange.share.model.network.ne_info.Model4NeThresholdIf;

/**
 * <p>
 * Wizard4UpdateNeThreshold
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class Wizard4UpdateNeThreshold extends Wizard {

	/**
	 * 사용자 생성 위자드의 리스너 인터페이스 입니다.
	 * 
	 * @since Version 1.4.0
	 * @author Hello NMS
	 */
	public interface Wizard4UpdateNeThresholdListenerIf {

		/**
		 * 사용자를 생성합니다.
		 * 
		 * @param wizard
		 *            위자드
		 * @param user
		 *            사용자 모델
		 */
		public void updateNeThreshold(Wizard4UpdateNeThreshold wizard, Model4NeThresholdIf ne_threshold, EMP_MODEL_NE_INFO_FIELD ne_info_field_def);

	}

	private static DialogWizard instance;

	/**
	 * 사용자 생성 위자드를 오픈합니다.
	 * 
	 * @param shell
	 *            부모 쉘
	 * @param userGroups
	 *            사용자 그룹 모델 배열
	 * @param listener
	 *            리스너
	 */
	public static void open(Shell shell, Model4NeThresholdIf ne_threshold, EMP_MODEL_NE_INFO_FIELD ne_info_field_def, Wizard4UpdateNeThresholdListenerIf listener) {
		if (instance == null || instance.getShell() == null || instance.getShell().isDisposed()) {
			instance = new DialogWizard(shell, new Wizard4UpdateNeThreshold(ne_threshold, ne_info_field_def, listener));
			instance.open();
		}
	}

	/**
	 * 사용자 생성 위자드를 닫습니다.
	 */
	public static void close() {
		if (instance != null && instance.getShell() != null && !instance.getShell().isDisposed()) {
			instance.close();
			instance = null;
		}
	}

	private WizardPage4NeThreshold wizardPage4NeThreshold;

	private Model4NeThresholdIf ne_threshold;

	private EMP_MODEL_NE_INFO_FIELD ne_info_field_def;

	private Wizard4UpdateNeThresholdListenerIf listener;

	private Wizard4UpdateNeThreshold(Model4NeThresholdIf ne_threshold, EMP_MODEL_NE_INFO_FIELD ne_info_field_def, Wizard4UpdateNeThresholdListenerIf listener) {
		setWindowTitle(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.UPDATE_TITLE, ne_info_field_def.getName()));

		this.ne_threshold = ne_threshold;
		this.ne_info_field_def = ne_info_field_def;
		this.listener = listener;
	}

	@Override
	public void addPages() {
		wizardPage4NeThreshold = new WizardPage4NeThreshold(PANEL_INPUT_TYPE.UPDATE, ne_threshold, ne_info_field_def);
		addPage(wizardPage4NeThreshold);
	}

	@Override
	public boolean performFinish() {
		Model4NeThresholdIf ne_threshold = wizardPage4NeThreshold.getModel();
		if (DialogMessage.openConfirm(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.UPDATE_TITLE, ne_info_field_def.getName()), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.UPDATE_CONFIRM, MESSAGE_CODE_ORANGE.NE_INFO, ne_info_field_def.getName()))) {
			listener.updateNeThreshold(this, ne_threshold, ne_info_field_def);
		}
		return false;
	}

}
