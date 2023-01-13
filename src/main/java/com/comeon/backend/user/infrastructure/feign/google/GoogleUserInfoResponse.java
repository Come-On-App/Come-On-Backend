package com.comeon.backend.user.infrastructure.feign.google;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GoogleUserInfoResponse {

    private String oauthId;
    private String email;
    private String name;
}
