package com.hellonms.platforms.emp_orange.client_swt.page.network.network_view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;

import com.hellonms.platforms.emp_core.share.model.ModelIf;
import com.hellonms.platforms.emp_orange.client_swt.page.network.menu.MenuPopup4Network;
import com.hellonms.platforms.emp_orange.client_swt.page.network.menu.MenuPopup4Network.MenuPopup4NetworkListenerIf;
import com.hellonms.platforms.emp_orange.client_swt.page.network.network_tree.ModelClient4NetworkTree.NODE;
import com.hellonms.platforms.emp_orange.client_swt.widget.page.PageNode.ToolbarItemIf;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE;
import com.hellonms.platforms.emp_orange.share.model.network.ne.Model4Ne;
import com.hellonms.platforms.emp_orange.share.model.network.ne_group.Model4NeGroup;

/**
 * <p>
 * Panel4NetworkViewAt
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public abstract class Panel4NetworkViewAt extends Composite {

	public interface Panel4NetworkViewListenerIf {

		public void openNetworkView(NODE node);

		public void openAlarmActive(NODE node);

		public void selectViewNode(NODE node);

		public void openWizard4CreateNeGroup(int parentNeGroupId);

		public void openWizard4UpdateNeGroup(Model4NeGroup model4NeGroup);

		public void deleteNeGroup(int neGroupId);

		public void openWizard4DiscoveryNe(int neGroupId);

		public void openWizard4CreateNe(int neGroupId);

		public void openWizard4UpdateNe(Model4Ne model4Ne);

		public void deleteNe(int neId);

		public void updateMapLocation(ModelIf[] values);

		public String[] queryOrderNetworkViewNe(EMP_MODEL_NE ne_def);

		public void updateOrderNetworkViewNe(EMP_MODEL_NE ne_def, String[] order);

	}

	public class Panel4NetworkViewChildListener implements MenuPopup4NetworkListenerIf {

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
			listener.openWizard4CreateNeGroup(parentNeGroupId);
		}

		@Override
		public void openWizard4UpdateNeGroup(Model4NeGroup model4NeGroup) {
			listener.openWizard4UpdateNeGroup(model4NeGroup);
		}

		@Override
		public void deleteNeGroup(int neGroupId) {
			listener.deleteNeGroup(neGroupId);
		}

		@Override
		public void openWizard4DiscoveryNe(int neGroupId) {
			listener.openWizard4DiscoveryNe(neGroupId);
		}

		@Override
		public void openWizard4CreateNe(int neGroupId) {
			listener.openWizard4CreateNe(neGroupId);
		}

		@Override
		public void openWizard4UpdateNe(Model4Ne model4Ne) {
			listener.openWizard4UpdateNe(model4Ne);
		}

		@Override
		public void deleteNe(int neId) {
			listener.deleteNe(neId);
		}

		@Override
		public void stopIconBlink(NODE node) {
			node.stopIconBlink(true);
		}

	}

	protected Panel4NetworkViewListenerIf listener;

	/**
	 * 스크롤 컴포지트
	 */
	protected ScrolledComposite scrolledComposite;

	/**
	 * 컨텐츠
	 */
	private Control content;

	protected MenuPopup4Network menuPopup4Network;

	/**
	 * 생성자 입니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 * @param style
	 *            스타일
	 */
	public Panel4NetworkViewAt(Composite parent, int style, Panel4NetworkViewListenerIf listener) {
		super(parent, style);

		this.listener = listener;

		createGUI();
	}

	@Override
	protected void checkSubclass() {
	}

	@Override
	public boolean forceFocus() {
		return content.forceFocus();
	}

	/**
	 * GUI를 생성합니다.
	 */
	protected void createGUI() {
		setLayout(new FormLayout());

		scrolledComposite = new ScrolledComposite(this, SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite.getHorizontalBar().setIncrement(8);
		scrolledComposite.getVerticalBar().setIncrement(8);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);

		FormData fd_scrolledComposite = new FormData();
		fd_scrolledComposite.top = new FormAttachment(0);
		fd_scrolledComposite.left = new FormAttachment(0);
		fd_scrolledComposite.right = new FormAttachment(100);
		fd_scrolledComposite.bottom = new FormAttachment(100);
		scrolledComposite.setLayoutData(fd_scrolledComposite);

		content = createContent(scrolledComposite);
		content.addListener(SWT.MouseDown, new Listener() {
			@Override
			public void handleEvent(Event e) {
				content.forceFocus();
			}
		});

		scrolledComposite.setContent(content);

		menuPopup4Network = createMenuPopup4Network(getShell());

		addMenuPopup4Network(content);
	}

	/**
	 * 스크롤의 최소 사이즈를 설정합니다.
	 * 
	 * @param size
	 *            최소 사이즈
	 */
	protected void computeScroll(Point size) {
		scrolledComposite.setMinSize(size);
	}

	/**
	 * 컨텐츠를 생성합니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 * @return 컨트롤
	 */
	abstract protected Control createContent(Composite parent);

	/**
	 * 네트워크관리 메뉴를 생성합니다.
	 * 
	 * @param shell
	 *            쉘
	 * @return 컨텍스트 메뉴
	 */
	protected MenuPopup4Network createMenuPopup4Network(Shell shell) {
		return new MenuPopup4Network(shell, new Panel4NetworkViewChildListener());
	}

	/**
	 * 컨텍스트 메뉴를 생성합니다.
	 * 
	 * @param control
	 *            컨트롤
	 */
	protected void addMenuPopup4Network(final Control control) {
		control.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				NODE selectedNode = getSelectedNode();
				if (menuPopup4Network != null && selectedNode != null && e.button == 3 && isShowPopupMenu()) {
					Menu menu = menuPopup4Network.getMenu(control, selectedNode, selectedNode == getNode());
					if (menu != null) {
						menu.setVisible(true);
					}
				}
			}
		});
	}

	/**
	 * 노드를 반환합니다.
	 * 
	 * @return 노드
	 */
	abstract protected NODE getNode();

	/**
	 * 선택된 노드를 반환합니다.
	 * 
	 * @return 노드
	 */
	abstract protected NODE getSelectedNode();

	/**
	 * 팝업 메뉴의 보이기 여부
	 * 
	 * @return 보이기 여부
	 */
	protected boolean isShowPopupMenu() {
		return true;
	}

	/**
	 * 화면에 출력합니다.
	 * 
	 * @param progressBar
	 *            프로그래스바 실행여부
	 */
	abstract public void display(boolean progressBar);

	/**
	 * 화면에 출력합니다.
	 * 
	 * @param node
	 *            노드
	 * @param progressBar
	 *            프로그래스바 실행여부
	 */
	abstract public void display(NODE node, boolean progressBar);

	/**
	 * 화면에 출력합니다.
	 * 
	 * @param node
	 *            노드
	 * @param object
	 *            객체
	 */
	abstract public void display(NODE node, Object object);

	/**
	 * 주기적으로 모니터 합니다.
	 */
	abstract public void monitor();

	/**
	 * 주기적으로 깜빡입니다.
	 * 
	 * @param blink
	 *            깜빡임 여부
	 */
	abstract public void blink(boolean blink);

	protected ToolbarItemIf[] getToolbarItems() {
		return new ToolbarItemIf[] {};
	}

}
