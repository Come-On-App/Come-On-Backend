package com.comeon.backend.user.presentation.api.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KakaoOAuth2LoginRequest {

    @NotBlank
    private String code;
}
