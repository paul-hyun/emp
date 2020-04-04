/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_core.share.error;

import com.hellonms.platforms.emp_core.share.language.UtilLanguage;

/**
 * <p>
 * 예외 발생
 * </p>
 * 
 * @since 1.6
 * @create 2015. 3. 10.
 * @modified 2015. 3. 10.
 * @author cchyun
 * 
 */
@SuppressWarnings("serial")
public class EmpException extends Exception {

	private ERROR_CODE error_code;

	private String[] args;

	public EmpException(ERROR_CODE error_code, Object... args) {
		super();

		this.error_code = error_code;
		this.args = new String[args.length];
		for (int i = 0; i < args.length; i++) {
			this.args[i] = String.valueOf(args[i]);
		}
	}

	public EmpException(Throwable cause, ERROR_CODE error_code, Object... args) {
		super(cause);

		this.error_code = error_code;
		this.args = new String[args.length];
		for (int i = 0; i < args.length; i++) {
			this.args[i] = String.valueOf(args[i]);
		}
	}

	public ERROR_CODE getError_code() {
		return error_code;
	}

	public void setError_code(ERROR_CODE error_code) {
		this.error_code = error_code;
	}

	public String[] getArgs() {
		return args;
	}

	public void setArgs(String[] args) {
		this.args = args;
	}

	@Override
	public String getMessage() {
		Object[] mmm = new Object[args.length];
		for (int i = 0; i < mmm.length; i++) {
			mmm[i] = UtilLanguage.getMessage(args[i]);
		}
		return UtilLanguage.getMessage(error_code, (Object[]) mmm);
	}

}
