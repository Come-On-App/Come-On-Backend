package com.comeon.backend.user.presentation.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class GoogleOauthRequest {

    @NotBlank
    private String idToken;

    public GoogleOauthRequest(String idToken) {
        this.idToken = idToken;
    }
}
