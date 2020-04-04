package com.hellonms.platforms.emp_orange.client_swt.page.network.network_view;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_core.share.language.UtilLanguage;
import com.hellonms.platforms.emp_core.share.model.ModelIf;
import com.hellonms.platforms.emp_onion.client_swt.widget.dialog.DialogMessage;
import com.hellonms.platforms.emp_onion.client_swt.widget.dialog.DialogProgress;
import com.hellonms.platforms.emp_onion.client_swt.widget.dialog.DialogProgress.ProgressTaskIf;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PageBlinkIf;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PageMonitorIf;
import com.hellonms.platforms.emp_onion.theme.ThemeBuilder4Onion.COLOR_ONION;
import com.hellonms.platforms.emp_onion.theme.ThemeFactory;
import com.hellonms.platforms.emp_orange.client_swt.ApplicationProperty;
import com.hellonms.platforms.emp_orange.client_swt.model_display.ModelDisplay4Ne;
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
import com.hellonms.platforms.emp_orange.client_swt.page.network.network_tree.ModelClient4NetworkTree;
import com.hellonms.platforms.emp_orange.client_swt.page.network.network_tree.ModelClient4NetworkTree.ModelClient4NetworkTreeListenerIf;
import com.hellonms.platforms.emp_orange.client_swt.page.network.network_tree.ModelClient4NetworkTree.NODE;
import com.hellonms.platforms.emp_orange.client_swt.page.network.network_view.ne.Panel4NetworkViewNe;
import com.hellonms.platforms.emp_orange.client_swt.page.network.network_view.ne.Panel4NetworkViewNe.Panel4NetworkViewNeListenerIf;
import com.hellonms.platforms.emp_orange.client_swt.page.network.network_view.ne.Panel4NetworkViewNeChart;
import com.hellonms.platforms.emp_orange.client_swt.page.network.network_view.ne_group.Panel4NetworkViewNeGroup;
import com.hellonms.platforms.emp_orange.client_swt.page.network.network_view.ne_group.Panel4NetworkViewNeGroup.Panel4NetworkViewNeGroupListenerIf;
import com.hellonms.platforms.emp_orange.client_swt.page.network.network_view.nms.Panel4NetworkViewNMS;
import com.hellonms.platforms.emp_orange.client_swt.widget.page.PageNode;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE;
import com.hellonms.platforms.emp_orange.share.model.network.link.Model4NetworkLink;
import com.hellonms.platforms.emp_orange.share.model.network.ne.Model4Ne;
import com.hellonms.platforms.emp_orange.share.model.network.ne.Model4ResourceNMS;
import com.hellonms.platforms.emp_orange.share.model.network.ne_group.Model4NeGroup;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.Model4NeSessionDiscoveryFilterIf;

/**
 * <p>
 * Page4NetworkView
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class Page4NetworkView extends PageNode implements ModelClient4NetworkTreeListenerIf, PageBlinkIf, PageMonitorIf {

	public interface Page4NetworkViewListenerIf extends //
			PageNodeListenerIf {

		public void openNetworkView(NODE node);

		public void openAlarmActive(NODE node);

	}

	/**
	 * 네트워크 보기 페이지의 하위 클래스의 리스너 인터페이스를 구현한 리스너 클래스 입니다.
	 * 
	 * @since Version 1.4.0
	 * @author Hello NMS
	 */
	public class Page4NetworkViewChildListener implements //
			Panel4NetworkViewNeGroupListenerIf, //
			Panel4NetworkViewNeListenerIf,//
			Wizard4CreateNeGroupListenerIf, //
			Wizard4UpdateNeGroupListenerIf, //
			Wizard4DiscoveryNeListenerIf, //
			Wizard4CreateNeListenerIf, //
			Wizard4UpdateNeListenerIf {

		@Override
		public void selectViewNode(NODE node) {
			Page4NetworkView.this.selectViewNode(node);
		}

		@Override
		public void openNetworkView(NODE node) {
			Page4NetworkView.this.openNetworkView(node);
		}

		@Override
		public void openAlarmActive(NODE node) {
			Page4NetworkView.this.openAlarmActive(node);
		}

		@Override
		public void openWizard4CreateNeGroup(int parentNeGroupId) {
			Page4NetworkView.this.openWizard4CreateNeGroup(parentNeGroupId, this);
		}

		@Override
		public void createNeGroup(Wizard4CreateNeGroup wizard, Model4NeGroup model4NeGroup) {
			Page4NetworkView.this.createNeGroup(wizard, model4NeGroup, true);
		}

		@Override
		public void openWizard4UpdateNeGroup(Model4NeGroup model4NeGroup) {
			Wizard4UpdateNeGroup.open(getShell(), model4NeGroup, queryListImagePath(), this);
		}

		@Override
		public void updateNeGroup(Wizard4UpdateNeGroup wizard, Model4NeGroup model4NeGroup) {
			Page4NetworkView.this.updateNeGroup(wizard, model4NeGroup, true);
		}

		@Override
		public void deleteNeGroup(int neGroupId) {
			Page4NetworkView.this.deleteNeGroup(neGroupId, true);
		}

		@Override
		public void openWizard4DiscoveryNe(int neGroupId) {
			Page4NetworkView.this.openWizard4DiscoveryNe(neGroupId, this);
		}

		@Override
		public void discoveryListNe(Wizard4DiscoveryNe wizard, Model4NeSessionDiscoveryFilterIf[] model4NeSessionDiscoveryFilters) {
			Page4NetworkView.this.discoveryListNe(wizard, model4NeSessionDiscoveryFilters);
		}

		@Override
		public void createListNe(Wizard4DiscoveryNe wizard, Model4Ne[] model4Nes) {
			Page4NetworkView.this.createListNe(wizard, model4Nes);
		}

		@Override
		public void openWizard4CreateNe(int neGroupId) {
			Page4NetworkView.this.openWizard4CreateNe(neGroupId, this);
		}

		@Override
		public void createNe(Wizard4CreateNe wizard, Model4Ne model4Ne) {
			Page4NetworkView.this.createNe(wizard, model4Ne, true);
		}

		@Override
		public void openWizard4UpdateNe(Model4Ne model4Ne) {
			Wizard4UpdateNe.open(getShell(), model4Ne, queryListImagePath(), this);
		}

		@Override
		public void updateNe(Wizard4UpdateNe wizard, Model4Ne model4Ne) {
			Page4NetworkView.this.updateNe(wizard, model4Ne, true);
		}

		@Override
		public void deleteNe(int neId) {
			Page4NetworkView.this.deleteNe(neId, true);
		}

		@Override
		public void updateMapLocation(ModelIf[] values) {
			Page4NetworkView.this.updateMapLocation(values, true);
		}

		@Override
		public void createNetworkLink(Model4NetworkLink networkLink) {
			Page4NetworkView.this.createNetworkLink(networkLink, true);
		}

		@Override
		public void deleteNetworkLink(int network_link_id) {
			Page4NetworkView.this.deleteNetworkLink(network_link_id, true);
		}

		@Override
		public void queryNetworkViewNe(int ne_id, boolean progressBar) {
			Page4NetworkView.this.queryNetworkViewNe(ne_id, progressBar);
		}

		@Override
		public String[] queryOrderNetworkViewNe(EMP_MODEL_NE ne_def) {
			return Page4NetworkView.this.queryOrderNetworkViewNe(ne_def, true);
		}

		@Override
		public void updateOrderNetworkViewNe(EMP_MODEL_NE ne_def, String[] order) {
			Page4NetworkView.this.updateOrderNetworkViewNe(ne_def, order, true);
		}

	}

	protected Page4NetworkViewListenerIf listener;

	/**
	 * 스택 레이아웃
	 */
	protected StackLayout stackLayout;

	/**
	 * 어드바이저
	 */
	protected Page4NetworkViewAdvisor advisor;

	/**
	 * NE그룹 아이콘 판넬
	 */
	protected Panel4NetworkViewAt panel4NetworkViewNeGroup;

	protected Map<Integer, Panel4NetworkViewAt> panel4NetworkViewNeMap = new HashMap<Integer, Panel4NetworkViewAt>();

	/**
	 * 생성자 입니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 * @param style
	 *            스타일
	 * @param listener
	 *            리스너
	 * @param view
	 *            뷰
	 */
	public Page4NetworkView(Composite parent, int style, Page4NetworkViewListenerIf listener) {
		super(parent, style, listener);

		this.listener = listener;

		advisor = createPage4NetworkViewAdvisor();

		try {
			display(ModelClient4NetworkTree.getInstance().getNeGroup(Model4NeGroup.ROOT_NE_GROUP_ID), true);
		} catch (EmpException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		ModelClient4NetworkTree.getInstance().addModelClient4NetworkTreeListener(Page4NetworkView.this);
		ApplicationProperty.addPageBlink(Page4NetworkView.this);
		ApplicationProperty.addPageMonitor(Page4NetworkView.this);
		addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				ModelClient4NetworkTree.getInstance().removeModelClient4NetworkTreeListener(Page4NetworkView.this);
				ApplicationProperty.removePageBlink(Page4NetworkView.this);
				ApplicationProperty.removePageMonitor(Page4NetworkView.this);
			}
		});
	}

	@Override
	protected Composite createContent(Composite parent) {
		Composite contentComposite = new Composite(parent, SWT.NONE);
		stackLayout = new StackLayout();
		contentComposite.setLayout(stackLayout);
		contentComposite.setBackgroundMode(SWT.INHERIT_DEFAULT);
		contentComposite.setBackground(ThemeFactory.getColor(COLOR_ONION.PAGE_BG));
		return contentComposite;
	}

	/**
	 * 어드바이저를 생성합니다.
	 * 
	 * @return 어드바이저
	 */
	protected Page4NetworkViewAdvisor createPage4NetworkViewAdvisor() {
		return new Page4NetworkViewAdvisor();
	}

	@Override
	public boolean forceFocus() {
		if (stackLayout.topControl == null) {
			return false;
		} else {
			return stackLayout.topControl.forceFocus();
		}
	}

	@Override
	public void display(boolean progressBar) {
		Control topControl = stackLayout.topControl;
		if (topControl instanceof Panel4NetworkViewAt) {
			Panel4NetworkViewAt panel4NetworkView = (Panel4NetworkViewAt) topControl;
			NODE validNode = ModelClient4NetworkTree.getInstance().getValidNode(node);

			if (node == validNode) {
				panel4NetworkView.display(progressBar);
			} else {
				display(validNode, progressBar);
			}
		}
	}

	/**
	 * 화면에 네트워크 상태를 출력합니다.
	 * 
	 * @param node
	 *            노드
	 * @param progressBar
	 *            프로그래스바 실행여부
	 */
	public void display(NODE node, boolean progressBar) {
		node = ModelClient4NetworkTree.getInstance().getValidNode(node);
		if (this.node != node) {
			// content.getParent().getParent().layout();
		}
		super.display(node);

		Panel4NetworkViewAt panel4NetworkView = getPanel4Display(node, content);
		if (panel4NetworkView != null) {
			stackLayout.topControl = (Control) panel4NetworkView;
			node.stopIconBlink(false);
			initToolbar();
			panel4NetworkView.display(node, progressBar);
			content.layout();
		}
	}

	@Override
	public void display(NODE node) {
		display(node, true);
	}

	@Override
	public void refresh() {
		monitor();
	}

	@Override
	public void blink(boolean blink) {
		Control topControl = stackLayout.topControl;
		if (topControl instanceof Panel4NetworkViewAt) {
			((Panel4NetworkViewAt) topControl).blink(blink);
		}
	}

	@Override
	public void monitor() {
		if (!isDisposed()) {
			Control topControl = stackLayout.topControl;
			if (topControl instanceof Panel4NetworkViewAt) {
				Panel4NetworkViewAt panel4NetworkMap = (Panel4NetworkViewAt) topControl;
				NODE validNode = ModelClient4NetworkTree.getInstance().getValidNode(node);

				if (node == validNode) {
					panel4NetworkMap.monitor();
				} else {
					if (Thread.currentThread() == getDisplay().getThread()) {
						display(true);
					} else {
						getDisplay().syncExec(new Runnable() {
							@Override
							public void run() {
								display(false);
							}
						});
					}
				}
			}
		}
	}

	/**
	 * 최상위 컨트롤을 반환합니다.
	 * 
	 * @return 네트워크 보기 판넬
	 */
	protected Panel4NetworkViewAt getTopControl() {
		Control topControl = stackLayout.topControl;
		if (topControl instanceof Panel4NetworkViewAt) {
			return (Panel4NetworkViewAt) topControl;
		} else {
			return null;
		}
	}

	/**
	 * 화면에 출력할 네트워크 보기 판넬을 반환합니다.
	 * 
	 * @param node
	 *            노드
	 * @param parent
	 *            부모 컴포지트
	 * @return 네트워크 보기 판넬
	 */
	protected Panel4NetworkViewAt getPanel4Display(NODE node, Composite parent) {
		if (node.isNeGroup()) {
			if (panel4NetworkViewNeGroup == null) {
				panel4NetworkViewNeGroup = createPanel4NetworkViewNeGroup(parent, SWT.NONE, new Page4NetworkViewChildListener());
			}
			return panel4NetworkViewNeGroup;
		} else if (node.isNe()) {
			EMP_MODEL_NE ne_def = EMP_MODEL.current().getNe(((Model4Ne) node.getValue()).getNe_code());
			Panel4NetworkViewAt panel4NetworkViewNe = panel4NetworkViewNeMap.get(ne_def == null ? 0 : ne_def.getCode());
			if (panel4NetworkViewNe == null) {
				panel4NetworkViewNe = createPanel4NetworkViewNe(parent, SWT.NONE, new Page4NetworkViewChildListener(), ne_def);
				panel4NetworkViewNeMap.put(ne_def == null ? 0 : ne_def.getCode(), panel4NetworkViewNe);
			}
			return panel4NetworkViewNe;
		} else {
			return null;
		}
	}

	@Override
	protected ToolbarItemIf[] getToolbarItems() {
		if (node.isNeGroup()) {
			if (panel4NetworkViewNeGroup != null) {
				return panel4NetworkViewNeGroup.getToolbarItems();
			}
		} else if (node.isNe()) {
			int ne_code = ((Model4Ne) node.getValue()).getNe_code();
			Panel4NetworkViewAt panel4NetworkViewNe = panel4NetworkViewNeMap.get(ne_code);
			if (panel4NetworkViewNe != null) {
				return panel4NetworkViewNe.getToolbarItems();
			}
		}
		return new ToolbarItemIf[] {};
	}

	/**
	 * NE그룹 아이콘 판넬을 생성합니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 * @param style
	 *            스타일
	 * @param listener
	 *            리스너
	 * @return NE그룹 아이콘 판넬
	 */
	protected Panel4NetworkViewAt createPanel4NetworkViewNeGroup(Composite parent, int style, Panel4NetworkViewNeGroupListenerIf listener) {
		return new Panel4NetworkViewNeGroup(parent, style, listener);
	}

	/**
	 * NE 판넬을 생성합니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 * @param style
	 *            스타일
	 * @param listener
	 *            리스너
	 * @param ne_def
	 * @return NE 판넬
	 */
	protected Panel4NetworkViewAt createPanel4NetworkViewNe(Composite parent, int style, Panel4NetworkViewNeListenerIf listener, EMP_MODEL_NE ne_def) {
		if (ne_def != null && ne_def.isNMS()) {
			return new Panel4NetworkViewNMS(parent, style, listener);
		} else {
			return new Panel4NetworkViewNe(parent, style, listener);
		}
	}

	protected void selectViewNode(NODE node) {
		listener.selectViewNode(node);
	}

	protected void openNetworkView(NODE node) {
		listener.openNetworkView(node);
	}

	protected void openAlarmActive(NODE node) {
		listener.openAlarmActive(node);
	}

	protected void openWizard4CreateNeGroup(int parentNeGroupId, Wizard4CreateNeGroupListenerIf listener) {
		Model4NeGroup model4NeGroup = new Model4NeGroup();
		model4NeGroup.setParent_ne_group_id(parentNeGroupId);
		Wizard4CreateNeGroup.open(getShell(), model4NeGroup, queryListImagePath(), listener);
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
			Wizard4DiscoveryNe.close();
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
			DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.CREATE_TITLE, MESSAGE_CODE_ORANGE.NE), ex);
		} catch (Throwable ex) {
			DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.CREATE_TITLE, MESSAGE_CODE_ORANGE.NE), ex);
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

	protected void updateMapLocation(final ModelIf[] values, boolean progressBar) {
		try {
			DialogProgress.run(getShell(), progressBar, new ProgressTaskIf() {
				@Override
				public Object run() throws EmpException {
					advisor.updateMapLocation(values);
					ModelClient4NetworkTree.getInstance().refresh();
					return null;
				}
			});
		} catch (EmpException ex) {
			DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NETWORK_VIEW), ex);
		} catch (Exception ex) {
			DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NETWORK_VIEW), ex);
		}
	}

	protected void createNetworkLink(final Model4NetworkLink network_link, boolean progressBar) {
		try {
			DialogProgress.run(getShell(), progressBar, new ProgressTaskIf() {
				@Override
				public Object run() throws EmpException {
					advisor.createNetworkLink(network_link);
					ModelClient4NetworkTree.getInstance().refresh();
					return null;
				}
			});
		} catch (EmpException ex) {
			DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NETWORK_VIEW), ex);
		} catch (Exception ex) {
			DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NETWORK_VIEW), ex);
		}
	}

	protected void deleteNetworkLink(final int network_link_id, boolean progressBar) {
		try {
			DialogProgress.run(getShell(), progressBar, new ProgressTaskIf() {
				@Override
				public Object run() throws EmpException {
					advisor.deleteNetworkLink(network_link_id);
					ModelClient4NetworkTree.getInstance().refresh();
					return null;
				}
			});
		} catch (EmpException ex) {
			DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NETWORK_VIEW), ex);
		} catch (Exception ex) {
			DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NETWORK_VIEW), ex);
		}
	}

	protected void queryNetworkViewNe(final int ne_id, boolean progressBar) {
		try {
			Shell shell = null;
			if (getDisplay().getThread() == Thread.currentThread()) {
				shell = getShell();
			}

			NODE node = ModelClient4NetworkTree.getInstance().getNe(ne_id);
			if (((Model4Ne) node.getValue()).isNMS()) {
				Model4ResourceNMS[] model = (Model4ResourceNMS[]) DialogProgress.run(shell, progressBar, new ProgressTaskIf() {
					@Override
					public Object run() throws EmpException {
						return advisor.queryListResourceNMS(!progressBar);
					}
				});
				getDisplay().syncExec(new Runnable() {
					@Override
					public void run() {
						Panel4NetworkViewAt panel4NetworkView = getPanel4Display(node, Page4NetworkView.this);
						panel4NetworkView.display(node, model);
					}
				});
			} else {
				ModelDisplay4Ne model = (ModelDisplay4Ne) DialogProgress.run(shell, progressBar, new ProgressTaskIf() {
					@Override
					public Object run() throws EmpException {
						return advisor.queryNetworkViewNe(ne_id, Panel4NetworkViewNeChart.getNeStatisticsIndexMap(), !progressBar);
					}
				});
				getDisplay().syncExec(new Runnable() {
					@Override
					public void run() {
						Panel4NetworkViewAt panel4NetworkView = getPanel4Display(node, Page4NetworkView.this);
						panel4NetworkView.display(node, model);
					}
				});
			}
		} catch (EmpException ex) {
			getDisplay().syncExec(new Runnable() {
				@Override
				public void run() {
					DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NETWORK_VIEW), ex);
				}
			});
		} catch (Exception ex) {
			getDisplay().syncExec(new Runnable() {
				@Override
				public void run() {
					DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NETWORK_VIEW), ex);
				}
			});
		}
	}

	protected String[] queryOrderNetworkViewNe(EMP_MODEL_NE ne_def, boolean progressBar) {
		String[] order = {};
		try {
			order = (String[]) DialogProgress.run(getShell(), progressBar, new ProgressTaskIf() {
				@Override
				public Object run() throws EmpException {
					return advisor.queryOrderNetworkViewNe(ne_def);
				}
			});
		} catch (EmpException ex) {
			getDisplay().syncExec(new Runnable() {
				@Override
				public void run() {
					DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NETWORK_VIEW), ex);
				}
			});
		} catch (Exception ex) {
			getDisplay().syncExec(new Runnable() {
				@Override
				public void run() {
					DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NETWORK_VIEW), ex);
				}
			});
		}
		return order;
	}

	protected void updateOrderNetworkViewNe(final EMP_MODEL_NE ne_def, final String[] order, boolean progressBar) {
		try {
			DialogProgress.run(getShell(), progressBar, new ProgressTaskIf() {
				@Override
				public Object run() throws EmpException {
					advisor.updateOrderNetworkViewNe(ne_def, order);
					return null;
				}
			});
		} catch (EmpException ex) {
			getDisplay().syncExec(new Runnable() {
				@Override
				public void run() {
					DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NETWORK_VIEW), ex);
				}
			});
		} catch (Exception ex) {
			getDisplay().syncExec(new Runnable() {
				@Override
				public void run() {
					DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NETWORK_VIEW), ex);
				}
			});
		}
	}

	protected String[] queryListImagePath() {
		return advisor.queryListImagePath("/data/image/node_icon/", new String[] { "png" });
	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if (visible) {
			ApplicationProperty.addPageBlink(Page4NetworkView.this);
			ApplicationProperty.addPageMonitor(Page4NetworkView.this);
		} else {
			ApplicationProperty.removePageBlink(Page4NetworkView.this);
			ApplicationProperty.removePageMonitor(Page4NetworkView.this);
		}
	}

}
