/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_start;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * <p>
 * Servlet을 통해 EMP 실행
 * </p>
 * 
 * @since 1.6
 * @create 2015. 3. 10.
 * @modified 2015. 3. 10.
 * @author cchyun
 */
@SuppressWarnings("serial")
public class Servlet4EmpStart extends HttpServlet {

	@Override
	public void init() throws ServletException {
		String emp_home = getServletContext().getRealPath("/");
		String product_name = getServletConfig().getInitParameter("product_name");
		new Thread(new Runnable() {
			@Override
			public void run() {
				 Main4EmpStart.start(new String[] { "emp.home=" + emp_home, "product.name=" + product_name });
			}
		}).start();
	}

	@Override
	public void destroy() {
		Main4EmpStart.stop(new String[] {});
	}

}
