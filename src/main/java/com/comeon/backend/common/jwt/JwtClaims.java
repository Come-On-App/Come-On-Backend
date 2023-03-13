package com.comeon.backend.common.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@Getter
@AllArgsConstructor
public class JwtClaims {

    private TokenType type;
    private Instant issuedAt;
    private Instant expiration;

    private Long userId;
    private String nickname;
    private String authorities;
}
