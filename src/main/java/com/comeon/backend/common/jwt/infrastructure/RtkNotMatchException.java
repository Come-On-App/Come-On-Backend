package com.comeon.backend.common.jwt.infrastructure;

import com.comeon.backend.common.error.ErrorCode;
import com.comeon.backend.common.error.JwtErrorCode;
import com.comeon.backend.common.error.RestApiException;

public class RtkNotMatchException extends RestApiException {

    private static final ErrorCode errorCode = JwtErrorCode.INVALID_USER_RTK;

    public RtkNotMatchException() {
        super(errorCode);
    }
}
