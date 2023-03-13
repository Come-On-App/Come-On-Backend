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
public class MeetingDetails {

    private MeetingMetaData meetingMetaData;
    private List<MemberDetails> members;
    private List<DateVotingSimple> votingDates;
    private List<PlaceDetails> places;

    public void setHostUserProfileImageUrl(String defaultImageUrl) {
        this.meetingMetaData.setHostUserProfileImageUrl(defaultImageUrl);
    }

    // Collection
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MemberDetails {

        private Long memberId;
        private Long userId;
        private String nickname;
        private String profileImageUrl;
        private String memberRole;

        public void setProfileImageUrl(String defaultImageUrl) {
            this.profileImageUrl = defaultImageUrl;
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DateVotingSimple {

        @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        private LocalDate date;

        private int memberCount;
        private boolean myVoting;
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
