package com.comeon.backend.meeting.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class MeetingCalendarResponse {

    private LocalDate startFrom;
    private LocalDate endTo;
}
