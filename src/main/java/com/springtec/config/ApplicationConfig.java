package com.springtec.config;

import com.springtec.models.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;


/**
 * Esta clase conteendra todas las configuraciones de nuestra aplicacion,
 * como por ejemplo contenedores
 */

// Spring tomará esta clase e intentará implementar e injectar todos los contenedores

@Configuration
@EnableWebMvc
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UserRepository userRepository;

    @Bean //Marca un método como un productor de un bean gestionado por Spring.
    public UserDetailsService userDetailsService() {
        //  username que retornará el metodo "loadUserByUsername()" de "UserDetailsService"
        return username -> userRepository.findById( Integer.parseInt(username) )
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));
    }

    /**
     * Configuramos que utilizará nuestro DaoAuthenticationProvider para utilizar
     * un userDetailsService personalizado.
     * */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        // Indicamos a SpringSecurity que use mi UserDetailsService personalizado
        authProvider.setUserDetailsService(userDetailsService());
        // Pasamos nuestro encriptador de contraseñas
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // BCryptPasswordEncoder será el que encriptará la contraseña
        return new BCryptPasswordEncoder();
    }

    /**
     *  El AuthenticationManager es un componente crucial en Spring Security que se encarga
     *  de la autenticación de los usuarios. Al obtenerlo de la configuración, se asegura
     *  que esté configurado y listo para su uso.
     * */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        // Usaremos el AuthenticationConfiguration de SpringSecurity
        return config.getAuthenticationManager();
    }

    @Bean
    public WebMvcConfigurer corsMappingConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                    .allowedOrigins("http://127.0.0.1:5500","http://localhost:5173")
                    .allowedMethods("GET", "POST", "PUT", "DELETE")
                    .maxAge(3600L)
                    .allowedHeaders("Requestor-Type","Authorization","Content-Type")
                    .exposedHeaders("X-Get-Header");
            }
        };
    }

}
