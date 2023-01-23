package com.comeon.backend.auth.infra;

import com.comeon.backend.auth.domain.JwtValidator;
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
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseClaimsJws(token)
                    .getBody() != null;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
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
        return false;
    }
}
