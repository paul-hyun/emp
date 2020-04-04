/**
 * Copyright 2016 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.client_swt.page.network.network_view.nms;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;

import com.hellonms.platforms.emp_onion.client_swt.data.DataFactory;
import com.hellonms.platforms.emp_onion.client_swt.data.table.DataTableIf;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.Panel;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelRound;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelTableIf;
import com.hellonms.platforms.emp_orange.client_swt.page.DataBuilder4Orange.DATA_TABLE_ORANGE;
import com.hellonms.platforms.emp_orange.client_swt.page.PanelBuilder4Orange.TABLE_ORANGE;
import com.hellonms.platforms.emp_orange.client_swt.page.PanelFactory;
import com.hellonms.platforms.emp_orange.share.model.network.ne.Model4ResourceNMS;

/**
 * <p>
 * MEM Panel
 * </p>
 *
 * @since 1.6
 * @create 2016. 1. 11.
 * @modified 2016. 1. 11.
 * @author cchyun
 *
 */
public class Panel4NetworkViewNMSTable extends Panel {

	public static final String PARTITION = "PARTITION";

	private String category;

	private PanelTableIf panelTable;

	public Panel4NetworkViewNMSTable(Composite parent, int style, String category) {
		super(parent, style, category);
		this.category = category;
		createGUI();
	}

	private void createGUI() {
		getContentComposite().setLayout(new FormLayout());

		PanelRound panel_round = new PanelRound(getContentComposite(), SWT.NONE);
		FormData fd_panel_round = new FormData();
		fd_panel_round.top = new FormAttachment(0, 5);
		fd_panel_round.left = new FormAttachment(0, 5);
		fd_panel_round.right = new FormAttachment(100, -5);
		fd_panel_round.bottom = new FormAttachment(100, -5);
		panel_round.setLayoutData(fd_panel_round);
		panel_round.getContentComposite().setLayout(new FillLayout());

		DataTableIf dataTable = DataFactory.createDataTable(DATA_TABLE_ORANGE.NMS_DETAIL_VIEW, category);

		panelTable = PanelFactory.createPanelTable(TABLE_ORANGE.NMS_DETAIL_VIEW, panel_round.getContentComposite(), SWT.NONE, 0, null);
		panelTable.setDataTable(dataTable);
	}

	public void display(Model4ResourceNMS[] resource_nmss) {
		panelTable.setDatas((Object) resource_nmss);
	}

}
