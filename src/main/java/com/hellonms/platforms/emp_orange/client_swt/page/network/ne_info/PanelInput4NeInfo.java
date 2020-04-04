package com.hellonms.platforms.emp_orange.client_swt.page.network.ne_info;

import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.hellonms.platforms.emp_core.share.language.UtilLanguage;
import com.hellonms.platforms.emp_onion.client_swt.widget.label.LabelText;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelInputAt;
import com.hellonms.platforms.emp_onion.theme.ThemeBuilder4Onion.COLOR_ONION;
import com.hellonms.platforms.emp_onion.theme.ThemeFactory;
import com.hellonms.platforms.emp_orange.client_swt.model_display.ModelDisplay4NeInfo;
import com.hellonms.platforms.emp_orange.client_swt.page.PanelFactory;
import com.hellonms.platforms.emp_orange.client_swt.widget.page.PanelField4Text;
import com.hellonms.platforms.emp_orange.client_swt.widget.page.PanelFieldAt.PanelFieldListenerIf;
import com.hellonms.platforms.emp_orange.client_swt.widget.page.PanelFieldIf;
import com.hellonms.platforms.emp_orange.share.error.ERROR_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO_FIELD;
import com.hellonms.platforms.emp_orange.share.model.network.ne_info.Model4NeInfoIf;
import com.hellonms.platforms.emp_util.string.UtilString;

/**
 * <p>
 * PanelInput4NeInfo
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class PanelInput4NeInfo extends PanelInputAt<Model4NeInfoIf> implements PanelInput4NeInfoIf {

	protected PANEL_INPUT_TYPE panelInputType;

	protected PanelInputListenerIf listener;

	protected ScrolledComposite scrolledComposite;

	protected Composite panelValue;

	protected EMP_MODEL_NE_INFO ne_info_def;

	protected EMP_MODEL_NE_INFO_FIELD[] ne_info_field_defs;

	protected Object[] datas;

	protected Map<EMP_MODEL_NE_INFO_FIELD, PanelFieldIf> neInfoFieldMap = new LinkedHashMap<EMP_MODEL_NE_INFO_FIELD, PanelFieldIf>();

	public PanelInput4NeInfo(Composite parent, int style, PANEL_INPUT_TYPE panelInputType, PanelInputListenerIf listener, EMP_MODEL_NE_INFO ne_info_def, Object[] datas) {
		super(parent, style, panelInputType, listener);

		this.panelInputType = panelInputType;
		this.listener = listener;
		this.ne_info_def = ne_info_def;
		this.ne_info_field_defs = ne_info_def.getNe_info_fields();
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
		panelValue.setLayout(new GridLayout(3, false));
		for (final EMP_MODEL_NE_INFO_FIELD ne_info_field_def : ne_info_field_defs) {
			if (ne_info_field_def.isDisplay_enable()) {
				PanelFieldIf panelFieldKey = PanelFactory.createPanelFieldKey(panelValue, ne_info_def, ne_info_field_def, isEnabled(ne_info_field_def), new PanelFieldListenerIf() {
					@Override
					public void checkComplete() {
						PanelInput4NeInfo.this.checkComplete();
					}
				});
				panelFieldKey.getControl().setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));

				PanelFieldIf panelFieldValue = PanelFactory.createPanelFieldValue(panelValue, ne_info_def, ne_info_field_def, isEnabled(ne_info_field_def), new PanelFieldListenerIf() {
					@Override
					public void checkComplete() {
						PanelInput4NeInfo.this.checkComplete();
					}
				});
				if (panelFieldValue instanceof PanelField4Text) {
					GridData gd_panelFieldValue = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
					gd_panelFieldValue.heightHint = 40;
					panelFieldValue.getControl().setLayoutData(gd_panelFieldValue);
				} else {
					panelFieldValue.getControl().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
				}

				if (UtilString.isEmpty(ne_info_field_def.getUnit())) {
					new LabelText(panelValue, SWT.NONE);
				} else {
					panelFieldValue.getControl().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

					LabelText labelUnit = new LabelText(panelValue, SWT.NONE);
					labelUnit.setText("(" + ne_info_field_def.getUnit() + ")");
					labelUnit.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1));
				}
				neInfoFieldMap.put(ne_info_field_def, panelFieldValue);
			}
		}
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

			for (PanelFieldIf fieldValue : neInfoFieldMap.values()) {
				if (!fieldValue.isComplete()) {
					setErrorMessage(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.INSERT_VALUE, fieldValue.getFieldName()));
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
	public Model4NeInfoIf getModel() {
		applyModel();
		return model;
	}

	@Override
	public void setModel(Model4NeInfoIf model) {
		if (model == null) {
			this.model = null;
		} else {
			this.model = (Model4NeInfoIf) model.copy();
		}
		displayModel();
	}

	public void display(ModelDisplay4NeInfo modelDisplay4Info) {
		setModel(modelDisplay4Info.getModel4NeInfo(0));
	}

	@Override
	public void clear() {
		for (final EMP_MODEL_NE_INFO_FIELD ne_info_field_def : ne_info_field_defs) {
			PanelFieldIf panelFieldValue = neInfoFieldMap.get(ne_info_field_def);
			if (panelFieldValue != null) {
				panelFieldValue.setNeFieldValue(null);
			}
		}
	}

	protected void displayModel() {
		for (final EMP_MODEL_NE_INFO_FIELD ne_info_field_def : ne_info_field_defs) {
			PanelFieldIf panelFieldValue = neInfoFieldMap.get(ne_info_field_def);
			if (panelFieldValue != null) {
				panelFieldValue.setNeFieldValue(model == null ? null : model.getField_value(ne_info_field_def));
			}
		}

		checkComplete();
	}

	protected void applyModel() {
		if (model != null) {
			for (PanelFieldIf neInfoField : neInfoFieldMap.values()) {
				if (neInfoField.getNeFieldDef().isUpdate()) {
					if (neInfoField.getNeFieldDef().getEnum_code() != 0) {
						model.setField_value(neInfoField.getNeFieldDef(), neInfoField.getNeFieldDef().getType_local().fromDisplay(String.valueOf(neInfoField.getNeFieldValue())));
					} else {
						model.setField_value(neInfoField.getNeFieldDef(), neInfoField.getNeFieldValue());
					}
				}
			}
		}
	}

	protected boolean isEnabled(EMP_MODEL_NE_INFO_FIELD ne_info_field_def) {
		switch (panelInputType) {
		case UPDATE:
			return ne_info_field_def.isUpdate();
		default:
			return false;
		}
	}

	@Override
	public int getStartNo() {
		return 0;
	}

	@Override
	public Model4NeInfoIf getSelected() {
		return getModel();
	}

	@Override
	public boolean isNeedWizard() {
		return false;
	}

}
