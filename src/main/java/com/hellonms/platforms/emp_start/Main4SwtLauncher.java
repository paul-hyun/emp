/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_start;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.hellonms.platforms.emp_core.server.log.BlackBox;
import com.hellonms.platforms.emp_core.server.log.LEVEL;
import com.hellonms.platforms.emp_core.share.error.ERROR_CODE;
import com.hellonms.platforms.emp_core.share.error.ERROR_CODE_CORE;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_core.share.language.LANGUAGE;
import com.hellonms.platforms.emp_core.share.language.UtilLanguage;
import com.hellonms.platforms.emp_onion.client_swt.util.resource.UtilResource;
import com.hellonms.platforms.emp_onion.client_swt.widget.dialog.DialogMessage;
import com.hellonms.platforms.emp_onion.client_swt.widget.dialog.DialogProgress;
import com.hellonms.platforms.emp_onion.client_swt.widget.dialog.DialogProgress.ProgressTaskIf;
import com.hellonms.platforms.emp_onion.share.message.MESSAGE_CODE_ONION;
import com.hellonms.platforms.emp_onion.share.model.help.about.Model4About;
import com.hellonms.platforms.emp_onion.share.rest.UtilRest;
import com.hellonms.platforms.emp_onion.theme.ThemeBuilder4Onion;
import com.hellonms.platforms.emp_onion.theme.ThemeBuilder4Onion.IMAGE_ONION;
import com.hellonms.platforms.emp_onion.theme.ThemeFactory;
import com.hellonms.platforms.emp_orange.share.EMP_ORANGE_DEFINE;
import com.hellonms.platforms.emp_orange.share.error.ERROR_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;
import com.hellonms.platforms.emp_plug.http.Plug4HTTPClient;
import com.hellonms.platforms.emp_util.http.UtilJson;
import com.hellonms.platforms.emp_util.string.UtilString;
import com.hellonms.platforms.emp_util.system.UtilSystem;

/**
 * <p>
 * Remote SWT Start
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 20.
 * @modified 2015. 5. 20.
 * @author cchyun
 *
 */
public class Main4SwtLauncher {

	private static final BlackBox blackBox = new BlackBox(Main4SwtLauncher.class);

	public static void main(String[] args) throws Exception {
		new Main4SwtLauncher().run(args);
	}

	protected Display display;

	protected String host = "127.0.0.1:20080";

	protected LANGUAGE language = LANGUAGE.KOREAN;

	protected String user_session_key = null;

	private File product_home;

	private File properties_file_client_swt;

	protected Properties properties_client_swt;

	protected Properties properties_remote;

	protected File running_home;

	protected void run(String[] args) throws Exception {
		initialze_theme();

		product_home = getProduct_home();
		product_home.mkdirs();

		initialze_codes();
		loadProperties();

		display = new Display();

		Shell shell = createLogin();
		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

	protected void start(Shell shell, final String host, final String user_account, final String password, final String language) {
		try {
			if (host.length() < 7) {
				DialogMessage.openInfo(shell, UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.LOGIN), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.INSERT_VALUE, MESSAGE_CODE_ORANGE.SERVER_ADDRESS));
				return;
			}
			if (user_account.length() == 0) {
				DialogMessage.openInfo(shell, UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.LOGIN), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.INSERT_VALUE, MESSAGE_CODE_ORANGE.USER_ID));
				return;
			}
			if (password.length() == 0) {
				DialogMessage.openInfo(shell, UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.LOGIN), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.INSERT_VALUE, MESSAGE_CODE_ORANGE.PASSWORD));
				return;
			}
			if (language.length() == 0) {
				DialogMessage.openInfo(shell, UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.LOGIN), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.INSERT_VALUE, MESSAGE_CODE_ORANGE.LANGUAGE));
				return;
			}

			this.host = host;
			properties_client_swt.setProperty("host", host);
			properties_client_swt.setProperty("user_account", user_account);
			properties_client_swt.setProperty("language", language);
			saveProperties();
			DialogProgress.run(shell, true, new ProgressTaskIf() {
				@Override
				public Object run() throws EmpException {
					login(user_account, password);

					if (user_session_key != null) {
						download_client_swt();
					}
					return null;
				}
			});

			if (user_session_key != null) {
				display.dispose();
				start_client_swt();
			}
		} catch (Exception e) {
			if (blackBox.isEnabledFor(LEVEL.Fatal)) {
				blackBox.log(LEVEL.Fatal, null, e);
			}
			openError(shell, e);
		} finally {
		}
	}
	
	protected void openError(Shell shell, Exception e) {
		DialogMessage.openError(shell, UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.LOGIN), e);
	}

	protected void stop() {
		display.dispose();
	}

	protected void initialze_theme() {
		ThemeFactory.addThemeBuilder(new ThemeBuilder4Onion());
	}

	protected void initialze_codes() {
		new ERROR_CODE_ORANGE();
		new MESSAGE_CODE_ORANGE();
	}

	protected void loadProperties() throws Exception {
		properties_client_swt = new Properties();
		properties_file_client_swt = new File(product_home, "emp_client_swt.properties");
		if (properties_file_client_swt.isFile()) {
			FileInputStream fis = new FileInputStream(properties_file_client_swt);
			properties_client_swt.load(fis);
			fis.close();
		}
	}

	protected void saveProperties() throws Exception {
		FileOutputStream os = new FileOutputStream(properties_file_client_swt);
		properties_client_swt.store(os, null);
		os.close();
	}

	protected Shell createLogin() {
		LANGUAGE[] languages = getLanguages();
		String[] languae_items = new String[languages.length];
		int index_select = 0;
		if (0 < languages.length) {
			language = LANGUAGE.valueOf(properties_client_swt.getProperty("language", languages[0].toString()));
			for (int i = 0; i < languages.length; i++) {
				languae_items[i] = languages[i].toString();
				if (language == languages[i]) {
					index_select = i;
				}
			}
			init_language(language);
		}

		final Image image = UtilResource.getImage(getLogin_image());
		Rectangle bounds = image.getBounds();

		final Shell shell = new Shell(display, SWT.NO_TRIM);
		shell.setImages(new Image[] { //
		ThemeFactory.getImage(IMAGE_ONION.APPLICATION_ICON_16), //
				ThemeFactory.getImage(IMAGE_ONION.APPLICATION_ICON_24), //
				ThemeFactory.getImage(IMAGE_ONION.APPLICATION_ICON_32), //
				ThemeFactory.getImage(IMAGE_ONION.APPLICATION_ICON_64), //
				ThemeFactory.getImage(IMAGE_ONION.APPLICATION_ICON_128), //
				ThemeFactory.getImage(IMAGE_ONION.APPLICATION_ICON_256), //
		});
		shell.setSize(bounds.width, bounds.height);
		shell.setBackground(UtilResource.getColor(239, 239, 239));
		shell.setBackgroundMode(SWT.INHERIT_DEFAULT);

		shell.addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
				e.gc.drawImage(image, 0, 0);
			}
		});

		final Text text_host = new Text(shell, SWT.BORDER);
		text_host.setText(properties_client_swt.getProperty("host", "127.0.0.1:20080"));
		final Text text_user_account = new Text(shell, SWT.BORDER);
		text_user_account.setText(properties_client_swt.getProperty("user_account", "admin"));
		final Text text_password = new Text(shell, SWT.BORDER | SWT.PASSWORD);
		final Combo combo_language = new Combo(shell, SWT.BORDER | SWT.READ_ONLY);
		combo_language.setItems(languae_items);
		if (0 < languages.length) {
			combo_language.select(index_select);
		}

		final Button button_ok = new Button(shell, SWT.NONE);
		button_ok.setText(UtilLanguage.getMessage(MESSAGE_CODE_ONION.OK));
		button_ok.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Main4SwtLauncher.this.start(shell, text_host.getText().trim(), text_user_account.getText().trim(), text_password.getText(), combo_language.getText());
			}
		});

		final Button button_cancel = new Button(shell, SWT.NONE);
		button_cancel.setText(UtilLanguage.getMessage(MESSAGE_CODE_ONION.CANCEL));
		button_cancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Main4SwtLauncher.this.stop();
			}
		});

		text_password.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				Main4SwtLauncher.this.start(shell, text_host.getText().trim(), text_user_account.getText().trim(), text_password.getText(), combo_language.getText());
			}
		});

		combo_language.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				language = LANGUAGE.valueOf(combo_language.getText());
				init_language(language);
				button_ok.setText(UtilLanguage.getMessage(MESSAGE_CODE_ONION.OK));
				button_cancel.setText(UtilLanguage.getMessage(MESSAGE_CODE_ONION.CANCEL));
			}
		});

		int location_x = 435;
		int location_y = 144;
		int location_width = 170;
		int location_height = 20;
		int space_width = 4;
		int space_height = 25;

		text_host.setBounds(location_x, location_y, location_width, location_height);

		location_y += (space_height);
		text_user_account.setBounds(location_x, location_y, location_width, location_height);

		location_y += (space_height);
		text_password.setBounds(location_x, location_y, location_width, location_height);

		location_y += (space_height);
		combo_language.setBounds(location_x, location_y, location_width, location_height);

		location_y += (space_height + 6);
		button_ok.setBounds(location_x, location_y, location_width / 2 - space_width / 2, location_height * 2);

		location_x += (location_width / 2 + space_width / 2);
		button_cancel.setBounds(location_x, location_y, location_width / 2 - space_width / 2, location_height * 2);

		return shell;
	}

	protected void init_language(LANGUAGE language) {
		try {
			Map<String, String> map = new HashMap<String, String>();
			for (String language_folder : getLanguage_folders()) {
				String language_resource = language_folder + "/language_" + language.getLocale() + ".properties";
				InputStream is = Main4SwtLauncher.class.getResourceAsStream(language_resource);
				Properties properties = new Properties();
				properties.load(is);
				is.close();
				for (Object key : properties.keySet()) {
					map.put((String) key, properties.getProperty((String) key));
				}
			}
			UtilLanguage.setMessages(map);
		} catch (Exception e) {
			if (blackBox.isEnabledFor(LEVEL.Fatal)) {
				blackBox.log(LEVEL.Fatal, null, e);
			}
		}
	}

	protected void login(final String user_account, final String password) throws EmpException {
		Plug4HTTPClient httpClient = new Plug4HTTPClient();
		try {
			Map<String, Object> about_response = UtilRest.parse_response(httpClient.get(new URL(getUrl_help_about(host))));
			Model4About about = UtilJson.toObject(about_response.get("about"), Model4About.class);
			validateAbout(about);

			Map<String, Object> login_request = new HashMap<String, Object>();
			login_request.put("user_account", user_account);
			login_request.put("password", password);
			Map<String, Object> login_response = UtilRest.parse_response(httpClient.post(new URL(getUrl_security_user_session(host)), "application/json", UtilJson.toString(login_request)));
			user_session_key = (String) login_response.get("user_session_key");
		} catch (EmpException e) {
			throw e;
		} catch (Exception e) {
			throw new EmpException(e, ERROR_CODE.ERROR_UNKNOWN);
		} finally {
			httpClient.close();
		}
	}

	protected void download_client_swt() throws EmpException {
		Plug4HTTPClient httpClient = new Plug4HTTPClient();
		try {
			URL properties_url_remote = new URL(getUrl_lib_client_swt(host) + "/lib_client_swt.properites");
			byte[] properties_buf_remote = httpClient.downloadFile(properties_url_remote);
			properties_remote = new Properties();
			properties_remote.load(new ByteArrayInputStream(properties_buf_remote));

			File product_home = getProduct_home();
			running_home = new File(product_home, properties_url_remote.getHost() //
					.replaceAll("\\[", "") //
					.replaceAll("]", "") //
					.replaceAll(":", "."));
			running_home.mkdirs();
			File properties_file_local = new File(running_home, "lib_client_swt.properites");
			Properties properties_local = new Properties();
			if (properties_file_local.isFile()) {
				FileInputStream is = new FileInputStream(properties_file_local);
				properties_local.load(is);
				is.close();
			}

			boolean need_download = false;
			try {
				if (!need_download) {
					need_download = !properties_remote.getProperty("build_time").equals(properties_local.getProperty("build_time"));
				}
				if (!need_download) {
					String[] jars = properties_local.getProperty("jars").split(",");
					for (String jar : jars) {
						File file = new File(running_home, jar.trim());
						if (!file.isFile()) {
							need_download = true;
							break;
						} else {
							URL url = new URL(getUrl_lib_client_swt(host) + "/" + jar + ".MD5");
							if (!equals_md5(httpClient, file, url)) {
								need_download = true;
								break;
							}
						}
					}
				}
				if (!need_download && UtilSystem.getOSArch() == 32) {
					String[] jars = properties_local.getProperty("x86_jars").split(",");
					for (String jar : jars) {
						File file = new File(running_home, jar.trim());
						if (!file.isFile()) {
							need_download = true;
							break;
						} else {
							URL url = new URL(getUrl_lib_client_swt(host) + "/" + jar + ".MD5");
							if (!equals_md5(httpClient, file, url)) {
								need_download = true;
								break;
							}
						}
					}
				}
				if (!need_download && UtilSystem.getOSArch() == 64) {
					String[] jars = properties_local.getProperty("x64_jars").split(",");
					for (String jar : jars) {
						File file = new File(running_home, jar.trim());
						if (!file.isFile()) {
							need_download = true;
							break;
						} else {
							URL url = new URL(getUrl_lib_client_swt(host) + "/" + jar + ".MD5");
							if (!equals_md5(httpClient, file, url)) {
								need_download = true;
								break;
							}
						}
					}
				}
			} catch (Exception e) {
				need_download = true;
				if (blackBox.isEnabledFor(LEVEL.Fatal)) {
					blackBox.log(LEVEL.Fatal, null, e);
				}
			}

			if (need_download) {
				if (blackBox.isEnabledFor(LEVEL.Fatal)) {
					blackBox.log(LEVEL.Fatal, null, UtilString.format("Download start : build_time is {} != {}", properties_remote.getProperty("build_time"), properties_local.getProperty("build_time")));
				}

				// clear jars
				for (File file : running_home.listFiles()) {
					if (file.getName().endsWith(".jar")) {
						file.delete();
						if (blackBox.isEnabledFor(LEVEL.Fatal)) {
							blackBox.log(LEVEL.Fatal, null, UtilString.format("\tDelete file : {}", file.getAbsolutePath()));
						}
					}
				}

				// download jars
				{
					String[] jars = properties_remote.getProperty("jars").split(",");
					for (String jar : jars) {
						URL url = new URL(getUrl_lib_client_swt(host) + "/" + jar);
						File file = new File(running_home, jar.trim());
						httpClient.downloadFile(url, file);
						if (blackBox.isEnabledFor(LEVEL.Fatal)) {
							blackBox.log(LEVEL.Fatal, null, UtilString.format("\tDownload file : {} -> {}", url.toString(), file.getAbsolutePath()));
						}
					}
				}
				if (UtilSystem.getOSArch() == 32) {
					String[] jars = properties_remote.getProperty("x86_jars").split(",");
					for (String jar : jars) {
						URL url = new URL(getUrl_lib_client_swt(host) + "/" + jar);
						File file = new File(running_home, jar.trim());
						httpClient.downloadFile(url, file);
						if (blackBox.isEnabledFor(LEVEL.Fatal)) {
							blackBox.log(LEVEL.Fatal, null, UtilString.format("\tDownload file : {} -> {}", url.toString(), file.getAbsolutePath()));
						}
					}
				}
				if (UtilSystem.getOSArch() == 64) {
					String[] jars = properties_remote.getProperty("x64_jars").split(",");
					for (String jar : jars) {
						URL url = new URL(getUrl_lib_client_swt(host) + "/" + jar);
						File file = new File(running_home, jar.trim());
						httpClient.downloadFile(url, file);
						if (blackBox.isEnabledFor(LEVEL.Fatal)) {
							blackBox.log(LEVEL.Fatal, null, UtilString.format("\tDownload file : {} -> {}", url.toString(), file.getAbsolutePath()));
						}
					}
				}

				// save lib_client_swt.properites
				FileOutputStream os = new FileOutputStream(properties_file_local);
				properties_remote.store(os, null);
				os.close();
			} else {
				if (blackBox.isEnabledFor(LEVEL.Fatal)) {
					blackBox.log(LEVEL.Fatal, null, UtilString.format("Download skip : build_time is {} == {}", properties_remote.getProperty("build_time"), properties_local.getProperty("build_time")));
				}
			}
		} catch (EmpException e) {
			throw e;
		} catch (Exception e) {
			throw new EmpException(e, ERROR_CODE.ERROR_UNKNOWN);
		} finally {
			httpClient.close();
		}
	}

	protected boolean equals_md5(Plug4HTTPClient httpClient, File file, URL url) throws Exception {
		return true;
	}

	protected void start_client_swt() {
		try {
			File javaw = new File(System.getProperty("sun.boot.library.path"), "javaw.exe");
			if (javaw.isFile()) {
				if (blackBox.isEnabledFor(LEVEL.Fatal)) {
					blackBox.log(LEVEL.Fatal, null, UtilString.format("####################################################################"));
					blackBox.log(LEVEL.Fatal, null, UtilString.format("Program start : javaw={}", javaw.getAbsolutePath()));
					blackBox.log(LEVEL.Fatal, null, UtilString.format("####################################################################"));
				}

				StringBuilder classpath = new StringBuilder();
				classpath.append(".");
				{
					String[] jars = properties_remote.getProperty("jars").split(",");
					for (String jar : jars) {
						classpath.append(";").append(new File(running_home, jar.trim()));
					}
				}
				if (UtilSystem.getOSArch() == 32) {
					String[] jars = properties_remote.getProperty("x86_jars").split(",");
					for (String jar : jars) {
						classpath.append(";").append(new File(running_home, jar.trim()));
					}
				}
				if (UtilSystem.getOSArch() == 64) {
					String[] jars = properties_remote.getProperty("x64_jars").split(",");
					for (String jar : jars) {
						classpath.append(";").append(new File(running_home, jar.trim()));
					}
				}

				ProcessBuilder builder = new ProcessBuilder(new String[] { javaw.getAbsolutePath(), "-Dfile.encoding=UTF8", "-classpath", classpath.toString(), properties_remote.getProperty("application", "com.hellonms.platforms.emp_orange.client_swt.Application"), "user.session=" + user_session_key, "emp.url=" + getUrl_client_swt(host), "emp.help=" + getUrl_help(host), "emp.language=" + language });
				Process process = builder.start();
				final InputStream in = process.getInputStream();
				final InputStream err = process.getErrorStream();
				new Thread(new Runnable() {
					@Override
					public void run() {
						byte[] buf = new byte[1024 * 8];
						try {
							for (int length = 0; 0 < (length = in.read(buf));) {
								if (blackBox.isEnabledFor(LEVEL.Fatal)) {
									String message = new String(buf, 0, length, System.getProperty("sun.jnu.encoding")).trim();
									if (0 < message.length()) {
										blackBox.log(LEVEL.Fatal, null, message);
									}
								}
							}
						} catch (Exception e) {
							if (blackBox.isEnabledFor(LEVEL.Fatal)) {
								blackBox.log(LEVEL.Fatal, null, e);
							}
						}
					}
				}).start();
				new Thread(new Runnable() {
					@Override
					public void run() {
						byte[] buf = new byte[1024 * 8];
						try {
							for (int length = 0; 0 < (length = err.read(buf));) {
								if (blackBox.isEnabledFor(LEVEL.Fatal)) {
									blackBox.log(LEVEL.Fatal, null, new String(buf, 0, length, System.getProperty("sun.jnu.encoding")));
								}
							}
						} catch (Exception e) {
							if (blackBox.isEnabledFor(LEVEL.Fatal)) {
								blackBox.log(LEVEL.Fatal, null, e);
							}
						}
					}
				}).start();
				process.waitFor();
			}
		} catch (Exception e) {
			if (blackBox.isEnabledFor(LEVEL.Fatal)) {
				blackBox.log(LEVEL.Fatal, null, e);
			}
		}
	}

	protected void validateAbout(Model4About about) throws EmpException {
		if (!about.getManufacturer().equals(EMP_ORANGE_DEFINE.MANUFACTURER) || !about.getOui().equals(EMP_ORANGE_DEFINE.OUI) || !about.getProduct_class().equals(EMP_ORANGE_DEFINE.PRODUCT_CLASS)) {
			throw new EmpException(ERROR_CODE_CORE.INVALID_CONFIG, about.getManufacturer(), about.getOui(), about.getProduct_class());
		}
	}

	protected File getProduct_home() {
		return new File(System.getProperty("user.home"), ".emp_orange");
	}

	protected String getLogin_image() {
		return "/com/hellonms/platforms/emp_orange/theme/public/emp_orange/image/page/security/login.png";
	}

	protected LANGUAGE[] getLanguages() {
		return new LANGUAGE[] { LANGUAGE.KOREAN, LANGUAGE.ENGLISH };
	}

	protected String[] getLanguage_folders() {
		return new String[] { "/com/hellonms/platforms/emp_core/share/language", "/com/hellonms/platforms/emp_onion/share/language", "/com/hellonms/platforms/emp_orange/share/language" };
	}

	protected String getProtocol() {
		return "http";
	}

	protected String getUrl_lib_client_swt(String host) {
		return new StringBuilder().append(getProtocol()).append("://").append(host).append("/lib_client_swt").toString();
	}

	protected String getUrl_help_about(String host) {
		return new StringBuilder().append(getProtocol()).append("://").append(host).append("/invoker/openapi/help/about.do").toString();
	}

	protected String getUrl_security_user_session(String host) {
		return new StringBuilder().append(getProtocol()).append("://").append(host).append("/invoker/openapi/security/user_session.do").toString();
	}

	protected String getLib_client_swt(String host) {
		return new StringBuilder().append(getProtocol()).append("://").append(host).append("/lib_client_swt").toString();
	}

	protected String getUrl_client_swt(String host) {
		return new StringBuilder().append(getProtocol()).append("://").append(host).append("/invoker/swt.do").toString();
	}

	protected String getUrl_help(String host) {
		try {
			URL url = new URL(new StringBuilder().append(getProtocol()).append("://").append(host).toString());
			return new StringBuilder().append(url.getProtocol()).append("://").append(url.getHost()).append(":").append(url.getPort()).append("/help/index.jsp").toString();
		} catch (Exception e) {
		}
		return new StringBuilder().append(getProtocol()).append("://").append(host).append("/help/index.jsp").toString();
	}

}
