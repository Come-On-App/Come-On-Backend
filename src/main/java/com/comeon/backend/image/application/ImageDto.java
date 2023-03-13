package com.comeon.backend.image.application;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ImageDto {

    @Getter
    @AllArgsConstructor
    public static class UploadResponse {

        private String imageUrl;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RemoveRequest {

        private String imageUrl;
    }
}
