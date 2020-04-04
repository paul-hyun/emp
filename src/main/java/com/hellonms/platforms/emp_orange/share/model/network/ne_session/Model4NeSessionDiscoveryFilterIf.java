/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.share.model.network.ne_session;

import com.hellonms.platforms.emp_core.share.model.ModelIf;

/**
 * <p>
 * NE 통신채널을 통한 NE 검색 조건
 * </p>
 *
 * @since 1.6
 * @create 2015. 3. 25.
 * @modified 2015. 3. 25.
 * @author cchyun
 *
 */
public interface Model4NeSessionDiscoveryFilterIf extends ModelIf {

	/**
	 * <p>
	 * 프로토콜 조회
	 * </p>
	 *
	 * @return
	 */
	public NE_SESSION_PROTOCOL getProtocol();

	public void setHost(String host);

	public void setCount(int count);

}
