package com.hellonms.platforms.emp_orange.client_swt.page.network.ne_info;

import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Shell;

import com.hellonms.platforms.emp_core.share.language.UtilLanguage;
import com.hellonms.platforms.emp_onion.client_swt.widget.dialog.DialogMessage;
import com.hellonms.platforms.emp_onion.client_swt.widget.dialog.DialogWizard;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelInputAt.PANEL_INPUT_TYPE;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.network.ne_info.Model4NeInfoIf;

/**
 * <p>
 * Wizard4CreateNeInfo
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class Wizard4CreateNeInfo extends Wizard {

	/**
	 * 사용자 생성 위자드의 리스너 인터페이스 입니다.
	 * 
	 * @since Version 1.4.0
	 * @author Hello NMS
	 */
	public interface Wizard4CreateNeInfoListenerIf {

		/**
		 * 사용자를 생성합니다.
		 * 
		 * @param wizard
		 *            위자드
		 * @param user
		 *            사용자 모델
		 */
		public void createNeInfo(Wizard4CreateNeInfo wizard, Model4NeInfoIf model4NeInfo);

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
	public static void open(Shell shell, Model4NeInfoIf model4NeInfo, Wizard4CreateNeInfoListenerIf listener) {
		if (instance == null || instance.getShell() == null || instance.getShell().isDisposed()) {
			instance = new DialogWizard(shell, new Wizard4CreateNeInfo(model4NeInfo, listener));
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

	private WizardPage4NeInfo wizardPage4NeInfo;

	private Model4NeInfoIf model4NeInfo;

	private Wizard4CreateNeInfoListenerIf listener;

	private Wizard4CreateNeInfo(Model4NeInfoIf model4NeInfo, Wizard4CreateNeInfoListenerIf listener) {
		setWindowTitle(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.CREATE_TITLE, model4NeInfo.getNe_info_def().getName()));

		this.model4NeInfo = model4NeInfo;
		this.listener = listener;
	}

	@Override
	public void addPages() {
		wizardPage4NeInfo = new WizardPage4NeInfo(PANEL_INPUT_TYPE.CREATE, model4NeInfo);
		addPage(wizardPage4NeInfo);
	}

	@Override
	public boolean performFinish() {
		Model4NeInfoIf model4NeInfo = wizardPage4NeInfo.getModel();
		if (DialogMessage.openConfirm(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.CREATE_TITLE, model4NeInfo.getNe_info_def().getName()), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.CREATE_CONFIRM, MESSAGE_CODE_ORANGE.NE_INFO, model4NeInfo.getNe_info_def().getName()))) {
			listener.createNeInfo(this, model4NeInfo);
		}
		return false;
	}

}
