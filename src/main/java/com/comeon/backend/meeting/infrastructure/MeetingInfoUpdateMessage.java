package com.comeon.backend.meeting.infrastructure;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MeetingInfoUpdateMessage {

    private Long targetMeetingId;
}
