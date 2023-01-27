package com.comeon.backend.meeting.command.domain;

import com.comeon.backend.common.model.BaseTimeEntity;
import com.comeon.backend.meeting.MemberRole;
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

    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    private MeetingMember(Long userId, Meeting meeting, MemberRole role) {
        this.userId = userId;
        this.meeting = meeting;
        this.role = role;
    }

    public static MeetingMember createHostMember(Long userId, Meeting meeting) {
        return new MeetingMember(userId, meeting, MemberRole.HOST);
    }

    public static MeetingMember createParticipantMember(Long userId, Meeting meeting) {
        return new MeetingMember(userId, meeting, MemberRole.PARTICIPANT);
    }

    public boolean isHost() {
        return this.role == MemberRole.HOST;
    }
}
