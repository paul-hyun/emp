package com.hellonms.platforms.emp_onion.client_swt.widget.button;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

/**
 * <p>
 * ButtonClick
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class ButtonClick extends Button {

	public ButtonClick(Composite parent) {
		this(parent, SWT.PUSH);
	}

	public ButtonClick(Composite parent, int style) {
		super(parent, style);
	}

	@Override
	protected void checkSubclass() {
	}

}
