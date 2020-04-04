package com.hellonms.platforms.emp_orange.client_swt;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Display;

import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_core.share.language.LANGUAGE;
import com.hellonms.platforms.emp_core.share.language.UtilLanguage;
import com.hellonms.platforms.emp_core.share.operation.OPERATION_CODE;
import com.hellonms.platforms.emp_onion.client_swt.data.DataFactory;
import com.hellonms.platforms.emp_onion.client_swt.util.location.UtilLocation;
import com.hellonms.platforms.emp_onion.client_swt.widget.dialog.DialogMessage;
import com.hellonms.platforms.emp_onion.client_swt.widget.dialog.DialogProgress;
import com.hellonms.platforms.emp_onion.client_swt.widget.dialog.DialogProgress.ProgressTaskIf;
import com.hellonms.platforms.emp_onion.client_swt.widget.menu.MenuStack;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PageBlinkIf;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PageMonitorIf;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PagePreference;
import com.hellonms.platforms.emp_onion.share.model.help.about.Model4About;
import com.hellonms.platforms.emp_onion.theme.ThemeBuilder4Onion;
import com.hellonms.platforms.emp_onion.theme.ThemeFactory;
import com.hellonms.platforms.emp_orange.client_swt.page.DataBuilder4Orange;
import com.hellonms.platforms.emp_orange.client_swt.page.PAGE_ORANGE;
import com.hellonms.platforms.emp_orange.client_swt.page.PanelBuilder4Orange;
import com.hellonms.platforms.emp_orange.client_swt.page.PanelFactory;
import com.hellonms.platforms.emp_orange.client_swt.page.emp_model.Shell4Model;
import com.hellonms.platforms.emp_orange.client_swt.page.help.about.Dialog4About;
import com.hellonms.platforms.emp_orange.client_swt.page.help.environment.db_backup.PagePreference4DbBackup;
import com.hellonms.platforms.emp_orange.client_swt.page.network.network_tree.ModelClient4NetworkTree;
import com.hellonms.platforms.emp_orange.client_swt.page.network.network_tree.ModelClient4NetworkTree.NODE;
import com.hellonms.platforms.emp_orange.client_swt.page.toolbar.Page4Toolbar.Page4ToolbarListenerIf;
import com.hellonms.platforms.emp_orange.client_swt.servlet.Invoker4OrangeClientSwtIf;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.security.operation_log.OPERATION_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.security.user_session.Model4UserSession;
import com.hellonms.platforms.emp_orange.theme.ThemeBuilder4Orange;
import com.hellonms.platforms.emp_orange.theme.ThemeBuilder4Orange.IMAGE_ORANGE;
import com.hellonms.platforms.emp_util.date.UtilDate;

/**
 * <p>
 * ApplicationProperty
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class ApplicationProperty {

	private static String emp_orange_url = "http://127.0.0.1:10080/client_swt";

	private static String emp_orange_server = "127.0.0.1";

	private static String emp_orange_help = "http://localhost/help/index.jsp";

	private static Model4UserSession model4UserSession;

	private static long time_diff = 0L;

	private static boolean discovery_ne = true;

	private static boolean search_ne = true;

	private static boolean copy_ne = true;

	private static boolean email_status = false;

	private static boolean sms_status = false;

	private static boolean ne_group_management = false;

	private static String emp_home = "/";

	private static ReentrantLock lock = new ReentrantLock();

	private static PageBlinkIf[] pageBlinks = {};

	private static PageMonitorIf[] pageMonitors = {};

	protected Model4About about;

	protected Display display;

	protected ApplicationAdvisor advisor;

	/**
	 * @return
	 */
	public static String getEmp_orange_url() {
		return emp_orange_url;
	}

	/**
	 * 서버 URL을 설정합니다.
	 * 
	 * @param emp_orange_url
	 *            서버 URL
	 */
	public static void setEmp_orange_url(String emp_orange_url) {
		ApplicationProperty.emp_orange_url = emp_orange_url;
	}

	/**
	 * 서버 주소를 반환합니다.
	 * 
	 * @return 서버 주소
	 */
	public static String getEmp_orange_server() {
		return emp_orange_server;
	}

	/**
	 * 서버 주소를 설정합니다.
	 * 
	 * @param emp_orange_server
	 *            서버 주소
	 */
	public static void setEmp_orange_server(String emp_orange_server) {
		ApplicationProperty.emp_orange_server = emp_orange_server;
	}

	/**
	 * 도움말 URL을 반환합니다.
	 * 
	 * @return 도움말 URL
	 */
	public static String getEmp_orange_help() {
		return emp_orange_help;
	}

	/**
	 * 도움말 URL을 설정합니다.
	 * 
	 * @param emp_orange_help
	 *            도움말 URL
	 */
	public static void setEmp_orange_help(String emp_orange_help) {
		ApplicationProperty.emp_orange_help = emp_orange_help;
	}

	/**
	 * 사용자 세션을 반환합니다.
	 * 
	 * @return 사용자 세션 모델
	 */
	public static Model4UserSession getUserSession() {
		return model4UserSession;
	}

	/**
	 * 사용자 세션을 설정합니다.
	 * 
	 * @param userSession
	 *            사용자 세션 모델
	 */
	public static void setUserSession(Model4UserSession userSession) {
		ApplicationProperty.model4UserSession = userSession;
	}

	/**
	 * 사용자 계정을 반환합니다.
	 * 
	 * @return 사용자 계정
	 */
	public static String getUser_account() {
		return model4UserSession == null ? "" : model4UserSession.getUser_account();
	}

	public static boolean getOperation_authority(OPERATION_CODE operationCode) {
		if (model4UserSession == null) {
			return false;
		} else {
			return model4UserSession.getOperation_authority(operationCode);
		}
	}

	/**
	 * 시간 차이를 반환합니다.
	 * 
	 * @return 시간
	 */
	public static long getTime_diff() {
		return time_diff;
	}

	/**
	 * 시간 차이를 설정합니다.
	 * 
	 * @param time_diff
	 *            시간
	 */
	public static void setTime_diff(long time_diff) {
		ApplicationProperty.time_diff = time_diff;
	}

	/**
	 * 새 날짜를 반환합니다.
	 * 
	 * @return 날짜
	 */
	public static Date newDate() {
		return new Date(System.currentTimeMillis() + time_diff);
	}

	/**
	 * NE 검색기능 사용여부를 반환합니다.
	 * 
	 * @return 사용여부
	 */
	public static boolean isDiscovery_ne() {
		return discovery_ne;
	}

	/**
	 * NE 검색기능 사용여부를 설정합니다.
	 * 
	 * @param discovery_ne
	 *            사용여부
	 */
	public static void setDiscovery_ne(boolean discovery_ne) {
		ApplicationProperty.discovery_ne = discovery_ne;
	}

	/**
	 * 네트워크 트리에서 NE 검색 가능여부를 반환합니다.
	 * 
	 * @return 가능여부
	 */
	public static boolean isSearch_ne() {
		return search_ne;
	}

	/**
	 * 네트워크 트리에서 NE 검색 가능여부를 설정합니다.
	 * 
	 * @param search_ne
	 *            가능여부
	 */
	public static void setSearch_ne(boolean search_ne) {
		ApplicationProperty.search_ne = search_ne;
	}

	/**
	 * NE 복사기능 사용여부를 반환합니다.
	 * 
	 * @return 사용여부
	 */
	public static boolean isCopy_ne() {
		return copy_ne;
	}

	/**
	 * NE 복사기능 사용여부를 설정합니다.
	 * 
	 * @param copy_ne
	 *            사용여부
	 */
	public static void setCopy_ne(boolean copy_ne) {
		ApplicationProperty.copy_ne = copy_ne;
	}

	/**
	 * 이메일 상태를 반환합니다.
	 * 
	 * @return 이메일 상태
	 */
	public static boolean isEmail_status() {
		return email_status;
	}

	/**
	 * 이메일 상태를 설정합니다.
	 * 
	 * @param email_status
	 *            이메일 상태
	 */
	public static void setEmail_status(boolean email_status) {
		ApplicationProperty.email_status = email_status;
	}

	/**
	 * SMS 상태를 반환합니다.
	 * 
	 * @return SMS 상태
	 */
	public static boolean isSms_status() {
		return sms_status;
	}

	/**
	 * SMS 상태를 설정합니다.
	 * 
	 * @param sms_status
	 *            SMS 상태
	 */
	public static void setSms_status(boolean sms_status) {
		ApplicationProperty.sms_status = sms_status;
	}

	/**
	 * NE그룹 관리여부를 반환합니다.
	 * 
	 * @return 관리여부
	 */
	public static boolean isNe_group_management() {
		return ne_group_management;
	}

	/**
	 * NE그룹 관리여부를 설정합니다.
	 * 
	 * @param ne_group_management
	 *            관리여부
	 */
	public static void setNe_group_management(boolean ne_group_management) {
		ApplicationProperty.ne_group_management = ne_group_management;
	}

	/**
	 * EMP 홈 디렉토리 경로를 반환합니다.
	 * 
	 * @return 경로
	 */
	public static String getEmp_home() {
		return emp_home;
	}

	/**
	 * 깜빡임 기능이 있는 페이지를 추가합니다.
	 * 
	 * @param pageBlink
	 *            깜빡이 기능이 있는 페이지
	 */
	public static final void addPageBlink(PageBlinkIf pageBlink) {
		lock.lock();
		try {
			List<PageBlinkIf> pageBlinkList = new ArrayList<PageBlinkIf>();
			pageBlinkList.add(pageBlink);
			for (PageBlinkIf ttt : pageBlinks) {
				if (ttt != pageBlink) {
					pageBlinkList.add(ttt);
				}
			}
			pageBlinks = pageBlinkList.toArray(new PageBlinkIf[0]);
		} finally {
			lock.unlock();
		}
	}

	/**
	 * 깜빡임 상태를 설정합니다.
	 * 
	 * @param blink
	 *            깜빡임 상태
	 */
	public void blink(boolean blink) {
		PageBlinkIf[] pageBlinks = ApplicationProperty.pageBlinks;
		for (PageBlinkIf pageBlink : pageBlinks) {
			pageBlink.blink(blink);
		}
	}

	/**
	 * 깜빡임 기능이 있는 페이지를 제거합니다.
	 * 
	 * @param pageBlink
	 *            깜빡임 기능이 있는 페이지
	 */
	public static final void removePageBlink(PageBlinkIf pageBlink) {
		lock.lock();
		try {
			List<PageBlinkIf> pageBlinkList = new ArrayList<PageBlinkIf>();
			for (PageBlinkIf ttt : pageBlinks) {
				if (ttt != pageBlink) {
					pageBlinkList.add(ttt);
				}
			}
			pageBlinks = pageBlinkList.toArray(new PageBlinkIf[0]);
		} finally {
			lock.unlock();
		}
	}

	/**
	 * 모니터 기능이 있는 페이지를 추가합니다.
	 * 
	 * @param pageMonitor
	 *            모니터 기능이 있는 페이지
	 */
	public static final void addPageMonitor(PageMonitorIf pageMonitor) {
		lock.lock();
		try {
			List<PageMonitorIf> pageMonitorList = new ArrayList<PageMonitorIf>();
			pageMonitorList.add(pageMonitor);
			for (PageMonitorIf ttt : pageMonitors) {
				if (ttt != pageMonitor) {
					pageMonitorList.add(ttt);
				}
			}
			pageMonitors = pageMonitorList.toArray(new PageMonitorIf[0]);
		} finally {
			lock.unlock();
		}
	}

	/**
	 * 모니터링을 합니다.
	 */
	public void monitor() {
		PageMonitorIf[] pageMonitors = ApplicationProperty.pageMonitors;
		for (PageMonitorIf pageMonitor : pageMonitors) {
			long startTime = System.currentTimeMillis();
			pageMonitor.monitor();
			long ttt = (System.currentTimeMillis() - startTime);
			if (5000 < ttt) {
				System.out.println(UtilDate.format() + " : monitor of " + pageMonitor.getClass().getSimpleName() + " takes " + ttt + " ms");
			}
		}
	}

	/**
	 * 모니터 기능이 있는 페이지를 제거합니다.
	 * 
	 * @param pageMonitor
	 */
	public static final void removePageMonitor(PageMonitorIf pageMonitor) {
		lock.lock();
		try {
			List<PageMonitorIf> pageMonitorList = new ArrayList<PageMonitorIf>();
			for (PageMonitorIf ttt : pageMonitors) {
				if (ttt != pageMonitor) {
					pageMonitorList.add(ttt);
				}
			}
			pageMonitors = pageMonitorList.toArray(new PageMonitorIf[0]);
		} finally {
			lock.unlock();
		}
	}

	/**
	 * 생성자
	 */
	public ApplicationProperty() {
		advisor = createApplicationAdvisor();
	}

	protected ApplicationAdvisor createApplicationAdvisor() {
		return new ApplicationAdvisor();
	}

	public byte[] queryEmp_model() throws EmpException {
		return advisor.queryEmp_model();
	}

	public Model4About getAbout() throws EmpException {
		if (about == null) {
			about = advisor.getAbout();
		}
		return about;
	}

	public Model4UserSession queryUserSession() throws EmpException {
		return advisor.queryUserSession();
	}

	public void queryLanguage(LANGUAGE language) throws EmpException {
		Map<String, String> langugae_map = advisor.queryLanguage(language);
		UtilLanguage.setMessages(langugae_map);
	}

	public boolean logout() {
		if (DialogMessage.openConfirm(display.getActiveShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.LOGOUT), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.LOGOUT_CONFIRM))) {
			try {
				DialogProgress.run(null, false, new ProgressTaskIf() {
					@Override
					public Object run() throws EmpException {
						advisor.logout();
						return null;
					}
				});
			} catch (EmpException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		} else {
			return false;
		}
	}

	public void setDisplay(Display display) {
		this.display = display;
	}

	/**
	 * 언어설정을 반환합니다.
	 * 
	 * @return 언어설정
	 */
	public String[] getLanguageProperties() {
		return new String[] { "/com/hellonms/platforms/emp/language", "/com/hellonms/platforms/emp_onion/language", "/com/hellonms/platforms/emp_orange/language" };
	}

	/**
	 * 언어를 반환합니다.
	 * 
	 * @return 언어 배열
	 */
	public LANGUAGE[] getLanguages() {
		return new LANGUAGE[] { LANGUAGE.KOREAN, LANGUAGE.ENGLISH };
	}

	/**
	 * 서비스 인터페이스를 반환합니다.
	 * 
	 * @return 서비스 인터페이스
	 */
	public Class<? extends Invoker4OrangeClientSwtIf> getServiceInterface() {
		return Invoker4OrangeClientSwtIf.class;
	}

	/**
	 * 로그인 크기를 반환합니다.
	 * 
	 * @return 로그인 크기
	 */
	public Rectangle getLoginBoundsInput() {
		return new Rectangle(430, 144, 110, 20);
	}

	protected String getProductTitle() {
		try {
			return getAbout().getProduct_name();
		} catch (EmpException e) {
			return e.getMessage();
		}
	}

	protected String getProductVersion() {
		try {
			return getAbout().getVersion();
		} catch (EmpException e) {
			return e.getMessage();
		}
	}

	protected String getBuildId() {
		try {
			return getAbout().getBuild_id();
		} catch (EmpException e) {
			return e.getMessage();
		}
	}

	protected String getProductInfo() {
		try {
			return getAbout().getAbout();
		} catch (EmpException e) {
			return e.getMessage();
		}
	}

	/**
	 * 제품명을 반환합니다.
	 * 
	 * @return 제품명
	 */
	public String getApplicationTitle() {
		return getProductTitle() + " - " + ApplicationProperty.getUser_account() + "@" + ApplicationProperty.getEmp_orange_server();
	}

	/**
	 * 제품정보의 크기를 반환합니다.
	 * 
	 * @return 크기
	 */
	public Point getAboutSize() {
		return new Point(550, 320);
	}

	/**
	 * 노드 정렬 시 필요한 비교 클래스를 반환합니다.
	 * 
	 * @return 비교 클래스
	 */
	protected Comparator<NODE> getNode_comparator() {
		return null;
	}

	public void initGUI() {
		DataFactory.addDataBuilder(new DataBuilder4Orange());
		PanelFactory.addPanelBuilder(new PanelBuilder4Orange());
		ThemeFactory.addThemeBuilder(new ThemeBuilder4Onion());
		ThemeFactory.addThemeBuilder(new ThemeBuilder4Orange());
	}

	/**
	 * 초기화 합니다.
	 * 
	 * @throws EmpException
	 */
	public void initialize() throws EmpException {
		ModelClient4NetworkTree.getInstance().refresh();
	}

	public void createMenu(MenuStack menu, Page4ToolbarListenerIf listener) {
		createMenu4Network(menu, listener);
		createMenu4Fault(menu, listener);
		createMenu4Security(menu, listener);
		createMenu4Help(menu, listener);

		boolean develop_mode = false;
		try {
			develop_mode = getAbout().isDevelop_mode();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if (develop_mode && model4UserSession != null && model4UserSession.getUser_id() == 1) {
			menu.addMenu(new String[] { UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.MODELING) }, new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					Shell4Model.open(display.getActiveShell());
				}
			});
		}

		menu.addMenu(new String[] { UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.LOGOUT) }, new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				if (logout()) {
					display.getActiveShell().dispose();
				}
			}
		});
	}

	protected void createMenu4Network(MenuStack menu, final Page4ToolbarListenerIf listener) {
		menu.addMenu(new String[] { UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NETWORK), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NETWORK_VIEW) }, new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				listener.selectMenu(PAGE_ORANGE.NETWORK_VIEW);
			}
		});

		if (ApplicationProperty.getOperation_authority(OPERATION_CODE_ORANGE.NETWORK_NE_INFO_QUERY) || true) {
			menu.addMenu(new String[] { UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NETWORK), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NE_INFO) }, new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					listener.selectMenu(PAGE_ORANGE.NE_INFO);
				}
			});
		}

		if (ApplicationProperty.getOperation_authority(OPERATION_CODE_ORANGE.NETWORK_NE_STATISTICS_QUERY) || true) {
			menu.addMenu(new String[] { UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NETWORK), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NE_STATISTICS) }, new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					listener.selectMenu(PAGE_ORANGE.NE_STATISTICS);
				}
			});
		}

		if (ApplicationProperty.getOperation_authority(OPERATION_CODE_ORANGE.NETWORK_NE_THRESHOLD_QUERY) || true) {
			menu.addMenu(new String[] { UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NETWORK), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NE_THRESHOLD) }, new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					listener.selectMenu(PAGE_ORANGE.NE_THRESHOLD);
				}
			});
		}
	}

	protected void createMenu4Fault(MenuStack menu, final Page4ToolbarListenerIf listener) {
		if (ApplicationProperty.getOperation_authority(OPERATION_CODE_ORANGE.FAULT_ALARM_QUERY) || true) {
			menu.addMenu(new String[] { UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.FAULT), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.ALARM_ACTIVE) }, new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					listener.selectMenu(PAGE_ORANGE.ALARM_ACTIVE);
				}
			});

			menu.addMenu(new String[] { UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.FAULT), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.ALARM_HISTORY) }, new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					listener.selectMenu(PAGE_ORANGE.ALARM_HISTORY);
				}
			});
		}

		if (ApplicationProperty.getOperation_authority(OPERATION_CODE_ORANGE.FAULT_EVENT_QUERY) || true) {
			menu.addMenu(new String[] { UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.FAULT), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.EVENT) }, new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					listener.selectMenu(PAGE_ORANGE.EVENT);
				}
			});
		}

		if (ApplicationProperty.getOperation_authority(OPERATION_CODE_ORANGE.FAULT_ALARM_STATISTICS_QUERY) || true) {
			menu.addMenu(new String[] { UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.FAULT), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.ALARM_STATISTICS) }, new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					listener.selectMenu(PAGE_ORANGE.ALARM_STATISTICS);
				}
			});
		}
	}

	protected void createMenu4Security(MenuStack menu, final Page4ToolbarListenerIf listener) {
		if (ApplicationProperty.getOperation_authority(OPERATION_CODE_ORANGE.SECURITY_USER_QUERY)) {
			menu.addMenu(new String[] { UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.SECURITY), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.USER_MANAGEMENT) }, new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					listener.selectMenu(PAGE_ORANGE.USER);
				}
			});
		}

		if (ApplicationProperty.getOperation_authority(OPERATION_CODE_ORANGE.SECURITY_OPERATIONLOG_QUERY)) {
			menu.addMenu(new String[] { UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.SECURITY), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.OPERATION_LOG) }, new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					listener.selectMenu(PAGE_ORANGE.OPERATION_LOG);
				}
			});
		}
	}

	protected void createMenu4Help(MenuStack menu, Page4ToolbarListenerIf listener) {
		// menu.addMenu(new String[] { UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.HELP), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.ENVIRONMENT) }, new SelectionAdapter() {
		// @Override
		// public void widgetSelected(SelectionEvent e) {
		// DialogPreference dialog = new DialogPreference(display.getActiveShell());
		// dialog.addPagePreferences(createPagePreferences());
		// dialog.open();
		// }
		// });
		menu.addMenu(new String[] { UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.HELP), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.HELP_CONTENTS) }, new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Program.launch(ApplicationProperty.getEmp_orange_help());
			}
		});

		menu.addMenu(new String[] { UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.HELP), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.ABOUT) }, new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Dialog4About dialog4About = new Dialog4About(display.getActiveShell(), ThemeFactory.getImage(IMAGE_ORANGE.ABOUT_CI), getProductInfo());
				dialog4About.setSize(getAboutSize());
				UtilLocation.toCenter(display.getActiveShell(), dialog4About);
				dialog4About.open();
			}
		});
	}

	protected PagePreference[] createPagePreferences() {
		return new PagePreference[] { new PagePreference4DbBackup() };
	}

}
