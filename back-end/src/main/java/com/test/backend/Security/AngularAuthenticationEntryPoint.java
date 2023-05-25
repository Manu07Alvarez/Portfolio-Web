package com.test.backend.Security;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AngularAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final String loginUrl;

    public AngularAuthenticationEntryPoint(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.sendRedirect(loginUrl);
    }
}
