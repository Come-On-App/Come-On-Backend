package com.comeon.backend.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByOauthIdAndProvider(String oauthId, OauthProvider provider);
}
