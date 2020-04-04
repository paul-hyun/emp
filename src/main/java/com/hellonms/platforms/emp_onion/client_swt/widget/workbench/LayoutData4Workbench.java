/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_onion.client_swt.widget.workbench;

/**
 * <p>
 * Layout Data
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 18.
 * @modified 2015. 5. 18.
 * @author cchyun
 *
 */
public class LayoutData4Workbench {

	public enum WORKBENCH_ITEM {
		VIEWPART_TOOLBAR, //
		VIEWPART_LEFT, //
		VIEWPART_CENTER, //
		VIEWPART_BOTTOM, //
		SASH_HORIZONTAL, //
		SASH_VERTICAL, //
	}

	public final WORKBENCH_ITEM worbench_item;

	public double layout_weight;

	public LayoutData4Workbench(WORKBENCH_ITEM worbench_item, double layout_weight) {
		this.worbench_item = worbench_item;
		this.layout_weight = layout_weight;
	}

}
