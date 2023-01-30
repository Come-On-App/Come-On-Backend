package com.comeon.backend.date.command.application.confirm;

import com.comeon.backend.common.error.ErrorCode;
import com.comeon.backend.common.error.MeetingErrorCode;
import com.comeon.backend.common.error.RestApiException;
import com.comeon.backend.date.command.domain.confirm.FixedDate;

public class FixedDateAlreadyExistException extends RestApiException {

    private static final ErrorCode errorCode = MeetingErrorCode.FIXED_DATE_EXIST;
    private static final String defaultMessage = "이미 확정된 모임일이 있습니다.";

    public FixedDateAlreadyExistException(FixedDate fixedDate) {
        super(defaultMessage + fixedDate.toString(), errorCode);
    }
}
