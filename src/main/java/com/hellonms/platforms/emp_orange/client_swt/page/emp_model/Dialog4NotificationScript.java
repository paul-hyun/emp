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
import com.hellonms.platforms.emp_onion.client_swt.widget.dialog.DialogMessage;
import com.hellonms.platforms.emp_onion.client_swt.widget.label.LabelText;
import com.hellonms.platforms.emp_onion.client_swt.widget.text.TextInput;
import com.hellonms.platforms.emp_onion.client_swt.widget.text.TextInput4Script;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;
import com.hellonms.platforms.emp_util.string.UtilString;

public class Dialog4NotificationScript extends Dialog {

	protected String oid;

	protected String script;

	protected boolean oid_enabled;

	protected String script_sample;

	protected TextInput text_oid;

	protected TextInput4Script text_script;

	protected boolean complete;

	public Dialog4NotificationScript(Shell parent, String title, String oid, String script, boolean oid_enabled, String script_sample) {
		super(parent, SWT.APPLICATION_MODAL | SWT.DIALOG_TRIM | SWT.MAX | SWT.RESIZE);
		super.setSize(670, 480);
		super.setText(title);

		this.oid = oid;
		this.script = script;
		this.oid_enabled = oid_enabled;
		this.script_sample = script_sample;

		createGUI();
	}

	protected void createGUI() {
		setLayout(new GridLayout(2, false));

		LabelText label_notification_oid = new LabelText(this, SWT.NONE, false);
		label_notification_oid.setText("Notification_oid");
		label_notification_oid.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));

		text_oid = new TextInput(this, SWT.BORDER | (oid_enabled ? 0 : SWT.READ_ONLY));
		text_oid.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		text_oid.setText(oid);

		LabelText label_notification_script = new LabelText(this, SWT.NONE, false);
		label_notification_script.setText("Notification_script");
		label_notification_script.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));

		text_script = new TextInput4Script(this, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
		text_script.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		text_script.setText(script);

		Composite panelButton = new Composite(this, SWT.NONE);
		panelButton.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 2, 1));
		panelButton.setLayout(new GridLayout(3, false));

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

		ButtonClick buttonOk = new ButtonClick(panelButton, SWT.NONE);
		buttonOk.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				oid = text_oid.getText().trim();
				if (UtilString.isEmpty(oid)) {
					DialogMessage.openInfo(getShell(), "입력오류", UtilString.format("'{}'을 입력하세요.", "Notification_oid"));
					return;
				}
				if (oid.startsWith(".") || oid.endsWith(".")) {
					DialogMessage.openInfo(getShell(), "입력오류", UtilString.format("잘못된 값 입니다. '{}'", oid));
					return;
				}
				String[] tokens = oid.split("\\.");
				int index_level = 0;
				for (String token : tokens) {
					if (token.equals("*")) {
						index_level++;
					} else {
						if (0 < index_level) {
							DialogMessage.openInfo(getShell(), "입력오류", UtilString.format("잘못된 값 입니다. '{}'", oid));
							return;
						}
						try {
							Integer.parseInt(token);
						} catch (Exception ex) {
							DialogMessage.openInfo(getShell(), "입력오류", UtilString.format("잘못된 값 입니다. '{}'", oid));
							return;
						}
					}
				}
				script = text_script.getText().trim();
				if (UtilString.isEmpty(script)) {
					DialogMessage.openInfo(getShell(), "입력오류", UtilString.format("'{}'을 입력하세요.", "Notification_script"));
					return;
				}
				complete = true;
				Dialog4NotificationScript.this.dispose();
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
				Dialog4NotificationScript.this.dispose();
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

	public String getOid() {
		return oid;
	}

	public String getScript() {
		return script;
	}

}
