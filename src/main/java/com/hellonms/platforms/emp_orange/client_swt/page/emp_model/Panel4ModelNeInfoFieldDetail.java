package com.hellonms.platforms.emp_orange.client_swt.page.emp_model;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

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
import com.hellonms.platforms.emp_onion.client_swt.widget.label.LabelImage;
import com.hellonms.platforms.emp_onion.client_swt.widget.label.LabelText;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.Panel;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelRound;
import com.hellonms.platforms.emp_onion.client_swt.widget.selector.SelectorCombo;
import com.hellonms.platforms.emp_onion.client_swt.widget.text.TextInput;
import com.hellonms.platforms.emp_onion.client_swt.widget.text.TextInput4Integer64;
import com.hellonms.platforms.emp_onion.client_swt.widget.text.TextInput4Script;
import com.hellonms.platforms.emp_onion.theme.ThemeBuilder4Onion.COLOR_ONION;
import com.hellonms.platforms.emp_onion.theme.ThemeFactory;
import com.hellonms.platforms.emp_orange.client_swt.util.resource.UtilResource4Orange;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_ENUM;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_EVENT;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO_FIELD;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO_FIELD.STATISTICS_AGGREGATION;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO_FIELD.STATISTICS_SAVE;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO_FIELD.THRESHOLD_TYPE;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_TYPE;
import com.hellonms.platforms.emp_orange.share.model.fault.event.SEVERITY;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.snmp.SNMP_TYPE;
import com.hellonms.platforms.emp_util.string.UtilString;

public class Panel4ModelNeInfoFieldDetail extends Panel {

	public interface Panel4ModelNeFieldDetailListnerIf extends Panel4ModelListenerIf {

		public void updated(EMP_MODEL_NE_INFO_FIELD info_field);

	}

	private class DataComboEnum extends DataComboSimple {

		private Map<Integer, EMP_MODEL_ENUM> enum_map = new LinkedHashMap<Integer, EMP_MODEL_ENUM>();

		public DataComboEnum() {
			super(0);
		}

		@Override
		public void setDatas(Object... datas) {
			Object[] datas_new = new Object[datas.length + 1];
			datas_new[0] = 0;
			enum_map.clear();
			for (int i = 0; i < datas.length; i++) {
				EMP_MODEL_ENUM enum_def = (EMP_MODEL_ENUM) datas[i];
				datas_new[i + 1] = enum_def.getCode();
				enum_map.put(enum_def.getCode(), enum_def);
			}
			super.setDatas(datas_new);
		}

		@Override
		public String getText(Object element) {
			if (element instanceof Integer) {
				EMP_MODEL_ENUM enum_def = enum_map.get(element);
				return enum_def == null ? "--" : enum_def.getName();
			} else {
				return String.valueOf(element);
			}
		}

	}

	private class DataComboEvent extends DataComboSimple {

		private Map<Integer, EMP_MODEL_EVENT> event_map = new LinkedHashMap<Integer, EMP_MODEL_EVENT>();

		public DataComboEvent() {
			super(0);
		}

		@Override
		public void setDatas(Object... datas) {
			Set<Integer> data_list = new LinkedHashSet<Integer>();
			data_list.add(0);
			event_map.clear();
			for (int i = 0; i < datas.length; i++) {
				EMP_MODEL_EVENT event_def = (EMP_MODEL_EVENT) datas[i];
				if (!EMP_MODEL_EVENT.isHidden(event_def.getCode())) {
					data_list.add(event_def.getCode());
					event_map.put(event_def.getCode(), event_def);
				}
			}
			super.setDatas(data_list.toArray());
		}

		@Override
		public String getText(Object element) {
			if (element instanceof Integer) {
				EMP_MODEL_EVENT event_def = event_map.get(element);
				return event_def == null ? "--" : event_def.getSpecific_problem();
			} else {
				return String.valueOf(element);
			}
		}

	}

	private LabelImage image_severity;
	private LabelText label_error;

	private ScrolledComposite scrolledData;
	private Composite panelData;

	private TextInput text_name;
	private TextInput text_display_name;
	private Button check_display_enable;
	private TextInput text_unit;
	private Button check_virtual;

	private LabelText label_oid;
	private TextInput text_oid;

	private LabelText label_type_remote;
	private DataComboSimple data_type_remote;
	private SelectorCombo combo_type_remote;

	private DataCombo4DataTypeLocal data_type_local;
	private SelectorCombo combo_type_local;

	private LabelText label_field_script;
	private TextInput4Script text_field_script;
	private ButtonClick button_field_script;

	private SelectorCombo combo_enum;
	private DataComboEnum data_enum;
	private Button check_index;
	private Button check_read;
	private Button check_update;

	private Button check_stat_label;
	private Button check_stat_enable;
	private Button check_chart_default;

	private LabelText label_stat_save;
	private SelectorCombo combo_stat_save;

	private LabelText label_stat_aggr;
	private SelectorCombo combo_stat_aggr;

	private Button check_thr_enable;
	private DataComboEvent data_thr_event;

	private LabelText label_thr_event;
	private SelectorCombo combo_thr_event;

	private LabelText label_thr_type;
	private SelectorCombo combo_thr_type;

	private Button check_thr_critical;
	private TextInput4Integer64 text_thr_critical_min;
	private TextInput4Integer64 text_thr_critical_max;
	private Button check_thr_major;
	private TextInput4Integer64 text_thr_major_min;
	private TextInput4Integer64 text_thr_major_max;
	private Button check_thr_minor;
	private TextInput4Integer64 text_thr_minor_min;
	private TextInput4Integer64 text_thr_minor_max;

	private EMP_MODEL_NE_INFO_FIELD info_field;

	private Panel4ModelNeFieldDetailListnerIf listener;

	public Panel4ModelNeInfoFieldDetail(Composite parent, int style, Panel4ModelNeFieldDetailListnerIf listener) {
		super(parent, style, EMP_MODEL.ENUM_FIELD);
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

		Group groupStatistics = new Group(panelData, SWT.NONE);
		groupStatistics.setText(" Statistics ");
		groupStatistics.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		createGUIStatistics(groupStatistics);

		Group groupThreshold = new Group(panelData, SWT.NONE);
		groupThreshold.setText(" Threshold ");
		groupThreshold.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		createGUIThreshold(groupThreshold);

		scrolledData.setMinSize(panelData.computeSize(0, SWT.DEFAULT));
	}

	private void createGUIGeneral(Composite parent) {
		parent.setLayout(new GridLayout(3, false));

		LabelText label_name = new LabelText(parent, SWT.NONE, true);
		label_name.setText("Name");
		label_name.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));

		text_name = new TextInput(parent, SWT.BORDER);
		text_name.setToolTipText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.TOOLTIP_NE_INFO_FIELD_NAME));
		text_name.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		text_name.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent arg0) {
				if (info_field != null) {
					String name = text_name.getText().trim();
					info_field.setName(name);
					listener.updated(info_field);
				}
			}
		});

		new Label(parent, SWT.NONE);

		LabelText label_display_name = new LabelText(parent, SWT.NONE);
		label_display_name.setText("Display Name");
		label_display_name.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));

		text_display_name = new TextInput(parent, SWT.BORDER);
		text_display_name.setToolTipText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.TOOLTIP_NE_INFO_FIELD_DISPLAY_NAME));
		text_display_name.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		text_display_name.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent arg0) {
				if (info_field != null) {
					String display_name = text_display_name.getText().trim();
					info_field.setDisplay_name(display_name);
				}
			}
		});

		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);

		check_display_enable = new Button(parent, SWT.CHECK);
		check_display_enable.setText("Display Enable");
		check_display_enable.setToolTipText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.TOOLTIP_NE_INFO_FIELD_DISPLAY_ENABLE));
		check_display_enable.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (info_field != null) {
					info_field.setDisplay_enable(check_display_enable.getSelection());
				}
			}
		});

		new Label(parent, SWT.NONE);

		LabelText label_unit = new LabelText(parent, SWT.NONE);
		label_unit.setText("Unit");
		label_unit.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));

		text_unit = new TextInput(parent, SWT.BORDER);
		text_unit.setToolTipText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.TOOLTIP_NE_INFO_FIELD_UNIT));
		text_unit.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		text_unit.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent arg0) {
				if (info_field != null) {
					String unit = text_unit.getText().trim();
					info_field.setUnit(unit);
					listener.updated(info_field);
				}
			}
		});

		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);

		check_virtual = new Button(parent, SWT.CHECK);
		check_virtual.setText("Virtual");
		check_virtual.setToolTipText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.TOOLTIP_NE_INFO_FIELD_VIRTUAL));
		check_virtual.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				label_oid.setMandatory(!check_virtual.getSelection());
				text_oid.setEnabled(!check_virtual.getSelection());
				label_type_remote.setMandatory(!check_virtual.getSelection());
				combo_type_remote.getControl().setEnabled(!check_virtual.getSelection());
				label_field_script.setMandatory(check_virtual.getSelection());
				text_field_script.setEnabled(check_virtual.getSelection());
				button_field_script.setEnabled(check_virtual.getSelection());
				changeDataTypeLocal(check_virtual.getSelection() ? null : SNMP_TYPE.valueOf((String) combo_type_remote.getSelectedItem()));
				check_index.setEnabled(!check_virtual.getSelection());
				check_index.setSelection(check_virtual.getSelection() ? false : check_index.getSelection());
				check_update.setEnabled(!check_virtual.getSelection());
				check_update.setSelection(check_virtual.getSelection() ? false : check_update.getSelection());

				if (info_field != null) {
					info_field.setVirtual_enable(check_virtual.getSelection());
					info_field.setIndex(check_index.getSelection());
					info_field.setUpdate(check_update.getSelection());
					listener.updated(info_field);
				}
			}
		});

		new Label(parent, SWT.NONE);

		label_oid = new LabelText(parent, SWT.NONE, true);
		label_oid.setText("Oid");
		label_oid.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));

		text_oid = new TextInput(parent, SWT.BORDER);
		text_oid.setToolTipText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.TOOLTIP_NE_INFO_FIELD_OID));
		text_oid.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		text_oid.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent arg0) {
				if (info_field != null) {
					String oid = text_oid.getText().trim();
					info_field.setOid(oid);
					listener.updated(info_field);
				}
			}
		});

		new Label(parent, SWT.NONE);

		label_type_remote = new LabelText(parent, SWT.NONE, true);
		label_type_remote.setText("Type_remote");
		label_type_remote.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));

		data_type_remote = new DataComboSimple();
		combo_type_remote = new SelectorCombo(parent, SWT.BORDER | SWT.READ_ONLY, data_type_remote);
		combo_type_remote.getControl().setToolTipText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.TOOLTIP_NE_INFO_FIELD_TYPE_REMOTE));
		combo_type_remote.getControl().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		combo_type_remote.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection selection = (IStructuredSelection) event.getSelection();
				if (selection.isEmpty()) {
					combo_type_local.setDatas((Object) new EMP_MODEL_TYPE[] {});
				} else {
					String type_remote = (String) selection.getFirstElement();
					SNMP_TYPE snmp_type = SNMP_TYPE.valueOf(type_remote);
					changeDataTypeLocal(snmp_type);
					if (info_field != null) {
						info_field.setType_remote(type_remote);
						EMP_MODEL_TYPE type_local = (EMP_MODEL_TYPE) combo_type_local.getValue();
						info_field.setType_local(type_local);
						boolean stat_enable = check_stat_enable.getSelection();
						info_field.setStat_enable(stat_enable);
						boolean thr_enable = check_thr_enable.getSelection();
						info_field.setThr_enable(thr_enable);
						listener.updated(info_field);
					}
				}
			}
		});

		new Label(parent, SWT.NONE);

		LabelText label_type_local = new LabelText(parent, SWT.NONE, true);
		label_type_local.setText("Type_local");
		label_type_local.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));

		data_type_local = new DataCombo4DataTypeLocal();
		combo_type_local = new SelectorCombo(parent, SWT.BORDER | SWT.READ_ONLY, data_type_local);
		combo_type_local.getControl().setToolTipText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.TOOLTIP_NE_INFO_FIELD_TYPE_LOCAL));
		combo_type_local.getControl().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		combo_type_local.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection selection = (IStructuredSelection) event.getSelection();
				if (!selection.isEmpty()) {
					EMP_MODEL_TYPE type_local = (EMP_MODEL_TYPE) selection.getFirstElement();
					changeDataTypeLocal(type_local);
					if (info_field != null) {
						info_field.setType_local(type_local);
						boolean stat_enable = check_stat_enable.getSelection();
						info_field.setStat_enable(stat_enable);
						boolean thr_enable = check_thr_enable.getSelection();
						info_field.setThr_enable(thr_enable);
						listener.updated(info_field);
					}
				}
			}
		});

		new Label(parent, SWT.NONE);

		label_field_script = new LabelText(parent, SWT.NONE);
		label_field_script.setText("Field_script");
		label_field_script.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));

		text_field_script = new TextInput4Script(parent, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
		text_field_script.setToolTipText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.TOOLTIP_NE_INFO_FIELD_FIELD_SCRIPT));
		GridData gd_text_field_script = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gd_text_field_script.heightHint = 80;
		text_field_script.setLayoutData(gd_text_field_script);
		text_field_script.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent arg0) {
				if (info_field != null) {
					String field_script = text_field_script.getText().trim();
					info_field.setField_script(field_script);
					listener.updated(info_field);
				}
			}
		});

		button_field_script = new ButtonClick(parent);
		button_field_script.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.EDITOR));
		button_field_script.setToolTipText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.TOOLTIP_NE_INFO_FIELD_FIELD_SCRIPT_EDITOR));
		GridData gd_button_field_script = new GridData(SWT.FILL, SWT.TOP, false, false);
		gd_button_field_script.widthHint = 70;
		button_field_script.setLayoutData(gd_button_field_script);
		button_field_script.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Dialog4Script dialog = new Dialog4Script(getShell(), "Field_script", text_field_script.getText().trim(), "");
				UtilLocation.toCenter(getShell(), dialog);
				dialog.open();
				if (dialog.isComplete()) {
					text_field_script.setText(dialog.getScript());
				}
			}
		});

		LabelText label_enum = new LabelText(parent, SWT.NONE, false);
		label_enum.setText("Enum");
		label_enum.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));

		data_enum = new DataComboEnum();
		combo_enum = new SelectorCombo(parent, SWT.BORDER | SWT.READ_ONLY, data_enum);
		combo_enum.getControl().setToolTipText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.TOOLTIP_NE_INFO_FIELD_ENUM));
		combo_enum.getControl().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		combo_enum.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection selection = (IStructuredSelection) event.getSelection();
				if (!selection.isEmpty()) {
					if (info_field != null) {
						Object enum_code = selection.getFirstElement();
						info_field.setEnum_code((Integer) enum_code);
						listener.updated(info_field);
					}
				}
			}
		});

		new Label(parent, SWT.NONE);

		LabelText label_access = new LabelText(parent, SWT.NONE, true);
		label_access.setText("Access");
		label_access.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));

		Composite composite_access = new Composite(parent, SWT.NONE);
		composite_access.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		composite_access.setLayout(new GridLayout(3, false));
		{
			check_index = new Button(composite_access, SWT.CHECK);
			check_index.setText("Index");
			check_index.setToolTipText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.TOOLTIP_NE_INFO_FIELD_INDEX));
			check_index.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					if (info_field != null) {
						boolean index = check_index.getSelection();
						info_field.setIndex(index);
						listener.updated(info_field);
					}
				}
			});

			check_read = new Button(composite_access, SWT.CHECK);
			check_read.setText("Read");
			check_read.setToolTipText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.TOOLTIP_NE_INFO_FIELD_READ));
			check_read.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					if (info_field != null) {
						boolean read = check_read.getSelection();
						info_field.setRead(read);
						listener.updated(info_field);
					}
				}
			});

			check_update = new Button(composite_access, SWT.CHECK);
			check_update.setText("Update");
			check_update.setToolTipText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.TOOLTIP_NE_INFO_FIELD_UPDATE));
			check_update.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					if (info_field != null) {
						boolean update = check_update.getSelection();
						info_field.setUpdate(update);
						listener.updated(info_field);
					}
				}
			});
		}
	}

	private void createGUIStatistics(Composite parent) {
		parent.setLayout(new GridLayout(2, false));

		new Label(parent, SWT.NONE);

		check_stat_label = new Button(parent, SWT.CHECK);
		check_stat_label.setText("Stat_label (NE_INFO - Statistics - Enable 체크 시 활성화)");
		check_stat_label.setToolTipText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.TOOLTIP_NE_INFO_FIELD_STAT_LABEL));
		check_stat_label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		check_stat_label.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (info_field != null) {
					boolean stat_label = check_stat_label.getSelection();
					info_field.setStat_label(stat_label);
					listener.updated(info_field);
				}
			}
		});

		new Label(parent, SWT.NONE);

		check_stat_enable = new Button(parent, SWT.CHECK);
		check_stat_enable.setText("Enable (NE_INFO - Statistics - Enable 체크, Type_local이 INT_32 또는 INT_64일 때 활성화)");
		check_stat_enable.setToolTipText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.TOOLTIP_NE_INFO_FIELD_STAT_ENABLE));
		check_stat_enable.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		check_stat_enable.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				selectStatistics_enable();
				if (info_field != null) {
					boolean stat_enable = check_stat_enable.getSelection();
					info_field.setStat_enable(stat_enable);
					boolean chart_default = check_chart_default.getSelection();
					info_field.setChart_default(chart_default);
					boolean thr_enable = check_thr_enable.getSelection();
					info_field.setThr_enable(thr_enable);
					listener.updated(info_field);
				}
			}
		});

		new Label(parent, SWT.NONE);

		check_chart_default = new Button(parent, SWT.CHECK);
		check_chart_default.setText("Chart Default (NE_INFO_FIELD - Statistics - Enable 체크 시 활성화)");
		check_chart_default.setToolTipText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.TOOLTIP_NE_INFO_FIELD_CHART_DEFAULT));
		check_chart_default.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		check_chart_default.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (info_field != null) {
					info_field.setChart_default(check_chart_default.getSelection());
				}
			}
		});

		label_stat_save = new LabelText(parent, SWT.NONE);
		label_stat_save.setText("Stat Save");
		label_stat_save.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));

		combo_stat_save = new SelectorCombo(parent, SWT.BORDER | SWT.READ_ONLY, new DataComboSimple((Object[]) STATISTICS_SAVE.values()));
		combo_stat_save.getControl().setToolTipText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.TOOLTIP_NE_INFO_FIELD_STAT_SAVE));
		combo_stat_save.getControl().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		combo_stat_save.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection selection = (IStructuredSelection) event.getSelection();
				if (!selection.isEmpty()) {
					if (info_field != null) {
						Object stat_save = selection.getFirstElement();
						info_field.setStat_save(stat_save instanceof STATISTICS_SAVE ? (STATISTICS_SAVE) stat_save : null);
						listener.updated(info_field);
					}
				}
			}
		});

		label_stat_aggr = new LabelText(parent, SWT.NONE);
		label_stat_aggr.setText("Stat Aggr");
		label_stat_aggr.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));

		combo_stat_aggr = new SelectorCombo(parent, SWT.BORDER | SWT.READ_ONLY, new DataComboSimple((Object[]) STATISTICS_AGGREGATION.values()));
		combo_stat_aggr.getControl().setToolTipText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.TOOLTIP_NE_INFO_FIELD_STAT_AGGR));
		combo_stat_aggr.getControl().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		combo_stat_aggr.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection selection = (IStructuredSelection) event.getSelection();
				if (!selection.isEmpty()) {
					if (info_field != null) {
						Object stat_aggr = selection.getFirstElement();
						info_field.setStat_aggr(stat_aggr instanceof STATISTICS_AGGREGATION ? (STATISTICS_AGGREGATION) stat_aggr : null);
						listener.updated(info_field);
					}
				}
			}
		});
	}

	private void createGUIThreshold(Composite parent) {
		parent.setLayout(new GridLayout(2, false));

		new Label(parent, SWT.NONE);

		check_thr_enable = new Button(parent, SWT.CHECK);
		check_thr_enable.setText("Enable (NE_INFO_FIELD - Statistics - Enable 체크 시 활성화)");
		check_thr_enable.setToolTipText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.TOOLTIP_NE_INFO_FIELD_THR_ENABLE));
		check_thr_enable.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		check_thr_enable.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				selectThreshold_enable();

				if (info_field != null) {
					boolean thr_enable = check_thr_enable.getSelection();
					info_field.setThr_enable(thr_enable);
					listener.updated(info_field);
				}
			}
		});

		label_thr_event = new LabelText(parent, SWT.NONE);
		label_thr_event.setText("Thr_event");
		label_thr_event.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));

		data_thr_event = new DataComboEvent();
		combo_thr_event = new SelectorCombo(parent, SWT.BORDER | SWT.READ_ONLY, data_thr_event);
		combo_thr_event.getControl().setToolTipText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.TOOLTIP_NE_INFO_FIELD_THR_EVENT));
		combo_thr_event.getControl().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		combo_thr_event.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection selection = (IStructuredSelection) event.getSelection();
				if (!selection.isEmpty()) {
					if (info_field != null) {
						Object thr_event = selection.getFirstElement();
						info_field.setThr_event_code(thr_event instanceof Integer ? (Integer) thr_event : 0);
						listener.updated(info_field);
					}
				}
			}
		});

		label_thr_type = new LabelText(parent, SWT.NONE);
		label_thr_type.setText("Thr_type");
		label_thr_type.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));

		combo_thr_type = new SelectorCombo(parent, SWT.BORDER | SWT.READ_ONLY, new DataComboSimple((Object[]) THRESHOLD_TYPE.values()));
		combo_thr_type.getControl().setToolTipText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.TOOLTIP_NE_INFO_FIELD_THR_TYPE));
		combo_thr_type.getControl().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		combo_thr_type.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection selection = (IStructuredSelection) event.getSelection();
				if (!selection.isEmpty()) {
					if (info_field != null) {
						THRESHOLD_TYPE thr_type = (THRESHOLD_TYPE) selection.getFirstElement();
						if (thr_type.equals(THRESHOLD_TYPE.GT)) {
							text_thr_critical_min.setEnabled(false);
							text_thr_critical_min.setText("");
							text_thr_major_min.setEnabled(false);
							text_thr_major_min.setText("");
							text_thr_minor_min.setEnabled(false);
							text_thr_minor_min.setText("");
							text_thr_critical_max.setEnabled(check_thr_critical.getSelection());
							text_thr_major_max.setEnabled(check_thr_major.getSelection());
							text_thr_minor_max.setEnabled(check_thr_minor.getSelection());
							info_field.setThr_critical_min(0L);
							info_field.setThr_major_min(0L);
							info_field.setThr_minor_min(0L);
						} else if (thr_type.equals(THRESHOLD_TYPE.LT)) {
							text_thr_critical_min.setEnabled(check_thr_critical.getSelection());
							text_thr_major_min.setEnabled(check_thr_major.getSelection());
							text_thr_minor_min.setEnabled(check_thr_minor.getSelection());
							text_thr_critical_max.setEnabled(false);
							text_thr_critical_max.setText("");
							text_thr_major_max.setEnabled(false);
							text_thr_major_max.setText("");
							text_thr_minor_max.setEnabled(false);
							text_thr_minor_max.setText("");
							info_field.setThr_critical_max(0L);
							info_field.setThr_major_max(0L);
							info_field.setThr_minor_max(0L);
						} else {
							text_thr_critical_min.setEnabled(check_thr_critical.getSelection());
							text_thr_major_min.setEnabled(check_thr_major.getSelection());
							text_thr_minor_min.setEnabled(check_thr_minor.getSelection());
							text_thr_critical_max.setEnabled(check_thr_critical.getSelection());
							text_thr_major_max.setEnabled(check_thr_major.getSelection());
							text_thr_minor_max.setEnabled(check_thr_minor.getSelection());
						}
						info_field.setThr_type(thr_type);
						listener.updated(info_field);
					}
				}
			}
		});

		new Label(parent, SWT.NONE);

		Composite composite_thr_value = new Composite(parent, SWT.NONE);
		composite_thr_value.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		composite_thr_value.setLayout(new GridLayout(5, false));
		{
			check_thr_critical = new Button(composite_thr_value, SWT.CHECK);
			check_thr_critical.setText("Critical");
			check_thr_critical.setToolTipText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.TOOLTIP_NE_INFO_FIELD_THR_CRITICAL_ENABLE));
			check_thr_critical.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					THRESHOLD_TYPE thr_type = (THRESHOLD_TYPE) combo_thr_type.getValue();
					if (thr_type.equals(THRESHOLD_TYPE.GT)) {
						text_thr_critical_min.setEnabled(false);
						text_thr_critical_min.setText("");
						text_thr_critical_max.setEnabled(check_thr_critical.getSelection());
					} else if (thr_type.equals(THRESHOLD_TYPE.LT)) {
						text_thr_critical_min.setEnabled(check_thr_critical.getSelection());
						text_thr_critical_max.setEnabled(false);
						text_thr_critical_max.setText("");
					} else {
						text_thr_critical_min.setEnabled(check_thr_critical.getSelection());
						text_thr_critical_max.setEnabled(check_thr_critical.getSelection());
					}
				}
			});

			LabelText label_thr_critical_min = new LabelText(composite_thr_value, SWT.NONE, false);
			label_thr_critical_min.setText("    Min");

			text_thr_critical_min = new TextInput4Integer64(composite_thr_value, SWT.BORDER);
			text_thr_critical_min.setToolTipText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.TOOLTIP_NE_INFO_FIELD_THR_CRITICAL_MIN));
			text_thr_critical_min.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
			text_thr_critical_min.addModifyListener(new ModifyListener() {
				@Override
				public void modifyText(ModifyEvent arg0) {
					if (info_field != null) {
						THRESHOLD_TYPE thr_type = (THRESHOLD_TYPE) combo_thr_type.getValue();
						if (thr_type.equals(THRESHOLD_TYPE.GT)) {
							info_field.setThr_critical_min(0L);
						} else {
							Long thr_critical_min = text_thr_critical_min.getValue();
							info_field.setThr_critical_min(thr_critical_min);
						}

					}
				}
			});

			LabelText label_thr_critical_max = new LabelText(composite_thr_value, SWT.NONE, false);
			label_thr_critical_max.setText("    Max");

			text_thr_critical_max = new TextInput4Integer64(composite_thr_value, SWT.BORDER);
			text_thr_critical_max.setToolTipText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.TOOLTIP_NE_INFO_FIELD_THR_CRITICAL_MAX));
			text_thr_critical_max.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
			text_thr_critical_max.addModifyListener(new ModifyListener() {
				@Override
				public void modifyText(ModifyEvent arg0) {
					if (info_field != null) {
						THRESHOLD_TYPE thr_type = (THRESHOLD_TYPE) combo_thr_type.getValue();
						if (thr_type.equals(THRESHOLD_TYPE.LT)) {
							info_field.setThr_critical_max(0L);
						} else {
							Long thr_critical_max = text_thr_critical_max.getValue();
							info_field.setThr_critical_max(thr_critical_max);
						}
					}
				}
			});
		}

		{
			check_thr_major = new Button(composite_thr_value, SWT.CHECK);
			check_thr_major.setText("Major");
			check_thr_major.setToolTipText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.TOOLTIP_NE_INFO_FIELD_THR_MAJOR_ENABLE));
			check_thr_major.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					THRESHOLD_TYPE thr_type = (THRESHOLD_TYPE) combo_thr_type.getValue();
					if (thr_type.equals(THRESHOLD_TYPE.GT)) {
						text_thr_major_min.setEnabled(false);
						text_thr_major_min.setText("");
						text_thr_major_max.setEnabled(check_thr_major.getSelection());
					} else if (thr_type.equals(THRESHOLD_TYPE.LT)) {
						text_thr_major_min.setEnabled(check_thr_major.getSelection());
						text_thr_major_max.setEnabled(false);
						text_thr_major_max.setText("");
					} else {
						text_thr_major_min.setEnabled(check_thr_major.getSelection());
						text_thr_major_max.setEnabled(check_thr_major.getSelection());
					}
				}
			});

			LabelText label_thr_major_min = new LabelText(composite_thr_value, SWT.NONE, false);
			label_thr_major_min.setText("    Min");

			text_thr_major_min = new TextInput4Integer64(composite_thr_value, SWT.BORDER);
			text_thr_major_min.setToolTipText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.TOOLTIP_NE_INFO_FIELD_THR_MAJOR_MIN));
			text_thr_major_min.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
			text_thr_major_min.addModifyListener(new ModifyListener() {
				@Override
				public void modifyText(ModifyEvent arg0) {
					if (info_field != null) {
						THRESHOLD_TYPE thr_type = (THRESHOLD_TYPE) combo_thr_type.getValue();
						if (thr_type.equals(THRESHOLD_TYPE.GT)) {
							info_field.setThr_major_min(0L);
						} else {
							Long thr_major_min = text_thr_major_min.getValue();
							info_field.setThr_major_min(thr_major_min);
						}
					}
				}
			});

			LabelText label_thr_major_max = new LabelText(composite_thr_value, SWT.NONE, false);
			label_thr_major_max.setText("    Max");

			text_thr_major_max = new TextInput4Integer64(composite_thr_value, SWT.BORDER);
			text_thr_major_max.setToolTipText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.TOOLTIP_NE_INFO_FIELD_THR_MAJOR_MAX));
			text_thr_major_max.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
			text_thr_major_max.addModifyListener(new ModifyListener() {
				@Override
				public void modifyText(ModifyEvent arg0) {
					if (info_field != null) {
						THRESHOLD_TYPE thr_type = (THRESHOLD_TYPE) combo_thr_type.getValue();
						if (thr_type.equals(THRESHOLD_TYPE.LT)) {
							info_field.setThr_major_max(0L);
						} else {
							Long thr_major_max = text_thr_major_max.getValue();
							info_field.setThr_major_max(thr_major_max);
						}
					}
				}
			});
		}

		{
			check_thr_minor = new Button(composite_thr_value, SWT.CHECK);
			check_thr_minor.setText("Minor");
			check_thr_minor.setToolTipText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.TOOLTIP_NE_INFO_FIELD_THR_MINOR_ENABLE));
			check_thr_minor.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					THRESHOLD_TYPE thr_type = (THRESHOLD_TYPE) combo_thr_type.getValue();
					if (thr_type.equals(THRESHOLD_TYPE.GT)) {
						text_thr_minor_min.setEnabled(false);
						text_thr_minor_min.setText("");
						text_thr_minor_max.setEnabled(check_thr_minor.getSelection());
					} else if (thr_type.equals(THRESHOLD_TYPE.LT)) {
						text_thr_minor_min.setEnabled(check_thr_minor.getSelection());
						text_thr_minor_max.setEnabled(false);
						text_thr_minor_max.setText("");
					} else {
						text_thr_minor_min.setEnabled(check_thr_minor.getSelection());
						text_thr_minor_max.setEnabled(check_thr_minor.getSelection());
					}
				}
			});

			LabelText label_thr_minor_min = new LabelText(composite_thr_value, SWT.NONE, false);
			label_thr_minor_min.setText("    Min");

			text_thr_minor_min = new TextInput4Integer64(composite_thr_value, SWT.BORDER);
			text_thr_minor_min.setToolTipText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.TOOLTIP_NE_INFO_FIELD_THR_MINOR_MIN));
			text_thr_minor_min.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
			text_thr_minor_min.addModifyListener(new ModifyListener() {
				@Override
				public void modifyText(ModifyEvent arg0) {
					if (info_field != null) {
						THRESHOLD_TYPE thr_type = (THRESHOLD_TYPE) combo_thr_type.getValue();
						if (thr_type.equals(THRESHOLD_TYPE.GT)) {
							info_field.setThr_minor_min(0L);
						} else {
							Long thr_minor_min = text_thr_minor_min.getValue();
							info_field.setThr_minor_min(thr_minor_min);
						}
					}
				}
			});

			LabelText label_thr_minor_max = new LabelText(composite_thr_value, SWT.NONE, false);
			label_thr_minor_max.setText("    Max");

			text_thr_minor_max = new TextInput4Integer64(composite_thr_value, SWT.BORDER);
			text_thr_minor_max.setToolTipText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.TOOLTIP_NE_INFO_FIELD_THR_MINOR_MAX));
			text_thr_minor_max.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
			text_thr_minor_max.addModifyListener(new ModifyListener() {
				@Override
				public void modifyText(ModifyEvent arg0) {
					if (info_field != null) {
						THRESHOLD_TYPE thr_type = (THRESHOLD_TYPE) combo_thr_type.getValue();
						if (thr_type.equals(THRESHOLD_TYPE.LT)) {
							info_field.setThr_minor_max(0L);
						} else {
							Long thr_minor_max = text_thr_minor_max.getValue();
							info_field.setThr_minor_max(thr_minor_max);
						}
					}
				}
			});
		}
	}

	public void displayDetail(EMP_MODEL_NE_INFO_FIELD info_field, EMP_MODEL_ENUM[] enum_defs, EMP_MODEL_EVENT[] event_defs) {
		this.info_field = info_field;

		if (info_field == null) {
			setTitle(UtilString.format("{}", EMP_MODEL.NE_INFO_FIELD));
			image_severity.setImage(null);
			label_error.setText("");

			displayDetailGeneral(null, null, null);
			displayDetailStatistics(null, null, null);
			displayDetailThreshold(null, null, null);
		} else {
			setTitle(UtilString.format("{} - {} - {}", EMP_MODEL.NE_INFO_FIELD, info_field.getNe_info().getName(), info_field.getName()));
			image_severity.setImage(UtilResource4Orange.getImage(info_field.getSeverity(), false));
			label_error.setText(info_field.getError());

			displayDetailGeneral(info_field, enum_defs, event_defs);
			displayDetailStatistics(info_field, enum_defs, event_defs);
			displayDetailThreshold(info_field, enum_defs, event_defs);
		}

		applyDetail();
		scrolledData.setMinSize(panelData.computeSize(0, SWT.DEFAULT));
	}

	public void displayDetail() {
		if (info_field == null) {
			setTitle(UtilString.format("{}", EMP_MODEL.NE_INFO_FIELD));
			image_severity.setImage(null);
			label_error.setText("");
		} else {
			setTitle(UtilString.format("{} - {} - {}", EMP_MODEL.NE_INFO_FIELD, info_field.getNe_info().getName(), info_field.getName()));
			image_severity.setImage(UtilResource4Orange.getImage(info_field.getSeverity(), false));
			label_error.setText(info_field.getError());
		}
	}

	private void displayDetailGeneral(EMP_MODEL_NE_INFO_FIELD info_field, EMP_MODEL_ENUM[] enum_defs, EMP_MODEL_EVENT[] event_defs) {
		if (info_field == null) {
			text_name.setText("");
			text_display_name.setText("");
			check_display_enable.setSelection(true);
			text_unit.setText("");
			check_virtual.setSelection(false);
			text_oid.setText("");
			data_type_remote.setDatas();
			data_type_remote.refresh();
			combo_type_remote.setSelectedItem(data_type_remote.getDefaultItem());
			SNMP_TYPE snmp_type = UtilString.isEmpty(String.valueOf(combo_type_remote.getSelectedItem())) ? null : SNMP_TYPE.valueOf(String.valueOf(combo_type_remote.getSelectedItem()));
			changeDataTypeLocal(snmp_type);
			combo_type_local.setSelectedItem(combo_type_local.getDefaultItem());
			text_field_script.setText("");
			data_enum.setDatas();
			data_enum.refresh();
			combo_enum.setSelectedItem(data_enum.getDefaultItem());
			check_index.setSelection(false);
			check_read.setSelection(false);
			check_update.setSelection(false);
		} else {
			if (!text_name.getText().trim().equals(info_field.getName())) {
				text_name.setText(info_field.getName());
			}
			if (!text_display_name.getText().trim().equals(info_field.getDisplay_name())) {
				text_display_name.setText(info_field.getDisplay_name());
			}
			check_display_enable.setSelection(info_field.isDisplay_enable());
			if (!text_unit.getText().trim().equals(info_field.getUnit())) {
				text_unit.setText(info_field.getUnit());
			}
			check_virtual.setSelection(info_field.isVirtual_enable());
			label_oid.setMandatory(!check_virtual.getSelection());
			text_oid.setEnabled(!check_virtual.getSelection());
			if (!text_oid.getText().trim().equals(info_field.getOid() == null ? "" : info_field.getOid())) {
				text_oid.setText(info_field.getOid() == null ? "" : info_field.getOid());
			}
			label_type_remote.setMandatory(!check_virtual.getSelection());
			data_type_remote.setDatas((Object[]) EMP_MODEL_NE_INFO_FIELD.getType_reals(info_field.getNe_info().getProtocol()));
			data_type_remote.refresh();
			combo_type_remote.getControl().setEnabled(!check_virtual.getSelection());
			combo_type_remote.setSelectedItem(UtilString.isEmpty(info_field.getType_remote()) ? combo_type_remote.getDefaultItem() : info_field.getType_remote());
			String type_remote = UtilString.isEmpty(String.valueOf(info_field.getType_remote())) ? String.valueOf(combo_type_remote.getDefaultItem()) : info_field.getType_remote();
			changeDataTypeLocal(check_virtual.getSelection() ? null : SNMP_TYPE.valueOf(type_remote));
			combo_type_local.setSelectedItem(info_field.getType_local());
			label_field_script.setMandatory(check_virtual.getSelection());
			text_field_script.setEnabled(check_virtual.getSelection());
			if (!text_field_script.getText().trim().equals(info_field.getField_script())) {
				text_field_script.setText(info_field.getField_script());
			}
			button_field_script.setEnabled(check_virtual.getSelection());
			data_enum.setDatas((Object[]) enum_defs);
			data_enum.refresh();
			combo_enum.setSelectedItem(info_field.getEnum_code() == 0 ? data_enum.getDefaultItem() : info_field.getEnum_code());
			check_index.setEnabled(!check_virtual.getSelection());
			check_index.setSelection(info_field.isIndex());
			check_read.setSelection(info_field.isRead());
			check_update.setEnabled(!check_virtual.getSelection());
			check_update.setSelection(info_field.isUpdate());
		}
	}

	private void displayDetailStatistics(EMP_MODEL_NE_INFO_FIELD info_field, EMP_MODEL_ENUM[] enum_defs, EMP_MODEL_EVENT[] event_defs) {
		EMP_MODEL_TYPE emp_model_type = (EMP_MODEL_TYPE) combo_type_local.getSelectedItem();
		if (info_field == null) {
			check_stat_label.setEnabled(false);
			check_stat_label.setSelection(false);
			check_stat_enable.setEnabled(false);
			check_stat_enable.setSelection(false);
			check_chart_default.setEnabled(false);
			check_chart_default.setSelection(false);
			combo_stat_save.getControl().setEnabled(false);
			combo_stat_save.setSelectedItem(combo_stat_save.getDefaultItem());
			combo_stat_aggr.getControl().setEnabled(false);
			combo_stat_aggr.setSelectedItem(combo_stat_aggr.getDefaultItem());
		} else if (!info_field.getNe_info().isStat_enable()) {
			check_stat_label.setEnabled(false);
			check_stat_label.setSelection(false);
			check_stat_enable.setEnabled(false);
			check_stat_enable.setSelection(false);
			check_chart_default.setEnabled(false);
			check_chart_default.setSelection(false);
			combo_stat_save.getControl().setEnabled(false);
			combo_stat_save.setSelectedItem(info_field.getStat_save() == null ? combo_stat_save.getDefaultItem() : info_field.getStat_save());
			combo_stat_aggr.getControl().setEnabled(false);
			combo_stat_aggr.setSelectedItem(info_field.getStat_aggr() == null ? combo_stat_aggr.getDefaultItem() : info_field.getStat_aggr());
		} else if (!emp_model_type.equals(EMP_MODEL_TYPE.INT_32) && !emp_model_type.equals(EMP_MODEL_TYPE.INT_64)) {
			check_stat_label.setEnabled(true);
			check_stat_label.setSelection(info_field.isStat_label());
			check_stat_enable.setEnabled(false);
			check_stat_enable.setSelection(false);
			check_chart_default.setEnabled(false);
			check_chart_default.setSelection(false);
			combo_stat_save.getControl().setEnabled(false);
			combo_stat_save.setSelectedItem(info_field.getStat_save() == null ? combo_stat_save.getDefaultItem() : info_field.getStat_save());
			combo_stat_aggr.getControl().setEnabled(false);
			combo_stat_aggr.setSelectedItem(info_field.getStat_aggr() == null ? combo_stat_aggr.getDefaultItem() : info_field.getStat_aggr());
		} else {
			check_stat_label.setEnabled(true);
			check_stat_label.setSelection(info_field.isStat_label());
			check_stat_enable.setEnabled(true);
			check_stat_enable.setSelection(info_field.isStat_enable());
			if (info_field.isStat_enable()) {
				check_chart_default.setEnabled(true);
				check_chart_default.setSelection(info_field.isChart_default());
			} else {
				check_chart_default.setEnabled(false);
				check_chart_default.setSelection(false);
			}
			label_stat_save.setMandatory(check_stat_enable.getSelection());
			combo_stat_save.getControl().setEnabled(check_stat_enable.getSelection());
			combo_stat_save.setSelectedItem(info_field.getStat_save() == null ? combo_stat_save.getDefaultItem() : info_field.getStat_save());
			label_stat_aggr.setMandatory(check_stat_enable.getSelection());
			combo_stat_aggr.getControl().setEnabled(check_stat_enable.getSelection());
			combo_stat_aggr.setSelectedItem(info_field.getStat_aggr() == null ? combo_stat_aggr.getDefaultItem() : info_field.getStat_aggr());
		}
	}

	private void displayDetailThreshold(EMP_MODEL_NE_INFO_FIELD info_field, EMP_MODEL_ENUM[] enum_defs, EMP_MODEL_EVENT[] event_defs) {
		if (info_field == null) {
			check_thr_enable.setEnabled(false);
			check_thr_enable.setSelection(false);
			data_thr_event.setDatas();
			data_thr_event.refresh();
			combo_thr_event.getControl().setEnabled(false);
			combo_thr_event.setSelectedItem(combo_thr_event.getDefaultItem());
			combo_thr_type.getControl().setEnabled(false);
			combo_thr_type.setSelectedItem(combo_thr_type.getDefaultItem());

			check_thr_critical.setEnabled(false);
			check_thr_critical.setSelection(false);
			text_thr_critical_min.setEnabled(false);
			if (text_thr_critical_min.getValue() != null) {
				text_thr_critical_min.setValue(null);
			}
			text_thr_critical_max.setEnabled(false);
			if (text_thr_critical_max.getValue() != null) {
				text_thr_critical_max.setValue(null);
			}

			check_thr_major.setEnabled(false);
			check_thr_major.setSelection(false);
			text_thr_major_min.setEnabled(false);
			if (text_thr_major_min.getValue() != null) {
				text_thr_major_min.setValue(null);
			}
			text_thr_major_max.setEnabled(false);
			if (text_thr_major_max.getValue() != null) {
				text_thr_major_max.setValue(null);
			}

			check_thr_minor.setEnabled(false);
			check_thr_minor.setSelection(false);
			text_thr_minor_min.setEnabled(false);
			if (text_thr_minor_min.getValue() != null) {
				text_thr_minor_min.setValue(null);
			}
			text_thr_minor_max.setEnabled(false);
			if (text_thr_minor_max.getValue() != null) {
				text_thr_minor_max.setValue(null);
			}
		} else if (!info_field.getNe_info().isStat_enable() || !info_field.isStat_enable()) {
			check_thr_enable.setEnabled(false);
			check_thr_enable.setSelection(false);
			data_thr_event.setDatas((Object[]) event_defs);
			data_thr_event.refresh();
			combo_thr_event.getControl().setEnabled(false);
			combo_thr_event.setSelectedItem(info_field.getThr_event_code() == 0 ? combo_thr_event.getDefaultItem() : info_field.getThr_event_code());
			combo_thr_type.getControl().setEnabled(false);
			combo_thr_type.setSelectedItem(info_field.getThr_type() == null ? combo_thr_type.getDefaultItem() : info_field.getThr_type());

			check_thr_critical.setEnabled(false);
			check_thr_critical.setSelection(false);
			text_thr_critical_min.setEnabled(false);
			if (info_field.getThr_critical_min() == null) {
				if (text_thr_critical_min.getValue() != null) {
					text_thr_critical_min.setValue(null);
				}
			} else if (!info_field.getThr_critical_min().equals(text_thr_critical_min.getValue())) {
				text_thr_critical_min.setValue(info_field.getThr_critical_min());
			}
			text_thr_critical_max.setEnabled(false);
			if (info_field.getThr_critical_max() == null) {
				if (text_thr_critical_max.getValue() != null) {
					text_thr_critical_max.setValue(null);
				}
			} else if (!info_field.getThr_critical_max().equals(text_thr_critical_max.getValue())) {
				text_thr_critical_max.setValue(info_field.getThr_critical_max());
			}

			check_thr_major.setEnabled(false);
			check_thr_major.setSelection(false);
			text_thr_major_min.setEnabled(false);
			if (info_field.getThr_major_min() == null) {
				if (text_thr_major_min.getValue() != null) {
					text_thr_major_min.setValue(null);
				}
			} else if (!info_field.getThr_major_min().equals(text_thr_major_min.getValue())) {
				text_thr_major_min.setValue(info_field.getThr_major_min());
			}
			text_thr_major_max.setEnabled(false);
			if (info_field.getThr_major_max() == null) {
				if (text_thr_major_max.getValue() != null) {
					text_thr_major_max.setValue(null);
				}
			} else if (!info_field.getThr_major_max().equals(text_thr_major_max.getValue())) {
				text_thr_major_max.setValue(info_field.getThr_major_max());
			}

			check_thr_minor.setEnabled(false);
			check_thr_minor.setSelection(false);
			text_thr_minor_min.setEnabled(false);
			if (info_field.getThr_minor_min() == null) {
				if (text_thr_minor_min.getValue() != null) {
					text_thr_minor_min.setValue(null);
				}
			} else if (!info_field.getThr_minor_min().equals(text_thr_minor_min.getValue())) {
				text_thr_minor_min.setValue(info_field.getThr_minor_min());
			}
			text_thr_minor_max.setEnabled(false);
			if (info_field.getThr_minor_max() == null) {
				if (text_thr_minor_max.getValue() != null) {
					text_thr_minor_max.setValue(null);
				}
			} else if (!info_field.getThr_minor_max().equals(text_thr_minor_max.getValue())) {
				text_thr_minor_max.setValue(info_field.getThr_minor_max());
			}

			THRESHOLD_TYPE thr_type = info_field.getThr_type();
			if (thr_type == null || thr_type.equals(THRESHOLD_TYPE.GT)) {
				text_thr_critical_min.setText("");
				text_thr_major_min.setText("");
				text_thr_minor_min.setText("");
			} else if (thr_type.equals(THRESHOLD_TYPE.LT)) {
				text_thr_critical_max.setText("");
				text_thr_major_max.setText("");
				text_thr_minor_max.setText("");
			}
		} else {
			check_thr_enable.setEnabled(true);
			check_thr_enable.setSelection(info_field.isThr_enable());
			data_thr_event.setDatas((Object[]) event_defs);
			data_thr_event.refresh();
			label_thr_event.setMandatory(check_thr_enable.getSelection());
			combo_thr_event.getControl().setEnabled(check_thr_enable.getSelection());
			combo_thr_event.setSelectedItem(info_field.getThr_event_code() == 0 ? combo_thr_event.getDefaultItem() : info_field.getThr_event_code());
			label_thr_type.setMandatory(check_thr_enable.getSelection());
			combo_thr_type.getControl().setEnabled(check_thr_enable.getSelection());
			combo_thr_type.setSelectedItem(info_field.getThr_type() == null ? combo_thr_type.getDefaultItem() : info_field.getThr_type());

			check_thr_critical.setEnabled(check_thr_enable.getSelection());
			check_thr_critical.setSelection(check_thr_enable.getSelection() ? info_field.isThr_critical_enable() : false);
			text_thr_critical_min.setEnabled(check_thr_critical.getSelection());
			if (info_field.getThr_critical_min() == null) {
				if (text_thr_critical_min.getValue() != null) {
					text_thr_critical_min.setValue(null);
				}
			} else if (!info_field.getThr_critical_min().equals(text_thr_critical_min.getValue())) {
				text_thr_critical_min.setValue(info_field.getThr_critical_min());
			}
			text_thr_critical_max.setEnabled(check_thr_critical.getSelection());
			if (info_field.getThr_critical_max() == null) {
				if (text_thr_critical_max.getValue() != null) {
					text_thr_critical_max.setValue(null);
				}
			} else if (!info_field.getThr_critical_max().equals(text_thr_critical_max.getValue())) {
				text_thr_critical_max.setValue(info_field.getThr_critical_max());
			}

			check_thr_major.setEnabled(check_thr_enable.getSelection());
			check_thr_major.setSelection(check_thr_enable.getSelection() ? info_field.isThr_major_enable() : false);
			text_thr_major_min.setEnabled(check_thr_major.getSelection());
			if (info_field.getThr_major_min() == null) {
				if (text_thr_major_min.getValue() != null) {
					text_thr_major_min.setValue(null);
				}
			} else if (!info_field.getThr_major_min().equals(text_thr_major_min.getValue())) {
				text_thr_major_min.setValue(info_field.getThr_major_min());
			}
			text_thr_major_max.setEnabled(check_thr_major.getSelection());
			if (info_field.getThr_major_max() == null) {
				if (text_thr_major_max.getValue() != null) {
					text_thr_major_max.setValue(null);
				}
			} else if (!info_field.getThr_major_max().equals(text_thr_major_max.getValue())) {
				text_thr_major_max.setValue(info_field.getThr_major_max());
			}

			check_thr_minor.setEnabled(check_thr_enable.getSelection());
			check_thr_minor.setSelection(check_thr_enable.getSelection() ? info_field.isThr_minor_enable() : false);
			text_thr_minor_min.setEnabled(check_thr_minor.getSelection());
			if (info_field.getThr_minor_min() == null) {
				if (text_thr_minor_min.getValue() != null) {
					text_thr_minor_min.setValue(null);
				}
			} else if (!info_field.getThr_minor_min().equals(text_thr_minor_min.getValue())) {
				text_thr_minor_min.setValue(info_field.getThr_minor_min());
			}
			text_thr_minor_max.setEnabled(check_thr_minor.getSelection());
			if (info_field.getThr_minor_max() == null) {
				if (text_thr_minor_max.getValue() != null) {
					text_thr_minor_max.setValue(null);
				}
			} else if (!info_field.getThr_minor_max().equals(text_thr_minor_max.getValue())) {
				text_thr_minor_max.setValue(info_field.getThr_minor_max());
			}

			THRESHOLD_TYPE thr_type = info_field.getThr_type();
			if (thr_type == null || thr_type.equals(THRESHOLD_TYPE.GT)) {
				text_thr_critical_min.setEnabled(false);
				text_thr_critical_min.setText("");
				text_thr_major_min.setEnabled(false);
				text_thr_major_min.setText("");
				text_thr_minor_min.setEnabled(false);
				text_thr_minor_min.setText("");
			} else if (thr_type.equals(THRESHOLD_TYPE.LT)) {
				text_thr_critical_max.setEnabled(false);
				text_thr_critical_max.setText("");
				text_thr_major_max.setEnabled(false);
				text_thr_major_max.setText("");
				text_thr_minor_max.setEnabled(false);
				text_thr_minor_max.setText("");
			}
		}
	}

	private void applyDetail() {
		if (info_field != null) {
			String type_remote = (String) combo_type_remote.getValue();
			info_field.setType_remote(type_remote instanceof String ? (String) type_remote : null);

			Object type_local = combo_type_local.getValue();
			info_field.setType_local(type_local instanceof EMP_MODEL_TYPE ? (EMP_MODEL_TYPE) type_local : null);

			Object stat_save = combo_stat_save.getValue();
			info_field.setStat_save(stat_save instanceof STATISTICS_SAVE ? (STATISTICS_SAVE) stat_save : null);

			Object stat_aggr = combo_stat_aggr.getValue();
			info_field.setStat_aggr(stat_aggr instanceof STATISTICS_AGGREGATION ? (STATISTICS_AGGREGATION) stat_aggr : null);

			THRESHOLD_TYPE thr_type = (THRESHOLD_TYPE) combo_thr_type.getValue();
			info_field.setThr_type(thr_type);
			if (thr_type.equals(THRESHOLD_TYPE.GT)) {
				info_field.setThr_critical_min(0L);
				info_field.setThr_major_min(0L);
				info_field.setThr_minor_min(0L);
			} else if (thr_type.equals(THRESHOLD_TYPE.LT)) {
				info_field.setThr_critical_max(0L);
				info_field.setThr_major_max(0L);
				info_field.setThr_minor_max(0L);
			}
		}
	}

	private void changeDataTypeLocal(SNMP_TYPE snmp_type) {
		EMP_MODEL_TYPE[] type_locals = snmp_type == null ? EMP_MODEL_TYPE.values() : snmp_type.getModel_types();
		boolean updateNeInfoCodes = combo_type_local.isNeedUpdate((Object) type_locals);
		if (updateNeInfoCodes) {
			Object selectedItem = combo_type_local.getSelectedItem();
			combo_type_local.setDatas((Object) type_locals);
			combo_type_local.setSelectedItem(combo_type_local.findElement(selectedItem));
			EMP_MODEL_TYPE emp_model_type = (EMP_MODEL_TYPE) combo_type_local.getSelectedItem();
			changeDataTypeLocal(emp_model_type);
		}
	}

	private void changeDataTypeLocal(EMP_MODEL_TYPE emp_model_type) {
		if (emp_model_type != null && (emp_model_type.equals(EMP_MODEL_TYPE.INT_32) || emp_model_type.equals(EMP_MODEL_TYPE.INT_64))) {
			check_stat_enable.setEnabled(info_field.getNe_info().isStat_enable());
		} else {
			check_stat_enable.setEnabled(false);
			check_stat_enable.setSelection(false);
		}

		selectStatistics_enable();
	}

	private void selectStatistics_enable() {
		label_stat_save.setMandatory(check_stat_enable.getSelection());
		combo_stat_save.getControl().setEnabled(check_stat_enable.getSelection());

		label_stat_aggr.setMandatory(check_stat_enable.getSelection());
		combo_stat_aggr.getControl().setEnabled(check_stat_enable.getSelection());

		check_chart_default.setEnabled(check_stat_enable.getSelection());
		check_chart_default.setSelection(check_stat_enable.getSelection());
		
		check_thr_enable.setEnabled(check_stat_enable.getSelection());
		if (!check_stat_enable.getSelection()) {
			check_thr_enable.setSelection(false);
		}

		selectThreshold_enable();
	}

	private void selectThreshold_enable() {
		label_thr_event.setMandatory(check_thr_enable.getSelection());
		combo_thr_event.getControl().setEnabled(check_thr_enable.getSelection());

		label_thr_type.setMandatory(check_thr_enable.getSelection());
		combo_thr_type.getControl().setEnabled(check_thr_enable.getSelection());

		check_thr_critical.setEnabled(check_thr_enable.getSelection());
		text_thr_critical_min.setEnabled(check_thr_enable.getSelection() && check_thr_critical.getSelection());
		text_thr_critical_max.setEnabled(check_thr_enable.getSelection() && check_thr_critical.getSelection());

		check_thr_major.setEnabled(check_thr_enable.getSelection());
		text_thr_major_min.setEnabled(check_thr_enable.getSelection() && check_thr_major.getSelection());
		text_thr_major_max.setEnabled(check_thr_enable.getSelection() && check_thr_major.getSelection());

		check_thr_minor.setEnabled(check_thr_enable.getSelection());
		text_thr_minor_min.setEnabled(check_thr_enable.getSelection() && check_thr_minor.getSelection());
		text_thr_minor_max.setEnabled(check_thr_enable.getSelection() && check_thr_minor.getSelection());

		THRESHOLD_TYPE thr_type = (THRESHOLD_TYPE) combo_thr_type.getValue();
		if (thr_type.equals(THRESHOLD_TYPE.GT)) {
			text_thr_critical_min.setEnabled(false);
			text_thr_major_min.setEnabled(false);
			text_thr_minor_min.setEnabled(false);
		} else if (thr_type.equals(THRESHOLD_TYPE.LT)) {
			text_thr_critical_max.setEnabled(false);
			text_thr_major_max.setEnabled(false);
			text_thr_minor_max.setEnabled(false);
		}
	}

}
