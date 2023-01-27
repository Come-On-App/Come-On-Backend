package com.comeon.backend.meeting.presentation;

import com.comeon.backend.common.error.ErrorCode;
import com.comeon.backend.common.error.RestApiException;
import com.comeon.backend.meeting.MeetingErrorCode;

public class NotMemberException extends RestApiException {

    private static final ErrorCode errorCode = MeetingErrorCode.NOT_MEMBER;

    public NotMemberException() {
        super(errorCode);
    }

    public NotMemberException(String message) {
        super(message, errorCode);
    }
}
