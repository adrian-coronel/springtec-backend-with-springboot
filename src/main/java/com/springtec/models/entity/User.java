package com.springtec.models.entity;

import com.springtec.models.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * SPRING SECURITY ya tiene su propia clase usuario, sin embargo, nosotros estamos
 * personalizando una para tener más control sobre nuestra class usuario
 * */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User  implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String email;
    private String password;
    @Enumerated(EnumType.STRING) //Asigna los valores de la enumeración a cadenas en la BD
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //Devolvemos un punto de lista, con una Nuevo AUTORIDAD CONCEDIDA SIMPLE
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    // IMPORTANTE PONER EN TRUE LOS SIGUIENTES METODOS
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
