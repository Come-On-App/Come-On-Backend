package com.comeon.backend.meeting.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class MeetingFixedDatesResponse {

    private List<YearMeetings> result;

    @Getter
    @AllArgsConstructor
    public static class YearMeetings {

        private Integer year;
        private List<MonthCalendar> calendar;
    }

    @Getter
    @AllArgsConstructor
    public static class MonthCalendar {

        private Integer month;
        private List<MeetingFixedDateSummary> meetings;
    }
}
