/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_util.system;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.prefs.Preferences;

/**
 * <p>
 * 로컬 시스템 정보 Util
 * </p>
 *
 * @since 1.6
 * @create 2015. 4. 1.
 * @modified 2015. 4. 1.
 * @author cchyun
 *
 */
public class UtilSystem {

	public enum OS {
		WINDOWS, LINUX, UNKNOWN
	}

	public static OS getOS() {
		String os_name = System.getProperty("os.name").toLowerCase();
		if (0 <= os_name.indexOf("window")) {
			return OS.WINDOWS;
		} else if (0 <= os_name.indexOf("linux")) {
			return OS.LINUX;
		} else {
			return OS.UNKNOWN;
		}
	}

	public static int getOSArch() {
		return System.getProperty("os.arch").contains("64") ? 64 : 32;
	}

	public static boolean isRoot() {
		PrintStream err = System.err;
		try {
			System.setErr(new PrintStream(new OutputStream() {
				public void write(int b) {
				}
			}));

			Preferences preferences = Preferences.systemRoot();
			preferences.put("foo", "bar");
			preferences.remove("foo");
			preferences.flush();
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			System.setErr(err);
		}
	}

}
