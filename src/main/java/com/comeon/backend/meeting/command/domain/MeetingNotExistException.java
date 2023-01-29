package com.comeon.backend.meeting.command.domain;

import com.comeon.backend.common.error.RestApiException;
import com.comeon.backend.common.error.MeetingErrorCode;

public class MeetingNotExistException extends RestApiException {
    public MeetingNotExistException(Long meetingId) {
        super("존재하지 않는 모임 식별값입니다. meetingId: " + meetingId, MeetingErrorCode.MEETING_NOT_EXIST);
    }
}
