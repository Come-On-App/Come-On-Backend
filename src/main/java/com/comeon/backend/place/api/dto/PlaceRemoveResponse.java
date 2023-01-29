package com.comeon.backend.place.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PlaceRemoveResponse {

    private boolean success;

    public PlaceRemoveResponse() {
        this.success = true;
    }
}
