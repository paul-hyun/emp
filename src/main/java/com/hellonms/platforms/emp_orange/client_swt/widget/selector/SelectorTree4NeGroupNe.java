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

public class SelectorTree4NeGroupNe extends Composite {

	public interface SelectorTree4NeGroupNePopupListenerIf {

		public void open(SelectorTree4NeGroupNe selectorTree4NeGroupNe, NODE node, int[] ne_group_id_filters, int[] ne_id_filters);

	}

	private static SelectorTree4NeGroupNePopupListenerIf popupListener = new SelectorTree4NeGroupNePopupListenerIf() {
		@Override
		public void open(SelectorTree4NeGroupNe selectorTree4NeGroupNe, NODE node, int[] ne_group_id_filters, int[] ne_id_filters) {
			SelectorTree4NeGroupNePopup popup = new SelectorTree4NeGroupNePopup(selectorTree4NeGroupNe.getShell(), node, ne_group_id_filters, ne_id_filters);
			popup.setLocation(selectorTree4NeGroupNe.toDisplay(selectorTree4NeGroupNe.getSize().x - popup.getSize().x, selectorTree4NeGroupNe.getSize().y + 1));
			popup.open();
			if (popup.getNode() != null) {
				selectorTree4NeGroupNe.select(popup.getNode());
			}
		}
	};

	public static void setPopupListener(SelectorTree4NeGroupNePopupListenerIf popupListener) {
		SelectorTree4NeGroupNe.popupListener = popupListener;
	}

	private TextInput4String textPath;

	private ButtonClick buttonPopup;

	private NODE node;

	private int[] ne_group_id_filters = {};

	private int[] ne_id_filters = {};

	private SelectionListener listener;

	/**
	 * 생성자 입니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 * @param style
	 *            스타일
	 */
	public SelectorTree4NeGroupNe(Composite parent, int style) {
		this(parent, style, new int[] {}, new int[] {});
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
	public SelectorTree4NeGroupNe(Composite parent, int style, int[] ne_group_id_filters, int[] ne_id_filters) {
		super(parent, style);
		this.ne_group_id_filters = ne_group_id_filters;
		this.ne_id_filters = ne_id_filters;

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

		buttonPopup = new ButtonClick(this, SWT.ARROW | SWT.DOWN);
		buttonPopup.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		buttonPopup.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				popupListener.open(SelectorTree4NeGroupNe.this, node, ne_group_id_filters, ne_id_filters);
			}
		});
	}

	@Override
	protected void checkSubclass() {
	}

	/**
	 * 선택된 노드를 반환합니다.
	 * 
	 * @return	노드
	 */
	public Object getSelected() {
		return node == null ? null : node.getValue();
	}

	/**
	 * NE그룹을 설정합니다.
	 * 
	 * @param ne_group_id	NE그룹 아이디
	 */
	public void setNeGroup(int ne_group_id) {
		try {
			NODE node = ModelClient4NetworkTree.getInstance().getNeGroup(ne_group_id);
			select(node);
		} catch (EmpException e) {
			e.printStackTrace();
		}
	}

	/**
	 * NE를 설정합니다.
	 * 
	 * @param ne_id	NE 아이디
	 */
	public void setNe(int ne_id) {
		try {
			NODE node = ModelClient4NetworkTree.getInstance().getNe(ne_id);
			select(node);
		} catch (EmpException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 선택 리스너를 추가합니다.
	 * 
	 * @param listener	선택 리스너
	 */
	public void addSelectionListener(SelectionListener listener) {
		this.listener = listener;
	}

	/**
	 * 노드를 선택합니다.
	 * 
	 * @param node	노드
	 */
	public void select(NODE node) {
		this.node = node;
		textPath.setText(node.getName());

		if (listener != null) {
			listener.widgetSelected(null);
		}
	}

	@Override
	public void setEnabled(boolean enabled) {
		textPath.setEnabled(enabled);
		buttonPopup.setEnabled(enabled);
	}

}
