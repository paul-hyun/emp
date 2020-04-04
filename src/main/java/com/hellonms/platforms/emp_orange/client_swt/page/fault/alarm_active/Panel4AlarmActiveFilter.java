package com.hellonms.platforms.emp_orange.client_swt.page.fault.alarm_active;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
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
import com.hellonms.platforms.emp_onion.client_swt.widget.selector.SelectorCombo;
import com.hellonms.platforms.emp_orange.client_swt.page.DataBuilder4Orange.DATA_COMBO_ORANGE;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.fault.event.SEVERITY;

/**
 * <p>
 * Panel4AlarmActiveFilter
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class Panel4AlarmActiveFilter extends Panel {

	/**
	 * 현재알람 검색 판넬의 리스너 인터페이스 입니다.
	 * 
	 * @since Version 1.4.0
	 * @author Hello NMS
	 */
	public interface Panel4AlarmActiveFilterListenerIf {

		/**
		 * 현재알람 리스트를 조회합니다.
		 * 
		 * @param startNo
		 *            시작번호
		 */
		public void queryListAlarmActive(int startNo);

	}

	/**
	 * 리스너
	 */
	protected Panel4AlarmActiveFilterListenerIf listener;

	/**
	 * 심각도 콤보 뷰어
	 */
	protected SelectorCombo selectorComboAlarmSeverity;

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
	public Panel4AlarmActiveFilter(Composite parent, int style, Panel4AlarmActiveFilterListenerIf listener) {
		super(parent, style, UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.FILTER_TITLE, MESSAGE_CODE_ORANGE.ALARM_ACTIVE));

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

		LabelText labelAlarmSeverity = new LabelText(panelContents.getContentComposite(), SWT.NONE);
		labelAlarmSeverity.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		labelAlarmSeverity.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.ALARM_SEVERITY));

		selectorComboAlarmSeverity = new SelectorCombo(panelContents.getContentComposite(), SWT.READ_ONLY);
		selectorComboAlarmSeverity.setDataCombo(DataFactory.createDataCombo(DATA_COMBO_ORANGE.ALARM_ACTIVE_SEVERITY));
		selectorComboAlarmSeverity.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				listener.queryListAlarmActive(0);
			}
		});
		selectorComboAlarmSeverity.getControl().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
	}

	/**
	 * 심각도를 반환합니다.
	 * 
	 * @return 심각도
	 */
	public SEVERITY getSeverity() {
		Object selectedItem = selectorComboAlarmSeverity.getSelectedItem();
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
		selectorComboAlarmSeverity.setSelectedItem(severity);
	}

}
