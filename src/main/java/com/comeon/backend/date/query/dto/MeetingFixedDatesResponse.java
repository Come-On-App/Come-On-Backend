package com.comeon.backend.date.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MeetingFixedDatesResponse {

    private List<YearMeetings> result = new ArrayList<>();

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class YearMeetings {

        private Integer year;
        private List<MonthCalendar> calendar = new ArrayList<>();
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MonthCalendar {

        private Integer month;
        private List<MeetingFixedDateSummary> meetings = new ArrayList<>();
    }
}
