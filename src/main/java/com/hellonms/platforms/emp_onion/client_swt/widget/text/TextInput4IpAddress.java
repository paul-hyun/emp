package com.hellonms.platforms.emp_onion.client_swt.widget.text;

import java.net.InetAddress;
import java.util.StringTokenizer;

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
 * TextInput4IpAddress
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class TextInput4IpAddress extends Composite implements ControlInputIf {

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
			} else if (e.keyCode == '.') {
				focusNext();
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
		}

		@Override
		public void verifyText(VerifyEvent e) {
			e.doit = (e.character == 0 || e.keyCode == SWT.BS || e.keyCode == SWT.DEL || Character.isDigit(e.character));

			if (Character.isDigit(e.character)) {
				String oldValue = TextInput4IpAddress.this.texts[index].getText();
				StringBuilder stringBuilder = new StringBuilder();
				if (e.start <= oldValue.length()) {
					stringBuilder.append(oldValue.substring(0, e.start));
				}

				stringBuilder.append(e.character);

				if (e.end < oldValue.length()) {
					stringBuilder.append(oldValue.substring(e.end));
				}
				String newValue = stringBuilder.toString();

				if (1 < newValue.length() && newValue.startsWith("0")) {
					e.doit = false;
				} else if (0 < newValue.length()) {
					try {
						int value = Integer.parseInt(newValue);
						e.doit = (min_value <= value && value <= max_value);
					} catch (Exception ex) {
						e.doit = false;
					}
				}
			}
		}

		@Override
		public void modifyText(ModifyEvent e) {
			String string = TextInput4IpAddress.this.texts[index].getText();
			if (string.length() == 3) {
				focusNext();
			}
			TextInput4IpAddress.this.modifyText(e);
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

	private Text[] texts = new Text[4];

	private long min_value = 0x00;

	private long max_value = 0xFF;

	private ModifyListener modifyListener;

	private PanelInputListenerIf inputPanelListener;

	/**
	 * 생성자 입니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 * @param style
	 *            스타일
	 */
	public TextInput4IpAddress(Composite parent, int style) {
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
				labelDot.setText(".");
			}

			texts[i] = new Text(this, SWT.CENTER);
			GridData gd_text = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
			gd_text.widthHint = 24;
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
	 * @param listener
	 *            변경 리스너
	 */
	public void addModifyListener(ModifyListener listener) {
		this.modifyListener = listener;
	}

	/**
	 * IP 주소를 문자열(예. 127.0.0.1)로 반환합니다.
	 * 
	 * @return IP 주소
	 */
	public String getText() {
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < texts.length; i++) {
			stringBuilder.append(i == 0 ? "" : ".").append(texts[i].getText());
		}
		return stringBuilder.toString();
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
		try {
			return InetAddress.getByName(getText());
		} catch (Exception e) {
			try {
				return InetAddress.getByName("127.0.0.1");
			} catch (Exception e1) {
				return null;
			}
		}
	}

	@Override
	public void setValue(Object value) {
		if (value instanceof InetAddress) {
			InetAddress inetAddress = (InetAddress) value;
			byte[] bytes = inetAddress.getAddress();
			texts[0].setText(String.valueOf(0x000000FF & bytes[0]));
			texts[1].setText(String.valueOf(0x000000FF & bytes[1]));
			texts[2].setText(String.valueOf(0x000000FF & bytes[2]));
			texts[3].setText(String.valueOf(0x000000FF & bytes[3]));
		} else if (value instanceof String) {
			StringTokenizer tokenizer = new StringTokenizer((String) value, ".");
			for (int i = 0; i < 4; i++) {
				texts[i].setText(tokenizer.hasMoreTokens() ? tokenizer.nextToken() : "");
			}
		}
	}

	@Override
	public void setPanelInputListener(PanelInputListenerIf listener) {
		this.inputPanelListener = listener;
	}

}
