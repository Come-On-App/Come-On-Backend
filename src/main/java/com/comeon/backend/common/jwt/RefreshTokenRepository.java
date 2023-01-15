package com.comeon.backend.common.jwt;

import com.comeon.backend.common.redis.RedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RefreshTokenRepository {

    private final RedisRepository redisRepository;

    public void add(String refreshToken, Long userId, Instant expiry) {
        redisRepository.set(refreshToken, String.valueOf(userId), Duration.between(Instant.now(), expiry));
    }

    public Optional<Long> getUserIdBy(String refreshToken) {
        String userId = redisRepository.get(refreshToken);
        if (userId == null) {
            return Optional.empty();
        }
        return Optional.of(Long.parseLong(userId));
    }

    public void remove(String refreshToken) {
        redisRepository.remove(refreshToken);
    }
}
