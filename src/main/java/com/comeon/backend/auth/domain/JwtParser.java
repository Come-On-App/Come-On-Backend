package com.comeon.backend.auth.domain;

public interface JwtParser {

    JwtClaims parse(String token);
}
