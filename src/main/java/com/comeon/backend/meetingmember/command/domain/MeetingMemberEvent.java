package com.comeon.backend.meetingmember.command.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MeetingMemberEvent {

    private Long meetingId;

    public static MeetingMemberEvent create(Long meetingId) {
        return new MeetingMemberEvent(meetingId);
    }
}
