package com.comeon.backend.auth.application;

import com.comeon.backend.auth.domain.JwtClaims;
import lombok.Getter;

import java.time.Instant;

@Getter
public class Payload {

    private TokenType type;
    private Instant issuedAt;
    private Instant expiration;

    private Long userId;
    private String nickname;
    private String authorities;

    public Payload(JwtClaims jwtClaims) {
        this.type = jwtClaims.getType();
        this.issuedAt = jwtClaims.getIssuedAt();
        this.expiration = jwtClaims.getExpiration();
        this.userId = jwtClaims.getUserId();
        this.nickname = jwtClaims.getNickname();
        this.authorities = jwtClaims.getAuthorities();
    }
}
