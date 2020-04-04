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
 * TextInput4Float32
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class TextInput4Float32 extends TextInput implements ControlInputIf {

	private float min_value;

	private float max_value;

	private PanelInputListenerIf listener;

	/**
	 * 생성자 입니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 * @param style
	 *            스타일
	 */
	public TextInput4Float32(Composite parent, int style) {
		this(parent, style, -Float.MAX_VALUE, Float.MAX_VALUE, "");
	}

	/**
	 * 생성자 입니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 * @param style
	 *            스타일
	 * @param guide
	 *            가이드 (설명)
	 */
	public TextInput4Float32(Composite parent, int style, String guide) {
		this(parent, style, -Float.MAX_VALUE, Float.MAX_VALUE, guide);
	}

	/**
	 * 생성자 입니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 * @param style
	 *            스타일
	 * @param min_value
	 *            최소값
	 * @param max_value
	 *            최대값
	 */
	public TextInput4Float32(Composite parent, int style, float min_value, float max_value) {
		this(parent, style, min_value, max_value, "");
	}

	/**
	 * 생성자 입니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 * @param style
	 *            스타일
	 * @param min_value
	 *            최소값
	 * @param max_value
	 *            최대값
	 * @param guide
	 *            가이드 (설명)
	 */
	public TextInput4Float32(Composite parent, int style, float min_value, float max_value, String guide) {
		super(parent, style, guide);

		this.min_value = min_value;
		this.max_value = max_value;

		addVerifyListener(new VerifyListener() {
			@Override
			public void verifyText(VerifyEvent e) {
				TextInput4Float32.this.verifyText(e);
			}
		});

		addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				TextInput4Float32.this.modifyText(e);
			}
		});
	}

	private void verifyText(VerifyEvent e) {
		try {
			e.doit = (e.character == 0 || e.keyCode == SWT.BS || e.keyCode == SWT.DEL || e.character == '-' || e.character == '.' || Character.isDigit(e.character));

			if (Character.isDigit(e.character) || e.character == '-' || e.character == '.') {
				String oldValue = TextInput4Float32.this.getText();
				StringBuilder stringBuilder = new StringBuilder();
				if (e.start <= oldValue.length()) {
					stringBuilder.append(oldValue.substring(0, e.start));
				}

				stringBuilder.append(e.character);

				if (e.end < oldValue.length()) {
					stringBuilder.append(oldValue.substring(e.end));
				}
				String newValue = stringBuilder.toString();

				if (newValue.equals("-")) {
					e.doit = min_value < 0;
				} else if (!newValue.contains(".") && (1 < newValue.length() && newValue.startsWith("0") || (2 < newValue.length() && newValue.startsWith("-0")))) {
					e.doit = false;
				} else if (0 < newValue.length()) {
					float value = Float.parseFloat(newValue);
					if (0 < value) {
						e.doit = value <= TextInput4Float32.this.max_value;
					} else if (value < 0) {
						e.doit = TextInput4Float32.this.min_value <= value;
					}
				}
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
	 * 최소값을 설정합니다.
	 * 
	 * @param min_value
	 *            최소값
	 */
	public void setMin_value(float min_value) {
		this.min_value = min_value;
	}

	/**
	 * 최대값을 설정합니다.
	 * 
	 * @param max_value
	 *            최대값
	 */
	public void setMax_value(float max_value) {
		this.max_value = max_value;
	}

	@Override
	public Control getControl() {
		return this;
	}

	@Override
	public boolean isComplete() {
		try {
			float value = Float.parseFloat(getText());
			return min_value <= value && value <= max_value;
		} catch (Exception ex) {
			return false;
		}
	}

	@Override
	public Object getValue() {
		try {
			return Float.parseFloat(getText());
		} catch (Exception e) {
			return 0;
		}
	}

	@Override
	public void setValue(Object value) {
	}

	@Override
	public void setPanelInputListener(PanelInputListenerIf listener) {
		this.listener = listener;
	}

}
