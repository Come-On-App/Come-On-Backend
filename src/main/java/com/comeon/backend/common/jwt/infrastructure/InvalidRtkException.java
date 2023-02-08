package com.comeon.backend.common.jwt.infrastructure;

import com.comeon.backend.common.error.ErrorCode;
import com.comeon.backend.common.error.JwtErrorCode;
import com.comeon.backend.common.error.RestApiException;

public class InvalidRtkException extends RestApiException {

    private static final ErrorCode errorCode = JwtErrorCode.INVALID_USER_RTK;

    public InvalidRtkException() {
        super("리프레시 토큰 검증 실패", errorCode);
    }
}
