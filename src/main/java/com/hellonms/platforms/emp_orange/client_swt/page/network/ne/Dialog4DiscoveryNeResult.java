package com.hellonms.platforms.emp_orange.client_swt.page.network.ne;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;

import com.hellonms.platforms.emp_core.share.language.UtilLanguage;
import com.hellonms.platforms.emp_onion.client_swt.data.DataFactory;
import com.hellonms.platforms.emp_onion.client_swt.widget.button.ButtonClick;
import com.hellonms.platforms.emp_onion.client_swt.widget.dialog.Dialog;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelTable;
import com.hellonms.platforms.emp_orange.client_swt.page.DataBuilder4Orange.DATA_TABLE_ORANGE;
import com.hellonms.platforms.emp_orange.client_swt.page.PanelBuilder4Orange.TABLE_ORANGE;
import com.hellonms.platforms.emp_orange.client_swt.page.PanelFactory;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.network.ne.Model4Ne;

public class Dialog4DiscoveryNeResult extends Dialog {

	protected PanelTable panelTable;

	protected Model4Ne[] model4Nes;

	protected Model4Ne[] model4CheckNes;

	protected boolean complete;

	/**
	 * 생성자 입니다.
	 * 
	 * @param parent
	 *            부모 쉘
	 * @param title
	 *            제목
	 * @param model
	 *            테이블 모델
	 */
	public Dialog4DiscoveryNeResult(Shell parent, String title, Model4Ne[] model4Nes) {
		super(parent, SWT.APPLICATION_MODAL | SWT.DIALOG_TRIM);
		super.setSize(670, 480);
		super.setText(title);

		this.model4Nes = model4Nes;

		createGUI();
	}

	/**
	 * GUI를 생성합니다.
	 */
	protected void createGUI() {
		setLayout(new GridLayout(1, false));

		panelTable = (PanelTable) PanelFactory.createPanelTable(TABLE_ORANGE.DISCOVERY_NE_RESULT, this, SWT.BORDER | SWT.FULL_SELECTION | SWT.CHECK, 0, null);
		panelTable.setDataTable(DataFactory.createDataTable(DATA_TABLE_ORANGE.DISCOVERY_NE_RESULT));
		panelTable.setDatas((Object) model4Nes);
		panelTable.getColumn(0).addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				boolean checked = false;

				TableItem[] items = panelTable.getItems();
				for (int row = 0; row < items.length; row++) {
					if (items[row].getChecked()) {
						checked = true;
						break;
					}
				}

				for (int row = 0; row < items.length; row++) {
					items[row].setChecked(!checked);
				}
			}
		});
		panelTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		Composite panelButton = new Composite(this, SWT.NONE);
		panelButton.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		panelButton.setLayout(new GridLayout(2, false));

		ButtonClick buttonOk = new ButtonClick(panelButton, SWT.NONE);
		buttonOk.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				List<Model4Ne> model4CheckNeList = new ArrayList<Model4Ne>();
				TableItem[] items = panelTable.getItems();
				for (int row = 0; row < items.length; row++) {
					if (items[row].getChecked()) {
						Model4Ne model4CheckNe = (Model4Ne) items[row].getData();
						model4CheckNeList.add(model4CheckNe);
					}
				}

				model4CheckNes = model4CheckNeList.toArray(new Model4Ne[0]);
				complete = true;
				Dialog4DiscoveryNeResult.this.dispose();
			}
		});
		GridData gd_buttonOk = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_buttonOk.widthHint = 60;
		buttonOk.setLayoutData(gd_buttonOk);
		buttonOk.setBounds(0, 0, 77, 22);
		buttonOk.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.OK));

		ButtonClick buttonCancel = new ButtonClick(panelButton, SWT.NONE);
		buttonCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				complete = false;
				Dialog4DiscoveryNeResult.this.dispose();
			}
		});
		GridData gd_buttonCancel = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_buttonCancel.widthHint = 60;
		buttonCancel.setLayoutData(gd_buttonCancel);
		buttonCancel.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.CANCEL));

		panelTable.refresh();

		checkComplete();
	}

	protected void checkComplete() {
	}

	public boolean isComplete() {
		return complete;
	}

	public Model4Ne[] getModel4CheckNes() {
		return model4CheckNes;
	}

}
