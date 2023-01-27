package com.comeon.backend.meeting.command.domain;

import com.comeon.backend.common.exception.ErrorCode;
import com.comeon.backend.common.exception.RestApiException;
import com.comeon.backend.meeting.MeetingErrorCode;

public class MemberAlreadyJoinedException extends RestApiException {

    private static final ErrorCode errorCode = MeetingErrorCode.ALREADY_JOINED;

    public MemberAlreadyJoinedException(MeetingMember member) {
        super("이미 모임에 가입된 유저입니다. meeting-id: " + member.getMeeting().getId()
                + ", user-id: " + member.getUserId(), errorCode);
    }

    public MemberAlreadyJoinedException(String message) {
        super(message, errorCode);
    }
}
