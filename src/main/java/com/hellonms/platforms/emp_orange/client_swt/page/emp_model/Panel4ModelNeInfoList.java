package com.hellonms.platforms.emp_orange.client_swt.page.emp_model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
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
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO_FIELD;
import com.hellonms.platforms.emp_orange.share.model.fault.event.SEVERITY;
import com.hellonms.platforms.emp_util.string.UtilString;

public class Panel4ModelNeInfoList extends Panel {

	public interface Panel4ModelNeInfoListListenerIf extends Panel4ModelListenerIf {

		public void selected(EMP_MODEL_NE_INFO info, EMP_MODEL_NE_INFO_FIELD info_field);

		public void deleted();

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
				return new String[] { EMP_MODEL.NE_INFO_LIST };
			} else if (element.equals(EMP_MODEL.NE_INFO_LIST)) {
				if (emp_model == null) {
					return DataTreeAt.EMPTY;
				} else {
					List<Object> ne_info_list = new ArrayList<Object>();
					EMP_MODEL_NE_INFO[] ne_infos = emp_model.getNe_infos();
					for (EMP_MODEL_NE_INFO ne_info : ne_infos) {
						if (ne_info.getName().toUpperCase().contains(search_text.toUpperCase())) {
							ne_info_list.add(ne_info);
						} else {
							EMP_MODEL_NE_INFO_FIELD[] ne_info_fields = ne_info.getNe_info_fields();
							for (EMP_MODEL_NE_INFO_FIELD ne_info_field : ne_info_fields) {
								if (ne_info_field.getName().toUpperCase().contains(search_text.toUpperCase())) {
									ne_info_list.add(ne_info);
									break;
								}
							}
						}
					}
					return ne_info_list.toArray();
				}
			} else if (element instanceof EMP_MODEL_NE_INFO) {
				List<Object> ne_info_field_list = new ArrayList<Object>();
				EMP_MODEL_NE_INFO_FIELD[] ne_info_fields = ((EMP_MODEL_NE_INFO) element).getNe_info_fields();
				for (EMP_MODEL_NE_INFO_FIELD ne_info_field : ne_info_fields) {
					if (ne_info_field.getName().toUpperCase().contains(search_text.toUpperCase())) {
						ne_info_field_list.add(ne_info_field);
					}
				}
				return ne_info_field_list.toArray();
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
			} else if (element instanceof EMP_MODEL_NE_INFO_FIELD) {
				return ((EMP_MODEL_NE_INFO_FIELD) element).getNe_info();
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
			} else if (element instanceof EMP_MODEL_NE_INFO) {
				return 0 < getElements(element).length;
			} else {
				return false;
			}
		}

		@Override
		public Image getImage(Object element) {
			if (element instanceof EMP_MODEL_NE_INFO) {
				return UtilResource4Orange.getImage(((EMP_MODEL_NE_INFO) element).getSeverity(), false);
			} else if (element instanceof EMP_MODEL_NE_INFO_FIELD) {
				SEVERITY seveirty = ((EMP_MODEL_NE_INFO_FIELD) element).getSeverity();
				if (seveirty == SEVERITY.CLEAR && ((EMP_MODEL_NE_INFO_FIELD) element).isStat_enable()) {
					seveirty = SEVERITY.INFO;
				}
				return UtilResource4Orange.getImage(seveirty, ((EMP_MODEL_NE_INFO_FIELD) element).isIndex());
			} else {
				return null;
			}
		}

		@Override
		public String getText(Object element) {
			if (element instanceof EMP_MODEL_NE_INFO) {
				EMP_MODEL_NE_INFO ne_info_def = (EMP_MODEL_NE_INFO) element;
				if (ne_info_def.isMonitoring() || ne_info_def.isFilter_enable() || ne_info_def.isFault_enable() || ne_info_def.isStat_enable()) {
					return UtilString.format("{} [{}{}{}{}]", ne_info_def.getName(), ne_info_def.isMonitoring() ? "M" : "", ne_info_def.isFilter_enable() ? "Y" : "", ne_info_def.isFault_enable() ? "F" : "", ne_info_def.isStat_enable() ? "S" : "");
				} else {
					return UtilString.format("{}", ne_info_def.getName());
				}
			} else if (element instanceof EMP_MODEL_NE_INFO_FIELD) {
				EMP_MODEL_NE_INFO_FIELD ne_info_field_def = (EMP_MODEL_NE_INFO_FIELD) element;
				if (ne_info_field_def.isVirtual_enable() || ne_info_field_def.isRead() || ne_info_field_def.isUpdate() || ne_info_field_def.isStat_label() || ne_info_field_def.isThr_enable()) {
					return UtilString.format("{} [{}{}{}{}{}]", ne_info_field_def.getName(), ne_info_field_def.isVirtual_enable() ? "V" : "", ne_info_field_def.isRead() ? "R" : "", ne_info_field_def.isUpdate() ? "U" : "", ne_info_field_def.isStat_label() ? "L" : "", ne_info_field_def.isThr_enable() ? "T" : "");
				} else {
					return UtilString.format("{}", ne_info_field_def.getName());
				}
			} else {
				return super.getText(element);
			}
		}

		public void setSearch_text(String search_text) {
			this.search_text = search_text;
			refresh();
		}

	}

	private PanelTree panelTree;

	private DataTree dataTree;

	protected ButtonClick buttonCreate;
	protected ButtonClick buttonCopy;
	protected ButtonClick buttonUp;
	protected ButtonClick buttonDown;
	protected ButtonClick buttonDelete;

	private EMP_MODEL emp_model;

	private Object selected;

	private Panel4ModelNeInfoListListenerIf listener;

	public Panel4ModelNeInfoList(Composite parent, int style, Panel4ModelNeInfoListListenerIf listener) {
		super(parent, style, EMP_MODEL.NE_INFO_LIST);
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
				panelTree.expandToLevel(2);
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
				Panel4ModelNeInfoList.this.selected = element;
				setEnabledButton();

				if (element instanceof EMP_MODEL_NE_INFO) {
					listener.selected((EMP_MODEL_NE_INFO) element, null);
				} else if (element instanceof EMP_MODEL_NE_INFO_FIELD) {
					listener.selected(null, (EMP_MODEL_NE_INFO_FIELD) element);
				} else {
					listener.selected(null, null);
				}
			}
		});

		ButtonClick[] detailButtons = createDetailButton(getContentComposite());
		for (int i = 0; i < detailButtons.length; i++) {
			FormData fd_button = new FormData();
			fd_button.right = (i == 0) ? new FormAttachment(100, -5) : new FormAttachment(detailButtons[i - 1], 0, SWT.RIGHT);
			fd_button.left = (i == 0) ? new FormAttachment(text_search, 5) : new FormAttachment(detailButtons[i - 1], 0, SWT.LEFT);
			fd_button.top = (i == 0) ? new FormAttachment(text_search, 0, SWT.TOP) : new FormAttachment(detailButtons[i - 1], 5, SWT.BOTTOM);
			detailButtons[i].setLayoutData(fd_button);
		}
	}

	/**
	 * 상세뷰어의 버튼을 생성합니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 * @return 버튼 배열
	 */
	protected ButtonClick[] createDetailButton(Composite parent) {
		List<ButtonClick> buttonList = new ArrayList<ButtonClick>();

		{
			buttonCreate = new ButtonClick(parent, SWT.NONE);
			buttonCreate.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					if (selected instanceof String) {
						EMP_MODEL_NE_INFO info = emp_model.addNe_info();
						listener.refresh();
						panelTree.setSelection(new StructuredSelection(info));
					} else if (selected instanceof EMP_MODEL_NE_INFO) {
						EMP_MODEL_NE_INFO info = (EMP_MODEL_NE_INFO) selected;
						EMP_MODEL_NE_INFO_FIELD info_field = info.addNe_info_field();
						listener.refresh();
						panelTree.setSelection(new StructuredSelection(info_field));
					}
				}
			});
			buttonCreate.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.CREATE));
			buttonCreate.setEnabled(false);
			buttonList.add(buttonCreate);
		}

		{
			buttonCopy = new ButtonClick(parent, SWT.NONE);
			buttonCopy.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					if (selected instanceof EMP_MODEL_NE_INFO) {
						EMP_MODEL_NE_INFO info = (EMP_MODEL_NE_INFO) selected;
						info = emp_model.copyNe_info(info);
						listener.refresh();
						panelTree.setSelection(new StructuredSelection(info));
					} else if (selected instanceof EMP_MODEL_NE_INFO_FIELD) {
						EMP_MODEL_NE_INFO_FIELD info_field = (EMP_MODEL_NE_INFO_FIELD) selected;
						info_field = info_field.getNe_info().copyNe_info_field(info_field);
						listener.refresh();
						panelTree.setSelection(new StructuredSelection(info_field));
					}
				}
			});
			buttonCopy.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.COPY));
			buttonCopy.setEnabled(false);
			buttonList.add(buttonCopy);
		}

		{
			buttonUp = new ButtonClick(parent, SWT.NONE);
			buttonUp.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					if (selected instanceof EMP_MODEL_NE_INFO) {
						EMP_MODEL_NE_INFO info = (EMP_MODEL_NE_INFO) selected;
						info.moveUp();
						listener.refresh();
						panelTree.setSelection(new StructuredSelection(info));
					} else if (selected instanceof EMP_MODEL_NE_INFO_FIELD) {
						EMP_MODEL_NE_INFO_FIELD info_field = (EMP_MODEL_NE_INFO_FIELD) selected;
						info_field.moveUp();
						listener.refresh();
						panelTree.setSelection(new StructuredSelection(info_field));
					}
				}
			});
			buttonUp.setText(UtilLanguage.getMessage("Up"));
			buttonUp.setEnabled(false);
			buttonList.add(buttonUp);
		}

		{
			buttonDown = new ButtonClick(parent, SWT.NONE);
			buttonDown.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					if (selected instanceof EMP_MODEL_NE_INFO) {
						EMP_MODEL_NE_INFO info = (EMP_MODEL_NE_INFO) selected;
						info.moveDown();
						listener.refresh();
						panelTree.setSelection(new StructuredSelection(info));
					} else if (selected instanceof EMP_MODEL_NE_INFO_FIELD) {
						EMP_MODEL_NE_INFO_FIELD info_field = (EMP_MODEL_NE_INFO_FIELD) selected;
						info_field.moveDown();
						listener.refresh();
						panelTree.setSelection(new StructuredSelection(info_field));
					}
				}
			});
			buttonDown.setText(UtilLanguage.getMessage("Down"));
			buttonDown.setEnabled(false);
			buttonList.add(buttonDown);
		}

		{
			buttonDelete = new ButtonClick(parent, SWT.NONE);
			buttonDelete.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					if (selected instanceof EMP_MODEL_NE_INFO) {
						EMP_MODEL_NE_INFO info = (EMP_MODEL_NE_INFO) selected;
						if (info != null) {
							String error = info.getErrorDelete();
							if (error != null) {
								DialogMessage.openInfo(getShell(), "입력오류", error);
								return;
							}

							info.dispose();
							listener.deleted();
						}
					} else if (selected instanceof EMP_MODEL_NE_INFO_FIELD) {
						EMP_MODEL_NE_INFO_FIELD info_field = (EMP_MODEL_NE_INFO_FIELD) selected;
						if (info_field != null) {
							info_field.dispose();
							listener.deleted();
						}
					}

				}
			});
			buttonDelete.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.DELETE));
			buttonDelete.setEnabled(false);
			buttonList.add(buttonDelete);
		}

		return buttonList.toArray(new ButtonClick[0]);
	}

	private void setEnabledButton() {
		if (selected instanceof String) {
			buttonCreate.setEnabled(true);
			buttonCopy.setEnabled(false);
			buttonUp.setEnabled(false);
			buttonDown.setEnabled(false);
			buttonDelete.setEnabled(false);
		} else if (selected instanceof EMP_MODEL_NE_INFO) {
			buttonCreate.setEnabled(true);
			buttonCopy.setEnabled(true);
			buttonUp.setEnabled(true);
			buttonDown.setEnabled(true);
			buttonDelete.setEnabled(true);
		} else if (selected instanceof EMP_MODEL_NE_INFO_FIELD) {
			buttonCreate.setEnabled(false);
			buttonCopy.setEnabled(true);
			buttonUp.setEnabled(true);
			buttonDown.setEnabled(true);
			buttonDelete.setEnabled(true);
		} else {
			buttonCreate.setEnabled(false);
			buttonCopy.setEnabled(false);
			buttonUp.setEnabled(false);
			buttonDown.setEnabled(false);
			buttonDelete.setEnabled(false);
		}
	}

	public void displayTree(EMP_MODEL emp_model) {
		this.emp_model = emp_model;
		dataTree.refresh();
		panelTree.expandToLevel(2);

		this.selected = null;
		setEnabledButton();
	}

	public void refresh() {
		dataTree.refresh();
		panelTree.expandToLevel(2);
	}

	public void select(EMP_MODEL_NE_INFO info) {
		panelTree.setSelection(new StructuredSelection(info));
		this.selected = info;
		setEnabledButton();
	}

	public void select(EMP_MODEL_NE_INFO_FIELD info_field) {
		panelTree.setSelection(new StructuredSelection(info_field));
		this.selected = info_field;
		setEnabledButton();
	}

}
