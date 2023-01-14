package com.comeon.backend.common.jwt.jjwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

@Slf4j
public class JJWTValidator {

    private JwtParser jwtParser;
    private String jwt;

    public JJWTValidator(String secretKey) {
        this.jwtParser = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                .build();
    }

    public JJWTValidator jwt(String jwt) {
        this.jwt = jwt;
        return this;
    }

    public boolean validate() {
        try {
            return jwtParser.parseClaimsJws(jwt).getBody() != null;
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
