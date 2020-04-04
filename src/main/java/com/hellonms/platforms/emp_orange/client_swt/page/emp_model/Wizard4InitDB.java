package com.hellonms.platforms.emp_orange.client_swt.page.emp_model;

import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Shell;

import com.hellonms.platforms.emp_core.share.language.UtilLanguage;
import com.hellonms.platforms.emp_onion.client_swt.widget.dialog.DialogMessage;
import com.hellonms.platforms.emp_onion.client_swt.widget.dialog.DialogWizard;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;

public class Wizard4InitDB extends Wizard {

	public interface Wizard4InitDBListenerIf {

		public void truncate(Wizard4InitDB wizard, boolean isNe, boolean isNe_info, boolean isFault, boolean isOperation_log);

	}

	private static DialogWizard instance;

	public static void open(Shell shell, Wizard4InitDBListenerIf listener) {
		if (instance == null || instance.getShell() == null || instance.getShell().isDisposed()) {
			instance = new DialogWizard(shell, new Wizard4InitDB(listener));
			instance.open();
		}
	}

	public static void close() {
		if (instance != null && instance.getShell() != null && !instance.getShell().isDisposed()) {
			instance.close();
			instance = null;
		}
	}

	private WizardPage4InitDB wizardPage4InitDB;

	private Wizard4InitDBListenerIf listener;

	private Wizard4InitDB(Wizard4InitDBListenerIf listener) {
		setWindowTitle(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.INIT_DB));

		this.listener = listener;
	}

	@Override
	public void addPages() {
		wizardPage4InitDB = new WizardPage4InitDB();
		addPage(wizardPage4InitDB);
	}

	@Override
	public boolean performFinish() {
		if (DialogMessage.openConfirm(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.INIT_DB), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.INIT_DB_CONFIRM))) {
			listener.truncate(this, wizardPage4InitDB.isNe(), wizardPage4InitDB.isNe_info(), wizardPage4InitDB.isFault(), true);
		}
		return false;
	}

}
