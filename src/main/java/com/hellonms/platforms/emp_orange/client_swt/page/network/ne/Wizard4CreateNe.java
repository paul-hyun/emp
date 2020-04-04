package com.hellonms.platforms.emp_orange.client_swt.page.network.ne;

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
 * Wizard4CreateNe
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class Wizard4CreateNe extends Wizard {

	/**
	 * NE 생성 위자드의 리스너 인터페이스 입니다.
	 * 
	 * @since Version 1.4.0
	 * @author Hello NMS
	 */
	public interface Wizard4CreateNeListenerIf {

		/**
		 * NE를 생성합니다.
		 * 
		 * @param wizard
		 *            위자드
		 * @param ne
		 *            NE 모델
		 * @param newImage
		 *            새 이미지 파일 모델
		 */
		public void createNe(Wizard4CreateNe wizard, Model4Ne model4Ne);

	}

	private static DialogWizard instance;

	/**
	 * NE 생성 위자드를 오픈합니다.
	 * 
	 * @param shell
	 *            부모 쉘
	 * @param ne_group_id
	 *            NE그룹 아이디
	 * @param listener
	 *            리스너
	 */
	public static void open(Shell shell, Model4Ne model4Ne, String[] image_paths, Wizard4CreateNeListenerIf listener) {
		if (instance == null || instance.getShell() == null || instance.getShell().isDisposed()) {
			instance = new DialogWizard(shell, new Wizard4CreateNe(model4Ne, image_paths, listener));
			instance.open();
		}
	}

	/**
	 * NE 생성 위자드를 닫습니다.
	 */
	public static void close() {
		if (instance != null && instance.getShell() != null && !instance.getShell().isDisposed()) {
			instance.close();
			instance = null;
		}
	}

	protected WizardPage4Ne wizardPage4Ne;

	protected Model4Ne model4Ne;

	protected Wizard4CreateNeListenerIf listener;

	private String[] image_paths;

	protected Wizard4CreateNe(Model4Ne model4Ne, String[] image_paths, Wizard4CreateNeListenerIf listener) {
		setWindowTitle(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.CREATE_TITLE, MESSAGE_CODE_ORANGE.NE));

		this.model4Ne = model4Ne;
		this.image_paths = image_paths;
		this.listener = listener;
	}

	@Override
	public void addPages() {
		wizardPage4Ne = new WizardPage4Ne(PANEL_INPUT_TYPE.CREATE, model4Ne, image_paths);
		addPage(wizardPage4Ne);
	}

	@Override
	public boolean performFinish() {
		final Model4Ne model4Ne = wizardPage4Ne.getNe();

		if (DialogMessage.openConfirm(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.CREATE_TITLE, MESSAGE_CODE_ORANGE.NE), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.CREATE_CONFIRM, MESSAGE_CODE_ORANGE.NE, model4Ne.getNe_name()))) {
			listener.createNe(this, model4Ne);
		}
		return false;
	}

}
