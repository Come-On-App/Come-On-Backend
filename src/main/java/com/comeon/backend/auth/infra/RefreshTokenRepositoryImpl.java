package com.comeon.backend.auth.infra;

import com.comeon.backend.auth.domain.RefreshTokenRepository;
import com.comeon.backend.common.redis.RedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository {

    private final RedisRepository redisRepository;

    @Override
    public void add(String refreshToken, Long userId, Instant expiry) {
        redisRepository.set(refreshToken, String.valueOf(userId), Duration.between(Instant.now(), expiry));
    }

    @Override
    public Optional<Long> getUserIdBy(String refreshToken) {
        String userId = redisRepository.get(refreshToken);
        if (userId == null) {
            return Optional.empty();
        }
        return Optional.of(Long.parseLong(userId));
    }

    @Override
    public void remove(String refreshToken) {
        redisRepository.remove(refreshToken);
    }
}
