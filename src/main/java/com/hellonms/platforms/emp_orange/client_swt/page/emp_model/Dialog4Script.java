package com.hellonms.platforms.emp_orange.client_swt.page.emp_model;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import com.hellonms.platforms.emp_core.share.language.UtilLanguage;
import com.hellonms.platforms.emp_onion.client_swt.widget.button.ButtonClick;
import com.hellonms.platforms.emp_onion.client_swt.widget.dialog.Dialog;
import com.hellonms.platforms.emp_onion.client_swt.widget.text.TextInput4Script;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;
import com.hellonms.platforms.emp_util.string.UtilString;

public class Dialog4Script extends Dialog {

	protected String script;

	protected String script_sample;

	protected TextInput4Script text_script;

	protected boolean complete;

	public Dialog4Script(Shell parent, String title, String script, String script_sample) {
		super(parent, SWT.APPLICATION_MODAL | SWT.DIALOG_TRIM | SWT.MAX | SWT.RESIZE);
		super.setSize(670, 480);
		super.setText(title);

		this.script = script;
		this.script_sample = script_sample;

		createGUI();
	}

	protected void createGUI() {
		setLayout(new GridLayout(1, false));

		text_script = new TextInput4Script(this, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
		text_script.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		text_script.setText(script);

		Composite panelButton = new Composite(this, SWT.NONE);
		panelButton.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		panelButton.setLayout(new GridLayout(3, false));

		if (!UtilString.isEmpty(script_sample)) {
			ButtonClick buttonSample = new ButtonClick(panelButton, SWT.NONE);
			buttonSample.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					text_script.setText(script_sample);
				}
			});
			GridData gd_buttonSample = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
			gd_buttonSample.widthHint = 60;
			buttonSample.setLayoutData(gd_buttonSample);
			buttonSample.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.SAMPLE));
		}

		ButtonClick buttonOk = new ButtonClick(panelButton, SWT.NONE);
		buttonOk.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				script = text_script.getText().trim();
				complete = true;
				Dialog4Script.this.dispose();
			}
		});
		GridData gd_buttonOk = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_buttonOk.widthHint = 60;
		buttonOk.setLayoutData(gd_buttonOk);
		buttonOk.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.OK));

		ButtonClick buttonCancel = new ButtonClick(panelButton, SWT.NONE);
		buttonCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				complete = false;
				Dialog4Script.this.dispose();
			}
		});
		GridData gd_buttonCancel = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_buttonCancel.widthHint = 60;
		buttonCancel.setLayoutData(gd_buttonCancel);
		buttonCancel.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.CANCEL));
	}

	public boolean isComplete() {
		return complete;
	}

	public String getScript() {
		return script;
	}

}
