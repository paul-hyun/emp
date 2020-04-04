package com.hellonms.platforms.emp_onion.client_swt.util.location;

import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Shell;

public class UtilLocation {

	/**
	 * 스크린의 중앙으로 위치를 이동합니다.
	 * 
	 * @param shell
	 *            위치를 이동하고자 하는 쉘
	 */
	public static void toCenter(Shell shell) {
		Rectangle screenRect = shell.getDisplay().getBounds();
		Rectangle shellRect = shell.getBounds();
		shell.setLocation((screenRect.width - shellRect.width) / 2, (screenRect.height - shellRect.height) / 2);
	}

	/**
	 * 부모 쉘의 중앙으로 위치를 이동합니다.
	 * <p>
	 * 부모 쉘이 null일 경우 스크린의 중앙으로 이동합니다.
	 * </p>
	 * 
	 * @param parent
	 *            부모 쉘
	 * @param shell
	 *            위치를 이동하고자 하는 셀
	 */
	public static void toCenter(Shell parent, Shell shell) {
		if (parent == null) {
			toCenter(shell);
		} else {
			Rectangle parentRect = parent.getBounds();
			Rectangle shellRect = shell.getBounds();
			shell.setLocation(parentRect.x + (parentRect.width - shellRect.width) / 2, parentRect.y + (parentRect.height - shellRect.height) / 2);
		}
	}

}
