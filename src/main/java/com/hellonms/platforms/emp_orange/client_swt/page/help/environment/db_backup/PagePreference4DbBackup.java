package com.hellonms.platforms.emp_orange.client_swt.page.help.environment.db_backup;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_core.share.language.UtilLanguage;
import com.hellonms.platforms.emp_onion.client_swt.widget.button.ButtonCheck;
import com.hellonms.platforms.emp_onion.client_swt.widget.button.ButtonClick;
import com.hellonms.platforms.emp_onion.client_swt.widget.dialog.DialogMessage;
import com.hellonms.platforms.emp_onion.client_swt.widget.dialog.DialogProgress;
import com.hellonms.platforms.emp_onion.client_swt.widget.dialog.DialogProgress.ProgressTaskIf;
import com.hellonms.platforms.emp_onion.client_swt.widget.label.LabelText;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PagePreference;
import com.hellonms.platforms.emp_onion.client_swt.widget.selector.SelectorCombo;
import com.hellonms.platforms.emp_onion.client_swt.widget.text.TextInput4String;
import com.hellonms.platforms.emp_onion.share.model.environment.preference.Model4Preference;
import com.hellonms.platforms.emp_onion.share.model.environment.preference.PREFERENCE_CODE;
import com.hellonms.platforms.emp_onion.share.model.environment.preference.PREFERENCE_CODE_ONION;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;

public class PagePreference4DbBackup extends PagePreference {

	/**
	 * createContents 메소드가 실행됐는지 여부
	 */
	protected boolean isCreateContents;

	/**
	 * 사용여부 체크버튼
	 */
	protected ButtonCheck buttonCheckAdminState;

	/**
	 * 
	 */
	protected SelectorCombo selectorComboBackupPeriod;

	/**
	 * 
	 */
	protected SelectorCombo selectorComboBackupOffset;

	/**
	 * SMTP 암호 입력필드
	 */
	protected TextInput4String textInputBackupDirectory;

	/**
	 * 시험 버튼
	 */
	protected ButtonClick buttonInstantBackup;

	/**
	 * 
	 */
	protected PagePreference4DbBackupAdvisor advisor;

	/**
	 * 
	 */
	protected Map<PREFERENCE_CODE, Model4Preference> model4PreferenceMap = new LinkedHashMap<PREFERENCE_CODE, Model4Preference>();

	/**
	 * 
	 */
	public PagePreference4DbBackup() {
		super(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.DB_BACKUP), "Environment.Database");

		setDescription(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.ENVIRONMENT_DESCRIPTION, MESSAGE_CODE_ORANGE.DB_BACKUP));

		advisor = createPagePreference4DbBackupAdvisor();
	}

	@Override
	protected Control createContents(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(2, false));

		buttonCheckAdminState = new ButtonCheck(container);
		buttonCheckAdminState.setSelection(true);
		buttonCheckAdminState.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				selectorComboBackupPeriod.getControl().setEnabled(buttonCheckAdminState.getSelection());
				selectorComboBackupOffset.getControl().setEnabled(buttonCheckAdminState.getSelection());
				textInputBackupDirectory.setEnabled(buttonCheckAdminState.getSelection());
				buttonInstantBackup.setEnabled(buttonCheckAdminState.getSelection());
			}
		});
		buttonCheckAdminState.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		buttonCheckAdminState.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.DB_BACKUP));

		LabelText labelTextBackupPeriod = new LabelText(container, SWT.NONE);
		labelTextBackupPeriod.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		labelTextBackupPeriod.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.BACKUP_PERIOD));

		selectorComboBackupPeriod = new SelectorCombo(container, SWT.READ_ONLY, new DataCombo4Counter(new int[] { 6, 12, 24 }));
		selectorComboBackupPeriod.getControl().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		LabelText labelTextBackupOffset = new LabelText(container, SWT.NONE);
		labelTextBackupOffset.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		labelTextBackupOffset.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.BACKUP_OFFSET));

		selectorComboBackupOffset = new SelectorCombo(container, SWT.READ_ONLY, new DataCombo4Counter(5));
		selectorComboBackupOffset.getControl().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		LabelText labelTextBackupDirectory = new LabelText(container, SWT.NONE);
		labelTextBackupDirectory.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		labelTextBackupDirectory.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.BACKUP_DIRECTORY));

		textInputBackupDirectory = new TextInput4String(container, SWT.BORDER);
		textInputBackupDirectory.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		new LabelText(container, SWT.NONE);

		buttonInstantBackup = new ButtonClick(container, SWT.NONE);
		buttonInstantBackup.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				backupDatabaseByUser(true);
			}
		});
		GridData gd_buttonTest = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		gd_buttonTest.widthHint = 100;
		buttonInstantBackup.setLayoutData(gd_buttonTest);
		buttonInstantBackup.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.INSTANT_BACKUP));

		queryListPreference(true);

		isCreateContents = true;

		return container;
	}

	public PagePreference4DbBackupAdvisor createPagePreference4DbBackupAdvisor() {
		return new PagePreference4DbBackupAdvisor();
	}

	@Override
	protected void performDefaults() {
		if (DialogMessage.openConfirm(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.ENVIRONMENT_DESCRIPTION, MESSAGE_CODE_ORANGE.DB_BACKUP), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.DEFAULT_CONFIRM))) {
			Model4Preference adminState = model4PreferenceMap.get(PREFERENCE_CODE_ONION.PREFERENCE_ONION_ENVIRONMENT_DATABASE_BACKUP_ADMIN_STATE);
			if (adminState == null) {
				adminState = new Model4Preference();
				adminState.setPreference_code(PREFERENCE_CODE_ONION.PREFERENCE_ONION_ENVIRONMENT_DATABASE_BACKUP_ADMIN_STATE);
			}
			adminState.setPreference("true");

			Model4Preference period = model4PreferenceMap.get(PREFERENCE_CODE_ONION.PREFERENCE_ONION_ENVIRONMENT_DATABASE_BACKUP_PERIOD_HOUR);
			if (period == null) {
				period = new Model4Preference();
				period.setPreference_code(PREFERENCE_CODE_ONION.PREFERENCE_ONION_ENVIRONMENT_DATABASE_BACKUP_PERIOD_HOUR);
			}
			period.setPreference("24");

			Model4Preference offset = model4PreferenceMap.get(PREFERENCE_CODE_ONION.PREFERENCE_ONION_ENVIRONMENT_DATABASE_BACKUP_OFFSET_HOUR);
			if (offset == null) {
				offset = new Model4Preference();
				offset.setPreference_code(PREFERENCE_CODE_ONION.PREFERENCE_ONION_ENVIRONMENT_DATABASE_BACKUP_OFFSET_HOUR);
			}
			offset.setPreference("4");

			Model4Preference directory = model4PreferenceMap.get(PREFERENCE_CODE_ONION.PREFERENCE_ONION_ENVIRONMENT_DATABASE_BACKUP_DIRECTORY);
			if (directory == null) {
				directory = new Model4Preference();
				directory.setPreference_code(PREFERENCE_CODE_ONION.PREFERENCE_ONION_ENVIRONMENT_DATABASE_BACKUP_DIRECTORY);
			}
			directory.setPreference("/emp_orange/db_backup");

			updateListPreference(model4PreferenceMap.values().toArray(new Model4Preference[0]), true);
		}
	}

	@Override
	public boolean performOk() {
		if (!isCreateContents) {
			return true;
		}

		if (buttonCheckAdminState.getSelection() && selectorComboBackupPeriod.getSelectedItem() == null) {
			DialogMessage.openInfo(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.ENVIRONMENT_DESCRIPTION, MESSAGE_CODE_ORANGE.DB_BACKUP), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.INVALID_VALUE, MESSAGE_CODE_ORANGE.BACKUP_PERIOD));
			return false;
		}
		if (buttonCheckAdminState.getSelection() && selectorComboBackupOffset.getSelectedItem() == null) {
			DialogMessage.openInfo(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.ENVIRONMENT_DESCRIPTION, MESSAGE_CODE_ORANGE.DB_BACKUP), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.INVALID_VALUE, MESSAGE_CODE_ORANGE.BACKUP_OFFSET));
			return false;
		}
		if (buttonCheckAdminState.getSelection() && textInputBackupDirectory.getText().trim().length() == 0) {
			DialogMessage.openInfo(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.ENVIRONMENT_DESCRIPTION, MESSAGE_CODE_ORANGE.DB_BACKUP), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.INVALID_VALUE, MESSAGE_CODE_ORANGE.BACKUP_DIRECTORY));
			return false;
		}

		if (DialogMessage.openConfirm(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.ENVIRONMENT_DESCRIPTION, MESSAGE_CODE_ORANGE.DB_BACKUP), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.UPDATE_ENVIRONMENT_CONFIRM))) {
			Model4Preference adminState = model4PreferenceMap.get(PREFERENCE_CODE_ONION.PREFERENCE_ONION_ENVIRONMENT_DATABASE_BACKUP_ADMIN_STATE);
			if (adminState == null) {
				adminState = new Model4Preference();
				adminState.setPreference_code(PREFERENCE_CODE_ONION.PREFERENCE_ONION_ENVIRONMENT_DATABASE_BACKUP_ADMIN_STATE);
			}
			adminState.setPreference(String.valueOf(buttonCheckAdminState.getSelection()));

			Model4Preference period = model4PreferenceMap.get(PREFERENCE_CODE_ONION.PREFERENCE_ONION_ENVIRONMENT_DATABASE_BACKUP_PERIOD_HOUR);
			if (period == null) {
				period = new Model4Preference();
				period.setPreference_code(PREFERENCE_CODE_ONION.PREFERENCE_ONION_ENVIRONMENT_DATABASE_BACKUP_PERIOD_HOUR);
			}
			period.setPreference(String.valueOf(selectorComboBackupPeriod.getSelectedItem()));

			Model4Preference offset = model4PreferenceMap.get(PREFERENCE_CODE_ONION.PREFERENCE_ONION_ENVIRONMENT_DATABASE_BACKUP_OFFSET_HOUR);
			if (offset == null) {
				offset = new Model4Preference();
				offset.setPreference_code(PREFERENCE_CODE_ONION.PREFERENCE_ONION_ENVIRONMENT_DATABASE_BACKUP_OFFSET_HOUR);
			}
			offset.setPreference(String.valueOf(selectorComboBackupOffset.getSelectedItem()));

			Model4Preference directory = model4PreferenceMap.get(PREFERENCE_CODE_ONION.PREFERENCE_ONION_ENVIRONMENT_DATABASE_BACKUP_DIRECTORY);
			if (directory == null) {
				directory = new Model4Preference();
				directory.setPreference_code(PREFERENCE_CODE_ONION.PREFERENCE_ONION_ENVIRONMENT_DATABASE_BACKUP_DIRECTORY);
			}
			directory.setPreference(textInputBackupDirectory.getText().trim());

			updateListPreference(model4PreferenceMap.values().toArray(new Model4Preference[0]), true);
			return true;
		} else {
			return false;
		}
	}

	protected void display(Model4Preference[] model4Preferences) {
		if (model4Preferences != null) {
			for (Model4Preference model4Preference : model4Preferences) {
				model4PreferenceMap.put(model4Preference.getPreference_code(), model4Preference);
			}
		}

		Model4Preference adminState = model4PreferenceMap.get(PREFERENCE_CODE_ONION.PREFERENCE_ONION_ENVIRONMENT_DATABASE_BACKUP_ADMIN_STATE);
		if (adminState != null) {
			buttonCheckAdminState.setSelection("true".equalsIgnoreCase(adminState.getPreference()));
		}

		Model4Preference period = model4PreferenceMap.get(PREFERENCE_CODE_ONION.PREFERENCE_ONION_ENVIRONMENT_DATABASE_BACKUP_PERIOD_HOUR);
		if (period != null) {
			selectorComboBackupPeriod.setSelectedItem(Integer.parseInt(period.getPreference()));
		}

		Model4Preference offset = model4PreferenceMap.get(PREFERENCE_CODE_ONION.PREFERENCE_ONION_ENVIRONMENT_DATABASE_BACKUP_OFFSET_HOUR);
		if (offset != null) {
			selectorComboBackupOffset.setSelectedItem(Integer.parseInt(offset.getPreference()));
		}

		Model4Preference directory = model4PreferenceMap.get(PREFERENCE_CODE_ONION.PREFERENCE_ONION_ENVIRONMENT_DATABASE_BACKUP_DIRECTORY);
		textInputBackupDirectory.setText(directory.getPreference());

		selectorComboBackupPeriod.getControl().setEnabled(buttonCheckAdminState.getSelection());
		selectorComboBackupOffset.getControl().setEnabled(buttonCheckAdminState.getSelection());
		textInputBackupDirectory.setEnabled(buttonCheckAdminState.getSelection());
		buttonInstantBackup.setEnabled(buttonCheckAdminState.getSelection());
	}

	protected void queryListPreference(boolean progressBar) {
		try {
			Model4Preference[] model4Preferences = (Model4Preference[]) DialogProgress.run(getShell(), progressBar, new ProgressTaskIf() {
				@Override
				public Object run() throws EmpException {
					return advisor.queryListPreference("Environment", "Database", null);
				}
			});

			display(model4Preferences);
		} catch (EmpException ex) {
			DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.ENVIRONMENT_DESCRIPTION, MESSAGE_CODE_ORANGE.DB_BACKUP), ex);
		} catch (Exception ex) {
			DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.ENVIRONMENT_DESCRIPTION, MESSAGE_CODE_ORANGE.DB_BACKUP), ex);
		}
	}

	protected void updateListPreference(final Model4Preference[] model4Preferences, boolean progressBar) {
		try {
			Model4Preference[] aaa = (Model4Preference[]) DialogProgress.run(getShell(), progressBar, new ProgressTaskIf() {
				@Override
				public Object run() throws EmpException {
					return advisor.updateListPreference(model4Preferences);
				}
			});

			display(aaa);
		} catch (EmpException ex) {
			DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.ENVIRONMENT_DESCRIPTION, MESSAGE_CODE_ORANGE.DB_BACKUP), ex);
		} catch (Exception ex) {
			DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.ENVIRONMENT_DESCRIPTION, MESSAGE_CODE_ORANGE.DB_BACKUP), ex);
		}
	}

	protected void backupDatabaseByUser(boolean progressBar) {
		try {

			File dbBackupFile = (File) DialogProgress.run(getShell(), progressBar, new ProgressTaskIf() {
				@Override
				public Object run() throws EmpException {
					return advisor.backupDatabaseByUser();
				}
			});

			DialogMessage.openInfo(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.ENVIRONMENT_DESCRIPTION, MESSAGE_CODE_ORANGE.DB_BACKUP), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.DB_BACKUP_COMPLETED, dbBackupFile.getAbsolutePath()));
		} catch (EmpException ex) {
			DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.ENVIRONMENT_DESCRIPTION, MESSAGE_CODE_ORANGE.DB_BACKUP), ex);
		} catch (Exception ex) {
			DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.ENVIRONMENT_DESCRIPTION, MESSAGE_CODE_ORANGE.DB_BACKUP), ex);
		}
	}

}
