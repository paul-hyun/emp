/**
 * Copyright 2016 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.client_swt.page.network.ne_threshold;

import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.hellonms.platforms.emp_onion.client_swt.data.DataFactory;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelInputAt.PANEL_INPUT_TYPE;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelInputAt.PanelInputListenerIf;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelTable;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelTable.PanelTableListenerIf;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelTableIf;
import com.hellonms.platforms.emp_orange.client_swt.page.DataBuilder4Orange.DATA_TABLE_ORANGE;
import com.hellonms.platforms.emp_orange.client_swt.page.PanelBuilder4Orange.INPUT_ORANGE;
import com.hellonms.platforms.emp_orange.client_swt.page.PanelBuilder4Orange.TABLE_ORANGE;
import com.hellonms.platforms.emp_orange.client_swt.page.PanelFactory;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO_FIELD;
import com.hellonms.platforms.emp_orange.share.model.network.ne_info.Model4NeThresholdIf;

/**
 * <p>
 * Panel4NeThresholdDataAt
 * </p>
 *
 * @since 1.6
 * @create 2016. 1. 22.
 * @modified 2016. 1. 22.
 * @author jungsun
 */
public class Panel4NeThresholdData extends Panel4NeThresholdDataAt {

	public class Panel4NeThresholdDataInputChildListener implements //
			PanelTableListenerIf, //
			PanelInputListenerIf {

		@Override
		public void load(int startNo, int count) {
			listener.queryNeThreshold();
		}

		@Override
		public void completeChanged() {
		}

	}

	public Panel4NeThresholdData(Composite parent, int style, Panel4NeThresholdDataListenerIf listener) {
		super(parent, style, listener);
	}

	@Override
	protected PanelInput4NeThresholdIf createPanelInput4NeThreshold(Composite parent, EMP_MODEL_NE_INFO ne_info_def, Object... datas) {
		EMP_MODEL_NE_INFO_FIELD[] ne_info_field_defs = ne_info_def.getNe_thresholds();
		if (1 < ne_info_field_defs.length) {
			final PanelRound4NeThreshold panelRound4NeThreshold = new PanelRound4NeThreshold(parent, SWT.NONE);
			panelRound4NeThreshold.panelInput4NeThreshold = (PanelInput4NeThresholdIf) PanelFactory.createPanelTable(TABLE_ORANGE.NE_THRESHOLD, panelRound4NeThreshold.getContentComposite(), SWT.BORDER | SWT.FULL_SELECTION, 0, new Panel4NeThresholdDataInputChildListener(), ne_info_def, datas);
			((PanelTable) panelRound4NeThreshold.panelInput4NeThreshold).setDataTable(DataFactory.createDataTable(DATA_TABLE_ORANGE.NE_THRESHOLD, ne_info_def));
			((PanelTable) panelRound4NeThreshold.panelInput4NeThreshold).addSelectionChangedListener(new ISelectionChangedListener() {
				@Override
				public void selectionChanged(SelectionChangedEvent event) {
					StructuredSelection selection = (StructuredSelection) event.getSelection();
					if (event.getSelection().isEmpty()) {
						Panel4NeThresholdData.this.selectionChanged(null, null);
					} else {
						Model4NeThresholdIf ne_threshold = (Model4NeThresholdIf) ((PanelTableIf) panelRound4NeThreshold.panelInput4NeThreshold).getDataTable().getData();
						EMP_MODEL_NE_INFO_FIELD ne_info_field_def = (EMP_MODEL_NE_INFO_FIELD) selection.getFirstElement();
						Panel4NeThresholdData.this.selectionChanged(ne_threshold, ne_info_field_def);
					}
				}
			});
			((PanelTable) panelRound4NeThreshold.panelInput4NeThreshold).addDoubleClickListener(new IDoubleClickListener() {
				@Override
				public void doubleClick(DoubleClickEvent event) {
					StructuredSelection selection = (StructuredSelection) event.getSelection();
					if (!event.getSelection().isEmpty()) {
						Model4NeThresholdIf ne_threshold = (Model4NeThresholdIf) ((PanelTableIf) panelRound4NeThreshold.panelInput4NeThreshold).getDataTable().getData();
						EMP_MODEL_NE_INFO_FIELD ne_info_field_def = (EMP_MODEL_NE_INFO_FIELD) selection.getFirstElement();
						Panel4NeThresholdData.this.listener.openWizard4UpdateNeThreshold(ne_threshold, ne_info_field_def);
					}
				}
			});
			return panelRound4NeThreshold;
		} else if (ne_info_field_defs.length == 1) {
			return (PanelInput4NeThresholdIf) PanelFactory.createPanelInput(INPUT_ORANGE.NE_THRESHOLD, parent, SWT.NONE, PANEL_INPUT_TYPE.UPDATE, new Panel4NeThresholdDataInputChildListener(), ne_info_def, ne_info_field_defs[0], datas);
		} else {
			return null;
		}
	}

}
