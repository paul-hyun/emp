package com.hellonms.platforms.emp_orange.client_swt.page.help.environment.email;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_core.share.language.UtilLanguage;
import com.hellonms.platforms.emp_onion.client_swt.util.location.UtilLocation;
import com.hellonms.platforms.emp_onion.client_swt.widget.button.ButtonCheck;
import com.hellonms.platforms.emp_onion.client_swt.widget.button.ButtonClick;
import com.hellonms.platforms.emp_onion.client_swt.widget.dialog.DialogMessage;
import com.hellonms.platforms.emp_onion.client_swt.widget.dialog.DialogProgress;
import com.hellonms.platforms.emp_onion.client_swt.widget.dialog.DialogProgress.ProgressTaskIf;
import com.hellonms.platforms.emp_onion.client_swt.widget.label.LabelText;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PagePreference;
import com.hellonms.platforms.emp_onion.client_swt.widget.text.TextInput4Integer16;
import com.hellonms.platforms.emp_onion.client_swt.widget.text.TextInput4String;
import com.hellonms.platforms.emp_orange.client_swt.page.help.environment.email.Dialog4EmailTest.Dialog4EmailTestListenerIf;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;

public class PagePreference4Email extends PagePreference {

	/**
	 * 이메일 시험발송 다이얼로그의 리스너 인터페이스를 구현한 리스너 클래스 입니다.
	 * 
	 * @since Version 1.4.0
	 * @author Hello NMS
	 */
	public class PagePreference4EmailChildListener implements //
			Dialog4EmailTestListenerIf {

		@Override
		public boolean sendEmail(Dialog4EmailTest dialog, String[] tos, String[] ccs, String[] bccs, String subject, String content) {
			return PagePreference4Email.this.sendEmail(dialog, tos, ccs, bccs, subject, content, true);
		}

	}

	/**
	 * 사용여부 체크버튼
	 */
	protected ButtonCheck buttonCheckAdminState;

	/**
	 * 발신 Email 입력필드
	 */
	protected TextInput4String textInputFromEmail;

	/**
	 * 발신 이름 입력필드
	 */
	protected TextInput4String textInputFromName;

	/**
	 * SMTP서버 주소 입력필드
	 */
	protected TextInput4String textInputSmtpHost;

	/**
	 * SMTP서버 포트 입력필드
	 */
	protected TextInput4Integer16 textInputSmtpPort;

	/**
	 * SMTP 계정 입력필드
	 */
	protected TextInput4String textInputSmtpAccount;

	/**
	 * SMTP 암호 입력필드
	 */
	protected TextInput4String textInputSmtpPassword;

	/**
	 * 시험 버튼
	 */
	protected ButtonClick buttonTest;

	protected PagePreference4EmailAdvisor advisor;

	public PagePreference4Email(String title) {
		super(title, "environment.email");

		setDescription(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.ENVIRONMENT_DESCRIPTION, MESSAGE_CODE_ORANGE.ALARM_EMAIL_SEND));

		advisor = createPagePreference4EmailAdvisor();
	}

	@Override
	protected Control createContents(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(2, false));

		buttonCheckAdminState = new ButtonCheck(container);
		buttonCheckAdminState.setSelection(true);
		buttonCheckAdminState.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				textInputFromEmail.setEnabled(buttonCheckAdminState.getSelection());
				textInputFromName.setEnabled(buttonCheckAdminState.getSelection());
				textInputSmtpHost.setEnabled(buttonCheckAdminState.getSelection());
				textInputSmtpPort.setEnabled(buttonCheckAdminState.getSelection());
				textInputSmtpAccount.setEnabled(buttonCheckAdminState.getSelection());
				textInputSmtpPassword.setEnabled(buttonCheckAdminState.getSelection());
				buttonTest.setEnabled(buttonCheckAdminState.getSelection());
			}
		});
		buttonCheckAdminState.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		buttonCheckAdminState.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.ALARM_EMAIL_SEND));

		LabelText labelTextFromEmail = new LabelText(container, SWT.NONE);
		labelTextFromEmail.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		labelTextFromEmail.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.FROM_EMAIL));

		textInputFromEmail = new TextInput4String(container, SWT.BORDER);
		textInputFromEmail.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		LabelText labelTextFromName = new LabelText(container, SWT.NONE);
		labelTextFromName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		labelTextFromName.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.FROM_NAME));

		textInputFromName = new TextInput4String(container, SWT.BORDER);
		textInputFromName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		LabelText labelTextSmtpHost = new LabelText(container, SWT.NONE);
		labelTextSmtpHost.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		labelTextSmtpHost.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.SMTP_IP));

		textInputSmtpHost = new TextInput4String(container, SWT.BORDER);
		textInputSmtpHost.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		LabelText labelTextSmtpPort = new LabelText(container, SWT.NONE);
		labelTextSmtpPort.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		labelTextSmtpPort.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.SMTP_PORT));

		textInputSmtpPort = new TextInput4Integer16(container, SWT.BORDER);
		textInputSmtpPort.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		LabelText labelTextSmtpAccount = new LabelText(container, SWT.NONE);
		labelTextSmtpAccount.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		labelTextSmtpAccount.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.SMTP_ACCOUNT));

		textInputSmtpAccount = new TextInput4String(container, SWT.BORDER);
		textInputSmtpAccount.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		LabelText labelTextSmtpPassword = new LabelText(container, SWT.NONE);
		labelTextSmtpPassword.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		labelTextSmtpPassword.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.SMTP_PASSWORD));

		textInputSmtpPassword = new TextInput4String(container, SWT.BORDER | SWT.PASSWORD);
		textInputSmtpPassword.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new LabelText(container, SWT.NONE);

		buttonTest = new ButtonClick(container, SWT.NONE);
		buttonTest.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Dialog4EmailTest dialog = new Dialog4EmailTest(getShell(), new PagePreference4EmailChildListener());
				UtilLocation.toCenter(getShell(), dialog);
				dialog.open();
			}
		});
		GridData gd_buttonTest = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		gd_buttonTest.widthHint = 80;
		buttonTest.setLayoutData(gd_buttonTest);
		buttonTest.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.TEST));

		return container;
	}

	public PagePreference4EmailAdvisor createPagePreference4EmailAdvisor() {
		return new PagePreference4EmailAdvisor();
	}

	/**
	 * 이메일을 보냅니다.
	 * 
	 * @param dialog
	 *            이메일 시험발송 다이얼로그
	 * @param tos
	 *            수신 Email
	 * @param ccs
	 *            참조
	 * @param bccs
	 *            숨은참조
	 * @param subject
	 *            제목
	 * @param content
	 *            내용
	 * @param progressBar
	 *            프로그래스바 실행여부
	 * @return 수신결과
	 */
	protected boolean sendEmail(Dialog4EmailTest dialog, final String[] tos, final String[] ccs, final String[] bccs, final String subject, final String content, boolean progressBar) {
		try {
			int aaa = 25;
			try {
				aaa = Integer.parseInt(textInputSmtpPort.getText());
			} catch (Exception e) {
				DialogMessage.openInfo(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.ENVIRONMENT_DESCRIPTION, MESSAGE_CODE_ORANGE.ALARM_EMAIL_SEND), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.INVALID_VALUE, MESSAGE_CODE_ORANGE.SMTP_PORT));
				return false;
			}

			final String host = textInputSmtpHost.getText();
			final int port = aaa;
			final String account = textInputSmtpAccount.getText();
			final String password = textInputSmtpPassword.getText();
			final String from = textInputFromEmail.getText();
			final String fromName = textInputFromName.getText();

			DialogProgress.run(dialog.getShell(), progressBar, new ProgressTaskIf() {
				@Override
				public Object run() throws EmpException {
					advisor.sendEmail(host, port, account, password, from, fromName, tos, ccs, bccs, subject, content);
					return null;
				}
			});

			return true;
		} catch (EmpException ex) {
			DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.ENVIRONMENT_DESCRIPTION, MESSAGE_CODE_ORANGE.ALARM_EMAIL_SEND), ex);
			return false;
		} catch (Exception ex) {
			DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.ENVIRONMENT_DESCRIPTION, MESSAGE_CODE_ORANGE.ALARM_EMAIL_SEND), ex);
			return false;
		}
	}

}
