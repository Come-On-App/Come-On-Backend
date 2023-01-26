package com.comeon.backend.meeting.query.dao.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MeetingDetailsResponse {

    private MeetingMetaData meetingMetaData;
    private List<MemberListResponse> members;
//    private List<DateListResponse> dates;
    private List<PlaceListResponse> places;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MeetingMetaData {
        private Long meetingId;
        private String thumbnailImageUrl;
        private String meetingName;
        private MeetingCalendar calendar;
        private FixedDate fixedDate;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MeetingCalendar {

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private LocalDate startFrom;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private LocalDate endTo;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FixedDate {

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private LocalDate startFrom;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private LocalDate endTo;
    }
}
