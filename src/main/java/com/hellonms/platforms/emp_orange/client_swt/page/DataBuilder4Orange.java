package com.hellonms.platforms.emp_orange.client_swt.page;

import com.hellonms.platforms.emp_onion.client_swt.data.DataBuilderIf;
import com.hellonms.platforms.emp_onion.client_swt.data.chart.DataChartIf;
import com.hellonms.platforms.emp_onion.client_swt.data.combo.DataComboIf;
import com.hellonms.platforms.emp_onion.client_swt.data.shelf.DataShelfIf;
import com.hellonms.platforms.emp_onion.client_swt.data.table.DataTableIf;
import com.hellonms.platforms.emp_onion.client_swt.data.tree.DataTreeIf;
import com.hellonms.platforms.emp_orange.client_swt.page.fault.alarm_active.DataCombo4AlarmActiveSeverity;
import com.hellonms.platforms.emp_orange.client_swt.page.fault.alarm_active.DataTable4AlarmActive;
import com.hellonms.platforms.emp_orange.client_swt.page.fault.alarm_active.DataTable4AlarmActiveConsole;
import com.hellonms.platforms.emp_orange.client_swt.page.fault.alarm_history.DataCombo4AlarmHistoryCode;
import com.hellonms.platforms.emp_orange.client_swt.page.fault.alarm_history.DataCombo4AlarmHistorySeverity;
import com.hellonms.platforms.emp_orange.client_swt.page.fault.alarm_history.DataTable4AlarmHistory;
import com.hellonms.platforms.emp_orange.client_swt.page.fault.alarm_history.DataTable4AlarmHistoryConsole;
import com.hellonms.platforms.emp_orange.client_swt.page.fault.alarm_statistics.DataChart4AlarmStatistics;
import com.hellonms.platforms.emp_orange.client_swt.page.fault.alarm_statistics.DataCombo4AlarmStatisticsItem;
import com.hellonms.platforms.emp_orange.client_swt.page.fault.alarm_statistics.DataCombo4AlarmStatisticsType;
import com.hellonms.platforms.emp_orange.client_swt.page.fault.alarm_statistics.DataTable4AlarmStatistics;
import com.hellonms.platforms.emp_orange.client_swt.page.fault.event.DataCombo4EventCode;
import com.hellonms.platforms.emp_orange.client_swt.page.fault.event.DataCombo4EventSeverity;
import com.hellonms.platforms.emp_orange.client_swt.page.fault.event.DataTable4Event;
import com.hellonms.platforms.emp_orange.client_swt.page.fault.event.DataTable4EventConsole;
import com.hellonms.platforms.emp_orange.client_swt.page.network.ne.DataCombo4NeType;
import com.hellonms.platforms.emp_orange.client_swt.page.network.ne.DataTable4DiscoveryNeResult;
import com.hellonms.platforms.emp_orange.client_swt.page.network.ne.DataTree4Ne;
import com.hellonms.platforms.emp_orange.client_swt.page.network.ne_group.DataTree4NeGroup;
import com.hellonms.platforms.emp_orange.client_swt.page.network.ne_info.DataTable4NeInfo;
import com.hellonms.platforms.emp_orange.client_swt.page.network.ne_session.DataCombo4SnmpVersion;
import com.hellonms.platforms.emp_orange.client_swt.page.network.ne_session.DataCombo4Timeout;
import com.hellonms.platforms.emp_orange.client_swt.page.network.ne_statistics.DataChart4NeStatistics;
import com.hellonms.platforms.emp_orange.client_swt.page.network.ne_statistics.DataCombo4NeStatisticsCode;
import com.hellonms.platforms.emp_orange.client_swt.page.network.ne_statistics.DataCombo4NeStatisticsIndex;
import com.hellonms.platforms.emp_orange.client_swt.page.network.ne_statistics.DataCombo4NeStatisticsType;
import com.hellonms.platforms.emp_orange.client_swt.page.network.ne_statistics.DataTable4NeStatistics;
import com.hellonms.platforms.emp_orange.client_swt.page.network.ne_threshold.DataTable4NeThreshold;
import com.hellonms.platforms.emp_orange.client_swt.page.network.network_tree.DataTree4NetworkTree;
import com.hellonms.platforms.emp_orange.client_swt.page.network.network_view.ne.DataChart4NetworkViewNe;
import com.hellonms.platforms.emp_orange.client_swt.page.network.network_view.ne.DataTable4NetworkViewNe;
import com.hellonms.platforms.emp_orange.client_swt.page.network.network_view.nms.DataChart4NetworkViewNMSCpu;
import com.hellonms.platforms.emp_orange.client_swt.page.network.network_view.nms.DataChart4NetworkViewNMSMem;
import com.hellonms.platforms.emp_orange.client_swt.page.network.network_view.nms.DataChart4NetworkViewNMSNet;
import com.hellonms.platforms.emp_orange.client_swt.page.network.network_view.nms.DataTable4NetworkViewNMSPartition;
import com.hellonms.platforms.emp_orange.client_swt.page.security.login.DataCombo4Language;
import com.hellonms.platforms.emp_orange.client_swt.page.security.operation_log.DataCombo4AllString;
import com.hellonms.platforms.emp_orange.client_swt.page.security.operation_log.DataCombo4Result;
import com.hellonms.platforms.emp_orange.client_swt.page.security.operation_log.DataTable4OperationLog;
import com.hellonms.platforms.emp_orange.client_swt.page.security.user.DataCombo4UserGroup;
import com.hellonms.platforms.emp_orange.client_swt.page.security.user.DataTree4ManageNeGroup;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO;

public class DataBuilder4Orange implements DataBuilderIf {

	public enum DATA_CHART_ORANGE implements DataChartEnumIf {
		NMS_DETAIL_VIEW, //
		NE_DETAIL_VIEW, //
		NE_STATISTICS, //
		ALARM_STATISTICS, //
	}

	public enum DATA_COMBO_ORANGE implements DataComboEnumIf {
		NE_TYPE, //
		NE_SESSION_SNMP_VERSION, //
		NE_SESSION_TIMEOUT, //
		NE_STATISTICS_CODE, //
		NE_STATISTICS_INDEX, //
		NE_STATISTICS_TYPE, //
		ALARM_ACTIVE_SEVERITY, //
		ALARM_HISTORY_CODE, //
		ALARM_HISTORY_SEVERITY, //
		ALARM_STATISTICS_ITEM, //
		ALARM_STATISTICS_TYPE, //
		EVENT_CODE, //
		EVENT_SEVERITY, //
		LOGIN_LANGUAGE, //
		OPERATION_LOG_SERVICE, //
		OPERATION_LOG_FUNCTION, //
		OPERATION_LOG_OPERATION, //
		OPERATION_LOG_RESULT, //
		USER_GROUP, //
	}

	public enum DATA_SHELF_ORANGE implements DataComboEnumIf {
	}

	public enum DATA_TABLE_ORANGE implements DataTableEnumIf {
		NMS_DETAIL_VIEW, //
		NE_DETAIL_VIEW, //
		DISCOVERY_NE_RESULT, //
		NE_INFO, //
		NE_STATISTICS, //
		NE_THRESHOLD, //
		ALARM_ACTIVE, //
		ALARM_ACTIVE_CONSOLE, //
		ALARM_HISTORY, //
		ALARM_HISTORY_CONSOLE, //
		ALARM_STATISTICS, //
		EVENT, //
		EVENT_CONSOLE, //
		OPERATION_LOG, //
	}

	public enum DATA_TREE_ORANGE implements DataTreeEnumIf {
		NETWORK_TREE, //
		NE_GROUP, //
		MANAGE_NE_GROUP, //
		NE, //
	}

	@Override
	public DataChartIf createDataChart(DataChartEnumIf dataChartEnum, Object... datas) {
		if (DATA_CHART_ORANGE.NMS_DETAIL_VIEW.equals(dataChartEnum)) {
			if (datas.length == 1 && datas[0] instanceof String) {
				String type = (String) datas[0];
				switch (type) {
				case "CPU":
					return new DataChart4NetworkViewNMSCpu();
				case "MEM":
					return new DataChart4NetworkViewNMSMem();
				case "NET":
					return new DataChart4NetworkViewNMSNet();
				default:
					return null;
				}
			}
		} else if (DATA_CHART_ORANGE.NE_DETAIL_VIEW.equals(dataChartEnum)) {
			if (datas.length == 1 && datas[0] instanceof EMP_MODEL_NE_INFO) {
				EMP_MODEL_NE_INFO ne_info_def = (EMP_MODEL_NE_INFO) datas[0];
				return new DataChart4NetworkViewNe(ne_info_def);
			}
		} else if (DATA_CHART_ORANGE.NE_STATISTICS.equals(dataChartEnum)) {
			if (datas.length == 1 && datas[0] instanceof EMP_MODEL_NE_INFO) {
				EMP_MODEL_NE_INFO ne_info_def = (EMP_MODEL_NE_INFO) datas[0];
				return new DataChart4NeStatistics(ne_info_def);
			}
		} else if (DATA_CHART_ORANGE.ALARM_STATISTICS.equals(dataChartEnum)) {
			return new DataChart4AlarmStatistics();
		}
		return null;
	}

	@Override
	public DataComboIf createDataCombo(DataComboEnumIf dataComboEnum) {
		if (DATA_COMBO_ORANGE.NE_TYPE.equals(dataComboEnum)) {
			return new DataCombo4NeType();
		} else if (DATA_COMBO_ORANGE.NE_SESSION_SNMP_VERSION.equals(dataComboEnum)) {
			return new DataCombo4SnmpVersion();
		} else if (DATA_COMBO_ORANGE.NE_SESSION_TIMEOUT.equals(dataComboEnum)) {
			return new DataCombo4Timeout(10);
		} else if (DATA_COMBO_ORANGE.NE_STATISTICS_CODE.equals(dataComboEnum)) {
			return new DataCombo4NeStatisticsCode();
		} else if (DATA_COMBO_ORANGE.NE_STATISTICS_INDEX.equals(dataComboEnum)) {
			return new DataCombo4NeStatisticsIndex();
		} else if (DATA_COMBO_ORANGE.NE_STATISTICS_TYPE.equals(dataComboEnum)) {
			return new DataCombo4NeStatisticsType();
		} else if (DATA_COMBO_ORANGE.ALARM_ACTIVE_SEVERITY.equals(dataComboEnum)) {
			return new DataCombo4AlarmActiveSeverity();
		} else if (DATA_COMBO_ORANGE.ALARM_HISTORY_CODE.equals(dataComboEnum)) {
			return new DataCombo4AlarmHistoryCode();
		} else if (DATA_COMBO_ORANGE.ALARM_HISTORY_SEVERITY.equals(dataComboEnum)) {
			return new DataCombo4AlarmHistorySeverity();
		} else if (DATA_COMBO_ORANGE.ALARM_STATISTICS_ITEM.equals(dataComboEnum)) {
			return new DataCombo4AlarmStatisticsItem();
		} else if (DATA_COMBO_ORANGE.ALARM_STATISTICS_TYPE.equals(dataComboEnum)) {
			return new DataCombo4AlarmStatisticsType();
		} else if (DATA_COMBO_ORANGE.EVENT_CODE.equals(dataComboEnum)) {
			return new DataCombo4EventCode();
		} else if (DATA_COMBO_ORANGE.EVENT_SEVERITY.equals(dataComboEnum)) {
			return new DataCombo4EventSeverity();
		} else if (DATA_COMBO_ORANGE.LOGIN_LANGUAGE.equals(dataComboEnum)) {
			return new DataCombo4Language();
		} else if (DATA_COMBO_ORANGE.OPERATION_LOG_SERVICE.equals(dataComboEnum)) {
			return new DataCombo4AllString();
		} else if (DATA_COMBO_ORANGE.OPERATION_LOG_FUNCTION.equals(dataComboEnum)) {
			return new DataCombo4AllString();
		} else if (DATA_COMBO_ORANGE.OPERATION_LOG_OPERATION.equals(dataComboEnum)) {
			return new DataCombo4AllString();
		} else if (DATA_COMBO_ORANGE.OPERATION_LOG_RESULT.equals(dataComboEnum)) {
			return new DataCombo4Result();
		} else if (DATA_COMBO_ORANGE.USER_GROUP.equals(dataComboEnum)) {
			return new DataCombo4UserGroup();
		}
		return null;
	}

	@Override
	public DataShelfIf createDataShelf(DataShelfEnumIf dataShelfEnum) {
		return null;
	}

	@Override
	public DataTableIf createDataTable(DataTableEnumIf dataTableEnum, Object... datas) {
		if (DATA_TABLE_ORANGE.NMS_DETAIL_VIEW.equals(dataTableEnum)) {
			if (datas.length == 1 && datas[0] instanceof String) {
				String type = (String) datas[0];
				switch (type) {
				case "PARTITION":
					return new DataTable4NetworkViewNMSPartition();
				default:
					return null;
				}
			}
		} else if (DATA_TABLE_ORANGE.NE_DETAIL_VIEW.equals(dataTableEnum)) {
			if (datas.length == 1 && datas[0] instanceof EMP_MODEL_NE_INFO) {
				EMP_MODEL_NE_INFO ne_info_def = (EMP_MODEL_NE_INFO) datas[0];
				return new DataTable4NetworkViewNe(ne_info_def);
			}
		} else if (DATA_TABLE_ORANGE.DISCOVERY_NE_RESULT.equals(dataTableEnum)) {
			return new DataTable4DiscoveryNeResult();
		} else if (DATA_TABLE_ORANGE.NE_INFO.equals(dataTableEnum)) {
			if (datas.length == 1 && datas[0] instanceof EMP_MODEL_NE_INFO) {
				EMP_MODEL_NE_INFO ne_info_def = (EMP_MODEL_NE_INFO) datas[0];
				return new DataTable4NeInfo(ne_info_def);
			}
		} else if (DATA_TABLE_ORANGE.NE_STATISTICS.equals(dataTableEnum)) {
			if (datas.length == 1 && datas[0] instanceof EMP_MODEL_NE_INFO) {
				EMP_MODEL_NE_INFO ne_info_def = (EMP_MODEL_NE_INFO) datas[0];
				return new DataTable4NeStatistics(ne_info_def);
			}
		} else if (DATA_TABLE_ORANGE.NE_THRESHOLD.equals(dataTableEnum)) {
			if (datas.length == 1 && datas[0] instanceof EMP_MODEL_NE_INFO) {
				EMP_MODEL_NE_INFO ne_info_def = (EMP_MODEL_NE_INFO) datas[0];
				return new DataTable4NeThreshold(ne_info_def);
			}
		} else if (DATA_TABLE_ORANGE.ALARM_ACTIVE.equals(dataTableEnum)) {
			return new DataTable4AlarmActive();
		} else if (DATA_TABLE_ORANGE.ALARM_ACTIVE_CONSOLE.equals(dataTableEnum)) {
			return new DataTable4AlarmActiveConsole();
		} else if (DATA_TABLE_ORANGE.ALARM_HISTORY.equals(dataTableEnum)) {
			return new DataTable4AlarmHistory();
		} else if (DATA_TABLE_ORANGE.ALARM_HISTORY_CONSOLE.equals(dataTableEnum)) {
			return new DataTable4AlarmHistoryConsole();
		} else if (DATA_TABLE_ORANGE.ALARM_STATISTICS.equals(dataTableEnum)) {
			return new DataTable4AlarmStatistics();
		} else if (DATA_TABLE_ORANGE.EVENT.equals(dataTableEnum)) {
			return new DataTable4Event();
		} else if (DATA_TABLE_ORANGE.EVENT_CONSOLE.equals(dataTableEnum)) {
			return new DataTable4EventConsole();
		} else if (DATA_TABLE_ORANGE.OPERATION_LOG.equals(dataTableEnum)) {
			return new DataTable4OperationLog();
		}
		return null;
	}

	@Override
	public DataTreeIf createDataTree(DataTreeEnumIf dataTreeEnumIf) {
		if (DATA_TREE_ORANGE.NETWORK_TREE.equals(dataTreeEnumIf)) {
			return new DataTree4NetworkTree();
		} else if (DATA_TREE_ORANGE.NE_GROUP.equals(dataTreeEnumIf)) {
			return new DataTree4NeGroup();
		} else if (DATA_TREE_ORANGE.MANAGE_NE_GROUP.equals(dataTreeEnumIf)) {
			return new DataTree4ManageNeGroup();
		} else if (DATA_TREE_ORANGE.NE.equals(dataTreeEnumIf)) {
			return new DataTree4Ne();
		}
		return null;
	}

}
