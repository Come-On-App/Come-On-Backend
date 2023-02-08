package com.comeon.backend.common.jwt.infrastructure;

import com.comeon.backend.common.jwt.TokenType;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtValidator {

    private final JwtProperties jwtProperties;

    public boolean verify(String token) {
        return getClaims(token) != null;
    }

    public boolean verifyAtk(String token) {
        Claims claims = getClaims(token);
        if (claims != null) {
            TokenType tokenType = TokenType.of(claims.get(ClaimName.SUBJECT.getValue(), String.class));
            return tokenType.isAtk();
        }
        return false;
    }

    public boolean verifyRtk(String token) {
        Claims claims = getClaims(token);
        if (claims != null) {
            TokenType tokenType = TokenType.of(claims.get(ClaimName.SUBJECT.getValue(), String.class));
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
            log.warn("JWT 서명이 잘못되었습니다.");
        } catch (ExpiredJwtException e) {
            log.warn("JWT 토큰이 만료되었습니다.");
        } catch (UnsupportedJwtException e) {
            log.warn("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.warn("잘못된 JWT 토큰입니다.");
        } catch (JwtException e) {
            log.warn("JWT 검증 오류 발생", e);
        }
        return null;
    }
}
