package com.comeon.backend.date.command.domain.confirm;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Entity @Getter
@Table(name = "meeting_fixed_date")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FixedDate {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fixed_date_id")
    private Long id;

    private Long meetingId;
    private LocalDate startFrom;
    private LocalDate endTo;
    private LocalTime startTime;

    public FixedDate(Long meetingId, LocalDate startFrom, LocalDate endTo, LocalTime startTime) {
        this.meetingId = meetingId;
        this.startFrom = startFrom;
        this.endTo = endTo;
        this.startTime = startTime;
    }

    @Override
    public String toString() {
        return "["
                + "meetingId: " + meetingId
                + "startFrom: " + startFrom.format(DateTimeFormatter.ISO_DATE)
                + "endTo: " + endTo.format(DateTimeFormatter.ISO_DATE)
                + "startTime: " + startTime.format(DateTimeFormatter.ISO_LOCAL_TIME)
                + "]";
    }
}
