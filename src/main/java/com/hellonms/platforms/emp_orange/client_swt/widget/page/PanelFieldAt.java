package com.hellonms.platforms.emp_orange.client_swt.widget.page;

import java.io.Serializable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.hellonms.platforms.emp_onion.client_swt.util.resource.UtilResource;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO_FIELD;

public abstract class PanelFieldAt implements PanelFieldIf {

	public static Color DISABLED = UtilResource.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW);

	public interface PanelFieldListenerIf {

		public void checkComplete();

	}

	protected EMP_MODEL_NE_INFO ne_info_def;

	protected EMP_MODEL_NE_INFO_FIELD ne_info_field_def;

	protected Serializable neFieldValue;

	protected boolean enabled;

	protected PanelFieldListenerIf listener;

	protected Control control;

	public PanelFieldAt(Composite parent, EMP_MODEL_NE_INFO ne_info_def, EMP_MODEL_NE_INFO_FIELD ne_info_field_def, boolean enabled, PanelFieldListenerIf listener) {
		this.ne_info_def = ne_info_def;
		this.ne_info_field_def = ne_info_field_def;
		this.neFieldValue = null;
		this.enabled = enabled;
		this.listener = listener;

		control = createControl(parent, SWT.NONE);
	}

	protected abstract Control createControl(Composite parent, int style);

	@Override
	public Control getControl() {
		return control;
	}

	public boolean isChanged() {
		Serializable old_value = neFieldValue;
		Serializable new_value = getNeFieldValue();
		return (old_value != null && new_value != null && !old_value.equals(new_value));
	}

	@Override
	public EMP_MODEL_NE_INFO getNeInfoDef() {
		return ne_info_def;
	}

	@Override
	public EMP_MODEL_NE_INFO_FIELD getNeFieldDef() {
		return ne_info_field_def;
	}

	@Override
	public String getFieldName() {
		return ne_info_field_def.getName();
	}

	@Override
	public boolean isEnabled() {
		return control.isEnabled();
	}

	@Override
	public void setEnabled(boolean enabled) {
		control.setEnabled(enabled);
	}

}
