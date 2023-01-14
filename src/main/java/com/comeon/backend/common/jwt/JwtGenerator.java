package com.comeon.backend.common.jwt;

import com.comeon.backend.common.jwt.jjwt.JJWTBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class JwtGenerator {

    private final String secretKey;
    private final long accessTokenExpirySec;
    private final long refreshTokenExpirySec;

    public JwtGenerator(@Value("${jwt.secret}") String secretKey,
                        @Value("${jwt.access-token.expire-time}") long accessTokenExpirySec,
                        @Value("${jwt.refresh-token.expire-time}") long refreshTokenExpirySec) {
        this.secretKey = secretKey;
        this.accessTokenExpirySec = accessTokenExpirySec;
        this.refreshTokenExpirySec = refreshTokenExpirySec;
    }

    public JwtBuilder initBuilder(TokenType type) {
        String issuer = "come-on-backend";
        Instant now = Instant.now();

        return new JJWTBuilder(secretKey)
                .subject(type.getValue())
                .issuer(issuer)
                .issuedAt(now)
                .expiration(now.plusSeconds(decideExpiry(type)));
    }

    private long decideExpiry(TokenType type) {
        long expirySec = 0;
        switch (type) {
            case ACCESS:
                expirySec = accessTokenExpirySec;
                break;
            case REFRESH:
                expirySec = refreshTokenExpirySec;
                break;
        }
        return expirySec;
    }
}
