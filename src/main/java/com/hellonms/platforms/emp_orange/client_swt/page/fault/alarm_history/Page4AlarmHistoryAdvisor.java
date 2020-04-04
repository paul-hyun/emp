package com.hellonms.platforms.emp_orange.client_swt.page.fault.alarm_history;

import java.util.Date;

import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_onion.client_swt.data.DataFactory;
import com.hellonms.platforms.emp_onion.client_swt.data.table.DataTableIf;
import com.hellonms.platforms.emp_onion.share.model.TablePageConfig;
import com.hellonms.platforms.emp_orange.client_swt.driver.Driver4OrangeClientSwt;
import com.hellonms.platforms.emp_orange.client_swt.driver.Driver4OrangeClientSwt.Driver4OrangeClientSwtTaskIf;
import com.hellonms.platforms.emp_orange.client_swt.page.DataBuilder4Orange.DATA_TABLE_ORANGE;
import com.hellonms.platforms.emp_orange.client_swt.page.network.network_tree.ModelClient4NetworkTree.NODE;
import com.hellonms.platforms.emp_orange.client_swt.servlet.Invoker4OrangeClientSwtIf;
import com.hellonms.platforms.emp_orange.client_swt.servlet.Invoker4OrangeClientSwtReqeust;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_EVENT;
import com.hellonms.platforms.emp_orange.share.model.fault.event.Model4Alarm;
import com.hellonms.platforms.emp_orange.share.model.fault.event.SEVERITY;
import com.hellonms.platforms.emp_util.excel.UtilExcel;

/**
 * <p>
 * Page4AlarmHistoryAdvisor
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class Page4AlarmHistoryAdvisor {

	@SuppressWarnings("unchecked")
	public TablePageConfig<Model4Alarm> queryListAlarmHistory(final NODE node, final EMP_MODEL_EVENT event_def, final SEVERITY severity, final Date fromDate, final Date toDate, final int startNo, final int count) throws EmpException {
		return (TablePageConfig<Model4Alarm>) Driver4OrangeClientSwt.getInstance().execute(new Driver4OrangeClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeClientSwtIf invoker, Invoker4OrangeClientSwtReqeust request) throws EmpException {
				int event_code = event_def == null ? 0 : event_def.getCode();
				if (node.isNeGroup()) {
					return invoker.queryListAlarmHistoryByNeGroup(request, node.getId(), event_code, severity, fromDate, toDate, startNo, count);
				} else if (node.isNe()) {
					return invoker.queryListAlarmHistoryByNe(request, node.getId(), event_code, severity, fromDate, toDate, startNo, count);
				} else {
					return new TablePageConfig<Model4Alarm>(startNo, count, new Model4Alarm[0], 0);
				}
			}
		});
	}

	protected byte[] saveExcelAlarmHistory(final NODE node, final EMP_MODEL_EVENT event_def, final SEVERITY severity, final Date fromDate, final Date toDate) throws EmpException {
		@SuppressWarnings("unchecked")
		TablePageConfig<Model4Alarm> pageConfig = (TablePageConfig<Model4Alarm>) Driver4OrangeClientSwt.getInstance().execute(new Driver4OrangeClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeClientSwtIf invoker, Invoker4OrangeClientSwtReqeust request) throws EmpException {
				int event_code = event_def == null ? 0 : event_def.getCode();
				if (node.isNeGroup()) {
					return invoker.queryListAlarmHistoryByNeGroup(request, node.getId(), event_code, severity, fromDate, toDate, 0, 999999);
				} else if (node.isNe()) {
					return invoker.queryListAlarmHistoryByNe(request, node.getId(), event_code, severity, fromDate, toDate, 0, 999999);
				} else {
					return new TablePageConfig<Model4Alarm>(0, 999999, new Model4Alarm[0], 0);
				}
			}
		});
		DataTableIf data_table = DataFactory.createDataTable(DATA_TABLE_ORANGE.ALARM_HISTORY);
		if (data_table != null) {
			data_table.setDatas((Object) pageConfig.values);
			return UtilExcel.saveExcel(data_table, "ALARM_HISTORY", 10000);
		} else {
			return new byte[] {};
		}
	}

}
