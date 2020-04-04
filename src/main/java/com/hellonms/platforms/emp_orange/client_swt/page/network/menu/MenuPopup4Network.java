package com.hellonms.platforms.emp_orange.client_swt.page.network.menu;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_core.share.language.UtilLanguage;
import com.hellonms.platforms.emp_onion.client_swt.widget.dialog.DialogMessage;
import com.hellonms.platforms.emp_onion.share.model.ACCESS;
import com.hellonms.platforms.emp_orange.client_swt.ApplicationProperty;
import com.hellonms.platforms.emp_orange.client_swt.page.network.network_tree.ModelClient4NetworkTree;
import com.hellonms.platforms.emp_orange.client_swt.page.network.network_tree.ModelClient4NetworkTree.NODE;
import com.hellonms.platforms.emp_orange.share.error.ERROR_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.network.ne.Model4Ne;
import com.hellonms.platforms.emp_orange.share.model.network.ne_group.Model4NeGroup;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.Model4NeSessionIf;
import com.hellonms.platforms.emp_orange.share.model.security.operation_log.OPERATION_CODE_ORANGE;

/**
 * <p>
 * MenuPopup4Network
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class MenuPopup4Network {

	/**
	 * 컨텍스트 메뉴의 리스너 인터페이스 입니다.
	 * 
	 * @since Version 1.4.0
	 * @author Hello NMS
	 */
	public interface MenuPopup4NetworkListenerIf {

		/**
		 * @param node
		 */
		public void openNetworkView(NODE node);

		/**
		 * 현재알람 화면을 오픈합니다.
		 * 
		 * @param node
		 *            노드
		 */
		public void openAlarmActive(NODE node);

		/**
		 * NE그룹 생성 위자드를 오픈합니다.
		 * 
		 * @param parent_ne_group_id
		 *            상위 NE그룹 아이디
		 */
		public void openWizard4CreateNeGroup(int parentNeGroupId);

		/**
		 * NE그룹 등록정보 위자드를 오픈합니다.
		 * 
		 * @param neGroup
		 *            NE그룹 모델
		 */
		public void openWizard4UpdateNeGroup(Model4NeGroup model4NeGroup);

		/**
		 * @param neGroupId
		 */
		public void deleteNeGroup(int neGroupId);

		/**
		 * @param neGroupId
		 */
		public void openWizard4DiscoveryNe(int neGroupId);

		/**
		 * NE 생성 위자드를 오픈합니다.
		 * 
		 * @param ne_group_id
		 *            NE그룹 아이디
		 */
		public void openWizard4CreateNe(int neGroupId);

		/**
		 * NE 등록정보 위자드를 오픈합니다.
		 * 
		 * @param ne
		 *            NE 모델
		 */
		public void openWizard4UpdateNe(Model4Ne model4Ne);

		/**
		 * @param neId
		 */
		public void deleteNe(int neId);

		/**
		 * 아이콘의 깜빡임을 중지합니다.
		 * 
		 * @param node
		 *            노드
		 */
		public void stopIconBlink(NODE node);

	}

	/**
	 * 쉘
	 */
	protected Shell shell;

	/**
	 * 리스너
	 */
	protected MenuPopup4NetworkListenerIf listener;

	/**
	 * 선택된 노드
	 */
	protected NODE selectedNode;

	/**
	 * 메뉴 맵
	 */
	protected Map<String, Menu> menuMap = new HashMap<String, Menu>();

	/**
	 * 생성자 입니다.
	 * 
	 * @param shell
	 *            쉘
	 * @param listener
	 *            리스너
	 */
	public MenuPopup4Network(Shell shell, MenuPopup4NetworkListenerIf listener) {
		this.shell = shell;
		this.listener = listener;
	}

	/**
	 * 쉘을 반환합니다.
	 * 
	 * @return 쉘
	 */
	public Shell getShell() {
		return shell;
	}

	/**
	 * 선택된 노드를 반환합니다.
	 * 
	 * @return 노드
	 */
	public NODE getSelectedNode() {
		return selectedNode;
	}

	/**
	 * 메뉴를 반환합니다.
	 * 
	 * @param control
	 *            컨트롤
	 * @param node
	 *            노드
	 * @param network_view_background
	 *            네트워크 보기 화면여부
	 * @return 메뉴
	 */
	public Menu getMenu(Control control, NODE node, boolean network_view_background) {
		MenuPopup4Network.this.selectedNode = node;

		if (node.isNeGroup()) {
			Model4NeGroup neGroup = (Model4NeGroup) node.getValue();
			boolean isNetwork = neGroup.getNe_group_id() == Model4NeGroup.ROOT_NE_GROUP_ID;
			ACCESS access = neGroup.getAccess();
			boolean isManagement = node.isManagement();
			String key = node.getValue().getClass().getName() + "." + isNetwork + "." + access + "." + isManagement + "." + network_view_background;
			Menu menu = menuMap.get(key);
			if (menu == null) {
				menu = new Menu(control);
				createMenu4NeGroup(menu, isNetwork, access, isManagement, network_view_background);
				menuMap.put(key, menu);
			}
			return menu;
		} else if (node.isNe()) {
			Model4Ne ne = (Model4Ne) node.getValue();
			ACCESS access = ne.getAccess();
			String key = node.getValue().getClass().getName() + "." + access;
			Menu menu = menuMap.get(key);
			if (menu == null) {
				menu = new Menu(control);
				createMenu4Ne(menu, access, network_view_background);
				menuMap.put(key, menu);
			}
			return menu;
		} else {
			return null;
		}
	}

	/**
	 * NE Group 메뉴를 생성합니다.
	 * 
	 * @param menu
	 * @param isNetwork
	 *            NETWORK(Root Node) 여부
	 * @param access
	 *            접근권한
	 * @param isManagement
	 *            사용자 관리의 그룹관리 여부
	 * @param network_view_background
	 *            NetworkView 여부
	 */
	protected void createMenu4NeGroup(Menu menu, boolean isNetwork, ACCESS access, boolean isManagement, boolean network_view_background) {
		if (!network_view_background) {
			MenuItem menuItemNetworkMap = new MenuItem(menu, SWT.NONE);
			menuItemNetworkMap.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					NODE selectedNode = getSelectedNode();
					if (selectedNode != null && selectedNode.isNeGroup()) {
						listener.openNetworkView(selectedNode);
					}
				}
			});
			menuItemNetworkMap.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NETWORK_VIEW));
		}

		MenuItem menuItemAlarmActive = new MenuItem(menu, SWT.NONE);
		menuItemAlarmActive.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				NODE selectedNode = getSelectedNode();
				if (selectedNode != null && selectedNode.isNeGroup()) {
					listener.openAlarmActive(selectedNode);
				}
			}
		});
		menuItemAlarmActive.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.ALARM_ACTIVE));

		new MenuItem(menu, SWT.SEPARATOR);

		boolean isSeparator = false;

		if (isManagement && ApplicationProperty.getOperation_authority(OPERATION_CODE_ORANGE.NETWORK_NEGROUP_CREATE)) {
			isSeparator = true;
			MenuItem menuItemCreateNeGroup = new MenuItem(menu, SWT.NONE);
			menuItemCreateNeGroup.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					NODE selectedNode = getSelectedNode();
					if (selectedNode != null && selectedNode.isNeGroup()) {
						listener.openWizard4CreateNeGroup(selectedNode.getId());
					}
				}
			});
			menuItemCreateNeGroup.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.CREATE_TITLE, MESSAGE_CODE_ORANGE.NE_GROUP));
		}

		if (!isNetwork && isManagement && ApplicationProperty.getOperation_authority(OPERATION_CODE_ORANGE.NETWORK_NEGROUP_DELETE)) {
			isSeparator = true;
			MenuItem menuItemDeleteNeGroup = new MenuItem(menu, SWT.NONE);
			menuItemDeleteNeGroup.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					NODE selectedNode = getSelectedNode();
					if (selectedNode != null && selectedNode.isNeGroup()) {
						try {
							NODE[] children = ModelClient4NetworkTree.getInstance().getListChildNe(selectedNode.getId());
							if (children.length == 0) {
								if (DialogMessage.openConfirm(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.DELETE_TITLE, MESSAGE_CODE_ORANGE.NE_GROUP), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.DELETE_CONFIRM, MESSAGE_CODE_ORANGE.NE_GROUP, selectedNode.getName()))) {
									listener.deleteNeGroup(selectedNode.getId());
								}
								return;
							}
						} catch (EmpException e1) {
						}
						DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.DELETE_TITLE, MESSAGE_CODE_ORANGE.NE_GROUP), UtilLanguage.getMessage(ERROR_CODE_ORANGE.MODEL_DENYDELETE, MESSAGE_CODE_ORANGE.NE_GROUP, MESSAGE_CODE_ORANGE.CHILD_NE_EXISTS));
					}
				}
			});
			menuItemDeleteNeGroup.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.DELETE_TITLE, MESSAGE_CODE_ORANGE.NE_GROUP));
		}

		if (isSeparator) {
			new MenuItem(menu, SWT.SEPARATOR);
			isSeparator = false;
		}

		if (isManagement && ApplicationProperty.getOperation_authority(OPERATION_CODE_ORANGE.NETWORK_NE_CREATE)) {
			isSeparator = true;
			MenuItem menuItemDiscoveryNe = new MenuItem(menu, SWT.NONE);
			menuItemDiscoveryNe.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					NODE selectedNode = getSelectedNode();
					if (selectedNode != null && selectedNode.isNeGroup()) {
						listener.openWizard4DiscoveryNe(selectedNode.getId());
					}
				}
			});
			menuItemDiscoveryNe.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.DISCOVERY_NE));

			MenuItem menuItemCreateNe = new MenuItem(menu, SWT.NONE);
			menuItemCreateNe.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					NODE selectedNode = getSelectedNode();
					if (selectedNode != null && selectedNode.isNeGroup()) {
						listener.openWizard4CreateNe(selectedNode.getId());
					}
				}
			});
			menuItemCreateNe.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.CREATE_TITLE, MESSAGE_CODE_ORANGE.NE));
		}

		if (isSeparator) {
			new MenuItem(menu, SWT.SEPARATOR);
			isSeparator = false;
		}

		if (network_view_background) {
			isSeparator = true;
			MenuItem menuItemStopBlink = new MenuItem(menu, SWT.NONE);
			menuItemStopBlink.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					NODE selectedNode = getSelectedNode();
					if (selectedNode != null && selectedNode.isNeGroup()) {
						listener.stopIconBlink(selectedNode);
					}
				}
			});
			menuItemStopBlink.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.STOP_BLINK));
		}

		if (isSeparator) {
			new MenuItem(menu, SWT.SEPARATOR);
		}

		MenuItem menuItemProperty = new MenuItem(menu, SWT.NONE);
		menuItemProperty.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				NODE selectedNode = getSelectedNode();
				if (selectedNode != null && selectedNode.isNeGroup()) {
					listener.openWizard4UpdateNeGroup((Model4NeGroup) selectedNode.getValue());
				}
			}
		});
		menuItemProperty.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.PROPERTY_TITLE, MESSAGE_CODE_ORANGE.NE_GROUP));
	}

	/**
	 * NE 메뉴를 생성합니다.
	 * 
	 * @param menu
	 *            메뉴
	 * @param ne_define_id
	 *            NE 정의 아이디
	 * @param access
	 *            접근권한
	 */
	protected void createMenu4Ne(Menu menu, ACCESS access, boolean network_view_background) {
		boolean isSeparator = false;

		if (!network_view_background) {
			MenuItem menuItemNetworkMap = new MenuItem(menu, SWT.NONE);
			menuItemNetworkMap.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					NODE selectedNode = getSelectedNode();
					if (selectedNode != null && selectedNode.isNe()) {
						listener.openNetworkView(selectedNode);
					}
				}
			});
			menuItemNetworkMap.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NE_DETAIL_VIEW));
		}

		MenuItem menuItemAlarmActive = new MenuItem(menu, SWT.NONE);
		menuItemAlarmActive.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				NODE selectedNode = getSelectedNode();
				if (selectedNode != null && selectedNode.isNe()) {
					listener.openAlarmActive(selectedNode);
				}
			}
		});
		menuItemAlarmActive.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.ALARM_ACTIVE));

		isSeparator = true;

		if (access.isDelete() && ApplicationProperty.getOperation_authority(OPERATION_CODE_ORANGE.NETWORK_NE_DELETE)) {
			if (isSeparator) {
				new MenuItem(menu, SWT.SEPARATOR);
				isSeparator = false;
			}

			isSeparator = true;
			MenuItem menuItemDeleteNe = new MenuItem(menu, SWT.NONE);
			menuItemDeleteNe.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					NODE selectedNode = getSelectedNode();
					if (selectedNode != null && selectedNode.isNe()) {
						if (DialogMessage.openConfirm(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.DELETE_TITLE, MESSAGE_CODE_ORANGE.NE), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.DELETE_CONFIRM, MESSAGE_CODE_ORANGE.NE, selectedNode.getName()))) {
							listener.deleteNe(selectedNode.getId());
						}
					}
				}
			});
			menuItemDeleteNe.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.DELETE_TITLE, MESSAGE_CODE_ORANGE.NE));
		}

		if (0 < ((Model4Ne) selectedNode.getValue()).getNeSessions().length && 0 <= System.getProperty("os.name").toLowerCase().indexOf("window")) {
			if (isSeparator) {
				new MenuItem(menu, SWT.SEPARATOR);
				isSeparator = false;
			}

			isSeparator = true;
			MenuItem menuItemPing = new MenuItem(menu, SWT.NONE);
			menuItemPing.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					NODE selectedNode = getSelectedNode();
					if (selectedNode != null && selectedNode.isNe()) {
						if (0 <= System.getProperty("os.name").toLowerCase().indexOf("window")) {
							try {
								Model4NeSessionIf[] neSessions = ((Model4Ne) selectedNode.getValue()).getNeSessions();
								if (0 < neSessions.length) {
									Runtime.getRuntime().exec("cmd /c start ping -t " + neSessions[0].getHost());
								}
							} catch (Throwable ex) {
								DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.ICMP), ex);
							}
						}
					}
				}
			});
			menuItemPing.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.ICMP));

			MenuItem menuItemTelnet = new MenuItem(menu, SWT.NONE);
			menuItemTelnet.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					NODE selectedNode = getSelectedNode();
					if (selectedNode != null && selectedNode.isNe()) {
						if (0 <= System.getProperty("os.name").toLowerCase().indexOf("window")) {
							try {
								Model4NeSessionIf[] neSessions = ((Model4Ne) selectedNode.getValue()).getNeSessions();
								if (0 < neSessions.length) {
									Runtime.getRuntime().exec("cmd /c start telnet " + neSessions[0].getHost());
								}
							} catch (Throwable ex) {
								DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.TELNET), ex);
							}
						}
					}
				}
			});
			menuItemTelnet.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.TELNET));
		}

		if (access.isUpdate()) {
			if (isSeparator) {
				new MenuItem(menu, SWT.SEPARATOR);
			}

			MenuItem menuItemProperty = new MenuItem(menu, SWT.NONE);
			menuItemProperty.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					NODE selectedNode = getSelectedNode();
					if (selectedNode != null && selectedNode.isNe()) {
						listener.openWizard4UpdateNe((Model4Ne) selectedNode.getValue());
					}
				}
			});
			menuItemProperty.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.PROPERTY_TITLE, MESSAGE_CODE_ORANGE.NE));
		}
	}
}
