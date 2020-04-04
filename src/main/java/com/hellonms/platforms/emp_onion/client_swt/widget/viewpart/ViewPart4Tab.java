/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_onion.client_swt.widget.viewpart;

import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabFolder2Adapter;
import org.eclipse.swt.custom.CTabFolderEvent;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import com.hellonms.platforms.emp_core.share.language.UtilLanguage;
import com.hellonms.platforms.emp_onion.client_swt.widget.action.ActionIf;
import com.hellonms.platforms.emp_onion.client_swt.widget.dialog.DialogMessage;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PageIf;
import com.hellonms.platforms.emp_onion.client_swt.widget.workbench.Workbench;
import com.hellonms.platforms.emp_onion.theme.ThemeBuilder4Onion.COLOR_ONION;
import com.hellonms.platforms.emp_onion.theme.ThemeFactory;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;

/**
 * <p>
 * ViewPart for Tab
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 18.
 * @modified 2015. 5. 18.
 * @author cchyun
 *
 */
public class ViewPart4Tab extends ViewPartAt {

	public interface ViewPart4TabListenerIf {

		public void selectMenu(PAGE page);

	}

	private class PageItem {

		private final PAGE page;

		private Control page_widget;

		private ToolBar toolbar;

		public PageItem(PAGE page) {
			this.page = page;
		}

		public void setVisible(boolean visible) {
			if (toolbar != null && !toolbar.isDisposed()) {
				toolbar.setVisible(visible);
			}
			if (page_widget != null && !page_widget.isDisposed()) {
				page_widget.setVisible(visible);
			}
		}

		public void dispose() {
			if (toolbar != null && !toolbar.isDisposed()) {
				toolbar.dispose();
			}
		}

	}

	public class TabFloderMouseListener implements MouseListener, MouseMoveListener {

		private CTabFolder tab_folder;

		private CTabItem selected;

		private Cursor cursor;

		public TabFloderMouseListener(CTabFolder tab_folder) {
			this.tab_folder = tab_folder;
		}

		@Override
		public void mouseDoubleClick(MouseEvent event) {
		}

		@Override
		public void mouseDown(MouseEvent event) {
			if (event.button == 1) {
				CTabItem selected = tab_folder.getSelection();
				if (selected != null) {
					// this.selected = selected;
				}
			}
		}

		@Override
		public void mouseUp(MouseEvent event) {
			tab_folder.setCursor(null);
			if (this.cursor != null) {
				this.cursor.dispose();
				this.cursor = null;
			}

			if ((event.stateMask & SWT.BUTTON_MASK) != 0 && this.selected != null && !this.selected.isDisposed()) {
				Point point_display = tab_folder.toDisplay(event.x, event.y);
				if (tab_folder.getShell().getBounds().contains(point_display)) {
					Point point_parent = tab_folder.getParent().toControl(point_display);

					CTabFolder drop_target = null;
					for (Control child : tab_folder.getParent().getChildren()) {
						if (child instanceof CTabFolder && child.getBounds().contains(point_parent)) {
							drop_target = (CTabFolder) child;
							break;
						}
					}
					if (drop_target != null) {
						layout_tab_item(drop_target, selected, point_display);
					}
				} else {
					detach(selected);
				}
			}

			this.selected = null;
		}

		@Override
		public void mouseMove(MouseEvent event) {
			if ((event.stateMask & SWT.BUTTON_MASK) != 0 && this.selected != null) {
				PageItem pageItem = (PageItem) selected.getData();
				if (this.cursor == null && pageItem.page_widget != null) {
					Display display = tab_folder.getDisplay();

					Point compositeSize = pageItem.page_widget.getSize();
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
				if (tab_folder.getCursor() != cursor) {
					tab_folder.setCursor(cursor);
				}
			} else {
				this.selected = null;
			}
		}

	}

	private ViewPart4TabListenerIf listener;

	private CTabFolder tab_folder;

	private Image pageIcon;

	public ViewPart4Tab(Workbench workbench, int style, Image pageIcon) {
		this(workbench, style, pageIcon, null);
	}

	public ViewPart4Tab(Workbench workbench, int style, Image pageIcon, ViewPart4TabListenerIf listener) {
		super(workbench, style);

		this.pageIcon = pageIcon;
		this.listener = listener;

		createGUI();
	}

	private void createGUI() {
		tab_folder = new CTabFolder(getWorkbench(), SWT.BORDER);
		tab_folder.setSimple(false);
		tab_folder.setSelectionBackground(new Color[] { //
				ThemeFactory.getColor(COLOR_ONION.VIEW_TAB_ACTIVE_BG1), //
				ThemeFactory.getColor(COLOR_ONION.VIEW_TAB_ACTIVE_BG2), //
		}, new int[] { 1 });
		tab_folder.setSelectionForeground(ThemeFactory.getColor(COLOR_ONION.VIEW_TAB_ACTIVE_FG));
		tab_folder.setBackground(ThemeFactory.getColor(COLOR_ONION.VIEW_TAB_INACTIVE_BG2));
		tab_folder.setUnselectedImageVisible(false);
		tab_folder.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (e.item instanceof CTabItem) {
					CTabItem tab_item = (CTabItem) e.item;
					PageItem page_item = (PageItem) tab_item.getData();
					if (listener != null) {
						listener.selectMenu(page_item.page);
					} else {
						ViewPart4Tab.this.openPage(page_item.page);
					}
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		tab_folder.addCTabFolder2Listener(new CTabFolder2Adapter() {
			@Override
			public void close(CTabFolderEvent e) {
				if (e.item instanceof CTabItem) {
					CTabItem tab_item = (CTabItem) e.item;
					dispose(tab_item);
				}
			}
		});
		TabFloderMouseListener tabFloderMouseListener = new TabFloderMouseListener(tab_folder);
		tab_folder.addMouseListener(tabFloderMouseListener);
		tab_folder.addMouseMoveListener(tabFloderMouseListener);
		tab_folder.setData(this);
	}

	private CTabItem getCTabItem(PAGE page) {
		CTabItem tab_item_select = null;
		for (CTabItem tab_item : tab_folder.getItems()) {
			PageItem page_item = (PageItem) tab_item.getData();
			if (page_item.page.equals(page)) {
				tab_item_select = tab_item;
				break;
			}
		}
		return tab_item_select;
	}

	public void addPage(PAGE page) {
		CTabItem tab_item_select = getCTabItem(page);
		if (tab_item_select == null) {
			tab_item_select = newCTabItem(tab_folder, page);
		}
		layout();
	}

	@Override
	public void openPage(PAGE page) {
		CTabItem tab_item_select = getCTabItem(page);
		if (tab_item_select == null) {
			tab_item_select = newCTabItem(tab_folder, page);
		}
		tab_folder.setSelection(tab_item_select);
		layout();
	}

	private AtomicBoolean lock_layout = new AtomicBoolean(false);

	@Override
	public void layout() {
		if (!lock_layout.get()) {
			try {
				lock_layout.set(true);

				Rectangle rectangle = getBounds();
				tab_folder.setBounds(rectangle.x, rectangle.y, rectangle.width, rectangle.height);

				CTabItem tab_item_select = tab_folder.getSelection();
				if (tab_item_select == null && 0 < tab_folder.getItemCount()) {
					tab_item_select = tab_folder.getItem(0);
					tab_folder.setSelection(tab_item_select);
				}

				for (CTabItem tab_item : tab_folder.getItems()) {
					boolean visible = (tab_item == tab_item_select);
					PageItem page_item = (PageItem) tab_item.getData();
					if (visible) {
						if (page_item.page_widget == null) {
							initCTabItem(tab_item);
						}

						int height_offset = tab_folder.getTabHeight();
						height_offset = Math.max(height_offset, 22);
						page_item.page_widget.setBounds(rectangle.x + 1, rectangle.y + height_offset + 1, rectangle.width - 2, rectangle.height - height_offset - 2);
						tab_folder.moveBelow(page_item.page_widget);
						if (visible && page_item.toolbar != null) {
							tab_folder.setTopRight(page_item.toolbar, SWT.RIGHT);
						}
					}
					page_item.setVisible(visible);
				}
			} finally {
				lock_layout.set(false);
			}
		}
	}

	private CTabItem newCTabItem(CTabFolder tab_folder, PAGE page) {
		return newCTabItem(tab_folder, page, tab_folder.getItemCount());
	}

	private CTabItem newCTabItem(CTabFolder tab_folder, PAGE page, int index) {
		CTabItem tab_item = new CTabItem(tab_folder, SWT.PUSH | (page.closable ? SWT.CLOSE : 0), index);
		tab_item.setText(page.title);
		tab_item.setImage(pageIcon);
		tab_item.setData(new PageItem(page));
		return tab_item;
	}

	private void initCTabItem(CTabItem tab_item) {
		PageItem page_item = (PageItem) tab_item.getData();

		PageIf page_widget = getWorkbench().getPage(page_item.page);
		ToolBar toolbar = null;
		if (0 < page_widget.getActions().length) {
			toolbar = new ToolBar(tab_folder, SWT.FLAT);

			for (final ActionIf action : page_widget.getActions()) {
				ToolItem item = new ToolItem(toolbar, action.getStyle());
				Image image = action.getImage();
				if (image != null) {
					item.setImage(image);
				} else if (action.getText() != null) {
					item.setText(action.getText());
				}
				if (action.getTooltip() != null) {
					item.setToolTipText(action.getTooltip());
				}
				item.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						try {
							action.widgetSelected((ToolItem) e.widget);
						} catch (Exception ex) {
							DialogMessage.openError(getWorkbench().getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.UNKNOWN), ex);
						}
					}
				});
				action.initAction(item);
			}
		}
		page_item.page_widget = (Control) page_widget;
		page_item.toolbar = toolbar;
	}

	private void layout_tab_item(CTabFolder drop_target, CTabItem drag_source, Point point_display) {
		if (0 <= drop_target.indexOf(drag_source)) {
			CTabItem drop_item = drop_target.getItem(drop_target.toControl(point_display));
			int source_index = drag_source == null ? -1 : tab_folder.indexOf(drag_source);
			int target_index = drop_item == null ? -1 : tab_folder.indexOf(drop_item);
			if (drop_item == null) {
				PageItem page_item = (PageItem) drag_source.getData();
				page_item.dispose();
				if (!drag_source.isDisposed()) {
					drag_source.dispose();
				}

				newCTabItem(tab_folder, page_item.page);
				getWorkbench().openPage(page_item.page);
			} else if (drag_source == drop_item) {
			} else if (0 <= source_index && 0 <= target_index && source_index + 1 == target_index) {
			} else if (0 <= tab_folder.indexOf(drop_item)) {
				PageItem page_item = (PageItem) drag_source.getData();
				page_item.dispose();
				if (!drag_source.isDisposed()) {
					drag_source.dispose();
				}

				newCTabItem(tab_folder, page_item.page, tab_folder.indexOf(drop_item));
				getWorkbench().openPage(page_item.page);
			}
		} else {
			PageItem page_item = (PageItem) drag_source.getData();
			page_item.dispose();
			if (!drag_source.isDisposed()) {
				drag_source.dispose();
			}
			getWorkbench().movePage(page_item.page, (ViewPart4Tab) drop_target.getData());
		}
	}

	private void detach(CTabItem tab_item) {
		// PageItem page_item = (PageItem) tab_item.getData();
		// page_item.dispose();
		// if (!tab_item.isDisposed()) {
		// tab_item.dispose();
		// }
		// getWorkbench().detachPage(page_item.page);
	}

	private void dispose(CTabItem tab_item) {
		PageItem page_item = (PageItem) tab_item.getData();
		page_item.dispose();
		if (!tab_item.isDisposed()) {
			tab_item.dispose();
		}
		getWorkbench().disposePage(page_item.page);
	}

}
