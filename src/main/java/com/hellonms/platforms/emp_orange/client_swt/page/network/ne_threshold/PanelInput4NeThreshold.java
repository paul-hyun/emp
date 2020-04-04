package com.hellonms.platforms.emp_orange.client_swt.page.network.ne_threshold;

import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import com.hellonms.platforms.emp_core.share.language.UtilLanguage;
import com.hellonms.platforms.emp_onion.client_swt.widget.button.ButtonCheck;
import com.hellonms.platforms.emp_onion.client_swt.widget.dialog.DialogMessage;
import com.hellonms.platforms.emp_onion.client_swt.widget.label.LabelText;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelInputAt;
import com.hellonms.platforms.emp_onion.client_swt.widget.text.TextInput;
import com.hellonms.platforms.emp_onion.client_swt.widget.text.TextInput4Integer64;
import com.hellonms.platforms.emp_onion.theme.ThemeBuilder4Onion.COLOR_ONION;
import com.hellonms.platforms.emp_onion.theme.ThemeFactory;
import com.hellonms.platforms.emp_orange.share.error.ERROR_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO_FIELD;
import com.hellonms.platforms.emp_orange.share.model.fault.event.SEVERITY;
import com.hellonms.platforms.emp_orange.share.model.network.ne_info.Model4NeThresholdIf;

/**
 * <p>
 * PanelInput4NeThreshold
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class PanelInput4NeThreshold extends PanelInputAt<Model4NeThresholdIf> implements PanelInput4NeThresholdIf {

	private class GroupNeThresholdSeverity extends Group {

		private SEVERITY severity;

		private ButtonCheck button_enable;

		private TextInput4Integer64 text_min;

		private TextInput4Integer64 text_max;

		public GroupNeThresholdSeverity(Composite parent, int style, SEVERITY severity) {
			super(parent, style);
			this.severity = severity;

			switch (ne_info_field_def.getThr_type()) {
			case GT:
				createGT();
				break;
			case LT:
				createLT();
				break;
			case BT:
				createBT();
				break;
			case OT:
				createOT();
				break;
			}
		}

		private void createGT() {
			GridLayout gridLayout = new GridLayout(7, false);
			gridLayout.verticalSpacing = 10;
			setLayout(gridLayout);

			button_enable = createButtonCheck();

			LabelText label_value1 = new LabelText(this, SWT.NONE);
			label_value1.setText(" # ");

			LabelText label_compare1 = new LabelText(this, SWT.NONE);
			label_compare1.setText(" > ");

			text_max = createTextInput4Integer64();
		}

		private void createLT() {
			GridLayout gridLayout = new GridLayout(7, false);
			gridLayout.verticalSpacing = 10;
			setLayout(gridLayout);

			button_enable = createButtonCheck();

			LabelText label_value1 = new LabelText(this, SWT.NONE);
			label_value1.setText(" # ");

			LabelText label_compare1 = new LabelText(this, SWT.NONE);
			label_compare1.setText(" < ");

			text_min = createTextInput4Integer64();
		}

		private void createBT() {
			GridLayout gridLayout = new GridLayout(7, false);
			gridLayout.verticalSpacing = 10;
			setLayout(gridLayout);

			button_enable = createButtonCheck();

			text_min = createTextInput4Integer64();

			LabelText label_compare1 = new LabelText(this, SWT.NONE);
			label_compare1.setText(" < ");

			LabelText label_value1 = new LabelText(this, SWT.NONE);
			label_value1.setText(" # ");

			LabelText label_compare2 = new LabelText(this, SWT.NONE);
			label_compare2.setText(" < ");

			text_max = createTextInput4Integer64();
		}

		private void createOT() {
			GridLayout gridLayout = new GridLayout(7, false);
			gridLayout.verticalSpacing = 10;
			setLayout(gridLayout);

			button_enable = createButtonCheck();

			LabelText label_value1 = new LabelText(this, SWT.NONE);
			label_value1.setText(" # ");

			LabelText label_compare1 = new LabelText(this, SWT.NONE);
			label_compare1.setText(" < ");

			text_min = createTextInput4Integer64();

			LabelText label_or = new LabelText(this, SWT.NONE);
			label_or.setText("  OR  ");

			text_max = createTextInput4Integer64();

			LabelText label_compare2 = new LabelText(this, SWT.NONE);
			label_compare2.setText(" < ");

			LabelText label_value2 = new LabelText(this, SWT.NONE);
			label_value2.setText(" # ");
		}

		private ButtonCheck createButtonCheck() {
			ButtonCheck button = new ButtonCheck(this);
			button.setText(severity.toString());
			button.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 7, 1));
			button.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					if (text_min != null) {
						text_min.setEnabled(button_enable.getSelection());
					}
					if (text_max != null) {
						text_max.setEnabled(button_enable.getSelection());
					}
					PanelInput4NeThreshold.this.checkComplete();
				}
			});
			return button;
		}

		private TextInput4Integer64 createTextInput4Integer64() {
			TextInput4Integer64 text = new TextInput4Integer64(this, SWT.BORDER);
			text.getControl().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
			text.setEnabled(button_enable.getSelection());
			text.addModifyListener(new ModifyListener() {
				@Override
				public void modifyText(ModifyEvent e) {
					PanelInput4NeThreshold.this.checkComplete();
				}
			});
			return text;
		}

		@Override
		protected void checkSubclass() {
		}

		public boolean isComplete() {
			if (button_enable != null && button_enable.getSelection()) {
				if (text_min != null && text_min.getValue() == null) {
					return false;
				}
				if (text_max != null && text_max.getValue() == null) {
					return false;
				}
			}
			return true;
		}

		public String getErrorMessage() {
			if (button_enable != null && button_enable.getSelection()) {
				if (text_min != null && text_min.getValue() == null) {
					return UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.INSERT_VALUE, severity);
				}
				if (text_max != null && text_max.getValue() == null) {
					return UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.INSERT_VALUE, severity);
				}
			}
			return null;
		}

		public void clear() {
			if (button_enable != null) {
				button_enable.setSelection(false);
			}
			if (text_min != null) {
				text_min.setText("");
			}
			if (text_max != null) {
				text_max.setText("");
			}
		}

		public void display(Model4NeThresholdIf model) {
			Long min;
			Long max;
			switch (severity) {
			case CRITICAL:
				min = model.getThreshold_critical_min(ne_info_field_def);
				max = model.getThreshold_critical_max(ne_info_field_def);
				break;
			case MAJOR:
				min = model.getThreshold_major_min(ne_info_field_def);
				max = model.getThreshold_major_max(ne_info_field_def);
				break;
			case MINOR:
				min = model.getThreshold_minor_min(ne_info_field_def);
				max = model.getThreshold_minor_max(ne_info_field_def);
				break;
			default:
				min = null;
				max = null;
				break;
			}

			if (button_enable != null) {
				button_enable.setSelection(min != null && max != null);
			}
			if (text_min != null) {
				text_min.setText(min == null ? "" : String.valueOf(min));
				text_min.setEnabled(button_enable.getSelection());
			}
			if (text_max != null) {
				text_max.setText(max == null ? "" : String.valueOf(max));
				text_max.setEnabled(button_enable.getSelection());
			}
		}

		public void apply(Model4NeThresholdIf model) {
			Long min = null;
			Long max = null;
			if (button_enable != null && button_enable.getSelection()) {
				if (text_min != null) {
					min = text_min.getValue();
				} else {
					min = 0L;
				}
				if (text_max != null) {
					max = text_max.getValue();
				} else {
					max = 0L;
				}
			}
			switch (severity) {
			case CRITICAL:
				model.setThreshold_critical_min(ne_info_field_def, min);
				model.setThreshold_critical_max(ne_info_field_def, max);
				break;
			case MAJOR:
				model.setThreshold_major_min(ne_info_field_def, min);
				model.setThreshold_major_max(ne_info_field_def, max);
				break;
			case MINOR:
				model.setThreshold_minor_min(ne_info_field_def, min);
				model.setThreshold_minor_max(ne_info_field_def, max);
				break;
			default:
				break;
			}
		}

	}

	protected PANEL_INPUT_TYPE panelInputType;

	protected PanelInputListenerIf listener;

	protected ScrolledComposite scrolledComposite;

	protected Composite panelValue;

	protected EMP_MODEL_NE_INFO ne_info_def;
	//
	protected EMP_MODEL_NE_INFO_FIELD ne_info_field_def;

	protected Map<SEVERITY, GroupNeThresholdSeverity> groupSeverityMap = new LinkedHashMap<SEVERITY, GroupNeThresholdSeverity>();

	protected Object[] datas;

	public PanelInput4NeThreshold(Composite parent, int style, PANEL_INPUT_TYPE panelInputType, PanelInputListenerIf listener, EMP_MODEL_NE_INFO ne_info_def, EMP_MODEL_NE_INFO_FIELD ne_info_field_def, Object[] datas) {
		super(parent, style, panelInputType, listener);

		this.panelInputType = panelInputType;
		this.listener = listener;
		this.ne_info_def = ne_info_def;
		this.ne_info_field_def = ne_info_field_def;
		this.datas = datas;

		createGUI();
	}

	protected void createGUI() {
		getContentComposite().setLayout(new FillLayout(SWT.HORIZONTAL));

		scrolledComposite = new ScrolledComposite(getContentComposite(), SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite.setBackground(ThemeFactory.getColor(COLOR_ONION.PANEL_CONTENTS_BG));
		scrolledComposite.setBackgroundMode(SWT.INHERIT_DEFAULT);
		scrolledComposite.getHorizontalBar().setIncrement(8);
		scrolledComposite.getVerticalBar().setIncrement(8);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);

		panelValue = new Composite(scrolledComposite, SWT.NONE);
		panelValue.setLayout(new GridLayout(2, false));

		LabelText label_field = new LabelText(panelValue, SWT.NONE);
		label_field.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.FIELD));
		label_field.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));

		TextInput text_field = new TextInput(panelValue, SWT.BORDER | SWT.READ_ONLY);
		text_field.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		text_field.setText(ne_info_field_def.getName());

		new Label(panelValue, SWT.NONE);

		GroupNeThresholdSeverity group_critical = new GroupNeThresholdSeverity(panelValue, SWT.NONE, SEVERITY.CRITICAL);
		group_critical.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		groupSeverityMap.put(group_critical.severity, group_critical);

		new Label(panelValue, SWT.NONE);

		GroupNeThresholdSeverity group_major = new GroupNeThresholdSeverity(panelValue, SWT.NONE, SEVERITY.MAJOR);
		group_major.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		groupSeverityMap.put(group_major.severity, group_major);

		new Label(panelValue, SWT.NONE);

		GroupNeThresholdSeverity group_minor = new GroupNeThresholdSeverity(panelValue, SWT.NONE, SEVERITY.MINOR);
		group_minor.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		groupSeverityMap.put(group_minor.severity, group_minor);

		panelValue.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				panelValue.forceFocus();
			}
		});

		scrolledComposite.setContent(panelValue);
		scrolledComposite.setMinSize(panelValue.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	}

	protected void checkComplete() {
		if (model == null) {
			setErrorMessage(UtilLanguage.getMessage(ERROR_CODE_ORANGE.MODEL_NOTEXISTS, MESSAGE_CODE_ORANGE.NE_INFO, ne_info_def == null ? "" : ne_info_def));
			setComplete(false);
		} else {
			boolean complete = true;

			for (GroupNeThresholdSeverity groupSeverity : groupSeverityMap.values()) {
				if (!groupSeverity.isComplete()) {
					setErrorMessage(groupSeverity.getErrorMessage());
					setComplete(false);
					complete = false;
					break;
				}
			}

			if (complete) {
				setErrorMessage(null);
				setComplete(true);
			}
		}

		listener.completeChanged();
	}

	@Override
	public Model4NeThresholdIf getModel() {
		applyModel();
		return model;
	}

	@Override
	public void setModel(Model4NeThresholdIf model) {
		if (model == null) {
			this.model = null;
		} else {
			this.model = (Model4NeThresholdIf) model.copy();
		}
		displayModel();
	}

	public void display(Model4NeThresholdIf ne_threshold) {
		setModel(ne_threshold);
	}

	@Override
	public void clear() {
		for (GroupNeThresholdSeverity groupSeverity : groupSeverityMap.values()) {
			groupSeverity.clear();
		}
	}

	protected void displayModel() {
		if (model == null) {
			DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NE_INFO), UtilLanguage.getMessage(ERROR_CODE_ORANGE.MODEL_NOTEXISTS, MESSAGE_CODE_ORANGE.NE_INFO, ne_info_def == null ? "" : ne_info_def));
			return;
		}

		for (GroupNeThresholdSeverity groupSeverity : groupSeverityMap.values()) {
			groupSeverity.display(model);
		}

		checkComplete();
	}

	protected void applyModel() {
		if (model != null) {
			for (GroupNeThresholdSeverity groupSeverity : groupSeverityMap.values()) {
				groupSeverity.apply(model);
			}
		}
	}

	protected boolean isEnabled(EMP_MODEL_NE_INFO_FIELD neFieldCode) {
		switch (panelInputType) {
		case UPDATE:
			return neFieldCode.isUpdate();
		default:
			return false;
		}
	}

	@Override
	public int getStartNo() {
		return 0;
	}

	@Override
	public Model4NeThresholdIf getSelected() {
		return getModel();
	}

	@Override
	public EMP_MODEL_NE_INFO_FIELD getNe_field_code() {
		return ne_info_field_def;
	}

	@Override
	public boolean isNeedWizard() {
		return false;
	}

}
