package com.hellonms.platforms.emp_orange.client_swt.page.network.network_view.ne;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.hellonms.platforms.emp_core.share.language.UtilLanguage;
import com.hellonms.platforms.emp_onion.client_swt.data.tree.DataTreeAt;
import com.hellonms.platforms.emp_onion.client_swt.widget.button.ButtonImage;
import com.hellonms.platforms.emp_onion.client_swt.widget.button.ButtonToggle;
import com.hellonms.platforms.emp_onion.client_swt.widget.dialog.DialogMessage;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.Panel;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelPopup;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelTree4Check;
import com.hellonms.platforms.emp_onion.share.message.MESSAGE_CODE_ONION;
import com.hellonms.platforms.emp_onion.theme.ThemeBuilder4Onion.COLOR_ONION;
import com.hellonms.platforms.emp_onion.theme.ThemeFactory;
import com.hellonms.platforms.emp_orange.client_swt.model_display.ModelDisplay4Ne;
import com.hellonms.platforms.emp_orange.client_swt.model_display.ModelDisplay4NeInfo;
import com.hellonms.platforms.emp_orange.client_swt.model_display.ModelDisplay4NeStatistics;
import com.hellonms.platforms.emp_orange.client_swt.page.network.menu.MenuPopup4Network;
import com.hellonms.platforms.emp_orange.client_swt.page.network.network_tree.ModelClient4NetworkTree.NODE;
import com.hellonms.platforms.emp_orange.client_swt.page.network.network_view.Panel4NetworkViewAt;
import com.hellonms.platforms.emp_orange.client_swt.widget.page.PageNode.ToolbarItemIf;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO;
import com.hellonms.platforms.emp_orange.share.model.network.ne.Model4Ne;
import com.hellonms.platforms.emp_orange.theme.ThemeBuilder4Orange.IMAGE_ORANGE;

/**
 * <p>
 * Panel4NetworkViewNe
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class Panel4NetworkViewNe extends Panel4NetworkViewAt {

	public interface Panel4NetworkViewNeListenerIf extends Panel4NetworkViewListenerIf {

		public void queryNetworkViewNe(int ne_id, boolean progressBar);

	}

	public class ToolbarItem4Item implements ToolbarItemIf {

		protected ButtonImage buttonImageItem;

		@Override
		public String getId() {
			return Panel4NetworkViewNe.class.getSimpleName() + "ITEM";
		}

		@Override
		public Control initControl(Composite parent) {
			buttonImageItem = new ButtonImage(parent, SWT.NONE, ThemeFactory.getImage(IMAGE_ORANGE.NETWORK_BUTTON_ITEM_NORMAL), ThemeFactory.getImage(IMAGE_ORANGE.NETWORK_BUTTON_ITEM_OVER), ThemeFactory.getImage(IMAGE_ORANGE.NETWORK_BUTTON_ITEM_DOWN), ThemeFactory.getImage(IMAGE_ORANGE.NETWORK_BUTTON_ITEM_DISABLED));
			buttonImageItem.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					Panel4NetworkViewNe.this.openItem();
				}
			});
			buttonImageItem.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.ITEM));
			buttonImageItem.setToolTipText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.ITEM));
			return buttonImageItem;
		}

		@Override
		public Control getControl() {
			return buttonImageItem;
		}

		@Override
		public void setSelection(boolean selection) {
		}

		@Override
		public void setEnabled(boolean enabled) {
			if (buttonImageItem != null) {
				buttonImageItem.setEnabled(enabled);
			}
		}

	}

	private class PanelPopup4Item extends PanelPopup {

		private EMP_MODEL_NE ne_def;
		private String[] order;

		private PanelTree4Check panelTree;
		private DataTree4Item dataTree;

		public PanelPopup4Item(Shell parent, EMP_MODEL_NE ne_def, String[] order, Rectangle rect) {
			super(parent);
			this.ne_def = ne_def;
			this.order = order;
			createGUI(rect);
		}

		private void createGUI(Rectangle rect) {
			setBackground(ThemeFactory.getColor(COLOR_ONION.PANEL_BG));
			setBounds(rect);

			addPaintListener(new PaintListener() {
				@Override
				public void paintControl(PaintEvent e) {
					e.gc.setForeground(ThemeFactory.getColor(COLOR_ONION.WORKBENCH_BG));
					e.gc.drawRectangle(0, 0, getSize().x - 1, getSize().y - 1);
				}
			});

			setLayout(new GridLayout(2, false));

			panelTree = new PanelTree4Check(this, SWT.BORDER);
			dataTree = new DataTree4Item(ne_def);
			panelTree.setDataTree(dataTree);
			panelTree.setCheckedElements(dataTree.getCheckedElements(order));
			dataTree.refresh();
			panelTree.expandAll();
			panelTree.getTree().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));

			Button buttonOk = new Button(this, SWT.NONE);
			buttonOk.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					if (DialogMessage.openConfirm(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.UPDATE_NE_INFO_DISPLAY), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.UPDATE_CONFIRM_NE_INFO_DISPLAY))) {
						Object[] checkedElements = panelTree.getCheckedElements();
						String[] order = dataTree.getCheckedOrder(checkedElements, PanelPopup4Item.this.order);
						PanelPopup4Item.this.dispose();
						listener.updateOrderNetworkViewNe(ne_def, order);
					}
				}
			});
			GridData gd_buttonOk = new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1);
			gd_buttonOk.widthHint = 60;
			buttonOk.setLayoutData(gd_buttonOk);
			buttonOk.setText(UtilLanguage.getMessage(MESSAGE_CODE_ONION.OK));

			Button buttonCancel = new Button(this, SWT.NONE);
			buttonCancel.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					PanelPopup4Item.this.dispose();
				}
			});
			GridData gd_buttonCancel = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
			gd_buttonCancel.widthHint = 60;
			buttonCancel.setLayoutData(gd_buttonCancel);
			buttonCancel.setText(UtilLanguage.getMessage(MESSAGE_CODE_ONION.CANCEL));
		}
	}

	private class DataTree4Item extends DataTreeAt {

		private String[] CATEGORY = { "NE_INFO", "NE_STATISTICS" };

		private Set<Long> ne_infos = new LinkedHashSet<Long>();

		private Set<Long> ne_statisticss = new LinkedHashSet<Long>();

		public DataTree4Item(EMP_MODEL_NE ne_def) {
			if (ne_def != null) {
				for (EMP_MODEL_NE_INFO ne_info_def : EMP_MODEL.current().getNe_infos_by_ne(ne_def.getCode())) {
					if (ne_info_def.isMonitoring()) {
						long vvv = 0x0000000100000000L | (0xFFFFFFFFFFFFFFFFL & ne_info_def.getCode());
						ne_infos.add(vvv);
					}
				}

				for (EMP_MODEL_NE_INFO ne_info_def : EMP_MODEL.current().getNe_infos_by_ne(ne_def.getCode())) {
					if (ne_info_def.isStat_enable()) {
						long vvv = 0x0000000200000000L | (0xFFFFFFFFFFFFFFFFL & ne_info_def.getCode());
						ne_statisticss.add(vvv);
					}
				}
			}
		}

		public Object[] getCheckedElements(String[] order) {
			Set<Long> checkeds = new LinkedHashSet<Long>();
			for (String code : order) {
				if (ModelDisplay4Ne.isNeInfo(code)) {
					long vvv = 0x0000000100000000L | (0xFFFFFFFFFFFFFFFFL & ModelDisplay4Ne.toNeInfo(code));
					checkeds.add(vvv);
				} else if (ModelDisplay4Ne.isNe_statistics(code)) {
					long vvv = 0x0000000200000000L | (0xFFFFFFFFFFFFFFFFL & ModelDisplay4Ne.toNe_statistics(code));
					checkeds.add(vvv);
				}
			}
			return checkeds.toArray();
		}

		public String[] getCheckedOrder(Object[] checkedElements, String[] order_old) {
			List<String> order_new = new ArrayList<String>();
			Set<Long> checkeds = new LinkedHashSet<Long>();
			for (Object element : checkedElements) {
				if (element instanceof Long) {
					checkeds.add((Long) element);
				}
			}
			for (String code : order_old) {
				long vvv = 0L;
				if (ModelDisplay4Ne.isNeInfo(code)) {
					vvv = 0x0000000100000000L | (0xFFFFFFFFFFFFFFFFL & ModelDisplay4Ne.toNeInfo(code));
				} else if (ModelDisplay4Ne.isNe_statistics(code)) {
					vvv = 0x0000000200000000L | (0xFFFFFFFFFFFFFFFFL & ModelDisplay4Ne.toNe_statistics(code));
				}
				if (checkeds.remove(vvv)) {
					order_new.add(code);
				}
			}
			for (Long vvv : checkeds) {
				long category = (0xFFFFFFFF00000000L & vvv);
				int ne_info_code = (int) (0x00000000FFFFFFFFL & vvv);
				if (category == 0x0000000100000000L) {
					order_new.add(ModelDisplay4Ne.toNeInfo(ne_info_code));
				} else if (category == 0x0000000200000000L) {
					order_new.add(ModelDisplay4Ne.toNe_statistics(ne_info_code));
				}
			}

			return order_new.toArray(new String[0]);
		}

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
				return CATEGORY;
			} else if (element.equals(CATEGORY[0])) {
				return ne_infos.toArray();
			} else if (element.equals(CATEGORY[1])) {
				return ne_statisticss.toArray();
			} else {
				return new Object[0];
			}
		}

		@Override
		public Object getParent(Object element) {
			if (element == this) {
				return null;
			} else if (element instanceof String) {
				return this;
			} else if (element instanceof Long) {
				long value = (Long) element;
				long category = (0xFFFFFFFF00000000L & value);
				if (category == 0x0000000100000000L) {
					return CATEGORY[0];
				} else if (category == 0x0000000200000000L) {
					return CATEGORY[1];
				} else {
					return null;
				}
			} else {
				return null;
			}
		}

		@Override
		public boolean hasChildren(Object element) {
			if (element == this) {
				return true;
			} else if (element instanceof String) {
				return true;
			} else {
				return false;
			}
		}

		@Override
		public String getText(Object element) {
			if (element == this) {
				return "";
			} else if (element instanceof String) {
				return (String) element;
			} else if (element instanceof Long) {
				long value = (Long) element;
				EMP_MODEL_NE_INFO ne_info_def = EMP_MODEL.current().getNe_info((int) (0x00000000FFFFFFFFL & value));
				return ne_info_def == null ? "" : ne_info_def.getName();
			} else {
				return "";
			}
		}

	}

	public class ToolbarItem4Edit implements ToolbarItemIf {

		protected ButtonToggle buttonToggleEdit;

		@Override
		public String getId() {
			return Panel4NetworkViewNe.class.getSimpleName() + "EDIT";
		}

		@Override
		public Control initControl(Composite parent) {
			buttonToggleEdit = new ButtonToggle(parent, SWT.NONE, ThemeFactory.getImage(IMAGE_ORANGE.NETWORK_BUTTON_EDIT_NORMAL), ThemeFactory.getImage(IMAGE_ORANGE.NETWORK_BUTTON_EDIT_OVER), ThemeFactory.getImage(IMAGE_ORANGE.NETWORK_BUTTON_EDIT_DOWN), ThemeFactory.getImage(IMAGE_ORANGE.NETWORK_BUTTON_EDIT_DISABLED));
			buttonToggleEdit.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					Panel4NetworkViewNe.this.setEdit_mode(buttonToggleEdit.isSelection());
				}
			});
			buttonToggleEdit.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.EDIT));
			buttonToggleEdit.setToolTipText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.EDIT));
			return buttonToggleEdit;
		}

		@Override
		public Control getControl() {
			return buttonToggleEdit;
		}

		@Override
		public void setSelection(boolean selection) {
			if (buttonToggleEdit != null) {
				buttonToggleEdit.setSelection(selection);
			}
		}

		@Override
		public void setEnabled(boolean enabled) {
			if (buttonToggleEdit != null) {
				buttonToggleEdit.setEnabled(enabled);
			}
		}

	}

	public class ToolbarItem4Save implements ToolbarItemIf {

		protected ButtonImage buttonImageSave;

		@Override
		public String getId() {
			return Panel4NetworkViewNe.class.getSimpleName() + "SAVE";
		}

		@Override
		public Control initControl(Composite parent) {
			buttonImageSave = new ButtonImage(parent, SWT.NONE, ThemeFactory.getImage(IMAGE_ORANGE.NETWORK_BUTTON_SAVE_NORMAL), ThemeFactory.getImage(IMAGE_ORANGE.NETWORK_BUTTON_SAVE_OVER), ThemeFactory.getImage(IMAGE_ORANGE.NETWORK_BUTTON_SAVE_DOWN), ThemeFactory.getImage(IMAGE_ORANGE.NETWORK_BUTTON_SAVE_DISABLED));
			buttonImageSave.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					Panel4NetworkViewNe.this.setEdit_mode(false);
				}
			});
			buttonImageSave.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.SAVE));
			buttonImageSave.setToolTipText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.SAVE));
			return buttonImageSave;
		}

		@Override
		public Control getControl() {
			return buttonImageSave;
		}

		@Override
		public void setSelection(boolean selection) {
		}

		@Override
		public void setEnabled(boolean enabled) {
			if (buttonImageSave != null) {
				buttonImageSave.setEnabled(enabled);
			}
		}

	}

	public class PanelMouseListener implements MouseListener, MouseMoveListener {

		private Panel panel;

		private boolean drag_start;

		private Cursor cursor;

		public PanelMouseListener(Panel panel) {
			this.panel = panel;
		}

		@Override
		public void mouseDoubleClick(MouseEvent event) {
		}

		@Override
		public void mouseDown(MouseEvent event) {
			if (edit_mode && event.button == 1) {
				this.drag_start = true;
			}
		}

		@Override
		public void mouseUp(MouseEvent event) {
			if (this.drag_start && this.cursor != null) {
				Point point_display = panel.toDisplay(event.x, event.y);
				Point point_parent = panel.getParent().toControl(point_display);
				Panel drop_target = null;
				for (Control children : panel.getParent().getChildren()) {
					if (children instanceof Panel && children.getBounds().contains(point_parent)) {
						drop_target = (Panel) children;
						break;
					}
				}
				if (drop_target != null) {
					layout_panel(drop_target, panel, point_display);
				}
			}

			this.drag_start = false;
			if (this.cursor != null) {
				panel.setCursor(null);
				this.cursor.dispose();
				this.cursor = null;
			}
		}

		@Override
		public void mouseMove(MouseEvent event) {
			if (this.drag_start) {
				if (this.cursor == null) {
					Display display = panel.getDisplay();

					Point compositeSize = panel.getSize();
					Image sourceImage = new Image(display, compositeSize.x, compositeSize.y);

					ImageData data = sourceImage.getImageData();
					data.transparentPixel = data.palette.getPixel(new RGB(255, 255, 255));
					Image image = new Image(sourceImage.getDevice(), data);

					GC gc = new GC(image);
					gc.setForeground(display.getSystemColor(SWT.COLOR_GRAY));
					gc.setLineWidth(4);
					gc.drawRectangle(0, 0, compositeSize.x - 1, compositeSize.y - 1);

					this.cursor = new Cursor(display, image.getImageData(), 0, 0);

					image.dispose();
					sourceImage.dispose();
					gc.dispose();
				}
				if (panel.getCursor() != cursor) {
					panel.setCursor(cursor);
				}
			}
		}

	}

	/**
	 * 콘텐츠
	 */
	protected Composite content;

	/**
	 * 노드
	 */
	protected NODE node;

	private Map<String, Panel> panel_map = new LinkedHashMap<String, Panel>();

	List<Panel> panel_layout = new ArrayList<Panel>();

	protected ToolbarItem4Item toolbarItem4Link;

	protected ToolbarItem4Edit toolbarItem4Edit;

	protected ToolbarItem4Save toolbarItem4Save;

	protected boolean edit_mode = false;

	/**
	 * 생성자 입니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 * @param style
	 *            스타일
	 * @param listener
	 *            리스너
	 */
	public Panel4NetworkViewNe(Composite parent, int style, Panel4NetworkViewNeListenerIf listener) {
		super(parent, style, listener);
		layout_panel();
	}

	@Override
	protected Control createContent(Composite parent) {
		content = new Composite(parent, SWT.NONE);
		content.setBackground(ThemeFactory.getColor(COLOR_ONION.PAGE_FG));
		content.setBackgroundMode(SWT.INHERIT_DEFAULT);
		content.setLayout(new FormLayout());

		return content;
	}

	@Override
	protected MenuPopup4Network createMenuPopup4Network(Shell shell) {
		return null;
	}

	@Override
	protected NODE getNode() {
		return node;
	}

	@Override
	protected NODE getSelectedNode() {
		return node;
	}

	@Override
	public void display(boolean progressBar) {
		if (node != null && node.isNe()) {
			((Panel4NetworkViewNeListenerIf) listener).queryNetworkViewNe(node.getId(), progressBar);
		}
	}

	@Override
	public void display(NODE node, boolean progressBar) {
		if (this.node == null || !this.node.isNe() || !node.isNe() || this.node.getId() != node.getId()) {
			setEdit_mode(false);
		}

		if (node.isNe()) {
			this.node = node;

			display(progressBar);
		}
	}

	@Override
	public void monitor() {
		if (!isDisposed() && node != null && node.isNe()) {
			((Panel4NetworkViewNeListenerIf) listener).queryNetworkViewNe(node.getId(), false);
		}
	}

	@Override
	public void display(final NODE node, final Object object) {
		if (Thread.currentThread() == getDisplay().getThread()) {
			displayDetail(node, object);
		} else {
			getDisplay().syncExec(new Runnable() {
				@Override
				public void run() {
					displayDetail(node, object);
				}
			});
		}
	}

	protected void displayDetail(NODE node, Object object) {
		if (object instanceof ModelDisplay4Ne) {
			ModelDisplay4Ne model = (ModelDisplay4Ne) object;
			Map<String, Panel> panel_map = new LinkedHashMap<String, Panel>(this.panel_map);
			List<Panel> panel_layout = new ArrayList<Panel>();

			for (Object entry : model.values()) {
				if (entry instanceof ModelDisplay4NeInfo) {
					ModelDisplay4NeInfo value = (ModelDisplay4NeInfo) entry;
					if (value.getNe_info_def().isDisplay_enable()) {
						String key = ModelDisplay4Ne.toNeInfo(value.getNe_info_code());
						Panel4NetworkViewNeTable panel = (Panel4NetworkViewNeTable) panel_map.remove(key);
						if (panel == null || !panel.getNe_info_def().equals(value.getNe_info_def())) {
							if (panel != null) {
								panel.dispose();
							}
							panel = new Panel4NetworkViewNeTable(content, SWT.NONE, value.getNe_info_def());
							PanelMouseListener mouseListener = new PanelMouseListener(panel);
							panel.addMouseListener(mouseListener);
							panel.addMouseMoveListener(mouseListener);
							this.panel_map.put(key, panel);
						}
						panel_layout.add(panel);
						panel.display(value);
					}
				} else if (entry instanceof ModelDisplay4NeStatistics) {
					ModelDisplay4NeStatistics value = (ModelDisplay4NeStatistics) entry;
					if (value.getNe_info_def().isDisplay_enable()) {
						String key = ModelDisplay4Ne.toNe_statistics(value.getNe_info_code());
						Panel4NetworkViewNeChart panel = (Panel4NetworkViewNeChart) panel_map.remove(key);
						if (panel == null || !panel.getNe_info_def().equals(value.getNe_info_def())) {
							if (panel != null) {
								panel.dispose();
							}
							panel = new Panel4NetworkViewNeChart(content, SWT.NONE, value.getNe_info_def(), (Panel4NetworkViewNeListenerIf) listener);
							PanelMouseListener mouseListener = new PanelMouseListener(panel);
							panel.addMouseListener(mouseListener);
							panel.addMouseMoveListener(mouseListener);
							this.panel_map.put(key, panel);
						}
						panel.display(value);
						panel_layout.add(panel);
					}
				}
			}

			for (Entry<String, Panel> entry : panel_map.entrySet()) {
				this.panel_map.remove(entry.getKey());
				entry.getValue().dispose();
			}

			boolean layout = false;
			if (!layout) {
				layout = (this.panel_layout.size() != panel_layout.size());
			}
			if (!layout) {
				for (int i = 0; i < this.panel_layout.size(); i++) {
					if (this.panel_layout.get(i) != panel_layout.get(i)) {
						layout = true;
						break;
					}
				}
			}
			if (!this.edit_mode && layout) {
				this.panel_layout = panel_layout;
				layout_panel();
			}
		}
	}

	@Override
	public void layout() {
		layout_panel();
		super.layout();
	}

	private void layout_panel(Panel drop_target, Panel drop_source, Point point_display) {
		int index_target = this.panel_layout.indexOf(drop_target);
		// int index_source = this.panel_list.indexOf(drop_source);

		if (0 <= index_target && this.panel_layout.remove(drop_source)) {
			this.panel_layout.add(index_target, drop_source);
		}
		layout_panel();
	}

	private void layout_panel() {
		int height = 205;
		Panel control_left = null;
		Panel control_right = null;
		int index = 0;
		for (Panel panel : this.panel_layout) {
			if (index % 2 == 0) {
				FormData fd = new FormData();
				fd.top = control_left == null ? new FormAttachment(0, 5) : new FormAttachment(control_left, 5, SWT.BOTTOM);
				fd.left = new FormAttachment(0, 5);
				fd.right = new FormAttachment(50, -3);
				fd.bottom = control_left == null ? new FormAttachment(0, height) : new FormAttachment(control_left, height, SWT.BOTTOM);
				panel.setLayoutData(fd);
				control_left = panel;
			} else {
				FormData fd = new FormData();
				fd.top = control_right == null ? new FormAttachment(0, 5) : new FormAttachment(control_right, 5, SWT.BOTTOM);
				fd.left = new FormAttachment(50, 3);
				fd.right = new FormAttachment(100, -5);
				fd.bottom = control_right == null ? new FormAttachment(0, height) : new FormAttachment(control_right, height, SWT.BOTTOM);
				panel.setLayoutData(fd);
				control_right = panel;
			}
			index++;
		}

		Point size = content.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		computeScroll(new Point(-1, size.y));
		content.layout();
	}

	@Override
	protected ToolbarItemIf[] getToolbarItems() {
		if (toolbarItem4Link == null) {
			toolbarItem4Link = new ToolbarItem4Item();
		}
		if (toolbarItem4Edit == null) {
			toolbarItem4Edit = new ToolbarItem4Edit();
		}
		if (toolbarItem4Save == null) {
			toolbarItem4Save = new ToolbarItem4Save();
		}
		return new ToolbarItemIf[] { toolbarItem4Link, toolbarItem4Edit, toolbarItem4Save };
	}

	protected void openItem() {
		String[] order = listener.queryOrderNetworkViewNe(((Model4Ne) node.getValue()).getNe_def());
		Point point = this.toDisplay(this.getSize().x - 300, 0);

		EMP_MODEL_NE ne_def = EMP_MODEL.current().getNe(((Model4Ne) node.getValue()).getNe_code());
		PanelPopup4Item panelPopup4Item = new PanelPopup4Item(getShell(), ne_def, order, new Rectangle(point.x, point.y, 300, 400));
		panelPopup4Item.open();
	}

	protected void setEdit_mode(boolean edit_mode) {
		if (toolbarItem4Edit != null && toolbarItem4Save != null) {
			toolbarItem4Edit.setSelection(edit_mode);
			toolbarItem4Save.setEnabled(edit_mode);
		}
		if (this.edit_mode && !edit_mode) {
			updateNeInfoDisplay();
		}

		this.edit_mode = edit_mode;
	}

	@Override
	public void blink(boolean blink) {
	}

	private void updateNeInfoDisplay() {
		if (DialogMessage.openConfirm(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.UPDATE_NE_INFO_DISPLAY), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.UPDATE_CONFIRM_NE_INFO_DISPLAY))) {
			List<String> order = new ArrayList<String>();
			for (Panel panel : this.panel_layout) {
				if (panel instanceof Panel4NetworkViewNeTable) {
					order.add(ModelDisplay4Ne.toNeInfo(((Panel4NetworkViewNeTable) panel).getNe_info_def().getCode()));
				} else if (panel instanceof Panel4NetworkViewNeChart) {
					order.add(ModelDisplay4Ne.toNe_statistics(((Panel4NetworkViewNeChart) panel).getNe_info_def().getCode()));
				}
			}
			listener.updateOrderNetworkViewNe(((Model4Ne) node.getValue()).getNe_def(), order.toArray(new String[0]));
		}
	}

}
