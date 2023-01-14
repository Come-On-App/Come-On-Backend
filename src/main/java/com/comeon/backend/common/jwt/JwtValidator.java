package com.comeon.backend.common.jwt;

import com.comeon.backend.common.jwt.jjwt.JJWTValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtValidator {

    private final String secretKey;

    public JwtValidator(@Value("${jwt.secret}") String secretKey) {
        this.secretKey = secretKey;
    }

    public boolean valid(String token) {
        return new JJWTValidator(secretKey)
                .jwt(token)
                .validate();
    }
}
