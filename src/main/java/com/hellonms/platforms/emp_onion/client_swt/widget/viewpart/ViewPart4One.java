/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_onion.client_swt.widget.viewpart;

import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.swt.widgets.Control;

import com.hellonms.platforms.emp_onion.client_swt.widget.page.PageIf;
import com.hellonms.platforms.emp_onion.client_swt.widget.workbench.Workbench;

/**
 * <p>
 * Hash Only One page
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 18.
 * @modified 2015. 5. 18.
 * @author cchyun
 *
 */
public class ViewPart4One extends ViewPartAt {

	private class PageItem {

		private final PAGE page;

		public PageItem(PAGE page) {
			this.page = page;
		}

		public void dispose() {
		}

	}

	private PageItem page;

	public ViewPart4One(Workbench workbench, int style) {
		super(workbench, style);
		createGUI();
	}

	private void createGUI() {
	}

	@Override
	public void openPage(PAGE page) {
		if (this.page != null) {
			this.page.dispose();
		}
		this.page = new PageItem(page);
	}

	private AtomicBoolean lock_layout = new AtomicBoolean(false);

	@Override
	public void layout() {
		if (!lock_layout.get() && page != null) {
			try {
				lock_layout.set(true);

				PageIf ppp = getWorkbench().getPage(page.page);
				if (ppp != null) {
					((Control) ppp).setBounds(getBounds());
				}
			} finally {
				lock_layout.set(false);
			}
		}
	}

}
