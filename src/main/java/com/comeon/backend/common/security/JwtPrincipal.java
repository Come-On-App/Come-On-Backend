package com.comeon.backend.common.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JwtPrincipal {

    private String accessToken;
    private Long userId;
}
