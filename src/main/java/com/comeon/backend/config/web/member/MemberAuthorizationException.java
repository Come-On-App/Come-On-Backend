package com.comeon.backend.config.web.member;

import com.comeon.backend.common.error.ErrorCode;
import com.comeon.backend.common.error.MeetingErrorCode;
import com.comeon.backend.common.error.RestApiException;

public class MemberAuthorizationException extends RestApiException {

    private static final ErrorCode errorCode = MeetingErrorCode.NO_AUTHORITIES;

    public MemberAuthorizationException() {
        super(errorCode);
    }

    public MemberAuthorizationException(String message) {
        super(message, errorCode);
    }
}
