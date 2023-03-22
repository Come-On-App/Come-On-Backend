package com.comeon.backend.meeting.command.domain.event;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberDropEvent {

    private Long meetingId;
    private Long userId;

    public static MemberDropEvent create(Long meetingId, Long userId) {
        return new MemberDropEvent(meetingId, userId);
    }
}
