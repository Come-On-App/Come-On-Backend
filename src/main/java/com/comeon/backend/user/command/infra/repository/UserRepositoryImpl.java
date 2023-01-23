package com.comeon.backend.user.command.infra.repository;

import com.comeon.backend.user.command.domain.OauthProvider;
import com.comeon.backend.user.command.domain.User;
import com.comeon.backend.user.command.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;


    @Override
    public Optional<User> findBy(Long userId) {
        return userJpaRepository.findById(userId);
    }

    @Override
    public Optional<User> findBy(String oauthId, OauthProvider provider) {
        return userJpaRepository.findByOauthIdAndProvider(oauthId, provider);
    }

    @Override
    public User save(User user) {
        return userJpaRepository.save(user);
    }
}
