package com.hellonms.platforms.emp_onion.client_swt.widget.selector;

import org.eclipse.swt.widgets.Composite;

import com.hellonms.platforms.emp_onion.client_swt.data.combo.DataComboSimple;
import com.hellonms.platforms.emp_onion.client_swt.widget.control.ControlInputIf;

/**
 * <p>
 * SelectorComboBoolean
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class SelectorComboBoolean extends SelectorCombo implements ControlInputIf {

	/**
	 * 생성자 입니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 * @param style
	 *            스타일
	 */
	public SelectorComboBoolean(Composite parent, int style) {
		super(parent, style);
		super.setDataCombo(new DataComboSimple(true, false));
	}

	@Override
	public Boolean getValue() {
		return (Boolean) getSelectedItem();
	}

}
