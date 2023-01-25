package com.comeon.backend.meeting.presentation.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PlaceAddRequest {

    private String name;
    private String memo;
    private String address;
    private Double lat;
    private Double lng;
    private String category;
    private String googlePlaceId;
}
