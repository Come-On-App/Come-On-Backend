package com.comeon.backend.user.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

public class LoginDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GoogleOauthRequest {

        @NotBlank
        private String idToken;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class KakaoOauthRequest {

        @NotBlank
        private String code;
    }

    @Getter
    @AllArgsConstructor
    public static class OauthLoginResponse {

        private AccessTokenResponse accessToken;
        private RefreshTokenResponse refreshToken;
    }

    @Getter
    @AllArgsConstructor
    public static class AccessTokenResponse {
        private String token;
        private Long expiry;
        private Long userId;
    }

    @Getter
    @AllArgsConstructor
    public static class RefreshTokenResponse {
        private String token;
        private Long expiry;
    }
}
