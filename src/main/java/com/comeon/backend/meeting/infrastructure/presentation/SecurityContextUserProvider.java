package com.comeon.backend.meeting.infrastructure.presentation;

import com.comeon.backend.common.security.JwtPrincipal;
import com.comeon.backend.meeting.presentation.UserProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SecurityContextUserProvider implements UserProvider {

    @Override
    public Optional<Long> getUserId() {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context != null) {
            Authentication authentication = context.getAuthentication();
            if (authentication != null) {
                Object principal = authentication.getPrincipal();
                if (principal instanceof JwtPrincipal) {
                    JwtPrincipal jwtPrincipal = (JwtPrincipal) principal;
                    Long userId = jwtPrincipal.getUserId();
                    return Optional.of(userId);
                }
            }
        }
        return Optional.empty();
    }
}
