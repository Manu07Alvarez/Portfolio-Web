package com.test.backend.Security;

import java.io.BufferedReader;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.*;
import com.test.backend.Service.MyUserDetailsService;


public class CustomAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
	
	@Autowired
    private PasswordEncoder passwordEncoder;
    
    public CustomAuthenticationFilter() {
    	super(new AntPathRequestMatcher("/login", "POST"));
    }
    

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        
        // Obtener los datos de inicio de sesión del cuerpo de la solicitud
        BufferedReader reader = request.getReader();
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        String requestBody = stringBuilder.toString();
        ObjectMapper objectMapper = new ObjectMapper();
        LoginRequest loginRequest = objectMapper.readValue(requestBody, LoginRequest.class);

        // Verificar los datos de inicio de sesión con los datos almacenados en la base de datos
        MyUserDetailsService userDetailsService = new MyUserDetailsService();
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
        if (!passwordEncoder.matches(loginRequest.getPassword(), userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid email/password");
        }

        // Crear una autenticación exitosa y devolverla
        EmailPasswordAuthenticationToken authenticationToken = new EmailPasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        return this.getAuthenticationManager().authenticate(authenticationToken);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class LoginRequest {
        private String email;
        private String password;
    }
}