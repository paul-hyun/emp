/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_onion.client_swt.widget.action;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.ToolItem;

/**
 * <p>
 * Action
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 27.
 * @modified 2015. 5. 27.
 * @author cchyun
 *
 */
public interface ActionIf {

	/**
	 * @return
	 */
	public int getStyle();

	/**
	 * @return
	 */
	public String getTooltip();

	/**
	 * @return
	 */
	public String getText();

	/**
	 * @return
	 */
	public Image getImage();

	/**
	 * <p>
	 * 최초작업 수행
	 * </p>
	 *
	 * @param item
	 */
	public void initAction(ToolItem item);

	/**
	 * <p>
	 * 작업수행
	 * </p>
	 *
	 * @param e
	 */
	public void widgetSelected(ToolItem item);

}
