package com.comeon.backend.auth.domain;

import java.time.Instant;
import java.util.Optional;

public interface RefreshTokenRepository {

    void add(String refreshToken, Long userId, Instant expiry);

    Optional<Long> getUserIdBy(String refreshToken);

    void remove(String refreshToken);
}
