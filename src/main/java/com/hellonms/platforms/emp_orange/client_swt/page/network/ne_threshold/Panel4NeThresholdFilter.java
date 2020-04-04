/**
 * Copyright 2016 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.client_swt.page.network.ne_threshold;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.hellonms.platforms.emp_core.share.language.UtilLanguage;
import com.hellonms.platforms.emp_onion.client_swt.widget.label.LabelText;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.Panel;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelRound;
import com.hellonms.platforms.emp_onion.client_swt.widget.selector.SelectorCombo;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO;

/**
 * <p>
 * Panel4NeThresholdFilter
 * </p>
 *
 * @since 1.6
 * @create 2016. 1. 22.
 * @modified 2016. 1. 22.
 * @author jungsun
 */
public class Panel4NeThresholdFilter extends Panel {

	public interface Panel4NeThresholdFilterListenerIf {

		public void selectionChanged(EMP_MODEL_NE_INFO ne_info_def);

	}

	/**
	 * 리스너
	 */
	protected Panel4NeThresholdFilterListenerIf listener;

	protected SelectorCombo selectorComboNeThresholdCode;

	/**
	 * 생성자 입니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 * @param style
	 *            스타일
	 * @param ne_group_id_filters
	 *            NE그룹 아이디 필터
	 * @param ne_id_filters
	 *            NE 아이디 필터
	 * @param ne_define_id_filters
	 *            NE 정의 아이디 필터
	 * @param listener
	 *            리스너
	 */
	public Panel4NeThresholdFilter(Composite parent, int style, Panel4NeThresholdFilterListenerIf listener) {
		super(parent, style, UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.FILTER_TITLE, MESSAGE_CODE_ORANGE.NE_THRESHOLD));
		this.listener = listener;

		createGUI();
	}

	/**
	 * GUI를 생성합니다.
	 */
	protected void createGUI() {
		getContentComposite().setLayout(new FormLayout());

		PanelRound panelContents = new PanelRound(getContentComposite(), SWT.NONE);
		FormData fd_panelContents = new FormData();
		fd_panelContents.bottom = new FormAttachment(100, -5);
		fd_panelContents.right = new FormAttachment(100, -5);
		fd_panelContents.top = new FormAttachment(0, 5);
		fd_panelContents.left = new FormAttachment(0, 5);
		panelContents.setLayoutData(fd_panelContents);
		GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.marginHeight = 0;
		gridLayout.marginWidth = 0;
		panelContents.getContentComposite().setLayout(gridLayout);

		LabelText labelThresholdCode = new LabelText(panelContents.getContentComposite(), SWT.NONE);
		labelThresholdCode.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		labelThresholdCode.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.ITEM));

		selectorComboNeThresholdCode = new SelectorCombo(panelContents.getContentComposite(), SWT.READ_ONLY, new DataCombo4NeThresholdCode());
		selectorComboNeThresholdCode.getControl().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		selectorComboNeThresholdCode.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection selection = (IStructuredSelection) event.getSelection();
				if (!selection.isEmpty()) {
					listener.selectionChanged((EMP_MODEL_NE_INFO) selection.getFirstElement());
				}
			}
		});
	}

	/**
	 * 화면에 출력합니다.
	 * 
	 * @param durations
	 * @param locations
	 */
	public void display(EMP_MODEL_NE_INFO[] ne_info_defs) {
		boolean updateNeThresholdCodes = selectorComboNeThresholdCode.isNeedUpdate((Object) ne_info_defs);
		if (updateNeThresholdCodes) {
			Object selectedItem = selectorComboNeThresholdCode.getSelectedItem();
			selectorComboNeThresholdCode.setDatas((Object) ne_info_defs);
			selectorComboNeThresholdCode.setSelectedItem(selectorComboNeThresholdCode.findElement(selectedItem));
		}
	}

	/**
	 * 성능항목을 반환합니다.
	 * 
	 * @return 성능항목
	 */
	public EMP_MODEL_NE_INFO getNeThresholdCode() {
		Object selectedItem = selectorComboNeThresholdCode.getSelectedItem();
		if (selectedItem instanceof EMP_MODEL_NE_INFO) {
			return (EMP_MODEL_NE_INFO) selectedItem;
		} else {
			return null;
		}
	}

}
