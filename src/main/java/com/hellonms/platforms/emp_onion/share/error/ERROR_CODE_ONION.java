/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_onion.share.error;

import com.hellonms.platforms.emp_core.share.error.ERROR_CODE;
import com.hellonms.platforms.emp_core.share.error.ERROR_CODE_CORE;

/**
 * <p>
 * Onion에서 발생하는 오류 정의
 * </p>
 * 
 * @since 1.6
 * @create 2015. 3. 10.
 * @modified 2015. 3. 10.
 * @author cchyun
 * 
 */
public class ERROR_CODE_ONION extends ERROR_CODE_CORE {

	/**
	 * 자원을 초과하여 사용한 경우
	 */
	public static final ERROR_CODE RESOURCE_OVERFLOW = ERROR_CODE.add(0x00001001, "ERROR_ONION_RESOURCE_OVERFLOW");

	/**
	 * SQL 오류 발생
	 */
	public static final ERROR_CODE SQL_EXCEPTION = ERROR_CODE.add(0x00001002, "ERROR_ONION_SQL_EXCEPTION");

	/**
	 * 모델이 존재하지 않음
	 */
	public static final ERROR_CODE MODEL_NOTEXISTS = ERROR_CODE.add(0x00001003, "ERROR_ONION_MODEL_NOTEXISTS");

	/**
	 * 동일한 모델이 이미 존재 함
	 */
	public static final ERROR_CODE MODEL_ALREADYEXISTS = ERROR_CODE.add(0x00001004, "ERROR_ONION_MODEL_ALREADYEXISTS");

	/**
	 * 모델 수정이 허용되지 않음
	 */
	public static final ERROR_CODE MODEL_DENYUPDATE = ERROR_CODE.add(0x00001005, "ERROR_ONION_MODEL_DENYUPDATE");

	/**
	 * 모델 삭제가 허용되지 않음
	 */
	public static final ERROR_CODE MODEL_DENYDELETE = ERROR_CODE.add(0x00001006, "ERROR_ONION_MODEL_DENYDELETE");

	/**
	 * 모델 삭제가 허용되지 않음
	 */
	public static final ERROR_CODE MODEL_STATE = ERROR_CODE.add(0x00001007, "ERROR_ONION_MODEL_STATE");

	/**
	 * 동일한 모델이 이미 존재 함
	 */
	public static final ERROR_CODE MODEL_CHILDEXISTS = ERROR_CODE.add(0x00001008, "ERROR_ONION_MODEL_CHILDEXISTS");

}
