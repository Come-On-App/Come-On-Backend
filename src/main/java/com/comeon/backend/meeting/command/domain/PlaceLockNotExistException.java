package com.comeon.backend.meeting.command.domain;

import com.comeon.backend.common.error.ErrorCode;
import com.comeon.backend.common.error.MeetingErrorCode;
import com.comeon.backend.common.error.RestApiException;

public class PlaceLockNotExistException extends RestApiException {

    private static final ErrorCode errorCode = MeetingErrorCode.PLACE_LOCK_NOT_EXIST;

    public PlaceLockNotExistException() {
        super(errorCode);
    }
}
