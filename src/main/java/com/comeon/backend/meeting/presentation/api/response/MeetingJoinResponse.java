package com.comeon.backend.meeting.presentation.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class MeetingJoinResponse {

    private Long meetingId;
    private MeetingMember meetingMember;

    public MeetingJoinResponse(Long meetingId, Long memberId, String memberRole) {
        this.meetingId = meetingId;
        this.meetingMember = new MeetingMember(memberId, memberRole);
    }

    @Getter
    @AllArgsConstructor
    public static class MeetingMember {
        private Long memberId;
        private String role;
    }
}
