package com.comeon.backend.meeting.infrastructure.event.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MeetingPlaceUnlockMessage {

    private Long meetingId;
    private Long meetingPlaceId;
    private Long userId;
}
