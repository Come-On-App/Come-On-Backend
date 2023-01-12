package com.comeon.backend.user.presentation.api.request;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GoogleOAuth2LoginRequest {

    private String idToken;

    public GoogleOAuth2LoginRequest(String idToken) {
        this.idToken = idToken;
    }
}
