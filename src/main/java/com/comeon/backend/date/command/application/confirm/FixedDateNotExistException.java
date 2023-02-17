package com.comeon.backend.date.command.application.confirm;

import com.comeon.backend.common.error.ErrorCode;
import com.comeon.backend.common.error.MeetingErrorCode;
import com.comeon.backend.common.error.RestApiException;

public class FixedDateNotExistException extends RestApiException {

    private static final ErrorCode errorCode = MeetingErrorCode.FIXED_DATE_NOT_EXIST;

    public FixedDateNotExistException() {
        super(errorCode);
    }
}
