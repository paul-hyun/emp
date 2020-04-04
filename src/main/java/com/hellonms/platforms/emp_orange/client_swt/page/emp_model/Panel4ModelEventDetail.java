package com.hellonms.platforms.emp_orange.client_swt.page.emp_model;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.hellonms.platforms.emp_core.share.language.UtilLanguage;
import com.hellonms.platforms.emp_onion.client_swt.widget.label.LabelImage;
import com.hellonms.platforms.emp_onion.client_swt.widget.label.LabelText;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.Panel;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelRound;
import com.hellonms.platforms.emp_onion.client_swt.widget.text.TextInput;
import com.hellonms.platforms.emp_orange.client_swt.util.resource.UtilResource4Orange;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_EVENT;
import com.hellonms.platforms.emp_orange.share.model.fault.event.SEVERITY;
import com.hellonms.platforms.emp_util.string.UtilString;

public class Panel4ModelEventDetail extends Panel {

	public interface Panel4ModelEventDetailListnerIf extends Panel4ModelListenerIf {

		public void updated(EMP_MODEL_EVENT event);

	}

	private LabelImage image_severity;
	private LabelText label_error;

	private TextInput text_specific_problem;
	private Button check_alarm;
	private Button check_audit_alarm;

	private EMP_MODEL_EVENT event;

	private Panel4ModelEventDetailListnerIf listener;

	public Panel4ModelEventDetail(Composite parent, int style, Panel4ModelEventDetailListnerIf listener) {
		super(parent, style, EMP_MODEL.EVENT);
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

			LabelText label_specific_problem = new LabelText(panelData.getContentComposite(), SWT.NONE, true);
			label_specific_problem.setText("Specific_problem");
			label_specific_problem.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));

			text_specific_problem = new TextInput(panelData.getContentComposite(), SWT.BORDER);
			text_specific_problem.setToolTipText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.TOOLTIP_EVENT_SPECIFIC_PROBLEM));
			text_specific_problem.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
			text_specific_problem.addModifyListener(new ModifyListener() {
				@Override
				public void modifyText(ModifyEvent arg0) {
					if (event != null) {
						String specific_problem = text_specific_problem.getText().trim();
						event.setSpecific_problem(specific_problem);
						listener.updated(event);
					}
				}
			});

			new Label(panelData.getContentComposite(), SWT.NONE);

			check_alarm = new Button(panelData.getContentComposite(), SWT.CHECK);
			check_alarm.setText("Alarm");
			check_alarm.setToolTipText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.TOOLTIP_EVENT_ALARM));
			check_alarm.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					boolean alarm = check_alarm.getSelection();
					if (alarm) {
						check_audit_alarm.setEnabled(true);
					} else {
						check_audit_alarm.setEnabled(false);
						check_audit_alarm.setSelection(false);
					}

					if (event != null) {
						event.setAlarm(alarm);
						listener.updated(event);
					}
				}
			});

			new Label(panelData.getContentComposite(), SWT.NONE);

			check_audit_alarm = new Button(panelData.getContentComposite(), SWT.CHECK);
			check_audit_alarm.setText("Audit Alarm (Alarm 체크 시 활성화)");
			check_audit_alarm.setToolTipText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.TOOLTIP_EVENT_AUDIT_ALARM));
			check_audit_alarm.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					if (event != null) {
						boolean alarm = check_alarm.getSelection();
						boolean audit_alarm = alarm ? check_audit_alarm.getSelection() : false;
						event.setAudit_alarm(audit_alarm);
						listener.updated(event);
					}
				}
			});
		}
	}

	public void displayDetail(EMP_MODEL_EVENT event) {
		this.event = event;

		if (event == null) {
			setTitle(UtilString.format("{}", EMP_MODEL.EVENT));
			image_severity.setImage(null);
			label_error.setText("");
			text_specific_problem.setText("");
		} else {
			setTitle(UtilString.format("{} - {}", EMP_MODEL.EVENT, event.getSpecific_problem()));
			image_severity.setImage(UtilResource4Orange.getImage(event.getSeverity(), false));
			label_error.setText(event.getError());
			if (!text_specific_problem.getText().trim().equals(event.getSpecific_problem())) {
				text_specific_problem.setText(event.getSpecific_problem());
			}
			check_alarm.setSelection(event.isAlarm());
			check_audit_alarm.setEnabled(event.isAlarm());
			check_audit_alarm.setSelection(event.isAlarm() && event.isAudit_alarm());
		}

		applyDetail();
	}

	public void displayDetail() {
		if (event == null) {
			setTitle(UtilString.format("{}", EMP_MODEL.EVENT));
			image_severity.setImage(null);
			label_error.setText("");
		} else {
			setTitle(UtilString.format("{} - {}", EMP_MODEL.EVENT, event.getSpecific_problem()));
			image_severity.setImage(UtilResource4Orange.getImage(event.getSeverity(), false));
			label_error.setText(event.getError());
		}
	}

	private void applyDetail() {
		if (event != null) {
			String probable_cause = event == null ? "" : event.getProbable_cause();
			event.setProbable_cause(probable_cause);
		}
	}

}
