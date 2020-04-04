package com.hellonms.platforms.emp_orange.client_swt.page.fault.alarm_active;

import java.util.ArrayList;
import java.util.List;

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
import com.hellonms.platforms.emp_orange.client_swt.ApplicationProperty;
import com.hellonms.platforms.emp_orange.client_swt.page.DataBuilder4Orange.DATA_TABLE_ORANGE;
import com.hellonms.platforms.emp_orange.client_swt.page.PanelBuilder4Orange.TABLE_ORANGE;
import com.hellonms.platforms.emp_orange.client_swt.page.PanelFactory;
import com.hellonms.platforms.emp_orange.client_swt.page.network.network_tree.ModelClient4NetworkTree;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_EVENT;
import com.hellonms.platforms.emp_orange.share.model.fault.event.Model4Alarm;
import com.hellonms.platforms.emp_orange.share.model.security.operation_log.OPERATION_CODE_ORANGE;
import com.hellonms.platforms.emp_util.date.UtilDate;

/**
 * <p>
 * Panel4AlarmActiveData
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class Panel4AlarmActiveData extends Panel {

	public interface Panel4AlarmActiveDataListenerIf {

		public void queryListAlarmActive(int startNo);

		public void ackAlarm(long gen_event_id);

		public void clearAlarmByUser(long gen_event_id);

		public void saveExcelAlarmActive(String path);

	}

	public class Panel4AlarmActiveDataChildListener implements //
			PanelTableListenerIf {

		@Override
		public void load(int startNo, int count) {
			listener.queryListAlarmActive(startNo);
		}

	}

	protected int rowCount;

	protected Panel4AlarmActiveDataListenerIf listener;

	protected PanelTableIf panelTable;

	protected ButtonClick buttonRefresh;

	protected ButtonClick buttonExcel;

	protected PanelDetail panelDetail;

	protected ButtonClick buttonAck;

	protected ButtonClick buttonClear;

	protected Model4Alarm model4AlarmActive;

	public Panel4AlarmActiveData(Composite parent, int style, int rowCount, Panel4AlarmActiveDataListenerIf listener) {
		super(parent, style, UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.DATA_TITLE, MESSAGE_CODE_ORANGE.ALARM_ACTIVE));

		this.rowCount = rowCount;
		this.listener = listener;

		createGUI();
	}

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

		panelTable = PanelFactory.createPanelTable(TABLE_ORANGE.ALARM_ACTIVE, panelRoundContents.getContentComposite(), SWT.BORDER | SWT.FULL_SELECTION, rowCount, new Panel4AlarmActiveDataChildListener());
		panelTable.setDataTable(DataFactory.createDataTable(DATA_TABLE_ORANGE.ALARM_ACTIVE));
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
		FormData fd_panelDetailForm = new FormData();
		fd_panelDetailForm.top = new FormAttachment(panelRoundContents, 5);
		fd_panelDetailForm.bottom = new FormAttachment(100, -5);
		fd_panelDetailForm.right = new FormAttachment(panelRoundContents, 0, SWT.RIGHT);
		fd_panelDetailForm.left = new FormAttachment(0, 5);
		panelRoundDetail.setLayoutData(fd_panelDetailForm);
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
				listener.queryListAlarmActive(panelTable.getStartNo());
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
					listener.saveExcelAlarmActive(file);
				}
			}
		});
		buttonExcel.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.EXCEL));

		return new ButtonClick[] { buttonRefresh, buttonExcel };
	}

	protected ButtonClick[] createDetailButton(Composite parent) {
		List<ButtonClick> buttonList = new ArrayList<ButtonClick>();

		if (ApplicationProperty.getOperation_authority(OPERATION_CODE_ORANGE.FAULT_ALARM_ACK) || true) {
			buttonAck = new ButtonClick(parent, SWT.NONE);
			buttonAck.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					if (model4AlarmActive != null) {
						listener.ackAlarm(model4AlarmActive.getGen_first_event_id());
					}
				}
			});
			buttonAck.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.ACK));
			buttonAck.setEnabled(false);
			buttonList.add(buttonAck);
		}

		buttonClear = new ButtonClick(parent, SWT.NONE);
		buttonClear.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (model4AlarmActive != null) {
					listener.clearAlarmByUser(model4AlarmActive.getGen_first_event_id());
				}
			}
		});
		buttonClear.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.CLEAR));
		buttonClear.setEnabled(false);
		buttonList.add(buttonClear);

		return buttonList.toArray(new ButtonClick[0]);
	}

	protected void displayDetail(Model4Alarm model4AlarmActive) {
		this.model4AlarmActive = model4AlarmActive == null ? null : model4AlarmActive.copy();

		if (buttonAck != null) {
			buttonAck.setEnabled(model4AlarmActive != null && !model4AlarmActive.isAck_state());
		}

		buttonClear.setEnabled(model4AlarmActive != null);
		if (model4AlarmActive != null) {
			panelDetail.setText(getDetailValue(model4AlarmActive));
		} else {
			panelDetail.setText();
		}
	}

	protected String[] getDetailTitle() {
		return new String[] { UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.ALARM_SEVERITY), //
				UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.GEN_TIME), //
				UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NE), //
				UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.LOCATION), //
				UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.CAUSE), //
				UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.ACK_USER), //
				UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.ACK_TIME), //
				UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.GEN_DESCRIPTION) };
	}

	protected Object[] getDetailValue(Model4Alarm model4AlarmActive) {
		String neName = "";
		try {
			neName = ModelClient4NetworkTree.getInstance().getNe(model4AlarmActive.getNe_id()).getName();
		} catch (EmpException e) {
		}
		EMP_MODEL_EVENT event_def = EMP_MODEL.current().getEvent(model4AlarmActive.getEvent_code());
		return new Object[] { model4AlarmActive.getSeverity().name(), //
				UtilDate.format(model4AlarmActive.getGen_first_time()), //
				neName, //
				model4AlarmActive.getLocation_display(), //
				event_def == null ? "" : event_def.getSpecific_problem(), //
				model4AlarmActive.isAck_state() ? model4AlarmActive.getAck_user() : "", //
				model4AlarmActive.isAck_state() ? UtilDate.format(model4AlarmActive.getAck_time()) : "", //
				model4AlarmActive.getGen_description() //
		};
	}

	public int getStartNo() {
		return panelTable.getStartNo();
	}

	public void display(TablePageConfig<Model4Alarm> pageConfig) {
		panelTable.setDatas((Object) pageConfig.values);
		panelTable.display(pageConfig);
	}

	public void display(TablePageConfig<Model4Alarm> pageConfig, long gen_event_id) {
		display(pageConfig);

		Model4Alarm model4AlarmActive = null;
		for (Model4Alarm model4Alarm : pageConfig.values) {
			if (model4Alarm.getGen_first_event_id() == gen_event_id) {
				model4AlarmActive = model4Alarm;
				break;
			}
		}
		displayDetail(model4AlarmActive);
	}

}
