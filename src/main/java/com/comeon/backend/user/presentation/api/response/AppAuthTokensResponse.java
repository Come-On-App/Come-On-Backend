package com.comeon.backend.user.presentation.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AppAuthTokensResponse {

    private AccessToken accessToken;
    private RefreshToken refreshToken;

    @Getter
    @AllArgsConstructor
    public static class AccessToken {
        private String token;
        private Long expiry;
        private Long userId;
    }

    @Getter
    @AllArgsConstructor
    public static class RefreshToken {
        private String token;
        private Long expiry;
    }
}
