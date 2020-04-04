/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.share.model.network.ne_session;

import com.hellonms.platforms.emp_core.share.model.ModelIf;

/**
 * <p>
 * NE 통신채널 모델 정의
 * </p>
 *
 * @since 1.6
 * @create 2015. 3. 10.
 * @modified 2015. 3. 10.
 * @author cchyun
 *
 */
public interface Model4NeSessionIf extends ModelIf {

	/**
	 * <p>
	 * NE ID 조회
	 * </p>
	 *
	 * @return
	 */
	public int getNe_id();

	/**
	 * <p>
	 * NE ID 저장
	 * </p>
	 *
	 * @param ne_id
	 */
	public void setNe_id(int ne_id);

	/**
	 * <p>
	 * 지원하는 프로토콜
	 * </p>
	 *
	 * @return
	 */
	public NE_SESSION_PROTOCOL getProtocol();

	/**
	 * <p>
	 * IP 주소 또는 DNS 이름
	 * </p>
	 *
	 * @return
	 */
	public String getHost();

	/**
	 * <p>
	 * IP 주소 또는 DNS 이름 저장
	 * </p>
	 *
	 * @param IP
	 *            주소 또는 DNS 이름
	 */
	public void setHost(String host);

	/**
	 * <p>
	 * 실제 IP 주소
	 * </p>
	 *
	 * @return
	 */
	public String getAddress();

	/**
	 * <p>
	 * 실제 IP 주소
	 * </p>
	 *
	 * @param address
	 */
	public void setAddress(String address);

	/**
	 * 모니터링 주기 설정]
	 * 
	 * @param session_check_period
	 */
	public void setSession_check_period(int session_check_period);

	/**
	 * <p>
	 * NE 통신 상태
	 * </p>
	 *
	 * @return
	 */
	public boolean isNe_session_state();

	/**
	 * <p>
	 * NE 통신 사용 상태
	 * </p>
	 *
	 * @return
	 */
	public boolean isAdmin_state();

	/**
	 * <p>
	 * NE 통신 사용 상태
	 * </p>
	 *
	 * @param admin_state
	 */
	public void setAdmin_state(boolean admin_state);

}
