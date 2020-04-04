package com.hellonms.platforms.emp_orange.client_openapi.rest;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class Filter4Cros implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		((HttpServletResponse) response).setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
		((HttpServletResponse) response).setHeader("Access-Control-Max-Age", "3600");
		((HttpServletResponse) response).setHeader("Access-Control-Allow-Headers", "Content-Type, dataType, Access-Control-Allow-Headers, Authorization, X-Requested-With");

		((HttpServletResponse) response).setHeader("Access-Control-Allow-Origin", "*");

		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
	}

}
