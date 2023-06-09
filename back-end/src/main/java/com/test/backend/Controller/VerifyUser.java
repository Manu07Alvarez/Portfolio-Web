package com.test.backend.Controller;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.test.backend.Model.User;
import com.test.backend.Model.VerificationCode;
import com.test.backend.Repository.UserRepository;
import com.test.backend.Repository.VerificationCodeRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true", allowedHeaders = {"*"}, exposedHeaders = "")
public class Verify {
    @Autowired
    private UserRepository usuarioRepository;
    
    @Autowired
    private VerificationCodeRepository verificationCodeRepository;
	
	@GetMapping("/verify")
	public String verify(@RequestParam String email, @RequestParam String code, HttpServletResponse servletResponse,  HttpServletRequest servletRequest) throws IOException {
		
	    if (email == null || code == null) {
	        servletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Solicitud de verificación inválida"); // Mensaje de error para la solicitud
	        return null;
	    }

	    VerificationCode verificationCode = verificationCodeRepository.findByEmail(email);
	    if (verificationCode == null) {
	        servletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "No se encontró un usuario con la dirección de correo electrónico proporcionada"); // Mensaje de error para la solicitud
	        return null;
	    }
	    
	    User usuario = usuarioRepository.findByEmail(email);
	    if (checkVerificationCode(email, code)) {
    	   usuario.setEnabled(true); // Actualiza el valor de "enabled" a "true"
    	   usuarioRepository.save(usuario); // Guarda los cambios en la base de datos
    	   return "Código de verificación válido y usuario habilitado";
	    } else {
	        // Si no son iguales, el código de verificación es inválido
	        servletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Código de verificación inválido"); // Mensaje de error para la solicitud
	        return null;
	    }
	}
	
	@Transactional
	public boolean checkVerificationCode(String email, String code) {
	    VerificationCode verificationCode = verificationCodeRepository.findByEmail(email);
	    if (verificationCode == null) {
	        throw new IllegalArgumentException("No se encontró un usuario con la dirección de correo electrónico proporcionada");
	    } else {
		    if (verificationCode.getExpiration_date().isBefore(LocalDateTime.now())) {
		        throw new IllegalArgumentException("El código de verificación ha expirado");
		    } else {
			    if (!verificationCode.getCode().equals(code)) {
			        return false;
			    } else {
				    // Eliminar el registro del código de verificación de la tabla VerificationCode
				    verificationCodeRepository.delete(verificationCode);
				    return true;
				    
			    }
		    }
	    }
	}
}
