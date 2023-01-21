package com.comeon.backend.meeting.query.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class MeetingCondition {

    private String searchWords;
    private LocalDate dateFrom;
    private LocalDate dateTo;
}
