package com.hellonms.platforms.emp_orange.client_swt.page.fault.event;

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
import com.hellonms.platforms.emp_orange.client_swt.page.DataBuilder4Orange.DATA_COMBO_ORANGE;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_EVENT;
import com.hellonms.platforms.emp_orange.share.model.fault.event.SEVERITY;

/**
 * <p>
 * Panel4EventFilter
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class Panel4EventFilter extends Panel {

	/**
	 * 현재알람 검색 판넬의 리스너 인터페이스 입니다.
	 * 
	 * @since Version 1.4.0
	 * @author Hello NMS
	 */
	public interface Panel4EventFilterListenerIf {

		/**
		 * 현재알람 리스트를 조회합니다.
		 * 
		 * @param startNo
		 *            시작번호
		 */
		public void queryListEvent(int startNo);

	}

	protected Panel4EventFilterListenerIf listener;

	protected SelectorCalendar selectorCalendarFromDate;

	protected SelectorCalendar selectorCalendarToDate;

	protected SelectorCombo selectorComboEventSeverity;

	protected SelectorCombo selectorComboEventCode;

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
	public Panel4EventFilter(Composite parent, int style, Panel4EventFilterListenerIf listener) {
		super(parent, style, UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.FILTER_TITLE, MESSAGE_CODE_ORANGE.EVENT));

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

		LabelText labelFromDate = new LabelText(panelContents.getContentComposite(), SWT.NONE);
		labelFromDate.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		labelFromDate.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.FROM_DATE));

		selectorCalendarFromDate = new SelectorCalendar(panelContents.getContentComposite(), SWT.NONE, PERIOD.DATE);
		selectorCalendarFromDate.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				listener.queryListEvent(0);
			}
		});
		selectorCalendarFromDate.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		LabelText labelToDate = new LabelText(panelContents.getContentComposite(), SWT.NONE);
		labelToDate.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		labelToDate.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.TO_DATE));

		selectorCalendarToDate = new SelectorCalendar(panelContents.getContentComposite(), SWT.NONE, PERIOD.DATE);
		selectorCalendarToDate.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				listener.queryListEvent(0);
			}
		});
		selectorCalendarToDate.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		LabelText labelSeverity = new LabelText(panelContents.getContentComposite(), SWT.NONE);
		labelSeverity.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		labelSeverity.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.EVENT_SEVERITY));

		selectorComboEventSeverity = new SelectorCombo(panelContents.getContentComposite(), SWT.READ_ONLY);
		selectorComboEventSeverity.setDataCombo(DataFactory.createDataCombo(DATA_COMBO_ORANGE.EVENT_SEVERITY));
		selectorComboEventSeverity.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				listener.queryListEvent(0);
			}
		});
		selectorComboEventSeverity.getControl().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		LabelText labelEventCode = new LabelText(panelContents.getContentComposite(), SWT.NONE);
		labelEventCode.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		labelEventCode.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.EVENT_CODE));

		selectorComboEventCode = new SelectorCombo(panelContents.getContentComposite(), SWT.READ_ONLY);
		selectorComboEventCode.setDataCombo(DataFactory.createDataCombo(DATA_COMBO_ORANGE.EVENT_CODE));
		selectorComboEventCode.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				listener.queryListEvent(0);
			}
		});
		selectorComboEventCode.getControl().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
	}

	/**
	 * 조회 시작날짜를 반환합니다.
	 * 
	 * @return 날짜
	 */
	public Date getFromDate() {
		return selectorCalendarFromDate.getStartDate();
	}

	/**
	 * 조회 종료날짜를 반환합니다.
	 * 
	 * @return 날짜
	 */
	public Date getToDate() {
		return selectorCalendarToDate.getEndDate();
	}

	/**
	 * 심각도를 반환합니다.
	 * 
	 * @return 심각도
	 */
	public SEVERITY getSeverity() {
		Object selectedItem = selectorComboEventSeverity.getSelectedItem();
		if (selectedItem instanceof SEVERITY) {
			return (SEVERITY) selectedItem;
		} else {
			return null;
		}
	}

	/**
	 * 심각도를 설정합니다.
	 * 
	 * @param severity
	 *            심각도
	 */
	public void setSeverity(SEVERITY severity) {
		selectorComboEventSeverity.setSelectedItem(severity);
	}

	/**
	 * 심각도를 반환합니다.
	 * 
	 * @return 심각도
	 */
	public EMP_MODEL_EVENT getEventCode() {
		Object selectedItem = selectorComboEventCode.getSelectedItem();
		if (selectedItem instanceof EMP_MODEL_EVENT) {
			return (EMP_MODEL_EVENT) selectedItem;
		} else {
			return null;
		}
	}

	/**
	 * 심각도를 설정합니다.
	 * 
	 * @param severity
	 *            심각도
	 */
	public void setEventCode(EMP_MODEL_EVENT event_def) {
		selectorComboEventCode.setSelectedItem(event_def);
	}

}
