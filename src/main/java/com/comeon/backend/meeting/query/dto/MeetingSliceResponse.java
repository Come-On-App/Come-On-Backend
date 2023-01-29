package com.comeon.backend.meeting.query.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MeetingSliceResponse {

    private Long meetingId;
    private UserSimple hostUser;
    private Integer memberCount;
    private String myMeetingRole;
    private String meetingName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate calendarStartFrom;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate calendarEndTo;

    private String meetingImageUrl;

    private List<FixedDate> fixedDates;

    private String meetingStatus;

    @Getter
    @AllArgsConstructor
    public static class UserSimple {

        private Long userId;
        private String nickname;
        private String profileImageUrl;
    }

    @Getter
    @AllArgsConstructor
    public static class FixedDate {

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private LocalDate meetingStartFrom;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private LocalDate meetingEndTo;
    }
}
