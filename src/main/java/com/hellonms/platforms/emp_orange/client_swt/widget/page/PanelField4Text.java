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

public class PanelField4Text extends PanelFieldAt {

	protected TextInput4String text4String;

	public PanelField4Text(Composite parent, EMP_MODEL_NE_INFO ne_info_def, EMP_MODEL_NE_INFO_FIELD ne_info_field_def, boolean enabled, PanelFieldListenerIf listener) {
		super(parent, ne_info_def, ne_info_field_def, enabled, listener);
	}

	@Override
	protected Control createControl(Composite parent, int style) {
		// if (0 == ne_info_field_def.getValue_min() && 0 == ne_info_field_def.getValue_max()) {
		text4String = new TextInput4String(parent, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL | style);
		// } else {
		// text4String = new TextInput4String(parent, SWT.BORDER | style, (int) ne_info_field_def.getValue_min(), (int) ne_info_field_def.getValue_max());
		// }
		text4String.setText(EMP_MODEL_TYPE.STRING.toDisplay(neFieldValue));
		text4String.setEditable(enabled);
		text4String.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				listener.checkComplete();
			}
		});

		return text4String;
	}

	@Override
	public boolean isComplete() {
		if (!enabled) {
			return true;
		}
		return text4String.isComplete() && !text4String.getText().equals("");
	}

	@Override
	public Serializable getNeFieldValue() {
		String string = text4String.getText();
		return EMP_MODEL_TYPE.STRING.fromDisplay(string);
	}

	@Override
	public void setNeFieldValue(Serializable neFieldValue) {
		this.neFieldValue = neFieldValue;
		text4String.setText(EMP_MODEL_TYPE.STRING.toDisplay(neFieldValue));
	}

	@Override
	public boolean isEnabled() {
		return text4String.getEditable();
	}

	@Override
	public void setEnabled(boolean enabled) {
		text4String.setEditable(enabled);
	}

}
