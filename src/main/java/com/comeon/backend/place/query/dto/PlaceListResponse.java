package com.comeon.backend.place.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
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
