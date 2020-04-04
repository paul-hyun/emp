package com.hellonms.platforms.emp_onion.client_swt.widget.text;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.hellonms.platforms.emp_onion.client_swt.util.resource.UtilResource;
import com.hellonms.platforms.emp_onion.client_swt.widget.control.ControlInputIf;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelInputAt.PanelInputListenerIf;

/**
 * <p>
 * TextInput4MacAddress
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class TextInput4MacAddress extends Composite implements ControlInputIf {

	private class ChildListener implements KeyListener, VerifyListener, ModifyListener {

		private int index;

		public ChildListener(int index) {
			this.index = index;
		}

		@Override
		public void keyPressed(KeyEvent e) {
			if (e.keyCode == SWT.ARROW_LEFT && texts[index].getCaretPosition() == 0) {
				focusPrev();
			} else if (e.keyCode == SWT.BS && texts[index].getCaretPosition() == 0) {
				focusPrev();
			} else if (e.keyCode == SWT.ARROW_RIGHT && texts[index].getCaretPosition() == texts[index].getCharCount()) {
				focusNext();
			} else if (e.keyCode == ':') {
				focusNext();
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
		}

		@Override
		public void verifyText(VerifyEvent e) {
			e.doit = (e.keyCode == SWT.BS || e.keyCode == SWT.DEL || (Character.isDigit(e.character) || ('a' <= e.character && e.character <= 'f') || ('A' <= e.character && e.character <= 'F')));

			if (Character.isDigit(e.character) || ('a' <= e.character && e.character <= 'f') || ('A' <= e.character && e.character <= 'F')) {
				String oldValue = TextInput4MacAddress.this.texts[index].getText();
				StringBuilder stringBuilder = new StringBuilder();
				if (e.start <= oldValue.length()) {
					stringBuilder.append(oldValue.substring(0, e.start));
				}

				stringBuilder.append(e.character);

				if (e.end < oldValue.length()) {
					stringBuilder.append(oldValue.substring(e.end));
				}
				String newValue = stringBuilder.toString();

				if (0 < newValue.length()) {
					try {
						int value = Integer.parseInt(newValue, 16);
						e.doit = (min_value <= value && value <= max_value);
					} catch (Exception ex) {
						e.doit = false;
					}
				}
			}
		}

		@Override
		public void modifyText(ModifyEvent e) {
			String string = TextInput4MacAddress.this.texts[index].getText();
			if (string.length() == 4) {
				focusNext();
			}
			TextInput4MacAddress.this.modifyText(e);
		}

		private void focusPrev() {
			if (0 < index) {
				texts[index - 1].setFocus();
			}
		}

		private void focusNext() {
			if (index < texts.length - 1) {
				texts[index + 1].setFocus();
			}
		}

	}

	private Text[] texts = new Text[8];

	private long min_value = 0x00;

	private long max_value = 0xFF;

	private ModifyListener modifyListener;

	private PanelInputListenerIf inputPanelListener;

	/**
	 * 생성자 입니다.
	 * 
	 * @param parent	부모 컴포지트
	 * @param style		스타일
	 */
	public TextInput4MacAddress(Composite parent, int style) {
		super(parent, style);

		super.setBackground(UtilResource.getColor(SWT.COLOR_WHITE));
		super.setBackgroundMode(SWT.INHERIT_DEFAULT);

		GridLayout gridLayout = new GridLayout(texts.length * 2 - 1, false);
		gridLayout.verticalSpacing = 0;
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		gridLayout.horizontalSpacing = 0;
		setLayout(gridLayout);

		for (int i = 0; i < texts.length; i++) {
			if (0 < i) {
				Label labelDot = new Label(this, SWT.NONE);
				labelDot.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
				labelDot.setText(":");
			}

			texts[i] = new Text(this, SWT.CENTER);
			GridData gd_text = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
			gd_text.widthHint = 27;
			texts[i].setLayoutData(gd_text);

			ChildListener childListener = new ChildListener(i);
			texts[i].addKeyListener(childListener);
			texts[i].addVerifyListener(childListener);
			texts[i].addModifyListener(childListener);
		}
	}

	private void modifyText(ModifyEvent e) {
		if (modifyListener != null) {
			modifyListener.modifyText(e);
		}
		if (inputPanelListener != null) {
			inputPanelListener.completeChanged();
		}
	}

	/**
	 * 변경 리스너를 추가합니다.
	 * 
	 * @param modifyListener	변경 리스너
	 */
	public void addModifyListener(ModifyListener modifyListener) {
		this.modifyListener = modifyListener;
	}

	/**
	 * Mac 주소를 반환합니다.
	 * 
	 * @return	Mac 주소
	 */
	public String getText() {
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < texts.length; i++) {
			stringBuilder.append(i == 0 ? "" : ":").append(texts[i].getText());
		}
		return stringBuilder.toString();
	}

	/**
	 * Mac 주소를 설정합니다. (구 현 내용 없음)
	 * 
	 * @param text	Mac 주소
	 */
	public void setText(String text) {
	}

	@Override
	public Control getControl() {
		return this;
	}

	@Override
	public boolean isComplete() {
		for (Text text : texts) {
			try {
				int value = Integer.parseInt(text.getText());
				if (value < min_value && max_value < value) {
					return false;
				}
			} catch (Exception e) {
				return false;
			}
		}
		return true;
	}

	@Override
	public Object getValue() {
		return getText();
	}

	@Override
	public void setValue(Object value) {
	}

	@Override
	public void setPanelInputListener(PanelInputListenerIf inputPanelListener) {
		this.inputPanelListener = inputPanelListener;
	}

}
