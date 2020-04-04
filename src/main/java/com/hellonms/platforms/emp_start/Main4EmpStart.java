/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_start;

import java.io.File;

import com.hellonms.platforms.emp_core.server.log.BlackBox;
import com.hellonms.platforms.emp_core.server.log.LEVEL;
import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.share.error.ERROR_CODE;
import com.hellonms.platforms.emp_core.share.error.EmpException;

/**
 * <p>
 * EMP 실행 (java main)
 * </p>
 * 
 * @since 1.6
 * @create 2015. 3. 10.
 * @modified 2015. 3. 10.
 * @author cchyun
 * 
 */
public class Main4EmpStart {

	private static final BlackBox blackBox = new BlackBox(Main4EmpStart.class);

	public static void main(String[] args) {
		start(args);
	}

	/**
	 * <p>
	 * 데몬기능 (시작)
	 * </p>
	 * 
	 * @param args
	 */
	public static void start(String[] args) {
		String emp_home = System.getProperty("user.dir");
		String product_name = "eOrange";

		for (String arg : args) {
			if (arg.startsWith("emp.home=")) {
				emp_home = new File(arg.substring("emp.home=".length())).getAbsolutePath();
			} else if (arg.startsWith("product.name=")) {
				product_name = arg.substring("product.name=".length());
			}
		}

		try {
			if (blackBox.isEnabledFor(LEVEL.Fatal)) {
				blackBox.log(LEVEL.Fatal, null, "########################################  EMP  START  ########################################");
			}

			Initializer4Emp.initialize(emp_home, product_name, true);

			if (blackBox.isEnabledFor(LEVEL.Fatal)) {
				blackBox.log(LEVEL.Fatal, null, "######################################## EMP START OK ########################################");
			}
		} catch (EmpException e) {
			if (blackBox.isEnabledFor(LEVEL.Fatal)) {
				blackBox.log(LEVEL.Fatal, null, e);
			}
			System.exit(1);
		} catch (Exception e) {
			if (blackBox.isEnabledFor(LEVEL.Fatal)) {
				blackBox.log(LEVEL.Fatal, null, e);
			}
			System.exit(1);
		}
	}

	/**
	 * <p>
	 * 데몬기능 (정지)
	 * </p>
	 * 
	 * @param args
	 * @throws EmpException
	 */
	public static void stop(String[] args) {
		try {
			dispose();
		} catch (EmpException e) {
			if (blackBox.isEnabledFor(LEVEL.Fatal)) {
				blackBox.log(LEVEL.Fatal, null, e);
			}
		} catch (Exception e) {
			if (blackBox.isEnabledFor(LEVEL.Fatal)) {
				blackBox.log(LEVEL.Fatal, null, e);
			}
		}
	}

	/**
	 * <p>
	 * 종료
	 * </p>
	 * 
	 * @throws EmpException
	 * 
	 */
	private static void dispose() throws EmpException {
		EmpContext context = new EmpContext(null);

		try {
			Initializer4Emp.dispose(context);

			context.commit();
		} catch (EmpException e) {
			throw e;
		} catch (Exception e) {
			context.rollback();

			throw new EmpException(e, ERROR_CODE.ERROR_UNKNOWN);
		} finally {
			context.close();
		}
	}

}
