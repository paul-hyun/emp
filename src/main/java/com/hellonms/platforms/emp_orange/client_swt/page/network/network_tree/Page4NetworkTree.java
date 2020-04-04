package com.hellonms.platforms.emp_orange.client_swt.page.network.network_tree;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;

import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_core.share.language.UtilLanguage;
import com.hellonms.platforms.emp_onion.client_swt.data.DataFactory;
import com.hellonms.platforms.emp_onion.client_swt.widget.dialog.DialogMessage;
import com.hellonms.platforms.emp_onion.client_swt.widget.dialog.DialogProgress;
import com.hellonms.platforms.emp_onion.client_swt.widget.dialog.DialogProgress.ProgressTaskIf;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.Page;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelTree;
import com.hellonms.platforms.emp_onion.theme.ThemeBuilder4Onion.COLOR_ONION;
import com.hellonms.platforms.emp_onion.theme.ThemeFactory;
import com.hellonms.platforms.emp_orange.client_swt.page.DataBuilder4Orange.DATA_TREE_ORANGE;
import com.hellonms.platforms.emp_orange.client_swt.page.PanelBuilder4Orange.TREE_ORANGE;
import com.hellonms.platforms.emp_orange.client_swt.page.PanelFactory;
import com.hellonms.platforms.emp_orange.client_swt.page.network.menu.MenuPopup4Network;
import com.hellonms.platforms.emp_orange.client_swt.page.network.menu.MenuPopup4Network.MenuPopup4NetworkListenerIf;
import com.hellonms.platforms.emp_orange.client_swt.page.network.ne.Wizard4CreateNe;
import com.hellonms.platforms.emp_orange.client_swt.page.network.ne.Wizard4CreateNe.Wizard4CreateNeListenerIf;
import com.hellonms.platforms.emp_orange.client_swt.page.network.ne.Wizard4DiscoveryNe;
import com.hellonms.platforms.emp_orange.client_swt.page.network.ne.Wizard4DiscoveryNe.Wizard4DiscoveryNeListenerIf;
import com.hellonms.platforms.emp_orange.client_swt.page.network.ne.Wizard4UpdateNe;
import com.hellonms.platforms.emp_orange.client_swt.page.network.ne.Wizard4UpdateNe.Wizard4UpdateNeListenerIf;
import com.hellonms.platforms.emp_orange.client_swt.page.network.ne_group.Wizard4CreateNeGroup;
import com.hellonms.platforms.emp_orange.client_swt.page.network.ne_group.Wizard4CreateNeGroup.Wizard4CreateNeGroupListenerIf;
import com.hellonms.platforms.emp_orange.client_swt.page.network.ne_group.Wizard4UpdateNeGroup;
import com.hellonms.platforms.emp_orange.client_swt.page.network.ne_group.Wizard4UpdateNeGroup.Wizard4UpdateNeGroupListenerIf;
import com.hellonms.platforms.emp_orange.client_swt.page.network.network_tree.ModelClient4NetworkTree.NODE;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.network.ne.Model4Ne;
import com.hellonms.platforms.emp_orange.share.model.network.ne_group.Model4NeGroup;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.Model4NeSessionDiscoveryFilterIf;
import com.hellonms.platforms.emp_orange.theme.ThemeBuilder4Orange.COLOR_ORANGE;

/**
 * <p>
 * Page4NetworkTree
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class Page4NetworkTree extends Page {

	public interface Page4NetworkTreeListener {

		public void selectTreeNode(NODE node);

		public void openNetworkView(NODE node);

		public void openAlarmActive(NODE node);

	}

	public class Page4NetworkTreeChildListener implements //
			MenuPopup4NetworkListenerIf, //
			Wizard4CreateNeGroupListenerIf, //
			Wizard4UpdateNeGroupListenerIf, //
			Wizard4DiscoveryNeListenerIf, //
			Wizard4CreateNeListenerIf, //
			Wizard4UpdateNeListenerIf {

		@Override
		public void openNetworkView(NODE node) {
			listener.openNetworkView(node);
		}

		@Override
		public void openAlarmActive(NODE node) {
			listener.openAlarmActive(node);
		}

		@Override
		public void openWizard4CreateNeGroup(int parentNeGroupId) {
			Page4NetworkTree.this.openWizard4CreateNeGroup(parentNeGroupId);
		}

		@Override
		public void createNeGroup(Wizard4CreateNeGroup wizard, Model4NeGroup model4NeGroup) {
			Page4NetworkTree.this.createNeGroup(wizard, model4NeGroup, true);
		}

		@Override
		public void openWizard4UpdateNeGroup(Model4NeGroup model4NeGroup) {
			Wizard4UpdateNeGroup.open(getShell(), model4NeGroup, queryListImagePath(), new Page4NetworkTreeChildListener());
		}

		@Override
		public void updateNeGroup(Wizard4UpdateNeGroup wizard, Model4NeGroup model4NeGroup) {
			Page4NetworkTree.this.updateNeGroup(wizard, model4NeGroup, true);
		}

		@Override
		public void deleteNeGroup(int neGroupId) {
			Page4NetworkTree.this.deleteNeGroup(neGroupId, true);
		}

		@Override
		public void openWizard4DiscoveryNe(int neGroupId) {
			Page4NetworkTree.this.openWizard4DiscoveryNe(neGroupId, this);
		}

		@Override
		public void discoveryListNe(Wizard4DiscoveryNe wizard, Model4NeSessionDiscoveryFilterIf[] model4NeSessionDiscoveryFilters) {
			Page4NetworkTree.this.discoveryListNe(wizard, model4NeSessionDiscoveryFilters);
		}

		@Override
		public void createListNe(Wizard4DiscoveryNe wizard, Model4Ne[] model4Nes) {
			Page4NetworkTree.this.createListNe(wizard, model4Nes);
		}

		@Override
		public void openWizard4CreateNe(int neGroupId) {
			Page4NetworkTree.this.openWizard4CreateNe(neGroupId, this);
		}

		@Override
		public void createNe(Wizard4CreateNe wizard, Model4Ne model4Ne) {
			Page4NetworkTree.this.createNe(wizard, model4Ne, true);
		}

		@Override
		public void openWizard4UpdateNe(Model4Ne model4Ne) {
			Wizard4UpdateNe.open(getShell(), model4Ne, queryListImagePath(), new Page4NetworkTreeChildListener());
		}

		@Override
		public void updateNe(Wizard4UpdateNe wizard, Model4Ne model4Ne) {
			Page4NetworkTree.this.updateNe(wizard, model4Ne, true);
		}

		@Override
		public void deleteNe(int neId) {
			Page4NetworkTree.this.deleteNe(neId, true);
		}

		@Override
		public void stopIconBlink(NODE node) {
		}

	}

	protected Page4NetworkTreeListener listener;

	protected PanelTree panelTree;

	protected MenuPopup4Network menuPopup4Network;

	protected NODE node_selected;

	protected Page4NetworkTreeAdvisor advisor;

	public Page4NetworkTree(Composite parent, int style, Page4NetworkTreeListener listener) {
		super(parent, style, "", "");

		this.listener = listener;

		createGUI();
	}

	protected void createGUI() {
		getContentComposite().setBackgroundMode(SWT.INHERIT_DEFAULT);
		getContentComposite().setBackground(ThemeFactory.getColor(COLOR_ONION.PAGE_BG));
		getContentComposite().setLayout(new FillLayout(SWT.HORIZONTAL));

		Composite treePanel = new Composite(this, SWT.NONE);
		GridLayout gl_treePanel = new GridLayout(1, false);
		gl_treePanel.marginWidth = 0;
		gl_treePanel.marginHeight = 0;
		treePanel.setLayout(gl_treePanel);

		panelTree = (PanelTree) PanelFactory.createPanelTree(TREE_ORANGE.NETWORK_TREE, treePanel, SWT.NONE);
		panelTree.setDataTree(DataFactory.createDataTree(DATA_TREE_ORANGE.NETWORK_TREE));
		panelTree.getControl().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				if (e.button == 1) {
					IStructuredSelection selection = (IStructuredSelection) panelTree.getSelection();
					if (!selection.isEmpty() && selection.getFirstElement() instanceof NODE && (selection.getFirstElement() != node_selected)) {
						node_selected = (NODE) selection.getFirstElement();
						listener.selectTreeNode((NODE) selection.getFirstElement());
					}
				}
			}

			@Override
			public void mouseDoubleClick(MouseEvent e) {
				if (e.button == 1) {
					IStructuredSelection selection = (IStructuredSelection) panelTree.getSelection();
					if (!selection.isEmpty() && selection.getFirstElement() instanceof NODE) {
						node_selected = (NODE) selection.getFirstElement();
						listener.openNetworkView((NODE) selection.getFirstElement());
					}
				}
			}
		});
		panelTree.setBackground(ThemeFactory.getColor(COLOR_ORANGE.CONSOLE_TREE_BG));
		panelTree.setForeground(ThemeFactory.getColor(COLOR_ORANGE.CONSOLE_TREE_FG));
		panelTree.setExpandedElements(panelTree.getDataTree().getElements(panelTree.getDataTree().getInput()));
		panelTree.getTree().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		menuPopup4Network = createMenuPopup4Network(new Page4NetworkTreeChildListener());

		createMenuPopup(panelTree.getTree());

		advisor = createPage4NetworkTreeAdvisor();
	}

	/**
	 * 네트워크관리의 컨텍스트 메뉴를 생성합니다.
	 * 
	 * @param listener
	 *            리스너
	 * @return 컨텍스트 메뉴
	 */
	protected MenuPopup4Network createMenuPopup4Network(MenuPopup4NetworkListenerIf listener) {
		return new MenuPopup4Network(getShell(), listener);
	}

	protected Page4NetworkTreeAdvisor createPage4NetworkTreeAdvisor() {
		return new Page4NetworkTreeAdvisor();
	}

	/**
	 * 컨텍스트 메뉴를 생성합니다.
	 * 
	 * @param control
	 *            컨트롤
	 */
	protected void createMenuPopup(final Control control) {
		control.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				if (e.button == 3) {
					IStructuredSelection selection = (IStructuredSelection) panelTree.getSelection();
					if (!selection.isEmpty() && selection.getFirstElement() instanceof NODE) {
						NODE node = (NODE) selection.getFirstElement();
						Menu menu = menuPopup4Network.getMenu(control, node, false);
						if (menu != null) {
							menu.setVisible(true);
						}
					}
				}
			}
		});
	}

	@Override
	public void display(boolean progressBar) {
		try {
			NODE node = ModelClient4NetworkTree.getInstance().getNeGroup(Model4NeGroup.ROOT_NE_GROUP_ID);
			selectNode(node);
		} catch (EmpException e) {
		}
	}

	public void selectNode(NODE node) {
		panelTree.setSelection(new StructuredSelection(node));
	}

	public NODE getSelectedNode() {
		StructuredSelection selection = (StructuredSelection) panelTree.getSelection();
		if (!selection.isEmpty() && selection.getFirstElement() instanceof NODE) {
			return (NODE) selection.getFirstElement();
		}
		return ModelClient4NetworkTree.getInstance().getRootNode();
	}

	protected void openWizard4CreateNeGroup(int parentNeGroupId) {
		Model4NeGroup model4NeGroup = new Model4NeGroup();
		model4NeGroup.setParent_ne_group_id(parentNeGroupId);
		Wizard4CreateNeGroup.open(getShell(), model4NeGroup, queryListImagePath(), new Page4NetworkTreeChildListener());
	}

	/**
	 * NE그룹을 생성합니다.
	 * 
	 * @param wizard
	 *            NE그룹 생성 위자드
	 * @param neGroup
	 *            NE그룹 모델
	 * @param newImage
	 *            새 이미지
	 * @param progressBar
	 *            프로그래스바 실행여부
	 */
	protected void createNeGroup(Wizard4CreateNeGroup wizard, final Model4NeGroup neGroup, boolean progressBar) {
		try {
			DialogProgress.run(wizard.getShell(), progressBar, new ProgressTaskIf() {
				@Override
				public Object run() throws EmpException {
					advisor.createNeGroup(neGroup);
					ModelClient4NetworkTree.getInstance().refresh();
					return null;
				}
			});
			Wizard4CreateNeGroup.close();
		} catch (EmpException ex) {
			DialogMessage.openError(wizard.getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.CREATE_TITLE, MESSAGE_CODE_ORANGE.NE_GROUP), ex);
		} catch (Exception ex) {
			DialogMessage.openError(wizard.getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.CREATE_TITLE, MESSAGE_CODE_ORANGE.NE_GROUP), ex);
		}
	}

	/**
	 * NE그룹을 수정합니다.
	 * 
	 * @param wizard
	 *            NE그룹 등록정보 위자드
	 * @param neGroup
	 *            NE그룹 모델
	 * @param newImage
	 *            새 이미지
	 * @param progressBar
	 *            프로그래스바 실행여부
	 */
	protected void updateNeGroup(Wizard4UpdateNeGroup wizard, final Model4NeGroup neGroup, boolean progressBar) {
		try {
			DialogProgress.run(wizard.getShell(), progressBar, new ProgressTaskIf() {
				@Override
				public Object run() throws EmpException {
					advisor.updateNeGroup(neGroup);
					ModelClient4NetworkTree.getInstance().refresh();
					return null;
				}
			});
			Wizard4UpdateNeGroup.close();
		} catch (EmpException ex) {
			DialogMessage.openError(wizard.getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.UPDATE_TITLE, MESSAGE_CODE_ORANGE.NE_GROUP), ex);
		} catch (Exception ex) {
			DialogMessage.openError(wizard.getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.UPDATE_TITLE, MESSAGE_CODE_ORANGE.NE_GROUP), ex);
		}
	}

	/**
	 * NE그룹을 삭제합니다.
	 * 
	 * @param ne_group_id
	 *            NE그룹 아이디
	 * @param progressBar
	 *            프로그래스바 실행여부
	 */
	protected void deleteNeGroup(final int neGroupId, boolean progressBar) {
		try {
			DialogProgress.run(getShell(), progressBar, new ProgressTaskIf() {
				@Override
				public Object run() throws EmpException {
					advisor.deleteNeGroup(neGroupId);
					ModelClient4NetworkTree.getInstance().refresh();
					return null;
				}
			});
		} catch (EmpException ex) {
			DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.DELETE_TITLE, MESSAGE_CODE_ORANGE.NE_GROUP), ex);
		} catch (Throwable ex) {
			DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.DELETE_TITLE, MESSAGE_CODE_ORANGE.NE_GROUP), ex);
		}
	}

	protected void openWizard4DiscoveryNe(int neGroupId, Wizard4DiscoveryNeListenerIf listener) {
		try {
			Model4NeSessionDiscoveryFilterIf[] model4NeSessions = (Model4NeSessionDiscoveryFilterIf[]) DialogProgress.run(getShell(), false, new ProgressTaskIf() {
				@Override
				public Object run() throws EmpException {
					return advisor.newInstanceListDiscoveryFilter();
				}
			});
			Wizard4DiscoveryNe.open(getShell(), neGroupId, model4NeSessions, listener);
		} catch (EmpException ex) {
			DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.DISCOVERY_NE), ex);
		} catch (Throwable ex) {
			DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.DISCOVERY_NE), ex);
		}
	}

	protected void discoveryListNe(Wizard4DiscoveryNe wizard, final Model4NeSessionDiscoveryFilterIf[] model4NeSessionDiscoveryFilters) {
		try {
			Model4Ne[] model4Nes = (Model4Ne[]) DialogProgress.run(getShell(), true, new ProgressTaskIf() {
				@Override
				public Object run() throws EmpException {
					return advisor.discoveryListNe(model4NeSessionDiscoveryFilters);
				}
			});
			wizard.display(model4Nes);
		} catch (EmpException ex) {
			DialogMessage.openError(wizard.getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.DISCOVERY_NE), ex);
		} catch (Throwable ex) {
			DialogMessage.openError(wizard.getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.DISCOVERY_NE), ex);
		}
	}

	protected void createListNe(Wizard4DiscoveryNe wizard, final Model4Ne[] model4Nes) {
		try {
			DialogProgress.run(wizard.getShell(), true, new ProgressTaskIf() {
				@Override
				public Object run() throws EmpException {
					advisor.createListNe(model4Nes);
					ModelClient4NetworkTree.getInstance().refresh();
					return null;
				}
			});
			Wizard4CreateNe.close();
		} catch (EmpException ex) {
			DialogMessage.openError(wizard.getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.DISCOVERY_NE), ex);
		} catch (Throwable ex) {
			DialogMessage.openError(wizard.getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.DISCOVERY_NE), ex);
		}
	}

	/**
	 * 
	 */
	protected void openWizard4CreateNe(int neGroupId, Wizard4CreateNeListenerIf listener) {
		try {
			Model4Ne model4Ne = (Model4Ne) DialogProgress.run(getShell(), false, new ProgressTaskIf() {
				@Override
				public Object run() throws EmpException {
					return advisor.newInstanceNe();
				}
			});
			model4Ne.setNe_group_id(neGroupId);
			Wizard4CreateNe.open(getShell(), model4Ne, queryListImagePath(), listener);
		} catch (EmpException ex) {
			DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.DELETE_TITLE, MESSAGE_CODE_ORANGE.NE_GROUP), ex);
		} catch (Throwable ex) {
			DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.DELETE_TITLE, MESSAGE_CODE_ORANGE.NE_GROUP), ex);
		}
	}

	/**
	 * NE그룹을 생성합니다.
	 * 
	 * @param wizard
	 *            NE그룹 생성 위자드
	 * @param neGroup
	 *            NE그룹 모델
	 * @param newImage
	 *            새 이미지
	 * @param progressBar
	 *            프로그래스바 실행여부
	 */
	protected void createNe(Wizard4CreateNe wizard, final Model4Ne model4Ne, boolean progressBar) {
		try {
			DialogProgress.run(wizard.getShell(), progressBar, new ProgressTaskIf() {
				@Override
				public Object run() throws EmpException {
					advisor.createNe(model4Ne);
					ModelClient4NetworkTree.getInstance().refresh();
					return null;
				}
			});
			Wizard4CreateNe.close();
		} catch (EmpException ex) {
			DialogMessage.openError(wizard.getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.CREATE_TITLE, MESSAGE_CODE_ORANGE.NE), ex);
		} catch (Exception ex) {
			DialogMessage.openError(wizard.getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.CREATE_TITLE, MESSAGE_CODE_ORANGE.NE), ex);
		}
	}

	protected void updateNe(Wizard4UpdateNe wizard, final Model4Ne model4Ne, boolean progressBar) {
		try {
			DialogProgress.run(wizard.getShell(), progressBar, new ProgressTaskIf() {
				@Override
				public Object run() throws EmpException {
					advisor.updateNe(model4Ne);
					ModelClient4NetworkTree.getInstance().refresh();
					return null;
				}
			});
			Wizard4UpdateNe.close();
		} catch (EmpException ex) {
			DialogMessage.openError(wizard.getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.UPDATE_TITLE, MESSAGE_CODE_ORANGE.NE), ex);
		} catch (Exception ex) {
			DialogMessage.openError(wizard.getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.UPDATE_TITLE, MESSAGE_CODE_ORANGE.NE), ex);
		}
	}

	protected void deleteNe(final int neId, boolean progressBar) {
		try {
			DialogProgress.run(getShell(), progressBar, new ProgressTaskIf() {
				@Override
				public Object run() throws EmpException {
					advisor.deleteNe(neId);
					ModelClient4NetworkTree.getInstance().refresh();
					return null;
				}
			});
		} catch (EmpException ex) {
			DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.DELETE_TITLE, MESSAGE_CODE_ORANGE.NE), ex);
		} catch (Exception ex) {
			DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.DELETE_TITLE, MESSAGE_CODE_ORANGE.NE), ex);
		}
	}

	protected String[] queryListImagePath() {
		return advisor.queryListImagePath("/data/image/node_icon/", new String[] { "png" });
	}

}
