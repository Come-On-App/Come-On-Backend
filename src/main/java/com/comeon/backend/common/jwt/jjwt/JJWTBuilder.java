package com.comeon.backend.common.jwt.jjwt;

import com.comeon.backend.common.jwt.JwtBuilder;
import com.comeon.backend.common.jwt.JwtToken;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;

public class JJWTBuilder extends JwtBuilder {

    public JJWTBuilder(String secretKey) {
        super(secretKey);
    }

    @Override
    public JwtToken build() {
        String token = Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
                .setClaims(claims)
                .compact();
        return new JwtToken(token, claims);
    }
}
