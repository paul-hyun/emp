package com.hellonms.platforms.emp_onion.client_common.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.server.workflow.WorkflowMap;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_orange.server.invoker.Invoker4OrangeIf;
import com.hellonms.platforms.emp_orange.share.model.fault.event.SEVERITY;

@SuppressWarnings("serial")
public class HttpServlet4Image extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		EmpContext context = new EmpContext(null);
		try {
			Invoker4OrangeIf invoker = (Invoker4OrangeIf) WorkflowMap.getInvoker(Invoker4OrangeIf.class);
			String path = req.getParameter("path");
			String width = req.getParameter("width");
			String height = req.getParameter("height");
			SEVERITY severity = SEVERITY.toEnum(req.getParameter("severity"));
			byte[] image = null;
			if (width == null || width.length() == 0 || height == null || height.length() == 0) {
				image = invoker.queryImage(context, path);
			} else if (severity == null) {
				image = invoker.queryImage(context, path, Integer.parseInt(width), Integer.parseInt(height));
			} else {
				image = invoker.queryImage(context, path, Integer.parseInt(width), Integer.parseInt(height), severity);
			}
			resp.setContentType("image/png");
			resp.getOutputStream().write(image, 0, image.length);
			context.commit();
		} catch (EmpException e) {
			context.rollback();
			throw new ServletException(e);
		} catch (Exception e) {
			context.rollback();
			throw new ServletException(e);
		} finally {
			context.close();
		}
	}

}
