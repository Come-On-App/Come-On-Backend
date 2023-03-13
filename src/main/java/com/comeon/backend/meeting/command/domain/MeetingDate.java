package com.comeon.backend.meeting.command.domain;

import com.comeon.backend.common.model.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Getter @Entity
@Table(name = "meeting_fixed_date")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MeetingDate extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fixed_date_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;

    private LocalDate startFrom;
    private LocalDate endTo;

    public MeetingDate(Meeting meeting, LocalDate startFrom, LocalDate endTo) {
        this.meeting = meeting;
        this.startFrom = startFrom;
        this.endTo = endTo;
    }

    public void update(LocalDate startFrom, LocalDate endTo) {
        this.startFrom = startFrom;
        this.endTo = endTo;
    }
}
