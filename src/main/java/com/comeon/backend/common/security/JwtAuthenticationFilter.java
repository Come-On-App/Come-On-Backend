package com.comeon.backend.common.security;

import com.comeon.backend.common.jwt.JwtClaims;
import com.comeon.backend.common.jwt.JwtParser;
import com.comeon.backend.common.jwt.JwtValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final static String PREFIX_BEARER = "Bearer ";

    private final JwtValidator jwtValidator;
    private final JwtParser jwtParser;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String accessTokenValue = resolveAccessToken(request);

        if (isValidAccessToken(accessTokenValue)) {
            JwtClaims claims = jwtParser.parse(accessTokenValue);
            JwtPrincipal jwtPrincipal = JwtPrincipal.builder()
                    .accessToken(accessTokenValue)
                    .expiry(claims.getExpiration())
                    .userId(claims.getUserId())
                    .build();
            List<GrantedAuthority> authorities = Arrays.stream(claims.getAuthorities().split(","))
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
            Authentication authentication = new UsernamePasswordAuthenticationToken(jwtPrincipal, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private boolean isValidAccessToken(String accessTokenValue) {
        return StringUtils.hasText(accessTokenValue) && jwtValidator.valid(accessTokenValue);
    }

    private String resolveAccessToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith(PREFIX_BEARER)) {
            return authorizationHeader.substring(PREFIX_BEARER.length());
        }
        return null;
    }
}
