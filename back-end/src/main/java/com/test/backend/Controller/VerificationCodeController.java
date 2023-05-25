package com.test.backend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.test.backend.Model.VerificationCode;
import com.test.backend.Repository.VerificationCodeRepository;

import java.util.Optional;


@RestController
@RequestMapping("/api/verificationCode")
@CrossOrigin(origins = "http://localhost:4200")
public class VerificationCodeController {

    @Autowired
    private VerificationCodeRepository verificationCodeRepository;

    @PostMapping
    public ResponseEntity<?> crearUsuario(@RequestBody VerificationCode usuario) {      
        verificationCodeRepository.save(usuario);		
        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }

    @GetMapping("/{email}")
    public ResponseEntity<?> obtenerUsuarioPorEmail(@PathVariable String email) {
        Optional<VerificationCode> usuario = Optional.ofNullable(verificationCodeRepository.findByEmail(email));
        if (usuario.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(usuario.get());
    }
}