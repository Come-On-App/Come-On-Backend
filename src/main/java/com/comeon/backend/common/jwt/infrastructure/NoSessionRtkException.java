package com.comeon.backend.common.jwt.infrastructure;

import com.comeon.backend.common.error.ErrorCode;
import com.comeon.backend.common.error.JwtErrorCode;
import com.comeon.backend.common.error.RestApiException;

public class NoSessionRtkException extends RestApiException {

    private static final ErrorCode errorCode = JwtErrorCode.NO_SESSION;

    public NoSessionRtkException() {
        super(errorCode);
    }

    public NoSessionRtkException(String message) {
        super(message, errorCode);
    }
}
