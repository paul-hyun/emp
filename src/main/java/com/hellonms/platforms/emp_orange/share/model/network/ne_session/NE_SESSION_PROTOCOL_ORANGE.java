/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.share.model.network.ne_session;

/**
 * <p>
 * 기본 통신 프로토콜
 * </p>
 *
 * @since 1.6
 * @create 2015. 4. 13.
 * @modified 2015. 4. 13.
 * @author cchyun
 *
 */
public class NE_SESSION_PROTOCOL_ORANGE {

	public static final NE_SESSION_PROTOCOL NONE = NE_SESSION_PROTOCOL.add(0x00002000, "NONE");

	public static final NE_SESSION_PROTOCOL ICMP = NE_SESSION_PROTOCOL.add(0x00002100, "ICMP");

	public static final NE_SESSION_PROTOCOL SNMP = NE_SESSION_PROTOCOL.add(0x00002200, "SNMP");

}
