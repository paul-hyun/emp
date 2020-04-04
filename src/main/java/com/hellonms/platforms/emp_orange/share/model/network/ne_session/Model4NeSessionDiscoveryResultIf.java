/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.share.model.network.ne_session;

import com.hellonms.platforms.emp_core.share.model.ModelIf;

/**
 * <p>
 * NE 통신채널을 통한 NE 검색 결과
 * </p>
 *
 * @since 1.6
 * @create 2015. 3. 25.
 * @modified 2015. 3. 25.
 * @author cchyun
 *
 */
public interface Model4NeSessionDiscoveryResultIf extends ModelIf {

	/**
	 * <p>
	 * 프로토콜 조회
	 * </p>
	 *
	 * @return
	 */
	public NE_SESSION_PROTOCOL getProtocol();

	/**
	 * <p>
	 * 검색 NE 주소
	 * </p>
	 *
	 * @return
	 */
	public String getAddress();

	/**
	 * <p>
	 * 성공여부
	 * </p>
	 *
	 * @return
	 */
	public boolean isSuccess();

	/**
	 * <p>
	 * 검색된 NE의 구분자
	 * </p>
	 *
	 * @return
	 */
	public String getNe_oid();

	/**
	 * <p>
	 * 검색된 NE 이름 (결정할 수 없으면 null)
	 * </p>
	 *
	 * @return
	 */
	public String getNe_name();

	/**
	 * <p>
	 * 검색된 NE 설명 (결정할 수 없으면 null)
	 * </p>
	 *
	 * @return
	 */
	public String getDescription();

	/**
	 * <p>
	 * 검색된 NE의 통신채널로 변환
	 * </p>
	 *
	 * @return
	 */
	public Model4NeSessionIf toNeSession();

}
