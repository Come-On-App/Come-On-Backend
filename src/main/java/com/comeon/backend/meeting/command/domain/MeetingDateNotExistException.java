package com.comeon.backend.meeting.command.domain;

import com.comeon.backend.common.error.ErrorCode;
import com.comeon.backend.common.error.MeetingErrorCode;
import com.comeon.backend.common.error.RestApiException;

public class MeetingDateNotExistException extends RestApiException {

    private static final ErrorCode errorCode = MeetingErrorCode.MEETING_DATE_NOT_EXIST;

    public MeetingDateNotExistException() {
        super(errorCode);
    }
}
