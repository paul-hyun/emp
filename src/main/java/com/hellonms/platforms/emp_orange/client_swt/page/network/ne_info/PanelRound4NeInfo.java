/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.client_swt.page.network.ne_info;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelRound;
import com.hellonms.platforms.emp_orange.client_swt.model_display.ModelDisplay4NeInfo;
import com.hellonms.platforms.emp_orange.share.model.network.ne_info.Model4NeInfoIf;

/**
 * <p>
 * Insert description of PanelRound4NeInfo.java
 * </p>
 *
 * @since 1.6
 * @create 2015. 7. 28.
 * @modified 2015. 7. 28.
 * @author cchyun
 *
 */
public class PanelRound4NeInfo extends PanelRound implements PanelInput4NeInfoIf {

	public PanelInput4NeInfoIf panelInput4NeInfo;

	public PanelRound4NeInfo(Composite parent, int style) {
		super(parent, style);
		getContentComposite().setLayout(new FillLayout(SWT.HORIZONTAL));
	}

	@Override
	public void display(ModelDisplay4NeInfo modelDisplay4NeInfo) {
		if (panelInput4NeInfo != null) {
			panelInput4NeInfo.display(modelDisplay4NeInfo);
		}
	}

	@Override
	public void clear() {
		if (panelInput4NeInfo != null) {
			panelInput4NeInfo.clear();
		}
	}

	@Override
	public int getStartNo() {
		return panelInput4NeInfo.getStartNo();
	}

	@Override
	public Model4NeInfoIf getSelected() {
		return panelInput4NeInfo.getSelected();
	}

	@Override
	public boolean isNeedWizard() {
		return panelInput4NeInfo.isNeedWizard();
	}

	@Override
	public boolean isComplete() {
		return panelInput4NeInfo.isComplete();
	}

	@Override
	public String getErrorMessage() {
		return panelInput4NeInfo.getErrorMessage();
	}

}
