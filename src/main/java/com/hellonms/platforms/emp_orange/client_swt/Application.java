package com.hellonms.platforms.emp_orange.client_swt;

import java.net.URL;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_core.share.language.LANGUAGE;
import com.hellonms.platforms.emp_core.share.language.UtilLanguage;
import com.hellonms.platforms.emp_onion.client_swt.widget.dialog.DialogMessage;
import com.hellonms.platforms.emp_onion.client_swt.widget.dialog.DialogProgress;
import com.hellonms.platforms.emp_onion.client_swt.widget.dialog.DialogProgress.ProgressTaskIf;
import com.hellonms.platforms.emp_onion.client_swt.widget.workbench.Workbench;
import com.hellonms.platforms.emp_onion.theme.ThemeBuilder4Onion.IMAGE_ONION;
import com.hellonms.platforms.emp_onion.theme.ThemeFactory;
import com.hellonms.platforms.emp_orange.client_swt.driver.Driver4OrangeClientSwt;
import com.hellonms.platforms.emp_orange.client_swt.page.network.network_tree.ModelClient4NetworkTree;
import com.hellonms.platforms.emp_orange.client_swt.page.security.login.Dialog4Login;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL;
import com.hellonms.platforms.emp_orange.share.model.security.user_session.Model4UserSession;
import com.hellonms.platforms.emp_orange.theme.ThemeBuilder4Orange.IMAGE_ORANGE;

/**
 * <p>
 * Application
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 18.
 * @modified 2015. 5. 18.
 * @author cchyun
 *
 */
public class Application {

	public class BlinkThread extends Thread {

		public BlinkThread() {
			super("ApplicationProperty::BlinkThread");
		}

		@Override
		public void run() {
			final Display display = Display.getDefault();
			boolean blink = false;

			while (display != null && !display.isDisposed()) {
				try {
					blink = !blink;
					if (display != null && !display.isDisposed()) {
						applicationProperty.blink(blink);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				try {
					Thread.sleep(blink ? SLEEP_BLINK_01 : SLEEP_BLINK_02);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public class MonitorThread extends Thread {

		public MonitorThread() {
			super("ApplicationProperty::MonitorThread");
		}

		@Override
		public void run() {
			final Display display = Display.getDefault();
			int session_fail_count = 0;

			while (session_fail_count < 5) {
				try {
					if (Driver4OrangeClientSwt.getInstance().testUserSession()) {
						session_fail_count = 0;

						if (display != null && !display.isDisposed()) {
							applicationProperty.monitor();
						}

						long timestamp = System.currentTimeMillis();
						ModelClient4NetworkTree.getInstance().refresh();
						long ttt = (System.currentTimeMillis() - timestamp);
						if (5000 < ttt) {
							System.out.println("refreshIfNeed: " + (System.currentTimeMillis() - timestamp) + " ms take");
						}
					} else {
						session_fail_count++;

						if (3 <= session_fail_count) {
							if (display != null && !display.isDisposed()) {
								display.syncExec(new Runnable() {
									@Override
									public void run() {
										DialogMessage.openInfo(display.getActiveShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.CONNECTION_CLOSED), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.CONNECTION_CLOSED_ERROR));
										display.dispose();
									}
								});
							}
							break;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					try {
						Model4UserSession userSession = Driver4OrangeClientSwt.getInstance().queryUserSession();
						if (userSession == null && display != null && !display.isDisposed()) {
							display.syncExec(new Runnable() {
								@Override
								public void run() {
									DialogMessage.openInfo(display.getActiveShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.CONNECTION_CLOSED), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.CONNECTION_CLOSED_ERROR));
									display.dispose();
								}
							});
						}
					} catch (EmpException e1) {
						e1.printStackTrace();
						if (display != null && !display.isDisposed()) {
							display.syncExec(new Runnable() {
								@Override
								public void run() {
									DialogMessage.openInfo(display.getActiveShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.CONNECTION_CLOSED), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.CONNECTION_CLOSED_ERROR));
									display.dispose();
								}
							});
						}
					}
				}

				try {
					Thread.sleep(SLEEP_MONITOR_02);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public static long SLEEP_BLINK_01 = 500L;

	public static long SLEEP_BLINK_02 = 2500L;

	public static long SLEEP_MONITOR_02 = 5000L;

	public static void main(String[] args) throws EmpException {
		new Application().start(args);
	}

	public static void initializeModel(byte[] emp_model_data) throws EmpException {
		EMP_MODEL.init_current(emp_model_data);
	}

	protected Display display;

	protected ApplicationProperty applicationProperty;

	protected void start(String[] args) {
		this.display = new Display();
		try {
			applicationProperty = newApplicationProperty();
			applicationProperty.setDisplay(display);

			String user_session_key = null;
			String emp_orange_server;
			String emp_orange_url = getDefaultEmp_orange_url();
			String emp_orange_help = getDefaultEmp_orange_help();
			LANGUAGE emp_language = LANGUAGE.KOREAN;
			for (String arg : args) {
				if (arg.startsWith("user.session=")) {
					user_session_key = arg.substring("user.session=".length());
				} else if (arg.startsWith("emp.url=")) {
					emp_orange_url = arg.substring("emp.url=".length());
				} else if (arg.startsWith("emp.help=")) {
					emp_orange_help = arg.substring("emp.help=".length());
				} else if (arg.startsWith("emp.language=")) {
					emp_language = getLanguage(arg.substring("emp.language=".length()));
				}
			}
			emp_orange_server = new URL(emp_orange_url).getHost();
			ApplicationProperty.setEmp_orange_server(emp_orange_server);
			ApplicationProperty.setEmp_orange_url(emp_orange_url);
			ApplicationProperty.setEmp_orange_help(emp_orange_help);

			Driver4OrangeClientSwt.getInstance().setUser_session_key(user_session_key);
			Driver4OrangeClientSwt.getInstance().setServiceUrl(emp_orange_url);
			Driver4OrangeClientSwt.getInstance().setServiceInterface(applicationProperty.getServiceInterface());

			applicationProperty.initGUI();
			try {
				applicationProperty.queryLanguage(emp_language);
			} catch (EmpException e) {
			}

			// 모델 초기화: 서버 환경이 정리 된 후 실행
			this.initializeModel();

			Model4UserSession userSession = null;
			try {
				userSession = applicationProperty.queryUserSession();
			} catch (EmpException e) {
			}

			if (userSession == null) {
				Dialog4Login dialog4Login = newDialog4Login(display, emp_orange_server, emp_language);
				dialog4Login.open();

				if (!dialog4Login.getPage4Login().isLogin()) {
					return;
				}
				try {
					userSession = applicationProperty.queryUserSession();
				} catch (EmpException e) {
					DialogMessage.openError(null, UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.EXEC_CLIENT_ERROR), e);
					return;
				}
			}

			try {
				DialogProgress.run(null, true, new ProgressTaskIf() {
					@Override
					public Object run() throws EmpException {
						applicationProperty.initialize();
						new BlinkThread().start();
						new MonitorThread().start();
						return null;
					}
				});
			} catch (EmpException e) {
				DialogMessage.openError(null, UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.EXEC_CLIENT_ERROR), e);
				display.dispose();
				return;
			}

			ApplicationProperty.setUserSession(userSession);
			Shell shell = newShell(display);
			shell.addListener(SWT.Close, new Listener() {
				public void handleEvent(Event event) {
					if (applicationProperty.logout()) {
						stop();
					} else {
						event.doit = false;
					}
				}
			});

			shell.setImages(new Image[] { ThemeFactory.getImage(IMAGE_ONION.APPLICATION_ICON_16), //
					ThemeFactory.getImage(IMAGE_ONION.APPLICATION_ICON_24), //
					ThemeFactory.getImage(IMAGE_ONION.APPLICATION_ICON_32), //
					ThemeFactory.getImage(IMAGE_ONION.APPLICATION_ICON_48), //
					ThemeFactory.getImage(IMAGE_ONION.APPLICATION_ICON_64), //
					ThemeFactory.getImage(IMAGE_ONION.APPLICATION_ICON_128), //
					ThemeFactory.getImage(IMAGE_ONION.APPLICATION_ICON_256), //
			});
			shell.setText(applicationProperty.getApplicationTitle());
			shell.setLayout(new FillLayout());
			@SuppressWarnings("unused")
			Workbench workbench = newWorkbench(shell, applicationProperty);
			shell.open();

			while (!shell.isDisposed()) {
				try {
					if (!display.readAndDispatch()) {
						display.sleep();
					}
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
			DialogMessage.openError(null, UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.EXEC_CLIENT_ERROR), e);
		} finally {
			display.dispose();
			System.exit(0);
		}
	}

	protected void stop() {
		display.dispose();
	}

	protected void initializeModel() throws EmpException {
		byte[] emp_model_data = applicationProperty.queryEmp_model();
		Application.initializeModel(emp_model_data);
		applicationProperty.getAbout();
	}

	protected Dialog4Login newDialog4Login(Display display, String emp_orange_server, LANGUAGE emp_language) {
		Dialog4Login dialog4Login = new Dialog4Login(display, ThemeFactory.getImage(IMAGE_ORANGE.LONGIN));
		dialog4Login.getPage4Login().setBoundsInput(applicationProperty.getLoginBoundsInput());
		dialog4Login.getPage4Login().setTextServer(emp_orange_server);
		dialog4Login.getPage4Login().setLanguages(applicationProperty.getLanguages());
		dialog4Login.getPage4Login().setLanguage(emp_language);
		return dialog4Login;
	}

	protected LANGUAGE getLanguage(String language) {
		return LANGUAGE.valueOf(language);
	}

	protected Shell newShell(Display display) {
		return new Shell(display);
	}

	protected ApplicationProperty newApplicationProperty() {
		return new ApplicationProperty();
	}

	protected Workbench newWorkbench(Shell shell, ApplicationProperty applicationProperty) {
		return new Workbench4Orange(shell, SWT.NONE, applicationProperty);
	}

	/**
	 * 서버 URl의 기본값을 반환합니다.
	 * 
	 * @return 서버 URL
	 */
	protected String getDefaultEmp_orange_url() {
		return "http://127.0.0.1:8080/invoker/swt.do";
	}

	/**
	 * 도움말 URL의 기본값을 반환합니다.
	 * 
	 * @return 도움말 URL
	 */
	protected String getDefaultEmp_orange_help() {
		return "http://127.0.0.1:8080/help/index.jsp";
	}

}
