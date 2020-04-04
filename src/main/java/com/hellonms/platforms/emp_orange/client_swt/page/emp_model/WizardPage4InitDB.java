package com.hellonms.platforms.emp_orange.client_swt.page.emp_model;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import com.hellonms.platforms.emp_core.share.language.UtilLanguage;
import com.hellonms.platforms.emp_onion.theme.ThemeBuilder4Onion.COLOR_ONION;
import com.hellonms.platforms.emp_onion.theme.ThemeFactory;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;

public class WizardPage4InitDB extends WizardPage {

	private Button check_ne;

	private Button check_ne_info;

	private Button check_fault;

	public WizardPage4InitDB() {
		super(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.INIT_DB));
		setTitle(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.INIT_DB));
		setDescription(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.INIT_DB));
	}

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		container.setBackground(ThemeFactory.getColor(COLOR_ONION.PANEL_CONTENTS_BG));
		container.setBackgroundMode(SWT.INHERIT_DEFAULT);
		setControl(container);
		GridLayout gl_container = new GridLayout(1, false);
		gl_container.marginWidth = 5;
		gl_container.marginHeight = 5;
		container.setLayout(gl_container);

		check_ne = new Button(container, SWT.CHECK);
		check_ne.setText("NE");
		check_ne.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		check_ne.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				checkComplete();
			}
		});

		check_ne_info = new Button(container, SWT.CHECK);
		check_ne_info.setText("NE_INFO");
		check_ne_info.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		check_ne_info.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				checkComplete();
			}
		});

		check_fault = new Button(container, SWT.CHECK);
		check_fault.setText("FAULT");
		check_fault.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		check_fault.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				checkComplete();
			}
		});

		checkComplete();
	}

	private void checkComplete() {
		if (!isNe() && !isNe_info() && !isFault()) {
			setErrorMessage("초기화 할 DB를 선택해 주세요.");
			setPageComplete(false);
		} else {
			setErrorMessage(null);
			setPageComplete(true);
		}
	}

	public boolean isNe() {
		return check_ne.getSelection();
	}

	public boolean isNe_info() {
		return check_ne_info.getSelection();
	}

	public boolean isFault() {
		return check_fault.getSelection();
	}

}
