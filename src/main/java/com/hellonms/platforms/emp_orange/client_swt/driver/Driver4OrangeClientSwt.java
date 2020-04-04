/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.client_swt.driver;

import java.net.SocketTimeoutException;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.remoting.RemoteAccessException;
import org.springframework.remoting.RemoteConnectFailureException;
import org.springframework.remoting.httpinvoker.HttpComponentsHttpInvokerRequestExecutor;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;

import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_orange.client_swt.ApplicationProperty;
import com.hellonms.platforms.emp_orange.client_swt.servlet.Invoker4OrangeClientSwtIf;
import com.hellonms.platforms.emp_orange.client_swt.servlet.Invoker4OrangeClientSwtReqeust;
import com.hellonms.platforms.emp_orange.share.error.ERROR_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.security.user_session.Model4UserSession;
import com.hellonms.platforms.emp_util.http.UtilHttp;

/**
 * <p>
 * SWT Client Invoker
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 5. 19.
 * @author cchyun
 *
 */
public class Driver4OrangeClientSwt {

	public interface Driver4OrangeClientSwtTaskIf {

		public Object run(Invoker4OrangeClientSwtIf invoker, Invoker4OrangeClientSwtReqeust request) throws EmpException;

	}

	private static final Driver4OrangeClientSwt instance = new Driver4OrangeClientSwt();

	/**
	 * 인스턴스를 반환합니다. (싱글톤)
	 * 
	 * @return 인스턴스
	 */
	public static Driver4OrangeClientSwt getInstance() {
		return instance;
	}

	public static void setInvoker(Invoker4OrangeClientSwtIf invoker) {
		instance.invoker = invoker;
	}

	private String serviceUrl = "https://127.0.0.1:8443/invoker/swt.do";

	private Class<? extends Invoker4OrangeClientSwtIf> serviceInterface = Invoker4OrangeClientSwtIf.class;

	private Invoker4OrangeClientSwtIf invoker;

	private Invoker4OrangeClientSwtReqeust request = new Invoker4OrangeClientSwtReqeust();

	private final ReentrantLock lock = new ReentrantLock();

	private Driver4OrangeClientSwt() {
	}

	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}

	public void setServiceInterface(Class<? extends Invoker4OrangeClientSwtIf> serviceInterface) {
		this.serviceInterface = serviceInterface;
	}

	private Invoker4OrangeClientSwtIf getInvoker() {
		if (invoker == null) {
			try {
				HttpInvokerProxyFactoryBean factory = new HttpInvokerProxyFactoryBean();
				factory.setServiceUrl(serviceUrl);
				factory.setServiceInterface(serviceInterface);
				factory.afterPropertiesSet();
				factory.setHttpInvokerRequestExecutor(new HttpComponentsHttpInvokerRequestExecutor(UtilHttp.newHttpClientNoSequerty()));
				invoker = (Invoker4OrangeClientSwtIf) factory.getObject();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return invoker;
	}

	public void setUser_session_key(String user_session_key) {
		if (user_session_key != null) {
			request.setProperty(Invoker4OrangeClientSwtReqeust.USER_SESSION_KEY, user_session_key);
		}
	}

	/**
	 * 사용자 세션을 조회합니다.
	 * 
	 * @return 사용자 세션
	 * @throws EmpException
	 */
	public boolean testUserSession() {
		try {
			long server_timestamp = (Long) Driver4OrangeClientSwt.getInstance().execute(new Driver4OrangeClientSwtTaskIf() {
				@Override
				public Object run(Invoker4OrangeClientSwtIf invoker, Invoker4OrangeClientSwtReqeust request) throws EmpException {
					return invoker.testSession(request);
				}
			});
			long time_diff = System.currentTimeMillis() - server_timestamp;
			ApplicationProperty.setTime_diff(time_diff);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 사용자 세션을 조회합니다.
	 * 
	 * @return 사용자 세션
	 * @throws EmpException
	 */
	public Model4UserSession queryUserSession() throws EmpException {
		try {
			lock.lock();

			Invoker4OrangeClientSwtIf invoker = getInvoker();

			return invoker.queryUserSession(request);
		} finally {
			lock.unlock();
		}
	}

	public String login(String user_account, String password) throws EmpException {
		try {
			lock.lock();

			Invoker4OrangeClientSwtIf invoker = getInvoker();

			String user_session_key = invoker.login(request, user_account, password);
			request.setProperty(Invoker4OrangeClientSwtReqeust.USER_SESSION_KEY, user_session_key);
			return user_session_key;
		} catch (RemoteConnectFailureException e) {
			throw new EmpException(e, ERROR_CODE_ORANGE.NETOWRK_IO, serviceUrl);
		} finally {
			lock.unlock();
		}
	}

	/**
	 * 로그아웃을 합니다.
	 * 
	 * @throws EmpException
	 */
	public void logout() throws EmpException {
		try {
			lock.lock();

			Invoker4OrangeClientSwtIf invoker = getInvoker();

			invoker.logout(request);
			request.setProperty(Invoker4OrangeClientSwtReqeust.USER_SESSION_KEY, "");
		} finally {
			lock.unlock();
		}
	}

	/**
	 * 작업을 실행합니다.
	 * 
	 * @param task
	 *            작업
	 * @return 작업 실행 결과
	 * @throws EmpException
	 */
	public Object execute(Driver4OrangeClientSwtTaskIf task) throws EmpException {
		try {
			// lock.lock();

			Invoker4OrangeClientSwtIf invoker = getInvoker();

			return task.run(invoker, request);
		} catch (RemoteConnectFailureException e) {
			throw new EmpException(e, ERROR_CODE_ORANGE.NETOWRK_IO, serviceUrl);
		} catch (RemoteAccessException e) {
			if (e.getCause() instanceof SocketTimeoutException) {
				throw new EmpException(e, ERROR_CODE_ORANGE.READ_TIMEOUT);
			}
			throw new EmpException(e, ERROR_CODE_ORANGE.NETOWRK_IO, serviceUrl);
		} finally {
			// lock.unlock();
		}
	}

}
