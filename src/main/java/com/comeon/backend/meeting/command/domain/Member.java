package com.comeon.backend.meeting.command.domain;

import com.comeon.backend.common.model.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity @Getter
@Table(name = "meeting_member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meeting_member_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;

    private Long userId;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    private Member(Meeting meeting, Long userId, MemberRole role) {
        this.meeting = meeting;
        this.userId = userId;
        this.role = role;
    }

    public static Member createHost(Meeting meeting, Long userId) {
        return new Member(meeting, userId, MemberRole.HOST);
    }

    public static Member createParticipant(Meeting meeting, Long userId) {
        return new Member(meeting, userId, MemberRole.PARTICIPANT);
    }

    public boolean isHost() {
        return this.role == MemberRole.HOST;
    }

    public void updateRoleToHost() {
        // TODO case Already Host
        this.role = MemberRole.HOST;
    }

    public void updateRoleToParticipant() {
        this.role = MemberRole.PARTICIPANT;
    }
}
