package com.comeon.backend.place.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PlaceModifyResponse {

    private boolean success;

    public PlaceModifyResponse() {
        this.success = true;
    }
}
