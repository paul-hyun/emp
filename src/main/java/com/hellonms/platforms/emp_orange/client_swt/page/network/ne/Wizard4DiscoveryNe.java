package com.hellonms.platforms.emp_orange.client_swt.page.network.ne;

import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Shell;

import com.hellonms.platforms.emp_core.share.language.UtilLanguage;
import com.hellonms.platforms.emp_onion.client_swt.util.location.UtilLocation;
import com.hellonms.platforms.emp_onion.client_swt.widget.dialog.DialogWizard;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.network.ne.Model4Ne;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.Model4NeSessionDiscoveryFilterIf;

public class Wizard4DiscoveryNe extends Wizard {

	public interface Wizard4DiscoveryNeListenerIf {

		public void discoveryListNe(Wizard4DiscoveryNe wizard, Model4NeSessionDiscoveryFilterIf[] model4NeSessionDiscoveryFilters);

		public void createListNe(Wizard4DiscoveryNe wizard, Model4Ne[] model4Nes);

	}

	private static DialogWizard instance;

	public static void open(Shell shell, int neGroupId, Model4NeSessionDiscoveryFilterIf[] model4NeSessionDiscoveryFilters, Wizard4DiscoveryNeListenerIf listener) {
		if (instance == null || instance.getShell() == null || instance.getShell().isDisposed()) {
			instance = new DialogWizard(shell, new Wizard4DiscoveryNe(neGroupId, model4NeSessionDiscoveryFilters, listener));
			instance.open();
		}
	}

	public static void close() {
		if (instance != null && instance.getShell() != null && !instance.getShell().isDisposed()) {
			instance.close();
			instance = null;
		}
	}

	private WizardPage4DiscoveryNe wizardPage4DiscoveryNe;

	private int neGroupId;

	private Model4NeSessionDiscoveryFilterIf[] model4NeSessionDiscoveryFilters;

	private Wizard4DiscoveryNeListenerIf listener;

	private Wizard4DiscoveryNe(int neGroupId, Model4NeSessionDiscoveryFilterIf[] model4NeSessionDiscoveryFilters, Wizard4DiscoveryNeListenerIf listener) {
		setWindowTitle(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.DISCOVERY_NE));

		this.neGroupId = neGroupId;
		this.model4NeSessionDiscoveryFilters = model4NeSessionDiscoveryFilters;
		this.listener = listener;
	}

	@Override
	public void addPages() {
		wizardPage4DiscoveryNe = new WizardPage4DiscoveryNe(model4NeSessionDiscoveryFilters);
		addPage(wizardPage4DiscoveryNe);
	}

	@Override
	public boolean performFinish() {
		final Model4NeSessionDiscoveryFilterIf[] model = wizardPage4DiscoveryNe.getModel();
		listener.discoveryListNe(this, model);
		return false;
	}

	public void display(Model4Ne[] model4Nes) {
		Dialog4DiscoveryNeResult dialog = new Dialog4DiscoveryNeResult(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NE_DISCOVERY_RESULT), model4Nes);
		UtilLocation.toCenter(getShell(), dialog);
		dialog.open();

		if (dialog.isComplete()) {
			Model4Ne[] model4CheckNes = dialog.getModel4CheckNes();
			for (Model4Ne model4Ne : model4CheckNes) {
				model4Ne.setNe_group_id(neGroupId);
			}
			listener.createListNe(this, model4CheckNes);
		}
	}

	public int getNeGroupId() {
		return neGroupId;
	}

}
