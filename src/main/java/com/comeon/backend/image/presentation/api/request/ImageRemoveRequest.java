package com.comeon.backend.image.presentation.api.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ImageRemoveRequest {

    private String imageUrl;
}
