package com.minimarket.JPF_SalesSystem.utility;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class AuthInterceptor implements HandlerInterceptor{

	
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            // Si no hay sesión o el usuario no está autenticado, redirigir al login
            response.sendRedirect("/");
            return false;  // Detener la solicitud si no está autenticado
        }
        return true;  // Continuar con la solicitud si el usuario está autenticado
    }
}
