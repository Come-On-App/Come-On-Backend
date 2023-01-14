package com.comeon.backend.common.security;

import com.comeon.backend.common.jwt.JwtClaims;
import com.comeon.backend.common.jwt.JwtParser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider {

    private final JwtParser jwtParser;

    public Authentication getAuthentication(String accessToken) {
        JwtClaims claims = jwtParser.parse(accessToken);
        JwtPrincipal jwtPrincipal = new JwtPrincipal(accessToken, claims.getUserId());
        List<GrantedAuthority> authorities = Arrays.stream(claims.getAuthorities().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return new UsernamePasswordAuthenticationToken(jwtPrincipal, null, authorities);
    }
}
