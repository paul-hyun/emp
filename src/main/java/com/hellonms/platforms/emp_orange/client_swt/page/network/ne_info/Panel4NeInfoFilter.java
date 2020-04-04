package com.hellonms.platforms.emp_orange.client_swt.page.network.ne_info;

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
 * Panel4NeInfoFilter
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class Panel4NeInfoFilter extends Panel {

	/**
	 * 성능이력 검색 판넬의 리스너 인터페이스 입니다.
	 * 
	 * @since Version 1.4.0
	 * @author Hello NMS
	 */
	public interface Panel4NeInfoFilterListenerIf {

		/**
		 * 성능이력 리스트를 조회합니다.
		 */
		public void selectionChanged(EMP_MODEL_NE_INFO ne_info_def);

	}

	/**
	 * 리스너
	 */
	protected Panel4NeInfoFilterListenerIf listener;

	protected SelectorCombo selectorComboNeInfoCode;

	protected EMP_MODEL_NE_INFO selected;

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
	public Panel4NeInfoFilter(Composite parent, int style, Panel4NeInfoFilterListenerIf listener) {
		super(parent, style, UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.FILTER_TITLE, MESSAGE_CODE_ORANGE.NE_INFO));
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

		LabelText labelFromDate = new LabelText(panelContents.getContentComposite(), SWT.NONE);
		labelFromDate.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		labelFromDate.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.ITEM));

		selectorComboNeInfoCode = new SelectorCombo(panelContents.getContentComposite(), SWT.READ_ONLY, new DataCombo4NeInfoCode());
		selectorComboNeInfoCode.getControl().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		selectorComboNeInfoCode.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection selection = (IStructuredSelection) event.getSelection();
				if (!selection.isEmpty()) {
					selected = (EMP_MODEL_NE_INFO) selection.getFirstElement();
					listener.selectionChanged(selected);
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
		boolean updateNeInfoCodes = selectorComboNeInfoCode.isNeedUpdate((Object) ne_info_defs);
		if (selected != null) {
			selectorComboNeInfoCode.setSelectedItem(selected);
		}
		if (updateNeInfoCodes) {
			Object selectedItem = selectorComboNeInfoCode.getSelectedItem();
			selectorComboNeInfoCode.setDatas((Object) ne_info_defs);
			if (selectedItem == null) {
				selectedItem = selected;
			}
			selectorComboNeInfoCode.setSelectedItem(selectorComboNeInfoCode.findElement(selectedItem));
		}
	}

	/**
	 * 성능항목을 반환합니다.
	 * 
	 * @return 성능항목
	 */
	public EMP_MODEL_NE_INFO getNeInfoCode() {
		Object selectedItem = selectorComboNeInfoCode.getSelectedItem();
		if (selectedItem instanceof EMP_MODEL_NE_INFO) {
			return (EMP_MODEL_NE_INFO) selectedItem;
		} else {
			return null;
		}
	}

}
