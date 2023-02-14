package com.comeon.backend.meeting.command.domain;

import com.comeon.backend.common.error.ErrorCode;
import com.comeon.backend.common.error.MeetingErrorCode;
import com.comeon.backend.common.error.RestApiException;

public class CalendarRangeException extends RestApiException {

    private static final ErrorCode errorCode = MeetingErrorCode.MEETING_CALENDAR_RANGE_INVALID;

    public CalendarRangeException() {
        super(errorCode);
    }
}
