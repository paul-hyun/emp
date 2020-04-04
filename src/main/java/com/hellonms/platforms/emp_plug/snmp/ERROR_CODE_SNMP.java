/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_plug.snmp;

import com.hellonms.platforms.emp_core.share.error.ERROR_CODE;

/**
 * <p>
 * SNMP ERROR_CODE
 * </p>
 *
 * @since 1.6
 * @create 2015. 4. 6.
 * @modified 2015. 4. 6.
 * @author cchyun
 *
 */
public class ERROR_CODE_SNMP {

	public static final ERROR_CODE ERROR_SNMP_RESPONSE_TIMEOUT = ERROR_CODE.add(0x0000F101, "ERROR_SNMP_RESPONSE_TIMEOUT");

	public static final ERROR_CODE ERROR_SNMP_RESPONSE_ERROR = ERROR_CODE.add(0x0000F102, "ERROR_SNMP_RESPONSE_ERROR");

	public static final ERROR_CODE ERROR_SNMP_RESPONSE_NULL = ERROR_CODE.add(0x0000F103, "ERROR_SNMP_RESPONSE_NULL");

}
