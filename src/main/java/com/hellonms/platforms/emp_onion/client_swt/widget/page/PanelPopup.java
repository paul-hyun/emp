package com.hellonms.platforms.emp_onion.client_swt.widget.page;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

/**
 * <p>
 * PanelPopup
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class PanelPopup extends Shell {

	/**
	 * 생성자 입니다.
	 * 
	 * @param parent
	 *            부모 쉘
	 */
	public PanelPopup(Shell parent) {
		super(parent, SWT.NO_TRIM);

		createGUI();
	}

	private void createGUI() {
		final Listener focusListener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				boolean dispose = true;
				if (event.widget instanceof Control && (PanelPopup.this == ((Control) event.widget).getShell() || PanelPopup.this == ((Control) event.widget).getShell().getParent())) {
					dispose = false;
				}
				if (dispose) {
					PanelPopup.this.dispose();
				}
			}
		};
		getDisplay().addFilter(SWT.FocusIn, focusListener);
		addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				getDisplay().removeFilter(SWT.FocusIn, focusListener);
			}
		});
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
