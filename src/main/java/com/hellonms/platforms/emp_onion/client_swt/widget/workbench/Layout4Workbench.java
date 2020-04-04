/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_onion.client_swt.widget.workbench;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Sash;

import com.hellonms.platforms.emp_onion.client_swt.widget.viewpart.ViewPartAt;

/**
 * <p>
 * Layout
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 18.
 * @modified 2015. 5. 18.
 * @author cchyun
 *
 */
public class Layout4Workbench extends Layout {

	private int toolbar_space = 0;

	private int viewpart_padding = 2;

	private int viewpart_space = 4;

	private Point workbench_size_priv;

	@Override
	protected Point computeSize(Composite composite, int wHint, int hHint, boolean flushCache) {
		return new Point(100, 100);
	}

	@Override
	protected void layout(Composite composite, boolean flushCache) {
		if (!(composite instanceof Workbench)) {
			throw new RuntimeException(composite + " is not Workbench !!");
		}
		Workbench workbench = (Workbench) composite;
		Point workbench_size = workbench.getSize();

		int toolbar_y = 0;
		int sash_0_y = 0;
		int sash_1_x = 0;
		int resize_x = 100;
		int resize_y = 100;

		for (ViewPartAt view_part : workbench.getView_parts()) {
			LayoutData4Workbench layout_data = view_part.getLayoutData();
			switch (layout_data.worbench_item) {
			case VIEWPART_TOOLBAR:
				toolbar_y = (int) layout_data.layout_weight;
				view_part.setBounds(0, 0, workbench_size.x, toolbar_y);
				break;
			default:
				break;
			}
		}

		if (workbench_size_priv != null && !(workbench_size_priv.x == workbench_size.x && workbench_size_priv.y == workbench_size.y)) {
			resize_x = workbench_size.x * 100 / workbench_size_priv.x;
			resize_y = (workbench_size.y - toolbar_y) * 100 / (workbench_size_priv.y - toolbar_y);
		}
		workbench_size_priv = workbench_size;

		for (Sash sash : workbench.getSashs()) {
			LayoutData4Workbench layout_data = (LayoutData4Workbench) sash.getLayoutData();
			switch (layout_data.worbench_item) {
			case SASH_HORIZONTAL:
				if (layout_data.layout_weight < 1) {
					sash_0_y = (int) (toolbar_y + toolbar_space + (workbench_size.y - toolbar_y - toolbar_space) * layout_data.layout_weight);
				} else {
					if (resize_y != 100) {
						layout_data.layout_weight = (layout_data.layout_weight * resize_y / 100);
					}
					sash_0_y = (int) (layout_data.layout_weight);
				}
				sash_0_y = Math.max(toolbar_y + 80, sash_0_y);
				sash_0_y = Math.min(workbench_size.y - 80, sash_0_y);
				sash.setBounds(0, sash_0_y, workbench_size.x, viewpart_space);
				break;
			case SASH_VERTICAL:
				if (layout_data.layout_weight < 1) {
					sash_1_x = (int) (workbench_size.x * layout_data.layout_weight + 5);
				} else {
					if (resize_x != 100) {
						layout_data.layout_weight = (layout_data.layout_weight * resize_x / 100);
					}
					sash_1_x = (int) (layout_data.layout_weight);
				}
				sash_1_x = Math.max(80, sash_1_x);
				sash_1_x = Math.min(workbench_size.x - 80, sash_1_x);
				sash.setBounds(sash_1_x, toolbar_y, viewpart_space, sash_0_y - toolbar_y);
				break;
			default:
				break;
			}
		}

		for (ViewPartAt view_part : workbench.getView_parts()) {
			LayoutData4Workbench layout_data = view_part.getLayoutData();
			switch (layout_data.worbench_item) {
			case VIEWPART_TOOLBAR:
				view_part.setBounds(0, 0, workbench_size.x, (int) layout_data.layout_weight);
				break;
			case VIEWPART_LEFT:
				view_part.setBounds(viewpart_padding, toolbar_y + toolbar_space, sash_1_x - viewpart_padding, sash_0_y - toolbar_y - toolbar_space);
				break;
			case VIEWPART_CENTER:
				view_part.setBounds(sash_1_x + viewpart_space, toolbar_y + toolbar_space, workbench_size.x - sash_1_x - viewpart_padding - viewpart_space, sash_0_y - toolbar_y - toolbar_space);
				break;
			case VIEWPART_BOTTOM:
				view_part.setBounds(viewpart_padding, sash_0_y + viewpart_space, workbench_size.x - viewpart_padding - viewpart_padding, workbench_size.y - sash_0_y - viewpart_padding - viewpart_space);
				break;
			default:
				break;
			}
			view_part.layout();
		}
	}

}
