package com.comeon.backend.meeting.command.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MeetingCreateEvent {

    private Long meetingId;
    private Long hostUserId;

    public static MeetingCreateEvent create(Long meetingId, Long hostUserId) {
        return new MeetingCreateEvent(meetingId, hostUserId);
    }
}
