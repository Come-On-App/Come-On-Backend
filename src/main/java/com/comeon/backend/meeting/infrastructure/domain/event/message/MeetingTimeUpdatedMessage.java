package com.comeon.backend.meeting.infrastructure.domain.event.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MeetingTimeUpdatedMessage {

    private Long targetMeetingId;
}
