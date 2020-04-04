package com.hellonms.platforms.emp_orange.client_swt.widget.page;

import java.io.Serializable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.hellonms.platforms.emp_onion.client_swt.widget.text.TextInput4Ipv6Address;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO_FIELD;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_TYPE;

public class PanelField4IpV6 extends PanelFieldAt {

	protected TextInput4Ipv6Address text4IpV6;

	public PanelField4IpV6(Composite parent, EMP_MODEL_NE_INFO ne_info_def, EMP_MODEL_NE_INFO_FIELD ne_info_field_def, boolean enabled, PanelFieldListenerIf listener) {
		super(parent, ne_info_def, ne_info_field_def, enabled, listener);
	}

	@Override
	protected Control createControl(Composite parent, int style) {
		text4IpV6 = new TextInput4Ipv6Address(parent, SWT.BORDER | style);
		text4IpV6.setValue(EMP_MODEL_TYPE.IP_V6.toDisplay(neFieldValue));
		text4IpV6.setEnabled(enabled);
		text4IpV6.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				listener.checkComplete();
			}
		});

		return text4IpV6;
	}

	@Override
	public boolean isComplete() {
		if (!enabled) {
			return true;
		}
		return text4IpV6.isComplete() && !text4IpV6.getText().equals("");
	}

	@Override
	public Serializable getNeFieldValue() {
		String ipV6 = text4IpV6.getText();
		return EMP_MODEL_TYPE.IP_V6.fromDisplay(ipV6);
	}

	@Override
	public void setNeFieldValue(Serializable neFieldValue) {
		this.neFieldValue = neFieldValue;
		text4IpV6.setValue(EMP_MODEL_TYPE.IP_V6.toDisplay(neFieldValue));
	}

}
