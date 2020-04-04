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
import com.hellonms.platforms.emp_orange.client_swt.page.emp_model.Panel4ModelEnumDetail.Panel4ModelEnumDetailListnerIf;
import com.hellonms.platforms.emp_orange.client_swt.page.emp_model.Panel4ModelEnumFieldDetail.Panel4ModelEnumFieldDetailListnerIf;
import com.hellonms.platforms.emp_orange.client_swt.page.emp_model.Panel4ModelEnumList.Panel4ModelEnumListListenerIf;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_ENUM;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_ENUM_FIELD;

public class Page4ModelEnum extends Page {

	private class Panel4ModelListener implements Panel4ModelEnumListListenerIf, Panel4ModelEnumDetailListnerIf, Panel4ModelEnumFieldDetailListnerIf {

		@Override
		public void refresh() {
			listener.refresh();
		}

		@Override
		public void selected(EMP_MODEL_ENUM enum_def, EMP_MODEL_ENUM_FIELD enum_field) {
			displayDetail(enum_def, enum_field);
		}

		@Override
		public void updated(EMP_MODEL_ENUM enum_def) {
			listener.refresh();
			panel4ModelEnumDetail.displayDetail();
		}

		@Override
		public void created(EMP_MODEL_ENUM_FIELD enum_field) {
			listener.refresh();
			panel4ModelEnumList.select(enum_field);
			displayDetail(null, enum_field);
		}

		@Override
		public void updated(EMP_MODEL_ENUM_FIELD enum_field) {
			listener.refresh();
			panel4ModelEnumFieldDetail.displayDetail();
		}

	}

	private Panel4ModelEnumList panel4ModelEnumList;

	private Composite dataPenel;

	@SuppressWarnings("unused")
	private EMP_MODEL emp_model;

	private Panel4ModelListener childListener = new Panel4ModelListener();

	private Panel4ModelListenerIf listener;

	private Panel4ModelEnumDetail panel4ModelEnumDetail;

	private Panel4ModelEnumFieldDetail panel4ModelEnumFieldDetail;

	public Page4ModelEnum(Composite parent, int style, Panel4ModelListenerIf listener) {
		super(parent, style, "", "");
		this.listener = listener;

		createGUI();
	}

	private void createGUI() {
		getContentComposite().setLayout(new FormLayout());

		panel4ModelEnumList = new Panel4ModelEnumList(getContentComposite(), SWT.NONE, childListener);
		FormData fd_panel4ModelEnumList = new FormData();
		fd_panel4ModelEnumList.top = new FormAttachment(0, 5);
		fd_panel4ModelEnumList.bottom = new FormAttachment(100, -5);
		fd_panel4ModelEnumList.left = new FormAttachment(0, 5);
		fd_panel4ModelEnumList.right = new FormAttachment(50, -3);
		panel4ModelEnumList.setLayoutData(fd_panel4ModelEnumList);

		dataPenel = new Composite(getContentComposite(), SWT.NONE);
		dataPenel.setBackground(ThemeFactory.getColor(COLOR_ONION.PANEL_BG));
		FormData fd_dataPenel = new FormData();
		fd_dataPenel.top = new FormAttachment(panel4ModelEnumList, 0, SWT.TOP);
		fd_dataPenel.bottom = new FormAttachment(panel4ModelEnumList, 0, SWT.BOTTOM);
		fd_dataPenel.left = new FormAttachment(panel4ModelEnumList, 5, SWT.RIGHT);
		fd_dataPenel.right = new FormAttachment(100, -5);
		dataPenel.setLayoutData(fd_dataPenel);

		dataPenel.setLayout(new StackLayout());

		panel4ModelEnumDetail = new Panel4ModelEnumDetail(dataPenel, SWT.NONE, childListener);
		panel4ModelEnumFieldDetail = new Panel4ModelEnumFieldDetail(dataPenel, SWT.NONE, childListener);
	}

	public void displayTree(EMP_MODEL emp_model) {
		this.emp_model = emp_model;
		panel4ModelEnumList.displayTree(emp_model);

		displayDetail(null, null);
	}

	private void displayDetail(EMP_MODEL_ENUM enum_def, EMP_MODEL_ENUM_FIELD enum_field) {
		if (enum_def != null) {
			panel4ModelEnumDetail.displayDetail(enum_def);
			((StackLayout) dataPenel.getLayout()).topControl = panel4ModelEnumDetail;
		} else if (enum_field != null) {
			panel4ModelEnumFieldDetail.displayDetail(enum_field);
			((StackLayout) dataPenel.getLayout()).topControl = panel4ModelEnumFieldDetail;
		} else {
			((StackLayout) dataPenel.getLayout()).topControl = null;
		}

		dataPenel.layout();
	}

	public void refresh() {
		panel4ModelEnumList.refresh();
	}

}
