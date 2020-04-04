/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_start;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.server.ssl.SslSelectChannelConnector;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.webapp.WebAppContext;

import com.hellonms.platforms.emp_core.server.log.BlackBox;
import com.hellonms.platforms.emp_core.server.log.LEVEL;
import com.hellonms.platforms.emp_util.string.UtilString;

/**
 * <p>
 * jetty를 통해 EMP 실행
 * </p>
 * 
 * @since 1.6
 * @create 2015. 3. 10.
 * @modified 2015. 3. 10.
 * @author cchyun
 * 
 */
public class Main4JettyStart {

	private static AtomicBoolean running = new AtomicBoolean(true);

	private static final BlackBox blackBox = new BlackBox(Main4JettyStart.class);

	private static final int PORT = 20082;

	public static void main(String[] args) throws Exception {
		if (args.length == 1 && args[0].equals("stop")) {
			sendCmd("stop");
		} else if (lock()) {
			try {
				start(args);
			} catch (Exception e) {
				if (blackBox.isEnabledFor(LEVEL.Fatal)) {
					blackBox.log(LEVEL.Fatal, null, e);
				}
			}
		}
	}

	private static boolean lock() {
		try {
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private static boolean sendCmd(String cmd) {
		try {
			Socket socket = new Socket("127.0.0.1", PORT);
			socket.getOutputStream().write(cmd.getBytes());
			socket.getOutputStream().flush();
			socket.close();
		} catch (Exception e1) {
			if (blackBox.isEnabledFor(LEVEL.Fatal)) {
				blackBox.log(LEVEL.Fatal, null, e1);
			}
		}
		return false;
	}

	public static void start(String[] args) throws Exception {
		try {
			if (blackBox.isEnabledFor(LEVEL.Fatal)) {
				blackBox.log(LEVEL.Fatal, null, "starting ...");
			}

			SAXReader reader = new SAXReader();
			Document document = reader.read("emp_server.xml");
			Element elementRoot = document.getRootElement();

			Thread.sleep(5000L); // 시작대기 DB가 살아나는 시간 대기

			Server server = new Server();

			Element elementHttp = elementRoot.element("http");
			if (elementHttp != null) {
				int port = Integer.parseInt(elementHttp.elementText("port"));
				if (blackBox.isEnabledFor(LEVEL.Fatal)) {
					blackBox.log(LEVEL.Fatal, null, UtilString.format("http port = {}", port));
				}
				try {
					ServerSocket serverSocket = new ServerSocket(port);
					serverSocket.close();
				} catch (Exception e) {
					throw new Exception(UtilString.format("Another program already uses port {}", port));
				}

				SelectChannelConnector selectChannelConnector = new SelectChannelConnector();
				selectChannelConnector.setPort(port);
				server.addConnector(selectChannelConnector);
			}

			Element elementHttps = elementRoot.element("https");
			if (elementHttps != null) {
				int port = Integer.parseInt(elementHttps.elementText("port"));
				if (blackBox.isEnabledFor(LEVEL.Fatal)) {
					blackBox.log(LEVEL.Fatal, null, UtilString.format("https port = {}", port));
				}
				try {
					ServerSocket serverSocket = new ServerSocket(port);
					serverSocket.close();
				} catch (Exception e) {
					throw new Exception(UtilString.format("Another program already uses port {}", port));
				}

				SslContextFactory sslContextFactory = new SslContextFactory();
				sslContextFactory.setKeyStorePath(elementHttps.elementText("key_store"));
				sslContextFactory.setKeyStorePassword(elementHttps.elementText("key_store_password"));

				SslSelectChannelConnector sslSelectChannelConnector = new SslSelectChannelConnector(sslContextFactory);
				sslSelectChannelConnector.setPort(port);
				server.addConnector(sslSelectChannelConnector);
			}

			List<Handler> handlerList = new ArrayList<Handler>();
			for (Object object : elementRoot.elements("context")) {
				Element elementContext = (Element) object;

				WebAppContext webAppContext = new WebAppContext();
				webAppContext.setParentLoaderPriority(true);
				webAppContext.setContextPath(elementContext.elementText("path"));
				webAppContext.setWar(elementContext.elementText("war"));

				if (blackBox.isEnabledFor(LEVEL.Fatal)) {
					blackBox.log(LEVEL.Fatal, null, UtilString.format("{} at {}", elementContext.elementText("path"), elementContext.elementText("war")));
				}

				handlerList.add(webAppContext);
			}
			ContextHandlerCollection contexts = new ContextHandlerCollection();
			contexts.setHandlers(handlerList.toArray(new Handler[0]));
			server.setHandler(contexts);

			server.start();

			while (running.get()) {
				Thread.sleep(1000L);
			}
		} catch (Throwable e) {
			if (blackBox.isEnabledFor(LEVEL.Fatal)) {
				blackBox.log(LEVEL.Fatal, null, e);
			}
		} finally {
			System.exit(0);
		}
	}

	public static void stop(String[] args) throws Exception {
		if (blackBox.isEnabledFor(LEVEL.Fatal)) {
			blackBox.log(LEVEL.Fatal, null, UtilString.format("stoping..."));
		}

		running.set(false);
	}

}
