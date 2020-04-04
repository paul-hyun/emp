package com.hellonms.platforms.emp_orange.client_swt.widget.page;

import java.io.Serializable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.hellonms.platforms.emp_onion.client_swt.widget.text.TextInput4String;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO_FIELD;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_TYPE;

public class PanelField4Date extends PanelFieldAt {

	protected TextInput4String text4Date;

	public PanelField4Date(Composite parent, EMP_MODEL_NE_INFO ne_info_def, EMP_MODEL_NE_INFO_FIELD ne_info_field_def, boolean enabled, PanelFieldListenerIf listener) {
		super(parent, ne_info_def, ne_info_field_def, enabled, listener);
	}

	@Override
	protected Control createControl(Composite parent, int style) {
		text4Date = new TextInput4String(parent, SWT.BORDER | style);
		text4Date.setValue(EMP_MODEL_TYPE.DATE.toDisplay(neFieldValue));
		text4Date.setEditable(enabled);
		text4Date.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				listener.checkComplete();
			}
		});

		return text4Date;
	}

	@Override
	public boolean isComplete() {
		if (!enabled) {
			return true;
		}
		return text4Date.isComplete() && !text4Date.getText().equals("");
	}

	@Override
	public Serializable getNeFieldValue() {
		String ipV4 = text4Date.getText();
		return EMP_MODEL_TYPE.DATE.fromDisplay(ipV4);
	}

	@Override
	public void setNeFieldValue(Serializable neFieldValue) {
		this.neFieldValue = neFieldValue;
		text4Date.setText(EMP_MODEL_TYPE.DATE.toDisplay(neFieldValue));
	}

	@Override
	public boolean isEnabled() {
		return text4Date.getEditable();
	}

	@Override
	public void setEnabled(boolean enabled) {
		text4Date.setEditable(enabled);
	}

}
