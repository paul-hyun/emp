package com.hellonms.platforms.emp_orange.client_swt.page.network.ne_info;

import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.hellonms.platforms.emp_core.share.language.UtilLanguage;
import com.hellonms.platforms.emp_onion.client_swt.widget.button.ButtonClick;
import com.hellonms.platforms.emp_onion.client_swt.widget.dialog.DialogMessage;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.Panel;
import com.hellonms.platforms.emp_orange.client_swt.model_display.ModelDisplay4NeInfo;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO_FIELD;
import com.hellonms.platforms.emp_orange.share.model.network.ne_info.Model4NeInfoIf;

/**
 * <p>
 * Panel4NeInfoDataAt
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public abstract class Panel4NeInfoDataAt extends Panel {

	protected static final String REFRESH = "REFRESH";

	protected static final String CREATE = "CREATE";

	protected static final String UPDATE = "UPDATE";

	protected static final String DELETE = "DELETE";

	/**
	 * 성능이력 검색결과 판넬의 리스너 인터페이스 입니다.
	 * 
	 * @since Version 1.4.0
	 * @author Hello NMS
	 */
	public interface Panel4NeInfoDataListenerIf {

		public void queryListNeInfo(int startNo, boolean isQuery);

		public void openWizard4CreateNeInfo();

		public void openWizard4UpdateNeInfo(Model4NeInfoIf model4NeInfo);

		public void updateNeInfo(Model4NeInfoIf model4NeInfo);

		public void openWizard4DeleteNeInfo(Model4NeInfoIf model4NeInfo);

		public void deleteNeInfo(Model4NeInfoIf model4NeInfo);

		public void saveExcelNeInfo(String path);

	}

	/**
	 * 리스너
	 */
	protected Panel4NeInfoDataListenerIf listener;

	protected Composite panelContents;

	protected PanelInput4NeInfoIf panelInput4NeInfo;

	protected Composite panelButtons;

	protected Map<String, PanelInput4NeInfoIf> panelMap = new LinkedHashMap<String, PanelInput4NeInfoIf>();

	protected Map<String, ButtonClick> buttonMap = new LinkedHashMap<String, ButtonClick>();

	/**
	 * 생성자 입니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 * @param style
	 *            스타일
	 * @param rowCount
	 *            테이블 페이지당 최대 항목 개수
	 * @param listener
	 *            리스너
	 */
	public Panel4NeInfoDataAt(Composite parent, int style, Panel4NeInfoDataListenerIf listener) {
		super(parent, style, UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.DATA_TITLE, MESSAGE_CODE_ORANGE.NE_INFO));

		this.listener = listener;

		createGUI();
	}

	/**
	 * 생성자 입니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 * @param style
	 *            스타일
	 * @param rowCount
	 *            테이블 페이지당 최대 항목 개수
	 * @param listener
	 *            리스너
	 */
	public Panel4NeInfoDataAt(Composite parent, int style, String title, Panel4NeInfoDataListenerIf listener) {
		super(parent, style, title);

		this.listener = listener;

		createGUI();
	}

	/**
	 * GUI를 생성합니다.
	 */
	protected void createGUI() {
		getContentComposite().setLayout(new FormLayout());

		panelContents = new Composite(getContentComposite(), SWT.NONE);
		FormData fd_panelContents = new FormData();
		fd_panelContents.bottom = new FormAttachment(100, -5);
		fd_panelContents.right = new FormAttachment(100, -80);
		fd_panelContents.top = new FormAttachment(0, 5);
		fd_panelContents.left = new FormAttachment(0, 5);
		panelContents.setLayoutData(fd_panelContents);
		panelContents.setLayout(new StackLayout());

		panelButtons = new Composite(getContentComposite(), SWT.NONE);
		FormData fd_panelButtons = new FormData();
		fd_panelButtons.top = new FormAttachment(panelContents, 0, SWT.TOP);
		fd_panelButtons.left = new FormAttachment(panelContents, 5);
		fd_panelButtons.right = new FormAttachment(100, -5);
		panelButtons.setLayoutData(fd_panelButtons);
		GridLayout gl_panelButtons = new GridLayout(1, false);
		gl_panelButtons.verticalSpacing = 5;
		gl_panelButtons.marginWidth = 0;
		gl_panelButtons.marginHeight = 0;
		gl_panelButtons.horizontalSpacing = 0;
		panelButtons.setLayout(gl_panelButtons);

		createButtons(panelButtons);
	}

	protected void createButtons(Composite parent) {
		ButtonClick buttonRefresh = buttonMap.get(REFRESH);
		if (buttonRefresh == null) {
			buttonRefresh = createButtonRefresh(parent);
			buttonMap.put(REFRESH, buttonRefresh);
			setVisibleButton(REFRESH, false);
		}

		ButtonClick buttonCreate = buttonMap.get(CREATE);
		if (buttonCreate == null) {
			buttonCreate = createButtonCreate(parent);
			buttonMap.put(CREATE, buttonCreate);
			setVisibleButton(CREATE, false);
		}

		ButtonClick buttonUpdate = buttonMap.get(UPDATE);
		if (buttonUpdate == null) {
			buttonUpdate = createButtonUpdate(parent);
			buttonMap.put(UPDATE, buttonUpdate);
			setVisibleButton(UPDATE, false);
		}

		ButtonClick buttonDelete = buttonMap.get(DELETE);
		if (buttonDelete == null) {
			buttonDelete = createButtonDelete(parent);
			buttonMap.put(DELETE, buttonDelete);
			setVisibleButton(DELETE, false);
		}
	}

	protected ButtonClick createButtonRefresh(Composite parent) {
		ButtonClick button = new ButtonClick(panelButtons);
		button.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (panelInput4NeInfo != null) {
					listener.queryListNeInfo(panelInput4NeInfo.getStartNo(), false);
				}
			}
		});
		button.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.REFRESH));
		return button;
	}

	protected ButtonClick createButtonCreate(Composite parent) {
		ButtonClick button = new ButtonClick(panelButtons);
		button.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (panelInput4NeInfo != null) {
					listener.openWizard4CreateNeInfo();
				}
			}
		});
		button.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.CREATE));
		return button;
	}

	protected ButtonClick createButtonUpdate(Composite parent) {
		ButtonClick button = new ButtonClick(panelButtons);
		button.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				if (panelInput4NeInfo != null) {
					Model4NeInfoIf model4NeInfo = panelInput4NeInfo.getSelected();
					if (model4NeInfo != null) {
						if (panelInput4NeInfo.isNeedWizard()) {
							listener.openWizard4UpdateNeInfo(model4NeInfo);
						} else {
							if (!panelInput4NeInfo.isComplete()) {
								String errorMessage = panelInput4NeInfo.getErrorMessage();
								DialogMessage.openInfo(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.UPDATE_TITLE, model4NeInfo.getNe_info_def().getName()), errorMessage == null ? "" : errorMessage);
							} else {
								if (DialogMessage.openConfirm(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.UPDATE_TITLE, model4NeInfo.getNe_info_def().getName()), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.UPDATE_CONFIRM, MESSAGE_CODE_ORANGE.NE_INFO, model4NeInfo.getNe_info_def().getName()))) {
									listener.updateNeInfo(model4NeInfo);
								}
							}
						}
					}
				}
			}
		});
		button.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.UPDATE));
		return button;
	}

	protected ButtonClick createButtonDelete(Composite parent) {
		ButtonClick button = new ButtonClick(panelButtons);
		button.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (panelInput4NeInfo != null) {
					Model4NeInfoIf model4NeInfo = panelInput4NeInfo.getSelected();
					if (model4NeInfo != null) {
						if (panelInput4NeInfo.isNeedWizard()) {
							listener.openWizard4DeleteNeInfo(model4NeInfo);
						} else {
							if (DialogMessage.openConfirm(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.DELETE_TITLE, model4NeInfo.getNe_info_def().getName()), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.DELETE_CONFIRM, MESSAGE_CODE_ORANGE.NE_INFO, model4NeInfo.getNe_info_def().getName()))) {
								listener.deleteNeInfo(model4NeInfo);
							}
						}
					}
				}
			}
		});
		button.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.DELETE));
		return button;
	}

	public void display(EMP_MODEL_NE_INFO ne_info_def, Object... datas) {
		if (ne_info_def == null) {
			((StackLayout) panelContents.getLayout()).topControl = null;
			this.panelInput4NeInfo = null;
		} else {
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(ne_info_def);
			for (Object data : datas) {
				stringBuilder.append(".").append(data);
			}
			String key = stringBuilder.toString();

			PanelInput4NeInfoIf panelInput4NeInfo = panelMap.get(key);
			if (panelInput4NeInfo == null) {
				panelInput4NeInfo = createPanelInput4NeInfo(panelContents, ne_info_def, datas);
				panelMap.put(key, panelInput4NeInfo);
			}
			((StackLayout) panelContents.getLayout()).topControl = (Control) panelInput4NeInfo;
			this.panelInput4NeInfo = panelInput4NeInfo;
		}

		panelContents.layout();
		displayButton(ne_info_def, datas);

		getContentComposite().layout();
	}

	protected abstract PanelInput4NeInfoIf createPanelInput4NeInfo(Composite parent, EMP_MODEL_NE_INFO ne_info_def, Object... datas);

	protected void displayButton(EMP_MODEL_NE_INFO ne_info_def, Object... datas) {
		// boolean isCreate = false;
		boolean isUpdate = false;
		// boolean isDelete = false;

		if (ne_info_def != null) {
			for (EMP_MODEL_NE_INFO_FIELD ne_info_field_def : ne_info_def.getNe_info_fields()) {
				// if (!isCreate && ne_info_field_def.isAccess_create()) {
				// isCreate = true;
				// }
				if (!isUpdate && ne_info_field_def.isUpdate()) {
					isUpdate = true;
				}
				// if (!isDelete && ne_info_field_def.isAccess_delete()) {
				// isDelete = true;
				// }
			}
		}

		setVisibleButton(REFRESH, ne_info_def != null);
		setVisibleButton(CREATE, false);
		setVisibleButton(UPDATE, isUpdate);
		setVisibleButton(DELETE, false);

		setEnabledButton(REFRESH, ne_info_def != null);
		setEnabledButton(CREATE, false);
		setEnabledButton(UPDATE, isUpdate);
		setEnabledButton(DELETE, false);

		panelButtons.layout();
	}

	protected void setVisibleButton(String button_id, boolean visible) {
		ButtonClick button = buttonMap.get(button_id);
		if (button != null) {
			button.setVisible(visible);
			((GridData) button.getLayoutData()).exclude = !visible;
		}
	}

	protected void setEnabledButton(String button_id, boolean enabled) {
		ButtonClick button = buttonMap.get(button_id);
		if (button != null) {
			button.setEnabled(enabled);
		}
	}

	public void display(ModelDisplay4NeInfo modelDisplay4NeInfo) {
		Control topControl = (((StackLayout) panelContents.getLayout())).topControl;
		if (topControl instanceof PanelInput4NeInfoIf) {
			((PanelInput4NeInfoIf) topControl).display(modelDisplay4NeInfo);
		}
	}

	public void clear() {
		Control topControl = (((StackLayout) panelContents.getLayout())).topControl;
		if (topControl instanceof PanelInput4NeInfoIf) {
			((PanelInput4NeInfoIf) topControl).clear();
		}
	}

	protected void selectionChanged(Model4NeInfoIf model4NeInfo) {
		setEnabledButton(UPDATE, model4NeInfo != null);
	}

}
