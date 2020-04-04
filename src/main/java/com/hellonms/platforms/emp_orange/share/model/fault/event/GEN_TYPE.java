/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.share.model.fault.event;

/**
 * <p>
 * Event 발생 타입
 * </p>
 *
 * @since 1.6
 * @create 2015. 4. 23.
 * @modified 2015. 4. 23.
 * @author cchyun
 *
 */
public enum GEN_TYPE {

	/**
	 * 장비로부터 수신한 경우
	 */
	AUTO,

	/**
	 * 로직에 의해서 발생한 경우
	 */
	SERVICE,

	/**
	 * 사용자에 의해서 발생한 경우
	 */
	MANUAL;

}
