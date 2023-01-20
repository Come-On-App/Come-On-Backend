package com.comeon.backend.user.command.domain;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findBy(Long userId);
    Optional<User> findBy(String oauthId, OauthProvider provider);
    User save(User user);
}
