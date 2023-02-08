package com.comeon.backend.common.jwt.infrastructure;

import com.comeon.backend.common.jwt.TokenType;
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
public class JwtBuilder {

    private static final String ISSUER = "comeon-backend";

    private final JwtProperties jwtProperties;

    public String buildAtk(Long userId, String nickname, String authorities) {
        Instant now = Instant.now();
        return Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
                .claim(ClaimName.SUBJECT.getValue(), TokenType.ATK.name())
                .claim(ClaimName.ISSUER.getValue(), ISSUER)
                .claim(ClaimName.ISSUED_AT.getValue(), Date.from(now))
                .claim(ClaimName.EXPIRATION.getValue(), Date.from(now.plusSeconds(jwtProperties.getAccessTokenExpirySec())))
                .claim(ClaimName.USER_ID.getValue(), userId)
                .claim(ClaimName.NICKNAME.getValue(), nickname)
                .claim(ClaimName.AUTHORITIES.getValue(), authorities)
                .compact();
    }

    public String buildRtk() {
        Instant now = Instant.now();
        return Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
                .claim(ClaimName.SUBJECT.getValue(), TokenType.RTK.name())
                .claim(ClaimName.ISSUER.getValue(), ISSUER)
                .claim(ClaimName.ISSUED_AT.getValue(), Date.from(now))
                .claim(ClaimName.EXPIRATION.getValue(), Date.from(now.plusSeconds(jwtProperties.getRefreshTokenExpirySec())))
                .compact();
    }
}
