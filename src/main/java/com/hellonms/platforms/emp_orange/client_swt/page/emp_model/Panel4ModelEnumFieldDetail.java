package com.hellonms.platforms.emp_orange.client_swt.page.emp_model;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.hellonms.platforms.emp_core.share.language.UtilLanguage;
import com.hellonms.platforms.emp_onion.client_swt.widget.label.LabelImage;
import com.hellonms.platforms.emp_onion.client_swt.widget.label.LabelText;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.Panel;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelRound;
import com.hellonms.platforms.emp_onion.client_swt.widget.text.TextInput;
import com.hellonms.platforms.emp_orange.client_swt.util.resource.UtilResource4Orange;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_ENUM_FIELD;
import com.hellonms.platforms.emp_orange.share.model.fault.event.SEVERITY;
import com.hellonms.platforms.emp_util.string.UtilString;

public class Panel4ModelEnumFieldDetail extends Panel {

	public interface Panel4ModelEnumFieldDetailListnerIf extends Panel4ModelListenerIf {

		public void updated(EMP_MODEL_ENUM_FIELD enum_field);

	}

	private LabelImage image_severity;
	private LabelText label_error;

	private TextInput text_name;
	private TextInput text_value;

	private EMP_MODEL_ENUM_FIELD enum_field;

	private Panel4ModelEnumFieldDetailListnerIf listener;

	public Panel4ModelEnumFieldDetail(Composite parent, int style, Panel4ModelEnumFieldDetailListnerIf listener) {
		super(parent, style, EMP_MODEL.ENUM_FIELD);
		this.listener = listener;

		createGUI();
	}

	/**
	 * GUI를 생성합니다.
	 */
	protected void createGUI() {
		getContentComposite().setLayout(new FormLayout());

		PanelRound panelError = new PanelRound(getContentComposite(), SWT.NONE);
		FormData fd_panelError = new FormData();
		fd_panelError.top = new FormAttachment(0, 5);
		fd_panelError.left = new FormAttachment(0, 5);
		fd_panelError.right = new FormAttachment(100, -5);
		panelError.setLayoutData(fd_panelError);
		{
			panelError.getContentComposite().setLayout(new GridLayout(2, false));

			image_severity = new LabelImage(panelError.getContentComposite(), SWT.NONE, UtilResource4Orange.getImage(SEVERITY.CLEAR, false));
			image_severity.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));

			label_error = new LabelText(panelError.getContentComposite(), SWT.NONE);
			label_error.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		}

		PanelRound panelData = new PanelRound(getContentComposite(), SWT.NONE);
		FormData fd_panelData = new FormData();
		fd_panelData.top = new FormAttachment(panelError, 5, SWT.BOTTOM);
		fd_panelData.bottom = new FormAttachment(100, -5);
		fd_panelData.right = new FormAttachment(100, -5);
		fd_panelData.left = new FormAttachment(0, 5);
		panelData.setLayoutData(fd_panelData);
		{
			panelData.getContentComposite().setLayout(new GridLayout(2, false));

			LabelText label_name = new LabelText(panelData.getContentComposite(), SWT.NONE, true);
			label_name.setText("Name");
			label_name.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));

			text_name = new TextInput(panelData.getContentComposite(), SWT.BORDER);
			text_name.setToolTipText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.TOOLTIP_ENUM_FIELD_NAME));
			text_name.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
			text_name.addModifyListener(new ModifyListener() {
				@Override
				public void modifyText(ModifyEvent arg0) {
					if (enum_field != null) {
						String name = text_name.getText().trim();
						enum_field.setName(name);
						listener.updated(enum_field);
					}
				}
			});

			LabelText label_value = new LabelText(panelData.getContentComposite(), SWT.NONE, true);
			label_value.setText("Value");
			label_value.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));

			text_value = new TextInput(panelData.getContentComposite(), SWT.BORDER);
			text_value.setToolTipText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.TOOLTIP_ENUM_FIELD_VALUE));
			text_value.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
			text_value.addModifyListener(new ModifyListener() {
				@Override
				public void modifyText(ModifyEvent arg0) {
					if (enum_field != null) {
						String value = text_value.getText().trim();
						enum_field.setValue(value);
						listener.updated(enum_field);
					}
				}
			});
		}
	}

	public void displayDetail(EMP_MODEL_ENUM_FIELD enum_field) {
		this.enum_field = enum_field;

		if (enum_field == null) {
			setTitle(UtilString.format("{}", EMP_MODEL.ENUM_FIELD));
			image_severity.setImage(null);
			label_error.setText("");
			text_name.setText("");
			text_value.setText("");
		} else {
			setTitle(UtilString.format("{} - {} - {}", EMP_MODEL.ENUM_FIELD, enum_field.getEnum().getName(), enum_field.getName()));
			image_severity.setImage(UtilResource4Orange.getImage(enum_field.getSeverity(), false));
			label_error.setText(enum_field.getError());
			if (!text_name.getText().trim().equals(enum_field.getName())) {
				text_name.setText(enum_field.getName());
			}
			if (!text_value.getText().trim().equals(enum_field.getValue())) {
				text_value.setText(enum_field.getValue());
			}
		}
	}

	public void displayDetail() {
		if (enum_field == null) {
			setTitle(UtilString.format("{}", EMP_MODEL.ENUM_FIELD));
			image_severity.setImage(null);
			label_error.setText("");
		} else {
			setTitle(UtilString.format("{} - {} - {}", EMP_MODEL.ENUM_FIELD, enum_field.getEnum().getName(), enum_field.getName()));
			image_severity.setImage(UtilResource4Orange.getImage(enum_field.getSeverity(), false));
			label_error.setText(enum_field.getError());
		}
	}

}
