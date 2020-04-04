package com.hellonms.platforms.emp_orange.client_swt.widget.selector;

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

public class SelectorTree4NeGroup extends Composite {

	public interface SelectorTree4NeGroupPopupListenerIf {

		public void open(SelectorTree4NeGroup selectorTree4NeGroup, NODE node, int[] ne_group_id_filters);

	}

	private static SelectorTree4NeGroupPopupListenerIf popupListener = new SelectorTree4NeGroupPopupListenerIf() {
		@Override
		public void open(SelectorTree4NeGroup selectorTree4NeGroup, NODE node, int[] ne_group_id_filters) {
			SelectorTree4NeGroupPopup popup = new SelectorTree4NeGroupPopup(selectorTree4NeGroup.getShell(), node, ne_group_id_filters);
			popup.setLocation(selectorTree4NeGroup.toDisplay(selectorTree4NeGroup.getSize().x - popup.getSize().x, selectorTree4NeGroup.getSize().y + 1));
			popup.open();
			if (popup.getNode() != null) {
				selectorTree4NeGroup.selectNeGroup(popup.getNode());
			}
		}
	};

	public static void setPopupListener(SelectorTree4NeGroupPopupListenerIf popupListener) {
		SelectorTree4NeGroup.popupListener = popupListener;
	}

	private TextInput4String textPath;

	private NODE node;

	private int[] ne_group_id_filters = {};

	private SelectionListener listener;

	/**
	 * 생성자 입니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 * @param style
	 *            스타일
	 */
	public SelectorTree4NeGroup(Composite parent, int style) {
		this(parent, style, new int[] {});
	}

	/**
	 * 생성자 입니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 * @param style
	 *            스타일
	 * @param ne_group_id_filters
	 *            NE그룹 필터
	 * @param ne_id_filters
	 *            NE 필터
	 */
	public SelectorTree4NeGroup(Composite parent, int style, int[] ne_group_id_filters) {
		super(parent, style);
		this.ne_group_id_filters = ne_group_id_filters;

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
		textPath.setBackground(ThemeFactory.getColor(COLOR_ONION.WIDGET_READ_ONLY));

		ButtonClick buttonPopup = new ButtonClick(this, SWT.ARROW | SWT.DOWN);
		buttonPopup.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		buttonPopup.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				popupListener.open(SelectorTree4NeGroup.this, node, ne_group_id_filters);
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

	/**
	 * NE그룹 필터를 설정합니다.
	 * 
	 * @param ne_group_id_filters
	 *            NE그룹 아이디 배열
	 */
	public void setNe_group_filters(int[] ne_group_id_filters) {
		this.ne_group_id_filters = ne_group_id_filters;
	}

}
