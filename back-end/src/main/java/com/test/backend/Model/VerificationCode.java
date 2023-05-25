package com.test.backend.Model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;


@Entity
@Table(name = "verification_code")
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class VerificationCode {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
 
    @Column(name = "email", nullable = false, length = 255)
    private String email;
 
    @Column(name = "code", nullable = true, length = 255)
    private String code;
    
    @Column(name = "expiration_date", nullable = true)
    private LocalDateTime expirationDate;
}
 