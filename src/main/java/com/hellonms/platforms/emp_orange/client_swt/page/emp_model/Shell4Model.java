package com.hellonms.platforms.emp_orange.client_swt.page.emp_model;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Listener;
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
import com.hellonms.platforms.emp_orange.client_swt.Application;
import com.hellonms.platforms.emp_orange.client_swt.page.emp_model.Page4ModelNe.Page4ModelNeListenerIf;
import com.hellonms.platforms.emp_orange.client_swt.page.emp_model.Wizard4InitDB.Wizard4InitDBListenerIf;
import com.hellonms.platforms.emp_orange.client_swt.page.emp_model.snmp.Shell4Snmp;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_ENUM;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_EVENT;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO;
import com.hellonms.platforms.emp_orange.share.model.fault.event.SEVERITY;
import com.hellonms.platforms.emp_orange.share.util.UTIL_EMP_MODEL;
import com.hellonms.platforms.emp_util.string.UtilString;

public class Shell4Model extends Shell {

	private static Shell4Model instance;

	public static Shell4Model open(Shell parent) {
		if (instance == null || instance.isDisposed()) {
			instance = new Shell4Model(parent, SWT.SHELL_TRIM);
		}
		instance.setMinimized(false);
		instance.open();
		return instance;
	}

	private class Panel4ModelListener implements Panel4ModelListenerIf, Page4ModelNeListenerIf, Wizard4InitDBListenerIf {

		@Override
		public void refresh() {
			Shell4Model.this.refresh();
		}

		@Override
		public void truncate(Wizard4InitDB wizard, boolean isNe, boolean isNe_info, boolean isFault, boolean isOperation_log) {
			try {
				DialogProgress.run(wizard.getShell(), true, new ProgressTaskIf() {
					@Override
					public Object run() throws EmpException {
						advisor.truncate(isNe, isNe_info, isFault, isOperation_log);
						return null;
					}
				});

				Wizard4InitDB.close();
			} catch (EmpException e) {
				DialogMessage.openError(wizard.getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.MODELING, MESSAGE_CODE_ORANGE.INIT_DB), e);
			}
		}

		@Override
		public String[] queryListImagePath(final String path, final String[] extensions) {
			try {
				return (String[]) DialogProgress.run(getShell(), true, new ProgressTaskIf() {
					@Override
					public Object run() throws EmpException {
						return advisor.queryListImagePath(path, extensions);
					}
				});
			} catch (EmpException e) {
				DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.MODELING), e);
				return new String[0];
			}
		}

		@Override
		public void createImage(final String path, final String filename, final byte[] filedata) {
			try {
				DialogProgress.run(getShell(), true, new ProgressTaskIf() {
					@Override
					public Object run() throws EmpException {
						advisor.createImage(path, filename, filedata);
						return null;
					}
				});
			} catch (EmpException e) {
				DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.MODELING), e);
			}
		}

	}

	private CTabFolder tabFolder;
	private Page4ModelEnum page4ModelEnum;
	private Page4ModelEvent page4ModelEvent;
	private Page4ModelNeInfo page4ModelNeInfo;
	private Page4ModelNe page4ModelNe;

	private byte[] emp_model_data = {};
	private EMP_MODEL emp_model;

	private Panel4ModelListener childListener = new Panel4ModelListener();

	private Shell4ModelAdvisor advisor = new Shell4ModelAdvisor();

	private Shell4Model(Shell parent, int style) {
		super(parent, style);
		setImages(parent.getImages());
		createGUI();
		display();

		addListener(SWT.Close, new Listener() {

			@Override
			public void handleEvent(Event event) {
				if (isDirty()) {
					if (!DialogMessage.openConfirm(getShell(), "모델링 종료", "서버에 저장되지 않은 값이 있습니다.\n정말 종료하시겠습니까?")) {
						event.doit = false;
					}
				}
			}
		});
	}

	@Override
	protected void checkSubclass() {
	}

	private void createGUI() {
		setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.MODELING));
		FillLayout fillLayout = new FillLayout();
		setLayout(fillLayout);

		{
			Menu menuBar = new Menu(this, SWT.BAR);
			this.setMenuBar(menuBar);

			MenuItem storeItem = new MenuItem(menuBar, SWT.PUSH);
			storeItem.setText("Apply");
			storeItem.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					if (emp_model != null) {
						try {
							EMP_MODEL_NE_INFO[] ne_infos = emp_model.getNe_infos();
							for (EMP_MODEL_NE_INFO ne_info : ne_infos) {
								if (ne_info.getSeverity().equals(SEVERITY.CRITICAL)) {
									DialogMessage.openError(getShell(), "Apply Fail", UtilString.format("NE_INFO에 잘못된 값이 있습니다."));
									return;
								}
							}
							EMP_MODEL_NE[] nes = emp_model.getNes();
							for (EMP_MODEL_NE ne : nes) {
								if (ne.getSeverity().equals(SEVERITY.CRITICAL)) {
									DialogMessage.openError(getShell(), "Apply Fail", UtilString.format("NE에 잘못된 값이 있습니다."));
									return;
								}
							}
							EMP_MODEL_ENUM[] enums = emp_model.getEnums();
							for (EMP_MODEL_ENUM enum_def : enums) {
								if (enum_def.getSeverity().equals(SEVERITY.CRITICAL)) {
									DialogMessage.openError(getShell(), "Apply Fail", UtilString.format("ENUM에 잘못된 값이 있습니다."));
									return;
								}
							}
							EMP_MODEL_EVENT[] events = emp_model.getEvents();
							for (EMP_MODEL_EVENT event : events) {
								if (event.getSeverity().equals(SEVERITY.CRITICAL)) {
									DialogMessage.openError(getShell(), "Apply Fail", UtilString.format("EVENT에 잘못된 값이 있습니다."));
									return;
								}
							}

							if (DialogMessage.openConfirm(getShell(), "Apply", "모델을 변경하면 기존 데이터와의 불일치로 인해 프로그램이 오동작할 수 있으므로 Init DB 메뉴를 이용하여 DB를 초기화하고 프로그램을 재시동하시길 권장합니다.\n정말 저장하시겠습니까?")) {
								byte[] emp_model_data = emp_model.getBytes();
								emp_model_data = advisor.updateEmp_model(emp_model_data);
								Application.initializeModel(emp_model_data);
								display(emp_model_data);
							}
						} catch (EmpException e1) {
							DialogMessage.openError(getShell(), "Apply Fail", e1);
						}
					}
				}
			});

			MenuItem exportItem = new MenuItem(menuBar, SWT.PUSH);
			exportItem.setText("Export");
			exportItem.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					if (emp_model != null) {
						FileDialog fileDialog = new FileDialog(getShell(), SWT.SAVE);
						fileDialog.setFilterExtensions(new String[] { "*.xlsx" });
						final String file = fileDialog.open();

						if (file != null) {
							try {
								UTIL_EMP_MODEL.export_excel(emp_model.getBytes(), new File(file));
							} catch (EmpException e1) {
								DialogMessage.openError(getShell(), "Export Fail", e1);
							}
						}
					}
				}
			});

			MenuItem importItem = new MenuItem(menuBar, SWT.PUSH);
			importItem.setText("Import");
			importItem.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					if (emp_model != null) {
						FileDialog fileDialog = new FileDialog(getShell(), SWT.OPEN);
						fileDialog.setFilterExtensions(new String[] { "*.xlsx" });
						final String file = fileDialog.open();

						if (file != null) {
							try {
								byte[] emp_model_data = UTIL_EMP_MODEL.import_excel(new File(file));
								display(emp_model_data);
							} catch (EmpException e1) {
								DialogMessage.openError(getShell(), "Import Fail", e1);
							}
						}
					}
				}
			});

			MenuItem snmpItem = new MenuItem(menuBar, SWT.PUSH);
			snmpItem.setText("SNMP");
			snmpItem.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					Shell4Snmp.open(Shell4Model.this);
				}
			});

			MenuItem initDbItem = new MenuItem(menuBar, SWT.PUSH);
			initDbItem.setText("Init DB");
			initDbItem.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					Wizard4InitDB.open(getShell(), childListener);
				}
			});
		}

		{
			tabFolder = new CTabFolder(this, SWT.NONE);
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
				tabItem.setText(EMP_MODEL.NE_INFO);
				tabItem.setImage(ThemeFactory.getImage(IMAGE_ONION.PAGE_ICON));
				page4ModelNeInfo = new Page4ModelNeInfo(tabFolder, SWT.NONE, childListener);
				tabItem.setControl(page4ModelNeInfo);
			}
			{
				CTabItem tabItem = new CTabItem(tabFolder, SWT.NONE);
				tabItem.setText(EMP_MODEL.NE);
				tabItem.setImage(ThemeFactory.getImage(IMAGE_ONION.PAGE_ICON));
				page4ModelNe = new Page4ModelNe(tabFolder, SWT.NONE, childListener);
				tabItem.setControl(page4ModelNe);
			}
			{
				CTabItem tabItem = new CTabItem(tabFolder, SWT.NONE);
				tabItem.setText(EMP_MODEL.ENUM);
				tabItem.setImage(ThemeFactory.getImage(IMAGE_ONION.PAGE_ICON));
				page4ModelEnum = new Page4ModelEnum(tabFolder, SWT.NONE, childListener);
				tabItem.setControl(page4ModelEnum);
			}
			{
				CTabItem tabItem = new CTabItem(tabFolder, SWT.NONE);
				tabItem.setText(EMP_MODEL.EVENT);
				tabItem.setImage(ThemeFactory.getImage(IMAGE_ONION.PAGE_ICON));
				page4ModelEvent = new Page4ModelEvent(tabFolder, SWT.NONE, childListener);
				tabItem.setControl(page4ModelEvent);
			}
		}
	}

	protected void display() {
		try {
			byte[] emp_model_data = advisor.queryEmp_model();
			display(emp_model_data);
		} catch (EmpException e) {
			e.printStackTrace();
		}
	}

	protected void display(byte[] emp_model_data) throws EmpException {
		this.emp_model_data = emp_model_data;
		this.emp_model = new EMP_MODEL(emp_model_data);

		page4ModelEnum.displayTree(this.emp_model);
		page4ModelEvent.displayTree(this.emp_model);
		page4ModelNeInfo.displayTree(this.emp_model);
		page4ModelNe.displayTree(this.emp_model);
	}

	public void refresh() {
		page4ModelEnum.refresh();
		page4ModelEvent.refresh();
		page4ModelNeInfo.refresh();
		page4ModelNe.refresh();
	}

	public EMP_MODEL getEmp_model() {
		return emp_model;
	}

	private boolean isDirty() {
		byte[] emp_model_data = this.emp_model.getBytes();
		boolean equals = this.emp_model_data.length == emp_model_data.length;
		if (equals) {
			for (int i = 0; i < emp_model_data.length; i++) {
				if (this.emp_model_data[i] != emp_model_data[i]) {
					equals = false;
					break;
				}
			}
		}
		return !equals;
	}

	public void showPage(String name) {
		for (CTabItem tabItem : tabFolder.getItems()) {
			if (tabItem.getText().equals(name)) {
				tabFolder.setSelection(tabItem);
				break;
			}
		}
	}

}
