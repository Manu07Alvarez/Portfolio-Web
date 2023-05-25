package com.test.backend.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.test.backend.Repository.UserRepository;
import com.test.backend.Model.User.Role;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
    private UserRepository userRepository;
	
	@Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Buscar el usuario por email en la base de datos
        Optional<Optional<com.test.backend.Model.User>> userOptional = Optional.ofNullable(userRepository.findByEmail(email));
        if (!userOptional.isPresent()) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
        Optional<com.test.backend.Model.User> user = userOptional.get();

        // Retornar un objeto UserDetails con los detalles del usuario
        return User.withUsername(user.getEmail())
                   .password(user.getPassword())
                   .roles(Role.toStringArray(user.getRole()))
                   .build();
    }
}
