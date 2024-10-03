package com.api_productos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EntityScan(basePackages = "com.beansdto.domain")
@SpringBootApplication
@EnableWebMvc
@EnableCaching
public class ApiProductosApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiProductosApplication.class, args);
    }

}
