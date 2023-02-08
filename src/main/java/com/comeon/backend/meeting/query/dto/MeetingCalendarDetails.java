package com.comeon.backend.meeting.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class MeetingCalendarDetails {

    private LocalDate startFrom;
    private LocalDate endTo;
}
