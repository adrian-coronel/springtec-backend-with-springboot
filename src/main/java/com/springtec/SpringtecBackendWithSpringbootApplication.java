package com.springtec;

import com.springtec.storage.StorageProperties;
import com.springtec.storage.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class SpringtecBackendWithSpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringtecBackendWithSpringbootApplication.class, args);
    }

    @Bean
    CommandLineRunner init(StorageService storageService) {
        return (args) -> {
            // En caso no exista, creará el directorio /uploads
            storageService.init();
        };
    }

}
