package com.comeon.backend.jwt.domain;

public interface JwtParser {

    JwtClaims parse(String token);
}
