package com.comeon.backend.common.config.interceptor;

import java.util.Optional;

public interface UserProvider {

    Optional<Long> getUserId();
}
