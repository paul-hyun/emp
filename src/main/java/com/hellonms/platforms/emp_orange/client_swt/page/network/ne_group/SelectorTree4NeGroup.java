package com.hellonms.platforms.emp_orange.client_swt.page.network.ne_group;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_onion.client_swt.widget.button.ButtonClick;
import com.hellonms.platforms.emp_onion.client_swt.widget.text.TextInput4String;
import com.hellonms.platforms.emp_onion.theme.ThemeBuilder4Onion.COLOR_ONION;
import com.hellonms.platforms.emp_onion.theme.ThemeFactory;
import com.hellonms.platforms.emp_orange.client_swt.page.network.network_tree.ModelClient4NetworkTree;
import com.hellonms.platforms.emp_orange.client_swt.page.network.network_tree.ModelClient4NetworkTree.NODE;
import com.hellonms.platforms.emp_orange.share.model.network.ne_group.Model4NeGroup;

/**
 * <p>
 * SelectorTree4NeGroup
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class SelectorTree4NeGroup extends Composite {

	/**
	 * NE그룹 셀렉터 팝업판넬의 리스너 인터페이스 입니다.
	 * 
	 * @since Version 1.4.0
	 * @author Hello NMS
	 */
	public interface SelectorTree4NeGroupPopupListenerIf {

		/**
		 * NE그룹 셀렉터 팝업판넬을 오픈합니다.
		 * 
		 * @param neGroupSelector
		 *            NE그룹 셀렉터
		 * @param node
		 *            노드
		 * @param ne_group_filters
		 *            NE그룹 아이디 필터
		 * @param check_management
		 *            관리여부
		 */
		public void open(SelectorTree4NeGroup neGroupSelector, NODE node);

	}

	private static SelectorTree4NeGroupPopupListenerIf popupListener = new SelectorTree4NeGroupPopupListenerIf() {
		@Override
		public void open(SelectorTree4NeGroup neGroupSelector, NODE node) {
			PanelPopup4NeGroup popup = new PanelPopup4NeGroup(neGroupSelector.getShell(), node);
			popup.setLocation(neGroupSelector.toDisplay(neGroupSelector.getSize().x - popup.getSize().x, neGroupSelector.getSize().y + 1));
			popup.open();
			if (popup.getNode() != null) {
				neGroupSelector.selectNeGroup(popup.getNode());
			}
		}
	};

	/**
	 * NE그룹 셀렉터 팝업판넬 리스너를 설정합니다.
	 * 
	 * @param popupListener
	 *            NE그룹 셀렉터 팝업판넬 리스너
	 */
	public static void setPopupListener(SelectorTree4NeGroupPopupListenerIf popupListener) {
		SelectorTree4NeGroup.popupListener = popupListener;
	}

	private TextInput4String textPath;

	private NODE node;

	private SelectionListener listener;

	public SelectorTree4NeGroup(Composite parent, int style) {
		super(parent, style);

		createGUI();
		pack();
	}

	private void createGUI() {
		GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.verticalSpacing = 0;
		gridLayout.horizontalSpacing = 0;
		gridLayout.marginHeight = 0;
		gridLayout.marginWidth = 0;
		setLayout(gridLayout);

		textPath = new TextInput4String(this, SWT.BORDER | SWT.READ_ONLY);
		textPath.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1));
		textPath.setBackground(ThemeFactory.getColor(COLOR_ONION.READ_ONLY));
		try {
			node = ModelClient4NetworkTree.getInstance().getNeGroup(Model4NeGroup.ROOT_NE_GROUP_ID);
			textPath.setText(node.getName());
		} catch (EmpException e) {
			node = null;
			e.printStackTrace();
		}

		ButtonClick buttonPopup = new ButtonClick(this, SWT.ARROW | SWT.DOWN);
		buttonPopup.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		buttonPopup.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				popupListener.open(SelectorTree4NeGroup.this, node);
			}
		});
	}

	@Override
	protected void checkSubclass() {
	}

	/**
	 * NE그룹을 반환합니다.
	 * 
	 * @return NE그룹 모델
	 */
	public Model4NeGroup getNeGroup() {
		return node == null ? null : (Model4NeGroup) node.getValue();
	}

	/**
	 * NE그룹을 설정합니다.
	 * 
	 * @param ne_group_id
	 *            NE그룹 아이디
	 */
	public void setNeGroup(int ne_group_id) {
		try {
			NODE node = ModelClient4NetworkTree.getInstance().getNeGroup(ne_group_id);
			selectNeGroup(node);
		} catch (EmpException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 선택 리스너를 추가합니다.
	 * 
	 * @param listener
	 *            선택 리스너
	 */
	public void addSelectionListener(SelectionListener listener) {
		this.listener = listener;
	}

	/**
	 * NE그룹을 선택합니다.
	 * 
	 * @param node
	 *            노드
	 */
	public void selectNeGroup(NODE node) {
		this.node = node;
		textPath.setText(node.getName());

		if (listener != null) {
			listener.widgetSelected(null);
		}
	}

}
