package com.hellonms.platforms.emp_onion.client_swt.data;

import java.util.LinkedList;

import com.hellonms.platforms.emp_onion.client_swt.data.DataBuilderIf.DataChartEnumIf;
import com.hellonms.platforms.emp_onion.client_swt.data.DataBuilderIf.DataComboEnumIf;
import com.hellonms.platforms.emp_onion.client_swt.data.DataBuilderIf.DataShelfEnumIf;
import com.hellonms.platforms.emp_onion.client_swt.data.DataBuilderIf.DataTableEnumIf;
import com.hellonms.platforms.emp_onion.client_swt.data.DataBuilderIf.DataTreeEnumIf;
import com.hellonms.platforms.emp_onion.client_swt.data.chart.DataChartIf;
import com.hellonms.platforms.emp_onion.client_swt.data.combo.DataComboIf;
import com.hellonms.platforms.emp_onion.client_swt.data.shelf.DataShelfIf;
import com.hellonms.platforms.emp_onion.client_swt.data.table.DataTableIf;
import com.hellonms.platforms.emp_onion.client_swt.data.tree.DataTreeIf;

public class DataFactory {

	private static LinkedList<DataBuilderIf> dataBuilderList = new LinkedList<DataBuilderIf>();

	public static DataChartIf createDataChart(DataChartEnumIf dataChartEnum, Object... datas) {
		for (DataBuilderIf dataBuilder : dataBuilderList) {
			DataChartIf dataChart = dataBuilder.createDataChart(dataChartEnum, datas);
			if (dataChart != null) {
				return dataChart;
			}
		}
		return null;
	}

	public static DataComboIf createDataCombo(DataComboEnumIf dataComboEnum) {
		for (DataBuilderIf dataBuilder : dataBuilderList) {
			DataComboIf dataCombo = dataBuilder.createDataCombo(dataComboEnum);
			if (dataCombo != null) {
				return dataCombo;
			}
		}
		return null;
	}

	public static DataShelfIf createDataShelf(DataShelfEnumIf dataShelfEnum) {
		for (DataBuilderIf dataBuilder : dataBuilderList) {
			DataShelfIf dataShelf = dataBuilder.createDataShelf(dataShelfEnum);
			if (dataShelf != null) {
				return dataShelf;
			}
		}
		return null;
	}

	public static DataTableIf createDataTable(DataTableEnumIf dataTableEnum, Object... datas) {
		for (DataBuilderIf dataBuilder : dataBuilderList) {
			DataTableIf dataTable = dataBuilder.createDataTable(dataTableEnum, datas);
			if (dataTable != null) {
				return dataTable;
			}
		}
		return null;
	}

	public static DataTreeIf createDataTree(DataTreeEnumIf dataTreeEnumIf) {
		for (DataBuilderIf dataBuilder : dataBuilderList) {
			DataTreeIf dataTree = dataBuilder.createDataTree(dataTreeEnumIf);
			if (dataTree != null) {
				return dataTree;
			}
		}
		return null;
	}

	public static void addDataBuilder(DataBuilderIf dataBuilderIf) {
		dataBuilderList.addFirst(dataBuilderIf);
	}

}
