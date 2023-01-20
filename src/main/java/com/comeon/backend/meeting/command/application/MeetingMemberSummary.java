package com.comeon.backend.meeting.command.application;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MeetingMemberSummary {

    private Long meetingId;
    private Long memberId;
    private String memberRole;
}
