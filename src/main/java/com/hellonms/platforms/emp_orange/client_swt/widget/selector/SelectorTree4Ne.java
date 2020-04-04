package com.hellonms.platforms.emp_orange.client_swt.widget.selector;

import java.util.HashSet;
import java.util.Set;

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
import com.hellonms.platforms.emp_orange.share.model.network.ne.Model4Ne;

public class SelectorTree4Ne extends Composite {

	public interface SelectorTree4NePopupListenerIf {

		public void open(SelectorTree4Ne selectorTree4Ne, NODE node, int[] ne_group_id_filters, int[] ne_id_filters);

	}

	private static SelectorTree4NePopupListenerIf popupListener = new SelectorTree4NePopupListenerIf() {
		@Override
		public void open(SelectorTree4Ne selectorTree4Ne, NODE node, int[] ne_group_id_filters, int[] ne_id_filters) {
			SelectorTree4NePopup popup = new SelectorTree4NePopup(selectorTree4Ne.getShell(), node, ne_group_id_filters, ne_id_filters);
			popup.setLocation(selectorTree4Ne.toDisplay(selectorTree4Ne.getSize().x - popup.getSize().x, selectorTree4Ne.getSize().y + 1));
			popup.open();
			if (popup.getNode() != null) {
				selectorTree4Ne.selectNe(popup.getNode());
			}
		}
	};

	public static void setPopupListener(SelectorTree4NePopupListenerIf popupListener) {
		SelectorTree4Ne.popupListener = popupListener;
	}

	private TextInput4String textPath;

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
	public SelectorTree4Ne(Composite parent, int style) {
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
	public SelectorTree4Ne(Composite parent, int style, int[] ne_group_id_filters, int[] ne_id_filters) {
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

		ButtonClick buttonPopup = new ButtonClick(this, SWT.ARROW | SWT.DOWN);
		buttonPopup.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		buttonPopup.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				popupListener.open(SelectorTree4Ne.this, node, ne_group_id_filters, ne_id_filters);
			}
		});
	}

	@Override
	protected void checkSubclass() {
	}

	/**
	 * NE를 반환합니다.
	 * 
	 * @return NE 모델
	 */
	public Model4Ne getNe() {
		return node == null ? null : node.isNe() ? (Model4Ne) node.getValue() : null;
	}

	/**
	 * 첫 번째 NE를 설정합니다.
	 */
	public void setFirstNe() {
		try {
			Set<Integer> neGroupIdFilter = new HashSet<Integer>();
			for (int neGroupId : ne_group_id_filters) {
				neGroupIdFilter.add(neGroupId);
			}
			Set<Integer> neIdFilter = new HashSet<Integer>();
			for (int neId : ne_id_filters) {
				neIdFilter.add(neId);
			}
			NODE node = ModelClient4NetworkTree.getInstance().getFirstNe(neGroupIdFilter, neIdFilter);
			selectNe(node);
		} catch (EmpException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 선택된 NE가 없도록 설정합니다.
	 */
	public void setNoNe() {
		selectNe(null);
	}

	/**
	 * NE를 설정합니다.
	 * 
	 * @param ne_id
	 *            NE 아이디
	 */
	public void setNe(int ne_id) {
		try {
			NODE node = ModelClient4NetworkTree.getInstance().getNe(ne_id);
			selectNe(node);
		} catch (EmpException e) {
			e.printStackTrace();
		}
	}

	/**
	 * NE를 선택합니다.
	 * 
	 * @param node
	 *            노드
	 */
	public void selectNe(NODE node) {
		this.node = node;
		textPath.setText(node == null ? "" : node.getName());

		if (listener != null) {
			listener.widgetSelected(null);
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
	 * NE그룹 필터를 설정합니다.
	 * 
	 * @param ne_group_id_filters
	 *            NE그룹 아이디 배열
	 */
	public void setNe_group_id_filters(int[] ne_group_id_filters) {
		this.ne_group_id_filters = ne_group_id_filters;
	}

	/**
	 * NE 필터를 설정합니다.
	 * 
	 * @param ne_id_filters
	 *            NE 아이디 배열
	 */
	public void setNe_id_filters(int[] ne_id_filters) {
		this.ne_id_filters = ne_id_filters;
	}

}
