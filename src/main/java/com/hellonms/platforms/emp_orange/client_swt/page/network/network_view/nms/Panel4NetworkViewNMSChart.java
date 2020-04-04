/**
 * Copyright 2016 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.client_swt.page.network.network_view.nms;

import java.util.Map;
import java.util.TreeMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

import com.hellonms.platforms.emp_onion.client_swt.data.DataFactory;
import com.hellonms.platforms.emp_onion.client_swt.data.chart.DataChartIf;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.Panel;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelChartIf;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelRound;
import com.hellonms.platforms.emp_orange.client_swt.page.DataBuilder4Orange.DATA_CHART_ORANGE;
import com.hellonms.platforms.emp_orange.client_swt.page.PanelBuilder4Orange.CHART_ORANGE;
import com.hellonms.platforms.emp_orange.client_swt.page.PanelFactory;
import com.hellonms.platforms.emp_orange.share.model.network.ne.Model4ResourceNMS;
import com.hellonms.platforms.emp_util.date.UtilDate;

/**
 * <p>
 * CPU Panel
 * </p>
 *
 * @since 1.6
 * @create 2016. 1. 11.
 * @modified 2016. 1. 11.
 * @author cchyun
 *
 */
public class Panel4NetworkViewNMSChart extends Panel {

	public static final String CPU = "CPU";

	public static final String MEM = "MEM";

	public static final String NET = "NET";

	private Map<Integer, Label> value_map = new TreeMap<Integer, Label>();

	private PanelChartIf panel_chart;

	private String category;

	public Panel4NetworkViewNMSChart(Composite parent, int style, String category) {
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
		panel_round.getContentComposite().setLayout(new FormLayout());

		DataChartIf dataChart = DataFactory.createDataChart(DATA_CHART_ORANGE.NMS_DETAIL_VIEW, category);

		panel_chart = PanelFactory.createPanelChart(CHART_ORANGE.NMS_DETAIL_VIEW, panel_round.getContentComposite(), SWT.NONE);
		FormData fd_panel_chart = new FormData();
		fd_panel_chart.top = new FormAttachment(0, 0);
		fd_panel_chart.left = new FormAttachment(0, 0);
		fd_panel_chart.right = new FormAttachment(100, 0);
		fd_panel_chart.bottom = new FormAttachment(100, 0);
		((Control) panel_chart).setLayoutData(fd_panel_chart);
		panel_chart.setDataChart(dataChart);
	}

	public void display(Model4ResourceNMS[] resource_nmss, String[] values) {
		for (int column = 0; column < value_map.size(); column++) {
			value_map.get(column).setText(column < values.length ? values[column] : "");
		}
		panel_chart.setDatas((Object) resource_nmss);
		panel_chart.displayLineChart("", UtilDate.MINUTELY_FORMAT, UtilDate.MINUTELY_FORMAT, true);
	}

}
