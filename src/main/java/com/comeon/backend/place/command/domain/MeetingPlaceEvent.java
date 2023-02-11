package com.comeon.backend.place.command.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MeetingPlaceEvent {

    private Long targetMeetingId;

    public static MeetingPlaceEvent create(Long meetingId) {
        return new MeetingPlaceEvent(meetingId);
    }
}
