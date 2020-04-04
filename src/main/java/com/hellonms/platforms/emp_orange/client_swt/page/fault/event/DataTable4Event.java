package com.hellonms.platforms.emp_orange.client_swt.page.fault.event;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;

import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_core.share.language.UtilLanguage;
import com.hellonms.platforms.emp_onion.client_swt.data.table.DataTableAt;
import com.hellonms.platforms.emp_orange.client_swt.page.network.network_tree.ModelClient4NetworkTree;
import com.hellonms.platforms.emp_orange.client_swt.util.resource.UtilResource4Orange;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.fault.event.Model4Event;
import com.hellonms.platforms.emp_util.date.UtilDate;

/**
 * <p>
 * DataTable4Event
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class DataTable4Event extends DataTableAt {

	/**
	 * 컬럼명 배열
	 */
	protected String[] COLUMN_NAMES = { UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.SEVERITY), //
			UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.GEN_TIME), //
			UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NE), //
			UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.LOCATION), //
			UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.CAUSE), //
			UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.DESCRIPTION), //
	};

	/**
	 * 컬럼 너비 배열
	 */
	protected int[] COLUMN_WIDTHS = { 100, 135, 120, 200, 200, 320 };

	/**
	 * 현재알람 모델 배열
	 */
	protected Model4Event[] model4Events = {};

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
		return model4Events;
	}

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		Model4Event events = (Model4Event) element;

		switch (columnIndex) {
		case 0:
			return UtilResource4Orange.getImage(events.getSeverity(), false);
		default:
			return null;
		}
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		Model4Event event = (Model4Event) element;

		switch (columnIndex) {
		case 0:
			return event.getSeverity().name();
		case 1:
			return UtilDate.format(event.getGen_time());
		case 2:
			try {
				return ModelClient4NetworkTree.getInstance().getNe(event.getNe_id()).getName();
			} catch (EmpException e) {
				return "";
			}
		case 3:
			return event.getLocation_display();
		case 4:
			try {
				return event.getEvent_def().getSpecific_problem();
			} catch (Exception e) {
				return "";
			}
		case 5:
			return event.getDescription();
		default:
			return event.toString();
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
		this.model4Events = new Model4Event[] {};
	}

	@Override
	public void setDatas(Object... datas) {
		if (datas.length == 1 && datas[0] instanceof Model4Event[]) {
			setData((Model4Event[]) datas[0]);
			refresh();
		}
	}

	/**
	 * 데이터를 설정합니다.
	 * 
	 * @param events
	 *            현재알람 모델 배열
	 */
	protected void setData(Model4Event[] model4Events) {
		this.model4Events = model4Events;
	}

	@Override
	public Model4Event[] getData() {
		return model4Events;
	}

}
