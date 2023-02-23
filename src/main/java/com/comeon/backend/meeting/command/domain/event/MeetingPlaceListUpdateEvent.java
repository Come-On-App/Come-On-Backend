package com.comeon.backend.meeting.command.domain.event;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MeetingPlaceListUpdateEvent {

    private Long meetingId;

    public static MeetingPlaceListUpdateEvent create(Long meetingId) {
        return new MeetingPlaceListUpdateEvent(meetingId);
    }
}
