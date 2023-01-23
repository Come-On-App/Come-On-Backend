package com.comeon.backend.auth.domain;

public interface JwtValidator {

    boolean verify(String token);
}
