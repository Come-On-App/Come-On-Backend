package com.comeon.backend.common.security;

import com.comeon.backend.jwt.application.JwtManager;
import com.comeon.backend.jwt.application.Payload;
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

    private final JwtManager jwtManager;

    public Authentication getAuthentication(String accessToken) {
        Payload payload = jwtManager.parse(accessToken);
        JwtPrincipal jwtPrincipal = new JwtPrincipal(accessToken, payload.getUserId());
        List<GrantedAuthority> authorities = Arrays.stream(payload.getAuthorities().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return new UsernamePasswordAuthenticationToken(jwtPrincipal, null, authorities);
    }
}
