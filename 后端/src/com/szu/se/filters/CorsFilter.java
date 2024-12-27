package com.szu.weboj.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * ClassName: CrosFilter
 * Package: com.szu.weboj.filters
 * Description:
 *
 * @Author Mallard
 * @Create 2024/12/21 14:48
 * @Version 1.0.0
 */
@WebFilter("/*")
public class CorsFilter implements Filter {
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, HEAD");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "access-control-allow-origin, authority, content-type, version-info, X-Requested-With, token");
		// 非预检请求,放行即可,预检请求,则到此结束,不需要放行
		if (!request.getMethod().equalsIgnoreCase("OPTIONS")) {
			filterChain.doFilter(servletRequest, servletResponse);
		}
	}
}
