package com.comeon.backend.meeting.command.domain;

import com.comeon.backend.common.error.ErrorCode;
import com.comeon.backend.common.error.RestApiException;
import com.comeon.backend.common.error.MeetingErrorCode;

public class EntryCodeNotMatchedException extends RestApiException {

    private static final ErrorCode errorCode = MeetingErrorCode.ENTRY_CODE_NOT_MATCHED;

    public EntryCodeNotMatchedException() {
        super(errorCode);
    }

    public EntryCodeNotMatchedException(String message) {
        super(message, errorCode);
    }
}
