package com.hellonms.platforms.emp_orange.client_swt.page.emp_model;

import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import com.hellonms.platforms.emp_core.share.language.UtilLanguage;
import com.hellonms.platforms.emp_onion.client_swt.data.combo.DataComboSimple;
import com.hellonms.platforms.emp_onion.client_swt.util.location.UtilLocation;
import com.hellonms.platforms.emp_onion.client_swt.widget.button.ButtonClick;
import com.hellonms.platforms.emp_onion.client_swt.widget.dialog.DialogMessage;
import com.hellonms.platforms.emp_onion.client_swt.widget.label.LabelImage;
import com.hellonms.platforms.emp_onion.client_swt.widget.label.LabelText;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.Panel;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelRound;
import com.hellonms.platforms.emp_onion.client_swt.widget.selector.SelectorCombo;
import com.hellonms.platforms.emp_onion.client_swt.widget.text.TextInput;
import com.hellonms.platforms.emp_onion.client_swt.widget.text.TextInput4Script;
import com.hellonms.platforms.emp_onion.share.model.STATISTICS_TYPE;
import com.hellonms.platforms.emp_onion.theme.ThemeBuilder4Onion.COLOR_ONION;
import com.hellonms.platforms.emp_onion.theme.ThemeFactory;
import com.hellonms.platforms.emp_orange.client_swt.util.resource.UtilResource4Orange;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO;
import com.hellonms.platforms.emp_orange.share.model.fault.event.SEVERITY;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.NE_SESSION_PROTOCOL;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.NE_SESSION_PROTOCOL_ORANGE;
import com.hellonms.platforms.emp_util.string.UtilString;

public class Panel4ModelNeInfoDetail extends Panel {

	public interface Panel4ModelNeInfoDetailListnerIf extends Panel4ModelListenerIf {

		public void updated(EMP_MODEL_NE_INFO info);

	}

	private LabelImage image_severity;
	private LabelText label_error;

	private ScrolledComposite scrolledData;
	private Composite panelData;

	private TextInput text_name;
	private TextInput text_display_name;
	private Button check_display_enable;
	private SelectorCombo combo_protocol;
	private Button check_monitoring;

	private Button check_filter_enable;
	private TextInput4Script text_filter_script;
	private ButtonClick button_filter_script;

	private Button check_fault_enable;
	private Button check_audit_alarm;
	private TextInput4Script text_event_script;
	private ButtonClick button_event_script;

	private org.eclipse.swt.widgets.List list_notification_oids;
	private ButtonClick buttonAdd;
	private ButtonClick buttonEdit;
	private ButtonClick buttonRemove;
	private Map<String, String> notification_map = new LinkedHashMap<String, String>();

	private Button check_stat_enable;
	private LabelText label_stat_type;
	private SelectorCombo combo_stat_type;

	private EMP_MODEL_NE_INFO ne_info_def;

	private Panel4ModelNeInfoDetailListnerIf listener;

	public Panel4ModelNeInfoDetail(Composite parent, int style, Panel4ModelNeInfoDetailListnerIf listener) {
		super(parent, style, EMP_MODEL.NE_INFO);
		this.listener = listener;

		createGUI();
	}

	/**
	 * GUI를 생성합니다.
	 */
	protected void createGUI() {
		getContentComposite().setLayout(new FormLayout());

		PanelRound panelError = new PanelRound(getContentComposite(), SWT.NONE);
		FormData fd_panelError = new FormData();
		fd_panelError.top = new FormAttachment(0, 5);
		fd_panelError.left = new FormAttachment(0, 5);
		fd_panelError.right = new FormAttachment(100, -5);
		panelError.setLayoutData(fd_panelError);
		{
			panelError.getContentComposite().setLayout(new GridLayout(2, false));

			image_severity = new LabelImage(panelError.getContentComposite(), SWT.NONE, UtilResource4Orange.getImage(SEVERITY.CLEAR, false));
			image_severity.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));

			label_error = new LabelText(panelError.getContentComposite(), SWT.NONE);
			label_error.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		}

		scrolledData = new ScrolledComposite(getContentComposite(), SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledData.getHorizontalBar().setIncrement(8);
		scrolledData.getVerticalBar().setIncrement(8);
		scrolledData.setExpandHorizontal(true);
		scrolledData.setExpandVertical(true);
		FormData fd_scrolledData = new FormData();
		fd_scrolledData.top = new FormAttachment(panelError, 5, SWT.BOTTOM);
		fd_scrolledData.bottom = new FormAttachment(100, -5);
		fd_scrolledData.right = new FormAttachment(100, -5);
		fd_scrolledData.left = new FormAttachment(0, 5);
		scrolledData.setLayoutData(fd_scrolledData);

		panelData = new Composite(scrolledData, SWT.NONE);
		scrolledData.setContent(panelData);
		panelData.setBackground(ThemeFactory.getColor(COLOR_ONION.PANEL_CONTENTS_BG));
		panelData.setBackgroundMode(SWT.INHERIT_DEFAULT);
		panelData.setLayout(new GridLayout(1, false));

		Group groupGeneral = new Group(panelData, SWT.NONE);
		groupGeneral.setText(" General ");
		groupGeneral.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		createGUIGeneral(groupGeneral);

		Group groupFilter = new Group(panelData, SWT.NONE);
		groupFilter.setText(" Filter ");
		groupFilter.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		createGUIFilter(groupFilter);

		Group groupFault = new Group(panelData, SWT.NONE);
		groupFault.setText(" Fault ");
		groupFault.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		createGUIFault(groupFault);

		Group groupStatistics = new Group(panelData, SWT.NONE);
		groupStatistics.setText(" Statistics ");
		groupStatistics.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		createGUIStatistics(groupStatistics);

		scrolledData.setMinSize(panelData.computeSize(0, SWT.DEFAULT));
	}

	private void createGUIGeneral(Group parent) {
		parent.setLayout(new GridLayout(2, false));

		LabelText label_name = new LabelText(parent, SWT.NONE, true);
		label_name.setText("Name");
		label_name.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));

		text_name = new TextInput(parent, SWT.BORDER);
		text_name.setToolTipText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.TOOLTIP_NE_INFO_NAME));
		text_name.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		text_name.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent arg0) {
				if (ne_info_def != null) {
					String name = text_name.getText().trim();
					ne_info_def.setName(name);
					listener.updated(ne_info_def);
				}
			}
		});

		LabelText label_display_name = new LabelText(parent, SWT.NONE);
		label_display_name.setText("Display Name");
		label_display_name.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));

		text_display_name = new TextInput(parent, SWT.BORDER);
		text_display_name.setToolTipText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.TOOLTIP_NE_INFO_DISPLAYT_NAME));
		text_display_name.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		text_display_name.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent arg0) {
				if (ne_info_def != null) {
					String display_name = text_display_name.getText().trim();
					ne_info_def.setDisplay_name(display_name);
				}
			}
		});

		new Label(parent, SWT.NONE);

		check_display_enable = new Button(parent, SWT.CHECK);
		check_display_enable.setText("Display Enable");
		check_display_enable.setToolTipText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.TOOLTIP_NE_INFO_DISPLAY_ENABLE));
		check_display_enable.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (ne_info_def != null) {
					ne_info_def.setDisplay_enable(check_display_enable.getSelection());
				}
			}
		});

		LabelText label_protocol = new LabelText(parent, SWT.NONE, true);
		label_protocol.setText("Protocol");
		label_protocol.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));

		combo_protocol = new SelectorCombo(parent, SWT.BORDER | SWT.READ_ONLY, new DataComboSimple(NE_SESSION_PROTOCOL_ORANGE.SNMP));
		combo_protocol.getControl().setToolTipText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.TOOLTIP_NE_INFO_PROTOCOL));
		combo_protocol.getControl().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		combo_protocol.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				if (ne_info_def != null) {
					IStructuredSelection selection = (IStructuredSelection) event.getSelection();
					if (!selection.isEmpty()) {
						Object protocol = selection.getFirstElement();
						ne_info_def.setProtocol(protocol instanceof NE_SESSION_PROTOCOL ? (NE_SESSION_PROTOCOL) protocol : null);
						listener.updated(ne_info_def);
					}
				}
			}
		});

		new Label(parent, SWT.NONE);

		check_monitoring = new Button(parent, SWT.CHECK);
		check_monitoring.setText("Monitoring");
		check_monitoring.setToolTipText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.TOOLTIP_NE_INFO_MONITORING));
		check_monitoring.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (ne_info_def != null) {
					boolean monitoring = check_monitoring.getSelection();
					ne_info_def.setMonitoring(monitoring);
					listener.updated(ne_info_def);
				}
			}
		});
	}

	private void createGUIFilter(Group parent) {
		parent.setLayout(new GridLayout(3, false));

		new Label(parent, SWT.NONE);

		check_filter_enable = new Button(parent, SWT.CHECK);
		check_filter_enable.setText("Enable");
		check_filter_enable.setToolTipText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.TOOLTIP_NE_INFO_FILTER_ENABLE));
		check_filter_enable.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		check_filter_enable.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				text_filter_script.setEnabled(check_filter_enable.getSelection());
				button_filter_script.setEnabled(check_filter_enable.getSelection());
				if (ne_info_def != null) {
					boolean filter_enable = check_filter_enable.getSelection();
					ne_info_def.setFilter_enable(filter_enable);
					listener.updated(ne_info_def);
				}
			}
		});

		new Label(parent, SWT.NONE);

		LabelText label_filter_script = new LabelText(parent, SWT.NONE, false);
		label_filter_script.setText("Filter_script");
		label_filter_script.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));

		text_filter_script = new TextInput4Script(parent, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
		text_filter_script.setToolTipText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.TOOLTIP_NE_INFO_FILTER_SCRIPT));
		GridData gd_filter_script = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gd_filter_script.heightHint = 80;
		text_filter_script.setLayoutData(gd_filter_script);
		text_filter_script.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent arg0) {
				if (ne_info_def != null) {
					String filter_script = text_filter_script.getText().trim();
					ne_info_def.setFilter_script(filter_script);
					listener.updated(ne_info_def);
				}
			}
		});

		button_filter_script = new ButtonClick(parent);
		button_filter_script.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.EDITOR));
		button_filter_script.setToolTipText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.TOOLTIP_NE_INFO_FILTER_SCRIPT_EDITOR));
		GridData gd_button_filter_script = new GridData(SWT.FILL, SWT.TOP, false, false);
		gd_button_filter_script.widthHint = 70;
		button_filter_script.setLayoutData(gd_button_filter_script);
		button_filter_script.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Dialog4Script dialog = new Dialog4Script(getShell(), "Filter_script", text_filter_script.getText().trim(), "");
				UtilLocation.toCenter(getShell(), dialog);
				dialog.open();
				if (dialog.isComplete()) {
					text_filter_script.setText(dialog.getScript());
				}
			}
		});
	}

	private void createGUIFault(Group parent) {
		parent.setLayout(new GridLayout(3, false));

		new Label(parent, SWT.NONE);

		check_fault_enable = new Button(parent, SWT.CHECK);
		check_fault_enable.setText("Enable");
		check_fault_enable.setToolTipText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.TOOLTIP_NE_INFO_FAULT_ENABLE));
		check_fault_enable.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		check_fault_enable.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				check_audit_alarm.setEnabled(check_fault_enable.getSelection());
				text_event_script.setEnabled(check_fault_enable.getSelection());
				button_event_script.setEnabled(check_fault_enable.getSelection());
				list_notification_oids.setEnabled(check_fault_enable.getSelection());
				list_notification_oids.deselectAll();
				buttonAdd.setEnabled(check_fault_enable.getSelection());
				buttonEdit.setEnabled(check_fault_enable.getSelection());
				buttonRemove.setEnabled(check_fault_enable.getSelection());
				if (ne_info_def != null) {
					boolean fault_enable = check_fault_enable.getSelection();
					ne_info_def.setFault_enable(fault_enable);
					listener.updated(ne_info_def);
				}
			}
		});

		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);

		check_audit_alarm = new Button(parent, SWT.CHECK);
		check_audit_alarm.setText("Audit Alarm");
		check_audit_alarm.setToolTipText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.TOOLTIP_NE_INFO_AUDIT_ALARM));
		check_audit_alarm.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (ne_info_def != null) {
					boolean audit_alarm = check_audit_alarm.getSelection();
					ne_info_def.setAudit_alarm(audit_alarm);
					listener.updated(ne_info_def);
				}
			}
		});

		new Label(parent, SWT.NONE);

		LabelText label_event_script = new LabelText(parent, SWT.NONE, false);
		label_event_script.setText("Event_script");
		label_event_script.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));

		text_event_script = new TextInput4Script(parent, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
		text_event_script.setToolTipText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.TOOLTIP_NE_INFO_EVENT_SCRIPT));
		GridData gd_event_script = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gd_event_script.heightHint = 80;
		text_event_script.setLayoutData(gd_event_script);
		text_event_script.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent arg0) {
				if (ne_info_def != null) {
					String event_script = text_event_script.getText().trim();
					ne_info_def.setEvent_script(event_script);
					listener.updated(ne_info_def);
				}
			}
		});

		button_event_script = new ButtonClick(parent);
		button_event_script.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.EDITOR));
		button_event_script.setToolTipText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.TOOLTIP_NE_INFO_EVENT_SCRIPT_EDITOR));
		GridData gd_button_event_script = new GridData(SWT.FILL, SWT.TOP, false, false);
		gd_button_event_script.widthHint = 70;
		button_event_script.setLayoutData(gd_button_event_script);
		button_event_script.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Dialog4Script dialog = new Dialog4Script(getShell(), "Event_script", text_event_script.getText().trim(), EMP_MODEL_NE_INFO.getScript_template(ne_info_def.getCode()));
				UtilLocation.toCenter(getShell(), dialog);
				dialog.open();
				if (dialog.isComplete()) {
					text_event_script.setText(dialog.getScript());
				}
			}
		});

		LabelText label_notification_oid = new LabelText(parent, SWT.NONE, false);
		label_notification_oid.setText("Notification");
		label_notification_oid.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 3));

		list_notification_oids = new org.eclipse.swt.widgets.List(parent, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		list_notification_oids.setToolTipText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.TOOLTIP_NE_INFO_NOTIFICATION));
		GridData gd_notification_oids = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd_notification_oids.verticalSpan = 3;
		gd_notification_oids.heightHint = 60;
		list_notification_oids.setLayoutData(gd_notification_oids);

		buttonAdd = new ButtonClick(parent, SWT.NONE);
		buttonAdd.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.ADD));
		buttonAdd.setToolTipText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.TOOLTIP_NE_INFO_NOTIFICATION_ADD));
		buttonAdd.setEnabled(false);
		buttonAdd.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false));
		buttonAdd.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Dialog4NotificationScript dialog = new Dialog4NotificationScript(getShell(), "Notification_script", "", "", true, EMP_MODEL_NE_INFO.getScript_template(ne_info_def.getCode()));
				UtilLocation.toCenter(getShell(), dialog);
				dialog.open();
				if (dialog.isComplete()) {
					String notification_oid = dialog.getOid();
					String notification_script = dialog.getScript();
					notification_map.put(notification_oid, notification_script);
					list_notification_oids.setItems(notification_map.keySet().toArray(new String[0]));
					if (ne_info_def != null) {
						ne_info_def.setNotification_map(notification_map);
						listener.updated(ne_info_def);
					}
				}
			}
		});

		buttonEdit = new ButtonClick(parent, SWT.NONE);
		buttonEdit.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.EDIT));
		buttonEdit.setToolTipText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.TOOLTIP_NE_INFO_NOTIFICATION_EDIT));
		buttonEdit.setEnabled(false);
		buttonEdit.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false));
		buttonEdit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int selectionIndex = list_notification_oids.getSelectionIndex();
				if (selectionIndex == -1) {
					DialogMessage.openInfo(getShell(), "선택오류", UtilString.format("편집할 '{}'을 선택하세요.", "Notification_oid"));
					return;
				} else {
					String notification_oid = list_notification_oids.getItem(selectionIndex);
					String notification_script = notification_map.get(notification_oid);
					Dialog4NotificationScript dialog = new Dialog4NotificationScript(getShell(), "Notification_script", notification_oid, notification_script, false, EMP_MODEL_NE_INFO.getScript_template(ne_info_def.getCode()));
					UtilLocation.toCenter(getShell(), dialog);
					dialog.open();
					if (dialog.isComplete()) {
						notification_oid = dialog.getOid();
						notification_script = dialog.getScript();
						notification_map.put(notification_oid, notification_script);
						list_notification_oids.setItems(notification_map.keySet().toArray(new String[0]));
						if (ne_info_def != null) {
							ne_info_def.setNotification_map(notification_map);
							listener.updated(ne_info_def);
						}
					}
				}
			}
		});

		buttonRemove = new ButtonClick(parent, SWT.NONE);
		buttonRemove.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.REMOVE));
		buttonRemove.setToolTipText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.TOOLTIP_NE_INFO_NOTIFICATION_REMOVE));
		buttonRemove.setEnabled(false);
		buttonRemove.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false));
		buttonRemove.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int selectionIndex = list_notification_oids.getSelectionIndex();
				if (selectionIndex == -1) {
					DialogMessage.openInfo(getShell(), "선택오류", UtilString.format("제거할 '{}'을 선택하세요.", "Notification_oid"));
					return;
				} else {
					String notification_oid = list_notification_oids.getItem(selectionIndex);
					notification_map.remove(notification_oid);
					list_notification_oids.setItems(notification_map.keySet().toArray(new String[0]));
					if (ne_info_def != null) {
						ne_info_def.setNotification_map(notification_map);
						listener.updated(ne_info_def);
					}
				}
			}
		});
	}

	private void createGUIStatistics(Group parent) {
		parent.setLayout(new GridLayout(2, false));

		new Label(parent, SWT.NONE);

		check_stat_enable = new Button(parent, SWT.CHECK);
		check_stat_enable.setText("Enable");
		check_stat_enable.setToolTipText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.TOOLTIP_NE_INFO_STAT_ENABLE));
		check_stat_enable.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		check_stat_enable.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				label_stat_type.setMandatory(check_stat_enable.getSelection());
				combo_stat_type.getControl().setEnabled(check_stat_enable.getSelection());

				if (ne_info_def != null) {
					boolean stat_enable = check_stat_enable.getSelection();
					ne_info_def.setStat_enable(stat_enable);
					listener.updated(ne_info_def);
				}
			}
		});

		label_stat_type = new LabelText(parent, SWT.NONE, false);
		label_stat_type.setText("Stat_type");

		label_stat_type.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));

		combo_stat_type = new SelectorCombo(parent, SWT.BORDER | SWT.READ_ONLY, new DataComboSimple(STATISTICS_TYPE.MINUTE_5, STATISTICS_TYPE.MINUTE_15, STATISTICS_TYPE.MINUTE_30));
		combo_stat_type.getControl().setToolTipText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.TOOLTIP_NE_INFO_STAT_TYPE));
		combo_stat_type.getControl().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		combo_stat_type.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				if (ne_info_def != null) {
					IStructuredSelection selection = (IStructuredSelection) event.getSelection();
					if (!selection.isEmpty()) {
						STATISTICS_TYPE stat_type = (STATISTICS_TYPE) selection.getFirstElement();
						ne_info_def.setStat_type(stat_type);
						listener.updated(ne_info_def);
					}
				}
			}
		});
	}

	public void displayDetail(EMP_MODEL_NE_INFO ne_info_def) {
		this.ne_info_def = ne_info_def;

		if (ne_info_def == null) {
			setTitle(UtilString.format("{}", EMP_MODEL.NE_INFO));
			image_severity.setImage(null);
			label_error.setText("");

			displayDetailGeneral(null);
			displayDetailFilter(null);
			displayDetailFault(null);
			displayDetailStatistics(null);
		} else {
			setTitle(UtilString.format("{} - {}", EMP_MODEL.NE_INFO, ne_info_def.getName()));
			image_severity.setImage(UtilResource4Orange.getImage(ne_info_def.getSeverity(), false));
			label_error.setText(ne_info_def.getError());

			displayDetailGeneral(ne_info_def);
			displayDetailFilter(ne_info_def);
			displayDetailFault(ne_info_def);
			displayDetailStatistics(ne_info_def);
		}

		applyDetail();
	}

	public void displayDetail() {
		if (ne_info_def == null) {
			setTitle(UtilString.format("{}", EMP_MODEL.NE_INFO));
			image_severity.setImage(null);
			label_error.setText("");
		} else {
			setTitle(UtilString.format("{} - {}", EMP_MODEL.NE_INFO, ne_info_def.getName()));
			image_severity.setImage(UtilResource4Orange.getImage(ne_info_def.getSeverity(), false));
			label_error.setText(ne_info_def.getError());
		}
	}

	private void displayDetailGeneral(EMP_MODEL_NE_INFO ne_info_def) {
		if (ne_info_def == null) {
			text_name.setText("");
			text_display_name.setText("");
			check_display_enable.setSelection(true);
			combo_protocol.setSelectedItem(null);
			check_monitoring.setSelection(false);
		} else {
			if (!text_name.getText().trim().equals(ne_info_def.getName())) {
				text_name.setText(ne_info_def.getName());
			}
			if (!text_display_name.getText().trim().equals(ne_info_def.getDisplay_name())) {
				text_display_name.setText(ne_info_def.getDisplay_name());
			}
			check_display_enable.setSelection(ne_info_def.isDisplay_enable());
			combo_protocol.setSelectedItem(ne_info_def.getProtocol());
			check_monitoring.setSelection(ne_info_def.isMonitoring());
		}
	}

	private void displayDetailFilter(EMP_MODEL_NE_INFO ne_info_def) {
		if (ne_info_def == null) {
			check_filter_enable.setEnabled(false);
			check_filter_enable.setSelection(false);
			text_filter_script.setEnabled(check_filter_enable.getSelection());
			text_filter_script.setText("");
			button_filter_script.setEnabled(check_filter_enable.getSelection());
		} else {
			check_filter_enable.setEnabled(true);
			check_filter_enable.setSelection(ne_info_def.isFilter_enable());
			text_filter_script.setEnabled(check_filter_enable.getSelection());
			if (!text_filter_script.getText().trim().equals(ne_info_def.getFilter_script() == null ? "" : ne_info_def.getFilter_script())) {
				text_filter_script.setText(ne_info_def.getFilter_script() == null ? "" : ne_info_def.getFilter_script());
			}
			button_filter_script.setEnabled(check_filter_enable.getSelection());
		}
	}

	private void displayDetailFault(EMP_MODEL_NE_INFO ne_info_def) {
		if (ne_info_def == null) {
			check_fault_enable.setEnabled(false);
			check_fault_enable.setSelection(false);
			check_audit_alarm.setEnabled(check_fault_enable.getSelection());
			check_audit_alarm.setSelection(check_fault_enable.getSelection());
			text_event_script.setEnabled(check_fault_enable.getSelection());
			text_event_script.setText("");
			button_event_script.setEnabled(check_fault_enable.getSelection());
			notification_map.clear();
			list_notification_oids.setEnabled(check_fault_enable.getSelection());
			list_notification_oids.setItems(notification_map.keySet().toArray(new String[0]));
			buttonAdd.setEnabled(check_fault_enable.getSelection());
			buttonEdit.setEnabled(check_fault_enable.getSelection());
			buttonRemove.setEnabled(check_fault_enable.getSelection());
			notification_map.clear();
		} else {
			check_fault_enable.setEnabled(true);
			check_fault_enable.setSelection(ne_info_def.isFault_enable());
			check_audit_alarm.setEnabled(check_fault_enable.getSelection());
			check_audit_alarm.setSelection(ne_info_def.isAudit_alarm());
			text_event_script.setEnabled(check_fault_enable.getSelection());
			if (!text_event_script.getText().trim().equals(ne_info_def.getEvent_script() == null ? "" : ne_info_def.getEvent_script())) {
				text_event_script.setText(ne_info_def.getEvent_script() == null ? "" : ne_info_def.getEvent_script());
			}
			button_event_script.setEnabled(check_fault_enable.getSelection());
			notification_map.clear();
			notification_map.putAll(ne_info_def.getNotification_map());
			list_notification_oids.setEnabled(check_fault_enable.getSelection());
			list_notification_oids.setItems(notification_map.keySet().toArray(new String[0]));
			buttonAdd.setEnabled(check_fault_enable.getSelection());
			buttonEdit.setEnabled(check_fault_enable.getSelection());
			buttonRemove.setEnabled(check_fault_enable.getSelection());
		}
	}

	private void displayDetailStatistics(EMP_MODEL_NE_INFO ne_info_def) {
		if (ne_info_def == null) {
			check_stat_enable.setEnabled(false);
			check_stat_enable.setSelection(false);
			label_stat_type.setMandatory(check_stat_enable.getSelection());
			combo_stat_type.getControl().setEnabled(check_stat_enable.getSelection());
			combo_stat_type.setSelectedItem(combo_stat_type.getDefaultItem());
		} else {
			check_stat_enable.setEnabled(true);
			check_stat_enable.setSelection(ne_info_def.isStat_enable());
			label_stat_type.setMandatory(check_stat_enable.getSelection());
			combo_stat_type.getControl().setEnabled(check_stat_enable.getSelection());
			combo_stat_type.setSelectedItem(ne_info_def.getStat_type() == null ? combo_stat_type.getDefaultItem() : ne_info_def.getStat_type());
		}
	}

	private void applyDetail() {
		if (ne_info_def != null) {
			Object protocol = combo_protocol.getValue();
			ne_info_def.setProtocol(protocol instanceof NE_SESSION_PROTOCOL ? (NE_SESSION_PROTOCOL) protocol : null);

			Object stat_type = combo_stat_type.getValue();
			ne_info_def.setStat_type(stat_type instanceof STATISTICS_TYPE ? (STATISTICS_TYPE) stat_type : null);
		}
	}

}
