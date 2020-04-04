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
import com.hellonms.platforms.emp_orange.client_swt.page.emp_model.Panel4ModelNeDetail.Panel4ModelNeDetailListnerIf;
import com.hellonms.platforms.emp_orange.client_swt.page.emp_model.Panel4ModelNeList.Panel4ModelNeListListenerIf;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE;

public class Page4ModelNe extends Page {

	public interface Page4ModelNeListenerIf extends Panel4ModelListenerIf {

		public void createImage(String path, String filename, byte[] filedata);

		public String[] queryListImagePath(final String path, final String[] extensions);

	}

	private class Panel4ModelListener implements Panel4ModelNeListListenerIf, Panel4ModelNeDetailListnerIf {

		@Override
		public void refresh() {
			listener.refresh();
		}

		@Override
		public void selected(EMP_MODEL_NE ne) {
			displayDetail(ne);
		}

		@Override
		public void updated(EMP_MODEL_NE ne) {
			listener.refresh();
			panel4ModelNeDetail.displayDetail();
		}

		@Override
		public void createImage(String path, String filename, byte[] filedata) {
			listener.createImage(path, filename, filedata);
		}

		@Override
		public String[] queryListImagePath(String path, String[] extensions) {
			return listener.queryListImagePath(path, extensions);
		}

	}

	private Panel4ModelNeList panel4ModelNeList;

	private Composite dataPenel;

	private Panel4ModelNeDetail panel4ModelNeDetail;

	private EMP_MODEL emp_model;

	private Panel4ModelListener childListener = new Panel4ModelListener();

	private Page4ModelNeListenerIf listener;

	public Page4ModelNe(Composite parent, int style, Page4ModelNeListenerIf listener) {
		super(parent, style, "", "");
		this.listener = listener;

		createGUI();
	}

	private void createGUI() {
		getContentComposite().setLayout(new FormLayout());

		panel4ModelNeList = new Panel4ModelNeList(getContentComposite(), SWT.NONE, childListener);
		FormData fd_panel4ModelNeList = new FormData();
		fd_panel4ModelNeList.top = new FormAttachment(0, 5);
		fd_panel4ModelNeList.bottom = new FormAttachment(100, -5);
		fd_panel4ModelNeList.left = new FormAttachment(0, 5);
		fd_panel4ModelNeList.right = new FormAttachment(50, -3);
		panel4ModelNeList.setLayoutData(fd_panel4ModelNeList);

		dataPenel = new Composite(getContentComposite(), SWT.NONE);
		dataPenel.setBackground(ThemeFactory.getColor(COLOR_ONION.PANEL_BG));
		FormData fd_dataPenel = new FormData();
		fd_dataPenel.top = new FormAttachment(panel4ModelNeList, 0, SWT.TOP);
		fd_dataPenel.bottom = new FormAttachment(panel4ModelNeList, 0, SWT.BOTTOM);
		fd_dataPenel.left = new FormAttachment(panel4ModelNeList, 5, SWT.RIGHT);
		fd_dataPenel.right = new FormAttachment(100, -5);
		dataPenel.setLayoutData(fd_dataPenel);

		dataPenel.setLayout(new StackLayout());

		panel4ModelNeDetail = new Panel4ModelNeDetail(dataPenel, SWT.NONE, childListener);
	}

	public void displayTree(EMP_MODEL emp_model) {
		this.emp_model = emp_model;
		panel4ModelNeList.displayTree(emp_model);

		displayDetail(null);
	}

	private void displayDetail(EMP_MODEL_NE ne) {
		if (ne != null) {
			panel4ModelNeDetail.displayDetail(emp_model, ne);
			((StackLayout) dataPenel.getLayout()).topControl = panel4ModelNeDetail;
		} else {
			((StackLayout) dataPenel.getLayout()).topControl = null;
		}

		dataPenel.layout();
	}

	public void refresh() {
		panel4ModelNeList.refresh();
	}

}
