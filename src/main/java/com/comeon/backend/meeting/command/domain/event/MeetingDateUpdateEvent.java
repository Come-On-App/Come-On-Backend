package com.comeon.backend.meeting.command.domain.event;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MeetingDateUpdateEvent {

    private Long meetingId;

    public static MeetingDateUpdateEvent create(Long meetingId) {
        return new MeetingDateUpdateEvent(meetingId);
    }
}
