package com.springtec.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/** Esta clase es la responsable de configurar la seguridad en nuestra aplicacion */

@Configuration
@EnableWebSecurity //Activa la funcionalidad de seguridad web de Spring Security en la aplicación
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    //Configurado en "ApplicationConfig"
    private final AuthenticationProvider authenticationProvider;

    @Bean // Marca el método como un bean gestionado por Spring
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Configuramos las reglas de seguridad
        http
            // Habilitamos CORS
            .cors()
            .and()
            // Desabilitamos la configuracion CSRF
            .csrf()
            .disable()

            // Autorizamos algunos puntos blancos que no requiren de token para accederlos(/login, /resgister...)
            .authorizeHttpRequests()
            .requestMatchers("/api/v1/auth/**")
            .permitAll() // Se las permitimos a todas las que estan en la lista de arriba

            // Cualquier otra solicitud debe ser AUTENTIFICADA
            .anyRequest()
            .authenticated()

            // Y configuramos la gestion de sesiones-> Usaremos el filtro 1 vez por solicitud
            // por ende, no es necerio guardar la sesion de estado
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS) //Se crea una SESSION SIN ESTADO

            // Especificamos el proveedor de autentificacion que usaremos
            .and()
            .authenticationProvider(authenticationProvider)

            // Implementamos NUESTRO FILTRO antes del filtro "UsernamePasswordAuthenticationFilter"
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
