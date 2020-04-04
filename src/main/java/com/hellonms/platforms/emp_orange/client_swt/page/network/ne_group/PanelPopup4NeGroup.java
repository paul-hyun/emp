package com.hellonms.platforms.emp_orange.client_swt.page.network.ne_group;

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
import com.hellonms.platforms.emp_orange.client_swt.page.network.network_tree.ModelClient4NetworkTree.NODE;

/**
 * <p>
 * PanelPopup4NeGroup
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class PanelPopup4NeGroup extends PanelPopup {

	protected PanelTreeIf panelTree;

	protected NODE node;

	protected boolean perform_finish = false;

	public PanelPopup4NeGroup(Shell shell, NODE node) {
		super(shell);
		this.node = node;

		createGUI();
	}

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

		panelTree = PanelFactory.createPanelTree(TREE_ORANGE.NE_GROUP, this, SWT.BORDER);
		panelTree.setDataTree(DataFactory.createDataTree(DATA_TREE_ORANGE.NE_GROUP));
		panelTree.getTree().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				PanelPopup4NeGroup.this.performFinish();
			}
		});
		panelTree.getTree().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		panelTree.expandToLevel(2);
		if (node != null) {
			panelTree.setSelection(new StructuredSelection(node));
		}

		ButtonClick buttonOk = new ButtonClick(this, SWT.NONE);
		buttonOk.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				PanelPopup4NeGroup.this.performFinish();
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
				PanelPopup4NeGroup.this.performCancel();
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

	protected void performFinish() {
		StructuredSelection selection = (StructuredSelection) panelTree.getSelection();
		if (!selection.isEmpty() && selection.getFirstElement() instanceof NODE && ((NODE) selection.getFirstElement()).isNeGroup()) {
			this.node = (NODE) selection.getFirstElement();
			this.perform_finish = true;
			dispose();
		}
	}

	protected void performCancel() {
		this.perform_finish = false;
		dispose();
	}

	public NODE getNode() {
		return perform_finish ? node : null;
	}

}
