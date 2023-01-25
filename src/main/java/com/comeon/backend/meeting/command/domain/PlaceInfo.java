package com.comeon.backend.meeting.command.domain;

import lombok.Getter;

@Getter
public class PlaceInfo {

    private String placeName;
    private String placeMemo;
    private Double lat;
    private Double lng;
    private String address;
    private PlaceCategory category;
    private String googlePlaceId;

    public PlaceInfo(String placeName, String placeMemo, Double lat, Double lng, String address, String category, String googlePlaceId) {
        this.placeName = placeName;
        this.placeMemo = placeMemo;
        this.lat = lat;
        this.lng = lng;
        this.address = address;
        this.category = PlaceCategory.of(category);
        this.googlePlaceId = googlePlaceId;
    }
}
