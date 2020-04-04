package com.hellonms.platforms.emp_orange.client_swt.page.fault.alarm_active;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;

import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_core.share.language.UtilLanguage;
import com.hellonms.platforms.emp_onion.client_swt.data.table.DataTableAt;
import com.hellonms.platforms.emp_orange.client_swt.page.network.network_tree.ModelClient4NetworkTree;
import com.hellonms.platforms.emp_orange.client_swt.util.resource.UtilResource4Orange;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.fault.event.Model4Alarm;
import com.hellonms.platforms.emp_util.date.UtilDate;

/**
 * <p>
 * DataTable4AlarmActive
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class DataTable4AlarmActive extends DataTableAt {

	/**
	 * 컬럼명 배열
	 */
	protected String[] COLUMN_NAMES = { //
	UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.SEVERITY), //
			UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.GEN_TIME), //
			UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NE), //
			UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.LOCATION), //
			UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.CAUSE), //
			UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.ACK_USER), //
			UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.GEN_DESCRIPTION), //
	};

	/**
	 * 컬럼 너비 배열
	 */
	protected int[] COLUMN_WIDTHS = { 100, 135, 120, 200, 200, 120, 320 };

	/**
	 * 현재알람 모델 배열
	 */
	protected Model4Alarm[] alarms = {};

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
		return alarms;
	}

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		Model4Alarm alarm = (Model4Alarm) element;

		switch (columnIndex) {
		case 0:
			return UtilResource4Orange.getImage(alarm.getSeverity(), alarm.isAck_state());
		default:
			return null;
		}
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		Model4Alarm alarm = (Model4Alarm) element;

		switch (columnIndex) {
		case 0:
			return alarm.getSeverity().name();
		case 1:
			return UtilDate.format(alarm.getGen_first_time());
		case 2:
			try {
				return ModelClient4NetworkTree.getInstance().getNe(alarm.getNe_id()).getName();
			} catch (EmpException e) {
				return "";
			}
		case 3:
			return alarm.getLocation_display();
		case 4:
			try {
				return alarm.getEvent_def().getSpecific_problem();
			} catch (Exception e) {
				return "";
			}
		case 5:
			return alarm.isAck_state() ? alarm.getAck_user() : "";
		case 6:
			return alarm.getGen_description();
		default:
			return alarm.toString();
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
		this.alarms = new Model4Alarm[] {};
	}

	@Override
	public void setDatas(Object... datas) {
		if (datas.length == 1 && datas[0] instanceof Model4Alarm[]) {
			setData((Model4Alarm[]) datas[0]);
			refresh();
		}
	}

	/**
	 * 데이터를 설정합니다.
	 * 
	 * @param alarms
	 *            현재알람 모델 배열
	 */
	protected void setData(Model4Alarm[] alarms) {
		this.alarms = alarms;
	}

	@Override
	public Model4Alarm[] getData() {
		return alarms;
	}

}
