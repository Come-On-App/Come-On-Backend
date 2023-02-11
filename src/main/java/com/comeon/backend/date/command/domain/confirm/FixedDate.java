package com.comeon.backend.date.command.domain.confirm;

import com.comeon.backend.common.event.Events;
import com.comeon.backend.common.model.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity @Getter
@Table(name = "meeting_fixed_date")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FixedDate extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fixed_date_id")
    private Long id;

    private Long meetingId;
    private LocalDate startFrom;
    private LocalDate endTo;

    public FixedDate(Long meetingId, LocalDate startFrom, LocalDate endTo) {
        this.meetingId = meetingId;
        this.startFrom = startFrom;
        this.endTo = endTo;

        Events.raise(FixedDateEvent.create(meetingId));
    }

    @Override
    public String toString() {
        return "["
                + "meetingId: " + meetingId
                + "startFrom: " + startFrom.format(DateTimeFormatter.ISO_DATE)
                + "endTo: " + endTo.format(DateTimeFormatter.ISO_DATE)
                + "]";
    }
}
