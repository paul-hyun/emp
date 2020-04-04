package com.hellonms.platforms.emp_onion.client_swt.widget.dialog;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.hellonms.platforms.emp_onion.theme.ThemeBuilder4Onion.COLOR_ONION;
import com.hellonms.platforms.emp_onion.theme.ThemeBuilder4Onion.IMAGE_ONION;
import com.hellonms.platforms.emp_onion.theme.ThemeFactory;

/**
 * <p>
 * DialogWizard
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class DialogWizard extends WizardDialog {

	/**
	 * 생성자 입니다.
	 * 
	 * @param parentShell
	 *            부모 쉘
	 * @param newWizard
	 *            위자드
	 */
	public DialogWizard(Shell parentShell, IWizard newWizard) {
		super(parentShell, newWizard);
	}

	@Override
	public void create() {
		super.create();

		setTitleImage(ThemeFactory.getImage(IMAGE_ONION.WIZARD_ICON));
		getTitleImageLabel().getParent().setBackground(ThemeFactory.getColor(COLOR_ONION.WIZARD_BG));

		for (Control titleControl : getTitleImageLabel().getParent().getChildren()) {
			if (titleControl instanceof Label) {
				((Label) titleControl).setForeground(ThemeFactory.getColor(COLOR_ONION.PANEL_FG));
				((Label) titleControl).setBackground(ThemeFactory.getColor(COLOR_ONION.WIZARD_BG));
			}
			if (titleControl instanceof Text) {
				((Text) titleControl).setForeground(ThemeFactory.getColor(COLOR_ONION.PANEL_FG));
				((Text) titleControl).setBackground(ThemeFactory.getColor(COLOR_ONION.WIZARD_BG));
			}
		}
		for (Control control : getButtonBar().getParent().getChildren()) {
			control.setBackground(ThemeFactory.getColor(COLOR_ONION.PANEL_BG));
			if (control instanceof Composite) {
				for (Control childControl : ((Composite) control).getChildren()) {
					childControl.setBackground(ThemeFactory.getColor(COLOR_ONION.PANEL_BG));
				}
			}
		}

		Composite dialogAreaComposite = (Composite) getDialogArea();
		for (Control childControl : dialogAreaComposite.getChildren()) {
			if (childControl instanceof Label) {
				childControl.setVisible(false);
			}
		}

		if (getButton(IDialogConstants.CANCEL_ID) != null) {
			getButton(IDialogConstants.CANCEL_ID).setBackground(ThemeFactory.getColor(COLOR_ONION.PANEL_BG));
		}
		if (getButton(IDialogConstants.NEXT_ID) != null) {
			getButton(IDialogConstants.NEXT_ID).setBackground(ThemeFactory.getColor(COLOR_ONION.PANEL_BG));
		}
		if (getButton(IDialogConstants.BACK_ID) != null) {
			getButton(IDialogConstants.BACK_ID).setBackground(ThemeFactory.getColor(COLOR_ONION.PANEL_BG));
		}
		if (getButton(IDialogConstants.FINISH_ID) != null) {
			getButton(IDialogConstants.FINISH_ID).setBackground(ThemeFactory.getColor(COLOR_ONION.PANEL_BG));
		}

		Composite titleDialogArea = (Composite) dialogArea;
		for (Control childControl : titleDialogArea.getChildren()) {
			if (childControl instanceof Label) {
				childControl.setVisible(false);
			}
		}

		getTitleImageLabel().getParent().layout();
	}

	@Override
	public IWizard getWizard() {
		return super.getWizard();
	}

	@Override
	protected void buttonPressed(int buttonId) {
		switch (buttonId) {
		case IDialogConstants.HELP_ID: {
			helpPressed();
			break;
		}
		case IDialogConstants.BACK_ID: {
			backPressed();
			setTitleImage(ThemeFactory.getImage(IMAGE_ONION.WIZARD_ICON));
			break;
		}
		case IDialogConstants.NEXT_ID: {
			nextPressed();
			setTitleImage(ThemeFactory.getImage(IMAGE_ONION.WIZARD_ICON));
			break;
		}
		case IDialogConstants.FINISH_ID: {
			finishPressed();
			break;
		}
		}
	}

}
