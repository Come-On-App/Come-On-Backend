package com.comeon.backend.meeting.presentation.interceptor;

import java.util.Optional;

public interface UserProvider {

    Optional<Long> getUserId();
}
