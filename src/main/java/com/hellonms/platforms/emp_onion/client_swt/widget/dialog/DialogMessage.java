package com.hellonms.platforms.emp_onion.client_swt.widget.dialog;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.hellonms.platforms.emp_onion.client_swt.util.location.UtilLocation;
import com.hellonms.platforms.emp_onion.client_swt.widget.button.ButtonClick;
import com.hellonms.platforms.emp_onion.theme.ThemeBuilder4Onion.COLOR_ONION;
import com.hellonms.platforms.emp_onion.theme.ThemeBuilder4Onion.IMAGE_ONION;
import com.hellonms.platforms.emp_onion.theme.ThemeFactory;

/**
 * <p>
 * DialogMessage
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class DialogMessage extends Shell {

	private enum TYPE {
		PASSWORD_CONFIRM, CONFIRM, ERROR, INFO
	}

	private static DialogMessage passwordConfirm;

	private static DialogMessage confirm;

	private static DialogMessage error;

	private static DialogMessage info;

	/**
	 * 패스워드를 입력할 수 있는 메시지 다이얼로그를 엽니다.
	 * 
	 * @param parent
	 *            부모 쉘
	 * @param title
	 *            제목
	 * @param message
	 *            메시지
	 * @return 비밀번호
	 */
	public static String openPasswordConfirm(Shell parent, String title, String message) {
		if (passwordConfirm != null && !passwordConfirm.isDisposed()) {
			passwordConfirm.dispose();
			passwordConfirm = null;
		}
		passwordConfirm = new DialogMessage(parent, TYPE.PASSWORD_CONFIRM, title, message);
		passwordConfirm.pack();
		UtilLocation.toCenter(parent, passwordConfirm);
		passwordConfirm.open();
		return passwordConfirm.password;
	}

	/**
	 * 확인/취소 버튼이 있는 메시지 다이얼로그를 엽니다.
	 * 
	 * @param parent
	 *            부모 쉘
	 * @param title
	 *            제목
	 * @param message
	 *            메시지
	 * @return 확인(true)/취소(false)
	 */
	public static boolean openConfirm(Shell parent, String title, String message) {
		if (confirm != null && !confirm.isDisposed()) {
			confirm.dispose();
			confirm = null;
		}
		confirm = new DialogMessage(parent, TYPE.CONFIRM, title, message);
		confirm.pack();
		UtilLocation.toCenter(parent, confirm);
		confirm.open();
		return confirm.result;
	}

	/**
	 * 에러 메시지 다이얼로그를 엽니다.
	 * 
	 * @param parent
	 *            부모 쉘
	 * @param title
	 *            제목
	 * @param e
	 *            예외
	 */
	public static void openError(Shell parent, String title, Throwable e) {
		e.printStackTrace();
		if (error != null && !error.isDisposed()) {
			error.dispose();
			error = null;
		}
		error = new DialogMessage(parent, TYPE.ERROR, title, (e.getMessage() == null ? e.getClass().getName() : e.getMessage()));
		error.pack();
		UtilLocation.toCenter(parent, error);
		error.open();
	}

	/**
	 * 에러 메시지 다이얼로그를 엽니다.
	 * 
	 * @param parent
	 *            부모 쉘
	 * @param title
	 *            제목
	 * @param message
	 *            에러 메시지
	 */
	public static void openError(Shell parent, String title, String message) {
		if (error != null && !error.isDisposed()) {
			error.dispose();
			error = null;
		}
		error = new DialogMessage(parent, TYPE.ERROR, title, message);
		error.pack();
		UtilLocation.toCenter(parent, error);
		error.open();
	}

	/**
	 * 메시지 다이얼로그를 엽니다.
	 * 
	 * @param parent
	 *            부모 쉘
	 * @param title
	 *            제목
	 * @param message
	 *            메시지
	 */
	public static void openInfo(Shell parent, String title, String message) {
		if (info != null && !info.isDisposed()) {
			info.dispose();
			info = null;
		}
		info = new DialogMessage(parent, TYPE.INFO, title, message);
		info.pack();
		UtilLocation.toCenter(parent, info);
		info.open();
	}

	private String title;

	private String message;

	private TYPE type;

	private boolean result = false;

	private Text textPassword = null;

	private String password;

	private DialogMessage(Shell parent, TYPE type, String title, String message) {
		super(parent, SWT.APPLICATION_MODAL | SWT.DIALOG_TRIM);
		this.type = type;
		this.title = title;
		this.message = message;

		createGUI();
	}

	private void createGUI() {
		setText(title);
		setBackground(ThemeFactory.getColor(COLOR_ONION.DIALOG_BG));
		setLayout(new FormLayout());

		ButtonClick buttonOk = new ButtonClick(this, SWT.NONE);
		buttonOk.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				executeOk();
			}
		});
		FormData fd_buttonOk = new FormData();
		fd_buttonOk.left = new FormAttachment(100, (type == TYPE.CONFIRM || type == TYPE.PASSWORD_CONFIRM) ? -140 : -70);
		fd_buttonOk.right = new FormAttachment(100, (type == TYPE.CONFIRM || type == TYPE.PASSWORD_CONFIRM) ? -80 : -10);
		fd_buttonOk.bottom = new FormAttachment(100, -10);
		buttonOk.setLayoutData(fd_buttonOk);
		buttonOk.setText("Ok");

		ButtonClick buttonCancel = null;
		if (type == TYPE.CONFIRM || type == TYPE.PASSWORD_CONFIRM) {
			buttonCancel = new ButtonClick(this, SWT.NONE);
			buttonCancel.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					password = null;
					result = false;
					DialogMessage.this.dispose();
				}
			});
			FormData fd_buttonCancel = new FormData();
			fd_buttonCancel.left = new FormAttachment(100, -70);
			fd_buttonCancel.bottom = new FormAttachment(100, -10);
			fd_buttonCancel.right = new FormAttachment(100, -10);
			buttonCancel.setLayoutData(fd_buttonCancel);
			buttonCancel.setText("Cancel");
		}

		Composite panelContents = new Composite(this, SWT.NONE);
		panelContents.setBackgroundMode(SWT.INHERIT_DEFAULT);
		panelContents.setBackground(ThemeFactory.getColor(COLOR_ONION.PAGE_BG));
		GridLayout gl_panelContents = new GridLayout(2, false);
		gl_panelContents.marginRight = 10;
		gl_panelContents.marginBottom = 10;
		gl_panelContents.marginHeight = 0;
		gl_panelContents.verticalSpacing = 10;
		gl_panelContents.marginWidth = 0;
		gl_panelContents.horizontalSpacing = 0;
		panelContents.setLayout(gl_panelContents);
		FormData fd_panelContents = new FormData();
		fd_panelContents.bottom = new FormAttachment(buttonOk, -10);
		fd_panelContents.top = new FormAttachment(0, 10);
		fd_panelContents.left = new FormAttachment(0, 10);
		fd_panelContents.right = new FormAttachment(100, -10);
		panelContents.setLayoutData(fd_panelContents);

		Label labelIcon = new Label(panelContents, SWT.NONE);
		labelIcon.setImage(ThemeFactory.getImage(getIconImage()));
		GridData gd_labelIcon = new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 3);
		gd_labelIcon.verticalIndent = 5;
		labelIcon.setLayoutData(gd_labelIcon);

		Label labelText = new Label(panelContents, SWT.NONE);
		labelText.setImage(ThemeFactory.getImage(getTextImage()));
		labelText.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));

		Text textMessage = new Text(panelContents, SWT.READ_ONLY | SWT.MULTI | SWT.WRAP);
		GridData gd_textMessage = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_textMessage.horizontalIndent = 5;
		gd_textMessage.widthHint = 360;
		textMessage.setLayoutData(gd_textMessage);
		textMessage.setBackground(ThemeFactory.getColor(COLOR_ONION.PAGE_BG));
		textMessage.setForeground(ThemeFactory.getColor(COLOR_ONION.PAGE_FG));
		textMessage.setText(message);

		if (type == TYPE.PASSWORD_CONFIRM) {
			textPassword = new Text(panelContents, SWT.PASSWORD | SWT.BORDER);
			GridData gd_textPassword = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
			textPassword.setLayoutData(gd_textPassword);
			textPassword.setFocus();
			textPassword.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					executeOk();
				}
			});
		}
	}

	@Override
	protected void checkSubclass() {
	}

	@Override
	public void open() {
		super.open();
		super.layout();

		Display display = Display.getCurrent();
		while (!isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	private IMAGE_ONION getIconImage() {
		switch (type) {
		case PASSWORD_CONFIRM:
		case CONFIRM:
			return IMAGE_ONION.DIALOG_MSSAGE_CONFIRM_ICON;
		case ERROR:
			return IMAGE_ONION.DIALOG_MSSAGE_ERROR_ICON;
		case INFO:
			return IMAGE_ONION.DIALOG_MSSAGE_INFO_ICON;
		default:
			return IMAGE_ONION.DIALOG_MSSAGE_ERROR_ICON;
		}
	}

	private IMAGE_ONION getTextImage() {
		switch (type) {
		case PASSWORD_CONFIRM:
		case CONFIRM:
			return IMAGE_ONION.DIALOG_MSSAGE_CONFIRM_TEXT;
		case ERROR:
			return IMAGE_ONION.DIALOG_MSSAGE_ERROR_TEXT;
		case INFO:
			return IMAGE_ONION.DIALOG_MSSAGE_INFO_TEXT;
		default:
			return IMAGE_ONION.DIALOG_MSSAGE_ERROR_TEXT;
		}
	}

	private void executeOk() {
		if (type == TYPE.PASSWORD_CONFIRM) {
			password = textPassword.getText();
		}
		result = true;
		DialogMessage.this.dispose();
	}

}
