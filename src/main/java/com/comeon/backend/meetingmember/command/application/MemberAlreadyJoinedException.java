package com.comeon.backend.meetingmember.command.application;

import com.comeon.backend.common.error.ErrorCode;
import com.comeon.backend.common.error.RestApiException;
import com.comeon.backend.common.error.MeetingErrorCode;
import com.comeon.backend.meetingmember.command.domain.MeetingMember;

public class MemberAlreadyJoinedException extends RestApiException {

    private static final ErrorCode errorCode = MeetingErrorCode.ALREADY_JOINED;

    public MemberAlreadyJoinedException(MeetingMember member) {
        super("이미 모임에 가입된 유저입니다. meeting-id: " + member.getMeetingId()
                + ", user-id: " + member.getUserId(), errorCode);
    }

    public MemberAlreadyJoinedException(String message) {
        super(message, errorCode);
    }
}
