package com.hellonms.platforms.emp_orange.client_swt.page.fault.alarm_statistics;

import java.util.Date;

import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_onion.client_swt.data.DataFactory;
import com.hellonms.platforms.emp_onion.client_swt.data.table.DataTableIf;
import com.hellonms.platforms.emp_onion.share.model.STATISTICS_TYPE;
import com.hellonms.platforms.emp_orange.client_swt.driver.Driver4OrangeClientSwt;
import com.hellonms.platforms.emp_orange.client_swt.driver.Driver4OrangeClientSwt.Driver4OrangeClientSwtTaskIf;
import com.hellonms.platforms.emp_orange.client_swt.page.DataBuilder4Orange.DATA_TABLE_ORANGE;
import com.hellonms.platforms.emp_orange.client_swt.page.network.network_tree.ModelClient4NetworkTree.NODE;
import com.hellonms.platforms.emp_orange.client_swt.servlet.Invoker4OrangeClientSwtIf;
import com.hellonms.platforms.emp_orange.client_swt.servlet.Invoker4OrangeClientSwtReqeust;
import com.hellonms.platforms.emp_orange.share.model.fault.event.Model4AlarmStatistics;
import com.hellonms.platforms.emp_orange.share.model.fault.event.Model4AlarmStatistics.ITEM;
import com.hellonms.platforms.emp_util.excel.UtilExcel;

/**
 * <p>
 * Page4AlarmStatisticsAdvisor
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class Page4AlarmStatisticsAdvisor {

	public Model4AlarmStatistics[] queryListAlarmStatistics(final Date fromDate, final Date toDate, final NODE node, final ITEM item, final STATISTICS_TYPE statisticsType) throws EmpException {
		return (Model4AlarmStatistics[]) Driver4OrangeClientSwt.getInstance().execute(new Driver4OrangeClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeClientSwtIf invoker, Invoker4OrangeClientSwtReqeust request) throws EmpException {
				if (node.isNeGroup()) {
					return invoker.queryListAlarmStatisticsByNeGroup(request, fromDate, toDate, node.getId(), item, statisticsType);
				} else if (node.isNe()) {
					return invoker.queryListAlarmStatisticsByNe(request, fromDate, toDate, node.getId(), item, statisticsType);
				} else {
					return new Model4AlarmStatistics[0];
				}
			}
		});
	}

	protected byte[] saveExcelAlarmStatistics(final NODE node, final ITEM item, final STATISTICS_TYPE statisticsType, final Date fromDate, final Date toDate) throws EmpException {
		Model4AlarmStatistics[] statisticss = (Model4AlarmStatistics[]) Driver4OrangeClientSwt.getInstance().execute(new Driver4OrangeClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeClientSwtIf invoker, Invoker4OrangeClientSwtReqeust request) throws EmpException {
				if (node.isNeGroup()) {
					return invoker.queryListAlarmStatisticsByNeGroup(request, fromDate, toDate, node.getId(), item, statisticsType);
				} else if (node.isNe()) {
					return invoker.queryListAlarmStatisticsByNe(request, fromDate, toDate, node.getId(), item, statisticsType);
				} else {
					return new Model4AlarmStatistics[0];
				}
			}
		});
		DataTableIf data_table = DataFactory.createDataTable(DATA_TABLE_ORANGE.ALARM_STATISTICS);
		if (data_table != null) {
			data_table.setDatas(item, statisticsType, statisticss);
			return UtilExcel.saveExcel(data_table, "ALARM_STATISTICS", 10000);
		} else {
			return new byte[] {};
		}
	}

}
