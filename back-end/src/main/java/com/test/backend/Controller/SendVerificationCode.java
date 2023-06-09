package com.test.backend.Controller;


import java.time.LocalDateTime;
import java.util.Properties;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import jakarta.servlet.http.HttpServletRequest;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.test.backend.Repository.VerificationCodeRepository;
import com.test.backend.Model.VerificationCode;
import com.test.backend.Utility.VerificationCodeUtility.VerificationCodeGenerator;
import jakarta.mail.*;
import jakarta.mail.internet.*;


@RestController
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true", allowedHeaders = {"*"}, exposedHeaders = "")
public class EmailSender {
	
    @Autowired
    private SessionFactory sessionFactory;
    
    @Autowired
    private VerificationCodeRepository verificationCodeRepository;
	
	@GetMapping("/sendVerificationCode")
	public void sendVerificationCode(@RequestParam String email ,HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws Exception {

		try {
			
	        // Aquí reemplaza con tus credenciales SMTP
	        final String username = "jimmie0@ethereal.email";
	        final String password = "QSa4c2Nf3u1YM13jEB";
	
	        // Configura las propiedades del servidor SMTP
	        Properties props = new Properties();
	        props.put("mail.smtp.auth", "true");
	        props.put("mail.smtp.starttls.enable", "true");
	        props.put("mail.smtp.host", "smtp.ethereal.email");
	        props.put("mail.smtp.port", "587");
	
	        // Inicia sesión en el servidor SMTP
	        Session session = Session.getInstance(props, new Authenticator() {
	        	@Override
	            protected PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication(username, password);
	            }
	        });
	
	        // Crea un mensaje de correo electrónico
	        Message message = new MimeMessage(session);
	        message.setFrom(new InternetAddress("jimmie0@ethereal.email"));
	        if (email == null || email.isEmpty()) {
	            throw new IllegalArgumentException("Cookie de correo electrónico no encontrada");
	        }
	        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
	        message.setSubject("Código de verificación");
	        String code = VerificationCodeGenerator.generateVerificationCode();
	        message.setText("Tu código de verificación es: " + code);
	        
	        // Envía el mensaje
	        try {
	            // Envía el mensaje
	            Transport.send(message);	
	            System.out.println("Correo electrónico enviado exitosamente");
	            
	            // Actualiza el objeto Usuario con el código de verificación
	            VerificationCode verificationCodeEntity = verificationCodeRepository.findByEmail(email);
	            if (verificationCodeEntity != null) {
	                verificationCodeEntity.setCode(code);
	                verificationCodeEntity.setExpiration_date(LocalDateTime.now().plusMinutes(10)); // El código de verificación expirará en 10 minutos
	                verificationCodeRepository.save(verificationCodeEntity);
	            } else {
	                throw new IllegalArgumentException("No se encontró un usuario con la dirección de correo electrónico proporcionada");
	            }
	        } catch (MessagingException e) {
	            servletResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al enviar el correo electrónico de verificación "); // Mensaje de error para el servidor
	        } catch (Exception e) {
	            servletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Solicitud de verificación inválida"); // Mensaje de error para la solicitud
	        }
		} catch (IllegalArgumentException e) {
			servletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage()); // Mensaje de error para la solicitud
		}
	}
       
}	

