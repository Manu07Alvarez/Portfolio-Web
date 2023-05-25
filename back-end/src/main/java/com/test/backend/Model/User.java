package com.test.backend.Model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.*;

@Entity
@Table(name = "verified_users")
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class User implements UserDetails {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8426681068045261254L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    
    @Column(name="username", nullable = false, length = 255)
    private String username;
    
    @Column(name="email", nullable = false, unique = true, length = 255)
    private String email;
    
    @Column(name="password", nullable = false, length = 255)
    private String password;
    
    @Column(name="data", nullable = true, length = 255, columnDefinition = "JSON")
    private String data;
    
    @Column(name="token", nullable = true, columnDefinition = "BINARY(64)")
    private byte[] token;
    
    @Enumerated(EnumType.STRING)
    private Role role;
    
    @Column(name="enabled", nullable = false, length = 1, columnDefinition = "TINYINT(1)")
    private boolean enabled;
    
    public User(String username, String email, String password, String data, boolean enabled, byte[] token, Role role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.data = data;
        this.token = token;
        this.role = role;
        this.enabled = enabled;
    }
    
    public enum Role {
        USER,
        ADMIN;
        
        public static String[] toStringArray(Role... role) {
            String[] result = new String[role.length];
            for (int i = 0; i < role.length; i++) {
              result[i] = role[i].name();
            }
            return result;
        }
    }

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}
    
}