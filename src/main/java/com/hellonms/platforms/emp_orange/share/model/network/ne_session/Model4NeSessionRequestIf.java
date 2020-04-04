/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.share.model.network.ne_session;

import com.hellonms.platforms.emp_core.share.model.ModelIf;

/**
 * <p>
 * NE 통신채널 명령 응답 정의
 * </p>
 *
 * @since 1.6
 * @create 2015. 4. 7.
 * @modified 2015. 4. 7.
 * @author cchyun
 *
 */
public interface Model4NeSessionRequestIf extends ModelIf {

	/**
	 * <p>
	 * 지원하는 프로토콜
	 * </p>
	 *
	 * @return
	 */
	public NE_SESSION_PROTOCOL getProtocol();

}
