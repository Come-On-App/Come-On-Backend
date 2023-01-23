package com.comeon.backend.auth.domain;

import java.time.Instant;

public interface ReissueCondition {

    boolean isSatisfied(Instant expiration);
}
