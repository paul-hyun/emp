package com.hellonms.platforms.emp_onion.client_swt.widget.text;

import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import com.hellonms.platforms.emp_onion.theme.ThemeBuilder4Onion.COLOR_ONION;
import com.hellonms.platforms.emp_onion.theme.ThemeFactory;

/**
 * <p>
 * TextInput
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class TextInput extends Text {

	private String guide;

	/**
	 * 생성자 입니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 * @param style
	 *            스타일
	 */
	public TextInput(Composite parent, int style) {
		this(parent, style, "");
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
	public TextInput(Composite parent, int style, String guide) {
		super(parent, style);
		this.guide = guide;

		createGUI();
	}

	private void createGUI() {
		addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				if (guide != null && guide.length() > 0 && getText().length() == 0) {
					setForeground(ThemeFactory.getColor(COLOR_ONION.TEXT_INPUT_GUIDE_FG));
					TextInput.super.setText(guide);
				}
			}

			@Override
			public void focusGained(FocusEvent e) {
				if (guide != null && guide.length() > 0 && getText().length() == 0) {
					setForeground(ThemeFactory.getColor(COLOR_ONION.TEXT_INPUT_FG));
					TextInput.super.setText("");
				}
			}
		});
		setText("");
	}

	@Override
	protected void checkSubclass() {
	}

	@Override
	public String getText() {
		String text = super.getText();
		if (text.equals(guide)) {
			return "";
		} else {
			return text;
		}
	}

	@Override
	public void setText(String text) {
		if (text.length() == 0 && guide != null && guide.length() > 0) {
			setForeground(ThemeFactory.getColor(COLOR_ONION.TEXT_INPUT_GUIDE_FG));
			super.setText(guide);
		} else {
			setForeground(ThemeFactory.getColor(COLOR_ONION.TEXT_INPUT_FG));
			super.setText(text);
		}
	}

}
