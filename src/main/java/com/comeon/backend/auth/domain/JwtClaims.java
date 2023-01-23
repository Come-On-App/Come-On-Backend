package com.comeon.backend.auth.domain;

import com.comeon.backend.auth.application.TokenType;
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
