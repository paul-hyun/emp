package com.hellonms.platforms.emp_orange.client_swt.page.fault.alarm_active;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

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
import com.hellonms.platforms.emp_orange.share.model.fault.event.SEVERITY;
import com.hellonms.platforms.emp_orange.share.model.network.ne_group.Model4NeGroup;
import com.hellonms.platforms.emp_util.date.UtilDate;

/**
 * <p>
 * DataTable4AlarmActiveConsole
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class DataTable4AlarmActiveConsole extends DataTableAt {

	/**
	 * 컬럼명 배열
	 */
	protected String[] COLUMN_NAMES = { UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.SEQUENCE), //
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
	protected int[] COLUMN_WIDTHS = { 60, 100, 135, 120, 190, 190, 120, 320 };

	/**
	 * 항목 최대 개수
	 */
	protected int maxRow = 1000;

	/**
	 * 통신오류 알람 필터 상태
	 */
	protected boolean cfFilter = true;

	/**
	 * 심각 이벤트 필터 상태
	 */
	protected boolean criticalFilter = true;

	/**
	 * 주의 이벤트 필터 상태
	 */
	protected boolean majorFilter = true;

	/**
	 * 경계 이벤트 필터 상태
	 */
	protected boolean minorFilter = true;

	/**
	 * 정상 이벤트 필터 상태
	 */
	protected boolean clearFilter = true;

	/**
	 * 경고 이벤트 필터 상태
	 */
	protected boolean warningFilter = true;

	/**
	 * 알림 이벤트 필터 상태
	 */
	protected boolean infoFilter = true;

	/**
	 * 필터된 NE 그룹 모델 배열
	 */
	protected Model4NeGroup[] neGroupFilters = null;

	/**
	 * 전체 이벤트 번호 표시 모델 리스트
	 */
	protected ArrayList<Model4Alarm> allAlarmList = new ArrayList<Model4Alarm>();

	/**
	 * 필터된이벤트 번호 표시 모델 리스트
	 */
	protected ArrayList<Model4Alarm> filteredAlarmList = new ArrayList<Model4Alarm>();

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
		switch (column) {
		case 0:
			return SWT.RIGHT;
		default:
			return SWT.NONE;
		}
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
		return filteredAlarmList.toArray();
	}

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		Model4Alarm alarm = (Model4Alarm) element;

		switch (columnIndex) {
		case 1:
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
			return String.valueOf(alarm.getGen_first_event_id());
		case 1:
			return alarm.getSeverity().name();
		case 2:
			return UtilDate.format(alarm.getGen_first_time());
		case 3:
			try {
				return ModelClient4NetworkTree.getInstance().getNe(alarm.getNe_id()).getName();
			} catch (EmpException e) {
				return "";
			}
		case 4:
			return alarm.getLocation_display();
		case 5:
			try {
				return alarm.getEvent_def().getSpecific_problem();
			} catch (Exception e) {
				return "";
			}
		case 6:
			return alarm.getAck_user();
		case 7:
			return alarm.getGen_description();
		default:
			return element.toString();
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
		lock.lock();
		try {
			allAlarmList.clear();
			filteredAlarmList.clear();
		} finally {
			lock.unlock();
		}
	}

	@Override
	public void setDatas(Object... datas) {
		if (datas.length == 1 && datas[0] instanceof Model4Alarm[]) {
			setData((Model4Alarm[]) datas[0]);
			remakeData();
		}
	}

	/**
	 * 락
	 */
	protected ReentrantLock lock = new ReentrantLock();

	/**
	 * 데이터를 설정합니다.
	 * 
	 * @param events
	 *            이벤트 모델 배열
	 */
	protected void setData(Model4Alarm[] alarmActives) {
		lock.lock();
		try {
			ArrayList<Model4Alarm> alarmList = new ArrayList<Model4Alarm>();
			for (int i = 0; i < alarmActives.length; i++) {
				alarmList.add(alarmActives[i]);
			}
			this.allAlarmList = alarmList;
		} finally {
			lock.unlock();
		}
	}

	/**
	 * 데이트를 다시 만듭니다.
	 */
	protected void remakeData() {
		lock.lock();
		try {
			filteredAlarmList.clear();

			for (Model4Alarm alarm : allAlarmList) {
				if (isDisplay(alarm)) {
					filteredAlarmList.add(alarm);
				}
			}
		} finally {
			lock.unlock();
		}
		refresh();
	}

	@Override
	public Model4Alarm[] getData() {
		return allAlarmList.toArray(new Model4Alarm[0]);
	}

	/**
	 * 표시상태를 반환합니다.
	 * 
	 * @param event
	 *            이벤트 모델
	 * @return 표시상태
	 */
	protected boolean isDisplay(Model4Alarm alarm) {
		SEVERITY severity = alarm.getSeverity();
		boolean display = !((!cfFilter && severity == SEVERITY.COMMUNICATION_FAIL) || //
				(!criticalFilter && severity == SEVERITY.CRITICAL) || //
				(!majorFilter && severity == SEVERITY.MAJOR) || //
		(!minorFilter && severity == SEVERITY.MINOR));

		if (display && neGroupFilters != null) {
			boolean display_ne_group = false;
			for (Model4NeGroup neGroupFilter : neGroupFilters) {
				display_ne_group = ModelClient4NetworkTree.getInstance().isChildNe(neGroupFilter.getNe_group_id(), alarm.getNe_id());
				if (display_ne_group) {
					break;
				}
			}
			display = display_ne_group;
		}

		return display;
	}

	/**
	 * 통신오류 이벤트 필터상태를 설정합니다.
	 * 
	 * @param cfFilter
	 *            통신오류 이벤트 필터상태
	 */
	public void setCfFilter(boolean cfFilter) {
		this.cfFilter = cfFilter;
		remakeData();
	}

	/**
	 * 심각 이벤트 필터상태를 설정합니다.
	 * 
	 * @param criticalFilter
	 *            심각 이벤트 필터상태
	 */
	public void setCriticalFilter(boolean criticalFilter) {
		this.criticalFilter = criticalFilter;
		remakeData();
	}

	/**
	 * 주의 이벤트 필터상태를 설정합니다.
	 * 
	 * @param majorFilter
	 *            주의 이벤트 필터상태
	 */
	public void setMajorFilter(boolean majorFilter) {
		this.majorFilter = majorFilter;
		remakeData();
	}

	/**
	 * 경계 이벤트 필터상태를 설정합니다.
	 * 
	 * @param minorFilter
	 *            경계 이벤트 필터상태
	 */
	public void setMinorFilter(boolean minorFilter) {
		this.minorFilter = minorFilter;
		remakeData();
	}

	/**
	 * 정상 이벤트 필터상태를 설정합니다.
	 * 
	 * @param clearFilter
	 *            정상 이벤트 필터상태
	 */
	public void setClearFilter(boolean clearFilter) {
		this.clearFilter = clearFilter;
		remakeData();
	}

	/**
	 * 경고 이벤트 필터상태를 설정합니다.
	 * 
	 * @param warningFilter
	 *            경고 이벤트 필터상태
	 */
	public void setWarningFilter(boolean warningFilter) {
		this.warningFilter = warningFilter;
		remakeData();
	}

	/**
	 * 알림 이벤트 필터상태를 설정합니다.
	 * 
	 * @param infoFilter
	 *            알림 이벤트 필터상태
	 */
	public void setInfoFilter(boolean infoFilter) {
		this.infoFilter = infoFilter;
		remakeData();
	}

	/**
	 * 필터된 NE 그룹 배열을 설정합니다.
	 * 
	 * @param neGroupFilters
	 *            필터된 NE 그룹 배열
	 */
	public void setNeGroupFilters(Model4NeGroup[] neGroupFilters) {
		boolean display_all = false;
		for (Model4NeGroup neGroupFilter : neGroupFilters) {
			if (neGroupFilter.getNe_group_id() == Model4NeGroup.ROOT_NE_GROUP_ID) {
				display_all = true;
				break;
			}
		}
		this.neGroupFilters = display_all ? null : neGroupFilters;
		remakeData();
	}

}
