package com.comeon.backend.place.command.application;

import com.comeon.backend.place.command.domain.PlaceInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

public class PlaceCommandDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AddRequest {

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

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ModifyRequest {

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
}
