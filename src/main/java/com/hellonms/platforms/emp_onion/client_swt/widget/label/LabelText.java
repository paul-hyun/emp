package com.hellonms.platforms.emp_onion.client_swt.widget.label;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.hellonms.platforms.emp_onion.theme.ThemeBuilder4Onion.COLOR_ONION;
import com.hellonms.platforms.emp_onion.theme.ThemeFactory;

/**
 * <p>
 * LabelText
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class LabelText extends Label {

	/**
	 * 생성자 입니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 * @param style
	 *            스타일
	 */
	public LabelText(Composite parent, int style) {
		this(parent, style, false);
	}

	/**
	 * 생성자 입니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 * @param style
	 *            스타일
	 * @param mandatory
	 *            필수여부
	 */
	public LabelText(Composite parent, int style, boolean mandatory) {
		super(parent, style);

		this.setForeground(ThemeFactory.getColor(mandatory ? COLOR_ONION.LABEL_TEXT_MANDATORY_FG : COLOR_ONION.LABEL_TEXT_OPTIONAL_FG));
	}
	
	public void setMandatory(boolean mandatory) {
		setForeground(ThemeFactory.getColor(mandatory ? COLOR_ONION.LABEL_TEXT_MANDATORY_FG : COLOR_ONION.LABEL_TEXT_OPTIONAL_FG));
		redraw();
	}

	@Override
	protected void checkSubclass() {
	}

}
