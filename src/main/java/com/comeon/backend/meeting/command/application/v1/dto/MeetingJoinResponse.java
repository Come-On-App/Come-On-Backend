package com.comeon.backend.meeting.command.application.v1.dto;

import com.comeon.backend.meeting.command.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class MeetingJoinResponse {

    private Long meetingId;
    private MemberSimple meetingMember;

    public MeetingJoinResponse(Member member) {
        this.meetingId = member.getMeeting().getId();
        this.meetingMember = new MemberSimple(member.getId(), member.getRole().name());
    }

    public MeetingJoinResponse(Long meetingId, Long memberId, String memberRole) {
        this.meetingId = meetingId;
        this.meetingMember = new MemberSimple(memberId, memberRole);
    }

    @Getter
    @AllArgsConstructor
    public static class MemberSimple {
        private Long memberId;
        private String memberRole;
    }
}
