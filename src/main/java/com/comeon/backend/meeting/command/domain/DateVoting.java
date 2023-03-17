package com.comeon.backend.meeting.command.domain;

import com.comeon.backend.common.model.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity @Getter
@Table(name = "meeting_date_voting")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DateVoting extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meeting_date_voting_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;

    private Long userId;
    private LocalDate date;

    public DateVoting(Meeting meeting, Long userId, LocalDate date) {
        this.meeting = meeting;
        this.userId = userId;
        this.date = date;
    }

    public boolean isRangeIn(LocalDate startFrom, LocalDate endTo) {
        return !this.date.isBefore(startFrom)
                && !this.date.isAfter(endTo);
    }

    public boolean isVoter(Long userId) {
        return this.userId.equals(userId);
    }
}
