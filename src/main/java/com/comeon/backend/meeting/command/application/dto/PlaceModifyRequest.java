package com.comeon.backend.meeting.command.application.dto;

import com.comeon.backend.meeting.command.domain.PlaceInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PlaceModifyRequest {

    @NotBlank
    private String name;

    private String memo;

    @NotNull
    private Double lat;

    @NotNull
    private Double lng;

    @NotBlank
    private String address;

    @NotBlank
    private String category;

    @NotBlank
    private String googlePlaceId;

    public PlaceInfo toPlaceInfo() {
        return PlaceInfo.builder()
                .placeName(this.name)
                .placeMemo(this.memo)
                .lat(this.lat)
                .lng(this.lng)
                .address(this.address)
                .category(this.category)
                .googlePlaceId(this.googlePlaceId)
                .build();
    }
}
