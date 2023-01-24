package com.comeon.backend.image.presentation.response;

import lombok.Getter;

@Getter
public class ImageRemoveResponse {

    private Boolean success;

    public ImageRemoveResponse() {
        this.success = true;
    }
}
