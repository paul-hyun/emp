/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.client_swt.page.network.ne_info;

import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.hellonms.platforms.emp_core.share.language.UtilLanguage;
import com.hellonms.platforms.emp_onion.client_swt.data.DataFactory;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelInputAt.PANEL_INPUT_TYPE;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelInputAt.PanelInputListenerIf;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelTable;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelTable.PanelTableListenerIf;
import com.hellonms.platforms.emp_orange.client_swt.model_display.ModelDisplay4NeInfo;
import com.hellonms.platforms.emp_orange.client_swt.page.DataBuilder4Orange.DATA_TABLE_ORANGE;
import com.hellonms.platforms.emp_orange.client_swt.page.PanelBuilder4Orange.INPUT_ORANGE;
import com.hellonms.platforms.emp_orange.client_swt.page.PanelBuilder4Orange.TABLE_ORANGE;
import com.hellonms.platforms.emp_orange.client_swt.page.PanelFactory;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO;
import com.hellonms.platforms.emp_orange.share.model.network.ne_info.Model4NeInfoIf;
import com.hellonms.platforms.emp_util.date.UtilDate;

/**
 * <p>
 * Insert description of Panel4NeInfoData.java
 * </p>
 *
 * @since 1.6
 * @create 2015. 7. 27.
 * @modified 2015. 7. 27.
 * @author cchyun
 *
 */
public class Panel4NeInfoData extends Panel4NeInfoDataAt {

	public class Panel4NeInfoDataInputChildListener implements //
			PanelTableListenerIf, //
			PanelInputListenerIf {

		@Override
		public void load(int startNo, int count) {
			listener.queryListNeInfo(startNo, true);
		}

		@Override
		public void completeChanged() {
		}

	}

	/**
	 * 생성자 입니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 * @param style
	 *            스타일
	 * @param rowCount
	 *            테이블 페이지당 최대 항목 개수
	 * @param listener
	 *            리스너
	 */
	public Panel4NeInfoData(Composite parent, int style, Panel4NeInfoDataListenerIf listener) {
		super(parent, style, listener);
	}

	/**
	 * 생성자 입니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 * @param style
	 *            스타일
	 * @param rowCount
	 *            테이블 페이지당 최대 항목 개수
	 * @param listener
	 *            리스너
	 */
	public Panel4NeInfoData(Composite parent, int style, String title, Panel4NeInfoDataListenerIf listener) {
		super(parent, style, title, listener);
	}

	@Override
	protected PanelInput4NeInfoIf createPanelInput4NeInfo(Composite parent, EMP_MODEL_NE_INFO ne_info_def, Object... datas) {
		if (ne_info_def.isTable()) {
			PanelRound4NeInfo panelRound4NeInfo = new PanelRound4NeInfo(parent, SWT.NONE);
			panelRound4NeInfo.panelInput4NeInfo = (PanelInput4NeInfoIf) PanelFactory.createPanelTable(TABLE_ORANGE.NE_INFO, panelRound4NeInfo.getContentComposite(), SWT.BORDER | SWT.FULL_SELECTION, 0, new Panel4NeInfoDataInputChildListener(), ne_info_def, datas);
			((PanelTable) panelRound4NeInfo.panelInput4NeInfo).setDataTable(DataFactory.createDataTable(DATA_TABLE_ORANGE.NE_INFO, ne_info_def));
			((PanelTable) panelRound4NeInfo.panelInput4NeInfo).addSelectionChangedListener(new ISelectionChangedListener() {
				@Override
				public void selectionChanged(SelectionChangedEvent event) {
					StructuredSelection selection = (StructuredSelection) event.getSelection();
					if (event.getSelection().isEmpty()) {
						Panel4NeInfoData.this.selectionChanged(null);
					} else {
						Panel4NeInfoData.this.selectionChanged((Model4NeInfoIf) selection.getFirstElement());
					}
				}
			});
			((PanelTable) panelRound4NeInfo.panelInput4NeInfo).addDoubleClickListener(new IDoubleClickListener() {
				@Override
				public void doubleClick(DoubleClickEvent event) {
					StructuredSelection selection = (StructuredSelection) event.getSelection();
					if (!event.getSelection().isEmpty()) {
						Panel4NeInfoData.this.listener.openWizard4UpdateNeInfo((Model4NeInfoIf) selection.getFirstElement());
					}
				}
			});
			return panelRound4NeInfo;
		} else {
			return (PanelInput4NeInfoIf) PanelFactory.createPanelInput(INPUT_ORANGE.NE_INFO, parent, SWT.NONE, PANEL_INPUT_TYPE.UPDATE, new Panel4NeInfoDataInputChildListener(), ne_info_def, datas);
		}
	}

	@Override
	public void display(ModelDisplay4NeInfo modelDisplay4NeInfo) {
		super.display(modelDisplay4NeInfo);

		Model4NeInfoIf model4NeInfo = modelDisplay4NeInfo.getModel4NeInfo(0);
		if (model4NeInfo != null) {
			setTitle(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.DATA_TITLE, MESSAGE_CODE_ORANGE.NE_INFO) + " ( " + UtilDate.format(UtilDate.TIME_FORMAT, model4NeInfo.getCollect_time()) + " )");
		}
	}

}
