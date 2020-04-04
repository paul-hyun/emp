/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_onion.client_swt.widget.workbench;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Sash;
import org.eclipse.swt.widgets.Shell;

import com.hellonms.platforms.emp_onion.client_swt.widget.page.Page;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PageIf;
import com.hellonms.platforms.emp_onion.client_swt.widget.viewpart.PAGE;
import com.hellonms.platforms.emp_onion.client_swt.widget.viewpart.ViewPart4One;
import com.hellonms.platforms.emp_onion.client_swt.widget.viewpart.ViewPart4Tab;
import com.hellonms.platforms.emp_onion.client_swt.widget.viewpart.ViewPart4Tab.ViewPart4TabListenerIf;
import com.hellonms.platforms.emp_onion.client_swt.widget.viewpart.ViewPartAt;
import com.hellonms.platforms.emp_onion.client_swt.widget.workbench.LayoutData4Workbench.WORKBENCH_ITEM;
import com.hellonms.platforms.emp_onion.theme.ThemeBuilder4Onion.COLOR_ONION;
import com.hellonms.platforms.emp_onion.theme.ThemeBuilder4Onion.IMAGE_ONION;
import com.hellonms.platforms.emp_onion.theme.ThemeFactory;

/**
 * <p>
 * Workbench
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 18.
 * @modified 2015. 5. 18.
 * @author cchyun
 *
 */
public class Workbench extends Composite {

	private class ChildListener implements ViewPart4TabListenerIf {

		@Override
		public void selectMenu(PAGE page) {
			Workbench.this.selectMenu(page);
		}

	}

	private int toobar_height = 70;

	private Sash[] sashs = { null, null };

	private ViewPartAt[] view_parts = { null, null, null, null };

	private Map<PAGE, ViewPartAt> view_part_map = new HashMap<PAGE, ViewPartAt>();

	private Map<PAGE, PageIf> page_map = new HashMap<PAGE, PageIf>();

	public Workbench(Composite parent, int style) {
		super(parent, style);

		createGUI();
	}

	private void createGUI() {
		setLayout(new Layout4Workbench());
		setBackground(ThemeFactory.getColor(COLOR_ONION.WORKBENCH_BG));

		view_parts[0] = new ViewPart4One(this, SWT.NONE);
		view_parts[0].setLayoutData(new LayoutData4Workbench(WORKBENCH_ITEM.VIEWPART_TOOLBAR, toobar_height));

		view_parts[1] = new ViewPart4Tab(this, SWT.NONE, ThemeFactory.getImage(IMAGE_ONION.PAGE_ICON));
		view_parts[1].setLayoutData(new LayoutData4Workbench(WORKBENCH_ITEM.VIEWPART_LEFT, 0));

		view_parts[2] = new ViewPart4Tab(this, SWT.NONE, ThemeFactory.getImage(IMAGE_ONION.PAGE_ICON), new ChildListener());
		view_parts[2].setLayoutData(new LayoutData4Workbench(WORKBENCH_ITEM.VIEWPART_CENTER, 0));

		view_parts[3] = new ViewPart4Tab(this, SWT.NONE, ThemeFactory.getImage(IMAGE_ONION.PAGE_ICON));
		view_parts[3].setLayoutData(new LayoutData4Workbench(WORKBENCH_ITEM.VIEWPART_BOTTOM, 0));

		sashs[0] = new Sash(this, SWT.HORIZONTAL);
		sashs[0].setBackground(ThemeFactory.getColor(COLOR_ONION.WORKBENCH_BG));
		sashs[0].setLayoutData(new LayoutData4Workbench(WORKBENCH_ITEM.SASH_HORIZONTAL, 0.7));
		sashs[0].addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int layout_weight = e.y;
				((LayoutData4Workbench) sashs[0].getLayoutData()).layout_weight = layout_weight;
				Workbench.this.layout();
			}
		});

		sashs[1] = new Sash(this, SWT.VERTICAL);
		sashs[1].setBackground(ThemeFactory.getColor(COLOR_ONION.WORKBENCH_BG));
		sashs[1].setLayoutData(new LayoutData4Workbench(WORKBENCH_ITEM.SASH_VERTICAL, 0.2));
		sashs[1].addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int layout_weight = e.x;
				((LayoutData4Workbench) sashs[1].getLayoutData()).layout_weight = layout_weight;
				Workbench.this.layout();
			}
		});

		initPage();
	}

	Sash[] getSashs() {
		return sashs;
	}

	ViewPartAt[] getView_parts() {
		return view_parts;
	}

	protected void initPage() {

	}

	protected void setToolbar(PAGE page) {
		view_part_map.put(page, view_parts[0]);
		view_parts[0].openPage(page);
	}

	protected void addLeft(PAGE page) {
		view_part_map.put(page, view_parts[1]);
		((ViewPart4Tab) view_parts[1]).addPage(page);
	}

	protected PAGE[] getListCenterActive() {
		List<PAGE> page_list = new ArrayList<PAGE>();
		for (PAGE page : view_part_map.keySet()) {
			ViewPartAt view_part = view_part_map.get(page);
			if (view_part == view_parts[2]) {
				PageIf page_widget = page_map.get(page);
				if (page_widget != null && ((Control) page_widget).isVisible()) {
					page_list.add(page);
				}
			}
		}
		return page_list.toArray(new PAGE[0]);
	}

	protected void addBottom(PAGE page) {
		view_part_map.put(page, view_parts[3]);
		((ViewPart4Tab) view_parts[3]).addPage(page);
	}

	public PageIf openPage(PAGE page) {
		ViewPartAt view_part = view_part_map.get(page);
		if (view_part == null) {
			view_part = view_parts[2];
			view_part_map.put(page, view_part);
		}

		view_part.openPage(page);
		view_part.layout();

		return getPage(page);
	}

	public PageIf getPage(PAGE page) {
		PageIf page_widget = page_map.get(page);
		if (page_widget == null || ((Control) page_widget).isDisposed()) {
			page_widget = newPage(page);
			page_map.put(page, page_widget);
		}
		return page_widget;
	}

	public void setPageVisible(PAGE page, boolean visible) {
		PageIf page_widget = page_map.get(page);
		if (page_widget != null && !((Control) page_widget).isDisposed()) {
			if (((Control) page_widget).getVisible() != visible) {
				((Control) page_widget).setVisible(visible);
			}
		}
	}

	public void movePage(PAGE page, ViewPart4Tab viewPart4Tab) {
		view_part_map.put(page, viewPart4Tab);
		openPage(page);
	}

	public void detachPage(final PAGE page) {
		PageIf page_widget = page_map.get(page);
		if (page_widget != null && !((Control) page_widget).isDisposed()) {
			Shell shell = new Shell(getShell(), SWT.SHELL_TRIM);
			shell.setLayout(new FillLayout());
			shell.setImage(ThemeFactory.getImage(IMAGE_ONION.PAGE_ICON));
			shell.setText(page.title);
			((Control) page_widget).setParent(shell);
			shell.addDisposeListener(new DisposeListener() {
				@Override
				public void widgetDisposed(DisposeEvent e) {
					attachPage(page);
				}
			});
			shell.open();
		}
	}

	public void attachPage(final PAGE page) {
		PageIf page_widget = page_map.get(page);
		if (page_widget != null && !((Control) page_widget).isDisposed()) {
			((Control) page_widget).setParent(this);
		}
		openPage(page);
	}

	public void disposePage(PAGE page) {
		PageIf page_widget = page_map.remove(page);
		if (page_widget != null && !((Control) page_widget).isDisposed()) {
			((Control) page_widget).dispose();
		}
	}

	protected Page newPage(PAGE page) {
		return null;
	}

	protected void selectMenu(PAGE page) {
	}

	@Override
	protected void checkSubclass() {
	}

}
