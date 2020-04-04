package com.hellonms.platforms.emp_orange.client_swt.page.network.ne_threshold;

import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Shell;

import com.hellonms.platforms.emp_core.share.language.UtilLanguage;
import com.hellonms.platforms.emp_onion.client_swt.widget.dialog.DialogMessage;
import com.hellonms.platforms.emp_onion.client_swt.widget.dialog.DialogWizard;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelInputAt.PANEL_INPUT_TYPE;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.network.ne.Model4Ne;

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
public class Wizard4CopyNeThreshold extends Wizard {

	public interface Wizard4CopyNeThresholdListenerIf {

		public void copyListNeThreshold(Wizard4CopyNeThreshold wizard, final int[] ne_id_targets);

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
	public static void open(Shell shell, Model4Ne model4Ne, Wizard4CopyNeThresholdListenerIf listener) {
		if (instance == null || instance.getShell() == null || instance.getShell().isDisposed()) {
			instance = new DialogWizard(shell, new Wizard4CopyNeThreshold(model4Ne, listener));
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

	private WizardPage4CopyNeThreshold wizardPage4CopyNeThreshold;

	private Model4Ne model4Ne;

	private Wizard4CopyNeThresholdListenerIf listener;

	private Wizard4CopyNeThreshold(Model4Ne model4Ne, Wizard4CopyNeThresholdListenerIf listener) {
		setWindowTitle(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.COPY_TITLE, MESSAGE_CODE_ORANGE.NE_THRESHOLD));

		this.model4Ne = model4Ne;
		this.listener = listener;
	}

	@Override
	public void addPages() {
		wizardPage4CopyNeThreshold = new WizardPage4CopyNeThreshold(PANEL_INPUT_TYPE.UPDATE, model4Ne);
		addPage(wizardPage4CopyNeThreshold);
	}

	@Override
	public boolean performFinish() {
		int[] ne_id_targets = wizardPage4CopyNeThreshold.getModel();
		if (DialogMessage.openConfirm(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.COPY_TITLE, MESSAGE_CODE_ORANGE.NE_THRESHOLD), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.COPY_CONFIRM, "", MESSAGE_CODE_ORANGE.NE_THRESHOLD))) {
			listener.copyListNeThreshold(this, ne_id_targets);
		}
		return false;
	}

}
