package com.hellonms.platforms.emp_onion.client_swt.widget.button;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

/**
 * <p>
 * ButtonCheck
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class ButtonCheck extends Button {

	/**
	 * 생성자 입니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 * @param style
	 *            스타일
	 */
	public ButtonCheck(Composite parent) {
		super(parent, SWT.CHECK);
	}

	@Override
	protected void checkSubclass() {
	}

}
