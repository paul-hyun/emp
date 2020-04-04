package com.hellonms.platforms.emp_orange.client_swt.page.help.environment.email;

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
import com.hellonms.platforms.emp_onion.client_swt.widget.label.LabelText;
import com.hellonms.platforms.emp_onion.client_swt.widget.text.TextInput4String;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;

public class Dialog4EmailTest extends Dialog {

	/**
	 * 이메일 시험발송 다이얼로그 리스너의 인터페이스 입니다.
	 * 
	 * @since Version 1.4.0
	 * @author Hello NMS
	 */
	public interface Dialog4EmailTestListenerIf {

		/**
		 * 이메일을 보낼 경우 호출됩니다.
		 * 
		 * @param dialog
		 *            이메일 시험 다이얼로그
		 * @param tos
		 *            받는 사람의 이메일 주소
		 * @param ccs
		 *            참조
		 * @param bccs
		 *            숨은참조
		 * @param subject
		 *            제목
		 * @param content
		 *            내용
		 * @return
		 */
		public boolean sendEmail(Dialog4EmailTest dialog, String[] tos, String[] ccs, String[] bccs, String subject, String content);

	}

	/**
	 * 이메일 시험발송 다이얼로그 리스너
	 */
	protected Dialog4EmailTestListenerIf listener;

	/**
	 * 받는 사람의 이메일 주소 입력 필드
	 */
	protected TextInput4String textTo;

	/**
	 * 제목 입력 필드
	 */
	protected TextInput4String textSubject;

	/**
	 * 내용 입력 필드
	 */
	protected TextInput4String textContents;

	/**
	 * 생성자 입니다.
	 * 
	 * @param parent
	 *            부모 쉘
	 * @param listener
	 *            이메일 시험발송 다이얼로그 리스너
	 */
	public Dialog4EmailTest(Shell parent, Dialog4EmailTestListenerIf listener) {
		super(parent, SWT.APPLICATION_MODAL | SWT.DIALOG_TRIM);
		this.listener = listener;
		setSize(360, 240);

		createGUI();
	}

	/**
	 * GUI를 생성합니다.
	 */
	protected void createGUI() {
		setLayout(new GridLayout(2, false));

		LabelText labelTo = new LabelText(this, SWT.NONE);
		labelTo.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		labelTo.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.TO));

		textTo = new TextInput4String(this, SWT.BORDER);
		textTo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		LabelText labelSubject = new LabelText(this, SWT.NONE);
		labelSubject.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		labelSubject.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.SUBJECT));

		textSubject = new TextInput4String(this, SWT.BORDER);
		textSubject.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		LabelText labelContents = new LabelText(this, SWT.NONE);
		labelContents.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, false, false, 1, 1));
		labelContents.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.CONTENT));

		textContents = new TextInput4String(this, SWT.BORDER | SWT.WRAP | SWT.MULTI);
		textContents.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		Composite panelButton = new Composite(this, SWT.NONE);
		GridLayout gl_panelButton = new GridLayout(2, false);
		gl_panelButton.marginHeight = 0;
		gl_panelButton.marginWidth = 0;
		panelButton.setLayout(gl_panelButton);
		GridData gd_panelButton = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 2, 1);
		gd_panelButton.verticalIndent = 5;
		panelButton.setLayoutData(gd_panelButton);

		ButtonClick buttonOk = new ButtonClick(panelButton, SWT.NONE);
		buttonOk.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (listener.sendEmail(Dialog4EmailTest.this, new String[] { textTo.getText() }, new String[] {}, new String[] {}, textSubject.getText(), textContents.getText())) {
					Dialog4EmailTest.this.dispose();
				}
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
				Dialog4EmailTest.this.dispose();
			}
		});
		GridData gd_buttonCancel = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_buttonCancel.widthHint = 60;
		buttonCancel.setLayoutData(gd_buttonCancel);
		buttonCancel.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.CANCEL));
	}

}
