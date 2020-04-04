package com.hellonms.platforms.emp_onion.client_swt.widget.text;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.hellonms.platforms.emp_onion.client_swt.widget.control.ControlInputIf;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelInputAt.PanelInputListenerIf;

/**
 * <p>
 * TextInput4DigitString
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class TextInput4DigitString extends TextInput implements ControlInputIf {

	private long min_size;

	private long max_size;

	private PanelInputListenerIf listener;

	/**
	 * 생성자 입니다.
	 * 
	 * @param parent	부모 컴포지트
	 * @param style		스타일
	 */
	public TextInput4DigitString(Composite parent, int style) {
		this(parent, style, 0, Integer.MAX_VALUE, "");
	}

	/**
	 * 생성자 입니다.
	 * 
	 * @param parent	부모 컴포지트
	 * @param style		스타일
	 * @param guide		가이드 (설명)
	 */
	public TextInput4DigitString(Composite parent, int style, String guide) {
		this(parent, style, 0, Integer.MAX_VALUE, guide);
	}

	/**
	 * 생성자 입니다.
	 * 
	 * @param parent	부모 컴포지트
	 * @param style		스타일
	 * @param min_size	문자열의 최소 길이
	 * @param max_size	문자열의 최대 길이
	 */
	public TextInput4DigitString(Composite parent, int style, long min_size, long max_size) {
		this(parent, style, min_size, max_size, "");
	}

	/**
	 * 생성자 입니다.
	 * 
	 * @param parent	부모 컴포지트
	 * @param style		스타일
	 * @param min_size	문자열의 최소 길이
	 * @param max_size	문자열의 최대 길이
	 * @param guide		가이드 (설명)
	 */
	public TextInput4DigitString(Composite parent, int style, long min_size, long max_size, String guide) {
		super(parent, style, guide);

		this.min_size = min_size;
		this.max_size = max_size;

		addVerifyListener(new VerifyListener() {
			@Override
			public void verifyText(VerifyEvent e) {
				TextInput4DigitString.this.verifyText(e);
			}
		});

		addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				TextInput4DigitString.this.modifyText(e);
			}
		});
	}

	private void verifyText(VerifyEvent e) {
		try {
			if (e.character == 0 || e.keyCode == SWT.BS || e.keyCode == SWT.DEL || Character.isDigit(e.character)) {
				String oldValue = TextInput4DigitString.this.getText();
				StringBuilder stringBuilder = new StringBuilder();
				if (e.start <= oldValue.length()) {
					stringBuilder.append(oldValue.substring(0, e.start));
				}

				stringBuilder.append(e.character);

				if (e.end < oldValue.length()) {
					stringBuilder.append(oldValue.substring(e.end));
				}
				String newValue = stringBuilder.toString();

				e.doit = newValue.length() <= TextInput4DigitString.this.max_size;
			} else {
				e.doit = false;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			e.doit = false;
		}
	}

	private void modifyText(ModifyEvent e) {
		if (listener != null) {
			listener.completeChanged();
		}
	}

	/**
	 * 문자열의 최소 길이를 설정합니다.
	 * 
	 * @param min_size	문자열의 최소 길이
	 */
	public void setMin_size(long min_size) {
		this.min_size = min_size;
	}

	/**
	 * 문자열의 최대 길이를 설정합니다.
	 * 
	 * @param max_size	문자열의 최대 길이
	 */
	public void setMax_size(long max_size) {
		this.max_size = max_size;
	}

	@Override
	public Control getControl() {
		return this;
	}

	@Override
	public boolean isComplete() {
		int size = getText().length();
		return min_size <= size && size <= max_size;
	}

	@Override
	public String getValue() {
		return getText();
	}

	@Override
	public void setValue(Object value) {
	}

	@Override
	public void setPanelInputListener(PanelInputListenerIf listener) {
		this.listener = listener;
	}

}
