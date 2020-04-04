package com.hellonms.platforms.emp_orange.client_swt.page.security.user;

import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Shell;

import com.hellonms.platforms.emp_core.share.language.UtilLanguage;
import com.hellonms.platforms.emp_onion.client_swt.widget.dialog.DialogMessage;
import com.hellonms.platforms.emp_onion.client_swt.widget.dialog.DialogWizard;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelInputAt.PANEL_INPUT_TYPE;
import com.hellonms.platforms.emp_orange.client_swt.model_display.ModelDisplay4User;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.security.user.Model4User;

/**
 * <p>
 * Wizard4CreateUser
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class Wizard4CreateUser extends Wizard {

	/**
	 * 사용자 생성 위자드의 리스너 인터페이스 입니다.
	 * 
	 * @since Version 1.4.0
	 * @author Hello NMS
	 */
	public interface Wizard4CreateUserListenerIf {

		/**
		 * 사용자를 생성합니다.
		 * 
		 * @param wizard
		 *            위자드
		 * @param user
		 *            사용자 모델
		 */
		public void createUser(Wizard4CreateUser wizard, Model4User user);

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
	public static void open(Shell shell, ModelDisplay4User displayModel4User, Wizard4CreateUserListenerIf listener) {
		if (instance == null || instance.getShell() == null || instance.getShell().isDisposed()) {
			instance = new DialogWizard(shell, new Wizard4CreateUser(displayModel4User, listener));
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

	private WizardPage4User wizardPage4User;

	private ModelDisplay4User displayModel4User;

	private Wizard4CreateUserListenerIf listener;

	private Wizard4CreateUser(ModelDisplay4User displayModel4User, Wizard4CreateUserListenerIf listener) {
		setWindowTitle(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.CREATE_TITLE, MESSAGE_CODE_ORANGE.USER));

		this.displayModel4User = displayModel4User;
		this.listener = listener;
	}

	@Override
	public void addPages() {
		wizardPage4User = new WizardPage4User(PANEL_INPUT_TYPE.CREATE, displayModel4User);
		addPage(wizardPage4User);
	}

	@Override
	public boolean performFinish() {
		Model4User user = wizardPage4User.getUser();
		if (DialogMessage.openConfirm(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.CREATE_TITLE, MESSAGE_CODE_ORANGE.USER), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.CREATE_CONFIRM, MESSAGE_CODE_ORANGE.USER, user.getUser_account()))) {
			listener.createUser(this, user);
		}
		return false;
	}

}
