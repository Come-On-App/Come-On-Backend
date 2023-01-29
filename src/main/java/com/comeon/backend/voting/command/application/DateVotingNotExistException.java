package com.comeon.backend.voting.command.application;

import com.comeon.backend.common.error.ErrorCode;
import com.comeon.backend.common.error.MeetingErrorCode;
import com.comeon.backend.common.error.RestApiException;

public class DateVotingNotExistException extends RestApiException {

    private static final ErrorCode errorCode = MeetingErrorCode.NO_DATE_VOTING_EXIST;

    public DateVotingNotExistException() {
        super("해당 일자에 투표한 이력이 없습니다.", errorCode);
    }

    public DateVotingNotExistException(String message) {
        super(message, errorCode);
    }
}
