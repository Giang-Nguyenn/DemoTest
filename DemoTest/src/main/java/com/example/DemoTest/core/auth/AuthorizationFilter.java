package com.example.DemoTest.core.auth;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthorizationFilter extends  OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        System.out.println("AuthorizationFilter");
//        System.out.println(request.getRequestURI());
//        System.out.println(request.getMethod());
//        System.out.println(request.getPathInfo());

//          response.setStatus(409);
//          response.getWriter().write("aaaaa");
//          response.;
        filterChain.doFilter(request,response);
    }
}
