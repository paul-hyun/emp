package com.hellonms.platforms.emp_orange.client_swt.page.security.login;

import java.util.Map;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;

import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_core.share.language.LANGUAGE;
import com.hellonms.platforms.emp_core.share.language.UtilLanguage;
import com.hellonms.platforms.emp_onion.client_swt.data.DataFactory;
import com.hellonms.platforms.emp_onion.client_swt.widget.button.ButtonClick;
import com.hellonms.platforms.emp_onion.client_swt.widget.dialog.DialogMessage;
import com.hellonms.platforms.emp_onion.client_swt.widget.label.LabelImage;
import com.hellonms.platforms.emp_onion.client_swt.widget.selector.SelectorCombo;
import com.hellonms.platforms.emp_onion.client_swt.widget.text.TextInput4String;
import com.hellonms.platforms.emp_onion.share.message.MESSAGE_CODE_ONION;
import com.hellonms.platforms.emp_orange.client_swt.page.DataBuilder4Orange.DATA_COMBO_ORANGE;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;

/**
 * <p>
 * Page4Login
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class Page4Login extends Composite {

	/**
	 * 배경 이미지
	 */
	protected Image bg;

	/**
	 * 서버 IP 입력필드
	 */
	protected TextInput4String textInputServer;

	/**
	 * 계정명 입력필드
	 */
	protected TextInput4String textInputUserAccount;

	/**
	 * 비밀번호 입력필드
	 */
	protected TextInput4String textInputPassword;

	/**
	 * 언어 콤보모델
	 */
	protected SelectorCombo selectorComboLanguage;

	/**
	 * 확인 버튼
	 */
	protected ButtonClick buttonClickOk;

	/**
	 * 취소 버튼
	 */
	protected ButtonClick buttonClickCancel;

	/**
	 * 로그인 상태
	 */
	protected boolean login = false;

	/**
	 * 어드바이저
	 */
	protected Page4LoginAdvisor advisor;

	/**
	 * 생성자 입니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 * @param style
	 *            스타일
	 * @param bg
	 *            배경 이미지
	 */
	public Page4Login(Composite parent, int style, Image bg) {
		super(parent, style);
		this.bg = bg;

		createGUI();
	}

	/**
	 * GUI를 생성합니다.
	 */
	protected void createGUI() {
		setLayout(null);

		textInputServer = new TextInput4String(this, SWT.BORDER | SWT.READ_ONLY);
		textInputServer.setBounds(0, 0, 0, 0);

		textInputUserAccount = new TextInput4String(this, SWT.BORDER);
		textInputUserAccount.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				login();
			}
		});
		textInputUserAccount.setBounds(0, 0, 0, 0);
		textInputUserAccount.forceFocus();

		textInputPassword = new TextInput4String(this, SWT.BORDER | SWT.PASSWORD);
		textInputPassword.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				login();
			}
		});
		textInputPassword.setBounds(0, 0, 0, 0);

		buttonClickOk = new ButtonClick(this, SWT.NONE);
		buttonClickOk.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				login();
			}
		});
		buttonClickOk.setBounds(0, 0, 0, 0);
		buttonClickOk.setText(UtilLanguage.getMessage(MESSAGE_CODE_ONION.OK));

		buttonClickCancel = new ButtonClick(this, SWT.NONE);
		buttonClickCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				login = false;
				Page4Login.this.getShell().dispose();
			}
		});
		buttonClickCancel.setBounds(0, 0, 0, 0);
		buttonClickCancel.setText(UtilLanguage.getMessage(MESSAGE_CODE_ONION.CANCEL));

		selectorComboLanguage = new SelectorCombo(this, SWT.READ_ONLY);
		selectorComboLanguage.setDataCombo(DataFactory.createDataCombo(DATA_COMBO_ORANGE.LOGIN_LANGUAGE));
		selectorComboLanguage.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				Object selectedItem = selectorComboLanguage.getSelectedItem();
				if (selectedItem != null && selectedItem instanceof LANGUAGE) {
					queryLanguage((LANGUAGE) selectedItem);
				}
			}
		});
		selectorComboLanguage.getControl().setBounds(0, 0, 0, 0);

		LabelImage labelBg = new LabelImage(this, SWT.NONE, bg);
		labelBg.setBounds(0, 0, bg.getImageData().width, bg.getImageData().height);

		advisor = new Page4LoginAdvisor();
	}

	@Override
	protected void checkSubclass() {
	}

	/**
	 * 로그인을 합니다.
	 */
	protected void login() {
		String user_account = textInputUserAccount.getText().trim();
		String password = textInputPassword.getText();

		if (user_account.length() == 0) {
			DialogMessage.openInfo(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.LOGIN), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.INSERT_VALUE, MESSAGE_CODE_ORANGE.USER_ID));
			return;
		}
		if (password.length() == 0) {
			DialogMessage.openInfo(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.LOGIN), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.INSERT_VALUE, MESSAGE_CODE_ORANGE.PASSWORD));
			return;
		}

		try {
			login = advisor.login(user_account, password);
		} catch (EmpException ex) {
			DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.LOGIN), ex);
			return;
		} catch (Throwable ex) {
			DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.LOGIN), ex);
			return;
		}

		Page4Login.this.getShell().dispose();
	}

	/**
	 * 로그인 화면의 크기를 설정합니다.
	 * 
	 * @param rect
	 *            크기
	 */
	public void setBoundsInput(Rectangle rect) {
		int locatiinX = rect.x;
		int locatiinY = rect.y;
		int spaceWidth = 4;
		int spaceheight = 6;

		textInputServer.setBounds(locatiinX, locatiinY, rect.width, rect.height);

		locatiinY += (rect.height + spaceheight);
		textInputUserAccount.setBounds(locatiinX, locatiinY, rect.width, rect.height);

		locatiinY += (rect.height + spaceheight);
		textInputPassword.setBounds(locatiinX, locatiinY, rect.width, rect.height);

		locatiinY += (rect.height + spaceheight);
		selectorComboLanguage.getControl().setBounds(locatiinX, locatiinY, rect.width, rect.height);

		buttonClickOk.setBounds(locatiinX + rect.width + spaceWidth, rect.y + spaceheight, 60, rect.height * 2);
		buttonClickCancel.setBounds(locatiinX + rect.width + spaceWidth, rect.y + rect.height * 2 + spaceheight * 2, 60, rect.height * 2);
	}

	/**
	 * 서버 IP 입력필드의 크기를 설정합니다.
	 * 
	 * @param x
	 *            X좌표
	 * @param y
	 *            Y좌표
	 * @param width
	 *            너비
	 * @param height
	 *            높이
	 */
	public void setBoundsTextServer(int x, int y, int width, int height) {
		textInputServer.setBounds(x, y, width, height);
	}

	/**
	 * 사용자 ID 입력필드의 크기를 설정합니다.
	 * 
	 * @param x
	 *            X좌표
	 * @param y
	 *            Y좌표
	 * @param width
	 *            너비
	 * @param height
	 *            높이
	 */
	public void setBoundsTextUserID(int x, int y, int width, int height) {
		textInputUserAccount.setBounds(x, y, width, height);
	}

	/**
	 * 비밀번호 입력필드의 크기를 설정합니다.
	 * 
	 * @param x
	 *            X좌표
	 * @param y
	 *            Y좌표
	 * @param width
	 *            너비
	 * @param height
	 *            높이
	 */
	public void setBoundsTextPassword(int x, int y, int width, int height) {
		textInputPassword.setBounds(x, y, width, height);
	}

	/**
	 * 언어 콤보뷰어의 크기를 설정합니다.
	 * 
	 * @param x
	 *            X좌표
	 * @param y
	 *            Y좌표
	 * @param width
	 *            너비
	 * @param height
	 *            높이
	 */
	public void setBoundsComboLanguage(int x, int y, int width, int height) {
		selectorComboLanguage.getControl().setBounds(x, y, width, height);
	}

	/**
	 * 확인 버튼의 크기를 설정합니다.
	 * 
	 * @param x
	 *            X좌표
	 * @param y
	 *            Y좌표
	 * @param width
	 *            너비
	 * @param height
	 *            높이
	 */
	public void setBoundsButtonOk(int x, int y, int width, int height) {
		buttonClickOk.setBounds(x, y, width, height);
	}

	/**
	 * 취소 버튼의 크기를 설정합니다.
	 * 
	 * @param x
	 *            X좌표
	 * @param y
	 *            Y좌표
	 * @param width
	 *            너비
	 * @param height
	 *            높이
	 */
	public void setBoundsButtonCancel(int x, int y, int width, int height) {
		buttonClickCancel.setBounds(x, y, width, height);
	}

	/**
	 * 서버 IP를 설정합니다.
	 * 
	 * @param text
	 *            서버 IP
	 */
	public void setTextServer(String text) {
		textInputServer.setText(text);
	}

	/**
	 * 사용자 ID를 설정합니다.
	 * 
	 * @param text
	 *            사용자 ID
	 */
	public void setTextUserID(String text) {
		textInputUserAccount.setText(text);
	}

	/**
	 * 비밀번호를 설정합니다.
	 * 
	 * @param text
	 *            비밀번호
	 */
	public void setTextPassword(String text) {
		textInputPassword.setText(text);
	}

	/**
	 * 언어를 반환합니다.
	 * 
	 * @return 언어
	 */
	public LANGUAGE getLanguage() {
		Object selectedItem = selectorComboLanguage.getSelectedItem();
		return selectedItem == null ? LANGUAGE.ENGLISH : (LANGUAGE) selectedItem;
	}

	/**
	 * 언어를 설정합니다.
	 * 
	 * @param language
	 *            언어
	 */
	public void setLanguage(LANGUAGE language) {
		selectorComboLanguage.setSelectedItem(language);
	}

	/**
	 * 언어를 설정합니다.
	 * 
	 * @param languages
	 *            언어 배열
	 */
	public void setLanguages(LANGUAGE[] languages) {
		selectorComboLanguage.setDatas((Object) languages);
	}

	/**
	 * 로그인 상태를 반환합니다.
	 * 
	 * @return 로그인 상태
	 */
	public boolean isLogin() {
		return login;
	}

	protected void queryLanguage(LANGUAGE language) {
		try {
			UtilLanguage.setLanguage(language);

			Map<String, String> messages = advisor.queryLanguage(language);
			UtilLanguage.setMessages(messages);

			buttonClickOk.setText(UtilLanguage.getMessage(MESSAGE_CODE_ONION.OK));
			buttonClickCancel.setText(UtilLanguage.getMessage(MESSAGE_CODE_ONION.CANCEL));
		} catch (EmpException e) {
			DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.LOGIN), e);
		} catch (Throwable e) {
			DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.LOGIN), e);
		}
	}

}
