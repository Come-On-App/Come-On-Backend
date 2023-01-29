package com.comeon.backend.meeting.query.application;

import com.comeon.backend.common.error.ErrorCode;
import com.comeon.backend.common.error.MeetingErrorCode;
import com.comeon.backend.common.error.RestApiException;

public class NotMemberException extends RestApiException {

    private static final ErrorCode errorCode = MeetingErrorCode.NOT_MEMBER;

    public NotMemberException() {
        super(errorCode);
    }

    public NotMemberException(String message) {
        super(message, errorCode);
    }
}
