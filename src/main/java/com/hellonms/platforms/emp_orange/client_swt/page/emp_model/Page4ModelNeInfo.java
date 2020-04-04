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
import com.hellonms.platforms.emp_orange.client_swt.page.emp_model.Panel4ModelNeInfoDetail.Panel4ModelNeInfoDetailListnerIf;
import com.hellonms.platforms.emp_orange.client_swt.page.emp_model.Panel4ModelNeInfoFieldDetail.Panel4ModelNeFieldDetailListnerIf;
import com.hellonms.platforms.emp_orange.client_swt.page.emp_model.Panel4ModelNeInfoList.Panel4ModelNeInfoListListenerIf;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO_FIELD;

public class Page4ModelNeInfo extends Page {

	private class Panel4ModelListener implements Panel4ModelNeInfoListListenerIf, Panel4ModelNeInfoDetailListnerIf, Panel4ModelNeFieldDetailListnerIf {

		@Override
		public void refresh() {
			listener.refresh();
		}

		@Override
		public void selected(EMP_MODEL_NE_INFO info, EMP_MODEL_NE_INFO_FIELD info_field) {
			displayDetail(info, info_field);
		}

		@Override
		public void updated(EMP_MODEL_NE_INFO info) {
			listener.refresh();
			panel4ModelNeInfoDetail.displayDetail();
		}

		@Override
		public void deleted() {
			listener.refresh();
			displayDetail(null, null);
		}

		@Override
		public void updated(EMP_MODEL_NE_INFO_FIELD info_field) {
			listener.refresh();
			panel4ModelNeInfoFieldDetail.displayDetail();
		}

	}

	private Panel4ModelNeInfoList panel4ModelNeInfoList;

	private Composite dataPenel;

	private Panel4ModelNeInfoDetail panel4ModelNeInfoDetail;

	private Panel4ModelNeInfoFieldDetail panel4ModelNeInfoFieldDetail;

	private EMP_MODEL emp_model;

	private Panel4ModelListener childListener = new Panel4ModelListener();

	private Panel4ModelListenerIf listener;

	public Page4ModelNeInfo(Composite parent, int style, Panel4ModelListenerIf listener) {
		super(parent, style, "", "");
		this.listener = listener;

		createGUI();
	}

	private void createGUI() {
		getContentComposite().setLayout(new FormLayout());

		panel4ModelNeInfoList = new Panel4ModelNeInfoList(getContentComposite(), SWT.NONE, childListener);
		FormData fd_panel4ModelNeInfoList = new FormData();
		fd_panel4ModelNeInfoList.top = new FormAttachment(0, 5);
		fd_panel4ModelNeInfoList.bottom = new FormAttachment(100, -5);
		fd_panel4ModelNeInfoList.left = new FormAttachment(0, 5);
		fd_panel4ModelNeInfoList.right = new FormAttachment(50, -3);
		panel4ModelNeInfoList.setLayoutData(fd_panel4ModelNeInfoList);

		dataPenel = new Composite(getContentComposite(), SWT.NONE);
		dataPenel.setBackground(ThemeFactory.getColor(COLOR_ONION.PANEL_BG));
		FormData fd_dataPenel = new FormData();
		fd_dataPenel.top = new FormAttachment(panel4ModelNeInfoList, 0, SWT.TOP);
		fd_dataPenel.bottom = new FormAttachment(panel4ModelNeInfoList, 0, SWT.BOTTOM);
		fd_dataPenel.left = new FormAttachment(panel4ModelNeInfoList, 5, SWT.RIGHT);
		fd_dataPenel.right = new FormAttachment(100, -5);
		dataPenel.setLayoutData(fd_dataPenel);

		dataPenel.setLayout(new StackLayout());

		panel4ModelNeInfoDetail = new Panel4ModelNeInfoDetail(dataPenel, SWT.NONE, childListener);
		panel4ModelNeInfoFieldDetail = new Panel4ModelNeInfoFieldDetail(dataPenel, SWT.NONE, childListener);
	}

	public void displayTree(EMP_MODEL emp_model) {
		this.emp_model = emp_model;
		panel4ModelNeInfoList.displayTree(emp_model);

		displayDetail(null, null);
	}

	private void displayDetail(EMP_MODEL_NE_INFO info, EMP_MODEL_NE_INFO_FIELD info_field) {
		if (info != null) {
			panel4ModelNeInfoDetail.displayDetail(info);
			((StackLayout) dataPenel.getLayout()).topControl = panel4ModelNeInfoDetail;
		} else if (info_field != null && emp_model != null) {
			panel4ModelNeInfoFieldDetail.displayDetail(info_field, emp_model.getEnums(), emp_model.getEvents());
			((StackLayout) dataPenel.getLayout()).topControl = panel4ModelNeInfoFieldDetail;
		} else {
			((StackLayout) dataPenel.getLayout()).topControl = null;
		}

		dataPenel.layout();
	}

	public void refresh() {
		panel4ModelNeInfoList.refresh();
	}

}
