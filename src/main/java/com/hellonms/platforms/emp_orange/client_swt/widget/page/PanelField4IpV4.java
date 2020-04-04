package com.hellonms.platforms.emp_orange.client_swt.widget.page;

import java.io.Serializable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.hellonms.platforms.emp_onion.client_swt.widget.text.TextInput4IpAddress;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO_FIELD;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_TYPE;

public class PanelField4IpV4 extends PanelFieldAt {

	protected TextInput4IpAddress text4IpV4;

	public PanelField4IpV4(Composite parent, EMP_MODEL_NE_INFO ne_info_def, EMP_MODEL_NE_INFO_FIELD ne_info_field_def, boolean enabled, PanelFieldListenerIf listener) {
		super(parent, ne_info_def, ne_info_field_def, enabled, listener);
	}

	@Override
	protected Control createControl(Composite parent, int style) {
		text4IpV4 = new TextInput4IpAddress(parent, SWT.BORDER | style);
		text4IpV4.setValue(EMP_MODEL_TYPE.IP_V4.toDisplay(neFieldValue));
		text4IpV4.setEnabled(enabled);
		text4IpV4.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				listener.checkComplete();
			}
		});

		return text4IpV4;
	}

	@Override
	public boolean isComplete() {
		if (!enabled) {
			return true;
		}
		return text4IpV4.isComplete() && !text4IpV4.getText().equals("");
	}

	@Override
	public Serializable getNeFieldValue() {
		String ipV4 = text4IpV4.getText();
		return EMP_MODEL_TYPE.IP_V4.fromDisplay(ipV4);
	}

	@Override
	public void setNeFieldValue(Serializable neFieldValue) {
		this.neFieldValue = neFieldValue;
		text4IpV4.setValue(EMP_MODEL_TYPE.IP_V4.toDisplay(neFieldValue));
	}

}
