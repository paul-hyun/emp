package com.hellonms.platforms.emp_orange.client_swt.page.emp_model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
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
import com.hellonms.platforms.emp_orange.client_swt.util.resource.UtilResource4Orange;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_ENUM;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_ENUM_FIELD;
import com.hellonms.platforms.emp_util.string.UtilString;

public class Panel4ModelEnumList extends Panel {

	public interface Panel4ModelEnumListListenerIf extends Panel4ModelListenerIf {

		public void selected(EMP_MODEL_ENUM enum_def, EMP_MODEL_ENUM_FIELD enum_field);

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
				return new String[] { EMP_MODEL.ENUM_LIST };
			} else if (element.equals(EMP_MODEL.ENUM_LIST)) {
				if (emp_model == null) {
					return DataTreeAt.EMPTY;
				} else {
					return emp_model.getEnums();
				}
			} else if (element instanceof EMP_MODEL_ENUM) {
				return ((EMP_MODEL_ENUM) element).getEnum_fields();
			} else {
				return DataTreeAt.EMPTY;
			}
		}

		@Override
		public Object getParent(Object element) {
			if (element == this) {
				return null;
			} else if (element.equals(EMP_MODEL.ENUM_LIST)) {
				return this;
			} else if (element instanceof EMP_MODEL_ENUM) {
				return EMP_MODEL.ENUM_LIST;
			} else if (element instanceof EMP_MODEL_ENUM_FIELD) {
				return ((EMP_MODEL_ENUM_FIELD) element).getEnum();
			} else {
				return null;
			}
		}

		@Override
		public boolean hasChildren(Object element) {
			if (element == this) {
				return true;
			} else if (element.equals(EMP_MODEL.ENUM_LIST)) {
				return emp_model != null;
			} else if (element instanceof EMP_MODEL_ENUM) {
				return true;
			} else {
				return false;
			}
		}

		@Override
		public Image getImage(Object element) {
			if (element instanceof EMP_MODEL_ENUM) {
				return UtilResource4Orange.getImage(((EMP_MODEL_ENUM) element).getSeverity(), false);
			} else if (element instanceof EMP_MODEL_ENUM_FIELD) {
				return UtilResource4Orange.getImage(((EMP_MODEL_ENUM_FIELD) element).getSeverity(), false);
			} else {
				return null;
			}
		}

		@Override
		public String getText(Object element) {
			if (element instanceof EMP_MODEL_ENUM) {
				return UtilString.format("{}", ((EMP_MODEL_ENUM) element).getName());
			} else if (element instanceof EMP_MODEL_ENUM_FIELD) {
				return UtilString.format("{} ({})", ((EMP_MODEL_ENUM_FIELD) element).getName(), ((EMP_MODEL_ENUM_FIELD) element).getValue());
			} else {
				return super.getText(element);
			}
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

	private Panel4ModelEnumListListenerIf listener;

	public Panel4ModelEnumList(Composite parent, int style, Panel4ModelEnumListListenerIf listener) {
		super(parent, style, EMP_MODEL.ENUM_LIST);
		this.listener = listener;

		createGUI();
	}

	/**
	 * GUI를 생성합니다.
	 */
	protected void createGUI() {
		getContentComposite().setLayout(new FormLayout());

		panelTree = new PanelTree(getContentComposite(), SWT.BORDER);
		Tree tree = panelTree.getTree();
		FormData fd_panelTree = new FormData();
		fd_panelTree.bottom = new FormAttachment(100, -5);
		fd_panelTree.right = new FormAttachment(100, -80);
		fd_panelTree.top = new FormAttachment(0, 5);
		fd_panelTree.left = new FormAttachment(0, 5);
		tree.setLayoutData(fd_panelTree);

		dataTree = new DataTree();
		panelTree.setDataTree(dataTree);
		panelTree.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection selection = (IStructuredSelection) event.getSelection();
				Object element = selection.isEmpty() ? null : selection.getFirstElement();
				Panel4ModelEnumList.this.selected = element;
				setEnabledButton();

				if (element instanceof EMP_MODEL_ENUM) {
					listener.selected((EMP_MODEL_ENUM) element, null);
				} else if (element instanceof EMP_MODEL_ENUM_FIELD) {
					listener.selected(null, (EMP_MODEL_ENUM_FIELD) element);
				} else {
					listener.selected(null, null);
				}
			}
		});

		ButtonClick[] detailButtons = createDetailButton(getContentComposite());
		for (int i = 0; i < detailButtons.length; i++) {
			FormData fd_button = new FormData();
			fd_button.right = (i == 0) ? new FormAttachment(100, -5) : new FormAttachment(detailButtons[i - 1], 0, SWT.RIGHT);
			fd_button.left = (i == 0) ? new FormAttachment(tree, 5) : new FormAttachment(detailButtons[i - 1], 0, SWT.LEFT);
			fd_button.top = (i == 0) ? new FormAttachment(tree, 0, SWT.TOP) : new FormAttachment(detailButtons[i - 1], 5, SWT.BOTTOM);
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
						EMP_MODEL_ENUM enum_def = emp_model.addEnum();
						listener.refresh();
						panelTree.setSelection(new StructuredSelection(enum_def));
					} else if (selected instanceof EMP_MODEL_ENUM) {
						EMP_MODEL_ENUM enum_def = (EMP_MODEL_ENUM) selected;
						EMP_MODEL_ENUM_FIELD enum_field = enum_def.addEnum_field();
						listener.refresh();
						panelTree.setSelection(new StructuredSelection(enum_field));
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
					if (selected instanceof EMP_MODEL_ENUM) {
						EMP_MODEL_ENUM enum_def = (EMP_MODEL_ENUM) selected;
						enum_def = emp_model.copyEnum(enum_def);
						listener.refresh();
						panelTree.setSelection(new StructuredSelection(enum_def));
					} else if (selected instanceof EMP_MODEL_ENUM_FIELD) {
						EMP_MODEL_ENUM_FIELD enum_field = (EMP_MODEL_ENUM_FIELD) selected;
						enum_field = enum_field.getEnum().copyEnum_field(enum_field);
						listener.refresh();
						panelTree.setSelection(new StructuredSelection(enum_field));
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
					if (selected instanceof EMP_MODEL_ENUM) {
						EMP_MODEL_ENUM enum_def = (EMP_MODEL_ENUM) selected;
						enum_def.moveUp();
						listener.refresh();
						panelTree.setSelection(new StructuredSelection(enum_def));
					} else if (selected instanceof EMP_MODEL_ENUM_FIELD) {
						EMP_MODEL_ENUM_FIELD enum_field = (EMP_MODEL_ENUM_FIELD) selected;
						enum_field.moveUp();
						listener.refresh();
						panelTree.setSelection(new StructuredSelection(enum_field));
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
					if (selected instanceof EMP_MODEL_ENUM) {
						EMP_MODEL_ENUM enum_def = (EMP_MODEL_ENUM) selected;
						enum_def.moveDown();
						listener.refresh();
						panelTree.setSelection(new StructuredSelection(enum_def));
					} else if (selected instanceof EMP_MODEL_ENUM_FIELD) {
						EMP_MODEL_ENUM_FIELD enum_field = (EMP_MODEL_ENUM_FIELD) selected;
						enum_field.moveDown();
						listener.refresh();
						panelTree.setSelection(new StructuredSelection(enum_field));
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
					if (selected instanceof EMP_MODEL_ENUM) {
						EMP_MODEL_ENUM enum_def = (EMP_MODEL_ENUM) selected;
						if (enum_def != null) {
							String error = enum_def.getErrorDelete();
							if (error != null) {
								DialogMessage.openInfo(getShell(), "입력오류", error);
								return;
							}

							enum_def.dispose();
							listener.refresh();
						}
					} else if (selected instanceof EMP_MODEL_ENUM_FIELD) {
						EMP_MODEL_ENUM_FIELD enum_field = (EMP_MODEL_ENUM_FIELD) selected;
						if (enum_field != null) {
							String error = enum_field.getErrorDelete();
							if (error != null) {
								DialogMessage.openInfo(getShell(), "입력오류", error);
								return;
							}

							enum_field.dispose();
							listener.refresh();
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
		} else if (selected instanceof EMP_MODEL_ENUM) {
			buttonCreate.setEnabled(true);
			buttonCopy.setEnabled(true);
			buttonUp.setEnabled(true);
			buttonDown.setEnabled(true);
			buttonDelete.setEnabled(true);
		} else if (selected instanceof EMP_MODEL_ENUM_FIELD) {
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

	public void select(EMP_MODEL_ENUM enum_def) {
		panelTree.setSelection(new StructuredSelection(enum_def));
		this.selected = enum_def;
		setEnabledButton();
	}

	public void select(EMP_MODEL_ENUM_FIELD enum_field) {
		panelTree.setSelection(new StructuredSelection(enum_field));
		this.selected = enum_field;
		setEnabledButton();
	}

}
