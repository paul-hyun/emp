/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_util.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.hellonms.platforms.emp_core.share.error.ERROR_CODE_CORE;
import com.hellonms.platforms.emp_core.share.error.EmpException;

/**
 * <p>
 * 파일 Util
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 12.
 * @modified 2015. 5. 12.
 * @author cchyun
 *
 */
public class UtilFile {

	private static final int BUFF_SIZE = 1024 * 8;

	private static final ThreadLocal<byte[]> threadLocal = new ThreadLocal<byte[]>() {
		@Override
		protected byte[] initialValue() {
			return new byte[BUFF_SIZE];
		}
	};

	public static byte[] readFile(File file) throws EmpException {
		FileInputStream fis = null;
		try {
			int length = (int) file.length();

			byte[] fileBuf = new byte[length];

			fis = new FileInputStream(file);
			BufferedInputStream bis = new BufferedInputStream(fis);

			int offset = 0;
			while (offset < length) {
				offset += bis.read(fileBuf, offset, (length - offset));
			}

			return fileBuf;
		} catch (IOException e) {
			throw new EmpException(ERROR_CODE_CORE.FILE_IO, e);
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static String readFile(File file, String charset) throws EmpException {
		BufferedReader in = null;
		try {
			StringBuilder stringBuilder = new StringBuilder();
			in = new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));

			String str;
			while ((str = in.readLine()) != null) {
				stringBuilder.append(str).append(System.lineSeparator());
			}

			return stringBuilder.toString();
		} catch (IOException e) {
			throw new EmpException(ERROR_CODE_CORE.FILE_IO, e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void saveFile(byte[] fromFile, File toFile) throws EmpException {
		saveFile(new ByteArrayInputStream(fromFile), toFile);
	}

	public static void saveFile(InputStream fromFile, File toFile) throws EmpException {
		FileOutputStream fos = null;

		try {
			// 1. 파일복사 경로 존재유무 확인
			File parent = toFile.getAbsoluteFile().getParentFile();
			if (!parent.exists()) {
				parent.mkdirs();
			}

			// 2. 파일 복사 실행
			fos = new FileOutputStream(toFile);
			BufferedOutputStream bos = new BufferedOutputStream(fos);

			byte[] buf = threadLocal.get();
			for (int length = 0; 0 < (length = fromFile.read(buf));) {
				bos.write(buf, 0, length);
			}
			bos.flush();
			bos.close();
		} catch (IOException e) {
			throw new EmpException(ERROR_CODE_CORE.FILE_IO, e);
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static int deleteFile(File file, long timestamp) {
		int delete_count = 0;

		if (file.isFile()) {
			if (file.lastModified() < timestamp) {
				file.delete();
				delete_count++;
			}
		} else if (file.isDirectory()) {
			for (File fff : file.listFiles()) {
				delete_count += deleteFile(fff, timestamp);
			}
			if (file.listFiles().length == 0) {
				file.delete();
				delete_count++;
			}
		}

		return delete_count;
	}

}
