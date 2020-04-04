package com.hellonms.platforms.emp_orange.client_swt.page.emp_model.snmp;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;

import com.hellonms.platforms.emp_core.share.language.UtilLanguage;
import com.hellonms.platforms.emp_onion.client_swt.data.tree.DataTreeAt;
import com.hellonms.platforms.emp_onion.client_swt.widget.button.ButtonClick;
import com.hellonms.platforms.emp_onion.client_swt.widget.dialog.DialogMessage;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.Panel;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelTree;
import com.hellonms.platforms.emp_onion.client_swt.widget.text.TextInput4String;
import com.hellonms.platforms.emp_orange.client_swt.util.resource.UtilResource4Orange;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_ENUM;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO_FIELD;
import com.hellonms.platforms.emp_orange.share.model.fault.event.SEVERITY;
import com.hellonms.platforms.emp_orange.share.parser.snmp.MIB_NODE;
import com.hellonms.platforms.emp_orange.share.parser.snmp.MIB_REPOSITORY;
import com.hellonms.platforms.emp_util.string.UtilString;

public class Panel4SnmpMoidList extends Panel {

	public interface Panel4SnmpMoidListListenerIf extends Panel4SnmpListenerIf {

		public void selected(MIB_NODE node);

		public String addNe_info(MIB_NODE node);

		public String addEnum(MIB_NODE node);

		public String addAll(MIB_NODE node);

	}

	private class DataTree extends DataTreeAt {

		private String search_text = "";

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
				return new Object[] { MIB_REPOSITORY.getInstance().getRoot() };
			} else if (element instanceof MIB_NODE) {
				List<Object> mib_node_list = new ArrayList<Object>();
				MIB_NODE[] mib_nodes = MIB_REPOSITORY.getInstance().getChilds((MIB_NODE) element);
				for (MIB_NODE mib_node : mib_nodes) {
					if (contains(mib_node)) {
						mib_node_list.add(mib_node);
					}
				}
				return mib_node_list.toArray();
			} else {
				return DataTreeAt.EMPTY;
			}
		}

		@Override
		public Object getParent(Object element) {
			return null;
		}

		@Override
		public boolean hasChildren(Object element) {
			if (element == this) {
				return true;
			} else if (element instanceof MIB_NODE) {
				return 0 < getElements(element).length;
			} else {
				return false;
			}
		}

		@Override
		public Image getImage(Object element) {
			if (element instanceof MIB_NODE) {
				MIB_NODE node = (MIB_NODE) element;
				if (MIB_REPOSITORY.getInstance().isModel(node, EMP_MODEL_NE_INFO.class)) {
					return UtilResource4Orange.getImage(SEVERITY.CLEAR, false);
				} else if (MIB_REPOSITORY.getInstance().isModel(node, EMP_MODEL_NE_INFO_FIELD.class)) {
					return UtilResource4Orange.getImage(SEVERITY.INFO, node.isIndex());
				} else if (MIB_REPOSITORY.getInstance().isModel(node, EMP_MODEL_ENUM.class)) {
					return UtilResource4Orange.getImage(SEVERITY.MINOR, false);
				}
				return null;
			} else {
				return null;
			}
		}

		@Override
		public String getText(Object element) {
			if (element instanceof MIB_NODE) {
				return UtilString.format("{} ({} {}{})", ((MIB_NODE) element).getName(), ((MIB_NODE) element).getThis_no(), ((MIB_NODE) element).isRead() ? "r" : "", ((MIB_NODE) element).isWrite() ? "w" : "");
			} else {
				return super.getText(element);
			}
		}

		public void setSearch_text(String search_text) {
			this.search_text = search_text;
			refresh();
		}

		protected boolean contains(MIB_NODE mib_node) {
			if (mib_node.getName().toUpperCase().contains(search_text.toUpperCase())) {
				return true;
			} else {
				MIB_NODE[] mib_nodes = MIB_REPOSITORY.getInstance().getChilds(mib_node);
				for (MIB_NODE mmm : mib_nodes) {
					if (contains(mmm)) {
						return true;
					}
				}
			}
			return false;
		}

	}

	private PanelTree panelTree;

	private DataTree dataTree;

	protected ButtonClick buttonNe_info;
	protected ButtonClick buttonEnum;
	protected ButtonClick buttonImport;

	private Panel4SnmpMoidListListenerIf listener;

	private MIB_NODE node;

	public Panel4SnmpMoidList(Composite parent, int style, Panel4SnmpMoidListListenerIf listener) {
		super(parent, style, "MIB Tree");
		this.listener = listener;

		createGUI();
	}

	/**
	 * GUI를 생성합니다.
	 */
	protected void createGUI() {
		getContentComposite().setLayout(new FormLayout());

		TextInput4String text_search = new TextInput4String(getContentComposite(), SWT.BORDER, "검색할 단어를 입력하세요.");
		FormData fd_text_search = new FormData();
		fd_text_search.top = new FormAttachment(0, 5);
		fd_text_search.left = new FormAttachment(0, 5);
		fd_text_search.right = new FormAttachment(100, -80);
		text_search.setLayoutData(fd_text_search);
		text_search.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent arg0) {
				dataTree.setSearch_text(text_search.getText().trim());
				panelTree.expandAll();
			}
		});

		panelTree = new PanelTree(getContentComposite(), SWT.BORDER);
		Tree tree = panelTree.getTree();
		FormData fd_panelTree = new FormData();
		fd_panelTree.bottom = new FormAttachment(100, -5);
		fd_panelTree.right = new FormAttachment(text_search, 0, SWT.RIGHT);
		fd_panelTree.top = new FormAttachment(text_search, 5, SWT.BOTTOM);
		fd_panelTree.left = new FormAttachment(text_search, 0, SWT.LEFT);
		tree.setLayoutData(fd_panelTree);

		dataTree = new DataTree();
		panelTree.setDataTree(dataTree);
		panelTree.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection selection = (IStructuredSelection) event.getSelection();
				Object element = selection.isEmpty() ? null : selection.getFirstElement();

				if (element instanceof MIB_NODE) {
					node = (MIB_NODE) element;
					buttonNe_info.setEnabled(MIB_REPOSITORY.getInstance().isModel(node, EMP_MODEL_NE_INFO.class));
					buttonEnum.setEnabled(MIB_REPOSITORY.getInstance().isModel(node, EMP_MODEL_ENUM.class));
					buttonImport.setEnabled(true);
					listener.selected((MIB_NODE) element);
				} else {
					node = null;
					buttonNe_info.setEnabled(false);
					buttonEnum.setEnabled(false);
					buttonImport.setEnabled(false);
					listener.selected(null);
				}
			}
		});
		panelTree.expandAll();

		ButtonClick[] detailButtons = createDetailButton(getContentComposite());
		for (int i = 0; i < detailButtons.length; i++) {
			FormData fd_button = new FormData();
			fd_button.right = (i == 0) ? new FormAttachment(100, -5) : new FormAttachment(detailButtons[i - 1], 0, SWT.RIGHT);
			fd_button.left = (i == 0) ? new FormAttachment(text_search, 5) : new FormAttachment(detailButtons[i - 1], 0, SWT.LEFT);
			fd_button.top = (i == 0) ? new FormAttachment(text_search, 0, SWT.TOP) : new FormAttachment(detailButtons[i - 1], 5, SWT.BOTTOM);
			detailButtons[i].setLayoutData(fd_button);
		}
	}

	protected ButtonClick[] createDetailButton(Composite parent) {
		List<ButtonClick> buttonList = new ArrayList<ButtonClick>();

		{
			buttonNe_info = new ButtonClick(parent, SWT.NONE);
			buttonNe_info.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					if (node != null) {
						String error = listener.addNe_info(node);
						if (error != null) {
							DialogMessage.openInfo(getShell(), "입력오류", error);
						}
					}
				}
			});
			buttonNe_info.setText(UtilLanguage.getMessage(EMP_MODEL.NE_INFO));
			buttonNe_info.setEnabled(false);
			buttonList.add(buttonNe_info);
		}

		{
			buttonEnum = new ButtonClick(parent, SWT.NONE);
			buttonEnum.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					if (node != null) {
						String error = listener.addEnum(node);
						if (error != null) {
							DialogMessage.openInfo(getShell(), "입력오류", error);
						}
					}
				}
			});
			buttonEnum.setText(UtilLanguage.getMessage(EMP_MODEL.ENUM));
			buttonEnum.setEnabled(false);
			buttonList.add(buttonEnum);
		}

		{
			buttonImport = new ButtonClick(parent, SWT.NONE);
			buttonImport.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					if (node != null) {
						String error = listener.addAll(node);
						if (error != null) {
							DialogMessage.openInfo(getShell(), "입력오류", error);
						}
					}
				}
			});
			buttonImport.setText(UtilLanguage.getMessage("AddAll"));
			buttonImport.setEnabled(false);
			buttonList.add(buttonImport);
		}

		return buttonList.toArray(new ButtonClick[0]);
	}

	public void refresh() {
		dataTree.refresh();
		panelTree.expandAll();
		buttonNe_info.setEnabled(false);
		buttonEnum.setEnabled(false);
	}

}
