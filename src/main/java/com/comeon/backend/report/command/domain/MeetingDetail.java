package com.comeon.backend.report.command.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MeetingDetail {

    private MeetingMetaData meetingMetaData;
    private List<MemberDetails> members;
    private List<PlaceDetails> places;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MeetingMetaData {
        private Long meetingId;
        private String thumbnailImageUrl;
        private String meetingName;

        private LocalTime meetingStartTime;

        private HostUserSimple hostUser;
        private MeetingCalendar calendar;
        private ConfirmedDate fixedDate;
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

        private LocalDate startFrom;
        private LocalDate endTo;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ConfirmedDate {

        private LocalDate startFrom;
        private LocalDate endTo;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MemberDetails {

        private Long memberId;
        private Long userId;
        private String nickname;
        private String profileImageUrl;
        private String memberRole;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PlaceDetails {

        private Long meetingPlaceId;
        private String placeName;
        private String memo;
        private Double lat;
        private Double lng;
        private String address;
        private Integer order;
        private String category;
        private String googlePlaceId;
    }
}
