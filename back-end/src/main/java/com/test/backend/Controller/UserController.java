package com.test.backend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.test.backend.Model.User;
import com.test.backend.Repository.UserRepository;

import java.util.Optional;


@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<?> crearUsuario(@RequestBody User user) {
        final Optional<Optional<User>> userExistente = Optional.ofNullable(userRepository.findByEmail(user.getEmail()));
        if (userExistente.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        
        userRepository.save(user);		
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping("/{email}")
    public ResponseEntity<?> obtenerUsuarioPorEmail(@PathVariable String email) {
        final Optional<Optional<User>> user = Optional.ofNullable(userRepository.findByEmail(email));
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(user.get());
    }
}