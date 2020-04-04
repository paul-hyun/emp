package com.hellonms.platforms.emp_orange.client_swt.page.emp_model.snmp;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_core.share.language.UtilLanguage;
import com.hellonms.platforms.emp_onion.client_swt.widget.dialog.DialogMessage;
import com.hellonms.platforms.emp_onion.client_swt.widget.dialog.DialogProgress;
import com.hellonms.platforms.emp_onion.client_swt.widget.dialog.DialogProgress.ProgressTaskIf;
import com.hellonms.platforms.emp_onion.theme.ThemeBuilder4Onion.COLOR_ONION;
import com.hellonms.platforms.emp_onion.theme.ThemeBuilder4Onion.IMAGE_ONION;
import com.hellonms.platforms.emp_onion.theme.ThemeFactory;
import com.hellonms.platforms.emp_orange.client_swt.page.emp_model.Shell4Model;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL;
import com.hellonms.platforms.emp_orange.share.parser.snmp.MIB_REPOSITORY;
import com.hellonms.platforms.emp_util.string.UtilString;

public class Shell4Snmp extends Shell {

	private static Shell4Snmp instance;

	public static Shell4Snmp open(Shell4Model shell4Model) {
		if (instance == null || instance.isDisposed()) {
			instance = new Shell4Snmp(shell4Model, SWT.SHELL_TRIM);
		}
		instance.setMinimized(false);
		instance.open();
		return instance;
	}

	private class Panel4SnmpListener implements Panel4SnmpListenerIf {

		@Override
		public void refresh() {
			shell4Model.refresh();
		}

		@Override
		public EMP_MODEL getEmp_model() {
			return shell4Model.getEmp_model();
		}

		@Override
		public void showPage(String name) {
			shell4Model.showPage(name);
		}

	}

	private Shell4Model shell4Model;
	private Page4SnmpMoid page4SnmpMoid;
	private Panel4SnmpListener childListener = new Panel4SnmpListener();

	private Shell4Snmp(Shell4Model shell4Model, int style) {
		super(shell4Model, style);
		this.shell4Model = shell4Model;
		setImages(shell4Model.getImages());
		createGUI();
	}

	@Override
	protected void checkSubclass() {
	}

	private void createGUI() {
		setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.SNMP));
		FillLayout fillLayout = new FillLayout();
		setLayout(fillLayout);

		{
			Menu menuBar = new Menu(this, SWT.BAR);
			this.setMenuBar(menuBar);

			MenuItem importItem = new MenuItem(menuBar, SWT.PUSH);
			importItem.setText("Import");
			importItem.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					try {
						FileDialog fileDialog = new FileDialog(getShell(), SWT.MULTI | SWT.OPEN);
						fileDialog.setFilterExtensions(new String[] { "*" });
						fileDialog.open();
						String path = fileDialog.getFilterPath();
						String[] fileNames = fileDialog.getFileNames();

						if (!UtilString.isEmpty(path) && fileNames != null && 0 < fileNames.length) {
							String error = (String) DialogProgress.run(getShell(), true, new ProgressTaskIf() {
								@Override
								public Object run() throws EmpException {
									for (String fileName : fileNames) {
										File file = new File(path, fileName);
										if (file.isFile()) {
											DialogProgress.setSubTask(UtilString.format("Loading... {}", file.getAbsolutePath()));
											MIB_REPOSITORY.getInstance().load_mib(file);
										}
									}
									DialogProgress.setSubTask(UtilString.format("Analysis..."));
									return MIB_REPOSITORY.getInstance().analysis();
								}
							});
							page4SnmpMoid.refresh();
							if (error != null) {
								DialogMessage.openInfo(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.SNMP), error);
							}
						}
					} catch (Exception ex) {
						DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.SNMP), ex);
					}
				}
			});
		}

		{
			CTabFolder tabFolder = new CTabFolder(this, SWT.NONE);
			tabFolder.setSimple(false);
			tabFolder.setSelectionBackground(new Color[] { //
					ThemeFactory.getColor(COLOR_ONION.VIEW_TAB_ACTIVE_BG1), //
							ThemeFactory.getColor(COLOR_ONION.VIEW_TAB_ACTIVE_BG2), //
					}, new int[] { 1 });
			tabFolder.setSelectionForeground(ThemeFactory.getColor(COLOR_ONION.VIEW_TAB_ACTIVE_FG));
			tabFolder.setBackground(ThemeFactory.getColor(COLOR_ONION.VIEW_TAB_INACTIVE_BG2));
			tabFolder.setUnselectedImageVisible(false);

			{
				CTabItem tabItem = new CTabItem(tabFolder, SWT.NONE);
				tabItem.setText("MIB");
				tabItem.setImage(ThemeFactory.getImage(IMAGE_ONION.PAGE_ICON));
				page4SnmpMoid = new Page4SnmpMoid(tabFolder, SWT.NONE, childListener);
				tabItem.setControl(page4SnmpMoid);
			}
		}
	}
}
