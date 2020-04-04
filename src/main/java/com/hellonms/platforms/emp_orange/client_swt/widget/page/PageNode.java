package com.hellonms.platforms.emp_orange.client_swt.widget.page;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_onion.client_swt.util.resource.UtilResource;
import com.hellonms.platforms.emp_onion.client_swt.widget.action.ActionIf;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.Page;
import com.hellonms.platforms.emp_onion.client_swt.widget.selector.SelectorPath;
import com.hellonms.platforms.emp_onion.client_swt.widget.selector.SelectorPath.SelectorPathListenerIf;
import com.hellonms.platforms.emp_orange.client_swt.page.network.network_tree.ModelClient4NetworkTree;
import com.hellonms.platforms.emp_orange.client_swt.page.network.network_tree.ModelClient4NetworkTree.NODE;

/**
 * <p>
 * PageNode
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class PageNode extends Page {

	public interface PageNodeListenerIf {

		public void selectViewNode(NODE node);

	}

	public interface ToolbarItemIf {

		public String getId();

		public Control initControl(Composite parent);

		public Control getControl();

		public void setSelection(boolean selection);

		public void setEnabled(boolean enabled);

	}

	protected PageNodeListenerIf listener;

	/**
	 * 패스 셀렉터
	 */
	protected SelectorPath selectorPath;

	/**
	 * 컨텐츠
	 */
	protected Composite content;

	protected Composite panelToolbar;

	protected NODE node;

	protected NODE selectedNode;

	// protected Map<String, ToolbarItemIf> toolbarItemMap = new LinkedHashMap<String, ToolbarItemIf>();

	public PageNode(Composite parent, int style, PageNodeListenerIf listener) {
		super(parent, style, "", "");

		this.listener = listener;

		createGUI();
	}

	protected void createGUI() {
		setLayout(new FormLayout());

		Composite panelSelectorPath = new Composite(this, SWT.NONE);
		GridLayout gl_panelSelectorPath = new GridLayout(2, false);
		gl_panelSelectorPath.verticalSpacing = 0;
		gl_panelSelectorPath.marginWidth = 1;
		gl_panelSelectorPath.marginHeight = 1;
		gl_panelSelectorPath.horizontalSpacing = 0;
		panelSelectorPath.setLayout(gl_panelSelectorPath);
		FormData fd_panelSelectorPath = new FormData();
		fd_panelSelectorPath.right = new FormAttachment(100);
		fd_panelSelectorPath.top = new FormAttachment(0);
		fd_panelSelectorPath.bottom = new FormAttachment(0, 27);
		fd_panelSelectorPath.left = new FormAttachment(0);
		panelSelectorPath.setLayoutData(fd_panelSelectorPath);
		panelSelectorPath.setBackground(UtilResource.getColor(255, 255, 255));

		selectorPath = new SelectorPath(panelSelectorPath, SWT.BORDER, new SelectorPathListenerIf() {
			@Override
			public void selected(Object value) {
				if (listener != null) {
					listener.selectViewNode((NODE) value);
					display((NODE) value);
				}
			}
		});
		selectorPath.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		panelToolbar = new Composite(panelSelectorPath, SWT.NONE);
		GridData gd_panelToolbar = new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1);
		gd_panelToolbar.horizontalIndent = 1;
		panelToolbar.setLayoutData(gd_panelToolbar);
		panelToolbar.setBackground(UtilResource.getColor(255, 255, 255));
		GridLayout gl_panelToolbar = new GridLayout(8, false);
		gl_panelToolbar.verticalSpacing = 0;
		gl_panelToolbar.marginWidth = 0;
		gl_panelToolbar.marginHeight = 0;
		gl_panelToolbar.horizontalSpacing = 0;
		panelToolbar.setLayout(gl_panelToolbar);

		content = createContent(this);
		content.addListener(SWT.MouseDown, new Listener() {
			@Override
			public void handleEvent(Event e) {
				content.forceFocus();
			}
		});

		FormData fd_content = new FormData();
		fd_content.top = new FormAttachment(panelSelectorPath);
		fd_content.right = new FormAttachment(panelSelectorPath, 0, SWT.RIGHT);
		fd_content.left = new FormAttachment(panelSelectorPath, 0, SWT.LEFT);
		fd_content.bottom = new FormAttachment(100);
		content.setLayoutData(fd_content);
	}

	/**
	 * 컨텐츠를 생성합니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 * @return 컨트롤
	 */
	protected Composite createContent(Composite parent) {
		return new Composite(parent, SWT.NONE);
	}

	protected void setActions(ActionIf[] actions) {
		panelToolbar.getParent().layout();
	}

	public void display(NODE node) {
		this.node = node;
		displayPath(node);
	}

	/**
	 * 패스를 출력합니다.
	 * 
	 * @param node
	 *            노드
	 */
	protected void displayPath(NODE node) {
		try {
			NODE[] path;
			if (node.isNeGroup()) {
				path = ModelClient4NetworkTree.getInstance().getPathNeGroup(node.getId());
			} else if (node.isNe()) {
				path = ModelClient4NetworkTree.getInstance().getPathNe(node.getId());
			} else {
				path = new NODE[] {};
			}
			selectorPath.setPath(path);
		} catch (EmpException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean forceFocus() {
		return content.forceFocus();
	}

	/**
	 * 노드를 반환합니다.
	 * 
	 * @return 노드
	 */
	protected NODE getNode() {
		return node;
	}

	/**
	 * 선택된 노드를 반환합니다.
	 * 
	 * @return 노드
	 */
	protected NODE getSelectedNode() {
		return selectedNode != null ? selectedNode : node;
	}

	protected void initToolbar() {
		for (Control control : panelToolbar.getChildren()) {
			control.setVisible(false);
			((GridData) control.getLayoutData()).exclude = true;
		}

		for (ToolbarItemIf toolbarItem : getToolbarItems()) {
			Control control = toolbarItem.getControl();
			if (control == null) {
				control = toolbarItem.initControl(panelToolbar);
				control.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));
			}
			control.setVisible(true);
			((GridData) control.getLayoutData()).exclude = false;
		}
		panelToolbar.getParent().layout();
		panelToolbar.layout();
	}

	protected ToolbarItemIf[] getToolbarItems() {
		return new ToolbarItemIf[] {};
	}

}
