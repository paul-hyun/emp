package com.hellonms.platforms.emp_onion.client_swt.widget.dialog;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * <p>
 * Dialog
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class Dialog extends Shell {

	/**
	 * 생성자 입니다.
	 */
	public Dialog() {
		super();
	}

	/**
	 * 생성자 입니다.
	 * 
	 * @param display
	 *            Display 객체
	 * @param style
	 *            스타일
	 */
	public Dialog(Display display, int style) {
		super(display, style);
	}

	/**
	 * 생성자 입니다.
	 * 
	 * @param display
	 *            Display 객체
	 */
	public Dialog(Display display) {
		super(display);
	}

	/**
	 * 생성자 입니다.
	 * 
	 * @param style
	 *            스타일
	 */
	public Dialog(int style) {
		super(style);
	}

	/**
	 * 생성자 입니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 * @param style
	 *            스타일
	 */
	public Dialog(Shell parent, int style) {
		super(parent, style);
	}

	/**
	 * 생성자 입니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 */
	public Dialog(Shell parent) {
		super(parent);
	}

	@Override
	protected void checkSubclass() {
	}

	@Override
	public void open() {
		super.open();
		super.layout();

		Display display = Display.getCurrent();
		while (!isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

}
