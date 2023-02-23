package com.comeon.backend.meeting.command.domain;

import com.comeon.backend.common.error.ErrorCode;
import com.comeon.backend.common.error.MeetingErrorCode;
import com.comeon.backend.common.error.RestApiException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateRangeOutOfMeetingCalendarException extends RestApiException {

    private static final ErrorCode errorCode = MeetingErrorCode.DATE_RANGE_OUT_OF_CALENDAR_RANGE;

    public DateRangeOutOfMeetingCalendarException() {
        super(errorCode);
    }

    public DateRangeOutOfMeetingCalendarException(Long meetingId, LocalDate startFrom, LocalDate endTo) {
        super("모임일 확정 일자가 모임 캘린더 범위에 포함되지 않습니다. meetingId: " + meetingId +
                ", startFrom: " + startFrom.format(DateTimeFormatter.ISO_DATE) +
                ", endTo: " + endTo.format(DateTimeFormatter.ISO_DATE), errorCode);
    }

    public DateRangeOutOfMeetingCalendarException(String message) {
        super(message, errorCode);
    }
}
