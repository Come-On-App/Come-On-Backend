package com.comeon.backend.config.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JwtPrincipal {

    private String atk;
    private Long userId;
}
