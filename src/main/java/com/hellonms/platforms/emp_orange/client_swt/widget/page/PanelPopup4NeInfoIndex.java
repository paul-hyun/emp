package com.hellonms.platforms.emp_orange.client_swt.widget.page;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
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
import com.hellonms.platforms.emp_onion.client_swt.data.tree.DataTreeAt;
import com.hellonms.platforms.emp_onion.client_swt.widget.button.ButtonClick;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelPopup;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelTree;
import com.hellonms.platforms.emp_onion.share.message.MESSAGE_CODE_ONION;
import com.hellonms.platforms.emp_onion.theme.ThemeBuilder4Onion.COLOR_ONION;
import com.hellonms.platforms.emp_onion.theme.ThemeFactory;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO;
import com.hellonms.platforms.emp_orange.share.model.network.ne_info.NE_INFO_INDEX;

/**
 * <p>
 * PanelPopup4NeInfoIndex
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class PanelPopup4NeInfoIndex extends PanelPopup {

	public class DataTree4NeInfoIndex extends DataTreeAt {

		@Override
		public Object getInput() {
			return this;
		}

		@Override
		public Object[] getElements(Object inputElement) {
			return getChildren(inputElement);
		}

		@Override
		public Object[] getChildren(Object parentElement) {
			if (this == parentElement) {
				return new Object[] { ne_info_def };
			} else if (parentElement == ne_info_def) {
				return ne_info_indexs;
			} else {
				return new Object[] {};
			}
		}

		@Override
		public Object getParent(Object element) {
			return null;
		}

		@Override
		public boolean hasChildren(Object element) {
			return 0 < getChildren(element).length;
		}

		@Override
		public String getText(Object element) {
			if (element instanceof EMP_MODEL_NE_INFO) {
				return ((EMP_MODEL_NE_INFO) element).getName();
			} else if (element instanceof NE_INFO_INDEX) {
				return ((NE_INFO_INDEX) element).toString(ne_info_def);
			}
			return super.getText(element);
		}

	}

	protected PanelTree panelTree;

	protected EMP_MODEL_NE_INFO ne_info_def;

	protected NE_INFO_INDEX[] ne_info_indexs = {};

	private NE_INFO_INDEX ne_info_index;

	protected boolean perform_finish = false;

	/**
	 * 생성자 입니다
	 * 
	 * @param shell
	 *            부모 쉘
	 * @param model
	 *            차트 모델
	 */
	public PanelPopup4NeInfoIndex(Shell shell, EMP_MODEL_NE_INFO ne_info_def, NE_INFO_INDEX[] ne_info_indexs) {
		super(shell);
		this.ne_info_def = ne_info_def;
		this.ne_info_indexs = ne_info_indexs;

		createGUI();
	}

	protected void createGUI() {
		setBackground(ThemeFactory.getColor(COLOR_ONION.PANEL_BG));
		setSize(320, 360);

		addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
				e.gc.setForeground(ThemeFactory.getColor(COLOR_ONION.WORKBENCH_BG));
				e.gc.drawRectangle(0, 0, getSize().x - 1, getSize().y - 1);
			}
		});

		setLayout(new GridLayout(2, false));

		panelTree = new PanelTree(this, SWT.NONE);
		panelTree.getTree().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		panelTree.setDataTree(new DataTree4NeInfoIndex());
		panelTree.expandToLevel(2);
		panelTree.getTree().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				PanelPopup4NeInfoIndex.this.performFinish();
			}
		});

		ButtonClick buttonOk = new ButtonClick(this, SWT.NONE);
		buttonOk.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				PanelPopup4NeInfoIndex.this.performFinish();
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
				PanelPopup4NeInfoIndex.this.performCancel();
			}
		});
		GridData gd_buttonCancel = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_buttonCancel.widthHint = 60;
		buttonCancel.setLayoutData(gd_buttonCancel);
		buttonCancel.setText(UtilLanguage.getMessage(MESSAGE_CODE_ONION.CANCEL));
	}

	protected void performFinish() {
		ISelection selection = panelTree.getSelection();
		if (selection.isEmpty()) {
			return;
		}
		Object element = ((IStructuredSelection) selection).getFirstElement();
		if (!(element instanceof NE_INFO_INDEX)) {
			return;
		}

		this.ne_info_index = (NE_INFO_INDEX) element;
		this.perform_finish = true;

		dispose();
	}

	protected void performCancel() {
		this.perform_finish = false;
		dispose();
	}

	public NE_INFO_INDEX getNe_info_index() {
		return perform_finish ? ne_info_index : null;
	}

}
