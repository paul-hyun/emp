package com.hellonms.platforms.emp_security.opened;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AESCrypto {

	public static byte[] encrypt(String key, byte[] buf) {
		Key skey = makeAESKey(key);

		buf = padding16(buf);
		byte[] enc = aesEncode(buf, skey);

		return enc;
	}

	public static String encrypt(String key, String text) {
		Key skey = makeAESKey(key);

		text = padding16(text);
		byte[] enc = aesEncode(text.getBytes(), skey);

		return byte2hex(enc);
	}

	public static byte[] decrypt(String key, byte[] buf) {
		Key skey = makeAESKey(key);
		byte[] dec = aesDecode(buf, skey);

		return removePadding(dec);
	}

	public static String decrypt(String key, String text) {
		Key skey = makeAESKey(key);
		byte[] enc = hexToByteArray(text);
		byte[] dec = aesDecode(enc, skey);
		String data = new String(dec);

		return removePadding(data);
	}

	private static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
		}
		return hs;
	}

	private static byte[] hexToByteArray(String hex) {
		if (hex == null || hex.length() == 0) {
			return null;
		}

		byte[] ba = new byte[hex.length() / 2];
		for (int i = 0; i < ba.length; i++) {
			ba[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
		}
		return ba;
	}

	private static Key makeAESKey(String sKey) {
		final byte[] key = sKey.getBytes();
		return new SecretKeySpec(key, "AES");
	}

	private static byte[] aesEncode(byte[] src, Key skey) {
		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
			cipher.init(Cipher.ENCRYPT_MODE, skey);
			return cipher.doFinal(src);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static byte[] aesDecode(byte[] src, Key skey) {
		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
			cipher.init(Cipher.DECRYPT_MODE, skey);
			return cipher.doFinal(src);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static byte[] padding16(byte[] buf) {
		ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
		try {
			byteArray.write(buf);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		int nCount = 16 - (buf.length % 16);
		for (int i = 0; i < nCount; i++) {
			byteArray.write(' ');
		}
		return byteArray.toByteArray();
	}

	private static String padding16(String string) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(string);

		int nCount = 16 - (string.getBytes().length % 16);
		for (int i = 0; i < nCount; i++) {
			stringBuilder.append(' ');
		}
		return stringBuilder.toString();
	}

	private static byte[] removePadding(byte[] buf) {
		return buf;
	}

	private static String removePadding(String string) {
		String result = null;

		if (string.indexOf(" ") == -1) {
			result = string;
		} else {
			result = string.trim();
		}
		return result;
	}

}
