package com.comeon.backend.meeting.command.domain;

import com.comeon.backend.common.error.ErrorCode;
import com.comeon.backend.common.error.MeetingErrorCode;
import com.comeon.backend.common.error.RestApiException;

public class PlaceLockAlreadyExistException extends RestApiException {

    private static final ErrorCode errorCode = MeetingErrorCode.PLACE_LOCK_ALREADY_EXIST;

    public PlaceLockAlreadyExistException() {
        super(errorCode);
    }
}
