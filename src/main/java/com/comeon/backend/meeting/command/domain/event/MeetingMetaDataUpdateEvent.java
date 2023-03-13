package com.comeon.backend.meeting.command.domain.event;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MeetingMetaDataUpdateEvent {

    private Long meetingId;

    public static MeetingMetaDataUpdateEvent create(Long meetingId) {
        return new MeetingMetaDataUpdateEvent(meetingId);
    }
}
