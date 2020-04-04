package com.hellonms.platforms.emp_orange.client_swt.page.emp_model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;

import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_core.share.language.UtilLanguage;
import com.hellonms.platforms.emp_onion.client_swt.data.tree.DataTreeAt;
import com.hellonms.platforms.emp_onion.client_swt.widget.dialog.DialogMessage;
import com.hellonms.platforms.emp_onion.client_swt.widget.label.LabelImage;
import com.hellonms.platforms.emp_onion.client_swt.widget.label.LabelText;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.Panel;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelRound;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelTree4Check;
import com.hellonms.platforms.emp_onion.client_swt.widget.text.TextInput;
import com.hellonms.platforms.emp_orange.client_swt.util.resource.UtilResource4Orange;
import com.hellonms.platforms.emp_orange.client_swt.widget.selector.SelectorImageNode;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO;
import com.hellonms.platforms.emp_orange.share.model.fault.event.SEVERITY;
import com.hellonms.platforms.emp_util.file.UtilFile;
import com.hellonms.platforms.emp_util.string.UtilString;

public class Panel4ModelNeDetail extends Panel {

	public interface Panel4ModelNeDetailListnerIf extends Panel4ModelListenerIf {

		public void updated(EMP_MODEL_NE ne_def);

		public void createImage(String path, String filename, byte[] filedata);

		public String[] queryListImagePath(final String path, final String[] extensions);

	}

	private class DataTree extends DataTreeAt {

		@Override
		public Object getInput() {
			return this;
		}

		@Override
		public Object[] getChildren(Object element) {
			return getElements(element);
		}

		@Override
		public Object[] getElements(Object element) {
			if (element == this) {
				return new String[] { EMP_MODEL.NE_INFO_LIST };
			} else if (element.equals(EMP_MODEL.NE_INFO_LIST)) {
				if (emp_model == null) {
					return DataTreeAt.EMPTY;
				} else {
					EMP_MODEL_NE_INFO[] infos = emp_model.getNe_infos();
					Integer[] ne_info_codes = new Integer[infos.length];
					for (int i = 0; i < infos.length; i++) {
						ne_info_codes[i] = infos[i].getCode();
					}
					return ne_info_codes;
				}
			} else {
				return DataTreeAt.EMPTY;
			}
		}

		@Override
		public Object getParent(Object element) {
			if (element == this) {
				return null;
			} else if (element.equals(EMP_MODEL.NE_INFO_LIST)) {
				return this;
			} else if (element instanceof EMP_MODEL_NE_INFO) {
				return EMP_MODEL.NE_INFO_LIST;
			} else {
				return null;
			}
		}

		@Override
		public boolean hasChildren(Object element) {
			if (element == this) {
				return true;
			} else if (element.equals(EMP_MODEL.NE_INFO_LIST)) {
				return emp_model != null;
			} else {
				return false;
			}
		}

		@Override
		public Image getImage(Object element) {
			return null;
		}

		@Override
		public String getText(Object element) {
			if (emp_model != null && element instanceof Integer) {
				EMP_MODEL_NE_INFO info = emp_model.getNe_info((Integer) element);
				return info == null ? String.valueOf(element) : UtilString.format("{}", info.getName());
			} else {
				return super.getText(element);
			}
		}

	}

	private LabelImage image_severity;
	private LabelText label_error;

	private TextInput text_manufacturer;
	private TextInput text_oui;
	private TextInput text_product_class;
	private TextInput text_ne_oid;
	private SelectorImageNode selectorImageNode;
	private PanelTree4Check tree_infos;

	private EMP_MODEL emp_model;

	private EMP_MODEL_NE ne_def;

	private Panel4ModelNeDetailListnerIf listener;

	public Panel4ModelNeDetail(Composite parent, int style, Panel4ModelNeDetailListnerIf listener) {
		super(parent, style, EMP_MODEL.NE);
		this.listener = listener;

		createGUI();
	}

	/**
	 * GUI를 생성합니다.
	 */
	protected void createGUI() {
		getContentComposite().setLayout(new FormLayout());

		PanelRound panelError = new PanelRound(getContentComposite(), SWT.NONE);
		FormData fd_panelError = new FormData();
		fd_panelError.top = new FormAttachment(0, 5);
		fd_panelError.left = new FormAttachment(0, 5);
		fd_panelError.right = new FormAttachment(100, -5);
		panelError.setLayoutData(fd_panelError);
		{
			panelError.getContentComposite().setLayout(new GridLayout(2, false));

			image_severity = new LabelImage(panelError.getContentComposite(), SWT.NONE, UtilResource4Orange.getImage(SEVERITY.CLEAR, false));
			image_severity.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));

			label_error = new LabelText(panelError.getContentComposite(), SWT.NONE);
			label_error.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		}

		PanelRound panelData = new PanelRound(getContentComposite(), SWT.NONE);
		FormData fd_panelData = new FormData();
		fd_panelData.top = new FormAttachment(panelError, 5, SWT.BOTTOM);
		fd_panelData.bottom = new FormAttachment(100, -5);
		fd_panelData.right = new FormAttachment(100, -80);
		fd_panelData.left = new FormAttachment(0, 5);
		panelData.setLayoutData(fd_panelData);
		{
			panelData.getContentComposite().setLayout(new GridLayout(2, false));

			LabelText label_manufacturer = new LabelText(panelData.getContentComposite(), SWT.NONE, true);
			label_manufacturer.setText("Manufacturer");
			label_manufacturer.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));

			text_manufacturer = new TextInput(panelData.getContentComposite(), SWT.BORDER);
			text_manufacturer.setToolTipText("제조사 이름 입니다.");
			text_manufacturer.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
			text_manufacturer.addModifyListener(new ModifyListener() {
				@Override
				public void modifyText(ModifyEvent arg0) {
					if (ne_def != null) {
						String manufacturer = text_manufacturer.getText().trim();
						ne_def.setManufacturer(manufacturer);
						listener.updated(ne_def);
					}
				}
			});

			LabelText label_oui = new LabelText(panelData.getContentComposite(), SWT.NONE, true);
			label_oui.setText("Oui");
			label_oui.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));

			text_oui = new TextInput(panelData.getContentComposite(), SWT.BORDER);
			text_oui.setToolTipText("제조사 OUI(Organizationally Unique Identifier)입니다. 모를 경우는 아무 값이나 입력하세요.");
			text_oui.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
			text_oui.addModifyListener(new ModifyListener() {
				@Override
				public void modifyText(ModifyEvent arg0) {
					if (ne_def != null) {
						String oui = text_oui.getText().trim();
						ne_def.setOui(oui);
						listener.updated(ne_def);
					}
				}
			});

			LabelText label_product_class = new LabelText(panelData.getContentComposite(), SWT.NONE, true);
			label_product_class.setText("Product_class");
			label_product_class.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));

			text_product_class = new TextInput(panelData.getContentComposite(), SWT.BORDER);
			text_product_class.setToolTipText("제품 모델명 입니다. (유일해야 합니다.)");
			text_product_class.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
			text_product_class.addModifyListener(new ModifyListener() {
				@Override
				public void modifyText(ModifyEvent arg0) {
					if (ne_def != null) {
						String product_class = text_product_class.getText().trim();
						ne_def.setProduct_class(product_class);
						listener.updated(ne_def);
					}
				}
			});

			LabelText label_ne_oid = new LabelText(panelData.getContentComposite(), SWT.NONE, true);
			label_ne_oid.setText("Ne_oid");
			label_ne_oid.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));

			text_ne_oid = new TextInput(panelData.getContentComposite(), SWT.BORDER);
			text_ne_oid.setToolTipText("NE 검색 시 장비 타입을 구분할 값 입니다. ((유일해야 합니다. 기본값으로 사용하고자 한다면 '*'을 입력하면 됩니다.)");
			text_ne_oid.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
			text_ne_oid.addModifyListener(new ModifyListener() {
				@Override
				public void modifyText(ModifyEvent arg0) {
					if (ne_def != null) {
						String ne_oid = text_ne_oid.getText().trim();
						ne_def.setNe_oid(ne_oid);
						listener.updated(ne_def);
					}
				}
			});

			LabelText label_ne_icon = new LabelText(panelData.getContentComposite(), SWT.NONE, true);
			label_ne_icon.setText("Ne_icon");
			label_ne_icon.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));

			Composite composite_ne_icon = new Composite(panelData.getContentComposite(), SWT.NONE);
			composite_ne_icon.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
			GridLayout gl_composite_ne_icon = new GridLayout(2, false);
			gl_composite_ne_icon.marginHeight = 0;
			gl_composite_ne_icon.marginWidth = 0;
			composite_ne_icon.setLayout(gl_composite_ne_icon);
			{
				selectorImageNode = new SelectorImageNode(composite_ne_icon, SWT.BORDER);
				selectorImageNode.setToolTipText("NE를 표현 할 ICON 입니다.");
				selectorImageNode.setImagePaths(listener.queryListImagePath("/data/image/node_icon/", new String[] { "png" }));
				selectorImageNode.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
				selectorImageNode.addModifyListener(new ModifyListener() {
					@Override
					public void modifyText(ModifyEvent e) {
						if (ne_def != null) {
							String ne_icon = selectorImageNode.getValue().trim();
							ne_def.setNe_icon(ne_icon);
							listener.updated(ne_def);
						}
					}
				});

				Button button = new Button(composite_ne_icon, SWT.NONE);
				button.setText(UtilLanguage.getMessage("추가"));
				button.setToolTipText("Ne_icon을 추가합니다.");
				GridData gd_button = new GridData(SWT.CENTER, SWT.CENTER, false, false);
				gd_button.widthHint = 80;
				button.setLayoutData(gd_button);
				button.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						FileDialog fileDialog = new FileDialog(getShell(), SWT.OPEN);
						fileDialog.setFilterExtensions(new String[] { "*.png" });
						fileDialog.open();
						String filename = fileDialog.getFileName();
						if (filename != null) {
							File file = new File(fileDialog.getFilterPath(), fileDialog.getFileName());
							if (file.isFile()) {
								try {
									listener.createImage("/data/image/node_icon/", file.getName(), UtilFile.readFile(file));
									selectorImageNode.setImagePaths(listener.queryListImagePath("/data/image/node_icon/", new String[] { "png" }));
								} catch (EmpException ex) {
									DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.MODELING), ex);
								}
							}
						}
					}
				});
			}

			LabelText label_ne_info_code = new LabelText(panelData.getContentComposite(), SWT.NONE, true);
			label_ne_info_code.setText("Ne_infos");
			label_ne_info_code.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));

			tree_infos = new PanelTree4Check(panelData.getContentComposite(), SWT.NONE, new DataTree());
			tree_infos.getTree().setToolTipText("NE_INFO의 코드 값 입니다.");
			tree_infos.getTree().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
			tree_infos.addCheckStateListener(new ICheckStateListener() {
				@Override
				public void checkStateChanged(CheckStateChangedEvent arg0) {
					if (ne_def != null) {
						Object[] checked_elements = tree_infos.getCheckedElements();
						List<Integer> checked_infos = new ArrayList<Integer>();
						for (int i = 0; i < checked_elements.length; i++) {
							if (checked_elements[i] instanceof Integer) {
								checked_infos.add((Integer) checked_elements[i]);
							}
						}
						int[] ne_info_codes = new int[checked_infos.size()];
						for (int i = 0; i < ne_info_codes.length; i++) {
							ne_info_codes[i] = checked_infos.get(i);
						}
						ne_def.setNe_info_codes(ne_info_codes);
						listener.updated(ne_def);
					}
				}
			});
		}
	}

	public void displayDetail(EMP_MODEL emp_model, EMP_MODEL_NE ne_def) {
		this.emp_model = emp_model;
		this.ne_def = ne_def;

		if (ne_def == null) {
			setTitle(UtilString.format("{}", EMP_MODEL.NE));
			image_severity.setImage(null);
			label_error.setText("");
			text_manufacturer.setText("");
			text_oui.setText("");
			text_product_class.setText("");
			text_ne_oid.setText("");
			selectorImageNode.setValue(EMP_MODEL_NE.NE_ICON);
			tree_infos.refresh();
			tree_infos.expandToLevel(1);
			tree_infos.setCheckedElements(new Integer[0]);
		} else {
			setTitle(UtilString.format("{} - {}", EMP_MODEL.NE, ne_def.getProduct_class()));
			image_severity.setImage(UtilResource4Orange.getImage(ne_def.getSeverity(), false));
			label_error.setText(ne_def.getError());
			if (!text_manufacturer.getText().trim().equals(ne_def.getManufacturer())) {
				text_manufacturer.setText(ne_def.getManufacturer());
			}
			if (!text_oui.getText().trim().equals(ne_def.getOui())) {
				text_oui.setText(ne_def.getOui());
			}
			if (!text_product_class.getText().trim().equals(ne_def.getProduct_class())) {
				text_product_class.setText(ne_def.getProduct_class());
			}
			if (!text_ne_oid.getText().trim().equals(ne_def.getNe_oid())) {
				text_ne_oid.setText(ne_def.getNe_oid());
			}
			System.out.println(ne_def.getNe_icon());
			if (!selectorImageNode.getValue().trim().equals(ne_def.getNe_icon())) {
				selectorImageNode.setValue(ne_def.getNe_icon());
			}
			tree_infos.refresh();
			tree_infos.expandToLevel(2);
			int[] ne_info_codes = ne_def.getNe_info_codes();
			Integer[] checked_elements = new Integer[ne_info_codes.length];
			for (int i = 0; i < ne_info_codes.length; i++) {
				checked_elements[i] = ne_info_codes[i];
			}
			tree_infos.setCheckedElements(checked_elements);
		}
	}

	public void displayDetail() {
		if (ne_def == null) {
			setTitle(UtilString.format("{}", EMP_MODEL.NE));
			image_severity.setImage(null);
			label_error.setText("");
		} else {
			setTitle(UtilString.format("{} - {}", EMP_MODEL.NE, ne_def.getProduct_class()));
			image_severity.setImage(UtilResource4Orange.getImage(ne_def.getSeverity(), false));
			label_error.setText(ne_def.getError());
		}
	}

}
