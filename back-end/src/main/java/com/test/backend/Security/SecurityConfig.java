package com.test.backend.Security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.test.backend.Service.MyUserDetailsService;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
    @Autowired
    private MyUserDetailsService userDetailsService;
	
    @Autowired
    private javax.sql.DataSource dataSource;
     
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public AngularAuthenticationEntryPoint angularAuthenticationEntryPoint() {
        return new AngularAuthenticationEntryPoint("/login");
    }
    
    
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("SELECT email, password, enabled FROM usuarios.verified_users WHERE email=?")
                .authoritiesByUsernameQuery("SELECT email, role FROM usuarios.verified_users WHERE email=?")
                .passwordEncoder(passwordEncoder());
    }
    
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter();
    	http
	        .addFilterBefore(customAuthenticationFilter, BasicAuthenticationFilter.class)
	        .authorizeHttpRequests(authorize -> authorize
	            .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
	            .requestMatchers("/user/**").hasRole("USER")
	            .requestMatchers("/login", "/register").permitAll()
	            .requestMatchers("/user/**", "/admin/**").hasRole("ADMIN")
	            .anyRequest().authenticated()
	        )
	        .formLogin(login -> login
	            .loginPage("/login")
	            .permitAll()
	            .usernameParameter("email") // nombre del campo de email en el formulario de login
	            .passwordParameter("password") // nombre del campo de password en el formulario de login
	            .loginProcessingUrl("/login") // la URL a la que el formulario de login debe hacer un POST
	            .successHandler(authenticationSuccessHandler()) // el manejador para cuando el login sea exitoso
	            .failureHandler(authenticationFailureHandler()) // el manejador para cuando el login falle
	        )
	        .logout(logout -> logout
	            .logoutUrl("/logout")
	            .logoutSuccessUrl("/")
	            .invalidateHttpSession(true)
	        )
	        .csrf().disable(); // desactivar la protección CSRF para este ejemplo
    	return http.build();
    }
    
    protected void configure(HttpSecurity http) throws Exception {
    	CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter();
        http
	        .addFilterBefore(customAuthenticationFilter, BasicAuthenticationFilter.class)
	        .authorizeHttpRequests(authorize -> authorize
	            .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
	            .requestMatchers("/user/**").hasRole("USER")
	            .requestMatchers("/login", "/register").permitAll()
	            .requestMatchers("/user/**", "/admin/**").hasRole("ADMIN")
	            .anyRequest().authenticated()
	        )
	        .formLogin(login -> login
	            .loginPage("/login")
	            .permitAll()
	            .usernameParameter("email") // nombre del campo de email en el formulario de login
	            .passwordParameter("password") // nombre del campo de password en el formulario de login
	            .loginProcessingUrl("/login") // la URL a la que el formulario de login debe hacer un POST
	            .successHandler(authenticationSuccessHandler()) // el manejador para cuando el login sea exitoso
	            .failureHandler(authenticationFailureHandler()) // el manejador para cuando el login falle
	        )
	        .logout(logout -> logout
	            .logoutUrl("/logout")
	            .logoutSuccessUrl("/")
	            .invalidateHttpSession(true)
	        )
	        .csrf().disable(); // desactivar la protección CSRF para este ejemplo
	}



    
    public void init(WebSecurity web) throws Exception {
        // Add any custom configuration you need to do before the SecurityConfigurer is applied
    }
    
    
    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }
    

    
    
}