package com.hellonms.platforms.emp_onion.client_swt.data;

import com.hellonms.platforms.emp_onion.client_swt.data.chart.DataChartIf;
import com.hellonms.platforms.emp_onion.client_swt.data.combo.DataComboIf;
import com.hellonms.platforms.emp_onion.client_swt.data.shelf.DataShelfIf;
import com.hellonms.platforms.emp_onion.client_swt.data.table.DataTableIf;
import com.hellonms.platforms.emp_onion.client_swt.data.tree.DataTreeIf;
import com.hellonms.platforms.emp_orange.client_swt.page.PanelBuilderIf.TreeEnumIf;

public class DataBuilder4Onion implements DataBuilderIf {

	public enum DATA_CHART_ONION implements DataChartEnumIf {
	}

	public enum DATA_COMBO_ONION implements DataComboEnumIf {
	}

	public enum DATA_SHELF_ONION implements DataComboEnumIf {
	}

	public enum DATA_TABLE_ONION implements DataTableEnumIf {
	}

	public enum DATA_TREE_ONION implements TreeEnumIf {
	}

	@Override
	public DataChartIf createDataChart(DataChartEnumIf dataChartEnum, Object... datas) {
		return null;
	}

	@Override
	public DataComboIf createDataCombo(DataComboEnumIf dataComboEnum) {
		return null;
	}

	@Override
	public DataShelfIf createDataShelf(DataShelfEnumIf dataShelfEnum) {
		return null;
	}

	@Override
	public DataTableIf createDataTable(DataTableEnumIf dataTableEnum, Object... datas) {
		return null;
	}

	@Override
	public DataTreeIf createDataTree(DataTreeEnumIf dataTreeEnumIf) {
		return null;
	}

}
