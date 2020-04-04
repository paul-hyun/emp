/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.client_swt.page.network.ne_statistics;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelRound;
import com.hellonms.platforms.emp_orange.client_swt.model_display.ModelDisplay4NeStatistics;

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
public class PanelRound4NeStatistics extends PanelRound implements PanelInput4NeStatisticsIf {

	public PanelInput4NeStatisticsIf panelInput4NeStatistics;

	public PanelRound4NeStatistics(Composite parent, int style) {
		super(parent, style);
		getContentComposite().setLayout(new FillLayout(SWT.HORIZONTAL));
	}

	@Override
	public void display(ModelDisplay4NeStatistics modelDisplay4NeStatistics) {
		if (panelInput4NeStatistics != null) {
			panelInput4NeStatistics.display(modelDisplay4NeStatistics);
		}
	}

}
