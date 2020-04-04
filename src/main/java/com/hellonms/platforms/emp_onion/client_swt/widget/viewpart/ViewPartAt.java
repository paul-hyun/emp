/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_onion.client_swt.widget.viewpart;

import org.eclipse.swt.graphics.Rectangle;

import com.hellonms.platforms.emp_onion.client_swt.widget.workbench.LayoutData4Workbench;
import com.hellonms.platforms.emp_onion.client_swt.widget.workbench.Workbench;

/**
 * <p>
 * ViewPart
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 18.
 * @modified 2015. 5. 18.
 * @author cchyun
 *
 */
public abstract class ViewPartAt {

	private Workbench workbench;

	private LayoutData4Workbench layout_data;

	private Rectangle bounds = new Rectangle(0, 0, 0, 0);

	public ViewPartAt(Workbench workbench, int style) {
		this.workbench = workbench;
	}

	public Workbench getWorkbench() {
		return workbench;
	}

	public LayoutData4Workbench getLayoutData() {
		return layout_data;
	}

	public void setLayoutData(LayoutData4Workbench layout_data) {
		this.layout_data = layout_data;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public void setBounds(int x, int y, int width, int height) {
		this.bounds = new Rectangle(x, y, width, height);
	}

	public abstract void layout();

	public abstract void openPage(PAGE page);

}
