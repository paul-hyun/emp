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

public class PanelField4ArrayByte extends PanelFieldAt {

	protected TextInput4Integer32 text4ArrayByte;

	public PanelField4ArrayByte(Composite parent, EMP_MODEL_NE_INFO ne_info_def, EMP_MODEL_NE_INFO_FIELD ne_info_field_def, boolean enabled, PanelFieldListenerIf listener) {
		super(parent, ne_info_def, ne_info_field_def, enabled, listener);
	}

	@Override
	protected Control createControl(Composite parent, int style) {
		text4ArrayByte = new TextInput4Integer32(parent, SWT.BORDER | style, 0, 0x000000FF);
		text4ArrayByte.setText(EMP_MODEL_TYPE.ARRAY_BYTE.toDisplay(neFieldValue));
		text4ArrayByte.setEditable(enabled);
		text4ArrayByte.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				listener.checkComplete();
			}
		});

		return text4ArrayByte;
	}

	@Override
	public boolean isComplete() {
		if (!enabled) {
			return true;
		}
		return text4ArrayByte.isComplete() && !text4ArrayByte.getText().equals("");
	}

	@Override
	public Serializable getNeFieldValue() {
		String arrByte = text4ArrayByte.getText();
		return EMP_MODEL_TYPE.ARRAY_BYTE.fromDisplay(arrByte);
	}

	@Override
	public void setNeFieldValue(Serializable neFieldValue) {
		this.neFieldValue = neFieldValue;
		text4ArrayByte.setText(EMP_MODEL_TYPE.ARRAY_BYTE.toDisplay(neFieldValue));
	}

	@Override
	public boolean isEnabled() {
		return text4ArrayByte.getEditable();
	}

	@Override
	public void setEnabled(boolean enabled) {
		text4ArrayByte.setEditable(enabled);
	}

}
