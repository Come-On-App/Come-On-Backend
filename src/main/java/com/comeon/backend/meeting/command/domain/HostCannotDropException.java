package com.comeon.backend.meeting.command.domain;

import com.comeon.backend.common.error.ErrorCode;
import com.comeon.backend.common.error.MeetingErrorCode;
import com.comeon.backend.common.error.RestApiException;

public class HostCannotDropException extends RestApiException {

    private static final ErrorCode errorCode = MeetingErrorCode.HOST_CANNOT_DROP;

    public HostCannotDropException() {
        super(errorCode);
    }
}
