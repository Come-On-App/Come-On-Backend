package com.comeon.backend.meetingmember.command.application;

import com.comeon.backend.common.error.ErrorCode;
import com.comeon.backend.common.error.MeetingErrorCode;
import com.comeon.backend.common.error.RestApiException;

public class MemberNotExistException extends RestApiException {

    private static final ErrorCode errorCode = MeetingErrorCode.MEMBER_NOT_EXIST;

    public MemberNotExistException() {
        super(errorCode);
    }
}
