package com.comeon.backend.meetingmember.command.domain;

import com.comeon.backend.common.event.Events;
import com.comeon.backend.common.model.BaseTimeEntity;
import com.comeon.backend.config.web.member.MemberRole;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MeetingMember extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meeting_member_id")
    private Long id;

    private Long meetingId;

    private Long userId;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    private MeetingMember(Long meetingId, Long userId, MemberRole role) {
        this.meetingId = meetingId;
        this.userId = userId;
        this.role = role;
    }

    public static MeetingMember createHostMember(Long meetingId, Long userId) {
        return new MeetingMember(meetingId, userId, MemberRole.HOST);
    }

    public static MeetingMember createParticipantMember(Long meetingId, Long userId) {
        MeetingMember member = new MeetingMember(meetingId, userId, MemberRole.PARTICIPANT);
        Events.raise(MeetingMemberEvent.create(meetingId));

        return member;
    }

    public boolean isHost() {
        return this.role == MemberRole.HOST;
    }
}
