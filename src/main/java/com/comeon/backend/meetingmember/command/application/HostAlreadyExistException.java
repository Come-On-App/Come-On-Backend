package com.comeon.backend.meetingmember.command.application;

import com.comeon.backend.common.error.ErrorCode;
import com.comeon.backend.common.error.MeetingErrorCode;
import com.comeon.backend.common.error.RestApiException;

public class HostAlreadyExistException extends RestApiException {

    private static final ErrorCode errorCode = MeetingErrorCode.HOST_ALREADY_EXIST;

    public HostAlreadyExistException(Long meetingId, Long hostUserId) {
        super("모임에 HOST 권한을 가진 멤버가 존재합니다. meetingId: " + meetingId + ", hostUserId: " + hostUserId, errorCode);
    }
}
