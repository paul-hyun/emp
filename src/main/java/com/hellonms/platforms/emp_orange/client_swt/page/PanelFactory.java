package com.hellonms.platforms.emp_orange.client_swt.page;

import java.util.LinkedList;

import org.eclipse.swt.widgets.Composite;

import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelChartIf;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelInputAt;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelInputAt.PANEL_INPUT_TYPE;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelInputAt.PanelInputListenerIf;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelTable.PanelTableListenerIf;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelTableIf;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelTreeIf;
import com.hellonms.platforms.emp_orange.client_swt.page.PanelBuilderIf.ChartEnumIf;
import com.hellonms.platforms.emp_orange.client_swt.page.PanelBuilderIf.InputEnumIf;
import com.hellonms.platforms.emp_orange.client_swt.page.PanelBuilderIf.TableEnumIf;
import com.hellonms.platforms.emp_orange.client_swt.page.PanelBuilderIf.TreeEnumIf;
import com.hellonms.platforms.emp_orange.client_swt.widget.page.PanelFieldAt.PanelFieldListenerIf;
import com.hellonms.platforms.emp_orange.client_swt.widget.page.PanelFieldIf;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO_FIELD;

public class PanelFactory {

	private static LinkedList<PanelBuilderIf> panelBuilderList = new LinkedList<PanelBuilderIf>();

	public static PanelChartIf createPanelChart(ChartEnumIf chartEnum, Composite parent, int style, Object... datas) {
		for (PanelBuilderIf panelBuilder : panelBuilderList) {
			PanelChartIf panelChart = panelBuilder.createPanelChart(chartEnum, parent, style, datas);
			if (panelChart != null) {
				return panelChart;
			}
		}
		return null;
	}

	public static PanelInputAt<?> createPanelInput(InputEnumIf inputEnum, Composite parent, int style, PANEL_INPUT_TYPE panelInputType, PanelInputListenerIf listener, Object... datas) {
		for (PanelBuilderIf panelBuilder : panelBuilderList) {
			PanelInputAt<?> panelInput = panelBuilder.createPanelInput(inputEnum, parent, style, panelInputType, listener, datas);
			if (panelInput != null) {
				return panelInput;
			}
		}
		return null;
	}

	public static PanelFieldIf createPanelFieldKey(Composite parent, EMP_MODEL_NE_INFO ne_info_def, EMP_MODEL_NE_INFO_FIELD ne_info_field_def, boolean enabled, PanelFieldListenerIf listener, Object... datas) {
		for (PanelBuilderIf panelBuilder : panelBuilderList) {
			PanelFieldIf panelField = panelBuilder.createPanelFieldKey(parent, ne_info_def, ne_info_field_def, enabled, listener, datas);
			if (panelField != null) {
				return panelField;
			}
		}
		return null;
	}

	public static PanelFieldIf createPanelFieldValue(Composite parent, EMP_MODEL_NE_INFO ne_info_def, EMP_MODEL_NE_INFO_FIELD ne_info_field_def, boolean enabled, PanelFieldListenerIf listener, Object... datas) {
		for (PanelBuilderIf panelBuilder : panelBuilderList) {
			PanelFieldIf panelField = panelBuilder.createPanelFieldValue(parent, ne_info_def, ne_info_field_def, enabled, listener, datas);
			if (panelField != null) {
				return panelField;
			}
		}
		return null;
	}

	public static PanelTableIf createPanelTable(TableEnumIf tableEnum, Composite parent, int style, int rowCount, PanelTableListenerIf listener, Object... datas) {
		for (PanelBuilderIf panelBuilder : panelBuilderList) {
			PanelTableIf panelTable = panelBuilder.createPanelTable(tableEnum, parent, style, rowCount, listener, datas);
			if (panelTable != null) {
				return panelTable;
			}
		}
		return null;
	}

	public static PanelTreeIf createPanelTree(TreeEnumIf treeEnum, Composite parent, int style, Object... datas) {
		for (PanelBuilderIf panelBuilder : panelBuilderList) {
			PanelTreeIf panelTree = panelBuilder.createPanelTree(treeEnum, parent, style, datas);
			if (panelTree != null) {
				return panelTree;
			}
		}
		return null;
	}

	public static void addPanelBuilder(PanelBuilderIf panelBuilder) {
		panelBuilderList.addFirst(panelBuilder);
	}

}
