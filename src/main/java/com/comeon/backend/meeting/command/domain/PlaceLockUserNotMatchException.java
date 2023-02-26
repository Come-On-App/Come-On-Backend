package com.comeon.backend.meeting.command.domain;

import com.comeon.backend.common.error.ErrorCode;
import com.comeon.backend.common.error.MeetingErrorCode;
import com.comeon.backend.common.error.RestApiException;

public class PlaceLockUserNotMatchException extends RestApiException {

    private static final ErrorCode errorCode = MeetingErrorCode.PLACE_LOCK_USER_NOT_MATCH;

    public PlaceLockUserNotMatchException() {
        super(errorCode);
    }
}
