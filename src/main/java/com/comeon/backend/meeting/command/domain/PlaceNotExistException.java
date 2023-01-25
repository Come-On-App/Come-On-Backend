package com.comeon.backend.meeting.command.domain;

import com.comeon.backend.common.exception.RestApiException;
import com.comeon.backend.meeting.common.MeetingErrorCode;

public class PlaceNotExistException extends RestApiException {

    public PlaceNotExistException(Long meetingPlaceId) {
        super("삭제하려는 장소가 없습니다. meetingPlaceId: " + meetingPlaceId, MeetingErrorCode.INVALID_PLACE_ID);
    }
}
