package com.comeon.backend.meeting.presentation;

import com.comeon.backend.common.error.ErrorCode;
import com.comeon.backend.common.error.RestApiException;
import com.comeon.backend.meeting.MeetingErrorCode;

public class MemberAuthorizationException extends RestApiException {

    private static final ErrorCode errorCode = MeetingErrorCode.NO_AUTHORITIES;

    public MemberAuthorizationException() {
        super(errorCode);
    }

    public MemberAuthorizationException(String message) {
        super(message, errorCode);
    }
}