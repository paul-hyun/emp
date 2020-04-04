package com.hellonms.platforms.emp_orange.client_swt.page.network.ne_statistics;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.hellonms.platforms.emp_onion.client_swt.data.DataFactory;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelChartIf;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelTable;
import com.hellonms.platforms.emp_orange.client_swt.page.DataBuilder4Orange.DATA_CHART_ORANGE;
import com.hellonms.platforms.emp_orange.client_swt.page.DataBuilder4Orange.DATA_TABLE_ORANGE;
import com.hellonms.platforms.emp_orange.client_swt.page.PanelBuilder4Orange.CHART_ORANGE;
import com.hellonms.platforms.emp_orange.client_swt.page.PanelBuilder4Orange.TABLE_ORANGE;
import com.hellonms.platforms.emp_orange.client_swt.page.PanelFactory;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO;

/**
 * <p>
 * Panel4NeStatisticsData
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class Panel4NeStatisticsData extends Panel4NeStatisticsDataAt {

	public Panel4NeStatisticsData(Composite parent, int style, Panel4NeStatisticsDataListenerIf listener) {
		super(parent, style, listener);
	}

	@Override
	protected PanelInput4NeStatisticsIf createPanelTable4NeStatistics(Composite parent, EMP_MODEL_NE_INFO ne_info_def, Object... datas) {
		PanelRound4NeStatistics panelRound4NeStatistics = new PanelRound4NeStatistics(parent, SWT.NONE);
		panelRound4NeStatistics.panelInput4NeStatistics = (PanelInput4NeStatisticsIf) PanelFactory.createPanelTable(TABLE_ORANGE.NE_STATISTICS, panelRound4NeStatistics.getContentComposite(), SWT.BORDER | SWT.FULL_SELECTION, 0, null, ne_info_def, datas);
		((PanelTable) panelRound4NeStatistics.panelInput4NeStatistics).setDataTable(DataFactory.createDataTable(DATA_TABLE_ORANGE.NE_STATISTICS, ne_info_def));
		((PanelTable) panelRound4NeStatistics.panelInput4NeStatistics).addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
			}
		});
		return panelRound4NeStatistics;
	}

	@Override
	protected PanelInput4NeStatisticsIf createPanelChart4NeStatistics(Composite parent, EMP_MODEL_NE_INFO ne_info_def, Object... datas) {
		PanelRound4NeStatistics panelRound4NeStatistics = new PanelRound4NeStatistics(parent, SWT.NONE);
		panelRound4NeStatistics.panelInput4NeStatistics = (PanelInput4NeStatisticsIf) PanelFactory.createPanelChart(CHART_ORANGE.NE_STATISTICS, panelRound4NeStatistics.getContentComposite(), SWT.NONE, ne_info_def, datas);
		((PanelChartIf) panelRound4NeStatistics.panelInput4NeStatistics).setDataChart(DataFactory.createDataChart(DATA_CHART_ORANGE.NE_STATISTICS, ne_info_def));
		return panelRound4NeStatistics;
	}

}
