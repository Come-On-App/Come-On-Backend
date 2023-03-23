package com.comeon.backend.config.security;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class AdminKeyProperties {

    @Value("${admin.key}")
    private String adminKey;
}
