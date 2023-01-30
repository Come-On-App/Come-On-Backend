package com.comeon.backend.date.command.application.voting;

import com.comeon.backend.common.error.ErrorCode;
import com.comeon.backend.common.error.MeetingErrorCode;
import com.comeon.backend.common.error.RestApiException;
import com.comeon.backend.date.command.domain.voting.DateVoting;

public class DateVotingAlreadyExistException extends RestApiException {

    private static final ErrorCode errorCode = MeetingErrorCode.DATE_VOTING_EXIST;
    private static final String defaultMessage = "이미 해당 일자에 투표한 이력이 있습니다.";

    public DateVotingAlreadyExistException() {
        super(defaultMessage, errorCode);
    }

    public DateVotingAlreadyExistException(String message) {
        super(message, errorCode);
    }

    public DateVotingAlreadyExistException(DateVoting dateVoting) {
        super(defaultMessage + dateVoting.toString(), errorCode);
    }
}
