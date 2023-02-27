package com.comeon.backend.common.jwt.infrastructure;

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

    public void add(Long userId, String refreshToken, Instant expiry) {
        redisRepository.set(String.valueOf(userId), refreshToken, Duration.between(Instant.now(), expiry));
    }

    public Optional<String> findRtkValueByUserId(Long userId) {
        String rtkValue = redisRepository.get(String.valueOf(userId));
        if (rtkValue == null) {
            return Optional.empty();
        }
        return Optional.of(rtkValue);
    }

    public void remove(Long userId) {
        redisRepository.remove(String.valueOf(userId));
    }
}
