package com.hellonms.platforms.emp_orange.client_swt.widget.page;

import java.io.Serializable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.hellonms.platforms.emp_onion.client_swt.widget.text.TextInput4Integer32;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO_FIELD;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_TYPE;

public class PanelField4Integer32 extends PanelFieldAt {

	protected TextInput4Integer32 text4Integer32;

	public PanelField4Integer32(Composite parent, EMP_MODEL_NE_INFO ne_info_def, EMP_MODEL_NE_INFO_FIELD ne_info_field_def, boolean enabled, PanelFieldListenerIf listener) {
		super(parent, ne_info_def, ne_info_field_def, enabled, listener);
	}

	@Override
	protected Control createControl(Composite parent, int style) {
		// if (0 == ne_info_field_def.getValue_min() && 0 == ne_info_field_def.getValue_max()) {
		text4Integer32 = new TextInput4Integer32(parent, SWT.BORDER | style);
		// } else {
		// text4Integer32 = new TextInput4Integer32(parent, SWT.BORDER | style, (int) ne_info_field_def.getValue_min(), (int) ne_info_field_def.getValue_max());
		// }
		text4Integer32.setText(EMP_MODEL_TYPE.INT_32.toDisplay(neFieldValue));
		text4Integer32.setEditable(enabled);
		text4Integer32.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				listener.checkComplete();
			}
		});

		return text4Integer32;
	}

	@Override
	public boolean isComplete() {
		if (!enabled) {
			return true;
		}
		return text4Integer32.isComplete() && !text4Integer32.getText().equals("");
	}

	@Override
	public Serializable getNeFieldValue() {
		String int32 = text4Integer32.getText();
		return EMP_MODEL_TYPE.INT_32.fromDisplay(int32);
	}

	@Override
	public void setNeFieldValue(Serializable neFieldValue) {
		this.neFieldValue = neFieldValue;
		text4Integer32.setText(EMP_MODEL_TYPE.INT_32.toDisplay(neFieldValue));
	}

	public void setMin_value(long min_value) {
		text4Integer32.setMin_value(min_value);
	}

	public void setMax_value(long max_value) {
		text4Integer32.setMax_value(max_value);
	}

	@Override
	public boolean isEnabled() {
		return text4Integer32.getEditable();
	}

	@Override
	public void setEnabled(boolean enabled) {
		text4Integer32.setEditable(enabled);
	}

}
