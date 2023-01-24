package com.comeon.backend.jwt.domain;

public interface JwtValidator {

    boolean verify(String token);

    boolean verifyAtk(String token);
    boolean verifyRtk(String token);
}
