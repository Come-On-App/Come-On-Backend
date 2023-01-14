package com.comeon.backend.common.security;

import com.comeon.backend.common.jwt.JwtValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final static String PREFIX_BEARER = "Bearer ";

    private final JwtValidator jwtValidator;
    private final JwtAuthenticationProvider authenticationProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String accessTokenValue = resolveAccessToken(request);

        if (isValidAccessToken(accessTokenValue)) {
            Authentication authentication = authenticationProvider.getAuthentication(accessTokenValue);
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
