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
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_ENUM;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_ENUM_FIELD;
import com.hellonms.platforms.emp_orange.share.model.fault.event.SEVERITY;
import com.hellonms.platforms.emp_util.string.UtilString;

public class Panel4ModelEnumDetail extends Panel {

	public interface Panel4ModelEnumDetailListnerIf extends Panel4ModelListenerIf {

		public void created(EMP_MODEL_ENUM_FIELD enum_field);

		public void updated(EMP_MODEL_ENUM enum_def);

	}

	private LabelImage image_severity;
	private LabelText label_error;

	private TextInput text_name;

	private EMP_MODEL_ENUM enum_def;

	private Panel4ModelEnumDetailListnerIf listener;

	public Panel4ModelEnumDetail(Composite parent, int style, Panel4ModelEnumDetailListnerIf listener) {
		super(parent, style, EMP_MODEL.ENUM);
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
			text_name.setToolTipText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.TOOLTIP_ENUM_NAME));
			text_name.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
			text_name.addModifyListener(new ModifyListener() {
				@Override
				public void modifyText(ModifyEvent arg0) {
					if (enum_def != null) {
						String name = text_name.getText().trim();
						enum_def.setName(name);
						listener.updated(enum_def);
					}
				}
			});
		}
	}

	public void displayDetail(EMP_MODEL_ENUM enum_def) {
		this.enum_def = enum_def;

		if (enum_def == null) {
			setTitle(UtilString.format("{}", EMP_MODEL.ENUM));
			image_severity.setImage(null);
			label_error.setText("");
			text_name.setText("");
		} else {
			setTitle(UtilString.format("{} - {}", EMP_MODEL.ENUM, enum_def.getName()));
			image_severity.setImage(UtilResource4Orange.getImage(enum_def.getSeverity(), false));
			label_error.setText(enum_def.getError());
			if (!text_name.getText().trim().equals(enum_def.getName())) {
				text_name.setText(enum_def.getName());
			}
		}
	}

	public void displayDetail() {
		if (enum_def == null) {
			setTitle(UtilString.format("{}", EMP_MODEL.ENUM));
			image_severity.setImage(null);
			label_error.setText("");
		} else {
			setTitle(UtilString.format("{} - {}", EMP_MODEL.ENUM, enum_def.getName()));
			image_severity.setImage(UtilResource4Orange.getImage(enum_def.getSeverity(), false));
			label_error.setText(enum_def.getError());
		}
	}

}
