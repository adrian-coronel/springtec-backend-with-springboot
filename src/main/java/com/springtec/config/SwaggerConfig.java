package com.springtec.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

   /**
    * En este caso, se está configurando un esquema de autenticación JWT
    * para una aplicación basada en HTTP.
    * */
   private SecurityScheme createAPIKeyScheme() {
      // Indicamos que utilizamos un schema basado en HTTP
      return new SecurityScheme().type(SecurityScheme.Type.HTTP)
          // El formato del token será JWT
          .bearerFormat("JWT")
          // El schema del token será de tipo "bearer"
          .scheme("bearer");
   }

   /**
    * Configuración de bean OpenAPI para incluir información
    * de API y esquemas de seguridad
    * */
   @Bean
   public OpenAPI openAPI() {
      // Agregamos un requisito de seguridad para la API
      return new OpenAPI().addSecurityItem(new SecurityRequirement().
              addList("Bearer Authentication")) //Se requiere "Bearer Authentication"
          // Configuramos los componentes de seguridad
          .components(new Components().addSecuritySchemes
              ("Bearer Authentication", createAPIKeyScheme())) //Agg nuestro SCHEMA PERSONALIZADO
          // Proporciona información general sobre la API
          .info(new Info().title("SPRINGTEC API RESTFULL")
              .description("Esta es una api de una plataforma innovadora que conecta a clientes con técnicos para resolver problemas técnicos.")
              .version("1.0").contact(new Contact().name("Adrian Gabriel Coronel Mendoza")
                  .email( "adrian.coronel@tecsup.edu.pe").url("www.springtec.com"))
              .license(new License().name("TECSUP")
                  .url("https://www.tecsup.edu.pe/")));
   }
}
