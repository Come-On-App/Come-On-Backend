package com.comeon.backend.jwt.infra;

import com.comeon.backend.jwt.application.TokenType;
import com.comeon.backend.jwt.domain.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtBuilderImpl implements JwtBuilder {

    private static final String ISSUER = "comeon-backend";

    private final JwtProperties jwtProperties;

    @Override
    public String buildAtk(Long userId, String nickname, String authorities) {
        Instant now = Instant.now();
        return Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
                .claim(ClaimNames.SUBJECT, TokenType.ATK.name())
                .claim(ClaimNames.ISSUER, ISSUER)
                .claim(ClaimNames.ISSUED_AT, Date.from(now))
                .claim(ClaimNames.EXPIRATION, Date.from(now.plusSeconds(jwtProperties.getAccessTokenExpirySec())))
                .claim(ClaimNames.USER_ID, userId)
                .claim(ClaimNames.NICKNAME, nickname)
                .claim(ClaimNames.AUTHORITIES, authorities)
                .compact();
    }

    @Override
    public String buildRtk() {
        Instant now = Instant.now();
        return Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
                .claim(ClaimNames.SUBJECT, TokenType.RTK.name())
                .claim(ClaimNames.ISSUER, ISSUER)
                .claim(ClaimNames.ISSUED_AT, Date.from(now))
                .claim(ClaimNames.EXPIRATION, Date.from(now.plusSeconds(jwtProperties.getRefreshTokenExpirySec())))
                .compact();
    }
}
