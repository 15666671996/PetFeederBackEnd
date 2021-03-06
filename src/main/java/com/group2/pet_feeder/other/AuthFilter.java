package com.group2.pet_feeder.other;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(urlPatterns = { "/*" })
public class AuthFilter implements Filter {
    private String whiteList[] = new String[] {
            "/lib/jquery-3.6.0.min.js",
            "/favicon.ico",

            "/",
            "/index.html",
            "/index.js",
            "/unauthorized.html",

            "/login",
            "/register",
            "/checkUsername"

    };

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(false);
        String uri = request.getRequestURI();
        System.out.println(uri);

        if (inWhiteList(uri) || uri.startsWith("/js") || uri.startsWith("/css")) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            // login status check
            if (session != null && session.getAttribute("userId") != null) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                // check Ajax request
                if ("XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"))) {
                    response.getWriter().write("Haven't login");
                    response.setStatus(401);
                } else {
                    response.sendRedirect(request.getContextPath() + "/");
                }
            }
        }

    }

    private boolean inWhiteList(String uri) {
        for (String path : whiteList) {
            if (path.equals(uri)) {
                return true;
            }
        }
        return false;
    }
}
