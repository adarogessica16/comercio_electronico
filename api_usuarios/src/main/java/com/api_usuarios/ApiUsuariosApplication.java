package com.api_usuarios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages="com.beansdto.domain")
public class ApiUsuariosApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiUsuariosApplication.class, args);
    }
}
