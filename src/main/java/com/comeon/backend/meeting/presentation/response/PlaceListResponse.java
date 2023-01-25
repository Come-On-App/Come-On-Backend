package com.comeon.backend.meeting.presentation.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PlaceListResponse {

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
