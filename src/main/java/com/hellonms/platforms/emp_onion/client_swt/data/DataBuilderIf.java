package com.hellonms.platforms.emp_onion.client_swt.data;

import com.hellonms.platforms.emp_onion.client_swt.data.chart.DataChartIf;
import com.hellonms.platforms.emp_onion.client_swt.data.combo.DataComboIf;
import com.hellonms.platforms.emp_onion.client_swt.data.shelf.DataShelfIf;
import com.hellonms.platforms.emp_onion.client_swt.data.table.DataTableIf;
import com.hellonms.platforms.emp_onion.client_swt.data.tree.DataTreeIf;

public interface DataBuilderIf {

	public interface DataChartEnumIf {

	}

	public interface DataComboEnumIf {

	}

	public interface DataShelfEnumIf {

	}

	public interface DataTableEnumIf {

	}

	public interface DataTreeEnumIf {

	}

	public DataChartIf createDataChart(DataChartEnumIf dataChartEnum, Object... datas);

	public DataComboIf createDataCombo(DataComboEnumIf dataComboEnum);

	public DataShelfIf createDataShelf(DataShelfEnumIf dataShelfEnum);

	public DataTableIf createDataTable(DataTableEnumIf dataTableEnum, Object... datas);

	public DataTreeIf createDataTree(DataTreeEnumIf dataTreeEnum);

}
