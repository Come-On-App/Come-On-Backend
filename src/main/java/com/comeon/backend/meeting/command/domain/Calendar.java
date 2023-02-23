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

    private Calendar(LocalDate startFrom, LocalDate endTo) {
        this.startFrom = startFrom;
        this.endTo = endTo;
    }

    public static Calendar create(LocalDate startFrom, LocalDate endTo) {
        if (!verifyCalendarRange(startFrom, endTo)) {
            throw new CalendarRangeException();
        }
        return new Calendar(startFrom, endTo);
    }

    private static boolean verifyCalendarRange(LocalDate startFrom, LocalDate endTo) {
        return !endTo.isBefore(startFrom);
    }

    public boolean verifyDate(LocalDate date) {
        return !this.startFrom.isAfter(date)
                && !this.endTo.isBefore(date);
    }

    public boolean verifyDateRange(LocalDate startFrom, LocalDate endTo) {
        return !this.startFrom.isAfter(startFrom)
                && !this.startFrom.isAfter(endTo)
                && !this.endTo.isBefore(startFrom)
                && !this.endTo.isBefore(endTo);
    }
}
