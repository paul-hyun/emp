package com.hellonms.platforms.emp_orange.client_swt.widget.page;

import java.io.Serializable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.hellonms.platforms.emp_onion.client_swt.widget.label.LabelText;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO_FIELD;
import com.hellonms.platforms.emp_util.string.UtilString;

public class PanelField4LabelText extends PanelFieldAt {

	protected LabelText labelTextName;

	public PanelField4LabelText(Composite parent, EMP_MODEL_NE_INFO ne_info_def, EMP_MODEL_NE_INFO_FIELD ne_info_field_def, boolean enabled, PanelFieldListenerIf listener) {
		super(parent, ne_info_def, ne_info_field_def, enabled, listener);
	}

	@Override
	protected Control createControl(Composite parent, int style) {
		labelTextName = new LabelText(parent, SWT.NONE, enabled);
		labelTextName.setText(UtilString.isEmpty(ne_info_field_def.getDisplay_name()) ? ne_info_field_def.getName() : ne_info_field_def.getDisplay_name());
		StringBuilder stringBuilder = new StringBuilder();
		// stringBuilder.append(ne_info_field_def.getDescription().replaceAll("\\\\r", "\r").replaceAll("\\\\n", "\n"));
		// if (ne_info_field_def.getValue_min() != ne_info_field_def.getValue_max()) {
		// stringBuilder.append("\n").append(" * min: ").append(ne_info_field_def.getValue_min());
		// stringBuilder.append("\n").append(" * max: ").append(ne_info_field_def.getValue_max());
		// }
		if (0 < stringBuilder.length()) {
			labelTextName.setToolTipText(stringBuilder.toString());
		}
		labelTextName.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				parent.forceFocus();
			}
		});

		return labelTextName;
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
	}

}
