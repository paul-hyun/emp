package com.hellonms.platforms.emp_orange.client_swt.widget.page;

import java.io.Serializable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.hellonms.platforms.emp_onion.client_swt.widget.text.TextInput4Integer64;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO_FIELD;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_TYPE;

public class PanelField4Integer64 extends PanelFieldAt {

	protected TextInput4Integer64 textInteger64;

	public PanelField4Integer64(Composite parent, EMP_MODEL_NE_INFO ne_info_def, EMP_MODEL_NE_INFO_FIELD ne_info_field_def, boolean enabled, PanelFieldListenerIf listener) {
		super(parent, ne_info_def, ne_info_field_def, enabled, listener);
	}

	@Override
	protected Control createControl(Composite parent, int style) {
		// if (0 == ne_info_field_def.getValue_min() && 0 == ne_info_field_def.getValue_max()) {
		textInteger64 = new TextInput4Integer64(parent, SWT.BORDER | style);
		// } else {
		// textInteger64 = new TextInput4Integer64(parent, SWT.BORDER | style, ne_info_field_def.getValue_min(), ne_info_field_def.getValue_max());
		// }
		textInteger64.setText(EMP_MODEL_TYPE.INT_64.toDisplay(neFieldValue));
		textInteger64.setEditable(enabled);
		textInteger64.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				listener.checkComplete();
			}
		});

		return textInteger64;
	}

	@Override
	public boolean isComplete() {
		if (!enabled) {
			return true;
		}
		return textInteger64.isComplete() && !textInteger64.getText().equals("");
	}

	@Override
	public Serializable getNeFieldValue() {
		String int64 = textInteger64.getText();
		return EMP_MODEL_TYPE.INT_64.fromDisplay(int64);
	}

	@Override
	public void setNeFieldValue(Serializable neFieldValue) {
		this.neFieldValue = neFieldValue;
		textInteger64.setText(EMP_MODEL_TYPE.INT_64.toDisplay(neFieldValue));
	}

	public void setMin_value(long min_value) {
		textInteger64.setMin_value(min_value);
	}

	public void setMax_value(long max_value) {
		textInteger64.setMax_value(max_value);
	}

	@Override
	public boolean isEnabled() {
		return textInteger64.getEditable();
	}

	@Override
	public void setEnabled(boolean enabled) {
		textInteger64.setEditable(enabled);
	}

}
