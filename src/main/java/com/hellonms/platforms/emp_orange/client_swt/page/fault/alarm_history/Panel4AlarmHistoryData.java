package com.hellonms.platforms.emp_orange.client_swt.page.fault.alarm_history;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;

import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_core.share.language.UtilLanguage;
import com.hellonms.platforms.emp_onion.client_swt.data.DataFactory;
import com.hellonms.platforms.emp_onion.client_swt.widget.button.ButtonClick;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.Panel;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelDetail;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelRound;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelTable.PanelTableListenerIf;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelTableIf;
import com.hellonms.platforms.emp_onion.share.model.TablePageConfig;
import com.hellonms.platforms.emp_orange.client_swt.page.DataBuilder4Orange.DATA_TABLE_ORANGE;
import com.hellonms.platforms.emp_orange.client_swt.page.PanelBuilder4Orange.TABLE_ORANGE;
import com.hellonms.platforms.emp_orange.client_swt.page.PanelFactory;
import com.hellonms.platforms.emp_orange.client_swt.page.network.network_tree.ModelClient4NetworkTree;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_EVENT;
import com.hellonms.platforms.emp_orange.share.model.fault.event.Model4Alarm;
import com.hellonms.platforms.emp_util.date.UtilDate;

/**
 * <p>
 * Panel4AlarmHistoryData
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class Panel4AlarmHistoryData extends Panel {

	public interface Panel4AlarmHistoryDataListenerIf {

		public void queryListAlarmHistory(int startNo);

		public void saveExcelAlarmHistory(String path);

	}

	public class Panel4AlarmHistoryDataChildListener implements //
			PanelTableListenerIf {

		@Override
		public void load(int startNo, int count) {
			listener.queryListAlarmHistory(startNo);
		}

	}

	protected int rowCount;

	protected Panel4AlarmHistoryDataListenerIf listener;

	protected PanelTableIf panelTable;

	protected ButtonClick buttonRefresh;

	protected ButtonClick buttonExcel;

	protected PanelDetail panelDetail;

	protected Model4Alarm model4AlarmHistory;

	public Panel4AlarmHistoryData(Composite parent, int style, int rowCount, Panel4AlarmHistoryDataListenerIf listener) {
		super(parent, style, UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.DATA_TITLE, MESSAGE_CODE_ORANGE.ALARM_HISTORY));

		this.rowCount = rowCount;
		this.listener = listener;

		createGUI();
	}

	/**
	 * GUI를 생성합니다.
	 */
	protected void createGUI() {
		getContentComposite().setLayout(new FormLayout());

		PanelRound panelRoundContents = new PanelRound(getContentComposite(), SWT.NONE);
		FormData fd_panelContents = new FormData();
		fd_panelContents.bottom = new FormAttachment(100, -(20 + 15 * getDetailTitle().length));
		fd_panelContents.right = new FormAttachment(100, -80);
		fd_panelContents.top = new FormAttachment(0, 5);
		fd_panelContents.left = new FormAttachment(0, 5);
		panelRoundContents.setLayoutData(fd_panelContents);
		panelRoundContents.getContentComposite().setLayout(new FillLayout(SWT.HORIZONTAL));

		panelTable = PanelFactory.createPanelTable(TABLE_ORANGE.ALARM_HISTORY, panelRoundContents.getContentComposite(), SWT.BORDER | SWT.FULL_SELECTION, rowCount, new Panel4AlarmHistoryDataChildListener());
		panelTable.setDataTable(DataFactory.createDataTable(DATA_TABLE_ORANGE.ALARM_HISTORY));
		panelTable.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				if (event.getSelection().isEmpty()) {
					displayDetail(null);
				} else {
					displayDetail((Model4Alarm) ((StructuredSelection) event.getSelection()).getFirstElement());
				}
			}
		});

		ButtonClick[] tableButtons = createTableButton(getContentComposite());
		for (int i = 0; i < tableButtons.length; i++) {
			FormData fd_button = new FormData();
			fd_button.right = (i == 0) ? new FormAttachment(100, -5) : new FormAttachment(tableButtons[i - 1], 0, SWT.RIGHT);
			fd_button.left = (i == 0) ? new FormAttachment(panelRoundContents, 5) : new FormAttachment(tableButtons[i - 1], 0, SWT.LEFT);
			fd_button.top = (i == 0) ? new FormAttachment(panelRoundContents, 0, SWT.TOP) : new FormAttachment(tableButtons[i - 1], 5, SWT.BOTTOM);
			tableButtons[i].setLayoutData(fd_button);
		}

		PanelRound panelRoundDetail = new PanelRound(getContentComposite(), SWT.NONE);
		FormData fd_panelDetail = new FormData();
		fd_panelDetail.top = new FormAttachment(panelRoundContents, 5);
		fd_panelDetail.bottom = new FormAttachment(100, -5);
		fd_panelDetail.right = new FormAttachment(panelRoundContents, 0, SWT.RIGHT);
		fd_panelDetail.left = new FormAttachment(0, 5);
		panelRoundDetail.setLayoutData(fd_panelDetail);
		panelRoundDetail.getContentComposite().setLayout(new FillLayout(SWT.HORIZONTAL));

		panelDetail = new PanelDetail(panelRoundDetail.getContentComposite(), SWT.NONE, getDetailTitle());

		ButtonClick[] detailButtons = createDetailButton(getContentComposite());
		for (int i = 0; i < detailButtons.length; i++) {
			FormData fd_button = new FormData();
			fd_button.right = (i == 0) ? new FormAttachment(100, -5) : new FormAttachment(detailButtons[i - 1], 0, SWT.RIGHT);
			fd_button.left = (i == 0) ? new FormAttachment(panelRoundDetail, 5) : new FormAttachment(detailButtons[i - 1], 0, SWT.LEFT);
			fd_button.top = (i == 0) ? new FormAttachment(panelRoundDetail, 0, SWT.TOP) : new FormAttachment(detailButtons[i - 1], 5, SWT.BOTTOM);
			detailButtons[i].setLayoutData(fd_button);
		}
	}

	protected ButtonClick[] createTableButton(Composite parent) {
		buttonRefresh = new ButtonClick(parent, SWT.NONE);
		buttonRefresh.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				listener.queryListAlarmHistory(panelTable.getStartNo());
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
					listener.saveExcelAlarmHistory(file);
				}
			}
		});
		buttonExcel.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.EXCEL));

		return new ButtonClick[] { buttonRefresh, buttonExcel };
	}

	protected ButtonClick[] createDetailButton(Composite parent) {
		return new ButtonClick[] {};
	}

	protected void displayDetail(Model4Alarm model4AlarmHistory) {
		this.model4AlarmHistory = model4AlarmHistory == null ? null : model4AlarmHistory.copy();

		if (model4AlarmHistory != null) {
			panelDetail.setText(getDetailValue(model4AlarmHistory));
		} else {
			panelDetail.setText();
		}
	}

	protected String[] getDetailTitle() {
		return new String[] { UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.ALARM_SEVERITY), //
				UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.GEN_TIME), //
				UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.CLEAR_TIME), //
				UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NE), //
				UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.LOCATION), //
				UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.CAUSE), //
				UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.ACK_USER), //
				UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.ACK_TIME), //
				UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.GEN_DESCRIPTION), //
				UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.CLEAR_DESCRIPTION) //
		};
	}

	protected Object[] getDetailValue(Model4Alarm model4AlarmHistory) {
		String neName = "";
		try {
			neName = ModelClient4NetworkTree.getInstance().getNe(model4AlarmHistory.getNe_id()).getName();
		} catch (EmpException e) {
		}
		EMP_MODEL_EVENT event_def = EMP_MODEL.current().getEvent(model4AlarmHistory.getEvent_code());
		return new Object[] { model4AlarmHistory.getSeverity().name(), //
				UtilDate.format(model4AlarmHistory.getGen_first_time()), //
				model4AlarmHistory.isClear_state() ? UtilDate.format(model4AlarmHistory.getClear_time()) : "", //
				neName, //
				model4AlarmHistory.getLocation_display(), //
				event_def == null ? "" : event_def.getSpecific_problem(), //
				model4AlarmHistory.isAck_state() ? model4AlarmHistory.getAck_user() : "", //
				model4AlarmHistory.isAck_state() ? UtilDate.format(model4AlarmHistory.getAck_time()) : "", //
				model4AlarmHistory.getGen_description(), //
				model4AlarmHistory.getClear_description() //
		};
	}

	public int getStartNo() {
		return panelTable.getStartNo();
	}

	public void display(TablePageConfig<Model4Alarm> pageConfig) {
		panelTable.setDatas((Object) pageConfig.values);
		panelTable.display(pageConfig);
	}

	public void display(TablePageConfig<Model4Alarm> pageConfig, long genEventId) {
		display(pageConfig);

		Model4Alarm model4AlarmHistory = null;
		for (Model4Alarm model4Alarm : pageConfig.values) {
			if (model4Alarm.getGen_first_event_id() == genEventId) {
				model4AlarmHistory = model4Alarm;
				break;
			}
		}
		displayDetail(model4AlarmHistory);
	}

}
