package com.minimarket.JPF_SalesSystem.utility;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class AuthInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//return true;
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("usuario") == null) {
			response.sendRedirect("/");
			return false;
		}
		String rol = (String) session.getAttribute("rol");

		String requestURI = request.getRequestURI();

		if (rol.equals("ADMINISTRADOR")) {
			return true;
		}
		
		if (rol.equals("VENDEDOR")) {
			if (requestURI.startsWith("/ventas")) {
				return true;
			} else {
				response.sendRedirect("/ventas");
				return false;
			}
		}

		response.sendRedirect("/");
		return false;
	}
}
