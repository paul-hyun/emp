package com.hellonms.platforms.emp_orange.client_swt.page.emp_model;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;

import com.hellonms.platforms.emp_onion.client_swt.widget.page.Page;
import com.hellonms.platforms.emp_onion.theme.ThemeBuilder4Onion.COLOR_ONION;
import com.hellonms.platforms.emp_onion.theme.ThemeFactory;
import com.hellonms.platforms.emp_orange.client_swt.page.emp_model.Panel4ModelEventDetail.Panel4ModelEventDetailListnerIf;
import com.hellonms.platforms.emp_orange.client_swt.page.emp_model.Panel4ModelEventList.Panel4ModelEventListListenerIf;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_EVENT;

public class Page4ModelEvent extends Page {

	private class Panel4ModelListener implements Panel4ModelEventListListenerIf, Panel4ModelEventDetailListnerIf {

		@Override
		public void refresh() {
			listener.refresh();
		}

		@Override
		public void selected(EMP_MODEL_EVENT event) {
			displayDetail(event);
		}

		@Override
		public void updated(EMP_MODEL_EVENT event) {
			listener.refresh();
			panel4ModelEventDetail.displayDetail();
		}

	}

	private Panel4ModelEventList panel4ModelEventList;

	private Composite dataPenel;

	private Panel4ModelEventDetail panel4ModelEventDetail;

	@SuppressWarnings("unused")
	private EMP_MODEL emp_model;

	private Panel4ModelListener childListener = new Panel4ModelListener();

	private Panel4ModelListenerIf listener;

	public Page4ModelEvent(Composite parent, int style, Panel4ModelListenerIf listener) {
		super(parent, style, "", "");
		this.listener = listener;

		createGUI();
	}

	private void createGUI() {
		getContentComposite().setLayout(new FormLayout());

		panel4ModelEventList = new Panel4ModelEventList(getContentComposite(), SWT.NONE, childListener);
		FormData fd_panel4ModelEventList = new FormData();
		fd_panel4ModelEventList.top = new FormAttachment(0, 5);
		fd_panel4ModelEventList.bottom = new FormAttachment(100, -5);
		fd_panel4ModelEventList.left = new FormAttachment(0, 5);
		fd_panel4ModelEventList.right = new FormAttachment(50, -3);
		panel4ModelEventList.setLayoutData(fd_panel4ModelEventList);

		dataPenel = new Composite(getContentComposite(), SWT.NONE);
		dataPenel.setBackground(ThemeFactory.getColor(COLOR_ONION.PANEL_BG));
		FormData fd_dataPenel = new FormData();
		fd_dataPenel.top = new FormAttachment(panel4ModelEventList, 0, SWT.TOP);
		fd_dataPenel.bottom = new FormAttachment(panel4ModelEventList, 0, SWT.BOTTOM);
		fd_dataPenel.left = new FormAttachment(panel4ModelEventList, 5, SWT.RIGHT);
		fd_dataPenel.right = new FormAttachment(100, -5);
		dataPenel.setLayoutData(fd_dataPenel);

		dataPenel.setLayout(new StackLayout());

		panel4ModelEventDetail = new Panel4ModelEventDetail(dataPenel, SWT.NONE, childListener);
	}

	public void displayTree(EMP_MODEL emp_model) {
		this.emp_model = emp_model;
		panel4ModelEventList.displayTree(emp_model);

		displayDetail(null);
	}

	private void displayDetail(EMP_MODEL_EVENT event) {
		if (event != null) {
			panel4ModelEventDetail.displayDetail(event);
			((StackLayout) dataPenel.getLayout()).topControl = panel4ModelEventDetail;
		} else {
			((StackLayout) dataPenel.getLayout()).topControl = null;
		}

		dataPenel.layout();
	}

	public void refresh() {
		panel4ModelEventList.refresh();
	}

}
