package com.session10miniproject.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class AdminAuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        Object loggedIn = session.getAttribute("adminLoggedIn");

        if (loggedIn == null || !(Boolean) loggedIn) {
            String contextPath = request.getContextPath();
            response.sendRedirect(contextPath + "/admin/login");
            return false;
        }
        
        return true;
    }
}
