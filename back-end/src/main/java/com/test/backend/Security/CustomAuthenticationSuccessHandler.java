package com.test.backend.Security;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.test.backend.Model.User;
import com.test.backend.Repository.UserRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	
    @Autowired
    private UserRepository usuarioRepository;
	
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
    	
    	// Generar una cadena aleatoria para la token de sesión
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[20];
        random.nextBytes(bytes);
        byte[] token = Base64.getUrlEncoder().encode(bytes);
        

        // Guardar la token de sesión en la base de datos del usuario
        final Optional<User> user = usuarioRepository.findByEmail(authentication.getName());
        user.setToken(token);
        usuarioRepository.save(user);


        String redirectUrl = "/home?token=" + token;
        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }
}
