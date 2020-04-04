/**
 * Copyright 2016 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.client_swt.page.network.network_view.ne;

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
import com.hellonms.platforms.emp_orange.client_swt.model_display.ModelDisplay4NeInfo;
import com.hellonms.platforms.emp_orange.client_swt.page.DataBuilder4Orange.DATA_TABLE_ORANGE;
import com.hellonms.platforms.emp_orange.client_swt.page.PanelBuilder4Orange.TABLE_ORANGE;
import com.hellonms.platforms.emp_orange.client_swt.page.PanelFactory;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO;

/**
 * <p>
 * NE INFO Panel
 * </p>
 *
 * @since 1.6
 * @create 2016. 1. 11.
 * @modified 2016. 1. 11.
 * @author cchyun
 *
 */
public class Panel4NetworkViewNeTable extends Panel {

	private EMP_MODEL_NE_INFO ne_info_def;

	private PanelTableIf panelTable;

	public Panel4NetworkViewNeTable(Composite parent, int style, EMP_MODEL_NE_INFO ne_info_def) {
		super(parent, style, ne_info_def.getName());
		this.ne_info_def = ne_info_def;
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

		DataTableIf dataTable = DataFactory.createDataTable(DATA_TABLE_ORANGE.NE_DETAIL_VIEW, ne_info_def);

		panelTable = PanelFactory.createPanelTable(TABLE_ORANGE.NE_DETAIL_VIEW, panel_round.getContentComposite(), SWT.NONE, 0, null);
		panelTable.setDataTable(dataTable);
	}

	public EMP_MODEL_NE_INFO getNe_info_def() {
		return this.ne_info_def;
	}

	public void display(ModelDisplay4NeInfo model) {
		if (ne_info_def.equals(model.getNe_info_def())) {
			this.ne_info_def = model.getNe_info_def();
			panelTable.setDatas(model);
		} else {
			this.ne_info_def = model.getNe_info_def();
			DataTableIf dataTable = DataFactory.createDataTable(DATA_TABLE_ORANGE.NE_DETAIL_VIEW, ne_info_def);
			panelTable.setDataTable(dataTable);
			panelTable.setDatas(model);
		}
	}

}
