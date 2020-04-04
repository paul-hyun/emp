package com.hellonms.platforms.emp_orange.client_swt.widget.page;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Shell;

import com.hellonms.platforms.emp_core.share.language.UtilLanguage;
import com.hellonms.platforms.emp_onion.client_swt.data.chart.DataChartIf;
import com.hellonms.platforms.emp_onion.client_swt.data.tree.DataTreeAt;
import com.hellonms.platforms.emp_onion.client_swt.widget.button.ButtonClick;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelPopup;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelTree4Check;
import com.hellonms.platforms.emp_onion.share.message.MESSAGE_CODE_ONION;
import com.hellonms.platforms.emp_onion.theme.ThemeBuilder4Onion.COLOR_ONION;
import com.hellonms.platforms.emp_onion.theme.ThemeFactory;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;

/**
 * <p>
 * PanelPopup4ChartItem
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class PanelPopup4ChartItem extends PanelPopup {

	public class DataTree4ChartItem extends DataTreeAt {

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
				return new String[] { ALL };
			} else if (parentElement == ALL) {
				String[] columns = new String[model.getColumnCount()];
				for (int i = 0; i < columns.length; i++) {
					columns[i] = model.getColumnTitle(i);
				}
				return columns;
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

	}

	protected static final String ALL = UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.ALL);

	protected PanelTree4Check checkTreeViewer;

	protected DataChartIf model;

	/**
	 * 생성자 입니다
	 * 
	 * @param shell
	 *            부모 쉘
	 * @param model
	 *            차트 모델
	 */
	public PanelPopup4ChartItem(Shell shell, DataChartIf model) {
		super(shell);
		this.model = model;

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

		checkTreeViewer = new PanelTree4Check(this, SWT.NONE);
		checkTreeViewer.getTree().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		checkTreeViewer.setDataTree(new DataTree4ChartItem());
		checkTreeViewer.expandToLevel(2);
		List<String> selectedItemList = new ArrayList<String>();
		for (int i = 0; i < model.getColumnCount(); i++) {
			if (model.isColumn_view(i)) {
				selectedItemList.add(model.getColumnTitle(i));
			}
		}
		checkTreeViewer.setCheckedElements(selectedItemList.toArray());

		ButtonClick buttonOk = new ButtonClick(this, SWT.NONE);
		buttonOk.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				PanelPopup4ChartItem.this.performFinish();
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
				PanelPopup4ChartItem.this.performCancel();
			}
		});
		GridData gd_buttonCancel = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_buttonCancel.widthHint = 60;
		buttonCancel.setLayoutData(gd_buttonCancel);
		buttonCancel.setText(UtilLanguage.getMessage(MESSAGE_CODE_ONION.CANCEL));
	}

	protected void performFinish() {
		Object[] checkedElements = checkTreeViewer.getCheckedElements();
		List<Integer> column_hide_list = new ArrayList<Integer>();
		for (int i = 0; i < model.getColumnCount(); i++) {
			String columnTitle = model.getColumnTitle(i);
			boolean hide = true;
			for (Object checkedElement : checkedElements) {
				if (checkedElement.equals(columnTitle)) {
					hide = false;
					break;
				}
			}
			if (hide) {
				column_hide_list.add(i);
			}
		}
		int[] column_hides = new int[column_hide_list.size()];
		for (int i = 0; i < column_hides.length; i++) {
			column_hides[i] = column_hide_list.get(i);
		}
		model.setColumn_hides(column_hides);

		dispose();
	}

	protected void performCancel() {
		dispose();
	}

}
