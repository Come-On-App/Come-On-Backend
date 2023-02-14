package com.comeon.backend.meeting.command.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MeetingInfoModifyEvent {

    private Long meetingId;

    public static MeetingInfoModifyEvent create(Long meetingId) {
        return new MeetingInfoModifyEvent(meetingId);
    }
}
