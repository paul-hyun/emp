/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.share.error;

import com.hellonms.platforms.emp_core.share.error.ERROR_CODE;
import com.hellonms.platforms.emp_onion.share.error.ERROR_CODE_ONION;

/**
 * <p>
 * Orange에서 발생하는 오류 정의
 * </p>
 * 
 * @since 1.6
 * @create 2015. 3. 10.
 * @modified 2015. 3. 10.
 * @author cchyun
 * 
 */
public class ERROR_CODE_ORANGE extends ERROR_CODE_ONION {

	/**
	 * 이미 사용자가 로그인 되어 있음
	 */
	public static final ERROR_CODE USER_ALREADYLOGIN = ERROR_CODE.add(0x00002001, "ERROR_ORANGE_USER_ALREADYLOGIN");

	/**
	 * 로그인 되지 않은 사용자
	 */
	public static final ERROR_CODE USER_NOTLOGIN = ERROR_CODE.add(0x00002002, "ERROR_ORANGE_USER_NOTLOGIN");

	/**
	 * 로그인 실패
	 */
	public static final ERROR_CODE USER_LOGINFAIL = ERROR_CODE.add(0x00002003, "ERROR_ORANGE_USER_LOGINFAIL");;

	/**
	 * 통신채널 명령어 수행 실패
	 */
	public static final ERROR_CODE NESESSION_EXECUTEFAIL = ERROR_CODE.add(0x00002004, "ERROR_ORANGE_NESESSION_EXECUTEFAIL");

	/**
	 * 등록된 SNMP 타입 없음
	 */
	public static final ERROR_CODE NESESSION_NOTYPE = ERROR_CODE.add(0x00002005, "ERROR_ORANGE_NESESSION_NOTYPE");
	
	/**
	 * 응답시간 초과
	 */
	public static final ERROR_CODE READ_TIMEOUT = ERROR_CODE.add(0x00002006, "ERROR_ORANGE_READ_TIMEOUT");

}
