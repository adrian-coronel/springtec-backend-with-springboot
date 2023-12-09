package com.springtec;

import com.springtec.storage.StorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class SpringtecBackendWithSpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringtecBackendWithSpringbootApplication.class, args);
    }



}
