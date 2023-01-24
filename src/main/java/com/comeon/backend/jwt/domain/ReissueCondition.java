package com.comeon.backend.jwt.domain;

import java.time.Instant;

public interface ReissueCondition {

    boolean isSatisfied(Instant expiration);
}
