package com.comeon.backend.meeting.command.application.v1.dto;

import com.comeon.backend.meeting.command.domain.PlaceInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PlaceAddRequest {

    @NotBlank
    private String placeName;

    private String memo;

    private Double lat;

    private Double lng;

    private String address;

    @NotBlank
    private String category;

    private String googlePlaceId;

    public PlaceInfo toPlaceInfo() {
        return PlaceInfo.builder()
                .placeName(this.placeName)
                .placeMemo(this.memo)
                .lat(this.lat)
                .lng(this.lng)
                .address(this.address)
                .category(this.category)
                .googlePlaceId(this.googlePlaceId)
                .build();
    }
}
