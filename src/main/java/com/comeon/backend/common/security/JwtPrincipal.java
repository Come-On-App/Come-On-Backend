package com.comeon.backend.common.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
@AllArgsConstructor
public class JwtPrincipal {

    private String accessToken;
    private Instant expiry;
    private Long userId;
}
