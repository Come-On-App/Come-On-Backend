package com.comeon.backend.meeting.infrastructure.event.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MeetingDateUpdatedMessage {

    private Long meetingId;
}
