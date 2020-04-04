/**
 * Copyright 2016 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.client_swt.page.network.network_view.ne;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.hellonms.platforms.emp_core.share.language.UtilLanguage;
import com.hellonms.platforms.emp_onion.client_swt.data.DataFactory;
import com.hellonms.platforms.emp_onion.client_swt.data.chart.DataChartIf;
import com.hellonms.platforms.emp_onion.client_swt.widget.button.ButtonLink;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.Panel;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelChartIf;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelRound;
import com.hellonms.platforms.emp_orange.client_swt.model_display.ModelDisplay4NeStatistics;
import com.hellonms.platforms.emp_orange.client_swt.page.DataBuilder4Orange.DATA_CHART_ORANGE;
import com.hellonms.platforms.emp_orange.client_swt.page.PanelBuilder4Orange.CHART_ORANGE;
import com.hellonms.platforms.emp_orange.client_swt.page.PanelFactory;
import com.hellonms.platforms.emp_orange.client_swt.page.network.network_view.ne.Panel4NetworkViewNe.Panel4NetworkViewNeListenerIf;
import com.hellonms.platforms.emp_orange.client_swt.widget.page.PanelPopup4ChartItem;
import com.hellonms.platforms.emp_orange.client_swt.widget.page.PanelPopup4NeInfoIndex;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO;
import com.hellonms.platforms.emp_orange.share.model.network.ne_info.NE_INFO_INDEX;
import com.hellonms.platforms.emp_util.date.UtilDate;
import com.hellonms.platforms.emp_util.string.UtilString;

/**
 * <p>
 * NE STATISTICS Panel
 * </p>
 *
 * @since 1.6
 * @create 2016. 1. 11.
 * @modified 2016. 1. 11.
 * @author cchyun
 *
 */
public class Panel4NetworkViewNeChart extends Panel {

	private static final Map<Integer, Integer> ne_statistics_index_map = new HashMap<Integer, Integer>();

	public static Map<Integer, Integer> getNeStatisticsIndexMap() {
		return ne_statistics_index_map;
	}

	private ButtonLink buttonNeInfoIndex;

	private ButtonLink buttonChartItem;

	private PanelChartIf panel_chart;

	private EMP_MODEL_NE_INFO ne_info_def;

	private ModelDisplay4NeStatistics model;

	private Panel4NetworkViewNeListenerIf listener;

	public Panel4NetworkViewNeChart(Composite parent, int style, EMP_MODEL_NE_INFO ne_info_def, Panel4NetworkViewNeListenerIf listener) {
		super(parent, style, ne_info_def.getName());
		this.ne_info_def = ne_info_def;
		this.listener = listener;

		createGUI();
	}

	private void createGUI() {
		buttonNeInfoIndex = new ButtonLink(getPanelToolbar(), SWT.NONE);
		buttonNeInfoIndex.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.PERFORMANCE_LOCATION));
		buttonNeInfoIndex.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				openNeInfoIndexPopup();
			}
		});

		buttonChartItem = new ButtonLink(getPanelToolbar(), SWT.NONE);
		buttonChartItem.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.CHART_ITEM));
		buttonChartItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				openChartItemCheckerPopup();
			}
		});

		getContentComposite().setLayout(new FormLayout());

		PanelRound panel_round = new PanelRound(getContentComposite(), SWT.NONE);
		FormData fd_panel_round = new FormData();
		fd_panel_round.top = new FormAttachment(0, 5);
		fd_panel_round.left = new FormAttachment(0, 5);
		fd_panel_round.right = new FormAttachment(100, -5);
		fd_panel_round.bottom = new FormAttachment(100, -5);
		panel_round.setLayoutData(fd_panel_round);
		panel_round.getContentComposite().setLayout(new FormLayout());

		DataChartIf dataChart = DataFactory.createDataChart(DATA_CHART_ORANGE.NE_DETAIL_VIEW, ne_info_def);

		panel_chart = PanelFactory.createPanelChart(CHART_ORANGE.NE_DETAIL_VIEW, panel_round.getContentComposite(), SWT.NONE);
		FormData fd_panel_chart = new FormData();
		fd_panel_chart.top = new FormAttachment(0, 0);
		fd_panel_chart.left = new FormAttachment(0, 0);
		fd_panel_chart.right = new FormAttachment(100, 0);
		fd_panel_chart.bottom = new FormAttachment(100, 0);
		((Control) panel_chart).setLayoutData(fd_panel_chart);
		panel_chart.setDataChart(dataChart);
		panel_chart.displayLineChart("", UtilDate.MINUTELY_FORMAT, UtilDate.MINUTELY_FORMAT, true);
	}

	public EMP_MODEL_NE_INFO getNe_info_def() {
		return this.ne_info_def;
	}

	public void display(ModelDisplay4NeStatistics model) {
		this.model = model;
		if (ne_info_def.equals(model.getNe_info_def())) {
			this.ne_info_def = model.getNe_info_def();
			panel_chart.setDatas(model);
			panel_chart.refresh();
		} else {
			this.ne_info_def = model.getNe_info_def();
			panel_chart.setDatas(model);
			panel_chart.displayLineChart("", UtilDate.MINUTELY_FORMAT, UtilDate.MINUTELY_FORMAT, true);
		}

		NE_INFO_INDEX ne_info_index = model.getNe_info_index();
		if (ne_info_index != null) {
			setTitle(UtilString.format("{} [{}]", ne_info_def.getName(), ne_info_index.toString(ne_info_def)));
		} else {
			setTitle(UtilString.format("{}", ne_info_def.getName()));
		}
	}

	protected void openNeInfoIndexPopup() {
		NE_INFO_INDEX[] ne_info_indexs = model == null ? null : model.getNe_info_indexs();
		if (ne_info_indexs != null) {
			Point checker_size = buttonNeInfoIndex.getSize();
			PanelPopup4NeInfoIndex popup = new PanelPopup4NeInfoIndex(getShell(), ne_info_def, ne_info_indexs);
			Point popup_size = popup.getSize();
			Point location = buttonNeInfoIndex.toDisplay(checker_size.x - popup_size.x, checker_size.y + 1);
			Rectangle screen = buttonNeInfoIndex.getDisplay().getClientArea();
			if (screen.height < location.y + popup_size.y) {
				location = buttonNeInfoIndex.toDisplay(checker_size.x - popup_size.x, -(popup_size.y + 1));
			}
			popup.setLocation(location);
			popup.open();

			NE_INFO_INDEX ne_info_index = popup.getNe_info_index();
			if (ne_info_index != null) {
				ne_statistics_index_map.put(ne_info_def.getCode(), ne_info_index.getNe_info_index());
				if (model != null) {
					listener.queryNetworkViewNe(model.getModel4Ne().getNe_id(), true);
				}
			}
		}
	}

	protected void openChartItemCheckerPopup() {
		Point checker_size = buttonChartItem.getSize();
		PanelPopup4ChartItem popup = new PanelPopup4ChartItem(getShell(), panel_chart.getDataChart());
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
