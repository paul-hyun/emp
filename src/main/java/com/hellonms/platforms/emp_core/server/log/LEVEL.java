/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_core.server.log;

import org.slf4j.spi.LocationAwareLogger;

public enum LEVEL {
	/**
	 * 아주 중요한 로그레벨 (가장 높음)
	 */
	Fatal(LocationAwareLogger.ERROR_INT),

	/**
	 * 안정화가 끝난 후 상용화 단계에서 확인할 로그
	 */
	Site(LocationAwareLogger.ERROR_INT),

	/**
	 * 검수 완료 후 안정화 단계에서 확인할 로그
	 */
	Deploy(LocationAwareLogger.WARN_INT),

	/**
	 * 개발 완료 후 검수 단계에서 확인할 로그
	 */
	UseCase(LocationAwareLogger.INFO_INT),

	/**
	 * 개발자가 개발완료 이전 시험 단계에서 확인할 로그
	 */
	Component(LocationAwareLogger.DEBUG_INT),

	/**
	 * 모든 동작을 학인할 수 있는 로그 (가장 낮음)
	 */
	Trace(LocationAwareLogger.TRACE_INT);

	public final int level;

	private LEVEL(int level) {
		this.level = level;
	}

}