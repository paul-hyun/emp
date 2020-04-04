package com.hellonms.platforms.emp_orange.client_swt.widget.selector;

import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Shell;

import com.hellonms.platforms.emp_core.share.language.UtilLanguage;
import com.hellonms.platforms.emp_onion.client_swt.data.DataFactory;
import com.hellonms.platforms.emp_onion.client_swt.widget.button.ButtonClick;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelPopup;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelTreeIf;
import com.hellonms.platforms.emp_onion.share.message.MESSAGE_CODE_ONION;
import com.hellonms.platforms.emp_onion.theme.ThemeBuilder4Onion.COLOR_ONION;
import com.hellonms.platforms.emp_onion.theme.ThemeFactory;
import com.hellonms.platforms.emp_orange.client_swt.page.DataBuilder4Orange.DATA_TREE_ORANGE;
import com.hellonms.platforms.emp_orange.client_swt.page.PanelBuilder4Orange.TREE_ORANGE;
import com.hellonms.platforms.emp_orange.client_swt.page.PanelFactory;
import com.hellonms.platforms.emp_orange.client_swt.page.network.ne.DataTree4Ne;
import com.hellonms.platforms.emp_orange.client_swt.page.network.network_tree.ModelClient4NetworkTree.NODE;

public class SelectorTree4NePopup extends PanelPopup {

	/**
	 * 트리뷰어
	 */
	protected PanelTreeIf panelTree;

	/**
	 * NE 셀렉터의 트리모델
	 */
	protected DataTree4Ne dataTree;

	/**
	 * 노드
	 */
	protected NODE node;

	/**
	 * 완료상태
	 */
	protected boolean perform_finish = false;

	/**
	 * 생성자 입니다.
	 * 
	 * @param shell
	 *            부모 쉘
	 * @param node
	 *            노드
	 * @param ne_group_id_filters
	 *            NE그룹 필터
	 * @param ne_id_filters
	 *            NE 필터
	 * @param ne_define_id_filters
	 *            NE 정의 필터
	 * @param check_management
	 *            관리여부
	 */
	public SelectorTree4NePopup(Shell shell, NODE node, int[] ne_group_id_filters, int[] ne_id_filters) {
		super(shell);
		this.node = node;
		dataTree = (DataTree4Ne) DataFactory.createDataTree(DATA_TREE_ORANGE.NE);
		dataTree.setNe_group_id_filters(ne_group_id_filters);
		dataTree.setNe_id_filters(ne_id_filters);

		createGUI();
	}

	/**
	 * GUI를 생성합니다.
	 */
	protected void createGUI() {
		setBackground(ThemeFactory.getColor(COLOR_ONION.PANEL_BG));
		setSize(280, 320);

		addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
				e.gc.setForeground(ThemeFactory.getColor(COLOR_ONION.WORKBENCH_BG));
				e.gc.drawRectangle(0, 0, getSize().x - 1, getSize().y - 1);
			}
		});

		setLayout(new GridLayout(2, false));

		panelTree = PanelFactory.createPanelTree(TREE_ORANGE.NE, this, SWT.BORDER);
		panelTree.getTree().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				SelectorTree4NePopup.this.performFinish();
			}
		});
		panelTree.getTree().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		panelTree.setDataTree(dataTree);
		panelTree.expandToLevel(2);
		if (node != null) {
			panelTree.setSelection(new StructuredSelection(node));
		}

		ButtonClick buttonOk = new ButtonClick(this, SWT.NONE);
		buttonOk.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				SelectorTree4NePopup.this.performFinish();
			}
		});
		GridData gd_buttonOk = new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1);
		gd_buttonOk.widthHint = 60;
		buttonOk.setLayoutData(gd_buttonOk);
		buttonOk.setText(UtilLanguage.getMessage(MESSAGE_CODE_ONION.OK));

		ButtonClick buttonCancel = new ButtonClick(this, SWT.NONE);
		buttonCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				SelectorTree4NePopup.this.performCancel();
			}
		});
		GridData gd_buttonCancel = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_buttonCancel.widthHint = 60;
		buttonCancel.setLayoutData(gd_buttonCancel);
		buttonCancel.setText(UtilLanguage.getMessage(MESSAGE_CODE_ONION.CANCEL));
	}

	@Override
	protected void checkSubclass() {
	}

	/**
	 * 완료작업을 수행합니다.
	 */
	protected void performFinish() {
		StructuredSelection selection = (StructuredSelection) panelTree.getSelection();
		if (!selection.isEmpty() && selection.getFirstElement() instanceof NODE && ((NODE) selection.getFirstElement()).isNe()) {
			this.node = (NODE) selection.getFirstElement();
			perform_finish = true;
			dispose();
		}
	}

	/**
	 * 취소작업을 수행합니다.
	 */
	protected void performCancel() {
		perform_finish = false;
		dispose();
	}

	/**
	 * 노드를 반환합니다.
	 * 
	 * @return 노드
	 */
	public NODE getNode() {
		return perform_finish ? node : null;
	}

}