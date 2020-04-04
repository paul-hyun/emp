package com.hellonms.platforms.emp_orange.client_swt.widget.page;

import java.io.Serializable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.hellonms.platforms.emp_onion.client_swt.widget.label.LabelText;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO_FIELD;

public class PanelField4Unknown extends PanelFieldAt {

	protected LabelText labelUnknown;

	public PanelField4Unknown(Composite parent, EMP_MODEL_NE_INFO ne_info_def, EMP_MODEL_NE_INFO_FIELD ne_info_field_def, boolean enabled, PanelFieldListenerIf listener) {
		super(parent, ne_info_def, ne_info_field_def, enabled, listener);
	}

	@Override
	protected Control createControl(Composite parent, int style) {
		labelUnknown = new LabelText(parent, SWT.BORDER | style);
		labelUnknown.setText(neFieldValue == null ? "" : neFieldValue.toString());

		return labelUnknown;
	}

	@Override
	public boolean isComplete() {
		return true;
	}

	@Override
	public Serializable getNeFieldValue() {
		return null;
	}

	@Override
	public void setNeFieldValue(Serializable neFieldValue) {
		this.neFieldValue = neFieldValue;
		labelUnknown.setText(neFieldValue == null ? "" : neFieldValue.toString());
	}

}
