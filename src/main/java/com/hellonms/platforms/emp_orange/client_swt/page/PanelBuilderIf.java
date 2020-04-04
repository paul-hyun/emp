package com.hellonms.platforms.emp_orange.client_swt.page;

import org.eclipse.swt.widgets.Composite;

import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelChartIf;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelInputAt;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelInputAt.PANEL_INPUT_TYPE;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelInputAt.PanelInputListenerIf;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelTable.PanelTableListenerIf;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelTableIf;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelTreeIf;
import com.hellonms.platforms.emp_orange.client_swt.widget.page.PanelFieldAt.PanelFieldListenerIf;
import com.hellonms.platforms.emp_orange.client_swt.widget.page.PanelFieldIf;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO_FIELD;

/**
 * <p>
 * PanelBuilderIf
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public interface PanelBuilderIf {

	public interface ChartEnumIf {

	}

	public interface InputEnumIf {

	}

	public interface TableEnumIf {

	}

	public interface TreeEnumIf {

	}

	public PanelChartIf createPanelChart(ChartEnumIf chartEnum, Composite parent, int style, Object... datas);

	public PanelInputAt<?> createPanelInput(InputEnumIf inputEnum, Composite parent, int style, PANEL_INPUT_TYPE panelInputType, PanelInputListenerIf listener, Object... datas);

	public PanelFieldIf createPanelFieldKey(Composite parent, EMP_MODEL_NE_INFO ne_info_def, EMP_MODEL_NE_INFO_FIELD ne_info_field_def, boolean enabled, PanelFieldListenerIf listener, Object... datas);

	public PanelFieldIf createPanelFieldValue(Composite parent, EMP_MODEL_NE_INFO ne_info_def, EMP_MODEL_NE_INFO_FIELD ne_info_field_def, boolean enabled, PanelFieldListenerIf listener, Object... datas);

	public PanelTableIf createPanelTable(TableEnumIf tableEnum, Composite parent, int style, int rowCount, PanelTableListenerIf listener, Object... datas);

	public PanelTreeIf createPanelTree(TreeEnumIf treeEnum, Composite parent, int style, Object... datas);

}
