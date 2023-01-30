package com.comeon.backend.date.command.application.confirm;

import com.comeon.backend.common.error.ErrorCode;
import com.comeon.backend.common.error.MeetingErrorCode;
import com.comeon.backend.common.error.RestApiException;

public class DateRangeOutOfMeetingCalendarException extends RestApiException {

    private static final ErrorCode errorCode = MeetingErrorCode.DATE_RANGE_OUT_OF_CALENDAR_RANGE;

    public DateRangeOutOfMeetingCalendarException() {
        super(errorCode);
    }

    public DateRangeOutOfMeetingCalendarException(String message) {
        super(message, errorCode);
    }
}
