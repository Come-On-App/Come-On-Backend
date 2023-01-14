package com.comeon.backend.common.jwt.jjwt;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

@Slf4j
public class JJWTValidator {

    private JwtParser jwtParser;
    private String jwt;

    public JJWTValidator(String secretKey) {
        this.jwtParser = Jwts.parserBuilder()
                .setSigningKey(
                        Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8))
                )
                .build();
    }

    public JJWTValidator jwt(String jwt) {
        this.jwt = jwt;
        return this;
    }

    public boolean validate() {
        try {
            return jwtParser.parseClaimsJws(jwt).getBody() != null;
        } catch (JwtException e) {
            log.error("Jwt Validate Error. {}", e.getMessage());
            return false;
        }
    }
}
