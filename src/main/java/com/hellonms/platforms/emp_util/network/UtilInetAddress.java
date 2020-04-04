/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_util.network;

import com.hellonms.platforms.emp_core.share.error.ERROR_CODE_CORE;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_util.cache.UtilCache;

import java.io.IOException;
import java.net.InetAddress;

/**
 * <p>
 * InetAddress 유틸
 * </p>
 *
 * @since 1.6
 * @create 2015. 4. 3.
 * @modified 2015. 4. 3.
 * @author cchyun
 *
 */
public class UtilInetAddress {

	private static String CACHE_KEY = UtilInetAddress.class.getName();
	static {
		UtilCache.buildCache(CACHE_KEY, 256, 3600);
	}

	public static InetAddress[] getInetAddresses(InetAddress address, int count) throws EmpException {
		try {
			InetAddress[] addresses = new InetAddress[count];

			byte[] addr = address.getAddress();
			for (int i = 0; i < addresses.length; i++) {
				addresses[i] = InetAddress.getByAddress(addr);

				for (int j = 0; j < addr.length; j++) {
					int value = (addr[addr.length - 1 - j] & 0x000000FF);
					if (value == 0x000000FF) {
						addr[addr.length - 1 - j] = 0;
					} else {
						addr[addr.length - 1 - j] = (byte) (value + 1);
						break;
					}
				}
			}
			return addresses;
		} catch (IOException e) {
			throw new EmpException(ERROR_CODE_CORE.NETOWRK_IO, e);
		}
	}

	public static InetAddress getInetAddresses(String host) throws EmpException {
		try {
			InetAddress address = (InetAddress) UtilCache.get(CACHE_KEY, host);
			if (address == null) {
				address = InetAddress.getByName(host);
				UtilCache.put(CACHE_KEY, host, address);
			}
			return address;
		} catch (IOException e) {
			throw new EmpException(ERROR_CODE_CORE.NETOWRK_IO, e);
		}
	}

}
