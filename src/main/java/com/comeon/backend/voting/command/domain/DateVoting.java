package com.comeon.backend.voting.command.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
@Table(name = "meeting_date_voting")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DateVoting {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meeting_date_voting_id")
    private Long id;

    private Long meetingId;
    private Long userId;
    private LocalDate date;

    public DateVoting(Long meetingId, Long userId, LocalDate date) {
        this.meetingId = meetingId;
        this.userId = userId;
        this.date = date;
    }

    @Override
    public String toString() {
        return "[votingId: " + id +
                ", meetingId: " + meetingId +
                ", userId: " + userId +
                ", date: " + date.format(DateTimeFormatter.ISO_DATE) +
                "]";
    }
}
