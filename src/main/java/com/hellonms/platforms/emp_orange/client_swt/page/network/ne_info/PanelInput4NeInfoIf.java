/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.client_swt.page.network.ne_info;

import com.hellonms.platforms.emp_orange.client_swt.model_display.ModelDisplay4NeInfo;
import com.hellonms.platforms.emp_orange.share.model.network.ne_info.Model4NeInfoIf;

/**
 * <p>
 * NE INFO Interface
 * </p>
 *
 * @since 1.6
 * @create 2015. 7. 27.
 * @modified 2015. 7. 27.
 * @author cchyun
 *
 */
public interface PanelInput4NeInfoIf {

	public void display(ModelDisplay4NeInfo modelDisplay4NeInfo);

	public int getStartNo();

	public Model4NeInfoIf getSelected();

	public boolean isNeedWizard();

	public boolean isComplete();

	public String getErrorMessage();

	public void clear();

}
