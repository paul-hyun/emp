/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_core.server.log;

import org.slf4j.LoggerFactory;
import org.slf4j.spi.LocationAwareLogger;

import com.hellonms.platforms.emp_core.server.transaction.EmpContext;

/**
 * <p>
 * 로그 라이브러리
 * </p>
 * 
 * @since 1.6
 * @create 2015. 3. 10.
 * @modified 2015. 3. 10.
 * @author cchyun
 */
public final class BlackBox {

	private static final String FQCN = BlackBox.class.getName();

	/**
	 * log4j Logger
	 */
	private final LocationAwareLogger logger;

	/**
	 * <p>
	 * 생성자
	 * </p>
	 * 
	 * @param clazz
	 */
	public BlackBox(Class<?> clazz) {
		logger = (LocationAwareLogger) LoggerFactory.getLogger(clazz);
	}

	/**
	 * <p>
	 * 로그 가능여부 확인
	 * </p>
	 * 
	 * @param fatal
	 * @return
	 */
	public boolean isEnabledFor(LEVEL level) {
		switch (level) {
		case Fatal:
			return logger.isErrorEnabled();
		case Site:
			return logger.isErrorEnabled();
		case Deploy:
			return logger.isWarnEnabled();
		case UseCase:
			return logger.isInfoEnabled();
		case Component:
			return logger.isDebugEnabled();
		case Trace:
			return logger.isTraceEnabled();
		default:
			return false;
		}
	}

	/**
	 * <p>
	 * 로그 저장
	 * </p>
	 * 
	 * @param level
	 * @param context
	 * @param message
	 */
	public void log(LEVEL level, EmpContext context, String message) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(context == null ? "                " : context.getTransaction_id_string()).append(" | ").append(message).append(" | ");
		logger.log(null, FQCN, level.level, stringBuilder.toString(), null, null);
	}

	/**
	 * <p>
	 * 로그 저장
	 * </p>
	 * 
	 * @param level
	 * @param context
	 * @param e
	 */
	public void log(LEVEL level, EmpContext context, Throwable e) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(context == null ? "                " : context.getTransaction_id_string()).append(" | ");
		logger.log(null, FQCN, level.level, stringBuilder.toString(), null, e);
	}

	/**
	 * <p>
	 * 로그 저장
	 * </p>
	 * 
	 * @param level
	 * @param context
	 * @param e
	 * @param message
	 */
	public void log(LEVEL level, EmpContext context, Throwable e, String message) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(context == null ? "                " : context.getTransaction_id_string()).append(" | ").append(message).append(" | ");
		logger.log(null, FQCN, level.level, stringBuilder.toString(), null, e);
	}

}
