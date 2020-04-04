package com.hellonms.platforms.emp_orange.client_swt.page.fault.alarm_statistics;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;

import com.hellonms.platforms.emp_core.share.language.UtilLanguage;
import com.hellonms.platforms.emp_onion.client_swt.data.DataFactory;
import com.hellonms.platforms.emp_onion.client_swt.widget.button.ButtonClick;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.Panel;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelChartIf;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelRound;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelTableIf;
import com.hellonms.platforms.emp_onion.share.model.STATISTICS_TYPE;
import com.hellonms.platforms.emp_orange.client_swt.page.DataBuilder4Orange.DATA_CHART_ORANGE;
import com.hellonms.platforms.emp_orange.client_swt.page.DataBuilder4Orange.DATA_TABLE_ORANGE;
import com.hellonms.platforms.emp_orange.client_swt.page.PanelBuilder4Orange.CHART_ORANGE;
import com.hellonms.platforms.emp_orange.client_swt.page.PanelBuilder4Orange.TABLE_ORANGE;
import com.hellonms.platforms.emp_orange.client_swt.page.PanelFactory;
import com.hellonms.platforms.emp_orange.client_swt.widget.page.PanelPopup4ChartItem;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.fault.event.Model4AlarmStatistics;
import com.hellonms.platforms.emp_orange.share.model.fault.event.Model4AlarmStatistics.ITEM;
import com.hellonms.platforms.emp_util.date.UtilDate;

/**
 * <p>
 * Panel4AlarmStatisticsData
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class Panel4AlarmStatisticsData extends Panel {

	public interface Panel4AlarmStatisticsDataListenerIf {

		public void queryListAlarmStatistics();

		public void saveExcelAlarmStatistics(String path);

	}

	protected Panel4AlarmStatisticsDataListenerIf listener;

	protected PanelTableIf panelTable;

	protected ButtonClick buttonRefresh;

	protected ButtonClick buttonExcel;

	protected PanelChartIf panelChart;

	protected ButtonClick buttonChartItem;

	public Panel4AlarmStatisticsData(Composite parent, int style, Panel4AlarmStatisticsDataListenerIf listener) {
		super(parent, style, UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.DATA_TITLE, MESSAGE_CODE_ORANGE.ALARM_STATISTICS));
		this.listener = listener;

		createGUI();
	}

	protected void createGUI() {
		getContentComposite().setLayout(new FormLayout());

		PanelRound panelTableForm = new PanelRound(getContentComposite(), SWT.NONE);
		FormData fd_panelTableForm = new FormData();
		fd_panelTableForm.bottom = new FormAttachment(100, -210);
		fd_panelTableForm.right = new FormAttachment(100, -80);
		fd_panelTableForm.top = new FormAttachment(0, 5);
		fd_panelTableForm.left = new FormAttachment(0, 5);
		panelTableForm.setLayoutData(fd_panelTableForm);
		panelTableForm.getContentComposite().setLayout(new FillLayout(SWT.HORIZONTAL));

		panelTable = PanelFactory.createPanelTable(TABLE_ORANGE.ALARM_STATISTICS, panelTableForm.getContentComposite(), SWT.BORDER | SWT.FULL_SELECTION, 0, null);
		panelTable.setDataTable(DataFactory.createDataTable(DATA_TABLE_ORANGE.ALARM_STATISTICS));

		ButtonClick[] tableButtons = createTableButton(getContentComposite());
		for (int i = 0; i < tableButtons.length; i++) {
			FormData fd_button = new FormData();
			fd_button.right = (i == 0) ? new FormAttachment(100, -5) : new FormAttachment(tableButtons[i - 1], 0, SWT.RIGHT);
			fd_button.left = (i == 0) ? new FormAttachment(panelTableForm, 5) : new FormAttachment(tableButtons[i - 1], 0, SWT.LEFT);
			fd_button.top = (i == 0) ? new FormAttachment(panelTableForm, 0, SWT.TOP) : new FormAttachment(tableButtons[i - 1], 5, SWT.BOTTOM);
			tableButtons[i].setLayoutData(fd_button);
		}

		PanelRound panelChartForm = new PanelRound(getContentComposite(), SWT.NONE);
		FormData fd_panelChartForm = new FormData();
		fd_panelChartForm.top = new FormAttachment(panelTableForm, 5);
		fd_panelChartForm.bottom = new FormAttachment(100, -5);
		fd_panelChartForm.right = new FormAttachment(panelTableForm, 0, SWT.RIGHT);
		fd_panelChartForm.left = new FormAttachment(0, 5);
		panelChartForm.setLayoutData(fd_panelChartForm);
		panelChartForm.getContentComposite().setLayout(new FillLayout(SWT.HORIZONTAL));

		panelChart = PanelFactory.createPanelChart(CHART_ORANGE.ALARM_STATISTICS, panelChartForm.getContentComposite(), SWT.NONE);
		panelChart.setDataChart(DataFactory.createDataChart(DATA_CHART_ORANGE.ALARM_STATISTICS));

		ButtonClick[] chartButtons = createChartButton(getContentComposite());
		for (int i = 0; i < chartButtons.length; i++) {
			FormData fd_button = new FormData();
			fd_button.right = (i == 0) ? new FormAttachment(100, -5) : new FormAttachment(chartButtons[i - 1], 0, SWT.RIGHT);
			fd_button.left = (i == 0) ? new FormAttachment(panelChartForm, 5) : new FormAttachment(chartButtons[i - 1], 0, SWT.LEFT);
			fd_button.top = (i == 0) ? new FormAttachment(panelChartForm, 0, SWT.TOP) : new FormAttachment(chartButtons[i - 1], 5, SWT.BOTTOM);
			chartButtons[i].setLayoutData(fd_button);
		}
	}

	protected ButtonClick[] createTableButton(Composite parent) {
		buttonRefresh = new ButtonClick(parent, SWT.PUSH);
		buttonRefresh.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Panel4AlarmStatisticsData.this.queryListAlarmStatistics(0);
			}
		});
		buttonRefresh.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.REFRESH));

		buttonExcel = new ButtonClick(parent);
		buttonExcel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog fileDialog = new FileDialog(getShell(), SWT.SAVE);
				fileDialog.setFilterExtensions(new String[] { "*.xlsx" });
				final String file = fileDialog.open();

				if (file != null) {
					listener.saveExcelAlarmStatistics(file);
				}
			}
		});
		buttonExcel.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.EXCEL));

		return new ButtonClick[] { buttonRefresh, buttonExcel };
	}

	protected ButtonClick[] createChartButton(Composite parent) {
		buttonChartItem = new ButtonClick(parent, SWT.PUSH);
		buttonChartItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Panel4AlarmStatisticsData.this.openChartItemCheckerPopup();
			}
		});
		buttonChartItem.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.CHART_ITEM));

		return new ButtonClick[] { buttonChartItem };
	}

	public void display(ITEM item, STATISTICS_TYPE statisticsType, Model4AlarmStatistics[] model4AlarmStatisticses) {
		panelTable.setDatas(item, statisticsType, (Object) model4AlarmStatisticses);
		panelTable.setDataTable(panelTable.getDataTable());

		panelChart.setDatas(item, statisticsType, (Object) model4AlarmStatisticses);

		String axisDateFormat = UtilDate.MINUTELY_FORMAT;
		String tooltipDateFormat = UtilDate.TIME_FORMAT;
		if (statisticsType != null) {
			switch (statisticsType) {
			case MINUTE_5:
				break;
			case MINUTE_15:
				break;
			case MINUTE_30:
				break;
			case HOUR_1:
				axisDateFormat = UtilDate.HOURLY_FORMAT;
				tooltipDateFormat = UtilDate.HOUR_FORMAT;
				break;
			case DAY_1:
				axisDateFormat = UtilDate.DAILY_FORMAT;
				tooltipDateFormat = UtilDate.DATE_FORMAT;
				break;
			case MONTH_1:
				axisDateFormat = UtilDate.MONTHLY_FORMAT;
				tooltipDateFormat = UtilDate.MONTH_FORMAT;
				break;
			default:
				break;
			}
		}

		panelChart.displayLineChart("", axisDateFormat, tooltipDateFormat, true);
	}

	protected void queryListAlarmStatistics(int startNo) {
		listener.queryListAlarmStatistics();
	}

	protected void openChartItemCheckerPopup() {
		Point checker_size = buttonChartItem.getSize();
		PanelPopup4ChartItem popup = new PanelPopup4ChartItem(getShell(), panelChart.getDataChart());
		Point popup_size = popup.getSize();
		Point location = buttonChartItem.toDisplay(checker_size.x - popup_size.x, checker_size.y + 1);
		Rectangle screen = buttonChartItem.getDisplay().getClientArea();
		if (screen.height < location.y + popup_size.y) {
			location = buttonChartItem.toDisplay(checker_size.x - popup_size.x, -(popup_size.y + 1));
		}
		popup.setLocation(location);
		popup.open();
	}

}
