package com.comeon.backend.meeting.command.domain;

import com.comeon.backend.common.error.ErrorCode;
import com.comeon.backend.common.error.RestApiException;
import com.comeon.backend.common.error.MeetingErrorCode;

public class MemberAlreadyJoinedException extends RestApiException {

    private static final ErrorCode errorCode = MeetingErrorCode.ALREADY_JOINED;

    public MemberAlreadyJoinedException(Member member) {
        super("이미 모임에 가입된 유저입니다. meeting-id: " + member.getMeeting().getId()
                + ", user-id: " + member.getUserId(), errorCode);
    }

    public MemberAlreadyJoinedException(String message) {
        super(message, errorCode);
    }
}
