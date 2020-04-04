/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.client_swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.Page;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PageIf;
import com.hellonms.platforms.emp_onion.client_swt.widget.viewpart.PAGE;
import com.hellonms.platforms.emp_onion.client_swt.widget.workbench.Workbench;
import com.hellonms.platforms.emp_orange.client_swt.page.PAGE_ORANGE;
import com.hellonms.platforms.emp_orange.client_swt.page.fault.alarm_active.Page4AlarmActive;
import com.hellonms.platforms.emp_orange.client_swt.page.fault.alarm_active.Page4AlarmActiveConsole;
import com.hellonms.platforms.emp_orange.client_swt.page.fault.alarm_history.Page4AlarmHistory;
import com.hellonms.platforms.emp_orange.client_swt.page.fault.alarm_history.Page4AlarmHistoryConsole;
import com.hellonms.platforms.emp_orange.client_swt.page.fault.alarm_statistics.Page4AlarmStatistics;
import com.hellonms.platforms.emp_orange.client_swt.page.fault.event.Page4Event;
import com.hellonms.platforms.emp_orange.client_swt.page.fault.event.Page4EventConsole;
import com.hellonms.platforms.emp_orange.client_swt.page.network.ne_info.Page4NeInfo;
import com.hellonms.platforms.emp_orange.client_swt.page.network.ne_statistics.Page4NeStatistics;
import com.hellonms.platforms.emp_orange.client_swt.page.network.ne_threshold.Page4NeThreshold;
import com.hellonms.platforms.emp_orange.client_swt.page.network.network_tree.ModelClient4NetworkTree;
import com.hellonms.platforms.emp_orange.client_swt.page.network.network_tree.ModelClient4NetworkTree.NODE;
import com.hellonms.platforms.emp_orange.client_swt.page.network.network_tree.Page4NetworkTree;
import com.hellonms.platforms.emp_orange.client_swt.page.network.network_tree.Page4NetworkTree.Page4NetworkTreeListener;
import com.hellonms.platforms.emp_orange.client_swt.page.network.network_view.Page4NetworkView;
import com.hellonms.platforms.emp_orange.client_swt.page.network.network_view.Page4NetworkView.Page4NetworkViewListenerIf;
import com.hellonms.platforms.emp_orange.client_swt.page.security.operation_log.Page4OperationLog;
import com.hellonms.platforms.emp_orange.client_swt.page.security.user.Page4User;
import com.hellonms.platforms.emp_orange.client_swt.page.toolbar.Page4Toolbar;
import com.hellonms.platforms.emp_orange.client_swt.page.toolbar.Page4Toolbar.Page4ToolbarListenerIf;
import com.hellonms.platforms.emp_orange.client_swt.widget.page.PageNode;
import com.hellonms.platforms.emp_orange.client_swt.widget.page.PageNode.PageNodeListenerIf;
import com.hellonms.platforms.emp_orange.share.model.fault.event.SEVERITY;
import com.hellonms.platforms.emp_orange.share.model.network.ne_group.Model4NeGroup;

/**
 * <p>
 * Workbench4Orange
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 18.
 * @modified 2015. 5. 18.
 * @author cchyun
 *
 */
public class Workbench4Orange extends Workbench {

	public class Workbench4OrangeChildListener implements //
			Page4ToolbarListenerIf, //
			Page4NetworkTreeListener, //
			Page4NetworkViewListenerIf, //
			PageNodeListenerIf {

		@Override
		public void selectMenu(PAGE page) {
			Workbench4Orange.this.selectMenu(page);
		}

		@Override
		public void openAlarmActive(SEVERITY severity) {
			PageIf page = getPage(PAGE_ORANGE.NETWORK_TREE);
			if (page != null && page instanceof Page4NetworkTree) {
				NODE node = ((Page4NetworkTree) page).getSelectedNode();
				page = openPage(PAGE_ORANGE.ALARM_ACTIVE);
				if (page != null && page instanceof Page4AlarmActive) {
					((Page4AlarmActive) page).display(node, severity);
				}
			}
		}

		@Override
		public void selectTreeNode(NODE node) {
			for (PAGE page : getListCenterActive()) {
				PageIf ppp = openPage(page);
				if (ppp instanceof PageNode) {
					((PageNode) ppp).display(node);
				}
			}
		}

		@Override
		public void openNetworkView(NODE node) {
			PageIf page = openPage(PAGE_ORANGE.NETWORK_VIEW);
			// PageIf page = getPage(PAGE_ORANGE.NETWORK_TREE);
			if (page != null && page instanceof Page4NetworkView) {
				((Page4NetworkView) page).display(node);
			}
		}

		@Override
		public void openAlarmActive(NODE node) {
			PageIf page = openPage(PAGE_ORANGE.ALARM_ACTIVE);
			if (page != null && page instanceof Page4AlarmActive) {
				((Page4AlarmActive) page).display(node);
			}
		}

		@Override
		public void selectViewNode(NODE node) {
			PageIf page = getPage(PAGE_ORANGE.NETWORK_TREE);
			if (page != null && page instanceof Page4NetworkTree) {
				((Page4NetworkTree) page).selectNode(node);
			}
		}
	}

	protected ApplicationProperty applicationProperty;

	public Workbench4Orange(Composite parent, int style, ApplicationProperty applicationProperty) {
		super(parent, style);
		this.applicationProperty = applicationProperty;
		preWindowOpen();
	}

	protected void selectMenu(PAGE page) {
		PageIf ppp = openPage(page);
		if (ppp instanceof PageNode) {
			NODE node = null;
			PageIf page4NetworkTree = getPage(PAGE_ORANGE.NETWORK_TREE);
			if (page4NetworkTree != null && page4NetworkTree instanceof Page4NetworkTree) {
				node = ((Page4NetworkTree) page4NetworkTree).getSelectedNode();
			}
			if (node == null) {
				ppp.display(true);
			} else {
				((PageNode) ppp).display(node);
			}
		} else {
			ppp.display(true);
		}
	}

	@Override
	protected void initPage() {
		setToolbar(PAGE_ORANGE.TOOLBAR);
		addLeft(PAGE_ORANGE.NETWORK_TREE);
		openPage(PAGE_ORANGE.NETWORK_VIEW);
		addBottom(PAGE_ORANGE.EVENT_CONSOLE);
		addBottom(PAGE_ORANGE.ALARM_ACTIVE_CONSOLE);
		addBottom(PAGE_ORANGE.ALARM_HISTORY_CONSOLE);
	}

	@Override
	protected Page newPage(PAGE page) {
		if (PAGE_ORANGE.TOOLBAR.equals(page)) {
			return new Page4Toolbar(this, SWT.NONE, applicationProperty, new Workbench4OrangeChildListener());
		} else if (PAGE_ORANGE.NETWORK_TREE.equals(page)) {
			return new Page4NetworkTree(this, SWT.NONE, new Workbench4OrangeChildListener());
		} else if (PAGE_ORANGE.NETWORK_VIEW.equals(page)) {
			return new Page4NetworkView(this, SWT.NONE, new Workbench4OrangeChildListener());
		} else if (PAGE_ORANGE.NE_INFO.equals(page)) {
			return new Page4NeInfo(this, SWT.NONE, new Workbench4OrangeChildListener());
		} else if (PAGE_ORANGE.NE_STATISTICS.equals(page)) {
			return new Page4NeStatistics(this, SWT.NONE, new Workbench4OrangeChildListener());
		} else if (PAGE_ORANGE.NE_THRESHOLD.equals(page)) {
			return new Page4NeThreshold(this, SWT.NONE, new Workbench4OrangeChildListener());
		} else if (PAGE_ORANGE.ALARM_ACTIVE.equals(page)) {
			return new Page4AlarmActive(this, SWT.NONE, new Workbench4OrangeChildListener());
		} else if (PAGE_ORANGE.ALARM_HISTORY.equals(page)) {
			return new Page4AlarmHistory(this, SWT.NONE, new Workbench4OrangeChildListener());
		} else if (PAGE_ORANGE.EVENT.equals(page)) {
			return new Page4Event(this, SWT.NONE, new Workbench4OrangeChildListener());
		} else if (PAGE_ORANGE.ALARM_STATISTICS.equals(page)) {
			return new Page4AlarmStatistics(this, SWT.NONE, new Workbench4OrangeChildListener());
		} else if (PAGE_ORANGE.USER.equals(page)) {
			return new Page4User(this, SWT.NONE);
		} else if (PAGE_ORANGE.OPERATION_LOG.equals(page)) {
			return new Page4OperationLog(this, SWT.NONE, new Workbench4OrangeChildListener());
		} else if (PAGE_ORANGE.EVENT_CONSOLE.equals(page)) {
			return new Page4EventConsole(this, SWT.NONE);
		} else if (PAGE_ORANGE.ALARM_ACTIVE_CONSOLE.equals(page)) {
			return new Page4AlarmActiveConsole(this, SWT.NONE);
		} else if (PAGE_ORANGE.ALARM_HISTORY_CONSOLE.equals(page)) {
			return new Page4AlarmHistoryConsole(this, SWT.NONE);
		} else {
			Page ppp = new Page(this, SWT.NONE, page.title, "");
			ppp.setBackground(getDisplay().getSystemColor(SWT.COLOR_DARK_GRAY));
			return ppp;
		}
	}

	@Override
	protected void checkSubclass() {
	}

	protected void preWindowOpen() {
		try {
			NODE node = ModelClient4NetworkTree.getInstance().getNeGroup(Model4NeGroup.ROOT_NE_GROUP_ID);
			PageIf page = getPage(PAGE_ORANGE.NETWORK_TREE);
			if (page != null && page instanceof Page4NetworkTree) {
				((Page4NetworkTree) page).selectNode(node);
			}
		} catch (EmpException e) {
		}
	}

}
