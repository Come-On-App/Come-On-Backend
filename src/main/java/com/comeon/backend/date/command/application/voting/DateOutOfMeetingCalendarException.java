package com.comeon.backend.date.command.application.voting;

import com.comeon.backend.common.error.ErrorCode;
import com.comeon.backend.common.error.MeetingErrorCode;
import com.comeon.backend.common.error.RestApiException;

public class DateOutOfMeetingCalendarException extends RestApiException {

    private static final ErrorCode errorCode = MeetingErrorCode.DATE_OUT_OF_CALENDAR_RANGE;

    public DateOutOfMeetingCalendarException() {
        super(errorCode);
    }

    public DateOutOfMeetingCalendarException(String message) {
        super(message, errorCode);
    }
}
