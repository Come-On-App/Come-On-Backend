package com.comeon.backend.place.command.application.dto;

import com.comeon.backend.place.command.domain.PlaceInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PlaceModifyRequest {

    @NotBlank
    private String name;
    private String memo;
    private Double lat;
    private Double lng;
    private String address;

    @NotBlank
    private String category;
    private String googlePlaceId;

    public PlaceInfo toPlaceInfo() {
        return new PlaceInfo(
                this.name, this.memo, this.lat, this.lng,
                this.address, this.category, this.googlePlaceId
        );
    }
}
