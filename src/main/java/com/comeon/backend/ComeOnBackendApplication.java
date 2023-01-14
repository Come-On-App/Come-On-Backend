package com.comeon.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@EnableFeignClients
@SpringBootApplication
public class ComeOnBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComeOnBackendApplication.class, args);
    }

}
