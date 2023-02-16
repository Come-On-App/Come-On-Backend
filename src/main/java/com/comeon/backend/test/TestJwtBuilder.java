package com.comeon.backend.test;

import com.comeon.backend.common.jwt.JwtToken;
import com.comeon.backend.common.jwt.Payload;
import com.comeon.backend.common.jwt.TokenType;
import com.comeon.backend.common.jwt.infrastructure.ClaimName;
import com.comeon.backend.common.jwt.infrastructure.JwtParser;
import com.comeon.backend.common.jwt.infrastructure.JwtProperties;
import com.comeon.backend.common.jwt.infrastructure.RefreshTokenRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

@Profile({"local", "dev"})
@Component
@RequiredArgsConstructor
public class TestJwtBuilder {

    private static final String ISSUER = "comeon-backend";

    private final JwtProperties jwtProperties;
    private final JwtParser jwtParser;
    private final RefreshTokenRepository rtkRepository;

    public JwtToken buildAtk(Long userId, String nickname, String authorities, Long atkExpirySec) {
        Instant now = Instant.now();
        long accessTokenExpirySec = atkExpirySec == null
                ? jwtProperties.getAccessTokenExpirySec()
                : atkExpirySec;

        String atk = Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
                .claim(ClaimName.SUBJECT.getValue(), TokenType.ATK.name())
                .claim(ClaimName.ISSUER.getValue(), ISSUER)
                .claim(ClaimName.ISSUED_AT.getValue(), Date.from(now))
                .claim(ClaimName.EXPIRATION.getValue(), Date.from(now.plusSeconds(accessTokenExpirySec)))
                .claim(ClaimName.USER_ID.getValue(), userId)
                .claim(ClaimName.NICKNAME.getValue(), nickname)
                .claim(ClaimName.AUTHORITIES.getValue(), authorities)
                .compact();

        return new JwtToken(atk, new Payload((jwtParser.parse(atk))));
    }

    public JwtToken buildRtk(Long userId, Long rtkExpirySec) {
        Instant now = Instant.now();
        long refreshTokenExpirySec = rtkExpirySec == null
                ? jwtProperties.getRefreshTokenExpirySec()
                : rtkExpirySec;

        String rtk = Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
                .claim(ClaimName.SUBJECT.getValue(), TokenType.RTK.name())
                .claim(ClaimName.ISSUER.getValue(), ISSUER)
                .claim(ClaimName.ISSUED_AT.getValue(), Date.from(now))
                .claim(ClaimName.EXPIRATION.getValue(), Date.from(now.plusSeconds(refreshTokenExpirySec)))
                .compact();

        JwtToken refreshToken = new JwtToken(rtk, new Payload((jwtParser.parse(rtk))));
        rtkRepository.add(refreshToken.getToken(), userId, refreshToken.getPayload().getExpiration());

        return refreshToken;
    }
}
