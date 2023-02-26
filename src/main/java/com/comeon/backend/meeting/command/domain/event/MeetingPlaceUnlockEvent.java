package com.comeon.backend.meeting.command.domain.event;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MeetingPlaceUnlockEvent {

    private Long meetingId;
    private Long meetingPlaceId;
    private Long userId;

    public static MeetingPlaceUnlockEvent create(Long meetingId, Long meetingPlaceId, Long userId) {
        return new MeetingPlaceUnlockEvent(meetingId, meetingPlaceId, userId);
    }
}
