package com.comeon.backend.config.web.member;

import java.util.Optional;

public interface UserProvider {

    Optional<Long> getUserId();
}
