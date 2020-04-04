package com.hellonms.platforms.emp_orange.client_swt.page.network.ne_statistics;

import java.util.Date;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
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
import com.hellonms.platforms.emp_orange.client_swt.model_display.ModelDisplay4NeStatistics;
import com.hellonms.platforms.emp_orange.client_swt.page.DataBuilder4Orange.DATA_COMBO_ORANGE;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO;
import com.hellonms.platforms.emp_orange.share.model.network.ne_info.NE_INFO_INDEX;

/**
 * <p>
 * Panel4NeStatisticsFilter
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class Panel4NeStatisticsFilter extends Panel {

	/**
	 * 성능통계 검색 판넬의 리스너 인터페이스 입니다.
	 * 
	 * @since Version 1.4.0
	 * @author Hello NMS
	 */
	public interface Panel4NeStatisticsFilterListenerIf {

		/**
		 * 성능이력 리스트를 조회합니다.
		 */
		public void selectionChanged(EMP_MODEL_NE_INFO ne_info_def);

		/**
		 * 성능통계 리스트를 조회합니다.
		 */
		public void queryListNeStatistics();

	}

	/**
	 * 리스너
	 */
	protected Panel4NeStatisticsFilterListenerIf listener;

	/**
	 * 조회날짜 셀렉터
	 */
	protected SelectorCalendar selectorCalendarDate;

	/**
	 * 성능함옥 콤보뷰어
	 */
	protected SelectorCombo selectorComboNeInfoCode;

	/**
	 * 세부항목 콤보뷰어
	 */
	protected SelectorCombo selectorComboNeInfoIndex;

	/**
	 * 통계타입 콤보뷰어
	 */
	protected SelectorCombo selectorComboStatisticsType;

	/**
	 * 생성자 입니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 * @param style
	 *            스타일
	 * @param ne_group_id_filters
	 *            NE그룹 아이디 필터
	 * @param ne_id_filters
	 *            NE 아이디 필터
	 * @param ne_define_id_filters
	 *            NE 정의 아이디 필터
	 * @param listener
	 *            리스너
	 */
	public Panel4NeStatisticsFilter(Composite parent, int style, Panel4NeStatisticsFilterListenerIf listener) {
		super(parent, style, UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NE_STATISTICS));
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

		LabelText labelTextDate = new LabelText(panelContents.getContentComposite(), SWT.NONE);
		labelTextDate.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		labelTextDate.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.DATE));

		selectorCalendarDate = new SelectorCalendar(panelContents.getContentComposite(), SWT.NONE, PERIOD.DATE);
		selectorCalendarDate.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				listener.queryListNeStatistics();
			}
		});
		selectorCalendarDate.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		LabelText labelTextNeInfoCode = new LabelText(panelContents.getContentComposite(), SWT.NONE);
		labelTextNeInfoCode.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		labelTextNeInfoCode.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.PERFORMANCE_ITEM));

		selectorComboNeInfoCode = new SelectorCombo(panelContents.getContentComposite(), SWT.READ_ONLY);
		selectorComboNeInfoCode.setDataCombo(DataFactory.createDataCombo(DATA_COMBO_ORANGE.NE_STATISTICS_CODE));
		selectorComboNeInfoCode.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection selection = (IStructuredSelection) event.getSelection();
				if (!selection.isEmpty()) {
					listener.selectionChanged((EMP_MODEL_NE_INFO) selection.getFirstElement());
				}
			}
		});
		selectorComboNeInfoCode.getControl().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		LabelText labelTextNeInfoIndex = new LabelText(panelContents.getContentComposite(), SWT.NONE);
		labelTextNeInfoIndex.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		labelTextNeInfoIndex.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.PERFORMANCE_LOCATION));

		selectorComboNeInfoIndex = new SelectorCombo(panelContents.getContentComposite(), SWT.READ_ONLY);
		selectorComboNeInfoIndex.setDataCombo(DataFactory.createDataCombo(DATA_COMBO_ORANGE.NE_STATISTICS_INDEX));
		selectorComboNeInfoIndex.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection selection = (IStructuredSelection) event.getSelection();
				if (!selection.isEmpty()) {
					listener.queryListNeStatistics();
				}
			}
		});
		selectorComboNeInfoIndex.getControl().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		LabelText labelStatisticsType = new LabelText(panelContents.getContentComposite(), SWT.NONE);
		labelStatisticsType.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		labelStatisticsType.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.STATISTICS_TYPE));

		selectorComboStatisticsType = new SelectorCombo(panelContents.getContentComposite(), SWT.READ_ONLY, new DataCombo4NeStatisticsType());
		selectorComboStatisticsType.setDataCombo(DataFactory.createDataCombo(DATA_COMBO_ORANGE.NE_STATISTICS_TYPE));
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
				listener.queryListNeStatistics();
			}
		});
		selectorComboStatisticsType.getControl().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
	}

	public void display(EMP_MODEL_NE_INFO[] ne_info_defs) {
		boolean updateNeInfoCodes = selectorComboNeInfoCode.isNeedUpdate((Object) ne_info_defs);
		if (updateNeInfoCodes) {
			Object selectedItem = selectorComboNeInfoCode.getSelectedItem();
			selectorComboNeInfoCode.setDatas((Object) ne_info_defs);
			selectorComboNeInfoCode.setSelectedItem(selectorComboNeInfoCode.findElement(selectedItem));

			selectorComboNeInfoIndex.setDatas((Object) new NE_INFO_INDEX[0]);
		}
	}

	/**
	 * 화면에 출력합니다.
	 * 
	 * @param durations
	 * @param locations
	 */
	public void display(ModelDisplay4NeStatistics modelDisplay4NeStatistics) {
		NE_INFO_INDEX[] ne_info_indexs = modelDisplay4NeStatistics.getNe_info_indexs();
		boolean updateNeInfoIndexes = selectorComboNeInfoIndex.isNeedUpdate(getNeInfoCode(), ne_info_indexs);
		if (updateNeInfoIndexes) {
			selectorComboNeInfoIndex.setFireSelectionChanged(false);
			Object selectedItem = selectorComboNeInfoIndex.getSelectedItem();
			selectorComboNeInfoIndex.setDatas(getNeInfoCode(), ne_info_indexs);
			selectorComboNeInfoIndex.setSelectedItem(selectorComboNeInfoIndex.findElement(selectedItem));
			selectorComboNeInfoIndex.setFireSelectionChanged(true);
		}
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
	 * 성능항목을 반환합니다.
	 * 
	 * @return 성능항목
	 */
	public EMP_MODEL_NE_INFO getNeInfoCode() {
		Object selectedItem = selectorComboNeInfoCode.getSelectedItem();
		if (selectedItem instanceof EMP_MODEL_NE_INFO) {
			return (EMP_MODEL_NE_INFO) selectedItem;
		} else {
			return null;
		}
	}

	/**
	 * 세부항목을 반환합니다.
	 * 
	 * @return 세부항목
	 */
	public NE_INFO_INDEX getNeInfoIndex() {
		Object selectedItem = selectorComboNeInfoIndex.getSelectedItem();
		if (selectedItem instanceof NE_INFO_INDEX) {
			return ((NE_INFO_INDEX) selectedItem);
		} else {
			return null;
		}
	}

	/**
	 * 통계타입을 반환합니다.
	 * 
	 * @return 통계타입
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
