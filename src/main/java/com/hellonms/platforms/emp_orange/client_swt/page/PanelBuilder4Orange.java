package com.hellonms.platforms.emp_orange.client_swt.page;

import org.eclipse.swt.widgets.Composite;

import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelChart4Nebula;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelChartIf;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelInputAt;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelInputAt.PANEL_INPUT_TYPE;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelInputAt.PanelInputListenerIf;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelTable;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelTable.PanelTableListenerIf;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelTableIf;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelTree;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelTree4Check;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelTreeIf;
import com.hellonms.platforms.emp_orange.client_swt.page.network.ne.PanelInput4DiscoveryNe;
import com.hellonms.platforms.emp_orange.client_swt.page.network.ne.PanelInput4Ne;
import com.hellonms.platforms.emp_orange.client_swt.page.network.ne_group.PanelInput4NeGroup;
import com.hellonms.platforms.emp_orange.client_swt.page.network.ne_info.PanelInput4NeInfo;
import com.hellonms.platforms.emp_orange.client_swt.page.network.ne_info.PanelTable4NeInfo;
import com.hellonms.platforms.emp_orange.client_swt.page.network.ne_session.icmp.PanelInput4DiscoveryNeSessionICMP;
import com.hellonms.platforms.emp_orange.client_swt.page.network.ne_session.icmp.PanelInput4NeSessionICMP;
import com.hellonms.platforms.emp_orange.client_swt.page.network.ne_session.snmp.PanelInput4DiscoveryNeSessionSNMP;
import com.hellonms.platforms.emp_orange.client_swt.page.network.ne_session.snmp.PanelInput4NeSessionSNMP;
import com.hellonms.platforms.emp_orange.client_swt.page.network.ne_statistics.PanelChart4NeStatistics;
import com.hellonms.platforms.emp_orange.client_swt.page.network.ne_statistics.PanelTable4NeStatistics;
import com.hellonms.platforms.emp_orange.client_swt.page.network.ne_threshold.PanelInput4NeThreshold;
import com.hellonms.platforms.emp_orange.client_swt.page.network.ne_threshold.PanelTable4NeThreshold;
import com.hellonms.platforms.emp_orange.client_swt.page.network.network_view.ne.PanelChart4etworkViewNe;
import com.hellonms.platforms.emp_orange.client_swt.page.network.network_view.nms.PanelChart4etworkViewNMS;
import com.hellonms.platforms.emp_orange.client_swt.page.network.network_view.nms.PanelTable4etworkViewNMS;
import com.hellonms.platforms.emp_orange.client_swt.page.security.user.DataTable4User;
import com.hellonms.platforms.emp_orange.client_swt.page.security.user.PanelInput4User;
import com.hellonms.platforms.emp_orange.client_swt.widget.page.PanelField4ArrayByte;
import com.hellonms.platforms.emp_orange.client_swt.widget.page.PanelField4Combo;
import com.hellonms.platforms.emp_orange.client_swt.widget.page.PanelField4Date;
import com.hellonms.platforms.emp_orange.client_swt.widget.page.PanelField4Integer32;
import com.hellonms.platforms.emp_orange.client_swt.widget.page.PanelField4Integer64;
import com.hellonms.platforms.emp_orange.client_swt.widget.page.PanelField4IpV4;
import com.hellonms.platforms.emp_orange.client_swt.widget.page.PanelField4IpV6;
import com.hellonms.platforms.emp_orange.client_swt.widget.page.PanelField4LabelText;
import com.hellonms.platforms.emp_orange.client_swt.widget.page.PanelField4String;
import com.hellonms.platforms.emp_orange.client_swt.widget.page.PanelField4Text;
import com.hellonms.platforms.emp_orange.client_swt.widget.page.PanelField4Unknown;
import com.hellonms.platforms.emp_orange.client_swt.widget.page.PanelFieldAt.PanelFieldListenerIf;
import com.hellonms.platforms.emp_orange.client_swt.widget.page.PanelFieldIf;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO_FIELD;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_TYPE;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.NE_SESSION_PROTOCOL;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.NE_SESSION_PROTOCOL_ORANGE;

/**
 * <p>
 * PanelBuilder4Orange
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class PanelBuilder4Orange implements PanelBuilderIf {

	public enum CHART_ORANGE implements ChartEnumIf {
		NMS_DETAIL_VIEW, //
		NE_DETAIL_VIEW, //
		NE_STATISTICS, //
		ALARM_STATISTICS, //
	}

	public enum INPUT_ORANGE implements InputEnumIf {
		NE_GROUP, //
		NE, //
		NE_SESSION, //
		DISCOVERY_NE, //
		DISCOVERY_NE_SESSION, //
		NE_INFO, //
		NE_THRESHOLD, //
		USER, //
	}

	public enum TABLE_ORANGE implements TableEnumIf {
		NMS_DETAIL_VIEW, //
		NE_DETAIL_VIEW, //
		DISCOVERY_NE_RESULT, //
		NE_INFO, //
		NE_STATISTICS, //
		NE_THRESHOLD, //
		ALARM_ACTIVE, //
		ALARM_HISTORY, //
		ALARM_STATISTICS, //
		EVENT, //
		USER, //
		OPERATION_LOG, //
		EVENT_CONSOLE, //
		ALARM_ACTIVE_CONSOLE, //
		ALARM_HISTORY_CONSOLE, //
	}

	public enum TREE_ORANGE implements TreeEnumIf {
		NETWORK_TREE, //
		NE_GROUP, //
		MANAGE_NE_GROUP, //
		NE, //
		NE_COPY, //
	}

	@Override
	public PanelChartIf createPanelChart(ChartEnumIf chartEnum, Composite parent, int style, Object... datas) {
		if (CHART_ORANGE.NMS_DETAIL_VIEW.equals(chartEnum)) {
			return new PanelChart4etworkViewNMS(parent, style);
		} else if (CHART_ORANGE.NE_DETAIL_VIEW.equals(chartEnum)) {
			return new PanelChart4etworkViewNe(parent, style);
		} else if (CHART_ORANGE.NE_STATISTICS.equals(chartEnum)) {
			return new PanelChart4NeStatistics(parent, style);
		} else if (CHART_ORANGE.ALARM_STATISTICS.equals(chartEnum)) {
			return new PanelChart4Nebula(parent, style);
		}
		return null;
	}

	@Override
	public PanelInputAt<?> createPanelInput(InputEnumIf inputEnum, Composite parent, int style, PANEL_INPUT_TYPE panelInputType, PanelInputListenerIf listener, Object... datas) {
		if (INPUT_ORANGE.NE_GROUP.equals(inputEnum)) {
			if (datas.length == 1 && datas[0] instanceof String[]) {
				return new PanelInput4NeGroup(parent, style, (String[]) datas[0], panelInputType, listener);
			}
		} else if (INPUT_ORANGE.NE.equals(inputEnum)) {
			if (datas.length == 1 && datas[0] instanceof String[]) {
				return new PanelInput4Ne(parent, style, (String[]) datas[0], panelInputType, listener);
			}
		} else if (INPUT_ORANGE.NE_SESSION.equals(inputEnum)) {
			if (datas.length == 1 && datas[0] instanceof NE_SESSION_PROTOCOL) {
				if (datas[0].equals(NE_SESSION_PROTOCOL_ORANGE.ICMP)) {
					return new PanelInput4NeSessionICMP(parent, style, panelInputType, listener);
				} else if (datas[0].equals(NE_SESSION_PROTOCOL_ORANGE.SNMP)) {
					return new PanelInput4NeSessionSNMP(parent, style, panelInputType, listener);
				}
			}
		} else if (INPUT_ORANGE.DISCOVERY_NE.equals(inputEnum)) {
			return new PanelInput4DiscoveryNe(parent, style, listener);
		} else if (INPUT_ORANGE.DISCOVERY_NE_SESSION.equals(inputEnum)) {
			if (datas.length == 1 && datas[0] instanceof NE_SESSION_PROTOCOL) {
				if (datas[0].equals(NE_SESSION_PROTOCOL_ORANGE.ICMP)) {
					return new PanelInput4DiscoveryNeSessionICMP(parent, style, listener);
				} else if (datas[0].equals(NE_SESSION_PROTOCOL_ORANGE.SNMP)) {
					return new PanelInput4DiscoveryNeSessionSNMP(parent, style, listener);
				}
			}
		} else if (INPUT_ORANGE.NE_INFO.equals(inputEnum)) {
			if (datas.length == 2 && datas[0] instanceof EMP_MODEL_NE_INFO && datas[1] instanceof Object[]) {
				EMP_MODEL_NE_INFO ne_info_def = (EMP_MODEL_NE_INFO) datas[0];
				Object[] ddd = (Object[]) datas[1];
				return new PanelInput4NeInfo(parent, style, panelInputType, listener, ne_info_def, ddd);
			}
		} else if (INPUT_ORANGE.NE_THRESHOLD.equals(inputEnum)) {
			if (datas.length == 3 && datas[0] instanceof EMP_MODEL_NE_INFO && datas[1] instanceof EMP_MODEL_NE_INFO_FIELD && datas[2] instanceof Object[]) {
				EMP_MODEL_NE_INFO ne_info_def = (EMP_MODEL_NE_INFO) datas[0];
				EMP_MODEL_NE_INFO_FIELD ne_info_field_def = (EMP_MODEL_NE_INFO_FIELD) datas[1];
				Object[] ddd = (Object[]) datas[2];
				return new PanelInput4NeThreshold(parent, style, panelInputType, listener, ne_info_def, ne_info_field_def, ddd);
			}
		} else if (INPUT_ORANGE.USER.equals(inputEnum)) {
			return new PanelInput4User(parent, style, panelInputType, listener);
		}
		return null;
	}

	@Override
	public PanelFieldIf createPanelFieldKey(Composite parent, EMP_MODEL_NE_INFO ne_info_def, EMP_MODEL_NE_INFO_FIELD ne_info_field_def, boolean enabled, PanelFieldListenerIf listener, Object... datas) {
		return new PanelField4LabelText(parent, ne_info_def, ne_info_field_def, enabled, listener);
	}

	@Override
	public PanelFieldIf createPanelFieldValue(Composite parent, EMP_MODEL_NE_INFO ne_info_def, EMP_MODEL_NE_INFO_FIELD ne_info_field_def, boolean enabled, PanelFieldListenerIf listener, Object... datas) {
		if (ne_info_field_def.getEnum_code() != 0) {
			return new PanelField4Combo(parent, ne_info_def, ne_info_field_def, enabled, listener);
		} else {
			final EMP_MODEL_TYPE neFieldType = ne_info_field_def.getType_local();
			switch (neFieldType) {
			case INT_32:
				return new PanelField4Integer32(parent, ne_info_def, ne_info_field_def, enabled, listener);
			case INT_64:
				return new PanelField4Integer64(parent, ne_info_def, ne_info_field_def, enabled, listener);
			case ARRAY_BYTE:
				return new PanelField4ArrayByte(parent, ne_info_def, ne_info_field_def, enabled, listener);
			case DATE:
				return new PanelField4Date(parent, ne_info_def, ne_info_field_def, enabled, listener);
			case IP_V4:
				return new PanelField4IpV4(parent, ne_info_def, ne_info_field_def, enabled, listener);
			case IP_V6:
				return new PanelField4IpV6(parent, ne_info_def, ne_info_field_def, enabled, listener);
			case STRING:
				return new PanelField4String(parent, ne_info_def, ne_info_field_def, enabled, listener);
			case TEXT:
				return new PanelField4Text(parent, ne_info_def, ne_info_field_def, enabled, listener);
			}
		}
		return new PanelField4Unknown(parent, ne_info_def, ne_info_field_def, enabled, listener);
	}

	@Override
	public PanelTableIf createPanelTable(TableEnumIf tableEnum, Composite parent, int style, int rowCount, PanelTableListenerIf listener, Object... datas) {
		if (TABLE_ORANGE.NMS_DETAIL_VIEW.equals(tableEnum)) {
			return new PanelTable4etworkViewNMS(parent, style, rowCount, listener);
		} else if (TABLE_ORANGE.NE_DETAIL_VIEW.equals(tableEnum)) {
			return new PanelTable(parent, style, rowCount, listener);
		} else if (TABLE_ORANGE.DISCOVERY_NE_RESULT.equals(tableEnum)) {
			return new PanelTable(parent, style, rowCount, listener);
		} else if (TABLE_ORANGE.NE_INFO.equals(tableEnum)) {
			if (datas.length == 2 && datas[0] instanceof EMP_MODEL_NE_INFO && datas[1] instanceof Object[]) {
				EMP_MODEL_NE_INFO ne_info_def = (EMP_MODEL_NE_INFO) datas[0];
				Object[] ddd = (Object[]) datas[1];
				return new PanelTable4NeInfo(parent, style, rowCount, listener, ne_info_def, ddd);
			}
		} else if (TABLE_ORANGE.NE_STATISTICS.equals(tableEnum)) {
			if (datas.length == 2 && datas[0] instanceof EMP_MODEL_NE_INFO && datas[1] instanceof Object[]) {
				EMP_MODEL_NE_INFO ne_info_def = (EMP_MODEL_NE_INFO) datas[0];
				Object[] ddd = (Object[]) datas[1];
				return new PanelTable4NeStatistics(parent, style, listener, ne_info_def, ddd);
			}
		} else if (TABLE_ORANGE.NE_THRESHOLD.equals(tableEnum)) {
			if (datas.length == 2 && datas[0] instanceof EMP_MODEL_NE_INFO && datas[1] instanceof Object[]) {
				EMP_MODEL_NE_INFO ne_info_def = (EMP_MODEL_NE_INFO) datas[0];
				Object[] ddd = (Object[]) datas[1];
				return new PanelTable4NeThreshold(parent, style, rowCount, listener, ne_info_def, ddd);
			}
		} else if (TABLE_ORANGE.ALARM_ACTIVE.equals(tableEnum)) {
			return new PanelTable(parent, style, rowCount, listener);
		} else if (TABLE_ORANGE.ALARM_HISTORY.equals(tableEnum)) {
			return new PanelTable(parent, style, rowCount, listener);
		} else if (TABLE_ORANGE.ALARM_STATISTICS.equals(tableEnum)) {
			return new PanelTable(parent, style, rowCount, listener);
		} else if (TABLE_ORANGE.EVENT.equals(tableEnum)) {
			return new PanelTable(parent, style, rowCount, listener);
		} else if (TABLE_ORANGE.USER.equals(tableEnum)) {
			return new PanelTable(parent, style, new DataTable4User());
		} else if (TABLE_ORANGE.OPERATION_LOG.equals(tableEnum)) {
			return new PanelTable(parent, style, rowCount, listener);
		} else if (TABLE_ORANGE.EVENT_CONSOLE.equals(tableEnum)) {
			return new PanelTable(parent, style);
		} else if (TABLE_ORANGE.ALARM_ACTIVE_CONSOLE.equals(tableEnum)) {
			return new PanelTable(parent, style);
		} else if (TABLE_ORANGE.ALARM_HISTORY_CONSOLE.equals(tableEnum)) {
			return new PanelTable(parent, style);
		}
		return null;
	}

	@Override
	public PanelTreeIf createPanelTree(TreeEnumIf treeEnum, Composite parent, int style, Object... datas) {
		if (TREE_ORANGE.NETWORK_TREE.equals(treeEnum)) {
			return new PanelTree(parent, style);
		} else if (TREE_ORANGE.NE_GROUP.equals(treeEnum)) {
			return new PanelTree(parent, style);
		} else if (TREE_ORANGE.MANAGE_NE_GROUP.equals(treeEnum)) {
			return new PanelTree4Check(parent, style);
		} else if (TREE_ORANGE.NE.equals(treeEnum)) {
			return new PanelTree(parent, style);
		} else if (TREE_ORANGE.NE_COPY.equals(treeEnum)) {
			return new PanelTree4Check(parent, style);
		}
		return null;
	}

}
