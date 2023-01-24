package com.comeon.backend.jwt.infra;

import com.comeon.backend.jwt.domain.ReissueCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@Component
@RequiredArgsConstructor
public class ReissueConditionImpl implements ReissueCondition {

    private final JwtProperties jwtProperties;

    @Override
    public boolean isSatisfied(Instant expiration) {
        long remainSec = Duration.between(Instant.now(), expiration).toSeconds();
        return remainSec <= jwtProperties.getRtkReissueCriteria();
    }
}
