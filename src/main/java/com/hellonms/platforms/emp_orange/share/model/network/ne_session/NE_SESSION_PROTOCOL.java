/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.share.model.network.ne_session;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>
 * Insert description of NE_SESSION_CODE.java
 * </p>
 *
 * @since 1.6
 * @create 2015. 4. 13.
 * @modified 2015. 4. 13.
 * @author cchyun
 *
 */
@SuppressWarnings("serial")
public class NE_SESSION_PROTOCOL implements Serializable {

	private static final Map<Integer, NE_SESSION_PROTOCOL> protocl_code_map = new LinkedHashMap<Integer, NE_SESSION_PROTOCOL>();

	private static NE_SESSION_PROTOCOL[] protocl_codes;

	public static final NE_SESSION_PROTOCOL PROTOCOL_UNKNOWN = new NE_SESSION_PROTOCOL(0xFFFFFFFF, "UNKNOWN");

	public static NE_SESSION_PROTOCOL add(int protocl_code, String protocol) {
		NE_SESSION_PROTOCOL value = protocl_code_map.get(protocl_code);
		if (value != null) {
			throw new RuntimeException("protocl_code=" + protocl_code + ", protocol=" + protocol + " duplicate !!");
		}
		value = new NE_SESSION_PROTOCOL(protocl_code, protocol);
		protocl_code_map.put(protocl_code, value);
		return value;
	}

	public static NE_SESSION_PROTOCOL[] values() {
		if (protocl_codes == null) {
			protocl_codes = protocl_code_map.values().toArray(new NE_SESSION_PROTOCOL[0]);
		}
		return protocl_codes;
	}

	public static NE_SESSION_PROTOCOL valueOf(int protocl_code) {
		NE_SESSION_PROTOCOL value = protocl_code_map.get(protocl_code);
		return value == null ? NE_SESSION_PROTOCOL.PROTOCOL_UNKNOWN : value;
	}

	public static NE_SESSION_PROTOCOL valueOf(String protocol) {
		for (NE_SESSION_PROTOCOL ne_session_protocol : values()) {
			if (ne_session_protocol.protocol.equals(protocol)) {
				return ne_session_protocol;
			}
		}
		return null;
	}

	private int protocl_code;

	private String protocol;

	public NE_SESSION_PROTOCOL() {
		protocl_code = 0;
		protocol = "";
	}

	private NE_SESSION_PROTOCOL(int protocl_code, String protocol) {
		this.protocl_code = protocl_code;
		this.protocol = protocol;
	}

	public int getProtocl_code() {
		return protocl_code;
	}

	public String getProtocol() {
		return protocol;
	}

	@Override
	public String toString() {
		return protocol;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof NE_SESSION_PROTOCOL) {
			return (protocl_code == ((NE_SESSION_PROTOCOL) obj).protocl_code);
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return protocl_code;
	}

}
