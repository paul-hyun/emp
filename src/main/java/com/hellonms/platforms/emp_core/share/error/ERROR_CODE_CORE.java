/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_core.share.error;

/**
 * <p>
 * 코어에서 발생하는 오류 정의
 * </p>
 * 
 * @since 1.6
 * @create 2015. 3. 10.
 * @modified 2015. 3. 10.
 * @author cchyun
 * 
 */
public class ERROR_CODE_CORE {

	/**
	 * FILE IO ERROR
	 */
	public static final ERROR_CODE FILE_IO = ERROR_CODE.add(0x00000001, "ERROR_CORE_FILE_IO");

	/**
	 * 폴더가 아닌 경우
	 */
	public static final ERROR_CODE NOT_FOLDER = ERROR_CODE.add(0x00000002, "ERROR_CORE_NOT_FOLDER");

	/**
	 * 파일이 아닌 경우
	 */
	public static final ERROR_CODE NOT_FILE = ERROR_CODE.add(0x00000003, "ERROR_CORE_NOT_FILE");

	/**
	 * NETWORK IO ERROR
	 */
	public static final ERROR_CODE NETOWRK_IO = ERROR_CODE.add(0x00000004, "ERROR_CORE_NETOWRK_IO");

	/**
	 * Configruation ERROR
	 */
	public static final ERROR_CODE INVALID_CONFIG = ERROR_CODE.add(0x00000005, "ERROR_CORE_INVALID_CONFIG");

	/**
	 * License ERROR
	 */
	public static final ERROR_CODE INVALID_LICENSE = ERROR_CODE.add(0x00000006, "ERROR_CORE_INVALID_LICENSE");

	/**
	 * Workflow 중복 등록
	 */
	public static final ERROR_CODE WORKFLOW_DUPLICATE = ERROR_CODE.add(0x00000007, "ERROR_CORE_WORKFLOW_DUPLICATE");

	/**
	 * Workflow 존재하지 않음
	 */
	public static final ERROR_CODE WORKFLOW_NOTEXISTS = ERROR_CODE.add(0x00000008, "ERROR_CORE_WORKFLOW_NOTEXISTS");

	/**
	 * Workflow에 적당한 컴포넌트가 설정되지 않은 경우
	 */
	public static final ERROR_CODE WORKFLOW_NOTSETTED = ERROR_CODE.add(0x00000009, "ERROR_CORE_WORKFLOW_NOTSETTED");
	
	/**
	 * 서버 초기화가 완료되지 않은 경우
	 */
	public static final ERROR_CODE SERVER_NOTREADY = ERROR_CODE.add(0x0000000A, "ERROR_CORE_SERVER_NOTREADY");

	/**
	 * 허용된 자원을 초과한 경우
	 */
	public static final ERROR_CODE RESOURCE_OVERFLOW = ERROR_CODE.add(0x0000000B, "ERROR_CORE_RESOURCE_OVERFLOW");

}
