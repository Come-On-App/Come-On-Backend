package com.comeon.backend.common.jwt.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@Component
@RequiredArgsConstructor
public class ReissueCondition {

    private final JwtProperties jwtProperties;

    public boolean isSatisfied(Instant expiration) {
        long remainSec = Duration.between(Instant.now(), expiration).toSeconds();
        return remainSec <= jwtProperties.getRtkReissueCriteria();
    }
}
