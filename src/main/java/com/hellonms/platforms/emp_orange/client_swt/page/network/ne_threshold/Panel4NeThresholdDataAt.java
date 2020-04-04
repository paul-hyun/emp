/**
 * Copyright 2016 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.client_swt.page.network.ne_threshold;

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
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO_FIELD;
import com.hellonms.platforms.emp_orange.share.model.network.ne_info.Model4NeThresholdIf;

/**
 * <p>
 * Panel4NeThresholdDataAt
 * </p>
 *
 * @since 1.6
 * @create 2016. 1. 22.
 * @modified 2016. 1. 22.
 * @author jungsun
 */
public abstract class Panel4NeThresholdDataAt extends Panel {

	protected static final String REFRESH = "REFRESH";

	protected static final String UPDATE = "UPDATE";

	protected static final String COPY = "COPY";

	/**
	 * 성능이력 검색결과 판넬의 리스너 인터페이스 입니다.
	 * 
	 * @since Version 1.4.0
	 * @author Hello NMS
	 */
	public interface Panel4NeThresholdDataListenerIf {

		public void queryNeThreshold();

		public void openWizard4UpdateNeThreshold(Model4NeThresholdIf ne_threshold, EMP_MODEL_NE_INFO_FIELD ne_info_field_def);

		public void updateNeThreshold(Model4NeThresholdIf ne_threshold, EMP_MODEL_NE_INFO_FIELD ne_info_field_def);

		public void openWizard4CopyNeThreshold();

	}

	/**
	 * 리스너
	 */
	protected Panel4NeThresholdDataListenerIf listener;

	protected Composite panelContents;

	protected PanelInput4NeThresholdIf panelInput4NeThreshold;

	protected Composite panelButtons;

	protected Map<String, PanelInput4NeThresholdIf> panelMap = new LinkedHashMap<String, PanelInput4NeThresholdIf>();

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
	public Panel4NeThresholdDataAt(Composite parent, int style, Panel4NeThresholdDataListenerIf listener) {
		super(parent, style, UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.DATA_TITLE, MESSAGE_CODE_ORANGE.NE_THRESHOLD));

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
	public Panel4NeThresholdDataAt(Composite parent, int style, String title, Panel4NeThresholdDataListenerIf listener) {
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

		ButtonClick buttonUpdate = buttonMap.get(UPDATE);
		if (buttonUpdate == null) {
			buttonUpdate = createButtonUpdate(parent);
			buttonMap.put(UPDATE, buttonUpdate);
			setVisibleButton(UPDATE, false);
		}

		ButtonClick buttonCopy = buttonMap.get(COPY);
		if (buttonCopy == null) {
			buttonCopy = createButtonCopy(parent);
			buttonMap.put(COPY, buttonCopy);
			setVisibleButton(COPY, false);
		}
	}

	protected ButtonClick createButtonRefresh(Composite parent) {
		ButtonClick button = new ButtonClick(panelButtons);
		button.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (panelInput4NeThreshold != null) {
					listener.queryNeThreshold();
				}
			}
		});
		button.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.REFRESH));
		return button;
	}

	protected ButtonClick createButtonUpdate(Composite parent) {
		ButtonClick button = new ButtonClick(panelButtons);
		button.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				if (panelInput4NeThreshold != null) {
					Model4NeThresholdIf ne_threshold = panelInput4NeThreshold.getSelected();
					EMP_MODEL_NE_INFO_FIELD ne_info_field_def = panelInput4NeThreshold.getNe_field_code();
					if (ne_threshold != null && ne_info_field_def != null) {
						if (panelInput4NeThreshold.isNeedWizard()) {
							listener.openWizard4UpdateNeThreshold(ne_threshold, ne_info_field_def);
						} else {
							if (!panelInput4NeThreshold.isComplete()) {
								String errorMessage = panelInput4NeThreshold.getErrorMessage();
								DialogMessage.openInfo(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.UPDATE_TITLE, ne_info_field_def.getName()), errorMessage == null ? "" : errorMessage);
							} else {
								if (DialogMessage.openConfirm(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.UPDATE_TITLE, ne_info_field_def.getName()), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.UPDATE_CONFIRM, MESSAGE_CODE_ORANGE.NE_THRESHOLD, ne_info_field_def.getName()))) {
									listener.updateNeThreshold(ne_threshold, ne_info_field_def);
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

	protected ButtonClick createButtonCopy(Composite parent) {
		ButtonClick button = new ButtonClick(panelButtons);
		button.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				if (panelInput4NeThreshold != null) {
					Model4NeThresholdIf ne_threshold = panelInput4NeThreshold.getSelected();
					if (ne_threshold != null) {
						listener.openWizard4CopyNeThreshold();
					}
				}
			}
		});
		button.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.COPY));
		return button;
	}

	public void display(EMP_MODEL_NE_INFO ne_info_def, Object... datas) {
		if (ne_info_def == null) {
			((StackLayout) panelContents.getLayout()).topControl = null;
			this.panelInput4NeThreshold = null;
		} else {
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(ne_info_def.getCode());
			for (Object data : datas) {
				stringBuilder.append(".").append(data);
			}
			String key = stringBuilder.toString();

			PanelInput4NeThresholdIf panelInput4NeThreshold = panelMap.get(key);
			if (panelInput4NeThreshold == null) {
				panelInput4NeThreshold = createPanelInput4NeThreshold(panelContents, ne_info_def, datas);
				panelMap.put(key, panelInput4NeThreshold);
			}
			((StackLayout) panelContents.getLayout()).topControl = (Control) panelInput4NeThreshold;
			this.panelInput4NeThreshold = panelInput4NeThreshold;
		}

		panelContents.layout();
		displayButton(ne_info_def, datas);

		getContentComposite().layout();
	}

	protected abstract PanelInput4NeThresholdIf createPanelInput4NeThreshold(Composite parent, EMP_MODEL_NE_INFO ne_info_def, Object... datas);

	protected void displayButton(EMP_MODEL_NE_INFO ne_info_def, Object... datas) {
		setVisibleButton(REFRESH, true);
		setVisibleButton(UPDATE, true);
		setVisibleButton(COPY, true);

		setEnabledButton(REFRESH, ne_info_def != null);
		setEnabledButton(UPDATE, false);
		setEnabledButton(COPY, ne_info_def != null);

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

	public void display(Model4NeThresholdIf ne_threshold) {
		Control topControl = (((StackLayout) panelContents.getLayout())).topControl;
		if (topControl instanceof PanelInput4NeThresholdIf) {
			((PanelInput4NeThresholdIf) topControl).display(ne_threshold);
			setEnabledButton(UPDATE, ne_threshold.getField_defs().length == 1);
		}
	}

	public void clear() {
		Control topControl = (((StackLayout) panelContents.getLayout())).topControl;
		if (topControl instanceof PanelInput4NeThresholdIf) {
			((PanelInput4NeThresholdIf) topControl).clear();
		}
	}

	protected void selectionChanged(Model4NeThresholdIf ne_threshold, EMP_MODEL_NE_INFO_FIELD ne_info_field_def) {
		setEnabledButton(UPDATE, (ne_threshold != null && ne_info_field_def != null));
	}

}
