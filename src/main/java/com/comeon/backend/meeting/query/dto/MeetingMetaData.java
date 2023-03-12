package com.comeon.backend.meeting.query.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MeetingMetaData {

    private Long meetingId;
    private String thumbnailImageUrl;
    private String meetingName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime meetingStartTime;

    private HostUserSimple hostUser;
    private MeetingCalendar calendar;
    private ConfirmedDate fixedDate;

    public void setHostUserProfileImageUrl(String defaultImageUrl) {
        this.hostUser.profileImageUrl = defaultImageUrl;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HostUserSimple {

        private Long userId;
        private String nickname;
        private String profileImageUrl;
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
    public static class ConfirmedDate {

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private LocalDate startFrom;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private LocalDate endTo;
    }
}
