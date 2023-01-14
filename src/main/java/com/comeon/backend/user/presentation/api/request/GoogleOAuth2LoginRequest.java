package com.comeon.backend.user.presentation.api.request;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class GoogleOAuth2LoginRequest {

    @NotBlank
    private String idToken;

    public GoogleOAuth2LoginRequest(String idToken) {
        this.idToken = idToken;
    }
}
