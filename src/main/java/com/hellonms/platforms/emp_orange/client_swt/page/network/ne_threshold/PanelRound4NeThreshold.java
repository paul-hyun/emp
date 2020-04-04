/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.client_swt.page.network.ne_threshold;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelRound;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO_FIELD;
import com.hellonms.platforms.emp_orange.share.model.network.ne_info.Model4NeThresholdIf;

/**
 * <p>
 * Insert description of PanelRound4NeThreshold.java
 * </p>
 *
 * @since 1.6
 * @create 2015. 7. 28.
 * @modified 2015. 7. 28.
 * @author cchyun
 *
 */
public class PanelRound4NeThreshold extends PanelRound implements PanelInput4NeThresholdIf {

	public PanelInput4NeThresholdIf panelInput4NeThreshold;

	public PanelRound4NeThreshold(Composite parent, int style) {
		super(parent, style);
		getContentComposite().setLayout(new FillLayout(SWT.HORIZONTAL));
	}

	@Override
	public void display(Model4NeThresholdIf ne_threshold) {
		if (panelInput4NeThreshold != null) {
			panelInput4NeThreshold.display(ne_threshold);
		}
	}

	@Override
	public void clear() {
		if (panelInput4NeThreshold != null) {
			panelInput4NeThreshold.clear();
		}
	}

	@Override
	public int getStartNo() {
		return panelInput4NeThreshold.getStartNo();
	}

	@Override
	public Model4NeThresholdIf getSelected() {
		return panelInput4NeThreshold.getSelected();
	}

	@Override
	public EMP_MODEL_NE_INFO_FIELD getNe_field_code() {
		return panelInput4NeThreshold.getNe_field_code();
	}

	@Override
	public boolean isNeedWizard() {
		return panelInput4NeThreshold.isNeedWizard();
	}

	@Override
	public boolean isComplete() {
		return panelInput4NeThreshold.isComplete();
	}

	@Override
	public String getErrorMessage() {
		return panelInput4NeThreshold.getErrorMessage();
	}

}
