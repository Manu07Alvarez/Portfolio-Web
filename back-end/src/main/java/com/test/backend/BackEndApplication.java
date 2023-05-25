package com.test.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@EnableJpaRepositories("com.test.backend.Repository")
@SpringBootApplication(scanBasePackages = {"com.test.backend"})
@EntityScan("com.test.backend.Model")
public class BackEndApplication extends SpringBootServletInitializer {
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(BackEndApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(BackEndApplication.class, args);
    }
    
}
