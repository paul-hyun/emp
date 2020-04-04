/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.client_swt.page.network.ne_statistics;

import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;

import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_core.share.language.UtilLanguage;
import com.hellonms.platforms.emp_onion.client_swt.widget.button.ButtonClick;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.Panel;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelChartIf;
import com.hellonms.platforms.emp_orange.client_swt.model_display.ModelDisplay4NeStatistics;
import com.hellonms.platforms.emp_orange.client_swt.widget.page.PanelPopup4ChartItem;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO;

/**
 * <p>
 * Statistics Panel
 * </p>
 *
 * @since 1.6
 * @create 2015. 11. 3.
 * @modified 2015. 11. 3.
 * @author cchyun
 *
 */
public abstract class Panel4NeStatisticsDataAt extends Panel {

	/**
	 * 성능통계 검색결과 판넬의 리스너 인터페이스 입니다.
	 * 
	 * @since Version 1.4.0
	 * @author Hello NMS
	 */
	public interface Panel4NeStatisticsDataListenerIf {

		/**
		 * 성능통계 리스트를 조회합니다.
		 */
		public void queryListNeStatistics();

		public void saveExcelNeStatistics(String path);

	}

	/**
	 * 리스너
	 */
	protected Panel4NeStatisticsDataListenerIf listener;

	protected Composite panelTable;

	protected PanelInput4NeStatisticsIf tableControl;

	protected ButtonClick buttonRefresh;

	protected ButtonClick buttonExcel;

	protected Composite panelChart;

	protected PanelInput4NeStatisticsIf chartControl;

	/**
	 * 차트 버튼 배열
	 */
	protected ButtonClick buttonChartItem;

	protected Map<String, PanelInput4NeStatisticsIf> tableMap = new LinkedHashMap<String, PanelInput4NeStatisticsIf>();

	protected Map<String, PanelInput4NeStatisticsIf> chartMap = new LinkedHashMap<String, PanelInput4NeStatisticsIf>();

	/**
	 * 생성자 입니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 * @param style
	 *            스타일
	 * @param listener
	 *            리스너
	 */
	public Panel4NeStatisticsDataAt(Composite parent, int style, Panel4NeStatisticsDataListenerIf listener) {
		super(parent, style, UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.DATA_TITLE, MESSAGE_CODE_ORANGE.NE_STATISTICS));
		this.listener = listener;

		createGUI();
	}

	/**
	 * GUI를 생성합니다.
	 */
	protected void createGUI() {
		getContentComposite().setLayout(new FormLayout());

		panelTable = new Composite(getContentComposite(), SWT.NONE);
		FormData fd_panelTable = new FormData();
		fd_panelTable.bottom = new FormAttachment(100, -210);
		fd_panelTable.right = new FormAttachment(100, -80);
		fd_panelTable.top = new FormAttachment(0, 5);
		fd_panelTable.left = new FormAttachment(0, 5);
		panelTable.setLayoutData(fd_panelTable);
		panelTable.setLayout(new StackLayout());

		ButtonClick[] tableButtons = createTableButton(getContentComposite());
		for (int i = 0; i < tableButtons.length; i++) {
			FormData fd_button = new FormData();
			fd_button.right = (i == 0) ? new FormAttachment(100, -5) : new FormAttachment(tableButtons[i - 1], 0, SWT.RIGHT);
			fd_button.left = (i == 0) ? new FormAttachment(panelTable, 5) : new FormAttachment(tableButtons[i - 1], 0, SWT.LEFT);
			fd_button.top = (i == 0) ? new FormAttachment(panelTable, 0, SWT.TOP) : new FormAttachment(tableButtons[i - 1], 5, SWT.BOTTOM);
			tableButtons[i].setLayoutData(fd_button);
		}

		panelChart = new Composite(getContentComposite(), SWT.NONE);
		FormData fd_panelChart = new FormData();
		fd_panelChart.top = new FormAttachment(panelTable, 5);
		fd_panelChart.bottom = new FormAttachment(100, -5);
		fd_panelChart.right = new FormAttachment(panelTable, 0, SWT.RIGHT);
		fd_panelChart.left = new FormAttachment(0, 5);
		panelChart.setLayoutData(fd_panelChart);
		panelChart.setLayout(new StackLayout());

		ButtonClick[] chartButtons = createChartButton(getContentComposite());
		for (int i = 0; i < chartButtons.length; i++) {
			FormData fd_button = new FormData();
			fd_button.right = (i == 0) ? new FormAttachment(100, -5) : new FormAttachment(chartButtons[i - 1], 0, SWT.RIGHT);
			fd_button.left = (i == 0) ? new FormAttachment(panelChart, 5) : new FormAttachment(chartButtons[i - 1], 0, SWT.LEFT);
			fd_button.top = (i == 0) ? new FormAttachment(panelChart, 0, SWT.TOP) : new FormAttachment(chartButtons[i - 1], 5, SWT.BOTTOM);
			chartButtons[i].setLayoutData(fd_button);
		}
	}

	/**
	 * 테이블 버튼을 생성합니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 * @return 버튼 배열
	 */
	protected ButtonClick[] createTableButton(Composite parent) {
		buttonRefresh = new ButtonClick(parent);
		buttonRefresh.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Panel4NeStatisticsDataAt.this.queryListNeStatistics(0);
			}
		});
		buttonRefresh.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.REFRESH));

		buttonExcel = new ButtonClick(parent);
		buttonExcel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog fileDialog = new FileDialog(getShell(), SWT.SAVE);
				fileDialog.setFilterExtensions(new String[] { "*.xlsx" });
				final String file = fileDialog.open();

				if (file != null) {
					listener.saveExcelNeStatistics(file);
				}
			}
		});
		buttonExcel.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.EXCEL));

		return new ButtonClick[] { buttonRefresh, buttonExcel };
	}

	/**
	 * 차트 버튼을 생성합니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 * @return 버튼 배열
	 */
	protected ButtonClick[] createChartButton(Composite parent) {
		buttonChartItem = new ButtonClick(parent, SWT.PUSH);
		buttonChartItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Panel4NeStatisticsDataAt.this.openChartItemCheckerPopup();
			}
		});
		buttonChartItem.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.CHART_ITEM));

		return new ButtonClick[] { buttonChartItem };
	}

	public void display(EMP_MODEL_NE_INFO ne_info_def, Object... datas) throws EmpException {
		if (ne_info_def == null || !ne_info_def.isDisplay_enable()) {
			((StackLayout) panelTable.getLayout()).topControl = null;
			this.tableControl = null;

			((StackLayout) panelChart.getLayout()).topControl = null;
			this.chartControl = null;
		} else {
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(ne_info_def);
			for (Object data : datas) {
				stringBuilder.append(".").append(data);
			}
			String key = stringBuilder.toString();

			PanelInput4NeStatisticsIf tableControl = tableMap.get(key);
			if (tableControl == null) {
				tableControl = createPanelTable4NeStatistics(panelTable, ne_info_def, datas);
				tableMap.put(key, tableControl);
			}
			((StackLayout) panelTable.getLayout()).topControl = (Control) tableControl;
			this.tableControl = tableControl;

			PanelInput4NeStatisticsIf chartControl = chartMap.get(key);
			if (chartControl == null) {
				chartControl = createPanelChart4NeStatistics(panelChart, ne_info_def, datas);
				chartMap.put(key, chartControl);
			}
			((StackLayout) panelChart.getLayout()).topControl = (Control) chartControl;
			this.chartControl = chartControl;
		}

		panelTable.layout();
		panelChart.layout();

		buttonRefresh.setVisible(ne_info_def != null);
		buttonExcel.setVisible(ne_info_def != null);
		buttonChartItem.setVisible(ne_info_def != null);

		getContentComposite().layout();
	}

	protected abstract PanelInput4NeStatisticsIf createPanelTable4NeStatistics(Composite parent, EMP_MODEL_NE_INFO ne_info_def, Object... datas);

	protected abstract PanelInput4NeStatisticsIf createPanelChart4NeStatistics(Composite parent, EMP_MODEL_NE_INFO ne_info_def, Object... datas);

	public void display(ModelDisplay4NeStatistics modelDisplay4NeStatistics) {
		Control tableControl = (((StackLayout) panelTable.getLayout())).topControl;
		if (tableControl instanceof PanelInput4NeStatisticsIf) {
			((PanelInput4NeStatisticsIf) tableControl).display(modelDisplay4NeStatistics);
		}

		Control chartControl = (((StackLayout) panelChart.getLayout())).topControl;
		if (chartControl instanceof PanelInput4NeStatisticsIf) {
			((PanelInput4NeStatisticsIf) chartControl).display(modelDisplay4NeStatistics);
		}
	}

	/**
	 * 성능통계 리스트를 조회합니다.
	 * 
	 * @param startNo
	 *            시작번호
	 */
	protected void queryListNeStatistics(int startNo) {
		listener.queryListNeStatistics();
	}

	protected void openChartItemCheckerPopup() {
		Control chartControl = (((StackLayout) panelChart.getLayout())).topControl;
		if (chartControl instanceof PanelRound4NeStatistics && ((PanelRound4NeStatistics) chartControl).panelInput4NeStatistics instanceof PanelChartIf) {
			PanelChartIf panelChart = (PanelChartIf) ((PanelRound4NeStatistics) chartControl).panelInput4NeStatistics;
			Point checker_size = buttonChartItem.getSize();
			PanelPopup4ChartItem popup = new PanelPopup4ChartItem(getShell(), panelChart.getDataChart());
			Point popup_size = popup.getSize();
			Point location = buttonChartItem.toDisplay(checker_size.x - popup_size.x, checker_size.y + 1);
			Rectangle screen = buttonChartItem.getDisplay().getClientArea();
			if (screen.height < location.y + popup_size.y) {
				location = buttonChartItem.toDisplay(checker_size.x - popup_size.x, -(popup_size.y + 1));
			}
			popup.setLocation(location);
			popup.open();
		}
	}

}
