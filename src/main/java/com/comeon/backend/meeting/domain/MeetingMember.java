package com.comeon.backend.meeting.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MeetingMember {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meeting_member_id")
    private Long id;

    // User 엔티티 매핑??
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;

    @Enumerated(EnumType.STRING)
    private MeetingMemberRole role;

    private MeetingMember(Long userId, Meeting meeting, MeetingMemberRole role) {
        this.userId = userId;
        this.meeting = meeting;
        this.role = role;
    }

    public static MeetingMember createHostMember(Long userId, Meeting meeting) {
        return new MeetingMember(userId, meeting, MeetingMemberRole.HOST);
    }

    public static MeetingMember createParticipantMember(Long userId, Meeting meeting) {
        return new MeetingMember(userId, meeting, MeetingMemberRole.PARTICIPANT);
    }
}
