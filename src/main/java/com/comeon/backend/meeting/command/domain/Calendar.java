package com.comeon.backend.meeting.command.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDate;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Calendar {

    @Column(name = "calendar_start_from")
    private LocalDate startFrom;

    @Column(name = "calendar_end_to")
    private LocalDate endTo;

    public Calendar(LocalDate startFrom, LocalDate endTo) {
        this.startFrom = startFrom;
        this.endTo = endTo;
    }
}
