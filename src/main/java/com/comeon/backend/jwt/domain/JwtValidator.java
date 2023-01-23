package com.comeon.backend.jwt.domain;

public interface JwtValidator {

    boolean verify(String token);
}
