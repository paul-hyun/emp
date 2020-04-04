package com.hellonms.platforms.emp_orange.client_swt.page.network.ne_statistics;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.widgets.Composite;

import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelTable;
import com.hellonms.platforms.emp_orange.client_swt.model_display.ModelDisplay4NeStatistics;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO;

/**
 * <p>
 * PanelInput4NeStatistics
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class PanelTable4NeStatistics extends PanelTable implements PanelInput4NeStatisticsIf {

	public PanelTable4NeStatistics(Composite parent, int style, PanelTableListenerIf listener, EMP_MODEL_NE_INFO ne_info_def, Object[] datas) {
		super(parent, style, 0, listener);

		this.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent e) {
			}
		});
	}

	@Override
	public void display(ModelDisplay4NeStatistics modelDisplay4NeStatistics) {
		getDataTable().setDatas(modelDisplay4NeStatistics);
	}

}
