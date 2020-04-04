package com.hellonms.platforms.emp_orange.client_swt.page.security.operation_log;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;

import com.hellonms.platforms.emp_core.share.language.UtilLanguage;
import com.hellonms.platforms.emp_onion.client_swt.data.table.DataTableAt;
import com.hellonms.platforms.emp_orange.client_swt.util.resource.UtilResource4Orange;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.fault.event.SEVERITY;
import com.hellonms.platforms.emp_orange.share.model.security.operation_log.Model4OperationLog;
import com.hellonms.platforms.emp_util.date.UtilDate;

/**
 * <p>
 * DataTable4OperationLog
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class DataTable4OperationLog extends DataTableAt {

	/**
	 * 컬럼명 배열
	 */
	protected String[] COLUMN_NAMES = { UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.OPERATION_TIME), //
			UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.TRANSACTION_ID), //
			UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.SESSION_ID), //
			UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.USER_ID), //
			UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.USER_IP), //
			UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.SERVICE), //
			UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.FUNCTION), //
			UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.OPERATION), //
			UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.RESULT), //
	};

	/**
	 * 컬럼 너비 배열
	 */
	protected int[] COLUMN_WIDTHS = { 145, 105, 80, 80, 100, 80, 80, 80, 60 };

	/**
	 * 현재알람 모델 배열
	 */
	protected Model4OperationLog[] model4OperationLogs = {};

	@Override
	public int getColumnCount() {
		return COLUMN_NAMES.length;
	}

	@Override
	public String getColumnTitle(int column) {
		return COLUMN_NAMES[column];
	}

	@Override
	public int getColumnStyle(int column) {
		return SWT.NONE;
	}

	@Override
	public int getColumnWidth(int column) {
		return COLUMN_WIDTHS[column];
	}

	@Override
	public Object getInput() {
		return this;
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return model4OperationLogs;
	}

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		Model4OperationLog model4OperationLog = (Model4OperationLog) element;

		switch (columnIndex) {
		case 0:
			return model4OperationLog.isResult() ? UtilResource4Orange.getImage(SEVERITY.CLEAR, false) : UtilResource4Orange.getImage(SEVERITY.CRITICAL, false);
		default:
			return null;
		}
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		Model4OperationLog model4OperationLog = (Model4OperationLog) element;

		switch (columnIndex) {
		case 0:
			return UtilDate.format(model4OperationLog.getStart_time());
		case 1:
			return String.valueOf(model4OperationLog.getTransaction_id());
		case 2:
			return String.valueOf(model4OperationLog.getUser_session_id());
		case 3:
			return model4OperationLog.getUser_account();
		case 4:
			return model4OperationLog.getUser_ip();
		case 5:
			return model4OperationLog.getOperation_code().getFunction_group();
		case 6:
			return model4OperationLog.getOperation_code().getFunction();
		case 7:
			return model4OperationLog.getOperation_code().getOperation();
		case 8:
			return model4OperationLog.isResult() ? UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.SUCCESS) : UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.FAIL);
		default:
			return model4OperationLog.toString();
		}
	}

	@Override
	public Color getForeground(Object element) {
		return null;
	}

	@Override
	public Color getBackground(Object element) {
		return null;
	}

	@Override
	public void clear() {
		this.model4OperationLogs = new Model4OperationLog[] {};
	}

	@Override
	public void setDatas(Object... datas) {
		if (datas.length == 1 && datas[0] instanceof Model4OperationLog[]) {
			setData((Model4OperationLog[]) datas[0]);
			refresh();
		}
	}

	protected void setData(Model4OperationLog[] model4OperationLogs) {
		this.model4OperationLogs = model4OperationLogs;
	}

	@Override
	public Model4OperationLog[] getData() {
		return model4OperationLogs;
	}

}
