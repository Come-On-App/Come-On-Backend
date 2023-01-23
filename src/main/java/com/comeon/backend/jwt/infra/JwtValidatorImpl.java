package com.comeon.backend.jwt.infra;

import com.comeon.backend.jwt.application.TokenType;
import com.comeon.backend.jwt.domain.JwtValidator;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtValidatorImpl implements JwtValidator {

    private final JwtProperties jwtProperties;

    @Override
    public boolean verify(String token) {
        return getClaims(token) != null;
    }

    @Override
    public boolean verifyAtk(String token) {
        Claims claims = getClaims(token);
        if (claims != null) {
            TokenType tokenType = TokenType.of(claims.get(ClaimNames.SUBJECT, String.class));
            return tokenType.isAtk();
        }
        return false;
    }

    @Override
    public boolean verifyRtk(String token) {
        Claims claims = getClaims(token);
        if (claims != null) {
            TokenType tokenType = TokenType.of(claims.get(ClaimNames.SUBJECT, String.class));
            return tokenType.isRtk();
        }
        return false;
    }

    private Claims getClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (SecurityException | MalformedJwtException e) {
            log.error("JWT 서명이 잘못되었습니다.");
        } catch (ExpiredJwtException e) {
            log.error("JWT 토큰이 만료되었습니다.");
        } catch (UnsupportedJwtException e) {
            log.error("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.error("잘못된 JWT 토큰입니다.");
        } catch (JwtException e) {
            log.error("JWT 검증 오류 발생", e);
        }
        return null;
    }
}
