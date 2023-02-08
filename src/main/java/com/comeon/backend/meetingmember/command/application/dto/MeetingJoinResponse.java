package com.comeon.backend.meetingmember.command.application.dto;

import com.comeon.backend.meetingmember.command.domain.MeetingMember;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class MeetingJoinResponse {

    private Long meetingId;
    private MemberSimple meetingMember;

    public MeetingJoinResponse(MeetingMember member) {
        this.meetingId = member.getMeetingId();
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
