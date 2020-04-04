package com.hellonms.platforms.emp_orange.client_swt.page.fault.alarm_statistics;

import java.util.Date;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.hellonms.platforms.emp_core.share.language.UtilLanguage;
import com.hellonms.platforms.emp_onion.client_swt.data.DataFactory;
import com.hellonms.platforms.emp_onion.client_swt.widget.label.LabelText;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.Panel;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelRound;
import com.hellonms.platforms.emp_onion.client_swt.widget.selector.SelectorCalendar;
import com.hellonms.platforms.emp_onion.client_swt.widget.selector.SelectorCalendar.PERIOD;
import com.hellonms.platforms.emp_onion.client_swt.widget.selector.SelectorCombo;
import com.hellonms.platforms.emp_onion.share.model.STATISTICS_TYPE;
import com.hellonms.platforms.emp_orange.client_swt.page.DataBuilder4Orange.DATA_COMBO_ORANGE;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.fault.event.Model4AlarmStatistics.ITEM;

/**
 * <p>
 * Panel4AlarmStatisticsFilter
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class Panel4AlarmStatisticsFilter extends Panel {

	/**
	 * 현재알람 검색 판넬의 리스너 인터페이스 입니다.
	 * 
	 * @since Version 1.4.0
	 * @author Hello NMS
	 */
	public interface Panel4AlarmStatisticsFilterListenerIf {

		/**
		 * 현재알람 리스트를 조회합니다.
		 * 
		 * @param startNo
		 *            시작번호
		 */
		public void queryListAlarmStatistics();

	}

	/**
	 * 리스너
	 */
	protected Panel4AlarmStatisticsFilterListenerIf listener;

	/**
	 * 조회날짜 셀렉터
	 */
	protected SelectorCalendar selectorCalendarDate;

	/**
	 * 심각도 콤보 뷰어
	 */
	protected SelectorCombo selectorComboItem;

	/**
	 * 심각도 콤보 뷰어
	 */
	protected SelectorCombo selectorComboStatisticsType;

	/**
	 * 생성자 입니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 * @param style
	 *            스타일
	 * @param listener
	 *            리스너
	 */
	public Panel4AlarmStatisticsFilter(Composite parent, int style, Panel4AlarmStatisticsFilterListenerIf listener) {
		super(parent, style, UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.FILTER_TITLE, MESSAGE_CODE_ORANGE.ALARM_STATISTICS));
		this.listener = listener;

		createGUI();
	}

	/**
	 * GUI를 생성합니다.
	 */
	protected void createGUI() {
		getContentComposite().setLayout(new FormLayout());

		PanelRound panelContents = new PanelRound(getContentComposite(), SWT.NONE);
		FormData fd_panelContents = new FormData();
		fd_panelContents.bottom = new FormAttachment(100, -5);
		fd_panelContents.right = new FormAttachment(100, -5);
		fd_panelContents.top = new FormAttachment(0, 5);
		fd_panelContents.left = new FormAttachment(0, 5);
		panelContents.setLayoutData(fd_panelContents);
		GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.marginHeight = 0;
		gridLayout.marginWidth = 0;
		panelContents.getContentComposite().setLayout(gridLayout);

		LabelText labelDate = new LabelText(panelContents.getContentComposite(), SWT.NONE);
		labelDate.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		labelDate.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.DATE));

		selectorCalendarDate = new SelectorCalendar(panelContents.getContentComposite(), SWT.NONE, PERIOD.DATE);
		selectorCalendarDate.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				listener.queryListAlarmStatistics();
			}
		});
		selectorCalendarDate.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		LabelText labelAlarmStatisticsItem = new LabelText(panelContents.getContentComposite(), SWT.NONE);
		labelAlarmStatisticsItem.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		labelAlarmStatisticsItem.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.ALARM_STATISTICS_ITEM));

		selectorComboItem = new SelectorCombo(panelContents.getContentComposite(), SWT.READ_ONLY);
		selectorComboItem.setDataCombo(DataFactory.createDataCombo(DATA_COMBO_ORANGE.ALARM_STATISTICS_ITEM));
		selectorComboItem.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				listener.queryListAlarmStatistics();
			}
		});
		selectorComboItem.getControl().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		LabelText labelAlarmStatisticsType = new LabelText(panelContents.getContentComposite(), SWT.NONE);
		labelAlarmStatisticsType.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		labelAlarmStatisticsType.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.ALARM_STATISTICS_TYPE));

		selectorComboStatisticsType = new SelectorCombo(panelContents.getContentComposite(), SWT.READ_ONLY);
		selectorComboStatisticsType.setDataCombo(DataFactory.createDataCombo(DATA_COMBO_ORANGE.ALARM_STATISTICS_TYPE));
		selectorComboStatisticsType.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				switch (getStatisticsType()) {
				case MINUTE_5:
				case MINUTE_15:
				case MINUTE_30:
				case HOUR_1:
					selectorCalendarDate.setPeriod(PERIOD.DATE);
					break;
				case DAY_1:
					selectorCalendarDate.setPeriod(PERIOD.MONTH);
					break;
				case MONTH_1:
					selectorCalendarDate.setPeriod(PERIOD.YEAR);
					break;
				default:
					selectorCalendarDate.setPeriod(PERIOD.DATE);
					break;
				}
				listener.queryListAlarmStatistics();
			}
		});
		selectorComboStatisticsType.getControl().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
	}

	/**
	 * 조회 시작날짜를 반환합니다.
	 * 
	 * @return 날짜
	 */
	public Date getFromTime() {
		return selectorCalendarDate.getStartDate();
	}

	/**
	 * 조회 종료날짜를 반환합니다.
	 * 
	 * @return 날짜
	 */
	public Date getToTime() {
		return selectorCalendarDate.getEndDate();
	}

	/**
	 * 심각도를 반환합니다.
	 * 
	 * @return 심각도
	 */
	public ITEM getItem() {
		Object selectedItem = selectorComboItem.getSelectedItem();
		if (selectedItem instanceof ITEM) {
			return (ITEM) selectedItem;
		} else {
			return null;
		}
	}

	/**
	 * 심각도를 반환합니다.
	 * 
	 * @return 심각도
	 */
	public STATISTICS_TYPE getStatisticsType() {
		Object selectedItem = selectorComboStatisticsType.getSelectedItem();
		if (selectedItem instanceof STATISTICS_TYPE) {
			return (STATISTICS_TYPE) selectedItem;
		} else {
			return null;
		}
	}

}
