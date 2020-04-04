package com.hellonms.platforms.emp_orange.client_swt.page.security.operation_log;

import java.util.Date;

import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_core.share.operation.OPERATION_CODE;
import com.hellonms.platforms.emp_onion.client_swt.data.DataFactory;
import com.hellonms.platforms.emp_onion.client_swt.data.table.DataTableIf;
import com.hellonms.platforms.emp_onion.share.model.TablePageConfig;
import com.hellonms.platforms.emp_orange.client_swt.driver.Driver4OrangeClientSwt;
import com.hellonms.platforms.emp_orange.client_swt.driver.Driver4OrangeClientSwt.Driver4OrangeClientSwtTaskIf;
import com.hellonms.platforms.emp_orange.client_swt.page.DataBuilder4Orange.DATA_TABLE_ORANGE;
import com.hellonms.platforms.emp_orange.client_swt.page.network.network_tree.ModelClient4NetworkTree.NODE;
import com.hellonms.platforms.emp_orange.client_swt.servlet.Invoker4OrangeClientSwtIf;
import com.hellonms.platforms.emp_orange.client_swt.servlet.Invoker4OrangeClientSwtReqeust;
import com.hellonms.platforms.emp_orange.share.model.network.ne_group.Model4NeGroup;
import com.hellonms.platforms.emp_orange.share.model.security.operation_log.Model4OperationLog;
import com.hellonms.platforms.emp_util.excel.UtilExcel;

/**
 * <p>
 * Page4OperationLogAdvisor
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class Page4OperationLogAdvisor {

	public OPERATION_CODE[] getListOperationCode() throws EmpException {
		return (OPERATION_CODE[]) Driver4OrangeClientSwt.getInstance().execute(new Driver4OrangeClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeClientSwtIf invoker, Invoker4OrangeClientSwtReqeust request) throws EmpException {
				return invoker.getListOperationCode(request);
			}
		});
	}

	@SuppressWarnings("unchecked")
	public TablePageConfig<Model4OperationLog> queryListOperationLog(final NODE node, final String service, final String function, final String operation, final Boolean result, final Integer sessionId, final String userId, final Date fromTime, final Date toTime, final int startNo, final int count) throws EmpException {
		return (TablePageConfig<Model4OperationLog>) Driver4OrangeClientSwt.getInstance().execute(new Driver4OrangeClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeClientSwtIf invoker, Invoker4OrangeClientSwtReqeust request) throws EmpException {
				if (node == null) {
					return invoker.queryListOperationLogByNeGroup(request, Model4NeGroup.ROOT_NE_GROUP_ID, service, function, operation, result, sessionId, userId, fromTime, toTime, startNo, count);
				} else if (node.isNeGroup()) {
					return invoker.queryListOperationLogByNeGroup(request, node.getId(), service, function, operation, result, sessionId, userId, fromTime, toTime, startNo, count);
				} else if (node.isNe()) {
					return invoker.queryListOperationLogByNe(request, node.getId(), service, function, operation, result, sessionId, userId, fromTime, toTime, startNo, count);
				} else {
					return new TablePageConfig<Model4OperationLog>(startNo, count, new Model4OperationLog[0], 0);
				}
			}
		});
	}

	protected byte[] saveExcelOperationLog(final NODE node, final String service, final String function, final String operation, final Boolean result, final Integer sessionId, final String userId, final Date fromTime, final Date toTime) throws EmpException {
		@SuppressWarnings("unchecked")
		TablePageConfig<Model4OperationLog> pageConfig = (TablePageConfig<Model4OperationLog>) Driver4OrangeClientSwt.getInstance().execute(new Driver4OrangeClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeClientSwtIf invoker, Invoker4OrangeClientSwtReqeust request) throws EmpException {
				if (node == null) {
					return invoker.queryListOperationLogByNeGroup(request, Model4NeGroup.ROOT_NE_GROUP_ID, service, function, operation, result, sessionId, userId, fromTime, toTime, 0, 999999);
				} else if (node.isNeGroup()) {
					return invoker.queryListOperationLogByNeGroup(request, node.getId(), service, function, operation, result, sessionId, userId, fromTime, toTime, 0, 999999);
				} else if (node.isNe()) {
					return invoker.queryListOperationLogByNe(request, node.getId(), service, function, operation, result, sessionId, userId, fromTime, toTime, 0, 999999);
				} else {
					return new TablePageConfig<Model4OperationLog>(0, 999999, new Model4OperationLog[0], 0);
				}
			}
		});
		DataTableIf data_table = DataFactory.createDataTable(DATA_TABLE_ORANGE.OPERATION_LOG);
		if (data_table != null) {
			data_table.setDatas((Object) pageConfig.values);
			return UtilExcel.saveExcel(data_table, "OPERATION_LOG", 10000);
		} else {
			return new byte[] {};
		}
	}

}
